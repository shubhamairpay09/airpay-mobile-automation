package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Welcome to airpay vyaapaar / Continue / City — Select City. Module home. */
public class HomeWelcomeToAirpayVyaapaarContinue2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-welcome-to-airpay-vyaapaar-continue-city";

    public static final String SELECT_CITY = "//android.widget.TextView[@text='Select City']";

    @AndroidFindBy(xpath = SELECT_CITY)
    private WebElement selectCity;

    public static final String EL2 = "//android.widget.TextView[@text='2']";

    @AndroidFindBy(xpath = EL2)
    private WebElement el2;

    public static final String EL24PARGANAS_N = "//android.widget.TextView[@text='24 Parganas (n)']";

    @AndroidFindBy(xpath = EL24PARGANAS_N)
    private WebElement el24ParganasN;

    public static final String EL24PARGANAS_S = "//android.widget.TextView[@text='24 Parganas (s)']";

    @AndroidFindBy(xpath = EL24PARGANAS_S)
    private WebElement el24ParganasS;

    public static final String A = "//android.widget.TextView[@text='A']";

    @AndroidFindBy(xpath = A)
    private WebElement a;

    public static final String ABHAYAPURI = "//android.widget.TextView[@text='Abhayapuri']";

    @AndroidFindBy(xpath = ABHAYAPURI)
    private WebElement abhayapuri;

    public static final String ABIRAMAM = "//android.widget.TextView[@text='Abiramam']";

    @AndroidFindBy(xpath = ABIRAMAM)
    private WebElement abiramam;

    public static final String ABOHAR = "//android.widget.TextView[@text='Abohar']";

    @AndroidFindBy(xpath = ABOHAR)
    private WebElement abohar;

    public static final String ABRAMA = "//android.widget.TextView[@text='Abrama']";

    @AndroidFindBy(xpath = ABRAMA)
    private WebElement abrama;

    public static final String ABU_ROAD = "//android.widget.TextView[@text='Abu Road']";

    @AndroidFindBy(xpath = ABU_ROAD)
    private WebElement abuRoad;

    public static final String ACHABAL = "//android.widget.TextView[@text='Achabal']";

    @AndroidFindBy(xpath = ACHABAL)
    private WebElement achabal;

    public static final String ACHALPUR = "//android.widget.TextView[@text='Achalpur']";

    @AndroidFindBy(xpath = ACHALPUR)
    private WebElement achalpur;

    public static final String ACHAMPET = "//android.widget.TextView[@text='Achampet']";

    @AndroidFindBy(xpath = ACHAMPET)
    private WebElement achampet;

    public static final String ACHAMPUDUR = "//android.widget.TextView[@text='Achampudur']";

    @AndroidFindBy(xpath = ACHAMPUDUR)
    private WebElement achampudur;

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public HomeWelcomeToAirpayVyaapaarContinue2Screen(TestContext context) {
        super(context);
    }
}
