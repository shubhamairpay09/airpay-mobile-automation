package com.airpay.vyaapaar.screens.khata;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Khata — Khata. Module khata. */
public class FeaturedKhataScreen extends BaseScreen {

    public static final String PAGE_ID = "featured-khata";

    public static final String KHATA = "//android.widget.TextView[@text='Khata']";

    @AndroidFindBy(xpath = KHATA)
    private WebElement khata;

    public FeaturedKhataScreen(TestContext context) {
        super(context);
    }
}
