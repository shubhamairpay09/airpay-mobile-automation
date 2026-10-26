package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Diagnose 03 / NET WORTH BREAKDOWN. Live dump 2026-09-16. */
public class DiagnosisNetWorthBreakdownScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'03 / NET WORTH BREAKDOWN')]")
    private WebElement stepLabel;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='TOTAL ASSETS']")
    private WebElement totalAssets;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='MY NET WORTH']")
    private WebElement netWorth;

    public DiagnosisNetWorthBreakdownScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("NET WORTH BREAKDOWN");
    }
}
