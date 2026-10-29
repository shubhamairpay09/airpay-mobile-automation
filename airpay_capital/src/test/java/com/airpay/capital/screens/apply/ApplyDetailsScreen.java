package com.airpay.capital.screens.apply;

import com.airpay.capital.data.CapitalExcel;
import com.airpay.capital.data.ScreenMessages;
import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.crawl.DummyFormFiller;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

/** Shared PAN / name / email / loan amount form for all products. Values come from Capital Excel. */
public class ApplyDetailsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Enter your details as per official records')]")
    @iOSXCUITFindBy(accessibility = "Enter your details as per official records")
    private WebElement banner;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'PAN') or contains(@hint,'Enter PAN')]")
    @iOSXCUITFindBy(accessibility = "Enter PAN")
    private WebElement panField;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Name as per PAN') or contains(@hint,'Enter Name')]")
    @iOSXCUITFindBy(accessibility = "Enter Name")
    private WebElement nameField;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Email') or contains(@hint,'email')]")
    @iOSXCUITFindBy(accessibility = "Enter email")
    private WebElement emailField;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Desired loan amount') or contains(@hint,'Enter amount')]")
    @iOSXCUITFindBy(accessibility = "Enter amount")
    private WebElement loanAmountField;

    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@hint,'Income') or contains(@hint,'Salary') or contains(@hint,'income')]")
    @iOSXCUITFindBy(accessibility = "Income")
    private WebElement monthlyIncomeField;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Continue']")
    @iOSXCUITFindBy(accessibility = "Continue")
    private WebElement continueButton;

    public ApplyDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains(ScreenMessages.DETAILS_BANNER, "Enter PAN", "Date of birth as per PAN");
    }

    public boolean waitUntilDisplayed(Duration timeout) {
        return waitUntilSourceContains(timeout, ScreenMessages.DETAILS_BANNER, "Enter PAN", "Date of birth as per PAN")
                || firstPresent(timeout, MobileWaits.xpath("//android.widget.EditText[contains(@hint,'PAN')]")) != null;
    }

    public ApplyDetailsScreen fillStandardDetails() {
        typeIfPresent(MobileWaits.xpath("//android.widget.EditText[contains(@hint,'PAN')]"),
                CapitalExcel.text("PAN", "ABCDE1234F"));
        typeIfPresent(MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Name as per PAN')]"),
                CapitalExcel.text("Name", "Test User"));
        typeIfPresent(MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Email')]"),
                CapitalExcel.text("Email", "test.user@airpay.example"));
        typeIfPresent(MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Desired loan amount')]"),
                CapitalExcel.text("LoanAmount", "500000"));
        typeIfPresent(MobileWaits.xpath("//android.widget.EditText[contains(@hint,'Income') or contains(@hint,'Salary')]"),
                CapitalExcel.text("MonthlyIncome", "75000"));
        new DummyFormFiller(driver).fillVisibleFields(2);
        return this;
    }

    private void typeIfPresent(org.openqa.selenium.By by, String value) {
        WebElement field = firstPresent(Duration.ofSeconds(2), by);
        if (field != null) {
            type(field, value);
        }
    }

    public boolean acceptTermsAndTapApply() {
        for (int pass = 0; pass < 6; pass++) {
            tickTermsCheckboxes();
            if (tapEnabledApplyOrContinue()) {
                waitUntilSourceGone(Duration.ofSeconds(8), "Terms and conditions");
                return true;
            }
            swipeUp();
        }
        tickTermsCheckboxes();
        return tapEnabledApplyOrContinue() || tapContinue();
    }

    private int tickTermsCheckboxes() {
        int n = 0;
        List<WebElement> boxes = driver.findElements(MobileWaits.xpath(
                "//android.widget.CheckBox[contains(@content-desc,'Terms and conditions')]"));
        if (boxes.isEmpty()) {
            boxes = driver.findElements(MobileWaits.xpath(
                    "//android.widget.CheckBox[@clickable='true' or @checkable='true']"));
        }
        for (WebElement box : boxes) {
            try {
                if (!box.isDisplayed()) {
                    continue;
                }
                if ("true".equalsIgnoreCase(String.valueOf(box.getAttribute("checked")))) {
                    continue;
                }
                box.click();
                n++;
            } catch (Exception ignored) {
                // stale
            }
        }
        return n;
    }

    private boolean tapEnabledApplyOrContinue() {
        List<WebElement> btns = driver.findElements(MobileWaits.xpath(
                "//android.widget.Button[@content-desc='Apply Now' or @content-desc='Continue']"));
        for (WebElement b : btns) {
            try {
                if (b.isDisplayed() && b.isEnabled()) {
                    b.click();
                    return true;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return false;
    }

    public boolean tapContinue() {
        return tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Continue')]"))
                || tapAny(List.of("Continue", "Next", "Proceed", "Save & Continue"));
    }
}
