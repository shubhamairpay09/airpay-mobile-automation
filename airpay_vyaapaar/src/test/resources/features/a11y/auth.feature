@airpay-vyaapaar @vyaapaar @android @a11y @auth
Feature: airpay vyaapaar auth screens
  Xpaths for this module live in screens/auth/.
  Each screen was captured once during the deep scan.

  @AUTH_001
  Scenario: Login detail — Username or Mobile number
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-username-or-mobile-number-detail" in module "auth" has stored xpaths

  @AUTH_002
  Scenario: Login filled — Username or Mobile number
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-username-or-mobile-number-filled" in module "auth" has stored xpaths

  @AUTH_003
  Scenario: Forgot Password — Forgot Password
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-forgot-password" in module "auth" has stored xpaths

  @AUTH_004
  Scenario: Login
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-login-wall" in module "auth" has stored xpaths

  @AUTH_005
  Scenario: Login with password
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-login-with-password" in module "auth" has stored xpaths

  @AUTH_006
  Scenario: OTP
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-otp" in module "auth" has stored xpaths

