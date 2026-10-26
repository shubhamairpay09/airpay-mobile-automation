package com.airpay.mobile.utilities.reporting;

import com.airpay.mobile.utilities.config.ConfigLoader;
import com.airpay.mobile.utilities.config.FrameworkConfig;
import io.appium.java_client.android.AndroidDriver;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotCapture {

    private static final Logger LOG = LogManager.getLogger(ScreenshotCapture.class);

    private ScreenshotCapture() {
    }

    public static Path capture(AndroidDriver driver, FrameworkConfig config, String name) {
        try {
            byte[] png = driver.getScreenshotAs(OutputType.BYTES);
            Path dir = resolveDir(config);
            Files.createDirectories(dir);
            String safe = name.replaceAll("[^A-Za-z0-9._-]+", "_");
            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            Path file = dir.resolve(safe + "-" + ts + ".png");
            Files.write(file, png);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(png), ".png");
            return file;
        } catch (Exception e) {
            LOG.warn("Screenshot failed: {}", e.toString());
            return null;
        }
    }

    private static Path resolveDir(FrameworkConfig config) {
        String dir = config.get("report.screenshot.dir", "target/screenshots/failures");
        Path path = Path.of(dir);
        if (path.isAbsolute()) {
            return path;
        }
        return ConfigLoader.projectRoot().resolve(path);
    }
}
