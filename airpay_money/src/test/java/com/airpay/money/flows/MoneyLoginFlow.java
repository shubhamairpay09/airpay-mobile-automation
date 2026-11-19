package com.airpay.money.flows;

import com.airpay.money.data.MoneyTestData;
import com.airpay.money.screens.auth.ForgotPinScreen;
import com.airpay.money.screens.auth.GooglePhonePickerScreen;
import com.airpay.money.screens.auth.BiometricScreen;
import com.airpay.money.screens.auth.GuestLandingScreen;
import com.airpay.money.screens.auth.MobileLoginScreen;
import com.airpay.money.screens.auth.OtpScreen;
import com.airpay.money.screens.auth.PinUnlockScreen;
import com.airpay.money.screens.auth.SetNewPinScreen;
import com.airpay.money.screens.auth.WelcomeScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.mobile.utilities.android.PermissionDialogHandler;
import com.airpay.mobile.utilities.context.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

public class MoneyLoginFlow {

    private static final Logger LOG = LogManager.getLogger(MoneyLoginFlow.class);

    private final TestContext context;
    private final GuestLandingScreen guest;
    private final WelcomeScreen welcome;
    private final MobileLoginScreen mobileLogin;
    private final OtpScreen otpScreen;
    private final HomeScreen home;
    private final BiometricScreen biometric;
    private final PinUnlockScreen pinUnlock;

    public MoneyLoginFlow(TestContext context) {
        this.context = context;
        this.guest = new GuestLandingScreen(context);
        this.welcome = new WelcomeScreen(context);
        this.mobileLogin = new MobileLoginScreen(context);
        this.otpScreen = new OtpScreen(context);
        this.home = new HomeScreen(context);
        this.biometric = new BiometricScreen(context);
        this.pinUnlock = new PinUnlockScreen(context);
    }

    public void loginWithConfiguredOrRandomMobile() {
        loginWithMobile(MoneyTestData.mobile(context.config()));
    }

    public void loginWithMobile(String mobile) {
        allowDialogs(Duration.ofSeconds(8));
        new GooglePhonePickerScreen(context).selectNumberIfShown(mobile);
        if (home.looksLoggedIn()) {
            LOG.info("Already logged in — skipping login");
            return;
        }
        if (enterPinIfShown()) {
            biometric.dismissIfShown();
            if (home.looksLoggedIn()) {
                return;
            }
        }
        if (mobileLogin.waitUntilDisplayed(Duration.ofSeconds(5))) {
            submitMobileAndOtp(mobile);
            biometric.dismissIfShown();
            return;
        }
        if (guest.isDisplayed()) {
            LOG.info("Guest landing — tap header Login beside hamburger");
            guest.tapHeaderLogin();
            if (!mobileLogin.waitUntilDisplayed(Duration.ofSeconds(15))) {
                throw new IllegalStateException("Mobile login screen did not appear after header Login.");
            }
            submitMobileAndOtp(mobile);
            biometric.dismissIfShown();
            return;
        }
        if (!welcome.waitForLoginEntry(Duration.ofSeconds(20))) {
            allowDialogs(Duration.ofSeconds(5));
        }
        welcome.tapLoginWithMobile();
        if (!mobileLogin.waitUntilDisplayed(Duration.ofSeconds(15))) {
            throw new IllegalStateException("Mobile login screen did not appear after welcome.");
        }
        submitMobileAndOtp(mobile);
        biometric.dismissIfShown();
        if (!home.looksLoggedIn()) {
            throw new IllegalStateException("Login failed — Money home not visible after OTP for " + mobile);
        }
    }

    private void submitMobileAndOtp(String mobile) {
        mobileLogin.enterMobile(mobile);
        mobileLogin.acceptConsentIfShown();
        mobileLogin.tapSendOtp();
        home.waitUntil(() -> home.looksLoggedIn()
                || home.sourceContains("Mobile Number Verification", "Resend OTP", "Verify OTP"),
                Duration.ofSeconds(8));
        allowDialogs(Duration.ofSeconds(6));
        if (!otpScreen.isDisplayed() && !home.looksLoggedIn()) {
            throw new IllegalStateException("OTP screen not shown after Send OTP.");
        }
        String otp = resolveOtp(mobile);
        if (!otpScreen.completeFromExcelOrUser(otp) && !home.looksLoggedIn()) {
            throw new IllegalStateException(
                    "OTP was not entered. Put OTP in the Login sheet, or type it on the phone.");
        }
        enterPinIfShown();
    }

