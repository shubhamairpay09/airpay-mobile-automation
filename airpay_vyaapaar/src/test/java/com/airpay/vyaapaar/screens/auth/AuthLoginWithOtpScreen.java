package com.airpay.vyaapaar.screens.auth;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Login with OTP. Module auth. */
public class AuthLoginWithOtpScreen extends BaseScreen {

    public static final String PAGE_ID = "auth-login-with-otp";

    public static final String LOGIN_ICON = "//android.widget.ImageView[contains(@content-desc,'Login Icon')]";

    @AndroidFindBy(xpath = LOGIN_ICON)
    private WebElement loginIcon;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String MPIN = "//android.widget.TextView[@text='MPIN']";

    @AndroidFindBy(xpath = MPIN)
    private WebElement mpin;

    public static final String VISIBILITY_OFF_ICON = "//android.view.View[contains(@content-desc,'Visibility Off Icon')]";

    @AndroidFindBy(xpath = VISIBILITY_OFF_ICON)
    private WebElement visibilityOffIcon;

    public static final String LOGIN = "//android.widget.TextView[@text='Login']";

    @AndroidFindBy(xpath = LOGIN)
    private WebElement login;

    public static final String OR = "//android.widget.TextView[@text='-OR-']";

    @AndroidFindBy(xpath = OR)
    private WebElement or;

    public static final String LOGIN_WITH_PASSWORD = "//android.widget.TextView[@text='Login with password']";

    @AndroidFindBy(xpath = LOGIN_WITH_PASSWORD)
    private WebElement loginWithPassword;

    public static final String DON_THAVE_AN_ACCOUNT_REGISTER_HERE = "//android.widget.TextView[@text=concat('Don',\"'\",'t have an account? Register Here')]";

    @AndroidFindBy(xpath = DON_THAVE_AN_ACCOUNT_REGISTER_HERE)
    private WebElement donTHaveAnAccountRegisterHere;

    public static final String EL3020STAGING_ACCESSIBILITY = "//android.widget.TextView[@text='3.0.20-staging-accessibility']";

    @AndroidFindBy(xpath = EL3020STAGING_ACCESSIBILITY)
    private WebElement el3020StagingAccessibility;

    public AuthLoginWithOtpScreen(TestContext context) {
        super(context);
    }
}
