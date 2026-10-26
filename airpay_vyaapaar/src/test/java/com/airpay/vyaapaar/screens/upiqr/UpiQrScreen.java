package com.airpay.vyaapaar.screens.upiqr;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** UPI QR — Alert. Module upi-qr. */
public class UpiQrScreen extends BaseScreen {

    public static final String PAGE_ID = "upi-qr";

    public static final String UPI_QR = "//android.widget.TextView[@text='UPI QR']";

    @AndroidFindBy(xpath = UPI_QR)
    private WebElement upiQr;

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String AUTHORIZATION_OPERATION_FAILED_DOMAIN_NOT_REGISTERED_FOR_THE = "//android.widget.TextView[@text='Authorization Operation Failed - Domain not registered for the Merchant.']";

    @AndroidFindBy(xpath = AUTHORIZATION_OPERATION_FAILED_DOMAIN_NOT_REGISTERED_FOR_THE)
    private WebElement authorizationOperationFailedDomainNotRegisteredForThe;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public UpiQrScreen(TestContext context) {
        super(context);
    }
}
