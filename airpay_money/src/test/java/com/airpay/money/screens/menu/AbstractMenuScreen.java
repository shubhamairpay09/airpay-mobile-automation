package com.airpay.money.screens.menu;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

/** Shared locators for menu sub-screens (back, scrollable body, links). */
public abstract class AbstractMenuScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Back')]")
    @iOSXCUITFindBy(accessibility = "Back")
    protected WebElement backButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@clickable='true']")
    @iOSXCUITFindBy(className = "XCUIElementTypeButton")
    protected WebElement firstButton;

    @AndroidFindBy(xpath = "//android.widget.EditText")
    @iOSXCUITFindBy(className = "XCUIElementTypeTextField")
    protected WebElement firstField;

    @AndroidFindBy(xpath = "//*[contains(@class,'Image') and @clickable='true']")
    @iOSXCUITFindBy(className = "XCUIElementTypeImage")
    protected WebElement firstImage;

    protected AbstractMenuScreen(TestContext context) {
        super(context);
    }

    public void scrollAndScan(int swipes) {
        for (int i = 0; i < swipes; i++) {
            swipeUp();
            sleep(300);
        }
    }
}
