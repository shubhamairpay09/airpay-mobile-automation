package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / CP — Alert. Module cms. */
public class HomeCreditBalanceCmsCashDrop10Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-cp";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String WE_ARE_UNABLE_TO_PROCEED_PLEASE_TRY_AGAIN = "//android.widget.TextView[@text='We are unable to proceed, please try again.']";

    @AndroidFindBy(xpath = WE_ARE_UNABLE_TO_PROCEED_PLEASE_TRY_AGAIN)
    private WebElement weAreUnableToProceedPleaseTryAgain;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public HomeCreditBalanceCmsCashDrop10Screen(TestContext context) {
        super(context);
    }
}
