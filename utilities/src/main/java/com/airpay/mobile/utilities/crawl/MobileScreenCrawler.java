package com.airpay.mobile.utilities.crawl;

import com.airpay.mobile.utilities.android.PermissionDialogHandler;
import com.airpay.mobile.utilities.config.FrameworkConfig;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HexFormat;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.regex.Pattern;

/**
 * Crawlee-style native screen crawler for Appium.
 * <p>
 * Discovers clickable actions, fills forms with {@link DummyFormFiller}, follows
 * Apply → employment → Continue/Next branches, fingerprints screens to avoid
 * loops, and stops before irreversible Submit/Pay actions.
 */
public final class MobileScreenCrawler {

    private static final Logger LOG = LogManager.getLogger(MobileScreenCrawler.class);

    private static final List<String> FORWARD_KEYWORDS = List.of(
            "apply now", "apply for loan", "apply", "get started", "check eligibility", "continue", "next",
            "proceed", "save & continue", "save and continue", "confirm details"
    );

    private static final List<String> BRANCH_KEYWORDS = List.of(
            "salaried", "self employed", "self-employed", "selfemployed",
            "business", "professional", "yes", "no"
    );

    private static final Set<String> NAV_CHROME = Set.of(
            "home", "loan", "info", "profile", "notifications", "back", "notification",
            "invest", "portfolio", "loans", "more", "dashboard", "menu",
            "products", "finsight", "calculators"
    );

    /** Screens already opened in this JVM. Each screen is scanned once, like the Drupal page inventory. */
    private static final Set<String> SESSION_EXPLORED = java.util.Collections.synchronizedSet(new LinkedHashSet<>());

    private static final List<Pattern> STOP_PATTERNS = List.of(
            Pattern.compile("(?i)^submit$"),
            Pattern.compile("(?i)resend"),
            Pattern.compile("(?i)submit application"),
            Pattern.compile("(?i)^confirm$"),
            Pattern.compile("(?i)confirm payment"),
            Pattern.compile("(?i)^pay\\b"),
            Pattern.compile("(?i)pay now"),
            Pattern.compile("(?i)logout"),
            Pattern.compile("(?i)delete"),
            Pattern.compile("(?i)enable biometric"),
            Pattern.compile("(?i)^enable$")
    );

    private final AndroidDriver driver;
    private final FrameworkConfig config;
    private final DummyFormFiller filler;
    private final BiConsumer<String, String> onScreen; // id, label
    private final Set<String> visitedFingerprints = new LinkedHashSet<>();
    private final Set<String> scannedIds = new LinkedHashSet<>();
    private final int maxScreens;
    private final int maxDepth;
    private final int maxForwardSteps;
    private final long branchBudgetMs;
    private final boolean stopBeforeSubmit;
    private final String appPackage;
    private long branchDeadlineMs;
    private boolean serverErrorSeen;

    public MobileScreenCrawler(AndroidDriver driver,
                               FrameworkConfig config,
                               BiConsumer<String, String> onScreen) {
        this.driver = driver;
        this.config = config;
        this.filler = new DummyFormFiller(driver);
        this.onScreen = onScreen;
        this.maxScreens = config.getInt("a11y.deep.max.screens", 200);
        this.maxDepth = config.getInt("a11y.crawl.max.depth", 12);
        this.maxForwardSteps = config.getInt("a11y.crawl.max.forward.steps", 12);
        this.branchBudgetMs = config.getLong("a11y.crawl.branch.budget.ms", 180_000L);
        this.stopBeforeSubmit = config.getBoolean("a11y.crawl.stop.before.submit", true);
        this.appPackage = currentPackage();
    }

    public int screensVisited() {
        return scannedIds.size();
    }

    /** True if this crawl aborted due to HTTP 500 / server error UI. */
    public boolean sawServerError() {
        return serverErrorSeen || looksLikeServerError();
    }

    private boolean budgetExceeded() {
        return System.currentTimeMillis() >= branchDeadlineMs;
    }

