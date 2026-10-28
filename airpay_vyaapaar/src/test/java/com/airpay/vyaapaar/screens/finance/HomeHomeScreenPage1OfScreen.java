package com.airpay.vyaapaar.screens.finance;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Home screen Page 1 of 3/Finance. Module finance. */
public class HomeHomeScreenPage1OfScreen extends BaseScreen {

    public static final String PAGE_ID = "home-home-screen-page-1-of-3-finance";

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

    public HomeHomeScreenPage1OfScreen(TestContext context) {
        super(context);
    }
}
