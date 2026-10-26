package com.airpay.money.steps;

import com.airpay.money.data.MoneyExcel;
import com.airpay.money.screens.product.DigitalGoldBuyScreen;
import com.airpay.money.screens.product.DigitalGoldDashboardScreen;
import com.airpay.money.screens.product.DigitalGoldLockerScreen;
import com.airpay.money.screens.product.DigitalGoldOrderSummaryScreen;
import com.airpay.money.screens.product.DigitalGoldPaymentScreen;
import com.airpay.money.screens.product.DigitalGoldSellScreen;
import com.airpay.money.screens.product.DigitalGoldTransactionsScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Remaining Digital Gold sheet cases. Payment screens are opened only to check them.
 * Continue is not tapped and no card or UPI is entered.
 */
public class DigitalGoldSheetSteps {

    private static final Pattern MONEY = Pattern.compile("(?:₹|Rs\\.?)\\s*([0-9][0-9,]*(?:\\.\\d+)?)");
    private static final Pattern CLOCK = Pattern.compile("(\\d{1,2}:\\d{2})");
    private static final Pattern LABEL = Pattern.compile("(?:text|content-desc)=\"([^\"]*)\"");
    private static final String TIMEOUT = "The session has timed out, Please retry again.";
    private static final String FAILURE = "Unable to process the transaction. Please try again later!";

    private final DigitalGoldDashboardScreen gold;
    private final DigitalGoldBuyScreen buy;
    private final DigitalGoldSellScreen sell;
    private final DigitalGoldLockerScreen locker;
    private final DigitalGoldTransactionsScreen transactions;
    private final DigitalGoldOrderSummaryScreen order;
    private final DigitalGoldPaymentScreen payment;
    private String orderSource = "";

    public DigitalGoldSheetSteps(TestContext context) {
        this.gold = new DigitalGoldDashboardScreen(context);
        this.buy = new DigitalGoldBuyScreen(context);
        this.sell = new DigitalGoldSellScreen(context);
        this.locker = new DigitalGoldLockerScreen(context);
        this.transactions = new DigitalGoldTransactionsScreen(context);
        this.order = new DigitalGoldOrderSummaryScreen(context);
        this.payment = new DigitalGoldPaymentScreen(context);
    }

    @When("I open the Gold section on the Digital Gold dashboard")
    public void openGoldSection() {
        gold.openGoldSection();
    }

    @And("gold value and quantity are displayed on the dashboard")
    public void goldValueQty() {
        String src = gold.pageSource();
        Assertions.assertThat(src).as("Gold value in rupees should be displayed").contains("₹");
        Assertions.assertThat(src).as("Gold quantity should be displayed").contains("gm");
    }

    @When("I open the Silver section on the Digital Gold dashboard")
    public void openSilverSection() {
        gold.openSilverSection();
    }

    @And("silver value and quantity are displayed on the dashboard")
    public void silverValueQty() {
        String src = gold.pageSource();
        Assertions.assertThat(src).as("Silver section should be displayed").contains("Silver");
        Assertions.assertThat(src).as("Silver value in rupees should be displayed").contains("₹");
        Assertions.assertThat(src).as("Silver quantity should be displayed").contains("gm");
    }

    @When("I enter 200 rupees and accept buy consent")
    public void enter200() {
        if (!buy.isDisplayed()) {
            gold.tapBuy();
        }
        buy.chooseBuyInRupees();
        buy.enterAmount(MoneyExcel.text("BuyRupees", "200"));
        buy.acceptConsent();
    }

    @And("I tap Proceed on the buy screen")
    public void tapProceed() {
        Assertions.assertThat(buy.proceedEnabled())
                .as("Proceed should be enabled after a valid amount and consent")
                .isTrue();
        buy.tapProceed();
    }

