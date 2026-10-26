@airpay-money @money @functional @android @dashboard-home
Feature: Dashboard home page after login

  Sheet: Dashboard home page, TC_001 to TC_008.
  Login is already done. Mobile verification, account setup, email verification, PIN, and biometrics are not run again.
  TC_001 checks the dashboard those steps lead to.
  Let's Start opens Personal Details and stops there. KYC is not started.
  Sync Your Net Worth opens the consent screen. Allow Consent is not tapped.
  The live Let's Start label is "Let's start".

  Background:
    Given the logged-in airpay money home screen is open
    When I open the FinSight dashboard

  @DASH_TC_001
  Scenario: TC_001 the signed-in user is on the dashboard
    Then the dashboard home page is displayed
    And the dashboard shows the welcome greeting Hello
    And the dashboard shows Your Financial Health Score
    And the dashboard shows the FinSight tab

  @DASH_TC_002
  Scenario: TC_002 the welcome section shows Hello and the registered name
    Then the welcome message Hello is displayed
    And the registered user name is displayed beside Hello

  @DASH_TC_003
  Scenario: TC_003 the Financial Health Check card is displayed
    Then the Financial Health Check card shows Your Financial Health Score
    And the Financial Health Check card shows its information text
    And the Financial Health Check information sits above Let's Start
    And the Let's Start button is displayed

  @DASH_TC_004
  Scenario: TC_004 Let's Start opens the Financial Health Check
    When I tap Let's Start on the dashboard
    Then the Financial Health Check Personal Details page is displayed

  @DASH_TC_005
  Scenario: TC_005 Sync Your Net Worth is displayed
    When I scroll to the Sync Your Net Worth card
    Then the Sync Your Net Worth button is displayed on the dashboard
    And the Sync Your Net Worth description is displayed

  @DASH_TC_006
  Scenario: TC_006 Sync Your Net Worth opens the consent screen
    When I scroll to the Sync Your Net Worth card
    And I tap the Sync Your Net Worth button on the dashboard
    Then the Financial Data Fetch Consent screen is displayed
    And Allow Consent is displayed on the consent screen

  @DASH_TC_007
  Scenario: TC_007 Products, FinSight, and Calculators are displayed and aligned
    Then the Products tab is displayed on the dashboard
    And the FinSight tab is displayed on the dashboard
    And the Calculators tab is displayed on the dashboard
    And the Products, FinSight, and Calculators tabs are aligned

  @DASH_TC_008
  Scenario: TC_008 the notification icon is visible and enabled
    Then the notification icon is visible in the dashboard header
    And the notification icon is enabled
