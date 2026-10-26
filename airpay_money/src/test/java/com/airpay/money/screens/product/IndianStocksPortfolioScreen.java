package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Indian Stocks portfolio. Dump {@code uidump-instk-01} 2.1.3. Do not place a buy or sell. */
public class IndianStocksPortfolioScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Portfolio']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Holdings') and contains(@content-desc,'Tab 1 of 2')]")
    private WebElement holdingsTab;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Orders') and contains(@content-desc,'Tab 2 of 2')]")
    private WebElement ordersTab;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Refresh']")
    private WebElement refresh;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'holdings imported')]")
    private WebElement importSummary;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Qty:')]")
    private WebElement holdingRow;

    public IndianStocksPortfolioScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Portfolio") && sourceContains("Holdings");
    }
}
