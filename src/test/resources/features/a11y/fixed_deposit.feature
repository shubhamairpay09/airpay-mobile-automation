@airpay-money @money @android @a11y @capture @fixed-deposit
Feature: Capture Fixed Deposit screen xpaths and a11y
  Operator taps. Automation only dumps locators and accessibility — no clicks.

  @MONEY_FD_001
  Scenario: Capture Discover Fixed Deposits landing, senior citizen toggle, year and category filters
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-fd-landing.xml" as "FD Landing Discover Fixed Deposits"
    And I capture saved hierarchy dump "uidump-fd-1.xml" as "FD Landing scrolled plan cards"
    And I capture saved hierarchy dump "uidump-fd-senior-on.xml" as "FD Landing Senior Citizen toggle on"
    And I capture saved hierarchy dump "uidump-fd-yr-01.xml" as "FD Landing year filter 0-1 year"
    And I capture saved hierarchy dump "uidump-fd-yr-13.xml" as "FD Landing year filter 1-3 year"
    And I capture saved hierarchy dump "uidump-fd-yr-3.xml" as "FD Landing year filter above 3 years"
    And I capture saved hierarchy dump "uidump-fd-cat-women.xml" as "FD Landing category Women Benefits"
    And I capture saved hierarchy dump "uidump-fd-cat-rates.xml" as "FD Landing category Best Rates"
    And I capture saved hierarchy dump "uidump-fd-cat-popular.xml" as "FD Landing category Popular FDs"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @MONEY_FD_002
  Scenario: Capture in-app FD plan booking, tenure sheet, payout and renewal dropdowns and amount sheet
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-fd-suryoday-0.xml" as "FD Plan Suryoday booking details"
    And I capture saved hierarchy dump "uidump-fd-suryoday-1.xml" as "FD Plan Suryoday booking scrolled"
    And I capture saved hierarchy dump "uidump-fd-suryoday-tenures.xml" as "FD Plan Select FD Option All tenures"
    And I capture saved hierarchy dump "uidump-fd-suryoday-tenures-01.xml" as "FD Plan Select FD Option 0-1 Year"
    And I capture saved hierarchy dump "uidump-fd-suryoday-tenures-13.xml" as "FD Plan Select FD Option 1-3 Years"
    And I capture saved hierarchy dump "uidump-fd-suryoday-tenures-35.xml" as "FD Plan Select FD Option 3-5 Years"
    And I capture saved hierarchy dump "uidump-fd-suryoday-payout.xml" as "FD Plan Interest Payout dropdown"
    And I capture saved hierarchy dump "uidump-fd-suryoday-renewal.xml" as "FD Plan Renewal Preference dropdown"
    And I capture saved hierarchy dump "uidump-fd-suryoday-amount.xml" as "FD Plan Investment amount sheet"
    And I capture saved hierarchy dump "uidump-fd-suryoday-ready.xml" as "FD Plan Suryoday ready to book"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @MONEY_FD_003
  Scenario: Capture Fixerra booking WebView tenures, amount, payout, renewal and Aadhaar hand-off
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-fd-suryoday-back-from-aadhaar.xml" as "FD Fixerra Suryoday booking page"
    And I capture saved hierarchy dump "uidump-fdx-tenures.xml" as "FD Fixerra All FD Rates sorted by rate"
    And I capture saved hierarchy dump "uidump-fdx-tenures-sort-tenure.xml" as "FD Fixerra All FD Rates sorted by tenure"
    And I capture saved hierarchy dump "uidump-fdx-tenures-selected.xml" as "FD Fixerra All FD Rates Tax Saver selected"
    And I capture saved hierarchy dump "uidump-fdx-after-tenure-save.xml" as "FD Fixerra booking page after tenure save"
    And I capture saved hierarchy dump "uidump-fdx-payout.xml" as "FD Fixerra Interest Payout sheet"
    And I capture saved hierarchy dump "uidump-fdx-payout-monthly.xml" as "FD Fixerra booking page monthly payout"
    And I capture saved hierarchy dump "uidump-fdx-renewal.xml" as "FD Fixerra Renewal Preference sheet"
    And I capture saved hierarchy dump "uidump-fdx-amount-focus.xml" as "FD Fixerra amount field focused"
    And I capture saved hierarchy dump "uidump-fdx-amount-below-min.xml" as "FD Fixerra amount below minimum error"
    And I capture saved hierarchy dump "uidump-fdx-amount-preset-1l.xml" as "FD Fixerra amount preset 1L applied"
    And I capture saved hierarchy dump "uidump-fdx-yield-info.xml" as "FD Fixerra Annual Yield explainer"
    And I capture saved hierarchy dump "uidump-fdx-banner.xml" as "FD Fixerra Break Anytime No Penalty sheet"
    And I capture saved hierarchy dump "uidump-fd-suryoday-aadhaar.xml" as "FD Fixerra Aadhaar Verification hand-off"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @MONEY_FD_004
  Scenario: Fixed Deposit booking fields not listed above
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-fdx-after-penalty-save.xml" as "fdx after penalty save"
    And I capture saved hierarchy dump "uidump-fdx-amount-1l.xml" as "fdx amount 1l"
    And I capture saved hierarchy dump "uidump-fdx-amount-error-nokb.xml" as "fdx amount error nokb"
    And I capture saved hierarchy dump "uidump-fdx-penalty-quarterly-selected.xml" as "fdx penalty quarterly selected"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

