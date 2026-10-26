package com.airpay.vyaapaar.screens.register;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Register — If you didn't receive a OTP!  Resend. Module register. */
public class RegisterIfYouDidnTReceiveScreen extends BaseScreen {

    public static final String PAGE_ID = "register-if-you-didn-t-receive-a-otp-resend";

    public static final String OTP_HAS_BEEN_SENT_TO_YOU_ON_YOUR = "//android.widget.TextView[@text='OTP has been sent to you on your mobile number, please enter it below']";

    @AndroidFindBy(xpath = OTP_HAS_BEEN_SENT_TO_YOU_ON_YOUR)
    private WebElement otpHasBeenSentToYouOnYour;

    public static final String IF_YOU_DIDN_TRECEIVE_AOTP_RESEND = "//android.widget.TextView[@text=concat('If you didn',\"'\",'t receive a OTP!  Resend')]";

    @AndroidFindBy(xpath = IF_YOU_DIDN_TRECEIVE_AOTP_RESEND)
    private WebElement ifYouDidnTReceiveAOtpResend;

    public static final String VERIFY_OTP = "//android.widget.TextView[@text='Verify OTP']";

    @AndroidFindBy(xpath = VERIFY_OTP)
    private WebElement verifyOtp;

    public RegisterIfYouDidnTReceiveScreen(TestContext context) {
        super(context);
    }
}
