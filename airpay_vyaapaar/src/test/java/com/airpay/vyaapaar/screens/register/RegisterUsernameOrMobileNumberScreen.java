package com.airpay.vyaapaar.screens.register;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Register — Username or Mobile number. Module register. */
public class RegisterUsernameOrMobileNumberScreen extends BaseScreen {

    public static final String PAGE_ID = "register-username-or-mobile-number";

    public static final String LOGIN_ICON = "//android.widget.ImageView[contains(@content-desc,'Login Icon')]";

    @AndroidFindBy(xpath = LOGIN_ICON)
    private WebElement loginIcon;

    public static final String USERNAME_OR_MOBILE_NUMBER = "//android.widget.TextView[@text='Username or Mobile number']";

    @AndroidFindBy(xpath = USERNAME_OR_MOBILE_NUMBER)
    private WebElement usernameOrMobileNumber;

    public static final String PASSWORD = "//android.widget.TextView[@text='Password']";

    @AndroidFindBy(xpath = PASSWORD)
    private WebElement password;

    public static final String FORGOT_PASSWORD = "//android.widget.TextView[@text='Forgot Password?']";

    @AndroidFindBy(xpath = FORGOT_PASSWORD)
    private WebElement forgotPassword;

    public static final String LOGIN = "//android.widget.TextView[@text='Login']";

    @AndroidFindBy(xpath = LOGIN)
    private WebElement login;

    public static final String OR = "//android.widget.TextView[@text='-OR-']";

    @AndroidFindBy(xpath = OR)
    private WebElement or;

    public static final String LOGIN_WITH_OTP = "//android.widget.TextView[@text='Login with OTP']";

    @AndroidFindBy(xpath = LOGIN_WITH_OTP)
    private WebElement loginWithOtp;

    public static final String DON_THAVE_AN_ACCOUNT_REGISTER_HERE = "//android.widget.TextView[@text=concat('Don',\"'\",'t have an account? Register Here')]";

    @AndroidFindBy(xpath = DON_THAVE_AN_ACCOUNT_REGISTER_HERE)
    private WebElement donTHaveAnAccountRegisterHere;

    public static final String EL3020STAGING_ACCESSIBILITY = "//android.widget.TextView[@text='3.0.20-staging-accessibility']";

    @AndroidFindBy(xpath = EL3020STAGING_ACCESSIBILITY)
    private WebElement el3020StagingAccessibility;

    public static final String ELEMENT = "//android.widget.EditText[@text='••••••••••']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public RegisterUsernameOrMobileNumberScreen(TestContext context) {
        super(context);
    }
}
