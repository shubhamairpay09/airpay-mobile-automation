@airpay-money @money @functional @android @calculators
Feature: Calculators after login

  Sheet: Calculator. The sheet has three cases and no test case numbers.
  Calculator names and the page each one opens are on the Calculator sheet in testdata/money-functional-data.xlsx.
  Login is already done. These cases open the Calculators tab.
  FD Calculator opens the page titled Fixed Deposit.
  Buy vs Rent Calculator opens the page titled Buy Vs Rent calculator.
  Term Insurance Calculator is checked for a Term Insurance page.
  No investment, payment, or KYC is started.

  Background:
    Given the logged-in airpay money home screen is open
    When I open the Calculators tab

  @CALC_TC_001
  Scenario: TC_001 Financial Calculators shows the header, greeting, and options
    Then the Financial Calculators screen is displayed
    And the Financial Calculators header is displayed
    And the calculator greeting Hello is displayed
    And the EMI Calculator option is visible
    And the calculator greeting, header, and EMI Calculator option do not overlap

  @CALC_TC_002
  Scenario: TC_002 every calculator option is listed
    When I review the calculator options
    Then every calculator option on the Calculator sheet is listed

  @CALC_TC_003
  Scenario: TC_003 each calculator opens its page and returns
    When I open every calculator on the Calculator sheet and return
    Then the Financial Calculators screen is displayed
