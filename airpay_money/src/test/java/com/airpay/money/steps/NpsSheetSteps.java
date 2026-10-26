package com.airpay.money.steps;

import com.airpay.money.screens.product.NpsDashboardScreen;
import com.airpay.money.screens.product.NpsTransactionsScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

/**
 * NPS sheet TC_001 to TC_010. Each expected line is its own assertion.
 * KYC is not started. Invest More is opened and payment is not entered.
 */
public class NpsSheetSteps {

    private final NpsDashboardScreen nps;
    private final NpsTransactionsScreen history;

    public NpsSheetSteps(TestContext context) {
        this.nps = new NpsDashboardScreen(context);
        this.history = new NpsTransactionsScreen(context);
    }

    @When("I open NPS from the products hub")
    public void openNps() {
        Assertions.assertThat(nps.openFromHub())
                .as("NPS should open from the products hub")
                .isTrue();
    }

    @Then("the NPS dashboard is displayed")
    public void dashboard() {
        Assertions.assertThat(nps.dashboardDisplayed())
                .as("NPS screen should be open. Invested accounts show Calculate Now. This account shows Features of NPS and Open NPS Account. KYC is not started")
                .isTrue();
    }

    @When("I tap Calculate Now on the NPS dashboard")
    public void calculateNow() {
        ScenarioSkip.unlessPresent(nps.optionDisplayed("Calculate Now"), "Calculate Now");
        Assertions.assertThat(nps.pressCalculateNow())
                .as("Calculate Now under NPS Calculator should be tapped")
                .isTrue();
    }

    @Then("the NPS Calculator screen is displayed")
    public void calculator() {
        Assertions.assertThat(nps.calculatorDisplayed())
                .as("NPS Calculator should show Amount Invested Monthly (in INR)")
                .isTrue();
    }

    @And("the NPS Calculator shows the maturity amount")
    public void maturity() {
        Assertions.assertThat(nps.calculatorShowsMaturity())
                .as("NPS Calculator should show Maturity Amount")
                .isTrue();
    }

    @When("I tap Details on the NPS investment card")
    public void details() {
        ScenarioSkip.unlessPresent(nps.optionDisplayed("Details"), "Details on the NPS investment card");
        Assertions.assertThat(nps.pressDetails())
                .as("Details on the NPS investment card should be tapped")
                .isTrue();
    }

    @Then("the NPS investment details page is displayed")
    public void detailsPage() {
        Assertions.assertThat(nps.detailsDisplayed())
                .as("NPS investment details should show NPS, Current Value, and Invested")
                .isTrue();
    }

    @When("I tap Invest More on the NPS dashboard")
    public void investMore() {
        ScenarioSkip.unlessPresent(nps.optionDisplayed("Invest More"), "Invest More");
        Assertions.assertThat(nps.pressInvestMore())
                .as("Invest More should be tapped")
                .isTrue();
    }

    @Then("the NPS contribution screen is displayed")
    public void contribution() {
        Assertions.assertThat(nps.contributionDisplayed())
                .as("NPS contribution should leave the dashboard and show an amount or One Time")
                .isTrue();
    }

    @And("NPS payment details are not requested")
    public void paymentClosed() {
        Assertions.assertThat(nps.paymentClosed())
                .as("Card, UPI, and Proceed to Pay should not be open")
                .isTrue();
    }

    @When("I tap Your SIPs on the NPS dashboard")
    public void yourSips() {
        ScenarioSkip.unlessPresent(nps.optionDisplayed("Your SIPs"), "Your SIPs");
        Assertions.assertThat(nps.pressYourSips())
                .as("Your SIPs should be tapped")
                .isTrue();
    }

    @Then("the NPS SIP list is displayed")
    public void sipList() {
        Assertions.assertThat(nps.sipListDisplayed())
                .as("Your SIPs should open as the page title and show SIP rows or an empty SIP list")
                .isTrue();
    }

    @When("I tap the NPS Dashboard tab")
    public void dashboardTab() {
        Assertions.assertThat(nps.pressTab("Dashboard"))
                .as("Dashboard tab should be tapped")
                .isTrue();
    }

    @Then("the NPS Dashboard screen is displayed")
    public void dashboardScreen() {
        Assertions.assertThat(nps.dashboardTabDisplayed())
                .as("Dashboard should show Features of NPS and Open NPS Account, or Calculate Now and Invest More")
                .isTrue();
    }

    @And("the NPS Dashboard tab is highlighted")
    public void dashboardHighlighted() {
        highlighted("Dashboard");
    }

    @When("I tap the NPS Portfolio tab")
    public void portfolioTab() {
        Assertions.assertThat(nps.pressTab("Portfolio"))
                .as("Portfolio tab should be tapped")
                .isTrue();
    }

    @Then("the NPS Portfolio screen is displayed")
    public void portfolioScreen() {
        Assertions.assertThat(nps.portfolioDisplayed())
                .as("Portfolio should show My Portfolio and No Portfolio Created, or Current Value")
                .isTrue();
    }

