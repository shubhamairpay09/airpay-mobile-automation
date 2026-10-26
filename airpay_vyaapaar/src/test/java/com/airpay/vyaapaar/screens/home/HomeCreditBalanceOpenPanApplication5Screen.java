package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar / Use PIN / Cancel / Add Credits — Select Payment Type. Module home. */
public class HomeCreditBalanceOpenPanApplication5Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-open-pan-application-use-pin-cancel-add-credits";

    public static final String SELECT_PAYMENT_TYPE = "//android.widget.TextView[@text='Select Payment Type']";

    @AndroidFindBy(xpath = SELECT_PAYMENT_TYPE)
    private WebElement selectPaymentType;

    public static final String REQUEST_FROM_NETWORK_ICON = "//android.view.View[contains(@content-desc,'Request From Network icon')]";

    @AndroidFindBy(xpath = REQUEST_FROM_NETWORK_ICON)
    private WebElement requestFromNetworkIcon;

    public static final String REQUEST_FROM_NETWORK = "//android.widget.TextView[@text='Request From Network']";

    @AndroidFindBy(xpath = REQUEST_FROM_NETWORK)
    private WebElement requestFromNetwork;

    public static final String AMOUNT = "//android.widget.TextView[@text='Amount']";

    @AndroidFindBy(xpath = AMOUNT)
    private WebElement amount;

    public static final String ADD_CREDIT = "//android.widget.TextView[@text='Add Credit']";

    @AndroidFindBy(xpath = ADD_CREDIT)
    private WebElement addCredit;

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

    public HomeCreditBalanceOpenPanApplication5Screen(TestContext context) {
        super(context);
    }
}
