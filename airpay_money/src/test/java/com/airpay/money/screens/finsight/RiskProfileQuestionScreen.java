package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds risk profile Question 1/5 — ideal time horizon.
 * Live dump 2026-09-17, build 2.0.124.
 */
public class RiskProfileQuestionScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@clickable='true' and @content-desc='']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Question 1/5']")
    private WebElement progress;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='What would be your ideal time horizon for your investments']")
    private WebElement question;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Less than 1 year']")
    private WebElement lessThanOneYear;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='1 to 3 years']")
    private WebElement oneToThreeYears;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'3 to 5 years')]")
    private WebElement threeToFiveYears;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Above 5 years']")
    private WebElement aboveFiveYears;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Next']")
    private WebElement next;

    public RiskProfileQuestionScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Question 1/5']"),
                MobileWaits.xpath("//android.view.View[@content-desc='What would be your ideal time horizon for your investments']")) != null;
    }
}
