package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * NPS KYC PAN Details. Live dump 2026-09-21, build 2.0.124.
 */
public class NpsKycPanDetailsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='PAN Details']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Enter your 10 digit Pan number']")
    private WebElement panInput;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Verify']")
    private WebElement verify;

    public NpsKycPanDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='PAN Details']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Verify']")) != null;
    }
}
