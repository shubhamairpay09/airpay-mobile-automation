package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * FinSight — Health Insurance plan list (manual capture 2026-09-09).
 * Niva Bupa / HDFC ERGO with Buy Now.
 */
public class HealthInsurancePlansScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Health Insurance\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Niva Bupa\")")
    private WebElement nivaBupa;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"HDFC ERGO\")")
    private WebElement hdfcErgo;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Buy Now\")")
    private WebElement buyNow;

    public HealthInsurancePlansScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Health Insurance") && sourceContains("Buy Now")
                && sourceContains("Niva Bupa", "HDFC ERGO");
    }
}
