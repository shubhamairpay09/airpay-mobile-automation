package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds — Fund Details. Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsFundDetailsScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Fund Details']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='HDFC Money Market Fund']")
    private WebElement fundName;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='One Time']")
    private WebElement oneTime;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Monthly']")
    private WebElement monthly;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Returns']")
    private WebElement returns;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Return Calculator-SIP/One time']")
    private WebElement returnCalculator;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Peer Comparison']")
    private WebElement peerComparison;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Holdings')]")
    private WebElement holdings;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Other plans of same fund']")
    private WebElement otherPlans;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Investment Objective']")
    private WebElement investmentObjective;

    public MutualFundsFundDetailsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Fund Details") || sourceContains("One Time", "Monthly");
    }

    public boolean waitDisplayed() {
        long deadline = System.currentTimeMillis() + 10_000;
        while (System.currentTimeMillis() < deadline) {
            if (isDisplayed()) {
                return true;
            }
            sleep(400);
        }
        return isDisplayed();
    }

    /**
     * Title "Fund Details" appears on the skeleton. Wait until the chart price / fund name is real.
     */
    public boolean waitUntilLoaded() {
        long deadline = System.currentTimeMillis() + 25_000;
        boolean refreshed = false;
        while (System.currentTimeMillis() < deadline) {
            if (contentLoaded()) {
                return true;
            }
            if (!refreshed && System.currentTimeMillis() + 12_000 < deadline) {
                swipeDown();
                refreshed = true;
            }
            sleep(600);
        }
        return contentLoaded();
    }

    /**
     * NAV is the rupee price on the fund chart (for example ₹15.07), not a "NAV" label.
     */
    public boolean chartNavPriceVisible() {
        if (!waitUntilLoaded()) {
            return false;
        }
        if (sourceHasChartNav()) {
            return true;
        }
        swipeUp();
        sleep(300);
        return sourceHasChartNav();
    }

    private boolean contentLoaded() {
        if (sourceHasChartNav()) {
            return true;
        }
        String src = pageSource();
        String lower = src.toLowerCase();
        return lower.contains("navi nifty")
                || lower.contains("index fund")
                || lower.contains("direct growth")
                || lower.contains("direct plan")
                || (src.contains("Returns") && src.contains("%"))
                || src.contains("One Time")
                || src.contains("Monthly");
    }

    /** Scroll to Returns and open that section. Invest / One Time / Monthly are not tapped. */
    public boolean openReturns() {
        waitUntilLoaded();
        for (int i = 0; i < 8; i++) {
            if (tapLabel("Returns") || tapLabel("Return")) {
                sleep(900);
                if (returnDetailsVisible()) {
                    return true;
                }
            }
            swipeUp();
            sleep(250);
        }
        return returnDetailsVisible();
    }

    public boolean returnDetailsVisible() {
        String src = pageSource();
        String lower = src.toLowerCase();
        return src.contains("1Y") || src.contains("3Y") || src.contains("5Y")
                || src.contains("1 Year") || src.contains("3 Year") || src.contains("5 Year")
                || src.contains("CAGR") || src.contains("Absolute")
                || src.contains("Return Calculator") || src.contains("Since Inception")
                || (lower.contains("return") && src.contains("%"));
    }

    private boolean sourceHasChartNav() {
        String src = pageSource()
                .replace("&#8377;", "₹")
                .replace("&#x20b9;", "₹")
                .replace("&amp;", "&");
        if (java.util.regex.Pattern.compile("₹\\s*\\d{1,4}(?:,\\d{2,3})*(?:\\.\\d{1,4})?")
                .matcher(src).find()) {
            return true;
        }
        if (java.util.regex.Pattern.compile("Rs\\.?\\s*\\d{1,4}\\.\\d{2,4}")
                .matcher(src).find()) {
            return true;
        }
        java.util.regex.Matcher nodes = java.util.regex.Pattern
                .compile("content-desc=\"([^\"]{1,120})\"")
                .matcher(src);
        while (nodes.find()) {
            String desc = nodes.group(1).replace("&#10;", " ");
            if (desc.contains("%")) {
                continue;
            }
            if (java.util.regex.Pattern.compile("\\b\\d{1,3}\\.\\d{2,4}\\b").matcher(desc).find()) {
                return true;
            }
        }
        return false;
    }
}
