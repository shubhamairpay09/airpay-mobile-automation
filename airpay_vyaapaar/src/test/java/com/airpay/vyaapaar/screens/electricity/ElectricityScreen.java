package com.airpay.vyaapaar.screens.electricity;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Electricity — Electricity. Module electricity. */
public class ElectricityScreen extends BaseScreen {

    public static final String PAGE_ID = "electricity";

    public static final String ELECTRICITY = "//android.widget.TextView[@text='Electricity']";

    @AndroidFindBy(xpath = ELECTRICITY)
    private WebElement electricity;

    public ElectricityScreen(TestContext context) {
        super(context);
    }
}
