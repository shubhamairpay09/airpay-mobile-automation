package com.airpay.money.steps;

import com.airpay.money.screens.finsight.FinvuNetWorthScreen;
import com.airpay.money.screens.finsight.FinSightHomeScreen;
import com.airpay.money.screens.finsight.NetWorthConsentScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

/**
 * Sync Your Net Worth sheet. Login is already done.
 * Allow Consent is tapped only when the case submits consent.
 * The OTP is not entered and Verify &amp; Proceed is not tapped.
 */
public class NetWorthSyncSteps {

    private final HomeScreen home;
    private final FinSightHomeScreen finsight;
    private final NetWorthConsentScreen consent;
    private final FinvuNetWorthScreen finvu;

    public NetWorthSyncSteps(TestContext context) {
        this.home = new HomeScreen(context);
        this.finsight = new FinSightHomeScreen(context);
        this.consent = new NetWorthConsentScreen(context);
        this.finvu = new FinvuNetWorthScreen(context);
    }

    @When("I open the FinSight dashboard")
    public void openFinsightDashboard() {
        for (int i = 0; i < 4 && !finsight.isDisplayed(); i++) {
            finsight.tapLabel("FinSight");
            home.dismissHomeCoachIfShown();
        }
        Assertions.assertThat(finsight.isDisplayed())
                .as("FinSight dashboard should be displayed")
                .isTrue();
    }

    @When("I scroll to the Sync Your Net Worth card")
    public void scrollToSyncCard() {
        if (!finsight.syncOfferDisplayed()) {
            finsight.scrollIntoDescription("Sync Your Net Worth");
        }
        if (!finsight.syncOfferDisplayed()) {
            ScenarioSkip.unlessPresent(false,
                    "Sync Your Net Worth. This account shows: " + finsight.netWorthCardText());
        }
    }

    @When("I tap Sync Your Net Worth")
    public void tapSyncYourNetWorth() {
        ScenarioSkip.unlessPresent(finsight.syncOfferDisplayed(), "Sync Your Net Worth");
        Assertions.assertThat(finsight.pressSyncOffer())
                .as("Sync Your Net Worth card should open")
                .isTrue();
    }

    @When("I open the Financial Data Fetch Consent screen")
    public void openConsentScreen() {
        if (consent.isDisplayed()) {
            return;
        }
        openFinsightDashboard();
        scrollToSyncCard();
        tapSyncYourNetWorth();
        Assertions.assertThat(consent.isDisplayed())
                .as("Financial Data Fetch Consent screen should be displayed")
                .isTrue();
    }

    @Then("the Financial Data Fetch Consent screen is displayed")
    public void consentDisplayed() {
        Assertions.assertThat(consent.isDisplayed())
                .as("Financial Data Fetch Consent screen should be displayed")
                .isTrue();
    }

    @Then("the Consent and Permissions section is displayed")
    public void permissionsDisplayed() {
        Assertions.assertThat(consent.permissionsDisplayed())
                .as("Consent & Permissions section should be visible")
                .isTrue();
    }

    @Then("Credit Data shows the Date Range")
    public void dateRangeDisplayed() {
        Assertions.assertThat(consent.dateRangeDisplayed())
                .as("Credit Data should show the Date Range")
                .isTrue();
    }

    @And("Credit Data shows the Consent Validity")
    public void consentValidityDisplayed() {
        Assertions.assertThat(consent.consentValidityDisplayed())
                .as("Credit Data should show the Consent Validity")
                .isTrue();
    }

    @And("the consent checkbox is displayed")
    public void checkboxDisplayed() {
        Assertions.assertThat(consent.checkboxDisplayed())
                .as("Consent checkbox should be displayed")
                .isTrue();
    }

    @And("Allow Consent is disabled")
    public void allowDisabled() {
        Assertions.assertThat(consent.allowConsentDisplayed())
                .as("Allow Consent button should be displayed")
                .isTrue();
        Assertions.assertThat(consent.allowConsentEnabled())
                .as("Allow Consent button should be disabled")
                .isFalse();
    }

    @When("I leave the consent checkbox unselected")
    public void leaveCheckboxUnselected() {
        consent.leaveCheckboxUnselected();
        Assertions.assertThat(consent.checkboxChecked())
                .as("Consent checkbox should stay unselected")
                .isFalse();
    }

    @Then("Allow Consent stays disabled")
    public void allowStaysDisabled() {
        allowDisabled();
    }

    @And("the Financial Data Fetch Consent screen is still displayed")
    public void stillOnConsent() {
        Assertions.assertThat(consent.isDisplayed())
                .as("User should remain on the Financial Data Fetch Consent screen without submitting consent")
                .isTrue();
    }

    @When("I tap the consent checkbox")
    public void tapCheckbox() {
        consent.tapCheckbox();
    }

    @When("I tap the consent checkbox again")
    public void tapCheckboxAgain() {
        consent.tapCheckbox();
    }

    @Then("the consent checkbox is selected")
    public void checkboxSelected() {
        Assertions.assertThat(consent.checkboxChecked())
                .as("Consent checkbox should be selected")
                .isTrue();
    }