    private boolean looksLikeServerError() {
        String src = safeSource().toLowerCase(Locale.ROOT);
        if (src.contains("internal server")
                || src.contains("something went wrong")
                || src.contains("server error")
                || src.contains("http error")
                || src.contains("try again later")
                || src.contains("unable to process")
                || src.contains("request failed")) {
            return true;
        }
        // Require error context with 500 — avoid matching loan amounts like 500000
        return (src.contains("http 500") || src.contains("error 500") || src.contains("status code 500")
                || src.contains("status: 500") || src.contains("\"500\""))
                || (src.contains(" 500") && src.contains("error"));
    }

    /** Dismiss common error dialogs then leave the screen. */
    private void abortOnServerError(String contextLabel) {
        serverErrorSeen = true;
        LOG.warn("SERVER ERROR detected on {} — aborting branch (no retry)", contextLabel);
        scanCurrent(slug(contextLabel) + "-server-error", contextLabel + " — server error");
        for (String dismiss : List.of("OK", "Ok", "Close", "Got it", "Retry", "Dismiss", "Back")) {
            if (tapLabel(dismiss)) {
                sleep(300);
                break;
            }
        }
        navigateBack();
        sleep(300);
    }

    /**
     * Deep-crawl from the current screen (e.g. product landing after opening Home Loan).
     *
     * @param rootId    slug prefix e.g. home-loan
     * @param rootLabel human label e.g. Home Loan
     */
    public void crawlFromHere(String rootId, String rootLabel) {
        crawlFromHere(rootId, rootLabel, false);
    }

    /**
     * @param alreadyInApplyFlow when true, skip re-tapping Apply (page object already entered)
     */
    public void crawlFromHere(String rootId, String rootLabel, boolean alreadyInApplyFlow) {
        branchDeadlineMs = System.currentTimeMillis() + branchBudgetMs;
        serverErrorSeen = false;
        LOG.info("Crawler start: {} (maxScreens={}, maxForward={}, budgetMs={}, alreadyInApply={})",
                rootLabel, maxScreens, maxForwardSteps, branchBudgetMs, alreadyInApplyFlow);
        scanCurrent(rootId, rootLabel);
        if (looksLikeServerError()) {
            abortOnServerError(rootLabel);
            return;
        }

        if (!alreadyInApplyFlow) {
            scrollToEnd(2);
            if (tapBestForward(List.of("Apply Now", "Apply now", "Apply", "Get Started", "Check Eligibility"))) {
                sleep(400);
                allowDialogs();
                if (looksLikeServerError()) {
                    abortOnServerError(rootLabel + " after Apply");
                    return;
                }
                scanCurrent(rootId + "-apply", rootLabel + " — Apply");
                crawlRecursive(rootId + "-apply", rootLabel + " — Apply", 1, new ArrayDeque<>());
                return;
            }
        }
        crawlRecursive(rootId, rootLabel, 1, new ArrayDeque<>());
    }

    /**
     * Click every interactive control (buttons, links, tiles, images) and recurse
     * into child screens. Skips logout/pay/delete and bottom-nav chrome.
     */
    public void crawlAllInteractive(String rootId, String rootLabel) {
        branchDeadlineMs = System.currentTimeMillis() + branchBudgetMs;
        serverErrorSeen = false;
        int maxClicks = config.getInt("a11y.crawl.max.clicks.per.screen", 80);
        int scrollPasses = config.getInt("a11y.info.scroll.swipes", 12);
        LOG.info("Exhaustive crawler start: {} (maxScreens={}, maxDepth={}, clicks/screen={}, budgetMs={})",
                rootLabel, maxScreens, maxDepth, maxClicks, branchBudgetMs);
        crawlInteractiveRecursive(rootId, rootLabel, 1, maxClicks, scrollPasses, new LinkedHashSet<>());
    }

