package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance. Module home. */
public class HomeCreditBalanceScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance";

    public static final String CREDIT_BALANCE = "//android.widget.TextView[@text='Credit Balance']";

    @AndroidFindBy(xpath = CREDIT_BALANCE)
    private WebElement creditBalance;

    public static final String VISIBILITY_OFF_ICON = "//android.view.View[contains(@content-desc,'Visibility Off Icon')]";

    @AndroidFindBy(xpath = VISIBILITY_OFF_ICON)
    private WebElement visibilityOffIcon;

    public static final String ELEMENT = "//android.widget.TextView[@text='#####']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String ADD_CREDITS = "//android.widget.TextView[@text='Add Credits']";

    @AndroidFindBy(xpath = ADD_CREDITS)
    private WebElement addCredits;

    public static final String FEATURED_SERVICES = "//android.widget.TextView[@text='Featured Services']";

    @AndroidFindBy(xpath = FEATURED_SERVICES)
    private WebElement featuredServices;

    public static final String PAN_APPLICATION_ICON = "//android.view.View[contains(@content-desc,'PAN Application icon')]";

    @AndroidFindBy(xpath = PAN_APPLICATION_ICON)
    private WebElement panApplicationIcon;

    public static final String PAN_APPLICATION = "//android.widget.TextView[@text='PAN Application']";

    @AndroidFindBy(xpath = PAN_APPLICATION)
    private WebElement panApplication;

    public static final String POST_OFFICE_ICON = "//android.view.View[contains(@content-desc,'Post Office icon')]";

    @AndroidFindBy(xpath = POST_OFFICE_ICON)
    private WebElement postOfficeIcon;

    public static final String POST_OFFICE = "//android.widget.TextView[@text='Post Office']";

    @AndroidFindBy(xpath = POST_OFFICE)
    private WebElement postOffice;

    public static final String AEPS_ICON = "//android.view.View[contains(@content-desc,'AEPS icon')]";

    @AndroidFindBy(xpath = AEPS_ICON)
    private WebElement aepsIcon;

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String AADHAAR_PAY_ICON = "//android.view.View[contains(@content-desc,'Aadhaar Pay icon')]";

    @AndroidFindBy(xpath = AADHAAR_PAY_ICON)
    private WebElement aadhaarPayIcon;

    public static final String AADHAAR_PAY = "//android.widget.TextView[@text='Aadhaar Pay']";

    @AndroidFindBy(xpath = AADHAAR_PAY)
    private WebElement aadhaarPay;

    public static final String CMS_ICON = "//android.view.View[contains(@content-desc,'CMS icon')]";

    @AndroidFindBy(xpath = CMS_ICON)
    private WebElement cmsIcon;

    public static final String CMS = "//android.widget.TextView[@text='CMS']";

    @AndroidFindBy(xpath = CMS)
    private WebElement cms;

    public static final String KHATA_ICON = "//android.view.View[contains(@content-desc,'Khata icon')]";

    @AndroidFindBy(xpath = KHATA_ICON)
    private WebElement khataIcon;

    public static final String KHATA = "//android.widget.TextView[@text='Khata']";

    @AndroidFindBy(xpath = KHATA)
    private WebElement khata;

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

    public static final String COLLECT_MONEY_ICON = "//android.view.View[contains(@content-desc,'Collect Money icon')]";

    @AndroidFindBy(xpath = COLLECT_MONEY_ICON)
    private WebElement collectMoneyIcon;

    public static final String BAAZAAR_ICON = "//android.view.View[contains(@content-desc,'Baazaar icon')]";

    @AndroidFindBy(xpath = BAAZAAR_ICON)
    private WebElement baazaarIcon;

    public static final String COLLECT_MONEY = "//android.widget.TextView[@text='Collect Money']";

    @AndroidFindBy(xpath = COLLECT_MONEY)
    private WebElement collectMoney;

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

    public static final String ONDC_ICON = "//android.view.View[contains(@content-desc,'ONDC icon')]";

    @AndroidFindBy(xpath = ONDC_ICON)
    private WebElement ondcIcon;

    public static final String AGREEMENT_ICON = "//android.view.View[contains(@content-desc,'Agreement icon')]";

    @AndroidFindBy(xpath = AGREEMENT_ICON)
    private WebElement agreementIcon;

    public static final String RETAILER_TRANSACTION_HISTORY_ICON = "//android.view.View[contains(@content-desc,'Retailer Transaction History icon')]";

    @AndroidFindBy(xpath = RETAILER_TRANSACTION_HISTORY_ICON)
    private WebElement retailerTransactionHistoryIcon;

    public static final String ONDC = "//android.widget.TextView[@text='ONDC']";

    @AndroidFindBy(xpath = ONDC)
    private WebElement ondc;

    public static final String AGREEMENT = "//android.widget.TextView[@text='Agreement']";

    @AndroidFindBy(xpath = AGREEMENT)
    private WebElement agreement;

    public static final String RETAILER_TRANSACTION_HISTORY = "//android.widget.TextView[@text='Retailer Transaction History']";

    @AndroidFindBy(xpath = RETAILER_TRANSACTION_HISTORY)
    private WebElement retailerTransactionHistory;

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

    public static final String LOAN_LEAD_GENERATION_ICON = "//android.view.View[contains(@content-desc,'Loan Lead Generation icon')]";

    @AndroidFindBy(xpath = LOAN_LEAD_GENERATION_ICON)
    private WebElement loanLeadGenerationIcon;

    public static final String CREDIT_DEBIT_OTHERS_ICON = "//android.view.View[contains(@content-desc,'Credit/Debit/Others icon')]";

    @AndroidFindBy(xpath = CREDIT_DEBIT_OTHERS_ICON)
    private WebElement creditDebitOthersIcon;

    public static final String REQUEST_FROM_NETWORK = "//android.widget.TextView[@text='Request From Network']";

    @AndroidFindBy(xpath = REQUEST_FROM_NETWORK)
    private WebElement requestFromNetwork;

    public static final String LOAN_LEAD_GENERATION = "//android.widget.TextView[@text='Loan Lead Generation']";

    @AndroidFindBy(xpath = LOAN_LEAD_GENERATION)
    private WebElement loanLeadGeneration;

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

    public static final String FINANCE_ICON = "//android.view.View[contains(@content-desc,'Finance icon')]";

    @AndroidFindBy(xpath = FINANCE_ICON)
    private WebElement financeIcon;

    public static final String DEVOTIONAL_EXPERIENCES = "//android.widget.TextView[@text='Devotional Experiences']";

    @AndroidFindBy(xpath = DEVOTIONAL_EXPERIENCES)
    private WebElement devotionalExperiences;

    public static final String FINANCE = "//android.widget.TextView[@text='Finance']";

    @AndroidFindBy(xpath = FINANCE)
    private WebElement finance;

    public static final String RECHARGE_BILLPAY = "//android.widget.TextView[@text='Recharge & Billpay']";

    @AndroidFindBy(xpath = RECHARGE_BILLPAY)
    private WebElement rechargeBillpay;

    public static final String MOBILE_ICON = "//android.view.View[contains(@content-desc,'Mobile icon')]";

    @AndroidFindBy(xpath = MOBILE_ICON)
    private WebElement mobileIcon;

    public static final String MOBILE = "//android.widget.TextView[@text='Mobile']";

    @AndroidFindBy(xpath = MOBILE)
    private WebElement mobile;

    public static final String DTH_ICON = "//android.view.View[contains(@content-desc,'DTH icon')]";

    @AndroidFindBy(xpath = DTH_ICON)
    private WebElement dthIcon;

    public static final String DTH = "//android.widget.TextView[@text='DTH']";

    @AndroidFindBy(xpath = DTH)
    private WebElement dth;

    public static final String ELECTRICITY_ICON = "//android.view.View[contains(@content-desc,'Electricity icon')]";

    @AndroidFindBy(xpath = ELECTRICITY_ICON)
    private WebElement electricityIcon;

    public static final String ELECTRICITY = "//android.widget.TextView[@text='Electricity']";

    @AndroidFindBy(xpath = ELECTRICITY)
    private WebElement electricity;

    public static final String LIC_ICON = "//android.view.View[contains(@content-desc,'LIC icon')]";

    @AndroidFindBy(xpath = LIC_ICON)
    private WebElement licIcon;

    public static final String LIC = "//android.widget.TextView[@text='LIC']";

    @AndroidFindBy(xpath = LIC)
    private WebElement lic;

    public static final String OTHER_SERVICES = "//android.widget.TextView[@text='Other Services']";

    @AndroidFindBy(xpath = OTHER_SERVICES)
    private WebElement otherServices;

    public static final String GOVT_SERVICES_ICON = "//android.view.View[contains(@content-desc,'Govt Services icon')]";

    @AndroidFindBy(xpath = GOVT_SERVICES_ICON)
    private WebElement govtServicesIcon;

    public static final String GOVT_SERVICES = "//android.widget.TextView[@text='Govt Services']";

    @AndroidFindBy(xpath = GOVT_SERVICES)
    private WebElement govtServices;

    public HomeCreditBalanceScreen(TestContext context) {
        super(context);
    }
}
