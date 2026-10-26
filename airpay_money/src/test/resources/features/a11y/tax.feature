@airpay-money @money @android @a11y @capture @tax
Feature: Capture Tax screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @TAX_001
  Scenario: Tax screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-tax-fileitr-1.xml" as "tax fileitr 1"
    And I capture saved hierarchy dump "uidump-tax-home.xml" as "tax home"
    And I capture saved hierarchy dump "uidump-tax-itr-plan-fold1.xml" as "tax itr plan fold1"
    And I capture saved hierarchy dump "uidump-tax-landing.xml" as "tax landing"
    And I capture saved hierarchy dump "uidump-tax-landing-fold1.xml" as "tax landing fold1"
    And I capture saved hierarchy dump "uidump-tax-landing-fold10.xml" as "tax landing fold10"
    And I capture saved hierarchy dump "uidump-tax-landing-fold11.xml" as "tax landing fold11"
    And I capture saved hierarchy dump "uidump-tax-landing-fold12.xml" as "tax landing fold12"
    And I capture saved hierarchy dump "uidump-tax-landing-fold13.xml" as "tax landing fold13"
    And I capture saved hierarchy dump "uidump-tax-landing-fold14.xml" as "tax landing fold14"
    And I capture saved hierarchy dump "uidump-tax-landing-fold15.xml" as "tax landing fold15"
    And I capture saved hierarchy dump "uidump-tax-landing-fold16.xml" as "tax landing fold16"
    And I capture saved hierarchy dump "uidump-tax-landing-fold17.xml" as "tax landing fold17"
    And I capture saved hierarchy dump "uidump-tax-landing-fold18.xml" as "tax landing fold18"
    And I capture saved hierarchy dump "uidump-tax-landing-fold19.xml" as "tax landing fold19"
    And I capture saved hierarchy dump "uidump-tax-landing-fold2.xml" as "tax landing fold2"
    And I capture saved hierarchy dump "uidump-tax-landing-fold20.xml" as "tax landing fold20"
    And I capture saved hierarchy dump "uidump-tax-landing-fold3.xml" as "tax landing fold3"
    And I capture saved hierarchy dump "uidump-tax-landing-fold4.xml" as "tax landing fold4"
    And I capture saved hierarchy dump "uidump-tax-landing-fold5.xml" as "tax landing fold5"
    And I capture saved hierarchy dump "uidump-tax-landing-fold6.xml" as "tax landing fold6"
    And I capture saved hierarchy dump "uidump-tax-landing-fold7.xml" as "tax landing fold7"
    And I capture saved hierarchy dump "uidump-tax-landing-fold8.xml" as "tax landing fold8"
    And I capture saved hierarchy dump "uidump-tax-landing-fold9.xml" as "tax landing fold9"
    And I capture saved hierarchy dump "uidump-tax-login.xml" as "tax login"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @TAX_002
  Scenario: Tax screens part 2
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-tax-login-empty.xml" as "tax login empty"
    And I capture saved hierarchy dump "uidump-tax-login-focused.xml" as "tax login focused"
    And I capture saved hierarchy dump "uidump-tax-menu.xml" as "tax menu"
    And I capture saved hierarchy dump "uidump-tax-menu-company.xml" as "tax menu company"
    And I capture saved hierarchy dump "uidump-tax-menu-fold2.xml" as "tax menu fold2"
    And I capture saved hierarchy dump "uidump-tax-menu-fold3.xml" as "tax menu fold3"
    And I capture saved hierarchy dump "uidump-tax-menu-resources.xml" as "tax menu resources"
    And I capture saved hierarchy dump "uidump-tax-menu-resources-fold2.xml" as "tax menu resources fold2"
    And I capture saved hierarchy dump "uidump-tax-nav-0.xml" as "tax nav 0"
    And I capture saved hierarchy dump "uidump-tax-nav-1.xml" as "tax nav 1"
    And I capture saved hierarchy dump "uidump-tax-rewards-page.xml" as "tax rewards page"
    And I capture saved hierarchy dump "uidump-tax-rewards-page-fold1.xml" as "tax rewards page fold1"
    And I capture saved hierarchy dump "uidump-tax-rewards-page-fold2.xml" as "tax rewards page fold2"
    And I capture saved hierarchy dump "uidump-tax-rewards-page-fold3.xml" as "tax rewards page fold3"
    And I capture saved hierarchy dump "uidump-tax-tab-rewards.xml" as "tax tab rewards"
    And I capture saved hierarchy dump "uidump-tax-tabstrip-scrolled.xml" as "tax tabstrip scrolled"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
