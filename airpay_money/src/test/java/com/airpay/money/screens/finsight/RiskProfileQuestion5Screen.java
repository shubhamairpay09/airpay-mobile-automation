package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds risk profile Question 5/5 — response to a loss beyond specified level.
 * Live dump 2026-09-17, build 2.0.124.
 */
public class RiskProfileQuestion5Screen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@clickable='true' and @content-desc='']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Question 5/5']")
    private WebElement progress;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='How would you typically respond if your investment portfolio experiences a loss beyond your specified level due to market fluctuations?']")
    private WebElement question;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Sell all your investments immediately']")
    private WebElement sellAll;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Sell some investments and continue to hold the rest']")
    private WebElement sellSome;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Stay Invested']")
    private WebElement stayInvested;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Next']")
    private WebElement next;

    public RiskProfileQuestion5Screen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Question 5/5']"),
                MobileWaits.xpath("//android.view.View[@content-desc='How would you typically respond if your investment portfolio experiences a loss beyond your specified level due to market fluctuations?']")) != null;
    }
}
