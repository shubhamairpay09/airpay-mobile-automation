package com.airpay.money.screens.auth;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.Map;

public class OtpScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Mobile Number Verification']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Verification code sent')]")
    private WebElement sentToAndEdit;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    private WebElement otpField;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Resend OTP')]")
    private WebElement resendOtp;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Submit']")
    private WebElement submit;

    public OtpScreen(TestContext context) {
        super(context);
    }

    public boolean waitUntilDisplayed(Duration timeout) {
        if (safeSource().contains("Login with Mobile Number")
                || safeSource().contains("Enter mobile number")) {
            return false;
        }
        return firstPresent(timeout,
                MobileWaits.xpath("//android.view.View[@content-desc='Mobile Number Verification']"),
                MobileWaits.xpath("//*[contains(@content-desc,'Verify OTP') or contains(@content-desc,'Enter OTP')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Resend OTP')]"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Submit']"),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'OTP') or contains(@hint,'digit')]")) != null
                || (sourceContains("Mobile Number Verification") && !sourceContains("Login with Mobile Number"));
    }

    public boolean isDisplayed() {
        return waitUntilDisplayed(Duration.ofSeconds(3));
    }

    public void enterOtp(String otp) {
        if (otp == null || otp.isBlank()) {
            return;
        }
        String digits = otp.trim().replaceAll("\\D", "");
        WebElement field = firstPresent(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'OTP') or contains(@hint,'password')"
                        + " or contains(@hint,'digit')]"),
                AppiumBy.className("android.widget.EditText"));
        if (field == null) {
            waitUntilGone(Duration.ofSeconds(120));
            return;
        }
        field.click();
        sleep(300);
        if (!typeDigits(field, digits)) {
            throw new IllegalStateException("Failed to type OTP.");
        }
        sleep(400);
        tapVerifyIfPresent();
    }

    private boolean typeDigits(WebElement field, String digits) {
        try {
            for (char c : digits.toCharArray()) {
                driver.pressKey(new KeyEvent(AndroidKey.valueOf("DIGIT_" + c)));
                sleep(120);
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
                MobileWaits.xpath("//android.widget.Button[@content-desc='Submit']"),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Verify')]"),
                accessibility("Submit"),
                accessibility("Verify"));
    }

    public boolean waitUntilGone(Duration timeout) {
        HomeScreen home = new HomeScreen(context);
        long end = System.currentTimeMillis() + timeout.toMillis();
        while (System.currentTimeMillis() < end) {
            if (!isDisplayed() && home.looksLoggedIn()) {
                return true;
            }
            sleep(2000);
        }
        return home.looksLoggedIn();
    }
}
