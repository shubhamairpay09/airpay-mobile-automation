package com.airpay.vyaapaar.screens.chromemenu;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Chrome — Airpay. Module chrome-menu. */
public class ChromeTermsScreen extends BaseScreen {

    public static final String PAGE_ID = "chrome-terms";

    public static final String COM_ANDROID_CHROME_ID_EDGE_TO_EDGE_BASE = "//android.widget.FrameLayout[@resource-id='com.android.chrome:id/edge_to_edge_base_layout']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_EDGE_TO_EDGE_BASE)
    private WebElement comAndroidChromeIdEdgeToEdgeBase;

    public static final String COM_ANDROID_CHROME_ID_COORDINATOR = "//android.view.ViewGroup[@resource-id='com.android.chrome:id/coordinator']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_COORDINATOR)
    private WebElement comAndroidChromeIdCoordinator;

    public static final String COM_ANDROID_CHROME_ID_COMPOSITOR_VIEW_HOLDER = "//android.widget.FrameLayout[@resource-id='com.android.chrome:id/compositor_view_holder']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_COMPOSITOR_VIEW_HOLDER)
    private WebElement comAndroidChromeIdCompositorViewHolder;

    public static final String NAVIGATION_BAR = "//android.view.View[@resource-id='navigation_bar']";

    @AndroidFindBy(xpath = NAVIGATION_BAR)
    private WebElement navigationBar;

    public static final String AIRPAY = "//android.view.View[contains(@content-desc,'Airpay')]";

    @AndroidFindBy(xpath = AIRPAY)
    private WebElement airpay;

    public static final String MENU = "//android.widget.Button[@text='Menu']";

    @AndroidFindBy(xpath = MENU)
    private WebElement menu;

    public static final String MAIN_CONTENT = "//android.view.View[@resource-id='main-content']";

    @AndroidFindBy(xpath = MAIN_CONTENT)
    private WebElement mainContent;

    public static final String MAIN_PAGE_WRAPPER = "//android.view.View[@resource-id='main-page-wrapper']";

    @AndroidFindBy(xpath = MAIN_PAGE_WRAPPER)
    private WebElement mainPageWrapper;

    public static final String EL404PAGE_ICON = "//android.widget.Image[@text='404-page-icon']";

    @AndroidFindBy(xpath = EL404PAGE_ICON)
    private WebElement el404PageIcon;

    public static final String YOU_VE_TAKEN_ADETOUR = "//android.widget.TextView[@text=concat('YOU',\"'\",'VE TAKEN A DETOUR')]";

    @AndroidFindBy(xpath = YOU_VE_TAKEN_ADETOUR)
    private WebElement youVeTakenADetour;

    public static final String THE_PAGE_YOU_RE_LOOKING_FOR_HAS_BEEN = "//android.widget.TextView[@text=concat('The page you',\"'\",'re looking for has been moved, removed, or the address was entered incorrectly. Your funds and account information remain secure — only this URL has gone missing.')]";

    @AndroidFindBy(xpath = THE_PAGE_YOU_RE_LOOKING_FOR_HAS_BEEN)
    private WebElement thePageYouReLookingForHasBeen;

    public static final String RETURN_TO_HOME = "//android.widget.Button[@text='Return to Home']";

    @AndroidFindBy(xpath = RETURN_TO_HOME)
    private WebElement returnToHome;

    public static final String VIEW_SITEMAP = "//android.widget.Button[@text='View Sitemap  ']";

    @AndroidFindBy(xpath = VIEW_SITEMAP)
    private WebElement viewSitemap;

    public static final String SITE_FOOTER = "//android.view.View[@resource-id='site-footer']";

    @AndroidFindBy(xpath = SITE_FOOTER)
    private WebElement siteFooter;

    public static final String LOGO = "//android.view.View[contains(@content-desc,'Logo')]";

    @AndroidFindBy(xpath = LOGO)
    private WebElement logo;

    public static final String BLOCK_AIRPAY_QUICKSUPPORT = "//android.view.View[@resource-id='block-airpay-quicksupport']";

    @AndroidFindBy(xpath = BLOCK_AIRPAY_QUICKSUPPORT)
    private WebElement blockAirpayQuicksupport;

    public static final String QUICK_SUPPORT = "//android.widget.TextView[@text='Quick Support']";

    @AndroidFindBy(xpath = QUICK_SUPPORT)
    private WebElement quickSupport;

    public static final String CUSTOMERSERVICE_AIRPAY_CO_IN = "//android.view.View[contains(@content-desc,'customerservice@airpay.co.in')]";

    @AndroidFindBy(xpath = CUSTOMERSERVICE_AIRPAY_CO_IN)
    private WebElement customerserviceAirpayCoIn;

    public static final String ACQUIRING_SUPPORT = "//android.widget.TextView[@text='Acquiring Support:']";

    @AndroidFindBy(xpath = ACQUIRING_SUPPORT)
    private WebElement acquiringSupport;

    public static final String CUSTOMER_GRIEVANCE_REPORTING = "//android.view.View[contains(@content-desc,'Customer Grievance Reporting')]";

    @AndroidFindBy(xpath = CUSTOMER_GRIEVANCE_REPORTING)
    private WebElement customerGrievanceReporting;

    public static final String COM_ANDROID_CHROME_ID_CONTROL_CONTAINER = "//android.widget.FrameLayout[@resource-id='com.android.chrome:id/control_container']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_CONTROL_CONTAINER)
    private WebElement comAndroidChromeIdControlContainer;

