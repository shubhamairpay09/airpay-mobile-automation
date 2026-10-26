package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Fixerra Report an Issue sheet on PAN Verification (manual capture 2026-09-09).
 */
public class ReportIssueSheet extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Report an Issue\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"PAN Verification button not working\")")
    private WebElement buttonNotWorking;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Submit Feedback\")")
    private WebElement submitFeedback;

    public ReportIssueSheet(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Report an Issue") && sourceContains("Submit Feedback");
    }
}
