package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Welcome to airpay vyaapaar. Module home. */
public class HomeWelcomeToAirpayVyaapaarOpen2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-welcome-to-airpay-vyaapaar-open-continue-open-continue";

    public static final String WELCOME_TO_AIRPAY_VYAAPAAR = "//android.widget.TextView[@text='Welcome to airpay vyaapaar']";

    @AndroidFindBy(xpath = WELCOME_TO_AIRPAY_VYAAPAAR)
    private WebElement welcomeToAirpayVyaapaar;

    public static final String TRANSFORM_YOUR_BUSINESS_WITH_AIRPAY_VYAAPAAR_ACCEPT_DIGITAL = "//android.widget.TextView[@text='Transform your business with airpay vyaapaar. Accept digital payments, manage transactions, and grow your business seamlessly.']";

    @AndroidFindBy(xpath = TRANSFORM_YOUR_BUSINESS_WITH_AIRPAY_VYAAPAAR_ACCEPT_DIGITAL)
    private WebElement transformYourBusinessWithAirpayVyaapaarAcceptDigital;

    public static final String ICON = "//android.view.View[contains(@content-desc,'Icon')]";

    @AndroidFindBy(xpath = ICON)
    private WebElement icon;

    public static final String TYPE_SELECTION = "//android.widget.TextView[@text='Type Selection']";

    @AndroidFindBy(xpath = TYPE_SELECTION)
    private WebElement typeSelection;

    public static final String CHOOSE_YOUR_ROLE_AND_TYPE = "//android.widget.TextView[@text='Choose your role and type']";

    @AndroidFindBy(xpath = CHOOSE_YOUR_ROLE_AND_TYPE)
    private WebElement chooseYourRoleAndType;

    public static final String BUSINESS_DETAILS = "//android.widget.TextView[@text='Business Details']";

    @AndroidFindBy(xpath = BUSINESS_DETAILS)
    private WebElement businessDetails;

    public static final String ADD_UPDATE_YOUR_BUSINESS_DETAILS = "//android.widget.TextView[@text='Add/Update your business details']";

    @AndroidFindBy(xpath = ADD_UPDATE_YOUR_BUSINESS_DETAILS)
    private WebElement addUpdateYourBusinessDetails;

    public static final String EL_CONTINUE = "//android.widget.TextView[@text='Continue']";

    @AndroidFindBy(xpath = EL_CONTINUE)
    private WebElement elContinue;

    public static final String UPLOAD_DASHBOARD = "//android.widget.TextView[@text='Upload Dashboard']";

    @AndroidFindBy(xpath = UPLOAD_DASHBOARD)
    private WebElement uploadDashboard;

    public static final String UPLOAD_REQUIRED_DOCUMENTS = "//android.widget.TextView[@text='Upload required documents']";

    @AndroidFindBy(xpath = UPLOAD_REQUIRED_DOCUMENTS)
    private WebElement uploadRequiredDocuments;

    public static final String PERSONAL_DETAILS = "//android.widget.TextView[@text='Personal Details']";

    @AndroidFindBy(xpath = PERSONAL_DETAILS)
    private WebElement personalDetails;

    public static final String ADD_UPDATE_YOUR_PERSONAL_DETAILS = "//android.widget.TextView[@text='Add/Update your personal details']";

    @AndroidFindBy(xpath = ADD_UPDATE_YOUR_PERSONAL_DETAILS)
    private WebElement addUpdateYourPersonalDetails;

    public static final String E_SIGN_AGREEMENT = "//android.widget.TextView[@text='E-Sign Agreement']";

    @AndroidFindBy(xpath = E_SIGN_AGREEMENT)
    private WebElement eSignAgreement;

    public static final String DIGITALLY_SIGN_THE_AGREEMENT = "//android.widget.TextView[@text='Digitally sign the agreement']";

    @AndroidFindBy(xpath = DIGITALLY_SIGN_THE_AGREEMENT)
    private WebElement digitallySignTheAgreement;

    public static final String ONBOARDING_COMPLETE = "//android.widget.TextView[@text='Onboarding Complete']";

    @AndroidFindBy(xpath = ONBOARDING_COMPLETE)
    private WebElement onboardingComplete;

    public static final String ONBOARDING_COMPLETED_SUCCESSFULLY_KINDLY_WAIT_FOR_CONFIRMATION = "//android.widget.TextView[@text='Onboarding completed successfully, kindly wait for confirmation']";

    @AndroidFindBy(xpath = ONBOARDING_COMPLETED_SUCCESSFULLY_KINDLY_WAIT_FOR_CONFIRMATION)
    private WebElement onboardingCompletedSuccessfullyKindlyWaitForConfirmation;

    public static final String ONBOARDING_PROGRESS = "//android.widget.TextView[@text='Onboarding Progress']";

    @AndroidFindBy(xpath = ONBOARDING_PROGRESS)
    private WebElement onboardingProgress;

    public HomeWelcomeToAirpayVyaapaarOpen2Screen(TestContext context) {
        super(context);
    }
}
