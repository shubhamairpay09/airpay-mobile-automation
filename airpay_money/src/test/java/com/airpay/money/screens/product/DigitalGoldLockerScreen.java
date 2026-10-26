package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Locker Details. Dump {@code uidump-gold-18-locker} / {@code uidump-gold-23-locker} 2.1.3.
 */
public class DigitalGoldLockerScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Locker Details']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Gold Details')]")
    private WebElement goldDetails;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Silver Details')]")
    private WebElement silverDetails;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'live selling price')]")
    private WebElement livePriceNote;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'only after 7 days')]")
    private WebElement sevenDayLock;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='No Transaction']")
    private WebElement noTransaction;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Buy Gold')]")
    private WebElement buyGold;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Sell Gold')]")
    private WebElement sellGold;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Buy Coin')]")
    private WebElement buyCoin;

    public DigitalGoldLockerScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Locker Details") && sourceContains("Gold Details");
    }

    public void openSilverDetails() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Silver Details')]"));
        sleep(400);
    }

    public void tapBuyGold() {
        tapLabel("Gold Details");
        tapIconAbove("Buy Gold", "Buy Silver");
    }

    public void tapSellGold() {
        tapLabel("Gold Details");
        tapIconAbove("Sell Gold", "Sell Silver");
    }

    /**
     * Buy Gold, Sell Gold, and Buy Coin labels sit on the bottom edge and are not clickable.
     * The tappable control is the ImageView directly above each label.
     */
    private void tapIconAbove(String... labels) {
        for (String label : labels) {
            WebElement el = firstPresent(Duration.ofSeconds(3),
                    MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]"));
            if (el == null) {
                continue;
            }
            var rect = el.getRect();
            int x = rect.getX() + rect.getWidth() / 2;
            int y = rect.getY() - 70;
            tapAt(x, y);
            sleep(900);
            return;
        }
    }

    public void tapBuyCoin() {
        tapIconAbove("Buy Coin");
    }
}
