@airpay-vyaapaar @vyaapaar @android @a11y @uicontainer-catalog-overview-card
Feature: airpay vyaapaar uicontainer-catalog-overview-card screens
  Xpaths for this module live in screens/uicontainercatalogoverviewcard/.
  Each screen was captured once during the deep scan.

  @UICONTAINER_CATALOG_OVERVIEW_CARD_001
  Scenario: UiContainer Catalog Overview Card
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "from-here-uicontainer-catalog-overview-card" in module "uicontainercatalogoverviewcard" has stored xpaths

  @UICONTAINER_CATALOG_OVERVIEW_CARD_002
  Scenario: UiContainer Catalog Overview Card (filled)
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "from-here-uicontainer-catalog-overview-card-filled" in module "uicontainercatalogoverviewcard" has stored xpaths

