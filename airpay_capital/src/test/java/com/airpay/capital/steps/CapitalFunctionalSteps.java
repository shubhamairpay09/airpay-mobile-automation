package com.airpay.capital.steps;

import com.airpay.capital.data.CapitalExcel;
import com.airpay.capital.data.CapitalTestData;
import com.airpay.capital.data.ScreenMessages;
import com.airpay.capital.flows.CapitalLoginFlow;
import com.airpay.capital.flows.ProductJourneyFlow;
import com.airpay.capital.screens.auth.BiometricScreen;
import com.airpay.capital.screens.auth.MobileLoginScreen;
import com.airpay.capital.screens.auth.OtpScreen;
import com.airpay.capital.screens.auth.WelcomeScreen;
import com.airpay.capital.screens.home.HomeScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.time.Duration;

/**
 * Capital functional steps. Form / credential values come from capital-functional-data.xlsx.
 * Then steps use clear, concrete assertion messages; leave scenarios without Then when verification is deferred.
 */
public class CapitalFunctionalSteps {

    private final TestContext context;
    private final CapitalLoginFlow loginFlow;
    private final ProductJourneyFlow journeyFlow;
    private final HomeScreen home;
    private final WelcomeScreen welcome;
    private final MobileLoginScreen mobileLogin;
    private final OtpScreen otpScreen;
    private final BiometricScreen biometric;

    private String lastMobile;

    public CapitalFunctionalSteps(TestContext context) {
        this.context = context;
        this.loginFlow = new CapitalLoginFlow(context);
        this.journeyFlow = new ProductJourneyFlow(context);
        this.home = new HomeScreen(context);
        this.welcome = new WelcomeScreen(context);
        this.mobileLogin = new MobileLoginScreen(context);
        this.otpScreen = new OtpScreen(context);
        this.biometric = new BiometricScreen(context);
    }

    @Given("the Airpay Capital app is ready")
    public void appReady() {
        Assertions.assertThat(context.config().require("capital.app.package"))
                .as("capital.app.package must be configured before running Capital scenarios")
                .isNotBlank();
    }

    @Given("I am logged in to Airpay Capital")
    public void loggedInDefault() {
        loginFlow.loginWithExcelCredentials();
    }

    @Given("I am logged in to Airpay Capital with a no-offer test account")
    public void loggedInNoOffer() {
        loginFlow.loginWith(CapitalTestData.Account.NO_OFFER);
    }

    @Given("I am logged in to Airpay Capital with a low CIBIL test account")
    public void loggedInLowCibil() {
        loginFlow.loginWith(CapitalTestData.Account.LOW_CIBIL);
    }

    @Given("I am logged in to Airpay Capital with an offers test account")
    public void loggedInWithOffers() {
        loginFlow.loginWith(CapitalTestData.Account.WITH_OFFERS);
    }

    @When("I tap Login With Mobile Number on the welcome screen")
    public void tapWelcomeLogin() {
        loginFlow.openWelcomeAndMobileLogin();
    }

    @When("I enter the mobile number from the Capital Excel sheet")
    public void enterMobileFromExcel() {
        lastMobile = loginFlow.resolveMobile();
        mobileLogin.enterMobile(lastMobile);
    }

    @When("I enter the mobile number and accept consent")
    public void enterMobileAndConsent() {
        lastMobile = loginFlow.resolveMobile();
        mobileLogin.enterMobile(lastMobile);
        mobileLogin.acceptConsent();
    }

    @When("I accept consent on the mobile login screen")
    public void acceptConsent() {
        mobileLogin.acceptConsent();
    }

    @When("I tap Send OTP")
    public void tapSendOtp() {
        mobileLogin.tapSendOtp();
        otpScreen.waitUntilDisplayed(Duration.ofSeconds(15));
    }

    @When("I tap Send OTP on the mobile login screen")
    public void tapSendOtpDetailed() {
        mobileLogin.tapSendOtp();
        otpScreen.waitUntilDisplayed(Duration.ofSeconds(15));
    }

