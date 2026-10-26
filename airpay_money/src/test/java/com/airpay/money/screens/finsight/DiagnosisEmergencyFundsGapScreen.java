package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Diagnose screen 3 — Gap 03 Emergency Funds HEALTHY.
 */
public class DiagnosisEmergencyFundsGapScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'GAP 03 OF 03')]")
    private WebElement gapLabel;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Emergency Funds']")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='HEALTHY']")
    private WebElement healthy;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Your cover']")
    private WebElement yourCover;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Required Cover']")
    private WebElement requiredCover;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='NO SHORTFALL']")
    private WebElement noShortfall;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Start SIP']")
    private WebElement startSip;

    public DiagnosisEmergencyFundsGapScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Emergency Funds") && sourceContains("HEALTHY", "GAP 03", "Start SIP");
    }
}
