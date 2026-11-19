package com.airpay.money.hooks;

import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/**
 * Keep Money in the foreground and recover a dead Appium session before each functional case.
 */
public class MoneyFunctionalHooks {

    private final TestContext testContext;

    public MoneyFunctionalHooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before(order = 1, value = "@functional and not @login")
    public void leaveNestedBeforeFunctionalScenario(Scenario scenario) {
        FunctionalModuleReset.leaveForeignProfile(testContext, scenario);
    }

    @After(order = 2, value = "@functional and not @login")
    public void returnToModuleAfterFunctionalScenario(Scenario scenario) {
        FunctionalModuleReset.returnToModule(testContext, scenario);
    }

    @Before(order = 0, value = "@functional and not @login")
    public void keepMoneyForegroundForFunctional() {
        if (!testContext.hasDriver()) {
            return;
        }
        new com.airpay.money.screens.home.HomeScreen(testContext).ensureMoneyInForeground();
    }

    @Before(order = -2, value = "@profile-settings")
    public void allowProfileNavigation() {
        ProfileNavigationGate.allow();
    }

    @After(order = 0, value = "@functional")
    public void clearProfileNavigationGate() {
        ProfileNavigationGate.clear();
    }

    @Before(order = -1, value = "@fhc")
    public void stopFhcAfterSystemError(Scenario scenario) {
        FunctionalModuleReset.markFhcScenario(!scenario.getSourceTagNames().contains("@FHC_TC_001"));
        if (FhcSystemError.tripped()) {
            throw new AssertionError(
                    "FHC stopped after a system error. Raise this: " + FhcSystemError.message());
        }
    }

    @After(order = 5, value = "@fhc")
    public void clearFhcContinue() {
        FunctionalModuleReset.markFhcScenario(false);
    }

    @After(order = 1)
    public void tally(Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@functional")) {
            FunctionalRunStats.record(scenario);
        }
        if (scenario.getSourceTagNames().contains("@fhc") && scenario.isFailed()
                && !FhcErrorLog.last().contains(scenario.getName())) {
            FhcErrorLog.record(scenario.getName(), "FAILED", scenario.getStatus().name());
        }
    }
}
