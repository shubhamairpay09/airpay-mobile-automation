@airpay-capital @capital @functional @android
Feature: Loan Against Property journeys

  Background:
    Given the Airpay Capital app is ready

  @CAPITAL_012
  Scenario: Verify LAP journey for Salaried employee
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Loan Against Property" application as "Salaried" until eligibility
    Then I should see loan offers available

  @CAPITAL_013
  Scenario: Verify LAP journey for Self-Employed customer
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Loan Against Property" application as "Self-Employed" until eligibility
    Then I should see loan offers available

  @CAPITAL_014
  Scenario: Verify LAP when no offer is available
    Given I am logged in to Airpay Capital with a no-offer test account
    When I complete the "Loan Against Property" application as "Salaried" until eligibility
    Then I should see no loan offers available

  @CAPITAL_015
  Scenario: Verify LAP offer navigates to lender page
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Loan Against Property" application as "Salaried" until eligibility
    And I open the first lender offer for "Loan Against Property"
    Then I should be on the lender application page

  @CAPITAL_016
  Scenario: Verify LAP low CIBIL score error
    Given I am logged in to Airpay Capital with a low CIBIL test account
    When I complete the "Loan Against Property" application as "Salaried" until eligibility
    Then I should see a low CIBIL score error message
