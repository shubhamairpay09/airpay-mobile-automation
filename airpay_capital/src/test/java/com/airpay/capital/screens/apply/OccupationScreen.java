package com.airpay.capital.screens.apply;

import com.airpay.capital.data.ScreenMessages;
import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class OccupationScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Tell us about your work')]")
    @iOSXCUITFindBy(accessibility = "Tell us about your work")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.RadioButton[contains(@content-desc,'Salaried')]")
    @iOSXCUITFindBy(accessibility = "Salaried")
    private WebElement salariedRadio;

    @AndroidFindBy(xpath = "//android.widget.RadioButton[contains(@content-desc,'Self-Employed') or contains(@content-desc,'Self Employed')]")
    @iOSXCUITFindBy(accessibility = "Self-Employed")
    private WebElement selfEmployedRadio;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Continue')]")
    @iOSXCUITFindBy(accessibility = "Continue")
    private WebElement continueButton;

    public OccupationScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains(ScreenMessages.OCCUPATION_HEADING, ScreenMessages.SALARIED)
                || isDisplayed(heading);
    }

    public boolean waitUntilDisplayed(Duration timeout) {
        return waitUntilSourceContains(timeout, ScreenMessages.OCCUPATION_HEADING, ScreenMessages.SALARIED, "Self-Employed")
                || isDisplayed();
    }

    public OccupationScreen selectSalaried() {
        if (!tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.RadioButton[contains(@content-desc,'Salaried')]"),
                MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc,'Salaried')]"))) {
            tapLabel(ScreenMessages.SALARIED);
        }
        return this;
    }

    public OccupationScreen selectSelfEmployed() {
        if (!tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.RadioButton[contains(@content-desc,'Self-Employed')"
                        + " or contains(@content-desc,'Self Employed')]"))) {
            tapAny(java.util.List.of("Self-Employed", "Self Employed"));
        }
        return this;
    }

    public void tapContinue() {
        if (!tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Continue')]"))) {
            tapLabel("Continue");
        }
        waitUntilSourceGone(Duration.ofSeconds(8), ScreenMessages.OCCUPATION_HEADING);
    }
}
