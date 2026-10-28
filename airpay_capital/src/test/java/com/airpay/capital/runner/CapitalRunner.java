package com.airpay.capital.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "airpay_capital/src/test/resources/features",
        glue = {
                "com.airpay.capital",
                "com.airpay.mobile.utilities.context",
                "com.airpay.mobile.utilities.hooks"
        },
        plugin = {
                "pretty",
                "html:target/capital/cucumber-reports/cucumber.html",
                "json:target/capital/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class CapitalRunner extends AbstractTestNGCucumberTests {

    static {
        System.setProperty("airpay.product", "capital");
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
