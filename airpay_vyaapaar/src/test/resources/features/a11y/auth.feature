@airpay-vyaapaar @vyaapaar @android @a11y @auth
Feature: airpay vyaapaar auth screens
  Xpaths for this module live in screens/auth/.
  Each screen was captured once during the deep scan.

  @AUTH_001
  Scenario: Login with OTP
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-login-with-otp" in module "auth" has stored xpaths

  @AUTH_002
  Scenario: Mobile Number
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-mobile-number" in module "auth" has stored xpaths

