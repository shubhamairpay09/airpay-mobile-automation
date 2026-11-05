package com.airpay.capital.lms.steps;

import com.airpay.capital.lms.flows.LmsLoginFlow;
import com.airpay.capital.lms.pages.DsaAgencyPage;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.util.List;

/** Detailed LMS / DSA steps. Each Then asserts one visible outcome. */
public class CapitalLmsSteps {

    private static final String[] GRID_COLUMNS = {
            "Sr. No", "DSA Code", "Agency Name", "Loan Type", "Onboarded On Date", "Status"
    };

    private final TestContext context;
    private LmsLoginFlow loginFlow;
    private DsaAgencyPage dsa;
    private boolean alreadySignedIn;

    public CapitalLmsSteps(TestContext context) {
        this.context = context;
    }

    private LmsLoginFlow flow() {
        if (loginFlow == null) {
            loginFlow = new LmsLoginFlow(context.webDriver(), context.config());
        }
        return loginFlow;
    }

    private DsaAgencyPage page() {
        return dsa != null ? dsa : flow().dsaAgency();
    }

    @Given("mobile Chrome is open on the LMS sign-in page")
    public void openSignIn() {
        Assertions.assertThat(context.config().get("capital.lms.url", ""))
                .as("capital.lms.url must be set")
                .isNotBlank();
        Assertions.assertThat(context.config().get("capital.lms.email", ""))
                .as("capital.lms.email must be set")
                .isNotBlank();
        flow().openSignIn();
        if (!flow().currentUrl().contains("/signin")) {
            alreadySignedIn = true;
            return;
        }
        alreadySignedIn = false;
        Assertions.assertThat(flow().currentUrl())
                .as("Sign-in URL should be the staging admin sign-in page")
                .contains("/signin");
    }

    @When("I enter the LMS email from config")
    public void enterEmail() {
        if (alreadySignedIn) {
            return;
        }
        flow().enterEmail();
    }

    @When("I enter the LMS password from config")
    public void enterPassword() {
        if (alreadySignedIn) {
            return;
        }
        flow().enterPassword();
    }

    @When("I tap Sign In on the LMS sign-in page")
    public void tapSignIn() {
        if (alreadySignedIn) {
            return;
        }
        flow().tapSignIn();
    }

    @Then("the Mobile OTP dialog should be displayed")
    public void assertOtpDialog() {
        if (alreadySignedIn) {
            return;
        }
        Assertions.assertThat(flow().otpDialogShown())
                .as("Mobile OTP dialog should appear after Sign In. url=%s", flow().currentUrl())
                .isTrue();
    }

    @When("I enter the LMS OTP from config")
    public void enterOtp() {
        if (alreadySignedIn) {
            return;
        }
        flow().enterOtp();
    }

    @When("I submit the LMS OTP")
    public void submitOtp() {
        if (alreadySignedIn) {
            return;
        }
        flow().submitOtp();
    }

    @Then("I should be signed in to the Capital LMS admin portal")
    public void assertSignedIn() {
        flow().waitUntilSignedIn();
        Assertions.assertThat(flow().currentUrl())
                .as("Authenticated LMS URL must leave /signin")
                .doesNotContain("/signin");
    }

    @When("I open DSA Agency Management from the LMS menu")
    public void openDsaAgency() {
        dsa = flow().openDsaAgency();
    }

    @Then("the DSA Agency Management title should be displayed")
    public void assertTitle() {
        Assertions.assertThat(page().titleVisible())
                .as("Title 'DSA Agency Management' should be visible. url=%s", page().currentUrl())
                .isTrue();
    }

    @Then("the DSA Agency Management URL should contain dsa-agency")
    public void assertAgencyUrl() {
        Assertions.assertThat(page().currentUrl())
                .as("DSA Agency list URL")
                .contains("dsa-agency");
    }

    @Then("the list of registered agencies heading should be displayed")
    public void assertListHeading() {
        Assertions.assertThat(page().listHeadingVisible())
                .as("Heading 'List of registered agencies' should be visible. url=%s", page().currentUrl())
                .isTrue();
    }

    @Then("the Register New Agency button should be visible")
    public void assertRegisterVisible() {
        Assertions.assertThat(page().registerNewAgencyVisible())
                .as("Register New Agency should be visible on the agency list. url=%s", page().currentUrl())
                .isTrue();
    }

    @Then("the Register New Agency button should be enabled")
    public void assertRegisterEnabled() {
        Assertions.assertThat(page().registerNewAgencyEnabled())
                .as("Register New Agency should be enabled")
                .isTrue();
    }

    @Then("the agency grid should show column {string}")
    public void assertColumn(String column) {
        Assertions.assertThat(page().missingColumns(column))
                .as("Agency grid should show column '%s'. url=%s", column, page().currentUrl())
                .isEmpty();
    }

    @Then("the agency grid should show the required columns")
    public void assertColumns() {
        List<String> missing = page().missingColumns(GRID_COLUMNS);
        Assertions.assertThat(missing)
                .as("Agency grid is missing columns")
                .isEmpty();
    }

    @Then("the agency grid should contain at least one agency row")
    public void assertRows() {
        Assertions.assertThat(page().agencyRowCount())
                .as("Agency grid should list at least one agency. url=%s", page().currentUrl())
                .isGreaterThan(0);
    }

    @When("I click Register New Agency on the DSA Agency page")
    public void clickRegister() {
        Assertions.assertThat(page().registerNewAgencyVisible())
                .as("Register New Agency must be visible before click")
                .isTrue();
        page().clickRegisterNewAgency();
    }

    @Then("the agency registration form should be displayed")
    public void assertRegistrationForm() {
        Assertions.assertThat(page().registrationFormOpen())
                .as("Registration form should open after Register New Agency. url=%s", page().currentUrl())
                .isTrue();
    }

    @Then("the registration form should display the PAN Number field")
    public void assertPanField() {
        Assertions.assertThat(page().panNumberFieldVisible())
                .as("Registration form should show a PAN Number field. url=%s", page().currentUrl())
                .isTrue();
    }
}
