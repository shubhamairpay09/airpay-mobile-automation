@airpay-capital @capital @functional @android @muthoot
Feature: Muthoot FinCorp offer journeys
  Product, Occupation, and LenderName are read from the Muthoot Fincorp sheet in
  testdata/capital-functional-data.xlsx.

  Background:
    Given the Airpay Capital app is ready
    And I am logged in to Airpay Capital with an offers test account

  @CA_MF_001
  Scenario: Verify the Muthoot FinCorp journey for a BRE1 offer
    When I open the product from the Capital Excel sheet
    Then the occupation screen or the in-progress loan journey should be displayed
    When I continue that application using the Capital Excel sheet
    Then I should see loan offers available
    When I open the Muthoot FinCorp offer from the Capital Excel sheet and tap Apply Now
    Then I should be on the lender application page

  @CA_MF_002
  Scenario: Verify the Muthoot FinCorp journey for a BRE2 offer
    When I open the product from the Capital Excel sheet
    Then the occupation screen or the in-progress loan journey should be displayed
    When I continue that application using the Capital Excel sheet
    Then I should see loan offers available
    When I open the Muthoot FinCorp offer from the Capital Excel sheet and tap Apply Now
    Then I should be on the lender application page
