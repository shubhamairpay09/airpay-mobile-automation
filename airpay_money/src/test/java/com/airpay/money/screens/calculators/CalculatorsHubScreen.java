package com.airpay.money.screens.calculators;

import com.airpay.money.data.MoneyExcel;
import com.airpay.money.screens.auth.BiometricScreen;
import com.airpay.money.screens.auth.PinUnlockScreen;
import com.airpay.mobile.utilities.waits.MobileWaits;

import java.time.Duration;

/** Live dump 2026-09-21 — Calculators tab (Financial Calculators hub), 15 tiles. */
public class CalculatorsHubScreen extends com.airpay.money.screens.base.BaseScreen {

    public static final String[] TILES = {
            "EMI Calculator",
            "SIP Calculator",
            "Lumpsum Calculator",
            "PPF Calculator",
            "EPF Calculator",
            "FD Calculator",
            "NPS Calculator",
            "Retirement Calculator",
            "Crorepati Calculator",
            "Term Insurance Calculator",
            "Buy vs Rent Calculator",
            "Taxation Indian Equity",
            "Taxation International Equity",
            "Inflation Calculator",
            "Budget Calculator"
    };

    public CalculatorsHubScreen(com.airpay.mobile.utilities.context.TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return stillOnHubGrid();
    }

    public boolean listsAllTiles() {
        String all = catalogSource();
        for (String tile : TILES) {
            if (!all.contains(tile)) {
                return false;
            }
        }
        return true;
    }

    public String catalogSource() {
        StringBuilder seen = new StringBuilder(pageSource());
        for (int i = 0; i < 5; i++) {
            swipeUp();
            sleep(300);
            seen.append('\n').append(pageSource());
        }
        return seen.toString();
    }

    /** Hello, the Financial Calculators header, and the EMI Calculator tile occupy separate areas. */
    public boolean headerGreetingAndOptionSeparated() {
        org.openqa.selenium.WebElement hello = firstPresent(java.time.Duration.ofSeconds(3),
                com.airpay.mobile.utilities.waits.MobileWaits.xpath("//*[@content-desc='Hello']"));
        org.openqa.selenium.WebElement header = firstPresent(java.time.Duration.ofSeconds(3),
                com.airpay.mobile.utilities.waits.MobileWaits.xpath("//*[@content-desc='Financial Calculators']"));
        org.openqa.selenium.WebElement option = firstPresent(java.time.Duration.ofSeconds(3),
                com.airpay.mobile.utilities.waits.MobileWaits.xpath("//*[@content-desc='EMI Calculator']"));
        if (hello == null || header == null || option == null) {
            return false;
        }
        return separated(hello, header) && separated(header, option) && separated(hello, option);
    }

    private static boolean separated(org.openqa.selenium.WebElement first, org.openqa.selenium.WebElement second) {
        org.openqa.selenium.Rectangle a = first.getRect();
        org.openqa.selenium.Rectangle b = second.getRect();
        boolean overlapsX = a.getX() < b.getX() + b.getWidth() && b.getX() < a.getX() + a.getWidth();
        boolean overlapsY = a.getY() < b.getY() + b.getHeight() && b.getY() < a.getY() + a.getHeight();
        return !(overlapsX && overlapsY);
    }

    /** Enters the Login-sheet PIN and skips biometrics when a Back step returns to the lock screen. */
    public void unlockIfNeeded() {
        ensureForeground();
        if (!sourceContains("Enter your Airpay Money PIN", "Unlock faster with Biometrics", "Forgot PIN")) {
            return;
        }
        BiometricScreen biometric = new BiometricScreen(context);
        biometric.dismissIfShown();
        PinUnlockScreen pin = new PinUnlockScreen(context);
        if (pin.waitUntilDisplayed(Duration.ofSeconds(3)) || sourceContains("Enter your Airpay Money PIN")) {
            pin.enterPin(MoneyExcel.pin("880272"));
            sleep(1200);
        }
        biometric.dismissIfShown();
    }

    public boolean openTile(String label) {
        return openTile(label, null);
    }

