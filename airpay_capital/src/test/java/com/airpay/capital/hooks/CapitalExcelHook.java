package com.airpay.capital.hooks;

import com.airpay.capital.data.CapitalExcel;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/** Binds the running scenario to its Capital Excel sheet and test-case row. */
public class CapitalExcelHook {

    @Before(order = 5)
    public void bindSheet(Scenario scenario) {
        CapitalExcel.bind(scenario.getSourceTagNames());
    }
}
