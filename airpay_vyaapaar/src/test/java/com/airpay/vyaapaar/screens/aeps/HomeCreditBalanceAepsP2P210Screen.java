package com.airpay.vyaapaar.screens.aeps;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar / Dashboard. Module aeps. */
public class HomeCreditBalanceAepsP2P210Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-aeps-p2-p2-open-p2-dashboard";

    public static final String DASHBOARD = "//android.widget.TextView[@text='Dashboard']";

    @AndroidFindBy(xpath = DASHBOARD)
    private WebElement dashboard;

    public static final String LOCK_ICON = "//android.widget.ImageView[contains(@content-desc,'lock Icon')]";

    @AndroidFindBy(xpath = LOCK_ICON)
    private WebElement lockIcon;

    public static final String VERIFY_IDENTITY = "//android.widget.TextView[@text='Verify Identity']";

    @AndroidFindBy(xpath = VERIFY_IDENTITY)
    private WebElement verifyIdentity;

    public static final String LOG_IN_USING_YOUR_DEVICE_CREDENTIALS = "//android.widget.TextView[@text='Log in using your device credentials']";

    @AndroidFindBy(xpath = LOG_IN_USING_YOUR_DEVICE_CREDENTIALS)
    private WebElement logInUsingYourDeviceCredentials;

    public static final String LOGOUT = "//android.widget.TextView[@text='Logout']";

    @AndroidFindBy(xpath = LOGOUT)
    private WebElement logout;

    public HomeCreditBalanceAepsP2P210Screen(TestContext context) {
        super(context);
    }
}
