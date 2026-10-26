package com.airpay.money.screens.terms.hamburger.financialhealth;

import com.airpay.money.screens.menu.AbstractMenuScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class StartFhcScreen extends AbstractMenuScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Start FHC\")")
    @iOSXCUITFindBy(accessibility = "Start FHC")
    private WebElement heading;

    public StartFhcScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Start FHC", "Start Financial Health Check");
    }
}
