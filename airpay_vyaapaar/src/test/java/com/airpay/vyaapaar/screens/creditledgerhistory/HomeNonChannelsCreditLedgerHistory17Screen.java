package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History/Filter Icon/Retailer Transaction History/HATM. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistory17Screen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history-dropdown-filter-icon-retailer-transaction-history";

    public static final String UPI_QR_ICON = "//android.view.View[contains(@content-desc,'UPI QR icon')]";

    @AndroidFindBy(xpath = UPI_QR_ICON)
    private WebElement upiQrIcon;

    public static final String UPI_QR = "//android.widget.TextView[contains(@content-desc,'UPI QR')]";

    @AndroidFindBy(xpath = UPI_QR)
    private WebElement upiQr;

    public static final String POS_ACTIVATION = "//android.widget.TextView[contains(@content-desc,'POS Activation')]";

    @AndroidFindBy(xpath = POS_ACTIVATION)
    private WebElement posActivation;

    public static final String CERTIFICATE_ICON = "//android.view.View[contains(@content-desc,'Certificate icon')]";

    @AndroidFindBy(xpath = CERTIFICATE_ICON)
    private WebElement certificateIcon;

    public static final String CERTIFICATE = "//android.widget.TextView[contains(@content-desc,'Certificate')]";

    @AndroidFindBy(xpath = CERTIFICATE)
    private WebElement certificate;

    public static final String ONDC_ICON = "//android.view.View[contains(@content-desc,'ONDC icon')]";

    @AndroidFindBy(xpath = ONDC_ICON)
    private WebElement ondcIcon;

    public static final String ONDC = "//android.widget.TextView[contains(@content-desc,'ONDC')]";

    @AndroidFindBy(xpath = ONDC)
    private WebElement ondc;

    public static final String AGREEMENT_ICON = "//android.view.View[contains(@content-desc,'Agreement icon')]";

    @AndroidFindBy(xpath = AGREEMENT_ICON)
    private WebElement agreementIcon;

    public static final String AGREEMENT = "//android.widget.TextView[contains(@content-desc,'Agreement')]";

    @AndroidFindBy(xpath = AGREEMENT)
    private WebElement agreement;

    public static final String RETAILER_TRANSACTION_HISTORY_ICON = "//android.view.View[contains(@content-desc,'Retailer Transaction History icon')]";

    @AndroidFindBy(xpath = RETAILER_TRANSACTION_HISTORY_ICON)
    private WebElement retailerTransactionHistoryIcon;

    public static final String RETAILER_TRANSACTION_HISTORY = "//android.widget.TextView[contains(@content-desc,'Retailer Transaction History')]";

    @AndroidFindBy(xpath = RETAILER_TRANSACTION_HISTORY)
    private WebElement retailerTransactionHistory;

    public static final String MONEY_TRANSFER_ICON = "//android.view.View[contains(@content-desc,'Money Transfer icon')]";

    @AndroidFindBy(xpath = MONEY_TRANSFER_ICON)
    private WebElement moneyTransferIcon;

    public static final String MONEY_TRANSFER = "//android.widget.TextView[contains(@content-desc,'Money Transfer')]";

    @AndroidFindBy(xpath = MONEY_TRANSFER)
    private WebElement moneyTransfer;

    public static final String CREDIT_LEDGER_HISTORY_ICON = "//android.view.View[contains(@content-desc,'Credit Ledger History icon')]";

    @AndroidFindBy(xpath = CREDIT_LEDGER_HISTORY_ICON)
    private WebElement creditLedgerHistoryIcon;

    public static final String CREDIT_LEDGER_HISTORY = "//android.widget.TextView[contains(@content-desc,'Credit Ledger History')]";

    @AndroidFindBy(xpath = CREDIT_LEDGER_HISTORY)
    private WebElement creditLedgerHistory;

    public static final String UPI_ICON = "//android.view.View[contains(@content-desc,'UPI icon')]";

    @AndroidFindBy(xpath = UPI_ICON)
    private WebElement upiIcon;

    public static final String UPI = "//android.widget.TextView[contains(@content-desc,'UPI')]";

    @AndroidFindBy(xpath = UPI)
    private WebElement upi;

    public static final String REQUEST_FROM_NETWORK_ICON = "//android.view.View[contains(@content-desc,'Request From Network icon')]";

    @AndroidFindBy(xpath = REQUEST_FROM_NETWORK_ICON)
    private WebElement requestFromNetworkIcon;

    public static final String REQUEST_FROM_NETWORK = "//android.widget.TextView[contains(@content-desc,'Request From Network')]";

    @AndroidFindBy(xpath = REQUEST_FROM_NETWORK)
    private WebElement requestFromNetwork;

    public static final String LOAN_LEAD_GENERATION_ICON = "//android.view.View[contains(@content-desc,'Loan Lead Generation icon')]";

    @AndroidFindBy(xpath = LOAN_LEAD_GENERATION_ICON)
    private WebElement loanLeadGenerationIcon;

    public static final String LOAN_LEAD_GENERATION = "//android.widget.TextView[contains(@content-desc,'Loan Lead Generation')]";

    @AndroidFindBy(xpath = LOAN_LEAD_GENERATION)
    private WebElement loanLeadGeneration;

    public static final String CREDIT_DEBIT_OTHERS_ICON = "//android.view.View[contains(@content-desc,'Credit/Debit/Others icon')]";

    @AndroidFindBy(xpath = CREDIT_DEBIT_OTHERS_ICON)
    private WebElement creditDebitOthersIcon;

    public static final String CREDIT_DEBIT_OTHERS = "//android.widget.TextView[contains(@content-desc,'Credit/Debit/Others')]";

    @AndroidFindBy(xpath = CREDIT_DEBIT_OTHERS)
    private WebElement creditDebitOthers;

    public static final String SUBSCRIPTION_ICON = "//android.view.View[contains(@content-desc,'Subscription icon')]";

    @AndroidFindBy(xpath = SUBSCRIPTION_ICON)
    private WebElement subscriptionIcon;

    public static final String SUBSCRIPTION = "//android.widget.TextView[contains(@content-desc,'Subscription')]";

    @AndroidFindBy(xpath = SUBSCRIPTION)
    private WebElement subscription;

    public static final String CASHIN_ONLINE_ICON = "//android.view.View[contains(@content-desc,'CashIn - Online icon')]";

    @AndroidFindBy(xpath = CASHIN_ONLINE_ICON)
    private WebElement cashinOnlineIcon;

    public static final String CASHIN_ONLINE = "//android.widget.TextView[contains(@content-desc,'CashIn - Online')]";

    @AndroidFindBy(xpath = CASHIN_ONLINE)
    private WebElement cashinOnline;

    public static final String LINK_MIDS_ICON = "//android.view.View[contains(@content-desc,'Link MIDs icon')]";

    @AndroidFindBy(xpath = LINK_MIDS_ICON)
    private WebElement linkMidsIcon;

    public static final String LINK_MIDS = "//android.widget.TextView[contains(@content-desc,'Link MIDs')]";

    @AndroidFindBy(xpath = LINK_MIDS)
    private WebElement linkMids;

    public static final String NOTIFICATIONS = "//android.view.View[contains(@content-desc,'Notifications')]";

    @AndroidFindBy(xpath = NOTIFICATIONS)
    private WebElement notifications;

    public static final String HOME = "//android.widget.TextView[contains(@content-desc,'Home')]";

    @AndroidFindBy(xpath = HOME)
    private WebElement home;

    public static final String SERVICES = "//android.widget.TextView[contains(@content-desc,'Services')]";

    @AndroidFindBy(xpath = SERVICES)
    private WebElement services;

    public static final String HISTORY = "//android.widget.TextView[contains(@content-desc,'History')]";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String PROFILE = "//android.widget.TextView[contains(@content-desc,'Profile')]";

    @AndroidFindBy(xpath = PROFILE)
    private WebElement profile;

    public static final String POWER_UP_YOUR_EARNINGS_SELECT_THE_PRODUCT_YOU = "//android.widget.TextView[contains(@content-desc,'Power up your earnings! Select the product you want to subscribe to.')]";

    @AndroidFindBy(xpath = POWER_UP_YOUR_EARNINGS_SELECT_THE_PRODUCT_YOU)
    private WebElement powerUpYourEarningsSelectTheProductYou;

    public static final String AEPS_ICON = "//android.view.View[contains(@content-desc,'AEPS icon')]";

    @AndroidFindBy(xpath = AEPS_ICON)
    private WebElement aepsIcon;

    public static final String AEPS = "//android.widget.TextView[contains(@content-desc,'AEPS')]";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String HATM_ICON = "//android.view.View[contains(@content-desc,'HATM icon')]";

    @AndroidFindBy(xpath = HATM_ICON)
    private WebElement hatmIcon;

    public static final String HATM = "//android.widget.TextView[contains(@content-desc,'HATM')]";

    @AndroidFindBy(xpath = HATM)
    private WebElement hatm;

    public static final String DIGIKHATA_ICON = "//android.view.View[contains(@content-desc,'DIGIKHATA icon')]";

    @AndroidFindBy(xpath = DIGIKHATA_ICON)
    private WebElement digikhataIcon;

    public static final String DIGIKHATA = "//android.widget.TextView[contains(@content-desc,'DIGIKHATA')]";

    @AndroidFindBy(xpath = DIGIKHATA)
    private WebElement digikhata;

    public static final String DMT_ICON = "//android.view.View[contains(@content-desc,'DMT icon')]";

    @AndroidFindBy(xpath = DMT_ICON)
    private WebElement dmtIcon;

    public static final String DMT = "//android.widget.TextView[contains(@content-desc,'DMT')]";

    @AndroidFindBy(xpath = DMT)
    private WebElement dmt;

    public static final String CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU = "//android.widget.TextView[contains(@content-desc,'Choose the plan that works for you!')]";

    @AndroidFindBy(xpath = CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU)
    private WebElement chooseThePlanThatWorksForYou;

    public static final String EL3MONTHS = "//android.widget.TextView[contains(@content-desc,'3 Months')]";

    @AndroidFindBy(xpath = EL3MONTHS)
    private WebElement el3Months;

    public static final String EL600 = "//android.widget.TextView[contains(@content-desc,'₹600')]";

    @AndroidFindBy(xpath = EL600)
    private WebElement el600;

    public static final String HATM03MONTH = "//android.widget.TextView[contains(@content-desc,'hatm03month')]";

    @AndroidFindBy(xpath = HATM03MONTH)
    private WebElement hatm03month;

    public static final String DOWN_ARROW = "//android.view.View[contains(@content-desc,'Down arrow')]";

    @AndroidFindBy(xpath = DOWN_ARROW)
    private WebElement downArrow;

    public static final String EL12MONTHS = "//android.widget.TextView[contains(@content-desc,'12 Months')]";

    @AndroidFindBy(xpath = EL12MONTHS)
    private WebElement el12Months;

    public static final String EL800 = "//android.widget.TextView[contains(@content-desc,'₹800')]";

    @AndroidFindBy(xpath = EL800)
    private WebElement el800;

    public static final String EL5INCR_RTAEPS12MONTH = "//android.widget.TextView[contains(@content-desc,'5 % Incr RTAEPS12Month')]";

    @AndroidFindBy(xpath = EL5INCR_RTAEPS12MONTH)
    private WebElement el5IncrRtaeps12month;

    public static final String EL1MONTH = "//android.widget.TextView[contains(@content-desc,'1 Month')]";

    @AndroidFindBy(xpath = EL1MONTH)
    private WebElement el1Month;

    public static final String EL499 = "//android.widget.TextView[contains(@content-desc,'₹499')]";

    @AndroidFindBy(xpath = EL499)
    private WebElement el499;

    public static final String EL2INCREASE_IN_COMMISSION = "//android.widget.TextView[contains(@content-desc,'2% increase in commission')]";

    @AndroidFindBy(xpath = EL2INCREASE_IN_COMMISSION)
    private WebElement el2IncreaseInCommission;

    public static final String EL17 = "//android.widget.TextView[contains(@content-desc,'₹17')]";

    @AndroidFindBy(xpath = EL17)
    private WebElement el17;

    public static final String EL1MONTH_HATM = "//android.widget.TextView[contains(@content-desc,'1month_HATM')]";

    @AndroidFindBy(xpath = EL1MONTH_HATM)
    private WebElement el1monthHatm;

    public static final String EL20 = "//android.widget.TextView[contains(@content-desc,'₹20')]";

    @AndroidFindBy(xpath = EL20)
    private WebElement el20;

    public static final String EL22 = "//android.widget.TextView[contains(@content-desc,'₹22')]";

    @AndroidFindBy(xpath = EL22)
    private WebElement el22;

    public static final String EL2FIXED_INCENTIVE_DIGIKHATA = "//android.widget.TextView[contains(@content-desc,'2fixed incentive_digikhata')]";

    @AndroidFindBy(xpath = EL2FIXED_INCENTIVE_DIGIKHATA)
    private WebElement el2fixedIncentiveDigikhata;

    public static final String EL9 = "//android.widget.TextView[contains(@content-desc,'₹9')]";

    @AndroidFindBy(xpath = EL9)
    private WebElement el9;

    public static final String DIGIKHATA15FIXED_INCENTIVE = "//android.widget.TextView[contains(@content-desc,'Digikhata_1.5 fixed_incentive')]";

    @AndroidFindBy(xpath = DIGIKHATA15FIXED_INCENTIVE)
    private WebElement digikhata15FixedIncentive;

    public static final String EL1150 = "//android.widget.TextView[contains(@content-desc,'₹1150')]";

    @AndroidFindBy(xpath = EL1150)
    private WebElement el1150;

    public static final String EL6MONTHS = "//android.widget.TextView[contains(@content-desc,'6 Months')]";

    @AndroidFindBy(xpath = EL6MONTHS)
    private WebElement el6Months;

    public static final String EL850 = "//android.widget.TextView[contains(@content-desc,'₹850')]";

    @AndroidFindBy(xpath = EL850)
    private WebElement el850;

    public static final String EL6MONTHS_DIGIKHATA = "//android.widget.TextView[contains(@content-desc,'6 Months_Digikhata')]";

    @AndroidFindBy(xpath = EL6MONTHS_DIGIKHATA)
    private WebElement el6MonthsDigikhata;

    public static final String EL149 = "//android.widget.TextView[contains(@content-desc,'₹149')]";

    @AndroidFindBy(xpath = EL149)
    private WebElement el149;

    public static final String DMT_SUBSCRIPTION_PLAN_FOR1MONTH = "//android.widget.TextView[contains(@content-desc,'DMT Subscription plan for 1 month')]";

    @AndroidFindBy(xpath = DMT_SUBSCRIPTION_PLAN_FOR1MONTH)
    private WebElement dmtSubscriptionPlanFor1Month;

    public static final String EL50 = "//android.widget.TextView[contains(@content-desc,'₹50')]";

    @AndroidFindBy(xpath = EL50)
    private WebElement el50;

    public static final String DMT3MONTH_PLAN_ALL = "//android.widget.TextView[contains(@content-desc,'DMT_3 month plan_All')]";

    @AndroidFindBy(xpath = DMT3MONTH_PLAN_ALL)
    private WebElement dmt3MonthPlanAll;

    public static final String DMT4FIXED_INCENTIVE_SHARE_FOR1MONTH = "//android.widget.TextView[contains(@content-desc,'DMT_4 fixed incentive share_for 1 month')]";

    @AndroidFindBy(xpath = DMT4FIXED_INCENTIVE_SHARE_FOR1MONTH)
    private WebElement dmt4FixedIncentiveShareFor1Month;

    public static final String EL19 = "//android.widget.TextView[contains(@content-desc,'₹19')]";

    @AndroidFindBy(xpath = EL19)
    private WebElement el19;

    public static final String DMT21MONTH = "//android.widget.TextView[contains(@content-desc,'DMT_2%_1month')]";

    @AndroidFindBy(xpath = DMT21MONTH)
    private WebElement dmt21month;

    public static final String EL21 = "//android.widget.TextView[contains(@content-desc,'₹21')]";

    @AndroidFindBy(xpath = EL21)
    private WebElement el21;

    public static final String DMT_AUTO_RENEWAL_EXCLUSIVE = "//android.widget.TextView[contains(@content-desc,'DMT_auto renewal_exclusive')]";

    @AndroidFindBy(xpath = DMT_AUTO_RENEWAL_EXCLUSIVE)
    private WebElement dmtAutoRenewalExclusive;

    public static final String DEVOTIONAL_EXPERIENCES_ICON = "//android.view.View[contains(@content-desc,'Devotional Experiences icon')]";

    @AndroidFindBy(xpath = DEVOTIONAL_EXPERIENCES_ICON)
    private WebElement devotionalExperiencesIcon;

    public static final String DEVOTIONAL_EXPERIENCES = "//android.widget.TextView[contains(@content-desc,'Devotional Experiences')]";

    @AndroidFindBy(xpath = DEVOTIONAL_EXPERIENCES)
    private WebElement devotionalExperiences;

    public static final String FINANCE_ICON = "//android.view.View[contains(@content-desc,'Finance icon')]";

    @AndroidFindBy(xpath = FINANCE_ICON)
    private WebElement financeIcon;

    public static final String FINANCE = "//android.widget.TextView[contains(@content-desc,'Finance')]";

    @AndroidFindBy(xpath = FINANCE)
    private WebElement finance;

    public static final String RECHARGE_BILLPAY = "//android.widget.TextView[contains(@content-desc,'Recharge & Billpay')]";

    @AndroidFindBy(xpath = RECHARGE_BILLPAY)
    private WebElement rechargeBillpay;

    public static final String I_AUTHORIZE_AUTO_DEBIT_OF_THE_SUBSCRIPTION_RENEWAL = "//android.widget.TextView[contains(@content-desc,'I authorize auto-debit of the subscription renewal amount from my trade balance.')]";

    @AndroidFindBy(xpath = I_AUTHORIZE_AUTO_DEBIT_OF_THE_SUBSCRIPTION_RENEWAL)
    private WebElement iAuthorizeAutoDebitOfTheSubscriptionRenewal;

    public static final String PROCEED_TO_BUY = "//android.widget.TextView[@text='Proceed To Buy']";

    @AndroidFindBy(xpath = PROCEED_TO_BUY)
    private WebElement proceedToBuy;

    public HomeNonChannelsCreditLedgerHistory17Screen(TestContext context) {
        super(context);
    }
}