    private boolean enterPinIfShown() {
        if (!pinUnlock.waitUntilDisplayed(Duration.ofSeconds(4))) {
            return false;
        }
        String pin = MoneyTestData.pin(context.config());
        if (pin.isBlank()) {
            throw new IllegalStateException("PIN screen shown but money.test.pin is blank.");
        }
        if (sourceLooksLocked()) {
            resetPinViaForgot(pin);
            return true;
        }
        LOG.info("Entering session PIN from the Login sheet");
        pinUnlock.enterPin(pin);
        home.waitUntil(() -> home.looksLoggedIn()
                || sourceContains("Incorrect PIN")
                || sourceLooksLocked()
                || !pinUnlock.isDisplayed(), Duration.ofSeconds(4));
        biometric.dismissIfShown();
        if (sourceLooksLocked() || sourceContains("Incorrect PIN")) {
            resetPinViaForgot(pin);
        }
        biometric.dismissIfShown();
        allowDialogs(Duration.ofSeconds(6));
        return true;
    }

    private boolean sourceLooksLocked() {
        return sourceContains("wrong PIN 3 times", "must now set a new PIN", "Forgot Your PIN");
    }

    private boolean sourceContains(String... fragments) {
        String src;
        try {
            src = context.driver().getPageSource();
        } catch (Exception e) {
            return false;
        }
        for (String f : fragments) {
            if (src.contains(f)) {
                return true;
            }
        }
        return false;
    }

    private void resetPinViaForgot(String newPin) {
        ForgotPinScreen forgot = new ForgotPinScreen(context);
        SetNewPinScreen setNew = new SetNewPinScreen(context);
        if (pinUnlock.isDisplayed() && !forgot.isDisplayed()) {
            pinUnlock.tapForgotPin();
        }
        if (forgot.isDisplayed() && !forgot.isOtpDisplayed()) {
            forgot.tapSendOtp();
        }
        String mobile = MoneyTestData.mobile(context.config());
        String otp = resolveOtp(mobile);
        if (!otp.isBlank()) {
            forgot.enterOtp(otp);
        } else {
            LOG.info("Forgot-PIN OTP — waiting for the user to enter it on the phone");
        }
        long waitMs = context.config().getLong("timeout.otp.wait.ms", 120_000);
        if (!setNew.waitUntil(setNew::isDisplayed, Duration.ofMillis(waitMs))) {
            throw new IllegalStateException("Set New PIN screen not shown after OTP.");
        }
        LOG.info("Setting new session PIN via Forgot PIN");
        setNew.enterPin(newPin);
        home.waitUntil(() -> sourceContains("Confirm your PIN", "Success!"), Duration.ofSeconds(4));
        if (!sourceContains("Success!")) {
            setNew.enterPin(newPin);
        }
        if (setNew.isSuccessDisplayed()) {
            setNew.tapContinue();
        }
        if (pinUnlock.waitUntilDisplayed(Duration.ofSeconds(8))) {
            pinUnlock.enterPin(newPin);
            home.waitUntil(() -> home.looksLoggedIn() || !pinUnlock.isDisplayed(), Duration.ofSeconds(4));
        }
        allowDialogs(Duration.ofSeconds(6));
    }

    private String resolveOtp(String mobile) {
        String fromExcel = MoneyTestData.otp(context.config());
        if (!fromExcel.isBlank()) {
            LOG.info("Using OTP from the Login Excel sheet or money.test.otp");
            return fromExcel;
        }
        LOG.info("OTP is blank in Excel — waiting for the user to enter it on the phone for {}", mask(mobile));
        return "";
    }

    private static String mask(String mobile) {
        if (mobile == null || mobile.length() < 4) {
            return "****";
        }
        return "******" + mobile.substring(mobile.length() - 4);
    }

    private void allowDialogs(Duration timeout) {
        PermissionDialogHandler.allowAll(context.driver(), timeout);
    }

}
