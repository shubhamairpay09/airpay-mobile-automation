package com.airpay.vyaapaar.screens.ticketstransaction;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Tickets / Transaction — Transaction. Module tickets-transaction. */
public class ProfileSettingsTicketsTransactionScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-settings-tickets-transaction";

    public static final String TRANSACTION = "//android.widget.EditText[@text='Transaction']";

    @AndroidFindBy(xpath = TRANSACTION)
    private WebElement transaction;

    public static final String TRANSACTION_TYPE = "//android.widget.TextView[@text='Transaction Type']";

    @AndroidFindBy(xpath = TRANSACTION_TYPE)
    private WebElement transactionType;

    public static final String TRANSACTION_ID = "//android.widget.TextView[@text='Transaction ID']";

    @AndroidFindBy(xpath = TRANSACTION_ID)
    private WebElement transactionId;

    public static final String SUBJECT = "//android.widget.TextView[@text='Subject']";

    @AndroidFindBy(xpath = SUBJECT)
    private WebElement subject;

    public static final String DESCRIPTION = "//android.widget.TextView[@text='Description']";

    @AndroidFindBy(xpath = DESCRIPTION)
    private WebElement description;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String SUPPORT_TICKET = "//android.widget.TextView[@text='Support Ticket']";

    @AndroidFindBy(xpath = SUPPORT_TICKET)
    private WebElement supportTicket;

    public ProfileSettingsTicketsTransactionScreen(TestContext context) {
        super(context);
    }
}
