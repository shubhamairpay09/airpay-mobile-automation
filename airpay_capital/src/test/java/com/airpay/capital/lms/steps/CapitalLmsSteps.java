package com.airpay.capital.lms.steps;

import com.airpay.capital.lms.flows.LmsLoginFlow;
import com.airpay.capital.lms.pages.DsaAgencyPage;
import com.airpay.mobile.utilities.context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/** Capital LMS / DSA Agency admin portal steps (Selenium). */
public class CapitalLmsSteps {

    private final TestContext context;
    private LmsLoginFlow loginFlow;
    private DsaAgencyPage dsa;

    public CapitalLmsSteps(TestContext context) {
        this.context = context;
    }

    private LmsLoginFlow flow() {
        if (loginFlow == null) {
            loginFlow = new LmsLoginFlow(context.webDriver(), context.config());
        }
        return loginFlow;
    }

    @Given("I am logged in to the Capital LMS admin portal")
    public void loggedInToLms() {
        Assertions.assertThat(context.config().get("capital.lms.email", ""))
                .as("capital.lms.email must be set in config/secrets.properties")
                .isNotBlank();
        flow().login();
        Assertions.assertThat(context.webDriver().getCurrentUrl())
                .as("After LMS OTP verify, URL should leave the sign-in page")
                .doesNotContain("/signin");
    }

    @When("I open DSA Agency Management from the LMS menu")
    public void openDsaAgency() {
        dsa = flow().openDsaAgency();
    }

    @Then("the DSA Agency Management page should be displayed")
    public void assertDsaPage() {
        Assertions.assertThat(dsa != null ? dsa.isDisplayed() : flow().dsaAgency().isDisplayed())
                .as("DSA Agency Management page should be displayed with the agencies list")
                .isTrue();
        Assertions.assertThat(context.webDriver().getPageSource())
                .as("Page should show List of registered agencies")
                .contains("List of registered agencies");
    }

    @Then("the DSA agency grid should show the required columns")
    public void assertColumns() {
        DsaAgencyPage page = dsa != null ? dsa : flow().dsaAgency();
        Assertions.assertThat(page.gridShowsRequiredColumns())
                .as("Grid should show Sr. No., DSA Code, Agency Name, Loan Type, Onboarded On Date, and Status")
                .isTrue();
        Assertions.assertThat(page.hasAgencyRows())
                .as("At least one agency row should be present in the grid")
                .isTrue();
    }

    @When("I click Register New Agency on the DSA Agency page")
    public void clickRegister() {
        DsaAgencyPage page = dsa != null ? dsa : flow().dsaAgency();
        Assertions.assertThat(page.registerNewAgencyVisible())
                .as("Register New Agency button should be visible")
                .isTrue();
        page.clickRegisterNewAgency();
    }

    @Then("the agency registration form should open")
    public void assertRegistrationForm() {
        new WebDriverWait(context.webDriver(), Duration.ofSeconds(15))
                .until(ExpectedConditions.or(
                        ExpectedConditions.urlContains("register"),
                        ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//*[contains(normalize-space(),'PAN') or contains(normalize-space(),'Register')]"))));
        String src = context.webDriver().getPageSource();
        Assertions.assertThat(src.contains("PAN") || src.toLowerCase().contains("register"))
                .as("Registration form should open with PAN / Register fields")
                .isTrue();
    }

    @Then("the registration form should display the PAN Number field")
    public void assertPanField() {
        String src = context.webDriver().getPageSource();
        Assertions.assertThat(src.contains("PAN") || src.contains("Pan"))
                .as("Registration form should display the PAN Number field")
                .isTrue();
    }
}
