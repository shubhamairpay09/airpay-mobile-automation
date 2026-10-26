package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Transaction Volume (Annual) / 1L - 5L. Module home. */
public class OpenTransactionVolumeAnnual1l5lScreen extends BaseScreen {

    public static final String PAGE_ID = "open-transaction-volume-annual-1l-5l";

    public static final String SAHARANPUR = "//android.widget.EditText[@text='Saharanpur']";

    @AndroidFindBy(xpath = SAHARANPUR)
    private WebElement saharanpur;

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

    public static final String EL0555 = "//android.widget.EditText[@text='05: 55']";

    @AndroidFindBy(xpath = EL0555)
    private WebElement el0555;

    public static final String OPENING_TIME = "//android.widget.TextView[@text='Opening Time']";

    @AndroidFindBy(xpath = OPENING_TIME)
    private WebElement openingTime;

    public static final String CLOSING_TIME = "//android.widget.TextView[@text='Closing Time']";

    @AndroidFindBy(xpath = CLOSING_TIME)
    private WebElement closingTime;

    public static final String EL1L5L = "//android.widget.EditText[@text='1L - 5L']";

    @AndroidFindBy(xpath = EL1L5L)
    private WebElement el1l5l;

    public static final String TRANSACTION_VOLUME_ANNUAL = "//android.widget.TextView[@text='Transaction Volume (Annual)']";

    @AndroidFindBy(xpath = TRANSACTION_VOLUME_ANNUAL)
    private WebElement transactionVolumeAnnual;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String STORE_DETAILS = "//android.widget.TextView[@text='Store Details']";

    @AndroidFindBy(xpath = STORE_DETAILS)
    private WebElement storeDetails;

    public OpenTransactionVolumeAnnual1l5lScreen(TestContext context) {
        super(context);
    }
}
