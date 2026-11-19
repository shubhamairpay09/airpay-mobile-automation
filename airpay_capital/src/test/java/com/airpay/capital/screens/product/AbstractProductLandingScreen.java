package com.airpay.capital.screens.product;

import com.airpay.capital.screens.apply.ApplyDetailsScreen;
import com.airpay.capital.screens.apply.EligibilityScreen;
import com.airpay.capital.screens.apply.OccupationScreen;
import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.capital.screens.home.HomeScreen;
import com.airpay.mobile.utilities.context.TestContext;

/**
 * Shared apply-flow actions for all loan/card products.
 * Product-specific landing screens only define the product label.
 */
public abstract class AbstractProductLandingScreen extends BaseScreen {

    protected final String productLabel;

    protected AbstractProductLandingScreen(TestContext context, String productLabel) {
        super(context);
        this.productLabel = productLabel;
    }

    public String productLabel() {
        return productLabel;
    }

    public boolean openFromHome() {
        return new HomeScreen(context).tapProductApplyNow(productLabel);
    }

    /** Alias used by a11y crawl. */
    public boolean tapHomeCardApplyNow() {
        return openFromHome();
    }

    public boolean looksLikeApplyFlow() {
        if (sourceContains("Tell us about your work", "Select your occupation",
                "Your loan journey is in progress", "Resume Application",
                "offers available", "offer available", "No Offers Available")) {
            return true;
        }
        boolean stillHomeGrid = sourceContains("Personal Loan") && sourceContains("Home Loan")
                && sourceContains("Gold Loan");
        if (stillHomeGrid) {
            return false;
        }
        return sourceContains("Terms and conditions", "Enter PAN", "Date of birth as per PAN");
    }

    public boolean selectSalaried() {
        new OccupationScreen(context).selectSalaried();
        return true;
    }

    public boolean selectSelfEmployed() {
        new OccupationScreen(context).selectSelfEmployed();
        return true;
    }

    public boolean tapContinue() {
        return new ApplyDetailsScreen(context).tapContinue();
    }

    public int fillOfficialDetails() {
        ApplyDetailsScreen details = new ApplyDetailsScreen(context);
        details.fillStandardDetails();
        details.acceptTermsAndTapApply();
        return 1;
    }

    public boolean acceptTermsAndTapApply() {
        return new ApplyDetailsScreen(context).acceptTermsAndTapApply();
    }

    public boolean tapApply() {
        HomeScreen home = new HomeScreen(context);
        for (int i = 0; i < 4; i++) {
            if (tapAny(java.util.List.of("Apply Now", "Apply now", "Apply", "Get Started", "Check Eligibility"))) {
                waitUntilSourceContains(java.time.Duration.ofSeconds(8),
                        "Tell us about your work", "Enter PAN", "Check Eligib", "offers available");
                return true;
            }
            home.swipeUp();
        }
        return false;
    }

    /** One Continue after the form is filled. Does not retype the fields. */
    public void advanceFormsUntilEligibility() {
        EligibilityScreen eligibility = new EligibilityScreen(context);
        ApplyDetailsScreen details = new ApplyDetailsScreen(context);
        if (eligibility.offersAreVisible() || eligibility.noOffersAreVisible()) {
            return;
        }
        if (details.isDisplayed() || eligibility.checkEligibilityVisible()) {
            if (eligibility.checkEligibilityVisible()) {
                eligibility.tapCheckEligibility();
            } else {
                details.tapContinue();
            }
        }
        waitUntilSourceContains(java.time.Duration.ofSeconds(20),
                "offers available", "offer available", "No Offers", "Check Eligib", "Apply now for");
    }
}
