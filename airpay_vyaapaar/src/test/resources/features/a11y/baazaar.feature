@airpay-vyaapaar @vyaapaar @android @a11y @baazaar
Feature: airpay vyaapaar baazaar screens
  Xpaths for this module live in screens/baazaar/.
  Each screen was captured once during the deep scan.

  @BAAZAAR_001
  Scenario: Non Channels/Baazaar
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-baazaar" in module "baazaar" has stored xpaths

  @BAAZAAR_002
  Scenario: Non Channels/Baazaar/Search oil, almonds, earphones here
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-baazaar-search-oil-almonds-earphones-here" in module "baazaar" has stored xpaths

