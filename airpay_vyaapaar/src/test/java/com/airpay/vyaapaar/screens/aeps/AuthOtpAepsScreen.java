package com.airpay.vyaapaar.screens.aeps;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** OTP/AEPS. Module aeps. */
public class AuthOtpAepsScreen extends BaseScreen {

    public static final String PAGE_ID = "auth-otp-aeps";

    public static final String P1 = "//android.widget.TextView[contains(@content-desc,'P1')]";

    @AndroidFindBy(xpath = P1)
    private WebElement p1;

    public static final String NOT_VERIFIED = "//android.widget.TextView[contains(@content-desc,'Not Verified')]";

    @AndroidFindBy(xpath = NOT_VERIFIED)
    private WebElement notVerified;

    public static final String P2 = "//android.widget.TextView[contains(@content-desc,'P2')]";

    @AndroidFindBy(xpath = P2)
    private WebElement p2;

    public static final String NOT_ONBOARDED = "//android.widget.TextView[contains(@content-desc,'Not Onboarded')]";

    @AndroidFindBy(xpath = NOT_ONBOARDED)
    private WebElement notOnboarded;

    public static final String P3 = "//android.widget.TextView[contains(@content-desc,'P3')]";

    @AndroidFindBy(xpath = P3)
    private WebElement p3;

    public static final String SELECT_PARTNER_BANK = "//android.widget.TextView[contains(@content-desc,'Select partner bank')]";

    @AndroidFindBy(xpath = SELECT_PARTNER_BANK)
    private WebElement selectPartnerBank;

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public AuthOtpAepsScreen(TestContext context) {
        super(context);
    }
}
