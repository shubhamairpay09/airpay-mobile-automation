package com.airpay.vyaapaar.screens.city;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Shop Details / City / Saharanpur. Module city. */
public class HomeShopDetailsCitySaharanpurScreen extends BaseScreen {

    public static final String PAGE_ID = "home-shop-details-city-saharanpur";

    public static final String SAHARANPUR = "//android.widget.EditText[@text='Saharanpur']";

    @AndroidFindBy(xpath = SAHARANPUR)
    private WebElement saharanpur;

    public static final String DISTRICT = "//android.widget.TextView[@text='District']";

    @AndroidFindBy(xpath = DISTRICT)
    private WebElement district;

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

    public static final String OPERATING_HOURS = "//android.widget.TextView[@text='Operating Hours']";

    @AndroidFindBy(xpath = OPERATING_HOURS)
    private WebElement operatingHours;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String STORE_DETAILS = "//android.widget.TextView[@text='Store Details']";

    @AndroidFindBy(xpath = STORE_DETAILS)
    private WebElement storeDetails;

    public HomeShopDetailsCitySaharanpurScreen(TestContext context) {
        super(context);
    }
}
