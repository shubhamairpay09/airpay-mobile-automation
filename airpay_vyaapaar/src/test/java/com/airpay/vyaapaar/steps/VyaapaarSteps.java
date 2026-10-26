package com.airpay.vyaapaar.steps;

import com.airpay.mobile.utilities.accessibility.A11yFinding;
import com.airpay.mobile.utilities.accessibility.A11yPageScanResult;
import com.airpay.mobile.utilities.accessibility.A11yScanSession;
import com.airpay.mobile.utilities.config.ConfigLoader;
import com.airpay.mobile.utilities.config.ProjectPaths;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.vyaapaar.flows.VyaapaarDeepScanFlow;
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
import java.util.stream.Stream;

public class VyaapaarSteps {

    private final TestContext context;
    private VyaapaarDeepScanFlow flow;

    public VyaapaarSteps(TestContext context) {
        this.context = context;
    }

    @Given("the airpay vyaapaar app is installed on the device")
    public void appInstalled() {
        Assertions.assertThat(context.config().require("vyaapaar.app.package")).isNotBlank();
        Assertions.assertThat(context.config().require("vyaapaar.test.mobile")).isNotBlank();
    }

    @When("I deep-scan every airpay vyaapaar screen once")
    public void deepScan() {
        flow = new VyaapaarDeepScanFlow(context);
        flow.scanEverythingOnce();
    }

    @When("I login to airpay vyaapaar with username and password")
    public void loginWithPassword() {
        flow = new VyaapaarDeepScanFlow(context);
        flow.loginWithUsernameAndPassword();
    }

    @Then("scorecards and page xpaths are written for airpay vyaapaar")
    public void reportsWritten() {
        Assertions.assertThat(A11yScanSession.results()).isNotEmpty();
        Assertions.assertThat(flow).isNotNull();
        Assertions.assertThat(flow.inventory().hasPages()).isTrue();
        Path html = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y).resolve("index.html");
        Assertions.assertThat(html).exists();
        try (Stream<Path> features = Files.list(ConfigLoader.projectRoot()
                .resolve("airpay_vyaapaar/src/test/resources/features/a11y"))) {
            Assertions.assertThat(features.anyMatch(p -> p.getFileName().toString().endsWith(".feature"))).isTrue();
        } catch (Exception e) {
            Assertions.fail("Module feature files were not written: " + e);
        }
    }

    @Then("airpay vyaapaar screen {string} in module {string} has stored xpaths")
    public void screenStored(String screenId, String modulePackage) {
        Path dir = ConfigLoader.projectRoot()
                .resolve("airpay_vyaapaar/src/test/java/com/airpay/vyaapaar/screens")
                .resolve(modulePackage);
        Assertions.assertThat(dir).isDirectory();
        boolean found;
        try (Stream<Path> files = Files.list(dir)) {
            found = files.anyMatch(p -> {
                try {
                    return Files.readString(p).contains("PAGE_ID = \"" + screenId + "\"");
                } catch (Exception e) {
                    return false;
                }
            });
        } catch (Exception e) {
            found = false;
        }
        Assertions.assertThat(found).as("xpaths for " + screenId).isTrue();
    }

    @Then("critical and serious vyaapaar findings are within the configured gate")
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
            Assertions.fail("Vyaapaar scan gate failed: " + gated.size() + " findings. See target/vyaapaar/accessibility_testing_result/index.html");
        }
    }
}
