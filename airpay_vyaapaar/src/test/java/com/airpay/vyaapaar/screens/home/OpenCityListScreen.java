package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Select City. Module home. */
public class OpenCityListScreen extends BaseScreen {

    public static final String PAGE_ID = "open-city-list";

    public static final String SELECT_CITY = "//android.widget.TextView[@text='Select City']";

    @AndroidFindBy(xpath = SELECT_CITY)
    private WebElement selectCity;

    public static final String A = "//android.widget.TextView[@text='A']";

    @AndroidFindBy(xpath = A)
    private WebElement a;

    public static final String ACHHALDA = "//android.widget.TextView[@text='Achhalda']";

    @AndroidFindBy(xpath = ACHHALDA)
    private WebElement achhalda;

    public static final String ACHHNERA = "//android.widget.TextView[@text='Achhnera']";

    @AndroidFindBy(xpath = ACHHNERA)
    private WebElement achhnera;

    public static final String ADARI = "//android.widget.TextView[@text='Adari']";

    @AndroidFindBy(xpath = ADARI)
    private WebElement adari;

    public static final String AFZALGARH = "//android.widget.TextView[@text='Afzalgarh']";

    @AndroidFindBy(xpath = AFZALGARH)
    private WebElement afzalgarh;

    public static final String AGARWAL_MANDI = "//android.widget.TextView[@text='Agarwal Mandi']";

    @AndroidFindBy(xpath = AGARWAL_MANDI)
    private WebElement agarwalMandi;

    public static final String AGRA = "//android.widget.TextView[@text='Agra']";

    @AndroidFindBy(xpath = AGRA)
    private WebElement agra;

    public static final String AGRA_CANTONMENT = "//android.widget.TextView[@text='Agra Cantonment']";

    @AndroidFindBy(xpath = AGRA_CANTONMENT)
    private WebElement agraCantonment;

    public static final String AHRAURA = "//android.widget.TextView[@text='Ahraura']";

    @AndroidFindBy(xpath = AHRAURA)
    private WebElement ahraura;

    public static final String AILUM = "//android.widget.TextView[@text='Ailum']";

    @AndroidFindBy(xpath = AILUM)
    private WebElement ailum;

    public static final String AIR_FORCE_AREA = "//android.widget.TextView[@text='Air Force Area']";

    @AndroidFindBy(xpath = AIR_FORCE_AREA)
    private WebElement airForceArea;

    public static final String AJHUWA = "//android.widget.TextView[@text='Ajhuwa']";

    @AndroidFindBy(xpath = AJHUWA)
    private WebElement ajhuwa;

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public OpenCityListScreen(TestContext context) {
        super(context);
    }
}
