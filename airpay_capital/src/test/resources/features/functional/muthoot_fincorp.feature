@airpay-capital @capital @functional @android @muthoot
Feature: Muthoot FinCorp offer journeys
  Product, occupation, PAN, name, email, loan amount, and LenderName are read from
  the Muthoot Fincorp sheet in testdata/capital-functional-data.xlsx.

  Background:
    Given the Airpay Capital app is ready
    And I am logged in to Airpay Capital with an offers test account

  @CA_MF_001
  Scenario: Verify the Muthoot FinCorp journey for a BRE1 offer
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
    When I open the Muthoot FinCorp offer from the Capital Excel sheet and tap Apply Now
    Then I should be on the lender application page

  @CA_MF_002
  Scenario: Verify the Muthoot FinCorp journey for a BRE2 offer
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
    When I open the Muthoot FinCorp offer from the Capital Excel sheet and tap Apply Now
    Then I should be on the lender application page
