package com.airpay.mobile.utilities.accessibility;

import com.airpay.mobile.utilities.config.ConfigLoader;
import com.airpay.mobile.utilities.config.ProjectPaths;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
        Files.copy(scan, backup, StandardCopyOption.REPLACE_EXISTING);

        Set<String> keep = new LinkedHashSet<>();
        for (int i = 1; i < args.length; i++) {
            String log = Files.readString(Path.of(args[i]));
            Matcher matcher = SCANNED.matcher(log);
            while (matcher.find()) {
                keep.add(matcher.group(1).trim());
            }
        }
        if (keep.isEmpty()) {
            throw new IllegalStateException("No scanned page ids found in the log files.");
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
