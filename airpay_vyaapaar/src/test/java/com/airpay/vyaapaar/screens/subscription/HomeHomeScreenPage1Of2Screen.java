package com.airpay.vyaapaar.screens.subscription;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Home screen Page 1 of 3/Subscription/AEPS. Module subscription. */
public class HomeHomeScreenPage1Of2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-home-screen-page-1-of-3-subscription-aeps";

    public static final String CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU = "//android.widget.TextView[@text='Choose the plan that works for you!']";

    @AndroidFindBy(xpath = CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU)
    private WebElement chooseThePlanThatWorksForYou;

    public static final String EL1MONTH = "//android.widget.TextView[@text='1 Month']";

    @AndroidFindBy(xpath = EL1MONTH)
    private WebElement el1Month;

    public static final String EL200 = "//android.widget.TextView[@text='₹200']";

    @AndroidFindBy(xpath = EL200)
    private WebElement el200;

    public static final String TEST01 = "//android.widget.TextView[@text='Test01']";

    @AndroidFindBy(xpath = TEST01)
    private WebElement test01;

    public static final String DOWN_ARROW = "//android.view.View[contains(@content-desc,'Down arrow')]";

    @AndroidFindBy(xpath = DOWN_ARROW)
    private WebElement downArrow;

    public static final String EL3MONTHS = "//android.widget.TextView[@text='3 Months']";

    @AndroidFindBy(xpath = EL3MONTHS)
    private WebElement el3Months;

    public static final String EL400 = "//android.widget.TextView[@text='₹400']";

    @AndroidFindBy(xpath = EL400)
    private WebElement el400;

    public static final String AEPS3MONTHRT = "//android.widget.TextView[@text='aeps 3monthRT']";

    @AndroidFindBy(xpath = AEPS3MONTHRT)
    private WebElement aeps3monthrt;

    public static final String EL180 = "//android.widget.TextView[@text='₹180']";

    @AndroidFindBy(xpath = EL180)
    private WebElement el180;

    public static final String AEPS1MONTH01 = "//android.widget.TextView[@text='AEPS1month01']";

    @AndroidFindBy(xpath = AEPS1MONTH01)
    private WebElement aeps1month01;

    public static final String EL6MONTHS = "//android.widget.TextView[@text='6 Months']";

    @AndroidFindBy(xpath = EL6MONTHS)
    private WebElement el6Months;

    public static final String EL700 = "//android.widget.TextView[@text='₹700']";

    @AndroidFindBy(xpath = EL700)
    private WebElement el700;

    public static final String AEPS06MONTHRT1 = "//android.widget.TextView[@text='AEPS06MonthRT1']";

    @AndroidFindBy(xpath = AEPS06MONTHRT1)
    private WebElement aeps06monthrt1;

    public static final String EL12MONTHS = "//android.widget.TextView[@text='12 Months']";

    @AndroidFindBy(xpath = EL12MONTHS)
    private WebElement el12Months;

    public static final String EL199 = "//android.widget.TextView[@text='₹199']";

    @AndroidFindBy(xpath = EL199)
    private WebElement el199;

    public static final String EL1YR_PLAN = "//android.widget.TextView[@text='1 yr plan']";

    @AndroidFindBy(xpath = EL1YR_PLAN)
    private WebElement el1YrPlan;

    public static final String SUBSCRIPTION = "//android.widget.TextView[@text='Subscription']";

    @AndroidFindBy(xpath = SUBSCRIPTION)
    private WebElement subscription;

    public HomeHomeScreenPage1Of2Screen(TestContext context) {
        super(context);
    }
}
