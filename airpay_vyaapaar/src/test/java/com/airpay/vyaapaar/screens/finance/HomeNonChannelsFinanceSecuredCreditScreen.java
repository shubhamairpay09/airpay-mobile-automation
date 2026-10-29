package com.airpay.vyaapaar.screens.finance;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Finance/Secured Credit Cards. Module finance. */
public class HomeNonChannelsFinanceSecuredCreditScreen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-finance-secured-credit-cards";

    public static final String CREDILIO_CARDS_LOGO = "//android.view.View[contains(@content-desc,'Credilio Cards Logo')]";

    @AndroidFindBy(xpath = CREDILIO_CARDS_LOGO)
    private WebElement credilioCardsLogo;

    public static final String CREDILIO_CARDS = "//android.widget.TextView[contains(@content-desc,'Credilio Cards')]";

    @AndroidFindBy(xpath = CREDILIO_CARDS)
    private WebElement credilioCards;

    public static final String SELECT_PARTNER = "//android.widget.TextView[contains(@content-desc,'Select Partner')]";

    @AndroidFindBy(xpath = SELECT_PARTNER)
    private WebElement selectPartner;

    public static final String SECURED_CREDIT_CARDS = "//android.widget.TextView[@text='Secured Credit Cards']";

    @AndroidFindBy(xpath = SECURED_CREDIT_CARDS)
    private WebElement securedCreditCards;

    public HomeNonChannelsFinanceSecuredCreditScreen(TestContext context) {
        super(context);
    }
}
