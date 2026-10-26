package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Index Funds lists — Nifty 50 through Global Fund.
 * Shared Search + Filter chrome. Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsIndexListScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Nifty 50']")
    private WebElement titleNifty50;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Nifty Mid Cap']")
    private WebElement titleNiftyMid;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Nifty 100']")
    private WebElement titleNifty100;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Nifty Small Cap 50']")
    private WebElement titleNiftySmall;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Nifty Large Cap']")
    private WebElement titleNiftyLarge;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Global Fund']")
    private WebElement titleGlobal;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='']")
    private WebElement filter;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='']")
    private WebElement search;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'DSP NIFTY 50 Equal Weight Index Fund')]")
    private WebElement firstNifty50Fund;

    public MutualFundsIndexListScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.EditText[@content-desc='']"),
                MobileWaits.xpath("//android.view.View[@content-desc='Nifty 50']"),
                MobileWaits.xpath("//android.view.View[@content-desc='Global Fund']")) != null;
    }

    public boolean openFundCard() {
        for (int pass = 0; pass < 3; pass++) {
            if (tapFundCard()) {
                return true;
            }
            swipeUp();
            sleep(400);
        }
        return false;
    }

    private boolean tapFundCard() {
        for (WebElement card : driver.findElements(MobileWaits.xpath("//*[@clickable='true' and @content-desc]"))) {
            try {
                String desc = String.valueOf(card.getAttribute("content-desc"));
                String lower = desc.toLowerCase();
                if (!card.isDisplayed() || desc.length() < 28) {
                    continue;
                }
                if (lower.contains("back") || lower.contains("dashboard") || lower.contains("filter")) {
                    continue;
                }
                if (lower.contains("fund") || lower.contains("investment") || desc.contains("%")) {
                    card.click();
                    sleep(800);
                    return true;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return false;
    }
}
