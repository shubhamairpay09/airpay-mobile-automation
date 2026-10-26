package com.airpay.vyaapaar.screens.posactivation;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** POS Activation — Pos Activation. Module pos-activation. */
public class PosActivationScreen extends BaseScreen {

    public static final String PAGE_ID = "pos-activation";

    public static final String POS_ACTIVATION = "//android.widget.TextView[@text='Pos Activation']";

    @AndroidFindBy(xpath = POS_ACTIVATION)
    private WebElement posActivation;

    public PosActivationScreen(TestContext context) {
        super(context);
    }
}
