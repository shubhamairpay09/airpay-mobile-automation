@airpay-capital @capital @functional @android @login
Feature: Airpay Capital Login
  Mobile is Login sheet key Mobile (8802729089).
  Valid OTP is read from the phone notification shade / SMS inbox via NotificationOtpReader.
  InvalidOTP / ExpiredOTP still come from the Excel sheet for negative cases.
  Leave capital.test.otp blank so notifications are used.

  Background:
    Given the Airpay Capital app is ready

  @CA_LO_001 @smoke
  Scenario: Verify login using a mobile number
    When I tap Login With Mobile Number on the welcome screen
    And I enter the mobile number from the Capital Excel sheet
    And I accept consent on the mobile login screen
    And I tap Send OTP on the mobile login screen
    And I enter the OTP from device notifications and verify
    # Then I should see the Capital home screen

  @CA_LO_002
  Scenario: Verify login with invalid OTP
    When I tap Login With Mobile Number on the welcome screen
    And I enter the mobile number from the Capital Excel sheet
    And I accept consent on the mobile login screen
    And I tap Send OTP on the mobile login screen
    And I enter the invalid OTP from the Capital Excel sheet and verify
    # Then an Invalid OTP error should be displayed and the user should remain on OTP verification

  @CA_LO_003
  Scenario: Verify login with an expired OTP
    When I tap Login With Mobile Number on the welcome screen
    And I enter the mobile number from the Capital Excel sheet
    And I accept consent on the mobile login screen
    And I tap Send OTP on the mobile login screen
    And I enter the expired OTP from the Capital Excel sheet and verify
    # Then an OTP Expired error should be displayed and the user should remain on OTP verification

  @CA_LO_004
  Scenario: Verify the Edit option on the OTP verification page
    When I tap Login With Mobile Number on the welcome screen
    And I enter the mobile number from the Capital Excel sheet
    And I accept consent on the mobile login screen
    And I tap Send OTP on the mobile login screen
    And I tap Edit beside the registered mobile number on the OTP screen
    # Then the mobile registration screen should be displayed for editing the number

  @CA_LO_005
  Scenario: Verify login using the newly resent OTP
    When I tap Login With Mobile Number on the welcome screen
    And I enter the mobile number from the Capital Excel sheet
    And I accept consent on the mobile login screen
    And I tap Send OTP on the mobile login screen
    And I tap Resend OTP on the OTP screen
    And I enter the OTP from device notifications and verify
    # Then I should see the Capital home screen
