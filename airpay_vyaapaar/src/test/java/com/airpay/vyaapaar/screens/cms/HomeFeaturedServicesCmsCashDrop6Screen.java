package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Featured Services/CMS/Cash Drop/BF/Bajaj Finance. Module cms. */
public class HomeFeaturedServicesCmsCashDrop6Screen extends BaseScreen {

    public static final String PAGE_ID = "home-featured-services-cms-cash-drop-bf-bajaj-finance";

    public static final String BAJAJ_ACCOUNT_MOBILE_NUMBER = "//android.widget.TextView[@text='Bajaj Account Mobile Number']";

    @AndroidFindBy(xpath = BAJAJ_ACCOUNT_MOBILE_NUMBER)
    private WebElement bajajAccountMobileNumber;

    public static final String BAJAJ_LOAN_ACCOUNT_NUMBER = "//android.widget.TextView[@text='Bajaj Loan Account Number']";

    @AndroidFindBy(xpath = BAJAJ_LOAN_ACCOUNT_NUMBER)
    private WebElement bajajLoanAccountNumber;

    public HomeFeaturedServicesCmsCashDrop6Screen(TestContext context) {
        super(context);
    }
}
