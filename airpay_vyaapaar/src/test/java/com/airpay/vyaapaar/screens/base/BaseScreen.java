package com.airpay.vyaapaar.screens.base;

import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.function.BooleanSupplier;

/** Base for airpay vyaapaar screens. Locators live on each *Screen class. */
public abstract class BaseScreen {

    protected final TestContext context;
    protected final AndroidDriver driver;
    protected final MobileWaits waits;

    protected BaseScreen(TestContext context) {
        this.context = context;
        this.driver = context.driver();
        this.waits = new MobileWaits(driver, Duration.ofMillis(
                context.config().getInt("timeout.action", 20000)));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(4)), this);
    }

    public boolean sourceContains(String... fragments) {
        String src;
        try {
            src = driver.getPageSource();
        } catch (Exception e) {
            return false;
        }
        if (src == null) {
            return false;
        }
        for (String fragment : fragments) {
            if (fragment != null && src.contains(fragment)) {
                return true;
            }
        }
        return false;
    }

    protected Duration actionTimeout() {
        return Duration.ofMillis(context.config().getInt("timeout.action", 20000));
    }

    protected Duration navigationTimeout() {
        return Duration.ofMillis(context.config().getInt("timeout.navigation", 60000));
    }

    protected Duration otpTimeout() {
        return Duration.ofMillis(context.config().getLong("timeout.otp.wait.ms", 120_000));
    }

    public boolean waitUntil(BooleanSupplier condition) {
        return waits.until(condition, actionTimeout());
    }

    public boolean waitUntil(BooleanSupplier condition, Duration timeout) {
        return waits.until(condition, timeout);
    }

    public boolean waitUntilSourceContains(Duration timeout, String... fragments) {
        return waits.until(() -> sourceContains(fragments), timeout);
    }

    public boolean waitUntilSourceGone(Duration timeout, String... fragments) {
        return waits.until(() -> !sourceContains(fragments), timeout);
    }
}
