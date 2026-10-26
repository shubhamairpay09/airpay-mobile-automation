package com.airpay.vyaapaar.screens.postoffice;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Post Office. Module post-office. */
public class HomeCreditBalancePostOfficeScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-post-office";

    public static final String INDIA_POST_OFFICE_ICON = "//android.view.View[contains(@content-desc,'India Post Office icon')]";

    @AndroidFindBy(xpath = INDIA_POST_OFFICE_ICON)
    private WebElement indiaPostOfficeIcon;

    public static final String INDIA_POST_OFFICE = "//android.widget.TextView[@text='India Post Office']";

    @AndroidFindBy(xpath = INDIA_POST_OFFICE)
    private WebElement indiaPostOffice;

    public static final String SPEED_POST_BUSINESS_PARCEL_AND_OTHER_INDIA_POST = "//android.widget.TextView[@text='Speed Post, Business Parcel and other India Post shipping services']";

    @AndroidFindBy(xpath = SPEED_POST_BUSINESS_PARCEL_AND_OTHER_INDIA_POST)
    private WebElement speedPostBusinessParcelAndOtherIndiaPost;

    public static final String POST_OFFICE = "//android.widget.TextView[@text='Post Office']";

    @AndroidFindBy(xpath = POST_OFFICE)
    private WebElement postOffice;

    public HomeCreditBalancePostOfficeScreen(TestContext context) {
        super(context);
    }
}
