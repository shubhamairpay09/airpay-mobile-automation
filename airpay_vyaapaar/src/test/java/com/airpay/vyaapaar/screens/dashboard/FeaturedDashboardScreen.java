package com.airpay.vyaapaar.screens.dashboard;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Dashboard — Dashboard. Module dashboard. */
public class FeaturedDashboardScreen extends BaseScreen {

    public static final String PAGE_ID = "featured-dashboard";

    public static final String DASHBOARD = "//android.widget.TextView[@text='Dashboard']";

    @AndroidFindBy(xpath = DASHBOARD)
    private WebElement dashboard;

    public FeaturedDashboardScreen(TestContext context) {
        super(context);
    }
}
