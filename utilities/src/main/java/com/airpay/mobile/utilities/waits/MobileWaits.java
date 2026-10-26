package com.airpay.mobile.utilities.waits;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class MobileWaits {

    private final AndroidDriver driver;
    private final Duration timeout;

    public MobileWaits(AndroidDriver driver, Duration timeout) {
        this.driver = driver;
        this.timeout = timeout;
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

    public static By xpath(String xpath) {
        return AppiumBy.xpath(xpath);
    }

    public static By accessibilityId(String id) {
        return AppiumBy.accessibilityId(id);
    }
}
