package com.airpay.mobile.utilities.config;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Report/log output under {@code target/<product>/} at repo root (same layout as airpay-automation web).
 */
public final class ProjectPaths {

    public static final String KIND_LOGS = "logs";
    public static final String KIND_SCREENSHOTS = "screenshots/failures";
    public static final String KIND_ALLURE = "allure-results";
    public static final String KIND_A11Y = "accessibility_testing_result";
    public static final String KIND_A11Y_ZIP = "accessibility_email_artifact";

    private static final List<String> OUTPUT_DIR_KEYS = List.of(
            "a11y.report.dir",
            "report.screenshot.dir",
            "airpay.log.dir");

    private static final Map<String, String> PRODUCT_ALIASES = Map.of(
            "airpay_capital", "capital",
            "capital", "capital",
            "airpay_money", "money",
            "money", "money",
            "airpay_vyaapaar", "vyaapaar",
            "vyaapaar", "vyaapaar",
            "utilities", "utilities");

    private ProjectPaths() {
    }

    public static void installDefaultJvmDirs() {
        System.setProperty("airpay.log.dir", resolveKind(KIND_LOGS).toString());
        System.setProperty("allure.results.directory", resolveKind(KIND_ALLURE).toString());
    }

    public static void absolutizeOutputDirs(Properties props) {
        if (props != null) {
            String fromConfig = props.getProperty("airpay.product");
            if (fromConfig != null && !fromConfig.isBlank()) {
                System.setProperty("airpay.product", normalizeProduct(fromConfig));
            }
        }
        if (props != null) {
            for (String key : OUTPUT_DIR_KEYS) {
                String value = props.getProperty(key);
                if (value == null || value.isBlank()) {
                    continue;
                }
                props.setProperty(key, resolveOutput(value).toString());
            }
        }
        for (String key : OUTPUT_DIR_KEYS) {
            String value = System.getProperty(key);
            if (value == null || value.isBlank()) {
                continue;
            }
            System.setProperty(key, resolveOutput(value).toString());
        }
    }

    public static Path resolveKind(String kind) {
        return reportsRoot().resolve(normalizeProduct(activeProduct())).resolve(kind);
    }

    public static Path resolveOutput(String configured) {
        Path path = Path.of(configured);
        if (path.isAbsolute()) {
            return path.normalize();
        }
        String product = normalizeProduct(activeProduct());
        String normalized = configured.replace('\\', '/');
        if (normalized.startsWith("target/")) {
            String suffix = normalized.substring("target/".length());
            if (suffix.startsWith(product + "/")) {
                return reportsRoot().resolve(suffix);
            }
            return reportsRoot().resolve(product).resolve(suffix);
        }
        return reportsRoot().resolve(product).resolve(configured);
    }

    public static Path reportsRoot() {
        return ConfigLoader.projectRoot().resolve("target");
    }

    public static String normalizeProduct(String raw) {
        if (raw == null || raw.isBlank()) {
            return "capital";
        }
        String key = raw.trim().toLowerCase();
        return PRODUCT_ALIASES.getOrDefault(key, key);
    }

    private static String activeProduct() {
        String fromJvm = System.getProperty("airpay.product");
        if (fromJvm != null && !fromJvm.isBlank()) {
            return fromJvm.trim();
        }
        return "capital";
    }
}
