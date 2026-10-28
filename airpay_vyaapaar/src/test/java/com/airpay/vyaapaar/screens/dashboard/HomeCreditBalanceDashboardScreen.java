package com.airpay.vyaapaar.screens.dashboard;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Dashboard — Dashboard. Module dashboard. */
public class HomeCreditBalanceDashboardScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-dashboard";

    public static final String DASHBOARD = "//android.widget.TextView[@text='Dashboard']";

    @AndroidFindBy(xpath = DASHBOARD)
    private WebElement dashboard;

    public HomeCreditBalanceDashboardScreen(TestContext context) {
        super(context);
    }
}
