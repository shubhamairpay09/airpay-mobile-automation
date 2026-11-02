package com.airpay.mobile.utilities.driver;

import com.airpay.mobile.utilities.config.FrameworkConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Stream;

/** Chrome WebDriver for Capital LMS / DSA admin portal (web-only — does not touch the mobile app). */
public final class WebBrowserManager {

    private static final Logger LOG = LogManager.getLogger(WebBrowserManager.class);
    private static WebDriver driver;

    private WebBrowserManager() {
    }

    public static synchronized WebDriver open(FrameworkConfig config) {
        if (driver != null) {
            return driver;
        }
        // Corporate TLS often breaks Selenium Manager JSON; prefer an explicit/cached chromedriver.
        System.setProperty("SE_AVOID_STATS", "true");
        resolveChromeDriver(config).ifPresent(path ->
                System.setProperty("webdriver.chrome.driver", path.toString()));

        ChromeOptions options = new ChromeOptions();
        if (config.getBoolean("web.headless", false)) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1440,900");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        String binary = config.get("web.chrome.binary", "").trim();
        if (!binary.isBlank()) {
            options.setBinary(binary);
        }
        ChromeDriverService.Builder serviceBuilder = new ChromeDriverService.Builder();
        String driverProp = System.getProperty("webdriver.chrome.driver", "");
        if (!driverProp.isBlank() && Files.isRegularFile(Path.of(driverProp))) {
            serviceBuilder.usingDriverExecutable(Path.of(driverProp).toFile());
        }
        driver = new ChromeDriver(serviceBuilder.build(), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                config.getInt("timeout.navigation", 60)));
        LOG.info("Chrome WebDriver started for LMS");
        return driver;
    }

    private static Optional<Path> resolveChromeDriver(FrameworkConfig config) {
        String configured = config.get("web.chrome.driver", "").trim();
        if (!configured.isBlank()) {
            Path p = Path.of(configured);
            if (Files.isRegularFile(p)) {
                return Optional.of(p);
            }
        }
        Path cacheRoot = Path.of(System.getProperty("user.home"), ".cache", "selenium", "chromedriver");
        if (!Files.isDirectory(cacheRoot)) {
            return Optional.empty();
        }
        try (Stream<Path> walk = Files.walk(cacheRoot)) {
            return walk
                    .filter(p -> p.getFileName().toString().equalsIgnoreCase("chromedriver.exe")
                            || p.getFileName().toString().equalsIgnoreCase("chromedriver"))
                    .filter(Files::isRegularFile)
                    .max(Comparator.comparingLong(p -> p.toFile().lastModified()));
        } catch (Exception e) {
            LOG.debug("ChromeDriver cache scan skipped: {}", e.toString());
            return Optional.empty();
        }
    }

    public static synchronized void close() {
        if (driver == null) {
            return;
        }
        try {
            driver.quit();
        } catch (Exception e) {
            LOG.debug("WebDriver quit skipped: {}", e.toString());
        } finally {
            driver = null;
        }
    }

    public static synchronized WebDriver current() {
        return driver;
    }
}
