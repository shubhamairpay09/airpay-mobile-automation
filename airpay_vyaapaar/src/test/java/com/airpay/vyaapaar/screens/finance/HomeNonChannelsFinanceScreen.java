package com.airpay.vyaapaar.screens.finance;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Finance. Module finance. */
public class HomeNonChannelsFinanceScreen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-finance";

    public static final String SECURED_CREDIT_CARDS = "//android.view.View[contains(@content-desc,'Secured Credit Cards')]";

    @AndroidFindBy(xpath = SECURED_CREDIT_CARDS)
    private WebElement securedCreditCards;

    public static final String UNSECURED_CREDIT_CARDS = "//android.view.View[contains(@content-desc,'Unsecured Credit Cards')]";

    @AndroidFindBy(xpath = UNSECURED_CREDIT_CARDS)
    private WebElement unsecuredCreditCards;

    public static final String NPS = "//android.view.View[contains(@content-desc,'NPS')]";

    @AndroidFindBy(xpath = NPS)
    private WebElement nps;

    public static final String FIXED_DEPOSITS = "//android.view.View[contains(@content-desc,'Fixed Deposits')]";

    @AndroidFindBy(xpath = FIXED_DEPOSITS)
    private WebElement fixedDeposits;

    public static final String FINANCE = "//android.widget.TextView[@text='Finance']";

    @AndroidFindBy(xpath = FINANCE)
    private WebElement finance;

    public static final String MESSAGE_FROM_JIOMART_OFFERS = "//android.widget.Toast[@text='Message from JioMart Offers']";

    @AndroidFindBy(xpath = MESSAGE_FROM_JIOMART_OFFERS)
    private WebElement messageFromJiomartOffers;

    public HomeNonChannelsFinanceScreen(TestContext context) {
        super(context);
    }
}
