package com.airpay.mobile.utilities.android;

import com.airpay.mobile.utilities.config.FrameworkConfig;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads an SMS OTP from the device notification shade / status bar
 * ({@code dumpsys notification}, {@code mobile: getNotifications}, Appium
 * {@code openNotifications}), then SMS inbox. Does not type on the device.
 */
public final class NotificationOtpReader {

    private static final Logger LOG = LogManager.getLogger(NotificationOtpReader.class);

    private static final Pattern CONTEXTUAL = Pattern.compile(
            "(?i)(?:otp|one[\\s-]?time(?:\\s+pass(?:word|code))?|verification(?:\\s+code)?|code(?:\\s+is)?)"
                    + "[^0-9]{0,24}(\\d{4,8})");
    private static final Pattern TRAILING = Pattern.compile(
            "(?i)(\\d{4,8})[^0-9]{0,24}(?:is your (?:otp|code)|is the otp|otp)");
    private static final Pattern SIX_DIGIT = Pattern.compile("(?<!\\d)(\\d{6})(?!\\d)");

    private NotificationOtpReader() {
    }

    public static String poll(AndroidDriver driver, FrameworkConfig config, String mobile, Duration timeout) {
        return poll(driver, config, mobile, timeout, 0, null);
    }

    public static String poll(AndroidDriver driver, FrameworkConfig config, String mobile, Duration timeout,
                              BooleanSupplier abort) {
        return poll(driver, config, mobile, timeout, 0, abort);
    }

    public static String poll(AndroidDriver driver, FrameworkConfig config, String mobile, Duration timeout,
                              int expectedLength, BooleanSupplier abort) {
        long end = System.currentTimeMillis() + Math.max(3_000, timeout.toMillis());
        String udid = config.get("android.udid", "");
        int shadeTries = 0;
        while (System.currentTimeMillis() < end) {
            if (abort != null && abort.getAsBoolean()) {
                LOG.info("OTP wait aborted — app already left the OTP screen");
                return null;
            }
            String otp = fromDumpsys(udid, mobile, expectedLength);
            if (otp != null) {
                LOG.info("OTP read from dumpsys notification for {}", mask(mobile));
                return otp;
            }
            otp = fromMobileGetNotifications(driver, mobile, expectedLength);
            if (otp != null) {
                LOG.info("OTP read from mobile:getNotifications for {}", mask(mobile));
                return otp;
            }
            otp = fromSmsInbox(udid, mobile, expectedLength);
            if (otp != null) {
                LOG.info("OTP read from SMS inbox for {}", mask(mobile));
                return otp;
            }
            long remaining = end - System.currentTimeMillis();
            if (shadeTries < 3 && remaining < timeout.toMillis() - 8_000) {
                otp = fromNotificationShade(driver, mobile, expectedLength);
                shadeTries++;
                if (otp != null) {
                    LOG.info("OTP read from notification shade for {}", mask(mobile));
                    return otp;
                }
            }
            sleep(2_000);
        }
        LOG.warn("Could not read OTP from notifications within {}s for {}",
                timeout.toSeconds(), mask(mobile));
        return null;
    }

    /** OTP digits from notification, SMS, or email text. */
    public static String otpFromText(String haystack, String mobile) {
        return extractOtp(haystack, mobile, 0);
    }

    static String extractOtp(String haystack, String mobile) {
        return extractOtp(haystack, mobile, 0);
    }

    static String extractOtp(String haystack, String mobile, int expectedLength) {
        if (haystack == null || haystack.isBlank()) {
            return null;
        }
        String cleaned = haystack;
        if (mobile != null && !mobile.isBlank()) {
            cleaned = cleaned.replace(mobile, " ");
            if (mobile.length() == 10) {
                cleaned = cleaned.replace("+91" + mobile, " ");
                cleaned = cleaned.replace("91" + mobile, " ");
            }
        }
        Matcher contextual = CONTEXTUAL.matcher(cleaned);
        while (contextual.find()) {
            String candidate = accept(contextual.group(1), expectedLength);
            if (candidate != null) {
                return candidate;
            }
        }
        Matcher trailing = TRAILING.matcher(cleaned);
        while (trailing.find()) {
            String candidate = accept(trailing.group(1), expectedLength);
            if (candidate != null) {
                return candidate;
            }
        }
        if (expectedLength == 6 || expectedLength == 0) {
            Matcher six = SIX_DIGIT.matcher(cleaned);
            while (six.find()) {
                String candidate = accept(six.group(1), expectedLength);
                if (candidate != null) {
                    return candidate;
                }
            }
        }
        return null;
    }

