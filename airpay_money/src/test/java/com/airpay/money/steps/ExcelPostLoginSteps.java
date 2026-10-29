package com.airpay.money.steps;

import com.airpay.money.data.MoneyExcel;
import com.airpay.money.screens.auth.BiometricScreen;
import com.airpay.money.screens.auth.PinUnlockScreen;
import com.airpay.money.screens.finsight.FinSightHomeScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.money.screens.product.DigitalGoldBuyScreen;
import com.airpay.money.screens.product.DigitalGoldDashboardScreen;
import com.airpay.money.screens.product.DigitalGoldSellScreen;
import com.airpay.money.screens.product.FdPlanBookingScreen;
import com.airpay.money.screens.product.FixedDepositsLandingScreen;
import com.airpay.money.screens.product.ProductsHubScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;

import java.time.Duration;

public class ExcelPostLoginSteps {

    private static final Logger LOG = LogManager.getLogger(ExcelPostLoginSteps.class);

    private final HomeScreen home;
    private final FinSightHomeScreen finsight;
    private final ProductsHubScreen products;
    private final DigitalGoldDashboardScreen gold;
    private final DigitalGoldBuyScreen buy;
    private final DigitalGoldSellScreen sell;
    private final FixedDepositsLandingScreen fixedDeposit;
    private final FdPlanBookingScreen fdPlan;
    private final PinUnlockScreen pinUnlock;
    private final BiometricScreen biometric;

    public ExcelPostLoginSteps(TestContext context) {
        this.home = new HomeScreen(context);
        this.finsight = new FinSightHomeScreen(context);
        this.products = new ProductsHubScreen(context);
        this.gold = new DigitalGoldDashboardScreen(context);
        this.buy = new DigitalGoldBuyScreen(context);
        this.sell = new DigitalGoldSellScreen(context);
        this.fixedDeposit = new FixedDepositsLandingScreen(context);
        this.fdPlan = new FdPlanBookingScreen(context);
        this.pinUnlock = new PinUnlockScreen(context);
        this.biometric = new BiometricScreen(context);
    }

    @Given("the logged-in airpay money home screen is open")
    public void loggedInHome() {
        home.dismissHomeCoachIfShown();
        unlockPinAndSkipBiometric();
        Assertions.assertThat(onPinOrLogin())
                .as("PIN from the Login sheet should unlock the session")
                .isFalse();
        if (insideHealthCheck()) {
            return;
        }
        leaveSideMenu();
        if (insideHealthCheck()) {
            return;
        }
        // Prior scenario may end on Airpay / Order Summary / Buy — leave before asserting home.
        leaveProductNestedScreens();
        ensureProductsHub();
        if (!products.isDisplayed()) {
            openFinsight();
        }
        unlockPinAndSkipBiometric();
        Assertions.assertThat(onPinOrLogin())
                .as("PIN from the Login sheet should unlock the session")
                .isFalse();
        Assertions.assertThat(products.isDisplayed() || finsight.isDisplayed() || home.looksLoggedIn())
                .as("Expected the logged-in home dashboard or products hub")
                .isTrue();
    }

    @Then("the home dashboard is displayed")
    public void homeDisplayed() {
        openFinsight();
        Assertions.assertThat(finsight.isDisplayed() || home.sourceContains("Hello", "Your Financial Health Score"))
                .as("Home dashboard contents should be displayed")
                .isTrue();
    }

    @Then("the welcome greeting shows the logged-in user name")
    public void welcomeName() {
        Assertions.assertThat(home.sourceContains("Hello"))
                .as("Welcome greeting Hello should be displayed")
                .isTrue();
        Assertions.assertThat(home.pageSource())
                .as("Registered user name should be displayed next to the welcome greeting")
                .containsPattern("Hello[\\s\\S]{0,80}[A-Za-z]{2,}");
    }

    @Then("the Financial Health Check card is displayed")
    public void healthCard() {
        scrollHomeTop();
        Assertions.assertThat(home.sourceContains(
                        "Your Financial Health Score", "Financial Health",
                        "Let's start", "Let's Start", "Reassess", "View Details"))
                .as("Financial Health Check card should be displayed")
                .isTrue();
    }

