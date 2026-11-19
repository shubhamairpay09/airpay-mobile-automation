package com.airpay.money.screens.base;

import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.android.AdbInput;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BooleanSupplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseScreen {

    private static final Logger LOG = LogManager.getLogger(BaseScreen.class);
    private static volatile ExecutorService APPIUM_IO = newAppiumIoExecutor();
    private static final long MIN_FIND_MS = 2_000;
    /** A failed recover must not be retried on every page read — that flood stops a new session starting. */
    private static long recoverBlockedUntil;

    private static ExecutorService newAppiumIoExecutor() {
        return Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r, "appium-io");
            t.setDaemon(true);
            return t;
        });
    }

    /** After UiAutomator2 dies, the worker thread can stay blocked on the old driver — replace the pool. */
    private static synchronized void resetAppiumIoExecutor() {
        try {
            APPIUM_IO.shutdownNow();
        } catch (Exception ignored) {
            // stale pool
        }
        APPIUM_IO = newAppiumIoExecutor();
    }

    protected final TestContext context;
    protected final AndroidDriver driver;
    protected final MobileWaits waits;

    protected BaseScreen(TestContext context) {
        this.context = context;
        this.driver = context.driver();
        this.waits = new MobileWaits(driver, Duration.ofMillis(
                context.config().getInt("timeout.action", 20000)));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofMillis(200)), this);
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void type(WebElement field, String value) {
        field.click();
        try {
            field.clear();
        } catch (Exception ignored) {
            // Flutter fields often reject clear()
        }
        field.sendKeys(value);
    }

    protected boolean tapFirst(Duration wait, By... locators) {
        long end = System.currentTimeMillis() + wait.toMillis();
        long poll = Math.min(80, Math.max(40, wait.toMillis() / 5));
        while (System.currentTimeMillis() < end) {
            long remain = end - System.currentTimeMillis();
            if (remain <= 0) {
                break;
            }
            long perFind = Math.min(400, remain);
            for (By by : locators) {
                remain = end - System.currentTimeMillis();
                if (remain <= 0) {
                    break;
                }
                for (WebElement el : findElementsTimed(by, Math.min(perFind, remain))) {
                    try {
                        if (el.isDisplayed() && el.isEnabled()) {
                            el.click();
                            return true;
                        }
                    } catch (Exception ignored) {
                        // stale
                    }
                }
            }
            sleep(poll);
        }
        return false;
    }

    /** Click the first match even when UiAutomator reports isDisplayed=false (Flutter lists). */
    protected boolean tapFirstAny(Duration wait, By... locators) {
        long end = System.currentTimeMillis() + wait.toMillis();
        while (System.currentTimeMillis() < end) {
            long remain = end - System.currentTimeMillis();
            if (remain <= 0) {
                break;
            }
            long perFind = Math.min(400, remain);
            for (By by : locators) {
                remain = end - System.currentTimeMillis();
                if (remain <= 0) {
                    break;
                }
                for (WebElement el : findElementsTimed(by, Math.min(perFind, remain))) {
                    try {
                        el.click();
                        return true;
                    } catch (Exception ignored) {
                        try {
                            if (el instanceof org.openqa.selenium.remote.RemoteWebElement remote) {
                                live().executeScript("mobile: clickGesture",
                                        java.util.Map.of("elementId", remote.getId()));
                                return true;
                            }
                        } catch (Exception ignored2) {
                            // try next node
                        }
                    }
                }
            }
            sleep(80);
        }
        return false;
    }

    protected WebElement firstPresent(Duration wait, By... locators) {
        long end = System.currentTimeMillis() + wait.toMillis();
        long poll = Math.min(80, Math.max(40, wait.toMillis() / 5));
        while (System.currentTimeMillis() < end) {
            long remain = end - System.currentTimeMillis();
            if (remain <= 0) {
                break;
            }
            long perFind = Math.min(400, remain);
            for (By by : locators) {
                remain = end - System.currentTimeMillis();
                if (remain <= 0) {
                    break;
                }
                for (WebElement el : findElementsTimed(by, Math.min(perFind, remain))) {
                    try {
                        if (el.isDisplayed()) {
                            return el;
                        }
                    } catch (Exception ignored) {
                        // stale
                    }
                }
            }
            sleep(poll);
        }
        return null;
    }

    protected List<WebElement> findElementsTimed(By by, long timeoutMs) {
        Future<List<WebElement>> future = APPIUM_IO.submit(() -> live().findElements(by));
        try {
            // A find on a large Flutter tree (Products hub) takes ~1s; a shorter cap reports present nodes as missing.
            List<WebElement> found = future.get(Math.max(MIN_FIND_MS, timeoutMs), TimeUnit.MILLISECONDS);
            return found != null ? found : List.of();
        } catch (TimeoutException e) {
            // Miss budget elapsed — not a UiAutomator crash. Do not recover or warn.
            return List.of();
        } catch (Exception e) {
            future.cancel(true);
            if (!recoverIfInstrumentationDead(e)) {
                LOG.debug("findElements failed: {}", e.toString());
            }
            return List.of();
        }
    }

    private boolean recoverIfInstrumentationDead(Throwable e) {
        String msg = e.getMessage() == null ? "" : e.getMessage().toLowerCase();
        if (!msg.contains("instrumentation process is not running")
                && !msg.contains("cannot be proxied to uiautomator2")
                && !msg.contains("nosuchsession")) {
            return false;
        }
        LOG.warn("UiAutomator2 died on findElements — recovering session once");
        resetAppiumIoExecutor();
        try {
            context.setDriver(com.airpay.mobile.utilities.driver.AppiumManager.recover(context.config()));
        } catch (Exception recoverFailed) {
            LOG.warn("Session recover failed: {}", recoverFailed.toString());
        }
        return true;
    }

    protected By accessibility(String id) {
        return AppiumBy.accessibilityId(id);
    }

    protected AndroidDriver live() {
        AndroidDriver current = context.driver();
        return current != null ? current : driver;
    }

    public String pageSource() {
        String src = fetchPageSource(8);
        if (src != null) {
            return src;
        }
        // A heavy Flutter screen can outlast one read. Recovering here used to quit a live session.
        src = fetchPageSource(8);
        if (src != null) {
            return src;
        }
        if (System.currentTimeMillis() < recoverBlockedUntil) {
            return "";
        }
        LOG.warn("UiAutomator2 hung or died on getPageSource — recovering session");
        resetAppiumIoExecutor();
        try {
            context.setDriver(com.airpay.mobile.utilities.driver.AppiumManager.recover(context.config()));
        } catch (Exception recoverFailed) {
            LOG.warn("Session recover failed: {}", recoverFailed.toString());
            recoverBlockedUntil = System.currentTimeMillis() + 15_000;
            return "";
        }
        src = fetchPageSource(8);
        return src != null ? src : "";
    }

    /** Bound getPageSource so a dead UiAutomator2 cannot sit on newCommandTimeout (4h). */
    private String fetchPageSource(int seconds) {
        Future<String> future = APPIUM_IO.submit(() -> live().getPageSource());
        try {
            return future.get(seconds, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            resetAppiumIoExecutor();
            LOG.warn("getPageSource timed out after {}s", seconds);
            return null;
        } catch (Exception e) {
            future.cancel(true);
            resetAppiumIoExecutor();
            LOG.warn("getPageSource failed: {}", e.toString());
            return null;
        }
    }

    /** If another app stole focus, bring Money forward. Never uses getCurrentPackage (hangs this realme). */
    public void ensureMoneyInForeground() {
        String udid = context.config().get("android.udid", "");
        String pkg = context.config().get("money.app.package", "com.finmapp.fininfinity");
        if (AdbInput.isForeground(udid, pkg)) {
            return;
        }
        String activity = context.config().get("money.app.activity", "com.finmapp.fininfinity.MainActivity");
        LOG.info("Money not on screen — am start {}", pkg);
        AdbInput.startActivity(udid, pkg, activity);
        waitUntil(() -> AdbInput.isForeground(udid, pkg), Duration.ofSeconds(3));
    }

    /** Short list of on-screen labels, so a failed check names what was actually open. */
    public String visibleLabels() {
        String source;
        try {
            source = pageSource();
        } catch (Exception e) {
            return "(screen not available)";
        }
        if (source == null) {
            return "(screen not available)";
        }
        Matcher matcher = Pattern.compile("content-desc=\"([^\"]{1,80})\"").matcher(source);
        LinkedHashSet<String> labels = new LinkedHashSet<>();
        while (matcher.find() && labels.size() < 14) {
            String value = matcher.group(1).replace("&#10;", " ").trim();
            if (!value.isBlank() && !"null".equals(value)) {
                labels.add(value);
            }
        }
        return labels.isEmpty() ? "(no labelled controls)" : String.join(" | ", labels);
    }

    public boolean sourceContains(String... fragments) {
        String src = safeSource();
        for (String f : fragments) {
            if (src.contains(f)) {
                return true;
            }
        }
        return false;
    }

    protected String safeSource() {
        try {
            return pageSource();
        } catch (Exception e) {
            return "";
        }
    }

    public boolean tapLabel(String label) {
        return tapFirst(Duration.ofSeconds(4),
                accessibility(label),
                MobileWaits.xpath("//*[@clickable='true' and @content-desc=" + xpathLiteral(label) + "]"),
                MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc," + xpathLiteral(label) + ")]"),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(label) + ")]"));
    }

    /** Fast deep-scan tap: ~400ms miss budget (not 4s). Prefer when probing many labels. */
    public boolean tapLabelFast(String label) {
        return tapFirst(Duration.ofMillis(400),
                accessibility(label),
                MobileWaits.xpath("//*[@clickable='true' and @content-desc=" + xpathLiteral(label) + "]"),
                MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc," + xpathLiteral(label) + ")]"),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(label) + ")]"));
    }

    public boolean tapAny(Collection<String> labels) {
        for (String label : labels) {
            if (tapLabel(label)) {
                return true;
            }
        }
        return false;
    }

    public void tapHeaderBack() {
        tapInAppBack();
    }

    /**
     * Top-left Back chevron. Live dump on this phone: Button bounds [40,80][112,160].
     * Never tap it on Home / FinSight / Calculators — that chip is Open settings (profile).
     */
    protected boolean pressHeaderBackChip() {
        String src = safeSource();
        if (src.isBlank() || isTabHub(src) || headerIsOpenSettings(src) || src.contains("Open settings")) {
            return false;
        }
        if (tapFirst(Duration.ofMillis(500),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Back']"),
                MobileWaits.xpath("//*[@content-desc='Back']"))) {
            return true;
        }
        return tapUnlabeledTopLeftBackBounds();
    }

    /** Flutter 2.1.4 detail pages: unlabeled chevron ~[40,80][112,160] on 720×1600. Never on tab hubs. */
    protected boolean tapUnlabeledTopLeftBackBounds() {
        String src = safeSource();
        if (src.isBlank() || isTabHub(src) || headerIsOpenSettings(src)) {
            return false;
        }
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        int x = Math.max(40, (int) (size[0] * 0.105));
        int y = Math.max(96, 64 + (int) (size[1] * 0.035));
        shellPress(x, y);
        sleep(250);
        return true;
    }

    protected void pressElement(WebElement element) {
        var rect = element.getRect();
        String before = screenFingerprint();
        shellPress(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
        waitUntilChanged(before, actionUiWait());
    }

    protected Duration actionUiWait() {
        boolean fast = context.config().getBoolean("money.functional.fast", false);
        return Duration.ofMillis(fast ? 900 : 1_500);
    }

    /** A short press. A zero-length click does not reach these Flutter controls. */
    protected boolean shellPress(int x, int y) {
        String udid = context.config().get("android.udid", "");
        int code = AdbCommands.shell(udid, "input", "swipe",
                Integer.toString(x), Integer.toString(y),
                Integer.toString(x), Integer.toString(y), "180");
        if (code != 0) {
            tapAt(x, y);
            return false;
        }
        return true;
    }

    public void pressSystemBack() {
        String udid = context.config().get("android.udid", "");
        AdbCommands.shell(udid, "input", "keyevent", "4");
        sleep(400);
    }

    /**
     * In-app header Back only. Prefer {@link #leaveViaBack()} when the Flutter Back control is dead.
     */
    public boolean tapBack() {
        return tapInAppBack();
    }

    /**
     * Top-left in-app Back chevron. Do not search for a "Back" label — 2.1.4 leaves it
     * unlabeled. Never tap it on a hub (that chip is Open settings / the profile icon).
     */
    protected boolean tapInAppBack() {
        String src = safeSource();
        if (src.isBlank() || isTabHub(src) || headerIsOpenSettings(src)) {
            return false;
        }
        return pressHeaderBackChip();
    }

    /**
     * Home / FinSight / Calculators / Products hubs. Top-left is Open settings, not Back.
     * Nested FHC / calculator / profile pages are not hubs even if bottom nav is still in the tree.
     */
    protected boolean isTabHub(String src) {
        if (src == null || src.isBlank()) {
            return false;
        }
        boolean onProfileTree = src.toUpperCase().contains("PROFILE SETTINGS")
                || src.contains("My Documents")
                || src.contains("Document Details");
        if (onProfileTree) {
            return false;
        }
        if (productsHubShown(src)) {
            return true;
        }
        if (src.contains("EMI Calculator") && src.contains("SIP Calculator")
                && (src.contains("Hello") || src.contains("Financial Calculators")
                || src.contains("Budget Calculator"))) {
            return true;
        }
        if (src.contains("Your Financial Health Score") && src.contains("FinSight")
                && src.contains("Calculators") && src.contains("Open settings")) {
            return true;
        }
        if (src.contains("Featured Services") && src.contains("Open settings")) {
            return true;
        }
        return src.contains("Explore Products") && src.contains("Open settings")
                && !src.contains("content-desc=\"Back\"");
    }

    /** Any tab hub whose header is the "Hello" greeting with the initials avatar (e.g. "SV"). */
    protected static boolean productsHubShown(String src) {
        return src.contains("content-desc=\"FinSight\"")
                && src.contains("content-desc=\"Calculators\"")
                && src.contains("Hello")
                && !src.contains("content-desc=\"Back\"");
    }

    /** True only on Home / FinSight / Calculators hubs, where top-left is Open settings. */
    protected boolean headerIsOpenSettings(String src) {
        if (src == null || src.isBlank()) {
            return false;
        }
        if (src.toUpperCase().contains("PROFILE SETTINGS") || src.contains("My Documents")
                || src.contains("content-desc=\"Back\"")) {
            return false;
        }
        if (isTabHub(src)) {
            return true;
        }
        return src.contains("Open settings")
                && (src.contains("Hello") || src.contains("FinSight"))
                && src.contains("Calculators");
    }

    /** Nested pages whose top-left control is the in-app Back chevron (often unlabeled). */
    protected boolean innerScreenWithAppBack(String src) {
        return src.contains("Personal Details")
                || src.contains("Income &amp; Expense")
                || src.contains("Income & Expense")
                || src.contains("Income and Expense")
                || src.contains("Insurance Details")
                || src.contains("Add Loan")
                || src.contains("Add Investment")
                || src.contains("Lets do risk profile")
                || src.contains("Your Risk Profile")
                || src.contains("Let us know your risk")
                || src.contains("Summary Details")
                || src.contains("YOUR FINANCIAL HEALTH SCORE")
                || src.contains("Your Money Diagnosed")
                || src.contains("Question ")
                || src.contains("PROFILE SETTINGS")
                || src.contains("Profile Settings")
                || src.contains("My Documents")
                || src.contains("Document Details")
                || src.contains("Work Profile")
                || src.contains("Edit personal details")
                || src.contains("content-desc=\"Back\"");
    }

    /**
     * Try in-app Back first. If the screen does not change (app Back broken / missing),
     * fall back to Android system Back once — but never when already on a root tab
     * (system Back would finish Money / land on the launcher).
     * Never brings the app back to foreground after system Back.
     */
    public boolean leaveViaBack() {
        if (onRootTabScreen() || headerIsOpenSettings(safeSource())) {
            return false;
        }
        String before = screenFingerprint();
        if (!tapInAppBack()) {
            return false;
        }
        return waitUntilChanged(before, Duration.ofSeconds(2));
    }

    /**
     * True only on a real tab hub (not a nested Flutter page that still shows the bottom nav).
     * System Back on these screens finishes Money / lands on the launcher.
     */
    protected boolean onRootTabScreen() {
        String src = safeSource();
        if (isTabHub(src)) {
            return true;
        }
        if (innerScreenWithAppBack(src)) {
            return false;
        }
        // Hub screens. System Back here backgrounds Money and drops the session.
        if (src.contains("EMI Calculator") && src.contains("SIP Calculator")
                && (src.contains("Hello") || src.contains("Financial Calculators"))) {
            return true;
        }
        boolean bottomNav = (src.contains("content-desc=\"Products\"") || src.contains("content-desc=\"Home\""))
                && src.contains("content-desc=\"FinSight\"")
                && src.contains("content-desc=\"Calculators\"");
        if (!bottomNav) {
            return false;
        }
        if (src.contains("content-desc=\"Back\"") || src.contains("content-desc=\"Back ")) {
            return false;
        }
        return src.contains("Your Financial Health Score")
                || src.contains("Featured Services")
                || src.contains("Explore Products")
                || src.contains("Financial Calculators")
                || (src.contains("Open settings") && src.contains("FinSight"));
    }

    protected String currentAppPackage() {
        return AdbInput.foregroundPackage(context.config().get("android.udid", ""));
    }

    protected String screenFingerprint() {
        String src = safeSource();
        int max = Math.min(src.length(), 1200);
        return src.substring(0, max);
    }

    protected void tapAt(int x, int y) {
        String udid = context.config().get("android.udid", "");
        AdbInput.tap(udid, x, y);
        sleep(200);
    }

    protected void adbSwipe(int x1, int y1, int x2, int y2, int durationMs) {
        String udid = context.config().get("android.udid", "");
        AdbInput.swipe(udid, x1, y1, x2, y2, durationMs);
        sleep(450);
    }

    /**
     * Finger swipes do not move this Flutter list. UiAutomator scrolls the vertical ScrollView.
     * Returns true when a visible content-desc contains {@code fragment}.
     */
    public boolean pressDisplayed(String label) {
        WebElement target = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(label) + ")]"));
        if (target == null) {
            return false;
        }
        pressElement(target);
        sleep(800);
        return true;
    }

    public boolean scrollIntoDescription(String fragment) {
        if (pageSource().contains(fragment)) {
            return true;
        }
        String escaped = fragment.replace("\\", "\\\\").replace("\"", "\\\"");
        try {
            live().findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().className(\"android.widget.ScrollView\"))"
                            + ".setAsVerticalList().setMaxSearchSwipes(3)"
                            + ".scrollIntoView(new UiSelector().descriptionContains(\""
                            + escaped + "\"))"));
            return pageSource().contains(fragment);
        } catch (Exception e) {
            return pageSource().contains(fragment);
        }
    }

    public String descriptionContaining(String fragment) {
        String source = pageSource().replace("&#10;", "\n").replace("&amp;", "&");
        int at = source.indexOf(fragment);
        if (at < 0) {
            return "";
        }
        int start = source.lastIndexOf("content-desc=\"", at);
        if (start < 0) {
            return fragment;
        }
        start += "content-desc=\"".length();
        int end = source.indexOf('"', start);
        if (end < 0) {
            return fragment;
        }
        return source.substring(start, end).replace('\n', ' ').trim();
    }

    public void swipeUp() {
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        int startX = size[0] / 2;
        int startY = (int) (size[1] * 0.78);
        int endY = (int) (size[1] * 0.28);
        adbSwipe(startX, startY, startX, endY, 320);
    }

    public void swipeDown() {
        int[] size = AdbInput.screenSize(context.config().get("android.udid", ""));
        int startX = size[0] / 2;
        int startY = (int) (size[1] * 0.30);
        int endY = (int) (size[1] * 0.75);
        adbSwipe(startX, startY, startX, endY, 320);
    }

    protected static String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }

    protected Duration actionTimeout() {
        return Duration.ofMillis(context.config().getInt("timeout.action", 20000));
    }

    protected Duration navigationTimeout() {
        return Duration.ofMillis(context.config().getInt("timeout.navigation", 60000));
    }

    protected Duration otpTimeout() {
        return Duration.ofMillis(context.config().getLong("timeout.otp.wait.ms", 120_000));
    }

    /** Poll until the condition is true. Prefer this over a fixed Thread.sleep. */
    public boolean waitUntil(BooleanSupplier condition) {
        return waits.until(condition, actionTimeout());
    }

    public boolean waitUntil(BooleanSupplier condition, Duration timeout) {
        return waits.until(condition, timeout);
    }

    public boolean waitUntilSourceContains(Duration timeout, String... fragments) {
        return waits.until(() -> sourceContains(fragments), timeout);
    }

    public boolean waitUntilSourceGone(Duration timeout, String... fragments) {
        return waits.until(() -> !sourceContains(fragments), timeout);
    }

    /** Wait until the screen dump changes after a tap. */
    protected boolean waitUntilChanged(String before) {
        return waitUntilChanged(before, actionTimeout());
    }

    protected boolean waitUntilChanged(String before, Duration timeout) {
        return waits.until(() -> !before.equals(screenFingerprint()), timeout);
    }

    /** Tap and poll gaps stay short. Anything longer is cut to 200ms; navigation waits on the screen. */
    protected static void sleep(long ms) {
        if (ms <= 0) {
            return;
        }
        MobileWaits.sleep(Math.min(ms, 200));
    }
}
