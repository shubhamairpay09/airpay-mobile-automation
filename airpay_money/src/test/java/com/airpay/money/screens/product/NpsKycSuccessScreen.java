package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * KYC DigiLocker success — Congratulations / Proceed. Live dump 2026-09-21, build 2.0.124.
 */
public class NpsKycSuccessScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Congratulations!')]")
    private WebElement message;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Proceed']")
    private WebElement proceed;

    public NpsKycSuccessScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Congratulations!')]"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Proceed']")) != null;
    }
}
