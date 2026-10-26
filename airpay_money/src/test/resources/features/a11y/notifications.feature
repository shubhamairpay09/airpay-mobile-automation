@airpay-money @money @android @a11y @capture @notifications
Feature: Capture Notification screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @NOTIFICATIONS_001
  Scenario: Notifications screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-notif-01-list.xml" as "notif 01 list"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
