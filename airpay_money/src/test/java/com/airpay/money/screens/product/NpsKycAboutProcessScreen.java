package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * About KYC Process DigiLocker / KRA consent. Live dumps 2026-09-18, build 2.0.124.
 */
public class NpsKycAboutProcessScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='About KYC Process']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.CheckBox")
    private WebElement consentCheckbox;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Continue']")
    private WebElement continueButton;

    public NpsKycAboutProcessScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='About KYC Process']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Continue']")) != null;
    }
}
