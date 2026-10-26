package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Fixerra PAN Verification after Book FD (manual capture 2026-09-09).
 * DOB, PAN Number, T&amp;C checkbox, Verify PAN.
 */
public class PanVerificationScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"PAN Verification\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Date of Birth\")")
    private WebElement dob;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"PAN Number\")")
    private WebElement panNumber;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Terms & Conditions\")")
    private WebElement terms;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Verify PAN\")")
    private WebElement verifyPan;

    public PanVerificationScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("PAN Verification") && sourceContains("Verify PAN");
    }
}
