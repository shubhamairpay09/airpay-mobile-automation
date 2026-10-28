package com.airpay.vyaapaar.screens.services;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Services / Credit Ledger History / Filter Icon / Show More / Subscription / DMT — Choose the plan that works for you!. Module services. */
public class HomeCreditBalanceServicesCreditLedger5Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-services-credit-ledger-history-dropdown-filter-icon-show-more-subscrip";

    public static final String POWER_UP_YOUR_EARNINGS_SELECT_THE_PRODUCT_YOU = "//android.widget.TextView[@text='Power up your earnings! Select the product you want to subscribe to.']";

    @AndroidFindBy(xpath = POWER_UP_YOUR_EARNINGS_SELECT_THE_PRODUCT_YOU)
    private WebElement powerUpYourEarningsSelectTheProductYou;

    public static final String AEPS_ICON = "//android.view.View[contains(@content-desc,'AEPS icon')]";

    @AndroidFindBy(xpath = AEPS_ICON)
    private WebElement aepsIcon;

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String HATM_ICON = "//android.view.View[contains(@content-desc,'HATM icon')]";

    @AndroidFindBy(xpath = HATM_ICON)
    private WebElement hatmIcon;

    public static final String HATM = "//android.widget.TextView[@text='HATM']";

    @AndroidFindBy(xpath = HATM)
    private WebElement hatm;

    public static final String DIGIKHATA_ICON = "//android.view.View[contains(@content-desc,'DIGIKHATA icon')]";

    @AndroidFindBy(xpath = DIGIKHATA_ICON)
    private WebElement digikhataIcon;

    public static final String DIGIKHATA = "//android.widget.TextView[@text='DIGIKHATA']";

    @AndroidFindBy(xpath = DIGIKHATA)
    private WebElement digikhata;

    public static final String DMT_ICON = "//android.view.View[contains(@content-desc,'DMT icon')]";

    @AndroidFindBy(xpath = DMT_ICON)
    private WebElement dmtIcon;

    public static final String DMT = "//android.widget.TextView[@text='DMT']";

    @AndroidFindBy(xpath = DMT)
    private WebElement dmt;

    public static final String SUBSCRIPTION = "//android.widget.TextView[@text='Subscription']";

    @AndroidFindBy(xpath = SUBSCRIPTION)
    private WebElement subscription;

    public static final String CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU = "//android.widget.TextView[@text='Choose the plan that works for you!']";

    @AndroidFindBy(xpath = CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU)
    private WebElement chooseThePlanThatWorksForYou;

    public static final String EL3MONTHS = "//android.widget.TextView[@text='3 Months']";

    @AndroidFindBy(xpath = EL3MONTHS)
    private WebElement el3Months;

    public static final String EL600 = "//android.widget.TextView[@text='₹600']";

    @AndroidFindBy(xpath = EL600)
    private WebElement el600;

    public static final String HATM03MONTH = "//android.widget.TextView[@text='hatm03month']";

    @AndroidFindBy(xpath = HATM03MONTH)
    private WebElement hatm03month;

    public static final String DOWN_ARROW = "//android.view.View[contains(@content-desc,'Down arrow')]";

    @AndroidFindBy(xpath = DOWN_ARROW)
    private WebElement downArrow;

    public static final String EL12MONTHS = "//android.widget.TextView[@text='12 Months']";

    @AndroidFindBy(xpath = EL12MONTHS)
    private WebElement el12Months;

    public static final String EL800 = "//android.widget.TextView[@text='₹800']";

    @AndroidFindBy(xpath = EL800)
    private WebElement el800;

    public static final String EL5INCR_RTAEPS12MONTH = "//android.widget.TextView[@text='5 % Incr RTAEPS12Month']";

    @AndroidFindBy(xpath = EL5INCR_RTAEPS12MONTH)
    private WebElement el5IncrRtaeps12month;

    public static final String EL1MONTH = "//android.widget.TextView[@text='1 Month']";

    @AndroidFindBy(xpath = EL1MONTH)
    private WebElement el1Month;

    public static final String EL499 = "//android.widget.TextView[@text='₹499']";

    @AndroidFindBy(xpath = EL499)
    private WebElement el499;

    public static final String EL2INCREASE_IN_COMMISSION = "//android.widget.TextView[@text='2% increase in commission']";

    @AndroidFindBy(xpath = EL2INCREASE_IN_COMMISSION)
    private WebElement el2IncreaseInCommission;

    public static final String EL17 = "//android.widget.TextView[@text='₹17']";

    @AndroidFindBy(xpath = EL17)
    private WebElement el17;

    public static final String EL1MONTH_HATM = "//android.widget.TextView[@text='1month_HATM']";

    @AndroidFindBy(xpath = EL1MONTH_HATM)
    private WebElement el1monthHatm;

    public static final String EL20 = "//android.widget.TextView[@text='₹20']";

    @AndroidFindBy(xpath = EL20)
    private WebElement el20;

    public static final String EL22 = "//android.widget.TextView[@text='₹22']";

    @AndroidFindBy(xpath = EL22)
    private WebElement el22;

    public static final String EL2FIXED_INCENTIVE_DIGIKHATA = "//android.widget.TextView[@text='2fixed incentive_digikhata']";

    @AndroidFindBy(xpath = EL2FIXED_INCENTIVE_DIGIKHATA)
    private WebElement el2fixedIncentiveDigikhata;

    public static final String EL9 = "//android.widget.TextView[@text='₹9']";

    @AndroidFindBy(xpath = EL9)
    private WebElement el9;

    public static final String DIGIKHATA15FIXED_INCENTIVE = "//android.widget.TextView[@text='Digikhata_1.5 fixed_incentive']";

    @AndroidFindBy(xpath = DIGIKHATA15FIXED_INCENTIVE)
    private WebElement digikhata15FixedIncentive;

    public static final String EL1150 = "//android.widget.TextView[@text='₹1150']";

    @AndroidFindBy(xpath = EL1150)
    private WebElement el1150;

    public static final String EL6MONTHS = "//android.widget.TextView[@text='6 Months']";

    @AndroidFindBy(xpath = EL6MONTHS)
    private WebElement el6Months;

    public static final String EL850 = "//android.widget.TextView[@text='₹850']";

    @AndroidFindBy(xpath = EL850)
    private WebElement el850;

    public static final String EL6MONTHS_DIGIKHATA = "//android.widget.TextView[@text='6 Months_Digikhata']";

    @AndroidFindBy(xpath = EL6MONTHS_DIGIKHATA)
    private WebElement el6MonthsDigikhata;

    public static final String EL149 = "//android.widget.TextView[@text='₹149']";

    @AndroidFindBy(xpath = EL149)
    private WebElement el149;

    public static final String DMT_SUBSCRIPTION_PLAN_FOR1MONTH = "//android.widget.TextView[@text='DMT Subscription plan for 1 month']";

    @AndroidFindBy(xpath = DMT_SUBSCRIPTION_PLAN_FOR1MONTH)
    private WebElement dmtSubscriptionPlanFor1Month;

    public static final String EL50 = "//android.widget.TextView[@text='₹50']";

    @AndroidFindBy(xpath = EL50)
    private WebElement el50;

    public static final String DMT3MONTH_PLAN_ALL = "//android.widget.TextView[@text='DMT_3 month plan_All']";

    @AndroidFindBy(xpath = DMT3MONTH_PLAN_ALL)
    private WebElement dmt3MonthPlanAll;

    public static final String DMT4FIXED_INCENTIVE_SHARE_FOR1MONTH = "//android.widget.TextView[@text='DMT_4 fixed incentive share_for 1 month']";

    @AndroidFindBy(xpath = DMT4FIXED_INCENTIVE_SHARE_FOR1MONTH)
    private WebElement dmt4FixedIncentiveShareFor1Month;

    public static final String EL19 = "//android.widget.TextView[@text='₹19']";

    @AndroidFindBy(xpath = EL19)
    private WebElement el19;

    public static final String DMT21MONTH = "//android.widget.TextView[@text='DMT_2%_1month']";

    @AndroidFindBy(xpath = DMT21MONTH)
    private WebElement dmt21month;

    public static final String EL21 = "//android.widget.TextView[@text='₹21']";

    @AndroidFindBy(xpath = EL21)
    private WebElement el21;

    public static final String DMT_AUTO_RENEWAL_EXCLUSIVE = "//android.widget.TextView[@text='DMT_auto renewal_exclusive']";

    @AndroidFindBy(xpath = DMT_AUTO_RENEWAL_EXCLUSIVE)
    private WebElement dmtAutoRenewalExclusive;

    public HomeCreditBalanceServicesCreditLedger5Screen(TestContext context) {
        super(context);
    }
}
