package com.airpay.vyaapaar.screens.servicescollectmoney;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Services / Collect Money — Select Payment Type. Module services-collect-money. */
public class ServicesCollectMoneyScreen extends BaseScreen {

    public static final String PAGE_ID = "services-collect-money";

    public static final String SELECT_PAYMENT_TYPE = "//android.widget.TextView[@text='Select Payment Type']";

    @AndroidFindBy(xpath = SELECT_PAYMENT_TYPE)
    private WebElement selectPaymentType;

    public static final String MENU_ICON = "//android.view.View[contains(@content-desc,'Menu Icon')]";

    @AndroidFindBy(xpath = MENU_ICON)
    private WebElement menuIcon;

    public static final String UPI_QR = "//android.widget.TextView[@text='UPI QR']";

    @AndroidFindBy(xpath = UPI_QR)
    private WebElement upiQr;

    public static final String UPI = "//android.widget.TextView[@text='UPI']";

    @AndroidFindBy(xpath = UPI)
    private WebElement upi;

    public static final String CREDIT_DEBIT_OTHERS = "//android.widget.TextView[@text='Credit/Debit/Others']";

    @AndroidFindBy(xpath = CREDIT_DEBIT_OTHERS)
    private WebElement creditDebitOthers;

    public static final String AMOUNT = "//android.widget.TextView[@text='Amount']";

    @AndroidFindBy(xpath = AMOUNT)
    private WebElement amount;

    public static final String MIN_AMOUNT100 = "//android.widget.TextView[@text='Min amount ₹100/-']";

    @AndroidFindBy(xpath = MIN_AMOUNT100)
    private WebElement minAmount100;

    public static final String CUSTOMER_MOBILE_NUMBER = "//android.widget.TextView[@text='Customer Mobile Number']";

    @AndroidFindBy(xpath = CUSTOMER_MOBILE_NUMBER)
    private WebElement customerMobileNumber;

    public static final String CUSTOMER_EMAIL = "//android.widget.TextView[@text='Customer Email']";

    @AndroidFindBy(xpath = CUSTOMER_EMAIL)
    private WebElement customerEmail;

    public static final String NEXT = "//android.widget.TextView[@text='Next']";

    @AndroidFindBy(xpath = NEXT)
    private WebElement next;

    public static final String COLLECT_MONEY = "//android.widget.TextView[@text='Collect Money']";

    @AndroidFindBy(xpath = COLLECT_MONEY)
    private WebElement collectMoney;

    public ServicesCollectMoneyScreen(TestContext context) {
        super(context);
    }
}
