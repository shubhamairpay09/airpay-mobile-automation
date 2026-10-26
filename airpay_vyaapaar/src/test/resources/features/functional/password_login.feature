@airpay-vyaapaar @vyaapaar @android @password-login
Feature: airpay vyaapaar username and password login
  Runs after the accessibility deep scan. Not part of the screen crawl.
  Set vyaapaar.test.password before this scenario.

  @VYAAPAAR_002 @password-login
  Scenario: Login with username and password
    Given the airpay vyaapaar app is installed on the device
    When I login to airpay vyaapaar with username and password
    Then scorecards and page xpaths are written for airpay vyaapaar
