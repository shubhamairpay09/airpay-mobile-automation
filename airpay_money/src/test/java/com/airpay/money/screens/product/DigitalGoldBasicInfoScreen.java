package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Digital Gold KYC — Personal details. Dump {@code uidump-gold-07-basic-info} 2.1.3 2026-09-25.
 * Back and info icons have no content-desc.
 */
public class DigitalGoldBasicInfoScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Personal details']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Full name']")
    private WebElement fullName;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint=\"Father's name\"]")
    private WebElement fatherName;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint=\"Mother's name\"]")
    private WebElement motherName;

    @AndroidFindBy(xpath = "//*[@hint='Date of birth']")
    private WebElement dateOfBirth;

    @AndroidFindBy(xpath = "//*[@hint='Email']")
    private WebElement email;

    @AndroidFindBy(xpath = "//*[@hint='Mobile Number']")
    private WebElement mobileNumber;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Gender']")
    private WebElement gender;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Male']")
    private WebElement genderMale;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Female']")
    private WebElement genderFemale;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Other']")
    private WebElement genderOther;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Marital status')]")
    private WebElement maritalStatus;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Employment type')]")
    private WebElement employmentType;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Update']")
    private WebElement update;

    public DigitalGoldBasicInfoScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Personal details") && sourceContains("Gender")
                && sourceContains("Marital status");
    }

    public void fillDummyParents() {
        WebElement father = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.EditText[@hint=\"Father's name\"]"));
        if (father != null) {
            type(father, "Test Father");
        }
        WebElement mother = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.EditText[@hint=\"Mother's name\"]"));
        if (mother != null) {
            type(mother, "Test Mother");
        }
    }

    public void selectGender(String label) {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc=" + xpathLiteral(label) + "]"));
    }

    public void openMaritalStatus() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Marital status')]"));
    }
}
