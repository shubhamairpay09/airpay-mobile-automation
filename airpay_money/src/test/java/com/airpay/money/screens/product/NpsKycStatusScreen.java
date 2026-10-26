package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * NPS KYC Status tracker. Live dumps 2026-09-18, build 2.0.124.
 */
public class NpsKycStatusScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='KYC Status']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Start']")
    private WebElement start;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Basic Information')]")
    private WebElement basicInformation;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Aadhaar Verification')]")
    private WebElement aadhaarVerification;

    public NpsKycStatusScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='KYC Status']"),
                MobileWaits.xpath("//android.widget.ImageView[contains(@content-desc,'Basic Information')]")) != null;
    }
}
