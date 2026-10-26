package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** ClearTax planning web view opened from Start Now. Dump {@code uidump-left-Start-Now-01} 2.1.3. */
public class TaxPlanningScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Tax Planning']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Tax Planning']/android.widget.Button")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.webkit.WebView")
    private WebElement webView;

    public TaxPlanningScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Tax Planning");
    }
}
