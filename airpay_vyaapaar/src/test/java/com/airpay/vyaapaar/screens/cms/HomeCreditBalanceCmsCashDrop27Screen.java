package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / TW / Cash Burial / BN / cash pickup / CMS / Cash Burial — Burial new company new. Module cms. */
public class HomeCreditBalanceCmsCashDrop27Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-tw-cash-burial-bn-cash-pickup-cms-cash-burial";

    public static final String BN = "//android.widget.TextView[@text='BN']";

    @AndroidFindBy(xpath = BN)
    private WebElement bn;

    public static final String EL2 = "//android.widget.TextView[@text='2']";

    @AndroidFindBy(xpath = EL2)
    private WebElement el2;

    public static final String BURIAL_NEW_COMPANY_NEW = "//android.widget.TextView[@text='Burial new company new']";

    @AndroidFindBy(xpath = BURIAL_NEW_COMPANY_NEW)
    private WebElement burialNewCompanyNew;

    public static final String CE = "//android.widget.TextView[@text='CE']";

    @AndroidFindBy(xpath = CE)
    private WebElement ce;

    public static final String CHANDEL_ENTERPRISES = "//android.widget.TextView[@text='Chandel Enterprises']";

    @AndroidFindBy(xpath = CHANDEL_ENTERPRISES)
    private WebElement chandelEnterprises;

    public static final String NT = "//android.widget.TextView[@text='NT']";

    @AndroidFindBy(xpath = NT)
    private WebElement nt;

    public static final String NEW_TESTING_TEST = "//android.widget.TextView[@text='New Testing Test']";

    @AndroidFindBy(xpath = NEW_TESTING_TEST)
    private WebElement newTestingTest;

    public static final String RC = "//android.widget.TextView[@text='RC']";

    @AndroidFindBy(xpath = RC)
    private WebElement rc;

    public static final String RADIANT_CASH_MANAGEMENT_SERVICES_LTD = "//android.widget.TextView[@text='Radiant Cash Management Services Ltd']";

    @AndroidFindBy(xpath = RADIANT_CASH_MANAGEMENT_SERVICES_LTD)
    private WebElement radiantCashManagementServicesLtd;

    public static final String SS = "//android.widget.TextView[@text='SS']";

    @AndroidFindBy(xpath = SS)
    private WebElement ss;

    public static final String SURYODAY_SMALL_FINANCE_BANK = "//android.widget.TextView[@text='Suryoday Small Finance Bank']";

    @AndroidFindBy(xpath = SURYODAY_SMALL_FINANCE_BANK)
    private WebElement suryodaySmallFinanceBank;

    public static final String NC = "//android.widget.TextView[@text='NC']";

    @AndroidFindBy(xpath = NC)
    private WebElement nc;

    public static final String NEW_COMPANY = "//android.widget.TextView[@text='New Company']";

    @AndroidFindBy(xpath = NEW_COMPANY)
    private WebElement newCompany;

    public static final String TC = "//android.widget.TextView[@text='TC']";

    @AndroidFindBy(xpath = TC)
    private WebElement tc;

    public static final String TEST_COMPANYY = "//android.widget.TextView[@text='Test Companyy']";

    @AndroidFindBy(xpath = TEST_COMPANYY)
    private WebElement testCompanyy;

    public static final String TP = "//android.widget.TextView[@text='TP']";

    @AndroidFindBy(xpath = TP)
    private WebElement tp;

    public static final String TESTING_PARTNER = "//android.widget.TextView[@text='Testing  Partner']";

    @AndroidFindBy(xpath = TESTING_PARTNER)
    private WebElement testingPartner;

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public static final String CASH_BURIAL = "//android.widget.TextView[@text='Cash Burial']";

    @AndroidFindBy(xpath = CASH_BURIAL)
    private WebElement cashBurial;

    public HomeCreditBalanceCmsCashDrop27Screen(TestContext context) {
        super(context);
    }
}
