package com.airpay.money.screens.auth;

import com.airpay.money.data.ScreenMessages;
import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/** Header Login opens this modal: Welcome to airpay Money / Login / Sign up. */
public class LoginPopupScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Login / Sign up\")")
    private WebElement loginSignUpButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Enter your mobile number\")")
    private WebElement mobileHint;

    public LoginPopupScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains(
                ScreenMessages.LOGIN_SIGN_UP,
                "Enter your mobile number",
                "Welcome to",
                "You will receive an OTP");
    }

    public boolean waitUntilDisplayed(Duration timeout) {
        return firstPresent(timeout,
                AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Login / Sign up\")"),
                AppiumBy.androidUIAutomator("new UiSelector().textContains(\"Enter your mobile number\")"),
                MobileWaits.xpath("//*[contains(@text,'Login / Sign up') or contains(@content-desc,'Login / Sign up')]"),
                MobileWaits.xpath("//*[contains(@hint,'Enter your mobile number') or contains(@text,'Enter your mobile number')]"))
                != null;
    }

    public void tapClose() {
        if (tapFirst(Duration.ofSeconds(6),
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Close\")"),
                AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"close\")"),
                AppiumBy.accessibilityId("Close"),
                MobileWaits.xpath("//*[contains(@content-desc,'Close') or @content-desc='x' or @content-desc='X']"),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Close')]"))) {
            sleep(500);
            return;
        }
        throw new IllegalStateException("Login popup X / Close icon not found.");
    }
}
