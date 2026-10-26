package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Google Photos picker for PAN Photo Upload. Live dump 2026-09-21, build 2.0.124.
 */
public class NpsKycPanPhotoPickerScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Photos']")
    private WebElement photos;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Collections']")
    private WebElement collections;

    public NpsKycPanPhotoPickerScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Photos']"),
                MobileWaits.xpath("//android.widget.TextView[@text='Search Google Photos']")) != null;
    }
}
