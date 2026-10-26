package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Credit card details sheet after card type, terms, and Proceed.
 * Dump {@code uidump-apply-cc-04} 2.1.3. Phone is prefilled. Do not tap Submit.
 */
public class CreditCardDetailsSheet extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Provide Your Details']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='mobile']")
    private WebElement mobile;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='name']")
    private WebElement name;

    @AndroidFindBy(xpath = "//android.widget.EditText[@resource-id='pincode']")
    private WebElement pincode;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Provide Your Details']/following::android.widget.CheckBox[1]")
    private WebElement consent;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Privacy Policy')]")
    private WebElement privacyPolicy;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Terms of use')]")
    private WebElement termsOfUse;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Disclaimer']")
    private WebElement disclaimer;

    @AndroidFindBy(xpath = "//android.widget.Button[@text='Submit']")
    private WebElement submit;

    public CreditCardDetailsSheet(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Provide Your Details") && sourceContains("Submit");
    }
}
