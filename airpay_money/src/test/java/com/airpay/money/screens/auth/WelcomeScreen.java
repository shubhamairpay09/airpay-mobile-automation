package com.airpay.money.screens.auth;

import com.airpay.money.data.ScreenMessages;
import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;

import java.time.Duration;
import java.util.List;

public class WelcomeScreen extends BaseScreen {

    public WelcomeScreen(TestContext context) {
        super(context);
    }

    public boolean waitForLoginEntry(Duration timeout) {
        return firstPresent(timeout,
                MobileWaits.xpath("//*[contains(@content-desc,'Login with Mobile')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Login With Mobile')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Continue with Mobile')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Get Started')]"),
                MobileWaits.xpath("//*[contains(@content-desc,'Send OTP')]")) != null;
    }

    public void tapLoginWithMobile() {
        new MobileLoginScreen(context).tapLoginWithMobileNumber();
    }
}
