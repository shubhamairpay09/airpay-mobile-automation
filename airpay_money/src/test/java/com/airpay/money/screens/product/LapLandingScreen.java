package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Loan Against Property landing. Dump {@code uidump-credit-lap-01} 2.1.3. Do not tap Apply Now. */
public class LapLandingScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Loan Against Property']/preceding-sibling::android.widget.Button")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Loan Against Property']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Up to')]")
    private WebElement amount;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'For Salaried')]")
    private WebElement audience;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'For Salaried')]/following-sibling::android.widget.ImageView[not(@content-desc)]")
    private WebElement heroImage;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Eligibility')]")
    private WebElement eligibility;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Interest Rate')]")
    private WebElement interestRate;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Loan Amount')]")
    private WebElement loanAmount;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Fee')]")
    private WebElement fee;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Apply Now']")
    private WebElement applyNow;

    public LapLandingScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Loan Against Property") && sourceContains("Apply Now");
    }
}
