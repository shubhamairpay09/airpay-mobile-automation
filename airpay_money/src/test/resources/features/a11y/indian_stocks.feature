@airpay-money @money @android @a11y @capture @indian-stocks
Feature: Capture Indian Stocks screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @INDIAN_STOCKS_001
  Scenario: Indian Stocks screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-instk-01.xml" as "instk 01"
    And I capture saved hierarchy dump "uidump-instk-02-scroll.xml" as "instk 02 scroll"
    And I capture saved hierarchy dump "uidump-instk-03-orders.xml" as "instk 03 orders"
    And I capture saved hierarchy dump "uidump-instk-04-holding.xml" as "instk 04 holding"
    And I capture saved hierarchy dump "uidump-instk-05b-orders.xml" as "instk 05b orders"
    And I capture saved hierarchy dump "uidump-instk-05-reopen.xml" as "instk 05 reopen"
    And I capture saved hierarchy dump "uidump-instk-06-filter.xml" as "instk 06 filter"
    And I capture saved hierarchy dump "uidump-instk-07-equities.xml" as "instk 07 equities"
    And I capture saved hierarchy dump "uidump-instk-08-etfs.xml" as "instk 08 etfs"
    And I capture saved hierarchy dump "uidump-instk-11-Holdings-10-Tab-1-of-2.xml" as "instk 11 Holdings 10 Tab 1 of 2"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @INDIAN_STOCKS_002
  Scenario: Indian Stocks equity, ETF, holdings and orders
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-in-0.xml" as "in 0"
    And I capture saved hierarchy dump "uidump-in-equity-details-tcs.xml" as "in equity details tcs"
    And I capture saved hierarchy dump "uidump-in-etf-details.xml" as "in etf details"
    And I capture saved hierarchy dump "uidump-in-holdings-scrolled.xml" as "in holdings scrolled"
    And I capture saved hierarchy dump "uidump-in-hub-0.xml" as "in hub 0"
    And I capture saved hierarchy dump "uidump-in-hub-1.xml" as "in hub 1"
    And I capture saved hierarchy dump "uidump-in-order-card-tapped.xml" as "in order card tapped"
    And I capture saved hierarchy dump "uidump-in-order-detail.xml" as "in order detail"
    And I capture saved hierarchy dump "uidump-in-order-detail-rejected.xml" as "in order detail rejected"
    And I capture saved hierarchy dump "uidump-in-orders.xml" as "in orders"
    And I capture saved hierarchy dump "uidump-in-orders-completed.xml" as "in orders completed"
    And I capture saved hierarchy dump "uidump-in-orders-failed.xml" as "in orders failed"
    And I capture saved hierarchy dump "uidump-in-orders-placed.xml" as "in orders placed"
    And I capture saved hierarchy dump "uidump-in-portfolio-holdings.xml" as "in portfolio holdings"
    And I capture saved hierarchy dump "uidump-in-search.xml" as "in search"
    And I capture saved hierarchy dump "uidump-in-search-equities.xml" as "in search equities"
    And I capture saved hierarchy dump "uidump-in-search-etfs.xml" as "in search etfs"
    And I capture saved hierarchy dump "uidump-in-search-no-results.xml" as "in search no results"
    And I capture saved hierarchy dump "uidump-in-search-tcs.xml" as "in search tcs"
    And I capture saved hierarchy dump "uidump-in-search-tcs-all.xml" as "in search tcs all"
    And I capture saved hierarchy dump "uidump-in-search-tcs-equities.xml" as "in search tcs equities"
    And I capture saved hierarchy dump "uidump-in-search-tcs-etfs.xml" as "in search tcs etfs"
    And I capture saved hierarchy dump "uidump-in-stock-idea.xml" as "in stock idea"
    And I capture saved hierarchy dump "uidump-in-stock-irfc.xml" as "in stock irfc"
    And I capture saved hierarchy dump "uidump-in-stock-irfc-scrolled.xml" as "in stock irfc scrolled"
    And I capture saved hierarchy dump "uidump-in-stock-tcs.xml" as "in stock tcs"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

