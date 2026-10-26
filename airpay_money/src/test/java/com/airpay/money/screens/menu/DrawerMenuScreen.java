package com.airpay.money.screens.menu;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Pre-login side drawer opened via {@code text="Menu"} on guest screens. */
public class DrawerMenuScreen extends BaseScreen {

    private static final Set<String> SKIP = Set.of(
            "Menu", "Login", "Back", "Close search", "Home", "Invest", "Portfolio", "Loans");

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Menu\")")
    private WebElement menuButton;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Start Financial Health Check\")")
    @iOSXCUITFindBy(accessibility = "Start Financial Health Check")
    private WebElement startHealthCheck;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Financial Health Check\")")
    @iOSXCUITFindBy(accessibility = "Financial Health Check")
    private WebElement financialHealthSection;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Resources\")")
    @iOSXCUITFindBy(accessibility = "Resources")
    private WebElement resourcesSection;

    public DrawerMenuScreen(TestContext context) {
        super(context);
    }

    public boolean isOpen() {
        return sourceContains(
                "Start Financial Health Check", "Financial Health Check", "Resources", "airpay Money");
    }

    public boolean openFromGuestHeader() {
        if (tapFirst(Duration.ofSeconds(6),
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Menu\")"),
                MobileWaits.xpath("//android.widget.Button[@text='Menu']"),
                AppiumBy.accessibilityId("Menu"))) {
            sleep(700);
            return isOpen();
        }
        return false;
    }

    public void close() {
        if (!isOpen()) {
            return;
        }
        if (tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[contains(@content-desc,'Close')]"),
                AppiumBy.androidUIAutomator("new UiSelector().descriptionContains(\"Close\")"),
                MobileWaits.xpath("//android.widget.Button[contains(@text,'Close')]"))) {
            sleep(500);
            return;
        }
        tapBack();
        sleep(400);
    }

    public boolean tapStartFinancialHealthCheck() {
        return tapSection("Start Financial Health Check");
    }

    public boolean expandFinancialHealthCheck() {
        return tapSection("Financial Health Check");
    }

    public boolean expandResources() {
        return tapSection("Resources");
    }

    public boolean tapSection(String label) {
        String escaped = label.replace("\\", "\\\\").replace("\"", "\\\"");
        if (tapFirst(Duration.ofSeconds(5),
                AppiumBy.androidUIAutomator("new UiSelector().text(\"" + escaped + "\")"),
                MobileWaits.xpath("//*[@text=" + xpathLiteral(label) + " or contains(@content-desc," + xpathLiteral(label) + ")]"))) {
            sleep(650);
            return true;
        }
        return tapLabel(label);
    }

    public List<String> visibleDrawerLabels() {
        List<String> labels = new ArrayList<>();
        if (!isOpen()) {
            return labels;
        }
        for (WebElement n : driver.findElements(MobileWaits.xpath(
                "//*[@clickable='true' and (string-length(@text)>0 or string-length(@content-desc)>0)]"))) {
            try {
                String text = n.getAttribute("text");
                String desc = n.getAttribute("contentDescription");
                String label = firstNonBlank(text, desc);
                if (label == null || label.isBlank() || label.length() > 80 || SKIP.contains(label)) {
                    continue;
                }
                labels.add(label.split("\n")[0].trim());
            } catch (Exception ignored) {
                // stale
            }
        }
        return labels;
    }

    private static String firstNonBlank(String... values) {
        for (String v : values) {
            if (v != null && !v.isBlank()) {
                return v;
            }
        }
        return null;
    }
}
