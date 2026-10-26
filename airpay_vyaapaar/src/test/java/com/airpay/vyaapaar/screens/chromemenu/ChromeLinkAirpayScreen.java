package com.airpay.vyaapaar.screens.chromemenu;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Chrome link Airpay — Logo. Module chrome-menu. */
public class ChromeLinkAirpayScreen extends BaseScreen {

    public static final String PAGE_ID = "chrome-link-airpay";

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

    public static final String LOGO = "//android.view.View[contains(@content-desc,'Logo')]";

    @AndroidFindBy(xpath = LOGO)
    private WebElement logo;

    public static final String MENU = "//android.widget.Button[@text='Menu']";

    @AndroidFindBy(xpath = MENU)
    private WebElement menu;

    public static final String ONLINE_IN_STORE_CROSS_BORDER = "//android.widget.TextView[@text='Online. In-store. Cross-border']";

    @AndroidFindBy(xpath = ONLINE_IN_STORE_CROSS_BORDER)
    private WebElement onlineInStoreCrossBorder;

    public static final String ALL_YOUR_PAYMENTS_ONE_PLATFORM = "//android.widget.TextView[@text='All your payments. One Platform']";

    @AndroidFindBy(xpath = ALL_YOUR_PAYMENTS_ONE_PLATFORM)
    private WebElement allYourPaymentsOnePlatform;

    public static final String TALK_TO_AN_EXPERT = "//android.widget.Button[@text='Talk to an expert']";

    @AndroidFindBy(xpath = TALK_TO_AN_EXPERT)
    private WebElement talkToAnExpert;

    public static final String LAYER1 = "//android.widget.Image[@resource-id='Layer_1']";

    @AndroidFindBy(xpath = LAYER1)
    private WebElement layer1;

    public static final String ARIVUROBOT = "//android.widget.Image[contains(@content-desc,'arivurobot')]";

    @AndroidFindBy(xpath = ARIVUROBOT)
    private WebElement arivurobot;

    public static final String POSAPP = "//android.widget.Image[contains(@content-desc,'posapp')]";

    @AndroidFindBy(xpath = POSAPP)
    private WebElement posapp;

    public static final String WEBAPP = "//android.widget.Image[contains(@content-desc,'webapp')]";

    @AndroidFindBy(xpath = WEBAPP)
    private WebElement webapp;

    public static final String SCENE_ASSET04 = "//android.widget.Image[@text='scene-asset04']";

    @AndroidFindBy(xpath = SCENE_ASSET04)
    private WebElement sceneAsset04;

    public static final String SCANQR = "//android.widget.Image[contains(@content-desc,'scanqr')]";

    @AndroidFindBy(xpath = SCANQR)
    private WebElement scanqr;

    public static final String SOUNDBOX = "//android.widget.Image[contains(@content-desc,'soundbox')]";

    @AndroidFindBy(xpath = SOUNDBOX)
    private WebElement soundbox;

    public static final String ELEMENT = "//android.widget.TextView[@text='✓']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String OUR_SOLUTIONS = "//android.widget.TextView[@text='Our solutions']";

    @AndroidFindBy(xpath = OUR_SOLUTIONS)
    private WebElement ourSolutions;

    public static final String ICON_PAYMENTS_PAYMENTS = "//android.view.View[contains(@content-desc,'icon-Payments Payments')]";

    @AndroidFindBy(xpath = ICON_PAYMENTS_PAYMENTS)
    private WebElement iconPaymentsPayments;

    public static final String ICON_PAYMENTS = "//android.widget.Image[contains(@content-desc,'icon-Payments')]";

    @AndroidFindBy(xpath = ICON_PAYMENTS)
    private WebElement iconPayments;

    public static final String PAYMENTS = "//android.widget.TextView[@text='Payments']";

    @AndroidFindBy(xpath = PAYMENTS)
    private WebElement payments;

    public static final String ICON_UPI_UPI = "//android.view.View[contains(@content-desc,'icon-UPI UPI')]";

    @AndroidFindBy(xpath = ICON_UPI_UPI)
    private WebElement iconUpiUpi;

    public static final String ICON_UPI = "//android.widget.Image[contains(@content-desc,'icon-UPI')]";

    @AndroidFindBy(xpath = ICON_UPI)
    private WebElement iconUpi;

    public static final String UPI = "//android.widget.TextView[@text='UPI']";

    @AndroidFindBy(xpath = UPI)
    private WebElement upi;

    public static final String ICON_OMNICHANNEL_OMNICHANNEL = "//android.view.View[contains(@content-desc,'icon-Omnichannel Omnichannel')]";

    @AndroidFindBy(xpath = ICON_OMNICHANNEL_OMNICHANNEL)
    private WebElement iconOmnichannelOmnichannel;

    public static final String ICON_OMNICHANNEL = "//android.widget.Image[contains(@content-desc,'icon-Omnichannel')]";

    @AndroidFindBy(xpath = ICON_OMNICHANNEL)
    private WebElement iconOmnichannel;

    public static final String OMNICHANNEL = "//android.widget.TextView[@text='Omnichannel']";

    @AndroidFindBy(xpath = OMNICHANNEL)
    private WebElement omnichannel;

    public static final String ICON_AI_AI_SOLUTIONS = "//android.view.View[contains(@content-desc,'icon-ai AI solutions')]";

    @AndroidFindBy(xpath = ICON_AI_AI_SOLUTIONS)
    private WebElement iconAiAiSolutions;

    public static final String ICON_AI = "//android.widget.Image[contains(@content-desc,'icon-ai')]";

    @AndroidFindBy(xpath = ICON_AI)
    private WebElement iconAi;

    public static final String TRUSTED = "//android.view.View[@resource-id='trusted']";

    @AndroidFindBy(xpath = TRUSTED)
    private WebElement trusted;

    public static final String TRUSTED_BY3LAKH_MERCHANTS = "//android.widget.TextView[@text='Trusted by 3 Lakh+ Merchants']";

    @AndroidFindBy(xpath = TRUSTED_BY3LAKH_MERCHANTS)
    private WebElement trustedBy3LakhMerchants;

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

    public static final String AIRPAY_CO_IN = "//android.widget.EditText[@hint='Search Google or type URL']";

    @AndroidFindBy(xpath = AIRPAY_CO_IN)
    private WebElement airpayCoIn;

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

    public ChromeLinkAirpayScreen(TestContext context) {
        super(context);
    }
}
