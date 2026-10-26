package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Home loan extra details. Dump {@code uidump-deep-26-after-continue} 2.1.3. */
public class HomeLoanAdditionalDetailsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Additional Details']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Enter your current work experience']")
    private WebElement monthsEmployed;

    @AndroidFindBy(xpath = "//android.view.View[@hint='Select your property type']")
    private WebElement propertyType;

    @AndroidFindBy(xpath = "//android.view.View[@hint='Select local body']")
    private WebElement localBody;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Enter value']")
    private WebElement propertyValue;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Enter your preferred loan tenure']")
    private WebElement tenureMonths;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Enter your current designation']")
    private WebElement designation;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Check Eligibility']")
    private WebElement checkEligibility;

    public HomeLoanAdditionalDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Additional Details") && sourceContains("Check Eligibility");
    }
}
