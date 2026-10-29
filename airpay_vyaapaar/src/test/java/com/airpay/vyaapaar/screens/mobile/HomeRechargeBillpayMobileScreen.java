package com.airpay.vyaapaar.screens.mobile;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Recharge & Billpay/Mobile. Module mobile. */
public class HomeRechargeBillpayMobileScreen extends BaseScreen {

    public static final String PAGE_ID = "home-recharge-billpay-mobile";

    public static final String ALLPAY_IMAGE = "//android.view.View[contains(@content-desc,'Allpay image')]";

    @AndroidFindBy(xpath = ALLPAY_IMAGE)
    private WebElement allpayImage;

    public static final String MOBILE_NUMBER = "//android.view.View[contains(@content-desc,'Mobile Number')]";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String MOBILE_RECHARGE = "//android.widget.TextView[@text='Mobile Recharge']";

    @AndroidFindBy(xpath = MOBILE_RECHARGE)
    private WebElement mobileRecharge;

    public HomeRechargeBillpayMobileScreen(TestContext context) {
        super(context);
    }
}
