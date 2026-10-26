package com.airpay.vyaapaar.screens.govtservices;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Govt Services — Government Services. Module govt-services. */
public class GovtServicesScreen extends BaseScreen {

    public static final String PAGE_ID = "govt-services";

    public static final String GOVERNMENT_SERVICES = "//android.widget.TextView[@text='Government Services']";

    @AndroidFindBy(xpath = GOVERNMENT_SERVICES)
    private WebElement governmentServices;

    public GovtServicesScreen(TestContext context) {
        super(context);
    }
}
