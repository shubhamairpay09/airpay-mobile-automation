package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds SIP Calculator — monthly amount, period, desired return, Start SIP.
 * Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsSipCalculatorScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='SIP Calculator']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Map the estimated returns of your SIP Mutual Fund')]")
    private WebElement intro;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Monthly Investment')]")
    private WebElement monthlyInvestment;

    @AndroidFindBy(xpath = "(//android.widget.SeekBar)[1]")
    private WebElement amountSlider;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Investment Period')]")
    private WebElement investmentPeriod;

    @AndroidFindBy(xpath = "(//android.widget.SeekBar)[2]")
    private WebElement periodSlider;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Desired Return')]")
    private WebElement desiredReturn;

    @AndroidFindBy(xpath = "(//android.widget.SeekBar)[3]")
    private WebElement returnSlider;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Invested Amount']")
    private WebElement investedAmount;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Est. Returns']")
    private WebElement estReturns;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Total Value']")
    private WebElement totalValue;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Total Return']")
    private WebElement totalReturn;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Start SIP']")
    private WebElement startSip;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Monthly Investment']")
    private WebElement sheetAmountTitle;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Edit Investment Period']")
    private WebElement sheetPeriodTitle;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Edit Desired Return']")
    private WebElement sheetReturnTitle;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    private WebElement sheetEdit;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Save Changes']")
    private WebElement saveChanges;

    public MutualFundsSipCalculatorScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='SIP Calculator']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Start SIP']")) != null;
    }
}
