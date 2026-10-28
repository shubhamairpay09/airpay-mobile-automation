package com.airpay.vyaapaar.screens.aadhaarpay;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/Aadhaar Pay. Module aadhaar-pay. */
public class HomeFeaturedServicesAadhaarPayScreen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-aadhaar-pay";

    public static final String AMOUNT = "//android.widget.TextView[@text='Amount']";

    @AndroidFindBy(xpath = AMOUNT)
    private WebElement amount;

    public static final String MINIMUM_AMOUNT100 = "//android.widget.TextView[@text='Minimum amount ₹ 100/-']";

    @AndroidFindBy(xpath = MINIMUM_AMOUNT100)
    private WebElement minimumAmount100;

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

    public static final String AADHAAR_PAY = "//android.widget.TextView[@text='Aadhaar Pay']";

    @AndroidFindBy(xpath = AADHAAR_PAY)
    private WebElement aadhaarPay;

    public HomeFeaturedServicesAadhaarPayScreen(TestContext context) {
        super(context);
    }
}
