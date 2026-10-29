@airpay-vyaapaar @vyaapaar @android @a11y @aeps
Feature: airpay vyaapaar aeps screens
  Xpaths for this module live in screens/aeps/.
  Each screen was captured once during the deep scan.

  @AEPS_001
  Scenario: OTP/AEPS
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-otp-aeps" in module "aeps" has stored xpaths

  @AEPS_002
  Scenario: OTP/AEPS/P2
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-otp-aeps-p2" in module "aeps" has stored xpaths

