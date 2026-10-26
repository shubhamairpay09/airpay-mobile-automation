package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** In-progress gold loan steps. Dump {@code uidump-apply-gl-02} 2.1.3. */
public class GoldLoanJourneyScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'gold loan journey is in progress')]")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Basic details']")
    private WebElement basicDetails;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Address details']")
    private WebElement addressDetails;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Income')]")
    private WebElement income;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Credit assessment']")
    private WebElement creditAssessment;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Personalised loan offers')]")
    private WebElement offers;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Resume Application']")
    private WebElement resumeApplication;

    public GoldLoanJourneyScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("gold loan journey") && sourceContains("Resume Application");
    }
}
