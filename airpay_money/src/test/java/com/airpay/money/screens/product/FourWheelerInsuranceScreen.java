package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** 4-wheeler quote loader. Dump {@code uidump-ins-4w-wait} 2.1.3. */
public class FourWheelerInsuranceScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'4-wheeler')]/preceding-sibling::android.widget.Button")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'4-wheeler')]")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Please Wait']")
    private WebElement pleaseWait;

    public FourWheelerInsuranceScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("4-wheeler");
    }
}
