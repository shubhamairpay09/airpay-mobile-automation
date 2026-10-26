package com.airpay.vyaapaar.screens.history;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Withdrawal Limit / History — No records found. Module history. */
public class HomeWithdrawalLimitHistoryScreen extends BaseScreen {

    public static final String PAGE_ID = "home-withdrawal-limit-history";

    public static final String FILTER_ICON = "//android.view.View[contains(@content-desc,'Filter Icon')]";

    @AndroidFindBy(xpath = FILTER_ICON)
    private WebElement filterIcon;

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public static final String WITHDRAWAL_HISTORY = "//android.widget.TextView[@text='Withdrawal History']";

    @AndroidFindBy(xpath = WITHDRAWAL_HISTORY)
    private WebElement withdrawalHistory;

    public HomeWithdrawalLimitHistoryScreen(TestContext context) {
        super(context);
    }
}
