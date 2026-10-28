package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / TW / Cash Burial / BN / cash pickup / Reject — Reject Request. Module cms. */
public class HomeCreditBalanceCmsCashDrop20Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-tw-cash-burial-bn-cash-pickup-reject";

    public static final String REJECT_REQUEST = "//android.widget.TextView[@text='Reject Request']";

    @AndroidFindBy(xpath = REJECT_REQUEST)
    private WebElement rejectRequest;

    public static final String DO_YOU_WANT_TO_REJECT_THIS_REQUEST = "//android.widget.TextView[@text='Do you want to reject this request?']";

    @AndroidFindBy(xpath = DO_YOU_WANT_TO_REJECT_THIS_REQUEST)
    private WebElement doYouWantToRejectThisRequest;

    public static final String NO = "//android.widget.TextView[@text='No']";

    @AndroidFindBy(xpath = NO)
    private WebElement no;

    public static final String YES = "//android.widget.TextView[@text='Yes']";

    @AndroidFindBy(xpath = YES)
    private WebElement yes;

    public HomeCreditBalanceCmsCashDrop20Screen(TestContext context) {
        super(context);
    }
}
