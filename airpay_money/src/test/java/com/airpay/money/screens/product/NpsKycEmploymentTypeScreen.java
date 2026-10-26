package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Select employment type sheet. Live dump 2026-09-18, build 2.0.124.
 */
public class NpsKycEmploymentTypeScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Select employment type']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Salaried']")
    private WebElement salaried;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Search...']")
    private WebElement search;

    public NpsKycEmploymentTypeScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Select employment type']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Salaried']")) != null;
    }
}
