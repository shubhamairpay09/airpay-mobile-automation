package com.airpay.capital.screens.auth;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.capital.screens.home.HomeScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Map;

public class OtpScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'OTP') or contains(@content-desc,'Verify')]")
    @iOSXCUITFindBy(accessibility = "Verify OTP")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='One time password' or contains(@hint,'digit code') or contains(@hint,'OTP')]")
    @iOSXCUITFindBy(accessibility = "One time password")
    private WebElement otpField;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Verify')]")
    @iOSXCUITFindBy(accessibility = "Verify")
    private WebElement verifyButton;

    public OtpScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        // Do not match "Send OTP" on the mobile-login screen.
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.EditText[@hint='One time password' or contains(@hint,'digit code')"
                        + " or contains(@hint,'OTP')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Verify OTP') or contains(@content-desc,'One time password')"
                        + " or contains(@content-desc,'Enter OTP')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Resend') and contains(@content-desc,'OTP')]"))
                != null;
    }

    public boolean waitUntilDisplayed(Duration timeout) {
        return firstPresent(timeout,
                MobileWaits.xpath("//android.widget.EditText[@hint='One time password' or contains(@hint,'digit code')"
                        + " or contains(@hint,'OTP')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Verify OTP') or contains(@content-desc,'One time password')"
                        + " or contains(@content-desc,'Enter OTP')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Resend') and contains(@content-desc,'OTP')]"))
                != null;
    }

    public int expectedLength() {
        String src = safeSource();
        if (src.contains("4 digit") || src.contains("4-digit") || src.contains("4 Digit")) {
            return 4;
        }
        if (src.contains("6 digit") || src.contains("6-digit")) {
            return 6;
        }
        return 4;
    }

    public void enterOtp(String otp) {
        if (otp == null || otp.isBlank()) {
            return;
        }
        String digits = otp.trim().replaceAll("\\D", "");
        int expected = expectedLength();
        if (digits.length() > expected) {
            digits = digits.substring(0, expected);
        }
        WebElement field = firstPresent(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.EditText[@hint='One time password' or contains(@hint,'digit code')"
                        + " or contains(@hint,'OTP')]"),
                AppiumBy.className("android.widget.EditText"));
        if (field == null) {
            throw new IllegalStateException("OTP input field not found.");
        }
        field.click();
        if (!typeDigits(field, digits)) {
            throw new IllegalStateException("Failed to type OTP.");
        }
        tapVerifyIfPresent();
    }

    private boolean typeDigits(WebElement field, String digits) {
        try {
            for (char c : digits.toCharArray()) {
                driver.pressKey(new KeyEvent(AndroidKey.valueOf("DIGIT_" + c)));
            }
            return true;
        } catch (Exception ignored) {
            // try mobile:type
        }
        try {
            driver.executeScript("mobile: type", Map.of("text", digits));
            return true;
        } catch (Exception ignored) {
            // last resort
        }
        try {
            field.sendKeys(digits);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void tapVerifyIfPresent() {
        dismissKeyboard();
        By verify = MobileWaits.xpath("//android.widget.Button[@content-desc='Verify']");
        int[] point = lowestButtonCenter(verify);
        if (point == null) {
            throw new IllegalStateException("Verify button was not tappable");
        }
        tapAt(point[0], point[1]);
        sleep(500);
        if (sourceContains("Verify OTP")) {
            adbTap(point[0], point[1]);
        }
    }

    private void dismissKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // already closed
        }
        String udid = context.config().get("android.udid", "");
        com.airpay.mobile.utilities.android.AdbCommands.shell(udid, "input", "keyevent", "111");
        sleep(300);
    }

    private int[] lowestButtonCenter(By by) {
        int[] best = null;
        int bestY = -1;
        for (WebElement el : driver.findElements(by)) {
            try {
                var r = el.getRect();
                if (r.height < 40 || r.width < 40) {
                    continue;
                }
                int y = r.y + r.height / 2;
                if (y >= bestY) {
                    bestY = y;
                    best = new int[]{r.x + r.width / 2, y};
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return best;
    }

    public boolean invalidOtpMessageVisible() {
        return waitUntilSourceContains(Duration.ofSeconds(8),
                "Invalid OTP", "incorrect OTP", "Incorrect OTP", "OTP is invalid", "invalid otp",
                "Wrong OTP", "Please enter a valid OTP", "Please enter valid OTP");
    }

    public boolean expiredOtpMessageVisible() {
        return waitUntilSourceContains(Duration.ofSeconds(8),
                "OTP Expired", "OTP expired", "expired OTP", "OTP has expired", "Expired OTP",
                "This OTP has expired");
    }

    /** Taps Edit beside the registered mobile number on the OTP screen. */
    public boolean tapEditMobile() {
        By edit = MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Edit')]");
        if (tapCenter(edit)) {
            return true;
        }
        return tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[contains(@content-desc,'Edit') and (@clickable='true' or self::android.widget.Button)]"),
                edit,
                accessibility("Edit"))
                || tapLabel("Edit");
    }

    /**
     * Taps resend after the cooldown. UI copy is {@code Send again OTP (Ns)} while waiting,
     * then {@code Send again OTP} when tappable (not {@code Resend OTP}).
     */
    public boolean tapResendOtp() {
        long end = System.currentTimeMillis() + Duration.ofSeconds(75).toMillis();
        while (System.currentTimeMillis() < end) {
            if (tryTapResendReady()) {
                waitUntilDisplayed(Duration.ofSeconds(10));
                return true;
            }
            sleep(1_000);
        }
        return tryTapResendReady();
    }

    private boolean tryTapResendReady() {
        By ready = MobileWaits.xpath("//*[@content-desc='Send again OTP' or @text='Send again OTP']");
        if (tapCenter(ready)) {
            return true;
        }
        return tapFirst(Duration.ofSeconds(2),
                ready,
                MobileWaits.xpath("//*[contains(@content-desc,'Resend OTP') and not(contains(@content-desc,'('))]"),
                accessibility("Send again OTP"),
                accessibility("Resend OTP"))
                || tapAny(java.util.List.of("Send again OTP", "Resend OTP"));
    }

    public boolean waitUntilGone(Duration timeout) {
        HomeScreen home = new HomeScreen(context);
        return waitUntil(() -> home.looksLoggedIn() || !sourceContains(
                "Verify OTP", "Enter OTP", "One time password", "Resend OTP"), timeout);
    }
}
