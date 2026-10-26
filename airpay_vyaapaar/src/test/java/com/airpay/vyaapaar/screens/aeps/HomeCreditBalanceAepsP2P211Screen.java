package com.airpay.vyaapaar.screens.aeps;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar / Notifications. Module aeps. */
public class HomeCreditBalanceAepsP2P211Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-aeps-p2-p2-open-p2-notifications";

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public static final String NOTIFICATIONS = "//android.widget.TextView[@text='Notifications']";

    @AndroidFindBy(xpath = NOTIFICATIONS)
    private WebElement notifications;

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

    public HomeCreditBalanceAepsP2P211Screen(TestContext context) {
        super(context);
    }
}
