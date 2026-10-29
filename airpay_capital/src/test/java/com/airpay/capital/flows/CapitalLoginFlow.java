package com.airpay.capital.flows;

import com.airpay.capital.data.CapitalExcel;
import com.airpay.capital.data.CapitalTestData;
import com.airpay.capital.screens.auth.BiometricScreen;
import com.airpay.capital.screens.auth.MobileLoginScreen;
import com.airpay.capital.screens.auth.OtpScreen;
import com.airpay.capital.screens.auth.WelcomeScreen;
import com.airpay.capital.screens.home.HomeScreen;
import com.airpay.mobile.utilities.android.NotificationOtpReader;
import com.airpay.mobile.utilities.android.PermissionDialogHandler;
import com.airpay.mobile.utilities.context.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

/**
 * Login flow for functional E2E.
 * Mobile from Capital Excel / capital.test.mobile (default 8802729089).
 * OTP is read from device notifications unless capital.test.otp is set.
 */
public class CapitalLoginFlow {

    private static final Logger LOG = LogManager.getLogger(CapitalLoginFlow.class);

    private final TestContext context;
    private final WelcomeScreen welcome;
    private final MobileLoginScreen mobileLogin;
    private final OtpScreen otpScreen;
    private final HomeScreen home;
    private final BiometricScreen biometric;

    public CapitalLoginFlow(TestContext context) {
        this.context = context;
        this.welcome = new WelcomeScreen(context);
        this.mobileLogin = new MobileLoginScreen(context);
        this.otpScreen = new OtpScreen(context);
        this.home = new HomeScreen(context);
        this.biometric = new BiometricScreen(context);
    }

    public void loginWith(CapitalTestData.Account account) {
        String configured = CapitalTestData.mobile(context.config(), account);
        String fromExcel = CapitalExcel.mobile("");
        loginWithMobile(fromExcel.isBlank() ? configured : fromExcel);
    }

    public void loginWithExcelCredentials() {
        loginWithMobile(resolveMobile());
    }

    public void loginWithMobile(String mobile) {
        allowDialogs(Duration.ofSeconds(8));
        if (home.looksLoggedIn()) {
            LOG.info("Already logged in — skipping login");
            return;
        }
        if (mobileLogin.waitUntilDisplayed(Duration.ofSeconds(5))) {
            submitMobileAndOtp(mobile);
            biometric.dismissIfShown();
            return;
        }
        if (!welcome.waitForLoginButton(Duration.ofSeconds(20))) {
            allowDialogs(Duration.ofSeconds(5));
        }
        welcome.tapLoginWithMobile();
        if (!mobileLogin.waitUntilDisplayed(Duration.ofSeconds(15))) {
            throw new IllegalStateException("Mobile login screen did not appear after Welcome.");
        }
        submitMobileAndOtp(mobile);
        biometric.dismissIfShown();
        otpScreen.waitUntilGone(Duration.ofSeconds(20));
        if (!home.looksLoggedIn()) {
            throw new IllegalStateException("Login failed — Home screen not visible after OTP for " + mobile);
        }
    }

    public void openWelcomeAndMobileLogin() {
        allowDialogs(Duration.ofSeconds(8));
        if (mobileLogin.waitUntilDisplayed(Duration.ofSeconds(3))) {
            return;
        }
        if (!welcome.waitForLoginButton(Duration.ofSeconds(20))) {
            allowDialogs(Duration.ofSeconds(5));
        }
        welcome.tapLoginWithMobile();
        if (!mobileLogin.waitUntilDisplayed(Duration.ofSeconds(15))) {
            throw new IllegalStateException("Mobile login screen did not appear after Welcome.");
        }
    }

    public void enterMobileAcceptConsentAndSendOtp() {
        String mobile = resolveMobile();
        mobileLogin.enterMobile(mobile);
        mobileLogin.acceptConsent();
        mobileLogin.tapSendOtp();
        allowDialogs(Duration.ofSeconds(6));
        if (!otpScreen.waitUntilDisplayed(Duration.ofSeconds(15)) && !home.looksLoggedIn()) {
            throw new IllegalStateException("OTP screen not shown after Send OTP.");
        }
    }

    public String resolveMobile() {
        String fromExcel = CapitalExcel.mobile("");
        if (!fromExcel.isBlank()) {
            return fromExcel;
        }
        String configured = context.config().get("capital.test.mobile", "").trim();
        if (!configured.isBlank()) {
            return configured;
        }
        return "8802729089";
    }

    /**
     * Prefers {@code capital.test.otp} only when explicitly set.
     * Otherwise polls SMS / notification shade via {@link NotificationOtpReader}.
     * Excel {@code OTP} is used only as a last-resort fallback (e.g. staging stub).
     */
    public String resolveOtpFromNotifications(String mobile) {
        String configured = context.config().get("capital.test.otp", "").trim();
        if (!configured.isBlank()) {
            LOG.info("Using OTP from capital.test.otp");
            return configured;
        }
        long waitMs = context.config().getLong("timeout.otp.wait.ms", 120_000);
        LOG.info("Waiting up to {}s for OTP in device notifications for {}", waitMs / 1000, mask(mobile));
        String fromDevice = NotificationOtpReader.poll(
                context.driver(), context.config(), mobile, Duration.ofMillis(waitMs),
                otpScreen.expectedLength(), home::looksLoggedIn);
        if (fromDevice != null && !fromDevice.isBlank()) {
            return fromDevice;
        }
        if (home.looksLoggedIn()) {
            LOG.info("Already past OTP screen (app may have auto-consumed SMS)");
            return "";
        }
        String excelFallback = CapitalExcel.otp("");
        if (!excelFallback.isBlank()) {
            LOG.warn("Notification OTP not found — falling back to Excel OTP for {}", mask(mobile));
            return excelFallback;
        }
        throw new IllegalStateException(
                "Could not read OTP from device notifications for " + mask(mobile)
                        + ". Ensure SMS notifications are enabled, or set capital.test.otp.");
    }

    private void submitMobileAndOtp(String mobile) {
        mobileLogin.enterMobile(mobile);
        mobileLogin.acceptConsent();
        mobileLogin.tapSendOtp();
        allowDialogs(Duration.ofSeconds(6));
        if (!otpScreen.waitUntilDisplayed(Duration.ofSeconds(15)) && !home.looksLoggedIn()) {
            throw new IllegalStateException("OTP screen not shown after Send OTP.");
        }
        if (home.looksLoggedIn()) {
            return;
        }
        String otp = resolveOtpFromNotifications(mobile);
        if (otp != null && !otp.isBlank()) {
            otpScreen.enterOtp(otp);
        }
        otpScreen.waitUntilGone(Duration.ofSeconds(20));
    }

    private void allowDialogs(Duration timeout) {
        PermissionDialogHandler.allowAll(context.driver(), timeout);
    }

    private static String mask(String mobile) {
        if (mobile == null || mobile.length() < 4) {
            return "****";
        }
        return "******" + mobile.substring(mobile.length() - 4);
    }
}
