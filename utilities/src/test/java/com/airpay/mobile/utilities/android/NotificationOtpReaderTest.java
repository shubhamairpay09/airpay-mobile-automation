package com.airpay.mobile.utilities.android;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NotificationOtpReaderTest {

    @Test
    void extractsContextualSixDigitOtpAndIgnoresMobile() {
        String dump = "android.title=Airpay Capital\n"
                + "android.text=8802729089 OTP is 482193. Do not share with anyone.\n";
        assertThat(NotificationOtpReader.extractOtp(dump, "8802729089")).isEqualTo("482193");
    }

    @Test
    void extractsTrailingIsYourOtp() {
        assertThat(NotificationOtpReader.extractOtp("918221 is your OTP for Airpay", "8802729089"))
                .isEqualTo("918221");
    }

    @Test
    void extractsForgotPinResetOtp() {
        assertThat(NotificationOtpReader.extractOtp(
                "OTP to reset your PIN is 600738", "7906862513")).isEqualTo("600738");
    }

    @Test
    void extractsFourDigitOtp() {
        assertThat(NotificationOtpReader.extractOtp(
                "A verification code 4821 has been sent. OTP 4821", "8802729089", 4))
                .isEqualTo("4821");
    }
}

class PermissionDialogHandlerTest {

    @Test
    void allowLabelsAreAcceptedAndDenyIsRejected() {
        assertThat(PermissionDialogHandler.isAllowLabel("Allow")).isTrue();
        assertThat(PermissionDialogHandler.isAllowLabel("While using the app")).isTrue();
        assertThat(PermissionDialogHandler.isAllowLabel("Allow notifications")).isTrue();
        assertThat(PermissionDialogHandler.isAllowLabel("Don't allow")).isFalse();
        assertThat(PermissionDialogHandler.isAllowLabel("Deny")).isFalse();
    }
}
