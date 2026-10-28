package com.airpay.vyaapaar.screens.khata;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / Khata — Khata. Module khata. */
public class HomeCreditBalanceKhataScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-khata";

    public static final String KHATA = "//android.widget.TextView[@text='Khata']";

    @AndroidFindBy(xpath = KHATA)
    private WebElement khata;

    public HomeCreditBalanceKhataScreen(TestContext context) {
        super(context);
    }
}
