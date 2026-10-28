@airpay-money @money @functional @android @login
Feature: airpay money Login

  Mobile number and OTP stay in config/money.properties.
  The PIN is the Login sheet row Pin in testdata/money-functional-data.xlsx.
  When the app asks for the PIN, that sheet value is entered.
  When biometrics is offered, Skip for now is tapped.
  When the session is already signed in, the welcome screen is not required.

  @MONEY_001 @smoke
  Scenario: Verify login using a mobile number
    Given the airpay money app is ready
    When the airpay money session is signed in or login is completed from the welcome screen
    Then the signed-in home shows Hello and Your Financial Health Score
