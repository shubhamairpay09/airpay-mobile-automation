package com.airpay.vyaapaar.screens.profile;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Profile/Settings. Module profile. */
public class ProfileSettingsScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-settings";

    public static final String APP = "//android.widget.TextView[@text='App']";

    @AndroidFindBy(xpath = APP)
    private WebElement app;

    public static final String DARK_THEME_ICON = "//android.view.View[contains(@content-desc,'Dark theme icon')]";

    @AndroidFindBy(xpath = DARK_THEME_ICON)
    private WebElement darkThemeIcon;

    public static final String DARK_THEME = "//android.widget.TextView[@text='Dark theme']";

    @AndroidFindBy(xpath = DARK_THEME)
    private WebElement darkTheme;

    public static final String NOTIFICATION_SETTINGS_ICON = "//android.view.View[contains(@content-desc,'Notification Settings icon')]";

    @AndroidFindBy(xpath = NOTIFICATION_SETTINGS_ICON)
    private WebElement notificationSettingsIcon;

    public static final String NOTIFICATION_SETTINGS = "//android.widget.TextView[@text='Notification Settings']";

    @AndroidFindBy(xpath = NOTIFICATION_SETTINGS)
    private WebElement notificationSettings;

    public static final String FORWARD_ARROW_ICON = "//android.view.View[contains(@content-desc,'Forward Arrow Icon')]";

    @AndroidFindBy(xpath = FORWARD_ARROW_ICON)
    private WebElement forwardArrowIcon;

    public static final String SUPPORT = "//android.widget.TextView[@text='Support']";

    @AndroidFindBy(xpath = SUPPORT)
    private WebElement support;

    public static final String TICKETS_ICON = "//android.view.View[contains(@content-desc,'Tickets icon')]";

    @AndroidFindBy(xpath = TICKETS_ICON)
    private WebElement ticketsIcon;

    public static final String TICKETS = "//android.widget.TextView[@text='Tickets']";

    @AndroidFindBy(xpath = TICKETS)
    private WebElement tickets;

    public static final String CUSTOMER_SUPPORT_ICON = "//android.view.View[contains(@content-desc,'Customer Support icon')]";

    @AndroidFindBy(xpath = CUSTOMER_SUPPORT_ICON)
    private WebElement customerSupportIcon;

    public static final String CUSTOMER_SUPPORT = "//android.widget.TextView[@text='Customer Support']";

    @AndroidFindBy(xpath = CUSTOMER_SUPPORT)
    private WebElement customerSupport;

    public static final String PRIVACY_POLICY_ICON = "//android.view.View[contains(@content-desc,'Privacy Policy icon')]";

    @AndroidFindBy(xpath = PRIVACY_POLICY_ICON)
    private WebElement privacyPolicyIcon;

    public static final String PRIVACY_POLICY = "//android.widget.TextView[@text='Privacy Policy']";

    @AndroidFindBy(xpath = PRIVACY_POLICY)
    private WebElement privacyPolicy;

    public static final String TERMS_AND_CONDITIONS_ICON = "//android.view.View[contains(@content-desc,'Terms and Conditions icon')]";

    @AndroidFindBy(xpath = TERMS_AND_CONDITIONS_ICON)
    private WebElement termsAndConditionsIcon;

    public static final String TERMS_AND_CONDITIONS = "//android.widget.TextView[@text='Terms and Conditions']";

    @AndroidFindBy(xpath = TERMS_AND_CONDITIONS)
    private WebElement termsAndConditions;

    public static final String COPYRIGHT2026AIRPAY_VYAAPAAR_VERSION3020 = "//android.widget.TextView[@text='Copyright © 2026 airpay vyaapaar  • Version 3.0.20-staging-accessibility']";

    @AndroidFindBy(xpath = COPYRIGHT2026AIRPAY_VYAAPAAR_VERSION3020)
    private WebElement copyright2026AirpayVyaapaarVersion3020;

    public static final String SETTINGS = "//android.widget.TextView[@text='Settings']";

    @AndroidFindBy(xpath = SETTINGS)
    private WebElement settings;

    public ProfileSettingsScreen(TestContext context) {
        super(context);
    }
}