    @Then("the Sync Your Net Worth card is displayed")
    public void syncCard() {
        scrollHomeTop();
        swipeUntil("Net Worth", "Accounts Synced", "Sync Your Net Worth", "Actual Net Worth");
        Assertions.assertThat(home.sourceContains(
                        "Sync Your Net Worth", "Accounts Synced", "View Net Worth", "Actual Net Worth"))
                .as("Sync Your Net Worth card, or the already synced net worth card, should be displayed")
                .isTrue();
    }

    @Then("the bottom navigation shows Products, FinSight, and Calculators")
    public void bottomNav() {
        String src = home.pageSource();
        Assertions.assertThat(src).as("Products tab should be displayed").contains("Products");
        Assertions.assertThat(src).as("FinSight tab should be displayed").contains("FinSight");
        Assertions.assertThat(src).as("Calculators tab should be displayed").contains("Calculators");
    }

    @Then("the notification icon is displayed on the dashboard")
    public void notifications() {
        Assertions.assertThat(home.sourceContains("Notifications"))
                .as("Notification icon should be visible in the header")
                .isTrue();
    }

    @When("I open Financial Health Check from the dashboard")
    public void openHealth() {
        openFinsight();
        if (finsight.tapLetsStart()) {
            return;
        }
        finsight.tapLabel("View Details");
        finsight.tapLabel("Reassess");
    }

    @Then("the Financial Health Check journey or the completed score is shown")
    public void healthOpened() {
        Assertions.assertThat(home.sourceContains(
                        "Personal Details", "Full Name", "Your Financial Health Score",
                        "Financial Health", "Let's start", "Reassess"))
                .as("Financial Health Check journey or the completed score should be shown. KYC is not started.")
                .isTrue();
    }

    @When("I open the net worth card")
    public void tapSync() {
        openFinsight();
        scrollHomeTop();
        swipeUntil("View Net Worth", "Accounts Synced", "Sync Your Net Worth", "Actual Net Worth");
        boolean opened = finsight.tapLabel("Sync Your Net Worth")
                || home.tapLabel("View Net Worth")
                || home.tapLabel("Accounts Synced")
                || home.tapLabel("Actual Net Worth");
        Assertions.assertThat(opened)
                .as("Sync Your Net Worth, or the already synced View Net Worth card, should open")
                .isTrue();
    }

    @Then("synced net worth or the consent screen is displayed")
    public void syncConsent() {
        boolean synced = false;
        boolean consent = false;
        long end = System.currentTimeMillis() + 8_000;
        while (System.currentTimeMillis() < end && !synced && !consent) {
            synced = home.sourceContains("Your Net Worth", "Total assets", "Assets");
            consent = home.sourceContains("consent", "Consent", "Fetch", "Allow");
            if (!synced && !consent) {
                sleepQuiet(400);
            }
        }
        Assertions.assertThat(synced || consent)
                .as("Opening the card should show synced net worth, or the consent screen before any new account is linked")
                .isTrue();
        if (synced) {
            Assertions.assertThat(home.sourceContains("Assets", "Liabilities"))
                    .as("Synced net worth should show Assets and Liabilities")
                    .isTrue();
        }
    }

    @And("I leave without linking a new account")
    public void leaveSync() {
        home.tapBack();
        openFinsight();
        Assertions.assertThat(finsight.isDisplayed() || home.looksLoggedIn())
                .as("Leaving net worth should return to the home dashboard without linking a new account")
                .isTrue();
    }

    @And("I return to the home dashboard")
    public void returnHome() {
        for (int i = 0; i < 4 && !finsight.isDisplayed(); i++) {
            home.tapBack();
            finsight.tapLabel("FinSight");
        }
        Assertions.assertThat(finsight.isDisplayed() || home.looksLoggedIn())
                .as("Home dashboard should be displayed again")
                .isTrue();
    }