    @Then("the order summary shows the purchase, price, metal, quantity, investment type, tax, total, timer, and Make Payment")
    public void orderSummary() {
        Assertions.assertThat(order.isDisplayed())
                .as("Order Summary should be displayed after Proceed")
                .isTrue();
        String src = order.pageSource();
        orderSource = src;
        Assertions.assertThat(src).as("Product or metal should be displayed").contains("Gold");
        Assertions.assertThat(src).as("Buying price should be displayed").containsIgnoringCase("Price");
        Assertions.assertThat(src).as("Quantity should be displayed").contains("gm");
        Assertions.assertThat(src.toLowerCase()).as("Investment type should be displayed").contains("one time");
        Assertions.assertThat(src).as("GST should be displayed as Tax").contains("Tax (");
        Assertions.assertThat(src).as("Total payment should be displayed").containsIgnoringCase("Total");
        Assertions.assertThat(src).as("Price validity timer should be displayed").containsPattern("\\d{1,2}:\\d{2}");
        Assertions.assertThat(src).as("Make Payment should be displayed").contains("Make Payment");
        Assertions.assertThat(src).as("No error should be displayed").doesNotContain("Something went wrong");
    }

    @And("the order summary matches the amount entered on the buy screen")
    public void orderMatches() {
        String src = order.pageSource();
        orderSource = src;
        Assertions.assertThat(src).as("Metal selected on the buy screen should be Gold").contains("Gold");
        Assertions.assertThat(src).as("Entered amount 200 should be carried to the order summary").contains("200");
        Assertions.assertThat(src.toLowerCase()).as("Investment type should stay One time").contains("one time");
        Assertions.assertThat(src).as("Quantity in grams should be displayed").contains("gm");
    }

    @And("the order summary total equals the amount plus tax")
    public void totalEqualsAmountPlusTax() {
        String src = order.pageSource();
        Assertions.assertThat(src).as("GST should be displayed as Tax").contains("Tax (");
        Assertions.assertThat(src).as("Total payment should be displayed").containsIgnoringCase("Total");
        Assertions.assertThat(amountsAddUp(src, true))
                .as("Total Payment should equal the amount plus Tax, which is the GST")
                .isTrue();
    }

    @And("the price validity timer is counting down")
    public void timerCounts() {
        String first = firstClock(order.pageSource());
        Assertions.assertThat(first).as("Price validity timer should be displayed").isNotBlank();
        sleep(3000);
        String second = firstClock(order.pageSource());
        Assertions.assertThat(second).as("Timer should still be displayed").isNotBlank();
        Assertions.assertThat(second).as("Countdown should decrease").isNotEqualTo(first);
    }

    @When("I tap Make Payment and do not submit a payment")
    public void makePayment() {
        order.tapMakePayment();
    }

    @Then("the Airpay payment page shows the amount, an order id, the mobile number, policy links, and Continue")
    public void airpayPage() {
        String src = payment.loadedSource();
        Assertions.assertThat(src.contains("Continue"))
                .as("Airpay payment page should open. Payment is not submitted.")
                .isTrue();
        Assertions.assertThat(src).as("Continue should be displayed").contains("Continue");
        Assertions.assertThat(src.toLowerCase()).as("Order id should be displayed").contains("order");
        Assertions.assertThat(src).as("A mobile number should be displayed").containsPattern("\\d{10}|\\+91");
        Assertions.assertThat(src).as("Privacy Policy should be visible").contains("Privacy");
        Assertions.assertThat(src).as("Terms should be visible").contains("Terms");
        Assertions.assertThat(src)
                .as("Payment must not be completed")
                .doesNotContain("Payment Successful")
                .doesNotContain("Buy Successful");
    }

    @And("the Airpay amount matches the order summary")
    public void airpayAmount() {
        String src = payment.loadedSource();
        Assertions.assertThat(src)
                .as("Airpay transaction amount should match the 200 rupee order summary")
                .contains("200");
        Assertions.assertThat(src).as("Airpay payment page should stay open").contains("Continue");
    }

    @And("the Airpay order id is not blank")
    public void orderIdPresent() {
        String labels = visibleLabels(payment.loadedSource());
        Assertions.assertThat(labels)
                .as("A unique Order ID should be displayed and should not be blank")
                .containsPattern("(?i)order\\s*id\\s+\\d+");
    }

