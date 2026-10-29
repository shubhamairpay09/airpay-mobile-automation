package com.airpay.vyaapaar.screens.auth;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Login. Module auth. */
public class AuthLoginWallScreen extends BaseScreen {

    public static final String PAGE_ID = "auth-login-wall";

    public static final String LOGIN_ICON = "//android.widget.ImageView[contains(@content-desc,'Login Icon')]";

    @AndroidFindBy(xpath = LOGIN_ICON)
    private WebElement loginIcon;

    public static final String USERNAME_OR_MOBILE_NUMBER = "//android.view.View[contains(@content-desc,'Username or Mobile number')]";

    @AndroidFindBy(xpath = USERNAME_OR_MOBILE_NUMBER)
    private WebElement usernameOrMobileNumber;

    public static final String PASSWORD = "//android.view.View[contains(@content-desc,'Password')]";

    @AndroidFindBy(xpath = PASSWORD)
    private WebElement password;

    public static final String FORGOT_PASSWORD = "//android.widget.TextView[contains(@content-desc,'Forgot Password?')]";

    @AndroidFindBy(xpath = FORGOT_PASSWORD)
    private WebElement forgotPassword;

    public static final String LOGIN = "//android.widget.TextView[@text='Login']";

    @AndroidFindBy(xpath = LOGIN)
    private WebElement login;

    public static final String OR = "//android.widget.TextView[contains(@content-desc,'-OR-')]";

    @AndroidFindBy(xpath = OR)
    private WebElement or;

    public static final String LOGIN_WITH_OTP = "//android.widget.TextView[contains(@content-desc,'Login with OTP')]";

    @AndroidFindBy(xpath = LOGIN_WITH_OTP)
    private WebElement loginWithOtp;

    public static final String DON_THAVE_AN_ACCOUNT_REGISTER_HERE = "//android.widget.TextView[@text=concat('Don',\"'\",'t have an account? Register Here')]";

    @AndroidFindBy(xpath = DON_THAVE_AN_ACCOUNT_REGISTER_HERE)
    private WebElement donTHaveAnAccountRegisterHere;

    public static final String EL3020STAGING_ACCESSIBILITY = "//android.widget.TextView[contains(@content-desc,'3.0.20-staging-accessibility')]";

    @AndroidFindBy(xpath = EL3020STAGING_ACCESSIBILITY)
    private WebElement el3020StagingAccessibility;

    public AuthLoginWallScreen(TestContext context) {
        super(context);
    }
}
