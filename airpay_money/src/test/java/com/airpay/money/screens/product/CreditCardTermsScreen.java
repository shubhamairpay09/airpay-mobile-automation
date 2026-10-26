package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit card terms web view. Dump {@code uidump-credit-cc-terms} 2.1.3. Do not tap Proceed. */
public class CreditCardTermsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Terms and Conditions']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Terms and Conditions']/android.widget.Button")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.webkit.WebView")
    private WebElement webView;

    public CreditCardTermsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Terms and Conditions");
    }
}
