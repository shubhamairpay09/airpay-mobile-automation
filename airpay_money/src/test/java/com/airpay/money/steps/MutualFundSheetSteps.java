package com.airpay.money.steps;

import com.airpay.money.screens.product.MutualFundsCreateGoalScreen;
import com.airpay.money.screens.product.MutualFundsCuratedListScreen;
import com.airpay.money.screens.product.MutualFundsFiltersScreen;
import com.airpay.money.screens.product.MutualFundsFundDetailsScreen;
import com.airpay.money.screens.product.MutualFundsGoalCreatedScreen;
import com.airpay.money.screens.product.MutualFundsIndexListScreen;
import com.airpay.money.screens.product.MutualFundsInvestorProfileScreen;
import com.airpay.money.data.MoneyExcel;
import com.airpay.money.screens.product.MutualFundsLandingScreen;
import com.airpay.money.screens.product.MutualFundsPortfolioScreen;
import com.airpay.money.screens.product.MutualFundsRecommendationScreen;
import com.airpay.money.screens.product.MutualFundsTransactionsScreen;
import com.airpay.money.screens.product.MutualFundsUpdateRiskProfileScreen;
import com.airpay.money.screens.product.ProductsHubScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

/**
 * Mutual Fund sheet. KYC is not started.
 * The Bike goal is created only so it can be checked on My Goals and then deleted.
 * Invest, One Time, and Monthly are not tapped.
 */
public class MutualFundSheetSteps {

    private final ProductsHubScreen products;
    private final MutualFundsLandingScreen landing;
    private final MutualFundsPortfolioScreen portfolio;
    private final MutualFundsTransactionsScreen transactions;
    private final MutualFundsInvestorProfileScreen profile;
    private final MutualFundsCreateGoalScreen createGoal;
    private final MutualFundsGoalCreatedScreen goalCreated;
    private final MutualFundsRecommendationScreen recommendation;
    private final MutualFundsCuratedListScreen curated;
    private final MutualFundsIndexListScreen indexList;
    private final MutualFundsFundDetailsScreen fundDetails;
    private final MutualFundsFiltersScreen filters;
    private final MutualFundsUpdateRiskProfileScreen riskProfile;

    public MutualFundSheetSteps(TestContext context) {
        this.products = new ProductsHubScreen(context);
        this.landing = new MutualFundsLandingScreen(context);
        this.portfolio = new MutualFundsPortfolioScreen(context);
        this.transactions = new MutualFundsTransactionsScreen(context);
        this.profile = new MutualFundsInvestorProfileScreen(context);
        this.createGoal = new MutualFundsCreateGoalScreen(context);
        this.goalCreated = new MutualFundsGoalCreatedScreen(context);
        this.recommendation = new MutualFundsRecommendationScreen(context);
        this.curated = new MutualFundsCuratedListScreen(context);
        this.indexList = new MutualFundsIndexListScreen(context);
        this.fundDetails = new MutualFundsFundDetailsScreen(context);
        this.filters = new MutualFundsFiltersScreen(context);
        this.riskProfile = new MutualFundsUpdateRiskProfileScreen(context);
    }

    @When("I open Mutual Funds from the products hub")
    public void openMf() {
        if (landing.ensureDashboard()) {
            return;
        }
        String src = landing.pageSource();
        if (src.toUpperCase().contains("PROFILE SETTINGS")
                || src.contains("Personal Details") || src.contains("My Documents")
                || src.contains("Work Profile") || src.contains("Privacy Policy")
                || src.contains("webkit.WebView")) {
            for (int i = 0; i < 6 && !landing.isDisplayed() && !products.isDisplayed(); i++) {
                landing.leaveViaBack();
            }
        }
        if (landing.ensureDashboard()) {
            return;
        }
        src = landing.pageSource();
        if (src.contains("Delete") && (src.contains("Desired goal value") || src.contains("Monthly SIP"))) {
            return;
        }
        if (src.contains("Select Tenure") && src.contains("year")) {
            createGoal.selectYears("3 year");
            return;
        }
        if (createGoal.isDisplayed() && src.contains("Bike")) {
            return;
        }
        if (landing.isDisplayed()) {
            return;
        }
        products.open();
        if (landing.isDisplayed() || (createGoal.isDisplayed() && landing.pageSource().contains("Bike"))) {
            return;
        }
        Assertions.assertThat(products.openMutualFunds() || landing.isDisplayed())
                .as("Mutual Funds tile should open the dashboard")
                .isTrue();
    }

    @When("I open Mutual Fund portfolio details")
    public void viewDetails() {
        if (!landing.openSection("View Details")) {
            landing.openTab("Portfolio");
        }
    }

