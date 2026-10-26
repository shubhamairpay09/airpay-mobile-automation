package com.airpay.money.screens.legal;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;

import java.time.Duration;

/** Privacy Policy / Terms WebView opened from the login consent row. Back is an unnamed header button. */
public class LegalWebViewScreen extends BaseScreen {

    public LegalWebViewScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(4),
                AppiumBy.className("android.webkit.WebView")) != null
                && !sourceContains("Login with Mobile Number");
    }

    public void tapHeaderBack() {
        if (tapFirst(Duration.ofSeconds(5),
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Back\")"),
                AppiumBy.accessibilityId("Back"),
                MobileWaits.xpath("//android.widget.Button[1]"))) {
            sleep(600);
            return;
        }
        tapBack();
        sleep(400);
    }
}