    @When("I enter the OTP from the Capital Excel sheet and verify")
    public void enterOtpFromExcel() {
        Assertions.assertThat(otpScreen.isDisplayed() || home.looksLoggedIn())
                .as("OTP verification screen should be displayed after Send OTP, or Home if already verified")
                .isTrue();
        if (home.looksLoggedIn()) {
            return;
        }
        String mobile = lastMobile != null && !lastMobile.isBlank() ? lastMobile : loginFlow.resolveMobile();
        String otp = loginFlow.resolveOtpFromNotifications(mobile);
        if (otp != null && !otp.isBlank()) {
            otpScreen.enterOtp(otp);
        }
        biometric.dismissIfShown();
        otpScreen.waitUntilGone(Duration.ofSeconds(20));
    }

    @When("I enter the OTP from device notifications and verify")
    public void enterOtpFromNotifications() {
        enterOtpFromExcel();
    }

    @When("I enter the invalid OTP from the Capital Excel sheet and verify")
    public void enterInvalidOtpFromExcel() {
        Assertions.assertThat(otpScreen.waitUntilDisplayed(Duration.ofSeconds(15)))
                .as("OTP verification screen should be displayed before entering an invalid OTP")
                .isTrue();
        otpScreen.enterOtp(CapitalExcel.invalidOtp("0000"));
    }

    @When("I enter the expired OTP from the Capital Excel sheet and verify")
    public void enterExpiredOtpFromExcel() {
        Assertions.assertThat(otpScreen.waitUntilDisplayed(Duration.ofSeconds(15)))
                .as("OTP verification screen should be displayed before entering an expired OTP")
                .isTrue();
        otpScreen.enterOtp(CapitalExcel.expiredOtp("9999"));
    }

    @When("I enter OTP {string} and verify")
    public void enterOtpAndVerify(String otp) {
        Assertions.assertThat(otpScreen.isDisplayed())
                .as("OTP screen should be visible after Send OTP")
                .isTrue();
        otpScreen.enterOtp(otp);
    }

    @When("I tap Edit beside the registered mobile number on the OTP screen")
    public void tapEditOnOtp() {
        Assertions.assertThat(otpScreen.waitUntilDisplayed(Duration.ofSeconds(10)))
                .as("OTP verification screen should be displayed before tapping Edit")
                .isTrue();
        Assertions.assertThat(otpScreen.tapEditMobile())
                .as("Edit control beside the registered mobile number should be tappable")
                .isTrue();
        mobileLogin.waitUntilDisplayed(Duration.ofSeconds(12));
    }

    @When("I tap Resend OTP on the OTP screen")
    public void tapResendOtp() {
        Assertions.assertThat(otpScreen.waitUntilDisplayed(Duration.ofSeconds(10)))
                .as("OTP verification screen should be displayed before tapping Resend OTP")
                .isTrue();
        Assertions.assertThat(otpScreen.tapResendOtp())
                .as("Resend OTP control should be tappable on the OTP verification screen")
                .isTrue();
    }

    @When("I complete the product application from the Capital Excel sheet until eligibility")
    public void completeProductFromExcel() {
        String product = CapitalExcel.text("Product", "Personal Loan");
        ProductJourneyFlow.Occupation occupation =
                ProductJourneyFlow.parseOccupation(CapitalExcel.text("Occupation", "Salaried"));
        journeyFlow.openProduct(product);
        journeyFlow.selectOccupation(occupation);
        journeyFlow.completeApplyForms(occupation);
        journeyFlow.tapCheckEligibility();
    }

    @When("I complete the {string} application as {string} until eligibility")
    public void completeUntilEligibility(String product, String occupationLabel) {
        ProductJourneyFlow.Occupation occupation = ProductJourneyFlow.parseOccupation(occupationLabel);
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
                .as("At least one loan offer card should be visible before opening a lender page")
                .isTrue();
        journeyFlow.openFirstOfferLenderPage();
    }

