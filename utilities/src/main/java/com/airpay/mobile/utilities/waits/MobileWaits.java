package com.airpay.mobile.utilities.waits;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.BooleanSupplier;

public final class MobileWaits {

    private static final Duration DEFAULT_POLL = Duration.ofMillis(200);

    private final AndroidDriver driver;
    private final Duration timeout;

    public MobileWaits(AndroidDriver driver, Duration timeout) {
        this.driver = driver;
        this.timeout = timeout;
    }

    public Duration timeout() {
        return timeout;
    }

    public WebElement visible(By by) {
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public WebElement clickable(By by) {
        return new WebDriverWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(by));
    }

    public boolean present(By by, Duration wait) {
        try {
            new WebDriverWait(driver, wait).until(ExpectedConditions.presenceOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Poll until {@code condition} is true, or {@link #timeout()} elapses. */
    public boolean until(BooleanSupplier condition) {
        return until(condition, timeout, DEFAULT_POLL);
    }

    public boolean until(BooleanSupplier condition, Duration wait) {
        return until(condition, wait, DEFAULT_POLL);
    }

    public boolean until(BooleanSupplier condition, Duration wait, Duration poll) {
        long end = System.currentTimeMillis() + Math.max(0, wait.toMillis());
        long gap = Math.max(40, poll.toMillis());
        while (System.currentTimeMillis() < end) {
            if (safeTrue(condition)) {
                return true;
            }
            sleep(gap);
        }
        return safeTrue(condition);
    }

    private static boolean safeTrue(BooleanSupplier condition) {
        try {
            return condition.getAsBoolean();
        } catch (Exception e) {
            return false;
        }
    }

    public static void sleep(long ms) {
        if (ms <= 0) {
            return;
        }
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static By xpath(String xpath) {
        return AppiumBy.xpath(xpath);
    }

    public static By accessibilityId(String id) {
        return AppiumBy.accessibilityId(id);
    }
}
