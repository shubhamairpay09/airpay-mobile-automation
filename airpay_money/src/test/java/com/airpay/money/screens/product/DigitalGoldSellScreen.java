package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Sell Gold &amp; Silver. Dump {@code uidump-gold-12-sell} / {@code uidump-gold-35-sell-gold} 2.1.3.
 */
public class DigitalGoldSellScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Sell Gold')]")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Available for selling')]")
    private WebElement available;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'7 days')]")
    private WebElement sevenDayLock;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Gold') and contains(@content-desc,'Tab 1')]")
    private WebElement goldTab;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Silver') and contains(@content-desc,'Tab 2')]")
    private WebElement silverTab;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Sell in rupees']")
    private WebElement sellInRupees;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Sell in grams']")
    private WebElement sellInGrams;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'AUGMONT')]")
    private WebElement augmontCard;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Total available value')]")
    private WebElement totalAvailable;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    private WebElement amount;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Proceed']")
    private WebElement proceed;

    public DigitalGoldSellScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Sell in rupees") && sourceContains("Sell in grams");
    }

    public void openSilverTab() {
        tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Silver') and contains(@content-desc,'Tab')]"));
        sleep(500);
    }

    public void chooseSellInGrams() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Sell in grams']"));
    }

    public void chooseSellInRupees() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Sell in rupees']"));
    }

    public void enterAmount(String digits) {
        WebElement field = firstPresent(Duration.ofSeconds(5),
                MobileWaits.xpath("//android.widget.EditText"));
        if (field == null) {
            return;
        }
        field.click();
        try {
            field.clear();
        } catch (Exception ignored) {
            // Flutter fields often reject clear()
        }
        field.sendKeys(digits);
        sleep(300);
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // keyboard already closed
        }
        sleep(300);
    }

    public void revealBottomActions() {
        for (int i = 0; i < 3; i++) {
            if (!driver.findElements(MobileWaits.xpath("//android.widget.Button[@content-desc='Proceed']")).isEmpty()) {
                return;
            }
            swipeUp();
        }
    }

    public boolean proceedEnabled() {
        revealBottomActions();
        WebElement button = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Proceed']"));
        return button != null && "true".equals(button.getAttribute("enabled"));
    }
}
