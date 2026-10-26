package com.airpay.vyaapaar.screens.auth;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Welcome. Module auth. */
public class AuthWelcomeScreen extends BaseScreen {

    public static final String PAGE_ID = "auth-welcome";

    public static final String AIRPAY_LOGO = "//android.view.View[contains(@content-desc,'Airpay Logo')]";

    @AndroidFindBy(xpath = AIRPAY_LOGO)
    private WebElement airpayLogo;

    public static final String TRANSFORM_YOUR_BUSINESS_WITH_AIRPAY_VYAAPAAR_ACCEPT_DIGITAL = "//android.widget.TextView[@text='Transform your business with airpay vyaapaar. Accept digital payments, manage transactions, and grow your business seamlessly.']";

    @AndroidFindBy(xpath = TRANSFORM_YOUR_BUSINESS_WITH_AIRPAY_VYAAPAAR_ACCEPT_DIGITAL)
    private WebElement transformYourBusinessWithAirpayVyaapaarAcceptDigital;

    public static final String MONEY_ICON = "//android.view.View[contains(@content-desc,'Money Icon')]";

    @AndroidFindBy(xpath = MONEY_ICON)
    private WebElement moneyIcon;

    public static final String MONEY_TRANSFER = "//android.widget.TextView[@text='Money Transfer']";

    @AndroidFindBy(xpath = MONEY_TRANSFER)
    private WebElement moneyTransfer;

    public static final String QUICK_SECURE_TRANSACTIONS = "//android.widget.TextView[@text='Quick & secure transactions']";

    @AndroidFindBy(xpath = QUICK_SECURE_TRANSACTIONS)
    private WebElement quickSecureTransactions;

    public static final String HISTORY_ICON = "//android.view.View[contains(@content-desc,'History Icon')]";

    @AndroidFindBy(xpath = HISTORY_ICON)
    private WebElement historyIcon;

    public static final String BUSINESS_ANALYTICS = "//android.widget.TextView[@text='Business Analytics']";

    @AndroidFindBy(xpath = BUSINESS_ANALYTICS)
    private WebElement businessAnalytics;

    public static final String TRACK_YOUR_GROWTH_INSIGHTS = "//android.widget.TextView[@text='Track your growth & insights']";

    @AndroidFindBy(xpath = TRACK_YOUR_GROWTH_INSIGHTS)
    private WebElement trackYourGrowthInsights;

    public static final String SCREEN_LOCK_ICON = "//android.view.View[contains(@content-desc,'Screen Lock Icon')]";

    @AndroidFindBy(xpath = SCREEN_LOCK_ICON)
    private WebElement screenLockIcon;

    public static final String SECURE_TRANSACTIONS = "//android.widget.TextView[@text='Secure Transactions']";

    @AndroidFindBy(xpath = SECURE_TRANSACTIONS)
    private WebElement secureTransactions;

    public static final String BANK_GRADE_SECURITY = "//android.widget.TextView[@text='Bank-grade security']";

    @AndroidFindBy(xpath = BANK_GRADE_SECURITY)
    private WebElement bankGradeSecurity;

    public static final String GET_STARTED = "//android.widget.TextView[@text='Get Started']";

    @AndroidFindBy(xpath = GET_STARTED)
    private WebElement getStarted;

    public AuthWelcomeScreen(TestContext context) {
        super(context);
    }
}
