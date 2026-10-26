package com.airpay.money.screens.finsight;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Financial Data Fetch Consent, opened from Sync Your Net Worth on the FinSight dashboard.
 * Allow Consent is not tapped unless the case says so. OTP is never entered here.
 */
public class NetWorthConsentScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Consent & Permissions') or contains(@content-desc,'Financial Data Fetch')]")
    private WebElement consentHeading;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Date Range')]")
    private WebElement dateRange;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Consent Validity')]")
    private WebElement consentValidity;

    @AndroidFindBy(xpath = "//android.widget.CheckBox")
    private WebElement consentCheckbox;

    @AndroidFindBy(xpath = "//*[@content-desc='Allow Consent' or contains(@content-desc,'Allow Consent')]")
    private WebElement allowConsent;

    public NetWorthConsentScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return allowConsentButton() != null
                && (shows("Consent & Permissions") || shows("Financial Data Fetch Consent"));
    }

    public boolean permissionsDisplayed() {
        return shows("Consent & Permissions");
    }

    public boolean dateRangeDisplayed() {
        return shows("Date Range");
    }

    public boolean consentValidityDisplayed() {
        return shows("Consent Validity");
    }

    public boolean checkboxDisplayed() {
        return checkbox() != null;
    }

    public boolean checkboxChecked() {
        WebElement box = checkbox();
        if (box == null) {
            return false;
        }
        return "true".equalsIgnoreCase(String.valueOf(box.getAttribute("checked")))
                || "true".equalsIgnoreCase(String.valueOf(box.getAttribute("selected")));
    }

    /** Leaves the checkbox cleared. Does not tap Allow Consent. */
    public void leaveCheckboxUnselected() {
        if (checkboxChecked()) {
            tapCheckbox();
        }
    }

    public void tapCheckbox() {
        WebElement box = checkbox();
        if (box != null) {
            pressElement(box);
            sleep(600);
        }
    }

    public boolean allowConsentDisplayed() {
        return allowConsentButton() != null;
    }

    public boolean allowConsentEnabled() {
        WebElement button = allowConsentButton();
        return button != null && "true".equalsIgnoreCase(String.valueOf(button.getAttribute("enabled")));
    }

    public void tapAllowConsent() {
        WebElement button = allowConsentButton();
        if (button != null) {
            pressElement(button);
            sleep(1200);
        }
    }

    public void goBack() {
        tapHeaderBack();
        sleep(700);
    }

    private boolean shows(String label) {
        return pageSource().replace("&amp;", "&").replace("&#10;", " ").contains(label);
    }

    private WebElement checkbox() {
        WebElement box = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.CheckBox"),
                MobileWaits.xpath("//*[@clickable='true' and (contains(@content-desc,'I agree') or contains(@content-desc,'I consent') or contains(@content-desc,'checkbox'))]"));
        if (box != null) {
            return box;
        }
        return firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//*[@checkable='true' and not(contains(@content-desc,'Allow Consent'))]"));
    }

    private WebElement allowConsentButton() {
        return firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[@content-desc='Allow Consent']"),
                MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc,'Allow Consent')]"));
    }
}
