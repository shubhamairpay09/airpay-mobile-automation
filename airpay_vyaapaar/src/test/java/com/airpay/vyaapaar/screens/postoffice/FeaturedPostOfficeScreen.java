package com.airpay.vyaapaar.screens.postoffice;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Post Office — Post Office. Module post-office. */
public class FeaturedPostOfficeScreen extends BaseScreen {

    public static final String PAGE_ID = "featured-post-office";

    public static final String POST_OFFICE = "//android.widget.TextView[@text='Post Office']";

    @AndroidFindBy(xpath = POST_OFFICE)
    private WebElement postOffice;

    public FeaturedPostOfficeScreen(TestContext context) {
        super(context);
    }
}