    /**
     * Scroll the 3-column grid until {@code label} is in view, then tap the tile
     * <em>label</em> (or icon) — the geometric centre is a dead gap (analysis F3).
     * Lower rows (Retirement through Budget) sit below the first viewport on 720x1600.
     */
    public boolean openTile(String label, String expectedPageTitle) {
        unlockIfNeeded();
        CalculatorScreen detail = new CalculatorScreen(context);
        if (openedCalculator(detail, expectedPageTitle)) {
            return true;
        }
        if (!isDisplayed()) {
            return false;
        }
        for (int i = 0; i < 2; i++) {
            swipeHub(false);
        }
        sleep(200);
        for (int attempt = 0; attempt < 8; attempt++) {
            if (tileInTree(label) && tapNamedTile(label)) {
                sleep(500);
                if (openedCalculator(detail, expectedPageTitle)) {
                    return true;
                }
                if (!stillOnHubGrid()) {
                    new CalculatorScreen(context).tapAppBack();
                    sleep(400);
                }
            }
            swipeHub(true);
            sleep(250);
        }
        for (int i = 0; i < 3; i++) {
            swipeHub(false);
        }
        sleep(200);
        if (tapNamedTile(label)) {
            sleep(500);
        }
        return openedCalculator(detail, expectedPageTitle);
    }

    /** Leave leftover product/calculator pages, then open the Calculators tab. */
    public boolean ensureOpen() {
        unlockIfNeeded();
        if (isDisplayed()) {
            return true;
        }
        for (int i = 0; i < 6 && !isDisplayed(); i++) {
            String src = pageSource();
            if (stillOnHubGrid()) {
                return true;
            }
            boolean rootTab = src.contains("Calculators")
                    && (src.contains("FinSight") || src.contains("Products") || src.contains("Home"));
            boolean inProfile = src.toUpperCase().contains("PROFILE SETTINGS")
                    || src.contains("Personal Details") || src.contains("My Documents");
            if (rootTab && !inProfile) {
                // Top-left on Home / FinSight / Calculators is Open settings — never Back.
                tapCalculatorsNav();
                sleep(500);
                continue;
            }
            if (inProfile || src.contains("Privacy Policy")) {
                new CalculatorScreen(context).tapAppBack();
                sleep(400);
                continue;
            }
            if (!src.contains("Financial Calculators")) {
                new CalculatorScreen(context).tapAppBack();
                sleep(400);
            }
            tapCalculatorsNav();
            sleep(400);
            unlockIfNeeded();
        }
        tapCalculatorsNav();
        sleep(700);
        return isDisplayed();
    }

    private void tapCalculatorsNav() {
        if (tapFirstAny(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Calculators']"),
                accessibility("Calculators"))) {
            return;
        }
        pressCalculatorsTab();
    }

    private void pressCalculatorsTab() {
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        int systemNav = Math.max(96, size[1] / 16);
        int tabMid = Math.max(28, size[1] / 48);
        shellPress((int) (size[0] * 5.0 / 6.0), size[1] - systemNav - tabMid);
    }

    private static String[] tileAliases(String label) {
        String lower = label.toLowerCase();
        if (lower.contains("buy") && lower.contains("rent")) {
            return new String[]{
                    label,
                    "Buy vs Rent Calculator",
                    "Buy Vs Rent Calculator",
                    "Buy vs Rent calculator",
                    "Buy Vs Rent calculator",
                    "Buy vs Rent",
                    "Buy Vs Rent"
            };
        }
        if (lower.contains("lump")) {
            return new String[]{
                    label,
                    "Lumpsum Calculator",
                    "Lump Sum Calculator",
                    "Lumpsum",
                    "Lump Sum"
            };
        }
        return new String[]{label};
    }

    private boolean tileInTree(String label) {
        String src = pageSource();
        for (String alias : tileAliases(label)) {
            if (src.contains(alias)) {
                return true;
            }
        }
        return false;
    }

