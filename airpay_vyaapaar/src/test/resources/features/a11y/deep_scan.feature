@airpay-vyaapaar @vyaapaar @android @a11y @deep
Feature: airpay vyaapaar full accessibility deep scan
  The app session stays on the phone. Each screen is walked once.
  Order: back to login, register a new user, login with OTP, then every module.
  Username and password login is a separate scenario after this scan.

  @VYAAPAAR_001 @deep
  Scenario: Scan every reachable vyaapaar screen once
    Given the airpay vyaapaar app is installed on the device
    When I deep-scan every airpay vyaapaar screen once
    Then scorecards and page xpaths are written for airpay vyaapaar
    And critical and serious vyaapaar findings are within the configured gate
