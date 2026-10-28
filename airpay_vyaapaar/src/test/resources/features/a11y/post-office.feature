@airpay-vyaapaar @vyaapaar @android @a11y @post-office
Feature: airpay vyaapaar post-office screens
  Xpaths for this module live in screens/postoffice/.
  Each screen was captured once during the deep scan.

  @POST_OFFICE_001
  Scenario: OTP/Post Office
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-otp-post-office" in module "postoffice" has stored xpaths

  @POST_OFFICE_002
  Scenario: OTP/Post Office/India Post Office
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "auth-otp-post-office-india-post-office" in module "postoffice" has stored xpaths

