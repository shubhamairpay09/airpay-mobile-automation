package com.airpay.money.screens.auth;

import com.airpay.money.data.ScreenMessages;
import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

/**
 * First login screen after launch (Appium dump):
 * Sign Up or Login to Continue, Enter mobile number, Privacy Policy, Terms &amp; Conditions,
 * Login with Mobile Number.
 */
public class MobileLoginScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Sign Up or Login to Continue']")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Airpay Money Logo']")
    private WebElement logo;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    private WebElement mobileField;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='+91']")
    private WebElement countryCode;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Privacy Policy')]")
    private WebElement privacyPolicyLink;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Terms')]")
    private WebElement termsLink;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Login with Mobile Number']")
    private WebElement loginWithMobileButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Page 1']")
    private WebElement carouselPage1;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Page 2']")
    private WebElement carouselPage2;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Page 3']")
    private WebElement carouselPage3;

    private static final List<String> OTP_BUTTON_LABELS = List.of(
            ScreenMessages.LOGIN_WITH_MOBILE,
            ScreenMessages.LOGIN_SIGN_UP,
            ScreenMessages.SEND_OTP,
            ScreenMessages.GET_OTP,
            "Get OTP",
            "Continue",
            "Proceed",
            "Verify");

    public MobileLoginScreen(TestContext context) {
        super(context);
    }

    public boolean waitUntilDisplayed() {
        return waitUntilDisplayed(Duration.ofSeconds(20));
    }

    public boolean waitUntilDisplayed(Duration timeout) {
        return firstPresent(timeout,
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Login with Mobile Number\")"),
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Sign Up or Login to Continue\")"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Login with Mobile Number']"),
                MobileWaits.xpath("//android.widget.EditText[@hint='Enter mobile number']")) != null;
    }

    public void enterMobile(String mobile) {
        WebElement field = firstPresent(Duration.ofSeconds(10),
                MobileWaits.xpath("//android.widget.EditText[@hint='Enter mobile number']"),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'mobile') or contains(@hint,'Mobile')]"),
                AppiumBy.className("android.widget.EditText"));
        if (field == null) {
            throw new IllegalStateException("Mobile number field not found.");
        }
        type(field, mobile);
    }

    public void openPrivacyPolicy() {
        if (tapFirst(Duration.ofSeconds(8),
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Privacy Policy \")"),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Privacy Policy')]"),
                AppiumBy.accessibilityId("Privacy Policy "))) {
            sleep(800);
            return;
        }
        throw new IllegalStateException("Privacy Policy link not found on login screen.");
    }

    public void openTermsAndConditions() {
        if (tapFirst(Duration.ofSeconds(8),
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Terms & Conditions\")"),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Terms')]"),
                AppiumBy.accessibilityId("Terms & Conditions"))) {
            sleep(800);
            return;
        }
        throw new IllegalStateException("Terms & Conditions link not found on login screen.");
    }

    public void tapLoginWithMobileNumber() {
        if (tapFirst(Duration.ofSeconds(8),
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Login with Mobile Number\")"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Login with Mobile Number']"),
                AppiumBy.accessibilityId("Login with Mobile Number"))) {
            sleep(800);
            return;
        }
        tapSendOtp();
    }

    public void acceptConsentIfShown() {
        tapAny(List.of(
                ScreenMessages.ACCEPT_CONSENT,
                "I agree",
                "Accept",
                "Terms and Conditions",
                "Privacy Policy"));
    }

    public void tapSendOtp() {
        sleep(500);
        if (tapAny(OTP_BUTTON_LABELS)) {
            return;
        }
        if (tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Login with Mobile Number']"),
                MobileWaits.xpath("//*[contains(@content-desc,'Login / Sign up')]"))) {
            return;
        }
        if (tapBottomPrimaryButton()) {
            return;
        }
        throw new IllegalStateException("Login with Mobile Number / Send OTP not found.");
    }

    private boolean tapBottomPrimaryButton() {
        try {
            Dimension size = driver.manage().window().getSize();
            driver.executeScript("mobile: clickGesture", java.util.Map.of(
                    "x", size.width / 2, "y", (int) (size.height * 0.92)));
            sleep(600);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
