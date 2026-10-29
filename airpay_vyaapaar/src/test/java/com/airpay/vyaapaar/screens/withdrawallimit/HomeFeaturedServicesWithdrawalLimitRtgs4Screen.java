package com.airpay.vyaapaar.screens.withdrawallimit;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/Withdrawal Limit/RTGS/Transfer to Distributor/RTGS. Module withdrawal-limit. */
public class HomeFeaturedServicesWithdrawalLimitRtgs4Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-withdrawal-limit-rtgs-transfer-to-distributor-rtgs-rtgs";

    public static final String FILTER = "//android.widget.TextView[@text='Filter']";

    @AndroidFindBy(xpath = FILTER)
    private WebElement filter;

    public static final String CLEAR_ALL = "//android.widget.TextView[contains(@content-desc,'Clear All')]";

    @AndroidFindBy(xpath = CLEAR_ALL)
    private WebElement clearAll;

    public static final String SELECT_DATE_RANGE = "//android.widget.TextView[@text='Select date range']";

    @AndroidFindBy(xpath = SELECT_DATE_RANGE)
    private WebElement selectDateRange;

    public static final String TRANSACTION_STATUS = "//android.widget.TextView[contains(@content-desc,'Transaction Status')]";

    @AndroidFindBy(xpath = TRANSACTION_STATUS)
    private WebElement transactionStatus;

    public static final String APPROVED = "//android.widget.TextView[contains(@content-desc,'Approved')]";

    @AndroidFindBy(xpath = APPROVED)
    private WebElement approved;

    public static final String COMPLETED = "//android.widget.TextView[contains(@content-desc,'Completed')]";

    @AndroidFindBy(xpath = COMPLETED)
    private WebElement completed;

    public static final String PENDING = "//android.widget.TextView[contains(@content-desc,'Pending')]";

    @AndroidFindBy(xpath = PENDING)
    private WebElement pending;

    public static final String REJECTED = "//android.widget.TextView[contains(@content-desc,'Rejected')]";

    @AndroidFindBy(xpath = REJECTED)
    private WebElement rejected;

    public static final String INITIATED = "//android.widget.TextView[contains(@content-desc,'Initiated')]";

    @AndroidFindBy(xpath = INITIATED)
    private WebElement initiated;

    public static final String UNDERPROCESSING = "//android.widget.TextView[contains(@content-desc,'Underprocessing')]";

    @AndroidFindBy(xpath = UNDERPROCESSING)
    private WebElement underprocessing;

    public static final String ALL = "//android.widget.TextView[contains(@content-desc,'All')]";

    @AndroidFindBy(xpath = ALL)
    private WebElement all;

    public static final String APPLY = "//android.widget.TextView[@text='Apply']";

    @AndroidFindBy(xpath = APPLY)
    private WebElement apply;

    public HomeFeaturedServicesWithdrawalLimitRtgs4Screen(TestContext context) {
        super(context);
    }
}
