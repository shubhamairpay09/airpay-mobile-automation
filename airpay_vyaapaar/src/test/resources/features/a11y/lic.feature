@airpay-vyaapaar @vyaapaar @android @a11y @lic
Feature: airpay vyaapaar lic screens
  Xpaths for this module live in screens/lic/.
  Each screen was captured once during the deep scan.

  @LIC_001
  Scenario: Credit Balance / LIC — LIC
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-lic" in module "lic" has stored xpaths

  @LIC_002
  Scenario: Credit Balance / LIC / Date of Birth — Close sheet
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-lic-date-of-birth" in module "lic" has stored xpaths

