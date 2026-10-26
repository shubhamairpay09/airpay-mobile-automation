package com.airpay.money.runner;

import com.airpay.mobile.utilities.accessibility.A11yPageRef;
import com.airpay.mobile.utilities.accessibility.A11yPageScanResult;
import com.airpay.mobile.utilities.accessibility.A11yScorecardWriter;
import com.airpay.mobile.utilities.accessibility.AndroidA11yScanner;
import com.airpay.mobile.utilities.config.ConfigLoader;
import com.airpay.mobile.utilities.config.FrameworkConfig;
import com.airpay.mobile.utilities.config.ProjectPaths;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * Writes {@code target/money/accessibility_testing_result/} from already-captured
 * UiAutomator dumps. No Appium. Covered modules only: Profile, Calculators, FHC, Tax.
 */
public class MoneyDumpA11yRunner {

    private static final String[] PREFIXES = {
            "uidump-prof-",
            "uidump-calc-",
            "uidump-fh-",
            "uidump-tax-",
            "uidump-gold-",
            "uidump-ins-",
            "uidump-mf-",
            "uidump-nps-",
            "uidump-fd-",
            "uidump-usstk-",
            "uidump-instk-",
            "uidump-notif-",
            "uidump-credit-",
            "uidump-apply-",
            "uidump-deep-",
            "uidump-rest-",
            "uidump-left-",
            "uidump-pin-unlock",
            "uidump-relaunch-pin",
            "uidump-resume.xml",
            "uidump-newbuild",
            "uidump-account-setup",
            "uidump-next"
    };

    @Test
    public void writeScorecardsFromCapturedDumps() throws Exception {
        System.setProperty("airpay.product", "money");
        FrameworkConfig config = ConfigLoader.load();
        Path dumpDir = ProjectPaths.reportsRoot().resolve("money").resolve("manual-capture");
        Path v213 = dumpDir.resolve("v213");
        Assert.assertTrue(Files.isDirectory(dumpDir), "Missing dump dir: " + dumpDir);

        List<A11yPageScanResult> pages = new ArrayList<>();
        StringBuilder discovered = new StringBuilder("{\n  \"application\":\"airpay money\",\n  \"pages\":[\n");
        int discoveredCount = 0;
        List<Path> roots = new ArrayList<>();
        roots.add(dumpDir);
        if (Files.isDirectory(v213)) {
            roots.add(v213);
        }
        for (String prefix : PREFIXES) {
            List<Path> xmls = new ArrayList<>();
            for (Path root : roots) {
                try (Stream<Path> walk = Files.list(root)) {
                    xmls.addAll(walk
                            .filter(p -> p.getFileName().toString().startsWith(prefix)
                                    && p.getFileName().toString().endsWith(".xml"))
                            .toList());
                }
            }
            xmls = xmls.stream()
                    .collect(java.util.stream.Collectors.toMap(
                            p -> p.getFileName().toString(), p -> p, (a, b) -> a))
                    .values().stream()
                    .sorted(Comparator.comparing(p -> p.getFileName().toString()))
                    .toList();
            for (Path xml : xmls) {
                String file = xml.getFileName().toString();
                String stem = file.replaceFirst("\\.xml$", "");
                String id = slug(stem.replaceFirst("^uidump-", ""));
                String label = labelFor(prefix, stem);
                try {
                    String src = Files.readString(xml);
                    if (src == null || src.isBlank() || !src.contains("<hierarchy")) {
                        continue;
                    }
                    A11yPageRef ref = A11yPageRef.screen(id, moduleOf(prefix), label, stem);
                    pages.add(AndroidA11yScanner.scanHierarchy(src, config, ref));
                    if (discoveredCount > 0) {
                        discovered.append(",\n");
                    }
                    discovered.append("    {\"id\":\"").append(esc(id))
                            .append("\",\"label\":\"").append(esc(label))
                            .append("\",\"source\":\"").append(esc(file)).append("\"}");
                    discoveredCount++;
                } catch (Exception e) {
                    System.err.println("Skip " + file + ": " + e.getMessage());
                }
            }
        }
        discovered.append("\n  ]\n}\n");
        Assert.assertFalse(pages.isEmpty(), "No dump XML scanned under " + dumpDir);

        Path dir = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y);
        Files.createDirectories(dir);
        Files.writeString(dir.resolve("money-discovered-pages.json"), discovered.toString());

        Path html = A11yScorecardWriter.write(
                config,
                config.get("money.app.package", "com.finmapp.fininfinity"),
                pages,
                dir,
                "money",
                "airpay money Android");
        Assert.assertTrue(Files.exists(html), "Scorecard not written: " + html);
    }

    private static String moduleOf(String prefix) {
        return switch (prefix) {
            case "uidump-prof-" -> "profile";
            case "uidump-calc-" -> "calculators";
            case "uidump-fh-" -> "financial-health-check";
            case "uidump-tax-" -> "tax-filing";
            case "uidump-gold-" -> "digital-gold";
            case "uidump-ins-" -> "insurance";
            case "uidump-mf-" -> "mutual-funds";
            case "uidump-nps-" -> "nps";
            case "uidump-fd-" -> "fixed-deposit";
            case "uidump-usstk-" -> "us-stocks";
            case "uidump-instk-" -> "indian-stocks";
            case "uidump-notif-" -> "notifications";
            case "uidump-credit-" -> "credit-loan";
            case "uidump-apply-" -> "credit-loan";
            case "uidump-deep-" -> "credit-loan";
            case "uidump-rest-" -> "products";
            case "uidump-left-" -> "products";
            case "uidump-pin-unlock", "uidump-relaunch-pin", "uidump-resume.xml",
                    "uidump-newbuild", "uidump-account-setup", "uidump-next" -> "login";
            default -> "money";
        };
    }

    private static String labelFor(String prefix, String stem) {
        String shortName = stem.replaceFirst("^uidump-", "");
        String module = switch (prefix) {
            case "uidump-prof-" -> "Profile";
            case "uidump-calc-" -> "Calculators";
            case "uidump-fh-" -> "Financial Health Check";
            case "uidump-tax-" -> "Tax Filing";
            case "uidump-gold-" -> "Digital Gold";
            case "uidump-ins-" -> "Insurance";
            case "uidump-mf-" -> "Mutual Funds";
            case "uidump-nps-" -> "NPS";
            case "uidump-fd-" -> "Fixed Deposit";
            case "uidump-usstk-" -> "US Stocks";
            case "uidump-instk-" -> "Indian Stocks";
            case "uidump-notif-" -> "Notifications";
            case "uidump-credit-" -> "Credit and Loans";
            case "uidump-apply-" -> "Credit and Loans";
            case "uidump-deep-" -> "Credit and Loans";
            case "uidump-rest-" -> "Products";
            case "uidump-left-" -> "Products";
            case "uidump-pin-unlock", "uidump-relaunch-pin", "uidump-resume.xml",
                    "uidump-newbuild", "uidump-account-setup", "uidump-next" -> "Login";
            default -> "Money";
        };
        return module + " — " + shortName;
    }

    private static String slug(String raw) {
        return raw.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }

    private static String esc(String value) {
        return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