    @Then("the consent checkbox is not selected")
    public void checkboxNotSelected() {
        Assertions.assertThat(consent.checkboxChecked())
                .as("Consent checkbox should be deselected")
                .isFalse();
    }

    @When("I select the consent checkbox")
    public void selectCheckbox() {
        if (!consent.checkboxChecked()) {
            consent.tapCheckbox();
        }
        Assertions.assertThat(consent.checkboxChecked())
                .as("Consent checkbox should be selected")
                .isTrue();
    }

    @Then("Allow Consent is enabled")
    public void allowEnabled() {
        Assertions.assertThat(consent.allowConsentDisplayed())
                .as("Allow Consent button should be displayed")
                .isTrue();
        Assertions.assertThat(consent.allowConsentEnabled())
                .as("Allow Consent button should be enabled and clickable")
                .isTrue();
    }

    @When("I tap Allow Consent")
    public void tapAllowConsent() {
        Assertions.assertThat(consent.allowConsentEnabled())
                .as("Allow Consent should be enabled before it is tapped")
                .isTrue();
        consent.tapAllowConsent();
    }

    @Then("the next Net Worth sync step is displayed without an error")
    public void nextStepWithoutError() {
        Assertions.assertThat(finvu.isDisplayed())
                .as("Allow Consent should open the next Net Worth sync step")
                .isTrue();
        Assertions.assertThat(finvu.errorDisplayed())
                .as("No consent error message should be displayed")
                .isFalse();
    }

    @When("I tap the back arrow on the consent screen")
    public void backFromConsent() {
        consent.goBack();
    }

    @Then("the FinSight dashboard is displayed again")
    public void finsightAgain() {
        boolean shown = false;
        long end = System.currentTimeMillis() + 8_000;
        while (System.currentTimeMillis() < end && !shown) {
            shown = finsight.isDisplayed();
            if (!shown) {
                sleepQuiet(400);
            }
        }
        Assertions.assertThat(shown)
                .as("Back should return to the FinSight dashboard")
                .isTrue();
    }

    @And("consent was not submitted")
    public void consentNotSubmitted() {
        Assertions.assertThat(finvu.visibleNow())
                .as("Back should not submit consent or open the Finvu screen")
                .isFalse();
        Assertions.assertThat(consent.isDisplayed())
                .as("Financial Data Fetch Consent screen should be closed")
                .isFalse();
    }

    @Then("the Finvu Net Worth Consent screen is displayed")
    public void finvuDisplayed() {
        Assertions.assertThat(finvu.isDisplayed())
                .as("Finvu Net Worth Consent screen should be displayed")
                .isTrue();
    }

    @And("the Finvu safety message is displayed")
    public void safetyMessage() {
        Assertions.assertThat(finvu.safetyMessageDisplayed())
                .as("Message \"airpaymoney uses Finvu to safely receive your financial details\" should be displayed")
                .isTrue();
    }

    @And("the OTP input fields are displayed")
    public void otpFields() {
        Assertions.assertThat(finvu.otpFieldsDisplayed())
                .as("OTP input fields should be displayed")
                .isTrue();
        Assertions.assertThat(finvu.otpNotEntered())
                .as("OTP is not entered. The fields stay empty.")
                .isTrue();
    }

    @And("the registered mobile number is partially masked")
    public void maskedMobile() {
        Assertions.assertThat(finvu.maskedMobileDisplayed())
                .as("Registered mobile number should be partially masked")
                .isTrue();
    }

    @And("Verify and Proceed is displayed and disabled")
    public void verifyDisabled() {
        Assertions.assertThat(finvu.verifyProceedDisplayed())
                .as("Verify & Proceed button should be displayed")
                .isTrue();
        Assertions.assertThat(finvu.verifyProceedEnabled())
                .as("Verify & Proceed button should be disabled. It is not tapped.")
                .isFalse();
    }

    @And("the Finvu Terms and Conditions link is displayed")
    public void finvuTerms() {
        Assertions.assertThat(finvu.termsDisplayed())
                .as("Finvu Terms and Conditions link should be visible")
                .isTrue();
    }

    @Then("the Finvu screen shows the OTP was sent to the masked mobile number")
    public void otpSent() {
        Assertions.assertThat(finvu.isDisplayed())
                .as("Finvu OTP screen should be displayed")
                .isTrue();
        Assertions.assertThat(finvu.otpFieldsDisplayed())
                .as("OTP input fields should show that an OTP was sent")
                .isTrue();
        Assertions.assertThat(finvu.maskedMobileDisplayed())
                .as("OTP should be sent to the masked registered mobile number")
                .isTrue();
        Assertions.assertThat(finvu.otpNotEntered())
                .as("OTP is not entered. The case stops on this screen.")
                .isTrue();
    }

    @And("the resend timer is displayed")
    public void resendTimer() {
        Assertions.assertThat(finvu.resendTimerDisplayed())
                .as("Resend timer should be displayed")
                .isTrue();
    }

    private static void sleepQuiet(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
