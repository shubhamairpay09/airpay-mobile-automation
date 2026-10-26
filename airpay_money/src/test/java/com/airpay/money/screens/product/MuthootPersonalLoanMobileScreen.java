package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Muthoot mobile step after Apply for Loan.
 * Dump {@code uidump-apply-pl-after} and {@code uidump-apply-pl-mobile} 2.1.3.
 * Stop after the number is entered. Do not request OTP.
 */
public class MuthootPersonalLoanMobileScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Muthoot FinCorp - Personal loan']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Enter mobile no.']")
    private WebElement mobileLabel;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Enter mobile no.']/following-sibling::android.widget.EditText")
    private WebElement mobileField;

    public MuthootPersonalLoanMobileScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Enter mobile no.");
    }
}
