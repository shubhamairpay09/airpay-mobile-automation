package com.airpay.vyaapaar.screens.lic;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** LIC — LIC Premium. Module lic. */
public class LicScreen extends BaseScreen {

    public static final String PAGE_ID = "lic";

    public static final String LIC_PREMIUM = "//android.widget.TextView[@text='LIC Premium']";

    @AndroidFindBy(xpath = LIC_PREMIUM)
    private WebElement licPremium;

    public LicScreen(TestContext context) {
        super(context);
    }
}
