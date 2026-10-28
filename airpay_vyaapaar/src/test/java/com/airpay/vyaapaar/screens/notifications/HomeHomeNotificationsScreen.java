package com.airpay.vyaapaar.screens.notifications;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Home/Notifications. Module notifications. */
public class HomeHomeNotificationsScreen extends BaseScreen {

    public static final String PAGE_ID = "home-home-notifications";

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public static final String NOTIFICATIONS = "//android.widget.TextView[@text='Notifications']";

    @AndroidFindBy(xpath = NOTIFICATIONS)
    private WebElement notifications;

    public HomeHomeNotificationsScreen(TestContext context) {
        super(context);
    }
}
