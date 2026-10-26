package com.airpay.money.screens.profile;

/**
 * Live dump 2026-09-21 — Profile hub (Home avatar), General settings, Contact Us,
 * Transaction History. Native Flutter inside {@code com.finmapp.fininfinity}.
 *
 * A11y:
 * Follow-us icons are NAF ImageViews with empty content-desc.
 * Two Home header buttons share content-desc "Menu option".
 * Screen Lock has no checked/selected state.
 * Change PIN has no in-app back.
 */
public class ProfileHubScreen extends com.airpay.money.screens.base.BaseScreen {

    public ProfileHubScreen(com.airpay.mobile.utilities.context.TestContext context) {
        super(context);
    }

    public static final String OPEN_SETTINGS = "//android.widget.Button[@content-desc='Open settings']";
    public static final String CHANGE_PROFILE_PICTURE = "//android.widget.ImageView[@content-desc='Change profile picture']";
    public static final String PROFILE_HEADER =
            "//android.view.View[contains(@content-desc,'Verified') and contains(@content-desc,'+91')]";
    public static final String SECTION_PROFILE_SETTINGS = "//android.view.View[@content-desc='PROFILE SETTINGS']";

    public static final String PERSONAL_DETAILS = "//android.widget.Button[@content-desc='Personal Details']";
    public static final String WORK_DETAILS = "//android.widget.Button[@content-desc='Work Details']";
    public static final String ADDRESS_DETAILS = "//android.widget.Button[@content-desc='Address Details']";
    public static final String BANK_ACCOUNT = "//android.widget.Button[@content-desc='Bank Account']";
    public static final String DOCUMENT_DETAILS = "//android.widget.Button[@content-desc='Document Details']";
    public static final String KYC_DETAILS = "//android.widget.Button[@content-desc='KYC Details, Verified']";
    public static final String TRANSACTION_HISTORY = "//android.widget.ImageView[@content-desc='Transaction history']";
    public static final String CONTACT_US = "//android.widget.ImageView[@content-desc='Contact Us']";
    public static final String HOW_CAN_WE_HELP = "//android.widget.Button[@content-desc='How can we help you?']";

    public static final String SECTION_GENERAL_SETTINGS = "//android.view.View[@content-desc='GENERAL SETTINGS']";
    public static final String SCREEN_LOCK = "//android.widget.Button[@content-desc='Screen Lock']";
    public static final String CHANGE_PIN = "//android.widget.Button[@content-desc='Change PIN']";
    public static final String UTILITIES = "//android.widget.Button[@content-desc='Utilities']";
    public static final String TERMS_AND_CONDITIONS = "//android.widget.Button[@content-desc='Terms & Conditions']";
    public static final String PRIVACY_POLICY = "//android.widget.Button[@content-desc='Privacy Policy']";
    public static final String ACCOUNT_MANAGEMENT = "//android.widget.Button[@content-desc='Account Management']";
    public static final String RATE_US = "//android.widget.Button[@content-desc='Rate Us']";
    public static final String FAQS = "//android.widget.Button[@content-desc='FAQs']";
    public static final String ABOUT_AIRPAY_MONEY = "//android.widget.Button[@content-desc='About airpay money']";
    public static final String FOLLOW_US_ON = "//android.view.View[@content-desc='Follow us on']";
    /** Four unlabelled social icons sit after Follow us on; first one only. */
    public static final String FOLLOW_US_ICON = "//android.widget.ImageView[@clickable='true' and @content-desc='']";
    public static final String APP_VERSION = "//android.view.View[contains(@content-desc,'App Version')]";
    public static final String LOG_OUT = "//android.view.View[@content-desc='Log Out']";

    public static final String CHANGE_PIN_TITLE = "//android.view.View[@content-desc='Enter your New PIN']";
    public static final String CHANGE_PIN_HINT = "//android.view.View[@content-desc='Create a unique 6-digit PIN']";
    public static final String CHANGE_PIN_PROGRESS = "//android.view.View[contains(@content-desc,'MPIN entered:')]";
    public static final String CHANGE_PIN_DIGIT_0 = "//android.widget.Button[@content-desc='Digit 0\n0']";
    public static final String CHANGE_PIN_BACKSPACE = "//android.widget.Button[@content-desc='Backspace']";

    public static final String ACCOUNT_MGMT_NO_CONSENT = "//android.view.View[@content-desc='No Consent Found']";
    public static final String DISMISS = "//android.view.View[@content-desc='Dismiss']";

    public static final String RATE_US_TITLE = "//android.view.View[@content-desc='Enjoying the airpay money app?']";
    public static final String RATE_US_THUMBS_DOWN = "//android.widget.Button[@content-desc='👎']";
    public static final String RATE_US_THUMBS_UP = "//android.widget.Button[@content-desc='👍']";
    public static final String RATE_FEEDBACK_TITLE = "//android.view.View[@content-desc=\"We're sorry to hear that\"]";
    public static final String RATE_FEEDBACK_OPTIONAL = "//android.widget.EditText[@hint='Your feedback (optional)']";
    public static final String RATE_SUBMIT_FEEDBACK = "//android.widget.Button[@content-desc='Submit Feedback']";

    public static final String CONTACT_TITLE = "//android.view.View[@content-desc='Contact Us']";
    public static final String CONTACT_FULL_NAME = "//android.widget.EditText[@hint='Full Name']";
    public static final String CONTACT_EMAIL = "//android.widget.EditText[@hint='Email']";
    public static final String CONTACT_PHONE = "//android.widget.EditText[@hint='Phone Number']";
    public static final String CONTACT_SUBJECT = "//android.widget.EditText[contains(@hint,'Subject')]";
    public static final String CONTACT_MESSAGE = "//android.widget.EditText[@hint='Message']";
    public static final String CONTACT_ATTACH_FILE = "//android.view.View[@content-desc='Attach File']";
    public static final String CONTACT_SEND = "//android.widget.Button[@content-desc='Send Message']";

    public static final String TX_TITLE = "//android.view.View[@content-desc='Transaction History']";
    public static final String TX_BACK = "//android.widget.Button[@content-desc='Back']";
    public static final String TX_MUTUAL_FUNDS = "//android.view.View[@content-desc='Mutual Funds']";
    public static final String TX_DIGITAL_GOLD = "//android.view.View[@content-desc='Digital Gold']";
    public static final String TX_NPS = "//android.view.View[@content-desc='NPS']";
    public static final String TX_TAB_ALL = "//android.widget.Button[@content-desc='All']";
    public static final String TX_TAB_ONE_TIME = "//android.widget.Button[@content-desc='One Time']";
    public static final String TX_TAB_SIP = "//android.widget.Button[@content-desc='SIP']";
    public static final String TX_TAB_REDEMPTION = "//android.widget.Button[@content-desc='Redemption']";
    public static final String TX_EMPTY = "//android.view.View[@content-desc='No Transaction']";

    public static final String WEBVIEW_BACK = "//android.widget.Button[@bounds='[56,123][169,219]']";
    public static final String WEBVIEW = "//android.webkit.WebView";
}
