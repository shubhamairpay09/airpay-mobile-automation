package com.airpay.money.steps;

/** FD plan page opened for this scenario (preferred Suryoday, else first available provider). */
final class FdSelectedPlan {

    private static final ThreadLocal<String> BANK = new ThreadLocal<>();

    private FdSelectedPlan() {
    }

    static void setBank(String bank) {
        BANK.set(bank);
    }

    static String bank() {
        String value = BANK.get();
        return value == null ? "Suryoday" : value;
    }

    static void clear() {
        BANK.remove();
    }
}
