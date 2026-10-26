package com.airpay.vyaapaar.screens.baazaarsearchoilalmondsearphoneshere;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Baazaar / Search oil, almonds, earphones here — Search oil, almonds, earphones here. Module baazaar-search-oil-almonds-earphones-here. */
public class BaazaarSearchOilAlmondsEarphonesHereScreen extends BaseScreen {

    public static final String PAGE_ID = "baazaar-search-oil-almonds-earphones-here";

    public static final String SEARCH_OIL_ALMONDS_EARPHONES_HERE = "//android.widget.TextView[@text='Search oil, almonds, earphones here']";

    @AndroidFindBy(xpath = SEARCH_OIL_ALMONDS_EARPHONES_HERE)
    private WebElement searchOilAlmondsEarphonesHere;

    public BaazaarSearchOilAlmondsEarphonesHereScreen(TestContext context) {
        super(context);
    }
}
