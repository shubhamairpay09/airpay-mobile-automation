package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistoryScreen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history";

    public static final String CREDIT_BALANCE = "//android.widget.TextView[contains(@content-desc,'Credit Balance')]";

    @AndroidFindBy(xpath = CREDIT_BALANCE)
    private WebElement creditBalance;

    public static final String FILTER_ICON = "//android.view.View[contains(@content-desc,'Filter Icon')]";

    @AndroidFindBy(xpath = FILTER_ICON)
    private WebElement filterIcon;

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public static final String BACK_ICON = "//android.view.View[contains(@content-desc,'Back Icon')]";

    @AndroidFindBy(xpath = BACK_ICON)
    private WebElement backIcon;

    public static final String CREDIT_LEDGER_HISTORY = "//android.widget.TextView[contains(@content-desc,'Credit Ledger History')]";

    @AndroidFindBy(xpath = CREDIT_LEDGER_HISTORY)
    private WebElement creditLedgerHistory;

    public HomeNonChannelsCreditLedgerHistoryScreen(TestContext context) {
        super(context);
    }
}
