@airpay-money @money @android @a11y @capture @live-two
Feature: Live a11y for two different screens
  Operator opens screen 1 on the phone. Automation scans it (no taps).
  Operator navigates to a completely different screen 2. Automation scans it.
  One combined scorecard is written.

  @MONEY_TWO_001 @live-two
  Scenario: Capture two distinct live screens for accessibility
    Given the airpay money app is installed on the device
    When I capture two live accessibility screens "Live screen 1" then "Live screen 2"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
