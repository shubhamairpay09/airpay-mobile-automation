@airpay-money @money @functional @android @profile-settings
Feature: Profile settings after login

  Sheet: Profile settings, TC_001 to TC_028.
  Login is already done. These cases open Profile Settings from Open settings.
  Update, Send Message, and Log Out Yes are not tapped. Change PIN is not opened.
  TC_017 has a summary and no step rows. The steps open Utilities and check Financial Calculators.
  TC_023 has no steps and is not automated.
  TC_025 and TC_027 confirm logout with Yes. Yes is not tapped, so those two cases are not run.
  TC_026 opens the confirmation. TC_028 taps No and stays signed in.
  Follow us icons have no label. From left to right they are Instagram, Facebook, LinkedIn, and YouTube.

  Background:
    Given the logged-in airpay money home screen is open
    When I open Profile Settings

  @PROF_TC_001
  Scenario: TC_001 Personal Details opens from Profile Settings
    When I open Personal Details from Profile Settings
    Then the Profile Personal Details page is displayed

  @PROF_TC_002
  Scenario: TC_002 Edit personal details shows the saved information
    When I open Personal Details from Profile Settings
    And I tap Edit personal details
    Then the Edit Personal Details page is displayed
    And the Edit Personal Details fields are pre-populated

  @PROF_TC_003
  Scenario: TC_003 Back from Edit personal details returns to Personal Details
    When I open Personal Details from Profile Settings
    And I tap Edit personal details
    And I tap Back on Profile Settings
    Then the Profile Personal Details page is displayed again

  @PROF_TC_004
  Scenario: TC_004 Back from Personal Details returns to Profile Settings
    When I open Personal Details from Profile Settings
    And I tap Back on Profile Settings
    Then the Profile Settings screen is displayed

  @PROF_TC_005
  Scenario: TC_005 Work Details opens the Work Profile page
    When I open Work Details from Profile Settings
    Then the Work Profile page is displayed
    And the Current Company section is displayed
    And the Past Jobs section is displayed

  @PROF_TC_006
  Scenario: TC_006 Current Company shows employment details
    When I open Work Details from Profile Settings
    Then the current Company Name is displayed
    And the current Designation is displayed
    And the current Monthly Income is displayed

  @PROF_TC_007
  Scenario: TC_007 Back from Work Profile returns to Profile Settings
    When I open Work Details from Profile Settings
    And I tap Back on Profile Settings
    Then the Profile Settings screen is displayed

  @PROF_TC_008
  Scenario: TC_008 Address Details shows Current Address and Permanent Address
    When I open Address Details from Profile Settings
    Then the Profile Address Details page is displayed
    And the Current Address section is displayed
    And the Permanent Address section is displayed

  @PROF_TC_009
  Scenario: TC_009 Current Address shows each address field
    When I open Address Details from Profile Settings
    Then the Current Address shows Address Line 1
    And the Current Address shows Address Line 2
    And the Current Address shows Landmark
    And the Current Address shows State
    And the Current Address shows City
    And the Current Address shows Pincode

  @PROF_TC_010
  Scenario: TC_010 Back from Address Details returns to Profile Settings
    When I open Address Details from Profile Settings
    And I tap Back on Profile Settings
    Then the Profile Settings screen is displayed

  @PROF_TC_011
  Scenario: TC_011 Document Details opens My Documents
    When I open Document Details from Profile Settings
    Then the My Documents page is displayed
    And an uploaded document is displayed

  @PROF_TC_012
  Scenario: TC_012 PAN Card opens the PAN document
    When I open Document Details from Profile Settings
    And I open the PAN Card document
    Then the PAN Card details are displayed

  @PROF_TC_013
  Scenario: TC_013 Back from My Documents returns to Profile Settings
    When I open Document Details from Profile Settings
    And I tap Back on Profile Settings
    Then the Profile Settings screen is displayed

  @PROF_TC_014
  Scenario: TC_014 Contact Us shows the message form
    When I open Contact Us from Profile Settings
    Then the Profile Contact Us page is displayed

  @PROF_TC_015
  Scenario: TC_015 Contact Us shows the logged-in name, email, and phone
    When I open Contact Us from Profile Settings
    Then the Contact Us Full Name is pre-populated
    And the Contact Us Email is pre-populated
    And the Contact Us Phone Number is pre-populated

  @PROF_TC_016
  Scenario: TC_016 Back from Contact Us keeps the signed-in Profile Settings
    When I open Contact Us from Profile Settings
    And I tap Back on Profile Settings
    Then the Profile Settings screen is displayed with the user still signed in

  @PROF_TC_017
  Scenario: TC_017 Utilities opens Financial Calculators
    When I open Utilities from Profile Settings
    Then the Financial Calculators screen is displayed

  @PROF_TC_018
  Scenario: TC_018 Terms and Conditions opens from Profile Settings
    When I open Terms and Conditions from Profile Settings
    Then the Profile Terms and Conditions page is displayed

  @PROF_TC_019
  Scenario: TC_019 Privacy Policy opens from Profile Settings
    When I open Privacy Policy from Profile Settings
    Then the Profile Privacy Policy page is displayed

  @PROF_TC_020
  Scenario: TC_020 Privacy Policy shows the title and last updated date
    When I open Privacy Policy from Profile Settings
    Then the Profile Privacy Policy page is displayed
    And the Privacy Policy last updated date is displayed

  @PROF_TC_021
  Scenario: TC_021 Privacy Policy can be scrolled
    When I open Privacy Policy from Profile Settings
    And I scroll the Privacy Policy page
    Then the Profile Privacy Policy page is displayed

  @PROF_TC_022
  Scenario: TC_022 Back from Privacy Policy returns to Profile Settings
    When I open Privacy Policy from Profile Settings
    And I tap Back on Profile Settings
    Then the Profile Settings screen is displayed

  @PROF_TC_024
  Scenario: TC_024 social icons open Instagram, Facebook, LinkedIn, and YouTube
    When I tap the Instagram icon on Profile Settings
    Then the Instagram page is displayed
    When I return to Profile Settings
    And I tap the Facebook icon on Profile Settings
    Then the Facebook page is displayed
    When I return to Profile Settings
    And I tap the LinkedIn icon on Profile Settings
    Then the LinkedIn page is displayed
    When I return to Profile Settings
    And I tap the YouTube icon on Profile Settings
    Then the YouTube page is displayed

  @PROF_TC_026
  Scenario: TC_026 Log Out shows the confirmation
    When I tap Log Out on Profile Settings
    Then the logout confirmation is displayed

  @PROF_TC_028
  Scenario: TC_028 No on the logout confirmation stays signed in
    When I tap Log Out on Profile Settings
    And I tap No on the logout confirmation
    Then the logout confirmation is closed
    And the Profile Settings screen is displayed with the user still signed in
