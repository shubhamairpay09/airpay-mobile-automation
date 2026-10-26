@airpay-money @money @android @a11y @capture @finsight
Feature: Capture FinSight screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @FINSIGHT_001
  Scenario: FinSight screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-fh-01-entry.xml" as "fh 01 entry"
    And I capture saved hierarchy dump "uidump-fh-02-dob-picker.xml" as "fh 02 dob picker"
    And I capture saved hierarchy dump "uidump-fh-03-dependant.xml" as "fh 03 dependant"
    And I capture saved hierarchy dump "uidump-fh-04-dependant-scrolled.xml" as "fh 04 dependant scrolled"
    And I capture saved hierarchy dump "uidump-fh-05-dependant-end.xml" as "fh 05 dependant end"
    And I capture saved hierarchy dump "uidump-fh-06-next-empty-pincode.xml" as "fh 06 next empty pincode"
    And I capture saved hierarchy dump "uidump-fh-07-pincode-filled.xml" as "fh 07 pincode filled"
    And I capture saved hierarchy dump "uidump-fh-08-income-expense.xml" as "fh 08 income expense"
    And I capture saved hierarchy dump "uidump-fh-09-income-filled.xml" as "fh 09 income filled"
    And I capture saved hierarchy dump "uidump-fh-10-loan-yes.xml" as "fh 10 loan yes"
    And I capture saved hierarchy dump "uidump-fh-11-add-loan.xml" as "fh 11 add loan"
    And I capture saved hierarchy dump "uidump-fh-12-loan-type.xml" as "fh 12 loan type"
    And I capture saved hierarchy dump "uidump-fh-13-loan-type-end.xml" as "fh 13 loan type end"
    And I capture saved hierarchy dump "uidump-fh-14-loan-add-empty.xml" as "fh 14 loan add empty"
    And I capture saved hierarchy dump "uidump-fh-15-loan-principal.xml" as "fh 15 loan principal"
    And I capture saved hierarchy dump "uidump-fh-16-loan-emi.xml" as "fh 16 loan emi"
    And I capture saved hierarchy dump "uidump-fh-17-loan-filled.xml" as "fh 17 loan filled"
    And I capture saved hierarchy dump "uidump-fh-18-loan-added.xml" as "fh 18 loan added"
    And I capture saved hierarchy dump "uidump-fh-19-insurance.xml" as "fh 19 insurance"
    And I capture saved hierarchy dump "uidump-fh-20-insurance-yes.xml" as "fh 20 insurance yes"
    And I capture saved hierarchy dump "uidump-fh-21-health-filled.xml" as "fh 21 health filled"
    And I capture saved hierarchy dump "uidump-fh-22-term-premium.xml" as "fh 22 term premium"
    And I capture saved hierarchy dump "uidump-fh-23-insurance-filled.xml" as "fh 23 insurance filled"
    And I capture saved hierarchy dump "uidump-fh-24-savings.xml" as "fh 24 savings"
    And I capture saved hierarchy dump "uidump-fh-25-investment-yes.xml" as "fh 25 investment yes"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @FINSIGHT_002
  Scenario: FinSight screens part 2
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-fh-26-add-investment.xml" as "fh 26 add investment"
    And I capture saved hierarchy dump "uidump-fh-27-investment-type.xml" as "fh 27 investment type"
    And I capture saved hierarchy dump "uidump-fh-28-investment-type-end.xml" as "fh 28 investment type end"
    And I capture saved hierarchy dump "uidump-fh-29-investment-filled.xml" as "fh 29 investment filled"
    And I capture saved hierarchy dump "uidump-fh-30-goal-yes.xml" as "fh 30 goal yes"
    And I capture saved hierarchy dump "uidump-fh-31-new-goal.xml" as "fh 31 new goal"
    And I capture saved hierarchy dump "uidump-fh-32-goal-icons.xml" as "fh 32 goal icons"
    And I capture saved hierarchy dump "uidump-fh-33-goal-icons-2.xml" as "fh 33 goal icons 2"
    And I capture saved hierarchy dump "uidump-fh-34-goal-icons-3.xml" as "fh 34 goal icons 3"
    And I capture saved hierarchy dump "uidump-fh-35-goal-icons-4.xml" as "fh 35 goal icons 4"
    And I capture saved hierarchy dump "uidump-fh-36-goal-icons-5.xml" as "fh 36 goal icons 5"
    And I capture saved hierarchy dump "uidump-fh-37-goal-icons-6.xml" as "fh 37 goal icons 6"
    And I capture saved hierarchy dump "uidump-fh-38-existing-goals.xml" as "fh 38 existing goals"
    And I capture saved hierarchy dump "uidump-fh-39-investment-added.xml" as "fh 39 investment added"
    And I capture saved hierarchy dump "uidump-fh-40-risk-profile.xml" as "fh 40 risk profile"
    And I capture saved hierarchy dump "uidump-fh-41-already-know.xml" as "fh 41 already know"
    And I capture saved hierarchy dump "uidump-fh-42-risk-mod-aggressive.xml" as "fh 42 risk mod aggressive"
    And I capture saved hierarchy dump "uidump-fh-43-risk-balanced.xml" as "fh 43 risk balanced"
    And I capture saved hierarchy dump "uidump-fh-44-risk-balanced-sel.xml" as "fh 44 risk balanced sel"
    And I capture saved hierarchy dump "uidump-fh-45-risk-mod-conservative.xml" as "fh 45 risk mod conservative"
    And I capture saved hierarchy dump "uidump-fh-46-back-to-intro.xml" as "fh 46 back to intro"
    And I capture saved hierarchy dump "uidump-fh-47-risk-q1.xml" as "fh 47 risk q1"
    And I capture saved hierarchy dump "uidump-fh-48-risk-q1-novalidate.xml" as "fh 48 risk q1 novalidate"
    And I capture saved hierarchy dump "uidump-fh-49-risk-q1-selected.xml" as "fh 49 risk q1 selected"
    And I capture saved hierarchy dump "uidump-fh-50-risk-q2.xml" as "fh 50 risk q2"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @FINSIGHT_003
  Scenario: FinSight screens part 3
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-fh-51-risk-q3.xml" as "fh 51 risk q3"
    And I capture saved hierarchy dump "uidump-fh-52-risk-q4.xml" as "fh 52 risk q4"
    And I capture saved hierarchy dump "uidump-fh-53-risk-q5.xml" as "fh 53 risk q5"
    And I capture saved hierarchy dump "uidump-fh-54-risk-result.xml" as "fh 54 risk result"
    And I capture saved hierarchy dump "uidump-fh-55-health-score.xml" as "fh 55 health score"
    And I capture saved hierarchy dump "uidump-fh-56-after-risk-next.xml" as "fh 56 after risk next"
    And I capture saved hierarchy dump "uidump-fh-57-summary-scrolled.xml" as "fh 57 summary scrolled"
    And I capture saved hierarchy dump "uidump-fh-58-score.xml" as "fh 58 score"
    And I capture saved hierarchy dump "uidump-fh-59-diagnosis.xml" as "fh 59 diagnosis"
    And I capture saved hierarchy dump "uidump-fh-60-diagnosis-gaps.xml" as "fh 60 diagnosis gaps"
    And I capture saved hierarchy dump "uidump-fh-61-protection-scrolled.xml" as "fh 61 protection scrolled"
    And I capture saved hierarchy dump "uidump-fh-62-diag-03.xml" as "fh 62 diag 03"
    And I capture saved hierarchy dump "uidump-fh-63-diag-04.xml" as "fh 63 diag 04"
    And I capture saved hierarchy dump "uidump-fh-64-diag-05.xml" as "fh 64 diag 05"
    And I capture saved hierarchy dump "uidump-fh-65-diag-06.xml" as "fh 65 diag 06"
    And I capture saved hierarchy dump "uidump-fh-66-diag-07.xml" as "fh 66 diag 07"
    And I capture saved hierarchy dump "uidump-fh-67-diag-08.xml" as "fh 67 diag 08"
    And I capture saved hierarchy dump "uidump-fh-68-plan-scrolled.xml" as "fh 68 plan scrolled"
    And I capture saved hierarchy dump "uidump-fh-state.xml" as "fh state"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
