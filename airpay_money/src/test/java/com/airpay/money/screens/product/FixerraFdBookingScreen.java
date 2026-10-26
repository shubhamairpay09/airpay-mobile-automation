package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Fixerra FD booking WebView reached from "Book FD at ..." on {@link FdPlanBookingScreen}.
 * Live dumps 2026-09-21, build 2.0.124. Holds the tenure list, payout and renewal sheets, the annual-yield
 * explainer and the penalty-free sheet; "Book FD" leads to {@link FixerraAadhaarVerificationScreen}.
 */
public class FixerraFdBookingScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Suryoday SF Bank']")
    private WebElement bankTitle;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='View All Tenures']")
    private WebElement viewAllTenures;

    @AndroidFindBy(xpath = "//android.widget.TextView[starts-with(@text,'₹ ')]")
    private WebElement amountDisplay;

    @AndroidFindBy(xpath = "//android.widget.Button[starts-with(@text,'Book FD at')]")
    private WebElement bookFd;

    public FixerraFdBookingScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(8),
                MobileWaits.xpath("//android.widget.Button[starts-with(@text,'Book FD at')]"),
                MobileWaits.xpath("//android.widget.TextView[@text='Powered by Fixerra']")) != null;
    }

    public void openAllRates() {
        viewAllTenures.click();
    }

    public boolean sortRatesBy(String sort) {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath(
                "//android.widget.Button[@text=" + xpathLiteral(sort) + "]"));
    }

    public boolean selectRate(String tenure, String rate) {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath(
                "//android.widget.Button[contains(@text,'" + tenure + "') and contains(@text,'" + rate + "')]"));
    }

    public boolean saveSheet() {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath("//android.widget.Button[@text='Save Changes']"));
    }

    public boolean closeSheet() {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath("//android.widget.Button[@content-desc='Close']"));
    }

    public void focusAmount() {
        amountDisplay.click();
    }

    public boolean selectPresetAmount(String label) {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath("//android.widget.Button[@text='" + label + "']"));
    }

    public boolean hasMinimumAmountError() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.TextView[contains(@text,'Minimum investment amount')]")) != null;
    }

    public boolean openInterestPayout() {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath("//android.widget.TextView[starts-with(@text,'Interest Payout')]"));
    }

    public boolean openRenewalPreference() {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath("//android.widget.TextView[starts-with(@text,'Renewal Preference')]"));
    }

    public boolean openAnnualYieldInfo() {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath("//android.widget.ImageView[@content-desc='Annual yield info']"));
    }

    public boolean openPenaltyFreeBanner() {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath("//android.widget.Button[contains(@text,'penalty-free')]"));
    }

    public void bookFd() {
        bookFd.click();
    }
}
