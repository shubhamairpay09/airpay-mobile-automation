package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Shree krishna store. Module home. */
public class OpenCityAfterShreeKrishnaStoreScreen extends BaseScreen {

    public static final String PAGE_ID = "open-city-after-shree-krishna-store";

    public static final String SHREE_KRISHNA_STORE = "//android.widget.EditText[@text='Shree krishna store']";

    @AndroidFindBy(xpath = SHREE_KRISHNA_STORE)
    private WebElement shreeKrishnaStore;

    public static final String AQIPV5654R = "//android.widget.EditText[@text='AQIPV5654R']";

    @AndroidFindBy(xpath = AQIPV5654R)
    private WebElement aqipv5654r;

    public static final String PAN_NUMBER = "//android.widget.TextView[@text='PAN Number']";

    @AndroidFindBy(xpath = PAN_NUMBER)
    private WebElement panNumber;

    public static final String EL5BAPUJI = "//android.widget.EditText[@text='5 bapuji']";

    @AndroidFindBy(xpath = EL5BAPUJI)
    private WebElement el5Bapuji;

    public static final String ADDRESS_LINE = "//android.widget.TextView[@text='Address Line']";

    @AndroidFindBy(xpath = ADDRESS_LINE)
    private WebElement addressLine;

    public static final String UTTAR_PRADESH = "//android.widget.EditText[@text='Uttar Pradesh']";

    @AndroidFindBy(xpath = UTTAR_PRADESH)
    private WebElement uttarPradesh;

    public static final String STATE = "//android.widget.TextView[@text='State']";

    @AndroidFindBy(xpath = STATE)
    private WebElement state;

    public static final String SAHARANPUR = "//android.widget.EditText[@text='Saharanpur']";

    @AndroidFindBy(xpath = SAHARANPUR)
    private WebElement saharanpur;

    public static final String CITY = "//android.widget.TextView[@text='City']";

    @AndroidFindBy(xpath = CITY)
    private WebElement city;

    public static final String DISTRICT = "//android.widget.TextView[@text='District']";

    @AndroidFindBy(xpath = DISTRICT)
    private WebElement district;

    public static final String PLEASE_ENTER_VALID_DISTRICT = "//android.widget.TextView[@text='Please enter valid District']";

    @AndroidFindBy(xpath = PLEASE_ENTER_VALID_DISTRICT)
    private WebElement pleaseEnterValidDistrict;

    public static final String PIN_CODE = "//android.widget.TextView[@text='Pin Code']";

    @AndroidFindBy(xpath = PIN_CODE)
    private WebElement pinCode;

    public static final String PLEASE_ENTER_VALID_PIN_CODE = "//android.widget.TextView[@text='Please enter valid Pin Code']";

    @AndroidFindBy(xpath = PLEASE_ENTER_VALID_PIN_CODE)
    private WebElement pleaseEnterValidPinCode;

    public static final String KYC_DETAILS = "//android.widget.TextView[@text='KYC Details']";

    @AndroidFindBy(xpath = KYC_DETAILS)
    private WebElement kycDetails;

    public static final String DO_YOU_HAVE_GST_NUMBER = "//android.widget.TextView[@text='Do you have GST Number?']";

    @AndroidFindBy(xpath = DO_YOU_HAVE_GST_NUMBER)
    private WebElement doYouHaveGstNumber;

    public static final String I_AM_ARECOGNIZED_SMALL_BUSINESS = "//android.widget.TextView[@text='I am a recognized small business']";

    @AndroidFindBy(xpath = I_AM_ARECOGNIZED_SMALL_BUSINESS)
    private WebElement iAmARecognizedSmallBusiness;

    public static final String STORE_DETAILS = "//android.widget.TextView[@text='Store Details']";

    @AndroidFindBy(xpath = STORE_DETAILS)
    private WebElement storeDetails;

    public OpenCityAfterShreeKrishnaStoreScreen(TestContext context) {
        super(context);
    }
}
