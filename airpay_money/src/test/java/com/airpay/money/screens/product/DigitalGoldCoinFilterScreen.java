package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Buy Coins filter sheet. Dump {@code uidump-gold-41-coins-filter} 2.1.3.
 * Opened from nameless filter ImageView at [923,95][1001,253].
 */
public class DigitalGoldCoinFilterScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Filter By']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Clear all']")
    private WebElement clearAll;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Metal Weight']")
    private WebElement metalWeight;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Price Range']")
    private WebElement priceRange;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Less than 2g']")
    private WebElement lessThan2g;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='2-4 gms']")
    private WebElement twoToFour;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='4-6 gms']")
    private WebElement fourToSix;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='6-8 gms']")
    private WebElement sixToEight;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='8+ gms']")
    private WebElement eightPlus;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Apply')]")
    private WebElement apply;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Dismiss']")
    private WebElement dismiss;

    public DigitalGoldCoinFilterScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Filter By") && sourceContains("Metal Weight", "Apply");
    }

    public void selectWeight(String label) {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc=" + xpathLiteral(label) + "]"));
    }

    public void tapApply() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[@content-desc='Apply']"));
        sleep(500);
    }

    public void tapClearAll() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Clear all']"));
        sleep(400);
    }

    public void dismiss() {
        tapFirst(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Dismiss']"));
        sleep(300);
    }
}
