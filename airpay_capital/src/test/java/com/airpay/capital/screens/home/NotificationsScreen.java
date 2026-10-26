package com.airpay.capital.screens.home;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class NotificationsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Notifications')]")
    @iOSXCUITFindBy(accessibility = "Notifications")
    private WebElement notificationsButton;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'Notifications')]")
    @iOSXCUITFindBy(accessibility = "Notifications")
    private WebElement heading;

    public NotificationsScreen(TestContext context) {
        super(context);
    }

    public void open() {
        new HomeScreen(context).openNotifications();
        sleep(700);
    }

    public boolean isDisplayed() {
        return sourceContains("Notifications", "No notifications");
    }
}
