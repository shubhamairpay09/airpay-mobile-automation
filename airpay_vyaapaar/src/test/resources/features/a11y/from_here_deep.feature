@airpay-vyaapaar @vyaapaar @android @a11y @from-here
Feature: Deep a11y from the currently open screen
  Starts on whatever screen is already open (no login / no Home reset).
  Covers edits, filters, dropdowns, date controls, then next screens.

  @VYAAPAAR_FROM_001 @from-here
  Scenario: Deep-scan from the open screen through nested controls
    Given the airpay vyaapaar app is installed on the device
    When I deep-scan every airpay vyaapaar screen once
    Then scorecards and page xpaths are written for airpay vyaapaar
    And critical and serious vyaapaar findings are within the configured gate
