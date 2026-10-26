package com.airpay.vyaapaar.screens.aeps;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar / History — Home. Module aeps. */
public class HomeCreditBalanceAepsP2P215Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-aeps-p2-p2-open-p2-history";

    public static final String FILTER_ICON = "//android.view.View[contains(@content-desc,'Filter Icon')]";

    @AndroidFindBy(xpath = FILTER_ICON)
    private WebElement filterIcon;

    public static final String HOME = "//android.widget.TextView[@text='Home']";

    @AndroidFindBy(xpath = HOME)
    private WebElement home;

    public static final String SERVICES = "//android.widget.TextView[@text='Services']";

    @AndroidFindBy(xpath = SERVICES)
    private WebElement services;

    public static final String HISTORY = "//android.widget.TextView[@text='History']";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String PROFILE = "//android.widget.TextView[@text='Profile']";

    @AndroidFindBy(xpath = PROFILE)
    private WebElement profile;

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

    public HomeCreditBalanceAepsP2P215Screen(TestContext context) {
        super(context);
    }
}