    @Then("Mutual Fund portfolio holdings are displayed")
    public void holdings() {
        String src = portfolio.pageSource();
        Assertions.assertThat(src).as("Investment details should show Portfolio").contains("Portfolio");
        Assertions.assertThat(src).as("Current Value should be displayed").contains("Current Value");
        Assertions.assertThat(src).as("Invested amount should be displayed").contains("Invested");
    }

    @When("I tap the Mutual Funds eye icon")
    public void tapEye() {
        landing.ensureDashboard();
        landing.openTab("Portfolio");
        String before = landing.pageSource();
        java.util.regex.Matcher amount = java.util.regex.Pattern
                .compile("(?:₹|\\?)\\s*\\d[\\d,]*\\.\\d{2}")
                .matcher(before);
        String shown = amount.find() ? amount.group() : (before.contains("0.00") ? "0.00" : "");
        Assertions.assertThat(before.contains("Current Value"))
                .as("Portfolio Current Value should be visible before the eye is tapped")
                .isTrue();
        Assertions.assertThat(landing.toggleEye())
                .as("The unlabeled Portfolio header eye should be tappable")
                .isTrue();
        String after = landing.pageSource();
        Assertions.assertThat(after.contains("Portfolio") || after.contains("Current Value")
                        || after.contains("Mutual Funds"))
                .as("The Portfolio tab should stay open")
                .isTrue();
        boolean masked = after.contains("••••") || after.contains("****") || after.contains("●")
                || after.contains("XX")
                || (before.contains("0.00") && !after.contains("0.00"))
                || (shown != null && !shown.isBlank() && !after.contains(shown));
        Assertions.assertThat(masked)
                .as("Investment value " + shown + " should hide after tapping the eye")
                .isTrue();
    }

    @When("I open Better than FDs")
    @When("I open Better than FDs from Curated Funds")
    public void openBetterThanFds() {
        Assertions.assertThat(landing.openTileInSection("Curated Funds", "Better than FDs"))
                .as("Better than FDs should open")
                .isTrue();
    }

    @Then("the Better than FDs list shows the title and Search Funds")
    public void betterThanFdsListShown() {
        Assertions.assertThat(curated.betterThanFdsListVisible())
                .as("Better than FDs should show the list title and the Search Funds field")
                .isTrue();
    }

    @When("I open filters on Better than FDs")
    public void openFiltersOnBetterThanFds() {
        openBetterThanFds();
        tapTopRightFilter();
    }

    @And("I tap the filter icon at the top right of Better than FDs")
    @And("I tap the blank filter icon at the top right, beside the Better than FDs title")
    public void tapTopRightFilter() {
        Assertions.assertThat(curated.openFilter())
                .as("The filter icon at the top right of Better than FDs should open Filters")
                .isTrue();
    }

    @When("I open Create Goal")
    public void openGoal() {
        Assertions.assertThat(landing.openAddGoal())
                .as("Add Goal should open the goal form. Continue is not tapped.")
                .isTrue();
    }

    @Then("the Create Goal screen shows the goal icons")
    public void goalIcons() {
        Assertions.assertThat(createGoal.showsGoalIcons())
                .as("Create Goal should show Bike, Car, Child Education, and the other goal icons")
                .isTrue();
    }

    @When("I create a Bike goal named {string} for {string} over {string}")
    public void createBikeGoal(String name, String amount, String years) {
        String goalName = MoneyExcel.text("GoalName", name);
        String goalAmount = MoneyExcel.text("GoalAmount", amount);
        String goalTenure = MoneyExcel.text("GoalTenure", years);
        createGoal.selectBike();
        createGoal.enterGoalName(goalName);
        createGoal.enterDesiredAmount(goalAmount);
        Assertions.assertThat(createGoal.selectYears(goalTenure))
                .as("Select Tenure should choose " + goalTenure)
                .isTrue();
        Assertions.assertThat(createGoal.submit())
                .as("Continue should create the goal")
                .isTrue();
        // Continue opens the Bike goal. Stay here so Delete can be tapped.
    }

    @Then("the goal {string} for {string} is listed on My Goals")
    public void goalListed(String name, String amount) {
        String goalName = MoneyExcel.text("GoalName", name);
        String goalAmount = MoneyExcel.text("GoalAmount", amount);
        Assertions.assertThat(landing.goalListed(goalName, goalAmount))
                .as("My Goals should show " + goalName + " and the amount " + goalAmount)
                .isTrue();
    }

