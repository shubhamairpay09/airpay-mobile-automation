package com.airpay.money.hooks;

import com.airpay.money.screens.calculators.CalculatorsHubScreen;
import com.airpay.money.screens.finsight.FinSightHomeScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.money.screens.product.ProductsHubScreen;
import com.airpay.money.screens.profile.ProfileSettingsScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;

/**
 * After pass or fail, stay on the hub of the module under test.
 * Never taps Open settings / Document Details unless the case is Profile Settings.
 */
public final class FunctionalModuleReset {

    private static final Logger LOG = LogManager.getLogger(FunctionalModuleReset.class);
    /** Later FHC cases continue from the current page instead of backing out to the hub. */
    private static volatile boolean fhcContinue;

    private FunctionalModuleReset() {
    }

    public static void markFhcScenario(boolean continueOnPage) {
        fhcContinue = continueOnPage;
    }

    public static boolean fhcContinue() {
        return fhcContinue;
    }

    public static void returnToModule(TestContext context, Scenario scenario) {
        if (context == null || !context.hasDriver()) {
            return;
        }
        Collection<String> tags = scenario.getSourceTagNames();
        if (!tags.contains("@functional") || tags.contains("@login")) {
            return;
        }
        try {
            Module module = Module.from(tags);
            HomeScreen home = new HomeScreen(context);
            String src = home.pageSource();
            if (alreadyOnModule(module, src)) {
                return;
            }
            LOG.info("Return to {} after {} | {}", module, scenario.getStatus(), scenario.getName());
            if (onProfileTree(src) && module != Module.PROFILE) {
                leaveProfileWithoutOpeningIt(context, home);
                src = home.pageSource();
            }
            switch (module) {
                case PROFILE -> {
                    if (ProfileNavigationGate.allowed()) {
                        new ProfileSettingsScreen(context).returnToSettings();
                    }
                }
                case CALCULATORS -> new CalculatorsHubScreen(context).ensureOpen();
                case PRODUCTS -> new ProductsHubScreen(context).open();
                default -> leaveToFinsight(context, home, src);
            }
        } catch (Exception e) {
            LOG.warn("Return to module skipped: {}", e.toString());
        }
    }

    /** System Back + FinSight tab only. Never taps Open settings. */
    public static void escapeAccidentalProfile(TestContext context) {
        if (context == null || !context.hasDriver()) {
            return;
        }
        HomeScreen home = new HomeScreen(context);
        if (!onProfileTree(home.pageSource())) {
            return;
        }
        leaveProfileWithoutOpeningIt(context, home);
    }

    public static void leaveForeignProfile(TestContext context, Scenario scenario) {
        if (context == null || !context.hasDriver()) {
            return;
        }
        Collection<String> tags = scenario.getSourceTagNames();
        if (!tags.contains("@functional") || tags.contains("@login") || tags.contains("@profile-settings")) {
            return;
        }
        try {
            HomeScreen home = new HomeScreen(context);
            String src = home.pageSource();
            if (!onProfileTree(src)) {
                return;
            }
            LOG.info("Leaving Profile/Documents before {} — not part of this module", scenario.getName());
            leaveProfileWithoutOpeningIt(context, home);
        } catch (Exception e) {
            LOG.warn("Leave profile skipped: {}", e.toString());
        }
    }

    private static void leaveToFinsight(TestContext context, HomeScreen home, String src) {
        if (onFinsightHub(src) || onFhcJourney(src)) {
            if (onFinsightHub(src)) {
                return;
            }
            new FinSightHomeScreen(context).openFinSightTab();
            return;
        }
        escapeAccidentalProfile(context);
        new FinSightHomeScreen(context).openFinSightTab();
    }

    private static void leaveProfileWithoutOpeningIt(TestContext context, HomeScreen home) {
        for (int i = 0; i < 5; i++) {
            String src = home.pageSource();
            if (!onProfileTree(src) || onFinsightHub(src)) {
                return;
            }
            home.pressSystemBack();
        }
        if (onProfileTree(home.pageSource())) {
            new FinSightHomeScreen(context).openFinSightTab();
        }
    }

    private static boolean alreadyOnModule(Module module, String src) {
        return switch (module) {
            case PROFILE -> src.toUpperCase().contains("PROFILE SETTINGS")
                    && !src.contains("My Documents")
                    && !src.contains("Edit personal details");
            case CALCULATORS -> src.contains("EMI Calculator") && src.contains("SIP Calculator")
                    && (src.contains("Hello") || src.contains("Financial Calculators")
                    || src.contains("Budget Calculator"));
            case PRODUCTS -> src.contains("Investments") && src.contains("Digital Gold");
            case FINSIGHT -> onFinsightHub(src) || onFhcJourney(src);
        };
    }

    public static boolean onProfileTree(String src) {
        if (src == null) {
            return false;
        }
        String upper = src.toUpperCase();
        if (onFhcJourney(src) && !upper.contains("PROFILE SETTINGS") && !src.contains("My Documents")) {
            return false;
        }
        return upper.contains("PROFILE SETTINGS")
                || src.contains("My Documents")
                || src.contains("Document Details")
                || src.contains("Work Profile")
                || src.contains("No Document found")
                || (src.contains("Log Out") && src.contains("Contact Us"))
                || (src.contains("Personal Details") && src.contains("Edit personal details"));
    }

    static boolean onFinsightHub(String src) {
        return src.contains("Your Financial Health Score")
                && src.contains("FinSight")
                && src.contains("Open settings")
                && !src.contains("content-desc=\"Back\"");
    }

    public static boolean onFhcPage(String src) {
        return onFhcJourney(src);
    }

    static boolean onFhcJourney(String src) {
        if (src == null || src.isBlank()) {
            return false;
        }
        return src.contains("Income & Expense")
                || src.contains("Insurance Details")
                || (src.contains("Savings") && src.contains("Investment"))
                || src.contains("Add Investment Details")
                || src.contains("Add Loan Details")
                || src.contains("Lets do risk profile")
                || src.contains("Your Risk Profile")
                || src.contains("Let us know your risk")
                || src.contains("Summary Details")
                || src.contains("YOUR FINANCIAL HEALTH SCORE")
                || src.contains("Your Money Diagnosed")
                || (src.contains("Personal Details") && src.contains("Pincode") && src.contains("Male")
                && !src.contains("Edit personal details"))
                || (src.contains("Question ") && src.contains("/5"));
    }

    enum Module {
        FINSIGHT, PROFILE, CALCULATORS, PRODUCTS;

        static Module from(Collection<String> tags) {
            if (tags.contains("@profile-settings")) {
                return PROFILE;
            }
            if (tags.contains("@calculators")) {
                return CALCULATORS;
            }
            if (tags.contains("@mutual-funds") || tags.contains("@digital-gold")
                    || tags.contains("@fixed-deposit") || tags.contains("@nps")) {
                return PRODUCTS;
            }
            return FINSIGHT;
        }
    }
}
