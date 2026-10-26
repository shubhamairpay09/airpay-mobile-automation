package com.airpay.vyaapaar.screens.privacypolicy;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Privacy Policy — airpay. Module privacy-policy. */
public class ProfileSettingsPrivacyPolicyScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-settings-privacy-policy";

    public static final String BROWSTATUSWRAP = "//android.view.View[@resource-id='browStatusWrap']";

    @AndroidFindBy(xpath = BROWSTATUSWRAP)
    private WebElement browstatuswrap;

    public static final String AIRPAY = "//android.view.View[contains(@content-desc,'airpay')]";

    @AndroidFindBy(xpath = AIRPAY)
    private WebElement airpay;

    public static final String PRIVACY_POLICY = "//android.widget.TextView[@text='PRIVACY POLICY']";

    @AndroidFindBy(xpath = PRIVACY_POLICY)
    private WebElement privacyPolicy;

    public static final String LAST_UPDATED_DATE17TH_SEPTEMBER2025 = "//android.widget.TextView[@text='Last Updated Date: 17th September 2025']";

    @AndroidFindBy(xpath = LAST_UPDATED_DATE17TH_SEPTEMBER2025)
    private WebElement lastUpdatedDate17thSeptember2025;

    public static final String THIS_PRIVACY_POLICY_SETS_OUT_HOW_AIRPAY_USES = "//android.widget.TextView[@text='This privacy policy sets out how AIRPAY uses and protects any information of a person who visit the site ']";

    @AndroidFindBy(xpath = THIS_PRIVACY_POLICY_SETS_OUT_HOW_AIRPAY_USES)
    private WebElement thisPrivacyPolicySetsOutHowAirpayUses;

    public static final String HTTPS_AIRPAY_CO_IN_VYAAPAAR = "//android.view.View[contains(@content-desc,'https://airpay.co.in/vyaapaar')]";

    @AndroidFindBy(xpath = HTTPS_AIRPAY_CO_IN_VYAAPAAR)
    private WebElement httpsAirpayCoInVyaapaar;

    public static final String AND_PROVIDE_INFORMATION_TO_AIRPAY_THE_VISITOR_YOU = "//android.widget.TextView[@text='and provide information to AIRPAY (the Visitor / You).']";

    @AndroidFindBy(xpath = AND_PROVIDE_INFORMATION_TO_AIRPAY_THE_VISITOR_YOU)
    private WebElement andProvideInformationToAirpayTheVisitorYou;

    public static final String AIRPAY_ENDEAVOURS_TO_SAFEGUARD_AND_PROTECT_THE_VISITOR = "//android.widget.TextView[@text=concat('AIRPAY endeavours to safeguard and protect the Visitor',\"'\",'s privacy.')]";

    @AndroidFindBy(xpath = AIRPAY_ENDEAVOURS_TO_SAFEGUARD_AND_PROTECT_THE_VISITOR)
    private WebElement airpayEndeavoursToSafeguardAndProtectTheVisitor;

    public static final String THE_INFORMATION_OF_CUSTOMERS_AND_OTHERS_WHO_VISIT = "//android.widget.TextView[@text=concat('The information of customers and others who visit the AIRPAY',\"'\",'s website we believe it is necessary to post a privacy statement. The information shared with the AIRPAY will be treated as private. We also desire to say explicitly that adequate precautions have been taken to protect information relating to customers and their information available with the AIRPAY.')]";

    @AndroidFindBy(xpath = THE_INFORMATION_OF_CUSTOMERS_AND_OTHERS_WHO_VISIT)
    private WebElement theInformationOfCustomersAndOthersWhoVisit;

    public static final String CUSTOMER_CONFIDENTIALITY_AND_PRIVACY_ARE_OF_UTMOST_CONCERN = "//android.widget.TextView[@text='Customer confidentiality and privacy are of utmost concern to AIRPAY.']";

    @AndroidFindBy(xpath = CUSTOMER_CONFIDENTIALITY_AND_PRIVACY_ARE_OF_UTMOST_CONCERN)
    private WebElement customerConfidentialityAndPrivacyAreOfUtmostConcern;

    public static final String WHO_IS_COVERED_BY_THIS_POLICY = "//android.widget.TextView[@text='Who is covered by this Policy?']";

    @AndroidFindBy(xpath = WHO_IS_COVERED_BY_THIS_POLICY)
    private WebElement whoIsCoveredByThisPolicy;

    public static final String ALL_PERSONS_WHO_VISIT_THE_WEBSITE = "//android.widget.TextView[@text='All persons who visit the website']";

    @AndroidFindBy(xpath = ALL_PERSONS_WHO_VISIT_THE_WEBSITE)
    private WebElement allPersonsWhoVisitTheWebsite;

    public static final String AND_PROVIDE_INFORMATION_TO_THE_AIRPAY_ONLINE_ARE = "//android.widget.TextView[@text='  and provide information to the AIRPAY online are covered under this Policy. This Policy does not cover the information provided through other modes of communication to AIRPAY.']";

    @AndroidFindBy(xpath = AND_PROVIDE_INFORMATION_TO_THE_AIRPAY_ONLINE_ARE)
    private WebElement andProvideInformationToTheAirpayOnlineAre;

    public static final String AIRPAY_VYAAPAAR = "//android.widget.TextView[@text='Airpay Vyaapaar']";

    @AndroidFindBy(xpath = AIRPAY_VYAAPAAR)
    private WebElement airpayVyaapaar;

    public ProfileSettingsPrivacyPolicyScreen(TestContext context) {
        super(context);
    }
}
