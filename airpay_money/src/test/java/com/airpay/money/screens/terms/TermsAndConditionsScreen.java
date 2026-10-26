package com.airpay.money.screens.terms;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/** Guest Terms & Conditions — start screen (header Login + Menu). */
public class TermsAndConditionsScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Terms & Conditions\")")
    @iOSXCUITFindBy(accessibility = "Terms & Conditions")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Menu\")")
    private WebElement menuButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().description(\"Login\")")
    private WebElement loginButton;

    public TermsAndConditionsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return hasMenu() && hasLogin();
    }

    public boolean hasMenu() {
        return firstPresent(Duration.ofSeconds(2),
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Menu\")"),
                MobileWaits.xpath("//android.widget.Button[@text='Menu']")) != null;
    }

    public boolean hasLogin() {
        return firstPresent(Duration.ofSeconds(2),
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Login\")"),
                AppiumBy.accessibilityId("Login"),
                MobileWaits.xpath("//*[@content-desc='Login']")) != null;
    }

    public boolean tapMenu() {
        return tapFirst(Duration.ofSeconds(8),
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Menu\")"),
                MobileWaits.xpath("//android.widget.Button[@text='Menu']"),
                AppiumBy.accessibilityId("Menu"));
    }

    public void tapLogin() {
        if (tapFirst(Duration.ofSeconds(8),
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Login\")"),
                AppiumBy.accessibilityId("Login"),
                MobileWaits.xpath("//*[@content-desc='Login']"))) {
            sleep(800);
            return;
        }
        throw new IllegalStateException("Header Login not found (UiSelector().description(\"Login\")).");
    }

    public void tapBackIcon() {
        if (tapFirst(Duration.ofSeconds(6),
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Back\")"),
                AppiumBy.accessibilityId("Back"),
                MobileWaits.xpath("//*[contains(@content-desc,'Back') or contains(@content-desc,'Navigate')]"),
                MobileWaits.xpath("//android.widget.ImageButton[@clickable='true']"))) {
            sleep(600);
            return;
        }
        tapBack();
        sleep(400);
    }
}
