package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * FinSight FHC — Let us know your risk capability (manual capture 2026-09-09).
 * Aggressive … Conservative radio list, Balanced selected, Next.
 */
public class RiskCapabilityScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"risk capability\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Aggressive\")")
    private WebElement aggressive;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Moderately Aggressive\")")
    private WebElement moderatelyAggressive;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Balanced\")")
    private WebElement balanced;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Moderately Conservative\")")
    private WebElement moderatelyConservative;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Conservative\")")
    private WebElement conservative;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Next\")")
    private WebElement nextButton;

    public RiskCapabilityScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("risk capability") && sourceContains("Balanced", "Conservative");
    }
}
