@airpay-vyaapaar @vyaapaar @android @a11y @pan-application
Feature: airpay vyaapaar pan-application screens
  Xpaths for this module live in screens/panapplication/.
  Each screen was captured once during the deep scan.

  @PAN_APPLICATION_001
  Scenario: Credit Balance / PAN Application
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-pan-application" in module "panapplication" has stored xpaths

  @PAN_APPLICATION_002
  Scenario: Credit Balance / PAN Application / DOB
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-pan-application-dob" in module "panapplication" has stored xpaths

