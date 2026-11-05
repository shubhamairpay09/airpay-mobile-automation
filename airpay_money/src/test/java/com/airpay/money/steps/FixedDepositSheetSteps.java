package com.airpay.money.steps;

import com.airpay.money.data.MoneyExcel;
import com.airpay.money.screens.product.FdPlanBookingScreen;
import com.airpay.money.screens.product.FixedDepositsLandingScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

/**
 * Fixed Deposit sheet cases that stay on the dashboard or the Suryoday plan page.
 * Book FD is never tapped.
 */
public class FixedDepositSheetSteps {

    private final FixedDepositsLandingScreen fixedDeposit;
    private final FdPlanBookingScreen plan;

    public FixedDepositSheetSteps(TestContext context) {
        this.fixedDeposit = new FixedDepositsLandingScreen(context);
        this.plan = new FdPlanBookingScreen(context);
    }

    @Then("interest rates are displayed on more than one FD provider card")
    public void providerRates() {
        Assertions.assertThat(fixedDeposit.multipleProviderRatesVisible())
                .as("More than one FD provider card should show interest rates")
                .isTrue();
    }

    @When("I change the FD investment amount to {string}")
    public void changeAmount(String amount) {
        String value = MoneyExcel.text("Amount", amount);
        Assertions.assertThat(plan.changeInvestmentAmount(value))
                .as("The investment amount should update to " + value + " and maturity should remain displayed. Book FD is not tapped.")
                .isTrue();
    }

    @When("I select a different FD tenure")
    public void changeTenure() {
        Assertions.assertThat(plan.selectDifferentTenure())
                .as("A different tenure should be selected and the rate or maturity should update. Book FD is not tapped.")
                .isTrue();
    }

    @Then("the FD interest payout options are displayed")
    public void payoutOptions() {
        Assertions.assertThat(plan.payoutOptionsDisplayed())
                .as("Interest Payout should show Monthly, with Maturity selected as the default. Book FD is not tapped.")
                .isTrue();
    }
}
