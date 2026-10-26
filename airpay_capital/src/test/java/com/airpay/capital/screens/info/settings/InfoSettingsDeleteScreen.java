package com.airpay.capital.screens.info.settings;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

/** Settings → Delete your account (confirmation — do not submit in automation). */
public class InfoSettingsDeleteScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Back')]")
    @iOSXCUITFindBy(accessibility = "Back")
    private WebElement backButton;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Account Delete is permanent')]")
    @iOSXCUITFindBy(accessibility = "Account Delete is permanent")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'account closure is a permanent action')]")
    @iOSXCUITFindBy(accessibility = "account closure is a permanent action")
    private WebElement warning;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Select the reason to delete your account')]")
    @iOSXCUITFindBy(accessibility = "Select the reason to delete your account")
    private WebElement reasonPrompt;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Delete reason')]")
    @iOSXCUITFindBy(accessibility = "Delete reason")
    private WebElement deleteReasonPicker;

    @AndroidFindBy(xpath = "//android.widget.CheckBox[contains(@content-desc,'permanently delete my airpay capital account')]")
    @iOSXCUITFindBy(accessibility = "permanently delete my airpay capital account")
    private WebElement acceptConsent;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Delete Account')]")
    @iOSXCUITFindBy(accessibility = "Delete Account")
    private WebElement deleteAccountButton;

    public InfoSettingsDeleteScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Account Delete is permanent", "Delete Account");
    }
}
