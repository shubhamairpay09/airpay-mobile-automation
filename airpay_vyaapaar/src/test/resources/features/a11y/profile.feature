@airpay-vyaapaar @vyaapaar @android @a11y @profile
Feature: airpay vyaapaar profile screens
  Xpaths for this module live in screens/profile/.
  Each screen was captured once during the deep scan.

  @PROFILE_001
  Scenario: Profile
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "profile" in module "profile" has stored xpaths

  @PROFILE_002
  Scenario: Profile/My Commercials
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "profile-my-commercials" in module "profile" has stored xpaths

  @PROFILE_003
  Scenario: Profile/BC Certificate
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "profile-bc-certificate" in module "profile" has stored xpaths

