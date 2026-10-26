package com.airpay.vyaapaar.screens.auth;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Forgot Password — Forgot Password. Module auth. */
public class AuthForgotPasswordScreen extends BaseScreen {

    public static final String PAGE_ID = "auth-forgot-password";

    public static final String FORGOT_PASSWORD_ILLUSTRATION = "//android.view.View[contains(@content-desc,'Forgot Password Illustration')]";

    @AndroidFindBy(xpath = FORGOT_PASSWORD_ILLUSTRATION)
    private WebElement forgotPasswordIllustration;

    public static final String FORGOT_PASSWORD = "//android.widget.TextView[@text='Forgot Password']";

    @AndroidFindBy(xpath = FORGOT_PASSWORD)
    private WebElement forgotPassword;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String BACK_TO_LOGIN = "//android.widget.TextView[@text='Back to Login']";

    @AndroidFindBy(xpath = BACK_TO_LOGIN)
    private WebElement backToLogin;

    public static final String BACK_ICON = "//android.view.View[contains(@content-desc,'Back Icon')]";

    @AndroidFindBy(xpath = BACK_ICON)
    private WebElement backIcon;

    public AuthForgotPasswordScreen(TestContext context) {
        super(context);
    }
}
