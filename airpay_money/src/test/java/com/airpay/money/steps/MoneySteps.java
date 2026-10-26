package com.airpay.money.steps;

import com.airpay.money.flows.MoneyDeepScanFlow;
import com.airpay.mobile.utilities.accessibility.A11yFinding;
import com.airpay.mobile.utilities.accessibility.A11yPageRef;
import com.airpay.mobile.utilities.accessibility.A11yPageScanResult;
import com.airpay.mobile.utilities.accessibility.A11yScanSession;
import com.airpay.mobile.utilities.accessibility.A11yScorecardWriter;
import com.airpay.mobile.utilities.accessibility.AndroidA11yScanner;
import com.airpay.mobile.utilities.config.ProjectPaths;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class MoneySteps {

    private final TestContext context;
    private MoneyDeepScanFlow flow;

    public MoneySteps(TestContext context) {
        this.context = context;
    }

    private MoneyDeepScanFlow flow() {
        if (flow == null) {
            flow = new MoneyDeepScanFlow(context);
        }
        return flow;
    }

    @Given("the airpay money app is installed on the device")
    public void appInstalled() {
        Assertions.assertThat(context.config().require("money.app.package")).isNotBlank();
    }

    @When("I scan all airpay money screens from login")
    public void scanFromLogin() {
        flow().scanFromLoginThroughAllModules();
    }

    @When("I capture the current screen as {string}")
    public void captureCurrent(String name) {
        flow().scanCurrentOnly(slug(name), name);
    }

    @When("I capture saved hierarchy dump {string} as {string}")
    public void captureDump(String fileName, String label) {
        Path xml = ProjectPaths.reportsRoot().resolve("money").resolve("manual-capture").resolve(fileName);
        try {
            String src = Files.readString(xml);
            String id = slug(label);
            A11yPageRef ref = A11yPageRef.screen(id, "money", label, id);
            A11yPageScanResult result = AndroidA11yScanner.scanHierarchy(src, context.config(), ref);
            A11yScanSession.add(result);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to scan dump " + xml, e);
        }
    }

    private static String slug(String raw) {
        return raw.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-");
    }

    @Then("scorecards are written for airpay money")
    public void writeReports() {
        Assertions.assertThat(A11yScanSession.results()).isNotEmpty();
        Path dir = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y);
        Path html = A11yScorecardWriter.write(
                context.config(),
                context.config().get("money.app.package"),
                A11yScanSession.results(),
                dir,
                "money",
                "airpay money Android");
        Assertions.assertThat(html).exists();
    }

    @Then("critical and serious money findings are within the configured gate")
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
        if (context.config().getBoolean("a11y.gate.strict", false) && !gated.isEmpty()) {
            Assertions.fail("Money scan gate failed: " + gated.size() + " findings. See target/"
                    + context.config().product() + "/accessibility_testing_result/index.html");
        }
    }
}
