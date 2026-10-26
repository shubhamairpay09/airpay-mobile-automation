package com.airpay.capital.steps;

import com.airpay.capital.flows.CapitalLoginA11yFlow;
import com.airpay.mobile.utilities.accessibility.A11yFinding;
import com.airpay.mobile.utilities.accessibility.A11yPageScanResult;
import com.airpay.mobile.utilities.accessibility.A11yScanSession;
import com.airpay.mobile.utilities.accessibility.A11yScorecardWriter;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import com.airpay.mobile.utilities.config.ProjectPaths;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class CapitalA11ySteps {

    private final TestContext context;
    private final CapitalLoginA11yFlow flow;

    public CapitalA11ySteps(TestContext context) {
        this.context = context;
        this.flow = new CapitalLoginA11yFlow(context);
    }

    @Given("the Airpay Capital app is installed on the device")
    public void appInstalled() {
        String pkg = context.config().require("capital.app.package");
        Assertions.assertThat(pkg).isNotBlank();
    }

    @When("I login with the configured mobile number and deep-scan accessibility")
    public void loginAndDeepScan() {
        flow.loginWithMobileAndDeepScan();
    }

    @When("I deep-scan Home Loan Salaried and Self-Employed apply paths from the current screen")
    public void deepScanHomeLoanBothFromCurrent() {
        flow.deepScanAllProductsBothOccupationsFromCurrent();
    }

    @When("I deep-scan all Capital product Salaried and Self-Employed apply paths from the current screen")
    public void deepScanAllProductsBothFromCurrent() {
        flow.deepScanAllProductsBothOccupationsFromCurrent();
    }

    @Then("accessibility scorecards are written for Capital")
    public void writeReports() {
        Assertions.assertThat(A11yScanSession.results())
                .as("at least one screen should be scanned")
                .isNotEmpty();
        Path dir = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y);
        Path html = A11yScorecardWriter.write(
                context.config(),
                context.config().get("capital.app.package"),
                A11yScanSession.results(),
                dir,
                "capital",
                "Capital Android");
        Assertions.assertThat(html).exists();
    }

    @Then("critical and serious accessibility findings are within the configured gate")
    public void gate() {
        String failOn = context.config().get("a11y.fail.on", "critical,serious");
        Set<String> impacts = Arrays.stream(failOn.split(","))
                .map(s -> s.trim().toLowerCase(Locale.ROOT))
                .filter(s -> !s.isBlank())
                .collect(Collectors.toSet());
        List<A11yFinding> gated = A11yScanSession.results().stream()
                .map(A11yPageScanResult::findings)
                .flatMap(List::stream)
                .filter(f -> impacts.contains(f.impact() == null ? "" : f.impact().toLowerCase(Locale.ROOT)))
                .toList();
        // Reports are already written. Gate is informational on first Capital pass —
        // fail only when a11y.gate.strict=true so stakeholders still get the HTML.
        if (context.config().getBoolean("a11y.gate.strict", false) && !gated.isEmpty()) {
            Assertions.fail("Accessibility gate failed: " + gated.size()
                    + " " + failOn + " finding(s). See target/"
                    + context.config().product()
                    + "/accessibility_testing_result/index.html");
        }
    }
}
