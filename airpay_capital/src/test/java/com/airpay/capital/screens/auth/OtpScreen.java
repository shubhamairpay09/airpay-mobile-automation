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
        return waits.present(MobileWaits.xpath(
                        "//*[contains(@content-desc,'OTP') or contains(@content-desc,'Verify')]"),
                Duration.ofSeconds(8))
                || firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'OTP') or contains(@hint,'password')]"))
                        != null;
    }

    public boolean waitUntilDisplayed(Duration timeout) {
        return firstPresent(timeout,
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'OTP') or contains(@hint,'password')"
                        + " or contains(@hint,'digit code')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Verify OTP') or contains(@content-desc,'One time password')]"))
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
        tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Verify']"),
                accessibility("Verify"));
    }

    /** Taps Edit beside the registered mobile number on the OTP screen. */
    public boolean tapEditMobile() {
        return tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//*[contains(@content-desc,'Edit') and (@clickable='true' or self::android.widget.Button)]"),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Edit')]"),
                accessibility("Edit"))
                || tapLabel("Edit");
    }

    /** Taps Resend OTP and waits until the OTP field is ready again. */
    public boolean tapResendOtp() {
        boolean tapped = tapFirst(Duration.ofSeconds(8),
                MobileWaits.xpath("//*[contains(@content-desc,'Resend') and contains(@content-desc,'OTP')]"),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Resend')]"),
                accessibility("Resend OTP"))
                || tapAny(java.util.List.of("Resend OTP", "Resend"));
        if (tapped) {
            waitUntilDisplayed(Duration.ofSeconds(10));
        }
        return tapped;
    }

    public boolean invalidOtpMessageVisible() {
        return sourceContains("Invalid OTP", "incorrect OTP", "Incorrect OTP", "OTP is invalid", "invalid otp");
    }

    public boolean expiredOtpMessageVisible() {
        return sourceContains("OTP Expired", "OTP expired", "expired OTP", "OTP has expired", "Expired");
    }

    public boolean waitUntilGone(Duration timeout) {
        HomeScreen home = new HomeScreen(context);
        long end = System.currentTimeMillis() + timeout.toMillis();
        while (System.currentTimeMillis() < end) {
            if (!isDisplayed() && home.looksLoggedIn()) {
                return true;
            }
            if (home.looksLoggedIn()) {
                return true;
            }
            sleep(250);
        }
        return home.looksLoggedIn();
    }
}
