package com.airpay.vyaapaar.screens.aeps;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/AEPS/P2. Module aeps. */
public class HomeFeaturedServicesAepsP2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-aeps-p2";

    public static final String AGENT_ONBOARDING = "//android.widget.TextView[@text='Agent Onboarding']";

    @AndroidFindBy(xpath = AGENT_ONBOARDING)
    private WebElement agentOnboarding;

    public HomeFeaturedServicesAepsP2Screen(TestContext context) {
        super(context);
    }
}
