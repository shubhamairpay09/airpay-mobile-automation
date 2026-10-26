package com.airpay.vyaapaar.screens.aeps;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar / History / Filter Icon / Filter. Module aeps. */
public class HomeCreditBalanceAepsP2P217Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-aeps-p2-p2-open-p2-history-dropdown-filter-icon-dropdown-filter";

    public static final String FILTER = "//android.widget.TextView[@text='Filter']";

    @AndroidFindBy(xpath = FILTER)
    private WebElement filter;

    public static final String CLEAR_ALL = "//android.widget.TextView[@text='Clear All']";

    @AndroidFindBy(xpath = CLEAR_ALL)
    private WebElement clearAll;

    public static final String SELECT_DATE = "//android.widget.TextView[@text='Select Date']";

    @AndroidFindBy(xpath = SELECT_DATE)
    private WebElement selectDate;

    public static final String TRANSACTION_DETAILS = "//android.widget.TextView[@text='Transaction Details']";

    @AndroidFindBy(xpath = TRANSACTION_DETAILS)
    private WebElement transactionDetails;

    public static final String TRANSACTION_ID = "//android.widget.TextView[@text='Transaction ID']";

    @AndroidFindBy(xpath = TRANSACTION_ID)
    private WebElement transactionId;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String BENEFICIARY_NAME = "//android.widget.TextView[@text='Beneficiary Name']";

    @AndroidFindBy(xpath = BENEFICIARY_NAME)
    private WebElement beneficiaryName;

    public static final String BENEFICIARY_ACCOUNT = "//android.widget.TextView[@text='Beneficiary Account']";

    @AndroidFindBy(xpath = BENEFICIARY_ACCOUNT)
    private WebElement beneficiaryAccount;

    public static final String TRANSACTION_MODE = "//android.widget.TextView[@text='Transaction Mode']";

    @AndroidFindBy(xpath = TRANSACTION_MODE)
    private WebElement transactionMode;

    public static final String SHOW_MORE = "//android.widget.TextView[@text='Show More']";

    @AndroidFindBy(xpath = SHOW_MORE)
    private WebElement showMore;

    public static final String ICON = "//android.view.View[contains(@content-desc,'Icon')]";

    @AndroidFindBy(xpath = ICON)
    private WebElement icon;

    public static final String NPS = "//android.widget.TextView[@text='NPS']";

    @AndroidFindBy(xpath = NPS)
    private WebElement nps;

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String BBPS = "//android.widget.TextView[@text='BBPS']";

    @AndroidFindBy(xpath = BBPS)
    private WebElement bbps;

    public static final String GOLD = "//android.widget.TextView[@text='GOLD']";

    @AndroidFindBy(xpath = GOLD)
    private WebElement gold;

    public static final String ONDC = "//android.widget.TextView[@text='ONDC']";

    @AndroidFindBy(xpath = ONDC)
    private WebElement ondc;

    public static final String TOPUP = "//android.widget.TextView[@text='TOPUP']";

    @AndroidFindBy(xpath = TOPUP)
    private WebElement topup;

    public static final String FASTAG = "//android.widget.TextView[@text='FASTAG']";

    @AndroidFindBy(xpath = FASTAG)
    private WebElement fastag;

    public static final String BAAZAAR = "//android.widget.TextView[@text='BAAZAAR']";

    @AndroidFindBy(xpath = BAAZAAR)
    private WebElement baazaar;

    public static final String GIFTCARD = "//android.widget.TextView[@text='GIFTCARD']";

    @AndroidFindBy(xpath = GIFTCARD)
    private WebElement giftcard;

    public static final String APPLY = "//android.widget.TextView[@text='Apply']";

    @AndroidFindBy(xpath = APPLY)
    private WebElement apply;

    public static final String RECHARGE = "//android.widget.TextView[@text='RECHARGE']";

    @AndroidFindBy(xpath = RECHARGE)
    private WebElement recharge;

    public static final String TRANSACTION_STATUS = "//android.widget.TextView[@text='Transaction Status']";

    @AndroidFindBy(xpath = TRANSACTION_STATUS)
    private WebElement transactionStatus;

    public static final String SUCCESS = "//android.widget.TextView[@text='Success']";

    @AndroidFindBy(xpath = SUCCESS)
    private WebElement success;

    public static final String FAILED = "//android.widget.TextView[@text='Failed']";

    @AndroidFindBy(xpath = FAILED)
    private WebElement failed;

    public static final String PENDING = "//android.widget.TextView[@text='Pending']";

    @AndroidFindBy(xpath = PENDING)
    private WebElement pending;

    public static final String INITIATED = "//android.widget.TextView[@text='Initiated']";

    @AndroidFindBy(xpath = INITIATED)
    private WebElement initiated;

    public static final String PARTIAL_SUCCESS = "//android.widget.TextView[@text='Partial Success']";

    @AndroidFindBy(xpath = PARTIAL_SUCCESS)
    private WebElement partialSuccess;

    public static final String INITIATE_REFUND = "//android.widget.TextView[@text='Initiate Refund']";

    @AndroidFindBy(xpath = INITIATE_REFUND)
    private WebElement initiateRefund;

    public HomeCreditBalanceAepsP2P217Screen(TestContext context) {
        super(context);
    }
}
