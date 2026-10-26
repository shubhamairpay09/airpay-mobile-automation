package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds risk profile Question 4/5 — maximum 1-year loss tolerance.
 * Live dump 2026-09-17, build 2.0.124.
 */
public class RiskProfileQuestion4Screen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@clickable='true' and @content-desc='']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Question 4/5']")
    private WebElement progress;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='What is the maximum loss (within 1 year) that you can tolerate?']")
    private WebElement question;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'any capital loss')]")
    private WebElement noLoss;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Upto 5% to 10% of my portfolio']")
    private WebElement fiveToTen;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Upto 10% to 20% of my portfolio']")
    private WebElement tenToTwenty;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Above 20%']")
    private WebElement aboveTwenty;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Next']")
    private WebElement next;

    public RiskProfileQuestion4Screen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Question 4/5']"),
                MobileWaits.xpath("//android.view.View[@content-desc='What is the maximum loss (within 1 year) that you can tolerate?']")) != null;
    }
}
