package com.airpay.vyaapaar.screens.aeps;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / AEPS / CMS / Cash Drop. Module aeps. */
public class HomeCreditBalanceAepsCmsCashScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-aeps-cms-cash-drop";

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public static final String AC = "//android.widget.TextView[@text='AC']";

    @AndroidFindBy(xpath = AC)
    private WebElement ac;

    public static final String AIRPAY_CMS = "//android.widget.TextView[@text='Airpay CMS']";

    @AndroidFindBy(xpath = AIRPAY_CMS)
    private WebElement airpayCms;

    public static final String S = "//android.widget.TextView[@text='S']";

    @AndroidFindBy(xpath = S)
    private WebElement s;

    public static final String SURYODAY = "//android.widget.TextView[@text='Suryoday']";

    @AndroidFindBy(xpath = SURYODAY)
    private WebElement suryoday;

    public static final String MM = "//android.widget.TextView[@text='MM']";

    @AndroidFindBy(xpath = MM)
    private WebElement mm;

    public static final String MIDLAND_MICROFIN_LTD = "//android.widget.TextView[@text='Midland Microfin Ltd']";

    @AndroidFindBy(xpath = MIDLAND_MICROFIN_LTD)
    private WebElement midlandMicrofinLtd;

    public static final String BF = "//android.widget.TextView[@text='BF']";

    @AndroidFindBy(xpath = BF)
    private WebElement bf;

    public static final String BAJAJ_FINANCE = "//android.widget.TextView[@text='Bajaj Finance']";

    @AndroidFindBy(xpath = BAJAJ_FINANCE)
    private WebElement bajajFinance;

    public static final String O = "//android.widget.TextView[@text='O']";

    @AndroidFindBy(xpath = O)
    private WebElement o;

    public static final String OTHERS = "//android.widget.TextView[@text='Others']";

    @AndroidFindBy(xpath = OTHERS)
    private WebElement others;

    public static final String TW = "//android.widget.TextView[@text='TW']";

    @AndroidFindBy(xpath = TW)
    private WebElement tw;

    public static final String THREE_WHEELS_CAPITAL = "//android.widget.TextView[@text='THREE WHEELS CAPITAL']";

    @AndroidFindBy(xpath = THREE_WHEELS_CAPITAL)
    private WebElement threeWheelsCapital;

    public static final String SI = "//android.widget.TextView[@text='SI']";

    @AndroidFindBy(xpath = SI)
    private WebElement si;

    public static final String SHABRI_INVESTMENTS = "//android.widget.TextView[@text='Shabri Investments']";

    @AndroidFindBy(xpath = SHABRI_INVESTMENTS)
    private WebElement shabriInvestments;

    public static final String PM = "//android.widget.TextView[@text='PM']";

    @AndroidFindBy(xpath = PM)
    private WebElement pm;

    public static final String POPULAR_MOTORS = "//android.widget.TextView[@text='Popular Motors']";

    @AndroidFindBy(xpath = POPULAR_MOTORS)
    private WebElement popularMotors;

    public static final String SWIGGY = "//android.widget.TextView[@text='Swiggy']";

    @AndroidFindBy(xpath = SWIGGY)
    private WebElement swiggy;

    public static final String CASH_DROP = "//android.widget.TextView[@text='Cash Drop']";

    @AndroidFindBy(xpath = CASH_DROP)
    private WebElement cashDrop;

    public static final String HC = "//android.widget.TextView[@text='HC']";

    @AndroidFindBy(xpath = HC)
    private WebElement hc;

    public static final String HOME_CREDIT = "//android.widget.TextView[@text='Home Credit']";

    @AndroidFindBy(xpath = HOME_CREDIT)
    private WebElement homeCredit;

    public static final String M = "//android.widget.TextView[@text='M']";

    @AndroidFindBy(xpath = M)
    private WebElement m;

    public static final String MARGDARSHAK = "//android.widget.TextView[@text='Margdarshak']";

    @AndroidFindBy(xpath = MARGDARSHAK)
    private WebElement margdarshak;

    public static final String LF = "//android.widget.TextView[@text='LF']";

    @AndroidFindBy(xpath = LF)
    private WebElement lf;

    public static final String LNT_FINANCE = "//android.widget.TextView[@text='LnT Finance']";

    @AndroidFindBy(xpath = LNT_FINANCE)
    private WebElement lntFinance;

    public static final String P = "//android.widget.TextView[@text='P']";

    @AndroidFindBy(xpath = P)
    private WebElement p;

    public static final String PARTNER = "//android.widget.TextView[@text='Partner']";

    @AndroidFindBy(xpath = PARTNER)
    private WebElement partner;

    public static final String CP = "//android.widget.TextView[@text='CP']";

    @AndroidFindBy(xpath = CP)
    private WebElement cp;

    public static final String CHILD_PARTNER_OF_PARENT_ID = "//android.widget.TextView[@text='child partner of parent id']";

    @AndroidFindBy(xpath = CHILD_PARTNER_OF_PARENT_ID)
    private WebElement childPartnerOfParentId;

    public static final String J = "//android.widget.TextView[@text='J']";

    @AndroidFindBy(xpath = J)
    private WebElement j;

    public static final String JAYBESS = "//android.widget.TextView[@text='jaybess']";

    @AndroidFindBy(xpath = JAYBESS)
    private WebElement jaybess;

    public static final String SHAIKH = "//android.widget.TextView[@text='Shaikh']";

    @AndroidFindBy(xpath = SHAIKH)
    private WebElement shaikh;

    public static final String PS = "//android.widget.TextView[@text='PS']";

    @AndroidFindBy(xpath = PS)
    private WebElement ps;

    public static final String PARTNER_SIMPLEICASH_CUSTOMERCOMMISSION = "//android.widget.TextView[@text='Partner Simpleicash customerCommission']";

    @AndroidFindBy(xpath = PARTNER_SIMPLEICASH_CUSTOMERCOMMISSION)
    private WebElement partnerSimpleicashCustomercommission;

    public static final String TP = "//android.widget.TextView[@text='TP']";

    @AndroidFindBy(xpath = TP)
    private WebElement tp;

    public static final String TEST_PERTNER = "//android.widget.TextView[@text='test pertner']";

    @AndroidFindBy(xpath = TEST_PERTNER)
    private WebElement testPertner;

    public static final String PW = "//android.widget.TextView[@text='PW']";

    @AndroidFindBy(xpath = PW)
    private WebElement pw;

    public static final String PARENT_WITHOUT_CHILD = "//android.widget.TextView[@text='parent without child']";

    @AndroidFindBy(xpath = PARENT_WITHOUT_CHILD)
    private WebElement parentWithoutChild;

    public static final String A = "//android.widget.TextView[@text='A']";

    @AndroidFindBy(xpath = A)
    private WebElement a;

    public static final String ABC = "//android.widget.TextView[@text='ABC']";

    @AndroidFindBy(xpath = ABC)
    private WebElement abc;

    public static final String TS = "//android.widget.TextView[@text='TS']";

    @AndroidFindBy(xpath = TS)
    private WebElement ts;

    public static final String TEST_SANITY_PARTNERSIMPLECASH = "//android.widget.TextView[@text='Test sanity partnersimpleCash']";

    @AndroidFindBy(xpath = TEST_SANITY_PARTNERSIMPLECASH)
    private WebElement testSanityPartnersimplecash;

    public static final String TEST_PARTNER = "//android.widget.TextView[@text='test partner']";

    @AndroidFindBy(xpath = TEST_PARTNER)
    private WebElement testPartner;

    public static final String NP = "//android.widget.TextView[@text='NP']";

    @AndroidFindBy(xpath = NP)
    private WebElement np;

    public static final String NEW_PARTNER_PARENT = "//android.widget.TextView[@text='new partner parent']";

    @AndroidFindBy(xpath = NEW_PARTNER_PARENT)
    private WebElement newPartnerParent;

    public static final String TEST_PART_NAME = "//android.widget.TextView[@text='test part name']";

    @AndroidFindBy(xpath = TEST_PART_NAME)
    private WebElement testPartName;

    public static final String TD = "//android.widget.TextView[@text='TD']";

    @AndroidFindBy(xpath = TD)
    private WebElement td;

    public static final String TEST_DDFSHSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS_RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRF = "//android.widget.TextView[@text='test ddfshssssssssssssssssssssssssssssssssssssssssssssssssssssssss rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrf']";

    @AndroidFindBy(xpath = TEST_DDFSHSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS_RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRF)
    private WebElement testDdfshssssssssssssssssssssssssssssssssssssssssssssssssssssssssRrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrf;

    public static final String TEST_SAMPLE_SAMPLE = "//android.widget.TextView[@text='test sample sample']";

    @AndroidFindBy(xpath = TEST_SAMPLE_SAMPLE)
    private WebElement testSampleSample;

    public static final String TC = "//android.widget.TextView[@text='TC']";

    @AndroidFindBy(xpath = TC)
    private WebElement tc;

    public static final String TEST_CMS = "//android.widget.TextView[@text='test cms']";

    @AndroidFindBy(xpath = TEST_CMS)
    private WebElement testCms;

    public static final String TEST_CMS_CNS = "//android.widget.TextView[@text='test cms cns']";

    @AndroidFindBy(xpath = TEST_CMS_CNS)
    private WebElement testCmsCns;

    public static final String T = "//android.widget.TextView[@text='T']";

    @AndroidFindBy(xpath = T)
    private WebElement t;

    public static final String TESTPARTNER = "//android.widget.TextView[@text='testPartner']";

    @AndroidFindBy(xpath = TESTPARTNER)
    private WebElement testpartner;

    public static final String NA = "//android.widget.TextView[@text='NA']";

    @AndroidFindBy(xpath = NA)
    private WebElement na;

    public static final String NEW_API_PARTNER = "//android.widget.TextView[@text='new api partner']";

    @AndroidFindBy(xpath = NEW_API_PARTNER)
    private WebElement newApiPartner;

    public HomeCreditBalanceAepsCmsCashScreen(TestContext context) {
        super(context);
    }
}
