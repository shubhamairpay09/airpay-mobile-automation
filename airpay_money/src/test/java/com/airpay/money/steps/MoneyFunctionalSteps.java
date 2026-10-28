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
        if (home.sourceContains("Hello") && home.sourceContains("Financial Health")) {
            return;
        }
        welcome.tapLoginWithMobile();
        Assertions.assertThat(mobileLogin.waitUntilDisplayed(Duration.ofSeconds(15)))
                .as("Login with Mobile must open the mobile number screen. Visible: " + home.visibleLabels())
                .isTrue();
        lastMobile = MoneyTestData.mobile(context.config());
        Assertions.assertThat(lastMobile)
                .as("money.test.mobile in config/money.properties must be set")
                .isNotBlank();
        mobileLogin.enterMobile(lastMobile);
        mobileLogin.acceptConsentIfShown();
        mobileLogin.tapSendOtp();
        String otp = MoneyTestData.otp(context.config());
        Assertions.assertThat(otp)
                .as("money.test.otp in config/money.properties must be set when the welcome screen is shown")
                .isNotBlank();
        otpScreen.enterOtp(otp);
        otpScreen.waitUntilGone(Duration.ofSeconds(30));
        new BiometricScreen(context).dismissIfShown();
        unlockPinAndSkipBiometric();
    }

    @Then("the signed-in home shows Hello and Your Financial Health Score")
    public void signedInHomeMarkers() {
        unlockPinAndSkipBiometric();
        String source = home.pageSource();
        Assertions.assertThat(source)
                .as("Signed-in home must show the greeting Hello. Visible: " + home.visibleLabels())
                .contains("Hello");
        Assertions.assertThat(source)
                .as("Signed-in home must show Your Financial Health Score. Visible: " + home.visibleLabels())
                .contains("Your Financial Health Score");
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
