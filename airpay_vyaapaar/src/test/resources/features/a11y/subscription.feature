@airpay-vyaapaar @vyaapaar @android @a11y @subscription
Feature: airpay vyaapaar subscription screens
  Xpaths for this module live in screens/subscription/.
  Each screen was captured once during the deep scan.

  @SUBSCRIPTION_001
  Scenario: Non Channels/Subscription
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-subscription" in module "subscription" has stored xpaths

  @SUBSCRIPTION_002
  Scenario: Non Channels/Subscription/HATM
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-subscription-hatm" in module "subscription" has stored xpaths

  @SUBSCRIPTION_003
  Scenario: Non Channels/Subscription/DIGIKHATA
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-subscription-digikhata" in module "subscription" has stored xpaths

  @SUBSCRIPTION_004
  Scenario: Non Channels/Subscription/DMT
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-non-channels-subscription-dmt" in module "subscription" has stored xpaths

