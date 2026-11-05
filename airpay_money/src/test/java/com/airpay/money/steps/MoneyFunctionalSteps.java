package com.airpay.money.steps;

import com.airpay.money.data.MoneyExcel;
import com.airpay.money.data.MoneyTestData;
import com.airpay.money.flows.MoneyLoginFlow;
import com.airpay.money.screens.auth.BiometricScreen;
import com.airpay.money.screens.auth.MobileLoginScreen;
import com.airpay.money.screens.auth.OtpScreen;
import com.airpay.money.screens.auth.PinUnlockScreen;
import com.airpay.money.screens.auth.WelcomeScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.time.Duration;

public class MoneyFunctionalSteps {

    private final TestContext context;
    private final MoneyLoginFlow loginFlow;
    private final HomeScreen home;
    private final WelcomeScreen welcome;
    private final MobileLoginScreen mobileLogin;
    private final OtpScreen otpScreen;

    private String lastMobile;

    public MoneyFunctionalSteps(TestContext context) {
        this.context = context;
        this.loginFlow = new MoneyLoginFlow(context);
        this.home = new HomeScreen(context);
        this.welcome = new WelcomeScreen(context);
        this.mobileLogin = new MobileLoginScreen(context);
        this.otpScreen = new OtpScreen(context);
    }

    @Given("the airpay money app is ready")
    public void appReady() {
        Assertions.assertThat(context.config().require("money.app.package")).isNotBlank();
    }

    @Given("I am logged in to airpay money")
    public void loggedIn() {
        loginFlow.loginWithConfiguredOrRandomMobile();
        Assertions.assertThat(home.looksLoggedIn()).isTrue();
    }

    @When("I login to airpay money with a random mobile number and static OTP")
    public void loginRandomMobile() {
        lastMobile = MoneyTestData.randomMobile();
        loginFlow.loginWithMobile(lastMobile);
    }

    @When("the airpay money session is signed in or login is completed from the welcome screen")
    public void sessionOrLogin() {
        home.dismissHomeCoachIfShown();
        unlockPinAndSkipBiometric();
        leaveNestedProductScreens();
        unlockPinAndSkipBiometric();
        if (alreadyOnSignedInHome()) {
            return;
        }
        if (home.sourceContains("Enter your Airpay Money PIN", "Unlock faster with Biometrics")) {
            unlockPinAndSkipBiometric();
            leaveNestedProductScreens();
            if (alreadyOnSignedInHome()) {
                return;
            }
        }
        if (!home.sourceContains("Sign Up or Login", "Login with Mobile", "Login With Mobile", "Get Started")) {
            // Mid-suite reuse: not on welcome — treat as signed in if tabs or Hello are present.
            if (alreadyOnSignedInHome() || home.sourceContains("Products", "FinSight", "Calculators")) {
                return;
            }
        }
        // Already on the mobile login form — do not tap Login before number + consent.
        if (!mobileLogin.waitUntilDisplayed(Duration.ofSeconds(3))) {
            welcome.tapLoginWithMobile();
            Assertions.assertThat(mobileLogin.waitUntilDisplayed(Duration.ofSeconds(15)))
                    .as("Login with Mobile must open the mobile number screen. Visible: " + home.visibleLabels())
                    .isTrue();
        }
        lastMobile = MoneyTestData.mobile(context.config());
        Assertions.assertThat(lastMobile)
                .as("money.test.mobile in config/money.properties must be set")
                .isNotBlank();
        mobileLogin.enterMobile(lastMobile);
        mobileLogin.acceptConsentIfShown();
        mobileLogin.tapSendOtp();
        Assertions.assertThat(otpScreen.waitUntilDisplayed(Duration.ofSeconds(45))
                        || home.sourceContains("Enter your Airpay Money PIN", "Hello"))
                .as("OTP screen or signed-in/PIN must open after Login with Mobile Number. Visible: "
                        + home.visibleLabels())
                .isTrue();
        if (otpScreen.isDisplayed()) {
            String otp = MoneyTestData.otp(context.config());
            Assertions.assertThat(otp)
                    .as("money.test.otp in config/money.properties must be set when the welcome screen is shown")
                    .isNotBlank();
            otpScreen.enterOtp(otp);
            otpScreen.waitUntilGone(Duration.ofSeconds(60));
        }
        new BiometricScreen(context).dismissIfShown();
        unlockPinAndSkipBiometric();
    }

