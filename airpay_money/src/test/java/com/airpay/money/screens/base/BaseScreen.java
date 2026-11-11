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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseScreen {

    private static final Logger LOG = LogManager.getLogger(BaseScreen.class);

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
            for (By by : locators) {
                for (WebElement el : findElementsTimed(by, 5)) {
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
            for (By by : locators) {
                for (WebElement el : findElementsTimed(by, 3)) {
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
            for (By by : locators) {
                for (WebElement el : findElementsTimed(by, 5)) {
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

    private List<WebElement> findElementsTimed(By by, int seconds) {
        try {
            List<WebElement> found = CompletableFuture.supplyAsync(() -> live().findElements(by))
                    .orTimeout(seconds, TimeUnit.SECONDS)
                    .join();
            return found != null ? found : List.of();
        } catch (Exception e) {
            LOG.warn("findElements hung or failed: {}", e.toString());
            recoverIfInstrumentationDead(e);
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
        LOG.warn("UiAutomator2 hung or died on getPageSource — recovering session");
        try {
            context.setDriver(com.airpay.mobile.utilities.driver.AppiumManager.recover(context.config()));
        } catch (Exception recoverFailed) {
            LOG.warn("Session recover failed: {}", recoverFailed.toString());
            return "";
        }
        src = fetchPageSource(8);
        return src != null ? src : "";
    }

    /** Bound getPageSource so a dead UiAutomator2 cannot sit on newCommandTimeout (4h). */
    private String fetchPageSource(int seconds) {
        try {
            return CompletableFuture.supplyAsync(() -> live().getPageSource())
                    .orTimeout(seconds, TimeUnit.SECONDS)
                    .join();
        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            String msg = cause.getMessage() == null ? cause.toString() : cause.getMessage();
            String lower = msg.toLowerCase();
            if (lower.contains("timeout")
                    || lower.contains("instrumentation process is not running")
                    || lower.contains("cannot be proxied to uiautomator2")
                    || lower.contains("nosuchsession")
                    || lower.contains("session is either terminated")) {
                LOG.warn("getPageSource failed: {}", msg);
                return null;
            }
            throw e instanceof RuntimeException re ? re : new RuntimeException(e);
        }
    }

    /** If launcher/search stole focus, bring Money back via adb only (Appium getCurrentPackage hangs this device). */
    public void ensureMoneyInForeground() {
        String udid = context.config().get("android.udid", "");
        String pkg = AdbInput.foregroundPackage(udid);
        if (pkg.isBlank() || "com.finmapp.fininfinity".equals(pkg)) {
            return;
        }
        LOG.info("Money not in foreground (pkg={}) — am start", pkg);
        AdbInput.startActivity(udid,
                context.config().get("money.app.package", "com.finmapp.fininfinity"),
                context.config().get("money.app.activity", "com.finmapp.fininfinity.MainActivity"));
        sleep(1500);
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
        String src = safeSource();
        if (src.contains("Open settings") && (src.contains("Hello") || src.contains("Financial Calculators")
                || src.contains("Your Financial Health Score"))) {
            return;
        }
        if (tapFirstAny(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Back']"),
                MobileWaits.xpath("//*[@content-desc='Back']"),
                AppiumBy.accessibilityId("Back"))) {
            sleep(400);
            return;
        }
        WebElement back = headerBackIcon();
        if (back != null) {
            pressElement(back);
        } else {
            pressHeaderBackChip();
        }
        sleep(700);
    }

    /** Top-left Back chip on 720x1600 (and similar). Hardcoded 79,174 misses this phone. */
    protected void pressHeaderBackChip() {
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        int x = Math.max(48, (int) (size[0] * 0.08));
        int y = Math.max(96, (int) (size[1] * 0.075));
        shellPress(x, y);
    }

    /**
     * Top-left Back only. Do not scan every clickable node — that query crashes UiAutomator2
     * and then hangs the rest of the suite.
     */
    private WebElement headerBackIcon() {
        return firstPresent(Duration.ofSeconds(1),
                MobileWaits.xpath("//*[contains(@content-desc,'Back')]"),
                AppiumBy.accessibilityId("Back"));
    }

    protected void pressElement(WebElement element) {
        var rect = element.getRect();
        shellPress(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
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
        if (tapLabelFast("Back")) {
            sleep(400);
            return true;
        }
        if (tapFirstAny(Duration.ofSeconds(1),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Back']"),
                MobileWaits.xpath("//*[@content-desc='Back']"))) {
            sleep(400);
            return true;
        }
        WebElement back = headerBackIcon();
        if (back != null) {
            pressElement(back);
            sleep(400);
            return true;
        }
        pressHeaderBackChip();
        sleep(400);
        return true;
    }

    /**
     * Try in-app Back first. If the screen does not change (app Back broken / missing),
     * fall back to Android system Back once — but never when already on a root tab
     * (system Back would finish Money / land on the launcher).
     * Never brings the app back to foreground after system Back.
     */
    public boolean leaveViaBack() {
        String before = screenFingerprint();
        boolean tapped = tapBack();
        sleep(500);
        if (tapped && !before.equals(screenFingerprint())) {
            return true;
        }
        if (onRootTabScreen()) {
            LOG.info("In-app Back did not leave, but already on a root tab — skipping system Back (would leave Money)");
            return false;
        }
        // One in-app header chip retry. System Back is not used — it backgrounds Money
        // and looks like the session was dismissed.
        pressHeaderBackChip();
        sleep(500);
        return !before.equals(screenFingerprint());
    }

    /**
     * True only on a real tab hub (not a nested Flutter page that still shows the bottom nav).
     * System Back on these screens finishes Money / lands on the launcher.
     */
    protected boolean onRootTabScreen() {
        String src = safeSource();
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
                || (src.contains("content-desc=\"Hello\"") && src.contains("Open settings"));
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

    protected static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
