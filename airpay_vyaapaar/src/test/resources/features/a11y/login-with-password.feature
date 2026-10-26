@airpay-vyaapaar @vyaapaar @android @a11y @login-with-password
Feature: airpay vyaapaar login-with-password screens
  Xpaths for this module live in screens/loginwithpassword/.
  Each screen was captured once during the deep scan.

  @LOGIN_WITH_PASSWORD_001
  Scenario: Mobile Number / Login with password
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-welcome-to-airpay-vyaapaar-open-continue-login-with-password" in module "loginwithpassword" has stored xpaths

  @LOGIN_WITH_PASSWORD_002
  Scenario: Mobile Number / Login with password / Forgot Password?
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-welcome-to-airpay-vyaapaar-open-continue-login-with-password-forgot-password" in module "loginwithpassword" has stored xpaths

