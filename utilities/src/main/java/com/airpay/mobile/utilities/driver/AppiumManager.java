package com.airpay.mobile.utilities.driver;

import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.config.FrameworkConfig;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public final class AppiumManager {

    private static final Logger LOG = LogManager.getLogger(AppiumManager.class);

    private static AppiumManager open;

    private final AndroidDriver driver;
    private boolean createdNow;
    private final String udid;
    private final String appPackage;
    private final String appActivity;
    private final boolean keepSession;

    /** One session for the whole run. App data is not cleared, so login stays on the phone. */
    public static synchronized AppiumManager open(FrameworkConfig config) {
        String wantedPackage = config.require(config.product() + ".app.package");
        if (open != null && open.alive() && !wantedPackage.equals(open.appPackage)) {
            LOG.info("Product changed | oldPkg={} | newPkg={} | product={} — closing old session",
                    open.appPackage, wantedPackage, config.product());
            open.quit();
            open = null;
        }
        if (open != null && open.alive()) {
            open.createdNow = false;
            LOG.info("Reusing Appium session | session={} | pkg={}", open.driver.getSessionId(), open.appPackage);
            if (!open.alreadyInApp()) {
                open.bringAppToForeground(open.udid, open.appPackage, open.appActivity);
            }
            return open;
        }
        if (open != null) {
            open.quit();
            open = null;
        }
        open = new AppiumManager(config);
        return open;
    }

    public static synchronized void close() {
        if (open != null) {
            open.quit();
            open = null;
        }
    }

    public AppiumManager(FrameworkConfig config) {
        this.createdNow = true;
        String product = config.product();
        String apk = config.get(product + ".app.apk.path", "");
        this.appPackage = config.require(product + ".app.package");
        this.appActivity = config.require(product + ".app.activity");
        this.udid = config.get("android.udid", "");
        this.keepSession = config.getBoolean("android.keep.session", true);
        String server = config.get("appium.server.url", "http://127.0.0.1:4723");

        UiAutomator2Options options = new UiAutomator2Options()
                .setAutomationName(config.get("android.automationName", "UiAutomator2"))
                .setAppPackage(appPackage)
                .setAppActivity(appActivity)
                .setNoReset(config.getBoolean("android.noReset", true))
                .setFullReset(config.getBoolean("android.fullReset", false))
                .setAutoGrantPermissions(config.getBoolean("android.autoGrantPermissions", true))
                .setNewCommandTimeout(Duration.ofSeconds(config.getInt("android.newCommandTimeout", 180)));
        if (this.udid != null && !this.udid.isBlank()) {
            options.setUdid(this.udid.trim());
        }
        if (apk != null && !apk.isBlank() && config.getBoolean(product + ".fresh.start", false)) {
            Path apkPath = Path.of(apk);
            if (Files.isRegularFile(apkPath)) {
                options.setApp(apkPath.toAbsolutePath().toString());
            } else {
                LOG.warn("APK not found at {} — launching installed package {}", apk, this.appPackage);
            }
        }

        options.setCapability("appium:dontStopAppOnReset", true);
        options.setCapability("appium:forceAppLaunch", config.getBoolean(product + ".fresh.start", false));
        options.setCapability("appium:autoLaunch", config.getBoolean("android.autoLaunch", true));

        LOG.info("Starting Appium session | server={} | udid={} | pkg={} | activity={}",
                server, this.udid.isBlank() ? "(first device)" : this.udid, this.appPackage, this.appActivity);
        if (config.getBoolean(product + ".fresh.start", false)) {
            clearAppData(this.udid, this.appPackage);
            AdbCommands.grantCommonRuntimePermissions(this.udid, this.appPackage);
        }
        try {
            this.driver = new AndroidDriver(URI.create(server).toURL(), options);
        } catch (Exception e) {
            String reason = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
            throw new IllegalStateException(
                    "Failed to create AndroidDriver at " + server
                            + " | udid=" + (this.udid.isBlank() ? "(first device)" : this.udid)
                            + " | pkg=" + this.appPackage
                            + " | cause=" + reason, e);
        }
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        AdbCommands.grantCommonRuntimePermissions(this.udid, this.appPackage);
        bringAppToForeground(this.udid, this.appPackage, this.appActivity);
    }

    public boolean createdNow() {
        return createdNow;
    }

    private boolean alreadyInApp() {
        try {
            return appPackage.equals(driver.getCurrentPackage());
        } catch (Exception e) {
            return false;
        }
    }

    private boolean alive() {
        try {
            return driver != null && driver.getSessionId() != null && driver.currentActivity() != null;
        } catch (Exception e) {
            return false;
        }
    }

    /** Samsung often leaves UiAutomator2 on the launcher; force Capital into foreground. */
    private void bringAppToForeground(String udid, String appPackage, String appActivity) {
        try {
            if (appPackage.equals(driver.getCurrentPackage())) {
                LOG.info("App already in foreground — leaving the current screen in place");
                return;
            }
        } catch (Exception ignored) {
            // Fall through and bring the app forward.
        }
        LOG.info("Resuming the existing app session | keepSession={}", keepSession);
        AdbCommands.shell(udid, "am", "start", "-n", appPackage + "/" + appActivity);
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void clearAppData(String udid, String appPackage) {
        int code = AdbCommands.shell(udid, "pm", "clear", appPackage);
        LOG.info("pm clear {} exit={}", appPackage, code);
    }

    public AndroidDriver driver() {
        return driver;
    }

    public void quit() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                LOG.warn("Driver quit failed: {}", e.toString());
            }
        }
    }
}
