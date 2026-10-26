package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Home loan address step after Resume Application.
 * Dump {@code uidump-apply-hl-resume} 2.1.3.
 * Basic details, including mobile, were already completed. Do not tap Submit.
 */
public class HomeLoanAddressScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Address details']/preceding-sibling::android.widget.Button")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Address details']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Address Details']")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Enter your current address information']")
    private WebElement subtitle;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Pincode*']")
    private WebElement pincode;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Line address 1*']")
    private WebElement line1;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Line address 2']")
    private WebElement line2;

    @AndroidFindBy(xpath = "//android.view.View[@hint='City*']")
    private WebElement city;

    @AndroidFindBy(xpath = "//android.view.View[@hint='State*']")
    private WebElement state;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Submit']")
    private WebElement submit;

    public HomeLoanAddressScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Address details") && sourceContains("Pincode*");
    }
}
