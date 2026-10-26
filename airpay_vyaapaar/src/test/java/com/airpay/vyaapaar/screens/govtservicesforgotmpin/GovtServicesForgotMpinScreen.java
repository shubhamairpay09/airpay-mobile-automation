package com.airpay.vyaapaar.screens.govtservicesforgotmpin;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Govt Services / Forgot MPIN? — Forgot MPIN. Module govt-services-forgot-mpin. */
public class GovtServicesForgotMpinScreen extends BaseScreen {

    public static final String PAGE_ID = "govt-services-forgot-mpin";

    public static final String COM_AIRPAY_MAHAEPAY_AEPS_ID_MAIN = "//android.widget.LinearLayout[@resource-id='com.airpay.mahaepay_aeps:id/main']";

    @AndroidFindBy(xpath = COM_AIRPAY_MAHAEPAY_AEPS_ID_MAIN)
    private WebElement comAirpayMahaepayAepsIdMain;

    public static final String COM_AIRPAY_MAHAEPAY_AEPS_ID_WEB_PARENT_LAYOUT = "//android.widget.FrameLayout[@resource-id='com.airpay.mahaepay_aeps:id/web_parent_layout_id']";

    @AndroidFindBy(xpath = COM_AIRPAY_MAHAEPAY_AEPS_ID_WEB_PARENT_LAYOUT)
    private WebElement comAirpayMahaepayAepsIdWebParentLayout;

    public static final String MAT_DIALOG0 = "//android.app.AlertDialog[@resource-id='mat-dialog-0']";

    @AndroidFindBy(xpath = MAT_DIALOG0)
    private WebElement matDialog0;

    public static final String FORGOT_MPIN = "//android.widget.TextView[@text='Forgot MPIN']";

    @AndroidFindBy(xpath = FORGOT_MPIN)
    private WebElement forgotMpin;

    public static final String WE_WILL_SEND_AN_OTP_ON_THIS_NUMBER = "//android.widget.TextView[@text='We will send an OTP on this number for verification.']";

    @AndroidFindBy(xpath = WE_WILL_SEND_AN_OTP_ON_THIS_NUMBER)
    private WebElement weWillSendAnOtpOnThisNumber;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number *']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String MAT_FORM_FIELD_LABEL5 = "//android.view.View[@resource-id='mat-form-field-label-5']";

    @AndroidFindBy(xpath = MAT_FORM_FIELD_LABEL5)
    private WebElement matFormFieldLabel5;

    public static final String ELEMENT = "//android.widget.TextView[@text=' ']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String MAT_INPUT2 = "//android.widget.EditText[@resource-id='mat-input-2']";

    @AndroidFindBy(xpath = MAT_INPUT2)
    private WebElement matInput2;

    public GovtServicesForgotMpinScreen(TestContext context) {
        super(context);
    }
}
