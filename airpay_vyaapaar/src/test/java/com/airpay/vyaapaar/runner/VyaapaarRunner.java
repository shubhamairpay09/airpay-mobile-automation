package com.airpay.vyaapaar.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "airpay_vyaapaar/src/test/resources/features",
        glue = {
                "com.airpay.vyaapaar",
                "com.airpay.mobile.utilities.context",
                "com.airpay.mobile.utilities.hooks"
        },
        plugin = {
                "pretty",
                "html:target/vyaapaar/cucumber-reports/cucumber.html",
                "json:target/vyaapaar/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class VyaapaarRunner extends AbstractTestNGCucumberTests {

    static {
        System.setProperty("airpay.product", "vyaapaar");
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
