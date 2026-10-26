package com.airpay.vyaapaar.screens.register;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Register — Alert. Module register. */
public class RegisterAlertScreen extends BaseScreen {

    public static final String PAGE_ID = "register-alert";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String LEAD_ALREADY_REGISTERED_OR_REJECTED = "//android.widget.TextView[@text='Lead already registered or rejected']";

    @AndroidFindBy(xpath = LEAD_ALREADY_REGISTERED_OR_REJECTED)
    private WebElement leadAlreadyRegisteredOrRejected;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public RegisterAlertScreen(TestContext context) {
        super(context);
    }
}
