@airpay-vyaapaar @vyaapaar @android @a11y @post-office
Feature: airpay vyaapaar post-office screens
  Xpaths for this module live in screens/postoffice/.
  Each screen was captured once during the deep scan.

  @POST_OFFICE_001
  Scenario: Featured Services/Post Office
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-post-office" in module "postoffice" has stored xpaths

  @POST_OFFICE_002
  Scenario: Featured Services/Post Office/India Post Office
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-post-office-india-post-office" in module "postoffice" has stored xpaths

  @POST_OFFICE_003
  Scenario: Featured Services/Post Office/India Post Office/BOOK
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-post-office-india-post-office-book" in module "postoffice" has stored xpaths

  @POST_OFFICE_004
  Scenario: Featured Services/Post Office/India Post Office/TRACK
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-post-office-india-post-office-track" in module "postoffice" has stored xpaths

