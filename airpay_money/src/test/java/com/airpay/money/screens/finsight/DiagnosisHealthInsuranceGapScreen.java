package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Diagnose gap 02 — Health Insurance BELOW TARGET.
 */
public class DiagnosisHealthInsuranceGapScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'GAP 02 OF 03')]")
    private WebElement gapLabel;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Health Insurance']")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='BELOW TARGET']")
    private WebElement belowTarget;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'See plans From')]")
    private WebElement seePlans;

    public DiagnosisHealthInsuranceGapScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Health Insurance") && sourceContains("BELOW TARGET");
    }
}
