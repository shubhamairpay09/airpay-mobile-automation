package com.airpay.vyaapaar.screens.panapplication;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / PAN Application — Full Name (As per Aadhaar). Module pan-application. */
public class HomeCreditBalancePanApplicationScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-pan-application";

    public static final String GET_YOUR_PAN_CARD_START_APPLICATION_NOW = "//android.widget.TextView[@text='Get Your PAN Card - Start Application Now']";

    @AndroidFindBy(xpath = GET_YOUR_PAN_CARD_START_APPLICATION_NOW)
    private WebElement getYourPanCardStartApplicationNow;

    public static final String ENTER_YOUR_DETAILS_BELOW_AND_WE_WILL_REDIRECT = "//android.widget.TextView[@text='Enter your details below and we will redirect you to Protean(NSDL)to complete your PAN card process']";

    @AndroidFindBy(xpath = ENTER_YOUR_DETAILS_BELOW_AND_WE_WILL_REDIRECT)
    private WebElement enterYourDetailsBelowAndWeWillRedirect;

    public static final String FULL_NAME_AS_PER_AADHAAR = "//android.widget.TextView[@text='Full Name (As per Aadhaar)']";

    @AndroidFindBy(xpath = FULL_NAME_AS_PER_AADHAAR)
    private WebElement fullNameAsPerAadhaar;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String DOB = "//android.widget.TextView[@text='DOB']";

    @AndroidFindBy(xpath = DOB)
    private WebElement dob;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String PAN_APPLICATION = "//android.widget.TextView[@text='PAN Application']";

    @AndroidFindBy(xpath = PAN_APPLICATION)
    private WebElement panApplication;

    public HomeCreditBalancePanApplicationScreen(TestContext context) {
        super(context);
    }
}
