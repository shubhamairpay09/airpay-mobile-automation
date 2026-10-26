package com.airpay.vyaapaar.screens.cms;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / CMS / Cash Drop / AC / Suryoday / Suryoday / MM. Module cms. */
public class HomeCreditBalanceCmsCashDrop5Screen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-cms-cash-drop-ac-suryoday-suryoday-mm";

    public static final String MERCHANT_NAME = "//android.widget.TextView[@text='Merchant Name']";

    @AndroidFindBy(xpath = MERCHANT_NAME)
    private WebElement merchantName;

    public static final String ELEMENT = "//android.widget.TextView[@text=':']";

    @AndroidFindBy(xpath = ELEMENT)
    private WebElement element;

    public static final String MIDLAND_MICROFIN_LIMITED = "//android.widget.TextView[@text='Midland Microfin Limited']";

    @AndroidFindBy(xpath = MIDLAND_MICROFIN_LIMITED)
    private WebElement midlandMicrofinLimited;

    public static final String MERCHANT_MOBILE = "//android.widget.TextView[@text='Merchant Mobile']";

    @AndroidFindBy(xpath = MERCHANT_MOBILE)
    private WebElement merchantMobile;

    public static final String MERCHANT_EMAIL = "//android.widget.TextView[@text='Merchant Email']";

    @AndroidFindBy(xpath = MERCHANT_EMAIL)
    private WebElement merchantEmail;

    public static final String TESTBHAVNA_BHAGAT_MIDLANDMICROFIN_COM = "//android.widget.TextView[@text='testbhavna.bhagat@midlandmicrofin.com']";

    @AndroidFindBy(xpath = TESTBHAVNA_BHAGAT_MIDLANDMICROFIN_COM)
    private WebElement testbhavnaBhagatMidlandmicrofinCom;

    public static final String SEARCH_VALUE = "//android.widget.TextView[@text='Search Value']";

    @AndroidFindBy(xpath = SEARCH_VALUE)
    private WebElement searchValue;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String DETAILS = "//android.widget.TextView[@text='Details']";

    @AndroidFindBy(xpath = DETAILS)
    private WebElement details;

    public HomeCreditBalanceCmsCashDrop5Screen(TestContext context) {
        super(context);
    }
}
