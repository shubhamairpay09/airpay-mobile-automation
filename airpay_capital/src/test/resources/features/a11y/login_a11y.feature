@airpay-capital @capital @android @a11y
Feature: Airpay Capital login and full-module accessibility deep scan
  Login with mobile OTP, then deep-scan every module screen:
  Home, Profile, Notifications, Info (scrolled), Personal/Home/Gold/LAP loans,
  Credit Card, Apply flows including Salaried / Self-employed and further steps.
  Locators live in Java *Screen classes; a11y reports under target/capital/accessibility_testing_result/.

  @CAPITAL_A11Y_001 @login @deep
  Scenario: Login with mobile and produce Capital accessibility reports for all modules
    Given the Airpay Capital app is installed on the device
    When I login with the configured mobile number and deep-scan accessibility
    Then accessibility scorecards are written for Capital
    And critical and serious accessibility findings are within the configured gate

  @CAPITAL_HL_OCC @home-loan @occupation @deep
  Scenario: Capture Home Loan Salaried and Self-Employed apply workflows from current screen
    Given the Airpay Capital app is installed on the device
    When I deep-scan Home Loan Salaried and Self-Employed apply paths from the current screen
    Then accessibility scorecards are written for Capital
    And critical and serious accessibility findings are within the configured gate

  @CAPITAL_ALL_OCC @all-products @occupation @deep
  Scenario: Capture all product Salaried and Self-Employed apply workflows from Home
    Given the Airpay Capital app is installed on the device
    When I deep-scan all Capital product Salaried and Self-Employed apply paths from the current screen
    Then accessibility scorecards are written for Capital
    And critical and serious accessibility findings are within the configured gate
