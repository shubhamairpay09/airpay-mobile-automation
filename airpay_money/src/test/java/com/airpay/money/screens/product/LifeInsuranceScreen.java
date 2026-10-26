package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Life insurance quote loader. Dump {@code uidump-ins-life-01} 2.1.3. The quote UI stays on Please Wait. */
public class LifeInsuranceScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Life Insurance']/preceding-sibling::android.widget.Button")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Life Insurance']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Please Wait']")
    private WebElement pleaseWait;

    public LifeInsuranceScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Life Insurance");
    }
}
