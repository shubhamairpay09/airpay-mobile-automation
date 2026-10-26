@airpay-capital @capital @functional @android
Feature: Home Loan journeys

  Background:
    Given the Airpay Capital app is ready

  @CAPITAL_007
  Scenario: Verify Home Loan journey for Salaried employee
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Home Loan" application as "Salaried" until eligibility
    Then I should see loan offers available

  @CAPITAL_008
  Scenario: Verify Home Loan journey for Self-Employed customer
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Home Loan" application as "Self-Employed" until eligibility
    Then I should see loan offers available

  @CAPITAL_009
  Scenario: Verify Home Loan when no offer is available
    Given I am logged in to Airpay Capital with a no-offer test account
    When I complete the "Home Loan" application as "Salaried" until eligibility
    Then I should see no loan offers available

  @CAPITAL_010
  Scenario: Verify Home Loan offer navigates to lender page
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Home Loan" application as "Salaried" until eligibility
    And I open the first lender offer for "Home Loan"
    Then I should be on the lender application page

  @CAPITAL_011
  Scenario: Verify Home Loan low CIBIL score error
    Given I am logged in to Airpay Capital with a low CIBIL test account
    When I complete the "Home Loan" application as "Salaried" until eligibility
    Then I should see a low CIBIL score error message
