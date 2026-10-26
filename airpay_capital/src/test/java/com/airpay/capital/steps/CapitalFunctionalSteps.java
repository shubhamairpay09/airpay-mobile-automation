package com.airpay.capital.steps;

import com.airpay.capital.data.CapitalTestData;
import com.airpay.capital.data.ScreenMessages;
import com.airpay.capital.flows.CapitalLoginFlow;
import com.airpay.capital.flows.ProductJourneyFlow;
import com.airpay.capital.screens.auth.MobileLoginScreen;
import com.airpay.capital.screens.auth.OtpScreen;
import com.airpay.capital.screens.auth.WelcomeScreen;
import com.airpay.capital.screens.home.HomeScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

public class CapitalFunctionalSteps {

    private final TestContext context;
    private final CapitalLoginFlow loginFlow;
    private final ProductJourneyFlow journeyFlow;
    private final HomeScreen home;
    private final WelcomeScreen welcome;
    private final MobileLoginScreen mobileLogin;
    private final OtpScreen otpScreen;

    private String lastMobile;

    public CapitalFunctionalSteps(TestContext context) {
        this.context = context;
        this.loginFlow = new CapitalLoginFlow(context);
        this.journeyFlow = new ProductJourneyFlow(context);
        this.home = new HomeScreen(context);
        this.welcome = new WelcomeScreen(context);
        this.mobileLogin = new MobileLoginScreen(context);
        this.otpScreen = new OtpScreen(context);
    }

    @Given("the Airpay Capital app is ready")
    public void appReady() {
        Assertions.assertThat(context.config().require("capital.app.package")).isNotBlank();
    }

    @Given("I am logged in to Airpay Capital")
    public void loggedInDefault() {
        loginFlow.loginWith(CapitalTestData.Account.DEFAULT);
        Assertions.assertThat(home.looksLoggedIn()).isTrue();
    }

    @Given("I am logged in to Airpay Capital with a no-offer test account")
    public void loggedInNoOffer() {
        loginFlow.loginWith(CapitalTestData.Account.NO_OFFER);
        Assertions.assertThat(home.looksLoggedIn()).isTrue();
    }

    @Given("I am logged in to Airpay Capital with a low CIBIL test account")
    public void loggedInLowCibil() {
        loginFlow.loginWith(CapitalTestData.Account.LOW_CIBIL);
        Assertions.assertThat(home.looksLoggedIn()).isTrue();
    }

    @Given("I am logged in to Airpay Capital with an offers test account")
    public void loggedInWithOffers() {
        loginFlow.loginWith(CapitalTestData.Account.WITH_OFFERS);
        Assertions.assertThat(home.looksLoggedIn()).isTrue();
    }

    @When("I login with a random mobile number and static OTP")
    public void loginRandomMobile() {
        lastMobile = CapitalTestData.randomMobile();
        loginFlow.loginWithMobile(lastMobile);
    }

    @When("I tap Login With Mobile Number on the welcome screen")
    public void tapWelcomeLogin() {
        welcome.tapLoginWithMobile();
        Assertions.assertThat(mobileLogin.waitUntilDisplayed(java.time.Duration.ofSeconds(15))).isTrue();
    }

    @When("I enter the mobile number and accept consent")
    public void enterMobileAndConsent() {
        lastMobile = CapitalTestData.randomMobile();
        mobileLogin.enterMobile(lastMobile);
        mobileLogin.acceptConsent();
    }

    @When("I tap Send OTP")
    public void tapSendOtp() {
        mobileLogin.tapSendOtp();
    }

    @When("I enter OTP {string} and verify")
    public void enterOtpAndVerify(String otp) {
        Assertions.assertThat(otpScreen.isDisplayed())
                .as("OTP screen should be visible after Send OTP")
                .isTrue();
        otpScreen.enterOtp(otp);
    }

    @When("I complete the {string} application as {string} until eligibility")
    public void completeUntilEligibility(String product, String occupationLabel) {
        ProductJourneyFlow.Occupation occupation = parseOccupation(occupationLabel);
        journeyFlow.openProduct(product);
        journeyFlow.selectOccupation(occupation);
        journeyFlow.completeApplyForms(occupation);
        journeyFlow.tapCheckEligibility();
    }

    @When("I open the first lender offer for {string}")
    public void openFirstOffer(String product) {
        Assertions.assertThat(journeyFlow.currentProduct())
                .as("Product journey should be started before opening offer")
                .isEqualToIgnoringCase(product);
        Assertions.assertThat(journeyFlow.offersAreVisible())
                .as("Offers should be visible before opening lender page")
                .isTrue();
        journeyFlow.openFirstOfferLenderPage();
    }

    @Then("I should see the Capital home screen")
    public void assertHome() {
        Assertions.assertThat(home.looksLoggedIn())
                .as("User should land on Home after login")
                .isTrue();
    }

    @Then("the welcome screen should show Login With Mobile Number")
    public void assertWelcome() {
        Assertions.assertThat(welcome.waitForLoginButton(java.time.Duration.ofSeconds(10))).isTrue();
    }

    @Then("the mobile login screen should show consent and Send OTP")
    public void assertMobileLogin() {
        Assertions.assertThat(mobileLogin.instructionIsVisible()).isTrue();
        Assertions.assertThat(mobileLogin.pageSource()).contains(ScreenMessages.SEND_OTP);
    }

    @Then("I should see loan offers available")
    public void assertOffers() {
        Assertions.assertThat(journeyFlow.offersAreVisible())
                .as("Expected loan offers on screen (message: '%s')", ScreenMessages.OFFERS_AVAILABLE)
                .isTrue();
    }

    @Then("I should see no loan offers available")
    public void assertNoOffers() {
        Assertions.assertThat(journeyFlow.noOffersAreVisible())
                .as("Expected no-offer message: '%s' or '%s'",
                        ScreenMessages.NO_OFFERS_AVAILABLE, ScreenMessages.NO_LOAN_OFFERS)
                .isTrue();
    }

    @Then("I should see a low CIBIL score error message")
    public void assertCibilLow() {
        String fragment = CapitalTestData.cibilLowMessageFragment(context.config());
        Assertions.assertThat(journeyFlow.cibilLowErrorIsVisible())
                .as("Expected CIBIL / low credit score error containing '%s'", fragment)
                .isTrue();
    }

    @Then("I should be on the lender application page")
    public void assertLenderPage() {
        String src = context.driver().getPageSource();
        Assertions.assertThat(
                src.contains("partner") || src.contains("Apply") || src.contains("chrome")
                        || src.contains("WebView") || !src.contains("offers available"))
                .as("Expected navigation to lender page after tapping offer")
                .isTrue();
    }

    private static ProductJourneyFlow.Occupation parseOccupation(String label) {
        if (label == null) {
            return ProductJourneyFlow.Occupation.SALARIED;
        }
        String normalized = label.trim().toLowerCase().replace(' ', '-');
        if (normalized.contains("self")) {
            return ProductJourneyFlow.Occupation.SELF_EMPLOYED;
        }
        return ProductJourneyFlow.Occupation.SALARIED;
    }
}
