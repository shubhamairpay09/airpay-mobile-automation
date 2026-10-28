package com.airpay.vyaapaar.screens.aeps;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / AEPS — AEPS. Module aeps. */
public class HomeCreditBalanceAepsScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-aeps";

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public HomeCreditBalanceAepsScreen(TestContext context) {
        super(context);
    }
}
