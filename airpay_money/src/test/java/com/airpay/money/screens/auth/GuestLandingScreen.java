package com.airpay.money.screens.auth;

import com.airpay.money.data.ScreenMessages;
import com.airpay.money.screens.base.BaseScreen;
import com.airpay.money.screens.menu.DrawerMenuScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;

import java.time.Duration;

/**
 * Guest screens (e.g. Terms &amp; Conditions) before login.
 * Inspector locators: Menu = {@code text("Menu")}, Login = {@code description("Login")}.
 */
public class GuestLandingScreen extends BaseScreen {

    public GuestLandingScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        if (new MobileLoginScreen(context).waitUntilDisplayed(Duration.ofSeconds(2))) {
            return false;
        }
        if (new OtpScreen(context).isDisplayed()) {
            return false;
        }
        return hasMenuButton() && hasHeaderLogin();
    }

    public boolean hasMenuButton() {
        return firstPresent(Duration.ofSeconds(2), menuLocators()) != null;
    }

    public boolean hasHeaderLogin() {
        return firstPresent(Duration.ofSeconds(2), loginLocators()) != null
                || safeSource().contains("content-desc=\"Login\"");
    }

    public boolean isTermsOrGuestContentScreen() {
        return sourceContains("Terms", "Terms & Conditions", "airpay money private limited", "airpay money");
    }

    /** {@code new UiSelector().text("Menu")} — top-right hamburger on guest screens. */
    public boolean tapHamburger() {
        sleep(400);
        if (tapFirst(Duration.ofSeconds(8), menuLocators())) {
            sleep(700);
            return new DrawerMenuScreen(context).isOpen();
        }
        return false;
    }

    /** {@code new UiSelector().description("Login")} — header Login beside Menu. */
    public void tapHeaderLogin() {
        sleep(400);
        if (tapFirst(Duration.ofSeconds(8), loginLocators())) {
            sleep(800);
            return;
        }
        throw new IllegalStateException("Header Login not found (UiSelector().description(\"Login\")).");
    }

    public void closeDrawer() {
        new DrawerMenuScreen(context).close();
    }

    public boolean isDrawerOpen() {
        return new DrawerMenuScreen(context).isOpen();
    }

    private static org.openqa.selenium.By[] menuLocators() {
        return new org.openqa.selenium.By[]{
                AppiumBy.androidUIAutomator("new UiSelector().text(\"Menu\")"),
                MobileWaits.xpath("//android.widget.Button[@text='Menu']"),
                AppiumBy.accessibilityId("Menu")
        };
    }

    private static org.openqa.selenium.By[] loginLocators() {
        return new org.openqa.selenium.By[]{
                AppiumBy.androidUIAutomator("new UiSelector().description(\"Login\")"),
                AppiumBy.accessibilityId("Login"),
                MobileWaits.xpath("//*[@content-desc='Login']"),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(ScreenMessages.LOGIN) + ")]")
        };
    }
}
