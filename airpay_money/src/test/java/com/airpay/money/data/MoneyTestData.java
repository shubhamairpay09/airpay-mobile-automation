package com.airpay.money.data;

import com.airpay.mobile.utilities.config.FrameworkConfig;

import java.util.concurrent.ThreadLocalRandom;

public final class MoneyTestData {

    private MoneyTestData() {
    }

    public static String mobile(FrameworkConfig config) {
        String configured = config.get("money.test.mobile", "").trim();
        if (!configured.isBlank()) {
            return configured;
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
        String otp = config.get("money.test.otp", "1234").trim();
        return otp.isBlank() ? "1234" : otp;
    }

    public static String pin(FrameworkConfig config) {
        String fromSheet = MoneyExcel.pin("");
        if (!fromSheet.isBlank()) {
            return fromSheet;
        }
        return config.get("money.test.pin", "").trim();
    }

    public static String pan(FrameworkConfig config) {
        return config.get("money.test.pan", "").trim();
    }

    public static String name(FrameworkConfig config) {
        return config.get("money.test.name", "Shubham Verma").trim();
    }

    public static String email(FrameworkConfig config) {
        return config.get("money.test.email", "").trim();
    }

    public static String dob(FrameworkConfig config) {
        return config.get("money.test.dob", "").trim();
    }
}
