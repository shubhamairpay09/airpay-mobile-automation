package com.airpay.vyaapaar.screens.register;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Register — Verify OTP. Module register. */
public class RegisterVerifyOtpScreen extends BaseScreen {

    public static final String PAGE_ID = "register-verify-otp";

    public static final String OTP_HAS_BEEN_SENT_TO_YOU_ON_YOUR = "//android.widget.TextView[@text='OTP has been sent to you on your mobile number, please enter it below']";

    @AndroidFindBy(xpath = OTP_HAS_BEEN_SENT_TO_YOU_ON_YOUR)
    private WebElement otpHasBeenSentToYouOnYour;

    public static final String EL0027 = "//android.widget.TextView[@text='00:27']";

    @AndroidFindBy(xpath = EL0027)
    private WebElement el0027;

    public static final String VERIFY_OTP = "//android.widget.TextView[@text='Verify OTP']";

    @AndroidFindBy(xpath = VERIFY_OTP)
    private WebElement verifyOtp;

    public static final String EL0019 = "//android.widget.TextView[@text='00:19']";

    @AndroidFindBy(xpath = EL0019)
    private WebElement el0019;

    public static final String EL0010 = "//android.widget.TextView[@text='00:10']";

    @AndroidFindBy(xpath = EL0010)
    private WebElement el0010;

    public static final String EL0005 = "//android.widget.TextView[@text='00:05']";

    @AndroidFindBy(xpath = EL0005)
    private WebElement el0005;

    public RegisterVerifyOtpScreen(TestContext context) {
        super(context);
    }
}
