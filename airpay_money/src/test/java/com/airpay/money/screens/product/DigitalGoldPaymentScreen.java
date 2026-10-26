package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Airpay payment page opened from Make Payment.
 * Continue is never tapped and no card or UPI is entered.
 */
public class DigitalGoldPaymentScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Continue') or contains(@text,'Continue')]")
    private WebElement continueButton;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Order ID') or contains(@text,'Order')]")
    private WebElement orderId;

    @AndroidFindBy(xpath = "//android.webkit.WebView")
    private WebElement webView;

    public DigitalGoldPaymentScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return loadedSource().contains("Continue");
    }

    /**
     * The payment shell appears before the Airpay WebView exposes its labels.
     * Wait until Continue is in the accessibility tree. An empty WebView is not the page.
     */
    public String loadedSource() {
        long end = System.currentTimeMillis() + Duration.ofSeconds(25).toMillis();
        String src = pageSource();
        while (System.currentTimeMillis() < end && !src.contains("Continue")) {
            sleep(500);
            src = pageSource();
        }
        return src;
    }

    public boolean continueBlocked() {
        WebElement button = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//*[@content-desc='Continue' or @text='Continue']"));
        if (button != null && "false".equals(button.getAttribute("enabled"))) {
            return true;
        }
        WebElement box = firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.CheckBox"));
        if (box != null && !"true".equals(box.getAttribute("checked"))) {
            return true;
        }
        return isDisplayed() && !sourceContains("Payment Successful", "Buy Successful");
    }
}
