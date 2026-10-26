package com.airpay.vyaapaar.screens.cashinonline;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** CashIn - Online — ONDC. Module cashin-online. */
public class CashinOnlineScreen extends BaseScreen {

    public static final String PAGE_ID = "cashin-online";

    public static final String ONDC_ICON = "//android.view.View[contains(@content-desc,'ONDC icon')]";

    @AndroidFindBy(xpath = ONDC_ICON)
    private WebElement ondcIcon;

    public static final String ONDC = "//android.widget.TextView[@text='ONDC']";

    @AndroidFindBy(xpath = ONDC)
    private WebElement ondc;

    public static final String AGREEMENT_ICON = "//android.view.View[contains(@content-desc,'Agreement icon')]";

    @AndroidFindBy(xpath = AGREEMENT_ICON)
    private WebElement agreementIcon;

    public static final String AGREEMENT = "//android.widget.TextView[@text='Agreement']";

    @AndroidFindBy(xpath = AGREEMENT)
    private WebElement agreement;

    public static final String RETAILER_TRANSACTION_HISTORY_ICON = "//android.view.View[contains(@content-desc,'Retailer Transaction History icon')]";

    @AndroidFindBy(xpath = RETAILER_TRANSACTION_HISTORY_ICON)
    private WebElement retailerTransactionHistoryIcon;

    public static final String RETAILER_TRANSACTION_HISTORY = "//android.widget.TextView[@text='Retailer Transaction History']";

    @AndroidFindBy(xpath = RETAILER_TRANSACTION_HISTORY)
    private WebElement retailerTransactionHistory;

    public static final String MONEY_TRANSFER_ICON = "//android.view.View[contains(@content-desc,'Money Transfer icon')]";

    @AndroidFindBy(xpath = MONEY_TRANSFER_ICON)
    private WebElement moneyTransferIcon;

    public static final String MONEY_TRANSFER = "//android.widget.TextView[@text='Money Transfer']";

    @AndroidFindBy(xpath = MONEY_TRANSFER)
    private WebElement moneyTransfer;

    public static final String CREDIT_LEDGER_HISTORY_ICON = "//android.view.View[contains(@content-desc,'Credit Ledger History icon')]";

    @AndroidFindBy(xpath = CREDIT_LEDGER_HISTORY_ICON)
    private WebElement creditLedgerHistoryIcon;

    public static final String CREDIT_LEDGER_HISTORY = "//android.widget.TextView[@text='Credit Ledger History']";

    @AndroidFindBy(xpath = CREDIT_LEDGER_HISTORY)
    private WebElement creditLedgerHistory;

    public static final String UPI_ICON = "//android.view.View[contains(@content-desc,'UPI icon')]";

    @AndroidFindBy(xpath = UPI_ICON)
    private WebElement upiIcon;

    public static final String UPI = "//android.widget.TextView[@text='UPI']";

    @AndroidFindBy(xpath = UPI)
    private WebElement upi;

    public static final String REQUEST_FROM_NETWORK_ICON = "//android.view.View[contains(@content-desc,'Request From Network icon')]";

    @AndroidFindBy(xpath = REQUEST_FROM_NETWORK_ICON)
    private WebElement requestFromNetworkIcon;

    public static final String REQUEST_FROM_NETWORK = "//android.widget.TextView[@text='Request From Network']";

    @AndroidFindBy(xpath = REQUEST_FROM_NETWORK)
    private WebElement requestFromNetwork;

    public static final String LOAN_LEAD_GENERATION_ICON = "//android.view.View[contains(@content-desc,'Loan Lead Generation icon')]";

    @AndroidFindBy(xpath = LOAN_LEAD_GENERATION_ICON)
    private WebElement loanLeadGenerationIcon;

    public static final String LOAN_LEAD_GENERATION = "//android.widget.TextView[@text='Loan Lead Generation']";

    @AndroidFindBy(xpath = LOAN_LEAD_GENERATION)
    private WebElement loanLeadGeneration;

    public static final String CREDIT_DEBIT_OTHERS_ICON = "//android.view.View[contains(@content-desc,'Credit/Debit/Others icon')]";

    @AndroidFindBy(xpath = CREDIT_DEBIT_OTHERS_ICON)
    private WebElement creditDebitOthersIcon;

    public static final String CREDIT_DEBIT_OTHERS = "//android.widget.TextView[@text='Credit/Debit/Others']";

    @AndroidFindBy(xpath = CREDIT_DEBIT_OTHERS)
    private WebElement creditDebitOthers;

    public static final String SUBSCRIPTION_ICON = "//android.view.View[contains(@content-desc,'Subscription icon')]";

    @AndroidFindBy(xpath = SUBSCRIPTION_ICON)
    private WebElement subscriptionIcon;

    public static final String SUBSCRIPTION = "//android.widget.TextView[@text='Subscription']";

    @AndroidFindBy(xpath = SUBSCRIPTION)
    private WebElement subscription;

    public static final String CASHIN_ONLINE_ICON = "//android.view.View[contains(@content-desc,'CashIn - Online icon')]";

    @AndroidFindBy(xpath = CASHIN_ONLINE_ICON)
    private WebElement cashinOnlineIcon;

    public static final String CASHIN_ONLINE = "//android.widget.TextView[@text='CashIn - Online']";

    @AndroidFindBy(xpath = CASHIN_ONLINE)
    private WebElement cashinOnline;

    public static final String LINK_MIDS_ICON = "//android.view.View[contains(@content-desc,'Link MIDs icon')]";

    @AndroidFindBy(xpath = LINK_MIDS_ICON)
    private WebElement linkMidsIcon;

    public static final String LINK_MIDS = "//android.widget.TextView[@text='Link MIDs']";

    @AndroidFindBy(xpath = LINK_MIDS)
    private WebElement linkMids;

    public static final String DEVOTIONAL_EXPERIENCES_ICON = "//android.view.View[contains(@content-desc,'Devotional Experiences icon')]";

    @AndroidFindBy(xpath = DEVOTIONAL_EXPERIENCES_ICON)
    private WebElement devotionalExperiencesIcon;

    public static final String DEVOTIONAL_EXPERIENCES = "//android.widget.TextView[@text='Devotional Experiences']";

    @AndroidFindBy(xpath = DEVOTIONAL_EXPERIENCES)
    private WebElement devotionalExperiences;

    public static final String FINANCE_ICON = "//android.view.View[contains(@content-desc,'Finance icon')]";

    @AndroidFindBy(xpath = FINANCE_ICON)
    private WebElement financeIcon;

    public static final String FINANCE = "//android.widget.TextView[@text='Finance']";

    @AndroidFindBy(xpath = FINANCE)
    private WebElement finance;

    public static final String NOTIFICATIONS = "//android.view.View[contains(@content-desc,'Notifications')]";

    @AndroidFindBy(xpath = NOTIFICATIONS)
    private WebElement notifications;

    public static final String HOME = "//android.widget.TextView[@text='Home']";

    @AndroidFindBy(xpath = HOME)
    private WebElement home;

    public static final String SERVICES = "//android.widget.TextView[@text='Services']";

    @AndroidFindBy(xpath = SERVICES)
    private WebElement services;

    public static final String HISTORY = "//android.widget.TextView[@text='History']";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String PROFILE = "//android.widget.TextView[@text='Profile']";

    @AndroidFindBy(xpath = PROFILE)
    private WebElement profile;

    public CashinOnlineScreen(TestContext context) {
        super(context);
    }
}
