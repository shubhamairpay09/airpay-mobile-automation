@airpay-vyaapaar @vyaapaar @android @a11y @profile
Feature: airpay vyaapaar profile screens
  Xpaths for this module live in screens/profile/.
  Each screen was captured once during the deep scan.

  @PROFILE_001
  Scenario: Profile/Settings
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "profile-settings" in module "profile" has stored xpaths

  @PROFILE_002
  Scenario: Profile/Settings/Support/Tickets
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "profile-settings-support-tickets" in module "profile" has stored xpaths

  @PROFILE_003
  Scenario: Profile/Settings/Support/Tickets/Transaction
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "profile-settings-support-tickets-transaction" in module "profile" has stored xpaths

  @PROFILE_004
  Scenario: Profile/Settings/Support/Customer Support
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "profile-settings-support-customer-support" in module "profile" has stored xpaths

  @PROFILE_005
  Scenario: Profile/Settings/Support/Privacy Policy
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "profile-settings-support-privacy-policy" in module "profile" has stored xpaths

  @PROFILE_006
  Scenario: Profile/Settings/Support/Terms and Conditions
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "profile-settings-support-terms-and-conditions" in module "profile" has stored xpaths

