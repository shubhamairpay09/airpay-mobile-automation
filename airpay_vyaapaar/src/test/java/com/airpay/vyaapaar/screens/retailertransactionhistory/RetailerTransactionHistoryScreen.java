package com.airpay.vyaapaar.screens.retailertransactionhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Retailer Transaction History — Alert. Module retailer-transaction-history. */
public class RetailerTransactionHistoryScreen extends BaseScreen {

    public static final String PAGE_ID = "retailer-transaction-history";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String FAIL_NO_RECORDS = "//android.widget.TextView[@text='Fail: No Records']";

    @AndroidFindBy(xpath = FAIL_NO_RECORDS)
    private WebElement failNoRecords;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public RetailerTransactionHistoryScreen(TestContext context) {
        super(context);
    }
}
