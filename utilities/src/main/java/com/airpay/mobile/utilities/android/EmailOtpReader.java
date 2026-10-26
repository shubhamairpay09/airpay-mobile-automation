package com.airpay.mobile.utilities.android;

import com.airpay.mobile.utilities.config.FrameworkConfig;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Reads an email OTP from Gmail (or Samsung Email) when the code is not in SMS.
 * Returns to {@code returnPackage} before handing the code back.
 */
public final class EmailOtpReader {

    private static final Logger LOG = LogManager.getLogger(EmailOtpReader.class);
    private static final String[] MAIL_PACKAGES = {
            "com.google.android.gm",
            "com.samsung.android.email.provider"
    };

    private EmailOtpReader() {
    }

    public static String readLatest(AndroidDriver driver, FrameworkConfig config, String mobile, String returnPackage) {
        String udid = config.get("android.udid", "");
        for (String mailPkg : MAIL_PACKAGES) {
            String path = AdbCommands.shellOut(udid, 10, "pm", "path", mailPkg);
            if (path == null || !path.contains("package:")) {
                continue;
            }
            LOG.info("Opening {} for an email OTP", mailPkg);
            AdbCommands.shell(udid, "monkey", "-p", mailPkg, "-c", "android.intent.category.LAUNCHER", "1");
            sleep(2500);
            String otp = otpOnScreen(driver, mobile);
            if (otp == null) {
                otp = openMatchingMail(driver, mobile);
            }
            returnToApp(driver, returnPackage);
            if (otp != null) {
                LOG.info("Email OTP read from {}", mailPkg);
                return otp;
            }
        }
        returnToApp(driver, returnPackage);
        return null;
    }

    private static String openMatchingMail(AndroidDriver driver, String mobile) {
        String src = safeSource(driver);
        String lower = src.toLowerCase();
        if (!lower.contains("airpay") && !lower.contains("vyaapaar") && !lower.contains("otp")
                && !lower.contains("verif")) {
            return null;
        }
        try {
            var nodes = driver.findElements(io.appium.java_client.AppiumBy.xpath(
                    "//*[contains(@text,'airpay') or contains(@text,'Airpay') or contains(@text,'Vyaapaar')"
                            + " or contains(@text,'OTP') or contains(@text,'verification')"
                            + " or contains(@content-desc,'airpay') or contains(@content-desc,'OTP')]"));
            for (var node : nodes) {
                try {
                    if (node.isDisplayed()) {
                        node.click();
                        sleep(1800);
                        return otpOnScreen(driver, mobile);
                    }
                } catch (Exception ignored) {
                    // stale row
                }
            }
        } catch (Exception e) {
            LOG.debug("Mailbox row tap failed: {}", e.toString());
        }
        return otpOnScreen(driver, mobile);
    }

    private static String otpOnScreen(AndroidDriver driver, String mobile) {
        return NotificationOtpReader.otpFromText(safeSource(driver), mobile);
    }

    private static void returnToApp(AndroidDriver driver, String returnPackage) {
        if (returnPackage == null || returnPackage.isBlank()) {
            return;
        }
        try {
            if (!returnPackage.equals(driver.getCurrentPackage())) {
                driver.activateApp(returnPackage);
                sleep(1200);
            }
        } catch (Exception e) {
            LOG.debug("Return to {} failed: {}", returnPackage, e.toString());
        }
    }

    private static String safeSource(AndroidDriver driver) {
        try {
            String src = driver.getPageSource();
            return src == null ? "" : src;
        } catch (Exception e) {
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
