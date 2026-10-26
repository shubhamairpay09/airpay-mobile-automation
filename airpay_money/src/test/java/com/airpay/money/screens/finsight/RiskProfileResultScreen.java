package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds risk profile result — Your Risk Profile.
 * Live dump 2026-09-17, build 2.0.124.
 */
public class RiskProfileResultScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Your Risk Profile']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='41.0']")
    private WebElement score;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Moderately Conservative']")
    private WebElement profile;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'moderately conservative investor')]")
    private WebElement description;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Go to dashboard']")
    private WebElement goToDashboard;

    public RiskProfileResultScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Your Risk Profile']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Go to dashboard']")) != null;
    }
}