    @Then("the signed-in home shows Hello and Your Financial Health Score")
    public void signedInHomeMarkers() {
        unlockPinAndSkipBiometric();
        leavePrivacyOrTermsWebView();
        unlockPinAndSkipBiometric();
        String source = home.pageSource();
        Assertions.assertThat(source)
                .as("Signed-in home must show the greeting Hello. Visible: " + home.visibleLabels())
                .contains("Hello");
        Assertions.assertThat(source)
                .as("Signed-in home must show Your Financial Health Score. Visible: " + home.visibleLabels())
                .contains("Your Financial Health Score");
    }

    private void leavePrivacyOrTermsWebView() {
        for (int i = 0; i < 4; i++) {
            String src = home.pageSource();
            boolean web = src.contains("Privacy Policy web page")
                    || (src.contains("Privacy Policy") && !src.contains("Login with Mobile") && !src.contains("Hello"));
            boolean terms = src.contains("Terms and Conditions") && !src.contains("Hello")
                    && !src.contains("Login with Mobile");
            if (!web && !terms) {
                return;
            }
            home.pressSystemBack();
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    private void unlockPinAndSkipBiometric() {
        BiometricScreen biometric = new BiometricScreen(context);
        biometric.dismissIfShown();
        if (!home.sourceContains("Enter your Airpay Money PIN")) {
            biometric.dismissIfShown();
            return;
        }
        new PinUnlockScreen(context).enterPin(MoneyExcel.pin("880272"));
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        biometric.dismissIfShown();
    }

    private boolean alreadyOnSignedInHome() {
        return home.sourceContains("Hello")
                && (home.sourceContains("FinSight") || home.sourceContains("Financial Health"));
    }

    /** Prior scenario may end on FD / MF / Airpay — Back out before login or home checks. */
    private void leaveNestedProductScreens() {
        for (int i = 0; i < 8; i++) {
            if (alreadyOnSignedInHome()) {
                return;
            }
            if (home.sourceContains("Products") && home.sourceContains("FinSight") && home.sourceContains("Calculators")
                    && !home.sourceContains("Select goal tenure", "Book FD at", "Make Payment")) {
                return;
            }
            if (home.sourceContains("Go to Dashboard")) {
                home.tapLabel("Go to Dashboard");
                sleepQuiet(600);
                continue;
            }
            if (!home.tapBack() && !home.tapLabel("Back")) {
                home.pressSystemBack();
            }
            sleepQuiet(450);
            unlockPinAndSkipBiometric();
        }
    }

    private static void sleepQuiet(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @When("I tap login with mobile on the airpay money welcome screen")
    public void tapWelcomeLogin() {
        welcome.tapLoginWithMobile();
        Assertions.assertThat(mobileLogin.waitUntilDisplayed(Duration.ofSeconds(15)))
                .as("Login with Mobile must open the mobile number screen. Visible: " + home.visibleLabels())
                .isTrue();
    }

    @When("I enter the airpay money mobile number and accept consent if shown")
    public void enterMobile() {
        lastMobile = MoneyTestData.mobile(context.config());
        mobileLogin.enterMobile(lastMobile);
        mobileLogin.acceptConsentIfShown();
    }

    @When("I tap Send OTP on airpay money")
    public void tapSendOtp() {
        mobileLogin.tapSendOtp();
    }

    @When("I enter airpay money OTP {string} and verify")
    public void enterOtp(String otp) {
        otpScreen.enterOtp(otp);
        otpScreen.waitUntilGone(Duration.ofSeconds(30));
    }

    @Then("I should see the airpay money home screen")
    public void assertHome() {
        Assertions.assertThat(home.looksLoggedIn())
                .as("Expected airpay money home/dashboard after login")
                .isTrue();
    }
}
