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
                && !sourceContains("View all Mutual Funds", "Features of NPS", "Buy Gold", "Calculate Now");
    }

    public boolean open() {
        new HomeScreen(context).dismissHomeCoachIfShown();
        if (isDisplayed()) {
            return true;
        }
        if (!tapFirst(Duration.ofMillis(800),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Products']"),
                MobileWaits.xpath("//android.view.View[@content-desc='Products']"),
                accessibility("Products"))) {
            new FinSightHomeScreen(context).openProductsTab();
        }
        sleep(200);
        return isDisplayed();
    }

    public boolean openDigitalGold() {
        return openModule("Digital Gold");
    }

    public boolean openFixedDeposit() {
        return openModule("Fixed Deposit");
    }

    public boolean openMutualFunds() {
        return openModule("Mutual Funds");
    }

    public boolean openNps() {
        return openModule("NPS");
    }

    public boolean openModule(String label) {
        String quoted = xpathLiteral(label);
        for (int i = 0; i < 4; i++) {
            if (tapFirst(Duration.ofMillis(700),
                    MobileWaits.xpath("//android.view.View[@content-desc=" + quoted + "]/android.widget.ImageView"),
                    MobileWaits.xpath("//android.widget.ImageView[@content-desc=" + quoted + "]"),
                    MobileWaits.xpath("//android.view.View[@content-desc=" + quoted + "]"),
                    MobileWaits.xpath("//*[@clickable='true' and @content-desc=" + quoted + "]"))) {
                sleep(200);
                return true;
            }
            swipeUp();
            sleep(80);
        }
        return false;
    }
}
