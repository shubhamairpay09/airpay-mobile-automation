package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds Transactions History — One Time / SIP / Redemption.
 * Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsTransactionsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Transactions History']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Help']")
    private WebElement help;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='One Time']")
    private WebElement oneTime;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='SIP']")
    private WebElement sip;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Redemption']")
    private WebElement redemption;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='No Transaction']")
    private WebElement empty;

    public MutualFundsTransactionsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Transactions History']"),
                MobileWaits.xpath("//android.view.View[@content-desc='No Transaction']")) != null;
    }
}
