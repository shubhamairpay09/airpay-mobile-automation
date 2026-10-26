@airpay-vyaapaar @vyaapaar @android @a11y @aeps
Feature: airpay vyaapaar aeps screens
  Xpaths for this module live in screens/aeps/.
  Each screen was captured once during the deep scan.

  @AEPS_001
  Scenario: Credit Balance / AEPS
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-aeps" in module "aeps" has stored xpaths

