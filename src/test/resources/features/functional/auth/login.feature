@airpay-money @money @functional @android @login
Feature: airpay money Login

  Module: Login.
  Locators live on the auth *Screen classes. This module is left to run later.

  @MONEY_001 @smoke
  Scenario: Verify login using a mobile number
    Given the airpay money app is ready
    When I tap login with mobile on the airpay money welcome screen
    And I enter the airpay money mobile number and accept consent if shown
    And I tap Send OTP on airpay money
    And I enter airpay money OTP "1234" and verify
    Then I should see the airpay money home screen
