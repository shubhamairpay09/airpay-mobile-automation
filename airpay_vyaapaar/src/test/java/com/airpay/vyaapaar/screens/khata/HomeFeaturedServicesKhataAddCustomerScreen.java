package com.airpay.vyaapaar.screens.khata;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/Khata/Add Customer. Module khata. */
public class HomeFeaturedServicesKhataAddCustomerScreen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-khata-add-customer";

    public static final String CAMERA = "//android.view.View[contains(@content-desc,'Camera')]";

    @AndroidFindBy(xpath = CAMERA)
    private WebElement camera;

    public static final String EDIT_ICON = "//android.view.View[contains(@content-desc,'Edit Icon')]";

    @AndroidFindBy(xpath = EDIT_ICON)
    private WebElement editIcon;

    public static final String CUSTOMER_NAME = "//android.view.View[contains(@content-desc,'Customer name')]";

    @AndroidFindBy(xpath = CUSTOMER_NAME)
    private WebElement customerName;

    public static final String CUSTOMER_MOBILE = "//android.view.View[contains(@content-desc,'Customer Mobile')]";

    @AndroidFindBy(xpath = CUSTOMER_MOBILE)
    private WebElement customerMobile;

    public static final String CUSTOMER_EMAIL = "//android.view.View[contains(@content-desc,'Customer Email')]";

    @AndroidFindBy(xpath = CUSTOMER_EMAIL)
    private WebElement customerEmail;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String ADD_CUSTOMER = "//android.widget.TextView[@text='Add Customer']";

    @AndroidFindBy(xpath = ADD_CUSTOMER)
    private WebElement addCustomer;

    public HomeFeaturedServicesKhataAddCustomerScreen(TestContext context) {
        super(context);
    }
}
