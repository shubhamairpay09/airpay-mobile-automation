@airpay-capital @capital @functional @android
Feature: Personal Loan journeys

  Background:
    Given the Airpay Capital app is ready

  @CAPITAL_002
  Scenario: Verify Personal Loan journey for Salaried employee
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Personal Loan" application as "Salaried" until eligibility
    Then I should see loan offers available

  @CAPITAL_003
  Scenario: Verify Personal Loan journey for Self-Employed customer
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Personal Loan" application as "Self-Employed" until eligibility
    Then I should see loan offers available

  @CAPITAL_004
  Scenario: Verify Personal Loan when no offer is available
    Given I am logged in to Airpay Capital with a no-offer test account
    When I complete the "Personal Loan" application as "Salaried" until eligibility
    Then I should see no loan offers available

  @CAPITAL_005
  Scenario: Verify Personal Loan offer navigates to lender page
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Personal Loan" application as "Salaried" until eligibility
    And I open the first lender offer for "Personal Loan"
    Then I should be on the lender application page

  @CAPITAL_006
  Scenario: Verify Personal Loan low CIBIL score error
    Given I am logged in to Airpay Capital with a low CIBIL test account
    When I complete the "Personal Loan" application as "Salaried" until eligibility
    Then I should see a low CIBIL score error message
