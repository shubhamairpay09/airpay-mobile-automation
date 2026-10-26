package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** 2-wheeler quote loader. Dump {@code uidump-ins-2w-wait} 2.1.3. The quote UI stays on Please Wait. */
public class TwoWheelerInsuranceScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='2-wheeler insurance']/preceding-sibling::android.widget.Button")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='2-wheeler insurance']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Please Wait']")
    private WebElement pleaseWait;

    public TwoWheelerInsuranceScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("2-wheeler insurance");
    }
}
