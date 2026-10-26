package com.airpay.money.screens.terms.hamburger;

import com.airpay.money.screens.menu.AbstractMenuScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class StartFinancialHealthCheckScreen extends AbstractMenuScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Start Financial Health Check\")")
    @iOSXCUITFindBy(accessibility = "Start Financial Health Check")
    private WebElement heading;

    public StartFinancialHealthCheckScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Financial Health Check", "Start Financial Health Check", "FHC");
    }
}
