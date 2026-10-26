package com.airpay.money.screens.auth;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Post-setup email confirmation before email OTP.
 * Live dump 2026-09-17, build 2.0.124.
 */
public class VerifyEmailScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Enter your email']")
    private WebElement title;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Verify your email id')]")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Email']")
    private WebElement email;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Proceed']")
    private WebElement proceed;

    public VerifyEmailScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Enter your email']"),
                MobileWaits.xpath("//*[contains(@content-desc,'Verify your email id')]"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Proceed']")) != null;
    }
}
