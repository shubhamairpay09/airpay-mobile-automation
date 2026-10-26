@airpay-money @money @functional @android @sync-net-worth
Feature: Sync Your Net Worth after login

  Sheet: sync your networth.
  Login is already done. These cases start on the logged-in home screen and open the FinSight dashboard.
  Every case runs when Sync Your Net Worth is on the dashboard. If this account is already synced,
  the cases are skipped. KYC is not run.
  Coverage stops on the Finvu OTP screen. The OTP is not entered. Verify & Proceed is not tapped.
  The sheet lists "consent is submitted when the user clicks Allow Consent" twice with the same steps. That case is automated once.

  Background:
    Given the logged-in airpay money home screen is open

  @NW_TC_001
  Scenario: TC_001 Sync Your Net Worth opens the Financial Data Fetch Consent screen
    When I open the FinSight dashboard
    And I scroll to the Sync Your Net Worth card
    And I tap Sync Your Net Worth
    Then the Financial Data Fetch Consent screen is displayed
    And the Consent and Permissions section is displayed
    And Credit Data shows the Date Range
    And Credit Data shows the Consent Validity
    And the consent checkbox is displayed
    And Allow Consent is disabled

  @NW_TC_002
  Scenario: TC_002 Allow Consent stays disabled when consent is not provided
    When I open the Financial Data Fetch Consent screen
    And I leave the consent checkbox unselected
    Then Allow Consent stays disabled
    And the Financial Data Fetch Consent screen is still displayed

  @NW_TC_003
  Scenario: TC_003 the consent checkbox can be selected and deselected
    When I open the Financial Data Fetch Consent screen
    And I tap the consent checkbox
    Then the consent checkbox is selected
    When I tap the consent checkbox again
    Then the consent checkbox is not selected

  @NW_TC_004
  Scenario: TC_004 Allow Consent becomes enabled after the checkbox is selected
    When I open the Financial Data Fetch Consent screen
    And I select the consent checkbox
    Then the consent checkbox is selected
    And Allow Consent is enabled

  @NW_TC_005
  Scenario: TC_005 Allow Consent submits and opens the next Net Worth step
    When I open the Financial Data Fetch Consent screen
    And I select the consent checkbox
    And I tap Allow Consent
    Then the next Net Worth sync step is displayed without an error

  @NW_TC_006
  Scenario: TC_006 back from consent returns to FinSight without submitting
    When I open the Financial Data Fetch Consent screen
    And I tap the back arrow on the consent screen
    Then the FinSight dashboard is displayed again
    And consent was not submitted

  @NW_TC_007
  Scenario: TC_007 Allow Consent opens the Finvu OTP screen
    When I open the Financial Data Fetch Consent screen
    And I select the consent checkbox
    And I tap Allow Consent
    Then the Finvu Net Worth Consent screen is displayed
    And the Finvu safety message is displayed
    And the OTP input fields are displayed
    And the registered mobile number is partially masked
    And Verify and Proceed is displayed and disabled
    And the Finvu Terms and Conditions link is displayed

  @NW_TC_008
  Scenario: TC_008 the Finvu screen shows the OTP was sent
    When I open the Financial Data Fetch Consent screen
    And I select the consent checkbox
    And I tap Allow Consent
    Then the Finvu screen shows the OTP was sent to the masked mobile number
    And the registered mobile number is partially masked
    And the resend timer is displayed
