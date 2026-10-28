package com.airpay.vyaapaar.screens.profile;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Profile/Settings/Support/Tickets. Module profile. */
public class ProfileSettingsSupportTicketsScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-settings-support-tickets";

    public static final String TRANSACTION = "//android.widget.TextView[@text='Transaction']";

    @AndroidFindBy(xpath = TRANSACTION)
    private WebElement transaction;

    public static final String TRANSACTION_FAILURES = "//android.widget.TextView[@text='Transaction failures']";

    @AndroidFindBy(xpath = TRANSACTION_FAILURES)
    private WebElement transactionFailures;

    public static final String ONDC = "//android.widget.TextView[@text='ONDC']";

    @AndroidFindBy(xpath = ONDC)
    private WebElement ondc;

    public static final String TOP_UP = "//android.widget.TextView[@text='Top Up']";

    @AndroidFindBy(xpath = TOP_UP)
    private WebElement topUp;

    public static final String SETTLEMENT = "//android.widget.TextView[@text='Settlement']";

    @AndroidFindBy(xpath = SETTLEMENT)
    private WebElement settlement;

    public static final String ACCOUNT_SERVICING = "//android.widget.TextView[@text='Account Servicing']";

    @AndroidFindBy(xpath = ACCOUNT_SERVICING)
    private WebElement accountServicing;

    public static final String DISPUTES = "//android.widget.TextView[@text='Disputes']";

    @AndroidFindBy(xpath = DISPUTES)
    private WebElement disputes;

    public static final String BANK_DETAIL_UPDATES = "//android.widget.TextView[@text='Bank detail updates']";

    @AndroidFindBy(xpath = BANK_DETAIL_UPDATES)
    private WebElement bankDetailUpdates;

    public static final String OPERATIONAL_CONCERNS = "//android.widget.TextView[@text='Operational concerns']";

    @AndroidFindBy(xpath = OPERATIONAL_CONCERNS)
    private WebElement operationalConcerns;

    public static final String OTHERS = "//android.widget.TextView[@text='Others']";

    @AndroidFindBy(xpath = OTHERS)
    private WebElement others;

    public ProfileSettingsSupportTicketsScreen(TestContext context) {
        super(context);
    }
}
