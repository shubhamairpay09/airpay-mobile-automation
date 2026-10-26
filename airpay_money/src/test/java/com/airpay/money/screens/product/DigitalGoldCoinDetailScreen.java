package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Single Augmont coin PDP. Dump {@code uidump-gold-35-filter-Metal-Weight} 2.1.3
 * (50g coin). Do not tap Request Delivery into checkout.
 */
public class DigitalGoldCoinDetailScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Gold coin')]")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'SKU')]")
    private WebElement sku;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Metal Type')]")
    private WebElement metalType;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Weight')]")
    private WebElement weight;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Purity')]")
    private WebElement purity;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Making and Delivery Charges')]")
    private WebElement makingCharges;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Quantity')]")
    private WebElement quantity;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='30 Day Return Policy']")
    private WebElement returnPolicy;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='100% BuyBack']")
    private WebElement buyBack;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Lowest Price Guarantee']")
    private WebElement lowestPrice;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='BIS Hallmarked jewellery']")
    private WebElement bisHallmark;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Description']")
    private WebElement description;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Request Delivery')]")
    private WebElement requestDelivery;

    public DigitalGoldCoinDetailScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Gold coin") && sourceContains("SKU", "Request Delivery");
    }

    /** Nameless minus stepper at [786,1507][881,1581]. */
    public void tapQuantityMinus() {
        tapAt(833, 1544);
        sleep(250);
    }

    /** Nameless plus stepper at [928,1507][1023,1581]. */
    public void tapQuantityPlus() {
        tapAt(975, 1544);
        sleep(250);
    }
}
