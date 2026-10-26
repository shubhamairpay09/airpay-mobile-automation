package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * First-visit Digital Gold walkthrough. Dump {@code uidump-gold-04-landing} 2.1.3 2026-09-25.
 * Back chevron at [45,118][158,230] has no content-desc.
 */
public class DigitalGoldIntroScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Own the Best Digital Assets Online!']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'one-stop shop for all your digital gold')]")
    private WebElement subtitle;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Get digital gold as low as')]")
    private WebElement benefitLowAmount;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'safely stored in vaults')]")
    private WebElement benefitVault;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'insured physical delivery')]")
    private WebElement benefitDelivery;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Next']")
    private WebElement next;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Get Started']")
    private WebElement getStarted;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Continue']")
    private WebElement continueButton;

    public DigitalGoldIntroScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Own the Best Digital Assets Online", "digital gold as low as");
    }

    public void tapNext() {
        if (!tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Next']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Get Started']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Continue']"),
                accessibility("Next"))) {
            throw new IllegalStateException("Next not found on Digital Gold intro.");
        }
        sleep(700);
    }

    public void completeIfShown() {
        for (int i = 0; i < 6 && isDisplayed(); i++) {
            tapNext();
        }
    }
}
