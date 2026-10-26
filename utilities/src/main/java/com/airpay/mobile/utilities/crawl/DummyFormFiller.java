package com.airpay.mobile.utilities.crawl;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Fills visible text inputs with deterministic dummy values so Apply / Continue
 * buttons unlock during deep module crawls (Crawlee-style form progression).
 */
public final class DummyFormFiller {

    private static final Logger LOG = LogManager.getLogger(DummyFormFiller.class);

    private static final Map<String, String> KEYWORD_VALUES = new LinkedHashMap<>();

    static {
        KEYWORD_VALUES.put("mobile", "7906862513");
        KEYWORD_VALUES.put("phone", "7906862513");
        KEYWORD_VALUES.put("email", "test.user@airpay.example");
        KEYWORD_VALUES.put("pan", "ABCDE1234F");
        KEYWORD_VALUES.put("aadhaar", "234567890123");
        KEYWORD_VALUES.put("aadhar", "234567890123");
        KEYWORD_VALUES.put("pincode", "400001");
        KEYWORD_VALUES.put("pin code", "400001");
        KEYWORD_VALUES.put("postal", "400001");
        KEYWORD_VALUES.put("city", "Mumbai");
        KEYWORD_VALUES.put("state", "Maharashtra");
        KEYWORD_VALUES.put("address", "12 Test Street Andheri East");
        KEYWORD_VALUES.put("salary", "75000");
        KEYWORD_VALUES.put("income", "75000");
        KEYWORD_VALUES.put("amount", "500000");
        KEYWORD_VALUES.put("loan amount", "500000");
        KEYWORD_VALUES.put("emi", "15000");
        KEYWORD_VALUES.put("tenure", "36");
        KEYWORD_VALUES.put("year", "5");
        KEYWORD_VALUES.put("age", "32");
        KEYWORD_VALUES.put("dob", "01/01/1992");
        KEYWORD_VALUES.put("date of birth", "01/01/1992");
        KEYWORD_VALUES.put("name as per pan", "Test User");
        KEYWORD_VALUES.put("name", "Test User");
        KEYWORD_VALUES.put("first name", "Test");
        KEYWORD_VALUES.put("last name", "User");
        KEYWORD_VALUES.put("full name", "Test User");
        KEYWORD_VALUES.put("company", "Airpay Test Pvt Ltd");
        KEYWORD_VALUES.put("employer", "Airpay Test Pvt Ltd");
        KEYWORD_VALUES.put("organization", "Airpay Test Pvt Ltd");
        KEYWORD_VALUES.put("business", "Retail Trading");
        KEYWORD_VALUES.put("gst", "27AABCU9603R1ZM");
        KEYWORD_VALUES.put("ifsc", "HDFC0001234");
        KEYWORD_VALUES.put("account", "50100234567890");
        KEYWORD_VALUES.put("otp", "123456");
    }

    private final AndroidDriver driver;
    private final String defaultText;
    private final String defaultNumber;

    public DummyFormFiller(AndroidDriver driver) {
        this(driver, "Test User", "500000");
    }

    public DummyFormFiller(AndroidDriver driver, String defaultText, String defaultNumber) {
        this.driver = driver;
        this.defaultText = defaultText;
        this.defaultNumber = defaultNumber;
    }

    /**
     * Fill visible EditTexts, then optionally scroll and fill again for Flutter forms
     * that hide fields below the fold.
     *
     * @return total fields filled across all passes
     */
    public int fillVisibleFields() {
        return fillVisibleFields(1);
    }

    /**
     * @param scrollPasses extra scroll-and-fill passes (0 = current viewport only)
     */
    public int fillVisibleFields(int scrollPasses) {
        int filled = fillOnce();
        for (int i = 0; i < scrollPasses; i++) {
            swipeUpFast();
            sleep(250);
            filled += fillOnce();
        }
        return filled;
    }

    private int fillOnce() {
        int filled = 0;
        List<WebElement> fields = driver.findElements(By.xpath(
                "//android.widget.EditText | //*[contains(@class,'EditText')]"));
        for (WebElement field : fields) {
            try {
                if (!field.isDisplayed() || !field.isEnabled()) {
                    continue;
                }
                String hint = firstAttr(field, "hint", "content-desc", "contentDescription", "text");
                String value = resolveValue(hint);
                if (hint == null || hint.isBlank()) {
                    value = KEYWORD_VALUES.get("mobile");
                }
                if (!typeInto(field, value)) {
                    continue;
                }
                filled++;
                LOG.info("Filled field '{}' → '{}'", abbreviate(hint), value);
            } catch (Exception e) {
                LOG.debug("Skip field fill: {}", e.toString());
            }
        }
        // Also try focusable text-entry Views used by Flutter
        if (filled == 0) {
            filled += fillFlutterishFields();
        }
        filled += tickConsentCheckboxes();
        hideKeyboardQuiet();
        return filled;
    }

