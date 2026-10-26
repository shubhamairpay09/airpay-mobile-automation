package com.airpay.money.screens.home;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends BaseScreen {
    private static final String OPEN_SETTINGS = "//android.widget.Button[@content-desc='Open settings']";
    private static final String HELLO = "//android.view.View[@content-desc='Hello']";
    private static final String MENU_OPTION = "//android.widget.Button[@content-desc='Menu option']";
    private static final String NOTIFICATIONS = "//android.widget.ImageView[@content-desc='Notifications']";


    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Open settings']")
    private WebElement openSettings;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Hello']")
    private WebElement hello;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Notifications']")
    private WebElement notifications;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Products']")
    private WebElement tabProducts;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='FinSight']")
    private WebElement tabFinsight;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Calculators']")
    private WebElement tabCalculators;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Mutual Funds']")
    private WebElement mutualFunds;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Digital Gold']")
    private WebElement digitalGold;

    public HomeScreen(TestContext context) {
        super(context);
    }

    public void dismissHomeCoachIfShown() {
        for (int i = 0; i < 5; i++) {
            if (!sourceContains("Supercharge your savings", "Go to slide",
                    "Your go-to for financial", "Get notified about important events")) {
                break;
            }
            // Tap the banner, not the product row under the coach copy.
            tapAt(540, 360);
            sleep(450);
        }
    }

    public boolean looksLikeHomeHub() {
        return sourceContains("Investments") && sourceContains("Mutual Funds") && sourceContains("NPS");
    }

    public boolean looksLikeMutualFundsLanding() {
        if (looksLikeHomeHub()) {
            return false;
        }
        return sourceContains("Start eKYC", "Create Goal", "View all Mutual Funds")
                || (sourceContains("Mutual Funds") && sourceContains("Back"));
    }

    public boolean openMutualFunds() {
        if (looksLikeMutualFundsLanding()) {
            return true;
        }
        for (int i = 0; i < 6 && !looksLikeHomeHub(); i++) {
            tapBack();
            sleep(350);
        }
        dismissHomeCoachIfShown();
        if (!tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Mutual Funds']"),
                accessibility("Mutual Funds"))) {
            tapAt(217, 1238);
        }
        return waitForMutualFundsLanding();
    }

    private boolean waitForMutualFundsLanding() {
        long end = System.currentTimeMillis() + 12_000;
        while (System.currentTimeMillis() < end) {
            if (looksLikeMutualFundsLanding()) {
                return true;
            }
            sleep(400);
        }
        return looksLikeMutualFundsLanding();
    }

    public boolean looksLoggedIn() {
        String src = safeSource();
        if (src.contains("Login with Mobile") || src.contains("Login With Mobile")
                || src.contains("Continue with Mobile")
                || src.contains("Enter mobile number") || src.contains("Enter 10-Digit")
                || src.contains("Send OTP")
                || src.contains("Get Started") || src.contains("Accept consent")
                || src.contains("Sign Up or Login to Continue")) {
            return false;
        }
        return sourceContains(
                "Mutual Fund", "Dashboard", "Portfolio", "Invest", "Net Worth",
                "Financial Health", "Your Financial Health Score", "Home", "Loans",
                "Insurance", "More", "FinSight");
    }

    public void openTab(String... labels) {
        for (String label : labels) {
            if (tapFirst(Duration.ofSeconds(4),
                    MobileWaits.xpath("//android.widget.ImageView[contains(@content-desc," + xpathLiteral(label) + ")]"),
                    MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc," + xpathLiteral(label) + ")]"),
                    accessibility(label))) {
                sleep(500);
                return;
            }
        }
        tapLabel(labels[0]);
        sleep(500);
    }

    public void openHomeTab() {
        openTab("Home", "Dashboard", "FinSight");
    }

    public void openProductsTab() {
        dismissHomeCoachIfShown();
        tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Products']"),
                accessibility("Products"));
        sleep(600);
    }

    public void openInvestTab() {
        openTab("Invest", "Mutual Fund", "Mutual Funds");
    }

    public void openPortfolioTab() {
        openTab("Portfolio", "My Portfolio");
    }

    public void openLoansTab() {
        openTab("Loans", "Loan", "Credit");
    }

    public void openMoreTab() {
        openTab("More", "Menu");
    }

    public List<String> visibleProductTiles() {
        List<String> tiles = new ArrayList<>();
        String[] keywords = {
                "Mutual Fund", "Digital Gold", "Gold", "Fixed Deposit", "FD", "NPS",
                "Insurance", "Tax", "Financial Health", "Net Worth", "Loan", "Credit Card", "SIP"
        };
        String src = safeSource();
        for (String k : keywords) {
            if (src.contains(k)) {
                tiles.add(k);
            }
        }
        return tiles;
    }

    public List<String> allClickableLabels() {
        List<String> labels = new ArrayList<>();
        List<org.openqa.selenium.WebElement> nodes;
        try {
            nodes = driver.findElements(MobileWaits.xpath(
                    "//*[@clickable='true' and string-length(@content-desc)>0]"
                            + " | //android.widget.ImageView[string-length(@content-desc)>0]"));
        } catch (Exception e) {
            return labels;
        }
        for (org.openqa.selenium.WebElement n : nodes) {
            try {
                String desc = n.getAttribute("contentDescription");
                if (desc == null || desc.isBlank()) {
                    continue;
                }
                String first = desc.split("\n")[0].trim();
                if (first.isBlank() || first.length() > 80) {
                    continue;
                }
                if (!labels.contains(first)) {
                    labels.add(first);
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return labels;
    }

    public boolean tapProductTile(String label) {
        if (tapFirst(Duration.ofSeconds(5),
                MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc," + xpathLiteral(label) + ")]"),
                accessibility(label))) {
            sleep(800);
            return true;
        }
        for (int i = 0; i < 4; i++) {
            swipeUp();
            sleep(300);
            if (tapFirst(Duration.ofSeconds(3),
                    MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc," + xpathLiteral(label) + ")]"))) {
                sleep(800);
                return true;
            }
        }
        return false;
    }
}
