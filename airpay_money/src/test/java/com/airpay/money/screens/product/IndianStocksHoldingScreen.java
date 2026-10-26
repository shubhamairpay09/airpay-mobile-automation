package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Indian Stocks holding details. Dump {@code uidump-instk-04-holding} 2.1.3. Do not tap Buy or Sell. */
public class IndianStocksHoldingScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Holding Details']/preceding-sibling::android.widget.Button")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Holding Details']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='EQUITY']")
    private WebElement equityTag;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Market Insights']")
    private WebElement marketInsights;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Quantity']")
    private WebElement quantity;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Average Price']")
    private WebElement averagePrice;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Invested Amount']")
    private WebElement investedAmount;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Transactable Quantity']")
    private WebElement transactableQuantity;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='ISIN']")
    private WebElement isin;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Sell']")
    private WebElement sell;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Buy']")
    private WebElement buy;

    public IndianStocksHoldingScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Holding Details") && sourceContains("Market Insights");
    }
}
