package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds — Fund Details. Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsFundDetailsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Fund Details']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='HDFC Money Market Fund']")
    private WebElement fundName;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='One Time']")
    private WebElement oneTime;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Monthly']")
    private WebElement monthly;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Returns']")
    private WebElement returns;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Return Calculator-SIP/One time']")
    private WebElement returnCalculator;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Peer Comparison']")
    private WebElement peerComparison;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Holdings')]")
    private WebElement holdings;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Other plans of same fund']")
    private WebElement otherPlans;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Investment Objective']")
    private WebElement investmentObjective;

    public MutualFundsFundDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Fund Details']"),
                MobileWaits.xpath("//android.view.View[@content-desc='One Time']")) != null;
    }
}
