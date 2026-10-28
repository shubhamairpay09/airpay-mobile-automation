@airpay-vyaapaar @vyaapaar @android @a11y @history
Feature: airpay vyaapaar history screens
  Xpaths for this module live in screens/history/.
  Each screen was captured once during the deep scan.

  @HISTORY_001
  Scenario: History
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "history" in module "history" has stored xpaths

  @HISTORY_002
  Scenario: History/Filter Icon
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "history-dropdown-filter-icon" in module "history" has stored xpaths

  @HISTORY_003
  Scenario: History/Filter Icon/Apply
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "history-dropdown-filter-icon-apply" in module "history" has stored xpaths

