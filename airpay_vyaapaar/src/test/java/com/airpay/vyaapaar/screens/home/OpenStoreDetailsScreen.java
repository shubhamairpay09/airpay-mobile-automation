package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Store Details. Module home. */
public class OpenStoreDetailsScreen extends BaseScreen {

    public static final String PAGE_ID = "open-store-details";

    public static final String STORE_DETAILS = "//android.widget.TextView[@text='Store Details']";

    @AndroidFindBy(xpath = STORE_DETAILS)
    private WebElement storeDetails;

    public OpenStoreDetailsScreen(TestContext context) {
        super(context);
    }
}
