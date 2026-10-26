package com.airpay.vyaapaar.screens.chromemenu;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Chrome link airpay AI — www. Module chrome-menu. */
public class ChromeLinkAirpayAiScreen extends BaseScreen {

    public static final String PAGE_ID = "chrome-link-airpay-ai";

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

    public static final String WWW_AIRPAY_CO = "//android.view.View[contains(@content-desc,'www.airpay.co')]";

    @AndroidFindBy(xpath = WWW_AIRPAY_CO)
    private WebElement wwwAirpayCo;

    public static final String MENU = "//android.widget.Button[@text='Menu']";

    @AndroidFindBy(xpath = MENU)
    private WebElement menu;

    public static final String MAIN_CONTENT = "//android.view.View[@resource-id='main-content']";

    @AndroidFindBy(xpath = MAIN_CONTENT)
    private WebElement mainContent;

    public static final String ATLASSEC = "//android.view.View[@resource-id='atlassec']";

    @AndroidFindBy(xpath = ATLASSEC)
    private WebElement atlassec;

    public static final String MEET_ARIVU_PAYMENTS_YOUR_AI_CAN_ACTUALLY_HANDLE = "//android.widget.TextView[@text='Meet arivu. payments your ai can actually handle.']";

    @AndroidFindBy(xpath = MEET_ARIVU_PAYMENTS_YOUR_AI_CAN_ACTUALLY_HANDLE)
    private WebElement meetArivuPaymentsYourAiCanActuallyHandle;

    public static final String ARIVU_IS_AIRPAY_SAI_LAYER_FOR_PAYMENTS = "//android.widget.TextView[@text=concat('arivu is airpay',\"'\",'s AI layer for payments. Connect the assistant you already use - Claude, or ChatGPT and let it pull settlements, process refunds, verify orders and everything else your business needs.')]";

    @AndroidFindBy(xpath = ARIVU_IS_AIRPAY_SAI_LAYER_FOR_PAYMENTS)
    private WebElement arivuIsAirpaySAiLayerForPayments;

    public static final String GET_STARTED_WITH_ARIVU = "//android.view.View[contains(@content-desc,'Get Started with arivu')]";

    @AndroidFindBy(xpath = GET_STARTED_WITH_ARIVU)
    private WebElement getStartedWithArivu;

    public static final String SEE_HOW_IT_WORKS = "//android.view.View[contains(@content-desc,'See How It Works')]";

    @AndroidFindBy(xpath = SEE_HOW_IT_WORKS)
    private WebElement seeHowItWorks;

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

    public static final String AIRPAY_CO_IN_ARIVU = "//android.widget.EditText[@hint='Search Google or type URL']";

    @AndroidFindBy(xpath = AIRPAY_CO_IN_ARIVU)
    private WebElement airpayCoInArivu;

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

    public ChromeLinkAirpayAiScreen(TestContext context) {
        super(context);
    }
}
