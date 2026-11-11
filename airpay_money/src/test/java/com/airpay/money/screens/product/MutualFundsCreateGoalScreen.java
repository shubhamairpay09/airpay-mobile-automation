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
        String src = pageSource();
        return src.contains("Create Goal")
                || (src.contains("Select Tenure") && src.contains("year"))
                || (src.contains("Bike") && src.contains("Car"));
    }

    public boolean showsGoalIcons() {
        if (tenurePickerOpen()) {
            closeTenurePicker();
        }
        if (!isDisplayed()) {
            return false;
        }
        String src = pageSource();
        return src.contains("Bike") && (src.contains("Car") || src.contains("Child Education"));
    }

    public void selectBike() {
        tapLabelFast("Bike");
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
        hideKeyboardQuiet();
        if (tenureChosen(years)) {
            return true;
        }
        int year = tenureYearNumber(years);
        String label = year + " year";
        if (tenurePickerOpen()) {
            if (tapTenureYear(label)) {
                hideKeyboardQuiet();
                return tenureChosen(years);
            }
        }
        tapSelectTenure();
        if (tapTenureYear(label)) {
            hideKeyboardQuiet();
            return tenureChosen(years);
        }
        return tenureChosen(years);
    }

    private void tapSelectTenure() {
        tapFirstAny(Duration.ofSeconds(3),
                accessibility("Select Tenure"),
                MobileWaits.xpath("//*[@content-desc='Select Tenure']"));
        sleep(800);
    }

    /**
     * ADB input tap is ignored by this Flutter year list. Click the accessibility
     * node even when isDisplayed is false. Exact "3 year" — not a substring of "13 year".
     */
    private boolean tapTenureYear(String label) {
        if (tapFirstAny(Duration.ofSeconds(8),
                accessibility(label),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]"),
                io.appium.java_client.AppiumBy.androidUIAutomator(
                        "new UiSelector().description(" + '"' + label + '"' + ")"))) {
            sleep(800);
            return true;
        }
        return false;
    }

    private static int tenureYearNumber(String years) {
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("(\\d+)")
                .matcher(years == null ? "" : years);
        return m.find() ? Integer.parseInt(m.group(1)) : 3;
    }

    private void closeTenurePicker() {
        tapLabelFast("Back");
        shellPress(54, 108);
        sleep(400);
        if (tenurePickerOpen()) {
            pressSystemBack();
            sleep(500);
        }
    }

    private boolean tenurePickerOpen() {
        String src = pageSource();
        return src.contains("10 year") || src.contains("11 year")
                || src.contains("content-desc=\"2 year\"")
                || src.contains("content-desc=\"1 year\"");
    }

    private boolean tenureListAtTop() {
        String src = pageSource();
        return src.contains("content-desc=\"1 year\"")
                || src.contains("content-desc='1 year'");
    }

    private boolean tenureChosen(String years) {
        if (tenurePickerOpen()) {
            return false;
        }
        String src = pageSource();
        int year = tenureYearNumber(years);
        String label = year + " year";
        boolean hasYear = src.contains(label) || src.contains(year + " years");
        if (year < 10) {
            hasYear = hasYear && !src.contains("1" + label) && !src.contains("2" + label);
        }
        return hasYear;
    }

    public boolean submit() {
        hideKeyboardQuiet();
        for (int i = 0; i < 3; i++) {
            if (tapLabel("Continue") || tapLabel("Create Goal") || tapLabel("Done")) {
                sleep(1200);
                if (!pageSource().contains("Select Tenure") || pageSource().contains("My Goals")
                        || pageSource().contains("Goal created") || pageSource().contains("Mutual Funds")) {
                    return true;
                }
            }
            swipeUp();
        }
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        tapAt((int) (size[0] * 0.50), (int) (size[1] * 0.90));
        sleep(1200);
        return !pageSource().contains("Select Tenure")
                || pageSource().contains("My Goals")
                || pageSource().contains("Mutual Funds");
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
        hideKeyboardQuiet();
    }

    private void hideKeyboardQuiet() {
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // keyboard already closed
        }
        sleep(200);
    }

}
