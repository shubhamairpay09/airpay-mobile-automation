package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * FinSight Financial Health Check — Personal Details (manual capture 2026-09-09).
 * Full Name, Date of Birth, Gender (Male/Female/Other), Dependant, Pincode, City, State, Next.
 */
public class PersonalDetailsScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Personal Details\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Full Name\")")
    private WebElement fullNameLabel;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Date of Birth\")")
    private WebElement dobLabel;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Male\")")
    private WebElement genderMale;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Female\")")
    private WebElement genderFemale;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Other\")")
    private WebElement genderOther;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Dependant\")")
    private WebElement dependant;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Pincode\")")
    private WebElement pincode;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"City\")")
    private WebElement city;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"State\")")
    private WebElement state;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Next\")")
    private WebElement nextButton;

    public PersonalDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Personal Details") && sourceContains("Pincode", "Next");
    }

    public boolean tapNext() {
        return tapFirst(Duration.ofSeconds(8),
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Next\")"),
                AppiumBy.accessibilityId("Next"),
                MobileWaits.xpath("//*[(@text='Next' or @content-desc='Next') and @clickable='true']"));
    }
}
