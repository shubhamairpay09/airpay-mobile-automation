@airpay-vyaapaar @vyaapaar @android @a11y @auth
Feature: airpay vyaapaar auth screens
  Xpaths for this module live in screens/auth/.
  Each screen was captured once during the deep scan.

  @AUTH_001
  Scenario: Login detail — M101319
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-m101319-detail" in module "auth" has stored xpaths

  @AUTH_002
  Scenario: Forgot Password — Forgot Password
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-forgot-password" in module "auth" has stored xpaths

  @AUTH_003
  Scenario: Login with password
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-login-with-password" in module "auth" has stored xpaths

