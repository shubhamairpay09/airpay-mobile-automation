package com.airpay.vyaapaar.screens.tickets;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Tickets — Subject. Module tickets. */
public class ProfileSettingsTicketsScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-settings-tickets";

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

    public ProfileSettingsTicketsScreen(TestContext context) {
        super(context);
    }
}
