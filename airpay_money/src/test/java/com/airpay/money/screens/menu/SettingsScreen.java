package com.airpay.money.screens.menu;

import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class SettingsScreen extends AbstractMenuScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Settings')]")
    @iOSXCUITFindBy(accessibility = "Settings")
    private WebElement heading;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Notification')]")
    @iOSXCUITFindBy(accessibility = "Notifications")
    private WebElement notificationsToggle;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Biometric') or contains(@content-desc,'Fingerprint')]")
    @iOSXCUITFindBy(accessibility = "Biometric")
    private WebElement biometricToggle;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Delete') or contains(@content-desc,'Account')]")
    @iOSXCUITFindBy(accessibility = "Delete Account")
    private WebElement deleteAccountRow;

    @AndroidFindBy(xpath = "//android.widget.Switch")
    @iOSXCUITFindBy(className = "XCUIElementTypeSwitch")
    private WebElement firstSwitch;

    public SettingsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Settings", "Notification", "Biometric", "Account");
    }
}
