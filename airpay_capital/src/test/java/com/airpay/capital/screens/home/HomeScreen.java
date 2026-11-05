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
        return sourceContains("Personal Loan", "Home Loan", "Dashboard",
                "Resume Application", "Your loan journey", "offers available", "Tab 1 of 3")
                || src.contains("content-desc=\"Home\"")
                || isDisplayed(tabHome);
    }

    public boolean journeyInProgress() {
        return sourceContains("Your loan journey is in progress");
    }

    public void openHome() {
        for (int i = 0; i < 5 && !productGridVisible(); i++) {
            if (journeyInProgress()
                    || (sourceContains("Back") && !sourceContains("Tab 1 of 3"))) {
                if (!tapCenter(MobileWaits.xpath("//android.widget.Button[@content-desc='Back']"))) {
                    tapBack();
                }
                waitUntilSourceContains(Duration.ofSeconds(4), "Tab 1 of 3", "Apply Now", "Your loan journey is in progress");
                continue;
            }
            tapFirst(Duration.ofSeconds(2),
                    MobileWaits.xpath("//android.widget.ImageView[contains(@content-desc,'Home')]"));
            waitUntilSourceContains(Duration.ofSeconds(4), "Tab 1 of 3", "Apply Now");
        }
    }

    private boolean productGridVisible() {
        return sourceContains("Tab 1 of 3") && sourceContains("Apply Now") && !journeyInProgress();
    }

    public boolean tapResumeApplication() {
        if (tapCenter(MobileWaits.xpath("//android.widget.Button[@content-desc='Resume Application']"))) {
            return true;
        }
        return tapLabel("Resume Application");
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
        String xpath = productCardXpath(productLabel);
        if (!scrollCardIntoView(xpath)) {
            return false;
        }
        String before = shortFingerprint();
        for (int attempt = 0; attempt < 3; attempt++) {
            if (!tapApplyStrip(productLabel, attempt)) {
                continue;
            }
            waitUntilSourceContains(Duration.ofSeconds(8),
                    "Tell us about your work", "Enter PAN", "Salaried",
                    "Your loan journey is in progress", "Resume Application", "offers available");
            if (leftHomeGridOrInApply(before)) {
                return true;
            }
        }
        return leftHomeGridOrInApply(before);
    }

    private String productCardXpath(String productLabel) {
        String exclude = "Home Loan".equals(productLabel) ? "Loan Against" : "";
        String xpath = "//*[@clickable='true' and contains(@content-desc," + xpathLiteral(productLabel) + ")";
        if (!exclude.isBlank()) {
            xpath += " and not(contains(@content-desc," + xpathLiteral(exclude) + "))";
        }
        xpath += "]";
        return xpath;
    }

    /** Smallest card that names this product and Apply Now, so a parent grid is not tapped. */
    private WebElement productCard(String productLabel) {
        String exclude = "Home Loan".equals(productLabel) ? "Loan Against" : "";
        List<WebElement> els = driver.findElements(MobileWaits.xpath(productCardXpath(productLabel)));
        WebElement best = null;
        int bestArea = Integer.MAX_VALUE;
        for (WebElement el : els) {
            String desc = el.getAttribute("content-desc");
            if (desc == null) {
                desc = el.getAttribute("contentDescription");
            }
            if (desc == null || !desc.contains("Apply")) {
                continue;
            }
            if (!exclude.isBlank() && desc.contains(exclude)) {
                continue;
            }
            var r = el.getRect();
            int area = r.width * r.height;
            if (area > 0 && area < bestArea) {
                best = el;
                bestArea = area;
            }
        }
        return best;
    }

    /**
     * Personal Loan sits above the fold once the home list is scrolled.
     * Move the card until its Apply strip is between the header and the tab bar.
     */
    private boolean scrollCardIntoView(String cardXpath) {
        for (int i = 0; i < 8; i++) {
            try {
                List<WebElement> els = driver.findElements(MobileWaits.xpath(cardXpath));
                int screenH = driver.manage().window().getSize().getHeight();
                int topSafe = (int) (screenH * 0.20);
                int bottomSafe = (int) (screenH * 0.84);
                if (!els.isEmpty()) {
                    var r = els.get(0).getRect();
                    int applyY = r.y + Math.max((r.height * 88) / 100, r.height - 36);
                    if (applyY > topSafe && applyY < bottomSafe && r.y < bottomSafe) {
                        return true;
                    }
                    if (r.y < topSafe || applyY < topSafe) {
                        swipeDown();
                    } else {
                        swipeUp();
                    }
                } else if (i < 4) {
                    swipeDown();
                } else {
                    swipeUp();
                }
                sleep(400);
            } catch (Exception e) {
                swipeDown();
            }
        }
        return !driver.findElements(MobileWaits.xpath(cardXpath)).isEmpty();
    }

    private boolean tapApplyStrip(String productLabel, int attempt) {
        int[] xy = applyStripCoords(productLabel, attempt);
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

    private int[] applyStripCoords(String productLabel, int attempt) {
        try {
            WebElement card = productCard(productLabel);
            if (card == null) {
                return null;
            }
            var r = card.getRect();
            int x = r.x + r.width / 2;
            int ratio = attempt == 0 ? 88 : attempt == 1 ? 78 : 94;
            int y = r.y + Math.max((r.height * ratio) / 100, r.height - 28);
            int screenH = driver.manage().window().getSize().getHeight();
            int tabTop = (int) (screenH * 0.86);
            if (y >= tabTop) {
                y = Math.min(r.y + r.height - 12, tabTop - 8);
            }
            return new int[]{x, y};
        } catch (Exception e) {
            return null;
        }
    }

    private boolean leftHomeGridOrInApply(String beforeFingerprint) {
        if (sourceContains("Tell us about your work", "Select your occupation", "Salaried",
                "Your loan journey is in progress", "Resume Application", "offers available")) {
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
