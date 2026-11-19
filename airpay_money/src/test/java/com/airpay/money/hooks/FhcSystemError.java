package com.airpay.money.hooks;

/**
 * When FHC shows an app/system error, remaining cases must stop so it can be raised.
 */
public final class FhcSystemError {

    private static volatile String message;

    private FhcSystemError() {
    }

    public static void trip(String visible) {
        if (message == null || message.isBlank()) {
            message = visible == null ? "unknown system error" : visible;
        }
        FhcErrorLog.record("SYSTEM", "APP_ERROR", message);
    }

    public static boolean tripped() {
        return message != null && !message.isBlank();
    }

    public static String message() {
        return message == null ? "" : message;
    }

    public static void clear() {
        message = null;
    }
}
