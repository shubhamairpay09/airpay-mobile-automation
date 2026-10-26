package com.airpay.money.screens.menu;

import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class ContactScreen extends AbstractMenuScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Contact Us')]")
    @iOSXCUITFindBy(accessibility = "Contact Us")
    private WebElement heading;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'support@') or contains(@content-desc,'@airpay')]")
    @iOSXCUITFindBy(accessibility = "Email")
    private WebElement emailLink;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Call') or contains(@content-desc,'Phone') or contains(@content-desc,'6911')]")
    @iOSXCUITFindBy(accessibility = "Call")
    private WebElement phoneLink;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'message') or contains(@hint,'Message')]")
    @iOSXCUITFindBy(accessibility = "Message")
    private WebElement messageField;

    public ContactScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Contact", "support", "Call", "Email");
    }
}
