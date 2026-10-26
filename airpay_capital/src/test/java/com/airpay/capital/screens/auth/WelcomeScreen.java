package com.airpay.capital.screens.auth;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class WelcomeScreen extends BaseScreen {

    public static final String LOGIN_LABEL = "Login With Mobile Number";

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Do you need Financial')]")
    @iOSXCUITFindBy(accessibility = "Do you need Financial")
    private WebElement heroCopy;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Login With Mobile Number')]")
    @iOSXCUITFindBy(accessibility = "Login With Mobile Number")
    private WebElement loginWithMobileButton;

    public WelcomeScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return waitForLoginButton(Duration.ofSeconds(25));
    }

    public boolean waitForLoginButton(Duration timeout) {
        if (firstPresent(timeout, MobileWaits.xpath(
                "//android.widget.Button[contains(@content-desc,'Login With Mobile Number')]")) != null) {
            return true;
        }
        return isDisplayed(loginWithMobileButton);
    }

    public void tapLoginWithMobile() {
        if (!tapFirst(Duration.ofSeconds(12),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Login With Mobile Number')]"),
                accessibility(LOGIN_LABEL))) {
            throw new IllegalStateException("Welcome Login button not found.");
        }
    }
}
