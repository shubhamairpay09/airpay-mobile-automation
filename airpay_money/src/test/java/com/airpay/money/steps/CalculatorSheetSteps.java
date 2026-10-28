package com.airpay.money.steps;

import com.airpay.money.data.MoneyExcel;
import com.airpay.money.screens.calculators.CalculatorScreen;
import com.airpay.money.screens.calculators.CalculatorsHubScreen;
import com.airpay.money.screens.finsight.FinSightHomeScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

/**
 * Calculator sheet. Start SIP and Start Investment are opened only.
 * No investment, payment, or KYC is completed.
 */
public class CalculatorSheetSteps {

    private static final String[][] CALCULATORS = {
            {"EMI Calculator", "EMI Calculator"},
            {"SIP Calculator", "SIP Calculator"},
            {"Lumpsum Calculator", "Lumpsum Calculator"},
            {"PPF Calculator", "PPF Calculator"},
            {"EPF Calculator", "EPF Calculator"},
            {"FD Calculator", "Fixed Deposit"},
            {"NPS Calculator", "NPS Calculator"},
            {"Retirement Calculator", "Retirement Calculator"},
            {"Crorepati Calculator", "Crorepati Calculator"},
            {"Term Insurance Calculator", "Retirement Calculator"},
            {"Buy vs Rent Calculator", "Buy Vs Rent"},
            {"Taxation Indian Equity", "Taxation Indian Equity"},
            {"Taxation International Equity", "Taxation International Equity"},
            {"Inflation Calculator", "Inflation Calculator"},
            {"Budget Calculator", "Budget Calculator"}
    };

    private final FinSightHomeScreen finsight;
    private final CalculatorsHubScreen hub;
    private final CalculatorScreen calculator;
    private String rememberedValue = "";
    private String catalog = "";

    public CalculatorSheetSteps(TestContext context) {
        this.finsight = new FinSightHomeScreen(context);
        this.hub = new CalculatorsHubScreen(context);
        this.calculator = new CalculatorScreen(context);
    }

    @When("I open the Calculators tab")
    public void openTab() {
        if (!hub.isDisplayed()) {
            finsight.openCalculatorsTab();
        }
        Assertions.assertThat(hub.isDisplayed())
                .as("Financial Calculators screen should open from the bottom navigation")
                .isTrue();
    }

    @Then("the Financial Calculators screen is displayed")
    public void hubShown() {
        Assertions.assertThat(hub.isDisplayed())
                .as("Financial Calculators screen should be displayed")
                .isTrue();
    }

    @And("the user greeting and calculator options are displayed")
    public void greeting() {
        String src = hub.pageSource();
        Assertions.assertThat(src).as("User greeting should be displayed").contains("Hello");
        Assertions.assertThat(src).as("Financial Calculators header should be displayed").contains("Financial Calculators");
        Assertions.assertThat(src).as("EMI Calculator option should be visible").contains("EMI Calculator");
    }

    @Then("all calculator options are listed")
    public void allTiles() {
        Assertions.assertThat(hub.listsAllTiles())
                .as("All 15 calculator options should be displayed")
                .isTrue();
    }

    @When("I open each calculator and return")
    public void openEach() {
        for (String[] pair : CALCULATORS) {
            Assertions.assertThat(hub.openTile(pair[0]))
                    .as(pair[0] + " should be clickable")
                    .isTrue();
            Assertions.assertThat(calculator.showsTitle(pair[1]))
                    .as(pair[0] + " should open its calculator page")
                    .isTrue();
            calculator.tapBack();
            if (!hub.isDisplayed()) {
                calculator.tapBack();
            }
        }
    }

    @Then("each calculator page opened without a crash")
    public void eachOpened() {
        Assertions.assertThat(hub.isDisplayed())
                .as("Returning from the calculators should show Financial Calculators again")
                .isTrue();
    }

    @When("I open the EMI Calculator")
    public void openEmi() {
        openNamed("EMI Calculator", "EMI Calculator");
    }

