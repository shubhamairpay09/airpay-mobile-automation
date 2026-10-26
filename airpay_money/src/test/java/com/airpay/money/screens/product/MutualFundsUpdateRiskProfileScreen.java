package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Dashboard pencil → Update Risk Profile bottom sheet.
 * Live dump 2026-09-17, build 2.0.124.
 */
public class MutualFundsUpdateRiskProfileScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Update Risk Profile']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Update Risk Profile']/following-sibling::android.view.View[@clickable='true' and @content-desc='']")
    private WebElement close;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'risk assessment is not matching')]")
    private WebElement body;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Conservative']")
    private WebElement conservative;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Moderately Conservative']")
    private WebElement moderatelyConservative;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Balanced']")
    private WebElement balanced;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Moderately Aggressive']")
    private WebElement moderatelyAggressive;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Aggressive']")
    private WebElement aggressive;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Balanced']//android.widget.RadioButton")
    private WebElement radioBalanced;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Dismiss']")
    private WebElement dismiss;

    public MutualFundsUpdateRiskProfileScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Update Risk Profile']"),
                MobileWaits.xpath("//android.view.View[@content-desc='Balanced']")) != null;
    }
}
