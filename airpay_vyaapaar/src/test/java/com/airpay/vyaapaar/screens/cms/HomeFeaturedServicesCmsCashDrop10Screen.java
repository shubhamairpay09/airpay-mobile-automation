package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/CMS/Cash Drop/Swiggy. Module cms. */
public class HomeFeaturedServicesCmsCashDrop10Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-cms-cash-drop-swiggy";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String SOMETHING_WENT_WRONG_PLEASE_CONNECT_TO_CUSTOMER_CARE = "//android.widget.TextView[@text='Something went wrong! please connect to customer care for support.']";

    @AndroidFindBy(xpath = SOMETHING_WENT_WRONG_PLEASE_CONNECT_TO_CUSTOMER_CARE)
    private WebElement somethingWentWrongPleaseConnectToCustomerCare;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public HomeFeaturedServicesCmsCashDrop10Screen(TestContext context) {
        super(context);
    }
}
