@airpay-money @money @android @a11y @capture @loans
Feature: Capture Loan application screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @LOANS_001
  Scenario: Loans screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-apply-cc-01.xml" as "apply cc 01"
    And I capture saved hierarchy dump "uidump-apply-cc-02.xml" as "apply cc 02"
    And I capture saved hierarchy dump "uidump-apply-cc-03.xml" as "apply cc 03"
    And I capture saved hierarchy dump "uidump-apply-cc-04.xml" as "apply cc 04"
    And I capture saved hierarchy dump "uidump-apply-gl-01.xml" as "apply gl 01"
    And I capture saved hierarchy dump "uidump-apply-gl-02.xml" as "apply gl 02"
    And I capture saved hierarchy dump "uidump-apply-hl-01.xml" as "apply hl 01"
    And I capture saved hierarchy dump "uidump-apply-hl-02.xml" as "apply hl 02"
    And I capture saved hierarchy dump "uidump-apply-lap-01.xml" as "apply lap 01"
    And I capture saved hierarchy dump "uidump-apply-lap-02.xml" as "apply lap 02"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