    public static final String COM_ANDROID_CHROME_ID_TOOLBAR_CONTAINER = "//android.view.ViewGroup[@resource-id='com.android.chrome:id/toolbar_container']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_TOOLBAR_CONTAINER)
    private WebElement comAndroidChromeIdToolbarContainer;

    public static final String COM_ANDROID_CHROME_ID_TOOLBAR = "//android.widget.FrameLayout[@resource-id='com.android.chrome:id/toolbar']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_TOOLBAR)
    private WebElement comAndroidChromeIdToolbar;

    public static final String OPEN_THE_HOMEPAGE = "//android.widget.ImageButton[contains(@content-desc,'Open the homepage')]";

    @AndroidFindBy(xpath = OPEN_THE_HOMEPAGE)
    private WebElement openTheHomepage;

    public static final String COM_ANDROID_CHROME_ID_LOCATION_BAR = "//android.view.ViewGroup[@resource-id='com.android.chrome:id/location_bar']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_LOCATION_BAR)
    private WebElement comAndroidChromeIdLocationBar;

    public static final String COM_ANDROID_CHROME_ID_LOCATION_BAR_STATUS = "//android.widget.LinearLayout[@resource-id='com.android.chrome:id/location_bar_status']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_LOCATION_BAR_STATUS)
    private WebElement comAndroidChromeIdLocationBarStatus;

    public static final String COM_ANDROID_CHROME_ID_LOCATION_BAR_STATUS_ICON = "//android.widget.LinearLayout[@resource-id='com.android.chrome:id/location_bar_status_icon_view']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_LOCATION_BAR_STATUS_ICON)
    private WebElement comAndroidChromeIdLocationBarStatusIcon;

    public static final String CONNECTION_IS_SECURE = "//android.widget.ImageView[contains(@content-desc,'Connection is secure')]";

    @AndroidFindBy(xpath = CONNECTION_IS_SECURE)
    private WebElement connectionIsSecure;

    public static final String COM_ANDROID_CHROME_ID_FOCUS_THIEF = "//android.view.View[@resource-id='com.android.chrome:id/focus_thief']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_FOCUS_THIEF)
    private WebElement comAndroidChromeIdFocusThief;

    public static final String AIRPAY_CO_IN_VYAAPAAR = "//android.widget.EditText[@hint='Search Google or type URL']";

    @AndroidFindBy(xpath = AIRPAY_CO_IN_VYAAPAAR)
    private WebElement airpayCoInVyaapaar;

    public static final String COM_ANDROID_CHROME_ID_TOOLBAR_BUTTONS = "//android.widget.LinearLayout[@resource-id='com.android.chrome:id/toolbar_buttons']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_TOOLBAR_BUTTONS)
    private WebElement comAndroidChromeIdToolbarButtons;

    public static final String COM_ANDROID_CHROME_ID_OPTIONAL_TOOLBAR_BUTTON_CONTAINER = "//android.widget.FrameLayout[@resource-id='com.android.chrome:id/optional_toolbar_button_container']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_OPTIONAL_TOOLBAR_BUTTON_CONTAINER)
    private WebElement comAndroidChromeIdOptionalToolbarButtonContainer;

    public static final String ASK_GEMINI = "//android.widget.ImageButton[contains(@content-desc,'Ask Gemini')]";

    @AndroidFindBy(xpath = ASK_GEMINI)
    private WebElement askGemini;

    public static final String SEE16TABS = "//android.widget.ImageButton[contains(@content-desc,'See 16 tabs')]";

    @AndroidFindBy(xpath = SEE16TABS)
    private WebElement see16Tabs;

    public static final String COM_ANDROID_CHROME_ID_MENU_BUTTON_WRAPPER = "//android.widget.FrameLayout[@resource-id='com.android.chrome:id/menu_button_wrapper']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_MENU_BUTTON_WRAPPER)
    private WebElement comAndroidChromeIdMenuButtonWrapper;

    public static final String CUSTOMISE_AND_CONTROL_GOOGLE_CHROME = "//android.widget.ImageButton[contains(@content-desc,'Customise and control Google Chrome')]";

    @AndroidFindBy(xpath = CUSTOMISE_AND_CONTROL_GOOGLE_CHROME)
    private WebElement customiseAndControlGoogleChrome;

    public static final String COM_ANDROID_CHROME_ID_TOOLBAR_HAIRLINE = "//android.widget.ImageView[@resource-id='com.android.chrome:id/toolbar_hairline']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_TOOLBAR_HAIRLINE)
    private WebElement comAndroidChromeIdToolbarHairline;

    public static final String COM_ANDROID_CHROME_ID_TOOLBAR_PROGRESS_BAR_CONTAINER = "//android.widget.FrameLayout[@resource-id='com.android.chrome:id/toolbar_progress_bar_container']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_TOOLBAR_PROGRESS_BAR_CONTAINER)
    private WebElement comAndroidChromeIdToolbarProgressBarContainer;

    public static final String COM_ANDROID_CHROME_ID_EDGE_TO_EDGE_BOTTOM = "//android.view.View[@resource-id='com.android.chrome:id/edge_to_edge_bottom_chin']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_EDGE_TO_EDGE_BOTTOM)
    private WebElement comAndroidChromeIdEdgeToEdgeBottom;

    public ChromeTermsScreen(TestContext context) {
        super(context);
    }
}
