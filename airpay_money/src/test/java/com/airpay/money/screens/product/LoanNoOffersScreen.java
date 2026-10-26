package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Empty offer result after Resume Application or Apply Now.
 * Dumps {@code uidump-apply-gl-resume} and {@code uidump-apply-lap-02} 2.1.3.
 */
public class LoanNoOffersScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='No Offers Available']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'couldn')]")
    private WebElement message;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Go to Dashboard']")
    private WebElement goToDashboard;

    public LoanNoOffersScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("No Offers Available");
    }
}
