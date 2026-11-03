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

/** Step-by-step LMS sign-in in mobile Chrome: email, password, Mobile OTP. */
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

    public void openSignIn() {
        signIn.open(config.require("capital.lms.url"));
    }

    public void enterEmail() {
        signIn.enterEmail(config.require("capital.lms.email"));
    }

    public void enterPassword() {
        signIn.enterPassword(config.require("capital.lms.password"));
    }

    public void tapSignIn() {
        signIn.tapSignIn();
    }

    public boolean otpDialogShown() {
        return signIn.waitForOtpDialog(Duration.ofSeconds(25));
    }

    public void enterOtp() {
        signIn.enterOtp(config.get("capital.lms.otp", "1234").trim());
    }

    public void submitOtp() {
        signIn.submitOtp();
    }

    public void waitUntilSignedIn() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(45))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.urlContains("/mis"),
                            ExpectedConditions.urlContains("dsa-agency"),
                            ExpectedConditions.urlContains("lead"),
                            ExpectedConditions.not(ExpectedConditions.urlContains("/signin"))));
        } catch (Exception e) {
            throw new IllegalStateException(
                    "LMS stayed on sign-in after OTP. url=" + driver.getCurrentUrl()
                            + " otpDialog=" + signIn.otpDialogVisible()
                            + " body=" + signIn.visibleText(), e);
        }
        LOG.info("Signed in | url={}", driver.getCurrentUrl());
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    public DsaAgencyPage openDsaAgency() {
        dsaAgency.openFromMenuOrDirect();
        return dsaAgency;
    }

    public DsaAgencyPage dsaAgency() {
        return dsaAgency;
    }
}
