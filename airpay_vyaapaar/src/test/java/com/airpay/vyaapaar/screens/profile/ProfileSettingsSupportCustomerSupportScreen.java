package com.airpay.vyaapaar.screens.profile;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Profile/Settings/Support/Customer Support. Module profile. */
public class ProfileSettingsSupportCustomerSupportScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-settings-support-customer-support";

    public static final String GET_IN_TOUCH = "//android.widget.TextView[@text='GET IN TOUCH!']";

    @AndroidFindBy(xpath = GET_IN_TOUCH)
    private WebElement getInTouch;

    public static final String ALWAYS_WITHIN_YOUR_REACH = "//android.widget.TextView[@text='Always within your reach']";

    @AndroidFindBy(xpath = ALWAYS_WITHIN_YOUR_REACH)
    private WebElement alwaysWithinYourReach;

    public static final String CUSTOMER_SUPPORT_ICON = "//android.view.View[contains(@content-desc,'Customer Support icon')]";

    @AndroidFindBy(xpath = CUSTOMER_SUPPORT_ICON)
    private WebElement customerSupportIcon;

    public static final String CUSTOMER_CARE_NUMBER = "//android.widget.TextView[@text='Customer Care Number']";

    @AndroidFindBy(xpath = CUSTOMER_CARE_NUMBER)
    private WebElement customerCareNumber;

    public static final String ICON = "//android.view.View[contains(@content-desc,'Icon')]";

    @AndroidFindBy(xpath = ICON)
    private WebElement icon;

    public static final String CUSTOMERCARE_AIRPAY_CO_IN = "//android.widget.TextView[@text='Customercare@airpay.co.in']";

    @AndroidFindBy(xpath = CUSTOMERCARE_AIRPAY_CO_IN)
    private WebElement customercareAirpayCoIn;

    public static final String AREA_SALES_MANAGER = "//android.widget.TextView[@text='Area Sales Manager']";

    @AndroidFindBy(xpath = AREA_SALES_MANAGER)
    private WebElement areaSalesManager;

    public static final String N_A = "//android.widget.TextView[@text='N/A']";

    @AndroidFindBy(xpath = N_A)
    private WebElement nA;

    public static final String TERRITORY_SALES_MANAGER = "//android.widget.TextView[@text='Territory Sales Manager']";

    @AndroidFindBy(xpath = TERRITORY_SALES_MANAGER)
    private WebElement territorySalesManager;

    public static final String CUSTOMER_SUPPORT = "//android.widget.TextView[@text='Customer Support']";

    @AndroidFindBy(xpath = CUSTOMER_SUPPORT)
    private WebElement customerSupport;

    public ProfileSettingsSupportCustomerSupportScreen(TestContext context) {
        super(context);
    }
}
