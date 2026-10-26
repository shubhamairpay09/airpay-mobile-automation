package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

/**
 * Invested NPS dashboard. Sheet labels: Calculate Now, Details, Invest More, Your SIPs,
 * and the bottom tabs Dashboard, Portfolio, Transactions, Profile.
 * Explore NPS and KYC Start are not tapped. Invest More is opened only.
 */
public class NpsDashboardScreen extends BaseScreen {

    public NpsDashboardScreen(TestContext context) {
        super(context);
    }

    public boolean openFromHub() {
        ProductsHubScreen hub = new ProductsHubScreen(context);
        if (!hub.open()) {
            return false;
        }
        WebElement tile = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='NPS']"),
                MobileWaits.xpath("//*[@content-desc='NPS']"));
        if (tile == null) {
            return hub.openNps();
        }
        pressElement(tile);
        sleep(1200);
        return true;
    }

    public boolean optionDisplayed(String label) {
        return firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]")) != null;
    }

    /**
     * The sheet starts on the NPS screen. An invested account shows Calculate Now.
     * This account shows the landing: Features of NPS and Open NPS Account.
     * KYC and Explore NPS are not the screen these cases continue from.
     */
    public boolean dashboardDisplayed() {
        String src = text();
        if (kycGate(src)) {
            return false;
        }
        boolean invested = src.contains("Calculate Now") || src.contains("Invest More") || src.contains("Your SIPs");
        boolean landing = src.contains("Features of NPS") || src.contains("Open NPS Account");
        boolean tabs = src.contains("Dashboard") && src.contains("Portfolio") && src.contains("Transactions");
        return src.contains("NPS") && (invested || landing || tabs);
    }

    private boolean kycGate(String src) {
        boolean gate = src.contains("KYC Status") || src.contains("Explore NPS") || src.contains("Basic Information");
        boolean nps = src.contains("Features of NPS") || src.contains("Open NPS Account") || src.contains("Calculate Now");
        return gate && !nps;
    }

    public boolean blocked() {
        String src = text();
        return src.contains("KYC Status") || src.contains("Explore NPS") || src.contains("Basic Information");
    }

    public boolean pressCalculateNow() {
        return pressExact("Calculate Now");
    }

    public boolean calculatorDisplayed() {
        String src = text();
        return src.contains("NPS Calculator")
                && src.contains("Amount Invested Monthly (in INR)")
                && !src.contains("Calculate Now");
    }

    public boolean calculatorShowsMaturity() {
        return text().contains("Maturity Amount");
    }

    public boolean pressDetails() {
        return pressExact("Details");
    }

    /** Details page is the investment card, not the dashboard and not the calculator. */
    public boolean detailsDisplayed() {
        String src = text();
        return src.contains("NPS")
                && src.contains("Current Value")
                && src.contains("Invested")
                && !src.contains("Calculate Now")
                && !src.contains("Amount Invested Monthly (in INR)");
    }

    public boolean pressInvestMore() {
        return pressExact("Invest More");
    }

    /**
     * Contribution has started when the dashboard is gone and the next screen asks
     * for an amount or shows One Time. Payment, UPI, and card entry are not opened.
     */
    public boolean contributionDisplayed() {
        String src = text();
        if (src.contains("Calculate Now") || src.contains("KYC Status") || src.contains("Explore NPS")) {
            return false;
        }
        boolean amount = src.contains("Amount");
        boolean oneTime = src.contains("One Time");
        boolean nps = src.contains("NPS") || src.contains("Invest") || src.contains("Contribut");
        return nps && (amount || oneTime);
    }

    public boolean paymentClosed() {
        String src = text().toLowerCase();
        return !src.contains("card number")
                && !src.contains("enter upi")
                && !src.contains("proceed to pay")
                && !src.contains("make payment");
    }

    public boolean pressYourSips() {
        return pressExact("Your SIPs");
    }

    public boolean sipListDisplayed() {
        WebElement title = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[@content-desc='Your SIPs']"));
        if (title == null || title.getRect().getY() > 450) {
            return false;
        }
        String src = text();
        boolean list = src.contains("Active") || src.contains("No SIP") || src.contains("₹");
        return list && !src.contains("Calculate Now");
    }

    public boolean pressTab(String label) {
        WebElement tab = tabButton(label);
        if (tab == null) {
            for (int i = 0; i < 3 && tab == null; i++) {
                tapHeaderBack();
                sleep(600);
                tab = tabButton(label);
            }
        }
        if (tab == null) {
            return false;
        }
        pressElement(tab);
        sleep(900);
        return true;
    }

    private WebElement tabButton(String label) {
        return firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.Button[@content-desc=" + xpathLiteral(label) + "]"),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]"));
    }

    public boolean tabHighlighted(String label) {
        WebElement tab = tabButton(label);
        if (tab != null) {
            String selected = String.valueOf(tab.getAttribute("selected"));
            String checked = String.valueOf(tab.getAttribute("checked"));
            if ("true".equalsIgnoreCase(selected) || "true".equalsIgnoreCase(checked)) {
                return true;
            }
        }
        return sectionOpen(label);
    }

    /** This build opens Portfolio, Transactions, and Profile as their own screens. */
    private boolean sectionOpen(String label) {
        String src = text();
        return switch (label) {
            case "Dashboard" -> src.contains("Features of NPS") || src.contains("Calculate Now");
            case "Portfolio" -> src.contains("My Portfolio") || src.contains("No Portfolio Created");
            case "Transactions" -> src.contains("Transaction History");
            case "Profile" -> src.contains("Investor Profile") || src.contains("No Investor Profile created");
            default -> false;
        };
    }

    public boolean dashboardTabDisplayed() {
        String src = text();
        boolean invested = src.contains("NPS") && src.contains("Calculate Now") && src.contains("Invest More");
        boolean landing = src.contains("NPS") && src.contains("Features of NPS") && src.contains("Open NPS Account");
        return invested || landing;
    }

    public boolean portfolioDisplayed() {
        String src = text();
        boolean empty = src.contains("My Portfolio") && src.contains("No Portfolio Created");
        boolean invested = src.contains("Portfolio") && src.contains("Current Value");
        return empty || invested;
    }

    public boolean transactionsTabDisplayed() {
        return titleNearTop("Transaction History") || titleNearTop("Transactions History");
    }

    public boolean profileDisplayed() {
        String src = text();
        return src.contains("Investor Profile") || src.contains("No Investor Profile created")
                || titleNearTop("Profile");
    }

    public void goBack() {
        tapHeaderBack();
        sleep(800);
    }

    public boolean productsHubDisplayed() {
        ProductsHubScreen hub = new ProductsHubScreen(context);
        String src = text();
        return hub.isDisplayed() && !src.contains("Calculate Now") && !src.contains("Invest More");
    }

    public boolean openTransactionHistory() {
        NpsTransactionsScreen history = new NpsTransactionsScreen(context);
        if (history.historyTitleDisplayed()) {
            return true;
        }
        if (pressExact("Transaction History") && history.historyTitleDisplayed()) {
            return true;
        }
        return pressTab("Transactions") && history.historyTitleDisplayed();
    }

    private boolean titleNearTop(String label) {
        WebElement title = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]"));
        return title != null && title.getRect().getY() < 500;
    }

    private boolean pressExact(String label) {
        WebElement target = firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]"));
        if (target == null) {
            return false;
        }
        pressElement(target);
        sleep(800);
        return true;
    }

    private void scrollToTop() {
        for (int i = 0; i < 4; i++) {
            swipeDown();
        }
        sleep(300);
    }

    private void swipeDown() {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.28);
        int endY = (int) (size.height * 0.78);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
        sleep(400);
    }

    private String text() {
        return pageSource()
                .replace("&#10;", "\n")
                .replace("&amp;", "&")
                .replace("&gt;", ">")
                .replace("&lt;", "<");
    }
}
