@airpay-capital @capital @functional @android @lap
Feature: Loan Against Property journeys
  PAN, name, email, and desired loan amount are read from the Loan Against Property sheet
  in testdata/capital-functional-data.xlsx.

  Background:
    Given the Airpay Capital app is ready
    And I am logged in to Airpay Capital with an offers test account

  @CA_LAP_001
  Scenario: Verify the LAP journey for a Salaried employee
    When I open the product card from the Capital Excel sheet
    Then the product feature page, occupation screen, personal details, or saved journey should be displayed
    When I accept the terms and tap Apply Now when the feature page is showing
    Then the occupation screen, personal details, or saved journey should be displayed
    When I select the occupation from the Capital Excel sheet and open personal details
    Then the personal details screen should be displayed
    When I enter the PAN, name, email, and desired loan amount from the Capital Excel sheet
    Then the personal details screen should show the PAN, name, email, and desired loan amount from the Capital Excel sheet
    When I set the date of birth and gender and tap Continue
    Then I should see loan offers available

  @CA_LAP_002
  Scenario: Verify the LAP journey for a Self-Employed customer
    When I open the product card from the Capital Excel sheet
    Then the product feature page, occupation screen, personal details, or saved journey should be displayed
    When I accept the terms and tap Apply Now when the feature page is showing
    Then the occupation screen, personal details, or saved journey should be displayed
    When I select the occupation from the Capital Excel sheet and open personal details
    Then the personal details screen should be displayed
    When I enter the PAN, name, email, and desired loan amount from the Capital Excel sheet
    Then the personal details screen should show the PAN, name, email, and desired loan amount from the Capital Excel sheet
    When I set the date of birth and gender and tap Continue
    Then I should see loan offers available
