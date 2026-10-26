package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds entry — Lets do risk profile / I already know.
 * Live dump 2026-09-17, build 2.0.124.
 */
public class RiskProfileIntroScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@clickable='true' and @content-desc='']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@bounds='[195,376][885,883]']")
    private WebElement hero;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Lets do risk profile']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'financial risk')]")
    private WebElement subtitle;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Lets do risk profile']")
    private WebElement letsDoRiskProfile;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='I already know']")
    private WebElement iAlreadyKnow;

    public RiskProfileIntroScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Lets do risk profile']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='I already know']")) != null;
    }
}
