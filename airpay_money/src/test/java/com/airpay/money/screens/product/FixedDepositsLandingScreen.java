package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Discover Fixed Deposits landing (Products hub - Fixed Deposit). Live dump 2026-09-21, build 2.0.124.
 */
public class FixedDepositsLandingScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Discover Fixed Deposits']")
    private WebElement heading;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='My Portfolio']")
    private WebElement portfolio;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Women Benefits']")
    private WebElement womenBenefits;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Best Rates']")
    private WebElement bestRates;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Popular FDs']")
    private WebElement popularFds;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Senior Citizen']")
    private WebElement seniorCitizen;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='0-1 year']")
    private WebElement range0to1;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='1-3 year']")
    private WebElement range1to3;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='>3 years']")
    private WebElement rangeOver3;

    @AndroidFindBy(xpath = "//android.widget.Switch")
    private WebElement seniorCitizenSwitch;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Suryoday Small Finance Bank')]")
    private WebElement suryodayPlan;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Mahindra')]")
    private WebElement mahindraPlan;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Bajaj')]")
    private WebElement bajajPlan;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Unity')]")
    private WebElement unityPlan;

    public FixedDepositsLandingScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Discover Fixed Deposits']"),
                MobileWaits.xpath("//android.view.View[@clickable='true' and contains(@content-desc,'Interest Rate %')]")) != null;
    }

    public boolean isSeniorCitizenOn() {
        return firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.Switch[@checked='true']")) != null;
    }

    public void toggleSeniorCitizen() {
        seniorCitizenSwitch.click();
    }

    public boolean selectYearRange(String label) {
        WebElement chip = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[@clickable='true' and @content-desc=" + xpathLiteral(label) + "]"));
        if (chip == null) {
            return false;
        }
        pressElement(chip);
        sleep(900);
        return true;
    }

    public boolean openPlan(String bank) {
        return tapFirst(Duration.ofSeconds(6),
                MobileWaits.xpath("//android.view.View[@clickable='true' and contains(@content-desc,'" + bank + "')]"));
    }

    /** Any provider card for this bank shows the rate. The list is scrolled until that card appears. */
    public boolean cardShows(String bank, String rate) {
        for (int i = 0; i < 8; i++) {
            for (WebElement card : driver.findElements(MobileWaits.xpath("//*[@content-desc]"))) {
                try {
                    String desc = String.valueOf(card.getAttribute("content-desc"));
                    if (desc.contains(bank) && desc.contains("%") && desc.contains(rate)) {
                        return true;
                    }
                } catch (Exception ignored) {
                    // stale
                }
            }
            swipeUp();
            sleep(300);
        }
        return false;
    }

    /**
     * The tenure chips do not expose selected=true. The filter is applied when every visible
     * provider duration falls inside the tapped range.
     */
    public boolean tenureRangeApplied(String label) {
        boolean sawDuration = false;
        for (WebElement card : driver.findElements(MobileWaits.xpath("//*[contains(@content-desc,'Duration')]"))) {
            try {
                String desc = String.valueOf(card.getAttribute("content-desc"));
                Double years = yearsIn(desc);
                if (years == null) {
                    continue;
                }
                sawDuration = true;
                if (!inRange(label, years)) {
                    return false;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return sawDuration;
    }

    private String cardsBeforeWomen = "";

    /** Women Benefits is a tile. It does not flip a selected attribute; the provider list changes. */
    public void pressWomenBenefits() {
        cardsBeforeWomen = visibleProviderCards();
        WebElement tile = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[@clickable='true' and @content-desc='Women Benefits']"));
        if (tile != null) {
            pressElement(tile);
            sleep(900);
        }
    }

    public boolean womenBenefitsApplied() {
        String after = visibleProviderCards();
        return after.contains("Interest Rate") && after.contains("%") && !after.equals(cardsBeforeWomen);
    }

    private String visibleProviderCards() {
        StringBuilder cards = new StringBuilder();
        for (WebElement card : driver.findElements(MobileWaits.xpath("//*[contains(@content-desc,'Interest Rate')]"))) {
            try {
                cards.append(card.getAttribute("content-desc")).append('\n');
            } catch (Exception ignored) {
                // stale
            }
        }
        return cards.toString();
    }

    /** "5 years" is 5. "1 year 4 months" is more than one year. */
    private static Double yearsIn(String desc) {
        Matcher years = Pattern.compile("(\\d+)\\s*(?:years?|yr|Y)\\b", Pattern.CASE_INSENSITIVE).matcher(desc);
        Matcher months = Pattern.compile("(\\d+)\\s*months?\\b", Pattern.CASE_INSENSITIVE).matcher(desc);
        if (!years.find() && !months.find()) {
            return null;
        }
        double total = 0;
        if (years.reset().find()) {
            total += Integer.parseInt(years.group(1));
        }
        if (months.reset().find()) {
            total += Integer.parseInt(months.group(1)) / 12.0;
        }
        return total;
    }

    private static boolean inRange(String label, double years) {
        if ("0-1 year".equals(label)) {
            return years <= 1.0;
        }
        if ("1-3 year".equals(label)) {
            return years >= 1.0 && years <= 3.0;
        }
        return years > 3.0;
    }

    /** At least two provider cards show an interest rate. Exact percentages are not locked. */
    public boolean multipleProviderRatesVisible() {
        StringBuilder seen = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            seen.append(pageSource());
            swipeUp();
            sleep(400);
        }
        String src = seen.toString();
        if (!src.contains("%")) {
            return false;
        }
        int banks = 0;
        for (String bank : new String[] {"Suryoday", "Mahindra", "Bajaj", "Unity", "Shriram"}) {
            if (src.contains(bank)) {
                banks++;
            }
        }
        return banks >= 2;
    }

    /**
     * Scrolls the provider list until the bank name and the sheet rate are both on screen.
     */
    public boolean showsBankRate(String bank, String rate) {
        for (int i = 0; i < 7; i++) {
            String src = pageSource();
            if (src.contains(bank) && src.contains(rate)) {
                return true;
            }
            swipeUp();
            sleep(400);
        }
        return pageSource().contains(bank) && pageSource().contains(rate);
    }

    /** Provider details page: tenure picker and Book FD. Booking itself is not used. */
    public boolean providerDetailsVisible() {
        long end = System.currentTimeMillis() + 8_000;
        while (System.currentTimeMillis() < end) {
            if (sourceContains("Select goal tenure", "Book FD")) {
                return true;
            }
            sleep(400);
        }
        return false;
    }
}
