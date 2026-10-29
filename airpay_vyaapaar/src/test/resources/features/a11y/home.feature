@airpay-vyaapaar @vyaapaar @android @a11y @home
Feature: airpay vyaapaar home screens
  Xpaths for this module live in screens/home/.
  Each screen was captured once during the deep scan.

  @HOME_001
  Scenario: Home after password login
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-after-password-login" in module "home" has stored xpaths

  @HOME_002
  Scenario: Home
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home" in module "home" has stored xpaths

