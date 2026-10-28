@airpay-vyaapaar @vyaapaar @android @a11y @aeps
Feature: airpay vyaapaar aeps screens
  Xpaths for this module live in screens/aeps/.
  Each screen was captured once during the deep scan.

  @AEPS_001
  Scenario: Featured Services/AEPS
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-aeps" in module "aeps" has stored xpaths

  @AEPS_002
  Scenario: Featured Services/AEPS/P2
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-aeps-p2" in module "aeps" has stored xpaths

