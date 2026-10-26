package com.airpay.mobile.utilities.accessibility;

import com.airpay.mobile.utilities.config.ConfigLoader;
import com.airpay.mobile.utilities.config.FrameworkConfig;
import com.airpay.mobile.utilities.config.ProjectPaths;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public final class A11yScorecardWriter {

    private static final Logger LOG = LogManager.getLogger(A11yScorecardWriter.class);

    private A11yScorecardWriter() {
    }

    public static Path write(FrameworkConfig config, String baseUrl, List<A11yPageScanResult> pages,
                             Path outDir, String filePrefix, String productTitle) {
        return write(config, baseUrl, pages, outDir, filePrefix, productTitle, true);
    }

    /** Writes only {@code pages}. Pages already in the scorecard are not kept. */
    public static Path writeReplacing(FrameworkConfig config, String baseUrl, List<A11yPageScanResult> pages,
                                      Path outDir, String filePrefix, String productTitle) {
        cachedExisting = List.of();
        existingLoaded = true;
        try {
            return write(config, baseUrl, pages, outDir, filePrefix, productTitle, true);
        } finally {
            existingLoaded = false;
            cachedExisting = List.of();
        }
    }

    public static Path write(FrameworkConfig config, String baseUrl, List<A11yPageScanResult> pages,
                             Path outDir, String filePrefix, String productTitle, boolean archive) {
        String prefix = (filePrefix == null || filePrefix.isBlank()) ? "a11y" : filePrefix.trim();
        String title = (productTitle == null || productTitle.isBlank()) ? "Accessibility" : productTitle.trim();
        try {
            Files.createDirectories(outDir);
            List<A11yPageScanResult> combined = mergeWithExisting(outDir.resolve(prefix + "-scan.json"), pages);
            String ts = Instant.now().toString();
            String json = A11yJson.reportDocument(baseUrl, ts, combined, prefix);
            pages = combined;
            Files.writeString(outDir.resolve(prefix + "-scan.json"), json, StandardCharsets.UTF_8);

            String html = renderHtml(baseUrl, ts, pages, title, prefix);
            Path htmlPath = outDir.resolve(prefix + "-scorecard.html");
            Files.writeString(htmlPath, html, StandardCharsets.UTF_8);
            Files.writeString(outDir.resolve("index.html"), html, StandardCharsets.UTF_8);

            Allure.addAttachment(prefix + "-scan", "application/json", json, ".json");
            Allure.addAttachment(prefix + "-scorecard", "text/html", html, ".html");

            Path expectedActual = A11yExpectedActualReport.write(outDir, baseUrl, ts, pages, prefix);

            StringBuilder index = new StringBuilder();
            index.append(title).append(" — screens scanned (").append(pages.size()).append(")\n");
            index.append("App: ").append(baseUrl).append("\n\n");
            int n = 1;
            for (A11yPageScanResult p : pages) {
                index.append(n++).append(". ")
                        .append(p.page().label())
                        .append(" [").append(p.page().id()).append("]")
                        .append(" | score=").append(p.score())
                        .append(" | violations=").append(p.violationsCount())
                        .append('\n');
            }
            Path indexPath = outDir.resolve(prefix + "-pages-scanned.txt");
            Files.writeString(indexPath, index.toString(), StandardCharsets.UTF_8);
            Path zipPath = archive ? zipReportFolder(outDir, prefix) : null;

            if (!archive) {
                LOG.info("Scorecard updated for {} pages at {}", pages.size(), htmlPath.toAbsolutePath());
                return htmlPath;
            }
            System.out.println();
            System.out.println("========== A11Y REPORTS ==========");
            System.out.println("Scorecard:          " + htmlPath.toAbsolutePath());
            System.out.println("Expected vs Actual: " + expectedActual.toAbsolutePath());
            System.out.println("CSV:                " + outDir.resolve(prefix + "-expected-vs-actual.csv").toAbsolutePath());
            System.out.println("Pages list:         " + indexPath.toAbsolutePath());
            System.out.println("Folder:             " + outDir.toAbsolutePath());
            if (zipPath != null) {
                System.out.println("ZIP:                " + zipPath.toAbsolutePath());
            }
            System.out.println("==================================");
            System.out.println();
            return htmlPath;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to write a11y scorecard to " + outDir, e);
        }
    }

    public static Path resolveReportDir(FrameworkConfig config) {
        String dir = config.get("a11y.report.dir", ProjectPaths.KIND_A11Y);
        return ProjectPaths.resolveOutput(dir);
    }

    public static Path zipReportFolder(Path reportDir, String filePrefix) {
        if (reportDir == null || !Files.isDirectory(reportDir)) {
            return null;
        }
        String prefix = filePrefix == null || filePrefix.isBlank() ? "a11y" : filePrefix.trim();
        Path zipPath = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y_ZIP)
                .resolve(prefix + "-a11y-reports.zip");
        try {
            Files.createDirectories(zipPath.getParent());
            Files.deleteIfExists(zipPath);
            try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath));
                 var walk = Files.walk(reportDir)) {
                walk.filter(Files::isRegularFile).forEach(file -> {
                    try {
                        zos.putNextEntry(new ZipEntry(reportDir.relativize(file).toString().replace('\\', '/')));
                        Files.copy(file, zos);
                        zos.closeEntry();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            return zipPath;
        } catch (Exception e) {
            LOG.warn("ZIP failed: {}", e.toString());
            return null;
        }
    }

    private static List<A11yPageScanResult> cachedExisting = List.of();
    private static boolean existingLoaded;

    /**
     * Keeps every page already in the scorecard. A page scanned again replaces that same id.
     * Pages that were not scanned this run stay in the report.
     */
    private static List<A11yPageScanResult> mergeWithExisting(Path existingJson, List<A11yPageScanResult> fresh)
            throws IOException {
        if (!existingLoaded) {
            cachedExisting = readExisting(existingJson);
            existingLoaded = true;
        }
        Map<String, A11yPageScanResult> byId = new LinkedHashMap<>();
        for (A11yPageScanResult page : cachedExisting) {
            byId.put(page.page().id(), page);
        }
        int added = 0;
        for (A11yPageScanResult page : fresh) {
            if (!byId.containsKey(page.page().id())) {
                added++;
            }
            byId.put(page.page().id(), page);
        }
        LOG.info("Scorecard merge: existing={} thisRun={} added={} total={}",
                cachedExisting.size(), fresh.size(), added, byId.size());
        return new ArrayList<>(byId.values());
    }

    private static List<A11yPageScanResult> readExisting(Path existingJson) throws IOException {
        if (existingJson == null || !Files.isRegularFile(existingJson) || Files.size(existingJson) < 20) {
            return List.of();
        }
        List<A11yPageScanResult> loaded = A11yJson.parsePages(Files.readString(existingJson));
        if (loaded.isEmpty() && Files.size(existingJson) > 50_000) {
            throw new IOException("Existing scorecard could not be read: " + existingJson);
        }
        return loaded;
    }

    private static String renderHtml(String baseUrl, String timestamp, List<A11yPageScanResult> pages,
                                     String productTitle, String prefix) {
        int totalV = pages.stream().mapToInt(A11yPageScanResult::violationsCount).sum();
        int totalP = pages.stream().mapToInt(A11yPageScanResult::passesCount).sum();
        List<Integer> scores = pages.stream().filter(p -> !p.hasError()).map(A11yPageScanResult::score).toList();
        int worst = scores.stream().mapToInt(Integer::intValue).min().orElse(0);
        int best = scores.stream().mapToInt(Integer::intValue).max().orElse(0);
        int avg = scores.isEmpty() ? 0
                : (int) Math.round(scores.stream().mapToInt(Integer::intValue).average().orElse(0));
        String heroClass = avg >= 90 ? "pass" : (avg >= 70 ? "warn" : "fail");
        Map<String, List<A11yFinding>> byRule = new LinkedHashMap<>();
        for (A11yPageScanResult page : pages) {
            for (A11yFinding f : page.findings()) {
                byRule.computeIfAbsent(nullToEmpty(f.ruleId()), k -> new ArrayList<>()).add(f);
            }
        }
        int uniqueTypes = byRule.size();
        int fieldFailCount = pages.stream().mapToInt(p -> p.findings().size()).sum();
        String wcagStatus = avg >= 90 ? "pass" : (avg >= 70 ? "partial" : "fail");
        String wcagLabel = avg >= 90 ? "PASS" : (avg >= 70 ? avg + "%" : "FAIL");
        String isStatus = avg >= 90 ? "pass" : "partial";
        String isLabel = avg >= 90 ? "Compliant" : "Partially Compliant";

        StringBuilder pageRows = new StringBuilder();
        StringBuilder scannedList = new StringBuilder("<ol class=\"scan-list\">");
        int idx = 1;
        for (A11yPageScanResult p : pages) {
            String statusBadge = p.hasError() ? "ERROR" : (p.violationsCount() == 0 ? "PASS" : "FAIL");
            String badgeCls = p.hasError() ? "badge-partial" : (p.violationsCount() == 0 ? "badge-pass" : "badge-fail");
            String sc = p.score() >= 90 ? "good" : (p.score() >= 70 ? "ok" : "bad");
            String bar = p.hasError() ? "#94a3b8"
                    : (p.score() >= 90 ? "#16a34a" : (p.score() >= 70 ? "#ea580c" : "#dc2626"));
            scannedList.append("<li><strong>").append(esc(p.page().label())).append("</strong>")
                    .append(" <code>").append(esc(p.page().id())).append("</code>")
                    .append("<br><span class=\"muted\">").append(esc(p.page().route())).append("</span></li>");
            pageRows.append("<tr><td>").append(idx++).append("</td>")
                    .append("<td><div class=\"page-name\">").append(esc(p.page().label())).append("</div>")
                    .append("<div class=\"page-meta\"><code>").append(esc(p.page().id())).append("</code></div></td>")
                    .append("<td class=\"page-score ").append(sc).append("\">").append(p.score()).append("</td>")
                    .append("<td><div class=\"score-bar\"><div class=\"score-fill\" style=\"width:")
                    .append(p.score()).append("%;background:").append(bar).append("\"></div></div></td>")
                    .append("<td><span class=\"badge ").append(badgeCls).append("\">")
                    .append(p.hasError() ? "ERR" : p.violationsCount()).append("</span></td>")
                    .append("<td><span class=\"badge badge-pass\">").append(p.passesCount()).append("</span></td>")
                    .append("<td><span class=\"badge ").append(badgeCls).append("\">")
                    .append(statusBadge).append("</span></td></tr>\n");
        }
        scannedList.append("</ol>");

        StringBuilder fieldRows = new StringBuilder();
        fieldRows.append("<table class=\"ea-table\"><thead><tr><th>Page</th><th>Rule</th><th>Field</th>")
                .append("<th>Expected</th><th>Actual</th><th>Impact</th></tr></thead><tbody>");
        for (A11yPageScanResult p : pages) {
            for (A11yFinding f : p.findings()) {
                fieldRows.append("<tr><td>").append(esc(f.pageLabel())).append("</td><td><code>")
                        .append(esc(f.ruleId())).append("</code></td><td>").append(esc(f.field()))
                        .append("</td><td class=\"expected\">").append(esc(f.expected()))
                        .append("</td><td class=\"actual\">").append(esc(f.actual()))
                        .append("</td><td>").append(esc(f.impact())).append("</td></tr>");
            }
        }
        fieldRows.append("</tbody></table>");

        StringBuilder violationCards = new StringBuilder();
        if (byRule.isEmpty()) {
            violationCards.append("<p style=\"color:#16a34a;font-weight:600;\">No violations found.</p>");
        } else {
            List<Map.Entry<String, List<A11yFinding>>> ordered = byRule.entrySet().stream()
                    .sorted(Comparator.comparing(e -> e.getValue().getFirst().impact(),
                            Comparator.nullsLast(String::compareTo)))
                    .collect(Collectors.toList());
            for (Map.Entry<String, List<A11yFinding>> entry : ordered) {
                A11yFinding sample = entry.getValue().getFirst();
                String impact = nullToEmpty(sample.impact()).toLowerCase(Locale.ROOT);
                String severityClass = "critical".equals(impact) ? "crit"
                        : ("serious".equals(impact) ? "serious" : "moderate");
                violationCards.append("<div class=\"violation-card ").append(severityClass).append("\">")
                        .append("<div class=\"vheader\" onclick=\"this.nextElementSibling.classList.toggle('open')\">")
                        .append("<span>").append(esc(sample.ruleId())).append(" — ").append(esc(sample.help()))
                        .append("</span><span class=\"badge badge-serious\">").append(esc(sample.impact()))
                        .append(" · ").append(entry.getValue().size()).append("</span></div><div class=\"vbody open\"><ul>");
                int shown = 0;
                for (A11yFinding n : entry.getValue()) {
                    if (shown++ >= 12) {
                        violationCards.append("<li>… ").append(entry.getValue().size() - 12).append(" more</li>");
                        break;
                    }
                    violationCards.append("<li><strong>").append(esc(n.pageLabel())).append("</strong> · ")
                            .append(esc(n.field())).append("<br><code>").append(esc(n.selector()))
                            .append("</code></li>");
                }
                violationCards.append("</ul></div></div>");
            }
        }

        return """
                <!DOCTYPE html>
                <html lang="en"><head><meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Airpay __TITLE__ — WCAG Compliance Scorecard</title>
                <style>
                  * { box-sizing: border-box; margin: 0; padding: 0; }
                  body { font-family: Inter, system-ui, sans-serif; background: #f0f4f8; color: #1e293b; padding: 1.5rem; }
                  .container { max-width: 1100px; margin: 0 auto; }
                  h1 { font-size: 1.35rem; font-weight: 700; }
                  .subtitle { color: #64748b; font-size: .8125rem; }
                  .meta { display: flex; gap: 1rem; flex-wrap: wrap; font-size: .75rem; color: #64748b; margin: 1rem 0 1.5rem; padding: .75rem 1rem; background: #fff; border-radius: 8px; }
                  .hero { background: linear-gradient(135deg, #1e293b, #334155); color: #fff; border-radius: 12px; padding: 1.5rem 2rem; margin-bottom: 1.5rem; display: flex; justify-content: space-between; align-items: center; }
                  .hero-score { font-size: 3rem; font-weight: 800; }
                  .hero-score.pass { color: #4ade80; } .hero-score.warn { color: #fb923c; } .hero-score.fail { color: #f87171; }
                  .stats { display: grid; grid-template-columns: repeat(5, 1fr); gap: .5rem; margin-bottom: 1.5rem; }
                  .stat { background: #fff; border-radius: 8px; padding: .75rem; text-align: center; }
                  .stat-num { font-size: 1.5rem; font-weight: 700; }
                  .stat-label { font-size: .625rem; color: #64748b; text-transform: uppercase; }
                  .stat.green .stat-num { color: #16a34a; } .stat.red .stat-num { color: #dc2626; }
                  .stat.orange .stat-num { color: #ea580c; } .stat.blue .stat-num { color: #2563eb; }
                  .section { background: #fff; border-radius: 10px; padding: 1.25rem 1.5rem; margin-bottom: 1.25rem; }
                  table { width: 100%; border-collapse: collapse; font-size: .75rem; }
                  th { text-align: left; padding: .5rem; border-bottom: 2px solid #e2e8f0; font-size: .625rem; color: #64748b; }
                  td { padding: .5rem; border-bottom: 1px solid #f1f5f9; vertical-align: top; }
                  .badge { display: inline-block; padding: .125rem .375rem; border-radius: 999px; font-size: .625rem; font-weight: 600; }
                  .badge-pass { background: #dcfce7; color: #166534; } .badge-fail { background: #fee2e2; color: #991b1b; }
                  .badge-partial { background: #fef9c3; color: #854d0e; } .badge-serious { background: #fee2e2; color: #991b1b; }
                  .score-bar { height: 6px; background: #e2e8f0; border-radius: 3px; }
                  .score-fill { height: 100%; border-radius: 3px; }
                  .page-score.good { color: #16a34a; font-weight: 700; } .page-score.ok { color: #ea580c; font-weight: 700; }
                  .page-score.bad { color: #dc2626; font-weight: 700; }
                  .standards { display: grid; grid-template-columns: repeat(3, 1fr); gap: .75rem; }
                  .std-card { border: 1px solid #e2e8f0; border-radius: 8px; padding: 1rem; text-align: center; }
                  .std-status.pass { color: #16a34a; font-weight: 700; } .std-status.fail { color: #dc2626; font-weight: 700; }
                  .std-status.partial { color: #ea580c; font-weight: 700; }
                  .expected { background: #ecfdf5; color: #065f46; } .actual { background: #fff7ed; color: #9a3412; }
                  .violation-card { border: 1px solid #e2e8f0; border-radius: 6px; margin-bottom: .5rem; }
                  .violation-card.crit { border-left: 3px solid #7c3aed; } .violation-card.serious { border-left: 3px solid #dc2626; }
                  .violation-card.moderate { border-left: 3px solid #ea580c; }
                  .vheader { padding: .5rem .75rem; display: flex; justify-content: space-between; background: #fafafa; font-weight: 600; cursor: pointer; }
                  .vbody { padding: .5rem .75rem; display: none; font-size: .75rem; } .vbody.open { display: block; }
                  .hub { display: grid; grid-template-columns: repeat(3, 1fr); gap: .75rem; margin-bottom: 1.25rem; }
                  .hub a { display: block; background: #fff; border: 1px solid #e2e8f0; border-radius: 10px; padding: 1rem; text-decoration: none; color: inherit; }
                  .muted { font-size: .6875rem; color: #64748b; } code { font-size: .7rem; }
                  @media (max-width: 900px) { .stats, .hub, .standards { grid-template-columns: 1fr; } }
                </style></head><body><div class="container">
                <h1>Airpay __TITLE__ — Accessibility Compliance Report</h1>
                <p class="subtitle">WCAG 2.1 AA / IS 17802 / RPWD Act 2016 — Automated scan (UiAutomator2 accessibility tree)</p>
                <div class="meta">
                  <span>App: <strong>__BASE__</strong></span>
                  <span>Tool: <strong>Appium UiAutomator2 + native node scan</strong></span>
                  <span>Screens: <strong>__PAGES__</strong></span>
                  <span>Field findings: <strong>__FIELDS__</strong></span>
                </div>
                <div class="hub">
                  <a href="#expected-actual"><strong>Expected vs Actual</strong><span class="muted"> Field-level guideline vs observed</span></a>
                  <a href="__PREFIX__-expected-vs-actual.html"><strong>Full detail HTML</strong><span class="muted"> __PREFIX__-expected-vs-actual.html</span></a>
                  <a href="__PREFIX__-expected-vs-actual.csv"><strong>Excel / CSV</strong><span class="muted"> Share with QA</span></a>
                </div>
                <div class="hero" id="summary"><div><h2>Accessibility Score</h2><p class="muted">Average across scanned screens. The lowest single screen is listed separately.</p></div>
                  <div class="hero-score __HERO__">__AVG__<span style="font-size:1.25rem;opacity:.5">/100</span></div></div>
                <div class="stats">
                  <div class="stat green"><div class="stat-num">__PASSES__</div><div class="stat-label">Passed checks</div></div>
                  <div class="stat red"><div class="stat-num">__VIOL__</div><div class="stat-label">Total violations</div></div>
                  <div class="stat orange"><div class="stat-num">__UNIQUE__</div><div class="stat-label">Unique types</div></div>
                  <div class="stat orange"><div class="stat-num">__WORST__</div><div class="stat-label">Lowest screen</div></div>
                  <div class="stat green"><div class="stat-num">__BEST__</div><div class="stat-label">Best score</div></div>
                </div>
                <div class="section"><h3>Regulatory Standards Compliance</h3>
                  <div class="standards">
                    <div class="std-card"><h4>WCAG 2.1 Level AA</h4><div class="std-status __WCAG_S__">__WCAG_L__</div></div>
                    <div class="std-card"><h4>IS 17802 (Part 1)</h4><div class="std-status __IS_S__">__IS_L__</div></div>
                    <div class="std-card"><h4>RPWD Act 2016 §42</h4><div class="std-status __IS_S__">__IS_L__</div></div>
                  </div>
                  <p class="muted" style="margin-top:.75rem">Native scan covers names, labels, and touch targets. Contrast, TalkBack gestures, and focus order still need manual QA.</p>
                </div>
                <div class="section" id="expected-actual"><h3>Expected vs Actual (__FIELDS__)</h3>__FIELD_TABLE__</div>
                <div class="section" id="pages"><h3>Screens scanned (__PAGES__)</h3>__SCAN_LIST__</div>
                <div class="section" id="scores"><h3>Per-screen scores</h3>
                  <table><thead><tr><th>#</th><th>Screen</th><th>Score</th><th></th><th>Violations</th><th>Passes</th><th>Status</th></tr></thead>
                  <tbody>__PAGE_ROWS__</tbody></table></div>
                <div class="section" id="violations"><h3>Violations by rule</h3>__VIOL_CARDS__</div>
                </div></body></html>
                """
                .replace("__TITLE__", esc(productTitle))
                .replace("__BASE__", esc(baseUrl))
                .replace("__TS__", esc(timestamp))
                .replace("__PAGES__", String.valueOf(pages.size()))
                .replace("__FIELDS__", String.valueOf(fieldFailCount))
                .replace("__PREFIX__", esc(prefix))
                .replace("__HERO__", heroClass)
                .replace("__WORST__", String.valueOf(worst))
                .replace("__PASSES__", String.valueOf(totalP))
                .replace("__VIOL__", String.valueOf(totalV))
                .replace("__UNIQUE__", String.valueOf(uniqueTypes))
                .replace("__AVG__", String.valueOf(avg))
                .replace("__BEST__", String.valueOf(best))
                .replace("__WCAG_S__", wcagStatus)
                .replace("__WCAG_L__", wcagLabel)
                .replace("__IS_S__", isStatus)
                .replace("__IS_L__", isLabel)
                .replace("__FIELD_TABLE__", fieldRows.toString())
                .replace("__SCAN_LIST__", scannedList.toString())
                .replace("__PAGE_ROWS__", pageRows.toString())
                .replace("__VIOL_CARDS__", violationCards.toString());
    }

    private static String nullToEmpty(String v) {
        return v == null ? "" : v;
    }

    private static String esc(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}
