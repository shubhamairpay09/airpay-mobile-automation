package com.airpay.vyaapaar.screens.auth;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** OTP. Module auth. */
public class AuthOtpScreen extends BaseScreen {

    public static final String PAGE_ID = "auth-otp";

    public static final String OTP_VERIFICATION_ILLUSTRATION = "//android.widget.ImageView[contains(@content-desc,'OTP Verification Illustration')]";

    @AndroidFindBy(xpath = OTP_VERIFICATION_ILLUSTRATION)
    private WebElement otpVerificationIllustration;

    public static final String OTP_VERIFICATION = "//android.widget.TextView[contains(@content-desc,'OTP Verification')]";

    @AndroidFindBy(xpath = OTP_VERIFICATION)
    private WebElement otpVerification;

    public static final String PLEASE_ENTER_THE_OTP_SENT_TO_YOUR_MOBILE = "//android.widget.TextView[contains(@content-desc,'Please enter the OTP sent to your mobile number.')]";

    @AndroidFindBy(xpath = PLEASE_ENTER_THE_OTP_SENT_TO_YOUR_MOBILE)
    private WebElement pleaseEnterTheOtpSentToYourMobile;

    public static final String DIDN_TGET_OTP_RESEND_OTP = "//android.widget.TextView[@text=concat('Didn',\"'\",'t get OTP? Resend OTP')]";

    @AndroidFindBy(xpath = DIDN_TGET_OTP_RESEND_OTP)
    private WebElement didnTGetOtpResendOtp;

    public static final String VERIFY_OTP = "//android.widget.TextView[@text='Verify OTP']";

    @AndroidFindBy(xpath = VERIFY_OTP)
    private WebElement verifyOtp;

    public static final String BACK_ICON = "//android.view.View[contains(@content-desc,'Back Icon')]";

    @AndroidFindBy(xpath = BACK_ICON)
    private WebElement backIcon;

    public static final String CREDIT_BALANCE = "//android.widget.TextView[contains(@content-desc,'Credit Balance')]";

    @AndroidFindBy(xpath = CREDIT_BALANCE)
    private WebElement creditBalance;

    public static final String VISIBILITY_OFF_ICON = "//android.view.View[contains(@content-desc,'Visibility Off Icon')]";

    @AndroidFindBy(xpath = VISIBILITY_OFF_ICON)
    private WebElement visibilityOffIcon;

    public static final String ELEMENT = "//android.widget.TextView[contains(@content-desc,'#####')]";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String ADD_CREDITS = "//android.widget.TextView[contains(@content-desc,'Add Credits')]";

    @AndroidFindBy(xpath = ADD_CREDITS)
    private WebElement addCredits;

    public static final String FEATURED_SERVICES = "//android.widget.TextView[contains(@content-desc,'Featured Services')]";

    @AndroidFindBy(xpath = FEATURED_SERVICES)
    private WebElement featuredServices;

    public static final String PAN_APPLICATION_ICON = "//android.view.View[contains(@content-desc,'PAN Application icon')]";

    @AndroidFindBy(xpath = PAN_APPLICATION_ICON)
    private WebElement panApplicationIcon;

    public static final String PAN_APPLICATION = "//android.widget.TextView[contains(@content-desc,'PAN Application')]";

    @AndroidFindBy(xpath = PAN_APPLICATION)
    private WebElement panApplication;

    public static final String POST_OFFICE_ICON = "//android.view.View[contains(@content-desc,'Post Office icon')]";

    @AndroidFindBy(xpath = POST_OFFICE_ICON)
    private WebElement postOfficeIcon;

    public static final String POST_OFFICE = "//android.widget.TextView[contains(@content-desc,'Post Office')]";

    @AndroidFindBy(xpath = POST_OFFICE)
    private WebElement postOffice;

    public static final String AEPS_ICON = "//android.view.View[contains(@content-desc,'AEPS icon')]";

