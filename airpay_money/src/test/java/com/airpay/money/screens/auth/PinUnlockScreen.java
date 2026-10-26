package com.airpay.money.screens.auth;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Session PIN pad. Live dump 2.1.3 2026-09-25: Welcome Back + Digit N keys.
 */
public class PinUnlockScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[@content-desc='Welcome Back']")
    private WebElement title;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Enter your Airpay Money PIN')]")
    private WebElement subtitle;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'MPIN entered')]")
    private WebElement progress;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Incorrect PIN')]")
    private WebElement error;

    @AndroidFindBy(xpath = "//*[@content-desc='Forgot PIN?']")
    private WebElement forgotPin;

    @AndroidFindBy(xpath = "//*[@content-desc='Backspace']")
    private WebElement backspace;

    @AndroidFindBy(xpath = "//*[starts-with(@content-desc,'Digit 0')]")
    private WebElement key0;

    @AndroidFindBy(xpath = "//*[starts-with(@content-desc,'Digit 1')]")
    private WebElement key1;

    @AndroidFindBy(xpath = "//*[starts-with(@content-desc,'Digit 2')]")
    private WebElement key2;

    @AndroidFindBy(xpath = "//*[starts-with(@content-desc,'Digit 3')]")
    private WebElement key3;

    @AndroidFindBy(xpath = "//*[starts-with(@content-desc,'Digit 4')]")
    private WebElement key4;

    @AndroidFindBy(xpath = "//*[starts-with(@content-desc,'Digit 5')]")
    private WebElement key5;

    @AndroidFindBy(xpath = "//*[starts-with(@content-desc,'Digit 6')]")
    private WebElement key6;

    @AndroidFindBy(xpath = "//*[starts-with(@content-desc,'Digit 7')]")
    private WebElement key7;

    @AndroidFindBy(xpath = "//*[starts-with(@content-desc,'Digit 8')]")
    private WebElement key8;

    @AndroidFindBy(xpath = "//*[starts-with(@content-desc,'Digit 9')]")
    private WebElement key9;

    public PinUnlockScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Welcome Back") && sourceContains("PIN");
    }

    public boolean waitUntilDisplayed(Duration timeout) {
        return firstPresent(timeout,
                MobileWaits.xpath("//*[@content-desc='Welcome Back']"),
                MobileWaits.xpath("//*[contains(@content-desc,'Enter your Airpay Money PIN')]")) != null;
    }

    public void tapForgotPin() {
        if (!tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//*[@content-desc='Forgot PIN?']"),
                accessibility("Forgot PIN?"))) {
            throw new IllegalStateException("Forgot PIN? not found.");
        }
        sleep(800);
    }

    public void enterPin(String pin) {
        if (pin == null || pin.isBlank()) {
            throw new IllegalStateException("money.test.pin is blank.");
        }
        for (int i = 0; i < pin.length(); i++) {
            char digit = pin.charAt(i);
            if (digit < '0' || digit > '9') {
                throw new IllegalArgumentException("PIN must be digits: " + pin);
            }
            if (!tapFirst(Duration.ofSeconds(4),
                    MobileWaits.xpath("//*[starts-with(@content-desc,'Digit " + digit + "')]"),
                    MobileWaits.xpath("//android.view.View[@content-desc='" + digit + "' and @clickable='true']"),
                    MobileWaits.xpath("//android.widget.Button[@content-desc='" + digit + "']"),
                    accessibility(String.valueOf(digit)))) {
                throw new IllegalStateException("PIN key " + digit + " not found.");
            }
            sleep(120);
        }
    }
}
