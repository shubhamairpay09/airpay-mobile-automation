package com.airpay.vyaapaar.screens.posactivation;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/POS Activation. Module pos-activation. */
public class HomeNonChannelsPosActivationScreen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-pos-activation";

    public static final String ACTIVATE_NEW_POS_DEVICE = "//android.widget.TextView[@text='Activate new POS device']";

    @AndroidFindBy(xpath = ACTIVATE_NEW_POS_DEVICE)
    private WebElement activateNewPosDevice;

    public static final String DEVICE_SERIAL_NUMBER = "//android.widget.TextView[@text='Device Serial Number']";

    @AndroidFindBy(xpath = DEVICE_SERIAL_NUMBER)
    private WebElement deviceSerialNumber;

    public static final String VERIFY_DEVICE = "//android.widget.TextView[@text='Verify Device']";

    @AndroidFindBy(xpath = VERIFY_DEVICE)
    private WebElement verifyDevice;

    public static final String NO_RECORDS_FOUND = "//android.widget.TextView[@text='No records found']";

    @AndroidFindBy(xpath = NO_RECORDS_FOUND)
    private WebElement noRecordsFound;

    public static final String POS_ACTIVATION = "//android.widget.TextView[@text='Pos Activation']";

    @AndroidFindBy(xpath = POS_ACTIVATION)
    private WebElement posActivation;

    public HomeNonChannelsPosActivationScreen(TestContext context) {
        super(context);
    }
}
