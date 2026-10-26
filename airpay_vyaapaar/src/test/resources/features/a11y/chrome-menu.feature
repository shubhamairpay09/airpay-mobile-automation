@airpay-vyaapaar @vyaapaar @android @a11y @chrome-menu
Feature: airpay vyaapaar chrome-menu screens
  Xpaths for this module live in screens/chromemenu/.
  Each screen was captured once during the deep scan.

  @CHROME_MENU_001
  Scenario: Chrome — Airpay
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "chrome-terms" in module "chromemenu" has stored xpaths

  @CHROME_MENU_002
  Scenario: Chrome menu — Airpay
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "chrome-menu-open" in module "chromemenu" has stored xpaths

  @CHROME_MENU_003
  Scenario: Chrome link airpay AI — www
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "chrome-link-airpay-ai" in module "chromemenu" has stored xpaths

  @CHROME_MENU_004
  Scenario: Chrome link Payments — logo
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "chrome-link-payments" in module "chromemenu" has stored xpaths

  @CHROME_MENU_005
  Scenario: Chrome link Solutions — logo
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "chrome-link-solutions" in module "chromemenu" has stored xpaths

  @CHROME_MENU_006
  Scenario: Chrome link Partner — Airpay
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "chrome-link-partner" in module "chromemenu" has stored xpaths

  @CHROME_MENU_007
  Scenario: Chrome link Developer Hub — airpay-Logo
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "chrome-link-developer-hub" in module "chromemenu" has stored xpaths

  @CHROME_MENU_008
  Scenario: Chrome link Resources — airpay-Logo
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "chrome-link-resources" in module "chromemenu" has stored xpaths

  @CHROME_MENU_009
  Scenario: Chrome link About Us — airpay-Logo
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "chrome-link-about-us" in module "chromemenu" has stored xpaths

