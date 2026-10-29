package com.airpay.vyaapaar.screens.services;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Services. Module services. */
public class ServicesScreen extends BaseScreen {

    public static final String PAGE_ID = "services";

    public static final String COLLECT_MONEY_ICON = "//android.view.View[contains(@content-desc,'Collect Money icon')]";

    @AndroidFindBy(xpath = COLLECT_MONEY_ICON)
    private WebElement collectMoneyIcon;

    public static final String COLLECT_MONEY = "//android.widget.TextView[contains(@content-desc,'Collect Money')]";

    @AndroidFindBy(xpath = COLLECT_MONEY)
    private WebElement collectMoney;

    public static final String UPI_QR_ICON = "//android.view.View[contains(@content-desc,'UPI QR icon')]";

    @AndroidFindBy(xpath = UPI_QR_ICON)
    private WebElement upiQrIcon;

    public static final String UPI_QR = "//android.widget.TextView[contains(@content-desc,'UPI QR')]";

    @AndroidFindBy(xpath = UPI_QR)
    private WebElement upiQr;

    public static final String POS_ACTIVATION_ICON = "//android.view.View[contains(@content-desc,'POS Activation icon')]";

    @AndroidFindBy(xpath = POS_ACTIVATION_ICON)
    private WebElement posActivationIcon;

    public static final String POS_ACTIVATION = "//android.widget.TextView[contains(@content-desc,'POS Activation')]";

    @AndroidFindBy(xpath = POS_ACTIVATION)
    private WebElement posActivation;

    public static final String CERTIFICATE_ICON = "//android.view.View[contains(@content-desc,'Certificate icon')]";

    @AndroidFindBy(xpath = CERTIFICATE_ICON)
    private WebElement certificateIcon;

    public static final String CERTIFICATE = "//android.widget.TextView[contains(@content-desc,'Certificate')]";

    @AndroidFindBy(xpath = CERTIFICATE)
    private WebElement certificate;

    public static final String CREDIT_LEDGER_HISTORY_ICON = "//android.view.View[contains(@content-desc,'Credit Ledger History icon')]";

    @AndroidFindBy(xpath = CREDIT_LEDGER_HISTORY_ICON)
    private WebElement creditLedgerHistoryIcon;

    public static final String CREDIT_LEDGER_HISTORY = "//android.widget.TextView[contains(@content-desc,'Credit Ledger History')]";

    @AndroidFindBy(xpath = CREDIT_LEDGER_HISTORY)
    private WebElement creditLedgerHistory;

    public static final String LOAN_LEAD_GENERATION_ICON = "//android.view.View[contains(@content-desc,'Loan Lead Generation icon')]";

    @AndroidFindBy(xpath = LOAN_LEAD_GENERATION_ICON)
    private WebElement loanLeadGenerationIcon;

    public static final String LOAN_LEAD_GENERATION = "//android.widget.TextView[contains(@content-desc,'Loan Lead Generation')]";

    @AndroidFindBy(xpath = LOAN_LEAD_GENERATION)
    private WebElement loanLeadGeneration;

    public static final String SUBSCRIPTION_ICON = "//android.view.View[contains(@content-desc,'Subscription icon')]";

    @AndroidFindBy(xpath = SUBSCRIPTION_ICON)
    private WebElement subscriptionIcon;

    public static final String SUBSCRIPTION = "//android.widget.TextView[contains(@content-desc,'Subscription')]";

    @AndroidFindBy(xpath = SUBSCRIPTION)
    private WebElement subscription;

    public static final String FINANCE_ICON = "//android.view.View[contains(@content-desc,'Finance icon')]";

    @AndroidFindBy(xpath = FINANCE_ICON)
    private WebElement financeIcon;

    public static final String FINANCE = "//android.widget.TextView[contains(@content-desc,'Finance')]";

    @AndroidFindBy(xpath = FINANCE)
    private WebElement finance;

    public static final String LINK_MIDS_ICON = "//android.view.View[contains(@content-desc,'Link MIDs icon')]";

    @AndroidFindBy(xpath = LINK_MIDS_ICON)
    private WebElement linkMidsIcon;

    public static final String LINK_MIDS = "//android.widget.TextView[contains(@content-desc,'Link MIDs')]";

    @AndroidFindBy(xpath = LINK_MIDS)
    private WebElement linkMids;

    public static final String DEVOTIONAL_EXPERIENCES_ICON = "//android.view.View[contains(@content-desc,'Devotional Experiences icon')]";

    @AndroidFindBy(xpath = DEVOTIONAL_EXPERIENCES_ICON)
    private WebElement devotionalExperiencesIcon;

    public static final String DEVOTIONAL_EXPERIENCES = "//android.widget.TextView[contains(@content-desc,'Devotional Experiences')]";

    @AndroidFindBy(xpath = DEVOTIONAL_EXPERIENCES)
    private WebElement devotionalExperiences;

    public static final String SEARCH = "//android.view.View[contains(@content-desc,'Search')]";

    @AndroidFindBy(xpath = SEARCH)
    private WebElement search;

    public static final String HOME = "//android.widget.TextView[contains(@content-desc,'Home')]";

    @AndroidFindBy(xpath = HOME)
    private WebElement home;

    public static final String SERVICES = "//android.widget.TextView[contains(@content-desc,'Services')]";

    @AndroidFindBy(xpath = SERVICES)
    private WebElement services;

    public static final String HISTORY = "//android.widget.TextView[contains(@content-desc,'History')]";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String PROFILE = "//android.widget.TextView[contains(@content-desc,'Profile')]";

    @AndroidFindBy(xpath = PROFILE)
    private WebElement profile;

    public ServicesScreen(TestContext context) {
        super(context);
    }
}
