@airpay-capital @capital @lms @dsa @web
Feature: DSA Agency Management (LMS admin)
  Opens https://admin-staging.airpay.capital in Chrome on the Android device (web.browser=mobile-chrome).
  Credentials: capital.lms.* in config/secrets.properties
  Staging OTP/PIN is capital.lms.otp (default 1234).

  Background:
    Given I am logged in to the Capital LMS admin portal

  @CA_DA_001 @smoke
  Scenario: Verify that the DSA Agency Management page loads successfully
    When I open DSA Agency Management from the LMS menu
    Then the DSA Agency Management page should be displayed

  @CA_DA_002
  Scenario: Verify that all required columns are displayed in the agency grid
    When I open DSA Agency Management from the LMS menu
    Then the DSA agency grid should show the required columns

  @CA_DA_003
  Scenario: Verify that the Register New Agency button is displayed and functional
    When I open DSA Agency Management from the LMS menu
    And I click Register New Agency on the DSA Agency page
    Then the agency registration form should open

  @CA_DA_0010
  Scenario: Verify that the registration form displays the PAN Number field
    When I open DSA Agency Management from the LMS menu
    And I click Register New Agency on the DSA Agency page
    Then the registration form should display the PAN Number field
