package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/CMS/Cash Drop/BF. Module cms. */
public class HomeFeaturedServicesCmsCashDrop5Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-cms-cash-drop-bf";

    public static final String MERCHANT_NAME = "//android.widget.TextView[@text='Merchant Name']";

    @AndroidFindBy(xpath = MERCHANT_NAME)
    private WebElement merchantName;

    public static final String ELEMENT = "//android.widget.TextView[@text=':']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String BAJAJ_FINANCE = "//android.widget.TextView[@text='Bajaj Finance']";

    @AndroidFindBy(xpath = BAJAJ_FINANCE)
    private WebElement bajajFinance;

    public static final String MERCHANT_MOBILE = "//android.widget.TextView[@text='Merchant Mobile']";

    @AndroidFindBy(xpath = MERCHANT_MOBILE)
    private WebElement merchantMobile;

    public static final String MERCHANT_EMAIL = "//android.widget.TextView[@text='Merchant Email']";

    @AndroidFindBy(xpath = MERCHANT_EMAIL)
    private WebElement merchantEmail;

    public static final String BAJAJFINANCETEST_GMAIL_COM = "//android.widget.TextView[@text='bajajfinancetest@gmail.com']";

    @AndroidFindBy(xpath = BAJAJFINANCETEST_GMAIL_COM)
    private WebElement bajajfinancetestGmailCom;

    public static final String MOBILE_NUMBER = "//android.view.View[contains(@content-desc,'Mobile Number')]";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String SEARCH_VALUE = "//android.view.View[contains(@content-desc,'Search Value')]";

    @AndroidFindBy(xpath = SEARCH_VALUE)
    private WebElement searchValue;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String DETAILS = "//android.widget.TextView[@text='Details']";

    @AndroidFindBy(xpath = DETAILS)
    private WebElement details;

    public HomeFeaturedServicesCmsCashDrop5Screen(TestContext context) {
        super(context);
    }
}
