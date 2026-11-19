@airpay-money @money @functional @android @fhc
Feature: Financial Health Check after login

  Sheet: FHC, TC_001 to TC_035 from Airpay money test case.xlsx.
  Login and KYC are already done. These cases start on the logged-in home screen.
  The Financial Health Check entry on this build is Continue, Start Now, Let's start, Next, View Details, or Reassess.
  Amounts are read from the FHC sheet in testdata/money-functional-data.xlsx.
  If the app shows a system error, the suite stops so the failure can be raised.

  Background:
    Given the logged-in airpay money home screen is open
    And FHC is not blocked by a system error

  @FHC_TC_001
  Scenario: TC_001 Next on Financial Health Check opens Personal Details
    When I open the Financial Health Check card
    Then the Financial Health Check entry button is displayed
    When I tap the Financial Health Check entry button
    Then the Personal Details page is displayed
    And the Personal Details page has no error
    And the Full Name field is displayed
    And the Date of Birth field is displayed
    And the Gender field is displayed
    And the Dependant field is displayed
    And the Pincode field is displayed
    And the City field is displayed
    And the State field is displayed
    And Next is visible and enabled on Personal Details
    And the Full Name field is prefilled

  @FHC_TC_002
  Scenario: TC_002 Next on Personal Details opens Income and Expense
    When I open the Personal Details page
    And I verify the Personal Details fields
    And I tap Next on Personal Details
    Then the Income and Expense page is displayed
    And the Income and Expense page has no error

  @FHC_TC_003
  Scenario: TC_003 No can be selected for a running loan
    When I open the Income and Expense page
    And I select No for the running loan question
    Then No is selected for the running loan question
    And Yes is not selected for the running loan question

  @FHC_TC_004
  Scenario: TC_004 valid income and expense details open the next page
    When I open the Income and Expense page
    And I enter the net monthly income from the FHC sheet
    And I enter the monthly household expenses from the FHC sheet
    And I select No for the running loan question
    And I tap Next on Income and Expense
    Then the Insurance Details page is displayed
    And the Insurance Details page has no error

  @FHC_TC_005
  Scenario: TC_005 Add Loan is shown when Yes is selected
    When I open the Income and Expense page
    And I select Yes for the running loan question
    Then the Add Loan button is displayed
    And the Add Loan button is enabled

  @FHC_TC_006
  Scenario: TC_006 Add Loan opens Add Loan Details
    When I open the Income and Expense page
    And I select Yes for the running loan question
    And I tap Add Loan
    Then the Add Loan Details page is displayed
    And the Add Loan Details page has no error

  @FHC_TC_007
  Scenario: TC_007 a loan can be added
    When I open the Add Loan Details page
    And I select the Education Loan type
    And I enter principal amount 500000
    And I enter EMI 10000
    And I enter interest rate 12
    And I tap Add on the loan form
    Then the Income and Expense page is displayed after the loan is added
    And the added loan shows Education Loan

  @FHC_TC_008
  Scenario: TC_008 Back from Add Loan Details keeps the income details
    When I open the Add Loan Details page
    And I tap Back on Add Loan Details
    Then the Income and Expense page is displayed
    And the FHC sheet income is still shown on Income and Expense

  @FHC_TC_009
  Scenario: TC_009 the added loan card shows the saved values
    When I open the Income and Expense page
    And I select Yes for the running loan question
    And I tap Add Loan
    And I select the Education Loan type
    And I enter principal amount 500000
    And I enter EMI 10000
    And I enter interest rate 12
    And I tap Add on the loan form
    Then the My Loans section shows the loan card
    And the loan card shows the loan type Education Loan
    And the loan card shows principal 500000
    And the loan card shows EMI 10000
    And the loan card shows interest rate 12

  @FHC_TC_010
  Scenario: TC_010 a loan can be deleted from My Loans
    When I add the Education Loan on Income and Expense
    And I delete the added loan
    Then the loan is no longer shown under My Loans

  @FHC_TC_011
  Scenario: TC_011 Next on Income and Expense opens Insurance Details
    When I open the Income and Expense page
    And I enter the net monthly income from the FHC sheet
    And I enter the monthly household expenses from the FHC sheet
    And I select No for the running loan question
    And I tap Next on Income and Expense
    Then the Insurance Details page is displayed
    And the Insurance Details page has no error

  @FHC_TC_012
  Scenario: TC_012 No for insurance does not require insurance details
    When I open the Insurance Details page
    And I select No for insurance
    Then No is selected for insurance
    And the Health Insurance section is not displayed
    And Next is enabled on Insurance Details

  @FHC_TC_013
  Scenario: TC_013 Next on Insurance Details opens the next page
    When I open the Insurance Details page
    And I select No for insurance
    And I tap Next on Insurance Details
    Then the Savings and Investments page is displayed
    And the Insurance Details page is no longer displayed

  @FHC_TC_014
  Scenario: TC_014 Next on Insurance Details opens Savings and Investments
    When I open the Insurance Details page
    And I select No for insurance
    And I tap Next on Insurance Details
    Then the Savings and Investments page is displayed
    And the Savings and Investments page has no error

  @FHC_TC_015
  Scenario: TC_015 an investment can be added
    When I open the Add Investment Details page
    And I select Public Provident Fund as the investment type
    And I enter the current value from the FHC sheet
    And I select No for associating a goal
    And I tap Add on the investment form
    Then the Savings and Investments page is displayed
    And My Investments shows the added investment

  @FHC_TC_016
  Scenario: TC_016 an investment can be deleted from My Investments
    When I open the Add Investment Details page
    And I select Public Provident Fund as the investment type
    And I enter the current value from the FHC sheet
    And I select No for associating a goal
    And I tap Add on the investment form
    And I delete the added investment
    Then the investment is no longer shown under My Investments

  @FHC_TC_017
  Scenario: TC_017 Next on Savings and Investments opens the risk profile introduction
    When I open the Savings and Investments page
    And I enter the total bank balance from the FHC sheet
    And I select No for investments
    And I select No for creating a plan
    And I tap Next on Savings and Investments
    Then the Risk Profile introduction page is displayed
    And the Risk Profile introduction page has no error
    And the risk assessment heading is displayed
    And the financial risk description is displayed
    And the Lets do risk profile button is displayed
    And the I already know button is displayed
    And the Back button is displayed on the risk introduction

  @FHC_TC_018
  Scenario: TC_018 Lets do risk profile opens question 1
    When I open the Risk Profile introduction page
    And I tap Lets do risk profile
    Then Question 1 of 5 is displayed
    And the question progress shows Question 1/5
    And the investment time horizon question is displayed
    And the option "Less than 1 year" is displayed
    And the option "1 to 3 years" is displayed
    And the option "3 to 5 years" is displayed
    And the option "Above 5 years" is displayed
    And Next is displayed on the question

  @FHC_TC_019
  Scenario: TC_019 an answer on question 1 opens question 2
    When I open risk profile question 1
    And I select "Above 5 years"
    And I tap Next on the risk question
    Then Question 2 of 5 is displayed
    And the question progress shows Question 2/5

  @FHC_TC_020
  Scenario: TC_020 question 2 shows the income question and its options
    When I open risk profile question 1
    And I select "Above 5 years"
    And I tap Next on the risk question
    Then Question 2 of 5 is displayed
    And the question progress shows Question 2/5
    And the portfolio income question is displayed
    And the option "Yes" is displayed
    And the option "No" is displayed
    And the option "Maybe" is displayed
    When I select "No"
    Then Next is enabled on the risk question

  @FHC_TC_021
  Scenario: TC_021 an answer on question 2 opens question 3
    When I open risk profile question 2
    And I select "No"
    And I tap Next on the risk question
    Then Question 3 of 5 is displayed
    And the question progress shows Question 3/5

  @FHC_TC_022
  Scenario: TC_022 question 3 shows the returns question and its options
    When I open risk profile question 2
    And I select "No"
    And I tap Next on the risk question
    Then Question 3 of 5 is displayed
    And the question progress shows Question 3/5
    And the investment returns question is displayed
    And the option "Stable and predictable returns even if lower" is displayed
    And the option "Balanced returns with some fluctuations" is displayed
    And the option "Potentially higher returns with increased volatility" is displayed
    And Next is displayed on the question

  @FHC_TC_023
  Scenario: TC_023 an answer on question 3 opens question 4
    When I open risk profile question 3
    And I select "Balanced returns with some fluctuations"
    And I tap Next on the risk question
    Then Question 4 of 5 is displayed
    And the question progress shows Question 4/5

  @FHC_TC_024
  Scenario: TC_024 question 4 shows the loss question and its options
    When I open risk profile question 3
    And I select "Balanced returns with some fluctuations"
    And I tap Next on the risk question
    Then Question 4 of 5 is displayed
    And the question progress shows Question 4/5
    And the maximum loss question is displayed
    And the option "I don't like any capital loss" is displayed
    And the option "Upto 5% to 10% of my portfolio" is displayed
    And the option "Upto 10% to 20% of my portfolio" is displayed
    And the option "Above 20%" is displayed
    And Next is displayed on the question

  @FHC_TC_025
  Scenario: TC_025 an answer on question 4 opens question 5
    When I open risk profile question 4
    And I select "Upto 5% to 10% of my portfolio"
    And I tap Next on the risk question
    Then Question 5 of 5 is displayed
    And the question progress shows Question 5/5

  @FHC_TC_026
  Scenario: TC_026 question 5 shows the market-loss question and its options
    When I open risk profile question 4
    And I select "Upto 5% to 10% of my portfolio"
    And I tap Next on the risk question
    Then Question 5 of 5 is displayed
    And the question progress shows Question 5/5
    And the market loss response question is displayed
    And the option "Sell all your investments immediately" is displayed
    And the option "Sell some investments and continue to hold the rest" is displayed
    And the option "Stay Invested" is displayed
    And Next is displayed on the question

  @FHC_TC_027
  Scenario: TC_027 question 5 opens the risk profile result
    When I open risk profile question 5
    And I select "Stay Invested"
    And I tap Next on the risk question
    Then the Risk Profile result page is displayed
    And the Risk Profile result page has no error

  @FHC_TC_028
  Scenario: TC_028 the result category is one of the profile bands
    When I complete all 5 risk profile questions
    Then the displayed risk category is Conservative, Moderately Conservative, Moderate, Moderately Aggressive, or Aggressive

  @FHC_TC_029
  Scenario: TC_029 Next on the risk result opens the next page
    When I complete all 5 risk profile questions
    And I tap Next on the risk result
    Then the Risk Capability page is displayed

  @FHC_TC_030
  Scenario: TC_030 the Risk Capability page lists every option
    When I open the Risk Capability page
    Then the heading Let us know your risk capability is displayed
    And the option "Aggressive" is displayed
    And the option "Moderately Aggressive" is displayed
    And the option "Balanced" is displayed
    And the option "Moderately Conservative" is displayed
    And the option "Conservative" is displayed
    And Next is displayed on Risk Capability

  @FHC_TC_031
  Scenario: TC_031 the calculated risk capability is already selected
    When I open the Risk Capability page
    Then one risk capability option is already selected
    And the selected risk capability shows a description

  @FHC_TC_032
  Scenario: TC_032 Next on Risk Capability opens Summary Details
    When I open the Risk Capability page
    And I tap Next on Risk Capability
    Then the Summary Details page is displayed
    And the summary shows the personal details section

  @FHC_TC_033
  Scenario: TC_033 Next on Summary Details opens the score gauge
    When I open the Summary Details page
    And I tap Next on Summary Details
    Then the Financial Health Score page is displayed
    And the score gauge shows OUT OF 100
    And a financial health score number is displayed

  @FHC_TC_034
  Scenario: TC_034 See the diagnosis opens the diagnosis page
    When I open the Financial Health Score page
    And I tap See the diagnosis
    Then the Diagnosis page is displayed
    And the Diagnosis page has no error

  @FHC_TC_035
  Scenario: TC_035 the diagnosis page shows net worth, assets, and liabilities
    When I open the Diagnosis page
    Then the title Your Money Diagnosed is displayed
    And the Net Worth section is displayed
    And the Assets section is displayed
    And the Liabilities section is displayed
    And the page indicator 01 / THE DIAGNOSIS is displayed
    And Show me the gaps is displayed and enabled
