@airpay-vyaapaar @vyaapaar @android @a11y @register
Feature: airpay vyaapaar register screens
  Xpaths for this module live in screens/register/.
  Each screen was captured once during the deep scan.

  @REGISTER_001
  Scenario: Register — Welcome image
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "register-welcome-image" in module "register" has stored xpaths

  @REGISTER_002
  Scenario: Register — Verify OTP
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "register-verify-otp" in module "register" has stored xpaths

  @REGISTER_003
  Scenario: Register — If you didn't receive a OTP!  Resend
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "register-if-you-didn-t-receive-a-otp-resend" in module "register" has stored xpaths

