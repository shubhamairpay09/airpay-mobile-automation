package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Complete your KYC? sheet after One Time / Monthly. Live dump 2026-09-17, build 2.0.124.
 */
public class MutualFundsKycPromptScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Complete your KYC?']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Cancel']")
    private WebElement cancel;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Start KYC']")
    private WebElement startKyc;

    public MutualFundsKycPromptScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Complete your KYC?']"),
                MobileWaits.xpath("//android.view.View[@content-desc='Cancel']")) != null;
    }
}
