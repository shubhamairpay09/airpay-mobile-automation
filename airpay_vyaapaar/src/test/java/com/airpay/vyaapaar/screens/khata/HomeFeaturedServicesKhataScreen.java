package com.airpay.vyaapaar.screens.khata;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/Khata. Module khata. */
public class HomeFeaturedServicesKhataScreen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-khata";

    public static final String ADD_BUSINESS = "//android.widget.TextView[contains(@content-desc,'Add Business')]";

    @AndroidFindBy(xpath = ADD_BUSINESS)
    private WebElement addBusiness;

    public static final String BUSINESS_NAME = "//android.view.View[contains(@content-desc,'Business Name')]";

    @AndroidFindBy(xpath = BUSINESS_NAME)
    private WebElement businessName;

    public static final String ADD = "//android.widget.TextView[contains(@content-desc,'Add')]";

    @AndroidFindBy(xpath = ADD)
    private WebElement add;

    public static final String CHOOSE_BUSINESS = "//android.widget.TextView[contains(@content-desc,'Choose Business')]";

    @AndroidFindBy(xpath = CHOOSE_BUSINESS)
    private WebElement chooseBusiness;

    public static final String B = "//android.widget.TextView[@text='B']";

    @AndroidFindBy(xpath = B)
    private WebElement b;

    public static final String BUSINESSONE = "//android.widget.TextView[contains(@content-desc,'BusinessOne')]";

    @AndroidFindBy(xpath = BUSINESSONE)
    private WebElement businessone;

    public static final String EDIT_ICON = "//android.view.View[contains(@content-desc,'Edit Icon')]";

    @AndroidFindBy(xpath = EDIT_ICON)
    private WebElement editIcon;

    public static final String VT = "//android.widget.TextView[@text='VT']";

    @AndroidFindBy(xpath = VT)
    private WebElement vt;

    public static final String VCARE_TEST = "//android.widget.TextView[contains(@content-desc,'Vcare test')]";

    @AndroidFindBy(xpath = VCARE_TEST)
    private WebElement vcareTest;

    public static final String SELECTED = "//android.widget.TextView[contains(@content-desc,'Selected')]";

    @AndroidFindBy(xpath = SELECTED)
    private WebElement selected;

    public static final String MANAGE_BUSINESS = "//android.widget.TextView[@text='Manage Business']";

    @AndroidFindBy(xpath = MANAGE_BUSINESS)
    private WebElement manageBusiness;

    public HomeFeaturedServicesKhataScreen(TestContext context) {
        super(context);
    }
}