    @When("I open Digital Gold from the products hub")
    public void openGold() {
        ensureProductsHub();
        Assertions.assertThat(products.open())
                .as("Products hub should open")
                .isTrue();
        Assertions.assertThat(products.openDigitalGold())
                .as("Digital Gold tile should open the dashboard")
                .isTrue();
        passGoldIntro();
    }

    @Then("the Digital Gold dashboard is displayed")
    public void goldDashboard() {
        Assertions.assertThat(gold.isDisplayed())
                .as("Digital Gold dashboard should show Buy and the locker or coins section")
                .isTrue();
    }

    @When("I tap Buy on the Digital Gold dashboard")
    public void tapBuy() {
        gold.tapBuy();
    }

    @Then("the Buy Gold and Silver screen is displayed")
    public void buyDisplayed() {
        Assertions.assertThat(buy.isDisplayed())
                .as("Buy Gold and Silver screen should be displayed")
                .isTrue();
    }

    @Then("the Buy Gold and Silver screen shows Gold selected with live rate, investment type, rupees, grams, amount, links, and Proceed off")
    public void buyFields() {
        Assertions.assertThat(buy.isDisplayed()).as("Buy Gold and Silver screen should be displayed").isTrue();
        String src = buy.pageSource();
        Assertions.assertThat(src).as("Gold tab should be available").contains("Gold");
        Assertions.assertThat(src).as("Silver tab should be available").contains("Silver");
        Assertions.assertThat(src).as("Investment type should be displayed").contains("Investment type");
        Assertions.assertThat(src).as("Buy in rupees should be displayed").contains("Buy in rupees");
        Assertions.assertThat(src).as("Buy in grams should be displayed").contains("Buy in grams");
        Assertions.assertThat(src).as("Live rate should show a rupee price").contains("₹");
        Assertions.assertThat(src.toLowerCase()).as("Live rate should be displayed").contains("live");
        Assertions.assertThat(src).as("Amount entry field should be displayed").contains("EditText");
        Assertions.assertThat(src).as("Privacy Policy link should be visible").contains("Privacy Policy");
        Assertions.assertThat(src).as("Terms and Conditions link should be visible").contains("Terms");
        Assertions.assertThat(buy.proceedEnabled())
                .as("Proceed should be disabled before a valid amount and consent")
                .isFalse();
    }

    @And("purchase options are displayed on the buy screen")
    public void purchaseOptions() {
        String src = buy.pageSource();
        Assertions.assertThat(src).as("Investment type should be displayed").contains("Investment type");
        Assertions.assertThat(src).as("Buy in rupees should be displayed").contains("Buy in rupees");
        Assertions.assertThat(src).as("Buy in grams should be displayed").contains("Buy in grams");
    }

    @When("I buy in rupees and enter 100")
    public void buyRupees() {
        buy.chooseBuyInRupees();
        buy.enterAmount(MoneyExcel.text("BuyRupees", "100"));
    }

    @Then("the rupee amount is accepted and a gram quantity is calculated")
    public void rupeeAccepted() {
        Assertions.assertThat(buy.pageSource())
                .as("Entered rupee amount should be accepted with no validation error")
                .contains(MoneyExcel.text("BuyRupees", "100"));
        String src = buy.pageSource().toLowerCase();
        Assertions.assertThat(src.contains("gm") || src.contains("gram"))
                .as("Corresponding gold quantity in grams should be calculated")
                .isTrue();
        Assertions.assertThat(buy.sourceContains("minimum", "Minimum", "at least"))
                .as("No minimum-amount validation should be shown for 100 rupees")
                .isFalse();
    }

    @When("I buy in grams and enter a gram chip")
    public void buyGrams() {
        buy.chooseBuyInGrams();
        buy.tapGramChip(MoneyExcel.text("Grams", "1.0"));
    }

    @Then("the gram quantity is accepted and a rupee amount is calculated")
    public void gramsAccepted() {
        String src = buy.pageSource();
        Assertions.assertThat(src).as("The entered gram quantity should be displayed")
                .contains(MoneyExcel.text("Grams", "1.0"));
        Assertions.assertThat(src).as("The equivalent rupee amount should be calculated").contains("₹");
        Assertions.assertThat(src.toLowerCase())
                .as("The rupee amount should be calculated from the live rate")
                .contains("live");
    }

