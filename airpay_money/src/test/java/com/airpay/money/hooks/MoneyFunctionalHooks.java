package com.airpay.money.hooks;

import com.airpay.money.screens.finsight.FinSightHomeScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.driver.AppiumManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.testng.SkipException;

/**
 * Fast-path hooks so already-synced net worth cases do not run heavy Background navigation.
 */
public class MoneyFunctionalHooks {

    private final TestContext testContext;

    public MoneyFunctionalHooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before(order = 1, value = "@functional and not @login")
    public void leaveNestedBeforeFunctionalScenario() {
        HomeScreen home = new HomeScreen(testContext);
        home.dismissHomeCoachIfShown();
        for (int i = 0; i < 5; i++) {
            String src;
            try {
                src = home.pageSource();
            } catch (RuntimeException ex) {
                AppiumManager.close();
                testContext.setDriver(AppiumManager.open(testContext.config()).driver());
                return;
            }
            if (atFunctionalRoot(src)) {
                return;
            }
            if (src.contains("Enter your Airpay Money PIN")) {
                return;
            }
            if (!home.tapBack() && !home.tapLabel("Back")) {
                home.pressSystemBack();
            }
            sleepQuiet(400);
        }
    }

    @Before(
            order = 2,
            value = "@NW_TC_002 or @NW_TC_003 or @NW_TC_004 or @NW_TC_005 or @NW_TC_006 or @NW_TC_007 or @NW_TC_008")
    public void skipConsentFlowWhenNetWorthAlreadySynced() {
        HomeScreen home = new HomeScreen(testContext);
        FinSightHomeScreen finsight = new FinSightHomeScreen(testContext);
        if (!finsight.isDisplayed() && home.sourceContains("FinSight")) {
            home.tapLabelFast("FinSight");
            sleepQuiet(350);
        }
        if (finsight.alreadySyncedCard()) {
            throw new SkipException("Already synced — consent flow not applicable on this account");
        }
    }

    @After(order = 1)
    public void tally(Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@functional")) {
            FunctionalRunStats.record(scenario);
        }
    }

    private static void sleepQuiet(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /** Tab bar alone is not enough — FHC Personal Details also shows FinSight + Hello in the tree. */
    private static boolean atFunctionalRoot(String src) {
        if (src.contains("Enter your Airpay Money PIN")) {
            return true;
        }
        if (src.contains("Personal Details") && src.contains("Pincode")) {
            return false;
        }
        if (src.contains("Question ") && src.contains("Financial Health Check")) {
            return false;
        }
        boolean tabs = src.contains("Products") && src.contains("FinSight") && src.contains("Calculators");
        if (!tabs || src.contains("Select goal tenure") || src.contains("Make Payment")) {
            return false;
        }
        return src.contains("Financial Calculators")
                || (src.contains("Hello") && src.contains("Your Financial Health Score"))
                || src.contains("Fixed Deposits")
                || src.contains("Mutual Funds");
    }
}
