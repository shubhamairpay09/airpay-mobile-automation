package com.airpay.vyaapaar.screens.addcredits;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Add Credits — Select Payment Type. Module add-credits. */
public class HomeCreditBalanceAddCreditsScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-add-credits";

    public static final String SELECT_PAYMENT_TYPE = "//android.widget.TextView[@text='Select Payment Type']";

    @AndroidFindBy(xpath = SELECT_PAYMENT_TYPE)
    private WebElement selectPaymentType;

    public static final String REQUEST_FROM_NETWORK_ICON = "//android.view.View[contains(@content-desc,'Request From Network icon')]";

    @AndroidFindBy(xpath = REQUEST_FROM_NETWORK_ICON)
    private WebElement requestFromNetworkIcon;

    public static final String REQUEST_FROM_NETWORK = "//android.widget.TextView[@text='Request From Network']";

    @AndroidFindBy(xpath = REQUEST_FROM_NETWORK)
    private WebElement requestFromNetwork;

    public static final String AMOUNT = "//android.widget.TextView[@text='Amount']";

    @AndroidFindBy(xpath = AMOUNT)
    private WebElement amount;

    public static final String PROCEED = "//android.widget.TextView[@text='Proceed']";

    @AndroidFindBy(xpath = PROCEED)
    private WebElement proceed;

    public static final String ADD_CREDIT = "//android.widget.TextView[@text='Add Credit']";

    @AndroidFindBy(xpath = ADD_CREDIT)
    private WebElement addCredit;

    public HomeCreditBalanceAddCreditsScreen(TestContext context) {
        super(context);
    }
}
