@airpay-money @money @android @a11y @capture @profile
Feature: Capture Profile settings screens
  Saved hierarchy dumps. No taps. Results append into the money scorecard.

  @PROFILE_001
  Scenario: Profile settings screens part 1
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-prof-00-hub.xml" as "prof 00 hub"
    And I capture saved hierarchy dump "uidump-prof-01-top.xml" as "prof 01 top"
    And I capture saved hierarchy dump "uidump-prof-02-scroll1.xml" as "prof 02 scroll1"
    And I capture saved hierarchy dump "uidump-prof-03-scroll2.xml" as "prof 03 scroll2"
    And I capture saved hierarchy dump "uidump-prof-04-end.xml" as "prof 04 end"
    And I capture saved hierarchy dump "uidump-prof-10-top.xml" as "prof 10 top"
    And I capture saved hierarchy dump "uidump-prof-11-scroll1.xml" as "prof 11 scroll1"
    And I capture saved hierarchy dump "uidump-prof-12-scroll2.xml" as "prof 12 scroll2"
    And I capture saved hierarchy dump "uidump-prof-13-end.xml" as "prof 13 end"
    And I capture saved hierarchy dump "uidump-prof-addr-01-top.xml" as "prof addr 01 top"
    And I capture saved hierarchy dump "uidump-prof-addr-02-add.xml" as "prof addr 02 add"
    And I capture saved hierarchy dump "uidump-prof-addr-03-pincode.xml" as "prof addr 03 pincode"
    And I capture saved hierarchy dump "uidump-prof-addr-04-residence.xml" as "prof addr 04 residence"
    And I capture saved hierarchy dump "uidump-prof-addr-05-residence-scroll.xml" as "prof addr 05 residence scroll"
    And I capture saved hierarchy dump "uidump-prof-addr-06-form.xml" as "prof addr 06 form"
    And I capture saved hierarchy dump "uidump-prof-addr-07-submit-empty.xml" as "prof addr 07 submit empty"
    And I capture saved hierarchy dump "uidump-prof-A-root.xml" as "prof A root"
    And I capture saved hierarchy dump "uidump-prof-bank-01-top.xml" as "prof bank 01 top"
    And I capture saved hierarchy dump "uidump-prof-bank-02-add.xml" as "prof bank 02 add"
    And I capture saved hierarchy dump "uidump-prof-bank-03-search.xml" as "prof bank 03 search"
    And I capture saved hierarchy dump "uidump-prof-bank-04-form.xml" as "prof bank 04 form"
    And I capture saved hierarchy dump "uidump-prof-bank-05-ifsc-search.xml" as "prof bank 05 ifsc search"
    And I capture saved hierarchy dump "uidump-prof-bank-06-ifsc-noresult.xml" as "prof bank 06 ifsc noresult"
    And I capture saved hierarchy dump "uidump-prof-bank-07-clearbtn.xml" as "prof bank 07 clearbtn"
    And I capture saved hierarchy dump "uidump-prof-bank-08-ifsc-88.xml" as "prof bank 08 ifsc 88"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @PROFILE_002
  Scenario: Profile settings screens part 2
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-prof-bank-09-acct-form.xml" as "prof bank 09 acct form"
    And I capture saved hierarchy dump "uidump-prof-bank-10-submit-empty.xml" as "prof bank 10 submit empty"
    And I capture saved hierarchy dump "uidump-prof-bank-11-name.xml" as "prof bank 11 name"
    And I capture saved hierarchy dump "uidump-prof-bank-12-scrolled.xml" as "prof bank 12 scrolled"
    And I capture saved hierarchy dump "uidump-prof-bank-13-dummy-acct.xml" as "prof bank 13 dummy acct"
    And I capture saved hierarchy dump "uidump-prof-bank-14-verify-result.xml" as "prof bank 14 verify result"
    And I capture saved hierarchy dump "uidump-prof-bank-15-add.xml" as "prof bank 15 add"
    And I capture saved hierarchy dump "uidump-prof-bank-16-ifsc88.xml" as "prof bank 16 ifsc88"
    And I capture saved hierarchy dump "uidump-prof-bank-17-form.xml" as "prof bank 17 form"
    And I capture saved hierarchy dump "uidump-prof-bank-18-result.xml" as "prof bank 18 result"
    And I capture saved hierarchy dump "uidump-prof-bank-19-picker.xml" as "prof bank 19 picker"
    And I capture saved hierarchy dump "uidump-prof-B-root.xml" as "prof B root"
    And I capture saved hierarchy dump "uidump-prof-contact-02-subject.xml" as "prof contact 02 subject"
    And I capture saved hierarchy dump "uidump-prof-contact-03-kbd-done.xml" as "prof contact 03 kbd done"
    And I capture saved hierarchy dump "uidump-prof-contact-04-attach.xml" as "prof contact 04 attach"
    And I capture saved hierarchy dump "uidump-prof-contact-05-dismiss.xml" as "prof contact 05 dismiss"
    And I capture saved hierarchy dump "uidump-prof-contact-06-closed.xml" as "prof contact 06 closed"
    And I capture saved hierarchy dump "uidump-prof-C-root.xml" as "prof C root"
    And I capture saved hierarchy dump "uidump-prof-docs-01-top.xml" as "prof docs 01 top"
    And I capture saved hierarchy dump "uidump-prof-docs-02-icon.xml" as "prof docs 02 icon"
    And I capture saved hierarchy dump "uidump-prof-D-root.xml" as "prof D root"
    And I capture saved hierarchy dump "uidump-prof-E1.xml" as "prof E1"
    And I capture saved hierarchy dump "uidump-prof-E2.xml" as "prof E2"
    And I capture saved hierarchy dump "uidump-prof-faq-00-now.xml" as "prof faq 00 now"
    And I capture saved hierarchy dump "uidump-prof-faq-01-open.xml" as "prof faq 01 open"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @PROFILE_003
  Scenario: Profile settings screens part 3
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-prof-fresh.xml" as "prof fresh"
    And I capture saved hierarchy dump "uidump-prof-G-root.xml" as "prof G root"
    And I capture saved hierarchy dump "uidump-prof-H1.xml" as "prof H1"
    And I capture saved hierarchy dump "uidump-prof-help-01.xml" as "prof help 01"
    And I capture saved hierarchy dump "uidump-prof-help-02.xml" as "prof help 02"
    And I capture saved hierarchy dump "uidump-prof-I-root.xml" as "prof I root"
    And I capture saved hierarchy dump "uidump-prof-J-root.xml" as "prof J root"
    And I capture saved hierarchy dump "uidump-prof-K-root.xml" as "prof K root"
    And I capture saved hierarchy dump "uidump-prof-kyc-01-top.xml" as "prof kyc 01 top"
    And I capture saved hierarchy dump "uidump-prof-L-root.xml" as "prof L root"
    And I capture saved hierarchy dump "uidump-prof-M-scroll.xml" as "prof M scroll"
    And I capture saved hierarchy dump "uidump-prof-now.xml" as "prof now"
    And I capture saved hierarchy dump "uidump-prof-N-settings.xml" as "prof N settings"
    And I capture saved hierarchy dump "uidump-prof-O-settings.xml" as "prof O settings"
    And I capture saved hierarchy dump "uidump-prof-personal-00-root.xml" as "prof personal 00 root"
    And I capture saved hierarchy dump "uidump-prof-personal-01-top.xml" as "prof personal 01 top"
    And I capture saved hierarchy dump "uidump-prof-personal-02-edit.xml" as "prof personal 02 edit"
    And I capture saved hierarchy dump "uidump-prof-personal-02-scroll1.xml" as "prof personal 02 scroll1"
    And I capture saved hierarchy dump "uidump-prof-personal-03-scroll.xml" as "prof personal 03 scroll"
    And I capture saved hierarchy dump "uidump-prof-personal-03-scroll2.xml" as "prof personal 03 scroll2"
    And I capture saved hierarchy dump "uidump-prof-personal-04-dob.xml" as "prof personal 04 dob"
    And I capture saved hierarchy dump "uidump-prof-personal-05-dob-cancel.xml" as "prof personal 05 dob cancel"
    And I capture saved hierarchy dump "uidump-prof-personal-06-marital.xml" as "prof personal 06 marital"
    And I capture saved hierarchy dump "uidump-prof-personal-07-marital-dismiss.xml" as "prof personal 07 marital dismiss"
    And I capture saved hierarchy dump "uidump-prof-personal-08-employment.xml" as "prof personal 08 employment"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @PROFILE_004
  Scenario: Profile settings screens part 4
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-prof-personal-09-gotit.xml" as "prof personal 09 gotit"
    And I capture saved hierarchy dump "uidump-prof-personal-10-menu.xml" as "prof personal 10 menu"
    And I capture saved hierarchy dump "uidump-prof-personal-11-gender-female.xml" as "prof personal 11 gender female"
    And I capture saved hierarchy dump "uidump-prof-personal-12-gender-male.xml" as "prof personal 12 gender male"
    And I capture saved hierarchy dump "uidump-prof-P-settings.xml" as "prof P settings"
    And I capture saved hierarchy dump "uidump-prof-Q-settings.xml" as "prof Q settings"
    And I capture saved hierarchy dump "uidump-prof-relaunch.xml" as "prof relaunch"
    And I capture saved hierarchy dump "uidump-prof-reopen.xml" as "prof reopen"
    And I capture saved hierarchy dump "uidump-prof-reset-a.xml" as "prof reset a"
    And I capture saved hierarchy dump "uidump-prof-reset-b.xml" as "prof reset b"
    And I capture saved hierarchy dump "uidump-prof-R-settings.xml" as "prof R settings"
    And I capture saved hierarchy dump "uidump-prof-set-01-screenlock.xml" as "prof set 01 screenlock"
    And I capture saved hierarchy dump "uidump-prof-set-02-changepin.xml" as "prof set 02 changepin"
    And I capture saved hierarchy dump "uidump-prof-set-03-pin-cancel.xml" as "prof set 03 pin cancel"
    And I capture saved hierarchy dump "uidump-prof-set-04-utilities.xml" as "prof set 04 utilities"
    And I capture saved hierarchy dump "uidump-prof-set-05-tnc.xml" as "prof set 05 tnc"
    And I capture saved hierarchy dump "uidump-prof-set-06-privacy.xml" as "prof set 06 privacy"
    And I capture saved hierarchy dump "uidump-prof-set-06-privacy-end.xml" as "prof set 06 privacy end"
    And I capture saved hierarchy dump "uidump-prof-set-06-privacy-end2.xml" as "prof set 06 privacy end2"
    And I capture saved hierarchy dump "uidump-prof-set-06-privacy-last.xml" as "prof set 06 privacy last"
    And I capture saved hierarchy dump "uidump-prof-set-06-privacy-last2.xml" as "prof set 06 privacy last2"
    And I capture saved hierarchy dump "uidump-prof-set-07-acctmgmt.xml" as "prof set 07 acctmgmt"
    And I capture saved hierarchy dump "uidump-prof-set-08-rateus.xml" as "prof set 08 rateus"
    And I capture saved hierarchy dump "uidump-prof-set-09-ratedown.xml" as "prof set 09 ratedown"
    And I capture saved hierarchy dump "uidump-prof-set-10-ratestar.xml" as "prof set 10 ratestar"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @PROFILE_005
  Scenario: Profile settings screens part 5
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-prof-set-11-rateclose.xml" as "prof set 11 rateclose"
    And I capture saved hierarchy dump "uidump-prof-set-12-faqs.xml" as "prof set 12 faqs"
    And I capture saved hierarchy dump "uidump-prof-set-13-webmenu.xml" as "prof set 13 webmenu"
    And I capture saved hierarchy dump "uidump-prof-set-14-learn.xml" as "prof set 14 learn"
    And I capture saved hierarchy dump "uidump-prof-set-15-resources.xml" as "prof set 15 resources"
    And I capture saved hierarchy dump "uidump-prof-set-16-resources2.xml" as "prof set 16 resources2"
    And I capture saved hierarchy dump "uidump-prof-S-settings.xml" as "prof S settings"
    And I capture saved hierarchy dump "uidump-prof-T-settings.xml" as "prof T settings"
    And I capture saved hierarchy dump "uidump-prof-tx-01.xml" as "prof tx 01"
    And I capture saved hierarchy dump "uidump-prof-tx-02-gold.xml" as "prof tx 02 gold"
    And I capture saved hierarchy dump "uidump-prof-tx-03-nps.xml" as "prof tx 03 nps"
    And I capture saved hierarchy dump "uidump-prof-tx-04-sip.xml" as "prof tx 04 sip"
    And I capture saved hierarchy dump "uidump-prof-tx-05-redeem.xml" as "prof tx 05 redeem"
    And I capture saved hierarchy dump "uidump-prof-tx-06-moretabs.xml" as "prof tx 06 moretabs"
    And I capture saved hierarchy dump "uidump-prof-tx-07-tabs-end.xml" as "prof tx 07 tabs end"
    And I capture saved hierarchy dump "uidump-prof-unlocked.xml" as "prof unlocked"
    And I capture saved hierarchy dump "uidump-prof-web-01-about.xml" as "prof web 01 about"
    And I capture saved hierarchy dump "uidump-prof-web-01-about-end.xml" as "prof web 01 about end"
    And I capture saved hierarchy dump "uidump-prof-web-01-about-last.xml" as "prof web 01 about last"
    And I capture saved hierarchy dump "uidump-prof-web-02-menu.xml" as "prof web 02 menu"
    And I capture saved hierarchy dump "uidump-prof-web-03-contact.xml" as "prof web 03 contact"
    And I capture saved hierarchy dump "uidump-prof-web-03-contact-end.xml" as "prof web 03 contact end"
    And I capture saved hierarchy dump "uidump-prof-web-03-contact-last.xml" as "prof web 03 contact last"
    And I capture saved hierarchy dump "uidump-prof-web-04-menu.xml" as "prof web 04 menu"
    And I capture saved hierarchy dump "uidump-prof-web-05-testimonials.xml" as "prof web 05 testimonials"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @PROFILE_006
  Scenario: Profile settings screens part 6
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-prof-web-05-testimonials-end.xml" as "prof web 05 testimonials end"
    And I capture saved hierarchy dump "uidump-prof-web-06-learn.xml" as "prof web 06 learn"
    And I capture saved hierarchy dump "uidump-prof-web-07-blogs.xml" as "prof web 07 blogs"
    And I capture saved hierarchy dump "uidump-prof-web-07-blogs-cards.xml" as "prof web 07 blogs cards"
    And I capture saved hierarchy dump "uidump-prof-web-07-blogs-posts.xml" as "prof web 07 blogs posts"
    And I capture saved hierarchy dump "uidump-prof-web-08-menu.xml" as "prof web 08 menu"
    And I capture saved hierarchy dump "uidump-prof-web-09-humburg.xml" as "prof web 09 humburg"
    And I capture saved hierarchy dump "uidump-prof-web-10-fhc-mod.xml" as "prof web 10 fhc mod"
    And I capture saved hierarchy dump "uidump-prof-web-11-what-fhc.xml" as "prof web 11 what fhc"
    And I capture saved hierarchy dump "uidump-prof-web-12a-fhc.xml" as "prof web 12a fhc"
    And I capture saved hierarchy dump "uidump-prof-web-12-how-works.xml" as "prof web 12 how works"
    And I capture saved hierarchy dump "uidump-prof-web-13-start-fhc.xml" as "prof web 13 start fhc"
    And I capture saved hierarchy dump "uidump-prof-work-01-top.xml" as "prof work 01 top"
    And I capture saved hierarchy dump "uidump-prof-work-02-expand.xml" as "prof work 02 expand"
    And I capture saved hierarchy dump "uidump-prof-work-03-scroll1.xml" as "prof work 03 scroll1"
    And I capture saved hierarchy dump "uidump-prof-work-04-scroll2.xml" as "prof work 04 scroll2"
    And I capture saved hierarchy dump "uidump-prof-work-05-orgtype.xml" as "prof work 05 orgtype"
    And I capture saved hierarchy dump "uidump-prof-work-06-orgtype-search.xml" as "prof work 06 orgtype search"
    And I capture saved hierarchy dump "uidump-prof-work-07-orgtype-noresult.xml" as "prof work 07 orgtype noresult"
    And I capture saved hierarchy dump "uidump-prof-work-08-back.xml" as "prof work 08 back"
    And I capture saved hierarchy dump "uidump-prof-work-09-designation.xml" as "prof work 09 designation"
    And I capture saved hierarchy dump "uidump-prof-work-10-top.xml" as "prof work 10 top"
    And I capture saved hierarchy dump "uidump-prof-work-11-company.xml" as "prof work 11 company"
    And I capture saved hierarchy dump "uidump-prof-work-12-exp-year.xml" as "prof work 12 exp year"
    And I capture saved hierarchy dump "uidump-prof-work-13-form.xml" as "prof work 13 form"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @PROFILE_007
  Scenario: Profile settings screens part 7
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-prof-work-14-exp-year.xml" as "prof work 14 exp year"
    And I capture saved hierarchy dump "uidump-prof-work-15-exp-month.xml" as "prof work 15 exp month"
    And I capture saved hierarchy dump "uidump-prof-work-16-submit-empty.xml" as "prof work 16 submit empty"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
