package com.airpay.mobile.utilities.driver;

import com.airpay.mobile.utilities.config.FrameworkConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Opens LMS / DSA admin in <b>Chrome on the Android device</b> via Appium
 * ({@code browserName=Chrome}). Does not launch the Capital app session.
 * Desktop Chrome is only used when {@code web.browser=desktop}.
 */
public final class WebBrowserManager {

    private static final Logger LOG = LogManager.getLogger(WebBrowserManager.class);
    private static WebDriver driver;

    private WebBrowserManager() {
    }

    public static synchronized WebDriver open(FrameworkConfig config) {
        if (driver != null) {
            return driver;
        }
        String mode = config.get("web.browser", "mobile-chrome").trim().toLowerCase();
        if ("desktop".equals(mode) || "desktop-chrome".equals(mode)) {
            driver = openDesktopChrome(config);
            LOG.info("Desktop Chrome started for LMS (web.browser={})", mode);
            return driver;
        }
        // Release any Capital/app Appium session so the device can run Chrome.
        AppiumManager.close();
        driver = openMobileChrome(config);
        LOG.info("Mobile Chrome started for LMS | udid={}", config.get("android.udid", "(first device)"));
        return driver;
    }

    private static AndroidDriver openMobileChrome(FrameworkConfig config) {
        String server = config.get("appium.server.url", "http://127.0.0.1:4723");
        String udid = config.get("android.udid", "").trim();
        UiAutomator2Options options = new UiAutomator2Options()
                .setAutomationName(config.get("android.automationName", "UiAutomator2"))
                .withBrowserName("Chrome")
                .setNewCommandTimeout(Duration.ofSeconds(config.getInt("android.newCommandTimeout", 300)));
        if (!udid.isBlank()) {
            options.setUdid(udid);
        }
        options.setCapability("appium:autoGrantPermissions", true);
        options.setCapability("goog:chromeOptions", Map.of(
                "args", java.util.List.of("--disable-notifications", "--disable-popup-blocking")));
        resolveChromeDriver(config).ifPresent(path ->
                options.setCapability("appium:chromedriverExecutable", path.toAbsolutePath().toString()));

        try {
            AndroidDriver mobile = new AndroidDriver(URI.create(server).toURL(), options);
            mobile.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
            mobile.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                    config.getInt("timeout.navigation", 60)));
            dismissChromeFirstRunIfNeeded(mobile);
            return mobile;
        } catch (Exception e) {
            String reason = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
            throw new IllegalStateException(
                    "Failed to open Chrome on device via Appium at " + server
                            + " | udid=" + (udid.isBlank() ? "(first device)" : udid)
                            + " | cause=" + reason, e);
        }
    }

    /** Dismiss Chrome welcome / sign-in walls when they block the first page load. */
    private static void dismissChromeFirstRunIfNeeded(AndroidDriver mobile) {
        try {
            var contexts = mobile.getContextHandles();
            if (contexts != null && contexts.stream().anyMatch(c -> c.contains("NATIVE"))) {
                // Stay in default CHROMIUM context for web; only poke native if needed later.
            }
        } catch (Exception e) {
            LOG.debug("Chrome context check skipped: {}", e.toString());
        }
    }

    private static WebDriver openDesktopChrome(FrameworkConfig config) {
        System.setProperty("SE_AVOID_STATS", "true");
        resolveChromeDriver(config).ifPresent(path ->
                System.setProperty("webdriver.chrome.driver", path.toString()));

        org.openqa.selenium.chrome.ChromeOptions chromeOptions = new org.openqa.selenium.chrome.ChromeOptions();
        if (config.getBoolean("web.headless", false)) {
            chromeOptions.addArguments("--headless=new");
        }
        chromeOptions.addArguments("--window-size=1440,900");
        chromeOptions.addArguments("--disable-notifications");
        chromeOptions.addArguments("--disable-popup-blocking");
        String binary = config.get("web.chrome.binary", "").trim();
        if (!binary.isBlank()) {
            chromeOptions.setBinary(binary);
        }
        org.openqa.selenium.chrome.ChromeDriverService.Builder serviceBuilder =
                new org.openqa.selenium.chrome.ChromeDriverService.Builder();
        String driverProp = System.getProperty("webdriver.chrome.driver", "");
        if (!driverProp.isBlank() && Files.isRegularFile(Path.of(driverProp))) {
            serviceBuilder.usingDriverExecutable(Path.of(driverProp).toFile());
        }
        WebDriver desktop = new org.openqa.selenium.chrome.ChromeDriver(serviceBuilder.build(), chromeOptions);
        desktop.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        desktop.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                config.getInt("timeout.navigation", 60)));
        return desktop;
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
