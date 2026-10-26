package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** US Stocks Stacks intro. Dump {@code uidump-usstk-01} and {@code uidump-usstk-02-scroll} 2.1.3. */
public class UsStocksLandingScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.ScrollView//android.view.View[@clickable='true' and not(@content-desc)]")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.widget.ScrollView//android.widget.ImageView[not(@content-desc)]")
    private WebElement heroImage;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Stacks are pre-configured baskets')]")
    private WebElement stacksIntro;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Why choose Stacks?']")
    private WebElement whyChoose;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Get diversified holdings from international markets']")
    private WebElement diversified;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Active rebalancing')]")
    private WebElement rebalancing;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Built by investment experts')]")
    private WebElement experts;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Sign up for free']")
    private WebElement signUp;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Already have an account?']")
    private WebElement alreadyHaveAccount;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Login']")
    private WebElement login;

    public UsStocksLandingScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Why choose Stacks?") && sourceContains("Sign up for free");
    }
}