    private static String fromDumpsys(String udid, String mobile, int expectedLength) {
        String dump = AdbCommands.shellOut(udid, 15, "dumpsys", "notification", "--noredact");
        if (dump == null || dump.isBlank() || dump.toLowerCase().contains("unknown option")
                || dump.toLowerCase().contains("usage:")) {
            dump = AdbCommands.shellOut(udid, 15, "dumpsys", "notification");
        }
        return extractOtp(dump, mobile, expectedLength);
    }

    private static String fromMobileGetNotifications(AndroidDriver driver, String mobile, int expectedLength) {
        try {
            Object raw = driver.executeScript("mobile: getNotifications");
            StringBuilder sb = new StringBuilder();
            collectStrings(raw, sb, 0);
            return extractOtp(sb.toString(), mobile, expectedLength);
        } catch (Exception e) {
            LOG.debug("mobile:getNotifications unavailable: {}", e.toString());
            return null;
        }
    }

    private static String fromSmsInbox(String udid, String mobile, int expectedLength) {
        String out = AdbCommands.shellOut(udid, 10,
                "content", "query", "--uri", "content://sms/inbox",
                "--projection", "body:address:date", "--sort", "date DESC");
        return extractOtp(out, mobile, expectedLength);
    }

    private static String fromNotificationShade(AndroidDriver driver, String mobile, int expectedLength) {
        String pkg = null;
        try {
            pkg = driver.getCurrentPackage();
            driver.openNotifications();
            sleep(1_200);
            String src = driver.getPageSource();
            String otp = extractOtp(src, mobile, expectedLength);
            try {
                driver.navigate().back();
                sleep(400);
            } catch (Exception e) {
                LOG.debug("Could not close notification shade: {}", e.toString());
            }
            if (pkg != null && !pkg.isBlank()) {
                try {
                    driver.activateApp(pkg);
                } catch (Exception e) {
                    LOG.debug("activateApp after notifications skipped: {}", e.toString());
                }
            }
            return otp;
        } catch (Exception e) {
            LOG.debug("openNotifications failed: {}", e.toString());
            return null;
        }
    }

    private static void collectStrings(Object node, StringBuilder sb, int depth) {
        if (node == null || depth > 8) {
            return;
        }
        if (node instanceof String s) {
            sb.append(' ').append(s);
            return;
        }
        if (node instanceof Number n) {
            sb.append(' ').append(n);
            return;
        }
        if (node instanceof Map<?, ?> map) {
            for (Object v : map.values()) {
                collectStrings(v, sb, depth + 1);
            }
            return;
        }
        if (node instanceof List<?> list) {
            for (Object v : list) {
                collectStrings(v, sb, depth + 1);
            }
            return;
        }
        if (node instanceof Object[] arr) {
            for (Object v : arr) {
                collectStrings(v, sb, depth + 1);
            }
        }
    }

    private static String accept(String digits, int expectedLength) {
        if (digits == null) {
            return null;
        }
        if (digits.length() < 4 || digits.length() > 8) {
            return null;
        }
        if (expectedLength > 0 && digits.length() != expectedLength) {
            return null;
        }
        if (digits.chars().allMatch(c -> c == '0')) {
            return null;
        }
        if (digits.length() == 4) {
            int year = Integer.parseInt(digits);
            if (year >= 2020 && year <= 2035) {
                return null;
            }
        }
        return digits;
    }

    private static String mask(String mobile) {
        if (mobile == null || mobile.length() < 4) {
            return "****";
        }
        return "******" + mobile.substring(mobile.length() - 4);
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
