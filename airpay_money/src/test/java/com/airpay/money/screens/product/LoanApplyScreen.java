package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

/** Partner loan apply (e.g. Muthoot Business Loan) — consents then Apply for Loan. */
public class LoanApplyScreen extends BaseScreen {

    public LoanApplyScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Apply for Loan")
                || firstPresent(Duration.ofSeconds(2), applyLocators()) != null;
    }

    public void acceptDeclarationsAndApply() {
        tickAllCheckboxes();
        sleep(500);
        WebElement apply = firstPresent(Duration.ofSeconds(8), applyLocators());
        if (apply == null) {
            throw new IllegalStateException("Apply for Loan button not found.");
        }
        long end = System.currentTimeMillis() + 12_000;
        while (System.currentTimeMillis() < end) {
            try {
                if (apply.isEnabled() || "true".equalsIgnoreCase(String.valueOf(apply.getAttribute("enabled")))) {
                    break;
                }
            } catch (Exception ignored) {
                apply = firstPresent(Duration.ofSeconds(2), applyLocators());
            }
            tickAllCheckboxes();
            sleep(400);
        }
        if (!tapFirst(Duration.ofSeconds(8), applyLocators())) {
            throw new IllegalStateException("Could not tap Apply for Loan.");
        }
        sleep(1500);
        enterMobileIfPresent(context.config().get("money.test.mobile", "7906862513"));
    }

    public void enterMobileIfPresent(String mobile) {
        if (mobile == null || mobile.isBlank()) {
            return;
        }
        WebElement field = firstPresent(Duration.ofSeconds(8),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint,'mobile') or contains(@hint,'Mobile')"
                        + " or contains(@hint,'phone') or contains(@hint,'Phone')]"),
                MobileWaits.xpath("//android.widget.EditText[@hint='Enter mobile number']"),
                AppiumBy.className("android.widget.EditText"));
        if (field == null) {
            return;
        }
        type(field, mobile);
        sleep(400);
    }

    public int tickAllCheckboxes() {
        int n = 0;
        List<WebElement> boxes = driver.findElements(By.xpath(
                "//android.widget.CheckBox"
                        + " | //*[@resource-id='mfl-terms-consent' or @resource-id='mfl-director-declaration']"
                        + " | //*[@checkable='true']"));
        for (WebElement box : boxes) {
            try {
                if (!box.isDisplayed()) {
                    continue;
                }
                String checked = String.valueOf(box.getAttribute("checked"));
                if ("true".equalsIgnoreCase(checked)) {
                    continue;
                }
                box.click();
                n++;
                sleep(250);
            } catch (Exception ignored) {
                // stale
            }
        }
        return n;
    }

    private static org.openqa.selenium.By[] applyLocators() {
        return new org.openqa.selenium.By[]{
                MobileWaits.xpath("//android.widget.Button[@text='Apply for Loan']"),
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Apply for Loan\")"),
                AppiumBy.accessibilityId("Apply for Loan"),
                MobileWaits.xpath("//*[contains(@text,'Apply for Loan') or contains(@content-desc,'Apply for Loan')]")
        };
    }
}
