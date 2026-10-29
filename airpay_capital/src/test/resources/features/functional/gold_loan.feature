@airpay-capital @capital @functional @android @gold-loan
Feature: Gold Loan journeys
  Application field values are read from the Gold Loan sheet in
  testdata/capital-functional-data.xlsx.

  Background:
    Given the Airpay Capital app is ready
    And I am logged in to Airpay Capital with an offers test account

  @CA_GL_001
  Scenario: Verify the Gold Loan journey for a Salaried employee
    When I complete the product application from the Capital Excel sheet until eligibility
    # Then I should see loan offers available

  @CA_GL_002
  Scenario: Verify the Gold Loan journey for a Self-Employed customer
    When I complete the product application from the Capital Excel sheet until eligibility
    # Then I should see loan offers available