    @AndroidFindBy(xpath = AEPS_ICON)
    private WebElement aepsIcon;

    public static final String AEPS = "//android.widget.TextView[contains(@content-desc,'AEPS')]";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String AADHAAR_PAY_ICON = "//android.view.View[contains(@content-desc,'Aadhaar Pay icon')]";

    @AndroidFindBy(xpath = AADHAAR_PAY_ICON)
    private WebElement aadhaarPayIcon;

    public static final String AADHAAR_PAY = "//android.widget.TextView[contains(@content-desc,'Aadhaar Pay')]";

    @AndroidFindBy(xpath = AADHAAR_PAY)
    private WebElement aadhaarPay;

    public static final String CMS_ICON = "//android.view.View[contains(@content-desc,'CMS icon')]";

    @AndroidFindBy(xpath = CMS_ICON)
    private WebElement cmsIcon;

    public static final String CMS = "//android.widget.TextView[contains(@content-desc,'CMS')]";

    @AndroidFindBy(xpath = CMS)
    private WebElement cms;

    public static final String KHATA_ICON = "//android.view.View[contains(@content-desc,'Khata icon')]";

    @AndroidFindBy(xpath = KHATA_ICON)
    private WebElement khataIcon;

    public static final String KHATA = "//android.widget.TextView[contains(@content-desc,'Khata')]";

    @AndroidFindBy(xpath = KHATA)
    private WebElement khata;

    public static final String DASHBOARD_ICON = "//android.view.View[contains(@content-desc,'Dashboard icon')]";

    @AndroidFindBy(xpath = DASHBOARD_ICON)
    private WebElement dashboardIcon;

    public static final String DASHBOARD = "//android.widget.TextView[contains(@content-desc,'Dashboard')]";

    @AndroidFindBy(xpath = DASHBOARD)
    private WebElement dashboard;

    public static final String WITHDRAWAL_LIMIT_ICON = "//android.view.View[contains(@content-desc,'Withdrawal Limit icon')]";

    @AndroidFindBy(xpath = WITHDRAWAL_LIMIT_ICON)
    private WebElement withdrawalLimitIcon;

    public static final String WITHDRAWAL_LIMIT = "//android.widget.TextView[contains(@content-desc,'Withdrawal Limit')]";

    @AndroidFindBy(xpath = WITHDRAWAL_LIMIT)
    private WebElement withdrawalLimit;

    public static final String PREPAID_CARDS_ICON = "//android.view.View[contains(@content-desc,'Prepaid Cards icon')]";

    @AndroidFindBy(xpath = PREPAID_CARDS_ICON)
    private WebElement prepaidCardsIcon;

    public static final String PREPAID_CARDS = "//android.widget.TextView[contains(@content-desc,'Prepaid Cards')]";

    @AndroidFindBy(xpath = PREPAID_CARDS)
    private WebElement prepaidCards;

    public static final String MONEY_TRANSFER = "//android.widget.TextView[contains(@content-desc,'Money Transfer')]";

    @AndroidFindBy(xpath = MONEY_TRANSFER)
    private WebElement moneyTransfer;

    public static final String NOTIFICATIONS = "//android.view.View[contains(@content-desc,'Notifications')]";

    @AndroidFindBy(xpath = NOTIFICATIONS)
    private WebElement notifications;

    public static final String HOME = "//android.widget.TextView[contains(@content-desc,'Home')]";

    @AndroidFindBy(xpath = HOME)
    private WebElement home;

    public static final String SERVICES = "//android.widget.TextView[contains(@content-desc,'Services')]";

    @AndroidFindBy(xpath = SERVICES)
    private WebElement services;

    public static final String HISTORY = "//android.widget.TextView[contains(@content-desc,'History')]";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String PROFILE = "//android.widget.TextView[contains(@content-desc,'Profile')]";

    @AndroidFindBy(xpath = PROFILE)
    private WebElement profile;

    public AuthOtpScreen(TestContext context) {
        super(context);
    }
}
