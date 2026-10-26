package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds Help WebView (Portfolio / Transactions).
 * Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsHelpScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Mutual Funds']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.webkit.WebView")
    private WebElement webView;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Please Wait']")
    private WebElement pleaseWait;

    public MutualFundsHelpScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.webkit.WebView"),
                MobileWaits.xpath("//android.view.View[@content-desc='Please Wait']")) != null;
    }
}
