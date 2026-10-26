package com.airpay.vyaapaar.screens.aeps;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar / History / Filter Icon / Filter / Apply. Module aeps. */
public class HomeCreditBalanceAepsP2P218Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-aeps-p2-p2-open-p2-history-dropdown-filter-icon-dropdown-filter-apply";

    public static final String GIFTCARD = "//android.widget.TextView[@text='GIFTCARD']";

    @AndroidFindBy(xpath = GIFTCARD)
    private WebElement giftcard;

    public static final String FILTER_ICON = "//android.view.View[contains(@content-desc,'Filter Icon')]";

    @AndroidFindBy(xpath = FILTER_ICON)
    private WebElement filterIcon;

    public static final String SOMETHING_WENT_WRONG_PLEASE_CONNECT_TO_CUSTOMER_CARE = "//android.widget.TextView[@text='Something went wrong! please connect to customer care for support.']";

    @AndroidFindBy(xpath = SOMETHING_WENT_WRONG_PLEASE_CONNECT_TO_CUSTOMER_CARE)
    private WebElement somethingWentWrongPleaseConnectToCustomerCare;

    public static final String RETRY = "//android.widget.TextView[@text='Retry']";

    @AndroidFindBy(xpath = RETRY)
    private WebElement retry;

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

    public HomeCreditBalanceAepsP2P218Screen(TestContext context) {
        super(context);
    }
}
