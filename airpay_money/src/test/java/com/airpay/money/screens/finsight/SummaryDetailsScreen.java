package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * FinSight FHC — Summary Details (manual capture 2026-09-09).
 * Expandable cards: Personal, Income, Insurance, Investments, Risk Appetite, Next.
 */
public class SummaryDetailsScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Summary Details\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"My Personal Details\")")
    private WebElement personalDetails;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"My Income and Expenses\")")
    private WebElement incomeExpenses;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"My Insurance\")")
    private WebElement insurance;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"My Investments\")")
    private WebElement investments;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"My Risk Appetite\")")
    private WebElement riskAppetite;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Next\")")
    private WebElement nextButton;

    public SummaryDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Summary Details") && sourceContains("My Personal Details");
    }
}
