package com.airpay.money.steps;

import com.airpay.money.screens.profile.ProfileSettingsScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

/**
 * Profile settings sheet. Login is already done.
 * Update, Send Message, and Log Out Yes are not tapped. Change PIN is not opened.
 * TC_023 has no steps. TC_025 and TC_027 would confirm logout, so they are not run.
 */
public class ProfileSettingsSteps {

    private final ProfileSettingsScreen profile;

    public ProfileSettingsSteps(TestContext context) {
        this.profile = new ProfileSettingsScreen(context);
    }

    @When("I open Profile Settings")
    public void openSettings() {
        Assertions.assertThat(profile.open())
                .as("Profile Settings should open from Open settings")
                .isTrue();
    }

    @When("I open Personal Details from Profile Settings")
    public void openPersonal() {
        Assertions.assertThat(profile.openRow("Personal Details"))
                .as("Personal Details should be tapped")
                .isTrue();
    }

    @Then("the Profile Personal Details page is displayed")
    public void personalPage() {
        Assertions.assertThat(profile.titleNearTop("Personal Details"))
                .as("Personal Details title should be displayed")
                .isTrue();
    }

    @When("I tap Edit personal details")
    public void editPersonal() {
        Assertions.assertThat(profile.openRow("Edit personal details"))
                .as("The Edit personal details pencil should be tapped")
                .isTrue();
    }

    @Then("the Edit Personal Details page is displayed")
    public void editPage() {
        Assertions.assertThat(profile.editPersonalDisplayed())
                .as("Edit Personal Details should show Full name, Father's name, and Mother's name")
                .isTrue();
    }

    @And("the Edit Personal Details fields are pre-populated")
    public void editPrefilled() {
        Assertions.assertThat(profile.editFieldsPrefilled())
                .as("Each personal EditText should show a saved value")
                .isTrue();
    }

    @When("I tap Back on Profile Settings")
    public void back() {
        profile.goBack();
    }

    @Then("the Profile Personal Details page is displayed again")
    public void personalAgain() {
        Assertions.assertThat(profile.personalDetailsAgain())
                .as("Personal Details should be displayed again, with Edit personal details and without Update")
                .isTrue();
    }

    @Then("the Profile Settings screen is displayed")
    public void settings() {
        Assertions.assertThat(profile.settingsDisplayed())
                .as("PROFILE SETTINGS should be displayed")
                .isTrue();
    }

    @When("I open Work Details from Profile Settings")
    public void openWork() {
        Assertions.assertThat(profile.openRow("Work Details"))
                .as("Work Details should be tapped")
                .isTrue();
    }

    @Then("the Work Profile page is displayed")
    public void workPage() {
        Assertions.assertThat(profile.titleNearTop("Work Profile"))
                .as("Work Profile title should be displayed")
                .isTrue();
    }

    @And("the Current Company section is displayed")
    public void currentCompany() {
        Assertions.assertThat(profile.shows("Current Company"))
                .as("Current Company should be displayed")
                .isTrue();
    }

    @And("the Past Jobs section is displayed")
    public void pastJobs() {
        Assertions.assertThat(profile.shows("Past Jobs"))
                .as("Past Jobs should be displayed")
                .isTrue();
    }

    @And("the current Company Name is displayed")
    public void companyName() {
        Assertions.assertThat(profile.shows("Company Name"))
                .as("Company Name should be displayed")
                .isTrue();
    }

    @And("the current Designation is displayed")
    public void designation() {
        Assertions.assertThat(profile.shows("Designation"))
                .as("Designation should be displayed")
                .isTrue();
    }

    @And("the current Monthly Income is displayed")
    public void income() {
        Assertions.assertThat(profile.shows("Monthly Income"))
                .as("Monthly Income should be displayed")
                .isTrue();
    }

    @When("I open Address Details from Profile Settings")
    public void openAddress() {
        Assertions.assertThat(profile.openRow("Address Details"))
                .as("Address Details should be tapped")
                .isTrue();
    }

