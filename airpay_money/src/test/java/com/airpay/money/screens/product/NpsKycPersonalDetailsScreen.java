package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * NPS KYC Personal details. Live dumps 2026-09-18, build 2.0.124.
 */
public class NpsKycPersonalDetailsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Personal details']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Full name']")
    private WebElement fullName;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Update']")
    private WebElement update;

    public NpsKycPersonalDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Personal details']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Update']")) != null;
    }
}
