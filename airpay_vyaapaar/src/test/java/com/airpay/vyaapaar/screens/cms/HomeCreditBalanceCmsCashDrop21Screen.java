package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / TW / Cash Burial / BN / cash pickup / Reject / No — New. Module cms. */
public class HomeCreditBalanceCmsCashDrop21Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-tw-cash-burial-bn-cash-pickup-reject-no";

    public static final String EL_NEW = "//android.widget.TextView[@text='New']";

    @AndroidFindBy(xpath = EL_NEW)
    private WebElement elNew;

    public static final String ACCEPTED = "//android.widget.TextView[@text='Accepted']";

    @AndroidFindBy(xpath = ACCEPTED)
    private WebElement accepted;

    public static final String ICON = "//android.view.View[contains(@content-desc,'Icon')]";

    @AndroidFindBy(xpath = ICON)
    private WebElement icon;

    public static final String AGENT_CODE = "//android.widget.TextView[@text='Agent Code']";

    @AndroidFindBy(xpath = AGENT_CODE)
    private WebElement agentCode;

    public static final String A1 = "//android.widget.TextView[@text='A1']";

    @AndroidFindBy(xpath = A1)
    private WebElement a1;

    public static final String REQUEST_TYPE = "//android.widget.TextView[@text='Request Type']";

    @AndroidFindBy(xpath = REQUEST_TYPE)
    private WebElement requestType;

    public static final String CASH_DROP = "//android.widget.TextView[@text='Cash drop']";

    @AndroidFindBy(xpath = CASH_DROP)
    private WebElement cashDrop;

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

    public static final String ADDRESS = "//android.widget.TextView[@text='Address']";

    @AndroidFindBy(xpath = ADDRESS)
    private WebElement address;

    public static final String TIME_SLOT_ERROR = "//android.widget.TextView[@text='Time slot error']";

    @AndroidFindBy(xpath = TIME_SLOT_ERROR)
    private WebElement timeSlotError;

    public static final String REJECT = "//android.widget.TextView[@text='Reject']";

    @AndroidFindBy(xpath = REJECT)
    private WebElement reject;

    public static final String ACCEPT = "//android.widget.TextView[@text='Accept']";

    @AndroidFindBy(xpath = ACCEPT)
    private WebElement accept;

    public static final String AIRPAY_LOGO = "//android.view.View[contains(@content-desc,'Airpay Logo')]";

    @AndroidFindBy(xpath = AIRPAY_LOGO)
    private WebElement airpayLogo;

    public static final String ASK_FOR1113 = "//android.widget.TextView[@text='Ask For - ₹1,113']";

    @AndroidFindBy(xpath = ASK_FOR1113)
    private WebElement askFor1113;

    public static final String REQUEST_DATE = "//android.widget.TextView[@text='Request Date']";

    @AndroidFindBy(xpath = REQUEST_DATE)
    private WebElement requestDate;

    public static final String CASH_BURIAL = "//android.widget.TextView[@text='Cash Burial']";

    @AndroidFindBy(xpath = CASH_BURIAL)
    private WebElement cashBurial;

    public HomeCreditBalanceCmsCashDrop21Screen(TestContext context) {
        super(context);
    }
}
