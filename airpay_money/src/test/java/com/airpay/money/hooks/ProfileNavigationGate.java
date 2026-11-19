package com.airpay.money.hooks;

/**
 * Open settings / Profile tree navigation runs only during {@code @profile-settings} scenarios.
 */
public final class ProfileNavigationGate {

    private static final ThreadLocal<Boolean> ALLOWED = ThreadLocal.withInitial(() -> false);

    private ProfileNavigationGate() {
    }

    public static void allow() {
        ALLOWED.set(true);
    }

    public static void clear() {
        ALLOWED.remove();
    }

    public static boolean allowed() {
        return Boolean.TRUE.equals(ALLOWED.get());
    }
}
