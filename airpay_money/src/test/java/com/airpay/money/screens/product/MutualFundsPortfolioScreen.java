package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds Portfolio — empty current value / returns card.
 * Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsPortfolioScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Portfolio']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Help']")
    private WebElement help;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Current Value']")
    private WebElement currentValue;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Overall Returns']")
    private WebElement overallReturns;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Invested: ']")
    private WebElement invested;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'as on')]")
    private WebElement asOn;

    public MutualFundsPortfolioScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Portfolio']"),
                MobileWaits.xpath("//android.view.View[@content-desc='Current Value']")) != null;
    }
}
