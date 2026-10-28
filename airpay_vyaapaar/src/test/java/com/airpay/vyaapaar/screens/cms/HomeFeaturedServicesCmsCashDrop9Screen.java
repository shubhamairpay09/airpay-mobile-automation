package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/CMS/Cash Drop/PM. Module cms. */
public class HomeFeaturedServicesCmsCashDrop9Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-cms-cash-drop-pm";

    public static final String MERCHANT_NAME = "//android.widget.TextView[@text='Merchant Name']";

    @AndroidFindBy(xpath = MERCHANT_NAME)
    private WebElement merchantName;

    public static final String ELEMENT = "//android.widget.TextView[@text=':']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String POPULAR_MOTORS = "//android.widget.TextView[@text='Popular Motors']";

    @AndroidFindBy(xpath = POPULAR_MOTORS)
    private WebElement popularMotors;

    public static final String MERCHANT_MOBILE = "//android.widget.TextView[@text='Merchant Mobile']";

    @AndroidFindBy(xpath = MERCHANT_MOBILE)
    private WebElement merchantMobile;

    public static final String MERCHANT_EMAIL = "//android.widget.TextView[@text='Merchant Email']";

    @AndroidFindBy(xpath = MERCHANT_EMAIL)
    private WebElement merchantEmail;

    public static final String POPULAR30MOTORSLTD_GMAIL_COM = "//android.widget.TextView[@text='popular30motorsltd@gmail.com']";

    @AndroidFindBy(xpath = POPULAR30MOTORSLTD_GMAIL_COM)
    private WebElement popular30motorsltdGmailCom;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String DETAILS = "//android.widget.TextView[@text='Details']";

    @AndroidFindBy(xpath = DETAILS)
    private WebElement details;

    public HomeFeaturedServicesCmsCashDrop9Screen(TestContext context) {
        super(context);
    }
}