    private void crawlInteractiveRecursive(String parentId, String parentLabel, int depth,
                                           int maxClicks, int scrollPasses, Set<String> exploredFp) {
        if (depth > maxDepth || scannedIds.size() >= maxScreens || budgetExceeded()) {
            return;
        }
        if (!inApp()) {
            returnToApp();
            return;
        }
        allowDialogs();
        scanCurrent(parentId, parentLabel);
        if (looksLikeServerError()) {
            abortOnServerError(parentLabel);
            return;
        }
        String here = fingerprint();
        if (!exploredFp.add(here) || !SESSION_EXPLORED.add(here)) {
            LOG.info("Screen already scanned once — leaving it ({})", parentLabel);
            return;
        }
        int filled = filler.fillVisibleFields(0);
        if (filled > 0) {
            scanCurrent(parentId + "-filled", parentLabel + " (filled)");
        }

        Set<String> tried = new LinkedHashSet<>();
        int clicks = 0;
        scrollToTop(4);
        while (clicks < maxClicks && scannedIds.size() < maxScreens && !budgetExceeded()) {
            Interactive next = firstUntouchedVisible(tried);
            if (next == null) {
                String fpBeforeSwipe = fingerprint();
                swipeUp();
                sleep(250);
                next = firstUntouchedVisible(tried);
                if (next == null && fingerprint().equals(fpBeforeSwipe)) {
                    break;
                }
                if (next == null) {
                    continue;
                }
            }
            tried.add(next.key);
            if (shouldSkipInteractive(next.label) || (stopBeforeSubmit && isStopAction(next.label))
                    || (next.label.startsWith("image-") && next.y < 450)) {
                continue;
            }
            String before = fingerprint();
            if (!tapInteractive(next)) {
                continue;
            }
            clicks++;
            sleep(500);
            allowDialogs();
            if (!inApp()) {
                LOG.info("Left app after '{}' — pressing Back, not relaunching", next.label);
                returnToApp();
                continue;
            }
            if (looksLikeServerError()) {
                abortOnServerError(parentLabel + " → " + next.label);
                return;
            }
            String after = fingerprint();
            if (before.equals(after)) {
                continue;
            }
            String childId = parentId + "-" + slug(next.label);
            String childLabel = parentLabel + " → " + next.label;
            LOG.info("Opened child '{}' from {}", next.label, parentLabel);
            crawlInteractiveRecursive(childId, childLabel, depth + 1, maxClicks, scrollPasses, exploredFp);
            backUntilFingerprint(before);
            sleep(280);
            if (!inApp()) {
                returnToApp();
            }
            scrollToTop(4);
        }
    }

    private record Interactive(String key, String label, int x, int y) {
    }

    private Interactive firstUntouchedVisible(Set<String> tried) {
        for (Interactive action : visibleInteractives()) {
            if (!tried.contains(action.key) && !shouldSkipInteractive(action.label)) {
                return action;
            }
        }
        return null;
    }

    private List<Interactive> visibleInteractives() {
        List<Interactive> out = new ArrayList<>();
        Dimension window = driver.manage().window().getSize();
        int minY = (int) (window.height * 0.07);
        int maxY = (int) (window.height * 0.88);
        List<WebElement> nodes;
        try {
            nodes = driver.findElements(By.xpath(
                    "//*[@clickable='true']"
                            + " | //android.widget.Button"
                            + " | //android.widget.ImageView[@clickable='true' or string-length(@content-desc)>0]"
                            + " | //android.widget.ImageButton"));
        } catch (Exception e) {
            return out;
        }
        LinkedHashSet<String> seen = new LinkedHashSet<>();
        for (WebElement n : nodes) {
            try {
                if (!n.isDisplayed() || !n.isEnabled()) {
                    continue;
                }
                String cls = String.valueOf(n.getAttribute("className"));
                if (cls.contains("EditText")) {
                    continue;
                }
                Rectangle r = n.getRect();
                if (r.getWidth() < 16 || r.getHeight() < 16) {
                    continue;
                }
                int cx = r.getX() + r.getWidth() / 2;
                int cy = r.getY() + r.getHeight() / 2;
                if (cy < minY || cy > maxY) {
                    continue;
                }
                String desc = firstAttr(n, "contentDescription", "content-desc", "text");
                String label = desc == null || desc.isBlank() ? "image-" + cx + "-" + cy : shortActionLabel(desc);
                if (label.isBlank()) {
                    label = "image-" + cx + "-" + cy;
                }
                String key = normalize(label) + "@" + (cx / 8) + "," + (cy / 8);
                if (!seen.add(key)) {
                    continue;
                }
                out.add(new Interactive(key, label, cx, cy));
            } catch (Exception ignored) {
                // stale
            }
        }
        return out;
    }

