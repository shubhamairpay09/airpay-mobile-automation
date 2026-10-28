package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Burial / BN — New. Module cms. */
public class HomeCreditBalanceCmsCashBurial2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-burial-bn";

    public static final String EL_NEW = "//android.widget.TextView[@text='New']";

    @AndroidFindBy(xpath = EL_NEW)
    private WebElement elNew;

    public static final String ACCEPTED = "//android.widget.TextView[@text='Accepted']";

    @AndroidFindBy(xpath = ACCEPTED)
    private WebElement accepted;

    public static final String ALL = "//android.widget.TextView[@text='all']";

    @AndroidFindBy(xpath = ALL)
    private WebElement all;

    public static final String CASH_PICKUP = "//android.widget.TextView[@text='cash pickup']";

    @AndroidFindBy(xpath = CASH_PICKUP)
    private WebElement cashPickup;

    public static final String CASH_DROP = "//android.widget.TextView[@text='cash drop']";

    @AndroidFindBy(xpath = CASH_DROP)
    private WebElement cashDrop;

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public static final String CASH_BURIAL = "//android.widget.TextView[@text='Cash Burial']";

    @AndroidFindBy(xpath = CASH_BURIAL)
    private WebElement cashBurial;

    public HomeCreditBalanceCmsCashBurial2Screen(TestContext context) {
        super(context);
    }
}
