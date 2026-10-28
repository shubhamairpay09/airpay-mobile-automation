package com.airpay.vyaapaar.screens.finance;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Finance / Unsecured Credit Cards — HDFC Logo. Module finance. */
public class HomeCreditBalanceFinanceUnsecuredCreditScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-finance-unsecured-credit-cards";

    public static final String HDFC_LOGO = "//android.view.View[contains(@content-desc,'HDFC Logo')]";

    @AndroidFindBy(xpath = HDFC_LOGO)
    private WebElement hdfcLogo;

    public static final String HDFC = "//android.widget.TextView[@text='HDFC']";

    @AndroidFindBy(xpath = HDFC)
    private WebElement hdfc;

    public static final String CREDILIO_LOGO = "//android.view.View[contains(@content-desc,'Credilio Logo')]";

    @AndroidFindBy(xpath = CREDILIO_LOGO)
    private WebElement credilioLogo;

    public static final String CREDILIO = "//android.widget.TextView[@text='Credilio']";

    @AndroidFindBy(xpath = CREDILIO)
    private WebElement credilio;

    public static final String SELECT_PARTNER = "//android.widget.TextView[@text='Select Partner']";

    @AndroidFindBy(xpath = SELECT_PARTNER)
    private WebElement selectPartner;

    public static final String UNSECURED_CREDIT_CARDS = "//android.widget.TextView[@text='Unsecured Credit Cards']";

    @AndroidFindBy(xpath = UNSECURED_CREDIT_CARDS)
    private WebElement unsecuredCreditCards;

    public HomeCreditBalanceFinanceUnsecuredCreditScreen(TestContext context) {
        super(context);
    }
}
