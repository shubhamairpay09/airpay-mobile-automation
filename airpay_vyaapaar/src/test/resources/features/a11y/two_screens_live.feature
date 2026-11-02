@airpay-vyaapaar @vyaapaar @android @a11y @live-two
Feature: Live a11y for two post-login Vyaapaar screens
  Automation logs in with valid credentials first (login wall is not the target).
  Then it scans post-login screen 1, waits for you to open a different screen 2, and scans that.
  Scorecard: target/vyaapaar/accessibility_testing_result/

  @VYAAPAAR_TWO_001 @live-two
  Scenario: Capture two post-login screens for accessibility
    Given the airpay vyaapaar app is installed on the device
    When I deep-scan every airpay vyaapaar screen once
    Then scorecards and page xpaths are written for airpay vyaapaar
    And critical and serious vyaapaar findings are within the configured gate
