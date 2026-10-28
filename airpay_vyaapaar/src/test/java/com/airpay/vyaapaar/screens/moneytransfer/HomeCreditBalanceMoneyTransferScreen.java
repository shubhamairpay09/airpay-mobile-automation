package com.airpay.vyaapaar.screens.moneytransfer;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Money Transfer — Money transfer Image. Module money-transfer. */
public class HomeCreditBalanceMoneyTransferScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-money-transfer";

    public static final String MONEY_TRANSFER_IMAGE = "//android.view.View[contains(@content-desc,'Money transfer Image')]";

    @AndroidFindBy(xpath = MONEY_TRANSFER_IMAGE)
    private WebElement moneyTransferImage;

    public static final String MOBILE_NUMBER = "//android.widget.TextView[@text='Mobile Number']";

    @AndroidFindBy(xpath = MOBILE_NUMBER)
    private WebElement mobileNumber;

    public static final String SUBMIT = "//android.widget.TextView[@text='Submit']";

    @AndroidFindBy(xpath = SUBMIT)
    private WebElement submit;

    public static final String DOMESTIC_MONEY_TRANSFER = "//android.widget.TextView[@text='Domestic Money Transfer']";

    @AndroidFindBy(xpath = DOMESTIC_MONEY_TRANSFER)
    private WebElement domesticMoneyTransfer;

    public HomeCreditBalanceMoneyTransferScreen(TestContext context) {
        super(context);
    }
}
