@airpay-vyaapaar @vyaapaar @android @a11y @upi-qr
Feature: airpay vyaapaar upi-qr screens
  Xpaths for this module live in screens/upiqr/.
  Each screen was captured once during the deep scan.

  @UPI_QR_001
  Scenario: Non Channels/UPI QR
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-upi-qr" in module "upiqr" has stored xpaths

