@airpay-vyaapaar @vyaapaar @android @a11y @services
Feature: airpay vyaapaar services screens
  Xpaths for this module live in screens/services/.
  Each screen was captured once during the deep scan.

  @SERVICES_001
  Scenario: Services
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "services" in module "services" has stored xpaths

  @SERVICES_002
  Scenario: Services/Link MIDs
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "services-link-mids" in module "services" has stored xpaths

