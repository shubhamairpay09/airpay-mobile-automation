package com.airpay.mobile.utilities.context;

import com.airpay.mobile.utilities.config.FrameworkConfig;
import io.appium.java_client.android.AndroidDriver;

public class TestContext {

    private FrameworkConfig config;
    private AndroidDriver driver;
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

    public boolean isFailureScreenshotAttached() {
        return failureScreenshotAttached;
    }

    public void setFailureScreenshotAttached(boolean failureScreenshotAttached) {
        this.failureScreenshotAttached = failureScreenshotAttached;
    }
}
