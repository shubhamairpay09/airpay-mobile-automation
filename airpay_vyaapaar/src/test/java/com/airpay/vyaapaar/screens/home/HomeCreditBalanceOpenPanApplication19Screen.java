package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar / Khata. Module home. */
public class HomeCreditBalanceOpenPanApplication19Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-open-pan-application-aeps-p2-p2-open-p2-khata";

    public static final String MANAGE_BUSINESS = "//android.widget.TextView[@text='Manage Business']";

    @AndroidFindBy(xpath = MANAGE_BUSINESS)
    private WebElement manageBusiness;

    public static final String ADD_BUSINESS = "//android.widget.TextView[@text='Add Business']";

    @AndroidFindBy(xpath = ADD_BUSINESS)
    private WebElement addBusiness;

    public static final String BUSINESS_NAME = "//android.widget.TextView[@text='Business Name']";

    @AndroidFindBy(xpath = BUSINESS_NAME)
    private WebElement businessName;

    public static final String ADD = "//android.widget.TextView[@text='Add']";

    @AndroidFindBy(xpath = ADD)
    private WebElement add;

    public static final String CHOOSE_BUSINESS = "//android.widget.TextView[@text='Choose Business']";

    @AndroidFindBy(xpath = CHOOSE_BUSINESS)
    private WebElement chooseBusiness;

    public static final String VT = "//android.widget.TextView[@text='VT']";

    @AndroidFindBy(xpath = VT)
    private WebElement vt;

    public static final String VCARE_TEST = "//android.widget.TextView[@text='Vcare test']";

    @AndroidFindBy(xpath = VCARE_TEST)
    private WebElement vcareTest;

    public static final String EDIT_ICON = "//android.view.View[contains(@content-desc,'Edit Icon')]";

    @AndroidFindBy(xpath = EDIT_ICON)
    private WebElement editIcon;

    public HomeCreditBalanceOpenPanApplication19Screen(TestContext context) {
        super(context);
    }
}
