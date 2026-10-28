package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / TW / Swiggy — Alert. Module cms. */
public class HomeCreditBalanceCmsCashDrop12Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-tw-swiggy";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String SOMETHING_WENT_WRONG_PLEASE_CONNECT_TO_CUSTOMER_CARE = "//android.widget.TextView[@text='Something went wrong! please connect to customer care for support.']";

    @AndroidFindBy(xpath = SOMETHING_WENT_WRONG_PLEASE_CONNECT_TO_CUSTOMER_CARE)
    private WebElement somethingWentWrongPleaseConnectToCustomerCare;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public HomeCreditBalanceCmsCashDrop12Screen(TestContext context) {
        super(context);
    }
}
