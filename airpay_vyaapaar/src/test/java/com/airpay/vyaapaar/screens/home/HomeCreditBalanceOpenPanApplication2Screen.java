package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** airpay vyaapaar / Use PIN. Module home. */
public class HomeCreditBalanceOpenPanApplication2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-open-pan-application-use-pin";

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_ID = "//android.widget.FrameLayout[@resource-id='com.samsung.android.biometrics.app.setting:id/id_root_overlay_container']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_ID)
    private WebElement comSamsungAndroidBiometricsAppSettingIdId;

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_BACKGROUND = "//android.widget.ImageView[@resource-id='com.samsung.android.biometrics.app.setting:id/background']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_BACKGROUND)
    private WebElement comSamsungAndroidBiometricsAppSettingIdBackground;

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_PANEL = "//android.view.View[@resource-id='com.samsung.android.biometrics.app.setting:id/panel']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_PANEL)
    private WebElement comSamsungAndroidBiometricsAppSettingIdPanel;

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_BASE = "//android.widget.LinearLayout[@resource-id='com.samsung.android.biometrics.app.setting:id/base_view']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_BASE)
    private WebElement comSamsungAndroidBiometricsAppSettingIdBase;

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_MAIN = "//android.view.ViewGroup[@resource-id='com.samsung.android.biometrics.app.setting:id/main_view']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_MAIN)
    private WebElement comSamsungAndroidBiometricsAppSettingIdMain;

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_ICON = "//android.widget.ImageView[@resource-id='com.samsung.android.biometrics.app.setting:id/icon']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_ICON)
    private WebElement comSamsungAndroidBiometricsAppSettingIdIcon;

    public static final String VERIFY_IDENTITY = "//android.widget.TextView[@text='Verify Identity']";

    @AndroidFindBy(xpath = VERIFY_IDENTITY)
    private WebElement verifyIdentity;

    public static final String LOG_IN_USING_YOUR_DEVICE_CREDENTIALS = "//android.widget.TextView[@text='Log in using your device credentials']";

    @AndroidFindBy(xpath = LOG_IN_USING_YOUR_DEVICE_CREDENTIALS)
    private WebElement logInUsingYourDeviceCredentials;

    public static final String ENTER_YOUR_CURRENT_PIN = "//android.widget.TextView[@text='Enter your current PIN.']";

    @AndroidFindBy(xpath = ENTER_YOUR_CURRENT_PIN)
    private WebElement enterYourCurrentPin;

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_LOCKPASSWORD = "//android.widget.EditText[@resource-id='com.samsung.android.biometrics.app.setting:id/lockPassword']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_LOCKPASSWORD)
    private WebElement comSamsungAndroidBiometricsAppSettingIdLockpassword;

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_BOTTOM = "//android.view.View[@resource-id='com.samsung.android.biometrics.app.setting:id/bottom_space']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_BOTTOM)
    private WebElement comSamsungAndroidBiometricsAppSettingIdBottom;

    public static final String CANCEL = "//android.widget.Button[@text='Cancel']";

    @AndroidFindBy(xpath = CANCEL)
    private WebElement cancel;

    public static final String COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_BUTTON = "//android.view.View[@resource-id='com.samsung.android.biometrics.app.setting:id/button_divider']";

    @AndroidFindBy(xpath = COM_SAMSUNG_ANDROID_BIOMETRICS_APP_SETTING_ID_BUTTON)
    private WebElement comSamsungAndroidBiometricsAppSettingIdButton;

    public static final String EL_CONTINUE = "//android.widget.Button[@text='Continue']";

    @AndroidFindBy(xpath = EL_CONTINUE)
    private WebElement elContinue;

    public HomeCreditBalanceOpenPanApplication2Screen(TestContext context) {
        super(context);
    }
}
