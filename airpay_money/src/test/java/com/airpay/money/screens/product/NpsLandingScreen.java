package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * NPS marketing landing. Dump {@code uidump-nps-01-Back} 2.1.3.
 */
public class NpsLandingScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='NPS']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Additional tax benefits')]")
    private WebElement taxBenefits;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'cheapest retirement plan')]")
    private WebElement cheapestPlan;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Tap here')]")
    private WebElement tapHere;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Explore NPS']")
    private WebElement explore;

    public NpsLandingScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='NPS']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Explore NPS']")) != null;
    }
}
