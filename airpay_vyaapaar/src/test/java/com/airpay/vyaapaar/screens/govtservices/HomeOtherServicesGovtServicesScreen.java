package com.airpay.vyaapaar.screens.govtservices;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Other Services/Govt Services. Module govt-services. */
public class HomeOtherServicesGovtServicesScreen extends BaseScreen {

    public static final String PAGE_ID = "home-other-services-govt-services";

    public static final String COM_AIRPAY_MAHAEPAY_AEPS_ID_MAIN = "//android.widget.LinearLayout[@resource-id='com.airpay.mahaepay_aeps:id/main']";

    @AndroidFindBy(xpath = COM_AIRPAY_MAHAEPAY_AEPS_ID_MAIN)
    private WebElement comAirpayMahaepayAepsIdMain;

    public static final String COM_AIRPAY_MAHAEPAY_AEPS_ID_WEB_PARENT_LAYOUT = "//android.widget.FrameLayout[@resource-id='com.airpay.mahaepay_aeps:id/web_parent_layout_id']";

    @AndroidFindBy(xpath = COM_AIRPAY_MAHAEPAY_AEPS_ID_WEB_PARENT_LAYOUT)
    private WebElement comAirpayMahaepayAepsIdWebParentLayout;

    public static final String UMANG_LOGO = "//android.widget.Image[contains(@content-desc,'UMANG Logo')]";

    @AndroidFindBy(xpath = UMANG_LOGO)
    private WebElement umangLogo;

    public static final String WELCOME_TO_UMANG = "//android.widget.TextView[@text='Welcome to UMANG ']";

    @AndroidFindBy(xpath = WELCOME_TO_UMANG)
    private WebElement welcomeToUmang;

    public static final String ONE_SINGLE_APP_FOR_AVAILING_VARIOUS_GOVERMENT_SERVICES = "//android.widget.TextView[@text='One single app for availing various goverment services.']";

    @AndroidFindBy(xpath = ONE_SINGLE_APP_FOR_AVAILING_VARIOUS_GOVERMENT_SERVICES)
    private WebElement oneSingleAppForAvailingVariousGovermentServices;

    public static final String MAT_RADIO2 = "//android.view.View[@resource-id='mat-radio-2']";

    @AndroidFindBy(xpath = MAT_RADIO2)
    private WebElement matRadio2;

    public static final String MAT_RADIO2INPUT = "//android.widget.RadioButton[@resource-id='mat-radio-2-input']";

    @AndroidFindBy(xpath = MAT_RADIO2INPUT)
    private WebElement matRadio2Input;

    public static final String LOGIN_WITH_MPIN = "//android.widget.TextView[@text='Login With MPIN']";

    @AndroidFindBy(xpath = LOGIN_WITH_MPIN)
    private WebElement loginWithMpin;

    public static final String MAT_RADIO3 = "//android.view.View[@resource-id='mat-radio-3']";

    @AndroidFindBy(xpath = MAT_RADIO3)
    private WebElement matRadio3;

    public static final String MAT_RADIO3INPUT = "//android.widget.RadioButton[@resource-id='mat-radio-3-input']";

    @AndroidFindBy(xpath = MAT_RADIO3INPUT)
    private WebElement matRadio3Input;

    public static final String LOGIN_WITH_OTP = "//android.widget.TextView[@text='Login With OTP']";

    @AndroidFindBy(xpath = LOGIN_WITH_OTP)
    private WebElement loginWithOtp;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number *']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String MAT_FORM_FIELD_LABEL1 = "//android.view.View[@resource-id='mat-form-field-label-1']";

    @AndroidFindBy(xpath = MAT_FORM_FIELD_LABEL1)
    private WebElement matFormFieldLabel1;

    public static final String ELEMENT = "//android.widget.TextView[@text=' ']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String MPIN = "//android.widget.TextView[@text='MPIN *']";

    @AndroidFindBy(xpath = MPIN)
    private WebElement mpin;

    public static final String MAT_FORM_FIELD_LABEL3 = "//android.view.View[@resource-id='mat-form-field-label-3']";

    @AndroidFindBy(xpath = MAT_FORM_FIELD_LABEL3)
    private WebElement matFormFieldLabel3;

    public static final String MAT_INPUT1 = "//android.widget.EditText[@resource-id='mat-input-1']";

    @AndroidFindBy(xpath = MAT_INPUT1)
    private WebElement matInput1;

    public static final String LOGIN = "//android.widget.Button[@text='Login']";

    @AndroidFindBy(xpath = LOGIN)
    private WebElement login;

    public static final String FORGOT_MPIN = "//android.view.View[contains(@content-desc,'Forgot MPIN?')]";

    @AndroidFindBy(xpath = FORGOT_MPIN)
    private WebElement forgotMpin;

    public static final String NEW_USER = "//android.widget.TextView[@text='New user?']";

    @AndroidFindBy(xpath = NEW_USER)
    private WebElement newUser;

    public static final String CREATE_AN_ACCOUNT = "//android.view.View[contains(@content-desc,'Create an account')]";

    @AndroidFindBy(xpath = CREATE_AN_ACCOUNT)
    private WebElement createAnAccount;

    public static final String COM_AIRPAY_MAHAEPAY_AEPS_ID_LL_FOOTER = "//android.widget.LinearLayout[@resource-id='com.airpay.mahaepay_aeps:id/ll_footer']";

    @AndroidFindBy(xpath = COM_AIRPAY_MAHAEPAY_AEPS_ID_LL_FOOTER)
    private WebElement comAirpayMahaepayAepsIdLlFooter;

    public static final String COM_AIRPAY_MAHAEPAY_AEPS_ID_LL_VERSION = "//androidx.appcompat.widget.LinearLayoutCompat[@resource-id='com.airpay.mahaepay_aeps:id/ll_version']";

    @AndroidFindBy(xpath = COM_AIRPAY_MAHAEPAY_AEPS_ID_LL_VERSION)
    private WebElement comAirpayMahaepayAepsIdLlVersion;

    public static final String COM_AIRPAY_MAHAEPAY_AEPS_ID_BOTTOMVERSION = "//androidx.appcompat.widget.LinearLayoutCompat[@resource-id='com.airpay.mahaepay_aeps:id/bottomVersion']";

    @AndroidFindBy(xpath = COM_AIRPAY_MAHAEPAY_AEPS_ID_BOTTOMVERSION)
    private WebElement comAirpayMahaepayAepsIdBottomversion;

    public static final String POWERED_BY_UMANG = "//android.widget.TextView[@text='Powered by UMANG']";

    @AndroidFindBy(xpath = POWERED_BY_UMANG)
    private WebElement poweredByUmang;

    public static final String COM_AIRPAY_MAHAEPAY_AEPS_ID_BOTTOMVERSION2 = "//androidx.appcompat.widget.LinearLayoutCompat[@resource-id='com.airpay.mahaepay_aeps:id/bottomVersion2']";

    @AndroidFindBy(xpath = COM_AIRPAY_MAHAEPAY_AEPS_ID_BOTTOMVERSION2)
    private WebElement comAirpayMahaepayAepsIdBottomversion2;

    public static final String VERSION113 = "//android.widget.TextView[@text='Version : 1.1.3']";

    @AndroidFindBy(xpath = VERSION113)
    private WebElement version113;

    public HomeOtherServicesGovtServicesScreen(TestContext context) {
        super(context);
    }
}
