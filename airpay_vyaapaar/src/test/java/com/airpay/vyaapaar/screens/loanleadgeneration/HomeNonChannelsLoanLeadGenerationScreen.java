package com.airpay.vyaapaar.screens.loanleadgeneration;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Loan Lead Generation. Module loan-lead-generation. */
public class HomeNonChannelsLoanLeadGenerationScreen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-loan-lead-generation";

    public static final String PIN_CODE = "//android.widget.TextView[@text='PIN code']";

    @AndroidFindBy(xpath = PIN_CODE)
    private WebElement pinCode;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String FIRST_NAME = "//android.widget.TextView[@text='First name']";

    @AndroidFindBy(xpath = FIRST_NAME)
    private WebElement firstName;

    public static final String LAST_NAME = "//android.widget.TextView[@text='Last name']";

    @AndroidFindBy(xpath = LAST_NAME)
    private WebElement lastName;

    public static final String EMAIL = "//android.widget.TextView[@text='Email']";

    @AndroidFindBy(xpath = EMAIL)
    private WebElement email;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String LOAN_LEAD_GENERATION = "//android.widget.TextView[@text='Loan Lead Generation']";

    @AndroidFindBy(xpath = LOAN_LEAD_GENERATION)
    private WebElement loanLeadGeneration;

    public HomeNonChannelsLoanLeadGenerationScreen(TestContext context) {
        super(context);
    }
}
