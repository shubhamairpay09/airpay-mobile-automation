package com.airpay.vyaapaar.screens.finance;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Finance / Unsecured Credit Cards / HDFC — Logo. Module finance. */
public class HomeCreditBalanceFinanceUnsecuredCredit2Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-finance-unsecured-credit-cards-hdfc";

    public static final String LOGO = "//android.view.View[contains(@content-desc,'Logo')]";

    @AndroidFindBy(xpath = LOGO)
    private WebElement logo;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String CUSTOMER_NAME = "//android.widget.TextView[@text='Customer Name']";

    @AndroidFindBy(xpath = CUSTOMER_NAME)
    private WebElement customerName;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String HDFC = "//android.widget.TextView[@text='HDFC']";

    @AndroidFindBy(xpath = HDFC)
    private WebElement hdfc;

    public HomeCreditBalanceFinanceUnsecuredCredit2Screen(TestContext context) {
        super(context);
    }
}
