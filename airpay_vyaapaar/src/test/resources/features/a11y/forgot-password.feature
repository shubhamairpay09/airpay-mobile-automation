@airpay-vyaapaar @vyaapaar @android @a11y @forgot-password
Feature: airpay vyaapaar forgot-password screens
  Xpaths for this module live in screens/forgotpassword/.
  Each screen was captured once during the deep scan.

  @FORGOT_PASSWORD_001
  Scenario: Forgot Password — Forgot Password
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "forgot-password-forgot-password" in module "forgotpassword" has stored xpaths

  @FORGOT_PASSWORD_002
  Scenario: Forgot Password — Screen
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "forgot-password-screen" in module "forgotpassword" has stored xpaths

