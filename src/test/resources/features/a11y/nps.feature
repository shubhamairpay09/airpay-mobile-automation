@airpay-money @money @android @a11y @capture @nps-kyc
Feature: Capture NPS KYC screen xpaths and a11y
  Operator taps. Automation only dumps locators and accessibility — no clicks.

  @MONEY_NPS_KYC_001
  Scenario: Capture NPS KYC Status, personal-detail sheets, Aadhaar consent and OTP WebView
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-nps-0.xml" as "NPS KYC Status Basic Information Start"
    And I capture saved hierarchy dump "uidump-nps-kyc-1.xml" as "NPS KYC Status scrolled"
    And I capture saved hierarchy dump "uidump-nps-kyc-basic-marital.xml" as "NPS KYC Select marital status"
    And I capture saved hierarchy dump "uidump-nps-kyc-basic-emp-2.xml" as "NPS KYC Select employment type"
    And I capture saved hierarchy dump "uidump-nps-kyc-after-basic.xml" as "NPS KYC Status Aadhaar Start after Basic"
    And I capture saved hierarchy dump "uidump-nps-kyc-aadhaar-0.xml" as "NPS KYC About KYC Process consent checked"
    And I capture saved hierarchy dump "uidump-nps-kyc-aadhaar-1.xml" as "NPS KYC About KYC Process consent unchecked"
    And I capture saved hierarchy dump "uidump-nps-kyc-aadhaar-checked.xml" as "NPS KYC About KYC Process consent ready"
    And I capture saved hierarchy dump "uidump-nps-kyc-aadhaar-2.xml" as "NPS KYC Verification Digilocker or Aadhaar OTP"
    And I capture saved hierarchy dump "uidump-nps-kyc-aadhaar-otp-0.xml" as "NPS KYC Aadhaar OTP WebView loading"
    And I capture saved hierarchy dump "uidump-nps-kyc-aadhaar-otp-1.xml" as "NPS KYC Aadhaar OTP UIDAI form"
    And I capture saved hierarchy dump "uidump-nps-back-1.xml" as "NPS KYC Status after leaving Aadhaar OTP"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate

  @MONEY_NPS_KYC_002
  Scenario: Capture NPS KYC DigiLocker success then later steps
    Given the airpay money app is installed on the device
    When I capture saved hierarchy dump "uidump-nps-kyc-now.xml" as "NPS KYC DigiLocker success Congratulations Proceed"
    And I capture saved hierarchy dump "uidump-nps-kyc-after-proceed.xml" as "NPS KYC Status PAN Start after DigiLocker"
    And I capture saved hierarchy dump "uidump-nps-kyc-pan-form.xml" as "NPS KYC PAN Details empty"
    And I capture saved hierarchy dump "uidump-nps-kyc-pan-verify-1.xml" as "NPS KYC Status PAN Photo Start after PAN verify"
    And I capture saved hierarchy dump "uidump-nps-kyc-pan-photo-1.xml" as "NPS KYC PAN Photo Crop image"
    And I capture saved hierarchy dump "uidump-nps-kyc-pan-photo-after-crop.xml" as "NPS KYC PAN Card preview Submit"
    And I capture saved hierarchy dump "uidump-nps-kyc-pan-photo-submit.xml" as "NPS KYC Status PAN Photo mismatch after Submit"
    And I capture saved hierarchy dump "uidump-nps-kyc-pan-photo-retry.xml" as "NPS KYC PAN Photo Google Photos picker"
    Then scorecards are written for airpay money
    And critical and serious money findings are within the configured gate
