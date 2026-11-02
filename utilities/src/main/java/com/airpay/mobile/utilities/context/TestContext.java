package com.airpay.mobile.utilities.context;

import com.airpay.mobile.utilities.config.FrameworkConfig;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;

public class TestContext {

    private FrameworkConfig config;
    private AndroidDriver driver;
    private WebDriver webDriver;
    private boolean failureScreenshotAttached;

    public FrameworkConfig config() {
        return config;
    }

    public void setConfig(FrameworkConfig config) {
        this.config = config;
    }

    public AndroidDriver driver() {
        if (driver == null) {
            throw new IllegalStateException("AndroidDriver not initialized");
        }
        return driver;
    }

    public void setDriver(AndroidDriver driver) {
        this.driver = driver;
    }

    public boolean hasDriver() {
        return driver != null;
    }

    public WebDriver webDriver() {
        if (webDriver == null) {
            throw new IllegalStateException("WebDriver not initialized");
        }
        return webDriver;
    }

    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public boolean hasWebDriver() {
        return webDriver != null;
    }

    public boolean isFailureScreenshotAttached() {
        return failureScreenshotAttached;
    }

    public void setFailureScreenshotAttached(boolean failureScreenshotAttached) {
        this.failureScreenshotAttached = failureScreenshotAttached;
    }
}
