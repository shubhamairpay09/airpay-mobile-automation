@airpay-vyaapaar @vyaapaar @android @a11y @aeps
Feature: airpay vyaapaar aeps screens
  Xpaths for this module live in screens/aeps/.
  Each screen was captured once during the deep scan.

  @AEPS_001
  Scenario: Credit Balance / AEPS
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-aeps" in module "aeps" has stored xpaths

  @AEPS_002
  Scenario: Credit Balance / AEPS / Select partner bank
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-aeps-dropdown-select-partner-bank" in module "aeps" has stored xpaths

  @AEPS_003
  Scenario: Credit Balance / AEPS / Select partner bank / P2
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-aeps-dropdown-select-partner-bank-p2" in module "aeps" has stored xpaths

  @AEPS_004
  Scenario: Credit Balance / AEPS / Visibility Off Icon
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-aeps-visibility-off-icon" in module "aeps" has stored xpaths

  @AEPS_005
  Scenario: Credit Balance / AEPS / Aadhaar Pay
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-aeps-aadhaar-pay" in module "aeps" has stored xpaths

  @AEPS_006
  Scenario: Credit Balance / AEPS / CMS
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-aeps-cms" in module "aeps" has stored xpaths

  @AEPS_007
  Scenario: Credit Balance / AEPS / CMS / Cash Drop
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-aeps-cms-cash-drop" in module "aeps" has stored xpaths

  @AEPS_008
  Scenario: Credit Balance / AEPS / CMS / Cash Drop / AC
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-aeps-cms-cash-drop-ac" in module "aeps" has stored xpaths

