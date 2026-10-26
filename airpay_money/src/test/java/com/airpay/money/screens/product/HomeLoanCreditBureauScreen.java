package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Shown when the bureau lookup fails. Dump {@code uidump-deep-35-eligibility-result} 2.1.3. */
public class HomeLoanCreditBureauScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Credit Bureau Issue']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'unable to fetch your credit details')]")
    private WebElement message;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Go to Dashboard']")
    private WebElement goToDashboard;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Retry Verification']")
    private WebElement retryVerification;

    public HomeLoanCreditBureauScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Credit Bureau Issue");
    }
}
