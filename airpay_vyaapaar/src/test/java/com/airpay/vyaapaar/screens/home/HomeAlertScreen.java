package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Alert. Module home. */
public class HomeAlertScreen extends BaseScreen {

    public static final String PAGE_ID = "home-alert";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String LEAD_ALREADY_REGISTERED_OR_REJECTED = "//android.widget.TextView[@text='Lead already registered or rejected']";

    @AndroidFindBy(xpath = LEAD_ALREADY_REGISTERED_OR_REJECTED)
    private WebElement leadAlreadyRegisteredOrRejected;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public static final String WELCOME_IMAGE = "//android.view.View[contains(@content-desc,'Welcome image')]";

    @AndroidFindBy(xpath = WELCOME_IMAGE)
    private WebElement welcomeImage;

    public static final String WELCOME = "//android.widget.TextView[@text='Welcome']";

    @AndroidFindBy(xpath = WELCOME)
    private WebElement welcome;

    public static final String CREATE_NEW_ACCOUNT_AND_REGISTER_NOW = "//android.widget.TextView[@text='Create new account and register now !']";

    @AndroidFindBy(xpath = CREATE_NEW_ACCOUNT_AND_REGISTER_NOW)
    private WebElement createNewAccountAndRegisterNow;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String I_AGREE_TO_PRIVACY_POLICY_AND_TERMS_AND = "//android.widget.TextView[@text='I agree to privacy policy and terms and conditions']";

    @AndroidFindBy(xpath = I_AGREE_TO_PRIVACY_POLICY_AND_TERMS_AND)
    private WebElement iAgreeToPrivacyPolicyAndTermsAnd;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String BACK_ICON = "//android.view.View[contains(@content-desc,'Back Icon')]";

    @AndroidFindBy(xpath = BACK_ICON)
    private WebElement backIcon;

    public HomeAlertScreen(TestContext context) {
        super(context);
    }
}
