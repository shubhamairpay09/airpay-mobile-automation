package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * FinSight FHC result — score 52 Average.
 * Live dump 2026-09-16. XPaths: {@link ScoreScreen}.
 */
public class FinancialHealthScoreScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Financial Health Check']")
    private WebElement heading;

    @AndroidFindBy(xpath = "//*[@content-desc='OUT OF 100']")
    private WebElement outOfHundred;

    @AndroidFindBy(xpath = "//*[@content-desc='Average']")
    private WebElement averageLabel;

    @AndroidFindBy(xpath = "//*[@content-desc='See the diagnosis']")
    private WebElement seeDiagnosis;

    public FinancialHealthScoreScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("YOUR FINANCIAL HEALTH SCORE", "See the diagnosis")
                || (sourceContains("Financial Health Check") && sourceContains("OUT OF 100"));
    }
}
