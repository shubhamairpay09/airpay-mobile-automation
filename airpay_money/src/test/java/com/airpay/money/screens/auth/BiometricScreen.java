package com.airpay.money.screens.auth;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;

/**
 * Post-email-OTP biometric opt-in.
 * Live dump 2026-09-17, build 2.0.124.
 */
public class BiometricScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Unlock faster with Biometrics']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Enable fingerprint or face unlock')]")
    private WebElement subtitle;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Enable Biometrics']")
    private WebElement enable;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Skip for Now']")
    private WebElement skip;

    public BiometricScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Unlock faster with Biometrics']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Enable Biometrics']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Skip for Now']")) != null;
    }

    public void dismissIfShown() {
        for (int i = 0; i < 4; i++) {
            boolean skipped = tapFirst(Duration.ofSeconds(2),
                    MobileWaits.xpath("//android.widget.Button[@content-desc='Skip for Now']"),
                    MobileWaits.xpath("//*[@content-desc='Skip for now' or @content-desc='Skip for Now']"));
            if (!skipped) {
                skipped = tapAny(List.of(
                        "Skip for Now",
                        "Skip for now",
                        "Skip, Continue without enabling biometric authentication",
                        "Not now",
                        "Maybe later"));
            }
            if (skipped) {
                sleep(900);
                tapOk();
                continue;
            }
            if (tapOk()) {
                continue;
            }
            break;
        }
        sleep(400);
    }

    /** Confirmation that follows Skip for now. */
    public boolean tapOk() {
        boolean tapped = tapFirst(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.Button[@content-desc='OK' or @text='OK']"),
                MobileWaits.xpath("//*[@clickable='true' and (@content-desc='OK' or @text='OK')]"));
        if (tapped) {
            sleep(700);
        }
        return tapped;
    }
}
