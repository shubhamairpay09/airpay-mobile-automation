package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Indian Stocks orders. Dump {@code uidump-instk-03-orders} 2.1.3. Do not place a new order. */
public class IndianStocksOrdersScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Portfolio']/preceding-sibling::android.widget.Button")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Portfolio']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Orders') and contains(@content-desc,'Tab 2 of 2')]")
    private WebElement ordersTab;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Portfolio']/following-sibling::android.view.View[@clickable='true' and not(@content-desc)]")
    private WebElement searchIcon;

    @AndroidFindBy(xpath = "//android.view.View[starts-with(@content-desc,'All')]")
    private WebElement allFilter;

    @AndroidFindBy(xpath = "//android.view.View[starts-with(@content-desc,'Placed')]")
    private WebElement placedFilter;

    @AndroidFindBy(xpath = "//android.view.View[starts-with(@content-desc,'Completed')]")
    private WebElement completedFilter;

    @AndroidFindBy(xpath = "//android.view.View[starts-with(@content-desc,'Failed')]")
    private WebElement failedFilter;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Qty:')]")
    private WebElement orderRow;

    public IndianStocksOrdersScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Orders") && sourceContains("Completed");
    }
}
