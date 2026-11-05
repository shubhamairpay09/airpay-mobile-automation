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

    /** Foreground package from dumpsys — does not need UiAutomator. */
    public static String foregroundPackage(String udid) {
        String out = AdbCommands.shellOut(udid, 8, "dumpsys", "activity", "activities");
        if (out == null || out.isBlank()) {
            return "";
        }
        for (String key : new String[]{"topResumedActivity", "mResumedActivity", "mCurrentFocus"}) {
            int at = out.indexOf(key);
            if (at < 0) {
                continue;
            }
            String slice = out.substring(at, Math.min(out.length(), at + 280));
            Matcher m = PKG.matcher(slice);
            if (m.find()) {
                return m.group(1);
            }
        }
        return "";
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
        int y = size[1] - Math.max(40, size[1] / 32);
        tap(udid, x, y);
    }
}
