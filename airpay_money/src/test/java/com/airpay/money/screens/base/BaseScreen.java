package com.airpay.money.screens.base;

import com.airpay.mobile.utilities.android.AdbCommands;
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

    public void tapHeaderBack() {
        if (tapLabel("Back")) {
            sleep(400);
            return;
        }
        WebElement back = headerBackIcon();
        if (back != null) {
            pressElement(back);
        } else {
            shellPress(79, 174);
        }
        sleep(700);
    }

    /** Blank back image in the top-left header. It has no content description. */
    private WebElement headerBackIcon() {
        WebElement back = null;
        int bestArea = Integer.MAX_VALUE;
        for (WebElement node : driver.findElements(MobileWaits.xpath("//*[@clickable='true']"))) {
            try {
                if (!node.isDisplayed()) {
                    continue;
                }
                String desc = String.valueOf(node.getAttribute("content-desc"));
                if (desc.contains("Back")) {
                    return node;
                }
                var rect = node.getRect();
                int area = rect.getWidth() * rect.getHeight();
                if (rect.getY() < 280 && rect.getX() < 220 && rect.getWidth() < 220
                        && rect.getHeight() < 220 && area < bestArea
                        && (desc.isBlank() || "null".equals(desc))) {
                    back = node;
                    bestArea = area;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return back;
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

    protected void tapAt(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(tap));
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
            driver.findElement(AppiumBy.androidUIAutomator(
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
        Dimension size = driver.manage().window().getSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.78);
        int endY = (int) (size.height * 0.28);
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

    protected static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
