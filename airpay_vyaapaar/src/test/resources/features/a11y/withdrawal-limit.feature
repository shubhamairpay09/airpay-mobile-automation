@airpay-vyaapaar @vyaapaar @android @a11y @withdrawal-limit
Feature: airpay vyaapaar withdrawal-limit screens
  Xpaths for this module live in screens/withdrawallimit/.
  Each screen was captured once during the deep scan.

  @WITHDRAWAL_LIMIT_001
  Scenario: Featured Services/Withdrawal Limit
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-withdrawal-limit" in module "withdrawallimit" has stored xpaths

  @WITHDRAWAL_LIMIT_002
  Scenario: Featured Services/Withdrawal Limit/RTGS
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-withdrawal-limit-rtgs" in module "withdrawallimit" has stored xpaths

  @WITHDRAWAL_LIMIT_003
  Scenario: Featured Services/Withdrawal Limit/RTGS/Transfer to Distributor
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-withdrawal-limit-rtgs-transfer-to-distributor" in module "withdrawallimit" has stored xpaths

  @WITHDRAWAL_LIMIT_004
  Scenario: Featured Services/Withdrawal Limit/RTGS/Transfer to Distributor/RTGS
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-withdrawal-limit-rtgs-transfer-to-distributor-rtgs" in module "withdrawallimit" has stored xpaths

  @WITHDRAWAL_LIMIT_005
  Scenario: Featured Services/Withdrawal Limit/RTGS/Transfer to Distributor/RTGS
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-withdrawal-limit-rtgs-transfer-to-distributor-rtgs-rtgs" in module "withdrawallimit" has stored xpaths

  @WITHDRAWAL_LIMIT_006
  Scenario: Featured Services/Withdrawal Limit/RTGS/Transfer to Distributor/RTGS/Transfer to Network
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-withdrawal-limit-rtgs-transfer-to-distributor-rtgs-rtgs-transfer-to" in module "withdrawallimit" has stored xpaths