    private boolean tapInteractive(Interactive action) {
        if (action.label != null && !action.label.startsWith("image-") && tapLabel(action.label)) {
            return true;
        }
        return tapViewport(action.x, action.y);
    }

    private boolean tapViewport(int x, int y) {
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(tap));
            return true;
        } catch (Exception e) {
            LOG.debug("tapViewport failed: {}", e.toString());
            return false;
        }
    }

    private boolean shouldSkipInteractive(String label) {
        String n = normalize(label);
        if (n.isBlank() || n.equals("null")) {
            return true;
        }
        if (NAV_CHROME.contains(n)) {
            return true;
        }
        return n.startsWith("digit ")
                || n.contains("logout") || n.contains("log out") || n.contains("sign out")
                || n.contains("forgot pin") || n.contains("forgot your pin") || n.contains("send otp")
                || n.contains("profile picture") || n.contains("open settings") || n.equals("tu")
                || n.contains("delete") || n.contains("pay now") || n.equals("pay")
                || n.contains("confirm payment") || n.contains("enable biometric")
                || n.contains("share") || n.contains("rate this") || n.contains("rate us")
                || n.contains("call") || n.contains("dial") || n.contains("whatsapp")
                || n.contains("camera") || n.contains("gallery") || n.contains("photo")
                || n.contains("play store") || n.contains("chrome") || n.equals("back");
    }

    private boolean inApp() {
        try {
            String pkg = currentPackage();
            if (appPackage != null && !appPackage.isBlank() && pkg != null && !pkg.isBlank()) {
                return appPackage.equals(pkg);
            }
        } catch (Exception ignored) {
            // fall through
        }
        String src = safeSource();
        return src.contains("com.finmapp") || src.contains("airpay");
    }

    /** Back only. activateApp was leaving the app and reopening the profile photo. */
    private void returnToApp() {
        try {
            driver.navigate().back();
        } catch (Exception e) {
            LOG.debug("Back failed: {}", e.toString());
        }
        sleep(400);
    }

    private String currentPackage() {
        try {
            String pkg = driver.getCurrentPackage();
            return pkg == null ? "" : pkg;
        } catch (Exception e) {
            return "";
        }
    }

    private void backUntilFingerprint(String targetFp) {
        for (int i = 0; i < 10; i++) {
            if (targetFp != null && targetFp.equals(fingerprint())) {
                return;
            }
            navigateBack();
            sleep(320);
        }
    }

    private void scrollToTop(int maxSwipes) {
        String prev = fingerprint();
        for (int i = 0; i < maxSwipes; i++) {
            swipeDown();
            sleep(180);
            String next = fingerprint();
            if (prev.equals(next)) {
                return;
            }
            prev = next;
        }
    }

    private void swipeDown() {
        try {
            Dimension size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.28);
            int endY = (int) (size.height * 0.78);
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(280), PointerInput.Origin.viewport(), startX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(swipe));
        } catch (Exception e) {
            LOG.debug("swipeDown failed: {}", e.toString());
        }
    }

    private static String firstAttr(WebElement el, String... names) {
        for (String name : names) {
            try {
                String v = el.getAttribute(name);
                if (v != null && !v.isBlank() && !"null".equalsIgnoreCase(v)) {
                    return v;
                }
            } catch (Exception ignored) {
                // missing
            }
        }
        return "";
    }

    private void crawlRecursive(String parentId, String parentLabel, int depth, Deque<String> path) {
        if (depth > maxDepth || scannedIds.size() >= maxScreens || budgetExceeded()) {
            if (budgetExceeded()) {
                LOG.info("Branch budget exhausted on {} — stopping crawl", parentLabel);
            }
            return;
        }
        if (looksLikeServerError()) {
            abortOnServerError(parentLabel);
            return;
        }

        allowDialogs();
        // Light fill only — avoid heavy multi-pass that triggers APIs
        int filled = prepareAndFillStep();
        if (filled > 0) {
            sleep(250);
            if (looksLikeServerError()) {
                abortOnServerError(parentLabel + " after fill");
                return;
            }
            scanCurrent(parentId + "-filled", parentLabel + " (filled)");
        }

        // Outer flow owns occupation switching — no nested branch exploration here
        boolean exploreBranches = false;

        if (exploreBranches) {
            List<String> branches = discoverLabels().stream()
                    .filter(this::isBranchAction)
                    .filter(l -> !path.contains(normalize(l)))
                    .toList();

            for (String branch : branches) {
                if (scannedIds.size() >= maxScreens || budgetExceeded() || looksLikeServerError()) {
                    return;
                }
                String before = fingerprint();
                if (!tapLabel(branch)) {
                    continue;
                }
                sleep(350);
                allowDialogs();
                if (looksLikeServerError()) {
                    abortOnServerError(parentLabel + " → " + branch);
                    return;
                }
                String after = fingerprint();
                if (before.equals(after) && !looksLikeProgress()) {
                    continue;
                }
                String childId = parentId + "-" + slug(branch);
                String childLabel = parentLabel + " → " + branch;
                path.push(normalize(branch));
                scanCurrent(childId, childLabel);
                crawlRecursive(childId, childLabel, depth + 1, path);
                path.pop();
                backUntilFingerprintOrLabels(before, List.of("Salaried", "Self", "Apply", "Continue"));
            }
        }

        // Forward CTA loop — hard-capped (default 4); abort immediately on 500
        String lastFp = fingerprint();
        int stagnant = 0;
        for (int step = 1; step <= maxForwardSteps && scannedIds.size() < maxScreens; step++) {
            if (budgetExceeded()) {
                LOG.info("Branch budget hit at forward step {} on {}", step, parentLabel);
                break;
            }
            if (looksLikeServerError()) {
                abortOnServerError(parentLabel + " step " + step);
                return;
            }
            prepareAndFillStep();
            sleep(250);
            if (looksLikeServerError()) {
                abortOnServerError(parentLabel + " after fill step " + step);
                return;
            }

            List<String> forwards = discoverLabels().stream()
                    .filter(this::isForwardAction)
                    .filter(l -> !(stopBeforeSubmit && isStopAction(l)))
                    .toList();

            if (forwards.isEmpty()) {
                scrollToEnd(2);
                filler.fillVisibleFields(0);
                sleep(250);
                forwards = discoverLabels().stream()
                        .filter(this::isForwardAction)
                        .filter(l -> !(stopBeforeSubmit && isStopAction(l)))
                        .toList();
            }
            if (forwards.isEmpty()) {
                break;
            }

            String next = pickBestForward(forwards);
            if (next == null || (stopBeforeSubmit && isStopAction(next))) {
                LOG.info("Stop before irreversible action on {}", parentLabel);
                break;
            }

            String before = fingerprint();
            if (!tapLabel(next)) {
                break;
            }
            sleep(400);
            allowDialogs();
            if (looksLikeServerError()) {
                abortOnServerError(parentLabel + " after " + next);
                return;
            }
            String after = fingerprint();
            if (before.equals(after)) {
                scrollToEnd(2);
                filler.fillVisibleFields(0);
                sleep(300);
                if (!tapLabel(next) || fingerprint().equals(before)) {
                    LOG.info("Forward CTA '{}' did not change screen — end of path", next);
                    break;
                }
                if (looksLikeServerError()) {
                    abortOnServerError(parentLabel + " after retry " + next);
                    return;
                }
            }

            after = fingerprint();
            if (after.equals(lastFp)) {
                stagnant++;
                if (stagnant >= 2) {
                    LOG.info("Fingerprint stagnant twice on {} — leaving loan path", parentLabel);
                    break;
                }
            } else {
                stagnant = 0;
                lastFp = after;
            }

            String stepId = parentId + "-step-" + step;
            String stepLabel = parentLabel + " → " + next + " (" + step + ")";
            scanCurrent(stepId, stepLabel);
        }
    }

    /**
     * One scroll pass + light fill. Avoids hammering forms that call APIs.
     */
    private int prepareAndFillStep() {
        scrollToEnd(3);
        return filler.fillVisibleFields(0);
    }

    /** Swipe up until fingerprint stable or maxSwipes reached. */
    private void scrollToEnd(int maxSwipes) {
        String prev = fingerprint();
        int limit = Math.max(1, Math.min(maxSwipes, 6));
        for (int i = 0; i < limit; i++) {
            swipeUp();
            sleep(220);
            String next = fingerprint();
            if (prev.equals(next)) {
                return;
            }
            prev = next;
        }
    }

    private void scrollToEnd() {
        scrollToEnd(4);
    }

    private void scanCurrent(String id, String label) {
        String fp = fingerprint();
        String unique = id;
        int n = 2;
        while (!scannedIds.add(unique)) {
            unique = id + "-" + n++;
        }
        // Still scan even if fingerprint seen — different route may matter for a11y reports,
        // but skip heavy duplicate fingerprint spam after first.
        boolean novel = visitedFingerprints.add(fp);
        if (!novel) {
            LOG.debug("Revisit fingerprint on {} ({})", label, unique);
        }
        try {
            onScreen.accept(unique, label);
        } catch (Exception e) {
            LOG.warn("onScreen failed for {}: {}", unique, e.toString());
        }
    }

    private List<String> discoverLabels() {
        LinkedHashSet<String> labels = new LinkedHashSet<>();
        List<WebElement> nodes = driver.findElements(By.xpath(
                "//*[@clickable='true' and string-length(@content-desc)>0]"));
        for (WebElement n : nodes) {
            try {
                String desc = n.getAttribute("contentDescription");
                if (desc == null || desc.isBlank()) {
                    desc = n.getAttribute("content-desc");
                }
                if (desc == null || desc.isBlank()) {
                    continue;
                }
                String shortLabel = shortActionLabel(desc);
                if (!shortLabel.isBlank()) {
                    labels.add(shortLabel);
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return new ArrayList<>(labels);
    }

    private boolean isForwardAction(String label) {
        String n = normalize(label);
        if (NAV_CHROME.contains(n) || isStopAction(label)) {
            return false;
        }
        for (String k : FORWARD_KEYWORDS) {
            if (n.equals(k) || n.contains(k)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBranchAction(String label) {
        String n = normalize(label);
        if (NAV_CHROME.contains(n) || isForwardAction(label) || isStopAction(label)) {
            return false;
        }
        for (String k : BRANCH_KEYWORDS) {
            if (n.equals(k) || n.contains(k)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStopAction(String label) {
        String raw = label == null ? "" : label.trim();
        for (Pattern p : STOP_PATTERNS) {
            if (p.matcher(raw).find()) {
                return true;
            }
        }
        return false;
    }

    private String pickBestForward(List<String> forwards) {
        // Prefer stronger CTAs in order
        for (String prefer : List.of("Apply for Loan", "Apply Now", "Check Eligibility", "Get Started", "Continue", "Next", "Proceed", "Apply")) {
            for (String f : forwards) {
                if (f.equalsIgnoreCase(prefer) || normalize(f).contains(normalize(prefer))) {
                    return f;
                }
            }
        }
        return forwards.isEmpty() ? null : forwards.get(0);
    }

    private boolean tapBestForward(List<String> preferred) {
        for (String p : preferred) {
            if (tapLabel(p)) {
                return true;
            }
        }
        // Dynamic discovery
        for (String label : discoverLabels()) {
            if (isForwardAction(label) && !isStopAction(label) && tapLabel(label)) {
                return true;
            }
        }
        return false;
    }

    private boolean tapLabel(String label) {
        By[] locators = new By[]{
                AppiumBy.accessibilityId(label),
                By.xpath("//*[@clickable='true' and @content-desc=" + xpathLiteral(label) + "]"),
                By.xpath("//*[@clickable='true' and contains(@content-desc," + xpathLiteral(label) + ")]"),
                By.xpath("//*[contains(@content-desc," + xpathLiteral(label) + ")]")
        };
        for (By by : locators) {
            try {
                List<WebElement> els = driver.findElements(by);
                for (WebElement el : els) {
                    try {
                        if (el.isDisplayed()) {
                            el.click();
                            return true;
                        }
                    } catch (Exception ignored) {
                        // stale
                    }
                }
            } catch (Exception ignored) {
                // bad locator
            }
        }
        return false;
    }

    private void backUntilFingerprintOrLabels(String targetFp, List<String> labels) {
        for (int i = 0; i < 8; i++) {
            if (targetFp != null && targetFp.equals(fingerprint())) {
                return;
            }
            String src = safeSource();
            boolean hasLabel = labels.stream().anyMatch(src::contains);
            if (hasLabel && (targetFp == null || depthGuess() <= 1)) {
                // close enough
            }
            if (hasLabel && fingerprint().equals(targetFp)) {
                return;
            }
            navigateBack();
            sleep(350);
            if (targetFp != null && targetFp.equals(fingerprint())) {
                return;
            }
            if (labels.stream().anyMatch(l -> safeSource().contains(l))) {
                return;
            }
        }
    }

    private int depthGuess() {
        return scannedIds.size();
    }

    private void navigateBack() {
        if (!tapLabel("Back")) {
            try {
                driver.navigate().back();
            } catch (Exception e) {
                LOG.debug("navigate().back failed: {}", e.toString());
            }
        }
    }

    private boolean looksLikeProgress() {
        String src = safeSource().toLowerCase(Locale.ROOT);
        return src.contains("continue") || src.contains("next") || src.contains("salary")
                || src.contains("income") || src.contains("amount") || src.contains("employment");
    }

    private String fingerprint() {
        try {
            String src = driver.getPageSource();
            // Keep only content-desc / hint / text tokens for stable hashing
            StringBuilder sb = new StringBuilder();
            for (String token : src.split("[\"']")) {
                String t = token.trim();
                if (t.length() >= 2 && t.length() <= 80 && t.matches(".*[A-Za-z].*")) {
                    sb.append(t.toLowerCase(Locale.ROOT)).append('|');
                }
            }
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] dig = md.digest(sb.toString().getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(dig).substring(0, 16);
        } catch (Exception e) {
            return "fp-error-" + System.currentTimeMillis();
        }
    }

    private void swipeUp() {
        try {
            Dimension size = driver.manage().window().getSize();
            int startX = size.width / 2;
            int startY = (int) (size.height * 0.78);
            int endY = (int) (size.height * 0.28);
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            // Faster swipe (~300ms) to reveal off-screen fields/CTAs quickly
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), startX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(swipe));
        } catch (Exception e) {
            LOG.debug("swipeUp failed: {}", e.toString());
        }
    }

    private void allowDialogs() {
        try {
            PermissionDialogHandler.allowAll(driver, Duration.ofMillis(800));
        } catch (Exception ignored) {
            // none
        }
    }

    private String safeSource() {
        try {
            return driver.getPageSource();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Flutter often packs title + help + "Option N of M" into one long content-desc.
     * Prefer the title before the first period (e.g. "Salaried", "Self-Employed").
     */
    private static String shortActionLabel(String desc) {
        String first = desc.split("\n")[0].trim();
        if (first.isBlank()) {
            return "";
        }
        int optionIdx = first.toLowerCase(Locale.ROOT).indexOf(" option ");
        if (optionIdx > 0) {
            first = first.substring(0, optionIdx).trim();
        }
        int dot = first.indexOf('.');
        if (dot > 0 && dot <= 40) {
            return first.substring(0, dot).trim();
        }
        if (first.length() <= 80) {
            return first;
        }
        return first.substring(0, 80).trim();
    }

    private static String normalize(String s) {
        return s == null ? "" : s.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", " ").trim()
                .replaceAll("\\s+", " ");
    }

    private static String slug(String label) {
        String s = label.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-");
        return s.isBlank() ? "screen" : s.replaceAll("^-+|-+$", "");
    }

    private static String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
