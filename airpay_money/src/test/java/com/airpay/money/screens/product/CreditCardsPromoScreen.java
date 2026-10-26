package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Cards promo. Dump {@code uidump-ins-life-04-Back} 2.1.3. Do not tap Proceed into an application. */
public class CreditCardsPromoScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Credit Cards']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Welcome Gifts!']")
    private WebElement welcomeGifts;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Bonus Reward Points!']")
    private WebElement bonusPoints;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Exciting Gift Vouchers!']")
    private WebElement vouchers;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Unbelievable Discounts!']")
    private WebElement discounts;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Fuel Convenience Fee Waiver!']")
    private WebElement fuelWaiver;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Travel')]")
    private WebElement travelPerks;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Next']")
    private WebElement next;

    public CreditCardsPromoScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Credit Cards") && sourceContains("Welcome Gifts");
    }
}
