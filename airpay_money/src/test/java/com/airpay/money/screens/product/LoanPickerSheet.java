package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Bottom sheet used by home-loan dropdowns.
 * Dumps {@code uidump-deep-15-years}, {@code uidump-deep-19-salary-sheet},
 * {@code uidump-deep-21-employer-sheet}, {@code uidump-deep-27-property-type},
 * {@code uidump-deep-28-local-body} 2.1.3.
 */
public class LoanPickerSheet extends BaseScreen {

    public static final String SCRIM = "//android.view.View[@content-desc='Scrim']";
    public static final String OPTION = "//android.widget.Button[@content-desc='%s']";

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Scrim']")
    private WebElement scrim;

    @AndroidFindBy(xpath = "//android.widget.ScrollView[@scrollable='true']")
    private WebElement options;

    public LoanPickerSheet(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Scrim");
    }
}
