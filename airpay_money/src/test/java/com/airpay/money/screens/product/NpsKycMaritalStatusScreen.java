package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Select marital status sheet. Live dump 2026-09-18, build 2.0.124.
 */
public class NpsKycMaritalStatusScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Select marital status']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Single']")
    private WebElement single;

    public NpsKycMaritalStatusScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Select marital status']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Single']")) != null;
    }
}
