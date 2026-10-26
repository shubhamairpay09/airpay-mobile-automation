package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * PAN Photo Crop image. Live dump 2026-09-21, build 2.0.124.
 */
public class NpsKycPanPhotoCropScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Crop image']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Crop']")
    private WebElement cropConfirm;

    public NpsKycPanPhotoCropScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.TextView[@text='Crop image']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Crop']")) != null;
    }
}
