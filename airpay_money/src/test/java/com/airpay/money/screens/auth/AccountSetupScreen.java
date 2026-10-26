package com.airpay.money.screens.auth;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.crawl.DummyFormFiller;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

/**
 * Post-OTP Setup your account (Full Name, Email, Date of Birth).
 * Live dump 2026-09-17, build 2.0.124.
 */
public class AccountSetupScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Setup your account']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Update profile photo']")
    private WebElement updatePhoto;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Full Name']")
    private WebElement fullName;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Email']")
    private WebElement email;

    @AndroidFindBy(xpath = "//android.view.View[@hint='Date of Birth']")
    private WebElement dateOfBirth;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Have a referral code?']")
    private WebElement referral;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Complete Your Setup']")
    private WebElement completeSetup;

    public AccountSetupScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Setup your account']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Complete Your Setup']")) != null;
    }

    public void fillAndSubmit() {
        new DummyFormFiller(driver).fillVisibleFields(0);
        typeIfPresent("//android.widget.EditText[@hint='Full Name']", "Test User");
        typeIfPresent("//android.widget.EditText[@hint='Email']", "test.user@airpay.example");
        hideKeyboardQuiet();
        pickDateOfBirth();
        hideKeyboardQuiet();
        sleep(400);
        if (!tapFirst(Duration.ofSeconds(8),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Complete Your Setup']"),
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Complete Your Setup\")"),
                AppiumBy.accessibilityId("Complete Your Setup"))) {
            throw new IllegalStateException("Complete Your Setup button not found.");
        }
        sleep(1500);
    }

    private void pickDateOfBirth() {
        WebElement dob = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@hint='Date of Birth']"),
                MobileWaits.xpath("//*[@hint='Date of Birth']"));
        if (dob == null) {
            return;
        }
        clickOrTapCenter(dob);
        sleep(700);
        tapFirst(Duration.ofSeconds(6),
                AppiumBy.androidUIAutomator("new UiSelector().text(\"OK\")"),
                AppiumBy.androidUIAutomator("new UiSelector().text(\"SET\")"),
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Done\")"),
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Confirm\")"),
                AppiumBy.androidUIAutomator("new UiSelector().description(\"OK\")"),
                AppiumBy.accessibilityId("OK"),
                MobileWaits.xpath("//android.widget.Button[@text='OK' or @content-desc='OK']"),
                MobileWaits.xpath("//*[@clickable='true' and (@text='OK' or @content-desc='OK')]"));
        sleep(400);
    }

    private void clickOrTapCenter(WebElement el) {
        try {
            el.click();
            return;
        } catch (Exception ignored) {
            // Flutter date field is often focusable but not clickable
        }
        Rectangle r = el.getRect();
        tapViewport(r.getX() + r.getWidth() / 2, r.getY() + r.getHeight() / 2);
    }

    private void tapViewport(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
    }

    private void typeIfPresent(String xpath, String value) {
        WebElement field = firstPresent(Duration.ofSeconds(4), MobileWaits.xpath(xpath));
        if (field != null) {
            type(field, value);
        }
    }

    private void hideKeyboardQuiet() {
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // keyboard not open
        }
    }
}
