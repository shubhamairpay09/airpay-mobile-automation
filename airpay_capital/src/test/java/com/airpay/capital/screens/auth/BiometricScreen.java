package com.airpay.capital.screens.auth;

import com.airpay.capital.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;

import java.time.Duration;
import java.util.List;

/** Post-login biometric / notification gates. */
public class BiometricScreen extends BaseScreen {

    public BiometricScreen(TestContext context) {
        super(context);
    }

    public void dismissIfShown() {
        for (int i = 0; i < 4; i++) {
            if (tapAny(List.of(
                    "Skip, Continue without enabling biometric authentication",
                    "Skip",
                    "Not now",
                    "Maybe later"))) {
                sleep(1000);
                continue;
            }
            break;
        }
        sleep(800);
    }

    public boolean isBiometricPromptVisible() {
        return sourceContains("Enable biometric", "biometric authentication", "Authentication required");
    }
}
