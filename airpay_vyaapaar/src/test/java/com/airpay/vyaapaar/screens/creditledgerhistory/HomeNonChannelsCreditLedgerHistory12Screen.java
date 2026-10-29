package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History/Filter Icon/Show More/Sort/Filter Icon/Select date range. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistory12Screen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history-dropdown-filter-icon-show-more-sort-filter-icon-se";

    public static final String SELECT_DATE_RANGE = "//android.widget.TextView[contains(@content-desc,'Select date range')]";

    @AndroidFindBy(xpath = SELECT_DATE_RANGE)
    private WebElement selectDateRange;

    public static final String SWITCH_TO_CALENDAR_INPUT_MODE = "//android.view.View[contains(@content-desc,'Switch to calendar input mode')]";

    @AndroidFindBy(xpath = SWITCH_TO_CALENDAR_INPUT_MODE)
    private WebElement switchToCalendarInputMode;

    public static final String START_DATE_DD_MM_YYYY = "//android.widget.TextView[contains(@content-desc,'Start date, DD/MM/YYYY')]";

    @AndroidFindBy(xpath = START_DATE_DD_MM_YYYY)
    private WebElement startDateDdMmYyyy;

    public static final String END_DATE_DD_MM_YYYY = "//android.widget.TextView[contains(@content-desc,'End date, DD/MM/YYYY')]";

    @AndroidFindBy(xpath = END_DATE_DD_MM_YYYY)
    private WebElement endDateDdMmYyyy;

    public static final String CANCEL = "//android.widget.TextView[contains(@content-desc,'Cancel')]";

    @AndroidFindBy(xpath = CANCEL)
    private WebElement cancel;

    public static final String OK = "//android.widget.TextView[contains(@content-desc,'OK')]";

    @AndroidFindBy(xpath = OK)
    private WebElement ok;

    public HomeNonChannelsCreditLedgerHistory12Screen(TestContext context) {
        super(context);
    }
}
