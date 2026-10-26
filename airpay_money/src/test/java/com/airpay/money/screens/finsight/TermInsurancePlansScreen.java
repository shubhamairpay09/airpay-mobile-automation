package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * FinSight — Term Insurance plan list (manual capture 2026-09-09).
 * TATA AIA / Max Life cards with Buy Now.
 */
public class TermInsurancePlansScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Term Insurance\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"TATA AIA\")")
    private WebElement tataAiaPlan;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Buy Now\")")
    private WebElement buyNow;

    public TermInsurancePlansScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Term Insurance") && sourceContains("Buy Now");
    }
}