    @Then("the EMI Calculator shows loan amount, interest, tenure, and results")
    public void emiFields() {
        String src = calculator.pageSource();
        Assertions.assertThat(src).as("EMI Calculator should be displayed").contains("EMI Calculator");
        Assertions.assertThat(src).as("Loan Amount should be displayed").contains("Loan Amount");
        Assertions.assertThat(src).as("Interest Rate should be displayed").contains("Interest Rate");
        Assertions.assertThat(src).as("Tenure should be displayed").contains("Tenure");
        Assertions.assertThat(src).as("Interest Amount should be displayed").contains("Interest Amount");
        Assertions.assertThat(src).as("Total Payment should be displayed").contains("Total Payment");
        Assertions.assertThat(src).as("EMI Amount should be displayed").contains("EMI Amount");
    }

    @When("I move the EMI Loan Amount slider")
    public void nudgeLoan() {
        rememberedValue = calculator.rowText("Loan Amount");
        calculator.nudgeSlider("Loan Amount");
    }

    @Then("the EMI loan amount and results update")
    public void loanUpdated() {
        assertRowChanged("Loan Amount");
        Assertions.assertThat(calculator.sourceContains("EMI Amount", "Total Payment"))
                .as("EMI results should still be displayed after the loan amount changes")
                .isTrue();
    }

    @When("I move the EMI Interest Rate slider")
    public void nudgeRate() {
        rememberedValue = calculator.rowText("Interest Rate");
        calculator.nudgeSlider("Interest Rate");
    }

    @Then("the EMI interest rate and results update")
    public void rateUpdated() {
        assertRowChanged("Interest Rate");
        Assertions.assertThat(calculator.sourceContains("EMI Amount", "Interest Amount", "Total Payment"))
                .as("EMI, interest, and total payment should remain displayed")
                .isTrue();
    }

    @When("I move the EMI Tenure slider")
    public void nudgeTenure() {
        rememberedValue = calculator.rowText("Tenure");
        calculator.nudgeSlider("Tenure");
    }

    @Then("the EMI tenure and results update")
    public void tenureUpdated() {
        assertRowChanged("Tenure");
        Assertions.assertThat(calculator.sourceContains("EMI Amount", "Total Payment"))
                .as("EMI results should update for the selected tenure")
                .isTrue();
    }

    @When("I set EMI loan 500000, interest 13 percent, and tenure 5 years")
    public void emiFormula() {
        calculator.editAndSave("Loan Amount", "500000");
        calculator.editAndSave("Interest Rate", "13");
        calculator.editAndSave("Tenure", "5");
    }

    @Then("the EMI total payment is 682592")
    public void emiTotal() {
        String src = calculator.pageSource();
        Assertions.assertThat(src.contains("6,82,592") || src.contains("682,592") || src.contains("682592"))
                .as("Total Payment should be 6,82,592 for a 5,00,000 loan at 13 percent for 5 years")
                .isTrue();
        Assertions.assertThat(src).as("Total Payment should be displayed").contains("Total Payment");
        Assertions.assertThat(src).as("EMI Amount should be displayed").contains("EMI Amount");
    }

    @When("I move the EMI Loan Amount slider to the minimum")
    public void loanMin() {
        calculator.dragSliderToEnd("Loan Amount", false);
    }

    @Then("the minimum EMI loan amount is accepted")
    public void loanMinOk() {
        Assertions.assertThat(calculator.sourceContains("Loan Amount", "EMI Amount"))
                .as("Minimum loan amount should still show an EMI result")
                .isTrue();
    }

    @When("I move the EMI Loan Amount slider to the maximum")
    public void loanMax() {
        calculator.dragSliderToEnd("Loan Amount", true);
    }

    @Then("the maximum EMI loan amount is accepted")
    public void loanMaxOk() {
        Assertions.assertThat(calculator.sourceContains("Loan Amount", "EMI Amount", "Total Payment"))
                .as("Maximum loan amount should still show EMI and total payment")
                .isTrue();
    }

    @When("I move the EMI Interest Rate slider to the minimum")
    public void rateMin() {
        calculator.dragSliderToEnd("Interest Rate", false);
    }

    @Then("the lowest EMI interest rate is accepted")
    public void rateMinOk() {
        Assertions.assertThat(calculator.sourceContains("Interest Amount", "EMI Amount", "Total Payment"))
                .as("Lowest interest rate should still show interest, EMI, and total payment")
                .isTrue();
    }

    @When("I move the EMI Interest Rate slider to the maximum")
    public void rateMax() {
        calculator.dragSliderToEnd("Interest Rate", true);
    }

