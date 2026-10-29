package com.airpay.vyaapaar.screens.creditledgerhistory;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Credit Ledger History/Filter Icon/Show More. Module credit-ledger-history. */
public class HomeNonChannelsCreditLedgerHistory3Screen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-credit-ledger-history-dropdown-filter-icon-show-more";

    public static final String GST = "//android.widget.TextView[contains(@content-desc,'GST')]";

    @AndroidFindBy(xpath = GST)
    private WebElement gst;

    public static final String NPS = "//android.widget.TextView[contains(@content-desc,'NPS')]";

    @AndroidFindBy(xpath = NPS)
    private WebElement nps;

    public static final String POS = "//android.widget.TextView[contains(@content-desc,'POS')]";

    @AndroidFindBy(xpath = POS)
    private WebElement pos;

    public static final String TDS = "//android.widget.TextView[contains(@content-desc,'TDS')]";

    @AndroidFindBy(xpath = TDS)
    private WebElement tds;

    public static final String AEPS = "//android.widget.TextView[contains(@content-desc,'AEPS')]";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String BBPS = "//android.widget.TextView[contains(@content-desc,'BBPS')]";

    @AndroidFindBy(xpath = BBPS)
    private WebElement bbps;

    public static final String GOLD = "//android.widget.TextView[contains(@content-desc,'GOLD')]";

    @AndroidFindBy(xpath = GOLD)
    private WebElement gold;

    public static final String ONDC = "//android.widget.TextView[contains(@content-desc,'ONDC')]";

    @AndroidFindBy(xpath = ONDC)
    private WebElement ondc;

    public static final String ADHOC = "//android.widget.TextView[contains(@content-desc,'ADHOC')]";

    @AndroidFindBy(xpath = ADHOC)
    private WebElement adhoc;

    public static final String TOPUP = "//android.widget.TextView[contains(@content-desc,'TOPUP')]";

    @AndroidFindBy(xpath = TOPUP)
    private WebElement topup;

    public static final String FASTAG = "//android.widget.TextView[contains(@content-desc,'FASTAG')]";

    @AndroidFindBy(xpath = FASTAG)
    private WebElement fastag;

    public static final String BAAZAAR = "//android.widget.TextView[contains(@content-desc,'BAAZAAR')]";

    @AndroidFindBy(xpath = BAAZAAR)
    private WebElement baazaar;

    public static final String GIFTCARD = "//android.widget.TextView[contains(@content-desc,'GIFTCARD')]";

    @AndroidFindBy(xpath = GIFTCARD)
    private WebElement giftcard;

    public static final String RECHARGE = "//android.widget.TextView[contains(@content-desc,'RECHARGE')]";

    @AndroidFindBy(xpath = RECHARGE)
    private WebElement recharge;

    public static final String REFUNDED = "//android.widget.TextView[contains(@content-desc,'REFUNDED')]";

    @AndroidFindBy(xpath = REFUNDED)
    private WebElement refunded;

    public static final String SEARCH = "//android.view.View[contains(@content-desc,'Search')]";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public HomeNonChannelsCreditLedgerHistory3Screen(TestContext context) {
        super(context);
    }
}
