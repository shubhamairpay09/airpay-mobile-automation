@airpay-vyaapaar @vyaapaar @android @a11y @certificate
Feature: airpay vyaapaar certificate screens
  Xpaths for this module live in screens/certificate/.
  Each screen was captured once during the deep scan.

  @CERTIFICATE_001
  Scenario: Non Channels/Certificate
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-certificate" in module "certificate" has stored xpaths

