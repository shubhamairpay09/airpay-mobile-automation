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
        if (otpScreen.isDisplayed()) {
            return;
        }
        mobileLogin.tapSendOtp();
        Assertions.assertThat(otpScreen.waitUntilDisplayed(Duration.ofSeconds(20)))
                .as("OTP verification screen should open after Send OTP for the entered mobile number")
                .isTrue();
    }

    @Then("the OTP verification screen should be displayed")
    public void assertOtpScreen() {
        Assertions.assertThat(otpScreen.isDisplayed())
                .as("OTP verification screen should show the OTP input and Verify action")
                .isTrue();
        Assertions.assertThat(home.looksLoggedIn())
                .as("Home must not be shown while OTP verification is still required")
                .isFalse();
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

    @Then("the user should remain on OTP verification after the invalid OTP")
    public void assertInvalidOtp() {
        Assertions.assertThat(otpScreen.isDisplayed())
                .as("Invalid OTP should keep the user on the Verify OTP screen")
                .isTrue();
        Assertions.assertThat(home.looksLoggedIn())
                .as("Invalid OTP must not open the Capital home screen")
                .isFalse();
        Assertions.assertThat(otpScreen.pageSource())
                .as("Verify OTP screen should still be showing after the invalid code")
                .contains("Verify OTP");
    }

    @Then("the user should remain on OTP verification after the expired OTP")
    public void assertExpiredOtp() {
        Assertions.assertThat(otpScreen.isDisplayed())
                .as("Expired OTP should keep the user on the Verify OTP screen")
                .isTrue();
        Assertions.assertThat(home.looksLoggedIn())
                .as("Expired OTP must not open the Capital home screen")
                .isFalse();
        Assertions.assertThat(otpScreen.pageSource())
                .as("Verify OTP screen should still be showing after the expired code")
                .contains("Verify OTP");
    }

    @Then("I should see loan offers available")
    public void assertOffers() {
        Assertions.assertThat(journeyFlow.offersAreVisible())
                .as("Loan offers should show '%s', '%s', or '%s'",
                        ScreenMessages.OFFERS_AVAILABLE, ScreenMessages.OFFER_AVAILABLE, ScreenMessages.APPLY_NOW_FOR)
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
        String lender = CapitalExcel.text("LenderName", "Muthoot");
        Assertions.assertThat(src.contains("WebView") || src.contains(lender))
                .as("Lender application should show a WebView or the lender name '%s'", lender)
                .isTrue();
        Assertions.assertThat(src.contains("Tell us about your work"))
                .as("Occupation screen should be closed after Apply Now on the lender offer")
                .isFalse();
    }

    @When("I open the product card from the Capital Excel sheet")
    public void openProductCardFromExcel() {
        journeyFlow.openProductCardFromExcel();
    }

    @Then("the product feature page, occupation screen, personal details, or saved journey should be displayed")
    public void assertProductSurface() {
        Assertions.assertThat(journeyFlow.productSurfaceVisible())
                .as("Opening the product card should show the feature page, occupation, personal details, or a saved journey")
                .isTrue();
    }

    @When("I accept the terms and tap Apply Now when the feature page is showing")
    public void acceptTermsAndApply() {
        journeyFlow.acceptTermsAndOpenApplication();
    }

    @Then("the occupation screen, personal details, or saved journey should be displayed")
    public void assertApplyEntryAfterTerms() {
        Assertions.assertThat(journeyFlow.applyEntryVisible())
                .as("Apply Now should open occupation, personal details, or the saved loan journey")
                .isTrue();
    }

    @When("I select the occupation from the Capital Excel sheet and open personal details")
    public void selectOccupationAndOpenDetails() {
        journeyFlow.selectOccupationAndOpenDetails();
    }

    @Then("the personal details screen should be displayed")
    public void assertPersonalDetailsScreen() {
        Assertions.assertThat(journeyFlow.applicationDetailsVisible() || journeyFlow.addressVisible()
                        || journeyFlow.workDetailsVisible() || journeyFlow.offersAreVisible())
                .as("Personal details should show PAN, name, email, and desired loan amount, or the saved journey should already be on offers")
                .isTrue();
    }

    @When("I enter the PAN, name, email, and desired loan amount from the Capital Excel sheet")
    public void enterIdentityFromExcel() {
        journeyFlow.enterIdentityFromExcel();
    }

    @Then("the personal details screen should show the PAN, name, email, and desired loan amount from the Capital Excel sheet")
    public void assertIdentityValues() {
        if (journeyFlow.offersAreVisible()) {
            return;
        }
        String missing = journeyFlow.missingIdentityValues();
        Assertions.assertThat(missing)
                .as("Personal details should show the Excel PAN, name, email, and desired loan amount. Missing: %s",
                        missing)
                .isEmpty();
    }

    @When("I set the date of birth and gender and tap Continue")
    public void setDobGenderAndContinue() {
        journeyFlow.setDobGenderAndContinueOnce();
    }

    @Then("the occupation screen or the in-progress loan journey should be displayed")
    public void assertApplyEntry() {
        boolean occupation = journeyFlow.occupationVisible();
        boolean journey = journeyFlow.journeyInProgress();
        boolean details = journeyFlow.applicationDetailsVisible();
        Assertions.assertThat(occupation || journey || details)
                .as("Opening the product should show occupation, application details, or the saved loan journey")
                .isTrue();
        if (journey) {
            String src = context.driver().getPageSource();
            for (String step : java.util.List.of(
                    "Basic details", "Address details", "Income",
                    "Credit assessment", "Personalised offers", "Resume Application")) {
                Assertions.assertThat(src)
                        .as("In-progress loan journey should list completed step '%s'", step)
                        .contains(step);
            }
        }
    }

    @When("I continue that application using the Capital Excel sheet")
    public void continueApplicationFromExcel() {
        if (journeyFlow.applicationDetailsVisible()) {
            journeyFlow.completeApplyFormsFromExcel();
            journeyFlow.tapCheckEligibility();
            return;
        }
        if (journeyFlow.occupationVisible()) {
            journeyFlow.selectOccupationFromExcel();
            Assertions.assertThat(journeyFlow.applicationDetailsVisible())
                    .as("Application details screen should be displayed after occupation is selected")
                    .isTrue();
            journeyFlow.completeApplyFormsFromExcel();
            journeyFlow.tapCheckEligibility();
            return;
        }
        journeyFlow.resumeSavedJourney();
    }

    @When("I open the product from the Capital Excel sheet")
    public void openProductFromExcel() {
        journeyFlow.openProductFromExcel();
    }

    @Then("the occupation selection screen should be displayed")
    public void assertOccupation() {
        Assertions.assertThat(journeyFlow.occupationVisible())
                .as("Occupation screen should show Salaried and Self-Employed before the application form")
                .isTrue();
    }

    @When("I select the occupation from the Capital Excel sheet and continue")
    public void selectOccupationFromExcel() {
        journeyFlow.selectOccupationFromExcel();
    }

    @Then("the application details screen should be displayed")
    public void assertApplyDetails() {
        Assertions.assertThat(journeyFlow.applicationDetailsVisible())
                .as("Application details screen should be displayed after occupation is selected")
                .isTrue();
    }

    @When("I fill the application details from the Capital Excel sheet and check eligibility")
    public void fillAndCheckEligibility() {
        journeyFlow.completeApplyFormsFromExcel();
        journeyFlow.tapCheckEligibility();
    }
}
