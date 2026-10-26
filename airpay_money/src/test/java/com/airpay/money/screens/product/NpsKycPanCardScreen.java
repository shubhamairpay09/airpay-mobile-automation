package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * PAN Card photo preview + Submit. Live dump 2026-09-21, build 2.0.124.
 */
public class NpsKycPanCardScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='PAN Card']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Submit']")
    private WebElement submit;

    public NpsKycPanCardScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='PAN Card']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Submit']")) != null;
    }
}