    private boolean tapNamedTile(String label) {
        for (String alias : tileAliases(label)) {
            if (tapTileOnLabel(alias)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Icon (top ~20%) and label (bottom ~80%) are tappable. The vertical gap at
     * the tile centre is not. W3C {@code click()} hits that gap, so press the label.
     */
    private boolean tapTileOnLabel(String name) {
        int[] box = tileBox(name);
        if (box == null) {
            return tapFirstAny(Duration.ofSeconds(1),
                    MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(name) + "]//android.widget.ImageView"),
                    MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(name) + "]"),
                    accessibility(name));
        }
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        int x = (box[0] + box[2]) / 2;
        int h = Math.max(1, box[3] - box[1]);
        int labelY = box[1] + (int) (h * 0.82);
        int iconY = box[1] + (int) (h * 0.18);
        if (onScreen(x, labelY, size)) {
            shellPress(x, labelY);
            return true;
        }
        if (onScreen(x, iconY, size)) {
            shellPress(x, iconY);
            return true;
        }
        return false;
    }

    private int[] tileBox(String name) {
        org.openqa.selenium.WebElement el = firstPresent(Duration.ofMillis(600),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(name) + "]"),
                accessibility(name));
        if (el != null) {
            try {
                var rect = el.getRect();
                if (rect.getWidth() >= 8 && rect.getHeight() >= 8) {
                    return new int[]{rect.getX(), rect.getY(),
                            rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight()};
                }
            } catch (Exception ignored) {
                // parse dump
            }
        }
        return boundsFromSource(name);
    }

    private int[] boundsFromSource(String name) {
        String src = pageSource();
        String needle = "content-desc=\"" + name + "\"";
        int at = src.indexOf(needle);
        if (at < 0) {
            return null;
        }
        int tagEnd = src.indexOf('>', at);
        int boundsAt = src.indexOf("bounds=\"[", at);
        if (boundsAt < 0 || (tagEnd >= 0 && boundsAt > tagEnd)) {
            return null;
        }
        int start = boundsAt + "bounds=\"[".length();
        int end = src.indexOf(']', start);
        if (end < 0) {
            return null;
        }
        String first = src.substring(start, end);
        int secondOpen = src.indexOf('[', end);
        int secondClose = src.indexOf(']', secondOpen);
        if (secondOpen < 0 || secondClose < 0) {
            return null;
        }
        String second = src.substring(secondOpen + 1, secondClose);
        String[] a = first.split(",");
        String[] b = second.split(",");
        if (a.length != 2 || b.length != 2) {
            return null;
        }
        return new int[]{
                Integer.parseInt(a[0].trim()), Integer.parseInt(a[1].trim()),
                Integer.parseInt(b[0].trim()), Integer.parseInt(b[1].trim())
        };
    }

    private static boolean onScreen(int x, int y, int[] size) {
        return x >= 8 && x <= size[0] - 8 && y >= 80 && y <= size[1] - 160;
    }

    /** Hub list: swipe at the left gutter so a tile centre is not dragged. */
    private void swipeHub(boolean up) {
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        int x = Math.max(24, (int) (size[0] * 0.08));
        int startY = (int) (size[1] * (up ? 0.72 : 0.34));
        int endY = (int) (size[1] * (up ? 0.40 : 0.70));
        adbSwipe(x, startY, x, endY, 280);
    }

    private boolean openedCalculator(CalculatorScreen detail, String expectedPageTitle) {
        if (stillOnHubGrid()) {
            return false;
        }
        if (expectedPageTitle == null || expectedPageTitle.isBlank()) {
            return true;
        }
        if (detail.showsTitle(expectedPageTitle)) {
            return true;
        }
        String lower = expectedPageTitle.toLowerCase();
        if (lower.contains("buy")) {
            return detail.showsTitle("Buy Vs Rent calculator")
                    || detail.showsTitle("Buy vs Rent calculator")
                    || detail.showsTitle("Buy Vs Rent");
        }
        if (lower.contains("lump")) {
            return detail.showsTitle("Lumpsum Calculator")
                    || detail.showsTitle("Lump Sum Calculator")
                    || detail.showsTitle("Lumpsum");
        }
        if (lower.contains("term insurance") || lower.contains("retirement")) {
            return detail.showsTitle("Retirement Calculator")
                    || detail.showsTitle("Term Insurance");
        }
        if (lower.contains("fixed deposit") || "fd calculator".equals(lower)) {
            return detail.showsTitle("Fixed Deposit");
        }
        return false;
    }

