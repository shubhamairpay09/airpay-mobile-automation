package com.airpay.capital.lms.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/** Admin staging sign-in + Mobile OTP modal. */
public class LmsSignInPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By EMAIL = By.cssSelector("input[placeholder*='registered email'], input[type='email']");
    private static final By PASSWORD = By.cssSelector("input[placeholder='Password'], input[type='password']");
    private static final By SIGN_IN = By.xpath("//button[normalize-space()='Sign In' or contains(.,'Sign In')]");
    private static final By OTP_HEADING = By.xpath("//*[contains(text(),'Mobile OTP')]");
    private static final By OTP_BOXES = By.cssSelector("input[type='tel'], input[maxlength='1'], input[inputmode='numeric']");
    private static final By OTP_SUBMIT = By.xpath("//button[normalize-space()='Submit' or contains(.,'Submit') or contains(.,'Verifying')]");

    public LmsSignInPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void open(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL));
    }

    public void signIn(String email, String password) {
        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(EMAIL));
        emailField.clear();
        emailField.sendKeys(email);
        WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD));
        passwordField.clear();
        passwordField.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(SIGN_IN)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(OTP_HEADING));
    }

    public void enterOtpAndSubmit(String otp) {
        String digits = otp == null ? "" : otp.replaceAll("\\D", "");
        if (digits.length() < 4) {
            throw new IllegalArgumentException("LMS OTP must be at least 4 digits");
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(OTP_HEADING));
        List<WebElement> boxes = driver.findElements(OTP_BOXES);
        if (boxes.size() >= 4) {
            for (int i = 0; i < 4; i++) {
                boxes.get(i).clear();
                boxes.get(i).sendKeys(String.valueOf(digits.charAt(i)));
            }
        } else {
            WebElement single = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("input[placeholder*='OTP'], input[name*='otp']")));
            single.clear();
            single.sendKeys(digits.substring(0, 4));
        }
        wait.until(ExpectedConditions.elementToBeClickable(OTP_SUBMIT)).click();
    }

    public boolean isOtpModalVisible() {
        return !driver.findElements(OTP_HEADING).isEmpty();
    }
}
