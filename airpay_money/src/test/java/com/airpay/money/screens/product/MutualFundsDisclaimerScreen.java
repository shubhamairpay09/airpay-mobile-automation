package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Fund Details — Know More disclaimer sheet. Live dump 2026-09-17, build 2.0.124.
 */
public class MutualFundsDisclaimerScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Disclaimer']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='OK']")
    private WebElement ok;

    public MutualFundsDisclaimerScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Disclaimer']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='OK']")) != null;
    }
}