    private void pressElementLong(org.openqa.selenium.WebElement element, int holdMs) {
        var rect = element.getRect();
        String udid = context.config().get("android.udid", "");
        int x = rect.getX() + rect.getWidth() / 2;
        int y = rect.getY() + rect.getHeight() / 2;
        com.airpay.mobile.utilities.android.AdbCommands.shell(udid, "input", "swipe",
                Integer.toString(x), Integer.toString(y),
                Integer.toString(x), Integer.toString(y), Integer.toString(holdMs));
    }

    /** Hub catalog still showing — tile tap did not open a calculator page. */
    private boolean stillOnHubGrid() {
        String src = pageSource();
        // 2.1.4 hub shows Hello + tiles; the "Financial Calculators" heading is often absent.
        return src.contains("EMI Calculator") && src.contains("SIP Calculator")
                && (src.contains("Hello") || src.contains("Financial Calculators")
                || src.contains("Budget Calculator"));
    }

    /**
     * Do not force Money into the foreground mid-suite ({@code am start} / activateApp).
     * Stay on the current screen; only unlock PIN/biometric when already on Money.
     * Foreground restore is allowed only for cases that explicitly need it (e.g. OTP).
     */
    public void ensureForeground() {
        // intentionally empty — never am start / activateApp between calculator steps
    }

    public static final String TAB_CALCULATORS = "//android.widget.Button[@content-desc='Calculators']";
    public static final String TAB_HOME = "//android.widget.Button[@content-desc='Home']";
    public static final String TAB_FINSIGHT = "//android.widget.Button[@content-desc='FinSight']";

    public static final String OPEN_SETTINGS = "//android.widget.Button[@content-desc='Open settings']";
    public static final String NOTIFICATIONS = "//android.widget.ImageView[@content-desc='Notifications']";
    public static final String GREETING = "//android.view.View[@content-desc='Hello']";
    public static final String USER_NAME = "//android.view.View[@content-desc='Shubham Verma']";

    public static final String HERO_TITLE = "//android.view.View[@content-desc='Financial Calculators']";
    public static final String HERO_SUBTITLE =
            "//android.view.View[@content-desc='Empower your money decisions with our suite of calculators']";
    /** Hero illustration above the grid — unlabelled, so only reachable positionally. */
    public static final String HERO_IMAGE = "//android.widget.ImageView[@content-desc='' and @clickable='true']";

    public static final String TILE_EMI = "//android.view.View[@content-desc='EMI Calculator']";
    public static final String TILE_SIP = "//android.view.View[@content-desc='SIP Calculator']";
    public static final String TILE_LUMPSUM = "//android.view.View[@content-desc='Lumpsum Calculator']";
    public static final String TILE_PPF = "//android.view.View[@content-desc='PPF Calculator']";
    public static final String TILE_EPF = "//android.view.View[@content-desc='EPF Calculator']";
    public static final String TILE_FD = "//android.view.View[@content-desc='FD Calculator']";
    public static final String TILE_NPS = "//android.view.View[@content-desc='NPS Calculator']";
    public static final String TILE_RETIREMENT = "//android.view.View[@content-desc='Retirement Calculator']";
    public static final String TILE_CROREPATI = "//android.view.View[@content-desc='Crorepati Calculator']";
    public static final String TILE_TERM_INSURANCE = "//android.view.View[@content-desc='Term Insurance Calculator']";
    public static final String TILE_BUY_VS_RENT = "//android.view.View[@content-desc='Buy vs Rent Calculator']";
    public static final String TILE_TAXATION_INDIAN_EQUITY = "//android.view.View[@content-desc='Taxation Indian Equity']";
    public static final String TILE_TAXATION_INTERNATIONAL_EQUITY =
            "//android.view.View[@content-desc='Taxation International Equity']";
    public static final String TILE_INFLATION = "//android.view.View[@content-desc='Inflation Calculator']";
    public static final String TILE_BUDGET = "//android.view.View[@content-desc='Budget Calculator']";

    public static String tile(String label) {
        return "//android.view.View[@content-desc='" + label + "']";
    }
}
