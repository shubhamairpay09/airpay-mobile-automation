package com.airpay.vyaapaar.screens.retailertransactionhistorydismissfiltericon;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Retailer Transaction History / Dismiss / Filter Icon — Filter. Module retailer-transaction-history-dismiss-filter-icon. */
public class RetailerTransactionHistoryDismissFilterIconScreen extends BaseScreen {

    public static final String PAGE_ID = "retailer-transaction-history-dismiss-filter-icon";

    public static final String FILTER = "//android.widget.TextView[@text='Filter']";

    @AndroidFindBy(xpath = FILTER)
    private WebElement filter;

    public static final String CLEAR_ALL = "//android.widget.TextView[@text='Clear All']";

    @AndroidFindBy(xpath = CLEAR_ALL)
    private WebElement clearAll;

    public static final String SELECT_DATE = "//android.widget.TextView[@text='Select Date']";

    @AndroidFindBy(xpath = SELECT_DATE)
    private WebElement selectDate;

    public static final String APPLY = "//android.widget.TextView[@text='Apply']";

    @AndroidFindBy(xpath = APPLY)
    private WebElement apply;

    public RetailerTransactionHistoryDismissFilterIconScreen(TestContext context) {
        super(context);
    }
}
