@airpay-money @money @functional @android @nps
Feature: NPS after login

  Sheet: NPS, TC_001 to TC_010. Every sheet case is in this feature.
  Login is already done. These cases start on the logged-in home screen and open NPS from the products hub.
  A case runs when its detail is on the screen. Calculate Now, Details, Invest More, Your SIPs,
  and transaction rows are skipped when this account does not show them.
  Tabs and Back always run. Open NPS Account is not tapped. KYC is not started.
  Invest More opens the contribution screen only. Card, UPI, and Proceed to Pay are not used.
  A transaction row counts as One Time only when that row's own text says One Time or Lumpsum.
  A transaction row counts as SIP only when that row's own text says SIP.
  The filter chips themselves are not treated as transaction rows.

  Background:
    Given the logged-in airpay money home screen is open
    When I open NPS from the products hub
    Then the NPS dashboard is displayed

  @NPS_TC_001
  Scenario: TC_001 Calculate Now opens the NPS Calculator
    When I tap Calculate Now on the NPS dashboard
    Then the NPS Calculator screen is displayed
    And the NPS Calculator shows the maturity amount

  @NPS_TC_002
  Scenario: TC_002 Details opens the NPS investment page
    When I tap Details on the NPS investment card
    Then the NPS investment details page is displayed

  @NPS_TC_003
  Scenario: TC_003 Invest More starts the NPS contribution
    When I tap Invest More on the NPS dashboard
    Then the NPS contribution screen is displayed
    And NPS payment details are not requested

  @NPS_TC_004
  Scenario: TC_004 Your SIPs opens the SIP list
    When I tap Your SIPs on the NPS dashboard
    Then the NPS SIP list is displayed

  @NPS_TC_005
  Scenario: TC_005 Dashboard, Portfolio, Transactions, and Profile tabs open
    When I tap the NPS Dashboard tab
    Then the NPS Dashboard screen is displayed
    And the NPS Dashboard tab is highlighted
    When I tap the NPS Portfolio tab
    Then the NPS Portfolio screen is displayed
    And the NPS Portfolio tab is highlighted
    When I tap the NPS Transactions tab
    Then the NPS Transactions screen is displayed
    And the NPS Transactions tab is highlighted
    When I tap the NPS Profile tab
    Then the NPS Profile screen is displayed
    And the NPS Profile tab is highlighted

  @NPS_TC_006
  Scenario: TC_006 Back returns to the products hub
    When I tap the NPS back arrow
    Then the products hub is displayed again

  @NPS_TC_007
  Scenario: TC_007 NPS Transaction History shows filters and records
    When I open NPS Transaction History
    Then the NPS Transaction History screen is displayed
    And the NPS All filter is displayed
    And the NPS One Time filter is displayed
    And the NPS SIP filter is displayed
    And NPS transaction records are displayed

  @NPS_TC_008
  Scenario: TC_008 All shows one-time and SIP transactions
    When I open NPS Transaction History
    And I tap the NPS All filter
    Then NPS All shows one-time and SIP transactions

  @NPS_TC_009
  Scenario: TC_009 One Time hides SIP transactions
    When I open NPS Transaction History
    And I tap the NPS One Time filter
    Then NPS One Time shows only one-time transactions
    And NPS One Time hides SIP transactions

  @NPS_TC_010
  Scenario: TC_010 SIP hides one-time transactions
    When I open NPS Transaction History
    And I tap the NPS SIP filter
    Then NPS SIP shows only SIP transactions
    And NPS SIP hides one-time transactions
