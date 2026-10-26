@airpay-money @money @android @a11y
Feature: airpay money login accessibility scan
  Start on Login. Open Privacy Policy, then Terms & Conditions.
  If Menu is present, scan hamburger (Start FHC, What is FHC, How it works, Start FHC,
  Resources: About Us, Contact Us, Testimonials). Then header Login popup, X, Back,
  Login with Mobile Number, OTP, then remaining modules.

  @MONEY_001 @login @deep
  Scenario: Scan every money screen from login
    Given the airpay money app is installed on the device
    When I scan all airpay money screens from login
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
