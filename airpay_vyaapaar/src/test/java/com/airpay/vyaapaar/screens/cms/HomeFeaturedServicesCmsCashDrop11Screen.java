package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/CMS/Cash Drop/CP. Module cms. */
public class HomeFeaturedServicesCmsCashDrop11Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-cms-cash-drop-cp";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String WE_ARE_UNABLE_TO_PROCEED_PLEASE_TRY_AGAIN = "//android.widget.TextView[@text='We are unable to proceed, please try again.']";

    @AndroidFindBy(xpath = WE_ARE_UNABLE_TO_PROCEED_PLEASE_TRY_AGAIN)
    private WebElement weAreUnableToProceedPleaseTryAgain;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public HomeFeaturedServicesCmsCashDrop11Screen(TestContext context) {
        super(context);
    }
}
