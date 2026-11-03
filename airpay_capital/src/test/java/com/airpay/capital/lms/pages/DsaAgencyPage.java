package com.airpay.capital.lms.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/** DSA Agency Management list and registration entry. */
public class DsaAgencyPage {

    private static final Logger LOG = LogManager.getLogger(DsaAgencyPage.class);
    private static final String PATH = "/dsa-agency-management";

    private final WebDriver driver;
    private final WebDriverWait wait;

    private static final By MENU_DSA = By.xpath(
            "//a[contains(@href,'dsa-agency') or normalize-space()='DSA Agency' or normalize-space()='DSA Agency Management']");
    private static final By PAGE_TITLE = By.xpath(
            "//*[normalize-space()='DSA Agency Management' or contains(normalize-space(),'DSA Agency Management')]");
    private static final By LIST_HEADING = By.xpath("//*[contains(normalize-space(),'List of registered agencies')]");
    private static final By REGISTER_BTN = By.xpath(
            "//button[contains(normalize-space(.),'Register New Agency')] | //a[contains(normalize-space(.),'Register New Agency')]");
    private static final By PAN_FIELD = By.xpath(
            "//label[contains(translate(normalize-space(.),'PAN','pan'),'pan')]"
                    + " | //input[contains(translate(@placeholder,'PAN','pan'),'pan') or contains(translate(@name,'PAN','pan'),'pan') or contains(translate(@id,'PAN','pan'),'pan')]");

    public DsaAgencyPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(25));
    }

    public void openFromMenuOrDirect() {
        if (driver.getCurrentUrl().contains("dsa-agency")) {
            waitUntilLoaded();
            return;
        }
        List<WebElement> links = driver.findElements(MENU_DSA).stream().filter(WebElement::isDisplayed).toList();
        if (!links.isEmpty()) {
            click(links.get(0));
            LOG.info("Opened DSA Agency from menu");
        } else {
            String target = origin() + PATH;
            driver.get(target);
            LOG.info("Menu link not visible — opened {}", target);
        }
        waitUntilLoaded();
    }

    public void waitUntilLoaded() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.urlContains("dsa-agency"),
                ExpectedConditions.visibilityOfElementLocated(PAGE_TITLE)));
        wait.until(ExpectedConditions.visibilityOfElementLocated(LIST_HEADING));
    }

    public boolean titleVisible() {
        return driver.findElements(PAGE_TITLE).stream().anyMatch(WebElement::isDisplayed)
                || driver.getPageSource().contains("DSA Agency Management");
    }

    public boolean listHeadingVisible() {
        return driver.findElements(LIST_HEADING).stream().anyMatch(WebElement::isDisplayed)
                || driver.getPageSource().contains("List of registered agencies");
    }

    public boolean urlIsAgencyList() {
        return driver.getCurrentUrl().contains("dsa-agency");
    }

    public boolean registerNewAgencyVisible() {
        return driver.findElements(REGISTER_BTN).stream().anyMatch(WebElement::isDisplayed);
    }

    public boolean registerNewAgencyEnabled() {
        return driver.findElements(REGISTER_BTN).stream()
                .filter(WebElement::isDisplayed)
                .anyMatch(WebElement::isEnabled);
    }

    public void clickRegisterNewAgency() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(REGISTER_BTN));
        click(button);
    }

    public boolean registrationFormOpen() {
        String url = driver.getCurrentUrl().toLowerCase();
        String src = driver.getPageSource();
        return url.contains("register")
                || src.contains("PAN Number")
                || src.contains("PAN number")
                || !driver.findElements(PAN_FIELD).isEmpty();
    }

    public boolean panNumberFieldVisible() {
        if (driver.findElements(PAN_FIELD).stream().anyMatch(WebElement::isDisplayed)) {
            return true;
        }
        String src = driver.getPageSource();
        return src.contains("PAN Number") || src.contains("PAN number");
    }

    public List<String> missingColumns(String... columns) {
        String src = driver.getPageSource();
        List<String> missing = new ArrayList<>();
        for (String column : columns) {
            if (!src.contains(column)) {
                missing.add(column);
            }
        }
        return missing;
    }

    public int agencyRowCount() {
        List<WebElement> rows = driver.findElements(By.cssSelector("table tbody tr, [role='row']"));
        int visible = (int) rows.stream().filter(WebElement::isDisplayed).count();
        if (visible > 0) {
            return visible;
        }
        return driver.findElements(By.xpath(
                "//a[contains(.,'View') or contains(@href,'agency')] | //button[contains(.,'View')]")).size();
    }

    public String currentUrl() {
        return driver.getCurrentUrl();
    }

    private String origin() {
        String url = driver.getCurrentUrl();
        int slash = url.indexOf('/', url.indexOf("://") + 3);
        return slash < 0 ? url : url.substring(0, slash);
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
