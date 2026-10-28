package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/CMS/Cash Drop/SI. Module cms. */
public class HomeFeaturedServicesCmsCashDrop8Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-cms-cash-drop-si";

    public static final String MERCHANT_NAME = "//android.widget.TextView[@text='Merchant Name']";

    @AndroidFindBy(xpath = MERCHANT_NAME)
    private WebElement merchantName;

    public static final String ELEMENT = "//android.widget.TextView[@text=':']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String SHABRI_INVESTMENTS = "//android.widget.TextView[@text='Shabri Investments']";

    @AndroidFindBy(xpath = SHABRI_INVESTMENTS)
    private WebElement shabriInvestments;

    public static final String MERCHANT_MOBILE = "//android.widget.TextView[@text='Merchant Mobile']";

    @AndroidFindBy(xpath = MERCHANT_MOBILE)
    private WebElement merchantMobile;

    public static final String MERCHANT_EMAIL = "//android.widget.TextView[@text='Merchant Email']";

    @AndroidFindBy(xpath = MERCHANT_EMAIL)
    private WebElement merchantEmail;

    public static final String TEST_ADMIN_COM = "//android.widget.TextView[@text='test@admin.com']";

    @AndroidFindBy(xpath = TEST_ADMIN_COM)
    private WebElement testAdminCom;

    public static final String SEARCH_VALUE = "//android.widget.TextView[@text='Search Value']";

    @AndroidFindBy(xpath = SEARCH_VALUE)
    private WebElement searchValue;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String DETAILS = "//android.widget.TextView[@text='Details']";

    @AndroidFindBy(xpath = DETAILS)
    private WebElement details;

    public HomeFeaturedServicesCmsCashDrop8Screen(TestContext context) {
        super(context);
    }
}
