@airpay-capital @capital @functional @android
Feature: Airpay Capital Login

  @CAPITAL_001 @smoke
  Scenario: Verify login using a mobile number
    Given the Airpay Capital app is ready
    When I tap Login With Mobile Number on the welcome screen
    And I enter the mobile number and accept consent
    And I tap Send OTP
    And I enter OTP "1234" and verify
    Then I should see the Capital home screen