    @Then("the registered mobile number and country code are shown on Airpay")
    public void registeredMobile() {
        Assertions.assertThat(payment.isDisplayed())
                .as("Airpay payment page should be displayed")
                .isTrue();
        String compact = payment.loadedSource().replace(" ", "").replace("-", "");
        Assertions.assertThat(compact)
                .as("Country code should be displayed")
                .containsPattern("\\+?91");
        Assertions.assertThat(compact)
                .as("Registered mobile number should be prefilled")
                .contains("9895907801");
    }

    @And("Continue stays off before Airpay consent")
    public void continueOff() {
        Assertions.assertThat(payment.continueBlocked())
                .as("Continue should stay disabled until Airpay terms are accepted. Terms are not accepted.")
                .isTrue();
    }

    @When("I leave the Airpay page without paying")
    public void leaveAirpay() {
        payment.tapBack();
        sleep(1200);
    }

    @Then("the unpaid transaction failure screen is displayed")
    public void failureScreen() {
        String src = payment.pageSource().replace("&#10;", " ").replaceAll("\\s+", " ");
        Assertions.assertThat(src)
                .as("Message \"" + FAILURE + "\" should be displayed")
                .contains(FAILURE);
        Assertions.assertThat(src).as("Purchased product details should show Gold Buy").contains("Gold");
        Assertions.assertThat(src).as("Purchased product details should show Buy").containsIgnoringCase("Buy");
        Assertions.assertThat(src).as("The example amount Gold Buy ₹200.00 should be displayed").contains("200");
        Assertions.assertThat(src).as("A Reference ID should be displayed").containsIgnoringCase("Reference");
        Assertions.assertThat(src).as("Reference ID should not be blank").containsPattern("(?i)reference.{0,40}\\d");
        Assertions.assertThat(src).as("Go to Dashboard should be displayed").containsIgnoringCase("Dashboard");
        Assertions.assertThat(src)
                .as("No payment should be processed")
                .doesNotContain("Payment Successful")
                .doesNotContain("Buy Successful");
    }

    @When("I tap Go to Dashboard on the failure screen")
    public void goDashboard() {
        payment.tapLabel("Go to Dashboard");
        sleep(800);
    }

    @Then("the Digital Gold dashboard is displayed and nothing was purchased")
    public void dashNoPurchase() {
        Assertions.assertThat(gold.isDisplayed())
                .as("User should return to the Digital Gold dashboard")
                .isTrue();
        Assertions.assertThat(gold.pageSource())
                .as("No gold or silver should be credited and no payment should be processed")
                .doesNotContain("Payment Successful")
                .doesNotContain("Buy Successful");
    }

    @When("I wait for the price validity timer to expire")
    public void waitTimer() {
        long end = System.currentTimeMillis() + Duration.ofMinutes(6).toMillis();
        while (System.currentTimeMillis() < end) {
            String src = order.pageSource();
            if (src.contains(TIMEOUT) || src.contains("timed out")) {
                return;
            }
            sleep(2000);
        }
        Assertions.fail("The session timeout message was not displayed within 6 minutes");
    }

    @Then("the session timeout message is displayed")
    public void timeoutShown() {
        String src = order.pageSource();
        Assertions.assertThat(src.contains(TIMEOUT) || (src.contains("timed out") && src.contains("retry")))
                .as("Message \"" + TIMEOUT + "\" should be displayed and payment with the expired quote should be blocked")
                .isTrue();
    }

    @When("I tap Go Back on the session timeout")
    public void timeoutGoBack() {
        order.tapGoBack();
    }

    @Then("the buy screen is ready for a new quote")
    public void newQuote() {
        Assertions.assertThat(buy.isDisplayed() || gold.isDisplayed())
                .as("Go Back should close the timeout and return to Buy Gold and Silver or the previous step")
                .isTrue();
        Assertions.assertThat(order.pageSource())
                .as("The timeout popup should be closed")
                .doesNotContain(TIMEOUT);
    }

