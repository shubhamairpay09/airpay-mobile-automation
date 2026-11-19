package com.airpay.money.steps;

import com.airpay.money.data.MoneyExcel;
import com.airpay.money.hooks.FhcErrorLog;
import com.airpay.money.hooks.FhcSystemError;
import com.airpay.money.screens.finsight.FhcFlow;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.time.Duration;

/**
 * Financial Health Check sheet. Each expected result is checked on its own.
 * KYC is not started. Diagnosis share actions are not tapped.
 */
public class FhcSheetSteps {

    private final FhcFlow fhc;
    private String rememberedScore = "";
    private String rememberedCategory = "";

    public FhcSheetSteps(TestContext context) {
        this.fhc = new FhcFlow(context);
    }

    @When("I open the Financial Health Check card")
    public void openCard() {
        fhc.openFinsight();
        stopIfSystemError();
    }

    @And("FHC is not blocked by a system error")
    public void notBlockedBySystemError() {
        if (FhcSystemError.tripped()) {
            Assertions.fail("FHC stopped after a system error. Raise this: " + FhcSystemError.message());
        }
    }

    @Then("the Financial Health Check entry button is displayed")
    public void entryButton() {
        Assertions.assertThat(fhc.entryButtonDisplayed())
                .as("Financial Health Check should show Continue, Start Now, Let's start, View Details, or Reassess")
                .isTrue();
    }

    @When("I tap the Financial Health Check entry button")
    public void tapEntry() {
        fhc.openPersonalDetails();
    }