    /** Tick visible consent / terms checkboxes so Continue can enable. */
    public int tickConsentCheckboxes() {
        int n = 0;
        List<WebElement> boxes = driver.findElements(By.xpath(
                "//android.widget.CheckBox"
                        + " | //*[@resource-id='mfl-terms-consent' or @resource-id='mfl-director-declaration']"
                        + " | //*[@checkable='true' and @clickable='true']"));
        for (WebElement box : boxes) {
            try {
                if (!box.isDisplayed()) {
                    continue;
                }
                String checked = String.valueOf(box.getAttribute("checked"));
                if ("true".equalsIgnoreCase(checked)) {
                    continue;
                }
                box.click();
                n++;
                LOG.info("Ticked consent/checkbox");
            } catch (Exception ignored) {
                // stale
            }
        }
        return n;
    }

    private void hideKeyboardQuiet() {
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // keyboard not open
        }
    }

    private void swipeUpFast() {
        try {
            var size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.78);
            int endY = (int) (size.height * 0.28);
            var finger = new org.openqa.selenium.interactions.PointerInput(
                    org.openqa.selenium.interactions.PointerInput.Kind.TOUCH, "finger");
            var swipe = new org.openqa.selenium.interactions.Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(java.time.Duration.ZERO,
                    org.openqa.selenium.interactions.PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(
                    org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(java.time.Duration.ofMillis(280),
                    org.openqa.selenium.interactions.PointerInput.Origin.viewport(), startX, endY));
            swipe.addAction(finger.createPointerUp(
                    org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(java.util.List.of(swipe));
        } catch (Exception e) {
            LOG.debug("swipeUpFast failed: {}", e.toString());
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private int fillFlutterishFields() {
        int filled = 0;
        List<WebElement> nodes = driver.findElements(By.xpath(
                "//*[@focusable='true' and (@hint or contains(@class,'Edit') "
                        + "or contains(@content-desc,'Enter') or contains(@content-desc,'Amount') "
                        + "or contains(@content-desc,'Salary') or contains(@content-desc,'Income') "
                        + "or contains(@content-desc,'Name') or contains(@content-desc,'Pincode') "
                        + "or contains(@content-desc,'Email') or contains(@content-desc,'PAN'))]"));
        for (WebElement field : nodes) {
            try {
                if (!field.isDisplayed()) {
                    continue;
                }
                String cls = String.valueOf(field.getAttribute("className"));
                if (cls.contains("Button") || cls.contains("Image") || cls.contains("CheckBox")) {
                    continue;
                }
                String hint = firstAttr(field, "hint", "content-desc", "contentDescription", "text");
                if (hint == null || hint.isBlank()) {
                    continue;
                }
                String lower = hint.toLowerCase(Locale.ROOT);
                if (lower.contains("back") || lower.contains("skip") || lower.contains("apply")) {
                    continue;
                }
                String value = resolveValue(hint);
                if (!typeInto(field, value)) {
                    continue;
                }
                filled++;
                LOG.info("Filled flutter-ish '{}' → '{}'", abbreviate(hint), value);
            } catch (Exception ignored) {
                // stale
            }
        }
        return filled;
    }

    private String resolveValue(String hint) {
        if (hint == null || hint.isBlank()) {
            return defaultText;
        }
        String lower = hint.toLowerCase(Locale.ROOT);
        for (Map.Entry<String, String> e : KEYWORD_VALUES.entrySet()) {
            if (lower.contains(e.getKey())) {
                return e.getValue();
            }
        }
        if (lower.matches(".*\\b(no|number|qty|quantity|count|age|year|month|day)\\b.*")
                || lower.contains("₹") || lower.contains("rs") || lower.contains("amount")) {
            return defaultNumber;
        }
        return hint.toLowerCase(Locale.ROOT).matches(".*\\d.*") ? defaultNumber : defaultText;
    }

    private boolean typeInto(WebElement field, String value) {
        try {
            field.click();
        } catch (Exception e) {
            return false;
        }
        try {
            field.clear();
        } catch (Exception ignored) {
            // Flutter / Compose fields often reject clear()
        }
        try {
            field.sendKeys(value);
            return true;
        } catch (Exception ignored) {
            // try mobile:type
        }
        try {
            driver.executeScript("mobile: type", java.util.Map.of("text", value));
            return true;
        } catch (Exception e) {
            LOG.debug("typeInto failed: {}", e.toString());
            return false;
        }
    }

    private static String firstAttr(WebElement el, String... names) {
        for (String name : names) {
            try {
                String v = el.getAttribute(name);
                if (v != null && !v.isBlank() && !"null".equalsIgnoreCase(v)) {
                    return v.split("\n")[0].trim();
                }
            } catch (Exception ignored) {
                // attribute not supported
            }
        }
        return "";
    }

    private static String abbreviate(String s) {
        if (s == null) {
            return "";
        }
        return s.length() > 48 ? s.substring(0, 48) + "…" : s;
    }
}
