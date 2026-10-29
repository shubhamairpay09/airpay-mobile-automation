package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History/Filter Icon/Show More/Sort/Filter Icon. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistory10Screen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history-dropdown-filter-icon-show-more-sort-filter-icon";

    public static final String FILTER = "//android.widget.TextView[@text='Filter']";

    @AndroidFindBy(xpath = FILTER)
    private WebElement filter;

    public static final String CLEAR_ALL = "//android.widget.TextView[contains(@content-desc,'Clear All')]";

    @AndroidFindBy(xpath = CLEAR_ALL)
    private WebElement clearAll;

    public static final String SELECT_DATE = "//android.widget.TextView[@text='Select Date']";

    @AndroidFindBy(xpath = SELECT_DATE)
    private WebElement selectDate;

    public static final String APPLY = "//android.widget.TextView[@text='Apply']";

    @AndroidFindBy(xpath = APPLY)
    private WebElement apply;

    public HomeNonChannelsCreditLedgerHistory10Screen(TestContext context) {
        super(context);
    }
}
