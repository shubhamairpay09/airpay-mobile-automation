package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * In-app INVESTMENT AMOUNT sheet on {@link FdPlanBookingScreen}. Live dump 2026-09-21, build 2.0.124.
 */
public class FdAmountSheetScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.EditText")
    private WebElement amountField;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Save Changes']")
    private WebElement saveChanges;

    public FdAmountSheetScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='INVESTMENT AMOUNT']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Save Changes']")) != null;
    }

    public void enterAmount(String amount) {
        type(amountField, amount);
    }

    public void save() {
        saveChanges.click();
    }
}
