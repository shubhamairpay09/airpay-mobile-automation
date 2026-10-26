package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Diagnose 04 / YOUR PLAN OF ACTION. Live dump 2026-09-16. */
public class DiagnosisPlanOfActionScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'04 / YOUR PLAN OF ACTION')]")
    private WebElement stepLabel;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Buy Term Insurance']")
    private WebElement buyTerm;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Reassess']")
    private WebElement reassess;

    public DiagnosisPlanOfActionScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("YOUR PLAN OF ACTION");
    }
}
