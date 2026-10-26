package com.airpay.capital.screens.apply;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class SalariedIncomeScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Work Details')]")
    @iOSXCUITFindBy(accessibility = "Work Details")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'monthly salary') or contains(@hint,'Monthly In-hand')]")
    @iOSXCUITFindBy(accessibility = "monthly salary")
    private WebElement monthlySalaryField;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Continue')]")
    @iOSXCUITFindBy(accessibility = "Continue")
    private WebElement continueButton;

    public SalariedIncomeScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Work Details", "monthly salary", "Monthly In-hand");
    }

    public void fillIfDisplayed() {
        if (!isDisplayed()) {
            return;
        }
        WebElement salary = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'monthly salary') or contains(@hint,'Monthly In-hand')]"));
        if (salary != null) {
            type(salary, "75000");
        }
        WebElement emi = firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Existing EMI')]"));
        if (emi != null) {
            type(emi, "0");
        }
        tapContinueIfPresent();
    }

    public void tapContinueIfPresent() {
        tapFirst(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Continue')]"));
        sleep(500);
    }
}
