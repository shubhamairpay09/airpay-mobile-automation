package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** City search Saharanpur. Module home. */
public class OpenCitySearchSaharanpurScreen extends BaseScreen {

    public static final String PAGE_ID = "open-city-search-saharanpur";

    public static final String SELECT_CITY = "//android.widget.TextView[@text='Select City']";

    @AndroidFindBy(xpath = SELECT_CITY)
    private WebElement selectCity;

    public static final String S = "//android.widget.TextView[@text='S']";

    @AndroidFindBy(xpath = S)
    private WebElement s;

    public static final String SAHARANPUR = "//android.widget.TextView[@text='Saharanpur']";

    @AndroidFindBy(xpath = SAHARANPUR)
    private WebElement saharanpur;

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public OpenCitySearchSaharanpurScreen(TestContext context) {
        super(context);
    }
}
