package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class InvestHubScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Mutual Funds']")
    @iOSXCUITFindBy(accessibility = "Mutual Funds")
    private WebElement mutualFundsTile;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Digital Gold']")
    @iOSXCUITFindBy(accessibility = "Digital Gold")
    private WebElement goldTile;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Fixed Deposit']")
    @iOSXCUITFindBy(accessibility = "Fixed Deposits")
    private WebElement fdTile;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='NPS']")
    @iOSXCUITFindBy(accessibility = "NPS")
    private WebElement npsTile;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='US Stocks']")
    @iOSXCUITFindBy(accessibility = "US Stocks")
    private WebElement usStocksTile;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Indian Stocks']")
    private WebElement indianStocksTile;

    public InvestHubScreen(TestContext context) {
        super(context);
    }

    public void open() {
        new HomeScreen(context).openInvestTab();
        sleep(600);
    }

    public boolean isDisplayed() {
        return sourceContains("Mutual Funds") && sourceContains("Digital Gold", "Fixed Deposit");
    }
}
