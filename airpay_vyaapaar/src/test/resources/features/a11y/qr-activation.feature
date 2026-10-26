@airpay-vyaapaar @vyaapaar @android @a11y @qr-activation
Feature: airpay vyaapaar qr-activation screens
  Xpaths for this module live in screens/qractivation/.
  Each screen was captured once during the deep scan.

  @QR_ACTIVATION_001
  Scenario: QR Activation — QR Code
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "profile-qr-activation" in module "qractivation" has stored xpaths

