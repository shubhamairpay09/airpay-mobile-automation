package com.airpay.money.screens.auth;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/** Forgot PIN — OTP then set a new MPIN. Dump 2.1.3 2026-09-25. */
public class ForgotPinScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[@content-desc='Forgot Your PIN']")
    private WebElement title;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Forgot your Airpay Money PIN')]")
    private WebElement subtitle;

    @AndroidFindBy(xpath = "//*[@content-desc='Send OTP']")
    private WebElement sendOtp;

    @AndroidFindBy(xpath = "//*[@content-desc='Logout']")
    private WebElement logout;

    @AndroidFindBy(xpath = "//*[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//*[@content-desc='Enter OTP']")
    private WebElement otpTitle;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'An OTP has been sent')]")
    private WebElement otpSubtitle;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Resend OTP')]")
    private WebElement resendOtp;

    @AndroidFindBy(xpath = "//*[@content-desc='Verify']")
    private WebElement verify;

    public ForgotPinScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Forgot Your PIN", "Forgot your Airpay Money PIN");
    }

    public void tapSendOtp() {
        if (!tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//*[@content-desc='Send OTP']"),
                accessibility("Send OTP"))) {
            throw new IllegalStateException("Send OTP not found on Forgot PIN.");
        }
        sleep(800);
    }

    public boolean isOtpDisplayed() {
        return sourceContains("Enter OTP") && sourceContains("An OTP has been sent", "Resend OTP");
    }

    public void enterOtp(String otp) {
        if (otp == null || otp.isBlank()) {
            throw new IllegalStateException("Forgot-PIN OTP is blank.");
        }
        String digits = otp.trim().replaceAll("\\D", "");
        WebElement field = firstPresent(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.EditText"),
                MobileWaits.xpath("//*[contains(@content-desc,'Enter OTP')]/following::*[@class='android.widget.EditText']"));
        if (field != null) {
            field.click();
            sleep(200);
            field.sendKeys(digits);
        } else {
            for (char c : digits.toCharArray()) {
                if (!tapFirst(Duration.ofSeconds(3),
                        MobileWaits.xpath("//*[starts-with(@content-desc,'Digit " + c + "')]"))) {
                    throw new IllegalStateException("Could not type Forgot-PIN OTP digit " + c);
                }
                sleep(120);
            }
        }
        sleep(300);
        tapVerify();
    }

    public void tapVerify() {
        tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//*[@content-desc='Verify']"),
                accessibility("Verify"));
        sleep(800);
    }
}
