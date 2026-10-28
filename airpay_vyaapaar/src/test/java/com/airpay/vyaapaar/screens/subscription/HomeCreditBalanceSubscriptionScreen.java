package com.airpay.vyaapaar.screens.subscription;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Subscription — AEPS. Module subscription. */
public class HomeCreditBalanceSubscriptionScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-subscription";

    public static final String POWER_UP_YOUR_EARNINGS_SELECT_THE_PRODUCT_YOU = "//android.widget.TextView[@text='Power up your earnings! Select the product you want to subscribe to.']";

    @AndroidFindBy(xpath = POWER_UP_YOUR_EARNINGS_SELECT_THE_PRODUCT_YOU)
    private WebElement powerUpYourEarningsSelectTheProductYou;

    public static final String AEPS_ICON = "//android.view.View[contains(@content-desc,'AEPS icon')]";

    @AndroidFindBy(xpath = AEPS_ICON)
    private WebElement aepsIcon;

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String HATM_ICON = "//android.view.View[contains(@content-desc,'HATM icon')]";

    @AndroidFindBy(xpath = HATM_ICON)
    private WebElement hatmIcon;

    public static final String HATM = "//android.widget.TextView[@text='HATM']";

    @AndroidFindBy(xpath = HATM)
    private WebElement hatm;

    public static final String DIGIKHATA_ICON = "//android.view.View[contains(@content-desc,'DIGIKHATA icon')]";

    @AndroidFindBy(xpath = DIGIKHATA_ICON)
    private WebElement digikhataIcon;

    public static final String DIGIKHATA = "//android.widget.TextView[@text='DIGIKHATA']";

    @AndroidFindBy(xpath = DIGIKHATA)
    private WebElement digikhata;

    public static final String DMT_ICON = "//android.view.View[contains(@content-desc,'DMT icon')]";

    @AndroidFindBy(xpath = DMT_ICON)
    private WebElement dmtIcon;

    public static final String DMT = "//android.widget.TextView[@text='DMT']";

    @AndroidFindBy(xpath = DMT)
    private WebElement dmt;

    public static final String SUBSCRIPTION = "//android.widget.TextView[@text='Subscription']";

    @AndroidFindBy(xpath = SUBSCRIPTION)
    private WebElement subscription;

    public HomeCreditBalanceSubscriptionScreen(TestContext context) {
        super(context);
    }
}
