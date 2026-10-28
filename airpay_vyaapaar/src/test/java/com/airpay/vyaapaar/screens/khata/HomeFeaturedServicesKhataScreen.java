package com.airpay.vyaapaar.screens.khata;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/Khata. Module khata. */
public class HomeFeaturedServicesKhataScreen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-khata";

    public static final String KHATA = "//android.widget.TextView[@text='Khata']";

    @AndroidFindBy(xpath = KHATA)
    private WebElement khata;

    public HomeFeaturedServicesKhataScreen(TestContext context) {
        super(context);
    }
}
