package com.airpay.capital.screens.info.about;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

/** About Us → Privacy Policy (in-app WebView). */
public class InfoAboutPrivacyScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Close') or @text='Close']")
    @iOSXCUITFindBy(accessibility = "Close")
    private WebElement closeButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Privacy Policy']")
    @iOSXCUITFindBy(accessibility = "Privacy Policy")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Last Updated')]")
    @iOSXCUITFindBy(accessibility = "Last Updated")
    private WebElement lastUpdated;

    @AndroidFindBy(xpath = "//*[contains(@text,'dpo@airpay.capital')]")
    @iOSXCUITFindBy(accessibility = "dpo@airpay.capital")
    private WebElement dpoEmail;

    @AndroidFindBy(xpath = "//*[contains(@text,'capitalsupport@airpay.co.in')]")
    @iOSXCUITFindBy(accessibility = "capitalsupport@airpay.co.in")
    private WebElement supportEmail;

    public InfoAboutPrivacyScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Privacy Policy", "Last Updated");
    }

    public void close() {
        tapLabel("Close");
    }
}