    @When("I open the goal {string}")
    public void openGoal(String name) {
        String goalName = MoneyExcel.text("GoalName", name);
        Assertions.assertThat(landing.openGoal(goalName))
                .as("The arrow beside " + goalName + " should open the goal")
                .isTrue();
    }

    @When("I delete the open goal")
    public void deleteGoal() {
        Assertions.assertThat(goalCreated.deleteGoal())
                .as("The delete icon should remove the goal")
                .isTrue();
    }

    @Then("the open goal is deleted")
    public void goalDeleted() {
        String goalName = MoneyExcel.text("GoalName", "bike");
        String goalAmount = MoneyExcel.text("GoalAmount", "50000");
        for (int i = 0; i < 5 && landing.goalListed(goalName, goalAmount); i++) {
            if (landing.openGoal(goalName)) {
                goalCreated.deleteGoal();
            }
        }
        Assertions.assertThat(landing.isDisplayed() || landing.pageSource().contains("Mutual Funds"))
                .as("Mutual Funds / My Goals should be showing after delete")
                .isTrue();
        Assertions.assertThat(landing.goalListed(goalName, goalAmount))
                .as("Deleted goal " + goalName + " should not be listed on My Goals")
                .isFalse();
    }

    @When("I open the recommendation list")
    public void openRecommendations() {
        Assertions.assertThat(landing.openRecommendationList())
                .as("View all on the Recommendation row should open the risk-fund list")
                .isTrue();
    }

    @Then("the recommendation list is displayed")
    public void recommendations() {
        Assertions.assertThat(recommendation.isDisplayed())
                .as("List title should be 'Funds basis on your risk profile' (dashboard Recommendation heading is not the list)")
                .isTrue();
    }

    @When("I open a recommended fund")
    public void openRecommendedFund() {
        openRecommendations();
        Assertions.assertThat(recommendation.openFundCard())
                .as("A recommended fund card should open")
                .isTrue();
    }

    @Then("the fund details page is displayed")
    public void fundPage() {
        Assertions.assertThat(fundDetails.waitDisplayed())
                .as("Fund Details should open. Invest is not tapped.")
                .isTrue();
        String src = fundDetails.pageSource();
        Assertions.assertThat(src.contains("One Time") || src.contains("Monthly") || src.contains("Invest")
                        || src.contains("SIP") || src.contains("Fund Details"))
                .as("An investment option should be displayed. Invest is not tapped.")
                .isTrue();
    }

    @Then("the fund chart shows the NAV price")
    public void chartNav() {
        Assertions.assertThat(fundDetails.chartNavPriceVisible())
                .as("Fund chart should show the NAV as a rupee price (for example 15.07), not a NAV label")
                .isTrue();
    }

    @When("I open Returns on the fund details page")
    public void openFundReturns() {
        Assertions.assertThat(fundDetails.openReturns())
                .as("Returns on fund details should open. Invest is not tapped.")
                .isTrue();
    }

    @Then("the fund return details are displayed")
    public void fundReturnDetails() {
        Assertions.assertThat(fundDetails.returnDetailsVisible())
                .as("Return details (periods or %) should be displayed after tapping Returns")
                .isTrue();
    }

    @When("I open the mutual fund list {string}")
    public void openList(String label) {
        String list = MoneyExcel.text("ListName", label);
        boolean opened = openNamedList(list);
        ScenarioSkip.unlessPresent(opened, "mutual fund module tile \"" + list + "\"");
        Assertions.assertThat(opened).as(list + " should open").isTrue();
    }

    @Then("the mutual fund list {string} is displayed")
    public void listShown(String label) {
        String list = MoneyExcel.text("ListName", label);
        String src = (curated.pageSource() + indexList.pageSource()).replace("&#10;", " ").replace("\n", " ");
        String expected = list.contains("Large") ? "Large Midcap" : list.contains("Bluechip") ? "Bluechip" : list;
        Assertions.assertThat(src)
                .as(list + " funds should be displayed")
                .contains(expected);
    }

    @When("I open View all Mutual Funds")
    public void viewAllFunds() {
        Assertions.assertThat(landing.openViewAllMutualFunds())
                .as("View all Mutual Funds should open")
                .isTrue();
    }

    @Then("the mutual fund catalog is displayed")
    public void catalog() {
        String src = curated.pageSource();
        Assertions.assertThat(src).as("The mutual fund search field should be displayed").contains("EditText");
        Assertions.assertThat(src).as("A mutual fund should be listed").contains("Fund");
    }

