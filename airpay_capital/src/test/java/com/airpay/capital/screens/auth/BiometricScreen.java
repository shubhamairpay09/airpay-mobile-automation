package com.airpay.capital.screens.auth;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import org.openqa.selenium.By;

/** Post-login biometric gate. Skip keeps the app in the foreground. */
public class BiometricScreen extends BaseScreen {

    private static final By SKIP = MobileWaits.xpath(
            "//*[@clickable='true' and (contains(@content-desc,'Skip') or contains(@text,'Skip')"
                    + " or contains(@content-desc,'Not now') or contains(@content-desc,'Maybe later'))]");

    public BiometricScreen(TestContext context) {
        super(context);
    }

    public void dismissIfShown() {
        long end = System.currentTimeMillis() + 12_000;
        boolean sawPrompt = false;
        while (System.currentTimeMillis() < end) {
            if (!isBiometricPromptVisible()) {
                if (sawPrompt) {
                    return;
                }
                sleep(400);
                if (!isBiometricPromptVisible()) {
                    return;
                }
            }
            sawPrompt = true;
            if (tapCenter(SKIP) || tapCenter(accessibility("Skip"))) {
                sleep(700);
                continue;
            }
            sleep(300);
        }
    }

    public boolean isBiometricPromptVisible() {
        return sourceContains(
                "Enable biometric",
                "biometric authentication",
                "Authentication required",
                "without enabling biometric",
                "Use biometric");
    }
}
