package com.airpay.vyaapaar.screens.electricity;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Recharge & Billpay/Electricity. Module electricity. */
public class HomeRechargeBillpayElectricityScreen extends BaseScreen {

    public static final String PAGE_ID = "home-recharge-billpay-electricity";

    public static final String SELECT_STATE = "//android.widget.TextView[@text='Select State']";

    @AndroidFindBy(xpath = SELECT_STATE)
    private WebElement selectState;

    public static final String A = "//android.widget.TextView[contains(@content-desc,'A')]";

    @AndroidFindBy(xpath = A)
    private WebElement a;

    public static final String ANDAMAN_AND_NICOBAR_ISLANDS = "//android.widget.TextView[contains(@content-desc,'Andaman and Nicobar Islands')]";

    @AndroidFindBy(xpath = ANDAMAN_AND_NICOBAR_ISLANDS)
    private WebElement andamanAndNicobarIslands;

    public static final String ANDHRA_PRADESH = "//android.widget.TextView[contains(@content-desc,'Andhra Pradesh')]";

    @AndroidFindBy(xpath = ANDHRA_PRADESH)
    private WebElement andhraPradesh;

    public static final String ARUNACHAL_PRADESH = "//android.widget.TextView[contains(@content-desc,'Arunachal Pradesh')]";

    @AndroidFindBy(xpath = ARUNACHAL_PRADESH)
    private WebElement arunachalPradesh;

    public static final String ASSAM = "//android.widget.TextView[contains(@content-desc,'Assam')]";

    @AndroidFindBy(xpath = ASSAM)
    private WebElement assam;

    public static final String B = "//android.widget.TextView[contains(@content-desc,'B')]";

    @AndroidFindBy(xpath = B)
    private WebElement b;

    public static final String BIHAR = "//android.widget.TextView[contains(@content-desc,'Bihar')]";

    @AndroidFindBy(xpath = BIHAR)
    private WebElement bihar;

    public static final String C = "//android.widget.TextView[contains(@content-desc,'C')]";

    @AndroidFindBy(xpath = C)
    private WebElement c;

    public static final String CHANDIGARH = "//android.widget.TextView[contains(@content-desc,'Chandigarh')]";

    @AndroidFindBy(xpath = CHANDIGARH)
    private WebElement chandigarh;

    public static final String CHHATTISGARH = "//android.widget.TextView[contains(@content-desc,'Chhattisgarh')]";

    @AndroidFindBy(xpath = CHHATTISGARH)
    private WebElement chhattisgarh;

    public static final String D = "//android.widget.TextView[contains(@content-desc,'D')]";

    @AndroidFindBy(xpath = D)
    private WebElement d;

    public static final String DADRA_AND_NAGAR_HAVELI = "//android.widget.TextView[contains(@content-desc,'Dadra and Nagar Haveli')]";

    @AndroidFindBy(xpath = DADRA_AND_NAGAR_HAVELI)
    private WebElement dadraAndNagarHaveli;

    public static final String DAMAN_AND_DIU = "//android.widget.TextView[contains(@content-desc,'Daman and Diu')]";

    @AndroidFindBy(xpath = DAMAN_AND_DIU)
    private WebElement damanAndDiu;

    public static final String DELHI = "//android.widget.TextView[contains(@content-desc,'Delhi')]";

    @AndroidFindBy(xpath = DELHI)
    private WebElement delhi;

    public static final String G = "//android.widget.TextView[contains(@content-desc,'G')]";

    @AndroidFindBy(xpath = G)
    private WebElement g;

    public static final String GOA = "//android.widget.TextView[contains(@content-desc,'Goa')]";

    @AndroidFindBy(xpath = GOA)
    private WebElement goa;

    public static final String GUJARAT = "//android.widget.TextView[contains(@content-desc,'Gujarat')]";

    @AndroidFindBy(xpath = GUJARAT)
    private WebElement gujarat;

    public HomeRechargeBillpayElectricityScreen(TestContext context) {
        super(context);
    }
}