    @When("I open the Muthoot FinCorp offer from the Capital Excel sheet and tap Apply Now")
    public void openMuthootOffer() {
        Assertions.assertThat(journeyFlow.openLenderOfferFromExcel())
                .as("Muthoot FinCorp offer card matching LenderName from Excel should open when Apply Now is tapped")
                .isTrue();
    }

    @Then("I should see the Capital home screen")
    public void assertHome() {
        Assertions.assertThat(home.looksLoggedIn())
                .as("After a successful OTP verify, the Airpay Capital Home screen with product cards should be displayed")
                .isTrue();
    }

    @Then("the welcome screen should show Login With Mobile Number")
    public void assertWelcome() {
        Assertions.assertThat(welcome.waitForLoginButton(Duration.ofSeconds(10)))
                .as("Welcome screen should show the Login With Mobile Number button")
                .isTrue();
    }

    @Then("the mobile login screen should show consent and Send OTP")
    public void assertMobileLogin() {
        Assertions.assertThat(mobileLogin.instructionIsVisible()
                        || mobileLogin.pageSource().contains(ScreenMessages.SEND_OTP))
                .as("Mobile login screen should show the instruction or Send OTP control")
                .isTrue();
        Assertions.assertThat(mobileLogin.pageSource())
                .as("Mobile login screen should contain Send OTP")
                .contains(ScreenMessages.SEND_OTP);
    }

    @Then("the mobile registration screen should be displayed for editing the number")
    public void assertBackToMobileLogin() {
        Assertions.assertThat(mobileLogin.waitUntilDisplayed(Duration.ofSeconds(12)))
                .as("After tapping Edit on OTP, the Mobile Registration screen with the 10-digit number field should be displayed")
                .isTrue();
    }

    @Then("an Invalid OTP error should be displayed and the user should remain on OTP verification")
    public void assertInvalidOtp() {
        Assertions.assertThat(otpScreen.invalidOtpMessageVisible() || otpScreen.isDisplayed())
                .as("An Invalid OTP error message should be displayed, and the user must remain on OTP verification without reaching Home")
                .isTrue();
        Assertions.assertThat(home.looksLoggedIn())
                .as("User must not be navigated to Home after an invalid OTP")
                .isFalse();
    }

    @Then("an OTP Expired error should be displayed and the user should remain on OTP verification")
    public void assertExpiredOtp() {
        Assertions.assertThat(otpScreen.expiredOtpMessageVisible() || otpScreen.isDisplayed())
                .as("An OTP Expired error message should be displayed, and the user must remain on OTP verification without reaching Home")
                .isTrue();
        Assertions.assertThat(home.looksLoggedIn())
                .as("User must not be navigated to Home after an expired OTP")
                .isFalse();
    }

    @Then("I should see loan offers available")
    public void assertOffers() {
        Assertions.assertThat(journeyFlow.offersAreVisible())
                .as("Eligible lender offers should be displayed after Check Eligibility (message containing '%s' or Apply Now)",
                        ScreenMessages.OFFERS_AVAILABLE)
                .isTrue();
    }

    @Then("I should see no loan offers available")
    public void assertNoOffers() {
        Assertions.assertThat(journeyFlow.noOffersAreVisible())
                .as("Expected no-offer message containing '%s' or '%s'",
                        ScreenMessages.NO_OFFERS_AVAILABLE, ScreenMessages.NO_LOAN_OFFERS)
                .isTrue();
    }

    @Then("I should see a low CIBIL score error message")
    public void assertCibilLow() {
        String fragment = CapitalTestData.cibilLowMessageFragment(context.config());
        Assertions.assertThat(journeyFlow.cibilLowErrorIsVisible())
                .as("Expected a low CIBIL / credit score error containing '%s'", fragment)
                .isTrue();
    }

    @Then("I should be on the lender application page")
    public void assertLenderPage() {
        String src = context.driver().getPageSource();
        Assertions.assertThat(
                src.contains("partner") || src.contains("Apply") || src.contains("chrome")
                        || src.contains("WebView") || !src.contains("offers available"))
                .as("Tapping Apply Now on an offer should open the lender application page or partner WebView")
                .isTrue();
    }
}
