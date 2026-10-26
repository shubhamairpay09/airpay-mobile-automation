package com.airpay.money.screens.menu;

import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class TermsScreen extends AbstractMenuScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Terms')]")
    @iOSXCUITFindBy(accessibility = "Terms and Conditions")
    private WebElement heading;

    public TermsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Terms", "Conditions", "agreement");
    }
}
