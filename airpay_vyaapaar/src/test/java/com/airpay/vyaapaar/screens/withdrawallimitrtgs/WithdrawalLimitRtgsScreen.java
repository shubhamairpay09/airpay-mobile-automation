package com.airpay.vyaapaar.screens.withdrawallimitrtgs;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Withdrawal Limit / RTGS — Withdrawable Balance. Module withdrawal-limit-rtgs. */
public class WithdrawalLimitRtgsScreen extends BaseScreen {

    public static final String PAGE_ID = "withdrawal-limit-rtgs";

    public static final String WITHDRAWABLE_BALANCE = "//android.widget.TextView[@text='Withdrawable Balance']";

    @AndroidFindBy(xpath = WITHDRAWABLE_BALANCE)
    private WebElement withdrawableBalance;

    public static final String HISTORY = "//android.widget.TextView[@text='History']";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String TRANSACTION_TYPE = "//android.widget.TextView[@text='Transaction Type']";

    @AndroidFindBy(xpath = TRANSACTION_TYPE)
    private WebElement transactionType;

    public static final String NEFT = "//android.widget.TextView[@text='NEFT']";

    @AndroidFindBy(xpath = NEFT)
    private WebElement neft;

    public static final String IMPS = "//android.widget.TextView[@text='IMPS']";

    @AndroidFindBy(xpath = IMPS)
    private WebElement imps;

    public static final String RTGS = "//android.widget.TextView[@text='RTGS']";

    @AndroidFindBy(xpath = RTGS)
    private WebElement rtgs;

    public static final String UPI = "//android.widget.TextView[@text='UPI']";

    @AndroidFindBy(xpath = UPI)
    private WebElement upi;

    public static final String TRANSFER_TO_DISTRIBUTOR = "//android.widget.TextView[@text='Transfer to Distributor']";

    @AndroidFindBy(xpath = TRANSFER_TO_DISTRIBUTOR)
    private WebElement transferToDistributor;

    public static final String TRANSFER_TO_NETWORK = "//android.widget.TextView[@text='Transfer to Network']";

    @AndroidFindBy(xpath = TRANSFER_TO_NETWORK)
    private WebElement transferToNetwork;

    public static final String LINKED_ACCOUNTS = "//android.widget.TextView[@text='Linked Accounts']";

    @AndroidFindBy(xpath = LINKED_ACCOUNTS)
    private WebElement linkedAccounts;

    public static final String BANK_ICON = "//android.view.View[contains(@content-desc,'Bank icon')]";

    @AndroidFindBy(xpath = BANK_ICON)
    private WebElement bankIcon;

    public static final String CENTRAL_BANK_OF_INDIA = "//android.widget.TextView[@text='Central Bank Of India']";

    @AndroidFindBy(xpath = CENTRAL_BANK_OF_INDIA)
    private WebElement centralBankOfIndia;

    public static final String EL0611 = "//android.widget.TextView[@text='**** **** **** 0611']";

    @AndroidFindBy(xpath = EL0611)
    private WebElement el0611;

    public static final String N_A = "//android.widget.TextView[@text='N/A']";

    @AndroidFindBy(xpath = N_A)
    private WebElement nA;

    public static final String DELETE_BANK_ICON = "//android.view.View[contains(@content-desc,'Delete Bank Icon')]";

    @AndroidFindBy(xpath = DELETE_BANK_ICON)
    private WebElement deleteBankIcon;

    public static final String AMOUNT = "//android.widget.TextView[@text='Amount']";

    @AndroidFindBy(xpath = AMOUNT)
    private WebElement amount;

    public static final String WITHDRAW = "//android.widget.TextView[@text='Withdraw']";

    @AndroidFindBy(xpath = WITHDRAW)
    private WebElement withdraw;

    public static final String WITHDRAWAL_LIMIT = "//android.widget.TextView[@text='Withdrawal Limit']";

    @AndroidFindBy(xpath = WITHDRAWAL_LIMIT)
    private WebElement withdrawalLimit;

    public WithdrawalLimitRtgsScreen(TestContext context) {
        super(context);
    }
}
