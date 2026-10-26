package com.airpay.money.screens.terms.hamburger.financialhealth;

import com.airpay.money.screens.menu.AbstractMenuScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class HowItWorksScreen extends AbstractMenuScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"How it works\")")
    @iOSXCUITFindBy(accessibility = "How it works")
    private WebElement heading;

    public HowItWorksScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("How it works", "How it Works", "How FHC works");
    }
}
