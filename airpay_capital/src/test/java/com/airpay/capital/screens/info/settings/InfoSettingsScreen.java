package com.airpay.capital.screens.info.settings;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/** Info → Settings. */
public class InfoSettingsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Back')]")
    @iOSXCUITFindBy(accessibility = "Back")
    private WebElement backButton;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Settings']")
    @iOSXCUITFindBy(accessibility = "Settings")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.widget.Switch[contains(@content-desc,'Notifications')]")
    @iOSXCUITFindBy(accessibility = "Notifications")
    private WebElement notificationsSwitch;

    @AndroidFindBy(xpath = "//android.widget.Switch[contains(@content-desc,'Enable Biometric Login')]")
    @iOSXCUITFindBy(accessibility = "Enable Biometric Login")
    private WebElement biometricSwitch;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Delete your account')]")
    @iOSXCUITFindBy(accessibility = "Delete your account")
    private WebElement deleteAccountRow;

    @AndroidFindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Logout')]")
    @iOSXCUITFindBy(accessibility = "Logout")
    private WebElement logoutRow;

    public InfoSettingsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Settings", "Notifications", "Delete your account");
    }

    public InfoSettingsDeleteScreen openDeleteAccount() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Delete your account')]"));
        sleep(800);
        return new InfoSettingsDeleteScreen(context);
    }

    public void toggleNotifications() {
        tapFirst(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Switch[contains(@content-desc,'Notifications')]"));
    }

    public void toggleBiometricLogin() {
        tapFirst(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Switch[contains(@content-desc,'Enable Biometric Login')]"));
    }
}
