package com.airpay.mobile.utilities.accessibility;

import java.util.ArrayList;
import java.util.List;

public final class A11yScanSession {

    private static final ThreadLocal<List<A11yPageScanResult>> RESULTS =
            ThreadLocal.withInitial(ArrayList::new);

    private A11yScanSession() {
    }

    public static void clear() {
        RESULTS.get().clear();
    }

    public static void add(A11yPageScanResult result) {
        RESULTS.get().add(result);
    }

    public static List<A11yPageScanResult> results() {
        return List.copyOf(RESULTS.get());
    }

    public static void remove() {
        RESULTS.remove();
    }
}
