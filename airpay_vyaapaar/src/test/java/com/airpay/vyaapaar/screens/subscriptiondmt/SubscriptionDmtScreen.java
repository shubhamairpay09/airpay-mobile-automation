package com.airpay.vyaapaar.screens.subscriptiondmt;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Subscription / DMT — Choose the plan that works for you!. Module subscription-dmt. */
public class SubscriptionDmtScreen extends BaseScreen {

    public static final String PAGE_ID = "subscription-dmt";

    public static final String CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU = "//android.widget.TextView[@text='Choose the plan that works for you!']";

    @AndroidFindBy(xpath = CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU)
    private WebElement chooseThePlanThatWorksForYou;

    public static final String EL1MONTH = "//android.widget.TextView[@text='1 Month']";

    @AndroidFindBy(xpath = EL1MONTH)
    private WebElement el1Month;

    public static final String EL149 = "//android.widget.TextView[@text='₹149']";

    @AndroidFindBy(xpath = EL149)
    private WebElement el149;

    public static final String DMT_SUBSCRIPTION_PLAN_FOR1MONTH = "//android.widget.TextView[@text='DMT Subscription plan for 1 month']";

    @AndroidFindBy(xpath = DMT_SUBSCRIPTION_PLAN_FOR1MONTH)
    private WebElement dmtSubscriptionPlanFor1Month;

    public static final String DOWN_ARROW = "//android.view.View[contains(@content-desc,'Down arrow')]";

    @AndroidFindBy(xpath = DOWN_ARROW)
    private WebElement downArrow;

    public static final String EL3MONTHS = "//android.widget.TextView[@text='3 Months']";

    @AndroidFindBy(xpath = EL3MONTHS)
    private WebElement el3Months;

    public static final String EL50 = "//android.widget.TextView[@text='₹50']";

    @AndroidFindBy(xpath = EL50)
    private WebElement el50;

    public static final String DMT3MONTH_PLAN_ALL = "//android.widget.TextView[@text='DMT_3 month plan_All']";

    @AndroidFindBy(xpath = DMT3MONTH_PLAN_ALL)
    private WebElement dmt3MonthPlanAll;

    public static final String DMT4FIXED_INCENTIVE_SHARE_FOR1MONTH = "//android.widget.TextView[@text='DMT_4 fixed incentive share_for 1 month']";

    @AndroidFindBy(xpath = DMT4FIXED_INCENTIVE_SHARE_FOR1MONTH)
    private WebElement dmt4FixedIncentiveShareFor1Month;

    public static final String EL19 = "//android.widget.TextView[@text='₹19']";

    @AndroidFindBy(xpath = EL19)
    private WebElement el19;

    public static final String DMT21MONTH = "//android.widget.TextView[@text='DMT_2%_1month']";

    @AndroidFindBy(xpath = DMT21MONTH)
    private WebElement dmt21month;

    public static final String EL21 = "//android.widget.TextView[@text='₹21']";

    @AndroidFindBy(xpath = EL21)
    private WebElement el21;

    public static final String DMT_AUTO_RENEWAL_EXCLUSIVE = "//android.widget.TextView[@text='DMT_auto renewal_exclusive']";

    @AndroidFindBy(xpath = DMT_AUTO_RENEWAL_EXCLUSIVE)
    private WebElement dmtAutoRenewalExclusive;

    public static final String SUBSCRIPTION = "//android.widget.TextView[@text='Subscription']";

    @AndroidFindBy(xpath = SUBSCRIPTION)
    private WebElement subscription;

    public SubscriptionDmtScreen(TestContext context) {
        super(context);
    }
}
