package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Digital Gold Transactions tab. Dump {@code uidump-gold-22-tx} 2.1.3.
 */
public class DigitalGoldTransactionsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Transaction History']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Transactions']")
    private WebElement tab;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='No Transaction']")
    private WebElement empty;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'We don')]")
    private WebElement emptyHint;

    @AndroidFindBy(xpath = "//*[@content-desc='Buy' or contains(@content-desc,'Buy')]")
    private WebElement buyTab;

    @AndroidFindBy(xpath = "//*[@content-desc='Sell' or contains(@content-desc,'Sell')]")
    private WebElement sellTab;

    @AndroidFindBy(xpath = "//*[@content-desc='Gift' or contains(@content-desc,'Gift')]")
    private WebElement giftTab;

    @AndroidFindBy(xpath = "//*[@content-desc='Delivery' or contains(@content-desc,'Delivery')]")
    private WebElement deliveryTab;

    @AndroidFindBy(xpath = "//*[@content-desc='One Time' or contains(@content-desc,'One Time')]")
    private WebElement oneTime;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'SIP')]")
    private WebElement sip;

    public DigitalGoldTransactionsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Transaction History") && sourceContains("No Transaction", "Transactions");
    }
}
