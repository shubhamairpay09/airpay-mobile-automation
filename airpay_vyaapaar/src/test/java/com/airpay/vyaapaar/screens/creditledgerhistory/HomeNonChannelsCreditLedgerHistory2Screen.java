package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History/Filter Icon. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistory2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history-dropdown-filter-icon";

    public static final String FILTER = "//android.widget.TextView[@text='Filter']";

    @AndroidFindBy(xpath = FILTER)
    private WebElement filter;

    public static final String CLEAR_ALL = "//android.widget.TextView[contains(@content-desc,'Clear All')]";

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

    public static final String TRANSACTION_ID = "//android.view.View[contains(@content-desc,'Transaction ID')]";

    @AndroidFindBy(xpath = TRANSACTION_ID)
    private WebElement transactionId;

    public static final String SELECT_TRANSACTION_TYPE = "//android.widget.TextView[contains(@content-desc,'Select Transaction Type')]";

    @AndroidFindBy(xpath = SELECT_TRANSACTION_TYPE)
    private WebElement selectTransactionType;

    public static final String SHOW_MORE = "//android.widget.TextView[contains(@content-desc,'Show More')]";

    @AndroidFindBy(xpath = SHOW_MORE)
    private WebElement showMore;

    public static final String SHOW_MORE_ICON = "//android.view.View[contains(@content-desc,'Show More icon')]";

    @AndroidFindBy(xpath = SHOW_MORE_ICON)
    private WebElement showMoreIcon;

    public static final String GST = "//android.widget.TextView[contains(@content-desc,'GST')]";

    @AndroidFindBy(xpath = GST)
    private WebElement gst;

    public static final String NPS = "//android.widget.TextView[contains(@content-desc,'NPS')]";

    @AndroidFindBy(xpath = NPS)
    private WebElement nps;

    public static final String POS = "//android.widget.TextView[contains(@content-desc,'POS')]";

    @AndroidFindBy(xpath = POS)
    private WebElement pos;

    public static final String TDS = "//android.widget.TextView[contains(@content-desc,'TDS')]";

    @AndroidFindBy(xpath = TDS)
    private WebElement tds;

    public static final String AEPS = "//android.widget.TextView[contains(@content-desc,'AEPS')]";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String BBPS = "//android.widget.TextView[contains(@content-desc,'BBPS')]";

    @AndroidFindBy(xpath = BBPS)
    private WebElement bbps;

    public static final String GOLD = "//android.widget.TextView[contains(@content-desc,'GOLD')]";

    @AndroidFindBy(xpath = GOLD)
    private WebElement gold;

    public static final String ONDC = "//android.widget.TextView[contains(@content-desc,'ONDC')]";

    @AndroidFindBy(xpath = ONDC)
    private WebElement ondc;

    public static final String ADHOC = "//android.widget.TextView[contains(@content-desc,'ADHOC')]";

    @AndroidFindBy(xpath = ADHOC)
    private WebElement adhoc;

    public static final String TOPUP = "//android.widget.TextView[contains(@content-desc,'TOPUP')]";

    @AndroidFindBy(xpath = TOPUP)
    private WebElement topup;

    public static final String FASTAG = "//android.widget.TextView[contains(@content-desc,'FASTAG')]";

    @AndroidFindBy(xpath = FASTAG)
    private WebElement fastag;

    public static final String BAAZAAR = "//android.widget.TextView[contains(@content-desc,'BAAZAAR')]";

    @AndroidFindBy(xpath = BAAZAAR)
    private WebElement baazaar;

    public static final String GIFTCARD = "//android.widget.TextView[contains(@content-desc,'GIFTCARD')]";

    @AndroidFindBy(xpath = GIFTCARD)
    private WebElement giftcard;

    public static final String RECHARGE = "//android.widget.TextView[contains(@content-desc,'RECHARGE')]";

    @AndroidFindBy(xpath = RECHARGE)
    private WebElement recharge;

    public static final String REFUNDED = "//android.widget.TextView[contains(@content-desc,'REFUNDED')]";

    @AndroidFindBy(xpath = REFUNDED)
    private WebElement refunded;

    public static final String TRANSFER = "//android.widget.TextView[contains(@content-desc,'TRANSFER')]";

    @AndroidFindBy(xpath = TRANSFER)
    private WebElement transfer;

    public static final String HUMAN_ATM = "//android.widget.TextView[contains(@content-desc,'HUMAN ATM')]";

    @AndroidFindBy(xpath = HUMAN_ATM)
    private WebElement humanAtm;

    public static final String INSURANCE = "//android.widget.TextView[contains(@content-desc,'INSURANCE')]";

    @AndroidFindBy(xpath = INSURANCE)
    private WebElement insurance;

    public static final String CHARGEBACK = "//android.widget.TextView[contains(@content-desc,'CHARGEBACK')]";

    @AndroidFindBy(xpath = CHARGEBACK)
    private WebElement chargeback;

    public static final String APPLY = "//android.widget.TextView[@text='Apply']";

    @AndroidFindBy(xpath = APPLY)
    private WebElement apply;

    public HomeNonChannelsCreditLedgerHistory2Screen(TestContext context) {
        super(context);
    }
}
