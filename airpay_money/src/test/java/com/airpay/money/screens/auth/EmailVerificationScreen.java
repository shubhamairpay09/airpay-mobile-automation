package com.airpay.money.screens.auth;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Email OTP bottom sheet after Proceed on Verify your email id.
 * Live dump 2026-09-17, build 2.0.124.
 */
public class EmailVerificationScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[@content-desc='Dismiss']")
    private WebElement dismiss;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Email Verification')]")
    private WebElement sheet;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Email Verification')]//android.widget.EditText")
    private WebElement otpField;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Resend')]")
    private WebElement resend;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Submit']")
    private WebElement submit;

    public EmailVerificationScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[contains(@content-desc,'Email Verification')]"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Submit']"),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Resend')]")) != null;
    }
}