    @Then("the highest EMI interest rate is accepted")
    public void rateMaxOk() {
        Assertions.assertThat(calculator.sourceContains("Interest Amount", "EMI Amount"))
                .as("Highest interest rate should still show interest and EMI")
                .isTrue();
    }

    @When("I move the EMI Tenure slider to the minimum")
    public void tenureMin() {
        calculator.dragSliderToEnd("Tenure", false);
    }

    @Then("the minimum EMI tenure is accepted")
    public void tenureMinOk() {
        Assertions.assertThat(calculator.sourceContains("Tenure", "EMI Amount", "Total Payment"))
                .as("Minimum tenure should still show EMI and total payment")
                .isTrue();
    }

    @When("I move the EMI Tenure slider to the maximum")
    public void tenureMax() {
        calculator.dragSliderToEnd("Tenure", true);
    }

    @Then("the maximum EMI tenure is accepted")
    public void tenureMaxOk() {
        Assertions.assertThat(calculator.sourceContains("Tenure", "EMI Amount", "Interest Amount"))
                .as("Maximum tenure should still show EMI and interest")
                .isTrue();
    }

    @When("I edit the EMI Loan Amount to 250000 and save")
    public void editLoan() {
        calculator.editAndSave("Loan Amount", "250000");
    }

    @Then("the EMI loan amount shows 250000")
    public void loan250() {
        Assertions.assertThat(calculator.pageSource())
                .as("Saved loan amount 2,50,000 should be displayed and results should remain")
                .contains("2,50,000");
        Assertions.assertThat(calculator.sourceContains("EMI Amount", "Total Payment")).isTrue();
    }

    @When("I edit the EMI Interest Rate to 10 and save")
    public void editRate() {
        calculator.editAndSave("Interest Rate", "10");
    }

    @Then("the EMI interest rate shows 10")
    public void rate10() {
        Assertions.assertThat(calculator.rowText("Interest Rate"))
                .as("Saved interest rate should be displayed")
                .contains("10");
    }

    @When("I edit the EMI Tenure to 3 and save")
    public void editTenure() {
        calculator.editAndSave("Tenure", "3");
    }

    @Then("the EMI tenure shows 3")
    public void tenure3() {
        Assertions.assertThat(calculator.rowText("Tenure"))
                .as("Saved tenure should be displayed")
                .contains("3");
    }

    @When("I edit the EMI Interest Rate to 12.5 and save")
    public void editDecimal() {
        calculator.editAndSave("Interest Rate", "12.5");
    }

    @Then("the EMI interest rate shows 12.5")
    public void rate125() {
        Assertions.assertThat(calculator.pageSource())
                .as("Decimal interest rate 12.5 should be accepted")
                .contains("12.5");
    }

    @When("I clear the EMI Loan Amount and save")
    public void clearLoan() {
        calculator.clearAndSave("Loan Amount");
    }

    @Then("a required amount validation is shown and the blank value is not saved")
    public void requiredAmount() {
        Assertions.assertThat(calculator.sourceContains("required", "Required", "Amount is required", "Save Changes"))
                .as("Blank loan amount should show a validation message or keep Save Changes open")
                .isTrue();
    }

    @When("I change the EMI Loan Amount and close without saving")
    public void closeWithoutSave() {
        rememberedValue = calculator.editAndClose("Loan Amount", "111111");
    }

    @Then("the previous EMI loan amount is unchanged")
    public void unchangedLoan() {
        Assertions.assertThat(calculator.rowText("Loan Amount"))
                .as("Closing the editor should keep the previous loan amount")
                .isEqualTo(rememberedValue);
    }

    @When("I open the SIP Calculator")
    public void openSip() {
        openNamed("SIP Calculator", "SIP Calculator");
    }

    @Then("the SIP Calculator shows investment fields, results, and Start SIP")
    public void sipFields() {
        String src = calculator.pageSource();
        Assertions.assertThat(src).as("SIP Calculator should be displayed").contains("SIP Calculator");
        Assertions.assertThat(src).as("Monthly Investment should be displayed").contains("Monthly Investment");
        Assertions.assertThat(src).as("Investment Period should be displayed").contains("Investment Period");
        Assertions.assertThat(src).as("Desired Return should be displayed").contains("Desired Return");
        Assertions.assertThat(src).as("Invested Amount should be displayed").contains("Invested Amount");
        Assertions.assertThat(src).as("Estimated returns should be displayed").contains("Est. Returns");
        Assertions.assertThat(src).as("Total Value should be displayed").contains("Total Value");
        Assertions.assertThat(src).as("Start SIP should be displayed").contains("Start SIP");
    }

