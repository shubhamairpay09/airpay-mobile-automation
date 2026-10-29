package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Logged-in FinSight hub. Live dump 2026-09-16.
 */
public class FinSightHomeScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Your Financial Health Score')]")
    private WebElement scoreSummary;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Reassess']")
    private WebElement reassess;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='View Details']")
    private WebElement viewDetails;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Sync Your Net Worth']")
    private WebElement syncNetWorth;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Products']")
    private WebElement productsTab;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='FinSight']")
    private WebElement finsightTab;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Calculators']")
    private WebElement calculatorsTab;

    public FinSightHomeScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        String src = pageSource().replace("&#10;", "\n");
        boolean tabs = src.contains("FinSight") && (src.contains("Products") || src.contains("Calculators"));
        String lower = src.toLowerCase();
        boolean body = src.contains("Your Financial Health Score")
                || src.contains("Financial Health Check")
                || src.contains("Take Action")
                || src.contains("Compare and File")
                || lower.contains("sync your networth")
                || lower.contains("sync your net worth")
                || lower.contains("view net worth")
                || lower.contains("key gaps");
        return tabs && body;
    }

    public boolean syncOfferDisplayed() {
        return pageSource().contains("Sync Your Net Worth");
    }

    public boolean alreadySyncedCard() {
        String lower = pageSource().toLowerCase();
        return lower.contains("view net worth") || lower.contains("accounts synced");
    }

    public String netWorthCardText() {
        String card = descriptionContaining("networth");
        if (card.isBlank()) {
            card = descriptionContaining("Net Worth");
        }
        return card.isBlank() ? "no net worth card" : card;
    }

    /**
     * Brings the sync card up if it is under the tab bar, then presses it.
     * Does not tap Continue on the Financial Health Check intro.
     */
    public boolean pressSyncOffer() {
        if (!revealSyncOffer()) {
            return false;
        }
        WebElement target = firstPresent(Duration.ofSeconds(3), MobileWaits.xpath(syncOfferXpath()));
        if (target == null) {
            return false;
        }
        if (target.getRect().getY() > 1400) {
            scrollListOnce();
            target = firstPresent(Duration.ofSeconds(2), MobileWaits.xpath(syncOfferXpath()));
            if (target == null) {
                return false;
            }
        }
        pressElement(target);
        sleep(800);
        return true;
    }

    private boolean revealSyncOffer() {
        if (syncOfferDisplayed()) {
            return true;
        }
        return scrollIntoDescription("Sync Your Net Worth");
    }

    private void scrollListOnce() {
        try {
            driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().className(\"android.widget.ScrollView\"))"
                            + ".setAsVerticalList().scrollForward()"));
        } catch (Exception ignored) {
            // end of the list
        }
    }

    private static String syncOfferXpath() {
        return "//*[@clickable='true' and @content-desc='Sync Your Net Worth']";
    }

    public boolean tapLetsStart() {
        return tapFirst(Duration.ofSeconds(8),
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Let's start\")"),
                AppiumBy.accessibilityId("Let's start"),
                MobileWaits.xpath("//*[contains(@content-desc,\"Let's start\") or @text=\"Let's start\"]"));
    }

    public boolean openProductsTab() {
        return tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Products']"),
                MobileWaits.xpath("//android.view.View[@content-desc='Products']"),
                AppiumBy.accessibilityId("Products"));
    }

    public boolean openCalculatorsTab() {
        WebElement tab = firstPresent(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Calculators']"),
                MobileWaits.xpath("//android.view.View[@content-desc='Calculators']"),
                AppiumBy.accessibilityId("Calculators"));
        if (tab == null) {
            return false;
        }
        pressElement(tab);
        sleep(900);
        return true;
    }
}
