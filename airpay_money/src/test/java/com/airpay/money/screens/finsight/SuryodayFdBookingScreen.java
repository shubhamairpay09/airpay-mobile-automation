package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * Suryoday Small Finance Bank FD booking (manual capture 2026-09-09).
 * Amount, tenure, payout, Book FD at 8.25%.
 */
public class SuryodayFdBookingScreen extends BaseScreen {

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Suryoday Small Finance Bank\")")
    private WebElement heading;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Select goal tenure\")")
    private WebElement selectTenure;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Most Popular\")")
    private WebElement mostPopular;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Interest Payout\")")
    private WebElement interestPayout;

    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Book FD\")")
    private WebElement bookFd;

    public SuryodayFdBookingScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Suryoday") && sourceContains("Book FD");
    }
}
