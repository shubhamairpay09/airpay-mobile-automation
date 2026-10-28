package com.airpay.vyaapaar.screens.services;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Services / UPI QR — Alert. Module services. */
public class HomeCreditBalanceServicesUpiQrScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-services-upi-qr";

    public static final String ALERT = "//android.widget.TextView[@text='Alert']";

    @AndroidFindBy(xpath = ALERT)
    private WebElement alert;

    public static final String AUTHORIZATION_OPERATION_FAILED_DOMAIN_NOT_REGISTERED_FOR_THE = "//android.widget.TextView[@text='Authorization Operation Failed - Domain not registered for the Merchant.']";

    @AndroidFindBy(xpath = AUTHORIZATION_OPERATION_FAILED_DOMAIN_NOT_REGISTERED_FOR_THE)
    private WebElement authorizationOperationFailedDomainNotRegisteredForThe;

    public static final String DISMISS = "//android.widget.TextView[@text='Dismiss']";

    @AndroidFindBy(xpath = DISMISS)
    private WebElement dismiss;

    public HomeCreditBalanceServicesUpiQrScreen(TestContext context) {
        super(context);
    }
}
