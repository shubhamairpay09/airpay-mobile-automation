@airpay-vyaapaar @vyaapaar @android @a11y @electricity
Feature: airpay vyaapaar electricity screens
  Xpaths for this module live in screens/electricity/.
  Each screen was captured once during the deep scan.

  @ELECTRICITY_001
  Scenario: Recharge & Billpay/Electricity
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-recharge-billpay-electricity" in module "electricity" has stored xpaths

