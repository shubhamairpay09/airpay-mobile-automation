package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.List;

/**
 * Mutual Funds Dashboard (scrollable). Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsLandingScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Mutual Funds']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Help']")
    private WebElement help;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Start eKYC']")
    private WebElement startEkyc;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Create Goal']")
    private WebElement createGoal;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Add Goal']")
    private WebElement addGoal;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='View all']")
    private WebElement viewAll;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='View all Mutual Funds']")
    private WebElement viewAllMutualFunds;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='41.0']")
    private WebElement riskScore;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='64.0']")
    private WebElement riskScoreBalanced;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Your Risk Profile: ']")
    private WebElement riskProfilePrefix;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Moderately Conservative']")
    private WebElement riskProfileType;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Balanced']")
    private WebElement riskProfileTypeBalanced;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Your Risk Profile: ']/following-sibling::android.view.View[@clickable='true' and @content-desc='']")
    private WebElement riskEdit;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Reassess']")
    private WebElement reassess;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Goal Calculator')]")
    private WebElement goalCalculator;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'SIP Calculator')]")
    private WebElement sipCalculator;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Dashboard']")
    private WebElement tabDashboard;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Portfolio']")
    private WebElement tabPortfolio;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Transactions']")
    private WebElement tabTransactions;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Profile']")
    private WebElement tabProfile;

    public MutualFundsLandingScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Mutual Funds']"),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Dashboard']")) != null;
    }

    public boolean openSection(String label) {
        for (int i = 0; i < 6; i++) {
            if (tapLabel(label)) {
                sleep(120);
                return true;
            }
            swipeUp();
        }
        return false;
    }

    /**
     * Recommendation and View all are one button. A center tap stays on the dashboard.
     * View all is the right side of that row.
     */
    public boolean openRecommendationList() {
        for (int i = 0; i < 5; i++) {
            WebElement row = firstPresent(Duration.ofMillis(800),
                    MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc,'Recommendation') and contains(@content-desc,'View all')]"));
            if (row != null) {
                var rect = row.getRect();
                tapAt(rect.getX() + rect.getWidth() - 70, rect.getY() + rect.getHeight() / 2);
                sleep(200);
                if (pageSource().contains("Funds basis on your risk")) {
                    return true;
                }
            }
            swipeUpSmall();
        }
        return false;
    }

    public void openTab(String label) {
        tapFirst(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.Button[@content-desc=" + xpathLiteral(label) + "]"),
                accessibility(label));
        sleep(120);
    }

    public boolean goalListed(String name, String amount) {
        String grouped = groupedAmount(amount);
        for (int i = 0; i < 3; i++) {
            swipeDownSmall();
        }
        for (int i = 0; i < 8; i++) {
            for (int across = 0; across < 4; across++) {
                String src = pageSource();
                boolean named = src.toLowerCase().contains(name.toLowerCase());
                boolean priced = src.contains(amount) || src.contains(grouped);
                if (named && priced && src.contains("Goal")) {
                    return true;
                }
                swipeLeft();
            }
            swipeUpSmall();
        }
        return false;
    }

    public boolean openGoal(String name) {
        for (int i = 0; i < 6; i++) {
            if (tapArrowBeside(name)) {
                sleep(150);
                return true;
            }
            swipeUpSmall();
        }
        return tapLabel(name);
    }

    private boolean tapArrowBeside(String name) {
        String needle = name.toLowerCase();
        for (WebElement label : driver.findElements(MobileWaits.xpath("//*[@content-desc]"))) {
            String desc;
            try {
                desc = String.valueOf(label.getAttribute("content-desc")).toLowerCase();
                if (!label.isDisplayed() || !desc.contains(needle)) {
                    continue;
                }
            } catch (Exception ignored) {
                continue;
            }
            var rect = label.getRect();
            int labelRight = rect.getX() + rect.getWidth();
            int labelMidY = rect.getY() + rect.getHeight() / 2;
            WebElement arrow = null;
            int nearest = Integer.MAX_VALUE;
            for (WebElement candidate : driver.findElements(MobileWaits.xpath("//*[@clickable='true']"))) {
                try {
                    if (!candidate.isDisplayed() || candidate.equals(label)) {
                        continue;
                    }
                    var box = candidate.getRect();
                    int midX = box.getX() + box.getWidth() / 2;
                    int midY = box.getY() + box.getHeight() / 2;
                    if (midX < labelRight - 8 || Math.abs(midY - labelMidY) > 120) {
                        continue;
                    }
                    int gap = midX - labelRight;
                    if (gap < nearest && box.getWidth() < 400) {
                        nearest = gap;
                        arrow = candidate;
                    }
                } catch (Exception ignored) {
                    // stale
                }
            }
            if (arrow != null) {
                arrow.click();
                return true;
            }
            tapAt(rect.getX() + Math.min(rect.getWidth() / 2, 200), rect.getY() + rect.getHeight() / 2);
            return true;
        }
        return false;
    }

    private static String groupedAmount(String amount) {
        String digits = amount.replaceAll("\\D", "");
        if (digits.length() <= 3) {
            return digits;
        }
        StringBuilder grouped = new StringBuilder(digits);
        for (int i = grouped.length() - 3; i > 0; i -= 2) {
            grouped.insert(i, ",");
            if (i <= grouped.length() - 6) {
                break;
            }
        }
        return grouped.toString();
    }

    private void swipeDownSmall() {
        var size = driver.manage().window().getSize();
        drag(size.width / 2, (int) (size.height * 0.35), size.width / 2, (int) (size.height * 0.72));
        sleep(350);
    }

    /**
     * The My Goals row is one button, "My Goals" plus "Add Goal".
     * A center click stays on the dashboard. The Add Goal label is on the right.
     */
    public boolean openAddGoal() {
        for (int i = 0; i < 8; i++) {
            WebElement row = firstPresent(Duration.ofMillis(800),
                    MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Add Goal')]"));
            if (row != null) {
                var rect = row.getRect();
                tapAt(rect.getX() + rect.getWidth() - 90, rect.getY() + rect.getHeight() / 2);
                sleep(200);
                String src = pageSource();
                if (src.contains("Create Goal") && src.contains("Bike")) {
                    return true;
                }
            }
            swipeUpSmall();
        }
        return false;
    }

    /**
     * Curated tiles sit under Curated Funds. Index tiles sit under Index Funds.
     * The row can extend off the right edge, so the section is held and swiped sideways.
     */
    public boolean openTileInSection(String section, String... labels) {
        for (int i = 0; i < 12; i++) {
            String src = squash(pageSource());
            if (src.contains(squash(section))) {
                for (String label : labels) {
                    if (label == null || label.isBlank()) {
                        continue;
                    }
                    if (tapQuick(label)) {
                        sleep(150);
                        return true;
                    }
                }
                if (labelPresent(src, labels)) {
                    swipeUpSmall();
                } else {
                    swipeLeft();
                }
            } else {
                swipeUpSmall();
            }
        }
        return false;
    }

    /** Edit control sits to the right of the Your risk profile text. The saved profile is not changed here. */
    public boolean openRiskEdit() {
        for (int i = 0; i < 10; i++) {
            if (pageSource().toLowerCase().contains("risk profile") && tapEditBesideRiskProfile()) {
                sleep(120);
                return true;
            }
            swipeUpSmall();
        }
        return false;
    }

    private boolean tapEditBesideRiskProfile() {
        for (WebElement label : driver.findElements(MobileWaits.xpath("//*[@content-desc]"))) {
            String desc;
            try {
                desc = String.valueOf(label.getAttribute("content-desc")).toLowerCase();
                if (!label.isDisplayed() || !desc.contains("risk profile")) {
                    continue;
                }
            } catch (Exception ignored) {
                continue;
            }
            var rect = label.getRect();
            int labelRight = rect.getX() + rect.getWidth();
            int labelMidY = rect.getY() + rect.getHeight() / 2;
            WebElement edit = null;
            int nearest = Integer.MAX_VALUE;
            for (WebElement candidate : driver.findElements(MobileWaits.xpath("//*[@clickable='true']"))) {
                try {
                    if (!candidate.isDisplayed()) {
                        continue;
                    }
                    var box = candidate.getRect();
                    int midX = box.getX() + box.getWidth() / 2;
                    int midY = box.getY() + box.getHeight() / 2;
                    if (midX <= labelRight - 8 || Math.abs(midY - labelMidY) > 90) {
                        continue;
                    }
                    int gap = midX - labelRight;
                    if (gap < nearest) {
                        nearest = gap;
                        edit = candidate;
                    }
                } catch (Exception ignored) {
                    // stale
                }
            }
            if (edit != null) {
                edit.click();
                return true;
            }
        }
        return false;
    }

    /** The portfolio eye icon has no content description. It is the small control beside the values. */
    public boolean toggleEye() {
        WebElement eye = smallestIcon(
                "//*[@clickable='true' and (self::android.widget.ImageView or self::android.widget.ImageButton or self::android.view.View)]");
        if (eye == null) {
            eye = smallestIcon("//android.widget.ImageView");
        }
        if (eye == null) {
            return false;
        }
        eye.click();
        sleep(120);
        return true;
    }

    private WebElement smallestIcon(String xpath) {
        WebElement eye = null;
        int bestArea = Integer.MAX_VALUE;
        for (WebElement icon : driver.findElements(MobileWaits.xpath(xpath))) {
            try {
                if (!icon.isDisplayed()) {
                    continue;
                }
                int y = icon.getLocation().getY();
                int x = icon.getLocation().getX();
                int height = icon.getSize().getHeight();
                int width = icon.getSize().getWidth();
                int area = width * height;
                if (y > 180 && y < 1100 && x > 400 && height > 16 && width > 16
                        && height < 160 && width < 160 && area < bestArea) {
                    eye = icon;
                    bestArea = area;
                }
            } catch (Exception ignored) {
                // stale node while the dashboard is scrolling
            }
        }
        return eye;
    }

    private boolean tapQuick(String label) {
        if (tapFirst(Duration.ofMillis(700),
                accessibility(label),
                MobileWaits.xpath("//*[@clickable='true' and contains(@content-desc," + xpathLiteral(label) + ")]"),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(label) + ")]"))) {
            return true;
        }
        return tapNormalized(label);
    }

    /** Live tiles put a line break in the label, such as "Better\nthan FD". A clipped tile at the bottom is skipped. */
    private boolean tapNormalized(String label) {
        String needle = squash(label);
        String unsuffixed = needle.endsWith("s") ? needle.substring(0, needle.length() - 1) : needle;
        for (WebElement el : driver.findElements(MobileWaits.xpath("//*[@clickable='true' and @content-desc]"))) {
            try {
                if (!el.isDisplayed() || !fullyVisible(el)) {
                    continue;
                }
                String desc = squash(String.valueOf(el.getAttribute("content-desc")));
                if (desc.equals(needle) || desc.equals(unsuffixed) || desc.contains(needle)) {
                    var rect = el.getRect();
                    int x = rect.getX() + rect.getWidth() / 2;
                    int y = rect.getY() + (int) (rect.getHeight() * 0.78);
                    shellPress(x, y);
                    return true;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return false;
    }

    private boolean labelPresent(String squashedSource, String... labels) {
        for (String label : labels) {
            if (label == null || label.isBlank()) {
                continue;
            }
            String needle = squash(label);
            String unsuffixed = needle.endsWith("s") ? needle.substring(0, needle.length() - 1) : needle;
            if (squashedSource.contains(needle) || squashedSource.contains(unsuffixed)) {
                return true;
            }
        }
        return false;
    }

    private boolean fullyVisible(WebElement el) {
        var rect = el.getRect();
        int screenBottom = driver.manage().window().getSize().getHeight();
        int bottom = rect.getY() + rect.getHeight();
        return rect.getHeight() > 160 && rect.getY() > 160 && bottom < screenBottom - 180;
    }

    private static String squash(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&#10;", " ").replace("&#13;", " ").replace('\n', ' ')
                .replaceAll("\\s+", " ").trim().toLowerCase();
    }

    private void swipeUpSmall() {
        var size = driver.manage().window().getSize();
        drag(size.width / 2, (int) (size.height * 0.72), size.width / 2, (int) (size.height * 0.46));
        sleep(80);
    }

    private void swipeLeft() {
        var size = driver.manage().window().getSize();
        int y = (int) (size.height * 0.55);
        drag((int) (size.width * 0.86), y, (int) (size.width * 0.16), y);
        sleep(80);
    }

    private void drag(int startX, int startY, int endX, int endY) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(280), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
    }
}
