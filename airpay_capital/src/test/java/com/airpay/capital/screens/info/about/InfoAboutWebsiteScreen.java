package com.airpay.capital.screens.info.about;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

/** About Us → Airpay Capital website (Chrome / WebView). */
public class InfoAboutWebsiteScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Close') or @text='Close']")
    @iOSXCUITFindBy(accessibility = "Close")
    private WebElement closeButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Eligibility']")
    @iOSXCUITFindBy(accessibility = "Eligibility")
    private WebElement eligibility;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'How it works')]")
    @iOSXCUITFindBy(accessibility = "How it works")
    private WebElement howItWorks;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='About Us']")
    @iOSXCUITFindBy(accessibility = "About Us")
    private WebElement quickLinkAboutUs;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Our Partners']")
    @iOSXCUITFindBy(accessibility = "Our Partners")
    private WebElement quickLinkPartners;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Grievance Redressal']")
    @iOSXCUITFindBy(accessibility = "Grievance Redressal")
    private WebElement quickLinkGrievance;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Terms & Conditions']")
    @iOSXCUITFindBy(accessibility = "Terms & Conditions")
    private WebElement quickLinkTerms;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Privacy Policy']")
    @iOSXCUITFindBy(accessibility = "Privacy Policy")
    private WebElement quickLinkPrivacy;

    public InfoAboutWebsiteScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Eligibility", "How it works", "airpay.capital", "About Us");
    }

    public void close() {
        tapLabel("Close");
    }
}
