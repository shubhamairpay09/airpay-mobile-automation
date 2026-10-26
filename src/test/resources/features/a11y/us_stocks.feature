@airpay-money @money @android @a11y @capture @us-stocks
Feature: Capture US Stocks screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @US_STOCKS_001
  Scenario: US Stocks screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-usstk-01.xml" as "usstk 01"
    And I capture saved hierarchy dump "uidump-usstk-02-scroll.xml" as "usstk 02 scroll"
    And I capture saved hierarchy dump "uidump-usstk-03-login.xml" as "usstk 03 login"
    And I capture saved hierarchy dump "uidump-usstk-04-signup.xml" as "usstk 04 signup"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @US_STOCKS_002
  Scenario: US Stocks login and account screens
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-us-0.xml" as "us 0"
    And I capture saved hierarchy dump "uidump-us-1.xml" as "us 1"
    And I capture saved hierarchy dump "uidump-us-2.xml" as "us 2"
    And I capture saved hierarchy dump "uidump-us-login.xml" as "us login"
    And I capture saved hierarchy dump "uidump-us-login-empty-error.xml" as "us login empty error"
    And I capture saved hierarchy dump "uidump-us-login-filled.xml" as "us login filled"
    And I capture saved hierarchy dump "uidump-us-login-pwd-masked.xml" as "us login pwd masked"
    And I capture saved hierarchy dump "uidump-us-login-pwd-visible.xml" as "us login pwd visible"
    And I capture saved hierarchy dump "uidump-us-nav-check.xml" as "us nav check"
    And I capture saved hierarchy dump "uidump-us-signup-0.xml" as "us signup 0"
    And I capture saved hierarchy dump "uidump-us-signup-country.xml" as "us signup country"
    And I capture saved hierarchy dump "uidump-us-signup-empty-error.xml" as "us signup empty error"
    And I capture saved hierarchy dump "uidump-us-signup-top.xml" as "us signup top"
    And I capture saved hierarchy dump "uidump-us-stockal-signup.xml" as "us stockal signup"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

