@airpay-vyaapaar @vyaapaar @android @a11y @history
Feature: airpay vyaapaar history screens
  Xpaths for this module live in screens/history/.
  Each screen was captured once during the deep scan.

  @HISTORY_001
  Scenario: Withdrawal Limit / History — No records found
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history" in module "history" has stored xpaths

  @HISTORY_002
  Scenario: Withdrawal Limit / History / Filter Icon
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon" in module "history" has stored xpaths

  @HISTORY_003
  Scenario: airpay vyaapaar
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon-open-apply" in module "history" has stored xpaths

  @HISTORY_004
  Scenario: airpay vyaapaar / Visibility Off Icon
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon-open-apply-visibility-off-icon" in module "history" has stored xpaths

  @HISTORY_005
  Scenario: airpay vyaapaar / Add Credits — Select Payment Type
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon-open-apply-add-credits" in module "history" has stored xpaths

  @HISTORY_006
  Scenario: airpay vyaapaar / PAN Application
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon-open-apply-pan-application" in module "history" has stored xpaths

  @HISTORY_007
  Scenario: airpay vyaapaar / PAN Application / DOB
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon-open-apply-pan-application-dob" in module "history" has stored xpaths

  @HISTORY_008
  Scenario: airpay vyaapaar / Post Office
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon-open-apply-post-office" in module "history" has stored xpaths

  @HISTORY_009
  Scenario: airpay vyaapaar / Post Office / India Post Office / TRACK
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon-open-apply-post-office-india-post-offic" in module "history" has stored xpaths

  @HISTORY_010
  Scenario: airpay vyaapaar / AEPS
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon-open-apply-aeps" in module "history" has stored xpaths

  @HISTORY_011
  Scenario: airpay vyaapaar / AEPS / P2
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon-open-apply-aeps-p2" in module "history" has stored xpaths

  @HISTORY_012
  Scenario: airpay vyaapaar / AEPS / P2 / P2
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon-open-apply-aeps-p2-p2" in module "history" has stored xpaths

  @HISTORY_013
  Scenario: airpay vyaapaar / Aadhaar Pay
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-withdrawal-limit-history-dropdown-filter-icon-open-apply-aadhaar-pay" in module "history" has stored xpaths

