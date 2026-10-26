package com.airpay.vyaapaar.screens.history;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Withdrawal Limit / History / Filter Icon. Module history. */
public class HomeWithdrawalLimitHistoryDropdownFilterScreen extends BaseScreen {

    public static final String PAGE_ID = "home-withdrawal-limit-history-dropdown-filter-icon";

    public static final String FILTER = "//android.widget.TextView[@text='Filter']";

    @AndroidFindBy(xpath = FILTER)
    private WebElement filter;

    public static final String CLEAR_ALL = "//android.widget.TextView[@text='Clear All']";

    @AndroidFindBy(xpath = CLEAR_ALL)
    private WebElement clearAll;

    public static final String SELECT_DATE_RANGE = "//android.widget.TextView[@text='Select date range']";

    @AndroidFindBy(xpath = SELECT_DATE_RANGE)
    private WebElement selectDateRange;

    public static final String TRANSACTION_STATUS = "//android.widget.TextView[@text='Transaction Status']";

    @AndroidFindBy(xpath = TRANSACTION_STATUS)
    private WebElement transactionStatus;

    public static final String APPROVED = "//android.widget.TextView[@text='Approved']";

    @AndroidFindBy(xpath = APPROVED)
    private WebElement approved;

    public static final String COMPLETED = "//android.widget.TextView[@text='Completed']";

    @AndroidFindBy(xpath = COMPLETED)
    private WebElement completed;

    public static final String PENDING = "//android.widget.TextView[@text='Pending']";

    @AndroidFindBy(xpath = PENDING)
    private WebElement pending;

    public static final String REJECTED = "//android.widget.TextView[@text='Rejected']";

    @AndroidFindBy(xpath = REJECTED)
    private WebElement rejected;

    public static final String INITIATED = "//android.widget.TextView[@text='Initiated']";

    @AndroidFindBy(xpath = INITIATED)
    private WebElement initiated;

    public static final String UNDERPROCESSING = "//android.widget.TextView[@text='Underprocessing']";

    @AndroidFindBy(xpath = UNDERPROCESSING)
    private WebElement underprocessing;

    public static final String ALL = "//android.widget.TextView[@text='All']";

    @AndroidFindBy(xpath = ALL)
    private WebElement all;

    public static final String APPLY = "//android.widget.TextView[@text='Apply']";

    @AndroidFindBy(xpath = APPLY)
    private WebElement apply;

    public HomeWithdrawalLimitHistoryDropdownFilterScreen(TestContext context) {
        super(context);
    }
}
