package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * FinSight FHC — Savings &amp; Investments after adding an MF (manual capture 2026-09-09).
 * Total bank balance, My Investments list, Add New, Next.
 */
public class SavingsInvestmentsScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Savings & Investments\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Total Bank Balance\")")
    private WebElement totalBankBalance;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"My Investments\")")
    private WebElement myInvestments;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Add New\")")
    private WebElement addNew;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Mutual Fund\")")
    private WebElement mutualFundCard;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Next\")")
    private WebElement nextButton;

    public SavingsInvestmentsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Savings & Investments");
    }
}
