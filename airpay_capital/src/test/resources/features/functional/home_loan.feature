@airpay-capital @capital @functional @android @home-loan
Feature: Home Loan journeys
  Application field values are read from the Home Loan sheet in
  testdata/capital-functional-data.xlsx.

  Background:
    Given the Airpay Capital app is ready
    And I am logged in to Airpay Capital with an offers test account

  @CA_HL_001
  Scenario: Verify the Home Loan journey for a Salaried employee
    When I open the product from the Capital Excel sheet
    Then the occupation screen or the in-progress loan journey should be displayed
    When I continue that application using the Capital Excel sheet
    Then I should see loan offers available

  @CA_HL_002
  Scenario: Verify the Home Loan journey for a Self-Employed customer
    When I open the product from the Capital Excel sheet
    Then the occupation screen or the in-progress loan journey should be displayed
    When I continue that application using the Capital Excel sheet
    Then I should see loan offers available
