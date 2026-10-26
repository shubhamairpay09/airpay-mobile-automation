package com.airpay.vyaapaar.screens.aadhaarpay;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Aadhaar Pay — Customer Mobile. Module aadhaar-pay. */
public class FeaturedAadhaarPayScreen extends BaseScreen {

    public static final String PAGE_ID = "featured-aadhaar-pay";

    public static final String CUSTOMER_MOBILE = "//android.widget.TextView[@text='Customer Mobile']";

    @AndroidFindBy(xpath = CUSTOMER_MOBILE)
    private WebElement customerMobile;

    public static final String CUSTOMER_EMAIL = "//android.widget.TextView[@text='Customer Email']";

    @AndroidFindBy(xpath = CUSTOMER_EMAIL)
    private WebElement customerEmail;

    public static final String AADHAAR_NUMBER = "//android.widget.TextView[@text='Aadhaar Number']";

    @AndroidFindBy(xpath = AADHAAR_NUMBER)
    private WebElement aadhaarNumber;

    public static final String BANK_NAME = "//android.widget.TextView[@text='Bank Name']";

    @AndroidFindBy(xpath = BANK_NAME)
    private WebElement bankName;

    public static final String ICON = "//android.view.View[contains(@content-desc,'Icon')]";

    @AndroidFindBy(xpath = ICON)
    private WebElement icon;

    public static final String I_AGREE_TO_THE_DISCLAIMER = "//android.widget.TextView[@text='I agree to the   disclaimer']";

    @AndroidFindBy(xpath = I_AGREE_TO_THE_DISCLAIMER)
    private WebElement iAgreeToTheDisclaimer;

    public static final String PROCEED = "//android.widget.TextView[@text='Proceed']";

    @AndroidFindBy(xpath = PROCEED)
    private WebElement proceed;

    public FeaturedAadhaarPayScreen(TestContext context) {
        super(context);
    }
}
