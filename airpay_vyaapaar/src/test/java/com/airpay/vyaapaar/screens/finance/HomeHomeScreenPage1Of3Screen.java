package com.airpay.vyaapaar.screens.finance;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Home screen Page 1 of 3/Finance/Secured Credit Cards/Credilio Cards. Module finance. */
public class HomeHomeScreenPage1Of3Screen extends BaseScreen {

    public static final String PAGE_ID = "home-home-screen-page-1-of-3-finance-secured-credit-cards-credilio-cards";

    public static final String START_YOUR_CREDIT_JOURNEY_SECURELY_LOGO = "//android.view.View[contains(@content-desc,'Start Your Credit Journey Securely! Logo')]";

    @AndroidFindBy(xpath = START_YOUR_CREDIT_JOURNEY_SECURELY_LOGO)
    private WebElement startYourCreditJourneySecurelyLogo;

    public static final String START_YOUR_CREDIT_JOURNEY_SECURELY = "//android.widget.TextView[@text='Start Your Credit Journey Securely!']";

    @AndroidFindBy(xpath = START_YOUR_CREDIT_JOURNEY_SECURELY)
    private WebElement startYourCreditJourneySecurely;

    public static final String EL3QUICK_FIELDS_WE_LL_TAKE_YOU_TO = "//android.widget.TextView[@text=concat('3 quick fields - we',\"'\",'ll take you to Credilio to finish instantly.')]";

    @AndroidFindBy(xpath = EL3QUICK_FIELDS_WE_LL_TAKE_YOU_TO)
    private WebElement el3QuickFieldsWeLlTakeYouTo;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String NAME_AS_PER_PAN = "//android.widget.TextView[@text='Name as Per PAN']";

    @AndroidFindBy(xpath = NAME_AS_PER_PAN)
    private WebElement nameAsPerPan;

    public static final String PINCODE = "//android.widget.TextView[@text='Pincode']";

    @AndroidFindBy(xpath = PINCODE)
    private WebElement pincode;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String CREDILIO_CARDS = "//android.widget.TextView[@text='Credilio Cards']";

    @AndroidFindBy(xpath = CREDILIO_CARDS)
    private WebElement credilioCards;

    public HomeHomeScreenPage1Of3Screen(TestContext context) {
        super(context);
    }
}
