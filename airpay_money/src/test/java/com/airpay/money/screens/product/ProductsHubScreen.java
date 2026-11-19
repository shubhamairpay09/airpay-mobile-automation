package com.airpay.money.screens.product;

import com.airpay.money.screens.finsight.FinSightHomeScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Products tab hub on 2.1.3 — Investments + Insurance tiles.
 * Dump {@code uidump-gold-03-products-hub} 2026-09-25.
 */
public class ProductsHubScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Open settings']")
    private WebElement openSettings;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Hello']")
    private WebElement hello;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='Notifications']")
    private WebElement notifications;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Menu option']")
    private WebElement menuOption;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Investments']")
    private WebElement investmentsHeading;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Mutual Funds']")
    private WebElement mutualFunds;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='NPS']")
    private WebElement nps;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Digital Gold']")
    private WebElement digitalGold;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Fixed Deposit']")
    private WebElement fixedDeposit;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='US Stocks']")
    private WebElement usStocks;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Indian Stocks']")
    private WebElement indianStocks;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Insurance']")
    private WebElement insuranceHeading;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='2 Wheeler']")
    private WebElement twoWheeler;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='4 Wheeler']")
    private WebElement fourWheeler;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Health']")
    private WebElement health;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Life']")
    private WebElement life;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Gold Loan']")
    private WebElement goldLoan;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Credit Cards']")
    private WebElement creditCards;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Personal Loan']")
    private WebElement personalLoan;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Home Loan']")
    private WebElement homeLoan;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Loan Against Property']")
    private WebElement loanAgainstProperty;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Loans & Credit Cards']")
    private WebElement loansAndCreditCards;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Personal Finance']")
    private WebElement personalFinance;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Tax Filing & Planning']")
    private WebElement taxFiling;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Cleartax')]")
    private WebElement clearTaxCard;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Start Now']")
    private WebElement startNow;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Life']/android.widget.ImageView")
    private WebElement lifeIcon;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Gold Loan']/android.widget.ImageView")
    private WebElement goldLoanIcon;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Credit Cards']/android.widget.ImageView")
    private WebElement creditCardsIcon;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Personal Loan']/android.widget.ImageView")
    private WebElement personalLoanIcon;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Home Loan']/android.widget.ImageView")
    private WebElement homeLoanIcon;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Loan Against Property']/android.widget.ImageView")
    private WebElement loanAgainstPropertyIcon;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Products']")
    private WebElement tabProducts;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='FinSight']")
    private WebElement tabFinsight;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Calculators']")
    private WebElement tabCalculators;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Go to slide 1 of 3']")
    private WebElement slide1;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Go to slide 2 of 3']")
    private WebElement slide2;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Go to slide 3 of 3']")
    private WebElement slide3;

    public ProductsHubScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        // Investments section + at least one other investment tile (not MF interior alone).
        return sourceContains("Investments")
                && sourceContains("Digital Gold", "NPS", "Fixed Deposit")
                && !sourceContains("View all Mutual Funds", "Features of NPS", "Calculate Now")
                && !onDigitalGoldDashboard()
                && !onFixedDepositLanding();
    }

    private boolean onDigitalGoldDashboard() {
        return sourceContains("Digital Gold") && sourceContains("Buy")
                && sourceContains("Locker", "Gold Coins", "Dashboard");
    }

    private boolean onFixedDepositLanding() {
        return sourceContains("Discover Fixed Deposits")
                || (sourceContains("Popular FDs") && sourceContains("Senior Citizen"));
    }

    public boolean open() {
        if (isDisplayed()) {
            return true;
        }
        String udid = context.config().get("android.udid", "");
        com.airpay.mobile.utilities.android.AdbInput.tapBottomNav(udid, 0, 3);
        if (waitUntil(this::isDisplayed, Duration.ofSeconds(4))) {
            return true;
        }
        ensureInvestmentsSectionVisible();
        return waitUntil(this::isDisplayed, actionTimeout());
    }

    public boolean openDigitalGold() {
        ensureInvestmentsSectionVisible();
        if (!tapProductTile("Digital Gold")) {
            return false;
        }
        DigitalGoldDashboardScreen dashboard = new DigitalGoldDashboardScreen(context);
        DigitalGoldIntroScreen intro = new DigitalGoldIntroScreen(context);
        Duration navWait = Duration.ofSeconds(10);
        if (!waitUntil(() -> dashboard.isDisplayed() || intro.isDisplayed(), navWait)) {
            if (!tapProductTile("Digital Gold")) {
                return false;
            }
            waitUntil(() -> dashboard.isDisplayed() || intro.isDisplayed(), navWait);
        }
        if (intro.isDisplayed()) {
            intro.completeIfShown();
        }
        return waitUntil(dashboard::isDisplayed, Duration.ofSeconds(6));
    }

    public boolean openFixedDeposit() {
        ensureInvestmentsSectionVisible();
        if (!tapProductTile("Fixed Deposit")) {
            return false;
        }
        FixedDepositsLandingScreen landing = new FixedDepositsLandingScreen(context);
        Duration navWait = Duration.ofSeconds(10);
        if (!waitUntil(landing::isDisplayed, navWait)) {
            ensureInvestmentsSectionVisible();
            if (!tapProductTile("Fixed Deposit")) {
                return false;
            }
            waitUntil(landing::isDisplayed, navWait);
        }
        return landing.isDisplayed();
    }

    public boolean openMutualFunds() {
        for (int i = 0; i < 6; i++) {
            String src = pageSource();
            if (src.contains("Mutual Funds") && src.contains("Investments")) {
                break;
            }
            swipeDown();
        }
        return openModule("Mutual Funds");
    }

    public boolean openNps() {
        return openModule("NPS");
    }

    public boolean openModule(String label) {
        if (isInvestmentTile(label)) {
            ensureInvestmentsSectionVisible();
        }
        if (pageSource().contains(label) && tapProductTile(label)) {
            return true;
        }
        if (!pageSource().contains(label)) {
            scrollIntoDescription(label);
        }
        if (tapProductTile(label)) {
            return true;
        }
        boolean investment = isInvestmentTile(label);
        for (int i = 0; i < 4; i++) {
            if (investment) {
                swipeDown();
            } else {
                swipeUp();
            }
            sleep(100);
            if (pageSource().contains(label) && tapProductTile(label)) {
                return true;
            }
        }
        return false;
    }

    /** FD / Gold / NPS / MF sit under Investments at the top — never scroll down to Insurance first. */
    private static boolean isInvestmentTile(String label) {
        return "Fixed Deposit".equals(label)
                || "Digital Gold".equals(label)
                || "NPS".equals(label)
                || "Mutual Funds".equals(label);
    }

    /** Pull the hub back so Investments and the first tile row are on screen. */
    public void ensureInvestmentsSectionVisible() {
        if (sourceContains("Investments") && sourceContains("Fixed Deposit", "Digital Gold", "Mutual Funds")) {
            return;
        }
        for (int i = 0; i < 5; i++) {
            swipeDown();
            sleep(120);
            if (sourceContains("Investments")
                    && sourceContains("Fixed Deposit", "Digital Gold", "Mutual Funds", "NPS")) {
                return;
            }
        }
    }

    /**
     * Hub tiles: tap the icon (top ~25%), not the dead centre — same pattern as Calculators grid.
     */
    private boolean tapProductTile(String label) {
        String quoted = xpathLiteral(label);
        if (!pageSource().contains(label)) {
            scrollIntoDescription(label);
        }
        org.openqa.selenium.WebElement tile = firstPresent(Duration.ofMillis(800),
                MobileWaits.xpath("//android.view.View[@content-desc=" + quoted + "]"),
                MobileWaits.xpath("//android.view.View[contains(@content-desc," + quoted + ")]"));
        if (tile != null) {
            try {
                var rect = tile.getRect();
                int x = rect.getX() + Math.max(8, rect.getWidth() / 2);
                int iconY = rect.getY() + Math.max(12, (int) (rect.getHeight() * 0.22));
                shellPress(x, iconY);
                sleep(350);
                return true;
            } catch (Exception ignored) {
                // fall through to xpath taps
            }
        }
        return tapFirst(Duration.ofMillis(600),
                MobileWaits.xpath("//android.view.View[contains(@content-desc," + quoted + ")]/android.widget.ImageView"),
                MobileWaits.xpath("//android.widget.ImageView[contains(@content-desc," + quoted + ")]"),
                MobileWaits.xpath("//android.view.View[contains(@content-desc," + quoted + ")]"),
                MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc," + quoted + ")]"));
    }
}
