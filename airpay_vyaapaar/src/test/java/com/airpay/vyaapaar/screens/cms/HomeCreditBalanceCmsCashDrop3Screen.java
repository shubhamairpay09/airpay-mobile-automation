package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / AC / Suryoday. Module cms. */
public class HomeCreditBalanceCmsCashDrop3Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-ac-suryoday";

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public static final String AC = "//android.widget.TextView[@text='AC']";

    @AndroidFindBy(xpath = AC)
    private WebElement ac;

    public static final String AIRPAY_CMS = "//android.widget.TextView[@text='Airpay CMS']";

    @AndroidFindBy(xpath = AIRPAY_CMS)
    private WebElement airpayCms;

    public static final String S = "//android.widget.TextView[@text='S']";

    @AndroidFindBy(xpath = S)
    private WebElement s;

    public static final String SURYODAY = "//android.widget.TextView[@text='Suryoday']";

    @AndroidFindBy(xpath = SURYODAY)
    private WebElement suryoday;

    public static final String MM = "//android.widget.TextView[@text='MM']";

    @AndroidFindBy(xpath = MM)
    private WebElement mm;

    public static final String MIDLAND_MICROFIN_LTD = "//android.widget.TextView[@text='Midland Microfin Ltd']";

    @AndroidFindBy(xpath = MIDLAND_MICROFIN_LTD)
    private WebElement midlandMicrofinLtd;

    public static final String BF = "//android.widget.TextView[@text='BF']";

    @AndroidFindBy(xpath = BF)
    private WebElement bf;

    public static final String BAJAJ_FINANCE = "//android.widget.TextView[@text='Bajaj Finance']";

    @AndroidFindBy(xpath = BAJAJ_FINANCE)
    private WebElement bajajFinance;

    public static final String O = "//android.widget.TextView[@text='O']";

    @AndroidFindBy(xpath = O)
    private WebElement o;

    public static final String OTHERS = "//android.widget.TextView[@text='Others']";

    @AndroidFindBy(xpath = OTHERS)
    private WebElement others;

    public static final String TW = "//android.widget.TextView[@text='TW']";

    @AndroidFindBy(xpath = TW)
    private WebElement tw;

    public static final String THREE_WHEELS_CAPITAL = "//android.widget.TextView[@text='THREE WHEELS CAPITAL']";

    @AndroidFindBy(xpath = THREE_WHEELS_CAPITAL)
    private WebElement threeWheelsCapital;

    public static final String SI = "//android.widget.TextView[@text='SI']";

    @AndroidFindBy(xpath = SI)
    private WebElement si;

    public static final String SHABRI_INVESTMENTS = "//android.widget.TextView[@text='Shabri Investments']";

    @AndroidFindBy(xpath = SHABRI_INVESTMENTS)
    private WebElement shabriInvestments;

    public static final String PM = "//android.widget.TextView[@text='PM']";

    @AndroidFindBy(xpath = PM)
    private WebElement pm;

    public static final String POPULAR_MOTORS = "//android.widget.TextView[@text='Popular Motors']";

    @AndroidFindBy(xpath = POPULAR_MOTORS)
    private WebElement popularMotors;

    public static final String SWIGGY = "//android.widget.TextView[@text='Swiggy']";

    @AndroidFindBy(xpath = SWIGGY)
    private WebElement swiggy;

    public static final String CASH_DROP = "//android.widget.TextView[@text='Cash Drop']";

    @AndroidFindBy(xpath = CASH_DROP)
    private WebElement cashDrop;

    public static final String HC = "//android.widget.TextView[@text='HC']";

    @AndroidFindBy(xpath = HC)
    private WebElement hc;

    public static final String HOME_CREDIT = "//android.widget.TextView[@text='Home Credit']";

    @AndroidFindBy(xpath = HOME_CREDIT)
    private WebElement homeCredit;

    public static final String M = "//android.widget.TextView[@text='M']";

    @AndroidFindBy(xpath = M)
    private WebElement m;

    public static final String MARGDARSHAK = "//android.widget.TextView[@text='Margdarshak']";

    @AndroidFindBy(xpath = MARGDARSHAK)
    private WebElement margdarshak;

    public static final String LF = "//android.widget.TextView[@text='LF']";

    @AndroidFindBy(xpath = LF)
    private WebElement lf;

    public static final String LNT_FINANCE = "//android.widget.TextView[@text='LnT Finance']";

    @AndroidFindBy(xpath = LNT_FINANCE)
    private WebElement lntFinance;

    public static final String P = "//android.widget.TextView[@text='P']";

    @AndroidFindBy(xpath = P)
    private WebElement p;

    public static final String PARTNER = "//android.widget.TextView[@text='Partner']";

    @AndroidFindBy(xpath = PARTNER)
    private WebElement partner;

    public static final String CP = "//android.widget.TextView[@text='CP']";

    @AndroidFindBy(xpath = CP)
    private WebElement cp;

    public static final String CHILD_PARTNER_OF_PARENT_ID = "//android.widget.TextView[@text='child partner of parent id']";

    @AndroidFindBy(xpath = CHILD_PARTNER_OF_PARENT_ID)
    private WebElement childPartnerOfParentId;

    public static final String J = "//android.widget.TextView[@text='J']";

    @AndroidFindBy(xpath = J)
    private WebElement j;

    public static final String JAYBESS = "//android.widget.TextView[@text='jaybess']";

    @AndroidFindBy(xpath = JAYBESS)
    private WebElement jaybess;

    public static final String SHAIKH = "//android.widget.TextView[@text='Shaikh']";

    @AndroidFindBy(xpath = SHAIKH)
    private WebElement shaikh;

    public static final String PS = "//android.widget.TextView[@text='PS']";

    @AndroidFindBy(xpath = PS)
    private WebElement ps;

    public static final String PARTNER_SIMPLEICASH_CUSTOMERCOMMISSION = "//android.widget.TextView[@text='Partner Simpleicash customerCommission']";

    @AndroidFindBy(xpath = PARTNER_SIMPLEICASH_CUSTOMERCOMMISSION)
    private WebElement partnerSimpleicashCustomercommission;

    public static final String TP = "//android.widget.TextView[@text='TP']";

    @AndroidFindBy(xpath = TP)
    private WebElement tp;

    public static final String TEST_PERTNER = "//android.widget.TextView[@text='test pertner']";

    @AndroidFindBy(xpath = TEST_PERTNER)
    private WebElement testPertner;

    public static final String PW = "//android.widget.TextView[@text='PW']";

    @AndroidFindBy(xpath = PW)
    private WebElement pw;

    public static final String PARENT_WITHOUT_CHILD = "//android.widget.TextView[@text='parent without child']";

    @AndroidFindBy(xpath = PARENT_WITHOUT_CHILD)
    private WebElement parentWithoutChild;

    public static final String A = "//android.widget.TextView[@text='A']";

    @AndroidFindBy(xpath = A)
    private WebElement a;

    public static final String ABC = "//android.widget.TextView[@text='ABC']";

    @AndroidFindBy(xpath = ABC)
    private WebElement abc;

    public static final String COM_ANDROID_SYSTEMUI_ID_LEGACY_WINDOW_ROOT = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/legacy_window_root']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_LEGACY_WINDOW_ROOT)
    private WebElement comAndroidSystemuiIdLegacyWindowRoot;

    public static final String COM_ANDROID_SYSTEMUI_ID_CAPTURED_BLUR_CONTAINER = "//android.view.View[@resource-id='com.android.systemui:id/captured_blur_container']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_CAPTURED_BLUR_CONTAINER)
    private WebElement comAndroidSystemuiIdCapturedBlurContainer;

    public static final String COM_ANDROID_SYSTEMUI_ID_LOCK_CLOCK_BLUR = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/lock_clock_blur']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_LOCK_CLOCK_BLUR)
    private WebElement comAndroidSystemuiIdLockClockBlur;

    public static final String COM_ANDROID_SYSTEMUI_ID_NOTIFICATION_PANEL = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/notification_panel']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_NOTIFICATION_PANEL)
    private WebElement comAndroidSystemuiIdNotificationPanel;

    public static final String COM_ANDROID_SYSTEMUI_ID_NOTIFICATION_CONTAINER_PARENT = "//android.view.ViewGroup[@resource-id='com.android.systemui:id/notification_container_parent']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_NOTIFICATION_CONTAINER_PARENT)
    private WebElement comAndroidSystemuiIdNotificationContainerParent;

    public static final String COM_ANDROID_SYSTEMUI_ID_QS_GRADATION_VIEW = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/qs_gradation_view']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_QS_GRADATION_VIEW)
    private WebElement comAndroidSystemuiIdQsGradationView;

    public static final String COM_ANDROID_SYSTEMUI_ID_SPLIT_SHADE_STATUS_BAR = "//android.view.ViewGroup[@resource-id='com.android.systemui:id/split_shade_status_bar']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_SPLIT_SHADE_STATUS_BAR)
    private WebElement comAndroidSystemuiIdSplitShadeStatusBar;

    public static final String COM_ANDROID_SYSTEMUI_ID_CARRIER_GROUP = "//android.widget.LinearLayout[@resource-id='com.android.systemui:id/carrier_group']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_CARRIER_GROUP)
    private WebElement comAndroidSystemuiIdCarrierGroup;

    public static final String COM_ANDROID_SYSTEMUI_ID_ANIM_VIEW = "//android.widget.LinearLayout[@resource-id='com.android.systemui:id/anim_view']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_ANIM_VIEW)
    private WebElement comAndroidSystemuiIdAnimView;

    public static final String COM_ANDROID_SYSTEMUI_ID_CARRIER1 = "//android.widget.LinearLayout[@resource-id='com.android.systemui:id/carrier1']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_CARRIER1)
    private WebElement comAndroidSystemuiIdCarrier1;

    public static final String AIRTEL_JIO_TRUE5G_JIO = "//android.widget.TextView[@text='airtel • Jio True5G | Jio']";

    @AndroidFindBy(xpath = AIRTEL_JIO_TRUE5G_JIO)
    private WebElement airtelJioTrue5gJio;

    public static final String COM_ANDROID_SYSTEMUI_ID_SHADE_HEADER_SYSTEM_ICONS = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/shade_header_system_icons']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_SHADE_HEADER_SYSTEM_ICONS)
    private WebElement comAndroidSystemuiIdShadeHeaderSystemIcons;

    public static final String COM_ANDROID_SYSTEMUI_ID_HOVER_SYSTEM_ICONS_CONTAINER = "//android.widget.LinearLayout[@resource-id='com.android.systemui:id/hover_system_icons_container']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_HOVER_SYSTEM_ICONS_CONTAINER)
    private WebElement comAndroidSystemuiIdHoverSystemIconsContainer;

    public static final String COM_ANDROID_SYSTEMUI_ID_STATUSICONS = "//android.widget.LinearLayout[@resource-id='com.android.systemui:id/statusIcons']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_STATUSICONS)
    private WebElement comAndroidSystemuiIdStatusicons;

    public static final String NFC_ON = "//android.widget.ImageView[contains(@content-desc,'NFC on')]";

    @AndroidFindBy(xpath = NFC_ON)
    private WebElement nfcOn;

    public static final String ALARM = "//android.widget.ImageView[contains(@content-desc,'Alarm')]";

    @AndroidFindBy(xpath = ALARM)
    private WebElement alarm;

    public static final String BLUETOOTH_CONNECTED = "//android.widget.ImageView[contains(@content-desc,'Bluetooth connected.')]";

    @AndroidFindBy(xpath = BLUETOOTH_CONNECTED)
    private WebElement bluetoothConnected;

    public static final String RINGER_VIBRATE = "//android.widget.ImageView[contains(@content-desc,'Ringer vibrate.')]";

    @AndroidFindBy(xpath = RINGER_VIBRATE)
    private WebElement ringerVibrate;

    public static final String VOLTE = "//android.widget.ImageView[contains(@content-desc,'VoLTE')]";

    @AndroidFindBy(xpath = VOLTE)
    private WebElement volte;

    public static final String AIRTEL_SIGNAL_FULL = "//android.widget.FrameLayout[contains(@content-desc,'airtel, signal full.')]";

    @AndroidFindBy(xpath = AIRTEL_SIGNAL_FULL)
    private WebElement airtelSignalFull;

    public static final String COM_ANDROID_SYSTEMUI_ID_MOBILE_GROUP = "//android.widget.LinearLayout[@resource-id='com.android.systemui:id/mobile_group']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_MOBILE_GROUP)
    private WebElement comAndroidSystemuiIdMobileGroup;

    public static final String COM_ANDROID_SYSTEMUI_ID_MOBILE_TYPE_CONTAINER = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/mobile_type_container']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_MOBILE_TYPE_CONTAINER)
    private WebElement comAndroidSystemuiIdMobileTypeContainer;

    public static final String COM_ANDROID_SYSTEMUI_ID_MOBILE_TYPE = "//android.widget.ImageView[@resource-id='com.android.systemui:id/mobile_type']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_MOBILE_TYPE)
    private WebElement comAndroidSystemuiIdMobileType;

    public static final String COM_ANDROID_SYSTEMUI_ID_DATA_ACTIVITY = "//android.widget.ImageView[@resource-id='com.android.systemui:id/data_activity']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_DATA_ACTIVITY)
    private WebElement comAndroidSystemuiIdDataActivity;

    public static final String COM_ANDROID_SYSTEMUI_ID_MOBILE_SIGNAL = "//android.widget.ImageView[@resource-id='com.android.systemui:id/mobile_signal']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_MOBILE_SIGNAL)
    private WebElement comAndroidSystemuiIdMobileSignal;

    public static final String COM_ANDROID_SYSTEMUI_ID_MOBILE_ROAMING = "//android.widget.ImageView[@resource-id='com.android.systemui:id/mobile_roaming']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_MOBILE_ROAMING)
    private WebElement comAndroidSystemuiIdMobileRoaming;

    public static final String JIO_TRUE5G_JIO_SIGNAL_FULL = "//android.widget.FrameLayout[contains(@content-desc,'Jio True5G-Jio, signal full.')]";

    @AndroidFindBy(xpath = JIO_TRUE5G_JIO_SIGNAL_FULL)
    private WebElement jioTrue5gJioSignalFull;

    public static final String BATTERY_CHARGING100PERCENT = "//android.widget.LinearLayout[contains(@content-desc,'Battery charging, 100 percent.')]";

    @AndroidFindBy(xpath = BATTERY_CHARGING100PERCENT)
    private WebElement batteryCharging100Percent;

    public static final String COM_ANDROID_SYSTEMUI_ID_QS_FRAME = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/qs_frame']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_QS_FRAME)
    private WebElement comAndroidSystemuiIdQsFrame;

    public static final String COM_ANDROID_SYSTEMUI_ID_QUICK_SETTINGS_CONTAINER = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/quick_settings_container']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_QUICK_SETTINGS_CONTAINER)
    private WebElement comAndroidSystemuiIdQuickSettingsContainer;

    public static final String COM_ANDROID_SYSTEMUI_ID_HEADER = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/header']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_HEADER)
    private WebElement comAndroidSystemuiIdHeader;

    public static final String COM_ANDROID_SYSTEMUI_ID_QS_CONTAINER = "//android.widget.RelativeLayout[@resource-id='com.android.systemui:id/qs_container']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_QS_CONTAINER)
    private WebElement comAndroidSystemuiIdQsContainer;

    public static final String COM_ANDROID_SYSTEMUI_ID_QUICK_QS_DATE_BUTTONS = "//android.widget.LinearLayout[@resource-id='com.android.systemui:id/quick_qs_date_buttons']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_QUICK_QS_DATE_BUTTONS)
    private WebElement comAndroidSystemuiIdQuickQsDateButtons;

    public static final String COM_ANDROID_SYSTEMUI_ID_CLOCK_DATE_BUTTONS_CONTAINER = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/clock_date_buttons_container']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_CLOCK_DATE_BUTTONS_CONTAINER)
    private WebElement comAndroidSystemuiIdClockDateButtonsContainer;

    public static final String COM_ANDROID_SYSTEMUI_ID_CLOCK_PARENT = "//android.widget.LinearLayout[@resource-id='com.android.systemui:id/clock_parent']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_CLOCK_PARENT)
    private WebElement comAndroidSystemuiIdClockParent;

    public static final String COM_ANDROID_SYSTEMUI_ID_CONTAINER_FOR_IMMERSIVE_POP = "//android.widget.LinearLayout[@resource-id='com.android.systemui:id/container_for_immersive_pop_over']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_CONTAINER_FOR_IMMERSIVE_POP)
    private WebElement comAndroidSystemuiIdContainerForImmersivePop;

    public static final String EL455PM = "//android.widget.TextView[contains(@content-desc,'4:55 pm')]";

    @AndroidFindBy(xpath = EL455PM)
    private WebElement el455Pm;

    public static final String MON26OCT = "//android.widget.TextView[@text='Mon, 26 Oct']";

    @AndroidFindBy(xpath = MON26OCT)
    private WebElement mon26Oct;

    public static final String COM_ANDROID_SYSTEMUI_ID_EXPANDED_QS_SCROLL_VIEW = "//android.widget.ScrollView[@resource-id='com.android.systemui:id/expanded_qs_scroll_view']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_EXPANDED_QS_SCROLL_VIEW)
    private WebElement comAndroidSystemuiIdExpandedQsScrollView;

    public static final String COM_ANDROID_SYSTEMUI_ID_SHARED_NOTIFICATION_CONTAINER = "//android.view.ViewGroup[@resource-id='com.android.systemui:id/shared_notification_container']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_SHARED_NOTIFICATION_CONTAINER)
    private WebElement comAndroidSystemuiIdSharedNotificationContainer;

    public static final String COM_ANDROID_SYSTEMUI_ID_NOTIFICATION_STACK_SCROLLER = "//android.widget.ScrollView[@resource-id='com.android.systemui:id/notification_stack_scroller']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_NOTIFICATION_STACK_SCROLLER)
    private WebElement comAndroidSystemuiIdNotificationStackScroller;

    public static final String COM_ANDROID_SYSTEMUI_ID_EXPANDABLENOTIFICATIONROW = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/expandableNotificationRow']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_EXPANDABLENOTIFICATIONROW)
    private WebElement comAndroidSystemuiIdExpandablenotificationrow;

    public static final String COM_ANDROID_SYSTEMUI_ID_BACKGROUNDNORMAL = "//android.view.View[@resource-id='com.android.systemui:id/backgroundNormal']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_BACKGROUNDNORMAL)
    private WebElement comAndroidSystemuiIdBackgroundnormal;

    public static final String COM_ANDROID_SYSTEMUI_ID_BACKGROUNDNORMAL_EFFECT = "//android.view.View[@resource-id='com.android.systemui:id/backgroundNormal_effect']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_BACKGROUNDNORMAL_EFFECT)
    private WebElement comAndroidSystemuiIdBackgroundnormalEffect;

    public static final String COM_ANDROID_SYSTEMUI_ID_BACKGROUNDDIMMED = "//android.view.View[@resource-id='com.android.systemui:id/backgroundDimmed']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_BACKGROUNDDIMMED)
    private WebElement comAndroidSystemuiIdBackgrounddimmed;

    public static final String COM_ANDROID_SYSTEMUI_ID_EXPANDED = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/expanded']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_EXPANDED)
    private WebElement comAndroidSystemuiIdExpanded;

    public static final String MESSAGES = "//android.widget.TextView[@text='Messages']";

    @AndroidFindBy(xpath = MESSAGES)
    private WebElement messages;

    public static final String EL454PM = "//android.widget.TextView[@text='4:54 pm']";

    @AndroidFindBy(xpath = EL454PM)
    private WebElement el454Pm;

    public static final String COLLAPSE = "//android.widget.Button[contains(@content-desc,'Collapse')]";

    @AndroidFindBy(xpath = COLLAPSE)
    private WebElement collapse;

    public static final String JX_SBIBNK_S = "//android.widget.TextView[@text='⁨JX-SBIBNK-S⁩']";

    @AndroidFindBy(xpath = JX_SBIBNK_S)
    private WebElement jxSbibnkS;

    public static final String DEAR_MR_MS_MAMTA_VERMA_WE_HAVE_RECEIVED = "//android.widget.TextView[@text='Dear Mr/Ms Mamta VERMA, We have received your application for Surya Ghar at Saharanpur (710) with loan amount of Rs 220000 for processing. RLMS App Ref No: RAASSG20269028913889. -SBI']";

    @AndroidFindBy(xpath = DEAR_MR_MS_MAMTA_VERMA_WE_HAVE_RECEIVED)
    private WebElement dearMrMsMamtaVermaWeHaveReceived;

    public static final String DELETE = "//android.widget.Button[contains(@content-desc,'Delete')]";

    @AndroidFindBy(xpath = DELETE)
    private WebElement delete;

    public static final String MARK_AS_READ = "//android.widget.Button[contains(@content-desc,'Mark as read')]";

    @AndroidFindBy(xpath = MARK_AS_READ)
    private WebElement markAsRead;

    public static final String VIEW = "//android.widget.Button[contains(@content-desc,'View')]";

    @AndroidFindBy(xpath = VIEW)
    private WebElement view;

    public static final String SHOW_BUBBLE = "//android.widget.ImageView[contains(@content-desc,'Show bubble')]";

    @AndroidFindBy(xpath = SHOW_BUBBLE)
    private WebElement showBubble;

    public static final String COM_ANDROID_SYSTEMUI_ID_FAKE_SHADOW = "//android.widget.FrameLayout[@resource-id='com.android.systemui:id/fake_shadow']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_FAKE_SHADOW)
    private WebElement comAndroidSystemuiIdFakeShadow;

    public static final String RAAS = "//android.widget.TextView[@text='raas']";

    @AndroidFindBy(xpath = RAAS)
    private WebElement raas;

    public static final String KAILASH_VERMA_INFORM_GMAIL_COM = "//android.widget.TextView[@text='kailash.verma.inform@gmail.com']";

    @AndroidFindBy(xpath = KAILASH_VERMA_INFORM_GMAIL_COM)
    private WebElement kailashVermaInformGmailCom;

    public static final String SURYA_GHAR_PROPOSAL_RECEIVED = "//android.widget.TextView[@text='Surya Ghar Proposal Received']";

    @AndroidFindBy(xpath = SURYA_GHAR_PROPOSAL_RECEIVED)
    private WebElement suryaGharProposalReceived;

    public static final String EXPAND = "//android.widget.Button[contains(@content-desc,'Expand')]";

    @AndroidFindBy(xpath = EXPAND)
    private WebElement expand;

    public static final String COM_ANDROID_SYSTEMUI_ID_NOTIFICATION_CHILDREN_CONTAINER = "//android.view.ViewGroup[@resource-id='com.android.systemui:id/notification_children_container']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_NOTIFICATION_CHILDREN_CONTAINER)
    private WebElement comAndroidSystemuiIdNotificationChildrenContainer;

    public static final String TELEGRAM = "//android.widget.TextView[@text='Telegram']";

    @AndroidFindBy(xpath = TELEGRAM)
    private WebElement telegram;

    public static final String EL437PM = "//android.widget.TextView[@text='4:37 pm']";

    @AndroidFindBy(xpath = EL437PM)
    private WebElement el437Pm;

    public static final String NEW_MESSAGE = "//android.widget.TextView[@text='New message']";

    @AndroidFindBy(xpath = NEW_MESSAGE)
    private WebElement newMessage;

    public static final String EL3 = "//android.widget.TextView[@text='3']";

    @AndroidFindBy(xpath = EL3)
    private WebElement el3;

    public static final String ELEMENT = "//android.widget.TextView[@text='🚀🚀🚀🚀']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String EL429PM = "//android.widget.TextView[@text='4:29 pm']";

    @AndroidFindBy(xpath = EL429PM)
    private WebElement el429Pm;

    public static final String NITESH_LOHCHAB = "//android.widget.TextView[@text='Nitesh Lohchab:']";

    @AndroidFindBy(xpath = NITESH_LOHCHAB)
    private WebElement niteshLohchab;

    public static final String EL2NEW_MESSAGES = "//android.widget.TextView[@text='2 new messages']";

    @AndroidFindBy(xpath = EL2NEW_MESSAGES)
    private WebElement el2NewMessages;

    public static final String NEED_EXTRA_FUNDS = "//android.widget.TextView[@text='Need Extra Funds? 💸']";

    @AndroidFindBy(xpath = NEED_EXTRA_FUNDS)
    private WebElement needExtraFunds;

    public static final String BHIM_APP = "//android.widget.TextView[@text='BHIM App']";

    @AndroidFindBy(xpath = BHIM_APP)
    private WebElement bhimApp;

    public static final String EL426PM = "//android.widget.TextView[@text='4:26 pm']";

    @AndroidFindBy(xpath = EL426PM)
    private WebElement el426Pm;

    public static final String GET_ATOP_UP_LOAN_OF_UP_TO = "//android.widget.TextView[@text='Get a top-up loan of up to ₹20,000!']";

    @AndroidFindBy(xpath = GET_ATOP_UP_LOAN_OF_UP_TO)
    private WebElement getATopUpLoanOfUpTo;

    public static final String GRAB_SELFIE_STICKS_FROM_JUST149 = "//android.widget.TextView[@text='🤳 Grab selfie sticks From Just ₹149!']";

    @AndroidFindBy(xpath = GRAB_SELFIE_STICKS_FROM_JUST149)
    private WebElement grabSelfieSticksFromJust149;

    public static final String EL421PM = "//android.widget.TextView[@text='4:21 pm']";

    @AndroidFindBy(xpath = EL421PM)
    private WebElement el421Pm;

    public static final String NEVER_MISS_THE_PERFECT_SHOT_AGAIN_SHOP_NOW = "//android.widget.TextView[@text='Never miss the perfect shot again! Shop now']";

    @AndroidFindBy(xpath = NEVER_MISS_THE_PERFECT_SHOT_AGAIN_SHOP_NOW)
    private WebElement neverMissThePerfectShotAgainShopNow;

    public static final String WALLET_KA_DIET_RAKHEIN_TIGHT = "//android.widget.TextView[@text='Wallet ka diet rakhein tight!']";

    @AndroidFindBy(xpath = WALLET_KA_DIET_RAKHEIN_TIGHT)
    private WebElement walletKaDietRakheinTight;

    public static final String EL414PM = "//android.widget.TextView[@text='4:14 pm']";

    @AndroidFindBy(xpath = EL414PM)
    private WebElement el414Pm;

    public static final String SUBSTITUTE_MEDS_PAR_SWITCH_KARNE_KAR_PAAYEIN_UP = "//android.widget.TextView[@text='Substitute meds par switch karne kar paayein up to 51%* savings. Switch now! *T&C']";

    @AndroidFindBy(xpath = SUBSTITUTE_MEDS_PAR_SWITCH_KARNE_KAR_PAAYEIN_UP)
    private WebElement substituteMedsParSwitchKarneKarPaayeinUp;

    public static final String COM_ANDROID_SYSTEMUI_ID_NOTIFICATION_SHELF_TEXT_AREA = "//android.widget.LinearLayout[@resource-id='com.android.systemui:id/notification_shelf_text_area']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_NOTIFICATION_SHELF_TEXT_AREA)
    private WebElement comAndroidSystemuiIdNotificationShelfTextArea;

    public static final String NOTIFICATION_SETTINGS_BUTTON = "//android.widget.TextView[contains(@content-desc,'Notification settings,Button')]";

    @AndroidFindBy(xpath = NOTIFICATION_SETTINGS_BUTTON)
    private WebElement notificationSettingsButton;

    public static final String CLEAR_BUTTON = "//android.widget.TextView[contains(@content-desc,'Clear,Button')]";

    @AndroidFindBy(xpath = CLEAR_BUTTON)
    private WebElement clearButton;

    public static final String COM_ANDROID_SYSTEMUI_ID_KEYGUARD_MESSAGE_AREA_CONTAINER = "//android.widget.LinearLayout[@resource-id='com.android.systemui:id/keyguard_message_area_container']";

    @AndroidFindBy(xpath = COM_ANDROID_SYSTEMUI_ID_KEYGUARD_MESSAGE_AREA_CONTAINER)
    private WebElement comAndroidSystemuiIdKeyguardMessageAreaContainer;

    public static final String ARCHIVE = "//android.widget.Button[contains(@content-desc,'Archive')]";

    @AndroidFindBy(xpath = ARCHIVE)
    private WebElement archive;

    public static final String REPLY = "//android.widget.Button[contains(@content-desc,'Reply')]";

    @AndroidFindBy(xpath = REPLY)
    private WebElement reply;

    public static final String GMAIL = "//android.widget.TextView[@text='Gmail']";

    @AndroidFindBy(xpath = GMAIL)
    private WebElement gmail;

    public HomeCreditBalanceCmsCashDrop3Screen(TestContext context) {
        super(context);
    }
}