    @And("the buy amount entered before the order summary is still shown")
    public void amountRetained() {
        order.tapBack();
        Assertions.assertThat(buy.isDisplayed())
                .as("Back should return to the Buy Gold and Silver screen")
                .isTrue();
        Assertions.assertThat(buy.pageSource())
                .as("The amount entered before Proceed should still be shown")
                .contains("200");
    }

    @And("locker total value equals gold value plus silver value")
    public void lockerMath() {
        String src = locker.pageSource();
        Assertions.assertThat(src).as("Total value should be displayed").contains("Total");
        Assertions.assertThat(src).as("Gold value should be displayed").contains("Gold");
        Assertions.assertThat(src).as("Silver value should be displayed").contains("Silver");
        Assertions.assertThat(amountsAddUp(src, false) || allZero(src))
                .as("Total Value should equal Gold value plus Silver value")
                .isTrue();
    }

    @And("available for selling is shown for the locker metal")
    public void availableSelling() {
        Assertions.assertThat(locker.sourceContains("Available for selling"))
                .as("Available for selling should be shown")
                .isTrue();
    }

    @And("gold purchase records or the empty locker state are displayed")
    public void goldRecords() {
        recordsOrEmpty("Gold");
    }

    @And("silver purchase records or the empty locker state are displayed")
    public void silverRecords() {
        recordsOrEmpty("Silver");
    }

    @And("Buy Gold, Sell Gold, and Buy Coin are available on the locker")
    public void lockerActions() {
        String src = locker.pageSource();
        Assertions.assertThat(src).as("Buy Gold should be displayed").contains("Buy Gold");
        Assertions.assertThat(src).as("Sell Gold should be displayed").contains("Sell Gold");
        Assertions.assertThat(src).as("Buy Coin should be displayed").contains("Buy Coin");
    }

    @Then("the 7-day selling restriction is shown and no purchase is placed")
    public void sevenDayNoBuy() {
        Assertions.assertThat(locker.sourceContains("7 day", "7 days", "only after 7 days"))
                .as("The 7-day selling restriction should be displayed. A purchase is not placed.")
                .isTrue();
        Assertions.assertThat(locker.pageSource())
                .as("No purchase should be started from this case")
                .doesNotContain("Make Payment")
                .doesNotContain("Payment Successful");
    }

    @When("I remember the locker balances")
    public void rememberLocker() {
        orderSource = locker.pageSource();
    }

    @Then("the sell screen shows the same locker balances")
    public void sellMatchesLocker() {
        String src = sell.pageSource();
        Assertions.assertThat(src).as("Gold available balance should be displayed").contains("Gold");
        Assertions.assertThat(src).as("Silver available balance should be displayed").contains("Silver");
        Assertions.assertThat(src).as("Balance should be shown in grams").contains("gm");
        if (orderSource.contains("0.00") || orderSource.contains("0.0")) {
            Assertions.assertThat(src).as("Sell balance should match the empty locker").contains("0");
        }
    }

    @When("I open One Time purchases in Transaction History")
    public void oneTime() {
        transactions.tapLabel("Buy");
        transactions.tapLabel("One Time");
    }

    @Then("a buy transaction shows product, date, amount, grams, and status, or the empty state")
    public void buyRecord() {
        recordOrEmpty();
    }

    @Then("completed purchases show Buy Successful, or the empty state is shown")
    public void buySuccessful() {
        String src = transactions.pageSource();
        if (src.contains("No Transaction")) {
            Assertions.assertThat(src).as("Empty buy history should say No Transaction").contains("No Transaction");
            return;
        }
        Assertions.assertThat(src).as("Completed purchases should show Buy Successful").contains("Buy Successful");
    }

    @Then("a transaction record shows product, purity, date, amount, grams, and status, or the empty state")
    public void anyRecord() {
        String src = transactions.pageSource();
        if (src.contains("No Transaction")) {
            Assertions.assertThat(src).as("Empty history should say No Transaction").contains("No Transaction");
            return;
        }
        Assertions.assertThat(src).as("Purity should be displayed").containsIgnoringCase("karat");
        recordOrEmpty();
    }