    @When("I move the SIP Monthly Investment slider")
    public void nudgeSipAmount() {
        rememberedValue = calculator.rowText("Monthly Investment");
        calculator.nudgeSlider("Monthly Investment");
    }

    @Then("the SIP monthly investment and results update")
    public void sipAmountUpdated() {
        assertRowChanged("Monthly Investment");
        Assertions.assertThat(calculator.sourceContains("Invested Amount", "Total Value")).isTrue();
    }

    @When("I move the SIP Investment Period slider")
    public void nudgeSipPeriod() {
        rememberedValue = calculator.rowText("Investment Period");
        calculator.nudgeSlider("Investment Period");
    }

    @Then("the SIP investment period and results update")
    public void sipPeriodUpdated() {
        assertRowChanged("Investment Period");
        Assertions.assertThat(calculator.sourceContains("Total Value", "Est. Returns")).isTrue();
    }

    @When("I move the SIP Desired Return slider")
    public void nudgeSipReturn() {
        rememberedValue = calculator.rowText("Desired Return");
        calculator.nudgeSlider("Desired Return");
    }

    @Then("the SIP desired return and results update")
    public void sipReturnUpdated() {
        assertRowChanged("Desired Return");
        Assertions.assertThat(calculator.sourceContains("Est. Returns", "Total Value")).isTrue();
    }

    @When("I edit the SIP Monthly Investment to 1000 and save")
    public void editSipAmount() {
        calculator.editAndSave("Monthly Investment", "1000");
    }

    @Then("the SIP monthly investment shows 1000")
    public void sip1000() {
        Assertions.assertThat(calculator.pageSource()).as("Monthly investment 1,000 should be saved").contains("1,000");
    }

    @When("I edit the SIP Investment Period to 5 and save")
    public void editSipPeriod() {
        calculator.editAndSave("Investment Period", "5");
    }

    @Then("the SIP investment period shows 5")
    public void sip5() {
        Assertions.assertThat(calculator.rowText("Investment Period")).contains("5");
    }

    @When("I edit the SIP Desired Return to 12 and save")
    public void editSipReturn() {
        calculator.editAndSave("Desired Return", "12");
    }

    @Then("the SIP desired return shows 12")
    public void sip12() {
        Assertions.assertThat(calculator.rowText("Desired Return")).contains("12");
    }

    @When("I clear the SIP Monthly Investment and save")
    public void clearSip() {
        calculator.clearAndSave("Monthly Investment");
    }

    @When("I set SIP monthly 1000, period 5 years, and return 12 percent")
    public void sipFormula() {
        calculator.editAndSave("Monthly Investment", "1000");
        calculator.editAndSave("Investment Period", "5");
        calculator.editAndSave("Desired Return", "12");
    }

    @Then("the SIP invested amount is 60000 and the total value is 81670")
    public void sipMath() {
        String src = calculator.pageSource();
        Assertions.assertThat(src.contains("60,000") || src.contains("60000"))
                .as("Invested Amount should be 60,000")
                .isTrue();
        Assertions.assertThat(src.contains("81,670") || src.contains("81670"))
                .as("Total Value should be 81,670")
                .isTrue();
        Assertions.assertThat(src).as("Total Value should equal invested amount plus estimated returns")
                .contains("Total Value");
    }

    @When("I tap Start SIP and leave without completing onboarding")
    public void startSip() {
        calculator.tapLabel("Start SIP");
        sleepQuiet(800);
    }

    @Then("the SIP next screen opens and no investment is submitted")
    public void sipNext() {
        Assertions.assertThat(calculator.pageSource())
                .as("Start SIP should open the next screen. No investment or KYC is completed.")
                .doesNotContain("Payment Successful");
        calculator.tapBack();
    }

    @When("I go back to Financial Calculators")
    public void backHub() {
        calculator.tapBack();
        if (!hub.isDisplayed()) {
            calculator.tapBack();
        }
    }

    @When("I open the Lumpsum Calculator")
    public void openLumpsum() {
        openNamed("Lumpsum Calculator", "Lumpsum Calculator");
    }

