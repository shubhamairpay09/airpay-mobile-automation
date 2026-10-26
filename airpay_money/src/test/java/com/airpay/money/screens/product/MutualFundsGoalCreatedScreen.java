package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds — goal created summary. Live dump 2026-09-17, build 2.0.124.
 */
public class MutualFundsGoalCreatedScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Delete')]")
    private WebElement delete;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='New investment']")
    private WebElement titleNewInvestment;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Buy bike']")
    private WebElement titleBuyBike;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Buy car']")
    private WebElement titleBuyCar;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Monthly SIP')]")
    private WebElement monthlySip;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Monthly SIP') and contains(@content-desc,'851')]")
    private WebElement monthlySip851;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'One time')]")
    private WebElement oneTime;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'One time') and contains(@content-desc,'9810')]")
    private WebElement oneTime9810;

    public MutualFundsGoalCreatedScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Delete')]"),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Goal created on:')]"),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Monthly SIP')]")) != null;
    }

    public boolean deleteGoal() {
        boolean tapped = tapLabel("Delete");
        sleep(700);
        if (sourceContains("Delete", "Yes", "Confirm", "Remove")) {
            if (!tapLabel("Delete")) {
                tapLabel("Yes");
                tapLabel("Confirm");
                tapLabel("Remove");
            }
            sleep(800);
        }
        return tapped;
    }
}
