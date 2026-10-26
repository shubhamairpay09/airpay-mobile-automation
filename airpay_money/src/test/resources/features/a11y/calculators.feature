@airpay-money @money @android @a11y @capture @calculators
Feature: Capture Calculators screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @CALCULATORS_001
  Scenario: Calculators screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-calc-00-exit-fhc.xml" as "calc 00 exit fhc"
    And I capture saved hierarchy dump "uidump-calc-01-home.xml" as "calc 01 home"
    And I capture saved hierarchy dump "uidump-calc-02-home.xml" as "calc 02 home"
    And I capture saved hierarchy dump "uidump-calc-03-hub.xml" as "calc 03 hub"
    And I capture saved hierarchy dump "uidump-calc-04-hub-scrolled.xml" as "calc 04 hub scrolled"
    And I capture saved hierarchy dump "uidump-calc-05-hub-end.xml" as "calc 05 hub end"
    And I capture saved hierarchy dump "uidump-calc-budget-00-hub.xml" as "calc budget 00 hub"
    And I capture saved hierarchy dump "uidump-calc-budget-01-top.xml" as "calc budget 01 top"
    And I capture saved hierarchy dump "uidump-calc-budget-02-scroll1.xml" as "calc budget 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-budget-03-scroll2.xml" as "calc budget 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-budget-04-scroll3.xml" as "calc budget 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-budget-10-top.xml" as "calc budget 10 top"
    And I capture saved hierarchy dump "uidump-calc-budget-11-scroll.xml" as "calc budget 11 scroll"
    And I capture saved hierarchy dump "uidump-calc-budget-12-end.xml" as "calc budget 12 end"
    And I capture saved hierarchy dump "uidump-calc-buyvsrent-00-hub.xml" as "calc buyvsrent 00 hub"
    And I capture saved hierarchy dump "uidump-calc-buyvsrent-01-top.xml" as "calc buyvsrent 01 top"
    And I capture saved hierarchy dump "uidump-calc-buyvsrent-02-scroll1.xml" as "calc buyvsrent 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-buyvsrent-03-scroll2.xml" as "calc buyvsrent 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-buyvsrent-04-scroll3.xml" as "calc buyvsrent 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-crorepati-00-hub.xml" as "calc crorepati 00 hub"
    And I capture saved hierarchy dump "uidump-calc-crorepati-01-top.xml" as "calc crorepati 01 top"
    And I capture saved hierarchy dump "uidump-calc-crorepati-02-scroll1.xml" as "calc crorepati 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-crorepati-03-scroll2.xml" as "calc crorepati 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-emi-00-probe.xml" as "calc emi 00 probe"
    And I capture saved hierarchy dump "uidump-calc-emi-01-top.xml" as "calc emi 01 top"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @CALCULATORS_002
  Scenario: Calculators screens part 2
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-calc-emi-02-mid.xml" as "calc emi 02 mid"
    And I capture saved hierarchy dump "uidump-calc-emi-02-scrolled.xml" as "calc emi 02 scrolled"
    And I capture saved hierarchy dump "uidump-calc-emi-03-end.xml" as "calc emi 03 end"
    And I capture saved hierarchy dump "uidump-calc-emi-04-edit.xml" as "calc emi 04 edit"
    And I capture saved hierarchy dump "uidump-calc-emi-04-end2.xml" as "calc emi 04 end2"
    And I capture saved hierarchy dump "uidump-calc-epf-00-hub.xml" as "calc epf 00 hub"
    And I capture saved hierarchy dump "uidump-calc-epf-01-top.xml" as "calc epf 01 top"
    And I capture saved hierarchy dump "uidump-calc-epf-02-scroll1.xml" as "calc epf 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-epf-03-scroll2.xml" as "calc epf 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-epf-04-scroll3.xml" as "calc epf 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-fd-00-hub.xml" as "calc fd 00 hub"
    And I capture saved hierarchy dump "uidump-calc-fd-01-top.xml" as "calc fd 01 top"
    And I capture saved hierarchy dump "uidump-calc-fd-02-scroll1.xml" as "calc fd 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-fd-03-scroll2.xml" as "calc fd 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-fd-04-scroll3.xml" as "calc fd 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-fd-05-target-tab.xml" as "calc fd 05 target tab"
    And I capture saved hierarchy dump "uidump-calc-fd-06-target-scroll.xml" as "calc fd 06 target scroll"
    And I capture saved hierarchy dump "uidump-calc-inflation-00-hub.xml" as "calc inflation 00 hub"
    And I capture saved hierarchy dump "uidump-calc-inflation-01-top.xml" as "calc inflation 01 top"
    And I capture saved hierarchy dump "uidump-calc-inflation-02-scroll1.xml" as "calc inflation 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-inflation-03-scroll2.xml" as "calc inflation 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-inflation-04-scroll3.xml" as "calc inflation 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-inflation-10-top.xml" as "calc inflation 10 top"
    And I capture saved hierarchy dump "uidump-calc-inflation-11-scroll.xml" as "calc inflation 11 scroll"
    And I capture saved hierarchy dump "uidump-calc-inflation-12-baseyear.xml" as "calc inflation 12 baseyear"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @CALCULATORS_003
  Scenario: Calculators screens part 3
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-calc-inflation-13-after-dismiss.xml" as "calc inflation 13 after dismiss"
    And I capture saved hierarchy dump "uidump-calc-inflation-14-showmore.xml" as "calc inflation 14 showmore"
    And I capture saved hierarchy dump "uidump-calc-lumpsum-00-hub.xml" as "calc lumpsum 00 hub"
    And I capture saved hierarchy dump "uidump-calc-lumpsum-01-top.xml" as "calc lumpsum 01 top"
    And I capture saved hierarchy dump "uidump-calc-lumpsum-02-scroll1.xml" as "calc lumpsum 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-lumpsum-03-scroll2.xml" as "calc lumpsum 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-lumpsum-04-scroll3.xml" as "calc lumpsum 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-nps-00-hub.xml" as "calc nps 00 hub"
    And I capture saved hierarchy dump "uidump-calc-nps-01-top.xml" as "calc nps 01 top"
    And I capture saved hierarchy dump "uidump-calc-nps-02-scroll1.xml" as "calc nps 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-nps-03-scroll2.xml" as "calc nps 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-nps-04-scroll3.xml" as "calc nps 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-ppf-00-hub.xml" as "calc ppf 00 hub"
    And I capture saved hierarchy dump "uidump-calc-ppf-01-top.xml" as "calc ppf 01 top"
    And I capture saved hierarchy dump "uidump-calc-ppf-02-scroll1.xml" as "calc ppf 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-ppf-03-scroll2.xml" as "calc ppf 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-ppf-04-scroll3.xml" as "calc ppf 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-recheck-00-hub.xml" as "calc recheck 00 hub"
    And I capture saved hierarchy dump "uidump-calc-retirement-00-hub.xml" as "calc retirement 00 hub"
    And I capture saved hierarchy dump "uidump-calc-retirement-01-top.xml" as "calc retirement 01 top"
    And I capture saved hierarchy dump "uidump-calc-retirement-02-scroll1.xml" as "calc retirement 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-retirement-03-scroll2.xml" as "calc retirement 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-retirement-04-scroll3.xml" as "calc retirement 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-sip-00-hub.xml" as "calc sip 00 hub"
    And I capture saved hierarchy dump "uidump-calc-sip-01-top.xml" as "calc sip 01 top"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @CALCULATORS_004
  Scenario: Calculators screens part 4
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-calc-sip-02-scroll1.xml" as "calc sip 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-sip-03-scroll2.xml" as "calc sip 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-sip-04-scroll3.xml" as "calc sip 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-sip-10-top.xml" as "calc sip 10 top"
    And I capture saved hierarchy dump "uidump-calc-sip-11-edit-sheet.xml" as "calc sip 11 edit sheet"
    And I capture saved hierarchy dump "uidump-calc-state-check.xml" as "calc state check"
    And I capture saved hierarchy dump "uidump-calc-taxindian-00-hub.xml" as "calc taxindian 00 hub"
    And I capture saved hierarchy dump "uidump-calc-taxindian-01-top.xml" as "calc taxindian 01 top"
    And I capture saved hierarchy dump "uidump-calc-taxindian-02-scroll1.xml" as "calc taxindian 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-taxindian-03-scroll2.xml" as "calc taxindian 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-taxindian-04-scroll3.xml" as "calc taxindian 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-taxintl-00-hub.xml" as "calc taxintl 00 hub"
    And I capture saved hierarchy dump "uidump-calc-taxintl-01-top.xml" as "calc taxintl 01 top"
    And I capture saved hierarchy dump "uidump-calc-taxintl-02-scroll1.xml" as "calc taxintl 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-taxintl-03-scroll2.xml" as "calc taxintl 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-taxintl-04-scroll3.xml" as "calc taxintl 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-terminsurance-00-hub.xml" as "calc terminsurance 00 hub"
    And I capture saved hierarchy dump "uidump-calc-terminsurance-01-top.xml" as "calc terminsurance 01 top"
    And I capture saved hierarchy dump "uidump-calc-terminsurance-02-scroll1.xml" as "calc terminsurance 02 scroll1"
    And I capture saved hierarchy dump "uidump-calc-terminsurance-03-scroll2.xml" as "calc terminsurance 03 scroll2"
    And I capture saved hierarchy dump "uidump-calc-terminsurance-04-scroll3.xml" as "calc terminsurance 04 scroll3"
    And I capture saved hierarchy dump "uidump-calc-terminsurance-10-verify.xml" as "calc terminsurance 10 verify"
    And I capture saved hierarchy dump "uidump-calc-terminsurance-11-scroll.xml" as "calc terminsurance 11 scroll"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
