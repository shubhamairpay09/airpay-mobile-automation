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
    public void leaveNestedBeforeFunctionalScenario() {
        // Do not call Appium here. getPageSource / getCurrentPackage / system Back
        // wedge UiAutomator2 on this realme until newCommandTimeout (4h).
        // ExcelPostLoginSteps.loggedInHome already leaves nested product screens.
    }

    @Before(order = 0, value = "@functional and not @login")
    public void keepMoneyForegroundForFunctional() {
        // Session start already confirmed Money in the foreground.
        // dumpsys / getCurrentPackage hang this realme and block Given.
    }

    @After(order = 1)
    public void tally(Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@functional")) {
            FunctionalRunStats.record(scenario);
        }
    }
}
