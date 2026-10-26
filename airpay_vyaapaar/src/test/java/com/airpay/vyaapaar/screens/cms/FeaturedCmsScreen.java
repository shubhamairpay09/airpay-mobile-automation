package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** CMS — Cash Drop. Module cms. */
public class FeaturedCmsScreen extends BaseScreen {

    public static final String PAGE_ID = "featured-cms";

    public static final String CASH_DROP_ICON = "//android.view.View[contains(@content-desc,'Cash Drop icon')]";

    @AndroidFindBy(xpath = CASH_DROP_ICON)
    private WebElement cashDropIcon;

    public static final String CASH_DROP = "//android.widget.TextView[@text='Cash Drop']";

    @AndroidFindBy(xpath = CASH_DROP)
    private WebElement cashDrop;

    public static final String DEPOSIT_YOUR_EARNINGS_SECURELY = "//android.widget.TextView[@text='Deposit your earnings securely']";

    @AndroidFindBy(xpath = DEPOSIT_YOUR_EARNINGS_SECURELY)
    private WebElement depositYourEarningsSecurely;

    public static final String CASH_PICKUP_ICON = "//android.view.View[contains(@content-desc,'Cash Pickup icon')]";

    @AndroidFindBy(xpath = CASH_PICKUP_ICON)
    private WebElement cashPickupIcon;

    public static final String CASH_PICKUP = "//android.widget.TextView[@text='Cash Pickup']";

    @AndroidFindBy(xpath = CASH_PICKUP)
    private WebElement cashPickup;

    public static final String SCHEDULE_APICKUP_FOR_YOUR_CASH = "//android.widget.TextView[@text='Schedule a pickup for your cash']";

    @AndroidFindBy(xpath = SCHEDULE_APICKUP_FOR_YOUR_CASH)
    private WebElement scheduleAPickupForYourCash;

    public static final String CASH_BURIAL_ICON = "//android.view.View[contains(@content-desc,'Cash Burial icon')]";

    @AndroidFindBy(xpath = CASH_BURIAL_ICON)
    private WebElement cashBurialIcon;

    public static final String CASH_BURIAL = "//android.widget.TextView[@text='Cash Burial']";

    @AndroidFindBy(xpath = CASH_BURIAL)
    private WebElement cashBurial;

    public static final String HANDING_OVER_COLLECTED_CASH = "//android.widget.TextView[@text='Handing over collected cash']";

    @AndroidFindBy(xpath = HANDING_OVER_COLLECTED_CASH)
    private WebElement handingOverCollectedCash;

    public FeaturedCmsScreen(TestContext context) {
        super(context);
    }
}
