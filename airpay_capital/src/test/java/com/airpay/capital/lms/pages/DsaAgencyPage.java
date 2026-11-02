package com.airpay.capital.lms.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/** DSA Agency Management list page. */
public class DsaAgencyPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By MENU_DSA = By.xpath("//a[normalize-space()='DSA Agency' or contains(@href,'dsa-agency')]");
    private static final By PAGE_TITLE = By.xpath("//*[contains(normalize-space(),'DSA Agency Management')]");
    private static final By LIST_HEADING = By.xpath("//*[contains(normalize-space(),'List of registered agencies')]");
    private static final By REGISTER_BTN = By.xpath("//button[contains(.,'Register New Agency')] | //a[contains(.,'Register New Agency')]");
    private static final By TABLE = By.cssSelector("table, [role='table'], .MuiDataGrid-root");
    private static final By TOTAL_RECORDS = By.xpath("//*[contains(normalize-space(),'Total records found')]");

    public DsaAgencyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public void openFromMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(MENU_DSA)).click();
        waitUntilLoaded();
    }

    public void waitUntilLoaded() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("dsa-agency"),
                ExpectedConditions.visibilityOfElementLocated(PAGE_TITLE)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(LIST_HEADING));
        wait.until(ExpectedConditions.visibilityOfElementLocated(REGISTER_BTN));
    }

    public boolean isDisplayed() {
        return driver.getCurrentUrl().contains("dsa-agency")
                || !driver.findElements(PAGE_TITLE).isEmpty();
    }

    public boolean registerNewAgencyVisible() {
        return !driver.findElements(REGISTER_BTN).isEmpty()
                && driver.findElement(REGISTER_BTN).isDisplayed();
    }

    public void clickRegisterNewAgency() {
        wait.until(ExpectedConditions.elementToBeClickable(REGISTER_BTN)).click();
    }

    public boolean gridShowsRequiredColumns() {
        String src = driver.getPageSource();
        return containsAll(src,
                "Sr. No", "DSA Code", "Agency Name",
                "Loan Type", "Onboarded On Date", "Status");
    }

    public boolean hasAgencyRows() {
        List<WebElement> viewLinks = driver.findElements(By.xpath("//a[contains(.,'View agency') or contains(@href,'agency')]"));
        if (!viewLinks.isEmpty()) {
            return true;
        }
        String src = driver.getPageSource();
        return src.contains("Total records found") && !src.contains("Total records found: 0");
    }

    public String totalRecordsText() {
        List<WebElement> els = driver.findElements(TOTAL_RECORDS);
        return els.isEmpty() ? "" : els.get(0).getText();
    }

    private static boolean containsAll(String haystack, String... needles) {
        for (String n : needles) {
            if (!haystack.contains(n)) {
                return false;
            }
        }
        return true;
    }
}
