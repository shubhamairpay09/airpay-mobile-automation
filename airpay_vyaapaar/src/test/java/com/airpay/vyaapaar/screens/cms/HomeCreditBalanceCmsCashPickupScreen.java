package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Pickup — Info Systems Limited. Module cms. */
public class HomeCreditBalanceCmsCashPickupScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-pickup";

    public static final String IS = "//android.widget.TextView[@text='IS']";

    @AndroidFindBy(xpath = IS)
    private WebElement is;

    public static final String INFO_SYSTEMS_LIMITED = "//android.widget.TextView[@text='Info Systems Limited']";

    @AndroidFindBy(xpath = INFO_SYSTEMS_LIMITED)
    private WebElement infoSystemsLimited;

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public static final String CASH_PICKUP = "//android.widget.TextView[@text='Cash Pickup']";

    @AndroidFindBy(xpath = CASH_PICKUP)
    private WebElement cashPickup;

    public HomeCreditBalanceCmsCashPickupScreen(TestContext context) {
        super(context);
    }
}
