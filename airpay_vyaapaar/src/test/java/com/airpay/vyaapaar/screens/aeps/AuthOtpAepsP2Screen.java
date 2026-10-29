package com.airpay.vyaapaar.screens.aeps;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** OTP/AEPS/P2. Module aeps. */
public class AuthOtpAepsP2Screen extends BaseScreen {

    public static final String PAGE_ID = "auth-otp-aeps-p2";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String FAIL_SOMETHING_WENT_WRONG = "//android.widget.TextView[@text='Fail: Something Went Wrong.']";

    @AndroidFindBy(xpath = FAIL_SOMETHING_WENT_WRONG)
    private WebElement failSomethingWentWrong;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public AuthOtpAepsP2Screen(TestContext context) {
        super(context);
    }
}
