package com.airpay.capital.screens.apply;

import com.airpay.capital.data.ScreenMessages;
import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.config.FrameworkConfig;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class EligibilityScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Check Eligib')]")
    @iOSXCUITFindBy(accessibility = "Check Eligibility")
    private WebElement checkEligibilityButton;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'No Offers Available') or contains(@content-desc,'No loan offers available')]")
    @iOSXCUITFindBy(accessibility = "No Offers Available")
    private WebElement noOffersMessage;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'CIBIL') or contains(@content-desc,'credit score')]")
    @iOSXCUITFindBy(accessibility = "CIBIL")
    private WebElement cibilError;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'offer available') or contains(@content-desc,'offers available')]")
    @iOSXCUITFindBy(accessibility = "offer available")
    private WebElement offersBanner;

    public EligibilityScreen(TestContext context) {
        super(context);
    }

    public boolean checkEligibilityVisible() {
        return sourceContains("Check Eligib")
                || firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Check Eligib')]")) != null;
    }

    public void tapCheckEligibility() {
        if (!tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Check Eligib')]"))) {
            tapAny(java.util.List.of("Check Eligibility", "Check Eligiblity"));
        }
        sleep(3000);
    }

    public boolean offersAreVisible() {
        return sourceContains(ScreenMessages.OFFERS_AVAILABLE, ScreenMessages.OFFER_AVAILABLE,
                ScreenMessages.APPLY_NOW_FOR)
                || firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'offer available')]")) != null;
    }

    public boolean noOffersAreVisible() {
        return sourceContains(ScreenMessages.NO_OFFERS_AVAILABLE, ScreenMessages.NO_LOAN_OFFERS,
                ScreenMessages.NO_OFFERS_REASON)
                || isDisplayed(noOffersMessage);
    }

    public boolean cibilLowErrorIsVisible() {
        FrameworkConfig config = context.config();
        String fragment = config.get("capital.cibil.low.message", ScreenMessages.CIBIL_FRAGMENT);
        return sourceContains(fragment, ScreenMessages.CREDIT_SCORE, ScreenMessages.LOW_CREDIT)
                || isDisplayed(cibilError);
    }
}
