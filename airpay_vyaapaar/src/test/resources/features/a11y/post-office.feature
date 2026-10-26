@airpay-vyaapaar @vyaapaar @android @a11y @post-office
Feature: airpay vyaapaar post-office screens
  Xpaths for this module live in screens/postoffice/.
  Each screen was captured once during the deep scan.

  @POST_OFFICE_001
  Scenario: Credit Balance / Post Office
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-post-office" in module "postoffice" has stored xpaths

  @POST_OFFICE_002
  Scenario: Credit Balance / Post Office / India Post Office
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-post-office-india-post-office" in module "postoffice" has stored xpaths

  @POST_OFFICE_003
  Scenario: Credit Balance / Post Office / India Post Office / BOOK
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-post-office-india-post-office-book" in module "postoffice" has stored xpaths

  @POST_OFFICE_004
  Scenario: Credit Balance / Post Office / India Post Office / BOOK / Estimate Tariff
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-post-office-india-post-office-book-estimate-tariff" in module "postoffice" has stored xpaths

  @POST_OFFICE_005
  Scenario: Credit Balance / Post Office / India Post Office / TRACK
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-credit-balance-post-office-india-post-office-track" in module "postoffice" has stored xpaths

