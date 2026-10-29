@airpay-capital @capital @functional @android @personal-loan
Feature: Personal Loan journeys
  Application field values (PAN, Name, Email, LoanAmount, MonthlyIncome, Occupation)
  are read from the Personal Loan sheet in testdata/capital-functional-data.xlsx.

  Background:
    Given the Airpay Capital app is ready
    And I am logged in to Airpay Capital with an offers test account

  @CA_PL_001
  Scenario: Verify the Personal Loan journey for a Salaried employee
    When I complete the product application from the Capital Excel sheet until eligibility
    # Then I should see loan offers available

  @CA_PL_002
  Scenario: Verify the Personal Loan journey for a Self-Employed customer
    When I complete the product application from the Capital Excel sheet until eligibility
    # Then I should see loan offers available
