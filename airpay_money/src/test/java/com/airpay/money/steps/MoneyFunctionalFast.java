package com.airpay.money.steps;

import com.airpay.mobile.utilities.context.TestContext;

/** Shorter waits when {@code -Dmoney.functional.fast=true}. */
public final class MoneyFunctionalFast {

    private MoneyFunctionalFast() {
    }

    public static boolean enabled(TestContext context) {
        return context != null && context.config().getBoolean("money.functional.fast", false);
    }

    public static long airpayLoadWaitMs(TestContext context) {
        return enabled(context) ? 12_000L : 25_000L;
    }

    public static long pollMs(TestContext context) {
        return enabled(context) ? 350L : 500L;
    }
}
