package com.airpay.money.screens.menu;

import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class AboutScreen extends AbstractMenuScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'About Us') or contains(@content-desc,'About airpay')]")
    @iOSXCUITFindBy(accessibility = "About Us")
    private WebElement heading;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Website') or contains(@content-desc,'airpay.money')]")
    @iOSXCUITFindBy(accessibility = "Website")
    private WebElement websiteLink;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Version') or contains(@content-desc,'App version')]")
    @iOSXCUITFindBy(accessibility = "App version")
    private WebElement versionLabel;

    public AboutScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("About", "airpay money", "Version");
    }
}
