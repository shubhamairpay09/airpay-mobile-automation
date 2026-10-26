package com.airpay.capital.screens.product;

import com.airpay.capital.screens.apply.ApplyDetailsScreen;
import com.airpay.capital.screens.apply.EligibilityScreen;
import com.airpay.capital.screens.apply.OccupationScreen;
import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.capital.screens.home.HomeScreen;
import com.airpay.mobile.utilities.context.TestContext;

import com.airpay.mobile.utilities.crawl.DummyFormFiller;

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
        if (sourceContains("Salaried") && (sourceContains("Self-Employed") || sourceContains("Self Employed"))) {
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
                sleep(550);
                return true;
            }
            home.swipeUp();
            sleep(300);
        }
        return false;
    }

    public void advanceFormsUntilEligibility() {
        DummyFormFiller filler = new DummyFormFiller(driver);
        EligibilityScreen eligibility = new EligibilityScreen(context);
        ApplyDetailsScreen details = new ApplyDetailsScreen(context);
        for (int step = 0; step < 14; step++) {
            if (eligibility.checkEligibilityVisible()
                    || eligibility.offersAreVisible()
                    || eligibility.noOffersAreVisible()) {
                return;
            }
            filler.fillVisibleFields(3);
            details.tapContinue();
            tapAny(java.util.List.of("Continue", "Next", "Proceed", "Save & Continue"));
            sleep(900);
        }
    }
}
