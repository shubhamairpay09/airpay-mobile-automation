package com.airpay.capital.data;

import com.airpay.mobile.utilities.config.FrameworkConfig;

import java.util.concurrent.ThreadLocalRandom;

/** Test mobiles and OTP for functional scenarios. */
public final class CapitalTestData {

    public enum Account {
        DEFAULT("capital.test.mobile"),
        NO_OFFER("capital.test.mobile.noOffer"),
        LOW_CIBIL("capital.test.mobile.lowCibil"),
        WITH_OFFERS("capital.test.mobile.withOffers");

        private final String configKey;

        Account(String configKey) {
            this.configKey = configKey;
        }
    }

    private CapitalTestData() {
    }

    /** Random 10-digit Indian mobile unless a scenario-specific number is configured. */
    public static String mobile(FrameworkConfig config, Account account) {
        String configured = config.get(account.configKey, "").trim();
        if (!configured.isBlank()) {
            return configured;
        }
        if (account == Account.DEFAULT) {
            String fallback = config.get("capital.test.mobile", "").trim();
            if (!fallback.isBlank()) {
                return fallback;
            }
        }
        return randomMobile();
    }

    public static String randomMobile() {
        ThreadLocalRandom r = ThreadLocalRandom.current();
        int first = 6 + r.nextInt(4);
        long rest = r.nextLong(1_000_000_000L);
        String digits = first + String.format("%09d", rest % 1_000_000_000L);
        return digits.length() > 10 ? digits.substring(0, 10) : digits;
    }

    public static String otp(FrameworkConfig config) {
        String otp = config.get("capital.test.otp", "1234").trim();
        return otp.isBlank() ? "1234" : otp;
    }

    public static String cibilLowMessageFragment(FrameworkConfig config) {
        return config.get("capital.cibil.low.message", ScreenMessages.CIBIL_FRAGMENT);
    }
}