    @Then("the Profile Address Details page is displayed")
    public void addressPage() {
        Assertions.assertThat(profile.titleNearTop("Address Details"))
                .as("Address Details title should be displayed")
                .isTrue();
    }

    @And("the Current Address section is displayed")
    public void currentAddress() {
        Assertions.assertThat(profile.shows("Current Address"))
                .as("Current Address should be displayed")
                .isTrue();
    }

    @And("the Permanent Address section is displayed")
    public void permanentAddress() {
        Assertions.assertThat(profile.shows("Permanent Address"))
                .as("Permanent Address should be displayed")
                .isTrue();
    }

    @And("the Current Address shows Address Line 1")
    public void line1() {
        shows("Address Line 1");
    }

    @And("the Current Address shows Address Line 2")
    public void line2() {
        shows("Address Line 2");
    }

    @And("the Current Address shows Landmark")
    public void landmark() {
        shows("Landmark");
    }

    @And("the Current Address shows State")
    public void state() {
        shows("State");
    }

    @And("the Current Address shows City")
    public void city() {
        shows("City");
    }

    @And("the Current Address shows Pincode")
    public void pincode() {
        shows("Pincode");
    }

    @When("I open Document Details from Profile Settings")
    public void openDocuments() {
        Assertions.assertThat(profile.openRow("Document Details"))
                .as("Document Details should be tapped")
                .isTrue();
    }

    @Then("the My Documents page is displayed")
    public void documents() {
        Assertions.assertThat(profile.titleNearTop("My Documents"))
                .as("My Documents title should be displayed")
                .isTrue();
    }

    @And("an uploaded document is displayed")
    public void uploaded() {
        Assertions.assertThat(profile.uploadedDocumentDisplayed())
                .as("My Documents should show an uploaded document")
                .isTrue();
    }

    @When("I open the PAN Card document")
    public void openPan() {
        Assertions.assertThat(profile.openContaining("PAN"))
                .as("The PAN Card row should be tapped")
                .isTrue();
    }

    @Then("the PAN Card details are displayed")
    public void panDetails() {
        Assertions.assertThat(profile.panDetailsDisplayed())
                .as("PAN Card details should show PAN and the PAN number")
                .isTrue();
    }

    @When("I open Contact Us from Profile Settings")
    public void openContact() {
        Assertions.assertThat(profile.openRow("Contact Us"))
                .as("Contact Us should be tapped")
                .isTrue();
    }

    @Then("the Profile Contact Us page is displayed")
    public void contactPage() {
        Assertions.assertThat(profile.contactFormDisplayed())
                .as("Contact Us should show Full Name, Email, Phone Number, Subject, Message, Attach File, and Send Message")
                .isTrue();
    }

    @And("the Contact Us Full Name is pre-populated")
    public void contactName() {
        Assertions.assertThat(profile.contactValue("Full Name"))
                .as("Full Name should show the logged-in name")
                .matches("[A-Za-z]+(?:[ .'-][A-Za-z]+)+");
    }

    @And("the Contact Us Email is pre-populated")
    public void contactEmail() {
        Assertions.assertThat(profile.contactValue("Email"))
                .as("Email should show the logged-in email")
                .contains("@");
    }

    @And("the Contact Us Phone Number is pre-populated")
    public void contactPhone() {
        String digits = profile.contactValue("Phone").replaceAll("\\D", "");
        Assertions.assertThat(digits)
                .as("Phone Number should show the logged-in mobile number")
                .hasSizeGreaterThanOrEqualTo(10);
    }

    @Then("the Profile Settings screen is displayed with the user still signed in")
    public void stillSignedIn() {
        Assertions.assertThat(profile.stillLoggedIn())
                .as("Profile Settings should still show the signed-in user")
                .isTrue();
    }

    @When("I open Utilities from Profile Settings")
    public void utilities() {
        Assertions.assertThat(profile.openRow("Utilities"))
                .as("Utilities should be tapped")
                .isTrue();
    }

