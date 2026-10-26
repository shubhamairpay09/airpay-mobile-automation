package com.airpay.mobile.utilities.accessibility;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class A11yExpectedActualReport {

    private static final Logger LOG = LogManager.getLogger(A11yExpectedActualReport.class);

    private A11yExpectedActualReport() {
    }

    public static Path write(Path outDir, String baseUrl, String timestamp, List<A11yPageScanResult> pages,
                             String filePrefix) throws IOException {
        Files.createDirectories(outDir);
        String prefix = filePrefix == null || filePrefix.isBlank() ? "a11y" : filePrefix.trim();
        Path htmlPath = outDir.resolve(prefix + "-expected-vs-actual.html");
        Path csvPath = outDir.resolve(prefix + "-expected-vs-actual.csv");
        Files.writeString(htmlPath, renderHtml(baseUrl, timestamp, pages, prefix), StandardCharsets.UTF_8);
        Files.writeString(csvPath, renderCsv(pages), StandardCharsets.UTF_8);
        Allure.addAttachment(prefix + "-expected-vs-actual", "text/html",
                Files.readString(htmlPath), ".html");
        LOG.info("Expected vs Actual: {} , {}", htmlPath, csvPath);
        return htmlPath;
    }

    private static String renderHtml(String baseUrl, String timestamp, List<A11yPageScanResult> pages, String prefix) {
        StringBuilder rows = new StringBuilder();
        for (A11yPageScanResult p : pages) {
            if (p.findings().isEmpty() && !p.hasError()) {
                rows.append("<tr><td>").append(esc(p.page().label())).append("</td><td>—</td><td>—</td>")
                        .append("<td class=\"expected\">Screen meets automated native checks</td>")
                        .append("<td class=\"actual\">No violations</td><td><span class=\"ok\">PASS</span></td></tr>");
                continue;
            }
            for (A11yFinding f : p.findings()) {
                rows.append("<tr><td>").append(esc(f.pageLabel())).append("</td><td><code>")
                        .append(esc(f.ruleId())).append("</code></td><td>").append(esc(f.field()))
                        .append("</td><td class=\"expected\">").append(esc(f.expected()))
                        .append("</td><td class=\"actual\">").append(esc(f.actual()))
                        .append("<br><code>").append(esc(f.selector())).append("</code></td>")
                        .append("<td><span class=\"fail\">").append(esc(f.impact())).append("</span></td></tr>");
            }
        }
        return """
                <!DOCTYPE html><html lang="en"><head><meta charset="UTF-8">
                <title>Expected vs Actual — __PREFIX__</title>
                <style>
                  body { font-family: system-ui, sans-serif; margin: 1.5rem; color: #1e293b; }
                  table { width: 100%; border-collapse: collapse; font-size: .8rem; }
                  th, td { border: 1px solid #e2e8f0; padding: .5rem; vertical-align: top; }
                  th { background: #f8fafc; text-align: left; }
                  .expected { background: #ecfdf5; } .actual { background: #fff7ed; }
                  .ok { color: #166534; font-weight: 700; } .fail { color: #991b1b; font-weight: 700; }
                </style></head><body>
                <h1>Expected vs Actual</h1>
                <p>App: __BASE__ · Generated: __TS__ · <a href="index.html">Scorecard</a></p>
                <table><thead><tr><th>Page</th><th>Rule</th><th>Field</th><th>Expected</th><th>Actual</th><th>Status</th></tr></thead>
                <tbody>__ROWS__</tbody></table></body></html>
                """
                .replace("__PREFIX__", esc(prefix))
                .replace("__BASE__", esc(baseUrl))
                .replace("__TS__", esc(timestamp))
                .replace("__ROWS__", rows.toString());
    }

    private static String renderCsv(List<A11yPageScanResult> pages) {
        StringBuilder sb = new StringBuilder("Page,Rule,Field,Expected,Actual,Impact,Selector,Status\n");
        for (A11yPageScanResult p : pages) {
            for (A11yFinding f : p.findings()) {
                sb.append(csv(f.pageLabel())).append(',')
                        .append(csv(f.ruleId())).append(',')
                        .append(csv(f.field())).append(',')
                        .append(csv(f.expected())).append(',')
                        .append(csv(f.actual())).append(',')
                        .append(csv(f.impact())).append(',')
                        .append(csv(f.selector())).append(',')
                        .append(csv(f.status())).append('\n');
            }
        }
        return sb.toString();
    }

    private static String csv(String v) {
        String s = v == null ? "" : v.replace("\"", "\"\"");
        return "\"" + s + "\"";
    }

    private static String esc(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}
