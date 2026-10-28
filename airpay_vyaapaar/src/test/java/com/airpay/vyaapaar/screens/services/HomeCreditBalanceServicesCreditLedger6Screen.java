package com.airpay.vyaapaar.screens.services;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Services / Credit Ledger History / Filter Icon / Show More / Finance / Unsecured Credit Cards / Credilio — Start Your Credit Journey Securely! Logo. Module services. */
public class HomeCreditBalanceServicesCreditLedger6Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-services-credit-ledger-history-dropdown-filter-icon-show-more-finance";

    public static final String SECURED_CREDIT_CARDS = "//android.view.View[contains(@content-desc,'Secured Credit Cards')]";

    @AndroidFindBy(xpath = SECURED_CREDIT_CARDS)
    private WebElement securedCreditCards;

    public static final String UNSECURED_CREDIT_CARDS = "//android.view.View[contains(@content-desc,'Unsecured Credit Cards')]";

    @AndroidFindBy(xpath = UNSECURED_CREDIT_CARDS)
    private WebElement unsecuredCreditCards;

    public static final String NPS = "//android.view.View[contains(@content-desc,'NPS')]";

    @AndroidFindBy(xpath = NPS)
    private WebElement nps;

    public static final String FIXED_DEPOSITS = "//android.view.View[contains(@content-desc,'Fixed Deposits')]";

    @AndroidFindBy(xpath = FIXED_DEPOSITS)
    private WebElement fixedDeposits;

    public static final String FINANCE = "//android.widget.TextView[@text='Finance']";

    @AndroidFindBy(xpath = FINANCE)
    private WebElement finance;

    public static final String CREDILIO_CARDS_LOGO = "//android.view.View[contains(@content-desc,'Credilio Cards Logo')]";

    @AndroidFindBy(xpath = CREDILIO_CARDS_LOGO)
    private WebElement credilioCardsLogo;

    public static final String CREDILIO_CARDS = "//android.widget.TextView[@text='Credilio Cards']";

    @AndroidFindBy(xpath = CREDILIO_CARDS)
    private WebElement credilioCards;

    public static final String SELECT_PARTNER = "//android.widget.TextView[@text='Select Partner']";

    @AndroidFindBy(xpath = SELECT_PARTNER)
    private WebElement selectPartner;

    public static final String START_YOUR_CREDIT_JOURNEY_SECURELY_LOGO = "//android.view.View[contains(@content-desc,'Start Your Credit Journey Securely! Logo')]";

    @AndroidFindBy(xpath = START_YOUR_CREDIT_JOURNEY_SECURELY_LOGO)
    private WebElement startYourCreditJourneySecurelyLogo;

    public static final String START_YOUR_CREDIT_JOURNEY_SECURELY = "//android.widget.TextView[@text='Start Your Credit Journey Securely!']";

    @AndroidFindBy(xpath = START_YOUR_CREDIT_JOURNEY_SECURELY)
    private WebElement startYourCreditJourneySecurely;

    public static final String EL3QUICK_FIELDS_WE_LL_TAKE_YOU_TO = "//android.widget.TextView[@text=concat('3 quick fields - we',\"'\",'ll take you to Credilio to finish instantly.')]";

    @AndroidFindBy(xpath = EL3QUICK_FIELDS_WE_LL_TAKE_YOU_TO)
    private WebElement el3QuickFieldsWeLlTakeYouTo;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String NAME_AS_PER_PAN = "//android.widget.TextView[@text='Name as Per PAN']";

    @AndroidFindBy(xpath = NAME_AS_PER_PAN)
    private WebElement nameAsPerPan;

    public static final String PINCODE = "//android.widget.TextView[@text='Pincode']";

    @AndroidFindBy(xpath = PINCODE)
    private WebElement pincode;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String HDFC_LOGO = "//android.view.View[contains(@content-desc,'HDFC Logo')]";

    @AndroidFindBy(xpath = HDFC_LOGO)
    private WebElement hdfcLogo;

    public static final String HDFC = "//android.widget.TextView[@text='HDFC']";

    @AndroidFindBy(xpath = HDFC)
    private WebElement hdfc;

    public static final String CREDILIO_LOGO = "//android.view.View[contains(@content-desc,'Credilio Logo')]";

    @AndroidFindBy(xpath = CREDILIO_LOGO)
    private WebElement credilioLogo;

    public static final String CREDILIO = "//android.widget.TextView[@text='Credilio']";

    @AndroidFindBy(xpath = CREDILIO)
    private WebElement credilio;

    public static final String LOGO = "//android.view.View[contains(@content-desc,'Logo')]";

    @AndroidFindBy(xpath = LOGO)
    private WebElement logo;

    public static final String CUSTOMER_NAME = "//android.widget.TextView[@text='Customer Name']";

    @AndroidFindBy(xpath = CUSTOMER_NAME)
    private WebElement customerName;

    public HomeCreditBalanceServicesCreditLedger6Screen(TestContext context) {
        super(context);
    }
}
