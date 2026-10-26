package com.airpay.vyaapaar.screens.register;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Register / airpay. Module register. */
public class RegisterTermsAndConditionsScreen extends BaseScreen {

    public static final String PAGE_ID = "register-terms-and-conditions";

    public static final String BROWSTATUSWRAP = "//android.view.View[@resource-id='browStatusWrap']";

    @AndroidFindBy(xpath = BROWSTATUSWRAP)
    private WebElement browstatuswrap;

    public static final String AIRPAY = "//android.view.View[contains(@content-desc,'airpay')]";

    @AndroidFindBy(xpath = AIRPAY)
    private WebElement airpay;

    public static final String TERMS_CONDITIONS = "//android.widget.TextView[@text='TERMS & CONDITIONS']";

    @AndroidFindBy(xpath = TERMS_CONDITIONS)
    private WebElement termsConditions;

    public static final String WE_RE_PULLING_OUT_ALL_THE_STOPS_TO = "//android.widget.TextView[@text=concat('We',\"'\",'re pulling out all the stops to protect your business interest at all times.')]";

    @AndroidFindBy(xpath = WE_RE_PULLING_OUT_ALL_THE_STOPS_TO)
    private WebElement weRePullingOutAllTheStopsTo;

    public static final String THIS_PAGE_STATES_THE_TERMS_AND_CONDITIONS_UNDER = "//android.widget.TextView[@text='This page states the Terms and Conditions under which you (Visitor) may visit this Web site http:']";

    @AndroidFindBy(xpath = THIS_PAGE_STATES_THE_TERMS_AND_CONDITIONS_UNDER)
    private WebElement thisPageStatesTheTermsAndConditionsUnder;

    public static final String WWW_AIRPAY_CO_IN = "//android.view.View[contains(@content-desc,'www.airpay.co.in')]";

    @AndroidFindBy(xpath = WWW_AIRPAY_CO_IN)
    private WebElement wwwAirpayCoIn;

    public static final String HEREINAFTER_REFERRED_TO_AS_WEBSITE_PLEASE_READ_THIS = "//android.widget.TextView[@text=concat('( hereinafter referred to as ',\"'\",'Website',\"'\",'. Please read this page carefully. If you do not accept the Terms and Conditions stated here, we would request you to exit the Website. Airpay, any of its business divisions and / or its subsidiaries, associate companies (in India or abroad) reserve their respective rights to revise these Terms and Conditions at any time by updating this post. You should visit this page periodically to re-appraise yourself of the Terms and Conditions, because they are binding on all Visitors of this Website. The Service is offered to you conditioned on your acceptance without modification of the terms, conditions, and notices contained herein. In using this Website or the Services by AIRPAY, you are deemed to have read and agreed to the following Terms and Conditions.')]";

    @AndroidFindBy(xpath = HEREINAFTER_REFERRED_TO_AS_WEBSITE_PLEASE_READ_THIS)
    private WebElement hereinafterReferredToAsWebsitePleaseReadThis;

    public static final String THE_FOLLOWING_DEFINITIONS_APPLY_TO_THESE_TERMS_AND = "//android.widget.TextView[@text='The following definitions apply to these Terms and Conditions, Privacy Statement and Disclaimer Notice and any or all Agreements:']";

    @AndroidFindBy(xpath = THE_FOLLOWING_DEFINITIONS_APPLY_TO_THESE_TERMS_AND)
    private WebElement theFollowingDefinitionsApplyToTheseTermsAnd;

    public static final String TERMS_AND_CONDITIONS = "//android.widget.TextView[@text='Terms and Conditions']";

    @AndroidFindBy(xpath = TERMS_AND_CONDITIONS)
    private WebElement termsAndConditions;

    public RegisterTermsAndConditionsScreen(TestContext context) {
        super(context);
    }
}