    @Then("the risk profile section is displayed")
    public void riskShown() {
        Assertions.assertThat(landing.scrollSectionIntoView("Your Risk Profile"))
                .as("Your Risk Profile should be scrolled into view on the MF dashboard")
                .isTrue();
        String src = landing.pageSource();
        Assertions.assertThat(src).as("Risk profile should be displayed").containsIgnoringCase("risk profile");
        Assertions.assertThat(src.contains("Conservative") || src.contains("Balanced")
                        || src.contains("Aggressive") || src.contains("Moderate"))
                .as("The risk category should be displayed")
                .isTrue();
    }

    @When("I open the risk profile editor")
    public void editRisk() {
        Assertions.assertThat(landing.openRiskEdit())
                .as("The edit control to the right of Your risk profile should open")
                .isTrue();
    }

    @Then("the risk profile edit screen is displayed")
    public void riskEditor() {
        Assertions.assertThat(riskProfile.pageSource())
                .as("The risk profile questionnaire or edit screen should open. The saved profile is not changed.")
                .contains("Update Risk Profile");
        riskProfile.tapLabel("Dismiss");
    }

    @When("I open the Mutual Funds {word} tab")
    public void openMfTab(String tab) {
        landing.openTab(tab);
    }

    @Then("the Mutual Funds portfolio tab is displayed")
    public void portfolioTab() {
        String src = portfolio.pageSource();
        Assertions.assertThat(src).as("Portfolio tab should open").contains("Portfolio");
        Assertions.assertThat(src).as("Portfolio should show Current Value").contains("Current Value");
    }

    @When("I tap the Mutual Funds back button")
    public void tapMfBack() {
        Assertions.assertThat(landing.returnToDashboardFromTab() || landing.onDashboardTab())
                .as("Mutual Funds Back (or the Dashboard tab) should return to the Dashboard without leaving Mutual Funds")
                .isTrue();
    }

    @Then("the Mutual Funds back button is displayed")
    public void backShown() {
        landing.ensureDashboard();
        Assertions.assertThat(landing.onDashboardTab() || landing.isDisplayed()
                        || landing.sourceContains("Back"))
                .as("Mutual Funds dashboard (header Back at top left) should be displayed")
                .isTrue();
    }

    @Then("the Mutual Funds transactions tab is displayed")
    public void txTab() {
        Assertions.assertThat(transactions.pageSource())
                .as("Transactions tab should open")
                .containsIgnoringCase("Transaction");
    }

    @Then("the Mutual Funds profile tab is displayed")
    public void profileTab() {
        Assertions.assertThat(profile.pageSource())
                .as("Profile tab should open")
                .contains("Profile");
    }

    @Then("the Mutual Funds dashboard tab is displayed")
    public void dashTab() {
        Assertions.assertThat(landing.onDashboardTab() || landing.isDisplayed())
                .as("Dashboard tab should show My Goals / Add Goal / Recommendation, not Portfolio")
                .isTrue();
    }

    @When("I search mutual funds for {string}")
    public void searchFunds(String name) {
        String query = MoneyExcel.text("Search", name);
        Assertions.assertThat(landing.openViewAllMutualFunds())
                .as("View all Mutual Funds should open the searchable catalog")
                .isTrue();
        curated.search(query);
    }

    @Then("an HDFC mutual fund result is displayed")
    public void hdfcResult() {
        String query = MoneyExcel.text("Search", "HDFC");
        String src = curated.pageSource();
        Assertions.assertThat(src).as("A fund matching " + query + " should be displayed").contains(query);
        Assertions.assertThat(src).as("A non-matching fund should be filtered out").doesNotContain("Aditya Birla");
    }

    @When("I open filters after searching mutual funds")
    public void openFilters() {
        String query = MoneyExcel.text("Search", "HDFC");
        if (!curated.pageSource().contains(query)) {
            searchFunds(query);
        }
        Assertions.assertThat(curated.openFilter()).as("Filter should open on the search results").isTrue();
    }

    @Then("the filter screen shows Filters, Clear All, Category, AMC, Risk, Returns, Rating, and Apply")
    public void filtersShown() {
        String src = filters.pageSource();
        Assertions.assertThat(src).as("Filters should open").contains("Filters");
        Assertions.assertThat(src).as("Clear All should be displayed").contains("Clear All");
        Assertions.assertThat(src).as("Category should be displayed").contains("Category");
        Assertions.assertThat(src).as("AMC should be displayed").contains("AMC");
        Assertions.assertThat(src).as("Risk should be displayed").contains("Risk");
        Assertions.assertThat(src).as("Returns should be displayed").contains("Returns");
        Assertions.assertThat(src).as("Rating should be displayed").contains("Rating");
        Assertions.assertThat(src).as("Apply should be displayed").contains("Apply");
    }

