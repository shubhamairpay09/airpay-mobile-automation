package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Diagnose screen 2 — Unfunded Protection.
 */
public class DiagnosisUnfundedProtectionScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'02 / UNFUNDED PROTECTION')]")
    private WebElement stepLabel;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Total Required Protection')]")
    private WebElement requiredProtection;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Term Insurance']")
    private WebElement termInsurance;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Health Insurance']")
    private WebElement healthInsurance;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Emergency Funds']")
    private WebElement emergencyFunds;

    public DiagnosisUnfundedProtectionScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("UNFUNDED PROTECTION") || sourceContains("Total Required Protection");
    }
}
