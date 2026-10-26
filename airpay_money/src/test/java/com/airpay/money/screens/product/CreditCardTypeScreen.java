package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Choose secured vs unsecured card. Dump {@code uidump-ins-life-05-Choose-Your-Card-Type} 2.1.3. Do not tap Proceed. */
public class CreditCardTypeScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Choose Your Card Type']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Secured Credit Card')]")
    private WebElement secured;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Unsecured Credit Card')]")
    private WebElement unsecured;

    @AndroidFindBy(xpath = "//android.widget.CheckBox")
    private WebElement termsCheckbox;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'I agree to the')]")
    private WebElement agreeText;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Terms and Conditions']")
    private WebElement terms;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Proceed']")
    private WebElement proceed;

    public CreditCardTypeScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Choose Your Card Type") && sourceContains("Secured Credit Card");
    }
}
