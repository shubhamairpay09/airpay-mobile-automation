package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Filters sheet — Category / AMC / Risk / Returns / Rating (Recommendation and Curated).
 * Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsFiltersScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Filters']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Clear All')]")
    private WebElement clearAll;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Category']")
    private WebElement tabCategory;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='AMC']")
    private WebElement tabAmc;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Risk']")
    private WebElement tabRisk;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Returns']")
    private WebElement tabReturns;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Rating']")
    private WebElement tabRating;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Select Categories']")
    private WebElement selectCategories;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Equity']")
    private WebElement equity;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Select AMCs']")
    private WebElement selectAmcs;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Aditya Birla Sun Life AMC')]")
    private WebElement amcAdityaBirla;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'HDFC Asset Management')]")
    private WebElement amcHdfc;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Select Risk']")
    private WebElement selectRisk;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Balanced']")
    private WebElement riskBalanced;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Sort by']")
    private WebElement sortByReturns;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='1 year return']")
    private WebElement return1Year;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Sort By']")
    private WebElement sortByRating;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Descending']")
    private WebElement ratingDescending;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Apply']")
    private WebElement apply;

    public MutualFundsFiltersScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Filters']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Apply']")) != null;
    }

    public void selectEquityAndApply() {
        tapLabel("Category");
        tapLabel("Equity");
        tapLabel("Apply");
        sleep(700);
    }

    public void selectBalancedRiskAndApply() {
        tapLabel("Risk");
        tapLabel("Balanced");
        tapLabel("Apply");
        sleep(700);
    }

    public void clearAll() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Clear All')]"),
                MobileWaits.xpath("//*[@content-desc='Clear All']"));
        sleep(400);
    }
}
