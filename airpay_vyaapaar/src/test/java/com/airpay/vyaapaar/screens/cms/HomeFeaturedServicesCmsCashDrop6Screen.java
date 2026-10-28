package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/CMS/Cash Drop/Others. Module cms. */
public class HomeFeaturedServicesCmsCashDrop6Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-cms-cash-drop-others";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String FAIL_SOMETHING_WENT_WRONG = "//android.widget.TextView[@text='Fail: Something Went Wrong.']";

    @AndroidFindBy(xpath = FAIL_SOMETHING_WENT_WRONG)
    private WebElement failSomethingWentWrong;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public HomeFeaturedServicesCmsCashDrop6Screen(TestContext context) {
        super(context);
    }
}
