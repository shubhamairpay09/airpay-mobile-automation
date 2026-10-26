package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Health insurance quote loader. Dump {@code uidump-ins-health-wait} 2.1.3. */
public class HealthInsuranceScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Health Insurance']/preceding-sibling::android.widget.Button")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Health Insurance']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Please Wait']")
    private WebElement pleaseWait;

    public HealthInsuranceScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Health Insurance") && sourceContains("Please Wait");
    }
}
