package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Welcome to airpay vyaapaar / Continue. Module home. */
public class HomeWelcomeToAirpayVyaapaarContinueScreen extends BaseScreen {

    public static final String PAGE_ID = "home-welcome-to-airpay-vyaapaar-continue";

    public static final String SHOP_DETAILS = "//android.widget.TextView[@text='Shop Details']";

    @AndroidFindBy(xpath = SHOP_DETAILS)
    private WebElement shopDetails;

    public static final String STORE_COMPANY_NAME = "//android.widget.TextView[@text='Store/Company Name']";

    @AndroidFindBy(xpath = STORE_COMPANY_NAME)
    private WebElement storeCompanyName;

    public static final String PAN_NUMBER = "//android.widget.TextView[@text='PAN Number']";

    @AndroidFindBy(xpath = PAN_NUMBER)
    private WebElement panNumber;

    public static final String ADDRESS_LINE = "//android.widget.TextView[@text='Address Line']";

    @AndroidFindBy(xpath = ADDRESS_LINE)
    private WebElement addressLine;

    public static final String DISTRICT = "//android.widget.TextView[@text='District']";

    @AndroidFindBy(xpath = DISTRICT)
    private WebElement district;

    public static final String PIN_CODE = "//android.widget.TextView[@text='Pin Code']";

    @AndroidFindBy(xpath = PIN_CODE)
    private WebElement pinCode;

    public static final String STORE_DETAILS = "//android.widget.TextView[@text='Store Details']";

    @AndroidFindBy(xpath = STORE_DETAILS)
    private WebElement storeDetails;

    public static final String SHUBHAM_VERMA = "//android.widget.EditText[@text='Shubham Verma']";

    @AndroidFindBy(xpath = SHUBHAM_VERMA)
    private WebElement shubhamVerma;

    public static final String CITY = "//android.widget.TextView[@text='City']";

    @AndroidFindBy(xpath = CITY)
    private WebElement city;

    public static final String KYC_DETAILS = "//android.widget.TextView[@text='KYC Details']";

    @AndroidFindBy(xpath = KYC_DETAILS)
    private WebElement kycDetails;

    public static final String DO_YOU_HAVE_GST_NUMBER = "//android.widget.TextView[@text='Do you have GST Number?']";

    @AndroidFindBy(xpath = DO_YOU_HAVE_GST_NUMBER)
    private WebElement doYouHaveGstNumber;

    public static final String I_AM_ARECOGNIZED_SMALL_BUSINESS = "//android.widget.TextView[@text='I am a recognized small business']";

    @AndroidFindBy(xpath = I_AM_ARECOGNIZED_SMALL_BUSINESS)
    private WebElement iAmARecognizedSmallBusiness;

    public static final String OPERATING_HOURS = "//android.widget.TextView[@text='Operating Hours']";

    @AndroidFindBy(xpath = OPERATING_HOURS)
    private WebElement operatingHours;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public HomeWelcomeToAirpayVyaapaarContinueScreen(TestContext context) {
        super(context);
    }
}
