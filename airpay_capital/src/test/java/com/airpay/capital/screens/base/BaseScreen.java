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
