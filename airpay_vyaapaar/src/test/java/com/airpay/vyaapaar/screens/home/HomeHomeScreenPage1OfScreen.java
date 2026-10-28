package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Home screen Page 1 of 3. Module home. */
public class HomeHomeScreenPage1OfScreen extends BaseScreen {

    public static final String PAGE_ID = "home-home-screen-page-1-of-3";

    public static final String DEVOTIONAL_EXPERIENCES_ICON = "//android.view.View[contains(@content-desc,'Devotional Experiences icon')]";

    @AndroidFindBy(xpath = DEVOTIONAL_EXPERIENCES_ICON)
    private WebElement devotionalExperiencesIcon;

    public static final String DEVOTIONAL_EXPERIENCES = "//android.widget.TextView[@text='Devotional Experiences']";

    @AndroidFindBy(xpath = DEVOTIONAL_EXPERIENCES)
    private WebElement devotionalExperiences;

    public static final String FINANCE_ICON = "//android.view.View[contains(@content-desc,'Finance icon')]";

    @AndroidFindBy(xpath = FINANCE_ICON)
    private WebElement financeIcon;

    public static final String FINANCE = "//android.widget.TextView[@text='Finance']";

    @AndroidFindBy(xpath = FINANCE)
    private WebElement finance;

    public static final String RECHARGE_BILLPAY = "//android.widget.TextView[@text='Recharge & Billpay']";

    @AndroidFindBy(xpath = RECHARGE_BILLPAY)
    private WebElement rechargeBillpay;

    public static final String MOBILE_ICON = "//android.view.View[contains(@content-desc,'Mobile icon')]";

    @AndroidFindBy(xpath = MOBILE_ICON)
    private WebElement mobileIcon;

    public static final String MOBILE = "//android.widget.TextView[@text='Mobile']";

    @AndroidFindBy(xpath = MOBILE)
    private WebElement mobile;

    public static final String DTH_ICON = "//android.view.View[contains(@content-desc,'DTH icon')]";

    @AndroidFindBy(xpath = DTH_ICON)
    private WebElement dthIcon;

    public static final String DTH = "//android.widget.TextView[@text='DTH']";

    @AndroidFindBy(xpath = DTH)
    private WebElement dth;

    public static final String ELECTRICITY_ICON = "//android.view.View[contains(@content-desc,'Electricity icon')]";

    @AndroidFindBy(xpath = ELECTRICITY_ICON)
    private WebElement electricityIcon;

    public static final String ELECTRICITY = "//android.widget.TextView[@text='Electricity']";

    @AndroidFindBy(xpath = ELECTRICITY)
    private WebElement electricity;

    public static final String LIC_ICON = "//android.view.View[contains(@content-desc,'LIC icon')]";

    @AndroidFindBy(xpath = LIC_ICON)
    private WebElement licIcon;

    public static final String LIC = "//android.widget.TextView[@text='LIC']";

    @AndroidFindBy(xpath = LIC)
    private WebElement lic;

    public static final String OTHER_SERVICES = "//android.widget.TextView[@text='Other Services']";

    @AndroidFindBy(xpath = OTHER_SERVICES)
    private WebElement otherServices;

    public static final String GOVT_SERVICES_ICON = "//android.view.View[contains(@content-desc,'Govt Services icon')]";

    @AndroidFindBy(xpath = GOVT_SERVICES_ICON)
    private WebElement govtServicesIcon;

    public static final String GOVT_SERVICES = "//android.widget.TextView[@text='Govt Services']";

    @AndroidFindBy(xpath = GOVT_SERVICES)
    private WebElement govtServices;

    public static final String NOTIFICATIONS = "//android.view.View[contains(@content-desc,'Notifications')]";

    @AndroidFindBy(xpath = NOTIFICATIONS)
    private WebElement notifications;

    public static final String HOME = "//android.widget.TextView[@text='Home']";

    @AndroidFindBy(xpath = HOME)
    private WebElement home;

    public static final String SERVICES = "//android.widget.TextView[@text='Services']";

    @AndroidFindBy(xpath = SERVICES)
    private WebElement services;

    public static final String HISTORY = "//android.widget.TextView[@text='History']";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String PROFILE = "//android.widget.TextView[@text='Profile']";

    @AndroidFindBy(xpath = PROFILE)
    private WebElement profile;

    public HomeHomeScreenPage1OfScreen(TestContext context) {
        super(context);
    }
}
