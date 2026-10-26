package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Digital Gold locker dashboard after KYC. Dump {@code uidump-gold-10-open} 2.1.3 2026-09-28.
 */
public class DigitalGoldDashboardScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Digital Gold']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Menu option']")
    private WebElement menuOption;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Total Value')]")
    private WebElement totalValue;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Gold']")
    private WebElement goldTab;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Silver']")
    private WebElement silverTab;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='LIVE']")
    private WebElement live;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Available for selling')]")
    private WebElement availableForSelling;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Sell')]")
    private WebElement sell;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Buy']")
    private WebElement buy;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Gold Coins']")
    private WebElement goldCoins;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='View all']")
    private WebElement viewAll;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Dashboard']")
    private WebElement dashboardTab;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Locker']")
    private WebElement lockerTab;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Transactions']")
    private WebElement transactionsTab;

    public DigitalGoldDashboardScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Digital Gold") && sourceContains("Buy")
                && sourceContains("Locker", "Gold Coins");
    }

    public void tapBuy() {
        tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Buy']"),
                accessibility("Buy"));
        sleep(700);
    }

    public void tapSell() {
        tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//*[contains(@content-desc,'Sell')]"),
                accessibility("Sell"));
        sleep(700);
    }

    public void openViewAllCoins() {
        tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//android.view.View[@content-desc='View all']"),
                accessibility("View all"));
        sleep(700);
    }

    public void openLocker() {
        tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//*[@content-desc='Locker']"),
                accessibility("Locker"));
        sleep(600);
    }

    public void openTransactions() {
        tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//*[@content-desc='Transactions']"),
                accessibility("Transactions"));
        sleep(600);
    }

    public void openHelp() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[@content-desc='Help' or contains(@content-desc,'help') or @content-desc='?']"),
                accessibility("Help"));
        sleep(600);
    }

    public void openGoldSection() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Gold']"),
                accessibility("Gold"));
        sleep(400);
    }

    public void openSilverSection() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Silver']"),
                accessibility("Silver"));
        sleep(400);
    }
}
