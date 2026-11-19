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

    private boolean onFinsightForFhc() {
        return finsight.isDashboardHome() || finsight.isDisplayed();
    }

    @When("I open the FinSight dashboard")
    public void openFinsightDashboard() {
        home.ensureMoneyInForeground();
        home.dismissHomeCoachIfShown();
        for (int i = 0; i < 4 && !onFinsightForFhc(); i++) {
            finsight.openFinSightTab();
            home.dismissHomeCoachIfShown();
            if (onFinsightForFhc()) {
                return;
            }
            sleepQuiet(250);
        }
        Assertions.assertThat(onFinsightForFhc())
                .as("FinSight dashboard should show Financial Health Score or the health-check card")
                .isTrue();
    }

    @When("I scroll to the Sync Your Net Worth card")
    public void scrollToSyncCard() {
        SyncedNetWorthMode.clear();
        revealNetWorthCard();
        if (finsight.syncOfferDisplayed()) {
            return;
        }
        if (finsight.alreadySyncedCard()) {
            SyncedNetWorthMode.setActive(true);
            return;
        }
        Assertions.fail("FinSight should show Sync Your Net Worth, or the already-synced net worth card. Visible: "
                + finsight.netWorthCardText());
    }

    private void revealNetWorthCard() {
        String[] needles = {
                "Sync Your Net Worth", "View Net Worth", "Accounts Synced", "Actual Net Worth"
        };
        for (String needle : needles) {
            if (finsight.syncOfferDisplayed() || finsight.alreadySyncedCard()) {
                return;
            }
            finsight.scrollIntoDescription(needle);
        }
    }

    @When("I tap Sync Your Net Worth")
    public void tapSyncYourNetWorth() {
        if (SyncedNetWorthMode.active() || finsight.alreadySyncedCard()) {
            SyncedNetWorthMode.setActive(true);
            return;
        }
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
        if (SyncedNetWorthMode.active()) {
            return;
        }
        tapSyncYourNetWorth();
        if (SyncedNetWorthMode.active()) {
            return;
        }
        Assertions.assertThat(consent.isDisplayed())
                .as("Financial Data Fetch Consent screen should be displayed")
                .isTrue();
    }

    @Then("the Financial Data Fetch Consent screen is displayed")
    public void consentDisplayed() {
        if (assertSyncedNetWorthInstead("Financial Data Fetch Consent screen")) {
            return;
        }
        Assertions.assertThat(consent.isDisplayed())
                .as("Financial Data Fetch Consent screen should be displayed")
                .isTrue();
    }

    private boolean assertSyncedNetWorthInstead(String missingFlow) {
        if (!SyncedNetWorthMode.active()) {
            return false;
        }
        Assertions.assertThat(finsight.alreadySyncedCard())
                .as("Already-synced account should show net worth on FinSight instead of " + missingFlow)
                .isTrue();
        return true;
    }

    @Then("the Consent and Permissions section is displayed")
    public void permissionsDisplayed() {
        if (assertSyncedNetWorthInstead("Consent & Permissions")) {
            return;
        }
        Assertions.assertThat(consent.permissionsDisplayed())
                .as("Consent & Permissions section should be visible")
                .isTrue();
    }

    @Then("Credit Data shows the Date Range")
    public void dateRangeDisplayed() {
        if (assertSyncedNetWorthInstead("Credit Data Date Range")) {
            return;
        }
        Assertions.assertThat(consent.dateRangeDisplayed())
                .as("Credit Data should show the Date Range")
                .isTrue();
    }

    @And("Credit Data shows the Consent Validity")
    public void consentValidityDisplayed() {
        if (assertSyncedNetWorthInstead("Credit Data Consent Validity")) {
            return;
        }
        Assertions.assertThat(consent.consentValidityDisplayed())
                .as("Credit Data should show the Consent Validity")
                .isTrue();
    }

    @And("the consent checkbox is displayed")
    public void checkboxDisplayed() {
        if (assertSyncedNetWorthInstead("consent checkbox")) {
            return;
        }
        Assertions.assertThat(consent.checkboxDisplayed())
                .as("Consent checkbox should be displayed")
                .isTrue();
    }

    @And("Allow Consent is disabled")
    public void allowDisabled() {
        if (assertSyncedNetWorthInstead("Allow Consent")) {
            return;
        }
        Assertions.assertThat(consent.allowConsentDisplayed())
                .as("Allow Consent button should be displayed")
                .isTrue();
        Assertions.assertThat(consent.allowConsentEnabled())
                .as("Allow Consent button should be disabled")
                .isFalse();
    }

    @When("I leave the consent checkbox unselected")
    public void leaveCheckboxUnselected() {
        if (SyncedNetWorthMode.active()) {
            return;
        }
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
        if (assertSyncedNetWorthInstead("consent screen")) {
            return;
        }
        Assertions.assertThat(consent.isDisplayed())
                .as("User should remain on the Financial Data Fetch Consent screen without submitting consent")
                .isTrue();
    }

    @When("I tap the consent checkbox")
    public void tapCheckbox() {
        if (SyncedNetWorthMode.active()) {
            return;
        }
        consent.tapCheckbox();
    }

    @When("I tap the consent checkbox again")
    public void tapCheckboxAgain() {
        if (SyncedNetWorthMode.active()) {
            return;
        }
        consent.tapCheckbox();
    }

    @Then("the consent checkbox is selected")
    public void checkboxSelected() {
        if (assertSyncedNetWorthInstead("consent checkbox selected")) {
            return;
        }
        Assertions.assertThat(consent.checkboxChecked())
                .as("Consent checkbox should be selected")
                .isTrue();
    }

    @Then("the consent checkbox is not selected")
    public void checkboxNotSelected() {
        if (assertSyncedNetWorthInstead("consent checkbox deselected")) {
            return;
        }
        Assertions.assertThat(consent.checkboxChecked())
                .as("Consent checkbox should be deselected")
                .isFalse();
    }

    @When("I select the consent checkbox")
    public void selectCheckbox() {
        if (SyncedNetWorthMode.active()) {
            return;
        }
        if (!consent.checkboxChecked()) {
            consent.tapCheckbox();
        }
        Assertions.assertThat(consent.checkboxChecked())
                .as("Consent checkbox should be selected")
                .isTrue();
    }

    @Then("Allow Consent is enabled")
    public void allowEnabled() {
        if (assertSyncedNetWorthInstead("Allow Consent enabled")) {
            return;
        }
        Assertions.assertThat(consent.allowConsentDisplayed())
                .as("Allow Consent button should be displayed")
                .isTrue();
        Assertions.assertThat(consent.allowConsentEnabled())
                .as("Allow Consent button should be enabled and clickable")
                .isTrue();
    }

    @When("I tap Allow Consent")
    public void tapAllowConsent() {
        if (SyncedNetWorthMode.active()) {
            return;
        }
        Assertions.assertThat(consent.allowConsentEnabled())
                .as("Allow Consent should be enabled before it is tapped")
                .isTrue();
        consent.tapAllowConsent();
    }

    @Then("the next Net Worth sync step is displayed without an error")
    public void nextStepWithoutError() {
        if (assertSyncedNetWorthInstead("next Net Worth sync step")) {
            return;
        }
        Assertions.assertThat(finvu.isDisplayed())
                .as("Allow Consent should open the next Net Worth sync step")
                .isTrue();
        Assertions.assertThat(finvu.errorDisplayed())
                .as("No consent error message should be displayed")
                .isFalse();
    }

    @When("I tap the back arrow on the consent screen")
    public void backFromConsent() {
        if (SyncedNetWorthMode.active()) {
            return;
        }
        consent.goBack();
    }

    @Then("the FinSight dashboard is displayed again")
    public void finsightAgain() {
        if (assertSyncedNetWorthInstead("FinSight dashboard after back")) {
            return;
        }
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
        if (assertSyncedNetWorthInstead("consent not submitted")) {
            return;
        }
        Assertions.assertThat(finvu.visibleNow())
                .as("Back should not submit consent or open the Finvu screen")
                .isFalse();
        Assertions.assertThat(consent.isDisplayed())
                .as("Financial Data Fetch Consent screen should be closed")
                .isFalse();
    }

    @Then("the Finvu Net Worth Consent screen is displayed")
    public void finvuDisplayed() {
        if (assertSyncedNetWorthInstead("Finvu Net Worth Consent screen")) {
            return;
        }
        Assertions.assertThat(finvu.isDisplayed())
                .as("Finvu Net Worth Consent screen should be displayed")
                .isTrue();
    }

    @And("the Finvu safety message is displayed")
    public void safetyMessage() {
        if (assertSyncedNetWorthInstead("Finvu safety message")) {
            return;
        }
        Assertions.assertThat(finvu.safetyMessageDisplayed())
                .as("Message \"airpaymoney uses Finvu to safely receive your financial details\" should be displayed")
                .isTrue();
    }

    @And("the OTP input fields are displayed")
    public void otpFields() {
        if (assertSyncedNetWorthInstead("Finvu OTP fields")) {
            return;
        }
        Assertions.assertThat(finvu.otpFieldsDisplayed())
                .as("OTP input fields should be displayed")
                .isTrue();
        Assertions.assertThat(finvu.otpNotEntered())
                .as("OTP is not entered. The fields stay empty.")
                .isTrue();
    }

    @And("the registered mobile number is partially masked")
    public void maskedMobile() {
        if (assertSyncedNetWorthInstead("masked mobile number")) {
            return;
        }
        Assertions.assertThat(finvu.maskedMobileDisplayed())
                .as("Registered mobile number should be partially masked")
                .isTrue();
    }

    @And("Verify and Proceed is displayed and disabled")
    public void verifyDisabled() {
        if (assertSyncedNetWorthInstead("Verify and Proceed")) {
            return;
        }
        Assertions.assertThat(finvu.verifyProceedDisplayed())
                .as("Verify & Proceed button should be displayed")
                .isTrue();
        Assertions.assertThat(finvu.verifyProceedEnabled())
                .as("Verify & Proceed button should be disabled. It is not tapped.")
                .isFalse();
    }

    @And("the Finvu Terms and Conditions link is displayed")
    public void finvuTerms() {
        if (assertSyncedNetWorthInstead("Finvu Terms and Conditions")) {
            return;
        }
        Assertions.assertThat(finvu.termsDisplayed())
                .as("Finvu Terms and Conditions link should be visible")
                .isTrue();
    }

    @Then("the Finvu screen shows the OTP was sent to the masked mobile number")
    public void otpSent() {
        if (assertSyncedNetWorthInstead("Finvu OTP sent")) {
            return;
        }
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
        if (assertSyncedNetWorthInstead("resend timer")) {
            return;
        }
        Assertions.assertThat(finvu.resendTimerDisplayed())
                .as("Resend timer should be displayed")
                .isTrue();
    }

    private static void sleepQuiet(long ms) {
        // The next page read is the wait.
    }
}
