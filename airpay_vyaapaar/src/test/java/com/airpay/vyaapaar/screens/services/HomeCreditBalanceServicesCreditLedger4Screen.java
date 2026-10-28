package com.airpay.vyaapaar.screens.services;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Services / Credit Ledger History / Filter Icon / Show More / GST / Apply — Credit Balance. Module services. */
public class HomeCreditBalanceServicesCreditLedger4Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-services-credit-ledger-history-dropdown-filter-icon-show-more-dropdown";

    public static final String FILTER = "//android.widget.TextView[@text='Filter']";

    @AndroidFindBy(xpath = FILTER)
    private WebElement filter;

    public static final String CLEAR_ALL = "//android.widget.TextView[@text='Clear All']";

    @AndroidFindBy(xpath = CLEAR_ALL)
    private WebElement clearAll;

    public static final String ALL = "//android.widget.EditText[@text='ALL']";

    @AndroidFindBy(xpath = ALL)
    private WebElement all;

    public static final String SELECT_TRANSACTION_MODE = "//android.widget.TextView[@text='Select Transaction Mode']";

    @AndroidFindBy(xpath = SELECT_TRANSACTION_MODE)
    private WebElement selectTransactionMode;

    public static final String SELECT_DATE_RANGE = "//android.widget.TextView[@text='Select date range']";

    @AndroidFindBy(xpath = SELECT_DATE_RANGE)
    private WebElement selectDateRange;

    public static final String TRANSACTION_ID = "//android.widget.TextView[@text='Transaction ID']";

    @AndroidFindBy(xpath = TRANSACTION_ID)
    private WebElement transactionId;

    public static final String SELECT_TRANSACTION_TYPE = "//android.widget.TextView[@text='Select Transaction Type']";

    @AndroidFindBy(xpath = SELECT_TRANSACTION_TYPE)
    private WebElement selectTransactionType;

    public static final String SHOW_MORE = "//android.widget.TextView[@text='Show More']";

    @AndroidFindBy(xpath = SHOW_MORE)
    private WebElement showMore;

    public static final String SHOW_MORE_ICON = "//android.view.View[contains(@content-desc,'Show More icon')]";

    @AndroidFindBy(xpath = SHOW_MORE_ICON)
    private WebElement showMoreIcon;

    public static final String GST = "//android.widget.TextView[@text='GST']";

    @AndroidFindBy(xpath = GST)
    private WebElement gst;

    public static final String NPS = "//android.widget.TextView[@text='NPS']";

    @AndroidFindBy(xpath = NPS)
    private WebElement nps;

    public static final String POS = "//android.widget.TextView[@text='POS']";

    @AndroidFindBy(xpath = POS)
    private WebElement pos;

    public static final String TDS = "//android.widget.TextView[@text='TDS']";

    @AndroidFindBy(xpath = TDS)
    private WebElement tds;

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

    public static final String ADHOC = "//android.widget.TextView[@text='ADHOC']";

    @AndroidFindBy(xpath = ADHOC)
    private WebElement adhoc;

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

    public static final String RECHARGE = "//android.widget.TextView[@text='RECHARGE']";

    @AndroidFindBy(xpath = RECHARGE)
    private WebElement recharge;

    public static final String REFUNDED = "//android.widget.TextView[@text='REFUNDED']";

    @AndroidFindBy(xpath = REFUNDED)
    private WebElement refunded;

    public static final String TRANSFER = "//android.widget.TextView[@text='TRANSFER']";

    @AndroidFindBy(xpath = TRANSFER)
    private WebElement transfer;

    public static final String HUMAN_ATM = "//android.widget.TextView[@text='HUMAN ATM']";

    @AndroidFindBy(xpath = HUMAN_ATM)
    private WebElement humanAtm;

    public static final String INSURANCE = "//android.widget.TextView[@text='INSURANCE']";

    @AndroidFindBy(xpath = INSURANCE)
    private WebElement insurance;

    public static final String CHARGEBACK = "//android.widget.TextView[@text='CHARGEBACK']";

    @AndroidFindBy(xpath = CHARGEBACK)
    private WebElement chargeback;

    public static final String APPLY = "//android.widget.TextView[@text='Apply']";

    @AndroidFindBy(xpath = APPLY)
    private WebElement apply;

    public static final String CREDIT_BALANCE = "//android.widget.TextView[@text='Credit Balance']";

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

    public static final String CREDIT_LEDGER_HISTORY = "//android.widget.TextView[@text='Credit Ledger History']";

    @AndroidFindBy(xpath = CREDIT_LEDGER_HISTORY)
    private WebElement creditLedgerHistory;

    public HomeCreditBalanceServicesCreditLedger4Screen(TestContext context) {
        super(context);
    }
}
