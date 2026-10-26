package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Coin delivery blocked when locker has no gold. Dump {@code uidump-gold-42-filter-Apply} 2.1.3.
 * Do not tap Buy now.
 */
public class DigitalGoldInsufficientBalanceSheet extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Insufficient Balance']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'You must first purchase')]")
    private WebElement message;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Buy now']")
    private WebElement buyNow;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Dismiss']")
    private WebElement dismiss;

    public DigitalGoldInsufficientBalanceSheet(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Insufficient Balance") && sourceContains("Buy now");
    }

    public void dismiss() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Dismiss']"),
                MobileWaits.xpath("//*[@content-desc='Dismiss']"));
        sleep(400);
    }
}
