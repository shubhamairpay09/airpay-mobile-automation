package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Buy Coins grid. Dump {@code uidump-gold-13-coins} / {@code uidump-gold-40-coins} 2.1.3.
 * Filter control is a nameless ImageView at [923,95][1001,253].
 */
public class DigitalGoldCoinsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Buy Coins']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Augmont 1Gm Gold Coin')]")
    private WebElement coin1g;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Augmont 10Gm Gold Coin')]")
    private WebElement coin10g;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Augmont 50Gm Gold Coin')]")
    private WebElement coin50g;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Augmont 20Gm Gold Coin')]")
    private WebElement coin20g;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Augmont 0.1Gm Gold Coin')]")
    private WebElement coin01g;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Augmont 0.5Gm Gold Coin')]")
    private WebElement coin05g;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Augmont 8Gm Gold Coin')]")
    private WebElement coin8g;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Augmont 5Gm Gold Coin')]")
    private WebElement coin5g;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Augmont 2Gm Gold Coin')]")
    private WebElement coin2g;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='No Coins Found']")
    private WebElement empty;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'no coins for selected filters')]")
    private WebElement emptyHint;

    public DigitalGoldCoinsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Buy Coins") && sourceContains("Augmont", "No Coins Found");
    }

    public void openFilters() {
        tapAt(962, 174);
        sleep(600);
    }

    public boolean openCoin(String gramsLabel) {
        return tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//android.view.View[contains(@content-desc," + xpathLiteral(gramsLabel) + ")]"));
    }
}
