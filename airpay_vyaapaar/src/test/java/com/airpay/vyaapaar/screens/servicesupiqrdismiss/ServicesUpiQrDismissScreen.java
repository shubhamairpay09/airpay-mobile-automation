package com.airpay.vyaapaar.screens.servicesupiqrdismiss;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Services / UPI QR / Dismiss — Link MIDs. Module services-upi-qr-dismiss. */
public class ServicesUpiQrDismissScreen extends BaseScreen {

    public static final String PAGE_ID = "services-upi-qr-dismiss";

    public static final String LINK_MIDS = "//android.widget.TextView[@text='Link MIDs']";

    @AndroidFindBy(xpath = LINK_MIDS)
    private WebElement linkMids;

    public ServicesUpiQrDismissScreen(TestContext context) {
        super(context);
    }
}
