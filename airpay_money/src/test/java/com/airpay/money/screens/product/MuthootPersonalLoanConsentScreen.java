package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Muthoot FinCorp consent shown after Personal Loan Apply Now.
 * Dump {@code uidump-apply-now} and {@code uidump-apply-pl-checked} 2.1.3.
 * Apply for Loan stays disabled until both checkboxes are checked.
 */
public class MuthootPersonalLoanConsentScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Muthoot FinCorp - Personal loan']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.CheckBox[@resource-id='mfl-terms-consent']")
    private WebElement termsConsent;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Muthoot Fincorp Privacy Policy (opens in new tab)']")
    private WebElement privacyPolicy;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Muthoot Fincorp Terms and Conditions PDF (opens in new tab)']")
    private WebElement termsPdf;

    @AndroidFindBy(xpath = "//android.widget.CheckBox[@resource-id='mfl-director-declaration']")
    private WebElement directorDeclaration;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Apply for Loan']")
    private WebElement applyForLoan;

    public MuthootPersonalLoanConsentScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Muthoot FinCorp - Personal loan") && sourceContains("Apply for Loan");
    }
}
