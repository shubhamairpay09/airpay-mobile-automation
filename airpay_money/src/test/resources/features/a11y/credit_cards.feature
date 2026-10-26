@airpay-money @money @android @a11y @capture @credit-cards
Feature: Capture Credit Card screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @CREDIT_CARDS_001
  Scenario: Credit Cards screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-credit-cc-01.xml" as "credit cc 01"
    And I capture saved hierarchy dump "uidump-credit-cc-02-scroll.xml" as "credit cc 02 scroll"
    And I capture saved hierarchy dump "uidump-credit-cc-11-Secured-Credit-Card-10-A-sec.xml" as "credit cc 11 Secured Credit Card 10 A sec"
    And I capture saved hierarchy dump "uidump-credit-cc-12-Unsecured-Credit-Card-10-An-.xml" as "credit cc 12 Unsecured Credit Card 10 An "
    And I capture saved hierarchy dump "uidump-credit-cc-landing.xml" as "credit cc landing"
    And I capture saved hierarchy dump "uidump-credit-cc-terms.xml" as "credit cc terms"
    And I capture saved hierarchy dump "uidump-credit-gl-01.xml" as "credit gl 01"
    And I capture saved hierarchy dump "uidump-credit-gl-02-scroll.xml" as "credit gl 02 scroll"
    And I capture saved hierarchy dump "uidump-credit-hl-01.xml" as "credit hl 01"
    And I capture saved hierarchy dump "uidump-credit-lap-01.xml" as "credit lap 01"
    And I capture saved hierarchy dump "uidump-credit-pl-01.xml" as "credit pl 01"
    And I capture saved hierarchy dump "uidump-credit-pl-02-scroll.xml" as "credit pl 02 scroll"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
