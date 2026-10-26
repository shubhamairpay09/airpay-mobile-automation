@airpay-capital @capital @functional @android
Feature: Credit Card journeys

  Background:
    Given the Airpay Capital app is ready

  @CAPITAL_022
  Scenario: Verify Credit Card journey for Salaried employee
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Credit Card" application as "Salaried" until eligibility
    Then I should see loan offers available

  @CAPITAL_023
  Scenario: Verify Credit Card journey for Self-Employed customer
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Credit Card" application as "Self-Employed" until eligibility
    Then I should see loan offers available

  @CAPITAL_024
  Scenario: Verify Credit Card when no offer is available
    Given I am logged in to Airpay Capital with a no-offer test account
    When I complete the "Credit Card" application as "Salaried" until eligibility
    Then I should see no loan offers available

  @CAPITAL_025
  Scenario: Verify Credit Card offer navigates to lender page
    Given I am logged in to Airpay Capital with an offers test account
    When I complete the "Credit Card" application as "Salaried" until eligibility
    And I open the first lender offer for "Credit Card"
    Then I should be on the lender application page

  @CAPITAL_026
  Scenario: Verify Credit Card low CIBIL score error
    Given I am logged in to Airpay Capital with a low CIBIL test account
    When I complete the "Credit Card" application as "Salaried" until eligibility
    Then I should see a low CIBIL score error message
