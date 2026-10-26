package com.airpay.vyaapaar.screens.upiqrdismiss;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** UPI QR / Dismiss — Collect Money. Module upi-qr-dismiss. */
public class UpiQrDismissScreen extends BaseScreen {

    public static final String PAGE_ID = "upi-qr-dismiss";

    public static final String DASHBOARD_ICON = "//android.view.View[contains(@content-desc,'Dashboard icon')]";

    @AndroidFindBy(xpath = DASHBOARD_ICON)
    private WebElement dashboardIcon;

    public static final String DASHBOARD = "//android.widget.TextView[@text='Dashboard']";

    @AndroidFindBy(xpath = DASHBOARD)
    private WebElement dashboard;

    public static final String WITHDRAWAL_LIMIT_ICON = "//android.view.View[contains(@content-desc,'Withdrawal Limit icon')]";

    @AndroidFindBy(xpath = WITHDRAWAL_LIMIT_ICON)
    private WebElement withdrawalLimitIcon;

    public static final String WITHDRAWAL_LIMIT = "//android.widget.TextView[@text='Withdrawal Limit']";

    @AndroidFindBy(xpath = WITHDRAWAL_LIMIT)
    private WebElement withdrawalLimit;

    public static final String PREPAID_CARDS_ICON = "//android.view.View[contains(@content-desc,'Prepaid Cards icon')]";

    @AndroidFindBy(xpath = PREPAID_CARDS_ICON)
    private WebElement prepaidCardsIcon;

    public static final String PREPAID_CARDS = "//android.widget.TextView[@text='Prepaid Cards']";

    @AndroidFindBy(xpath = PREPAID_CARDS)
    private WebElement prepaidCards;

    public static final String MONEY_TRANSFER = "//android.widget.TextView[@text='Money Transfer']";

    @AndroidFindBy(xpath = MONEY_TRANSFER)
    private WebElement moneyTransfer;

    public static final String FINO_SUVIDHA_DMT_ICON = "//android.view.View[contains(@content-desc,'Fino Suvidha DMT icon')]";

    @AndroidFindBy(xpath = FINO_SUVIDHA_DMT_ICON)
    private WebElement finoSuvidhaDmtIcon;

    public static final String FINO_SUVIDHA_DMT = "//android.widget.TextView[@text='Fino Suvidha DMT']";

    @AndroidFindBy(xpath = FINO_SUVIDHA_DMT)
    private WebElement finoSuvidhaDmt;

    public static final String MONEY_TRANSFER_ICON = "//android.view.View[contains(@content-desc,'Money Transfer icon')]";

    @AndroidFindBy(xpath = MONEY_TRANSFER_ICON)
    private WebElement moneyTransferIcon;

    public static final String NON_CHANNELS = "//android.widget.TextView[@text='Non Channels']";

    @AndroidFindBy(xpath = NON_CHANNELS)
    private WebElement nonChannels;

    public static final String AEPS_ICON = "//android.view.View[contains(@content-desc,'AEPS icon')]";

    @AndroidFindBy(xpath = AEPS_ICON)
    private WebElement aepsIcon;

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String COLLECT_MONEY_ICON = "//android.view.View[contains(@content-desc,'Collect Money icon')]";

    @AndroidFindBy(xpath = COLLECT_MONEY_ICON)
    private WebElement collectMoneyIcon;

    public static final String COLLECT_MONEY = "//android.widget.TextView[@text='Collect Money']";

    @AndroidFindBy(xpath = COLLECT_MONEY)
    private WebElement collectMoney;

    public static final String BAAZAAR_ICON = "//android.view.View[contains(@content-desc,'Baazaar icon')]";

    @AndroidFindBy(xpath = BAAZAAR_ICON)
    private WebElement baazaarIcon;

    public static final String BAAZAAR = "//android.widget.TextView[@text='Baazaar']";

    @AndroidFindBy(xpath = BAAZAAR)
    private WebElement baazaar;

    public static final String UPI_QR_ICON = "//android.view.View[contains(@content-desc,'UPI QR icon')]";

    @AndroidFindBy(xpath = UPI_QR_ICON)
    private WebElement upiQrIcon;

    public static final String UPI_QR = "//android.widget.TextView[@text='UPI QR']";

    @AndroidFindBy(xpath = UPI_QR)
    private WebElement upiQr;

    public static final String POS_ACTIVATION_ICON = "//android.view.View[contains(@content-desc,'POS Activation icon')]";

    @AndroidFindBy(xpath = POS_ACTIVATION_ICON)
    private WebElement posActivationIcon;

    public static final String POS_ACTIVATION = "//android.widget.TextView[@text='POS Activation']";

    @AndroidFindBy(xpath = POS_ACTIVATION)
    private WebElement posActivation;

    public static final String CERTIFICATE_ICON = "//android.view.View[contains(@content-desc,'Certificate icon')]";

    @AndroidFindBy(xpath = CERTIFICATE_ICON)
    private WebElement certificateIcon;

    public static final String CERTIFICATE = "//android.widget.TextView[@text='Certificate']";

    @AndroidFindBy(xpath = CERTIFICATE)
    private WebElement certificate;

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

    public static final String CREDIT_LEDGER_HISTORY_ICON = "//android.view.View[contains(@content-desc,'Credit Ledger History icon')]";

    @AndroidFindBy(xpath = CREDIT_LEDGER_HISTORY_ICON)
    private WebElement creditLedgerHistoryIcon;

    public static final String CREDIT_LEDGER_HISTORY = "//android.widget.TextView[@text='Credit Ledger History']";

    @AndroidFindBy(xpath = CREDIT_LEDGER_HISTORY)
    private WebElement creditLedgerHistory;

    public static final String LOAN_LEAD_GENERATION_ICON = "//android.view.View[contains(@content-desc,'Loan Lead Generation icon')]";

    @AndroidFindBy(xpath = LOAN_LEAD_GENERATION_ICON)
    private WebElement loanLeadGenerationIcon;

    public static final String LOAN_LEAD_GENERATION = "//android.widget.TextView[@text='Loan Lead Generation']";

    @AndroidFindBy(xpath = LOAN_LEAD_GENERATION)
    private WebElement loanLeadGeneration;

    public static final String SUBSCRIPTION_ICON = "//android.view.View[contains(@content-desc,'Subscription icon')]";

    @AndroidFindBy(xpath = SUBSCRIPTION_ICON)
    private WebElement subscriptionIcon;

    public static final String SUBSCRIPTION = "//android.widget.TextView[@text='Subscription']";

    @AndroidFindBy(xpath = SUBSCRIPTION)
    private WebElement subscription;

    public static final String FINANCE_ICON = "//android.view.View[contains(@content-desc,'Finance icon')]";

    @AndroidFindBy(xpath = FINANCE_ICON)
    private WebElement financeIcon;

    public static final String FINANCE = "//android.widget.TextView[@text='Finance']";

    @AndroidFindBy(xpath = FINANCE)
    private WebElement finance;

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

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public UpiQrDismissScreen(TestContext context) {
        super(context);
    }
}
