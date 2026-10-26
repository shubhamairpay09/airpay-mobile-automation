@airpay-vyaapaar @vyaapaar @android @a11y @register
Feature: airpay vyaapaar register screens
  Xpaths for this module live in screens/register/.
  Each screen was captured once during the deep scan.

  @REGISTER_001
  Scenario: Register — Welcome image
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "register-welcome-image" in module "register" has stored xpaths

  @REGISTER_002
  Scenario: Register / airpay
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "register-privacy-policy" in module "register" has stored xpaths

