@airpay-vyaapaar @vyaapaar @android @a11y @aadhaar-pay
Feature: airpay vyaapaar aadhaar-pay screens
  Xpaths for this module live in screens/aadhaarpay/.
  Each screen was captured once during the deep scan.

  @AADHAAR_PAY_001
  Scenario: Featured Services/Aadhaar Pay
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "home-featured-services-aadhaar-pay" in module "aadhaarpay" has stored xpaths