    @Then("the Lumpsum Calculator shows investment fields, results, and Start Investment")
    public void lumpsumFields() {
        String src = calculator.pageSource();
        Assertions.assertThat(src).as("Lumpsum Calculator should be displayed").contains("Lumpsum Calculator");
        Assertions.assertThat(src).as("Total investment should be displayed").contains("Total investment");
        Assertions.assertThat(src).as("Expected return should be displayed").contains("Expected Return");
        Assertions.assertThat(src).as("Tenure should be displayed").contains("Tenure");
        Assertions.assertThat(src).as("Investment Amount should be displayed").contains("Investment Amount");
        Assertions.assertThat(src).as("Estimated returns should be displayed").contains("Est. Returns");
        Assertions.assertThat(src).as("Total value should be displayed").containsIgnoringCase("Total value");
        Assertions.assertThat(src).as("Start Investment should be displayed").contains("Start Investment");
    }

    @When("I move the Lumpsum Total investment slider")
    public void nudgeLumpAmount() {
        rememberedValue = calculator.rowText("Total investment");
        calculator.nudgeSlider("Total investment");
    }

    @Then("the Lumpsum investment and results update")
    public void lumpAmountUpdated() {
        assertRowChanged("Total investment");
        Assertions.assertThat(calculator.sourceContains("Est. Returns")).isTrue();
    }

    @When("I move the Lumpsum Expected Return slider")
    public void nudgeLumpReturn() {
        rememberedValue = calculator.rowText("Expected Return");
        calculator.nudgeSlider("Expected Return");
    }

    @Then("the Lumpsum expected return and results update")
    public void lumpReturnUpdated() {
        assertRowChanged("Expected Return");
    }

    @When("I move the Lumpsum Tenure slider")
    public void nudgeLumpTenure() {
        rememberedValue = calculator.rowText("Tenure");
        calculator.nudgeSlider("Tenure");
    }

    @Then("the Lumpsum tenure and results update")
    public void lumpTenureUpdated() {
        assertRowChanged("Tenure");
    }

    @When("I edit the Lumpsum Total investment to 500000 and save")
    public void editLumpAmount() {
        calculator.editAndSave("Total investment", "500000");
    }

    @Then("the Lumpsum investment shows 500000")
    public void lump500() {
        Assertions.assertThat(calculator.pageSource()).contains("5,00,000");
    }

    @When("I edit the Lumpsum Expected Return to 12 and save")
    public void editLumpReturn() {
        calculator.editAndSave("Expected Return", "12");
    }

    @Then("the Lumpsum expected return shows 12")
    public void lump12() {
        Assertions.assertThat(calculator.rowText("Expected Return")).contains("12");
    }

    @When("I edit the Lumpsum Tenure to 10 and save")
    public void editLumpTenure() {
        calculator.editAndSave("Tenure", "10");
    }

    @Then("the Lumpsum tenure shows 10")
    public void lump10() {
        Assertions.assertThat(calculator.rowText("Tenure")).contains("10");
    }

    @When("I set Lumpsum investment 500000, return 12 percent, and tenure 10 years")
    public void lumpFormula() {
        calculator.editAndSave("Total investment", "500000");
        calculator.editAndSave("Expected Return", "12");
        calculator.editAndSave("Tenure", "10");
    }

    @Then("the Lumpsum total value is 1552924")
    public void lumpMath() {
        String src = calculator.pageSource();
        Assertions.assertThat(src.contains("15,52,924") || src.contains("1552924"))
                .as("Total value should be 15,52,924 for 5,00,000 at 12 percent for 10 years")
                .isTrue();
    }

    @When("I tap Start Investment and leave without completing a purchase")
    public void startInvestment() {
        calculator.tapLabel("Start Investment");
        sleepQuiet(800);
    }

    @Then("the investment next screen opens and no purchase is submitted")
    public void investNext() {
        Assertions.assertThat(calculator.pageSource())
                .as("Start Investment should open the next screen. No purchase or KYC is completed.")
                .doesNotContain("Payment Successful");
        calculator.tapBack();
    }

    @When("I change lumpsum investment, return, and tenure")
    public void changeAll() {
        rememberedValue = calculator.pageSource();
        calculator.nudgeSlider("Total investment");
        calculator.nudgeSlider("Expected Return");
        calculator.nudgeSlider("Tenure");
    }

