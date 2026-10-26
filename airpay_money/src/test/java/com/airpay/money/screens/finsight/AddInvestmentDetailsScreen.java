package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * FinSight FHC — Add Investment Details (manual start 2026-09-09).
 * Type Mutual Funds (MF-Lumpsum), Current Value, Monthly Deposit Amount,
 * associate a goal Yes/No, Add.
 */
public class AddInvestmentDetailsScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Add Investment Details\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Investment type\")")
    private WebElement investmentTypeLabel;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Mutual Funds\")")
    private WebElement mutualFundsLumpsum;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Current Value\")")
    private WebElement currentValue;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Monthly Deposit Amount\")")
    private WebElement monthlyDepositAmount;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"associate a goal\")")
    private WebElement associateGoalQuestion;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Add\")")
    private WebElement addButton;

    public AddInvestmentDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Add Investment Details") && sourceContains("Investment type");
    }
}
