package com.airpay.capital.screens.info.about;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

/** About Us → Terms & Conditions (in-app WebView). */
public class InfoAboutTermsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Close') or @text='Close']")
    @iOSXCUITFindBy(accessibility = "Close")
    private WebElement closeButton;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Terms and Conditions']")
    @iOSXCUITFindBy(accessibility = "Terms and Conditions")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.TextView[contains(@text,'Last Updated')]")
    @iOSXCUITFindBy(accessibility = "Last Updated")
    private WebElement lastUpdated;

    public InfoAboutTermsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Terms and Conditions", "Last Updated");
    }

    public void close() {
        tapLabel("Close");
    }
}
