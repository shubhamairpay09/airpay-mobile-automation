package com.airpay.mobile.utilities.android;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

/**
 * Auto-accepts Android system and in-app permission / notification prompts
 * (Allow, While using the app, Allow notifications, Allow SMS, …).
 */
public final class PermissionDialogHandler {

    private static final Logger LOG = LogManager.getLogger(PermissionDialogHandler.class);

    private static final List<By> ALLOW_IDS = List.of(
            AppiumBy.id("com.android.permissioncontroller:id/permission_allow_button"),
            AppiumBy.id("com.android.permissioncontroller:id/permission_allow_foreground_only_button"),
            AppiumBy.id("com.android.permissioncontroller:id/permission_allow_one_time_button"),
            AppiumBy.id("com.android.permissioncontroller:id/permission_allow_always_button"),
            AppiumBy.id("com.android.permissioncontroller:id/permission_allow_all_button"),
            AppiumBy.id("com.android.packageinstaller:id/permission_allow_button"),
            AppiumBy.id("com.samsung.android.permissioncontroller:id/permission_allow_button"),
            AppiumBy.id("com.android.permissioncontroller:id/permission_enable_one_time_button"),
            AppiumBy.id("com.google.android.permissioncontroller:id/permission_allow_button")
    );

    private PermissionDialogHandler() {
    }

    public static int allowAll(AndroidDriver driver, Duration timeout) {
        long end = System.currentTimeMillis() + Math.max(500, timeout.toMillis());
        int accepted = 0;
        int idleRounds = 0;
        while (System.currentTimeMillis() < end && idleRounds < 3) {
            if (clickKnownAllowIds(driver) || clickAllowByLabel(driver)) {
                accepted++;
                idleRounds = 0;
                sleep(400);
            } else {
                idleRounds++;
                sleep(250);
            }
        }
        if (accepted > 0) {
            LOG.info("Accepted {} permission/notification dialog(s)", accepted);
        }
        return accepted;
    }

    private static boolean clickKnownAllowIds(AndroidDriver driver) {
        for (By by : ALLOW_IDS) {
            try {
                List<WebElement> found = driver.findElements(by);
                if (!found.isEmpty()) {
                    found.getFirst().click();
                    LOG.info("Tapped permission allow control {}", by);
                    return true;
                }
            } catch (Exception e) {
                LOG.debug("allow id {} skipped: {}", by, e.toString());
            }
        }
        return false;
    }

    private static boolean clickAllowByLabel(AndroidDriver driver) {
        try {
            List<WebElement> clickable = driver.findElements(AppiumBy.xpath(
                    "//android.widget.Button | //android.widget.TextView[@clickable='true']"));
            int checked = 0;
            for (WebElement el : clickable) {
                if (checked++ >= 8) {
                    break;
                }
                String label = firstLabel(el);
                if (isAllowLabel(label)) {
                    el.click();
                    LOG.info("Tapped permission/notification prompt: {}", label);
                    return true;
                }
            }
        } catch (Exception e) {
            LOG.debug("label scan skipped: {}", e.toString());
        }
        return false;
    }

    static boolean isAllowLabel(String raw) {
        if (raw == null || raw.isBlank()) {
            return false;
        }
        String n = raw.trim().toLowerCase(Locale.ROOT);
        if (n.contains("don’t allow") || n.contains("don't allow") || n.contains("dont allow")
                || n.contains("deny") || n.contains("no thanks") || n.contains("not now")
                || n.contains("don't send") || n.equals("no")) {
            return false;
        }
        return n.equals("allow")
                || n.equals("while using the app")
                || n.equals("allow only while using the app")
                || n.equals("allow all the time")
                || n.equals("only this time")
                || n.equals("allow notifications")
                || n.equals("allow sms")
                || n.startsWith("allow ")
                || n.contains("while using the app")
                || n.contains("allow notification")
                || n.contains("allow sms")
                || n.contains("allow access");
    }

    private static String firstLabel(WebElement el) {
        try {
            String text = el.getText();
            return text == null ? "" : text.trim();
        } catch (Exception ignored) {
            return "";
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
