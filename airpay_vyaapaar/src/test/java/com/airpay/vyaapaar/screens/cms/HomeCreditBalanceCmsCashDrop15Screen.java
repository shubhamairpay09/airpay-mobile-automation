package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / TW / Cash Pickup / IS — New. Module cms. */
public class HomeCreditBalanceCmsCashDrop15Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-tw-cash-pickup-is";

    public static final String EL_NEW = "//android.widget.TextView[@text='New']";

    @AndroidFindBy(xpath = EL_NEW)
    private WebElement elNew;

    public static final String ACCEPTED = "//android.widget.TextView[@text='Accepted']";

    @AndroidFindBy(xpath = ACCEPTED)
    private WebElement accepted;

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public static final String CASH_PICKUP = "//android.widget.TextView[@text='Cash Pickup']";

    @AndroidFindBy(xpath = CASH_PICKUP)
    private WebElement cashPickup;

    public HomeCreditBalanceCmsCashDrop15Screen(TestContext context) {
        super(context);
    }
}
