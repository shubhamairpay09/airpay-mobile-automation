package com.airpay.vyaapaar.screens.qractivation;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** QR Activation — QR Code. Module qr-activation. */
public class ProfileQrActivationScreen extends BaseScreen {

    public static final String PAGE_ID = "profile-qr-activation";

    public static final String QR_CODE = "//android.view.View[contains(@content-desc,'QR Code')]";

    @AndroidFindBy(xpath = QR_CODE)
    private WebElement qrCode;

    public static final String UPI_ID = "//android.widget.TextView[@text='UPI ID']";

    @AndroidFindBy(xpath = UPI_ID)
    private WebElement upiId;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String QR_ACTIVATION = "//android.widget.TextView[@text='QR Activation']";

    @AndroidFindBy(xpath = QR_ACTIVATION)
    private WebElement qrActivation;

    public ProfileQrActivationScreen(TestContext context) {
        super(context);
    }
}
