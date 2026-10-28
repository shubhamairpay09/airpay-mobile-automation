@airpay-vyaapaar @vyaapaar @android @a11y @collect-money
Feature: airpay vyaapaar collect-money screens
  Xpaths for this module live in screens/collectmoney/.
  Each screen was captured once during the deep scan.

  @COLLECT_MONEY_001
  Scenario: Non Channels/Collect Money
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-collect-money" in module "collectmoney" has stored xpaths

