@airpay-money @money @android @a11y @capture @home
Feature: Capture Home, hub and saved form screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @HOME_001
  Scenario: Home and form screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-home.xml" as "home"
    And I capture saved hierarchy dump "uidump-hub-top.xml" as "hub top"
    And I capture saved hierarchy dump "uidump-products.xml" as "products"
    And I capture saved hierarchy dump "uidump-financial-health-score.xml" as "financial health score"
    And I capture saved hierarchy dump "uidump-pin-unlock.xml" as "pin unlock"
    And I capture saved hierarchy dump "uidump-deep-01-address.xml" as "deep 01 address"
    And I capture saved hierarchy dump "uidump-deep-02-address-pin.xml" as "deep 02 address pin"
    And I capture saved hierarchy dump "uidump-deep-03-address-line1.xml" as "deep 03 address line1"
    And I capture saved hierarchy dump "uidump-deep-04-address-line2.xml" as "deep 04 address line2"
    And I capture saved hierarchy dump "uidump-deep-05-city.xml" as "deep 05 city"
    And I capture saved hierarchy dump "uidump-deep-06-state.xml" as "deep 06 state"
    And I capture saved hierarchy dump "uidump-deep-07-after-address.xml" as "deep 07 after address"
    And I capture saved hierarchy dump "uidump-deep-08-step0-Continue.xml" as "deep 08 step0 Continue"
    And I capture saved hierarchy dump "uidump-deep-09-step1-Continue.xml" as "deep 09 step1 Continue"
    And I capture saved hierarchy dump "uidump-deep-10-salary.xml" as "deep 10 salary"
    And I capture saved hierarchy dump "uidump-deep-11-emi.xml" as "deep 11 emi"
    And I capture saved hierarchy dump "uidump-deep-12-salary-mode.xml" as "deep 12 salary mode"
    And I capture saved hierarchy dump "uidump-deep-13-employer.xml" as "deep 13 employer"
    And I capture saved hierarchy dump "uidump-deep-14-work-scrolled.xml" as "deep 14 work scrolled"
    And I capture saved hierarchy dump "uidump-deep-15-years.xml" as "deep 15 years"
    And I capture saved hierarchy dump "uidump-deep-16-months.xml" as "deep 16 months"
    And I capture saved hierarchy dump "uidump-deep-17-work-filled.xml" as "deep 17 work filled"
    And I capture saved hierarchy dump "uidump-deep-18-after-work.xml" as "deep 18 after work"
    And I capture saved hierarchy dump "uidump-deep-19-salary-sheet.xml" as "deep 19 salary sheet"
    And I capture saved hierarchy dump "uidump-deep-20-salary-sheet-scroll.xml" as "deep 20 salary sheet scroll"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @HOME_002
  Scenario: Home and form screens part 2
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-deep-21-employer-sheet.xml" as "deep 21 employer sheet"
    And I capture saved hierarchy dump "uidump-deep-22-employer-sheet-scroll.xml" as "deep 22 employer sheet scroll"
    And I capture saved hierarchy dump "uidump-deep-23-years-again.xml" as "deep 23 years again"
    And I capture saved hierarchy dump "uidump-deep-24-years-scroll.xml" as "deep 24 years scroll"
    And I capture saved hierarchy dump "uidump-deep-25-work-ready.xml" as "deep 25 work ready"
    And I capture saved hierarchy dump "uidump-deep-26-after-continue.xml" as "deep 26 after continue"
    And I capture saved hierarchy dump "uidump-deep-27-property-type.xml" as "deep 27 property type"
    And I capture saved hierarchy dump "uidump-deep-28-local-body.xml" as "deep 28 local body"
    And I capture saved hierarchy dump "uidump-deep-29-additional-scrolled.xml" as "deep 29 additional scrolled"
    And I capture saved hierarchy dump "uidump-deep-30-additional-filled.xml" as "deep 30 additional filled"
    And I capture saved hierarchy dump "uidump-deep-31-after-eligibility.xml" as "deep 31 after eligibility"
    And I capture saved hierarchy dump "uidump-deep-32-value-fixed.xml" as "deep 32 value fixed"
    And I capture saved hierarchy dump "uidump-deep-33-tenure-visible.xml" as "deep 33 tenure visible"
    And I capture saved hierarchy dump "uidump-deep-34-ready-eligibility.xml" as "deep 34 ready eligibility"
    And I capture saved hierarchy dump "uidump-deep-35-eligibility-result.xml" as "deep 35 eligibility result"
    And I capture saved hierarchy dump "uidump-deep-36-retry.xml" as "deep 36 retry"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
