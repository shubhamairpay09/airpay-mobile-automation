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
                || src.contains("Enter OTP") || src.contains("Verify OTP") || src.contains("One time password")
                || src.contains("Authentication required") || src.contains("Enable biometric")
                || src.contains("without enabling biometric") || src.contains("Use biometric")) {
            return false;
        }
        if (src.contains("Enter your details as per official records")
                || src.contains("Enter PAN")
                || src.contains("Tell us about your work")
                || src.contains("For Self-Employed")
                || src.contains("Terms and conditions")) {
            return false;
        }
        boolean homeGrid = src.contains("Personal Loan") && src.contains("Gold Loan")
                && src.contains("Tab 1 of 3");
        return src.contains("Tab 1 of 3")
                || src.contains("Dashboard")
                || src.contains("Your loan journey")
                || src.contains("Resume Application")
                || homeGrid;
    }

    public boolean journeyInProgress() {
        return sourceContains("Your loan journey is in progress");
    }

    public void openHome() {
        for (int i = 0; i < 8 && !productGridVisible(); i++) {
            if (sourceContains("Close Gender selection")) {
                tapCenter(MobileWaits.xpath("//*[@content-desc='Close Gender selection']"));
                sleep(300);
                continue;
            }
            if (sourceContains("Select date") && sourceContains("Cancel")) {
                tapCenter(MobileWaits.xpath("//android.widget.Button[@content-desc='Cancel']"));
                sleep(300);
                continue;
            }
            if (journeyInProgress()
                    || (sourceContains("Back") && !sourceContains("Tab 1 of 3"))) {
                if (!tapCenter(MobileWaits.xpath("//android.widget.Button[@content-desc='Back']"))
                        && !tapCenter(MobileWaits.xpath(
                                "//*[contains(@content-desc,'Back') and contains(@content-desc,'go back')]"))) {
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

    /**
     * Info → Settings → Logout → Yes. Lands on the welcome screen.
     * Login cases call this when a previous scenario left the app on Home.
     */
    public void logOut() {
        openHome();
        if (!tapCenter(MobileWaits.xpath("//android.widget.ImageView[contains(@content-desc,'Info')]"))) {
            throw new IllegalStateException("Info tab was not tappable");
        }
        if (!waitUntilSourceContains(Duration.ofSeconds(8), "Settings", "Information")) {
            throw new IllegalStateException("Info screen did not open");
        }
        if (!tapCenter(MobileWaits.xpath("//*[contains(@content-desc,'Settings')]"))) {
            throw new IllegalStateException("Settings was not tappable");
        }
        if (!waitUntilSourceContains(Duration.ofSeconds(8), "Logout")) {
            throw new IllegalStateException("Settings screen did not show Logout");
        }
        if (!tapCenter(MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc,'Logout')]"))) {
            throw new IllegalStateException("Logout was not tappable");
        }
        if (!waitUntilSourceContains(Duration.ofSeconds(6), "you want to logout", "Yes")) {
            throw new IllegalStateException("Logout confirmation did not appear");
        }
        if (!tapCenter(MobileWaits.xpath("//android.widget.Button[@content-desc='Yes']"))) {
            throw new IllegalStateException("Logout confirmation Yes was not tappable");
        }
        if (!waitUntilSourceContains(Duration.ofSeconds(15), "Login With Mobile Number", "Enter 10-Digit Number")) {
            throw new IllegalStateException("Welcome screen did not appear after logout");
        }
    }

    /**
     * Opens the product card and stops on the feature page when that page is shown.
     * Some products go straight to occupation or personal details.
     */
    public boolean openProductFeaturePage(String productLabel) {
        openHome();
        if (featurePageVisible() || applyEntryReached()) {
            return true;
        }
        if (!scrollCardIntoView(productLabel)) {
            return false;
        }
        for (int attempt = 0; attempt < 3; attempt++) {
            if (!tapApplyStrip(productLabel, attempt)) {
                continue;
            }
            waitUntilSourceContains(Duration.ofSeconds(8),
                    "Loan Amount", "Terms and conditions", "Tell us about your work",
                    "Enter PAN", "Your loan journey is in progress", "Resume Application");
            if (featurePageVisible() || applyEntryReached()) {
                return true;
            }
        }
        return featurePageVisible() || applyEntryReached();
    }

    /** Marketing page with the loan summary and a disabled Apply Now until terms are accepted. */
    public boolean featurePageVisible() {
        if (productGridVisible() || journeyInProgress()) {
            return false;
        }
        if (sourceContains("Tell us about your work")
                || sourceContains("Enter your details as per official records")) {
            return false;
        }
        return sourceContains("Terms and conditions")
                || (sourceContains("Loan Amount") && sourceContains("For Self-Employed"));
    }

    /** Tap product card Apply Now strip from home grid (Flutter-safe coordinate tap). */
    public boolean tapProductApplyNow(String productLabel) {
        openHome();
        if (!scrollCardIntoView(productLabel)) {
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
                openLandingApplyIfPresent();
                return !landingApplyBlocked() && applyEntryReached();
            }
        }
        if (leftHomeGridOrInApply(before)) {
            openLandingApplyIfPresent();
            return !landingApplyBlocked() && applyEntryReached();
        }
        return false;
    }

    /**
     * Home Loan and Loan Against Property open a feature page first.
     * Apply Now stays disabled until every "Terms and conditions" box is checked.
     * Occupation's Continue returns here; Apply Now then opens the application form.
     */
    public void finishFeaturePage() {
        openLandingApplyIfPresent();
    }

    private void openLandingApplyIfPresent() {
        if (!landingApplyBlocked() && applyEntryReached()) {
            return;
        }
        for (int i = 0; i < 8 && (landingApplyBlocked() || !applyEntryReached()); i++) {
            acceptLandingTerms();
            if (tapEnabledLandingApply()) {
                waitUntilSourceContains(Duration.ofSeconds(10),
                        "Tell us about your work", "Select your occupation",
                        "Enter your details as per official records", "Enter PAN",
                        "Your loan journey is in progress", "Resume Application");
                if (!landingApplyBlocked() && applyEntryReached()) {
                    return;
                }
            }
            swipeUp();
            sleep(400);
        }
    }

    /** Grey Apply Now on the feature page. It stays disabled until the terms boxes are checked. */
    private boolean landingApplyBlocked() {
        List<WebElement> els = driver.findElements(MobileWaits.xpath(
                "//android.widget.Button[@content-desc='Apply Now' or @content-desc='Apply now']"));
        for (WebElement el : els) {
            try {
                if (!el.isDisplayed() || el.getRect().height < 40) {
                    continue;
                }
                if (!"true".equalsIgnoreCase(String.valueOf(el.getAttribute("enabled")))) {
                    return true;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return false;
    }

    private boolean applyEntryReached() {
        if (sourceContains("Tell us about your work", "Select your occupation",
                "Enter your details as per official records", "Enter PAN", "Date of birth as per PAN",
                "Your loan journey is in progress", "Resume Application", "offers available")) {
            return true;
        }
        return !driver.findElements(MobileWaits.xpath(
                "//android.widget.RadioButton[contains(@content-desc,'Salaried')"
                        + " or contains(@content-desc,'Self-Employed')"
                        + " or contains(@content-desc,'Self Employed')]")).isEmpty();
    }

    private void acceptLandingTerms() {
        for (int n = 0; n < 4; n++) {
            List<WebElement> boxes = driver.findElements(MobileWaits.xpath(
                    "//android.widget.CheckBox[contains(@content-desc,'tap to accept')]"));
            if (boxes.isEmpty()) {
                return;
            }
            var r = boxes.get(0).getRect();
            tapAt(r.x + Math.min(48, Math.max(r.width / 2, 1)), r.y + Math.max(r.height / 2, 1));
            sleep(350);
        }
    }

    private boolean tapEnabledLandingApply() {
        List<WebElement> els = driver.findElements(MobileWaits.xpath(
                "//android.widget.Button[@content-desc='Apply Now' or @content-desc='Apply now']"));
        for (WebElement el : els) {
            try {
                if (!"true".equalsIgnoreCase(String.valueOf(el.getAttribute("enabled")))) {
                    continue;
                }
                var r = el.getRect();
                if (r.height < 40) {
                    continue;
                }
                int x = r.x + Math.max(r.width / 2, 1);
                int y = r.y + Math.max(r.height / 2, 1);
                tapAt(x, y);
                return true;
            } catch (Exception ignored) {
                // stale node after a checkbox tap
            }
        }
        return false;
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
     * A card clipped by the bottom of the list reports a short height (Home Loan was 43px)
     * and a tap there hits the title, not Apply Now. Scroll until the full card is on screen.
     */
    private boolean scrollCardIntoView(String productLabel) {
        for (int i = 0; i < 8; i++) {
            try {
                WebElement card = productCard(productLabel);
                int screenH = driver.manage().window().getSize().getHeight();
                int topSafe = (int) (screenH * 0.18);
                int bottomSafe = (int) (screenH * 0.88);
                if (card != null) {
                    var r = card.getRect();
                    int applyY = r.y + (r.height * 88) / 100;
                    if (r.height >= 180 && applyY > topSafe && applyY < bottomSafe) {
                        return true;
                    }
                    if (r.height < 180) {
                        if (r.y > screenH / 2) {
                            swipeUp();
                        } else {
                            swipeDown();
                        }
                    } else if (r.y < topSafe || applyY < topSafe) {
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
                swipeUp();
            }
        }
        WebElement card = productCard(productLabel);
        if (card == null) {
            return false;
        }
        return card.getRect().height >= 180;
    }

    private boolean tapApplyStrip(String productLabel, int attempt) {
        int[] xy = applyStripCoords(productLabel, attempt);
        if (xy == null) {
            return false;
        }
        try {
            tapAt(xy[0], xy[1]);
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
