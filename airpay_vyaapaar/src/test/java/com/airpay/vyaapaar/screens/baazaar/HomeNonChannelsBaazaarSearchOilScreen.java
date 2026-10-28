package com.airpay.vyaapaar.screens.baazaar;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Baazaar/Search oil, almonds, earphones here. Module baazaar. */
public class HomeNonChannelsBaazaarSearchOilScreen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-baazaar-search-oil-almonds-earphones-here";

    public static final String SEARCH_OIL_ALMONDS_EARPHONES_HERE = "//android.widget.TextView[@text='Search oil, almonds, earphones here']";

    @AndroidFindBy(xpath = SEARCH_OIL_ALMONDS_EARPHONES_HERE)
    private WebElement searchOilAlmondsEarphonesHere;

    public HomeNonChannelsBaazaarSearchOilScreen(TestContext context) {
        super(context);
    }
}
