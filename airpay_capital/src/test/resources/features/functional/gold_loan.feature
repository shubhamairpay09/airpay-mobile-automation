@airpay-capital @capital @functional @android
Feature: Gold Loan journeys

  Background:
    Given the Airpay Capital app is ready

  @CAPITAL_017
  Scenario: Verify Gold Loan journey for Salaried employee
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Gold Loan" application as "Salaried" until eligibility
    Then I should see loan offers available

  @CAPITAL_018
  Scenario: Verify Gold Loan journey for Self-Employed customer
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Gold Loan" application as "Self-Employed" until eligibility
    Then I should see loan offers available

  @CAPITAL_019
  Scenario: Verify Gold Loan when no offer is available
    Given I am logged in to Airpay Capital with a no-offer test account
    When I complete the "Gold Loan" application as "Salaried" until eligibility
    Then I should see no loan offers available

  @CAPITAL_020
  Scenario: Verify Gold Loan offer navigates to lender page
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Gold Loan" application as "Salaried" until eligibility
    And I open the first lender offer for "Gold Loan"
    Then I should be on the lender application page

  @CAPITAL_021
  Scenario: Verify Gold Loan low CIBIL score error
    Given I am logged in to Airpay Capital with a low CIBIL test account
    When I complete the "Gold Loan" application as "Salaried" until eligibility
    Then I should see a low CIBIL score error message
