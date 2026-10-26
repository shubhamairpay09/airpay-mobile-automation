package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Home loan employment form. Dump {@code uidump-deep-07-after-address} 2.1.3. */
public class HomeLoanWorkDetailsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Work Details']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Enter your monthly in hand salary']")
    private WebElement salary;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Enter your existing EMI']")
    private WebElement existingEmi;

    @AndroidFindBy(xpath = "//android.view.View[@hint='Select salary mode']")
    private WebElement salaryMode;

    @AndroidFindBy(xpath = "//android.view.View[@hint='Select employer type']")
    private WebElement employerCategory;

    @AndroidFindBy(xpath = "//android.view.View[@hint='Years']")
    private WebElement years;

    @AndroidFindBy(xpath = "//android.view.View[@hint='Months']")
    private WebElement months;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Continue']")
    private WebElement continueButton;

    public HomeLoanWorkDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Work Details") && sourceContains("Monthly In-hand Salary");
    }
}
