@airpay-vyaapaar @vyaapaar @android @a11y @money-transfer
Feature: airpay vyaapaar money-transfer screens
  Xpaths for this module live in screens/moneytransfer/.
  Each screen was captured once during the deep scan.

  @MONEY_TRANSFER_001
  Scenario: OTP/Money Transfer
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-otp-money-transfer" in module "moneytransfer" has stored xpaths

  @MONEY_TRANSFER_002
  Scenario: OTP/Money Transfer (filled)
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-otp-money-transfer-filled" in module "moneytransfer" has stored xpaths

