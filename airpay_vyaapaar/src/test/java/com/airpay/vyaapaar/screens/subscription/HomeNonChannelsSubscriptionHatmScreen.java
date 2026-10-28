package com.airpay.vyaapaar.screens.subscription;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Subscription/HATM. Module subscription. */
public class HomeNonChannelsSubscriptionHatmScreen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-subscription-hatm";

    public static final String CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU = "//android.widget.TextView[@text='Choose the plan that works for you!']";

    @AndroidFindBy(xpath = CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU)
    private WebElement chooseThePlanThatWorksForYou;

    public static final String EL3MONTHS = "//android.widget.TextView[@text='3 Months']";

    @AndroidFindBy(xpath = EL3MONTHS)
    private WebElement el3Months;

    public static final String EL600 = "//android.widget.TextView[@text='₹600']";

    @AndroidFindBy(xpath = EL600)
    private WebElement el600;

    public static final String HATM03MONTH = "//android.widget.TextView[@text='hatm03month']";

    @AndroidFindBy(xpath = HATM03MONTH)
    private WebElement hatm03month;

    public static final String DOWN_ARROW = "//android.view.View[contains(@content-desc,'Down arrow')]";

    @AndroidFindBy(xpath = DOWN_ARROW)
    private WebElement downArrow;

    public static final String EL12MONTHS = "//android.widget.TextView[@text='12 Months']";

    @AndroidFindBy(xpath = EL12MONTHS)
    private WebElement el12Months;

    public static final String EL800 = "//android.widget.TextView[@text='₹800']";

    @AndroidFindBy(xpath = EL800)
    private WebElement el800;

    public static final String EL5INCR_RTAEPS12MONTH = "//android.widget.TextView[@text='5 % Incr RTAEPS12Month']";

    @AndroidFindBy(xpath = EL5INCR_RTAEPS12MONTH)
    private WebElement el5IncrRtaeps12month;

    public static final String EL1MONTH = "//android.widget.TextView[@text='1 Month']";

    @AndroidFindBy(xpath = EL1MONTH)
    private WebElement el1Month;

    public static final String EL499 = "//android.widget.TextView[@text='₹499']";

    @AndroidFindBy(xpath = EL499)
    private WebElement el499;

    public static final String EL2INCREASE_IN_COMMISSION = "//android.widget.TextView[@text='2% increase in commission']";

    @AndroidFindBy(xpath = EL2INCREASE_IN_COMMISSION)
    private WebElement el2IncreaseInCommission;

    public static final String EL17 = "//android.widget.TextView[@text='₹17']";

    @AndroidFindBy(xpath = EL17)
    private WebElement el17;

    public static final String EL1MONTH_HATM = "//android.widget.TextView[@text='1month_HATM']";

    @AndroidFindBy(xpath = EL1MONTH_HATM)
    private WebElement el1monthHatm;

    public static final String EL20 = "//android.widget.TextView[@text='₹20']";

    @AndroidFindBy(xpath = EL20)
    private WebElement el20;

    public static final String SUBSCRIPTION = "//android.widget.TextView[@text='Subscription']";

    @AndroidFindBy(xpath = SUBSCRIPTION)
    private WebElement subscription;

    public HomeNonChannelsSubscriptionHatmScreen(TestContext context) {
        super(context);
    }
}
