package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Discover Fixed Deposits (opened from FHC Start SIP) — manual capture 2026-09-09.
 */
public class DiscoverFixedDepositsScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Discover Fixed Deposits\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"My Portfolio\")")
    private WebElement myPortfolio;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Women Benefits\")")
    private WebElement womenBenefits;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Best Rates\")")
    private WebElement bestRates;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Popular FDs\")")
    private WebElement popularFds;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Senior Citizen\")")
    private WebElement seniorCitizen;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Suryoday\")")
    private WebElement suryodayBank;

    public DiscoverFixedDepositsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Discover Fixed Deposits") || sourceContains("Suryoday");
    }
}
