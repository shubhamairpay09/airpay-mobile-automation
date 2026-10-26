package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Recommendation View all — Funds basis on your risk profile.
 * Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsRecommendationScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Funds basis on your risk')]")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='']")
    private WebElement filter;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='']")
    private WebElement search;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Clear All']")
    private WebElement chipClearAll;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Aditya Birla Sun Life Medium Term Plan')]")
    private WebElement firstFund;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'HDFC Money Market Fund')]")
    private WebElement hdfcMoneyMarket;

    public MutualFundsRecommendationScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Funds basis on your risk')]"),
                MobileWaits.xpath("//android.widget.EditText[@content-desc='']")) != null;
    }

    public boolean openFundCard() {
        for (WebElement card : driver.findElements(MobileWaits.xpath(
                "//android.view.View[@clickable='true' and contains(@content-desc,'Fund')]"))) {
            try {
                String desc = String.valueOf(card.getAttribute("content-desc"));
                if (card.isDisplayed() && desc.length() > 24) {
                    card.click();
                    sleep(800);
                    return true;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return false;
    }
}
