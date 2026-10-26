package com.airpay.vyaapaar.screens.aepsp2;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** AEPS / P2 — Alert. Module aeps-p2. */
public class AepsP2Screen extends BaseScreen {

    public static final String PAGE_ID = "aeps-p2";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String DATA_VALIDATION_ERROR_SORRY_MERCHANT_ID_DOES_NOT = "//android.widget.TextView[@text='Data Validation Error - Sorry!!, Merchant id does not exist.']";

    @AndroidFindBy(xpath = DATA_VALIDATION_ERROR_SORRY_MERCHANT_ID_DOES_NOT)
    private WebElement dataValidationErrorSorryMerchantIdDoesNot;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public AepsP2Screen(TestContext context) {
        super(context);
    }
}
