package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.regex.Pattern;

/**
 * Finvu Net Worth Consent screen after Allow Consent.
 * Verify &amp; Proceed is not tapped and the OTP is not entered.
 */
public class FinvuNetWorthScreen extends BaseScreen {

    private static final String SAFETY_MESSAGE =
            "airpaymoney uses Finvu to safely receive your financial details";

    private static final Pattern MASKED_MOBILE = Pattern.compile(
            "(?i)(?:\\+?91[\\s-]*)?(?:\\d{2,6}[\\s-]*)?[xX*•●]{3,}[\\s-]*\\d{2,6}|\\d{2}[xX*•●]{3,}\\d{2,4}");

    private static final Pattern RESEND_TIMER = Pattern.compile(
            "(?i)(?:resend|try again)[\\s\\S]{0,40}\\d{1,3}\\s*(?:sec|s|:)"
                    + "|\\d{1,2}:\\d{2}[\\s\\S]{0,40}resend"
                    + "|didn't receive otp");

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'uses Finvu to safely receive your financial details')]")
    private WebElement safetyMessage;

    @AndroidFindBy(xpath = "//*[@content-desc='Verify & Proceed' or contains(@content-desc,'Verify & Proceed')]")
    private WebElement verifyAndProceed;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    private WebElement otpField;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Terms and Conditions')]")
    private WebElement termsLink;

    public FinvuNetWorthScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        long end = System.currentTimeMillis() + 12_000;
        while (System.currentTimeMillis() < end) {
            if (visibleNow()) {
                return true;
            }
            sleep(400);
        }
        return false;
    }

    /** One look, with no wait. Used when the Finvu screen should not be open. */
    public boolean visibleNow() {
        return showsText().contains("Finvu") && (safetyMessageDisplayed() || verifyButton() != null || otpFieldsDisplayed());
    }

    public boolean safetyMessageDisplayed() {
        String src = showsText();
        return src.contains(SAFETY_MESSAGE)
                || (src.contains("airpaymoney") && src.contains("uses Finvu")
                && src.contains("safely receive your financial details"));
    }

    public boolean otpFieldsDisplayed() {
        int shown = 0;
        for (WebElement field : driver.findElements(MobileWaits.xpath("//android.widget.EditText"))) {
            try {
                if (field.isDisplayed()) {
                    shown++;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return shown >= 1;
    }

    public boolean otpNotEntered() {
        for (WebElement field : driver.findElements(MobileWaits.xpath("//android.widget.EditText"))) {
            try {
                if (!field.isDisplayed()) {
                    continue;
                }
                String value = String.valueOf(field.getAttribute("text"));
                if (value.matches(".*\\d.*")) {
                    return false;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return true;
    }

    public boolean maskedMobileDisplayed() {
        return MASKED_MOBILE.matcher(showsText()).find();
    }

    public boolean verifyProceedDisplayed() {
        return verifyButton() != null || showsText().contains("Verify & Proceed");
    }

    public boolean verifyProceedEnabled() {
        WebElement button = verifyButton();
        return button != null && "true".equalsIgnoreCase(String.valueOf(button.getAttribute("enabled")));
    }

    public boolean termsDisplayed() {
        String src = showsText();
        return src.contains("Terms and Conditions") && src.contains("Finvu");
    }

    public boolean resendTimerDisplayed() {
        return RESEND_TIMER.matcher(showsText()).find();
    }

    public boolean errorDisplayed() {
        String src = showsText().toLowerCase();
        return src.contains("something went wrong")
                || src.contains("unable to")
                || src.contains("could not")
                || src.contains("couldn't")
                || src.contains("consent failed");
    }

    private String showsText() {
        return pageSource().replace("&amp;", "&").replace("&#10;", "\n").replace('\n', ' ');
    }

    private WebElement verifyButton() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//*[@content-desc='Verify &amp; Proceed' or @text='Verify &amp; Proceed']"),
                MobileWaits.xpath("//*[contains(@content-desc,'Verify') and contains(@content-desc,'Proceed')]"),
                MobileWaits.xpath("//*[contains(@text,'Verify') and contains(@text,'Proceed')]"));
    }
}
