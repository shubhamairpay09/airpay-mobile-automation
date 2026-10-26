package com.airpay.money.screens.menu;

import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class PrivacyScreen extends AbstractMenuScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Privacy Policy') or contains(@content-desc,'Privacy')]")
    @iOSXCUITFindBy(accessibility = "Privacy Policy")
    private WebElement heading;

    public PrivacyScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Privacy", "personal data", "policy");
    }
}
