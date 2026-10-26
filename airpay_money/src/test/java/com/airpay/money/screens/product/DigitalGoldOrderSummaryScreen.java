package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Order summary after Proceed on Buy Gold and Silver.
 * Make Payment opens the gateway. This screen does not enter card or UPI details.
 */
public class DigitalGoldOrderSummaryScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Order Summary') or contains(@content-desc,'Buying Price')]")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Make Payment')]")
    private WebElement makePayment;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Go Back')]")
    private WebElement goBack;

    public DigitalGoldOrderSummaryScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Make Payment", "Order Summary", "Buying Price", "Total Payment");
    }

    public void tapMakePayment() {
        tapFirst(Duration.ofSeconds(8),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Make Payment')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Make Payment')]"));
        sleep(1500);
    }

    public void tapGoBack() {
        tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//*[contains(@content-desc,'Go Back')]"),
                accessibility("Go Back"));
        sleep(800);
    }
}
