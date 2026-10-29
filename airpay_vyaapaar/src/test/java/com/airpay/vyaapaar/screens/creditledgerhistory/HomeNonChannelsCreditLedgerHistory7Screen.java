package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History/Filter Icon/Show More/Sort/Below 2500/2500 - 5000/5000 - 7500/7500 - 10000. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistory7Screen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history-dropdown-filter-icon-show-more-sort-below-2500-250";

    public static final String FILTER = "//android.widget.TextView[@text='Filter']";

    @AndroidFindBy(xpath = FILTER)
    private WebElement filter;

    public static final String BELOW2500 = "//android.widget.TextView[contains(@content-desc,'Below 2500')]";

    @AndroidFindBy(xpath = BELOW2500)
    private WebElement below2500;

    public static final String EL2500 = "//android.widget.EditText[@text='2500']";

    @AndroidFindBy(xpath = EL2500)
    private WebElement el2500;

    public static final String MIN = "//android.view.View[contains(@content-desc,'Min')]";

    @AndroidFindBy(xpath = MIN)
    private WebElement min;

    public static final String EL5000 = "//android.widget.EditText[@text='5000']";

    @AndroidFindBy(xpath = EL5000)
    private WebElement el5000;

    public static final String MAX = "//android.view.View[contains(@content-desc,'Max')]";

    @AndroidFindBy(xpath = MAX)
    private WebElement max;

    public static final String APPLY = "//android.widget.TextView[@text='Apply']";

    @AndroidFindBy(xpath = APPLY)
    private WebElement apply;

    public static final String EL7500 = "//android.widget.EditText[@text='7500']";

    @AndroidFindBy(xpath = EL7500)
    private WebElement el7500;

    public static final String EL10000 = "//android.widget.EditText[@text='10000']";

    @AndroidFindBy(xpath = EL10000)
    private WebElement el10000;

    public HomeNonChannelsCreditLedgerHistory7Screen(TestContext context) {
        super(context);
    }
}
