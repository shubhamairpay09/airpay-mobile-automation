package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * FinSight FHC — Insurance Details (manual capture 2026-09-09).
 * Do you have any Insurance Yes/No, Health + Term premium/sum insured, skip toggles, Next.
 */
public class InsuranceDetailsScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Insurance Details\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Do you have any Insurance?\")")
    private WebElement hasInsuranceQuestion;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Health Insurance\")")
    private WebElement healthHeading;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"I don't have Health insurance\")")
    private WebElement noHealthToggle;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Term Insurance\")")
    private WebElement termHeading;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"I don't have Term insurance\")")
    private WebElement noTermToggle;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Next\")")
    private WebElement nextButton;

    public InsuranceDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Insurance Details") && sourceContains("Do you have any Insurance?");
    }
}
