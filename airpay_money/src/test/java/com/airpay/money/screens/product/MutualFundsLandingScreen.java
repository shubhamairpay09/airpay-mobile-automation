package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
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
        String src = pageSource();
        // Products/Home hub also has a Mutual Funds tile — that is not this dashboard.
        if (src.contains("Investments") && (src.contains("Digital Gold") || src.contains("NPS"))) {
            return false;
        }
        return src.contains("Mutual Funds")
                && (src.contains("Create Goal") || src.contains("Add Goal")
                || src.contains("My Goals") || src.contains("View all Mutual Funds")
                || src.contains("Your Risk Profile") || src.contains("Start eKYC"));
    }

    /** Scroll MF dashboard to top then find {@code label} without requiring a tap. */
    public boolean scrollSectionIntoView(String label) {
        scrollToTop();
        for (int i = 0; i < 10; i++) {
            if (pageSource().contains(label)) {
                return true;
            }
            swipeUpSmall();
        }
        return pageSource().contains(label);
    }

    public boolean openSection(String label) {
        scrollToTop();
        for (int i = 0; i < 10; i++) {
            if (tapQuick(label) || tapLabel(label)) {
                sleep(200);
                return true;
            }
            swipeUpSmall();
        }
        return false;
    }

    /** Catalog entry — "View all Mutual Funds" sits mid-dashboard above the calculators. */
    public boolean openViewAllMutualFunds() {
        scrollToTop();
        for (int i = 0; i < 12; i++) {
            if (tapQuick("View all Mutual Funds") || tapLabel("View all Mutual Funds")) {
                sleep(250);
                String src = pageSource();
                if (src.contains("All Mutual Funds") || src.contains("EditText")
                        || src.contains("Search Funds") || src.contains("Search")) {
                    return true;
                }
                if (!src.contains("SIP Calculator") && src.contains("Fund")) {
                    return true;
                }
            }
            swipeUpSmall();
        }
        return false;
    }

    /** MF often restores prior scroll (calculators at bottom); reset before tile hunts. */
    public boolean waitReady() {
        long deadline = System.currentTimeMillis() + 30_000;
        while (System.currentTimeMillis() < deadline) {
            String src = pageSource();
            String lower = src.toLowerCase();
            if (lower.contains("please wait") || src.contains("android.widget.ProgressBar")) {
                sleep(500);
                continue;
            }
            if (isDisplayed()) {
                scrollToTop();
                return true;
            }
            sleep(300);
        }
        if (isDisplayed()) {
            scrollToTop();
            return true;
        }
        return false;
    }

    /** Risk profile sits near the top of the MF dashboard; calculators are at the bottom. */
    private void scrollToTop() {
        for (int i = 0; i < 6; i++) {
            swipeDown();
        }
        sleep(200);
    }

    /**
     * After My Goals the Recommendation heading has a separate View all chip on the right
     * (2.1.4 dump: View all [552,1330]-[680,1372], clickable). A center tap of that row
     * stays on the dashboard. Never match "View all Mutual Funds".
     */
    public boolean openRecommendationList() {
        if (recommendationListOpen()) {
            return true;
        }
        String start = pageSource();
        if (start.contains("Fund Details") || (start.contains("One Time") && start.contains("Monthly"))) {
            leaveViaBack();
            sleep(500);
            if (recommendationListOpen()) {
                return true;
            }
            start = pageSource();
        }
        if (!start.contains("Recommendation")) {
            scrollToTop();
        }
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        for (int i = 0; i < 5; i++) {
            String src = pageSource();
            if (recommendationListOpen(src)) {
                return true;
            }
            if (src.contains("Recommendation")) {
                tapRecommendationViewAll(size);
                if (waitRecommendationList(2000)) {
                    return true;
                }
                // W3C can report a click without Flutter navigation — press the chip on the right.
                pressViewAllOnRecommendationRow(size);
                if (waitRecommendationList(2000)) {
                    return true;
                }
                swipeUpSmall();
                continue;
            }
            swipeUpSmall();
        }
        return recommendationListOpen();
    }

    /** Exact content-desc View all — not "View all Mutual Funds". */
    private boolean tapRecommendationViewAll(int[] size) {
        return tapFirstAny(Duration.ofSeconds(2),
                MobileWaits.xpath("//*[@content-desc='View all']"),
                accessibility("View all"))
                || pressViewAllOnRecommendationRow(size);
    }

    /** View all sits on the right of the Recommendation heading (same row, ~86% of width). */
    private boolean pressViewAllOnRecommendationRow(int[] size) {
        WebElement viewAll = firstPresent(Duration.ofMillis(600),
                MobileWaits.xpath("//*[@content-desc='View all']"));
        if (viewAll != null) {
            pressElement(viewAll);
            return true;
        }
        WebElement heading = firstPresent(Duration.ofMillis(600),
                MobileWaits.xpath("//*[@content-desc='Recommendation']"));
        if (heading != null) {
            var rect = heading.getRect();
            shellPress((int) (size[0] * 0.86), rect.getY() + Math.max(20, rect.getHeight() / 2));
            return true;
        }
        return false;
    }

    private boolean waitRecommendationList(int timeoutMs) {
        long end = System.currentTimeMillis() + timeoutMs;
        while (System.currentTimeMillis() < end) {
            if (recommendationListOpen()) {
                return true;
            }
            sleep(250);
        }
        return recommendationListOpen();
    }

    public boolean recommendationListOpen() {
        return recommendationListOpen(pageSource());
    }

    private boolean recommendationListOpen(String src) {
        // Title wraps: "Funds basis on your" / "risk profile"
        String flat = src.replace('\n', ' ').replace('\r', ' ')
                .replace("&#10;", " ").replaceAll("\\s+", " ");
        return flat.contains("Funds basis on your")
                || flat.contains("Funds based on your");
    }

    public void openTab(String label) {
        tapFirstAny(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Button[@content-desc=" + xpathLiteral(label) + "]"),
                accessibility(label));
        sleep(400);
    }

    /**
     * From Portfolio / Transactions / Profile: in-app Back first (W3C, then header chip).
     * If Back does not leave the tab, tap the Dashboard tab — stay inside Mutual Funds.
     * System Back is not used here; it leaves Mutual Funds or stays on Portfolio.
     */
    public boolean returnToDashboardFromTab() {
        if (onDashboardTab()) {
            return true;
        }
        if (tapFirstAny(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Back']"),
                MobileWaits.xpath("//*[@content-desc='Back']"),
                accessibility("Back"))) {
            sleep(500);
            if (onDashboardTab()) {
                return true;
            }
        }
        pressHeaderBackChip();
        sleep(500);
        if (onDashboardTab()) {
            return true;
        }
        openTab("Dashboard");
        sleep(500);
        return onDashboardTab();
    }

    public boolean onDashboardTab() {
        String src = pageSource();
        if (src.contains("Investments") && (src.contains("Digital Gold") || src.contains("NPS"))) {
            return false;
        }
        boolean dashboardCues = src.contains("Add Goal") || src.contains("My Goals")
                || src.contains("Create Goal") || src.contains("View all Mutual Funds")
                || src.contains("Your Risk Profile") || src.contains("Start eKYC")
                || src.contains("Recommendation");
        boolean portfolioOnly = src.contains("Current Value") && !dashboardCues;
        return src.contains("Mutual Funds") && dashboardCues && !portfolioOnly;
    }

    /** Nested MF pages (fund details / recommendation list) Back until the dashboard. */
    public boolean ensureDashboard() {
        for (int i = 0; i < 5; i++) {
            if (onDashboardTab() || isDisplayed()) {
                return true;
            }
            String src = pageSource();
            if (src.contains("Fund Details") || src.contains("Funds basis on your")
                    || src.contains("Funds based on your")
                    || (src.contains("One Time") && src.contains("Monthly"))
                    || (src.contains("Delete") && src.contains("Monthly SIP"))) {
                leaveViaBack();
                continue;
            }
            if (src.contains("Current Value") || src.contains("Transactions")
                    || (src.contains("Profile") && src.contains("Mutual Funds"))) {
                openTab("Dashboard");
                continue;
            }
            break;
        }
        return onDashboardTab() || isDisplayed();
    }

    public boolean goalListed(String name, String amount) {
        String grouped = groupedAmount(amount);
        if (goalDetailsOpen(name)) {
            String src = pageSource();
            return src.contains(amount) || src.contains(grouped);
        }
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
        if (goalDetailsOpen(name)) {
            return true;
        }
        scrollToTop();
        String needle = name.toLowerCase();
        String titled = name.isEmpty() ? name
                : Character.toUpperCase(name.charAt(0)) + name.substring(1);
        for (int i = 0; i < 12; i++) {
            String src = pageSource();
            if (src.toLowerCase().contains(needle)) {
                if (tapFirstAny(Duration.ofSeconds(3),
                        accessibility(titled),
                        accessibility(name),
                        MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(titled) + ")]"),
                        MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(name) + ")]"))) {
                    sleep(800);
                    return true;
                }
            }
            swipeUpSmall();
        }
        return goalDetailsOpen(name);
    }

    private boolean goalDetailsOpen(String name) {
        String src = pageSource();
        return src.contains("Delete")
                && src.toLowerCase().contains(name.toLowerCase())
                && (src.contains("Monthly SIP") || src.contains("Goal created on")
                || src.contains("Desired goal value"));
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
                pressElement(arrow);
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
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        drag(size[0] / 2, (int) (size[1] * 0.35), size[0] / 2, (int) (size[1] * 0.72));
        sleep(350);
    }

    /**
     * The My Goals row is one button, "My Goals" plus "Add Goal".
     * A center click stays on the dashboard. The Add Goal label is on the right.
     */
    public boolean openAddGoal() {
        if (createGoalOpen()) {
            return true;
        }
        String here = pageSource();
        if (here.contains("Delete") && (here.contains("Monthly SIP") || here.contains("Desired goal value"))) {
            leaveViaBack();
            sleep(700);
        }
        scrollToTop();
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        for (int i = 0; i < 8; i++) {
            if (tapQuick("Add Goal") || tapLabel("Add Goal")
                    || tapQuick("Create Goal") || tapLabel("Create Goal")) {
                sleep(350);
                if (createGoalOpen()) {
                    return true;
                }
            }
            WebElement row = firstPresent(Duration.ofMillis(600),
                    MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Add Goal')]"),
                    MobileWaits.xpath("//*[contains(@content-desc,'Add Goal')]"));
            if (row != null) {
                var rect = row.getRect();
                int x = rect.getWidth() > 40
                        ? rect.getX() + rect.getWidth() - Math.max(40, rect.getWidth() / 8)
                        : (int) (size[0] * 0.88);
                int y = rect.getHeight() > 8
                        ? rect.getY() + rect.getHeight() / 2
                        : (int) (size[1] * 0.38);
                tapAt(x, y);
                sleep(350);
                if (createGoalOpen()) {
                    return true;
                }
            }
            swipeUpSmall();
        }
        return createGoalOpen();
    }

    private boolean createGoalOpen() {
        String src = pageSource();
        return (src.contains("Create Goal") && (src.contains("Bike") || src.contains("Select Tenure")))
                || (src.contains("Select Tenure") && src.contains("year"));
    }

    /**
     * Curated tiles sit under Curated Funds. Index tiles sit under Index Funds.
     * The row can extend off the right edge, so the section is held and swiped sideways.
     */
    public boolean openTileInSection(String section, String... labels) {
        scrollToTop();
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
        scrollToTop();
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

    /**
     * Portfolio header eye — unlabeled ImageView top-right (dump: [622,64]-[690,146]).
     * Not Help (named) and not the Dashboard bookmark.
     */
    public boolean toggleEye() {
        if (!pageSource().contains("Current Value")) {
            openTab("Portfolio");
            sleep(600);
        }
        if (tapFirstAny(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.ImageView[@clickable='true']"))) {
            sleep(500);
            return true;
        }
        WebElement eye = firstPresent(Duration.ofSeconds(2),
                MobileWaits.xpath("//android.widget.ImageView[@clickable='true']"));
        if (eye != null) {
            pressElement(eye);
            sleep(500);
            return true;
        }
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        shellPress((int) (size[0] * 0.91), (int) (size[1] * 0.066));
        sleep(500);
        return true;
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
        int screenBottom = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""))[1];
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
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        drag(size[0] / 2, (int) (size[1] * 0.72), size[0] / 2, (int) (size[1] * 0.46));
        sleep(80);
    }

    private void swipeLeft() {
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        int y = (int) (size[1] * 0.55);
        drag((int) (size[0] * 0.86), y, (int) (size[0] * 0.16), y);
        sleep(80);
    }

    private void drag(int startX, int startY, int endX, int endY) {
        adbSwipe(startX, startY, endX, endY, 280);
    }
}
