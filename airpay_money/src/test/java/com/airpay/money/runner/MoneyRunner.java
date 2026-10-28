package com.airpay.money.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "airpay_money/src/test/resources/features",
        glue = {
                "com.airpay.money",
                "com.airpay.mobile.utilities.context",
                "com.airpay.mobile.utilities.hooks"
        },
        plugin = {
                "pretty",
                "html:target/money/cucumber-reports/cucumber.html",
                "json:target/money/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        }
)
public class MoneyRunner extends AbstractTestNGCucumberTests {

    static {
        // IntelliJ often omits -Dairpay.product; defaults.properties points at capital.
        System.setProperty("airpay.product", "money");
    }

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
