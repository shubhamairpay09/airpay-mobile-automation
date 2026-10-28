package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History/Filter Icon/Show More/Sort. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistory5Screen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history-dropdown-filter-icon-show-more-sort";

    public static final String SORT = "//android.widget.TextView[@text='Sort']";

    @AndroidFindBy(xpath = SORT)
    private WebElement sort;

    public static final String PRICE_HIGH_TO_LOW = "//android.widget.TextView[@text='Price - High to Low']";

    @AndroidFindBy(xpath = PRICE_HIGH_TO_LOW)
    private WebElement priceHighToLow;

    public static final String PRICE_LOW_TO_HIGH = "//android.widget.TextView[@text='Price - Low to High']";

    @AndroidFindBy(xpath = PRICE_LOW_TO_HIGH)
    private WebElement priceLowToHigh;

    public static final String STORE_ATO_Z = "//android.widget.TextView[@text='Store - A to Z']";

    @AndroidFindBy(xpath = STORE_ATO_Z)
    private WebElement storeAToZ;

    public static final String STORE_ZTO_A = "//android.widget.TextView[@text='Store - Z to A']";

    @AndroidFindBy(xpath = STORE_ZTO_A)
    private WebElement storeZToA;

    public static final String APPLY = "//android.widget.TextView[@text='Apply']";

    @AndroidFindBy(xpath = APPLY)
    private WebElement apply;

    public HomeNonChannelsCreditLedgerHistory5Screen(TestContext context) {
        super(context);
    }
}
