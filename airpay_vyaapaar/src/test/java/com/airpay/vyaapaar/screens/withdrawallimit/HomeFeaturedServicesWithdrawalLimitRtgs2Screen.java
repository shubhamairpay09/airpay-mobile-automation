package com.airpay.vyaapaar.screens.withdrawallimit;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/Withdrawal Limit/RTGS/Transfer to Distributor. Module withdrawal-limit. */
public class HomeFeaturedServicesWithdrawalLimitRtgs2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-withdrawal-limit-rtgs-transfer-to-distributor";

    public static final String WITHDRAWABLE_BALANCE = "//android.widget.TextView[contains(@content-desc,'Withdrawable Balance')]";

    @AndroidFindBy(xpath = WITHDRAWABLE_BALANCE)
    private WebElement withdrawableBalance;

    public static final String HISTORY = "//android.widget.TextView[contains(@content-desc,'History')]";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String TRANSACTION_TYPE = "//android.widget.TextView[contains(@content-desc,'Transaction Type')]";

    @AndroidFindBy(xpath = TRANSACTION_TYPE)
    private WebElement transactionType;

    public static final String NEFT = "//android.widget.TextView[contains(@content-desc,'NEFT')]";

    @AndroidFindBy(xpath = NEFT)
    private WebElement neft;

    public static final String IMPS = "//android.widget.TextView[contains(@content-desc,'IMPS')]";

    @AndroidFindBy(xpath = IMPS)
    private WebElement imps;

    public static final String RTGS = "//android.widget.TextView[contains(@content-desc,'RTGS')]";

    @AndroidFindBy(xpath = RTGS)
    private WebElement rtgs;

    public static final String UPI = "//android.widget.TextView[contains(@content-desc,'UPI')]";

    @AndroidFindBy(xpath = UPI)
    private WebElement upi;

    public static final String TRANSFER_TO_DISTRIBUTOR = "//android.widget.TextView[contains(@content-desc,'Transfer to Distributor')]";

    @AndroidFindBy(xpath = TRANSFER_TO_DISTRIBUTOR)
    private WebElement transferToDistributor;

    public static final String TRANSFER_TO_NETWORK = "//android.widget.TextView[contains(@content-desc,'Transfer to Network')]";

    @AndroidFindBy(xpath = TRANSFER_TO_NETWORK)
    private WebElement transferToNetwork;

    public static final String LINKED_ACCOUNTS = "//android.widget.TextView[contains(@content-desc,'Linked Accounts')]";

    @AndroidFindBy(xpath = LINKED_ACCOUNTS)
    private WebElement linkedAccounts;

    public static final String ACCOUNT_CIRCLE_ICON_ICON = "//android.view.View[contains(@content-desc,'Account Circle Icon icon')]";

    @AndroidFindBy(xpath = ACCOUNT_CIRCLE_ICON_ICON)
    private WebElement accountCircleIconIcon;

    public static final String RAVI_DUBEY = "//android.widget.TextView[contains(@content-desc,'Ravi Dubey')]";

    @AndroidFindBy(xpath = RAVI_DUBEY)
    private WebElement raviDubey;

    public static final String DFG = "//android.widget.TextView[contains(@content-desc,'dfg')]";

    @AndroidFindBy(xpath = DFG)
    private WebElement dfg;

    public static final String SUCCESS_ICON = "//android.view.View[contains(@content-desc,'Success Icon')]";

    @AndroidFindBy(xpath = SUCCESS_ICON)
    private WebElement successIcon;

    public static final String AMOUNT = "//android.view.View[contains(@content-desc,'Amount')]";

    @AndroidFindBy(xpath = AMOUNT)
    private WebElement amount;

    public static final String WITHDRAW = "//android.widget.TextView[@text='Withdraw']";

    @AndroidFindBy(xpath = WITHDRAW)
    private WebElement withdraw;

    public static final String WITHDRAWAL_LIMIT = "//android.widget.TextView[@text='Withdrawal Limit']";

    @AndroidFindBy(xpath = WITHDRAWAL_LIMIT)
    private WebElement withdrawalLimit;

    public HomeFeaturedServicesWithdrawalLimitRtgs2Screen(TestContext context) {
        super(context);
    }
}
