package com.airpay.money.steps;

/** When the FinSight card is already synced, net-worth cases assert that state instead of the sync offer. */
final class SyncedNetWorthMode {

    private static final ThreadLocal<Boolean> ACTIVE = ThreadLocal.withInitial(() -> false);

    private SyncedNetWorthMode() {
    }

    static void setActive(boolean active) {
        ACTIVE.set(active);
    }

    static void clear() {
        ACTIVE.remove();
    }

    static boolean active() {
        return Boolean.TRUE.equals(ACTIVE.get());
    }
}
