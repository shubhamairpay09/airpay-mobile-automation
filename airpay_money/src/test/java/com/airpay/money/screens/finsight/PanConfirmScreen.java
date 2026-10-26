package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Fixerra PAN confirm card after Verify PAN (manual capture 2026-09-09).
 * Confirm &amp; Proceed / Edit Details. Do not store PAN values in locators.
 */
public class PanConfirmScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"PAN Verification\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Please verify your PAN details\")")
    private WebElement verifyPrompt;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Confirm & Proceed\")")
    private WebElement confirmProceed;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Edit Details\")")
    private WebElement editDetails;

    public PanConfirmScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Please verify your PAN details") && sourceContains("Confirm & Proceed");
    }
}
