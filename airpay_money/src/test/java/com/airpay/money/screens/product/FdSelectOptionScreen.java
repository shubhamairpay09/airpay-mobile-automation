package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** FD tenure picker. Dump {@code uidump-fd-05-Select-FD-Option} 2.1.3. */
public class FdSelectOptionScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Select FD Option')]")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='All']")
    private WebElement all;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='0-1 Year']")
    private WebElement range0to1;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='1-3 Years']")
    private WebElement range1to3;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='3-5 Years']")
    private WebElement range3to5;

    public FdSelectOptionScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Select FD Option") && sourceContains("0-1 Year");
    }
}
