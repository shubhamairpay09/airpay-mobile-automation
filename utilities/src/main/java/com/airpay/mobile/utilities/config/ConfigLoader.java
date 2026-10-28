package com.airpay.mobile.utilities.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;
import java.util.Set;

public final class ConfigLoader {

    private static final Set<String> RUNTIME_KEYS = Set.of("env", "airpay.product");

    private ConfigLoader() {
    }

    public static FrameworkConfig load() {
        ProjectPaths.installDefaultJvmDirs();
        Properties merged = new Properties();
        loadProjectConfig(merged, "defaults.properties");

        String product = resolveActiveProduct(merged);
        merged.setProperty("airpay.product", product);
        System.setProperty("airpay.product", product);

        // Active product only — avoids cross-product key collisions when more apps are added.
        loadProjectConfig(merged, product + ".properties");
        loadProjectConfig(merged, "secrets.properties");
        loadProjectConfig(merged, "ci-overrides.properties");

        overlaySystemProperties(merged);
        ProjectPaths.absolutizeOutputDirs(merged);
        return new FrameworkConfig(merged);
    }

    private static String resolveActiveProduct(Properties merged) {
        String fromJvm = System.getProperty("airpay.product");
        if (fromJvm != null && !fromJvm.isBlank()) {
            return ProjectPaths.normalizeProduct(fromJvm);
        }
        String fromFile = merged.getProperty("airpay.product");
        if (fromFile != null && !fromFile.isBlank()) {
            return ProjectPaths.normalizeProduct(fromFile);
        }
        return "money";
    }

    private static void overlaySystemProperties(Properties target) {
        System.getProperties().forEach((key, value) -> {
            if (!(key instanceof String k) || value == null) {
                return;
            }
            if (RUNTIME_KEYS.contains(k)) {
                String v = value.toString().trim();
                if (!v.isBlank()) {
                    target.setProperty(k, v);
                }
                return;
            }
            if (k.startsWith("airpay.")) {
                target.setProperty(k.substring("airpay.".length()), value.toString());
                return;
            }
            if (shouldOverlayKey(k, target)) {
                target.setProperty(k, value.toString());
            }
        });
    }

    private static boolean shouldOverlayKey(String key, Properties target) {
        if (target.containsKey(key)) {
            return true;
        }
        return key.startsWith("capital.")
                || key.startsWith("money.")
                || key.startsWith("vyaapaar.")
                || key.startsWith("android.")
                || key.startsWith("appium.")
                || key.startsWith("mobile.")
                || key.startsWith("a11y.")
                || key.startsWith("timeout.")
                || key.startsWith("report.");
    }

    private static void loadProjectConfig(Properties target, String fileName) {
        Path configFile = findProjectConfig(fileName);
        if (configFile == null) {
            return;
        }
        try (InputStream in = new FileInputStream(configFile.toFile())) {
            target.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load " + configFile.toAbsolutePath(), e);
        }
    }

    private static Path findProjectConfig(String fileName) {
        Path relative = Path.of("config").resolve(fileName);
        Path start = Path.of(System.getProperty("user.dir", ".")).toAbsolutePath();
        Path current = Files.isDirectory(start) ? start : start.getParent();
        while (current != null) {
            Path candidate = current.resolve(relative);
            if (Files.isRegularFile(candidate)) {
                return candidate;
            }
            current = current.getParent();
        }
        String multi = System.getProperty("maven.multiModuleProjectDirectory");
        if (multi != null && !multi.isBlank() && !multi.contains("${")) {
            Path candidate = Path.of(multi).resolve("config").resolve(fileName);
            if (Files.isRegularFile(candidate)) {
                return candidate;
            }
        }
        return null;
    }

    public static Path projectRoot() {
        String multi = System.getProperty("maven.multiModuleProjectDirectory");
        if (multi != null && !multi.isBlank() && !multi.contains("${")) {
            Path candidate = Path.of(multi).toAbsolutePath().normalize();
            if (Files.isDirectory(candidate.resolve("config"))) {
                return candidate;
            }
        }
        Path start = Path.of(System.getProperty("user.dir", ".")).toAbsolutePath().normalize();
        Path current = Files.isDirectory(start) ? start : start.getParent();
        while (current != null) {
            if (Files.isDirectory(current.resolve("config"))
                    && Files.isRegularFile(current.resolve("pom.xml"))) {
                return current;
            }
            current = current.getParent();
        }
        return start;
    }
}
