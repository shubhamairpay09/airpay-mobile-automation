package com.airpay.vyaapaar.screens.baazaarviewall;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Baazaar / View All — Starter Pack. Module baazaar-view-all. */
public class BaazaarViewAllScreen extends BaseScreen {

    public static final String PAGE_ID = "baazaar-view-all";

    public static final String STARTER_PACK = "//android.widget.TextView[@text='Starter Pack']";

    @AndroidFindBy(xpath = STARTER_PACK)
    private WebElement starterPack;

    public static final String ONE_MONTH_SUBSCRIPTION = "//android.widget.TextView[@text='One month subscription']";

    @AndroidFindBy(xpath = ONE_MONTH_SUBSCRIPTION)
    private WebElement oneMonthSubscription;

    public static final String EL19900 = "//android.widget.TextView[@text='₹199.00']";

    @AndroidFindBy(xpath = EL19900)
    private WebElement el19900;

    public static final String EL20000 = "//android.widget.TextView[@text='₹200.00']";

    @AndroidFindBy(xpath = EL20000)
    private WebElement el20000;

    public static final String LOADING_IMAGE = "//android.view.View[contains(@content-desc,'Loading image...')]";

    @AndroidFindBy(xpath = LOADING_IMAGE)
    private WebElement loadingImage;

    public static final String BRAINGYMJR = "//android.widget.TextView[@text='BrainGymJr']";

    @AndroidFindBy(xpath = BRAINGYMJR)
    private WebElement braingymjr;

    public static final String PREMIUM_PICKS = "//android.widget.TextView[@text='Premium Picks']";

    @AndroidFindBy(xpath = PREMIUM_PICKS)
    private WebElement premiumPicks;

    public BaazaarViewAllScreen(TestContext context) {
        super(context);
    }
}
