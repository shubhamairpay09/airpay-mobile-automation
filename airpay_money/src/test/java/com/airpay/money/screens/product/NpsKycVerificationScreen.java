package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * KYC Verification Digilocker vs Aadhaar OTP. Live dump 2026-09-18, build 2.0.124.
 */
public class NpsKycVerificationScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='KYC Verification']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Verify using Digilocker')]")
    private WebElement digilocker;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Verify using Aadhaar OTP')]")
    private WebElement aadhaarOtp;

    public NpsKycVerificationScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='KYC Verification']"),
                MobileWaits.xpath("//android.widget.ImageView[contains(@content-desc,'Verify using Aadhaar OTP')]")) != null;
    }
}