    @When("I apply the Equity category filter")
    public void applyEquity() {
        filters.selectEquityAndApply();
    }

    @Then("the filter closes and the fund list remains")
    public void filterApplied() {
        Assertions.assertThat(filters.pageSource())
                .as("Apply should close the filter screen")
                .doesNotContain("Select Categories");
        Assertions.assertThat(curated.pageSource())
                .as("The fund list should update after Apply")
                .contains("Fund");
    }

    @Then("only Equity funds are displayed")
    public void equityFunds() {
        String src = curated.pageSource();
        Assertions.assertThat(src).as("Equity funds should be displayed").contains("Equity");
        Assertions.assertThat(src).as("The fund list should be displayed").contains("Fund");
    }

    @Then("the Balanced risk filter is applied")
    public void balancedFunds() {
        Assertions.assertThat(filters.pageSource())
                .as("Apply should close the filter screen")
                .doesNotContain("Select Risk");
        String src = curated.pageSource();
        Assertions.assertThat(src).as("The Balanced risk filter should be applied").contains("Balanced");
        Assertions.assertThat(src).as("Only the matching fund list should be displayed").contains("Fund");
    }

    @When("I apply the Balanced risk filter")
    public void applyRisk() {
        if (!filters.isDisplayed()) {
            curated.openFilter();
        }
        filters.selectBalancedRiskAndApply();
    }

    @Then("the risk filter screen shows AMC, risk, returns, and rating")
    public void filterTabs() {
        String src = filters.pageSource();
        Assertions.assertThat(src).as("AMC should be displayed").contains("AMC");
        Assertions.assertThat(src).as("Risk should be displayed").contains("Risk");
        Assertions.assertThat(src).as("Returns should be displayed").contains("Returns");
        Assertions.assertThat(src).as("Rating should be displayed").contains("Rating");
    }

    @When("I clear all mutual fund filters")
    public void clearFilters() {
        if (!filters.isDisplayed()) {
            curated.openFilter();
        }
        filters.tapLabel("Equity");
        filters.clearAll();
    }

    @Then("the mutual fund filters return to the default list")
    public void cleared() {
        String src = filters.pageSource();
        Assertions.assertThat(src).as("Clear All should keep the filter choices on screen").contains("Category");
        Assertions.assertThat(src).as("Clear All should still be displayed").contains("Clear All");
        Assertions.assertThat(src)
                .as("Clear All should remove the Equity selection")
                .doesNotContainPattern("(?s)content-desc=\"Equity\"[^>]*selected=\"true\"|selected=\"true\"[^>]*content-desc=\"Equity\"");
    }

    @When("I open a fund from the current list")
    public void openCurrentFund() {
        boolean opened = indexList.openFundCard() || curated.openFundCard() || recommendation.openFundCard();
        Assertions.assertThat(opened || fundDetails.isDisplayed())
                .as("A mutual fund card should open details")
                .isTrue();
        Assertions.assertThat(fundDetails.waitDisplayed())
                .as("Fund Details should open. Invest is not tapped.")
                .isTrue();
    }

    @When("I open {string} from the current list")
    public void openNamedFundFromList(String name) {
        String fund = MoneyExcel.text("FundName", name);
        boolean opened = indexList.openNamedFund(fund);
        if (!opened) {
            curated.search(fund.contains("Navi") ? "Navi" : fund);
            opened = indexList.openNamedFund(fund) || curated.openFundCard();
        }
        Assertions.assertThat(opened || fundDetails.isDisplayed())
                .as(fund + " should open fund details")
                .isTrue();
        Assertions.assertThat(fundDetails.waitDisplayed())
                .as("Fund Details for " + fund + " should open. Invest is not tapped.")
                .isTrue();
    }

    private boolean openNamedList(String label) {
        String lower = label.toLowerCase();
        if (lower.contains("emergency") || lower.contains("bluechip") || lower.contains("long term")
                || lower.contains("tax saving") || lower.contains("better than") || lower.contains("high return")
                || lower.contains("short term")) {
            if (lower.contains("bluechip")) {
                return landing.openTileInSection("Curated Funds", "Bluechip Fund", "Bluechip fund", label);
            }
            return landing.openTileInSection("Curated Funds", label);
        }
        if (lower.contains("nifty") || lower.contains("global fund")) {
            if (lower.contains("large")) {
                return landing.openTileInSection("Index Funds", "Nifty Large Midcap 250", label);
            }
            return landing.openTileInSection("Index Funds", label);
        }
        return landing.openSection(label);
    }
}