    @When("I open Terms and Conditions from Profile Settings")
    public void terms() {
        Assertions.assertThat(profile.openRow("Terms & Conditions"))
                .as("Terms & Conditions should be tapped")
                .isTrue();
    }

    @Then("the Profile Terms and Conditions page is displayed")
    public void termsPage() {
        Assertions.assertThat(profile.termsDisplayed())
                .as("Terms & Conditions should open in the page view")
                .isTrue();
    }

    @When("I open Privacy Policy from Profile Settings")
    public void privacy() {
        Assertions.assertThat(profile.openRow("Privacy Policy"))
                .as("Privacy Policy should be tapped")
                .isTrue();
    }

    @Then("the Profile Privacy Policy page is displayed")
    public void privacyPage() {
        Assertions.assertThat(profile.privacyTitleDisplayed())
                .as("Privacy Policy title should be displayed")
                .isTrue();
    }

    @And("the Privacy Policy last updated date is displayed")
    public void privacyDate() {
        Assertions.assertThat(profile.privacyDateDisplayed())
                .as("Privacy Policy should show Last Updated and a date")
                .isTrue();
    }

    @When("I scroll the Privacy Policy page")
    public void scrollPrivacy() {
        Assertions.assertThat(profile.privacyScrolled())
                .as("Privacy Policy should scroll to more sections and stay open")
                .isTrue();
    }

    @When("I tap the Instagram icon on Profile Settings")
    public void instagram() {
        openSocial(0, "Instagram");
    }

    @Then("the Instagram page is displayed")
    public void instagramPage() {
        social("instagram");
    }

    @When("I tap the Facebook icon on Profile Settings")
    public void facebook() {
        openSocial(1, "Facebook");
    }

    @Then("the Facebook page is displayed")
    public void facebookPage() {
        social("facebook");
    }

    @When("I tap the LinkedIn icon on Profile Settings")
    public void linkedin() {
        openSocial(2, "LinkedIn");
    }

    @Then("the LinkedIn page is displayed")
    public void linkedinPage() {
        social("linkedin");
    }

    @When("I tap the YouTube icon on Profile Settings")
    public void youtube() {
        openSocial(3, "YouTube");
    }

    @Then("the YouTube page is displayed")
    public void youtubePage() {
        social("youtube");
    }

    @When("I return to Profile Settings")
    public void returnSettings() {
        Assertions.assertThat(profile.returnToSettings())
                .as("Profile Settings should be displayed again")
                .isTrue();
    }

    @When("I tap Log Out on Profile Settings")
    public void logOut() {
        Assertions.assertThat(profile.openRow("Log Out"))
                .as("Log Out should be tapped. Yes is not tapped")
                .isTrue();
    }

    @Then("the logout confirmation is displayed")
    public void logoutPrompt() {
        Assertions.assertThat(profile.logoutPromptDisplayed())
                .as("Logout confirmation should show Are you sure, you want to log out from the Application? with Yes and No")
                .isTrue();
    }

    @When("I tap No on the logout confirmation")
    public void tapNo() {
        Assertions.assertThat(profile.dismissLogout())
                .as("No should close the logout confirmation")
                .isTrue();
    }

    @Then("the logout confirmation is closed")
    public void logoutClosed() {
        Assertions.assertThat(profile.stillLoggedIn())
                .as("The user should remain on Profile Settings")
                .isTrue();
    }

    private void openSocial(int index, String name) {
        Assertions.assertThat(profile.openSocial(index))
                .as(name + " icon should be tapped")
                .isTrue();
    }

    private void social(String host) {
        Assertions.assertThat(profile.socialHostDisplayed(host))
                .as(host + " should be the opened page")
                .isTrue();
    }

    private void shows(String label) {
        Assertions.assertThat(profile.shows(label))
                .as(label + " should be displayed")
                .isTrue();
    }
}
