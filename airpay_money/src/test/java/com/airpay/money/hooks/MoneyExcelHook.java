package com.airpay.money.hooks;

import com.airpay.money.data.MoneyExcel;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/** Binds the running scenario to its module sheet and test-case row. */
public class MoneyExcelHook {

    @Before(order = 5)
    public void bindSheet(Scenario scenario) {
        MoneyExcel.bind(scenario.getSourceTagNames());
    }
}
