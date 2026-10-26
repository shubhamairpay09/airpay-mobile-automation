package com.airpay.mobile.utilities.hooks;

import com.airpay.mobile.utilities.accessibility.A11yScanSession;
import com.airpay.mobile.utilities.accessibility.A11yScorecardWriter;
import com.airpay.mobile.utilities.android.PermissionDialogHandler;
import com.airpay.mobile.utilities.config.ConfigLoader;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.driver.AppiumManager;
import com.airpay.mobile.utilities.reporting.ScreenshotCapture;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.time.Duration;

public class BaseHooks {

    private static final Logger LOG = LogManager.getLogger(BaseHooks.class);

    private final TestContext testContext;
    private AppiumManager appiumManager;

    public BaseHooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {
        testContext.setFailureScreenshotAttached(false);
        testContext.setConfig(ConfigLoader.load());
        if (scenario.getSourceTagNames().contains("@capture")
                && !scenario.getSourceTagNames().contains("@deep")) {
            LOG.info("Saved-dump capture | {} — device session is not opened", scenario.getName());
            return;
        }
        appiumManager = AppiumManager.open(testContext.config());
        testContext.setDriver(appiumManager.driver());
        if (appiumManager.createdNow()) {
            try {
                PermissionDialogHandler.allowAll(testContext.driver(), Duration.ofSeconds(10));
            } catch (Exception e) {
                LOG.debug("Launch permission pass skipped: {}", e.toString());
            }
        }
        LOG.info("Scenario start | {} | env={} | product={} | udid={}",
                scenario.getName(),
                testContext.config().environment(),
                testContext.config().product(),
                testContext.config().get("android.udid", ""));
    }

    @Before(order = Integer.MAX_VALUE)
    public void allureLabels(Scenario scenario) {
        try {
            var config = testContext.config();
            Allure.label("environment", config.environment());
            Allure.label("product", config.product());
            Allure.label("platform", "android");
            Allure.parameter("environment", config.environment());
            Allure.parameter("product", config.product());
            Allure.parameter("udid", config.get("android.udid", ""));
        } catch (Exception e) {
            LOG.debug("Allure labels skipped: {}", e.toString());
        }
    }

    @After(order = Integer.MAX_VALUE)
    public void afterFailure(Scenario scenario) {
        if (!scenario.isFailed() || testContext.isFailureScreenshotAttached()) {
            return;
        }
        if (accessibilityScenario(scenario)) {
            LOG.info("Failure screenshot skipped for accessibility scenario | {}", scenario.getName());
            return;
        }
        try {
            Path shot = ScreenshotCapture.capture(testContext.driver(), testContext.config(), "failure-" + scenario.getName());
            if (shot != null) {
                LOG.info("Failure screenshot | {}", shot);
            }
            testContext.setFailureScreenshotAttached(true);
            String source = testContext.driver().getPageSource();
            if (source != null) {
                Allure.addAttachment("page-source", "text/xml", source, ".xml");
            }
        } catch (Exception e) {
            LOG.warn("Failure capture skipped: {}", e.toString());
        }
    }

    @After(order = 1)
    public void writeA11yReportsIfAny(Scenario scenario) {
        if (A11yScanSession.results().isEmpty() || testContext.config() == null) {
            return;
        }
        try {
            var config = testContext.config();
            String product = config.product();
            String appPackage = config.get(product + ".app.package", product);
            String title = config.get(product + ".app.name", product + " Android");
            Path dir = A11yScorecardWriter.resolveReportDir(config);
            A11yScorecardWriter.write(
                    config,
                    appPackage,
                    A11yScanSession.results(),
                    dir,
                    product,
                    title);
        } catch (Exception e) {
            LOG.warn("A11y report write skipped: {}", e.toString());
        }
    }

    @After(order = 0)
    public void keepSession(Scenario scenario) {
        LOG.info("Scenario end | {} | status={}", scenario.getName(), scenario.getStatus());
    }

    @AfterAll
    public static void closeSession() {
        AppiumManager.close();
    }

    private static boolean accessibilityScenario(Scenario scenario) {
        var tags = scenario.getSourceTagNames();
        return tags.contains("@a11y") || tags.contains("@capture") || tags.contains("@deep");
    }
}
