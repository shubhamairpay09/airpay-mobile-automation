package com.airpay.vyaapaar.screens.aeps;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar / CMS / Cash Drop / AC. Module aeps. */
public class HomeCreditBalanceAepsP2P27Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-aeps-p2-p2-open-p2-cms-cash-drop-ac";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String FAIL_NO_RECORDS = "//android.widget.TextView[@text='Fail: No Records']";

    @AndroidFindBy(xpath = FAIL_NO_RECORDS)
    private WebElement failNoRecords;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public static final String S = "//android.widget.TextView[@text='S']";

    @AndroidFindBy(xpath = S)
    private WebElement s;

    public static final String SWIGGY = "//android.widget.TextView[@text='Swiggy']";

    @AndroidFindBy(xpath = SWIGGY)
    private WebElement swiggy;

    public static final String CASH_DROP = "//android.widget.TextView[@text='Cash Drop']";

    @AndroidFindBy(xpath = CASH_DROP)
    private WebElement cashDrop;

    public HomeCreditBalanceAepsP2P27Screen(TestContext context) {
        super(context);
    }
}
