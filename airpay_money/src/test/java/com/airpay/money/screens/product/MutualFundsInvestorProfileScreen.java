package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds Profile tab — Investor Profile empty state.
 * Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsInvestorProfileScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Investor Profile']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='No investor Profile Created']")
    private WebElement empty;

    public MutualFundsInvestorProfileScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Investor Profile']"),
                MobileWaits.xpath("//android.view.View[@content-desc='No investor Profile Created']")) != null;
    }
}
