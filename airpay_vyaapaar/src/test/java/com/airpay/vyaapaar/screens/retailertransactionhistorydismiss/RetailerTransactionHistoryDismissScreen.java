package com.airpay.vyaapaar.screens.retailertransactionhistorydismiss;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Retailer Transaction History / Dismiss — No records found. Module retailer-transaction-history-dismiss. */
public class RetailerTransactionHistoryDismissScreen extends BaseScreen {

    public static final String PAGE_ID = "retailer-transaction-history-dismiss";

    public static final String FILTER_ICON = "//android.view.View[contains(@content-desc,'Filter Icon')]";

    @AndroidFindBy(xpath = FILTER_ICON)
    private WebElement filterIcon;

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public static final String RETAILER_HISTORY = "//android.widget.TextView[@text='Retailer History']";

    @AndroidFindBy(xpath = RETAILER_HISTORY)
    private WebElement retailerHistory;

    public RetailerTransactionHistoryDismissScreen(TestContext context) {
        super(context);
    }
}
