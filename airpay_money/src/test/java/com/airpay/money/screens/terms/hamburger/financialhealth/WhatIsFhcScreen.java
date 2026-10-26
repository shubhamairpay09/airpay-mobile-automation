package com.airpay.money.screens.terms.hamburger.financialhealth;

import com.airpay.money.screens.menu.AbstractMenuScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class WhatIsFhcScreen extends AbstractMenuScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"What is\")")
    @iOSXCUITFindBy(accessibility = "What is FHC")
    private WebElement heading;

    public WhatIsFhcScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("What is FHC", "What is Financial Health Check", "Financial Health Check");
    }
}
