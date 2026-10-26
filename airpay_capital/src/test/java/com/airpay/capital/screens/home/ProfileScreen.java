package com.airpay.capital.screens.home;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class ProfileScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Profile')]")
    @iOSXCUITFindBy(accessibility = "Profile")
    private WebElement profileTab;

    @AndroidFindBy(xpath = "//*[contains(@content-desc,'My profile') or contains(@content-desc,'Edit profile')]")
    @iOSXCUITFindBy(accessibility = "My profile")
    private WebElement profileHeading;

    public ProfileScreen(TestContext context) {
        super(context);
    }

    public void open() {
        new HomeScreen(context).openProfile();
        sleep(700);
    }

    public boolean isDisplayed() {
        return sourceContains("Profile", "My profile", "Edit profile")
                || isDisplayed(profileHeading);
    }
}
