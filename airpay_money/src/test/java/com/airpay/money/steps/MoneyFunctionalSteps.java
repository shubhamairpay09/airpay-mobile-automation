package com.airpay.money.steps;

import com.airpay.money.data.MoneyTestData;
import com.airpay.money.flows.MoneyLoginFlow;
import com.airpay.money.screens.auth.MobileLoginScreen;
import com.airpay.money.screens.auth.OtpScreen;
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

    @When("I tap login with mobile on the airpay money welcome screen")
    public void tapWelcomeLogin() {
        welcome.tapLoginWithMobile();
        Assertions.assertThat(mobileLogin.waitUntilDisplayed(Duration.ofSeconds(15))).isTrue();
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
