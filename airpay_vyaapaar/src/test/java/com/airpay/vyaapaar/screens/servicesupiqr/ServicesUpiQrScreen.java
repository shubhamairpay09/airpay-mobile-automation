package com.airpay.vyaapaar.screens.servicesupiqr;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Services / UPI QR — UPI QR. Module services-upi-qr. */
public class ServicesUpiQrScreen extends BaseScreen {

    public static final String PAGE_ID = "services-upi-qr";

    public static final String UPI_QR = "//android.widget.TextView[@text='UPI QR']";

    @AndroidFindBy(xpath = UPI_QR)
    private WebElement upiQr;

    public ServicesUpiQrScreen(TestContext context) {
        super(context);
    }
}
