package com.airpay.vyaapaar.screens.servicesposactivation;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Services / POS Activation — Pos Activation. Module services-pos-activation. */
public class ServicesPosActivationScreen extends BaseScreen {

    public static final String PAGE_ID = "services-pos-activation";

    public static final String POS_ACTIVATION = "//android.widget.TextView[@text='Pos Activation']";

    @AndroidFindBy(xpath = POS_ACTIVATION)
    private WebElement posActivation;

    public ServicesPosActivationScreen(TestContext context) {
        super(context);
    }
}
