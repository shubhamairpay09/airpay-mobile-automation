package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Suryoday FD — edit investment amount sheet (manual capture 2026-09-09).
 */
public class FdInvestmentAmountSheet extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"INVESTMENT AMOUNT\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Save Changes\")")
    private WebElement saveChanges;

    public FdInvestmentAmountSheet(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("INVESTMENT AMOUNT") && sourceContains("Save Changes");
    }
}
