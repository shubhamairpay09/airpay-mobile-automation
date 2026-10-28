package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History/Filter Icon/Apply. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistory10Screen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history-dropdown-filter-icon-filter-icon-apply";

    public static final String FILTER_ICON = "//android.view.View[contains(@content-desc,'Filter Icon')]";

    @AndroidFindBy(xpath = FILTER_ICON)
    private WebElement filterIcon;

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public static final String RETAILER_HISTORY = "//android.widget.TextView[@text='Retailer History']";

    @AndroidFindBy(xpath = RETAILER_HISTORY)
    private WebElement retailerHistory;

    public HomeNonChannelsCreditLedgerHistory10Screen(TestContext context) {
        super(context);
    }
}
