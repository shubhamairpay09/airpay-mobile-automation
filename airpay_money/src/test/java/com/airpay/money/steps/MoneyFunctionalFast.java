package com.airpay.money.steps;

import com.airpay.mobile.utilities.context.TestContext;

import java.time.Duration;

/** Shorter waits when {@code -Dmoney.functional.fast=true}. */
public final class MoneyFunctionalFast {

    private MoneyFunctionalFast() {
    }

    public static boolean enabled(TestContext context) {
        return context != null && context.config().getBoolean("money.functional.fast", false);
    }

    public static long airpayLoadWaitMs(TestContext context) {
        return enabled(context) ? 8_000L : 25_000L;
    }

    public static long pollMs(TestContext context) {
        return enabled(context) ? 250L : 500L;
    }

    public static long waitMs(TestContext context, long normalMs) {
        return enabled(context) ? Math.max(80L, normalMs / 3) : normalMs;
    }

    public static int rounds(TestContext context, int normal) {
        if (!enabled(context) || normal <= 1) {
            return normal;
        }
        return Math.max(1, normal - 2);
    }

    public static long pageSourceCacheMs(TestContext context) {
        return enabled(context) ? 900L : 400L;
    }

    public static Duration uiWait(TestContext context, long millis) {
        long budget = enabled(context) ? Math.max(350L, millis * 2 / 3) : millis;
        return Duration.ofMillis(budget);
    }
}
