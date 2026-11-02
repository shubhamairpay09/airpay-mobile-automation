package com.airpay.capital.lms.flows;

import com.airpay.capital.lms.pages.DsaAgencyPage;
import com.airpay.capital.lms.pages.LmsSignInPage;
import com.airpay.mobile.utilities.config.FrameworkConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Email + password + staging Mobile OTP (capital.lms.otp / PIN). */
public class LmsLoginFlow {

    private static final Logger LOG = LogManager.getLogger(LmsLoginFlow.class);

    private final WebDriver driver;
    private final FrameworkConfig config;
    private final LmsSignInPage signIn;
    private final DsaAgencyPage dsaAgency;

    public LmsLoginFlow(WebDriver driver, FrameworkConfig config) {
        this.driver = driver;
        this.config = config;
        this.signIn = new LmsSignInPage(driver);
        this.dsaAgency = new DsaAgencyPage(driver);
    }

    public void login() {
        String url = config.require("capital.lms.url");
        String email = config.require("capital.lms.email");
        String password = config.require("capital.lms.password");
        String otp = config.get("capital.lms.otp", "1234").trim();
        LOG.info("LMS login as {}", email);
        signIn.open(url);
        signIn.signIn(email, password);
        signIn.enterOtpAndSubmit(otp);
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.or(
                        ExpectedConditions.urlContains("/mis"),
                        ExpectedConditions.urlContains("dsa-agency"),
                        ExpectedConditions.urlContains("lead")));
    }

    public DsaAgencyPage openDsaAgency() {
        if (!driver.getCurrentUrl().contains("dsa-agency")) {
            dsaAgency.openFromMenu();
        } else {
            dsaAgency.waitUntilLoaded();
        }
        return dsaAgency;
    }

    public DsaAgencyPage dsaAgency() {
        return dsaAgency;
    }
}