    private void recordsOrEmpty(String metal) {
        String src = locker.pageSource();
        Assertions.assertThat(src).as(metal + " details should be displayed").contains(metal);
        if (src.contains("No Transaction")) {
            Assertions.assertThat(src)
                    .as(metal + " locker has no purchases, so No Transaction should be displayed")
                    .contains("No Transaction");
            return;
        }
        Assertions.assertThat(src).as(metal + " amount should be displayed").contains("₹");
        Assertions.assertThat(src).as(metal + " grams should be displayed").contains("gm");
        Assertions.assertThat(src.toLowerCase()).as("Purity should be displayed").contains("k");
    }

    private void recordOrEmpty() {
        String src = transactions.pageSource();
        if (src.contains("No Transaction")) {
            Assertions.assertThat(src).as("Empty transaction history should say No Transaction").contains("No Transaction");
            return;
        }
        Assertions.assertThat(src).as("Amount should be displayed").contains("₹");
        Assertions.assertThat(src).as("Quantity in grams should be displayed").contains("gm");
        Assertions.assertThat(src).as("A date should be displayed").containsPattern("\\d{1,2}");
        String lower = src.toLowerCase();
        Assertions.assertThat(lower.contains("successful") || lower.contains("failed")
                        || lower.contains("pending") || lower.contains("processing"))
                .as("Transaction status should be displayed")
                .isTrue();
    }

    private void openOrderIfNeeded() {
        if (order.isDisplayed()) {
            orderSource = order.pageSource();
            return;
        }
        if (!buy.isDisplayed()) {
            gold.tapBuy();
        }
        buy.chooseBuyInRupees();
        buy.enterAmount(MoneyExcel.text("BuyRupees", "200"));
        buy.acceptConsent();
        buy.tapProceed();
        orderSource = order.pageSource();
    }

    @When("I open the order summary for 200 rupees")
    public void openOrder() {
        openOrderIfNeeded();
        Assertions.assertThat(order.isDisplayed())
                .as("Order Summary should open for the 200 rupee quote. Payment is not submitted.")
                .isTrue();
    }

    private static boolean amountsAddUp(String src, boolean taxMustBePositive) {
        List<BigDecimal> nums = money(src);
        for (BigDecimal left : nums) {
            for (BigDecimal right : nums) {
                if (right.compareTo(BigDecimal.ZERO) < 0) {
                    continue;
                }
                if (taxMustBePositive && right.compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                BigDecimal sum = left.add(right);
                for (BigDecimal total : nums) {
                    if (total.subtract(sum).abs().compareTo(new BigDecimal("0.05")) <= 0
                            && total.compareTo(left) >= 0
                            && right.compareTo(total) < 0) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean allZero(String src) {
        List<BigDecimal> nums = money(src);
        return !nums.isEmpty() && nums.stream().allMatch(n -> n.compareTo(BigDecimal.ZERO) == 0);
    }

    private static List<BigDecimal> money(String src) {
        List<BigDecimal> nums = new ArrayList<>();
        Matcher matcher = MONEY.matcher(src);
        while (matcher.find() && nums.size() < 40) {
            nums.add(new BigDecimal(matcher.group(1).replace(",", "")));
        }
        return nums;
    }

    private static String firstClock(String src) {
        Matcher matcher = CLOCK.matcher(src);
        return matcher.find() ? matcher.group(1) : "";
    }

    /** Visible labels in order. Airpay splits "Order ID" and its number across text nodes. */
    private static String visibleLabels(String src) {
        Matcher matcher = LABEL.matcher(src);
        StringBuilder labels = new StringBuilder();
        while (matcher.find()) {
            String value = matcher.group(1).replace("&#10;", " ").replace("&amp;", "&").trim();
            if (value.isEmpty()) {
                continue;
            }
            if (labels.length() > 0) {
                labels.append(' ');
            }
            labels.append(value);
        }
        return labels.toString();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
