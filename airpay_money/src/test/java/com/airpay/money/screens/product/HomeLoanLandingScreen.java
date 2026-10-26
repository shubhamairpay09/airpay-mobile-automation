package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Home Loan landing. Dump {@code uidump-ins-life-07-Home-Loan} 2.1.3. Do not tap Apply Now. */
public class HomeLoanLandingScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Home Loan']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Up to')]")
    private WebElement amount;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Salaried')]")
    private WebElement audience;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Eligibility')]")
    private WebElement eligibility;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Interest Rate')]")
    private WebElement interestRate;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Loan Amount')]")
    private WebElement loanAmount;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Processing Fee')]")
    private WebElement fee;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Apply Now']")
    private WebElement applyNow;

    public HomeLoanLandingScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Home Loan") && sourceContains("Apply Now");
    }
}
