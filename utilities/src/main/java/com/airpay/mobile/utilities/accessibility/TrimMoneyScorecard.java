package com.airpay.mobile.utilities.accessibility;

import com.airpay.mobile.utilities.config.ConfigLoader;
import com.airpay.mobile.utilities.config.ProjectPaths;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Rebuilds the money scorecard from screens actually scanned in the latest capture runs.
 */
public final class TrimMoneyScorecard {

    private static final Pattern SCANNED = Pattern.compile("A11y ([^|]+?) \\| score=");

    private TrimMoneyScorecard() {
    }

    public static void main(String[] args) throws Exception {
        Path repo = Path.of(args.length > 0 ? args[0] : ".").toAbsolutePath().normalize();
        Path report = repo.resolve("target/money/accessibility_testing_result");
        Path scan = report.resolve("money-scan.json");
        Path backup = repo.resolve("target/money/money-scan-full-before-trim.json");
        Files.createDirectories(backup.getParent());

        if (args.length > 1 && "restore".equalsIgnoreCase(args[1])) {
            if (!Files.isRegularFile(backup)) {
                throw new IllegalStateException("Missing backup: " + backup);
            }
            List<A11yPageScanResult> full = A11yJson.parsePages(Files.readString(backup));
            List<A11yPageScanResult> current = Files.isRegularFile(scan)
                    ? A11yJson.parsePages(Files.readString(scan)) : List.of();
            Map<String, A11yPageScanResult> byId = new LinkedHashMap<>();
            for (A11yPageScanResult p : full) {
                byId.put(p.page().id(), p);
            }
            for (A11yPageScanResult p : current) {
                byId.put(p.page().id(), p);
            }
            System.setProperty("airpay.product", "money");
            Path html = A11yScorecardWriter.writeReplacing(
                    ConfigLoader.load(),
                    "com.finmapp.fininfinity",
                    new ArrayList<>(byId.values()),
                    ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y),
                    "money",
                    "airpay money Android");
            System.out.println("RESTORE pages=" + byId.size() + " fromBackup=" + full.size()
                    + " fromCurrent=" + current.size() + " html=" + html);
            return;
        }

        Files.copy(scan, backup, StandardCopyOption.REPLACE_EXISTING);

        Set<String> keep = new LinkedHashSet<>();
        for (int i = 1; i < args.length; i++) {
            Path input = Path.of(args[i]);
            String text = Files.readString(input);
            String name = input.getFileName().toString().toLowerCase();
            if (name.contains("deep-scan-ids") || name.endsWith("-ids.txt") || !text.contains("A11y ")) {
                for (String line : text.split("\\R")) {
                    String trimmed = line.trim();
                    if (trimmed.isEmpty() || trimmed.startsWith("Money ")) {
                        continue;
                    }
                    // "12. prod-us-stocks" or bare id
                    int dot = trimmed.indexOf(". ");
                    String id = (dot > 0 && Character.isDigit(trimmed.charAt(0)))
                            ? trimmed.substring(dot + 2).trim()
                            : trimmed;
                    if (!id.isEmpty() && !id.contains(" ")) {
                        keep.add(id);
                    }
                }
            } else {
                Matcher matcher = SCANNED.matcher(text);
                while (matcher.find()) {
                    keep.add(matcher.group(1).trim());
                }
            }
        }
        if (keep.isEmpty()) {
            throw new IllegalStateException("No scanned page ids found in the input files.");
        }

        List<A11yPageScanResult> all = A11yJson.parsePages(Files.readString(scan));
        List<A11yPageScanResult> latest = new ArrayList<>();
        for (A11yPageScanResult page : all) {
            if (keep.contains(page.page().id())) {
                latest.add(page);
            }
        }
        if (latest.isEmpty()) {
            throw new IllegalStateException("None of the " + keep.size() + " scanned ids are in the scorecard.");
        }

        System.setProperty("airpay.product", "money");
        Path html = A11yScorecardWriter.writeReplacing(
                ConfigLoader.load(),
                "com.finmapp.fininfinity",
                latest,
                ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y),
                "money",
                "airpay money Android");
        System.out.println("TRIM kept=" + latest.size()
                + " scannedIds=" + keep.size()
                + " removed=" + (all.size() - latest.size())
                + " html=" + html);
    }
}
