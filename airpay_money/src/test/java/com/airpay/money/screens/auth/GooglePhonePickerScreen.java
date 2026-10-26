package com.airpay.money.screens.auth;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/** Google "Choose a phone number" sheet shown on first launch. Dump 2.1.3 2026-09-25. */
public class GooglePhonePickerScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[@resource-id='com.google.android.gms:id/title' and @text='Choose a phone number']")
    private WebElement title;

    @AndroidFindBy(xpath = "//*[@resource-id='com.google.android.gms:id/cancel']")
    private WebElement cancel;

    @AndroidFindBy(xpath = "//*[@resource-id='com.google.android.gms:id/phone_number_list_item']")
    private WebElement firstNumberRow;

    @AndroidFindBy(xpath = "//*[@resource-id='com.google.android.gms:id/phone_number']")
    private WebElement firstNumberText;

    public GooglePhonePickerScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Choose a phone number", "com.google.android.gms:id/phone_number_list");
    }

    public boolean selectNumberIfShown(String mobile) {
        if (!isDisplayed()) {
            return false;
        }
        String digits = mobile == null ? "" : mobile.replaceAll("\\D", "");
        if (!digits.isBlank()) {
            String spaced = digits.length() == 10
                    ? "0" + digits.substring(0, 5) + " " + digits.substring(5)
                    : digits;
            if (tapFirst(Duration.ofSeconds(4),
                    MobileWaits.xpath("//*[@resource-id='com.google.android.gms:id/phone_number' and contains(@text,'" + digits.substring(Math.max(0, digits.length() - 10)) + "')]"),
                    MobileWaits.xpath("//*[@resource-id='com.google.android.gms:id/phone_number' and contains(@text,'" + spaced + "')]"),
                    AppiumBy.id("com.google.android.gms:id/phone_number_list_item"))) {
                sleep(800);
                return true;
            }
        }
        dismissIfShown();
        return false;
    }

    public void dismissIfShown() {
        if (!isDisplayed()) {
            return;
        }
        tapFirst(Duration.ofSeconds(6),
                AppiumBy.id("com.google.android.gms:id/cancel"),
                AppiumBy.accessibilityId("Cancel"),
                MobileWaits.xpath("//*[@content-desc='Cancel']"));
        sleep(800);
    }
}
