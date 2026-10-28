package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / TW / Partner — Search. Module cms. */
public class HomeCreditBalanceCmsCashDrop13Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-tw-partner";

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

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

    public static final String S = "//android.widget.TextView[@text='S']";

    @AndroidFindBy(xpath = S)
    private WebElement s;

    public static final String SWIGGY = "//android.widget.TextView[@text='Swiggy']";

    @AndroidFindBy(xpath = SWIGGY)
    private WebElement swiggy;

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

    public static final String CASH_DROP = "//android.widget.TextView[@text='Cash Drop']";

    @AndroidFindBy(xpath = CASH_DROP)
    private WebElement cashDrop;

    public HomeCreditBalanceCmsCashDrop13Screen(TestContext context) {
        super(context);
    }
}
