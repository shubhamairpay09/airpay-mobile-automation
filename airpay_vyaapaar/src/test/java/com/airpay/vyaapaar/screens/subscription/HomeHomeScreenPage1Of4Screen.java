package com.airpay.vyaapaar.screens.subscription;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Home screen Page 1 of 3/Subscription/DIGIKHATA. Module subscription. */
public class HomeHomeScreenPage1Of4Screen extends BaseScreen {

    public static final String PAGE_ID = "home-home-screen-page-1-of-3-subscription-digikhata";

    public static final String CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU = "//android.widget.TextView[@text='Choose the plan that works for you!']";

    @AndroidFindBy(xpath = CHOOSE_THE_PLAN_THAT_WORKS_FOR_YOU)
    private WebElement chooseThePlanThatWorksForYou;

    public static final String EL1MONTH = "//android.widget.TextView[@text='1 Month']";

    @AndroidFindBy(xpath = EL1MONTH)
    private WebElement el1Month;

    public static final String EL22 = "//android.widget.TextView[@text='₹22']";

    @AndroidFindBy(xpath = EL22)
    private WebElement el22;

    public static final String EL2FIXED_INCENTIVE_DIGIKHATA = "//android.widget.TextView[@text='2fixed incentive_digikhata']";

    @AndroidFindBy(xpath = EL2FIXED_INCENTIVE_DIGIKHATA)
    private WebElement el2fixedIncentiveDigikhata;

    public static final String DOWN_ARROW = "//android.view.View[contains(@content-desc,'Down arrow')]";

    @AndroidFindBy(xpath = DOWN_ARROW)
    private WebElement downArrow;

    public static final String EL3MONTHS = "//android.widget.TextView[@text='3 Months']";

    @AndroidFindBy(xpath = EL3MONTHS)
    private WebElement el3Months;

    public static final String EL9 = "//android.widget.TextView[@text='₹9']";

    @AndroidFindBy(xpath = EL9)
    private WebElement el9;

    public static final String DIGIKHATA15FIXED_INCENTIVE = "//android.widget.TextView[@text='Digikhata_1.5 fixed_incentive']";

    @AndroidFindBy(xpath = DIGIKHATA15FIXED_INCENTIVE)
    private WebElement digikhata15FixedIncentive;

    public static final String EL12MONTHS = "//android.widget.TextView[@text='12 Months']";

    @AndroidFindBy(xpath = EL12MONTHS)
    private WebElement el12Months;

    public static final String EL1150 = "//android.widget.TextView[@text='₹1150']";

    @AndroidFindBy(xpath = EL1150)
    private WebElement el1150;

    public static final String EL6MONTHS = "//android.widget.TextView[@text='6 Months']";

    @AndroidFindBy(xpath = EL6MONTHS)
    private WebElement el6Months;

    public static final String EL850 = "//android.widget.TextView[@text='₹850']";

    @AndroidFindBy(xpath = EL850)
    private WebElement el850;

    public static final String EL6MONTHS_DIGIKHATA = "//android.widget.TextView[@text='6 Months_Digikhata']";

    @AndroidFindBy(xpath = EL6MONTHS_DIGIKHATA)
    private WebElement el6MonthsDigikhata;

    public static final String SUBSCRIPTION = "//android.widget.TextView[@text='Subscription']";

    @AndroidFindBy(xpath = SUBSCRIPTION)
    private WebElement subscription;

    public HomeHomeScreenPage1Of4Screen(TestContext context) {
        super(context);
    }
}
