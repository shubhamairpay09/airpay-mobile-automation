@airpay-capital @capital @lms @dsa @web
Feature: DSA Agency Management (LMS admin)
  Runs in Chrome on the Android device (web.browser=mobile-chrome).
  Sign-in uses capital.lms.email, capital.lms.password, and capital.lms.otp from secrets.

  Background:
    Given mobile Chrome is open on the LMS sign-in page
    When I enter the LMS email from config
    And I enter the LMS password from config
    And I tap Sign In on the LMS sign-in page
    Then the Mobile OTP dialog should be displayed
    When I enter the LMS OTP from config
    And I submit the LMS OTP
    Then I should be signed in to the Capital LMS admin portal

  @CA_DA_001 @smoke
  Scenario: Verify that the DSA Agency Management page loads successfully
    When I open DSA Agency Management from the LMS menu
    Then the DSA Agency Management title should be displayed
    And the DSA Agency Management URL should contain dsa-agency
    And the list of registered agencies heading should be displayed
    And the Register New Agency button should be visible

  @CA_DA_002
  Scenario: Verify that all required columns are displayed in the agency grid
    When I open DSA Agency Management from the LMS menu
    Then the agency grid should show column "Sr. No"
    And the agency grid should show column "DSA Code"
    And the agency grid should show column "Agency Name"
    And the agency grid should show column "Loan Type"
    And the agency grid should show column "Onboarded On Date"
    And the agency grid should show column "Status"
    And the agency grid should contain at least one agency row

  @CA_DA_003
  Scenario: Verify that the Register New Agency button is displayed and functional
    When I open DSA Agency Management from the LMS menu
    Then the Register New Agency button should be visible
    And the Register New Agency button should be enabled
    When I click Register New Agency on the DSA Agency page
    Then the agency registration form should be displayed

  @CA_DA_0010
  Scenario: Verify that the registration form displays the PAN Number field
    When I open DSA Agency Management from the LMS menu
    When I click Register New Agency on the DSA Agency page
    Then the agency registration form should be displayed
    And the registration form should display the PAN Number field
