package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / TW / Cash Burial / BN — New. Module cms. */
public class HomeCreditBalanceCmsCashDrop17Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-tw-cash-burial-bn";

    public static final String EL_NEW = "//android.widget.TextView[@text='New']";

    @AndroidFindBy(xpath = EL_NEW)
    private WebElement elNew;

    public static final String ACCEPTED = "//android.widget.TextView[@text='Accepted']";

    @AndroidFindBy(xpath = ACCEPTED)
    private WebElement accepted;

    public static final String ALL = "//android.widget.TextView[@text='all']";

    @AndroidFindBy(xpath = ALL)
    private WebElement all;

    public static final String CASH_PICKUP = "//android.widget.TextView[@text='cash pickup']";

    @AndroidFindBy(xpath = CASH_PICKUP)
    private WebElement cashPickup;

    public static final String CASH_DROP = "//android.widget.TextView[@text='cash drop']";

    @AndroidFindBy(xpath = CASH_DROP)
    private WebElement cashDrop;

    public static final String IMPORTANT_NOTICE_KINDLY_HANDOVER_THE_CASH_TO_THE = "//android.widget.TextView[@text='Important Notice - Kindly handover the cash to the agent post you see a successful transaction notification on the screen.']";

    @AndroidFindBy(xpath = IMPORTANT_NOTICE_KINDLY_HANDOVER_THE_CASH_TO_THE)
    private WebElement importantNoticeKindlyHandoverTheCashToThe;

    public static final String AIRPAY_LOGO = "//android.view.View[contains(@content-desc,'Airpay Logo')]";

    @AndroidFindBy(xpath = AIRPAY_LOGO)
    private WebElement airpayLogo;

    public static final String ASK_FOR1114 = "//android.widget.TextView[@text='Ask For - ₹1,114']";

    @AndroidFindBy(xpath = ASK_FOR1114)
    private WebElement askFor1114;

    public static final String ICON = "//android.view.View[contains(@content-desc,'Icon')]";

    @AndroidFindBy(xpath = ICON)
    private WebElement icon;

    public static final String REQUEST_DATE = "//android.widget.TextView[@text='Request Date']";

    @AndroidFindBy(xpath = REQUEST_DATE)
    private WebElement requestDate;

    public static final String AGENT_CODE = "//android.widget.TextView[@text='Agent Code']";

    @AndroidFindBy(xpath = AGENT_CODE)
    private WebElement agentCode;

    public static final String A1 = "//android.widget.TextView[@text='A1']";

    @AndroidFindBy(xpath = A1)
    private WebElement a1;

    public static final String REQUEST_TYPE = "//android.widget.TextView[@text='Request Type']";

    @AndroidFindBy(xpath = REQUEST_TYPE)
    private WebElement requestType;

    public static final String COMPANY_NAME = "//android.widget.TextView[@text='Company Name']";

    @AndroidFindBy(xpath = COMPANY_NAME)
    private WebElement companyName;

    public static final String BURIAL_NEW_COMPANY_NEW = "//android.widget.TextView[@text='Burial new company new']";

    @AndroidFindBy(xpath = BURIAL_NEW_COMPANY_NEW)
    private WebElement burialNewCompanyNew;

    public static final String AGENT_PHONE_NUMBER = "//android.widget.TextView[@text='Agent Phone Number']";

    @AndroidFindBy(xpath = AGENT_PHONE_NUMBER)
    private WebElement agentPhoneNumber;

    public static final String N_A = "//android.widget.TextView[@text='N/A']";

    @AndroidFindBy(xpath = N_A)
    private WebElement nA;

    public static final String CASH_BURIAL = "//android.widget.TextView[@text='Cash Burial']";

    @AndroidFindBy(xpath = CASH_BURIAL)
    private WebElement cashBurial;

    public HomeCreditBalanceCmsCashDrop17Screen(TestContext context) {
        super(context);
    }
}
