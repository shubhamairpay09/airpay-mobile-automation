@airpay-capital @capital @functional @android @lap
Feature: Loan Against Property journeys
  Application field values are read from the Loan Against Property sheet in
  testdata/capital-functional-data.xlsx.

  Background:
    Given the Airpay Capital app is ready
    And I am logged in to Airpay Capital with an offers test account

  @CA_LAP_001
  Scenario: Verify the LAP journey for a Salaried employee
    When I complete the product application from the Capital Excel sheet until eligibility
    # Then I should see loan offers available

  @CA_LAP_002
  Scenario: Verify the LAP journey for a Self-Employed customer
    When I complete the product application from the Capital Excel sheet until eligibility
    # Then I should see loan offers available
