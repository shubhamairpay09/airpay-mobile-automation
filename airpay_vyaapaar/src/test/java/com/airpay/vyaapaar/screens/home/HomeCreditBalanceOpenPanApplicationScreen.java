package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar. Module home. */
public class HomeCreditBalanceOpenPanApplicationScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-open-pan-application";

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_ID = "//android.widget.FrameLayout[@resource-id='com.samsung.android.biometrics.app.setting:id/id_root_overlay_container']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_ID)
    private WebElement comSamsungAndroidBiometricsAppSettingIdId;

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_LOGO = "//android.widget.ImageView[@resource-id='com.samsung.android.biometrics.app.setting:id/logo']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_LOGO)
    private WebElement comSamsungAndroidBiometricsAppSettingIdLogo;

    public static final String AIRPAY_VYAAPAAR = "//android.widget.TextView[@text='airpay vyaapaar']";

    @AndroidFindBy(xpath = AIRPAY_VYAAPAAR)
    private WebElement airpayVyaapaar;

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_SCROLLVIEW = "//android.widget.ScrollView[@resource-id='com.samsung.android.biometrics.app.setting:id/scrollView']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_SCROLLVIEW)
    private WebElement comSamsungAndroidBiometricsAppSettingIdScrollview;

    public static final String VERIFY_IDENTITY = "//android.widget.TextView[@text='Verify Identity']";

    @AndroidFindBy(xpath = VERIFY_IDENTITY)
    private WebElement verifyIdentity;

    public static final String LOG_IN_USING_YOUR_DEVICE_CREDENTIALS = "//android.widget.TextView[@text='Log in using your device credentials']";

    @AndroidFindBy(xpath = LOG_IN_USING_YOUR_DEVICE_CREDENTIALS)
    private WebElement logInUsingYourDeviceCredentials;

    public static final String SCAN_YOUR_FINGERPRINT = "//android.widget.TextView[@text='Scan your fingerprint.']";

    @AndroidFindBy(xpath = SCAN_YOUR_FINGERPRINT)
    private WebElement scanYourFingerprint;

    public static final String USE_PIN = "//android.widget.Button[@text='Use PIN']";

    @AndroidFindBy(xpath = USE_PIN)
    private WebElement usePin;

    public static final String MISSED_CALL_FROM_SUSPECTED_SPAM = "//android.widget.Toast[@text='Missed call from Suspected Spam']";

    @AndroidFindBy(xpath = MISSED_CALL_FROM_SUSPECTED_SPAM)
    private WebElement missedCallFromSuspectedSpam;

    public HomeCreditBalanceOpenPanApplicationScreen(TestContext context) {
        super(context);
    }
}
