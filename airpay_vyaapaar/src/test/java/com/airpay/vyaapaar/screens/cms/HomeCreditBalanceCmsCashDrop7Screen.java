package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / AC / Suryoday / Suryoday / TW. Module cms. */
public class HomeCreditBalanceCmsCashDrop7Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-ac-suryoday-suryoday-tw";

    public static final String MERCHANT_NAME = "//android.widget.TextView[@text='Merchant Name']";

    @AndroidFindBy(xpath = MERCHANT_NAME)
    private WebElement merchantName;

    public static final String ELEMENT = "//android.widget.TextView[@text=':']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String THREE_WHEELS_CAPITAL = "//android.widget.TextView[@text='THREE WHEELS CAPITAL']";

    @AndroidFindBy(xpath = THREE_WHEELS_CAPITAL)
    private WebElement threeWheelsCapital;

    public static final String MERCHANT_MOBILE = "//android.widget.TextView[@text='Merchant Mobile']";

    @AndroidFindBy(xpath = MERCHANT_MOBILE)
    private WebElement merchantMobile;

    public static final String MERCHANT_EMAIL = "//android.widget.TextView[@text='Merchant Email']";

    @AndroidFindBy(xpath = MERCHANT_EMAIL)
    private WebElement merchantEmail;

    public static final String TEST_TEST_CO_IN = "//android.widget.TextView[@text='test@test.co.in']";

    @AndroidFindBy(xpath = TEST_TEST_CO_IN)
    private WebElement testTestCoIn;

    public static final String SEARCH_VALUE = "//android.widget.TextView[@text='Search Value']";

    @AndroidFindBy(xpath = SEARCH_VALUE)
    private WebElement searchValue;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String DETAILS = "//android.widget.TextView[@text='Details']";

    @AndroidFindBy(xpath = DETAILS)
    private WebElement details;

    public HomeCreditBalanceCmsCashDrop7Screen(TestContext context) {
        super(context);
    }
}
