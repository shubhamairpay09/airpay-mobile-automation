package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds Goal Calculator — amount, tenure, Monthly / One Time, Invest now.
 * Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsGoalCalculatorScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Goal Calculator']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Desired amount (in rupee)']")
    private WebElement desiredAmountLabel;

    @AndroidFindBy(xpath = "(//android.widget.EditText)[1]")
    private WebElement amount;

    @AndroidFindBy(xpath = "(//android.widget.SeekBar)[1]")
    private WebElement amountSlider;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Range should be in 10000')]")
    private WebElement amountRangeError;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Tenure (in year)']")
    private WebElement tenureLabel;

    @AndroidFindBy(xpath = "(//android.widget.EditText)[2]")
    private WebElement tenure;

    @AndroidFindBy(xpath = "//android.widget.SeekBar[contains(@content-desc,'Tenure')]")
    private WebElement tenureSlider;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='range should be in 1 and 30']")
    private WebElement tenureRangeError;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Investment type']")
    private WebElement investmentType;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='One Time']")
    private WebElement oneTime;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Monthly']")
    private WebElement monthly;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='One Time']//android.widget.RadioButton")
    private WebElement radioOneTime;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Monthly']//android.widget.RadioButton")
    private WebElement radioMonthly;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Target amount']")
    private WebElement targetAmount;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Need to Invest(pm)']")
    private WebElement needToInvestPm;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Need to Invest']")
    private WebElement needToInvest;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'inflation adjusted')]")
    private WebElement inflationNote;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Invest now']")
    private WebElement investNow;

    public MutualFundsGoalCalculatorScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Goal Calculator']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Invest now']")) != null;
    }
}
