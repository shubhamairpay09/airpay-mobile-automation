@airpay-money @money @android @a11y @capture @insurance
Feature: Capture Insurance screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @INSURANCE_001
  Scenario: Insurance screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-ins-2w-01-2-wheeler-insurance.xml" as "ins 2w 01 2 wheeler insurance"
    And I capture saved hierarchy dump "uidump-ins-2w-02-scroll.xml" as "ins 2w 02 scroll"
    And I capture saved hierarchy dump "uidump-ins-2w-b-01.xml" as "ins 2w b 01"
    And I capture saved hierarchy dump "uidump-ins-2w-ready.xml" as "ins 2w ready"
    And I capture saved hierarchy dump "uidump-ins-2w-wait.xml" as "ins 2w wait"
    And I capture saved hierarchy dump "uidump-ins-4w-01-4-wheeler-insurance.xml" as "ins 4w 01 4 wheeler insurance"
    And I capture saved hierarchy dump "uidump-ins-4w-02-scroll.xml" as "ins 4w 02 scroll"
    And I capture saved hierarchy dump "uidump-ins-4w-b-01.xml" as "ins 4w b 01"
    And I capture saved hierarchy dump "uidump-ins-4w-b-02-loaded.xml" as "ins 4w b 02 loaded"
    And I capture saved hierarchy dump "uidump-ins-4w-ready.xml" as "ins 4w ready"
    And I capture saved hierarchy dump "uidump-ins-4w-wait.xml" as "ins 4w wait"
    And I capture saved hierarchy dump "uidump-ins-health-01-Health-Insurance.xml" as "ins health 01 Health Insurance"
    And I capture saved hierarchy dump "uidump-ins-health-02-scroll.xml" as "ins health 02 scroll"
    And I capture saved hierarchy dump "uidump-ins-health-b-01.xml" as "ins health b 01"
    And I capture saved hierarchy dump "uidump-ins-health-b-02-loaded.xml" as "ins health b 02 loaded"
    And I capture saved hierarchy dump "uidump-ins-health-ready.xml" as "ins health ready"
    And I capture saved hierarchy dump "uidump-ins-health-wait.xml" as "ins health wait"
    And I capture saved hierarchy dump "uidump-ins-life-01.xml" as "ins life 01"
    And I capture saved hierarchy dump "uidump-ins-life-01-Open-settings.xml" as "ins life 01 Open settings"
    And I capture saved hierarchy dump "uidump-ins-life-02-Notification.xml" as "ins life 02 Notification"
    And I capture saved hierarchy dump "uidump-ins-life-03-screen.xml" as "ins life 03 screen"
    And I capture saved hierarchy dump "uidump-ins-life-04-Back.xml" as "ins life 04 Back"
    And I capture saved hierarchy dump "uidump-ins-life-05-Choose-Your-Card-Type.xml" as "ins life 05 Choose Your Card Type"
    And I capture saved hierarchy dump "uidump-ins-life-06-Terms-and-Conditions.xml" as "ins life 06 Terms and Conditions"
    And I capture saved hierarchy dump "uidump-ins-life-07-Home-Loan.xml" as "ins life 07 Home Loan"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @INSURANCE_002
  Scenario: Insurance screens part 2
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-ins-life-08-Your-home-loan-journey-is-in.xml" as "ins life 08 Your home loan journey is in"
    And I capture saved hierarchy dump "uidump-ins-life-09-Address-details.xml" as "ins life 09 Address details"
    And I capture saved hierarchy dump "uidump-ins-life-10-Tax-Planning.xml" as "ins life 10 Tax Planning"
    And I capture saved hierarchy dump "uidump-ins-life-11-What-Is-An-Investment.xml" as "ins life 11 What Is An Investment"
    And I capture saved hierarchy dump "uidump-ins-life-12-Back.xml" as "ins life 12 Back"
    And I capture saved hierarchy dump "uidump-ins-life-13-Back.xml" as "ins life 13 Back"
    And I capture saved hierarchy dump "uidump-ins-life-b-01.xml" as "ins life b 01"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
