package com.airpay.capital.screens.base;

import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.function.BooleanSupplier;

/** Base for all Capital screens — PageFactory init + shared gestures and waits. */
public abstract class BaseScreen {

    protected final TestContext context;
    protected final AndroidDriver driver;
    protected final MobileWaits waits;

    protected BaseScreen(TestContext context) {
        this.context = context;
        this.driver = context.driver();
        this.waits = new MobileWaits(driver, Duration.ofMillis(
                context.config().getInt("timeout.action", 20000)));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void tap(WebElement element) {
        element.click();
    }

    protected void type(WebElement field, String value) {
        if (value == null || value.isBlank()) {
            return;
        }
        String current = field.getAttribute("text");
        if (current != null && current.trim().equalsIgnoreCase(value.trim())) {
            return;
        }
        try {
            field.click();
        } catch (Exception ignored) {
            var rect = field.getRect();
            tapAt(rect.x + Math.max(rect.width / 2, 1), rect.y + Math.max(rect.height / 2, 1));
        }
        sleep(250);
        try {
            field.clear();
        } catch (Exception ignored) {
            // Flutter fields often reject clear()
        }
        field.sendKeys(value);
        sleep(200);
    }

    protected boolean tapFirst(Duration wait, By... locators) {
        long end = System.currentTimeMillis() + wait.toMillis();
        while (System.currentTimeMillis() < end) {
            for (By by : locators) {
                try {
                    for (WebElement el : driver.findElements(by)) {
                        try {
                            if (el.isDisplayed() && el.isEnabled()) {
                                el.click();
                                return true;
                            }
                        } catch (Exception ignored) {
                            // stale
                        }
                    }
                } catch (Exception ignored) {
                    // invalid locator on this screen
                }
            }
            sleep(300);
        }
        return false;
    }

    /** Flutter often ignores WebElement.click() and clickGesture. A short W3C touch does register. */
    protected boolean tapCenter(By by) {
        try {
            List<WebElement> els = driver.findElements(by);
            if (els.isEmpty()) {
                return false;
            }
            var r = els.get(0).getRect();
            int x = r.x + Math.max(r.width / 2, 1);
            int y = r.y + Math.max(r.height / 2, 1);
            tapAt(x, y);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected void tapAt(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new org.openqa.selenium.interactions.Pause(finger, Duration.ofMillis(120)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
    }

    /** OS-level tap. Flutter sometimes ignores the WebDriver pointer on the same coordinate. */
    protected void adbTap(int x, int y) {
        String udid = context.config().get("android.udid", "");
        com.airpay.mobile.utilities.android.AdbCommands.shell(
                udid, "input", "tap", Integer.toString(x), Integer.toString(y));
    }

    protected WebElement firstPresent(Duration wait, By... locators) {
        long end = System.currentTimeMillis() + wait.toMillis();
        while (System.currentTimeMillis() < end) {
            for (By by : locators) {
                try {
                    for (WebElement el : driver.findElements(by)) {
                        try {
                            if (el.isDisplayed()) {
                                return el;
                            }
                        } catch (Exception ignored) {
                            // stale
                        }
                    }
                } catch (Exception ignored) {
                    // ignore
                }
            }
            sleep(250);
        }
        return null;
    }

    protected By accessibility(String id) {
        return AppiumBy.accessibilityId(id);
    }

    public String pageSource() {
        return driver.getPageSource();
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
            return driver.getPageSource();
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

    public boolean tapAny(Collection<String> labels) {
        for (String label : labels) {
            if (tapLabel(label)) {
                return true;
            }
        }
        return false;
    }

    public boolean tapBack() {
        if (tapLabel("Back")) {
            return true;
        }
        try {
            driver.navigate().back();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void swipeUp() {
        swipeVertical(0.78, 0.28);
    }

    /** Scroll the list toward the top (content that sits above the viewport). */
    public void swipeDown() {
        swipeVertical(0.32, 0.78);
    }

    private void swipeVertical(double startRatio, double endRatio) {
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * startRatio);
        int endY = (int) (size.height * endRatio);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
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

    /**
     * Polls page source until any fragment appears, or timeout elapses.
     * Prefer this over Thread.sleep for screen transitions.
     */
    public boolean waitUntilSourceContains(Duration timeout, String... fragments) {
        return waits.until(() -> sourceContains(fragments), timeout);
    }

    /** Wait until none of the fragments are present (e.g. OTP screen gone). */
    public boolean waitUntilSourceGone(Duration timeout, String... fragments) {
        return waits.until(() -> !sourceContains(fragments), timeout);
    }

    protected static void sleep(long ms) {
        MobileWaits.sleep(ms);
    }
}
