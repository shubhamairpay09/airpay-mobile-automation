package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * FinSight FHC — Income &amp; Expense details (manual capture 2026-09-09).
 * Net monthly income, household expenses, lifestyle expenses, running loan Yes/No, Next.
 */
public class IncomeExpenseScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Income & Expense details\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Net Monthly Income\")")
    private WebElement netMonthlyIncome;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Household\")")
    private WebElement householdExpenses;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"LifeStyle\")")
    private WebElement lifestyleExpenses;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Do you have any running Loan?\")")
    private WebElement runningLoanQuestion;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Yes\")")
    private WebElement loanYes;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"No\")")
    private WebElement loanNo;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Next\")")
    private WebElement nextButton;

    public IncomeExpenseScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Income & Expense details", "Net Monthly Income")
                && sourceContains("running Loan");
    }
}
