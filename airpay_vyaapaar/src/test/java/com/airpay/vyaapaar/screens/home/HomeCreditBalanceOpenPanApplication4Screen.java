package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar / Use PIN / Cancel / Visibility Off Icon. Module home. */
public class HomeCreditBalanceOpenPanApplication4Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-open-pan-application-use-pin-cancel-visibility-off-icon";

    public static final String CREDIT_BALANCE = "//android.widget.TextView[@text='Credit Balance']";

    @AndroidFindBy(xpath = CREDIT_BALANCE)
    private WebElement creditBalance;

    public static final String VISIBILITY_ON_ICON = "//android.view.View[contains(@content-desc,'Visibility On Icon')]";

    @AndroidFindBy(xpath = VISIBILITY_ON_ICON)
    private WebElement visibilityOnIcon;

    public static final String ADD_CREDITS = "//android.widget.TextView[@text='Add Credits']";

    @AndroidFindBy(xpath = ADD_CREDITS)
    private WebElement addCredits;

    public static final String MONTHLY_TOTAL = "//android.widget.TextView[@text='Monthly Total']";

    @AndroidFindBy(xpath = MONTHLY_TOTAL)
    private WebElement monthlyTotal;

    public static final String DMT = "//android.widget.TextView[@text='DMT']";

    @AndroidFindBy(xpath = DMT)
    private WebElement dmt;

    public static final String EL000 = "//android.widget.TextView[@text='₹0.00']";

    @AndroidFindBy(xpath = EL000)
    private WebElement el000;

    public static final String H_ATM = "//android.widget.TextView[@text='H-ATM']";

    @AndroidFindBy(xpath = H_ATM)
    private WebElement hAtm;

    public static final String OTHER = "//android.widget.TextView[@text='Other']";

    @AndroidFindBy(xpath = OTHER)
    private WebElement other;

    public static final String LIEN = "//android.widget.TextView[@text='Lien']";

    @AndroidFindBy(xpath = LIEN)
    private WebElement lien;

    public static final String UP_ARROW = "//android.view.View[contains(@content-desc,'Up arrow')]";

    @AndroidFindBy(xpath = UP_ARROW)
    private WebElement upArrow;

    public static final String FEATURED_SERVICES = "//android.widget.TextView[@text='Featured Services']";

    @AndroidFindBy(xpath = FEATURED_SERVICES)
    private WebElement featuredServices;

    public static final String PAN_APPLICATION_ICON = "//android.view.View[contains(@content-desc,'PAN Application icon')]";

    @AndroidFindBy(xpath = PAN_APPLICATION_ICON)
    private WebElement panApplicationIcon;

    public static final String PAN_APPLICATION = "//android.widget.TextView[@text='PAN Application']";

    @AndroidFindBy(xpath = PAN_APPLICATION)
    private WebElement panApplication;

    public static final String POST_OFFICE_ICON = "//android.view.View[contains(@content-desc,'Post Office icon')]";

    @AndroidFindBy(xpath = POST_OFFICE_ICON)
    private WebElement postOfficeIcon;

    public static final String POST_OFFICE = "//android.widget.TextView[@text='Post Office']";

    @AndroidFindBy(xpath = POST_OFFICE)
    private WebElement postOffice;

    public static final String AEPS_ICON = "//android.view.View[contains(@content-desc,'AEPS icon')]";

    @AndroidFindBy(xpath = AEPS_ICON)
    private WebElement aepsIcon;

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String AADHAAR_PAY_ICON = "//android.view.View[contains(@content-desc,'Aadhaar Pay icon')]";

    @AndroidFindBy(xpath = AADHAAR_PAY_ICON)
    private WebElement aadhaarPayIcon;

    public static final String AADHAAR_PAY = "//android.widget.TextView[@text='Aadhaar Pay']";

    @AndroidFindBy(xpath = AADHAAR_PAY)
    private WebElement aadhaarPay;

    public static final String CMS_ICON = "//android.view.View[contains(@content-desc,'CMS icon')]";

    @AndroidFindBy(xpath = CMS_ICON)
    private WebElement cmsIcon;

    public static final String CMS = "//android.widget.TextView[@text='CMS']";

    @AndroidFindBy(xpath = CMS)
    private WebElement cms;

    public static final String KHATA_ICON = "//android.view.View[contains(@content-desc,'Khata icon')]";

    @AndroidFindBy(xpath = KHATA_ICON)
    private WebElement khataIcon;

    public static final String KHATA = "//android.widget.TextView[@text='Khata']";

    @AndroidFindBy(xpath = KHATA)
    private WebElement khata;

    public static final String NOTIFICATIONS = "//android.view.View[contains(@content-desc,'Notifications')]";

    @AndroidFindBy(xpath = NOTIFICATIONS)
    private WebElement notifications;

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

    public HomeCreditBalanceOpenPanApplication4Screen(TestContext context) {
        super(context);
    }
}
