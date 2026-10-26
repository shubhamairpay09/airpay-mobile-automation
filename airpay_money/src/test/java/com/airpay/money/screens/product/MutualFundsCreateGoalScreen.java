package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds — Create Goal. Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsCreateGoalScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Create Goal']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Bike']")
    private WebElement bike;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Car']")
    private WebElement car;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Child Education']")
    private WebElement childEducation;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Home']")
    private WebElement home;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Laptop']")
    private WebElement laptop;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Marriage']")
    private WebElement marriage;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Mobile']")
    private WebElement mobile;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Regular Payout']")
    private WebElement regularPayout;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Retirement']")
    private WebElement retirement;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Travels']")
    private WebElement travels;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Custom']")
    private WebElement custom;

    @AndroidFindBy(xpath = "(//android.widget.EditText)[1]")
    private WebElement goalName;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='New Investment']")
    private WebElement goalNameNewInvestment;

    @AndroidFindBy(xpath = "(//android.widget.EditText)[2]")
    private WebElement desiredValue;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Select Tenure']")
    private WebElement selectTenure;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Continue']")
    private WebElement continueButton;

    public MutualFundsCreateGoalScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(8),
                MobileWaits.xpath("//android.view.View[@content-desc='Create Goal']"),
                MobileWaits.xpath("//*[contains(@content-desc,'Bike')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Child Education')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Car')]")) != null;
    }

    public boolean showsGoalIcons() {
        if (!isDisplayed()) {
            return false;
        }
        String src = pageSource();
        return src.contains("Bike") && (src.contains("Car") || src.contains("Child Education"));
    }

    public void selectBike() {
        tapLabel("Bike");
        sleep(400);
    }

    public void enterGoalName(String name) {
        WebElement field = firstPresent(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Goal name') or contains(@text,'Goal name')]"),
                MobileWaits.xpath("//android.widget.EditText[@text='New Investment']"),
                MobileWaits.xpath("//android.widget.EditText[contains(@text,'bike') or contains(@text,'Bike')]"),
                MobileWaits.xpath("(//android.widget.EditText)[1]"));
        replace(field, name);
    }

    public void enterDesiredAmount(String amount) {
        WebElement field = firstPresent(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Desired') or contains(@hint,'amount') or contains(@hint,'Amount')]"),
                MobileWaits.xpath("(//android.widget.EditText)[2]"));
        replace(field, amount);
    }

    public boolean selectYears(String years) {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[@clickable='true' and @content-desc='Select Tenure']"),
                MobileWaits.xpath("//*[@content-desc='Select Tenure']"));
        sleep(700);
        for (int i = 0; i < 5; i++) {
            WebElement year = firstPresent(Duration.ofMillis(600),
                    MobileWaits.xpath("//*[@clickable='true' and @content-desc=" + xpathLiteral(years) + "]"));
            if (year != null) {
                year.click();
                sleep(800);
                return !pageSource().contains("1 year");
            }
            swipeUp();
        }
        return false;
    }

    public boolean submit() {
        for (int i = 0; i < 3; i++) {
            if (tapLabel("Continue")) {
                sleep(1200);
                return true;
            }
            swipeUp();
        }
        return false;
    }

    private void replace(WebElement field, String value) {
        if (field == null) {
            return;
        }
        field.click();
        try {
            field.clear();
        } catch (Exception ignored) {
            // Flutter fields often reject clear()
        }
        field.sendKeys(value);
        sleep(300);
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // keyboard already closed
        }
    }

}
