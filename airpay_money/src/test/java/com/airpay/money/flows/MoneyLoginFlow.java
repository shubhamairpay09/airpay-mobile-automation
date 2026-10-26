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
import com.airpay.mobile.utilities.android.NotificationOtpReader;
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
            sleep(1000);
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
        sleep(1000);
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
        sleep(400);
        mobileLogin.tapSendOtp();
        sleep(1500);
        allowDialogs(Duration.ofSeconds(6));
        if (!otpScreen.isDisplayed() && !home.looksLoggedIn()) {
            throw new IllegalStateException("OTP screen not shown after Send OTP.");
        }
        otpScreen.enterOtp(resolveOtp(mobile));
        sleep(2000);
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
        LOG.info("Entering session PIN");
        pinUnlock.enterPin(pin);
        sleep(1500);
        if (sourceLooksLocked() || sourceContains("Incorrect PIN")) {
            resetPinViaForgot(pin);
        }
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
        if (!forgot.isOtpDisplayed() && otp == null) {
            throw new IllegalStateException("Forgot-PIN OTP screen not shown.");
        }
        forgot.enterOtp(otp);
        if (!setNew.isDisplayed()) {
            throw new IllegalStateException("Set New PIN screen not shown after OTP.");
        }
        LOG.info("Setting new session PIN via Forgot PIN");
        setNew.enterPin(newPin);
        sleep(600);
        setNew.enterPin(newPin);
        if (setNew.isSuccessDisplayed()) {
            setNew.tapContinue();
        }
        if (pinUnlock.waitUntilDisplayed(Duration.ofSeconds(8))) {
            pinUnlock.enterPin(newPin);
            sleep(1200);
        }
        allowDialogs(Duration.ofSeconds(6));
    }

    private String resolveOtp(String mobile) {
        String configured = context.config().get("money.test.otp", "").trim();
        if (!configured.isBlank()) {
            return configured;
        }
        long waitMs = context.config().getLong("timeout.otp.wait.ms", 120_000);
        String fromDevice = NotificationOtpReader.poll(
                context.driver(), context.config(), mobile, Duration.ofMillis(waitMs),
                6, home::looksLoggedIn);
        if (fromDevice != null && !fromDevice.isBlank()) {
            return fromDevice;
        }
        return MoneyTestData.otp(context.config());
    }

    private void allowDialogs(Duration timeout) {
        PermissionDialogHandler.allowAll(context.driver(), timeout);
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
