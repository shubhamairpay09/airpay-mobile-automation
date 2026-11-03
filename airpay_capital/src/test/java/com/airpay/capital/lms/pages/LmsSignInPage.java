package com.airpay.capital.lms.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/** Admin staging sign-in + Mobile OTP dialog, driven in Chrome on the phone. */
public class LmsSignInPage {

    private static final Logger LOG = LogManager.getLogger(LmsSignInPage.class);

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By EMAIL = By.cssSelector(
            "input[type='email'], input[placeholder*='email' i], input[name*='email' i]");
    private static final By PASSWORD = By.cssSelector("input[type='password']");
    private static final By SIGN_IN = By.xpath(
            "//button[normalize-space()='Sign In'] | //button[contains(normalize-space(.),'Sign In')]");
    /** Leaf text only — a parent that merely contains the word OTP must not match. */
    private static final By OTP_HEADING = By.xpath(
            "//*[contains(normalize-space(.),'Mobile OTP') and not(.//*[contains(normalize-space(.),'Mobile OTP')])]");
    private static final By OTP_DIGIT = By.cssSelector("input[maxlength='1']");
    private static final By OTP_SUBMIT = By.xpath(
            "//button[normalize-space()='Submit' or normalize-space()='Verify']");

    public LmsSignInPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void open(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL));
        LOG.info("LMS sign-in loaded | url={}", driver.getCurrentUrl());
    }

    public void enterEmail(String email) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL));
        setReactValue(field, email);
        LOG.info("Entered LMS email");
    }

    public void enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD));
        setReactValue(field, password);
        LOG.info("Entered LMS password");
    }

    public void tapSignIn() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(SIGN_IN));
        String emailValue = driver.findElement(EMAIL).getAttribute("value");
        String passwordValue = driver.findElement(PASSWORD).getAttribute("value");
        LOG.info("Before Sign In | emailPresent={} passwordLen={} buttonDisabled={}",
                emailValue != null && !emailValue.isBlank(),
                passwordValue == null ? 0 : passwordValue.length(),
                button.getAttribute("disabled"));
        for (int attempt = 1; attempt <= 2; attempt++) {
            click(wait.until(ExpectedConditions.elementToBeClickable(SIGN_IN)));
            LOG.info("Tapped Sign In attempt {} | body={}", attempt, bodySnippet());
            if (signingOrOtpStarted(Duration.ofSeconds(8))) {
                return;
            }
        }
    }

    private boolean signingOrOtpStarted(Duration timeout) {
        long end = System.currentTimeMillis() + timeout.toMillis();
        while (System.currentTimeMillis() < end) {
            String body = bodySnippet().toLowerCase();
            if (body.contains("signing") || body.contains("mobile otp") || body.contains("4-digit")) {
                return true;
            }
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return false;
    }

    public boolean waitForOtpDialog(Duration timeout) {
        try {
            new WebDriverWait(driver, timeout).until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(OTP_HEADING),
                    ExpectedConditions.numberOfElementsToBeMoreThan(OTP_DIGIT, 3)));
            return true;
        } catch (Exception e) {
            LOG.warn("OTP dialog not shown | url={} body={}", driver.getCurrentUrl(), bodySnippet());
            return false;
        }
    }

    public String visibleText() {
        return bodySnippet();
    }

    private String bodySnippet() {
        try {
            Object text = ((JavascriptExecutor) driver).executeScript(
                    "return (document.body && document.body.innerText) ? document.body.innerText.slice(0, 800) : ''");
            return text == null ? "" : text.toString().replaceAll("\\s+", " ").trim();
        } catch (Exception e) {
            return "(unavailable)";
        }
    }

    public void enterOtp(String otp) {
        String digits = otp == null ? "" : otp.replaceAll("\\D", "");
        if (digits.length() < 4) {
            throw new IllegalArgumentException("LMS OTP must be 4 digits");
        }
        digits = digits.substring(0, 4);
        List<WebElement> boxes = visibleDigits();
        if (boxes.size() < 4) {
            throw new IllegalStateException("Expected 4 Mobile OTP boxes, found " + boxes.size()
                    + " | url=" + driver.getCurrentUrl());
        }
        WebElement first = boxes.get(0);
        click(first);
        for (int i = 0; i < 4; i++) {
            WebElement active = driver.switchTo().activeElement();
            active.sendKeys(String.valueOf(digits.charAt(i)));
        }
        String entered = visibleDigits().stream()
                .limit(4)
                .map(el -> {
                    String v = el.getAttribute("value");
                    return v == null || v.isEmpty() ? "_" : v;
                })
                .reduce("", String::concat);
        LOG.info("Entered OTP boxes | values={}", entered);
        if (entered.replace("_", "").length() < 4) {
            // Fallback: write each box directly when auto-advance did not fill them.
            boxes = visibleDigits();
            for (int i = 0; i < 4 && i < boxes.size(); i++) {
                WebElement box = boxes.get(i);
                click(box);
                ((JavascriptExecutor) driver).executeScript(
                        "const el = arguments[0]; const value = arguments[1];"
                                + "const proto = Object.getPrototypeOf(el);"
                                + "const desc = Object.getOwnPropertyDescriptor(proto, 'value');"
                                + "if (desc && desc.set) { desc.set.call(el, value); } else { el.value = value; }"
                                + "el.dispatchEvent(new InputEvent('input', {bubbles:true, data:value, inputType:'insertText'}));"
                                + "el.dispatchEvent(new Event('change', {bubbles:true}));",
                        box, String.valueOf(digits.charAt(i)));
            }
            entered = visibleDigits().stream().limit(4)
                    .map(el -> {
                        String v = el.getAttribute("value");
                        return v == null || v.isEmpty() ? "_" : v;
                    })
                    .reduce("", String::concat);
            LOG.info("OTP boxes after direct set | values={}", entered);
        }
    }

    public void submitOtp() {
        List<WebElement> buttons = driver.findElements(OTP_SUBMIT).stream()
                .filter(WebElement::isDisplayed)
                .toList();
        if (buttons.isEmpty()) {
            List<WebElement> boxes = visibleDigits();
            if (!boxes.isEmpty()) {
                boxes.get(boxes.size() - 1).sendKeys(Keys.ENTER);
                LOG.info("Submitted LMS OTP with Enter");
                return;
            }
            throw new IllegalStateException("OTP Submit button not found | url=" + driver.getCurrentUrl());
        }
        click(buttons.get(0));
        LOG.info("Tapped OTP Submit | body={}", bodySnippet());
    }

    public boolean otpDialogVisible() {
        return !driver.findElements(OTP_HEADING).isEmpty() || visibleDigits().size() >= 4;
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    private List<WebElement> visibleDigits() {
        return driver.findElements(OTP_DIGIT).stream().filter(WebElement::isDisplayed).toList();
    }

    private void setReactValue(WebElement field, String value) {
        click(field);
        ((JavascriptExecutor) driver).executeScript(
                "const el = arguments[0]; const value = arguments[1];"
                        + "const proto = Object.getPrototypeOf(el);"
                        + "const desc = Object.getOwnPropertyDescriptor(proto, 'value');"
                        + "if (desc && desc.set) { desc.set.call(el, value); } else { el.value = value; }"
                        + "el.dispatchEvent(new Event('input', {bubbles:true}));"
                        + "el.dispatchEvent(new Event('change', {bubbles:true}));",
                field, value);
        String actual = field.getAttribute("value");
        if (actual == null || !actual.equals(value)) {
            field.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            field.sendKeys(value);
        }
    }

    private void click(WebElement el) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", el);
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }
}
