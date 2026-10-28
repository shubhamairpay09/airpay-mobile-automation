package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History/Filter Icon/Show More/Filter. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistory6Screen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history-dropdown-filter-icon-show-more-filter";

    public static final String FILTER = "//android.widget.TextView[@text='Filter']";

    @AndroidFindBy(xpath = FILTER)
    private WebElement filter;

    public static final String BELOW2500 = "//android.widget.TextView[@text='Below 2500']";

    @AndroidFindBy(xpath = BELOW2500)
    private WebElement below2500;

    public static final String MIN = "//android.widget.TextView[@text='Min']";

    @AndroidFindBy(xpath = MIN)
    private WebElement min;

    public static final String MAX = "//android.widget.TextView[@text='Max']";

    @AndroidFindBy(xpath = MAX)
    private WebElement max;

    public static final String PLEASE_ENTER_MIN_AND_MAX_AMOUNT = "//android.widget.TextView[@text='Please enter Min and Max Amount']";

    @AndroidFindBy(xpath = PLEASE_ENTER_MIN_AND_MAX_AMOUNT)
    private WebElement pleaseEnterMinAndMaxAmount;

    public static final String APPLY = "//android.widget.TextView[@text='Apply']";

    @AndroidFindBy(xpath = APPLY)
    private WebElement apply;

    public HomeNonChannelsCreditLedgerHistory6Screen(TestContext context) {
        super(context);
    }
}
