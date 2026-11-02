@airpay-vyaapaar @vyaapaar @android @a11y @live
Feature: airpay vyaapaar live screens
  Xpaths for this module live in screens/live/.
  Each screen was captured once during the deep scan.

  @LIVE_001
  Scenario: Home after login
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "live-post-login-1-home-after-login" in module "live" has stored xpaths

