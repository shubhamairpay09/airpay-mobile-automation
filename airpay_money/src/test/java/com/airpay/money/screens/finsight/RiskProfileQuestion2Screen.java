package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds risk profile Question 2/5 — income needed during investment period.
 * Live dump 2026-09-17, build 2.0.124.
 */
public class RiskProfileQuestion2Screen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@clickable='true' and @content-desc='']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Question 2/5']")
    private WebElement progress;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Do you need income from this investment portfolio to cover your expenses during the specified investment period ?']")
    private WebElement question;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Yes']")
    private WebElement yes;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='No']")
    private WebElement no;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Maybe']")
    private WebElement maybe;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Next']")
    private WebElement next;

    public RiskProfileQuestion2Screen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Question 2/5']"),
                MobileWaits.xpath("//android.view.View[@content-desc='Do you need income from this investment portfolio to cover your expenses during the specified investment period ?']")) != null;
    }
}
