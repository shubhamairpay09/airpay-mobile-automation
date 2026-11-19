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
 * OTP comes from capital.test.otp (1234 on staging). Notifications are used only when that value is blank.
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
        biometric.dismissIfShown();
        if (otpScreen.isDisplayed() && !otpScreen.tapEditMobile()) {
            home.tapBack();
        }
        if (home.looksLoggedIn()) {
            LOG.info("Already logged in — skipping login");
            return;
        }
        if (mobileLogin.waitUntilDisplayed(Duration.ofSeconds(5))) {
            submitMobileAndOtp(mobile);
            biometric.dismissIfShown();
            if (!home.looksLoggedIn()) {
                throw new IllegalStateException("Login failed — Home screen not visible after OTP for " + mobile);
            }
            return;
        }
        if (!welcome.waitForLoginButton(Duration.ofSeconds(2))) {
            home.openHome();
            if (home.looksLoggedIn()) {
                LOG.info("Already logged in after returning home — skipping login");
                return;
            }
            allowDialogs(Duration.ofSeconds(3));
        }
        if (!welcome.waitForLoginButton(Duration.ofSeconds(6))) {
            throw new IllegalStateException("Welcome Login button not found.");
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
        // Prior scenario may have left us on OTP — Edit/Back returns to mobile entry.
        if (otpScreen.isDisplayed()) {
            if (!otpScreen.tapEditMobile()) {
                home.tapBack();
            }
            if (mobileLogin.waitUntilDisplayed(Duration.ofSeconds(10))) {
                return;
            }
        }
        if (home.looksLoggedIn()) {
            LOG.info("Already on Home — logging out so login can start from Welcome");
            home.logOut();
        }
        if (!welcome.waitForLoginButton(Duration.ofSeconds(20))) {
            allowDialogs(Duration.ofSeconds(5));
            bringCapitalToForeground();
        }
        if (mobileLogin.waitUntilDisplayed(Duration.ofSeconds(3))) {
            return;
        }
        if (!welcome.waitForLoginButton(Duration.ofSeconds(8))) {
            throw new IllegalStateException("Welcome Login button not found.");
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
     * Excel OTP first. Then {@code capital.test.otp} if set.
     * Otherwise wait for the user to type OTP on the phone (notifications are used if they arrive).
     */
    public String resolveOtpFromNotifications(String mobile) {
        String fromExcel = CapitalExcel.otp("");
        if (!fromExcel.isBlank()) {
            LOG.info("Using OTP from the Capital Excel Login sheet");
            return fromExcel;
        }
        String configured = context.config().get("capital.test.otp", "").trim();
        if (!configured.isBlank()) {
            LOG.info("Using OTP from capital.test.otp");
            return configured;
        }
        long waitMs = context.config().getLong("timeout.otp.wait.ms", 120_000);
        LOG.info("Excel OTP is blank — waiting up to {}s for OTP on the phone for {}",
                waitMs / 1000, mask(mobile));
        int expectedLen = otpScreen.isDisplayed() ? otpScreen.expectedLength() : 0;
        String fromDevice = NotificationOtpReader.poll(
                context.driver(), context.config(), mobile, Duration.ofMillis(waitMs),
                expectedLen, home::looksLoggedIn);
        if (fromDevice != null && !fromDevice.isBlank()) {
            return fromDevice;
        }
        if (home.looksLoggedIn()) {
            LOG.info("Already past OTP screen");
            return "";
        }
        otpScreen.waitUntilGone(Duration.ofMillis(waitMs));
        return "";
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
        biometric.dismissIfShown();
        if (!otpScreen.waitUntilGone(otpScreenTimeout()) && !home.looksLoggedIn()) {
            throw new IllegalStateException(
                    "OTP was not entered. Put OTP in the Login sheet, or type it on the phone.");
        }
    }

    private Duration otpScreenTimeout() {
        return Duration.ofMillis(context.config().getLong("timeout.otp.wait.ms", 120_000));
    }

    private void allowDialogs(Duration timeout) {
        PermissionDialogHandler.allowAll(context.driver(), timeout);
    }

    /** Only when Capital is not already foreground — never bounce a focused session. */
    private void bringCapitalToForeground() {
        String pkg = context.config().get("capital.app.package", "com.example.airpay_capital");
        try {
            String now = context.driver().getCurrentPackage();
            if (pkg.equals(now)) {
                return;
            }
            LOG.info("Capital not in foreground (pkg={}) — activateApp", now);
            context.driver().activateApp(pkg);
        } catch (Exception e) {
            LOG.debug("activateApp skipped: {}", e.toString());
        }
    }

    private static String mask(String mobile) {
        if (mobile == null || mobile.length() < 4) {
            return "****";
        }
        return "******" + mobile.substring(mobile.length() - 4);
    }
}
