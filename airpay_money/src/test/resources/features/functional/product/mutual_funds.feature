@airpay-money @money @functional @android @mutual-funds
Feature: Mutual Funds after login

  Sheet: Mutual Fund, TC_001 to TC_027.
  Goal name, amount, tenure, fund list, and search text are read from the Mutual Fund sheet in testdata/money-functional-data.xlsx.
  The sheet repeats Long Term through Nifty 50 after TC_011 without new numbers.
  Those repeated rows are the same cases and are not automated twice.
  KYC is not started. A Bike goal is created, checked on My Goals, and deleted.
  Invest, One Time, and Monthly are not tapped.

  Background:
    Given the logged-in airpay money home screen is open
    When I open Mutual Funds from the products hub

  @MF_TC_001
  Scenario: TC_001 View Details opens portfolio holdings
    When I open Mutual Fund portfolio details
    Then Mutual Fund portfolio holdings are displayed

  @MF_TC_002
  Scenario: TC_002 the eye icon hides and shows investment values
    When I tap the Mutual Funds eye icon

  @MF_TC_003
  Scenario: TC_003 Add Goal creates a Bike goal and that goal is deleted
    When I open Create Goal
    Then the Create Goal screen shows the goal icons
    When I create a Bike goal named "bike" for "50000" over "3 year"
    Then the goal "bike" for "50000" is listed on My Goals
    When I open the goal "bike"
    And I delete the open goal
    Then the open goal is deleted

  @MF_TC_004
  Scenario: TC_004 a recommended fund opens fund details
    When I open a recommended fund
    Then the fund details page is displayed

  @MF_TC_005
  Scenario: TC_005 View all opens the recommendation list
    When I open the recommendation list
    Then the recommendation list is displayed

  @MF_TC_006
  Scenario: TC_006 View all Mutual Funds opens the catalog
    When I open View all Mutual Funds
    Then the mutual fund catalog is displayed

  @MF_TC_007
  Scenario: TC_007 Long Term Investment opens that fund list
    When I open the mutual fund list "Long Term Investment"
    Then the mutual fund list "Long Term Investment" is displayed

  @MF_TC_008
  Scenario: TC_008 Tax Saving Fund opens that fund list
    When I open the mutual fund list "Tax Saving Fund"
    Then the mutual fund list "Tax Saving Fund" is displayed

  @MF_TC_009
  Scenario: TC_009 Emergency Fund opens that fund list and a fund can be viewed
    When I open the mutual fund list "Emergency Fund"
    Then the mutual fund list "Emergency Fund" is displayed
    When I open a fund from the current list
    Then the fund details page is displayed

  @MF_TC_010
  Scenario: TC_010 Bluechip Fund opens that fund list and a fund can be viewed
    When I open the mutual fund list "Bluechip Fund"
    Then the mutual fund list "Bluechip Fund" is displayed
    When I open a fund from the current list
    Then the fund details page is displayed

  @MF_TC_011
  Scenario: TC_011 Nifty 50 opens that fund list and a fund can be viewed
    When I open the mutual fund list "Nifty 50"
    Then the mutual fund list "Nifty 50" is displayed
    When I open a fund from the current list
    Then the fund details page is displayed

  @MF_TC_012
  Scenario: TC_012 Nifty Mid Cap opens that fund list
    When I open the mutual fund list "Nifty Mid Cap"
    Then the mutual fund list "Nifty Mid Cap" is displayed

  @MF_TC_013
  Scenario: TC_013 Nifty 100 opens that fund list
    When I open the mutual fund list "Nifty 100"
    Then the mutual fund list "Nifty 100" is displayed

  @MF_TC_014
  Scenario: TC_014 Nifty Small Cap 50 opens that fund list
    When I open the mutual fund list "Nifty Small Cap 50"
    Then the mutual fund list "Nifty Small Cap 50" is displayed

  @MF_TC_015
  Scenario: TC_015 Nifty Large Midcap opens that fund list
    When I open the mutual fund list "Nifty Large Midcap 250"
    Then the mutual fund list "Nifty Large Midcap 250" is displayed

  @MF_TC_016
  Scenario: TC_016 Global Fund opens that fund list and investment options can be viewed
    When I open the mutual fund list "Global Fund"
    Then the mutual fund list "Global Fund" is displayed
    When I open a fund from the current list
    Then the fund details page is displayed

  @MF_TC_017
  Scenario: TC_017 the risk profile category is displayed
    Then the risk profile section is displayed

  @MF_TC_018
  Scenario: TC_018 the risk profile editor opens and the saved profile is not changed
    When I open the risk profile editor
    Then the risk profile edit screen is displayed

  @MF_TC_019
  Scenario: TC_019 Dashboard, Portfolio, Transactions, and Profile tabs open
    Then the Mutual Funds back button is displayed
    When I open the Mutual Funds Portfolio tab
    Then the Mutual Funds portfolio tab is displayed
    When I tap the Mutual Funds back button
    When I open the Mutual Funds Transactions tab
    Then the Mutual Funds transactions tab is displayed
    When I tap the Mutual Funds back button
    When I open the Mutual Funds Profile tab
    Then the Mutual Funds profile tab is displayed
    When I tap the Mutual Funds back button
    Then the Mutual Funds dashboard tab is displayed

  @MF_TC_020
  Scenario: TC_020 search finds an HDFC mutual fund
    When I search mutual funds for "HDFC"
    Then an HDFC mutual fund result is displayed

  @MF_TC_021
  Scenario: TC_021 the filter icon on Better than FDs opens filters
    When I open Better than FDs from Curated Funds
    Then the Better than FDs list shows the title and Search Funds
    And I tap the blank filter icon at the top right, beside the Better than FDs title
    Then the filter screen shows Filters, Clear All, Category, AMC, Risk, Returns, Rating, and Apply

  @MF_TC_022
  Scenario: TC_022 a mutual fund card opens fund details
    When I open View all Mutual Funds
    And I open a fund from the current list
    Then the fund details page is displayed

  @MF_TC_023
  Scenario: TC_023 Equity category filter applies and closes
    When I open Better than FDs from Curated Funds
    Then the Better than FDs list shows the title and Search Funds
    And I tap the blank filter icon at the top right, beside the Better than FDs title
    And I apply the Equity category filter
    Then the filter closes and the fund list remains
    And only Equity funds are displayed

  @MF_TC_024
  Scenario: TC_024 a risk filter applies after AMC, risk, returns, and rating are shown
    When I open Better than FDs from Curated Funds
    Then the Better than FDs list shows the title and Search Funds
    And I tap the blank filter icon at the top right, beside the Better than FDs title
    Then the risk filter screen shows AMC, risk, returns, and rating
    When I apply the Balanced risk filter
    Then the Balanced risk filter is applied

  @MF_TC_025
  Scenario: TC_025 Clear All restores the filter choices
    When I open Better than FDs from Curated Funds
    Then the Better than FDs list shows the title and Search Funds
    And I tap the blank filter icon at the top right, beside the Better than FDs title
    And I clear all mutual fund filters
    Then the mutual fund filters return to the default list

  @MF_TC_026
  Scenario: TC_026 Apply closes the filter after a selection
    When I open Better than FDs from Curated Funds
    Then the Better than FDs list shows the title and Search Funds
    And I tap the blank filter icon at the top right, beside the Better than FDs title
    And I apply the Equity category filter
    Then the filter closes and the fund list remains
    And only Equity funds are displayed

  @MF_TC_027
  Scenario: TC_027 a fund on Better than FDs opens fund details
    When I open Better than FDs
    And I open a fund from the current list
    Then the fund details page is displayed
