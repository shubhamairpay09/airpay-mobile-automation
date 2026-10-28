package com.airpay.vyaapaar.screens.services;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Services / Credit Ledger History / Filter Icon / Show More — GST. Module services. */
public class HomeCreditBalanceServicesCreditLedger3Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-services-credit-ledger-history-dropdown-filter-icon-show-more";

    public static final String GST = "//android.widget.TextView[@text='GST']";

    @AndroidFindBy(xpath = GST)
    private WebElement gst;

    public static final String NPS = "//android.widget.TextView[@text='NPS']";

    @AndroidFindBy(xpath = NPS)
    private WebElement nps;

    public static final String POS = "//android.widget.TextView[@text='POS']";

    @AndroidFindBy(xpath = POS)
    private WebElement pos;

    public static final String TDS = "//android.widget.TextView[@text='TDS']";

    @AndroidFindBy(xpath = TDS)
    private WebElement tds;

    public static final String AEPS = "//android.widget.TextView[@text='AEPS']";

    @AndroidFindBy(xpath = AEPS)
    private WebElement aeps;

    public static final String BBPS = "//android.widget.TextView[@text='BBPS']";

    @AndroidFindBy(xpath = BBPS)
    private WebElement bbps;

    public static final String GOLD = "//android.widget.TextView[@text='GOLD']";

    @AndroidFindBy(xpath = GOLD)
    private WebElement gold;

    public static final String ONDC = "//android.widget.TextView[@text='ONDC']";

    @AndroidFindBy(xpath = ONDC)
    private WebElement ondc;

    public static final String ADHOC = "//android.widget.TextView[@text='ADHOC']";

    @AndroidFindBy(xpath = ADHOC)
    private WebElement adhoc;

    public static final String TOPUP = "//android.widget.TextView[@text='TOPUP']";

    @AndroidFindBy(xpath = TOPUP)
    private WebElement topup;

    public static final String FASTAG = "//android.widget.TextView[@text='FASTAG']";

    @AndroidFindBy(xpath = FASTAG)
    private WebElement fastag;

    public static final String BAAZAAR = "//android.widget.TextView[@text='BAAZAAR']";

    @AndroidFindBy(xpath = BAAZAAR)
    private WebElement baazaar;

    public static final String GIFTCARD = "//android.widget.TextView[@text='GIFTCARD']";

    @AndroidFindBy(xpath = GIFTCARD)
    private WebElement giftcard;

    public static final String RECHARGE = "//android.widget.TextView[@text='RECHARGE']";

    @AndroidFindBy(xpath = RECHARGE)
    private WebElement recharge;

    public static final String REFUNDED = "//android.widget.TextView[@text='REFUNDED']";

    @AndroidFindBy(xpath = REFUNDED)
    private WebElement refunded;

    public static final String SEARCH = "//android.widget.TextView[@text='Search']";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public HomeCreditBalanceServicesCreditLedger3Screen(TestContext context) {
        super(context);
    }
}
