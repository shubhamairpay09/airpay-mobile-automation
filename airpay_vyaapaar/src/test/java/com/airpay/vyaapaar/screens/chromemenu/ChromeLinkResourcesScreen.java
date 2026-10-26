package com.airpay.vyaapaar.screens.chromemenu;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Chrome link Resources — airpay-Logo. Module chrome-menu. */
public class ChromeLinkResourcesScreen extends BaseScreen {

    public static final String PAGE_ID = "chrome-link-resources";

    public static final String COM_ANDROID_CHROME_ID_EDGE_TO_EDGE_BASE = "//android.widget.FrameLayout[@resource-id='com.android.chrome:id/edge_to_edge_base_layout']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_EDGE_TO_EDGE_BASE)
    private WebElement comAndroidChromeIdEdgeToEdgeBase;

    public static final String COM_ANDROID_CHROME_ID_COORDINATOR = "//android.view.ViewGroup[@resource-id='com.android.chrome:id/coordinator']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_COORDINATOR)
    private WebElement comAndroidChromeIdCoordinator;

    public static final String COM_ANDROID_CHROME_ID_COMPOSITOR_VIEW_HOLDER = "//android.widget.FrameLayout[@resource-id='com.android.chrome:id/compositor_view_holder']";

    @AndroidFindBy(xpath = COM_ANDROID_CHROME_ID_COMPOSITOR_VIEW_HOLDER)
    private WebElement comAndroidChromeIdCompositorViewHolder;

    public static final String OFFCANVASNAVBAR = "//android.app.AlertDialog[@resource-id='offcanvasNavbar']";

    @AndroidFindBy(xpath = OFFCANVASNAVBAR)
    private WebElement offcanvasnavbar;

    public static final String AIRPAY_LOGO = "//android.view.View[@text='airpay-Logo']";

    @AndroidFindBy(xpath = AIRPAY_LOGO)
    private WebElement airpayLogo;

    public static final String CLOSE = "//android.widget.Button[contains(@content-desc,'Close')]";

    @AndroidFindBy(xpath = CLOSE)
    private WebElement close;

    public static final String AIRPAY_AI = "//android.view.View[contains(@content-desc,'airpay AI')]";

    @AndroidFindBy(xpath = AIRPAY_AI)
    private WebElement airpayAi;

    public static final String PAYMENTS = "//android.widget.Button[@text='Payments']";

    @AndroidFindBy(xpath = PAYMENTS)
    private WebElement payments;

    public static final String SOLUTIONS = "//android.widget.Button[@text='Solutions']";

    @AndroidFindBy(xpath = SOLUTIONS)
    private WebElement solutions;

    public static final String PARTNER = "//android.view.View[contains(@content-desc,'Partner')]";

    @AndroidFindBy(xpath = PARTNER)
    private WebElement partner;

    public static final String DEVELOPER_HUB = "//android.widget.Button[@text='Developer Hub']";

    @AndroidFindBy(xpath = DEVELOPER_HUB)
    private WebElement developerHub;

    public static final String RESOURCES = "//android.widget.Button[@text='Resources']";

    @AndroidFindBy(xpath = RESOURCES)
    private WebElement resources;

    public static final String COLLAPSE_RESOURCES_MOB = "//android.widget.ListView[@resource-id='collapse-resources-mob']";

    @AndroidFindBy(xpath = COLLAPSE_RESOURCES_MOB)
    private WebElement collapseResourcesMob;

    public static final String BLOG = "//android.view.View[contains(@content-desc,'Blog')]";

    @AndroidFindBy(xpath = BLOG)
    private WebElement blog;

    public static final String CASE_STUDIES = "//android.view.View[contains(@content-desc,'Case Studies')]";

    @AndroidFindBy(xpath = CASE_STUDIES)
    private WebElement caseStudies;

    public static final String FAQS = "//android.view.View[contains(@content-desc,'FAQs')]";

    @AndroidFindBy(xpath = FAQS)
    private WebElement faqs;

    public static final String ABOUT_US = "//android.widget.Button[@text='About Us']";

    @AndroidFindBy(xpath = ABOUT_US)
    private WebElement aboutUs;

    public static final String LOGIN = "//android.view.View[contains(@content-desc,'Login')]";

    @AndroidFindBy(xpath = LOGIN)
    private WebElement login;

    public static final String SIGN_UP = "//android.view.View[contains(@content-desc,'Sign Up')]";

    @AndroidFindBy(xpath = SIGN_UP)
    private WebElement signUp;

    public static final String TALK_TO_AN_EXPERT = "//android.view.View[contains(@content-desc,'Talk to an expert')]";

    @AndroidFindBy(xpath = TALK_TO_AN_EXPERT)
    private WebElement talkToAnExpert;

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

    public static final String AIRPAY_CO_IN_PARTNER_WITH_US = "//android.widget.EditText[@hint='Search Google or type URL']";

    @AndroidFindBy(xpath = AIRPAY_CO_IN_PARTNER_WITH_US)
    private WebElement airpayCoInPartnerWithUs;

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

    public ChromeLinkResourcesScreen(TestContext context) {
        super(context);
    }
}
