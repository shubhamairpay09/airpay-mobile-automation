package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / AEPS / P2 / P2. Module home. */
public class HomeCreditBalanceOpenPanApplication10Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-open-pan-application-aeps-p2-p2";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String DATA_VALIDATION_ERROR_SORRY_MERCHANT_ID_DOES_NOT = "//android.widget.TextView[@text='Data Validation Error - Sorry!!, Merchant id does not exist.']";

    @AndroidFindBy(xpath = DATA_VALIDATION_ERROR_SORRY_MERCHANT_ID_DOES_NOT)
    private WebElement dataValidationErrorSorryMerchantIdDoesNot;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public static final String P1 = "//android.widget.TextView[@text='P1']";

    @AndroidFindBy(xpath = P1)
    private WebElement p1;

    public static final String NOT_VERIFIED = "//android.widget.TextView[@text='Not Verified']";

    @AndroidFindBy(xpath = NOT_VERIFIED)
    private WebElement notVerified;

    public static final String P2 = "//android.widget.TextView[@text='P2']";

    @AndroidFindBy(xpath = P2)
    private WebElement p2;

    public static final String NOT_ONBOARDED = "//android.widget.TextView[@text='Not Onboarded']";

    @AndroidFindBy(xpath = NOT_ONBOARDED)
    private WebElement notOnboarded;

    public static final String P3 = "//android.widget.TextView[@text='P3']";

    @AndroidFindBy(xpath = P3)
    private WebElement p3;

    public static final String SELECT_PARTNER_BANK = "//android.widget.TextView[@text='Select partner bank']";

    @AndroidFindBy(xpath = SELECT_PARTNER_BANK)
    private WebElement selectPartnerBank;

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public HomeCreditBalanceOpenPanApplication10Screen(TestContext context) {
        super(context);
    }
}
