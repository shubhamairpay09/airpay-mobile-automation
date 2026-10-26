package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Shop Details / District. Module home. */
public class HomeShopDetailsDropdownDistrictScreen extends BaseScreen {

    public static final String PAGE_ID = "home-shop-details-dropdown-district";

    public static final String STORE_COMPANY_NAME = "//android.widget.TextView[@text='Store/Company Name']";

    @AndroidFindBy(xpath = STORE_COMPANY_NAME)
    private WebElement storeCompanyName;

    public static final String SHUBHAM_VERMA = "//android.widget.EditText[@text='Shubham Verma']";

    @AndroidFindBy(xpath = SHUBHAM_VERMA)
    private WebElement shubhamVerma;

    public static final String PAN_NUMBER = "//android.widget.TextView[@text='PAN Number']";

    @AndroidFindBy(xpath = PAN_NUMBER)
    private WebElement panNumber;

    public static final String PLEASE_ENTER_VALID_PAN_NUMBER = "//android.widget.TextView[@text='Please enter valid PAN Number']";

    @AndroidFindBy(xpath = PLEASE_ENTER_VALID_PAN_NUMBER)
    private WebElement pleaseEnterValidPanNumber;

    public static final String AQIPV5654R = "//android.widget.EditText[@text='AQIPV5654R']";

    @AndroidFindBy(xpath = AQIPV5654R)
    private WebElement aqipv5654r;

    public static final String ADDRESS_LINE = "//android.widget.TextView[@text='Address Line']";

    @AndroidFindBy(xpath = ADDRESS_LINE)
    private WebElement addressLine;

    public static final String SAHARANPUR = "//android.widget.EditText[@text='Saharanpur']";

    @AndroidFindBy(xpath = SAHARANPUR)
    private WebElement saharanpur;

    public static final String CITY = "//android.widget.TextView[@text='City']";

    @AndroidFindBy(xpath = CITY)
    private WebElement city;

    public static final String DISTRICT = "//android.widget.TextView[@text='District']";

    @AndroidFindBy(xpath = DISTRICT)
    private WebElement district;

    public static final String SHOP_DETAILS = "//android.widget.TextView[@text='Shop Details']";

    @AndroidFindBy(xpath = SHOP_DETAILS)
    private WebElement shopDetails;

    public static final String PLEASE_ENTER_VALID_DISTRICT = "//android.widget.TextView[@text='Please enter valid District']";

    @AndroidFindBy(xpath = PLEASE_ENTER_VALID_DISTRICT)
    private WebElement pleaseEnterValidDistrict;

    public static final String PIN_CODE = "//android.widget.TextView[@text='Pin Code']";

    @AndroidFindBy(xpath = PIN_CODE)
    private WebElement pinCode;

    public static final String STORE_DETAILS = "//android.widget.TextView[@text='Store Details']";

    @AndroidFindBy(xpath = STORE_DETAILS)
    private WebElement storeDetails;

    public HomeShopDetailsDropdownDistrictScreen(TestContext context) {
        super(context);
    }
}
