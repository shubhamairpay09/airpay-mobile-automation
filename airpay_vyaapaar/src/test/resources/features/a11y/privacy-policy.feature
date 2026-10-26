@airpay-vyaapaar @vyaapaar @android @a11y @privacy-policy
Feature: airpay vyaapaar privacy-policy screens
  Xpaths for this module live in screens/privacypolicy/.
  Each screen was captured once during the deep scan.

  @PRIVACY_POLICY_001
  Scenario: Privacy Policy — airpay
    Given the airpay vyaapaar app is installed on the device
    Then airpay vyaapaar screen "profile-settings-privacy-policy" in module "privacypolicy" has stored xpaths

