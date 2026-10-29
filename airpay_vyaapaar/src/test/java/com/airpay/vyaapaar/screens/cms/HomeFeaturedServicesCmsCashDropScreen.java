package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/CMS/Cash Drop. Module cms. */
public class HomeFeaturedServicesCmsCashDropScreen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-cms-cash-drop";

    public static final String SEARCH = "//android.view.View[contains(@content-desc,'Search')]";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public static final String AC = "//android.widget.TextView[contains(@content-desc,'AC')]";

    @AndroidFindBy(xpath = AC)
    private WebElement ac;

    public static final String AIRPAY_CMS = "//android.widget.TextView[contains(@content-desc,'Airpay CMS')]";

    @AndroidFindBy(xpath = AIRPAY_CMS)
    private WebElement airpayCms;

    public static final String S = "//android.widget.TextView[contains(@content-desc,'S')]";

    @AndroidFindBy(xpath = S)
    private WebElement s;

    public static final String SURYODAY = "//android.widget.TextView[contains(@content-desc,'Suryoday')]";

    @AndroidFindBy(xpath = SURYODAY)
    private WebElement suryoday;

    public static final String MM = "//android.widget.TextView[contains(@content-desc,'MM')]";

    @AndroidFindBy(xpath = MM)
    private WebElement mm;

    public static final String MIDLAND_MICROFIN_LTD = "//android.widget.TextView[contains(@content-desc,'Midland Microfin Ltd')]";

    @AndroidFindBy(xpath = MIDLAND_MICROFIN_LTD)
    private WebElement midlandMicrofinLtd;

    public static final String BF = "//android.widget.TextView[contains(@content-desc,'BF')]";

    @AndroidFindBy(xpath = BF)
    private WebElement bf;

    public static final String BAJAJ_FINANCE = "//android.widget.TextView[contains(@content-desc,'Bajaj Finance')]";

    @AndroidFindBy(xpath = BAJAJ_FINANCE)
    private WebElement bajajFinance;

    public static final String O = "//android.widget.TextView[contains(@content-desc,'O')]";

    @AndroidFindBy(xpath = O)
    private WebElement o;

    public static final String OTHERS = "//android.widget.TextView[contains(@content-desc,'Others')]";

    @AndroidFindBy(xpath = OTHERS)
    private WebElement others;

    public static final String TW = "//android.widget.TextView[contains(@content-desc,'TW')]";

    @AndroidFindBy(xpath = TW)
    private WebElement tw;

    public static final String THREE_WHEELS_CAPITAL = "//android.widget.TextView[contains(@content-desc,'THREE WHEELS CAPITAL')]";

    @AndroidFindBy(xpath = THREE_WHEELS_CAPITAL)
    private WebElement threeWheelsCapital;

    public static final String SI = "//android.widget.TextView[contains(@content-desc,'SI')]";

    @AndroidFindBy(xpath = SI)
    private WebElement si;

    public static final String SHABRI_INVESTMENTS = "//android.widget.TextView[contains(@content-desc,'Shabri Investments')]";

    @AndroidFindBy(xpath = SHABRI_INVESTMENTS)
    private WebElement shabriInvestments;

    public static final String PM = "//android.widget.TextView[contains(@content-desc,'PM')]";

    @AndroidFindBy(xpath = PM)
    private WebElement pm;

    public static final String POPULAR_MOTORS = "//android.widget.TextView[contains(@content-desc,'Popular Motors')]";

    @AndroidFindBy(xpath = POPULAR_MOTORS)
    private WebElement popularMotors;

    public static final String SWIGGY = "//android.widget.TextView[contains(@content-desc,'Swiggy')]";

    @AndroidFindBy(xpath = SWIGGY)
    private WebElement swiggy;

    public static final String CASH_DROP = "//android.widget.TextView[@text='Cash Drop']";

    @AndroidFindBy(xpath = CASH_DROP)
    private WebElement cashDrop;

    public HomeFeaturedServicesCmsCashDropScreen(TestContext context) {
        super(context);
    }
}
