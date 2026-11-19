package com.airpay.mobile.utilities.android;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Shell input tap/swipe — reliable on realme/OEM devices where W3C perform() often fails. */
public final class AdbInput {

    private static final Pattern SIZE = Pattern.compile("(\\d+)x(\\d+)");
    private static final Pattern PKG = Pattern.compile("([a-zA-Z0-9_.]+)/");

    private AdbInput() {
    }

    public static void tap(String udid, int x, int y) {
        AdbCommands.shell(udid, "input", "tap", Integer.toString(x), Integer.toString(y));
    }

    public static void swipe(String udid, int x1, int y1, int x2, int y2, int durationMs) {
        AdbCommands.shell(udid, "input", "swipe",
                Integer.toString(x1), Integer.toString(y1),
                Integer.toString(x2), Integer.toString(y2),
                Integer.toString(Math.max(80, durationMs)));
    }

    /** Physical screen size from {@code wm size} — does not need UiAutomator. */
    public static int[] screenSize(String udid) {
        String out = AdbCommands.shellOut(udid, 8, "wm", "size");
        Matcher m = SIZE.matcher(out == null ? "" : out);
        String lastW = null;
        String lastH = null;
        while (m.find()) {
            lastW = m.group(1);
            lastH = m.group(2);
        }
        if (lastW != null) {
            return new int[]{Integer.parseInt(lastW), Integer.parseInt(lastH)};
        }
        return new int[]{720, 1600};
    }

    /** Foreground package. Avoid dumpsys on this realme — it wedges adb. */
    public static String foregroundPackage(String udid) {
        String out = AdbCommands.shellOut(udid, 3, "pidof", "com.finmapp.fininfinity");
        if (out != null && !out.isBlank()) {
            return "com.finmapp.fininfinity";
        }
        return "";
    }

    /** Focused window from {@code dumpsys window}; does not touch UiAutomator2. */
    public static boolean isForeground(String udid, String appPackage) {
        String out = AdbCommands.shellOut(udid, 5, "dumpsys", "window");
        if (out == null) {
            return false;
        }
        for (String line : out.split("\n")) {
            if ((line.contains("mCurrentFocus") || line.contains("mFocusedApp")) && line.contains(appPackage)) {
                return true;
            }
        }
        return false;
    }

    public static void startActivity(String udid, String appPackage, String activity) {
        AdbCommands.shell(udid, "am", "start", "-n", appPackage + "/" + activity);
    }

    /**
     * Bottom nav tap by slot (0-based). Money: 0=Products, 1=FinSight, 2=Calculators.
     * Uses {@code wm size} so it works on 720x1600 as well as taller phones.
     */
    public static void tapBottomNav(String udid, int slot, int slots) {
        int[] size = screenSize(udid);
        int n = Math.max(1, slots);
        int i = Math.max(0, Math.min(slot, n - 1));
        int x = (int) (size[0] * ((i + 0.5) / n));
        // Stay above the 3-button system nav (~80-120px). size/32 lands in the system bar
        // on 720x1600 and never hits Products / FinSight / Calculators.
        int systemNav = Math.max(96, size[1] / 16);
        int tabMid = Math.max(28, size[1] / 48);
        int y = size[1] - systemNav - tabMid;
        tap(udid, x, y);
    }
}
