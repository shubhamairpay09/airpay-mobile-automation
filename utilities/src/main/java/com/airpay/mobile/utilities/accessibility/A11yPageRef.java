package com.airpay.mobile.utilities.accessibility;

public record A11yPageRef(
        String id,
        String module,
        String label,
        String route,
        boolean authRequired,
        String nav,
        String menuGroup,
        String pageObject,
        String a11yPriority
) {
    public static A11yPageRef screen(String id, String module, String label, String route) {
        return new A11yPageRef(id, module, label, route, false, "screen", "", "", "high");
    }
}
