package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Select Province. Module home. */
public class OpenSelectProvinceScreen extends BaseScreen {

    public static final String PAGE_ID = "open-select-province";

    public static final String SELECT_PROVINCE = "//android.widget.TextView[@text='Select Province']";

    @AndroidFindBy(xpath = SELECT_PROVINCE)
    private WebElement selectProvince;

    public static final String A = "//android.widget.TextView[@text='A']";

    @AndroidFindBy(xpath = A)
    private WebElement a;

    public static final String ANDAMAN_AND_NICOBAR_ISLANDS = "//android.widget.TextView[@text='Andaman and Nicobar Islands']";

    @AndroidFindBy(xpath = ANDAMAN_AND_NICOBAR_ISLANDS)
    private WebElement andamanAndNicobarIslands;

    public static final String ANDHRA_PRADESH = "//android.widget.TextView[@text='Andhra Pradesh']";

    @AndroidFindBy(xpath = ANDHRA_PRADESH)
    private WebElement andhraPradesh;

    public static final String ARUNACHAL_PRADESH = "//android.widget.TextView[@text='Arunachal Pradesh']";

    @AndroidFindBy(xpath = ARUNACHAL_PRADESH)
    private WebElement arunachalPradesh;

    public static final String ASSAM = "//android.widget.TextView[@text='Assam']";

    @AndroidFindBy(xpath = ASSAM)
    private WebElement assam;

    public static final String B = "//android.widget.TextView[@text='B']";

    @AndroidFindBy(xpath = B)
    private WebElement b;

    public static final String BIHAR = "//android.widget.TextView[@text='Bihar']";

    @AndroidFindBy(xpath = BIHAR)
    private WebElement bihar;

    public static final String C = "//android.widget.TextView[@text='C']";

    @AndroidFindBy(xpath = C)
    private WebElement c;

    public static final String CHANDIGARH = "//android.widget.TextView[@text='Chandigarh']";

    @AndroidFindBy(xpath = CHANDIGARH)
    private WebElement chandigarh;

    public static final String CHHATTISGARH = "//android.widget.TextView[@text='Chhattisgarh']";

    @AndroidFindBy(xpath = CHHATTISGARH)
    private WebElement chhattisgarh;

    public static final String D = "//android.widget.TextView[@text='D']";

    @AndroidFindBy(xpath = D)
    private WebElement d;

    public static final String DADRA_AND_NAGAR_HAVELI = "//android.widget.TextView[@text='Dadra and Nagar Haveli']";

    @AndroidFindBy(xpath = DADRA_AND_NAGAR_HAVELI)
    private WebElement dadraAndNagarHaveli;

    public static final String DAMAN_AND_DIU = "//android.widget.TextView[@text='Daman and Diu']";

    @AndroidFindBy(xpath = DAMAN_AND_DIU)
    private WebElement damanAndDiu;

    public static final String DELHI = "//android.widget.TextView[@text='Delhi']";

    @AndroidFindBy(xpath = DELHI)
    private WebElement delhi;

    public static final String G = "//android.widget.TextView[@text='G']";

    @AndroidFindBy(xpath = G)
    private WebElement g;

    public static final String GOA = "//android.widget.TextView[@text='Goa']";

    @AndroidFindBy(xpath = GOA)
    private WebElement goa;

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public OpenSelectProvinceScreen(TestContext context) {
        super(context);
    }
}
