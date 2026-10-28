package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / Suryoday — Alert. Module cms. */
public class HomeCreditBalanceCmsCashDrop3Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-suryoday";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String SOMETHING_WENT_WRONG = "//android.widget.TextView[@text='Something went wrong']";

    @AndroidFindBy(xpath = SOMETHING_WENT_WRONG)
    private WebElement somethingWentWrong;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public HomeCreditBalanceCmsCashDrop3Screen(TestContext context) {
        super(context);
    }
}
