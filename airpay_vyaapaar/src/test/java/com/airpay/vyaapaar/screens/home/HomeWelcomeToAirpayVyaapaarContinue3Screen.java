package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Welcome to airpay vyaapaar / Continue / City / 24 Parganas (n). Module home. */
public class HomeWelcomeToAirpayVyaapaarContinue3Screen extends BaseScreen {

    public static final String PAGE_ID = "home-welcome-to-airpay-vyaapaar-continue-city-24-parganas-n";

    public static final String EL24PARGANAS_N = "//android.widget.EditText[@text='24 Parganas (n)']";

    @AndroidFindBy(xpath = EL24PARGANAS_N)
    private WebElement el24ParganasN;

    public static final String CITY = "//android.widget.TextView[@text='City']";

    @AndroidFindBy(xpath = CITY)
    private WebElement city;

    public static final String DISTRICT = "//android.widget.TextView[@text='District']";

    @AndroidFindBy(xpath = DISTRICT)
    private WebElement district;

    public static final String PIN_CODE = "//android.widget.TextView[@text='Pin Code']";

    @AndroidFindBy(xpath = PIN_CODE)
    private WebElement pinCode;

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

    public static final String STORE_DETAILS = "//android.widget.TextView[@text='Store Details']";

    @AndroidFindBy(xpath = STORE_DETAILS)
    private WebElement storeDetails;

    public HomeWelcomeToAirpayVyaapaarContinue3Screen(TestContext context) {
        super(context);
    }
}
