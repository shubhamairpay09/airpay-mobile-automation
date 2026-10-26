package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Diagnose screen 1 — Your Money Diagnosed.
 */
public class DiagnosisOverviewScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'01 / THE DIAGNOSIS')]")
    private WebElement stepLabel;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Your Money Diagnosed']")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='MY NET WORTH']")
    private WebElement netWorth;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Show me the gaps']")
    private WebElement showGaps;

    public DiagnosisOverviewScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Your Money Diagnosed") && sourceContains("Show me the gaps");
    }
}
