@airpay-vyaapaar @vyaapaar @android @a11y @from-here
Feature: airpay vyaapaar from-here screens
  Xpaths for this module live in screens/fromhere/.
  Each screen was captured once during the deep scan.

  @FROM_HERE_001
  Scenario: UiContainer Catalog Overview Card
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "from-here-uicontainer-catalog-overview-card" in module "fromhere" has stored xpaths

  @FROM_HERE_002
  Scenario: UiContainer Catalog Overview Card (filled)
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "from-here-uicontainer-catalog-overview-card-filled" in module "fromhere" has stored xpaths

  @FROM_HERE_003
  Scenario: UiContainer Catalog Overview Card / pass 0 (filled)
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "from-here-uicontainer-catalog-overview-card-p0-filled" in module "fromhere" has stored xpaths

  @FROM_HERE_004
  Scenario: UiContainer Catalog Overview Card/Show password
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "from-here-uicontainer-catalog-overview-card-show-password" in module "fromhere" has stored xpaths

  @FROM_HERE_005
  Scenario: UiContainer Catalog Overview Card/Show password (filled)
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "from-here-uicontainer-catalog-overview-card-show-password-filled" in module "fromhere" has stored xpaths

  @FROM_HERE_006
  Scenario: UiContainer Catalog Overview Card/Show password/Create keyboard theme with my image/Navigate to Without UiContainer Catalog/Show password/Filter Chip
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "from-here-uicontainer-catalog-overview-card-show-password-create-keyboard-theme-with-my-im" in module "fromhere" has stored xpaths

