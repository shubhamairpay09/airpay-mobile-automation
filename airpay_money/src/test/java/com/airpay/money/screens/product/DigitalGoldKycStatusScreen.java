package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Digital Gold KYC tracker. Dump {@code uidump-gold-06-kyc-status} 2.1.3 2026-09-25.
 */
public class DigitalGoldKycStatusScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='KYC Status']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Basic Information')]")
    private WebElement basicInformation;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Start']")
    private WebElement start;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Email Verification')]")
    private WebElement emailVerification;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Aadhaar Verification')]")
    private WebElement aadhaarVerification;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'PAN Verification')]")
    private WebElement panVerification;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'PAN Photo Upload')]")
    private WebElement panPhotoUpload;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Pending']")
    private WebElement pending;

    public DigitalGoldKycStatusScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("KYC Status") && sourceContains("Basic Information")
                && sourceContains("PAN Photo Upload");
    }

    public void tapStart() {
        if (!tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Start']"),
                accessibility("Start"))) {
            throw new IllegalStateException("Start not found on Digital Gold KYC Status.");
        }
        sleep(800);
    }
}
