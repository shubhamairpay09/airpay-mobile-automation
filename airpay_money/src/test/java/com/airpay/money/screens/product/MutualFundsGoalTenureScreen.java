package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Create Goal — Select Tenure sheet. Live dump 2026-09-17, build 2.0.124.
 */
public class MutualFundsGoalTenureScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Select Tenure']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='3 year']")
    private WebElement year3;

    public MutualFundsGoalTenureScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Select Tenure']"),
                MobileWaits.xpath("//android.view.View[@content-desc='1 year']")) != null;
    }
}
