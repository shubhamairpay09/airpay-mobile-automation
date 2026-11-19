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
        if (!occupationVisible() && !home.journeyInProgress() && !offersAreVisible()) {
            occupation.waitUntilDisplayed(Duration.ofSeconds(12));
        }
        return product;
    }

    public boolean journeyInProgress() {
        return home.journeyInProgress();
    }

    /** Saved journey: every completed step is on screen, then Resume Application opens offers. */
    public void resumeSavedJourney() {
        if (!home.journeyInProgress()) {
            throw new IllegalStateException("In-progress loan journey is not displayed");
        }
        for (String step : new String[]{
                "Basic details", "Address details", "Income",
                "Credit assessment", "Personalised offers", "Resume Application"}) {
            if (!home.sourceContains(step)) {
                throw new IllegalStateException("Loan journey is missing step: " + step);
            }
        }
        if (!home.tapResumeApplication()) {
            throw new IllegalStateException("Resume Application was not tappable");
        }
        home.waitUntilSourceContains(Duration.ofSeconds(20), "offers available", "offer available", "Apply now");
    }

    public void openProductFromExcel() {
        openProduct(CapitalExcel.text("Product", currentProduct == null ? "Personal Loan" : currentProduct));
    }

    /** Opens the home-grid card and stops when the feature page or the apply flow is visible. */
    public void openProductCardFromExcel() {
        currentProduct = CapitalExcel.text("Product", currentProduct == null ? "Personal Loan" : currentProduct);
        home.openHome();
        if (!home.openProductFeaturePage(currentProduct)) {
            throw new IllegalStateException("Could not open " + currentProduct + " from the Home grid");
        }
    }

    public boolean addressVisible() {
        return applyDetails.addressVisible();
    }

    public boolean workDetailsVisible() {
        return salariedIncome.isDisplayed();
    }

    public boolean productSurfaceVisible() {
        return home.featurePageVisible() || occupationVisible() || applicationDetailsVisible()
                || addressVisible() || workDetailsVisible() || journeyInProgress() || offersAreVisible();
    }

    public boolean applyEntryVisible() {
        return occupationVisible() || applicationDetailsVisible() || addressVisible()
                || workDetailsVisible() || journeyInProgress() || offersAreVisible();
    }

    /** Checks the feature-page terms and taps Apply Now. No-op when the apply flow is already open. */
    public void acceptTermsAndOpenApplication() {
        if (applyEntryVisible()) {
            return;
        }
        home.finishFeaturePage();
        if (!applyEntryVisible()) {
            throw new IllegalStateException(
                    "Apply Now did not open occupation or personal details for " + currentProduct);
        }
    }

    /** Selects the Excel occupation, then opens personal details. A saved journey is resumed instead. */
    public void selectOccupationAndOpenDetails() {
        if (addressVisible() || workDetailsVisible() || applicationDetailsVisible() || offersAreVisible()) {
            return;
        }
        if (journeyInProgress()) {
            resumeSavedJourney();
            return;
        }
        selectOccupationFromExcel();
        if (!applicationDetailsVisible() && !offersAreVisible()) {
            throw new IllegalStateException(
                    "Personal details were not displayed after occupation was selected for " + currentProduct);
        }
    }

    public void enterIdentityFromExcel() {
        if (offersAreVisible() || addressVisible() || workDetailsVisible() || !applicationDetailsVisible()) {
            return;
        }
        applyDetails.enterIdentityFromExcel();
    }

    public String missingIdentityValues() {
        if (offersAreVisible() || addressVisible() || workDetailsVisible()) {
            return "";
        }
        return applyDetails.missingIdentityValues();
    }

    /** Sets date of birth and gender, then taps Continue once. Fills address when that screen is showing. */
    public void setDobGenderAndContinueOnce() {
        if (offersAreVisible()) {
            return;
        }
        if (addressVisible() || workDetailsVisible()) {
            submitAddressAndWork();
            return;
        }
        if (journeyInProgress()) {
            return;
        }
        if (!applicationDetailsVisible()) {
            throw new IllegalStateException("Personal details are not displayed for " + currentProduct);
        }
        applyDetails.chooseDateOfBirth();
        applyDetails.chooseGender();
        if (!applyDetails.tapContinue() && !eligibility.checkEligibilityVisible()) {
            throw new IllegalStateException("Continue was not tappable on personal details for " + currentProduct);
        }
        submitAddressAndWork();
    }

    /** Address Continue opens Work Details. Fill that form before Check Eligibility. */
    private void submitAddressAndWork() {
        applyDetails.fillAddressIfDisplayed();
        if (salariedIncome.isDisplayed()) {
            salariedIncome.fillIfDisplayed();
        }
        if (eligibility.checkEligibilityVisible()) {
            eligibility.tapCheckEligibility();
        }
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
        // Continue on this screen is announced "Go to Loan feature screen" and returns
        // to the product page. Apply Now there opens the personal-details form.
        if (!applyDetails.isDisplayed()) {
            home.finishFeaturePage();
        }
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

    public boolean applicationDetailsVisible() {
        return applyDetails.isDisplayed();
    }

    public boolean occupationVisible() {
        return occupation.isDisplayed();
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
