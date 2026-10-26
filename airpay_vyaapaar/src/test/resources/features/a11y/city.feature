@airpay-vyaapaar @vyaapaar @android @a11y @city
Feature: airpay vyaapaar city screens
  Xpaths for this module live in screens/city/.
  Each screen was captured once during the deep scan.

  @CITY_001
  Scenario: Shop Details / City — Select City
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-shop-details-city" in module "city" has stored xpaths

  @CITY_002
  Scenario: Shop Details / City / Saharanpur
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-shop-details-city-saharanpur" in module "city" has stored xpaths

