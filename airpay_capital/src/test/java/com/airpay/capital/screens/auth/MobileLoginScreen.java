package com.airpay.capital.screens.auth;

import com.airpay.capital.data.ScreenMessages;
import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class MobileLoginScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Please enter mobile number')]")
    @iOSXCUITFindBy(accessibility = "Please enter mobile number")
    private WebElement instruction;

    @AndroidFindBy(xpath = "//*[@hint='Enter 10-Digit Number' or contains(@hint,'10-Digit')]")
    @iOSXCUITFindBy(accessibility = "Enter 10-Digit Number")
    private WebElement mobileNumberField;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Accept consent')]")
    @iOSXCUITFindBy(accessibility = "Accept consent")
    private WebElement consentCheckbox;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Send OTP')]")
    @iOSXCUITFindBy(accessibility = "Send OTP")
    private WebElement sendOtpButton;

    public MobileLoginScreen(TestContext context) {
        super(context);
    }

    public boolean waitUntilDisplayed() {
        return waitUntilDisplayed(Duration.ofSeconds(20));
    }

    public boolean waitUntilDisplayed(Duration timeout) {
        return firstPresent(timeout,
                MobileWaits.xpath("//*[contains(@content-desc,'Send OTP')]"),
                MobileWaits.xpath("//*[@hint='Enter 10-Digit Number' or contains(@hint,'10-Digit')]")) != null;
    }

    public void enterMobile(String mobile) {
        WebElement field = firstPresent(Duration.ofSeconds(8),
                MobileWaits.xpath("//*[@hint='Enter 10-Digit Number' or contains(@hint,'10-Digit')]"),
                accessibility("Enter 10-Digit Number"),
                AppiumBy.className("android.widget.EditText"));
        if (field == null) {
            throw new IllegalStateException("Mobile number field not found.");
        }
        type(field, mobile);
    }

    public void acceptConsent() {
        if (!tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//*[contains(@content-desc,'Accept consent')]"),
                accessibility(ScreenMessages.ACCEPT_CONSENT))) {
            throw new IllegalStateException("Consent checkbox not found.");
        }
    }

    public void tapSendOtp() {
        if (!tapFirst(Duration.ofSeconds(8),
                MobileWaits.xpath("//*[contains(@content-desc,'Send OTP')]"),
                accessibility(ScreenMessages.SEND_OTP))) {
            throw new IllegalStateException("Send OTP button not found.");
        }
    }

    public boolean instructionIsVisible() {
        return sourceContains(ScreenMessages.LOGIN_INSTRUCTION) || isDisplayed(instruction);
    }
}