    @When("I open the Personal Details page")
    public void openPersonal() {
        fhc.dismissOverlays();
        fhc.openPersonalDetails();
        fhc.dismissOverlays();
        waitPersonalFields();
        Assertions.assertThat(fhc.onPersonal())
                .as("Personal Details must show the title, Full Name, and Pincode. Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    @When("I verify the Personal Details fields")
    public void verifyPersonalFields() {
        Assertions.assertThat(fhc.onPersonal())
                .as("Personal Details must be open before filling. Visible: " + fhc.visibleLabels())
                .isTrue();
        fhc.dismissOverlays();
        fhc.fillPersonalDetails();
        fhc.dismissOverlays();
        Assertions.assertThat(fhc.onPersonal())
                .as("Personal Details should remain open after field fill. Visible: " + fhc.visibleLabels())
                .isTrue();
        Assertions.assertThat(fhc.reveal("Full Name") || fhc.namePrefilled())
                .as("Full Name should be displayed. Visible: " + fhc.visibleLabels())
                .isTrue();
        Assertions.assertThat(fhc.reveal("Pincode")).as("Pincode should be displayed").isTrue();
        stopIfSystemError();
    }

    @When("I tap Next on Personal Details")
    public void nextPersonal() {
        if (!fhc.onPersonal()) {
            return;
        }
        fhc.dismissOverlays();
        for (int i = 0; i < 2 && fhc.onPersonal(); i++) {
            fhc.tapNext();
            fhc.dismissOverlays();
        }
        fhc.waitUntil(() -> {
            fhc.dismissOverlays();
            return fhc.onIncome();
        }, Duration.ofSeconds(10));
    }

    @Then("the Personal Details page is displayed")
    public void personalDisplayed() {
        waitPersonalFields();
        Assertions.assertThat(fhc.onPersonal())
                .as("Personal Details must show the title, Full Name, and Pincode. Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    private void waitPersonalFields() {
        fhc.waitUntil(fhc::onPersonal, Duration.ofSeconds(3));
    }

    @And("the Personal Details page has no error")
    public void personalNoError() {
        noError("Personal Details");
    }

    @And("the Full Name field is displayed")
    public void fullName() {
        shows("Full Name", "Full Name field");
    }

    @And("the Date of Birth field is displayed")
    public void dob() {
        shows("Date of Birth", "Date of Birth field");
    }

    @And("the Gender field is displayed")
    public void gender() {
        Assertions.assertThat(fhc.shows("Male") && fhc.shows("Female") && fhc.shows("Other"))
                .as("Gender options Male, Female, and Other should be displayed")
                .isTrue();
    }

    @And("the Dependant field is displayed")
    public void dependant() {
        shows("Dependant", "Dependant field");
    }

    @And("the Pincode field is displayed")
    public void pincode() {
        shows("Pincode", "Pincode field");
    }

    @And("the City field is displayed")
    public void city() {
        shows("City", "City field");
    }

    @And("the State field is displayed")
    public void state() {
        shows("State", "State field");
    }

    @And("Next is visible and enabled on Personal Details")
    public void nextEnabledPersonal() {
        Assertions.assertThat(fhc.shows("Next")).as("Next should be visible").isTrue();
        Assertions.assertThat(fhc.nextEnabled()).as("Next should be enabled").isTrue();
    }

    @And("the Full Name field is prefilled")
    public void namePrefilled() {
        Assertions.assertThat(fhc.namePrefilled())
                .as("Full Name should already contain the logged-in user name")
                .isTrue();
    }

    @Then("the Income and Expense page is displayed")
    public void incomeDisplayed() {
        fhc.dismissOverlays();
        requirePage(fhc::onIncome, "Income & Expense must show the title and Net Monthly Income or My Loans", 10);
    }

    @And("the Income and Expense page has no error")
    public void incomeNoError() {
        noError("Income & Expense");
    }

    @When("I open the Income and Expense page")
    public void openIncome() {
        fhc.openIncome();
        requirePage(fhc::onIncome, "Income & Expense must show the title and Net Monthly Income or My Loans");
    }

    @When("I enter the net monthly income from the FHC sheet")
    public void enterIncome() {
        fhc.ensureIncomeAmounts();
        Assertions.assertThat(fhc.fieldDigits("Income"))
                .as("Net monthly income must equal the FHC sheet Income value " + FhcFlow.income()
                        + ". Visible: " + fhc.visibleLabels())
                .isEqualTo(FhcFlow.income());
    }

    @When("I enter the monthly household expenses from the FHC sheet")
    public void enterExpense() {
        fhc.ensureIncomeAmounts();
        Assertions.assertThat(fhc.fieldDigits("Household"))
                .as("Household expenses must equal the FHC sheet Expenses value " + FhcFlow.expense()
                        + ". Visible: " + fhc.visibleLabels())
                .isEqualTo(FhcFlow.expense());
    }

    @When("I select No for the running loan question")
    public void loanNo() {
        fhc.selectLoanChoice("No");
    }

    @When("I select Yes for the running loan question")
    public void loanYes() {
        fhc.selectLoanChoice("Yes");
    }

    @Then("No is selected for the running loan question")
    public void noSelected() {
        Assertions.assertThat(fhc.runningLoanNoSelected())
                .as("No should be selected for the running loan and Add Loan hidden. Visible: "
                        + fhc.visibleLabels())
                .isTrue();
    }

    @And("Yes is not selected for the running loan question")
    public void yesNotSelected() {
        Assertions.assertThat(fhc.choiceChecked("running Loan", "Yes"))
                .as("Yes should not be selected for the running loan question")
                .isFalse();
    }

    @When("I tap Next on Income and Expense")
    public void nextIncome() {
        fhc.tapNextOnIncomeExpense();
    }

    @Then("the Insurance Details page is displayed")
    public void insuranceDisplayed() {
        fhc.dismissOverlays();
        requirePage(fhc::onInsurance, "Insurance Details must show the title and Do you have any Insurance", 10);
    }

    @And("the Insurance Details page has no error")
    public void insuranceNoError() {
        noError("Insurance Details");
    }

    @When("I tap Back on Income and Expense")
    public void backIncome() {
        fhc.goBack();
    }

    @And("the FHC sheet pincode is still shown on Personal Details")
    public void pincodeRetained() {
        shows(FhcFlow.pincode(), "FHC sheet Pincode " + FhcFlow.pincode());
    }

    @Then("the Add Loan button is displayed")
    public void addLoanButton() {
        Assertions.assertThat(fhc.addLoanActionVisible())
                .as("Add Loan button must show when Yes is selected. Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    @And("the Add Loan button is enabled")
    public void addLoanEnabled() {
        Assertions.assertThat(fhc.addLoanActionVisible())
                .as("Add Loan should be enabled. Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    @When("I tap Add Loan")
    public void tapAddLoan() {
        fhc.tapAddLoanOnIncome();
    }

    @Then("the Add Loan Details page is displayed")
    public void addLoanPage() {
        requirePage(fhc::onAddLoan, "Add Loan Details must show Loan Type and Principal");
    }

    @And("the Add Loan Details page has no error")
    public void addLoanNoError() {
        noError("Add Loan Details");
    }

    @When("I open the Add Loan Details page")
    public void openAddLoan() {
        fhc.openAddLoan();
        requirePage(fhc::onAddLoan, "Add Loan Details must show Loan Type and Principal");
    }

    @Then("the Loan Type dropdown is displayed")
    public void loanType() {
        shows("Loan Type", "Loan Type dropdown");
    }

    @And("the Principal Amount field is displayed")
    public void principalField() {
        shows("Principal", "Principal Amount field");
    }

    @And("the EMI field is displayed")
    public void emiField() {
        shows("EMI", "EMI field");
    }

    @And("the Interest Rate field is displayed")
    public void rateField() {
        shows("Interest", "Interest Rate field");
    }

    @And("the Add button is displayed")
    public void addButton() {
        shows("Add", "Add button");
    }

    @And("the Back button is displayed")
    public void backButton() {
        Assertions.assertThat(fhc.headerBackDisplayed())
                .as("Back button should be displayed")
                .isTrue();
    }

    @When("I select the Education Loan type")
    public void selectEducation() {
        fhc.selectLoanType(FhcFlow.loanType());
    }

    @When("I enter principal amount 500000")
    public void enterPrincipal() {
        fhc.enterPrincipal();
    }

    @When("I enter EMI 10000")
    public void enterEmi() {
        fhc.enterEmi();
    }

    @When("I enter interest rate 12")
    public void enterRate() {
        fhc.enterRate();
    }

    @When("I tap Add on the loan form")
    public void tapAddLoanForm() {
        fhc.tapAdd();
    }

    @Then("the Income and Expense page is displayed after the loan is added")
    public void incomeAfterLoan() {
        fhc.dismissOverlays();
        Assertions.assertThat(fhc.waitForIncomeAfterLoan())
                .as("Income & Expense page should be displayed after Add. Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    @And("the added loan shows Education Loan")
    public void loanTypeShown() {
        shows(FhcFlow.loanType(), FhcFlow.loanType());
    }

    @When("I tap Back on Add Loan Details")
    public void backAddLoan() {
        fhc.goBack();
    }

    @And("the FHC sheet income is still shown on Income and Expense")
    public void incomeRetained() {
        shows(FhcFlow.income(), "FHC sheet Income " + FhcFlow.income());
    }

    @When("I open the Loan Type dropdown")
    public void openLoanTypes() {
        fhc.openLoanTypePicker();
    }

    @When("I search loan types for {string}")
    public void searchLoan(String word) {
        fhc.openLoanTypePicker();
    }

    @Then("Home Loan is shown in the loan type list")
    public void homeLoanShown() {
        shows("Home Loan", "Home Loan");
    }

    @And("Two Wheeler Loan is not shown in the loan type list")
    public void twoWheelerHidden() {
        hidden("Two Wheeler Loan");
    }

    @Then("Personal Loan is shown in the loan type list")
    public void personalLoanShown() {
        shows("Personal Loan", "Personal Loan");
    }

    @And("Home Loan is not shown in the loan type list")
    public void homeLoanHidden() {
        hidden("Home Loan");
    }

    @Then("Gold Loan is shown in the loan type list")
    public void goldLoanShown() {
        shows("Gold Loan", "Gold Loan");
    }

    @And("Personal Loan is not shown in the loan type list")
    public void personalLoanHidden() {
        hidden("Personal Loan");
    }

    @Then("the My Loans section shows the loan card")
    public void myLoans() {
        boolean card = fhc.waitUntil(
                () -> fhc.showsLoanCard(FhcFlow.loanType()), Duration.ofSeconds(8));
        Assertions.assertThat(card)
                .as("The added loan card should show " + FhcFlow.loanType()
                        + ". Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    @And("the loan card shows the loan type Education Loan")
    public void cardType() {
        shows(FhcFlow.loanType(), "Loan type on the card");
    }

    @And("the loan card shows principal 500000")
    public void cardPrincipal() {
        Assertions.assertThat(showsAmount(FhcFlow.principal()))
                .as("The loan card should show principal " + FhcFlow.principal())
                .isTrue();
    }

    @And("the loan card shows EMI 10000")
    public void cardEmi() {
        Assertions.assertThat(showsAmount(FhcFlow.emi()))
                .as("The loan card should show EMI " + FhcFlow.emi())
                .isTrue();
    }

    @And("the loan card shows interest rate 12")
    public void cardRate() {
        Assertions.assertThat(fhc.shows(FhcFlow.rate()))
                .as("The loan card should show interest rate " + FhcFlow.rate())
                .isTrue();
    }

    @When("I add the Education Loan on Income and Expense")
    public void addEducationLoan() {
        fhc.addLoan(FhcFlow.LOAN_EDUCATION);
        stopIfSystemError();
    }

    @When("I delete the added loan")
    public void deleteLoan() {
        fhc.deleteAddedLoan();
        stopIfSystemError();
    }

    @Then("the loan is no longer shown under My Loans")
    public void loanGone() {
        Assertions.assertThat(fhc.loanCardGone())
                .as("Deleted loan should not remain under My Loans. Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    @When("I delete the added investment")
    public void deleteInvestment() {
        fhc.deleteAddedInvestment();
        stopIfSystemError();
    }

    @Then("the investment is no longer shown under My Investments")
    public void investmentGone() {
        Assertions.assertThat(fhc.investmentCardGone())
                .as("Deleted investment should not remain under My Investments. Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    @When("I open the Insurance Details page")
    public void openInsurance() {
        fhc.openInsurance();
        fhc.dismissOverlays();
        requirePage(fhc::onInsurance, "Insurance Details must show the title and Do you have any Insurance", 12);
    }

    @When("I select Yes for insurance")
    public void insuranceYes() {
        fhc.selectInsuranceChoice("Yes");
    }

    @When("I select No for insurance")
    public void insuranceNo() {
        fhc.selectInsuranceChoice("No");
    }

    @Then("Yes is selected for insurance")
    public void insuranceYesSelected() {
        shows("Health Insurance", "Health Insurance after Yes");
    }

    @And("the Health Insurance section is displayed")
    public void healthSection() {
        shows("Health Insurance", "Health Insurance section");
    }

    @And("the Term Insurance section is displayed")
    public void termSection() {
        shows("Term Insurance", "Term Insurance section");
    }

    @When("I enable I don't have Health Insurance")
    public void skipHealth() {
        fhc.enableSkipToggle("Health");
    }

    @When("I enable I don't have Term Insurance")
    public void skipTerm() {
        fhc.enableSkipToggle("Term");
    }

    @Then("the Health Insurance skip toggle stays selected")
    public void healthToggle() {
        Assertions.assertThat(fhc.toggleChecked("Health"))
                .as("I don't have Health Insurance should stay selected")
                .isTrue();
    }

    @And("the Health Insurance amount fields are not shown")
    public void healthFieldsHidden() {
        Assertions.assertThat(fhc.fewerAmountFields())
                .as("Health Insurance amount fields should be hidden")
                .isTrue();
    }

    @Then("the Term Insurance skip toggle stays selected")
    public void termToggle() {
        Assertions.assertThat(fhc.toggleChecked("Term"))
                .as("I don't have Term Insurance should stay selected")
                .isTrue();
    }

    @And("the Term Insurance amount fields are not shown")
    public void termFieldsHidden() {
        Assertions.assertThat(fhc.fewerAmountFields())
                .as("Term Insurance amount fields should be hidden")
                .isTrue();
    }

    @Then("No is selected for insurance")
    public void insuranceNoSelected() {
        Assertions.assertThat(fhc.shows("Health Insurance"))
                .as("No is selected, so Health Insurance should not be displayed")
                .isFalse();
    }

    @And("the Health Insurance section is not displayed")
    public void healthHidden() {
        Assertions.assertThat(fhc.shows("Health Insurance"))
                .as("Health Insurance section should not be displayed")
                .isFalse();
    }

    @And("Next is enabled on Insurance Details")
    public void nextInsurance() {
        Assertions.assertThat(fhc.nextEnabled()).as("Next should be enabled on Insurance Details").isTrue();
    }

    @When("I tap Next on Insurance Details")
    public void tapNextInsurance() {
        fhc.tapNext();
    }

    @Then("the Savings and Investments page is displayed")
    public void savingsDisplayed() {
        requirePage(fhc::onSavings, "Savings & Investments must show Savings and Investment");
    }

    @And("the Insurance Details page is no longer displayed")
    public void insuranceGone() {
        Assertions.assertThat(fhc.onInsurance())
                .as("Insurance Details should be closed after Next")
                .isFalse();
    }

    @And("the Savings and Investments page has no error")
    public void savingsNoError() {
        noError("Savings & Investments");
    }

    @When("I open the Savings and Investments page")
    public void openSavings() {
        fhc.openSavings();
        requirePage(fhc::onSavings, "Savings & Investments must show Savings and Investment");
    }

    @When("I select No for investments")
    public void investmentNo() {
        fhc.selectInvestmentChoice("No");
    }

    @Then("No is selected for investments")
    public void investmentNoSelected() {
        Assertions.assertThat(fhc.shows("Add Investment") || fhc.shows("Add New"))
                .as("No is selected, so Add Investment should not be displayed")
                .isFalse();
    }

    @And("Next is enabled on Savings and Investments")
    public void nextSavingsEnabled() {
        Assertions.assertThat(fhc.nextEnabled()).as("Next should be enabled on Savings & Investments").isTrue();
    }

    @When("I open the Add Investment Details page")
    public void openAddInvestment() {
        fhc.openAddInvestment();
        stopIfSystemError();
        fhc.storeFieldErrors("open Add Investment");
        if (!fhc.onAddInvestment()) {
            FhcErrorLog.record("open Add Investment", "ASSERT",
                    "Add Investment Details page should be displayed. Visible: " + fhc.visibleLabels());
        }
        Assertions.assertThat(fhc.onAddInvestment())
                .as("Add Investment Details page should be displayed. Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    @When("I select Public Provident Fund as the investment type")
    public void selectPpf() {
        fhc.chooseInvestmentTypePublic();
        stopIfSystemError();
        fhc.storeFieldErrors("select PPF");
        if (!fhc.investmentTypeSelected()) {
            FhcErrorLog.record("select PPF", "ASSERT",
                    "Public Provident Fund (PPF) must be selected from the dropdown. Visible: "
                            + fhc.visibleLabels());
        }
        Assertions.assertThat(fhc.investmentTypeSelected())
                .as("Public Provident Fund (PPF) must be selected from the dropdown. Visible: "
                        + fhc.visibleLabels())
                .isTrue();
    }

    @When("I enter the current value from the FHC sheet")
    public void enterCurrentValue() {
        fhc.enterCurrentValue();
        stopIfSystemError();
        fhc.storeFieldErrors("enter current value");
        if (!showsAmount(FhcFlow.currentValue())) {
            FhcErrorLog.record("enter current value", "ASSERT",
                    "Current value must show the FHC sheet CurrentValue " + FhcFlow.currentValue()
                            + ". Visible: " + fhc.visibleLabels());
        }
        Assertions.assertThat(showsAmount(FhcFlow.currentValue()))
                .as("Current value must show the FHC sheet CurrentValue " + FhcFlow.currentValue()
                        + ". Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    @When("I select No for associating a goal")
    public void goalNo() {
        fhc.selectGoal("No");
        fhc.storeFieldErrors("select goal No");
    }

    @When("I select Yes for associating a goal")
    public void goalYes() {
        fhc.selectGoal("Yes");
    }

    @When("I choose the Car goal")
    public void chooseCar() {
        fhc.chooseCarGoal();
    }

    @When("I tap Add on the investment form")
    public void tapAddInvestment() {
        fhc.tapAdd();
        stopIfSystemError();
        fhc.storeFieldErrors("tap Add investment");
    }

    @And("My Investments shows the added investment")
    public void myInvestments() {
        shows("My Investments", "My Investments");
        Assertions.assertThat(showsAmount(FhcFlow.currentValue()) || fhc.shows("Provident"))
                .as("My Investments should show the saved investment")
                .isTrue();
    }

    @When("I enter the total bank balance from the FHC sheet")
    public void bankBalance() {
        fhc.enterBankBalance();
        Assertions.assertThat(showsAmount(FhcFlow.bankBalance()))
                .as("Bank balance must show the FHC sheet BankBalance " + FhcFlow.bankBalance()
                        + ". Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    @And("I select No for creating a plan")
    public void declinePlan() {
        fhc.declinePlan();
    }

    @When("I tap Next on Savings and Investments")
    public void nextSavings() {
        fhc.declinePlan();
        fhc.tapNext();
    }

    @Then("the Risk Profile introduction page is displayed")
    public void riskIntro() {
        Assertions.assertThat(fhc.onRiskIntro()).as("Risk Profile introduction should be displayed").isTrue();
    }

    @And("the Risk Profile introduction page has no error")
    public void riskIntroNoError() {
        noError("Risk Profile introduction");
    }

    @And("the risk assessment heading is displayed")
    public void riskHeading() {
        shows("risk taking capability", "Let's assess your risk taking capability");
    }

    @And("the financial risk description is displayed")
    public void riskDescription() {
        shows("financial risk", "financial risk description");
    }

    @And("the Lets do risk profile button is displayed")
    public void letsDo() {
        shows("Lets do risk profile", "Lets do risk profile button");
    }

    @And("the I already know button is displayed")
    public void alreadyKnow() {
        shows("I already know", "I already know button");
    }

    @And("the Back button is displayed on the risk introduction")
    public void riskBack() {
        Assertions.assertThat(fhc.headerBackDisplayed())
                .as("Back should be displayed on the risk introduction")
                .isTrue();
    }

    @When("I open the Risk Profile introduction page")
    public void openRiskIntro() {
        fhc.openRiskIntro();
        Assertions.assertThat(fhc.onRiskIntro()).as("Risk Profile introduction should be displayed").isTrue();
    }

    @When("I tap Lets do risk profile")
    public void tapLetsDo() {
        fhc.openQuestion1();
    }

    @Then("Question {int} of 5 is displayed")
    public void questionDisplayed(int number) {
        Assertions.assertThat(fhc.onQuestion(Integer.toString(number)))
                .as("Question " + number + "/5 should be displayed")
                .isTrue();
    }

    @And("the question progress shows Question {int}\\/5")
    public void questionProgress(int number) {
        shows("Question " + number + "/5", "Question " + number + "/5 progress");
    }

    @And("the investment time horizon question is displayed")
    public void q1Text() {
        shows("ideal time horizon", "time horizon question");
    }

    @And("the option {string} is displayed")
    public void optionText(String option) {
        shows(option, option);
    }

    @And("Next is displayed on the question")
    public void nextOnQuestion() {
        shows("Next", "Next on the question");
    }

    @When("I open risk profile question {int}")
    public void openQuestion(int number) {
        fhc.openQuestion(number);
        Assertions.assertThat(fhc.onQuestion(Integer.toString(number)))
                .as("Question " + number + "/5 should be displayed")
                .isTrue();
    }

    @When("I select {string}")
    public void selectOption(String option) {
        fhc.answer(option);
    }

    @When("I tap Next on the risk question")
    public void nextQuestion() {
        fhc.tapNext();
        for (int i = 0; i < 10 && fhc.onQuestion("5") && !fhc.onRiskResult(); i++) {
            fhc.tapNext();
        }
    }

    @And("the portfolio income question is displayed")
    public void q2Text() {
        shows("specified investment period", "portfolio income question");
    }

    @Then("Next is enabled on the risk question")
    public void nextQuestionEnabled() {
        Assertions.assertThat(fhc.nextEnabled()).as("Next should be enabled after an answer is selected").isTrue();
    }

    @And("the investment returns question is displayed")
    public void q3Text() {
        shows("expectations with regards to your investment returns", "returns question");
    }

    @And("the maximum loss question is displayed")
    public void q4Text() {
        shows("maximum loss", "maximum loss question");
    }

    @And("the market loss response question is displayed")
    public void q5Text() {
        shows("market fluctuations", "market loss question");
    }

    @Then("the Risk Profile result page is displayed")
    public void riskResult() {
        Assertions.assertThat(fhc.waitUntil(() -> fhc.onRiskResult() || fhc.onCapability(), Duration.ofSeconds(3)))
                .as("Risk Profile result page should be displayed")
                .isTrue();
    }

    @And("the Risk Profile result page has no error")
    public void riskResultNoError() {
        noError("Risk Profile result");
    }

    @When("I complete all 5 risk profile questions")
    public void completeQuestions() {
        fhc.openRiskResult();
    }

    @And("a risk score number is displayed")
    public void riskScore() {
        Assertions.assertThat(fhc.onRiskResult()).as("The saved risk result should stay on screen").isTrue();
        Assertions.assertThat(fhc.riskScore())
                .as("The saved risk profile score should be displayed")
                .matches("\\d{1,3}\\.\\d");
    }

    @And("the risk profile gauge is displayed")
    public void riskGauge() {
        shows("Your Risk Profile", "risk profile gauge");
    }

    @And("a risk category is displayed")
    public void riskCategoryShown() {
        Assertions.assertThat(fhc.riskCategory())
                .as("The saved risk profile category should be displayed")
                .isNotBlank();
    }

    @And("the risk category description is displayed")
    public void riskCategoryDescription() {
        Assertions.assertThat(fhc.text().toLowerCase())
                .as("A description of the risk category should be displayed")
                .contains("investor");
    }

    @And("Next is enabled on the risk result")
    public void nextResult() {
        Assertions.assertThat(fhc.nextEnabled()).as("Next should be enabled on the risk result").isTrue();
    }

    @Then("the displayed risk category is Conservative, Moderately Conservative, Moderate, Moderately Aggressive, or Aggressive")
    public void riskBand() {
        Assertions.assertThat(fhc.riskCategory())
                .as("The risk category should be one of the profile bands")
                .isIn("Conservative", "Moderately Conservative", "Moderate", "Balanced", "Moderately Aggressive", "Aggressive");
    }

    @When("I tap Next on the risk result")
    public void nextResultTap() {
        fhc.tapNextIfShown();
        for (int i = 0; i < 8 && fhc.onRiskResult() && !fhc.onCapability(); i++) {
            fhc.tapNextIfShown();
        }
    }

    @Then("the Risk Capability page is displayed")
    public void capability() {
        Assertions.assertThat(fhc.waitUntil(fhc::onCapability, Duration.ofSeconds(3)))
                .as("Risk Capability page should be displayed")
                .isTrue();
    }

    @When("I open the Risk Capability page")
    public void openCapability() {
        fhc.openCapability();
        Assertions.assertThat(fhc.onCapability()).as("Risk Capability page should be displayed").isTrue();
    }

    @Then("the heading Let us know your risk capability is displayed")
    public void capabilityHeading() {
        shows("Let us know your risk", "risk capability heading");
        shows("capability", "risk capability heading");
    }

    @And("Next is displayed on Risk Capability")
    public void nextCapability() {
        shows("Next", "Next on Risk Capability");
    }

    @Then("one risk capability option is already selected")
    public void capabilitySelected() {
        Assertions.assertThat(fhc.riskCategory())
                .as("The calculated risk capability should already be selected")
                .isNotBlank();
    }

    @And("the selected risk capability shows a description")
    public void capabilityDescription() {
        Assertions.assertThat(fhc.text().length())
                .as("The selected risk capability should show a description")
                .isGreaterThan(80);
    }

    @When("I tap Next on Risk Capability")
    public void nextCapabilityTap() {
        fhc.tapNextIfShown();
        for (int i = 0; i < 8 && fhc.onCapability() && !fhc.onSummary(); i++) {
            fhc.tapNextIfShown();
        }
    }

    @Then("the Summary Details page is displayed")
    public void summary() {
        requirePage(fhc::onSummary, "Summary Details must show Summary Details and My Personal Details");
    }

    @And("the summary shows the personal details section")
    public void summaryPersonal() {
        shows("My Personal Details", "My Personal Details");
    }

    @When("I open the Summary Details page")
    public void openSummary() {
        fhc.openSummary();
        requirePage(fhc::onSummary, "Summary Details must show Summary Details and My Personal Details");
    }

    @When("I tap Next on Summary Details")
    public void nextSummary() {
        fhc.tapNextIfShown();
        for (int i = 0; i < 8 && fhc.onSummary() && !fhc.onScore(); i++) {
            fhc.tapNextIfShown();
        }
    }

    @Then("the Financial Health Score page is displayed")
    public void scorePage() {
        requirePage(fhc::onScore, "Financial Health Score must show OUT OF 100 and YOUR FINANCIAL HEALTH SCORE");
    }

    @And("the score gauge shows OUT OF 100")
    public void outOf100() {
        shows("OUT OF 100", "OUT OF 100");
    }

    @Then("a financial health score number is displayed")
    public void healthScore() {
        Assertions.assertThat(fhc.healthScore())
                .as("A financial health score from 0 to 100 should be displayed")
                .matches("\\d{1,3}");
    }

    @When("I open the Financial Health Score page")
    @And("I return to the Financial Health Score page")
    public void openScore() {
        fhc.openScore();
        requirePage(fhc::onScore, "Financial Health Score must show OUT OF 100 and YOUR FINANCIAL HEALTH SCORE");
    }

    @Then("the displayed health category is Poor, Average, Good, Excellent, or Weak")
    public void healthCategory() {
        Assertions.assertThat(fhc.healthCategory())
                .as("A category based on the score should be displayed")
                .isIn("Poor", "Average", "Good", "Excellent", "Weak");
    }

    @Then("the financial health recommendation is displayed")
    public void recommendation() {
        Assertions.assertThat(fhc.recommendationShown())
                .as("The financial health recommendation should be displayed")
                .isTrue();
    }

    @When("I tap See the diagnosis")
    public void seeDiagnosis() {
        fhc.openDiagnosis();
    }

    @Then("the Diagnosis page is displayed")
    public void diagnosis() {
        requirePage(fhc::onDiagnosis, "Diagnosis must show Your Money Diagnosed");
    }

    @When("I tap Back on the Financial Health Score page")
    public void backScore() {
        fhc.goBack();
    }

    @When("I remember the financial health score")
    public void rememberScore() {
        rememberedScore = fhc.healthScore();
        rememberedCategory = fhc.healthCategory();
        Assertions.assertThat(rememberedScore).as("A score should be visible before leaving the page").isNotBlank();
    }

    @Then("the Financial Health Score page shows the same score")
    public void sameScore() {
        Assertions.assertThat(fhc.healthScore())
                .as("The financial health score should stay the same")
                .isEqualTo(rememberedScore);
    }

    @And("the Financial Health Score page shows the same category")
    public void sameCategory() {
        Assertions.assertThat(fhc.healthCategory())
                .as("The health category should stay the same")
                .isEqualTo(rememberedCategory);
    }

    @And("the Financial Health Score page has no error")
    public void scoreNoError() {
        noError("Financial Health Score");
    }

    @And("the Diagnosis page has no error")
    public void diagnosisNoError() {
        noError("Diagnosis");
    }

    @When("I open the Diagnosis page")
    public void openDiagnosis() {
        fhc.openDiagnosis();
        requirePage(fhc::onDiagnosis, "Diagnosis must show Your Money Diagnosed");
    }

    @Then("the title Your Money Diagnosed is displayed")
    public void diagnosedTitle() {
        shows("Your Money Diagnosed", "Your Money Diagnosed");
    }

    @And("the Net Worth section is displayed")
    public void netWorthSection() {
        shows("NET WORTH", "Net Worth section");
    }

    @And("the Assets section is displayed")
    public void assetsSection() {
        shows("ASSETS", "Assets section");
    }

    @And("the Liabilities section is displayed")
    public void liabilitiesSection() {
        shows("LIABILITIES", "Liabilities section");
    }

    @And("the page indicator 01 \\/ THE DIAGNOSIS is displayed")
    public void diagnosisIndicator() {
        shows("01 / THE DIAGNOSIS", "01 / THE DIAGNOSIS");
    }

    @And("Show me the gaps is displayed and enabled")
    public void showGaps() {
        Assertions.assertThat(fhc.gapsButtonEnabled())
                .as("Show me the gaps should be displayed and enabled")
                .isTrue();
    }

    private void requirePage(java.util.function.BooleanSupplier page, String expected) {
        requirePage(page, expected, 3);
    }

    private void requirePage(java.util.function.BooleanSupplier page, String expected, int timeoutSeconds) {
        stopIfSystemError();
        boolean shown = fhc.waitUntil(() -> {
            stopIfSystemError();
            if (page.getAsBoolean()) {
                return true;
            }
            fhc.dismissOverlays();
            return page.getAsBoolean();
        }, Duration.ofSeconds(timeoutSeconds));
        if (shown && fhc.fieldErrorMessage() != null) {
            fhc.resolveVisibleValidationErrors();
        }
        if (!shown) {
            FhcErrorLog.record("PAGE", "ASSERT", expected + ". Visible: " + fhc.visibleLabels());
        }
        Assertions.assertThat(shown)
                .as(expected + ". Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    private void stopIfSystemError() {
        String error = fhc.systemErrorMessage();
        if (error == null) {
            return;
        }
        String visible = fhc.visibleLabels();
        FhcSystemError.trip(error + " | " + visible);
        FhcErrorLog.record("SYSTEM", "APP_ERROR", error + " | " + visible);
        Assertions.fail("SYSTEM ERROR on FHC — suite stopped so this can be raised: "
                + error + ". Visible: " + visible);
    }

    private void shows(String label, String name) {
        Assertions.assertThat(fhc.reveal(label))
                .as(name + " must show \"" + label + "\". Visible: " + fhc.visibleLabels())
                .isTrue();
    }

    private boolean showsAmount(String digits) {
        return fhc.shows(digits) || fhc.shows(withCommas(digits));
    }

    private static String withCommas(String digits) {
        String clean = digits.replace(",", "");
        if (clean.length() <= 3) {
            return clean;
        }
        StringBuilder out = new StringBuilder(clean);
        int index = out.length() - 3;
        while (index > 0) {
            out.insert(index, ',');
            index -= 2;
        }
        return out.toString();
    }

    private void hidden(String label) {
        Assertions.assertThat(fhc.shows(label)).as(label + " should not be displayed").isFalse();
    }

    private void noError(String page) {
        stopIfSystemError();
        if (fhc.fieldErrorMessage() != null) {
            fhc.resolveVisibleValidationErrors();
        }
        Assertions.assertThat(fhc.errorShown()).as(page + " should not show a system error").isFalse();
        Assertions.assertThat(fhc.fieldErrorMessage())
                .as(page + " should not show field validation after auto-fill. Visible: " + fhc.visibleLabels())
                .isNull();
    }
}
