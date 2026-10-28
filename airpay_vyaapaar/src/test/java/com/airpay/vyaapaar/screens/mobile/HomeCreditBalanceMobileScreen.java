package com.airpay.vyaapaar.screens.mobile;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Mobile — Allpay image. Module mobile. */
public class HomeCreditBalanceMobileScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-mobile";

    public static final String ALLPAY_IMAGE = "//android.view.View[contains(@content-desc,'Allpay image')]";

    @AndroidFindBy(xpath = ALLPAY_IMAGE)
    private WebElement allpayImage;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String MOBILE_RECHARGE = "//android.widget.TextView[@text='Mobile Recharge']";

    @AndroidFindBy(xpath = MOBILE_RECHARGE)
    private WebElement mobileRecharge;

    public HomeCreditBalanceMobileScreen(TestContext context) {
        super(context);
    }
}
