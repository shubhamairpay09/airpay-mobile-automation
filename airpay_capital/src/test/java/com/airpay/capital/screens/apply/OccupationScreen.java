package com.airpay.capital.screens.apply;

import com.airpay.capital.data.ScreenMessages;
import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

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
        // Marketing copy includes "Salaried" and can keep the next route's text in the tree.
        if (sourceContains("For Self-Employed") && sourceContains("Loan Amount")) {
            return false;
        }
        if (sourceContains(ScreenMessages.OCCUPATION_HEADING, "Select your occupation")) {
            return true;
        }
        return firstPresent(Duration.ofMillis(500),
                MobileWaits.xpath("//android.widget.RadioButton[contains(@content-desc,'Salaried')"
                        + " or contains(@content-desc,'Self-Employed')"
                        + " or contains(@content-desc,'Self Employed')]")) != null;
    }

    public boolean waitUntilDisplayed(Duration timeout) {
        long end = System.currentTimeMillis() + timeout.toMillis();
        while (System.currentTimeMillis() < end) {
            if (isDisplayed()) {
                return true;
            }
            sleep(250);
        }
        return isDisplayed();
    }

    public OccupationScreen selectSalaried() {
        selectRadio("Salaried");
        return this;
    }

    public OccupationScreen selectSelfEmployed() {
        selectRadio("Self-Employed");
        return this;
    }

    /**
     * Continue stays disabled until a radio is checked. Its label is
     * "Select an employment type first" until then.
     */
    private void selectRadio(String label) {
        By radio = MobileWaits.xpath(
                "//android.widget.RadioButton[starts-with(@content-desc," + xpathLiteral(label) + ")]");
        for (int attempt = 0; attempt < 3 && !continueEnabled(); attempt++) {
            WebElement best = null;
            int bestArea = 0;
            for (WebElement el : driver.findElements(radio)) {
                try {
                    var r = el.getRect();
                    int area = r.width * r.height;
                    if (r.y < 250 || area <= bestArea) {
                        continue;
                    }
                    best = el;
                    bestArea = area;
                } catch (Exception ignored) {
                    // stale
                }
            }
            if (best == null) {
                return;
            }
            var r = best.getRect();
            int x = r.x + r.width / 2;
            int y = r.y + r.height / 2;
            adbTap(x, y);
            sleep(700);
        }
    }

    private boolean continueEnabled() {
        for (WebElement el : driver.findElements(MobileWaits.xpath(
                "//android.widget.Button[@content-desc='Continue']"))) {
            try {
                if (el.isDisplayed() && "true".equalsIgnoreCase(String.valueOf(el.getAttribute("enabled")))) {
                    return true;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return false;
    }

    public void tapContinue() {
        long end = System.currentTimeMillis() + Duration.ofSeconds(8).toMillis();
        while (System.currentTimeMillis() < end && !continueEnabled()) {
            sleep(250);
        }
        List<WebElement> buttons = driver.findElements(MobileWaits.xpath(
                "//android.widget.Button[@content-desc='Continue']"));
        for (WebElement button : buttons) {
            try {
                if (!button.isDisplayed()) {
                    continue;
                }
                if (!"true".equalsIgnoreCase(String.valueOf(button.getAttribute("enabled")))) {
                    continue;
                }
                var r = button.getRect();
                if (r.y < 1000) {
                    continue;
                }
                int x = r.x + Math.max(r.width / 2, 1);
                int y = r.y + Math.max(r.height / 2, 1);
                adbTap(x, y);
                break;
            } catch (Exception ignored) {
                // stale
            }
        }
        waitUntilSourceGone(Duration.ofSeconds(8), ScreenMessages.OCCUPATION_HEADING);
    }
}
