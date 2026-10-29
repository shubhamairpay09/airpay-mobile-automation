package com.airpay.vyaapaar.screens.khata;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/Khata/Edit Icon. Module khata. */
public class HomeFeaturedServicesKhataEditIconScreen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-khata-edit-icon";

    public static final String BUSINESS_NAME = "//android.widget.TextView[contains(@content-desc,'Business Name')]";

    @AndroidFindBy(xpath = BUSINESS_NAME)
    private WebElement businessName;

    public static final String MANAGE_BUSINESS = "//android.widget.TextView[contains(@content-desc,'Manage Business')]";

    @AndroidFindBy(xpath = MANAGE_BUSINESS)
    private WebElement manageBusiness;

    public static final String BUSINESS_ICON = "//android.view.View[contains(@content-desc,'Business icon')]";

    @AndroidFindBy(xpath = BUSINESS_ICON)
    private WebElement businessIcon;

    public static final String BUSINESSONE = "//android.widget.TextView[contains(@content-desc,'BusinessOne')]";

    @AndroidFindBy(xpath = BUSINESSONE)
    private WebElement businessone;

    public static final String ELEMENT = "//android.widget.TextView[contains(@content-desc,'↓')]";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String YOU_LL_GIVE = "//android.widget.TextView[contains(@content-desc,concat('You',\"'\",'ll Give'))]";

    @AndroidFindBy(xpath = YOU_LL_GIVE)
    private WebElement youLlGive;

    public static final String EL000 = "//android.widget.TextView[contains(@content-desc,'₹0.00')]";

    @AndroidFindBy(xpath = EL000)
    private WebElement el000;

    public static final String YOU_LL_GET = "//android.widget.TextView[contains(@content-desc,concat('You',\"'\",'ll Get'))]";

    @AndroidFindBy(xpath = YOU_LL_GET)
    private WebElement youLlGet;

    public static final String SEARCH = "//android.view.View[contains(@content-desc,'Search')]";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public static final String CUSTOMER_DETAILS = "//android.widget.TextView[contains(@content-desc,'Customer Details')]";

    @AndroidFindBy(xpath = CUSTOMER_DETAILS)
    private WebElement customerDetails;

    public static final String ADD_CUSTOMER = "//android.widget.TextView[contains(@content-desc,'Add Customer')]";

    @AndroidFindBy(xpath = ADD_CUSTOMER)
    private WebElement addCustomer;

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public static final String KHATA = "//android.widget.TextView[@text='Khata']";

    @AndroidFindBy(xpath = KHATA)
    private WebElement khata;

    public HomeFeaturedServicesKhataEditIconScreen(TestContext context) {
        super(context);
    }
}
