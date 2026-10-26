package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * UIDAI Aadhaar OTP WebView inside KYC Verification. Live dumps 2026-09-18, build 2.0.124.
 */
public class NpsKycAadhaarOtpScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='KYC Verification']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.webkit.WebView")
    private WebElement webView;

    @AndroidFindBy(xpath = "//android.widget.Button[@hint='Send OTP']")
    private WebElement sendOtp;

    public NpsKycAadhaarOtpScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.TextView[contains(@text,'resident.uidai.gov.in/offline-kyc')]"),
                MobileWaits.xpath("//android.widget.Button[@hint='Send OTP']")) != null;
    }
}
