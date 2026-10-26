package com.airpay.capital.flows;

import com.airpay.capital.data.CapitalTestData;
import com.airpay.capital.screens.auth.BiometricScreen;
import com.airpay.capital.screens.auth.MobileLoginScreen;
import com.airpay.capital.screens.auth.OtpScreen;
import com.airpay.capital.screens.auth.WelcomeScreen;
import com.airpay.capital.screens.home.HomeScreen;
import com.airpay.mobile.utilities.android.PermissionDialogHandler;
import com.airpay.mobile.utilities.context.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

/** Login flow for functional E2E — random mobile + static OTP 1234. */
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
        String mobile = CapitalTestData.mobile(context.config(), account);
        loginWithMobile(mobile);
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
        sleep(1000);
        if (!mobileLogin.waitUntilDisplayed(Duration.ofSeconds(15))) {
            throw new IllegalStateException("Mobile login screen did not appear after Welcome.");
        }
        submitMobileAndOtp(mobile);
        biometric.dismissIfShown();
        if (!home.looksLoggedIn()) {
            throw new IllegalStateException("Login failed — Home screen not visible after OTP for " + mobile);
        }
    }

    private void submitMobileAndOtp(String mobile) {
        mobileLogin.enterMobile(mobile);
        mobileLogin.acceptConsent();
        sleep(400);
        mobileLogin.tapSendOtp();
        sleep(1500);
        allowDialogs(Duration.ofSeconds(6));
        if (!otpScreen.isDisplayed() && !home.looksLoggedIn()) {
            throw new IllegalStateException("OTP screen not shown after Send OTP.");
        }
        otpScreen.enterOtp(CapitalTestData.otp(context.config()));
        sleep(2000);
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