    @And("the NPS Portfolio tab is highlighted")
    public void portfolioHighlighted() {
        highlighted("Portfolio");
    }

    @When("I tap the NPS Transactions tab")
    public void transactionsTab() {
        Assertions.assertThat(nps.pressTab("Transactions"))
                .as("Transactions tab should be tapped")
                .isTrue();
    }

    @Then("the NPS Transactions screen is displayed")
    public void transactionsScreen() {
        Assertions.assertThat(nps.transactionsTabDisplayed())
                .as("Transactions should show Transaction History")
                .isTrue();
    }

    @And("the NPS Transactions tab is highlighted")
    public void transactionsHighlighted() {
        highlighted("Transactions");
    }

    @When("I tap the NPS Profile tab")
    public void profileTab() {
        Assertions.assertThat(nps.pressTab("Profile"))
                .as("Profile tab should be tapped")
                .isTrue();
    }

    @Then("the NPS Profile screen is displayed")
    public void profileScreen() {
        Assertions.assertThat(nps.profileDisplayed())
                .as("Profile should show Investor Profile")
                .isTrue();
    }

    @And("the NPS Profile tab is highlighted")
    public void profileHighlighted() {
        highlighted("Profile");
    }

    @When("I tap the NPS back arrow")
    public void back() {
        nps.goBack();
    }

    @Then("the products hub is displayed again")
    public void hubAgain() {
        Assertions.assertThat(nps.productsHubDisplayed())
                .as("Products hub should be displayed and the NPS dashboard should be closed")
                .isTrue();
    }

    @When("I open NPS Transaction History")
    public void openHistory() {
        Assertions.assertThat(nps.openTransactionHistory())
                .as("NPS Transaction History should open")
                .isTrue();
    }

    @Then("the NPS Transaction History screen is displayed")
    public void historyScreen() {
        Assertions.assertThat(history.historyTitleDisplayed())
                .as("Transaction History title should be displayed")
                .isTrue();
    }

    @And("the NPS All filter is displayed")
    public void allFilter() {
        Assertions.assertThat(history.filterDisplayed("All"))
                .as("All filter should be displayed")
                .isTrue();
    }

    @And("the NPS One Time filter is displayed")
    public void oneTimeFilter() {
        Assertions.assertThat(history.filterDisplayed("One Time"))
                .as("One Time filter should be displayed")
                .isTrue();
    }

    @And("the NPS SIP filter is displayed")
    public void sipFilter() {
        Assertions.assertThat(history.filterDisplayed("SIP"))
                .as("SIP filter should be displayed")
                .isTrue();
    }

    @And("NPS transaction records are displayed")
    public void records() {
        ScenarioSkip.unlessPresent(history.recordsDisplayed(), "NPS transaction records");
    }

    @When("I tap the NPS All filter")
    public void tapAll() {
        Assertions.assertThat(history.pressFilter("All"))
                .as("All filter should be tapped")
                .isTrue();
    }

    @Then("NPS All shows one-time and SIP transactions")
    public void allBoth() {
        ScenarioSkip.unlessPresent(history.hasOneTimeRow() && history.hasSipRow(),
                "both a One Time transaction and a SIP transaction");
        Assertions.assertThat(history.allShowsBothTypes())
                .as("All should show a row labelled One Time and a row labelled SIP")
                .isTrue();
    }

    @When("I tap the NPS One Time filter")
    public void tapOneTime() {
        Assertions.assertThat(history.pressFilter("One Time"))
                .as("One Time filter should be tapped")
                .isTrue();
    }

    @Then("NPS One Time shows only one-time transactions")
    public void onlyOneTime() {
        ScenarioSkip.unlessPresent(history.hasOneTimeRow(), "a One Time NPS transaction");
        Assertions.assertThat(history.onlyOneTimeRows())
                .as("Every visible NPS row should be labelled One Time")
                .isTrue();
    }

    @And("NPS One Time hides SIP transactions")
    public void hideSip() {
        Assertions.assertThat(history.sipRowsHidden())
                .as("No visible NPS row should be labelled SIP")
                .isTrue();
    }

    @When("I tap the NPS SIP filter")
    public void tapSip() {
        Assertions.assertThat(history.pressFilter("SIP"))
                .as("SIP filter should be tapped")
                .isTrue();
    }

    @Then("NPS SIP shows only SIP transactions")
    public void onlySip() {
        ScenarioSkip.unlessPresent(history.hasSipRow(), "an NPS SIP transaction");
        Assertions.assertThat(history.onlySipRows())
                .as("Every visible NPS row should be labelled SIP")
                .isTrue();
    }

    @And("NPS SIP hides one-time transactions")
    public void hideOneTime() {
        Assertions.assertThat(history.oneTimeRowsHidden())
                .as("No visible NPS row should be labelled One Time")
                .isTrue();
    }

    private void highlighted(String tab) {
        Assertions.assertThat(nps.tabHighlighted(tab))
                .as(tab + " tab should be selected")
                .isTrue();
    }
}
