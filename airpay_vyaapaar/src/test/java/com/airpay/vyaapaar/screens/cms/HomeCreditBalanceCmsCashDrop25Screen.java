package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / TW / Cash Burial / BN / cash pickup / AEPS — AEPS. Module cms. */
public class HomeCreditBalanceCmsCashDrop25Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-tw-cash-burial-bn-cash-pickup-aeps";

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public HomeCreditBalanceCmsCashDrop25Screen(TestContext context) {
        super(context);
    }
}
