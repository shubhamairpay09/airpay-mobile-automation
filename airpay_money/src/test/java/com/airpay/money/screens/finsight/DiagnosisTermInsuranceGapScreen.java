package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Diagnose gap 01 — Term Insurance CRITICAL.
 */
public class DiagnosisTermInsuranceGapScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'GAP 01 OF 03')]")
    private WebElement gapLabel;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Term Insurance']")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='CRITICAL']")
    private WebElement critical;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'See plans')]")
    private WebElement seePlans;

    public DiagnosisTermInsuranceGapScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Term Insurance") && sourceContains("CRITICAL") && sourceContains("See plans");
    }
}
