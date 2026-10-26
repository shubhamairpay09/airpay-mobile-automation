package com.airpay.vyaapaar.screens.dth;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** DTH — Allpay image. Module dth. */
public class DthScreen extends BaseScreen {

    public static final String PAGE_ID = "dth";

    public static final String ALLPAY_IMAGE = "//android.view.View[contains(@content-desc,'Allpay image')]";

    @AndroidFindBy(xpath = ALLPAY_IMAGE)
    private WebElement allpayImage;

    public static final String CUSTOMER_ID = "//android.widget.TextView[@text='Customer ID']";

    @AndroidFindBy(xpath = CUSTOMER_ID)
    private WebElement customerId;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String DTH_RECHARGE = "//android.widget.TextView[@text='DTH Recharge']";

    @AndroidFindBy(xpath = DTH_RECHARGE)
    private WebElement dthRecharge;

    public DthScreen(TestContext context) {
        super(context);
    }
}
