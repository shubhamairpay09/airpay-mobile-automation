package com.airpay.capital.flows;

import com.airpay.capital.data.CapitalExcel;
import com.airpay.capital.screens.apply.ApplyDetailsScreen;
import com.airpay.capital.screens.apply.EligibilityScreen;
import com.airpay.capital.screens.apply.OccupationScreen;
import com.airpay.capital.screens.apply.SalariedIncomeScreen;
import com.airpay.capital.screens.apply.SelfEmployedDetailsScreen;
import com.airpay.capital.screens.home.HomeScreen;
import com.airpay.capital.screens.product.AbstractProductLandingScreen;
import com.airpay.capital.screens.product.ProductLandingScreens;
import com.airpay.capital.screens.product.offers.AbstractOffersScreen;
import com.airpay.capital.screens.product.offers.ProductOffersScreens;
import com.airpay.mobile.utilities.context.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;

/**
 * Product apply journeys using screen objects:
 * Home → product → occupation → forms (Excel data) → Check Eligibility → offers / errors.
 */
public class ProductJourneyFlow {

    private static final Logger LOG = LogManager.getLogger(ProductJourneyFlow.class);

    public enum Occupation {
        SALARIED, SELF_EMPLOYED
    }

    private final TestContext context;
    private final HomeScreen home;
    private final OccupationScreen occupation;
    private final ApplyDetailsScreen applyDetails;
    private final SalariedIncomeScreen salariedIncome;
    private final SelfEmployedDetailsScreen selfEmployedDetails;
    private final EligibilityScreen eligibility;

    private String currentProduct;

    public ProductJourneyFlow(TestContext context) {
        this.context = context;
        this.home = new HomeScreen(context);
        this.occupation = new OccupationScreen(context);
        this.applyDetails = new ApplyDetailsScreen(context);
        this.salariedIncome = new SalariedIncomeScreen(context);
        this.selfEmployedDetails = new SelfEmployedDetailsScreen(context);
        this.eligibility = new EligibilityScreen(context);
    }

    public String currentProduct() {
        return currentProduct;
    }

    public AbstractProductLandingScreen openProduct(String productLabel) {
        currentProduct = productLabel;
        home.openHome();
        home.waitUntilSourceContains(Duration.ofSeconds(8), productLabel, "Personal Loan", "Dashboard");
        AbstractProductLandingScreen product = ProductLandingScreens.forProduct(context, productLabel);
        if (!product.openFromHome()) {
            throw new IllegalStateException("Could not open Apply flow for " + productLabel);
        }
        occupation.waitUntilDisplayed(Duration.ofSeconds(15));
        return product;
    }

    public void openProductFromExcel() {
        openProduct(CapitalExcel.text("Product", currentProduct == null ? "Personal Loan" : currentProduct));
    }

    public void selectOccupation(Occupation occ) {
        if (!occupation.isDisplayed() && !ProductLandingScreens.forProduct(context, currentProduct).looksLikeApplyFlow()) {
            throw new IllegalStateException("Occupation screen not shown for " + currentProduct);
        }
        if (occ == Occupation.SALARIED) {
            occupation.selectSalaried();
        } else {
            occupation.selectSelfEmployed();
        }
        occupation.tapContinue();
        applyDetails.waitUntilDisplayed(Duration.ofSeconds(12));
    }

    public void selectOccupationFromExcel() {
        selectOccupation(parseOccupation(CapitalExcel.text("Occupation", "Salaried")));
    }

    public void completeApplyForms(Occupation occ) {
        if (occ == Occupation.SELF_EMPLOYED && selfEmployedDetails.isDisplayed()) {
            selfEmployedDetails.fillIfDisplayed();
        } else {
            applyDetails.fillStandardDetails();
            applyDetails.acceptTermsAndTapApply();
        }
        salariedIncome.fillIfDisplayed();
        ProductLandingScreens.forProduct(context, currentProduct).advanceFormsUntilEligibility();
    }

    public void completeApplyFormsFromExcel() {
        completeApplyForms(parseOccupation(CapitalExcel.text("Occupation", "Salaried")));
    }

    public void tapCheckEligibility() {
        eligibility.tapCheckEligibility();
    }

    public boolean offersAreVisible() {
        return offersScreen().offersAreVisible() || eligibility.offersAreVisible();
    }

    public boolean noOffersAreVisible() {
        return offersScreen().noOffersAreVisible() || eligibility.noOffersAreVisible();
    }

    public boolean cibilLowErrorIsVisible() {
        return eligibility.cibilLowErrorIsVisible();
    }

    public void openFirstOfferLenderPage() {
        offersScreen().tapFirstLenderOffer();
    }

    /** Locates a lender offer by Excel LenderName (e.g. Muthoot) and taps Apply Now. */
    public boolean openLenderOfferFromExcel() {
        String lender = CapitalExcel.text("LenderName", "Muthoot");
        LOG.info("Looking for lender offer containing '{}'", lender);
        home.waitUntilSourceContains(Duration.ofSeconds(20), lender, "Apply now", "offers available");
        for (int i = 0; i < 6; i++) {
            if (home.sourceContains(lender)) {
                break;
            }
            home.swipeUp();
            home.waitUntilSourceContains(Duration.ofSeconds(2), lender);
        }
        boolean tapped = home.tapLabel(lender)
                || home.tapAny(java.util.List.of("Apply now for", "Apply Now", "Apply now"));
        return tapped;
    }

    public static Occupation parseOccupation(String label) {
        if (label == null) {
            return Occupation.SALARIED;
        }
        String normalized = label.trim().toLowerCase().replace(' ', '-');
        if (normalized.contains("self")) {
            return Occupation.SELF_EMPLOYED;
        }
        return Occupation.SALARIED;
    }

    private AbstractOffersScreen offersScreen() {
        return ProductOffersScreens.forProduct(context, currentProduct);
    }
}
