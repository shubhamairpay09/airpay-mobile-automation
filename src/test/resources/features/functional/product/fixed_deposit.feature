@airpay-money @money @functional @android @fixed-deposit
Feature: Fixed Deposit after login

  Sheet: Fixed Deposit, TC_001 to TC_012.
  Book FD is not tapped. KYC inside a provider is not run.
  Senior Citizen Suryoday rate is 8.5%. Regular rates are Suryoday 8.25%, Mahindra 7.50%, Bajaj 7.40%, and Unity 8.00%.
  Tenure rates on the Suryoday page are 8.25%, 7.25%, and 4.00%.
  Rates, tenure filters, and the investment amount are read from the Fixed Deposit sheet in testdata/money-functional-data.xlsx.
  Interest Payout uses the sheet data only: Monthly, with Maturity as the default.

  Background:
    Given the logged-in airpay money home screen is open
    When I open Fixed Deposit from the products hub

  @FD_TC_001
  Scenario: TC_001 an FD provider card opens the details page
    When I open the Suryoday Small Finance Bank FD card
    Then the selected FD provider details are displayed
    And I go back to the Fixed Deposit dashboard

  @FD_TC_002
  Scenario: TC_002 Senior Citizen toggle shows senior FD rates
    When I turn Senior Citizen rates on
    Then senior citizen FD rates are displayed

  @FD_TC_003
  Scenario: TC_003 turning Senior Citizen off restores regular FD rates
    When I turn Senior Citizen rates on
    And I turn Senior Citizen rates off
    Then regular FD rates are displayed

  @FD_TC_004
  Scenario: TC_004 0-1 year filter is applied
    When I filter Fixed Deposits by "0-1 year"
    Then only the 0 to 1 year FD range is selected

  @FD_TC_005
  Scenario: TC_005 1-3 year filter is applied
    When I filter Fixed Deposits by "1-3 year"
    Then only the 1 to 3 year FD range is selected

  @FD_TC_006
  Scenario: TC_006 greater than 3 years filter is applied
    When I filter Fixed Deposits by ">3 years"
    Then only the over 3 years FD range is selected

  @FD_TC_007
  Scenario: TC_007 Women Benefits filter shows applicable FDs
    When I open Women Benefits
    Then women-benefit FD products are displayed

  @FD_TC_008
  Scenario: TC_008 provider cards show interest rates
    Then interest rates are displayed on more than one FD provider card

  @FD_TC_009
  Scenario: TC_009 Suryoday details page shows amount, tenure, payout, renewal, maturity, and Book FD
    When I open the Suryoday Small Finance Bank FD card
    Then the selected FD provider details are displayed
    And I go back to the Fixed Deposit dashboard

  @FD_TC_010
  Scenario: TC_010 the Suryoday investment amount can be changed
    When I open the Suryoday Small Finance Bank FD card
    And I change the FD investment amount to "50000"
    And I go back to the Fixed Deposit dashboard

  @FD_TC_011
  Scenario: TC_011 a different Suryoday tenure can be selected
    When I open the Suryoday Small Finance Bank FD card
    And I select a different FD tenure
    And I go back to the Fixed Deposit dashboard

  @FD_TC_012
  Scenario: TC_012 Interest Payout shows Monthly, and Maturity is the default
    When I open the Suryoday Small Finance Bank FD card
    Then the FD interest payout options are displayed
    And I go back to the Fixed Deposit dashboard
