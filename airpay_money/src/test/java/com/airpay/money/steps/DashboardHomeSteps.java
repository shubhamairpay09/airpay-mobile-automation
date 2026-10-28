package com.airpay.money.steps;

import com.airpay.money.screens.home.DashboardHomeScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

/**
 * Dashboard home page sheet, TC_001 to TC_008.
 * Login, OTP, account setup, email verification, PIN, and biometrics are not run.
 * Let's Start stops on Personal Details. Sync Your Net Worth stops on the consent screen.
 */
public class DashboardHomeSteps {

    private final DashboardHomeScreen dashboard;

    public DashboardHomeSteps(TestContext context) {
        this.dashboard = new DashboardHomeScreen(context);
    }

    @Then("the dashboard home page is displayed")
    public void homePage() {
        Assertions.assertThat(dashboard.dashboardDisplayed())
                .as("Dashboard should show Hello, Your Financial Health Score, and FinSight")
                .isTrue();
    }

    @And("the dashboard shows the welcome greeting Hello")
    public void helloOnDashboard() {
        Assertions.assertThat(dashboard.showsExact("Hello"))
                .as("Hello should be displayed on the dashboard")
                .isTrue();
    }

    @And("the dashboard shows Your Financial Health Score")
    public void scoreOnDashboard() {
        Assertions.assertThat(dashboard.healthTitleDisplayed())
                .as("Your Financial Health Score should be displayed")
                .isTrue();
    }

    @And("the dashboard shows the FinSight tab")
    public void finsightOnDashboard() {
        Assertions.assertThat(dashboard.tabDisplayed("FinSight"))
                .as("FinSight tab should be displayed")
                .isTrue();
    }

    @Then("the welcome message Hello is displayed")
    public void welcome() {
        Assertions.assertThat(dashboard.showsExact("Hello"))
                .as("Welcome message Hello should be displayed")
                .isTrue();
    }

    @And("the registered user name is displayed beside Hello")
    public void registeredName() {
        String name = dashboard.nameBesideHello();
        Assertions.assertThat(name)
                .as("The name beside Hello should be the registered user name")
                .matches("[A-Za-z]+(?:[ .'-][A-Za-z]+){1,6}");
    }

    @Then("the Financial Health Check card shows Your Financial Health Score")
    public void healthCard() {
        Assertions.assertThat(dashboard.healthTitleDisplayed())
                .as("Financial Health Check card should show Your Financial Health Score")
                .isTrue();
    }

    @And("the Financial Health Check card shows its information text")
    public void healthInfo() {
        Assertions.assertThat(dashboard.healthInformationLines())
                .as("Financial Health Check card should show information text besides the score title")
                .isNotEmpty();
    }

    @And("the Financial Health Check information sits above Let's Start")
    public void healthAligned() {
        Assertions.assertThat(dashboard.healthInformationAligned())
                .as("Financial Health Check information should sit above Let's Start / Reassess and share the card's left edge")
                .isTrue();
    }

    @And("the Let's Start button is displayed")
    public void letsStart() {
        Assertions.assertThat(dashboard.letsStartDisplayed())
                .as("Let's Start, Reassess, or View Details should be displayed on the health card")
                .isTrue();
    }

    @When("I tap Let's Start on the dashboard")
    public void tapLetsStart() {
        Assertions.assertThat(dashboard.tapLetsStart())
                .as("Let's Start or Reassess should open the Financial Health Check journey")
                .isTrue();
    }

    @Then("the Financial Health Check Personal Details page is displayed")
    public void personalDetails() {
        Assertions.assertThat(dashboard.personalDetailsDisplayed())
                .as("Personal Details or the completed diagnosis should be displayed")
                .isTrue();
    }

    @Then("the Sync Your Net Worth button is displayed on the dashboard")
    public void syncButton() {
        Assertions.assertThat(dashboard.syncButtonDisplayed())
                .as("Sync Your Net Worth button should be displayed")
                .isTrue();
    }

    @And("the Sync Your Net Worth description is displayed")
    public void syncDescription() {
        Assertions.assertThat(dashboard.syncDescription())
                .as("Sync Your Net Worth description should be displayed")
                .hasSizeGreaterThan(8);
    }

    @When("I tap the Sync Your Net Worth button on the dashboard")
    public void tapSync() {
        Assertions.assertThat(dashboard.tapSync())
                .as("Sync Your Net Worth should open the consent screen. Allow Consent is not tapped")
                .isTrue();
    }

    @And("Allow Consent is displayed on the consent screen")
    public void allowConsent() {
        Assertions.assertThat(dashboard.allowConsentDisplayed())
                .as("Allow Consent should be displayed. It is not tapped")
                .isTrue();
    }

    @Then("the Products tab is displayed on the dashboard")
    public void productsTab() {
        Assertions.assertThat(dashboard.tabDisplayed("Products"))
                .as("Products tab should be displayed")
                .isTrue();
    }

    @And("the FinSight tab is displayed on the dashboard")
    public void finsightTab() {
        Assertions.assertThat(dashboard.tabDisplayed("FinSight"))
                .as("FinSight tab should be displayed")
                .isTrue();
    }

    @And("the Calculators tab is displayed on the dashboard")
    public void calculatorsTab() {
        Assertions.assertThat(dashboard.tabDisplayed("Calculators"))
                .as("Calculators tab should be displayed")
                .isTrue();
    }

    @And("the Products, FinSight, and Calculators tabs are aligned")
    public void tabsAligned() {
        Assertions.assertThat(dashboard.tabsAligned())
                .as("Products, FinSight, and Calculators should sit on one row from left to right")
                .isTrue();
    }

    @Then("the notification icon is visible in the dashboard header")
    public void notificationVisible() {
        Assertions.assertThat(dashboard.notificationVisible())
                .as("Notifications icon should be visible in the header")
                .isTrue();
    }

    @And("the notification icon is enabled")
    public void notificationEnabled() {
        Assertions.assertThat(dashboard.notificationEnabled())
                .as("Notifications icon should be enabled")
                .isTrue();
    }
}