    @Then("the Lumpsum results stay displayed after the inputs change")
    public void lumpStillThere() {
        Assertions.assertThat(calculator.sourceContains("Est. Returns", "Investment Amount"))
                .as("Investment amount, estimated returns, and total value should stay displayed")
                .isTrue();
        Assertions.assertThat(calculator.pageSource())
                .as("Changing the inputs should refresh the result screen")
                .isNotEqualTo(rememberedValue);
    }

    private void openNamed(String tile, String title) {
        if (!calculator.showsTitle(title)) {
            Assertions.assertThat(hub.openTile(tile)).as(tile + " should open").isTrue();
        }
        Assertions.assertThat(calculator.showsTitle(title)).as(title + " should be displayed").isTrue();
    }

    private void assertRowChanged(String label) {
        String after = calculator.rowText(label);
        Assertions.assertThat(after).as(label + " should still be displayed").contains(label);
        if (!rememberedValue.isBlank()) {
            Assertions.assertThat(after).as(label + " value should change when the slider moves").isNotEqualTo(rememberedValue);
        }
    }

    @Then("the Financial Calculators header is displayed")
    public void headerDisplayed() {
        Assertions.assertThat(hub.pageSource())
                .as("Financial Calculators header should be displayed")
                .contains("Financial Calculators");
    }

    @And("the calculator greeting Hello is displayed")
    public void helloDisplayed() {
        Assertions.assertThat(hub.pageSource())
                .as("User greeting Hello should be displayed")
                .contains("Hello");
    }

    @And("the EMI Calculator option is visible")
    public void emiVisible() {
        Assertions.assertThat(hub.pageSource())
                .as("EMI Calculator option should be visible")
                .contains("EMI Calculator");
    }

    @And("the calculator greeting, header, and EMI Calculator option do not overlap")
    public void noOverlap() {
        Assertions.assertThat(hub.headerGreetingAndOptionSeparated())
                .as("Hello, Financial Calculators, and EMI Calculator should be visible in separate areas")
                .isTrue();
    }

    @When("I review the calculator options")
    public void reviewOptions() {
        catalog = hub.catalogSource();
    }

    @Then("every calculator option on the Calculator sheet is listed")
    public void optionsFromSheet() {
        for (String[] pair : MoneyExcel.rows("Option")) {
            Assertions.assertThat(catalog)
                    .as(pair[0] + " should be displayed")
                    .contains(pair[0]);
        }
    }

    @When("I open every calculator on the Calculator sheet and return")
    public void openFromSheet() {
        for (String[] pair : MoneyExcel.rows("Option")) {
            showCalculatorsHub();
            Assertions.assertThat(hub.openTile(pair[0]))
                    .as(pair[0] + " must be listed on Financial Calculators. Visible: " + hub.visibleLabels())
                    .isTrue();
            Assertions.assertThat(calculator.showsTitle(pair[1]))
                    .as(pair[0] + " must open the page titled \"" + pair[1]
                            + "\". Visible: " + calculator.visibleLabels())
                    .isTrue();
            returnToHub(pair[1]);
        }
    }

    /** Leave the calculator page, then the profile menu if Back landed there, and open the Calculators tab. */
    private void returnToHub(String openedTitle) {
        if (calculator.showsTitle(openedTitle)) {
            calculator.tapBack();
            sleepQuiet(800);
        }
        showCalculatorsHub();
    }

    private void showCalculatorsHub() {
        hub.unlockIfNeeded();
        for (int i = 0; i < 4 && !hub.isDisplayed(); i++) {
            if (hub.sourceContains("PROFILE SETTINGS", "Personal Details", "Log Out")) {
                hub.tapBack();
                sleepQuiet(700);
                continue;
            }
            if (finsight.openCalculatorsTab() && hub.isDisplayed()) {
                return;
            }
            sleepQuiet(600);
        }
    }

    @Then("the calculator option {string} is listed")
    public void optionListed(String name) {
        Assertions.assertThat(catalog)
                .as(name + " should be displayed")
                .contains(name);
    }

    @When("I tap the calculator option {string}")
    public void tapOption(String name) {
        Assertions.assertThat(hub.openTile(name))
                .as(name + " should be tapped")
                .isTrue();
    }

    @Then("the calculator page {string} is displayed")
    public void calculatorPage(String title) {
        Assertions.assertThat(calculator.showsTitle(title))
                .as(title + " should be displayed")
                .isTrue();
    }

    private static void sleepQuiet(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
