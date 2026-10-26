package com.airpay.money.screens.auth;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Android system permission prompt. Live dump 2026-09-17 (notifications).
 * Always Allow — never Don't allow.
 */
public class PermissionDialogScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//*[@resource-id='com.android.permissioncontroller:id/permission_message']")
    private WebElement message;

    @AndroidFindBy(xpath = "//*[@resource-id='com.android.permissioncontroller:id/permission_allow_button']")
    private WebElement allow;

    @AndroidFindBy(xpath = "//*[@resource-id='com.android.permissioncontroller:id/permission_deny_button']")
    private WebElement dontAllow;

    public PermissionDialogScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//*[@resource-id='com.android.permissioncontroller:id/grant_dialog']"),
                MobileWaits.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_allow_button']"),
                MobileWaits.xpath("//android.widget.Button[@text='Allow' or @text='WHILE USING THE APP' or @text='While using the app' or @text='Allow all the time']")) != null;
    }

    /** Tap Allow / While using / Allow all the time. Never Don't allow. */
    public boolean allow() {
        return tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_allow_button']"),
                MobileWaits.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_allow_all_button']"),
                MobileWaits.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_allow_always_button']"),
                MobileWaits.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_allow_foreground_only_button']"),
                MobileWaits.xpath("//*[@resource-id='com.android.permissioncontroller:id/permission_allow_one_time_button']"),
                MobileWaits.xpath("//android.widget.Button[@text='Allow' or @text='WHILE USING THE APP' or @text='While using the app' or @text='Allow all the time']"));
    }
}
