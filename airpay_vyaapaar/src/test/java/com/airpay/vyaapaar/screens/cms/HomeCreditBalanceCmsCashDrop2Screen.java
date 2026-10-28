package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / AC — Alert. Module cms. */
public class HomeCreditBalanceCmsCashDrop2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-ac";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String FAIL_NO_RECORDS = "//android.widget.TextView[@text='Fail: No Records']";

    @AndroidFindBy(xpath = FAIL_NO_RECORDS)
    private WebElement failNoRecords;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public HomeCreditBalanceCmsCashDrop2Screen(TestContext context) {
        super(context);
    }
}
