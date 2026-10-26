package com.airpay.capital.screens.home;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Home')]")
    @iOSXCUITFindBy(accessibility = "Home")
    private WebElement tabHome;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Loan')]")
    @iOSXCUITFindBy(accessibility = "Loan")
    private WebElement tabLoan;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Info')]")
    @iOSXCUITFindBy(accessibility = "Info")
    private WebElement tabInfo;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Profile')]")
    @iOSXCUITFindBy(accessibility = "Profile")
    private WebElement profileButton;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Notifications')]")
    @iOSXCUITFindBy(accessibility = "Notifications")
    private WebElement notificationsButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@clickable='true' and contains(@content-desc,'Personal Loan') and contains(@content-desc,'Apply Now')]")
    @iOSXCUITFindBy(accessibility = "Personal Loan")
    private WebElement personalLoanCard;

    @AndroidFindBy(xpath = "//android.widget.Button[@clickable='true' and contains(@content-desc,'Home Loan') and contains(@content-desc,'Apply Now') and not(contains(@content-desc,'Loan Against'))]")
    @iOSXCUITFindBy(accessibility = "Home Loan")
    private WebElement homeLoanCard;

    @AndroidFindBy(xpath = "//android.widget.Button[@clickable='true' and contains(@content-desc,'Loan Against Property') and contains(@content-desc,'Apply Now')]")
    @iOSXCUITFindBy(accessibility = "Loan Against Property")
    private WebElement lapCard;

    @AndroidFindBy(xpath = "//android.widget.Button[@clickable='true' and contains(@content-desc,'Gold Loan') and contains(@content-desc,'Apply Now')]")
    @iOSXCUITFindBy(accessibility = "Gold Loan")
    private WebElement goldLoanCard;

    @AndroidFindBy(xpath = "//android.widget.Button[@clickable='true' and contains(@content-desc,'Credit Card') and contains(@content-desc,'Apply Now')]")
    @iOSXCUITFindBy(accessibility = "Credit Card")
    private WebElement creditCardCard;

    public HomeScreen(TestContext context) {
        super(context);
    }

    public boolean looksLoggedIn() {
        String src = safeSource();
        if (src.contains("Login With Mobile Number") || src.contains("Enter 10-Digit Number")
                || src.contains("Send OTP") || src.contains("Accept consent")
                || src.contains("Authentication required") || src.contains("Enable biometric")) {
            return false;
        }
        return sourceContains("Personal Loan", "Home Loan", "Dashboard")
                || src.contains("content-desc=\"Home\"")
                || isDisplayed(tabHome);
    }

    public void openHome() {
        if (!tapFirst(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.ImageView[contains(@content-desc,'Home')]"))) {
            tapLabel("Home");
        }
        sleep(400);
    }

    public void openProfile() {
        if (!tapFirst(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Profile')]"))) {
            tapLabel("Profile");
        }
    }

    public void openNotifications() {
        if (!tapFirst(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Notifications')]"))) {
            tapLabel("Notifications");
        }
    }

    public void openInfoTab() {
        if (!tapFirst(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.ImageView[contains(@content-desc,'Info')]"))) {
            tapLabel("Info");
        }
    }

    public void openLoanTab() {
        if (!tapFirst(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.ImageView[contains(@content-desc,'Loan')]"))) {
            tapLabel("Loan");
        }
    }

    /** Tap product card Apply Now strip from home grid (Flutter-safe coordinate tap). */
    public boolean tapProductApplyNow(String productLabel) {
        openHome();
        sleep(500);
        for (int i = 0; i < 6 && !sourceContains(productLabel); i++) {
            swipeUp();
            sleep(280);
        }
        String exclude = "Home Loan".equals(productLabel) ? "Loan Against" : "";
        String xpath = "//android.widget.Button[@clickable='true' and contains(@content-desc,"
                + xpathLiteral(productLabel) + ") and (contains(@content-desc,'Apply Now') or contains(@content-desc,'Apply'))]";
        if (!exclude.isBlank()) {
            xpath += " and not(contains(@content-desc," + xpathLiteral(exclude) + "))";
        }
        bringCardAboveTabBar(xpath);
        String before = shortFingerprint();
        if (tapApplyStrip(xpath)) {
            sleep(2000);
            if (leftHomeGridOrInApply(before)) {
                return true;
            }
        }
        tapApplyStrip(xpath);
        sleep(1800);
        return leftHomeGridOrInApply(before);
    }

    private void bringCardAboveTabBar(String cardXpath) {
        for (int i = 0; i < 4; i++) {
            try {
                List<WebElement> els = driver.findElements(MobileWaits.xpath(cardXpath));
                if (els.isEmpty()) {
                    swipeUp();
                    sleep(300);
                    continue;
                }
                var r = els.get(0).getRect();
                int screenH = driver.manage().window().getSize().getHeight();
                int tabTop = (int) (screenH * 0.88);
                if (r.y + r.height < tabTop) {
                    return;
                }
                swipeUp();
                sleep(300);
            } catch (Exception e) {
                return;
            }
        }
    }

    private boolean tapApplyStrip(String cardXpath) {
        int[] xy = applyStripCoords(cardXpath);
        if (xy == null) {
            return false;
        }
        try {
            driver.executeScript("mobile: clickGesture", java.util.Map.of("x", xy[0], "y", xy[1]));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private int[] applyStripCoords(String cardXpath) {
        try {
            List<WebElement> els = driver.findElements(MobileWaits.xpath(cardXpath));
            if (els.isEmpty()) {
                return null;
            }
            var r = els.get(0).getRect();
            int x = r.x + r.width / 2;
            int y = r.y + Math.max((r.height * 88) / 100, r.height - 36);
            return new int[]{x, y};
        } catch (Exception e) {
            return null;
        }
    }

    private boolean leftHomeGridOrInApply(String beforeFingerprint) {
        if (sourceContains("Tell us about your work", "Select your occupation", "Salaried")) {
            return true;
        }
        String after = shortFingerprint();
        boolean changed = beforeFingerprint != null && !beforeFingerprint.equals(after);
        boolean stillHomeGrid = sourceContains("Personal Loan") && sourceContains("Home Loan")
                && (sourceContains("Loan Against Property") || sourceContains("Credit Card"));
        return changed && !stillHomeGrid;
    }

    private String shortFingerprint() {
        String s = safeSource().replaceAll("\\s+", " ");
        return s.length() > 800 ? s.substring(0, 800) : s;
    }

    public List<String> clickableLabels() {
        List<String> labels = new ArrayList<>();
        List<WebElement> nodes = driver.findElements(MobileWaits.xpath(
                "//*[@clickable='true' and string-length(@content-desc)>0]"));
        for (WebElement n : nodes) {
            String desc = n.getAttribute("contentDescription");
            if (desc == null) {
                desc = n.getAttribute("content-desc");
            }
            if (desc == null || desc.isBlank()) {
                continue;
            }
            String first = desc.split("\n")[0].trim();
            if (first.length() <= 60) {
                labels.add(first);
            }
        }
        return labels;
    }

    public void scrollInfoAndCollect(int swipes) {
        for (int i = 0; i < swipes; i++) {
            swipeUp();
            sleep(600);
        }
    }
}
