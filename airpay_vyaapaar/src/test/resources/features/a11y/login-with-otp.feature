@airpay-vyaapaar @vyaapaar @android @a11y @login-with-otp
Feature: airpay vyaapaar login-with-otp screens
  Xpaths for this module live in screens/loginwithotp/.
  Each screen was captured once during the deep scan.

  @LOGIN_WITH_OTP_001
  Scenario: Login Icon / Login with OTP
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-login-icon-login-with-otp" in module "loginwithotp" has stored xpaths

  @LOGIN_WITH_OTP_002
  Scenario: Login Icon / Login with OTP / Visibility Off Icon
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-login-icon-login-with-otp-visibility-off-icon" in module "loginwithotp" has stored xpaths

