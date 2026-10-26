package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar / Khata / Add / VT. Module home. */
public class HomeCreditBalanceOpenPanApplication21Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-open-pan-application-aeps-p2-p2-open-p2-khata-add-vt";

    public static final String KHATA = "//android.widget.TextView[@text='Khata']";

    @AndroidFindBy(xpath = KHATA)
    private WebElement khata;

    public static final String BUSINESS_NAME = "//android.widget.TextView[@text='Business Name']";

    @AndroidFindBy(xpath = BUSINESS_NAME)
    private WebElement businessName;

    public static final String MANAGE_BUSINESS = "//android.widget.TextView[@text='Manage Business']";

    @AndroidFindBy(xpath = MANAGE_BUSINESS)
    private WebElement manageBusiness;

    public static final String BUSINESS_ICON = "//android.view.View[contains(@content-desc,'Business icon')]";

    @AndroidFindBy(xpath = BUSINESS_ICON)
    private WebElement businessIcon;

    public static final String VCARE_TEST = "//android.widget.TextView[@text='Vcare test']";

    @AndroidFindBy(xpath = VCARE_TEST)
    private WebElement vcareTest;

    public static final String ELEMENT = "//android.widget.TextView[@text='↓']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String YOU_LL_GIVE = "//android.widget.TextView[@text=concat('You',\"'\",'ll Give')]";

    @AndroidFindBy(xpath = YOU_LL_GIVE)
    private WebElement youLlGive;

    public static final String EL000 = "//android.widget.TextView[@text='₹0.00']";

    @AndroidFindBy(xpath = EL000)
    private WebElement el000;

    public static final String YOU_LL_GET = "//android.widget.TextView[@text=concat('You',\"'\",'ll Get')]";

    @AndroidFindBy(xpath = YOU_LL_GET)
    private WebElement youLlGet;

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public static final String CUSTOMER_DETAILS = "//android.widget.TextView[@text='Customer Details']";

    @AndroidFindBy(xpath = CUSTOMER_DETAILS)
    private WebElement customerDetails;

    public static final String ADD_CUSTOMER = "//android.widget.TextView[@text='Add Customer']";

    @AndroidFindBy(xpath = ADD_CUSTOMER)
    private WebElement addCustomer;

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public HomeCreditBalanceOpenPanApplication21Screen(TestContext context) {
        super(context);
    }
}