    @When("I select and clear the buy consent checkbox")
    public void toggleConsent() {
        boolean before = buy.consentChecked();
        buy.acceptConsent();
        Assertions.assertThat(buy.consentChecked())
                .as("The consent checkbox should become selected")
                .isNotEqualTo(before);
        buy.acceptConsent();
        Assertions.assertThat(buy.consentChecked())
                .as("The consent checkbox should be cleared again")
                .isEqualTo(before);
    }

    @Then("the buy consent checkbox changes state")
    public void consentChanged() {
        Assertions.assertThat(buy.consentChecked())
                .as("Consent should be cleared after it is selected and tapped again")
                .isFalse();
    }

    @When("I enter 100 rupees and accept buy consent")
    public void amountAndConsent() {
        buy.chooseBuyInRupees();
        buy.enterAmount(MoneyExcel.text("BuyRupees", "100"));
        buy.acceptConsent();
    }

    @Then("Proceed becomes enabled on the buy screen")
    public void proceedOn() {
        buy.revealBottomActions();
        Assertions.assertThat(buy.proceedEnabled())
                .as("Proceed should become enabled after a valid amount and consent. Payment is not opened.")
                .isTrue();
    }

    @And("I go back to the Digital Gold dashboard")
    public void backToGold() {
        for (int i = 0; i < 3 && !gold.isDisplayed(); i++) {
            gold.tapBack();
        }
        Assertions.assertThat(gold.isDisplayed())
                .as("Back should return to the Digital Gold dashboard")
                .isTrue();
    }

    @When("I tap Sell on the Digital Gold dashboard")
    public void tapSellDash() {
        gold.tapSell();
    }

    @Then("the Sell Gold and Silver screen is displayed")
    public void sellDisplayed() {
        Assertions.assertThat(sell.isDisplayed())
                .as("Sell Gold and Silver screen should be displayed with the available balance")
                .isTrue();
    }

    @And("the available balance for selling is displayed")
    public void sellingBalance() {
        String src = sell.pageSource();
        Assertions.assertThat(src.contains("Available for selling") || src.contains("Total available value"))
                .as("Available balance for selling should be displayed")
                .isTrue();
        Assertions.assertThat(src).as("The selling balance should include an amount").contains("₹");
    }

    @When("I open Fixed Deposit from the products hub")
    public void openFd() {
        ensureProductsHub();
        Assertions.assertThat(products.open()).as("Products hub should open").isTrue();
        Assertions.assertThat(products.openFixedDeposit())
                .as("Fixed Deposit tile should open the dashboard")
                .isTrue();
    }

    @When("I open the Suryoday Small Finance Bank FD card")
    public void openSuryoday() {
        Assertions.assertThat(fixedDeposit.openPlan("Suryoday"))
                .as("Suryoday Small Finance Bank card should open")
                .isTrue();
    }

    @Then("the selected FD provider details are displayed")
    public void suryodayDetails() {
        Assertions.assertThat(fixedDeposit.providerDetailsVisible() || fdPlan.isDisplayed())
                .as("The selected FD provider details page should open. Book FD is not tapped.")
                .isTrue();
        String src = fdPlan.pageSource();
        Assertions.assertThat(src).as("Bank name should be displayed").contains("Suryoday");
        Assertions.assertThat(src).as("Investment amount should be displayed").containsIgnoringCase("Invest");
        Assertions.assertThat(src).as("FD tenure options should be displayed").contains("Select goal tenure");
        Assertions.assertThat(src).as("Interest Payout dropdown should be displayed").contains("Interest Payout");
        Assertions.assertThat(src).as("Renewal Preference dropdown should be displayed").contains("Renewal");
        Assertions.assertThat(src).as("Maturity Amount should be displayed").contains("Maturity");
        Assertions.assertThat(src).as("Book FD should be displayed and not tapped").contains("Book FD");
    }

