@airpay-vyaapaar @vyaapaar @android @a11y @finance
Feature: airpay vyaapaar finance screens
  Xpaths for this module live in screens/finance/.
  Each screen was captured once during the deep scan.

  @FINANCE_001
  Scenario: Non Channels/Finance
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-finance" in module "finance" has stored xpaths

  @FINANCE_002
  Scenario: Non Channels/Finance/Secured Credit Cards
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-finance-secured-credit-cards" in module "finance" has stored xpaths

  @FINANCE_003
  Scenario: Non Channels/Finance/Secured Credit Cards/Credilio Cards
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-finance-secured-credit-cards-credilio-cards" in module "finance" has stored xpaths

  @FINANCE_004
  Scenario: Non Channels/Finance/Unsecured Credit Cards
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-finance-unsecured-credit-cards" in module "finance" has stored xpaths

  @FINANCE_005
  Scenario: Non Channels/Finance/Unsecured Credit Cards/HDFC
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-finance-unsecured-credit-cards-hdfc" in module "finance" has stored xpaths

  @FINANCE_006
  Scenario: Non Channels/Finance/Unsecured Credit Cards/Credilio
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-finance-unsecured-credit-cards-credilio" in module "finance" has stored xpaths

