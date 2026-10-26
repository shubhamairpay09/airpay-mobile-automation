package com.airpay.money.screens.auth;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/** Create / confirm new MPIN after Forgot PIN. Dump 2.1.3 2026-09-25. */
public class SetNewPinScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[@content-desc='Enter your New PIN']")
    private WebElement enterTitle;

    @AndroidFindBy(xpath = "//*[@content-desc='Confirm your PIN']")
    private WebElement confirmTitle;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Create a unique 6-digit PIN')]")
    private WebElement subtitle;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'MPIN entered')]")
    private WebElement progress;

    @AndroidFindBy(xpath = "//*[@content-desc='Backspace']")
    private WebElement backspace;

    @AndroidFindBy(xpath = "//*[@content-desc='Success!']")
    private WebElement successTitle;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'PIN has been set successfully')]")
    private WebElement successMessage;

    @AndroidFindBy(xpath = "//*[@content-desc='Continue']")
    private WebElement continueButton;

    public SetNewPinScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Enter your New PIN", "Confirm your PIN", "Create a unique 6-digit PIN");
    }

    public void enterPin(String pin) {
        if (pin == null || pin.length() != 6) {
            throw new IllegalArgumentException("New PIN must be 6 digits.");
        }
        for (int i = 0; i < pin.length(); i++) {
            char digit = pin.charAt(i);
            if (!tapFirst(Duration.ofSeconds(4),
                    MobileWaits.xpath("//*[starts-with(@content-desc,'Digit " + digit + "')]"),
                    accessibility("Digit " + digit))) {
                throw new IllegalStateException("PIN key " + digit + " not found.");
            }
            sleep(150);
        }
    }

    public boolean isSuccessDisplayed() {
        return sourceContains("Success!") && sourceContains("PIN has been set successfully");
    }

    public void tapContinue() {
        if (!tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//*[@content-desc='Continue']"),
                accessibility("Continue"))) {
            throw new IllegalStateException("Continue not found after PIN reset.");
        }
        sleep(800);
    }
}
