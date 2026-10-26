package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Indian Stocks symbol search. Dump {@code uidump-instk-06-filter} 2.1.3. Do not place an order. */
public class IndianStocksSearchScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Search']/preceding-sibling::android.widget.Button")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Search']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Search']")
    private WebElement searchField;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='All']")
    private WebElement all;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Equities']")
    private WebElement equities;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='ETFs']")
    private WebElement etfs;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'NSE')]")
    private WebElement symbolRow;

    public IndianStocksSearchScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Search") && sourceContains("Equities") && sourceContains("ETFs");
    }
}
