package com.airpay.vyaapaar.screens.auth;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** OTP. Module auth. */
public class AuthOtpScreen extends BaseScreen {

    public static final String PAGE_ID = "auth-otp";

    public static final String OTP_VERIFICATION_ILLUSTRATION = "//android.widget.ImageView[contains(@content-desc,'OTP Verification Illustration')]";

    @AndroidFindBy(xpath = OTP_VERIFICATION_ILLUSTRATION)
    private WebElement otpVerificationIllustration;

    public static final String OTP_VERIFICATION = "//android.widget.TextView[@text='OTP Verification']";

    @AndroidFindBy(xpath = OTP_VERIFICATION)
    private WebElement otpVerification;

    public static final String PLEASE_ENTER_THE_OTP_SENT_TO_YOUR_MOBILE = "//android.widget.TextView[@text='Please enter the OTP sent to your mobile number.']";

    @AndroidFindBy(xpath = PLEASE_ENTER_THE_OTP_SENT_TO_YOUR_MOBILE)
    private WebElement pleaseEnterTheOtpSentToYourMobile;

    public static final String RESEND_OTP_IN0025 = "//android.widget.TextView[@text='Resend OTP in  00:25']";

    @AndroidFindBy(xpath = RESEND_OTP_IN0025)
    private WebElement resendOtpIn0025;

    public static final String VERIFY_OTP = "//android.widget.TextView[@text='Verify OTP']";

    @AndroidFindBy(xpath = VERIFY_OTP)
    private WebElement verifyOtp;

    public static final String BACK_ICON = "//android.view.View[contains(@content-desc,'Back Icon')]";

    @AndroidFindBy(xpath = BACK_ICON)
    private WebElement backIcon;

    public static final String EL10213 = "//android.widget.EditText[@text='10213']";

    @AndroidFindBy(xpath = EL10213)
    private WebElement el10213;

    public static final String RESEND_OTP_IN0016 = "//android.widget.TextView[@text='Resend OTP in  00:16']";

    @AndroidFindBy(xpath = RESEND_OTP_IN0016)
    private WebElement resendOtpIn0016;

    public static final String RESEND_OTP_IN0011 = "//android.widget.TextView[@text='Resend OTP in  00:11']";

    @AndroidFindBy(xpath = RESEND_OTP_IN0011)
    private WebElement resendOtpIn0011;

    public static final String RESEND_OTP_IN0010 = "//android.widget.TextView[@text='Resend OTP in  00:10']";

    @AndroidFindBy(xpath = RESEND_OTP_IN0010)
    private WebElement resendOtpIn0010;

    public AuthOtpScreen(TestContext context) {
        super(context);
    }
}
