package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Fixerra Aadhaar Verification hand-off after "Book FD" on {@link FixerraFdBookingScreen}.
 * Live dump 2026-09-21, build 2.0.124. Proceeding leaves the app for the Aadhaar OTP journey, so automation stops here.
 */
public class FixerraAadhaarVerificationScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Verify Your Aadhaar']")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Proceed to Verification']")
    private WebElement proceed;

    public FixerraAadhaarVerificationScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(10),
                MobileWaits.xpath("//android.widget.TextView[@text='Aadhaar Verification']"),
                MobileWaits.xpath("//android.widget.Button[@text='Proceed to Verification']")) != null;
    }

    public void proceed() {
        proceed.click();
    }

    public boolean openReportIssue() {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath("//android.widget.TextView[@text='Report here']"));
    }
}