    @And("I go back to the Fixed Deposit dashboard")
    public void backFd() {
        for (int i = 0; i < 3 && !fixedDeposit.isDisplayed(); i++) {
            fixedDeposit.tapBack();
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Assertions.assertThat(fixedDeposit.isDisplayed())
                .as("Back should return to the Fixed Deposit dashboard")
                .isTrue();
    }

    @When("I turn Senior Citizen rates on")
    public void seniorOn() {
        if (!fixedDeposit.isSeniorCitizenOn()) {
            fixedDeposit.toggleSeniorCitizen();
        }
    }

    @Then("senior citizen FD rates are displayed")
    public void seniorRates() {
        Assertions.assertThat(fixedDeposit.isSeniorCitizenOn())
                .as("Senior Citizen toggle should be on")
                .isTrue();
        Assertions.assertThat(fixedDeposit.cardShows("Suryoday", MoneyExcel.text("SeniorRate", "8.5")))
                .as("Suryoday senior citizen rate should match the Fixed Deposit sheet")
                .isTrue();
    }

    @When("I turn Senior Citizen rates off")
    public void seniorOff() {
        if (fixedDeposit.isSeniorCitizenOn()) {
            fixedDeposit.toggleSeniorCitizen();
        }
    }

    @Then("regular FD rates are displayed")
    public void regularRates() {
        Assertions.assertThat(fixedDeposit.isSeniorCitizenOn())
                .as("Senior Citizen toggle should be off for regular rates")
                .isFalse();
        Assertions.assertThat(fixedDeposit.cardShows("Suryoday", MoneyExcel.text("SuryodayRate", "8.25")))
                .as("Suryoday should return to the regular rate from the Fixed Deposit sheet")
                .isTrue();
        Assertions.assertThat(fixedDeposit.cardShows("Suryoday", MoneyExcel.text("SeniorRate", "8.5")))
                .as("The senior citizen 8.5% rate should be removed")
                .isFalse();
    }

    @When("I filter Fixed Deposits by {string}")
    public void filterFd(String label) {
        String tenure = MoneyExcel.text("Tenure", label);
        Assertions.assertThat(fixedDeposit.selectYearRange(tenure))
                .as("FD tenure filter " + tenure + " should be selectable")
                .isTrue();
    }

    @Then("only the 0 to 1 year FD range is selected")
    public void range01() {
        Assertions.assertThat(fixedDeposit.tenureRangeApplied(MoneyExcel.text("Tenure", "0-1 year")))
                .as("Only FDs in the 0-1 year range should be displayed")
                .isTrue();
    }

    @Then("only the 1 to 3 year FD range is selected")
    public void range13() {
        Assertions.assertThat(fixedDeposit.tenureRangeApplied(MoneyExcel.text("Tenure", "1-3 year")))
                .as("Only FDs in the 1-3 year range should be displayed")
                .isTrue();
    }

    @Then("only the over 3 years FD range is selected")
    public void range3() {
        Assertions.assertThat(fixedDeposit.tenureRangeApplied(MoneyExcel.text("Tenure", ">3 years")))
                .as("Only FDs with tenure greater than 3 years should be displayed")
                .isTrue();
    }

    @When("I open Women Benefits")
    public void women() {
        fixedDeposit.pressWomenBenefits();
    }

    @Then("women-benefit FD products are displayed")
    public void womenShown() {
        Assertions.assertThat(fixedDeposit.womenBenefitsApplied())
                .as("Women Benefits should be selected and a provider card should still show an interest rate")
                .isTrue();
    }

    /**
     * Leave payment gateway / order / buy-sell so Background can reach Products.
     * Avoid repeated full pageSource on WebView (hangs Appium). Use short taps + limited system Back.
     */
    private void leaveProductNestedScreens() {
        for (int i = 0; i < 6; i++) {
            if (onMainChrome()) {
                return;
            }
            if (home.tapLabelFast("Go to Dashboard")) {
                sleepQuiet(700);
                unlockPinAndSkipBiometric();
                continue;
            }
            if (home.tapBack() || home.tapLabelFast("Close") || home.tapLabelFast("Cancel")) {
                sleepQuiet(500);
                unlockPinAndSkipBiometric();
                continue;
            }
            home.tapHeaderBack();
            sleepQuiet(500);
            // Airpay WebView often has no in-app Back — one system Back leaves gateway only.
            if (i >= 1 && !onMainChrome()) {
                home.pressSystemBack();
                sleepQuiet(700);
                unlockPinAndSkipBiometric();
            }
        }
    }

    /** Tab bar present — cheap check, no full Products hub content match. */
    private boolean onMainChrome() {
        return home.sourceContains("Products")
                && home.sourceContains("FinSight")
                && home.sourceContains("Calculators");
    }

    private void ensureProductsHub() {
        for (int i = 0; i < 5 && !products.isDisplayed(); i++) {
            if (home.sourceContains("PROFILE SETTINGS", "Log Out")) {
                home.pressSystemBack();
                sleepQuiet(400);
                continue;
            }
            if (home.sourceContains("Your Risk Profile")) {
                return;
            }
            if (i > 0) {
                leaveProductNestedScreens();
            }
            if (products.open()) {
                return;
            }
            home.tapHeaderBack();
        }
    }

    private void passGoldIntro() {
        for (int i = 0; i < 6 && !gold.isDisplayed(); i++) {
            if (!gold.tapLabel("Next") && !gold.tapLabel("Skip") && !gold.tapLabel("Get Started")) {
                break;
            }
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void openFinsight() {
        for (int i = 0; i < 4 && !finsight.isDisplayed(); i++) {
            finsight.tapLabel("FinSight");
            home.dismissHomeCoachIfShown();
        }
        scrollHomeTop();
    }

    private void scrollHomeTop() {
        for (int i = 0; i < 5; i++) {
            if (home.sourceContains("Your Financial Health Score")
                    && (home.sourceContains("Hello")
                    || home.sourceContains("Reassess")
                    || home.sourceContains("Let's start")
                    || home.sourceContains("Let's Start"))) {
                return;
            }
            home.swipeDown();
            sleepQuiet(300);
        }
        home.scrollIntoDescription("Your Financial Health Score");
    }

    private void leaveSideMenu() {
        for (int i = 0; i < 4 && home.sourceContains("Log Out", "PROFILE SETTINGS", "Account Management"); i++) {
            home.pressSystemBack();
            sleepQuiet(500);
        }
    }

    private boolean insideHealthCheck() {
        return home.sourceContains(
                "Question ", "Lets do risk profile", "I already know",
                "Savings & Investments", "Income & Expense", "Insurance Details",
                "Your Risk Profile", "Summary Details", "Your Money Diagnosed",
                "Financial Health Check", "Add Goal Details", "OUT OF 100", "See the diagnosis");
    }

    private void unlockPinAndSkipBiometric() {
        biometric.dismissIfShown();
        if (!pinUnlock.waitUntilDisplayed(Duration.ofSeconds(3))
                && !home.sourceContains("Enter your Airpay Money PIN")) {
            biometric.dismissIfShown();
            return;
        }
        String pin = MoneyExcel.pin("880272");
        LOG.info("Entering session PIN from the Login sheet");
        pinUnlock.enterPin(pin);
        sleepQuiet(1200);
        biometric.dismissIfShown();
    }

    private boolean onPinOrLogin() {
        return home.sourceContains(
                "Login with Mobile", "Enter your Airpay Money PIN",
                "Forgot PIN", "Sign Up or Login", "Unlock faster with Biometrics");
    }

    private static void sleepQuiet(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void swipeUntil(String... fragments) {
        if (home.sourceContains(fragments)) {
            return;
        }
        for (int i = 0; i < 4 && !home.sourceContains(fragments); i++) {
            home.swipeDown();
            sleepQuiet(250);
        }
        for (int i = 0; i < 4 && !home.sourceContains(fragments); i++) {
            home.swipeUp();
            sleepQuiet(250);
        }
    }
}
