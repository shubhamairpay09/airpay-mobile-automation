@airpay-money @money @functional @android @home
Feature: Home dashboard after login

  Module: FinSight home.
  Locators live on FinSightHomeScreen and HomeScreen.
  Login, OTP, account setup, email verification, PIN, and biometrics are left for a later run.
  KYC cases are left out. This account is already KYC verified.
  These cases start on the logged-in FinSight home screen.

  @HOME_001
  Scenario: Home dashboard shows the signed-in user, health check, sync, navigation, and notifications
    Given the logged-in airpay money home screen is open
    Then the home dashboard is displayed
    And the welcome greeting shows the logged-in user name
    And the Financial Health Check card is displayed
    And the Sync Your Net Worth card is displayed
    And the bottom navigation shows Products, FinSight, and Calculators
    And the notification icon is displayed on the dashboard

  @HOME_002
  Scenario: Financial Health Check opens from the dashboard without starting KYC
    Given the logged-in airpay money home screen is open
    When I open Financial Health Check from the dashboard
    Then the Financial Health Check journey or the completed score is shown
    And I return to the home dashboard

  @HOME_003
  Scenario: Net worth card shows synced accounts or consent, and does not link a new account
    Given the logged-in airpay money home screen is open
    When I open the net worth card
    Then synced net worth or the consent screen is displayed
    And I leave without linking a new account
