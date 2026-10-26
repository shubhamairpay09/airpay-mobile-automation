@airpay-money @money @functional @android @digital-gold
Feature: Digital Gold after login

  Sheet: Digital Gold, TC_001 to TC_021.
  Buy amounts and gram chips are read from the Digital Gold sheet in testdata/money-functional-data.xlsx. Change BuyRupees or Grams for that TC.
  Login and KYC are already done and are not run.
  Make Payment opens the Airpay page. Continue is not tapped and no card or UPI is entered.
  Leaving Airpay without paying shows the unpaid failure screen. Go to Dashboard does not credit the locker.

  Background:
    Given the logged-in airpay money home screen is open
    When I open Digital Gold from the products hub

  @DG_TC_001
  Scenario: TC_001 Buy opens the Buy Gold and Silver screen
    When I tap Buy on the Digital Gold dashboard
    Then the Buy Gold and Silver screen is displayed
    And purchase options are displayed on the buy screen

  @DG_TC_002
  Scenario: TC_002 Sell opens the Sell Gold and Silver screen
    When I tap Sell on the Digital Gold dashboard
    Then the Sell Gold and Silver screen is displayed
    And the available balance for selling is displayed

  @DG_TC_003
  Scenario: TC_003 Buy Gold and Silver screen shows the purchase form
    When I tap Buy on the Digital Gold dashboard
    Then the Buy Gold and Silver screen shows Gold selected with live rate, investment type, rupees, grams, amount, links, and Proceed off

  @DG_TC_004
  Scenario: TC_004 amount in rupees calculates grams
    When I tap Buy on the Digital Gold dashboard
    And I buy in rupees and enter 100
    Then the rupee amount is accepted and a gram quantity is calculated

  @DG_TC_005
  Scenario: TC_005 quantity in grams calculates rupees
    When I tap Buy on the Digital Gold dashboard
    And I buy in grams and enter a gram chip
    Then the gram quantity is accepted and a rupee amount is calculated

  @DG_TC_006
  Scenario: TC_006 consent checkbox can be selected and cleared
    When I tap Buy on the Digital Gold dashboard
    And I select and clear the buy consent checkbox
    Then the buy consent checkbox changes state

  @DG_TC_007
  Scenario: TC_007 Proceed enables after amount and consent
    When I tap Buy on the Digital Gold dashboard
    And I enter 100 rupees and accept buy consent
    Then Proceed becomes enabled on the buy screen

  @DG_TC_008
  Scenario: TC_008 Proceed opens the order review without an error
    When I tap Buy on the Digital Gold dashboard
    And I enter 200 rupees and accept buy consent
    And I tap Proceed on the buy screen
    Then the order summary shows the purchase, price, metal, quantity, investment type, tax, total, timer, and Make Payment
    And the order summary matches the amount entered on the buy screen

  @DG_TC_009
  Scenario: TC_009 back from Buy returns to the dashboard
    When I tap Buy on the Digital Gold dashboard
    And I go back to the Digital Gold dashboard
    Then the Digital Gold dashboard is displayed

  @DG_TC_010
  Scenario: TC_010 Proceed opens Order Summary with the quote details
    When I enter 200 rupees and accept buy consent
    And I tap Proceed on the buy screen
    Then the order summary shows the purchase, price, metal, quantity, investment type, tax, total, timer, and Make Payment

  @DG_TC_011
  Scenario: TC_011 order details match the buy screen
    When I open the order summary for 200 rupees
    Then the order summary matches the amount entered on the buy screen

  @DG_TC_012
  Scenario: TC_012 total payment equals amount plus GST
    When I open the order summary for 200 rupees
    Then the order summary total equals the amount plus tax

  @DG_TC_013
  Scenario: TC_013 Make Payment opens the gateway and the amount matches
    When I open the order summary for 200 rupees
    And I tap Make Payment and do not submit a payment
    Then the Airpay payment page shows the amount, an order id, the mobile number, policy links, and Continue
    And the Airpay amount matches the order summary

  @DG_TC_014
  Scenario: TC_014 back from Order Summary keeps the buy amount
    When I open the order summary for 200 rupees
    Then the buy amount entered before the order summary is still shown

  @DG_TC_015
  Scenario: TC_015 Make Payment opens the Airpay page
    When I open the order summary for 200 rupees
    And I tap Make Payment and do not submit a payment
    Then the Airpay payment page shows the amount, an order id, the mobile number, policy links, and Continue

  @DG_TC_016
  Scenario: TC_016 Airpay amount matches the order summary
    When I open the order summary for 200 rupees
    And I tap Make Payment and do not submit a payment
    Then the Airpay amount matches the order summary

  @DG_TC_017
  Scenario: TC_017 Airpay shows an order id
    When I open the order summary for 200 rupees
    And I tap Make Payment and do not submit a payment
    Then the Airpay order id is not blank

  @DG_TC_018
  Scenario: TC_018 registered mobile number is shown on Airpay
    When I open the order summary for 200 rupees
    And I tap Make Payment and do not submit a payment
    Then the registered mobile number and country code are shown on Airpay

  @DG_TC_019
  Scenario: TC_019 back from Airpay opens the Go to Dashboard screen
    When I open the order summary for 200 rupees
    And I tap Make Payment and do not submit a payment
    And I leave the Airpay page without paying
    Then the unpaid transaction failure screen is displayed

  @DG_TC_020
  Scenario: TC_020 leaving Airpay without paying shows the failure screen
    When I open the order summary for 200 rupees
    And I tap Make Payment and do not submit a payment
    And I leave the Airpay page without paying
    Then the unpaid transaction failure screen is displayed

  @DG_TC_021
  Scenario: TC_021 Go to Dashboard returns without crediting the locker
    When I open the order summary for 200 rupees
    And I tap Make Payment and do not submit a payment
    And I leave the Airpay page without paying
    And I tap Go to Dashboard on the failure screen
    Then the Digital Gold dashboard is displayed and nothing was purchased
