@airpay-capital @capital @functional @android @login
Feature: Airpay Capital Login
  Mobile is Login sheet key Mobile (8802729089).
  Valid OTP is capital.test.otp (1234). InvalidOTP / ExpiredOTP still come from the Excel sheet.

  Background:
    Given the Airpay Capital app is ready

  @CA_LO_001 @smoke @feasible
  Scenario: Verify login using a mobile number
    When I tap Login With Mobile Number on the welcome screen
    And I enter the mobile number from the Capital Excel sheet
    And I accept consent on the mobile login screen
    And I tap Send OTP on the mobile login screen
    Then the OTP verification screen should be displayed
    And I enter the OTP from device notifications and verify
    Then I should see the Capital home screen

  @CA_LO_002 @feasible
  Scenario: Verify login with invalid OTP
    When I tap Login With Mobile Number on the welcome screen
    And I enter the mobile number from the Capital Excel sheet
    And I accept consent on the mobile login screen
    And I tap Send OTP on the mobile login screen
    Then the OTP verification screen should be displayed
    And I enter the invalid OTP from the Capital Excel sheet and verify
    Then the user should remain on OTP verification after the invalid OTP

  @CA_LO_003
  Scenario: Verify login with an expired OTP
    When I tap Login With Mobile Number on the welcome screen
    And I enter the mobile number from the Capital Excel sheet
    And I accept consent on the mobile login screen
    And I tap Send OTP on the mobile login screen
    Then the OTP verification screen should be displayed
    And I enter the expired OTP from the Capital Excel sheet and verify
    Then the user should remain on OTP verification after the expired OTP

  @CA_LO_004 @feasible
  Scenario: Verify the Edit option on the OTP verification page
    When I tap Login With Mobile Number on the welcome screen
    And I enter the mobile number from the Capital Excel sheet
    And I accept consent on the mobile login screen
    And I tap Send OTP on the mobile login screen
    Then the OTP verification screen should be displayed
    And I tap Edit beside the registered mobile number on the OTP screen
    Then the mobile registration screen should be displayed for editing the number

  @CA_LO_005 @feasible
  Scenario: Verify login using the newly resent OTP
    When I tap Login With Mobile Number on the welcome screen
    And I enter the mobile number from the Capital Excel sheet
    And I accept consent on the mobile login screen
    And I tap Send OTP on the mobile login screen
    Then the OTP verification screen should be displayed
    And I tap Resend OTP on the OTP screen
    And I enter the OTP from device notifications and verify
    Then I should see the Capital home screen
