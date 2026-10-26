package com.airpay.vyaapaar.screens.home;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Transaction Volume (Annual). Module home. */
public class OpenTransactionVolumeAnnualScreen extends BaseScreen {

    public static final String PAGE_ID = "open-transaction-volume-annual";

    public static final String EL01L = "//android.widget.TextView[@text='0 - 1L']";

    @AndroidFindBy(xpath = EL01L)
    private WebElement el01l;

    public static final String EL1L5L = "//android.widget.TextView[@text='1L - 5L']";

    @AndroidFindBy(xpath = EL1L5L)
    private WebElement el1l5l;

    public static final String EL5L25L = "//android.widget.TextView[@text='5L- 25L']";

    @AndroidFindBy(xpath = EL5L25L)
    private WebElement el5l25l;

    public static final String EL25L50L = "//android.widget.TextView[@text='25L - 50L']";

    @AndroidFindBy(xpath = EL25L50L)
    private WebElement el25l50l;

    public static final String EL50L1CR = "//android.widget.TextView[@text='50L - 1Cr']";

    @AndroidFindBy(xpath = EL50L1CR)
    private WebElement el50l1cr;

    public static final String ABOVE1CR = "//android.widget.TextView[@text='Above 1Cr']";

    @AndroidFindBy(xpath = ABOVE1CR)
    private WebElement above1cr;

    public OpenTransactionVolumeAnnualScreen(TestContext context) {
        super(context);
    }
}
