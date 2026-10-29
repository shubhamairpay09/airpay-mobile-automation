package com.airpay.money.flows;

import com.airpay.money.screens.home.HomeScreen;
import com.airpay.money.screens.product.MutualFundsLandingScreen;
import com.airpay.money.screens.product.ProductsHubScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.BooleanSupplier;

/**
 * Ordered Products deep scan: Investments → Insurance → Loans → Personal Finance.
 * Fast mode: targeted scroll (no blind loops), explore filters/links/buttons/sub-screens,
 * in-app Back then system Back if needed, keep Money foreground.
 * See docs/analysis/money-products-deep-scan-flow.md.
 */
public final class MoneyProductsDeepWalker {

    private static final Logger LOG = LogManager.getLogger(MoneyProductsDeepWalker.class);
    private static final String PKG = "com.finmapp.fininfinity";

    private static final long TAP = 35;
    private static final long SCREEN = 70;

    /** Max clickables opened per module landing (filters, chips, links, CTAs). */
    private static final int MAX_CONTROLS = 20;
    /** Nested depth after opening a control (sub-filters / sub-links). */
    private static final int MAX_NEST = 2;

    private static final String[] SKIP_TAP = {
            // Pay/KYC only — Invest Now / Start Now ARE scanned (open → scan → leave).
            "Pay", "Pay Now", "Confirm", "Submit", "Continue to pay", "Make Payment",
            "Proceed to pay", "Verify OTP", "Get OTP", "Send OTP",
            "Aadhaar", "eSign", "DigiLocker", "UPI", "Net Banking", "Debit Card",
            "Logout", "Log out", "Delete", "Remove account",
            // External apps / share sheets steal focus → PIN gate on return
            "Share", "Twitter", "WhatsApp", "Telegram", "Gmail", "Chrome",
            "Open with", "Copy link", "Browser", "Play Store", "Call", "Email"
    };

    private static final String[] TAB_LABELS = {
            "Dashboard", "Portfolio", "Transactions", "Profile", "Orders", "Holdings",
            "Watchlist", "Explore", "Overview", "History"
    };

    private final TestContext context;
    private final HomeScreen home;
    private final ProductsHubScreen products;
    private final MutualFundsLandingScreen mf;
    private final ScanFn scan;
    private final Runnable publish;
    private final BooleanSupplier timeLeft;
    private final Runnable keepForeground;
    private final Runnable unlockPin;
    private final Set<String> coveredIds;
    /** Labels already opened in the current tile deep-pass — blocks soft re-taps. */
    private final Set<String> openedThisTile = new LinkedHashSet<>();
    /** Filter/Sort already covered for the current screen — exploreControls skips them. */
    private boolean filtersDoneThisScreen;

    @FunctionalInterface
    public interface ScanFn {
        void scan(String id, String label, String route);
    }

    public MoneyProductsDeepWalker(
            TestContext context,
            ScanFn scan,
            Runnable publish,
            BooleanSupplier timeLeft,
            Runnable keepForeground,
            Runnable unlockPin,
            Set<String> coveredIds) {
        this.context = context;
        this.home = new HomeScreen(context);
        this.products = new ProductsHubScreen(context);
        this.mf = new MutualFundsLandingScreen(context);
        this.scan = scan;
        this.publish = publish;
        this.timeLeft = timeLeft;
        this.keepForeground = keepForeground;
        this.unlockPin = unlockPin;
        this.coveredIds = coveredIds;
    }

    public void run() {
        LOG.info("=== Products DEEP fast (in-app leave + skip stuck loaders) ===");
        keepForeground.run();
        unlockPin.run();
        if (!returnToInvestmentsHub()) {
            LOG.warn("Investments hub not ready — will still try modules");
        } else {
            scan.scan("products-hub", "Products / Investments hub", "products");
        }

        coverInvestment("Mutual Funds", this::coverMutualFundsDeep);
        coverInvestment("NPS", () -> coverModuleDeep("NPS", "nps"));
        coverInvestment("Digital Gold", () -> coverModuleDeep("Digital Gold", "gold"));
        coverInvestment("Fixed Deposit", () -> coverModuleDeep("Fixed Deposit", "fd"));
        coverInvestment("US Stocks", () -> coverModuleDeep("US Stocks", "us"));
        coverInvestment("Indian Stocks", () -> coverModuleDeep("Indian Stocks", "in"));

        coverSectionTile("2 Wheeler", "ins-2w");
        coverSectionTile("4 Wheeler", "ins-4w");
        coverSectionTile("Health", "ins-health");
        coverSectionTile("Life", "ins-life");

        coverSectionTile("Gold Loan", "loan-gold");
        coverSectionTile("Credit Cards", "loan-cc");
        coverSectionTile("Personal Loan", "loan-pl");
        coverSectionTile("Home Loan", "loan-hl");
        coverSectionTile("Loan Against Property", "loan-lap");

        coverSectionTile("Tax Filing & Planning", "tax-file");
        coverSectionTile("Tax Filing", "tax-file-alt");

        publish.run();
        LOG.info("=== Products DEEP done ===");
    }

    private void coverInvestment(String tile, Runnable deep) {
        if (!timeLeft.getAsBoolean() || !sessionAlive()) {
            return;
        }
        // Only skip when a true deep pass finished (x-done). Shallow *-done alone is not enough.
        String doneId = "prod-" + slug(tile) + "-x-done";
        if (coveredIds.contains(doneId)) {
            LOG.info("[{}] already deep-covered — skip", tile);
            return;
        }
        LOG.info(">>> Products DEEP START: {}", tile);
        openedThisTile.clear();
        filtersDoneThisScreen = false;
        try {
            if (!returnToInvestmentsHub()) {
                LOG.warn("[{}] hub soft-fail — still opening tile", tile);
            }
            if (!openTileFast(tile)) {
                LOG.warn("[{}] tile not opened / loader stuck — skip next", tile);
                jumpProductsTab();
                return;
            }
            if (loaderPersists()) {
                LOG.warn("[{}] loader stuck — Products tab, next tile", tile);
                jumpProductsTab();
                return;
            }
            settle(SCREEN);
            deep.run();
            coveredIds.add(doneId);
            coveredIds.add("prod-" + slug(tile) + "-done");
            scan.scan(doneId, "Products / " + tile + " (deep done)", doneId);
            LOG.info("<<< Products DEEP DONE: {}", tile);
        } catch (Exception e) {
            LOG.warn("[{}] broke: {}", tile, e.toString());
        } finally {
            if (sessionAlive()) {
                jumpProductsTab();
            }
        }
    }

    private void coverSectionTile(String tile, String idPrefix) {
        if (!timeLeft.getAsBoolean() || !sessionAlive()) {
            return;
        }
        String doneId = idPrefix + "-x-done";
        if (coveredIds.contains(doneId)) {
            LOG.info("[{}] already deep-covered — skip", tile);
            return;
        }
        LOG.info(">>> Products DEEP START: {}", tile);
        openedThisTile.clear();
        filtersDoneThisScreen = false;
        try {
            if (!returnToInvestmentsHub()) {
                LOG.warn("[{}] hub soft-fail — still opening tile", tile);
            }
            if (!openTileFast(tile)) {
                LOG.warn("[{}] tile not opened / loader stuck — skip next", tile);
                jumpProductsTab();
                return;
            }
            if (loaderPersists()) {
                LOG.warn("[{}] loader stuck — Products tab, next tile", tile);
                jumpProductsTab();
                return;
            }
            settle(SCREEN);
            coverModuleDeep(tile, idPrefix);
            coveredIds.add(doneId);
            coveredIds.add(idPrefix + "-done");
            scan.scan(doneId, "Products / " + tile + " (deep done)", doneId);
            LOG.info("<<< Products DEEP DONE: {}", tile);
        } catch (Exception e) {
            LOG.warn("[{}] broke: {}", tile, e.toString());
        } finally {
            if (sessionAlive()) {
                jumpProductsTab();
            }
        }
    }

    /**
     * Open hub tile fast — no UiScrollable (that alone was ~30s when tile missing).
     * ADB Products → tap if visible → one swipe up → tap again.
     */
    private boolean openTileFast(String tile) {
        jumpProductsTab();
        // Enough swipes for lower Insurance/Loans/Tax tiles; idle-break if hub unchanged.
        String prev = "";
        for (int i = 0; i < 6; i++) {
            String src = safeSource();
            if (srcContains(src, tile)) {
                if (quickTap(tile) || products.openModule(tile) || home.tapLabelFast(tile)) {
                    return leftHubAfterOpen();
                }
                return false;
            }
            String fp = screenFingerprint();
            if (!fp.isBlank() && fp.equals(prev) && i >= 2) {
                break;
            }
            prev = fp;
            home.swipeUp();
            settle(TAP);
        }
        LOG.info("[{}] not in hub after swipes — skip", tile);
        return false;
    }

    private boolean leftHubAfterOpen() {
        ensureMoneyOnly();
        settle(SCREEN);
        if (loaderPersists()) {
            return false;
        }
        if (!onProductsHubLoose()) {
            return true;
        }
        // Some tiles (Tax / Cleartax) stay hub-framed — treat as open if body left the grid.
        String src = safeSource();
        return src.contains("Compare and File")
                || src.contains("Cleartax")
                || src.contains("Check Eligibility")
                || src.contains("Apply for")
                || src.contains("Get Quote")
                || insideProductModule(src);
    }

    /**
     * Wait for loader at most 25s (poll 500ms). If still loading → skip module (caller moves on).
     */
    private boolean loaderPersists() {
        if (!isLoaderShowing()) {
            return false;
        }
        long deadline = System.currentTimeMillis() + 25_000;
        while (System.currentTimeMillis() < deadline) {
            ensureMoneyOnly();
            if (!isLoaderShowing()) {
                return false;
            }
            settle(500);
        }
        LOG.warn("Loader persisted >25s — skipping this tile");
        return true;
    }

    private boolean isLoaderShowing() {
        String src = safeSource();
        if (src.isEmpty()) {
            return false;
        }
        String lower = src.toLowerCase(Locale.ROOT);
        return lower.contains("please wait")
                || lower.contains("content-desc=\"loading")
                || lower.contains(">loading<")
                || src.contains("android.widget.ProgressBar")
                || lower.contains("fetching")
                || lower.contains("please wait while");
    }

    /**
     * Leave one level WITHOUT killing Money.
     * NEVER system Back / NEVER am start MainActivity (those relaunch/crash the app).
     * Order: in-app Back → Close/Dismiss → vertical swipe. Hub jump only in leaveUntil/leaveScreen.
     */
    private void leaveOneLevel() {
        ensureMoneyOnly();
        String before = screenFingerprint();
        try {
            if (home.tapBack()) {
                settle(TAP);
                if (!screenFingerprint().equals(before)) {
                    return;
                }
            }
        } catch (Exception ignored) {
            // try close / swipe
        }
        if (home.tapLabelFast("Close") || home.tapLabelFast("Dismiss")
                || home.tapLabelFast("Cancel") || home.tapLabelFast("Not now")) {
            settle(TAP);
            if (!screenFingerprint().equals(before)) {
                return;
            }
        }
        try {
            home.swipeDown();
        } catch (Exception ignored) {
            // ignore
        }
        settle(TAP);
        ensureMoneyOnly();
    }

    private void coverMutualFundsDeep() {
        scanOnce("mf-dash", "Mutual Funds / Dashboard");
        coverFiltersAndEdits("mf", "Mutual Funds");
        exploreControls("mf", "Mutual Funds");
        openScanBack(new String[]{"Help", "Bookmark"}, "mf-hdr", "Mutual Funds / header");
        openScanBack(new String[]{"Create Mutual Fund Account", "Create Account", "Start eKYC"},
                "mf-create-acct", "Mutual Funds / Create account");

        if (mf.openAddGoal() || quickTap("Add Goal")) {
            settle(SCREEN);
            scanOnce("mf-add-goal", "Mutual Funds / Add Goal");
            coverFiltersAndEdits("mf-goal-sheet", "MF Add Goal");
            exploreControls("mf-goal-sheet", "MF Add Goal");
            backToMfDash();
        }

        if (openFirstGoal()) {
            settle(SCREEN);
            scanOnce("mf-goal", "Mutual Funds / Goal detail");
            coverFiltersAndEdits("mf-goal", "MF Goal");
            exploreControls("mf-goal", "MF Goal");
            backToMfDash();
        }

        if (mf.openRecommendationList() || quickTap("View all") || quickTap("Recommendation")) {
            settle(SCREEN);
            scanOnce("mf-rec-list", "Mutual Funds / Recommendation list");
            coverFiltersAndEdits("mf-rec", "MF Recommendation");
            exploreControls("mf-rec", "MF Recommendation");
            if (openFirstFundCard()) {
                settle(SCREEN);
                scanOnce("mf-rec-fund", "Mutual Funds / Recommended fund");
                coverFiltersAndEdits("mf-fund", "MF Fund");
                exploreControls("mf-fund", "MF Fund");
                leaveScreen(2);
            }
            backToMfDash();
        }

        if (quickTap("View all Mutual Funds") || mf.openSection("View all Mutual Funds")) {
            settle(SCREEN);
            scanOnce("mf-curated", "Mutual Funds / View all / Curated");
            coverFiltersAndEdits("mf-curated", "MF Curated");
            exploreControls("mf-curated", "MF Curated");
            backToMfDash();
        }

        for (String tab : TAB_LABELS) {
            if (!timeLeft.getAsBoolean()) {
                break;
            }
            String tabKey = normLabel(tab);
            if (!openedThisTile.add(tabKey)) {
                continue;
            }
            if (!quickTap(tab)) {
                try {
                    mf.openTab(tab);
                } catch (Exception ignored) {
                    openedThisTile.remove(tabKey);
                    continue;
                }
            }
            settle(SCREEN);
            scanOnce("mf-tab-" + slug(tab), "Mutual Funds / tab " + tab);
            coverFiltersAndEdits("mf-tab-" + slug(tab), "MF " + tab);
            exploreControls("mf-tab-" + slug(tab), "MF " + tab);
        }
        backToMfDash();
    }

    /** Landing + tabs + every safe filter/link/button/sub-module. */
    private void coverModuleDeep(String name, String prefix) {
        if (loaderPersists()) {
            LOG.warn("[{}] loader on landing — abort module", name);
            return;
        }
        scanOnce(prefix + "-dash", name + " / landing");
        coverFiltersAndEdits(prefix, name);
        exploreControls(prefix, name);
        for (String tab : TAB_LABELS) {
            if (!timeLeft.getAsBoolean()) {
                break;
            }
            if (loaderPersists()) {
                LOG.warn("[{}] loader mid-module — abort", name);
                return;
            }
            String tabKey = normLabel(tab);
            if (!openedThisTile.add(tabKey)) {
                continue;
            }
            if (!quickTap(tab)) {
                openedThisTile.remove(tabKey);
                continue;
            }
            settle(SCREEN);
            if (loaderPersists()) {
                leaveOneLevel();
                continue;
            }
            scanOnce(prefix + "-tab-" + slug(tab), name + " / " + tab);
            coverFiltersAndEdits(prefix + "-tab-" + slug(tab), name + " " + tab);
            exploreControls(prefix + "-tab-" + slug(tab), name + " " + tab);
            leaveScreen(1);
        }
    }

    /**
     * Open visible filters, chips, links, buttons, sub-modules — scan — leave.
     * Cap clicks; skip payment/KYC; no scroll loops.
     */
    private void exploreControls(String idPrefix, String title) {
        if (!timeLeft.getAsBoolean() || !sessionAlive()) {
            return;
        }
        String landingFp = screenFingerprint();
        List<String> labels = discoverControls();
        int opened = 0;
        // Per-screen only — same CTA may appear on another screen later in the tile.
        Set<String> openedThisScreen = new LinkedHashSet<>();
        for (String label : labels) {
            if (opened >= MAX_CONTROLS || !timeLeft.getAsBoolean()) {
                break;
            }
            if (shouldSkipControl(label)) {
                continue;
            }
            String key = normLabel(label);
            if (!openedThisScreen.add(key)) {
                continue;
            }
            if (!quickTap(label)) {
                openedThisScreen.remove(key);
                continue;
            }
            settle(SCREEN);
            if (loaderPersists()) {
                LOG.warn("Loader after '{}' — leave, next control", label);
                leaveOneLevel();
                continue;
            }
            opened++;
            String cid = idPrefix + "-ctl-" + slug(label);
            scanOnce(cid, title + " / " + label);
            if (isFilterish(label) && !filtersDoneThisScreen) {
                coverEditFields(cid, title + " / " + label);
            }
            exploreNested(idPrefix + "-" + slug(label), title + " / " + label, 1, openedThisScreen);
            leaveUntil(landingFp, 3);
            if (onProductsHubLoose()) {
                return;
            }
        }
    }

    private void exploreNested(String idPrefix, String title, int depth, Set<String> openedThisScreen) {
        if (depth > MAX_NEST || !timeLeft.getAsBoolean()) {
            return;
        }
        String fp = screenFingerprint();
        int n = 0;
        for (String label : discoverControls()) {
            if (n >= MAX_NEST || shouldSkipControl(label)) {
                continue;
            }
            String key = normLabel(label);
            if (!openedThisScreen.add(key)) {
                continue;
            }
            if (!quickTap(label)) {
                openedThisScreen.remove(key);
                continue;
            }
            settle(SCREEN);
            n++;
            String nid = idPrefix + "-n" + depth + "-" + slug(label);
            scanOnce(nid, title + " / " + label);
            if (isFilterish(label) && !filtersDoneThisScreen) {
                coverEditFields(nid, title + " / " + label);
            }
            if (depth < MAX_NEST) {
                exploreNested(idPrefix + "-" + slug(label), title + " / " + label, depth + 1, openedThisScreen);
            }
            leaveUntil(fp, 3);
            if (onProductsHubLoose()) {
                return;
            }
        }
    }

    /** Explicit Filter/Sort sheets + EditText fill before generic control crawl. */
    private void coverFiltersAndEdits(String idPrefix, String title) {
        if (!timeLeft.getAsBoolean()) {
            return;
        }
        filtersDoneThisScreen = false;
        coverEditFields(idPrefix + "-edit", title);
        String landingFp = screenFingerprint();
        String[] entries = {
                "Filters", "Filter", "Filter By", "Filter icon", "Sort", "Sort by", "Sort By"
        };
        // Local only — do not put filter/section keys in openedThisTile (tabs need Filter again).
        Set<String> opened = new LinkedHashSet<>();
        Set<String> sectionsTried = new LinkedHashSet<>();
        for (String entry : entries) {
            if (!timeLeft.getAsBoolean()) {
                filtersDoneThisScreen = true;
                return;
            }
            String key = entry.toLowerCase(Locale.ROOT).contains("filter") ? "filter"
                    : entry.toLowerCase(Locale.ROOT).contains("sort") ? "sort" : entry;
            if (!opened.add(key)) {
                continue;
            }
            String src = safeSource();
            if (!srcContains(src, entry)) {
                continue;
            }
            if (!quickTap(entry) && !home.tapLabelFast(entry)) {
                continue;
            }
            settle(SCREEN);
            String fid = idPrefix + "-" + slug(key);
            scanOnce(fid, title + " / " + entry);
            coverEditFields(fid, title + " / " + entry);
            for (String section : new String[]{
                    "Category", "AMC", "Risk", "Returns", "Rating",
                    "Select Categories", "Select AMCs", "Select Risk",
                    "Metal Weight", "Price Range", "Equity", "Debt", "Hybrid",
                    "Select date range", "Start date", "End date", "Date",
                    "1M", "3M", "6M", "1Y", "3Y", "5Y"
            }) {
                if (!timeLeft.getAsBoolean() || !srcContains(safeSource(), section)) {
                    continue;
                }
                if (!sectionsTried.add(normLabel(section))) {
                    continue;
                }
                if (!quickTap(section)) {
                    sectionsTried.remove(normLabel(section));
                    continue;
                }
                settle(SCREEN);
                String sid = fid + "-" + slug(section);
                scanOnce(sid, title + " / " + entry + " / " + section);
                coverEditFields(sid, title + " / " + section);
                leaveOneLevel();
            }
            leaveUntil(landingFp, 3);
            if (onProductsHubLoose()) {
                filtersDoneThisScreen = true;
                return;
            }
        }
        filtersDoneThisScreen = true;
    }

    private void coverEditFields(String parentId, String parentLabel) {
        try {
            var fields = context.driver().findElements(org.openqa.selenium.By.xpath("//android.widget.EditText"));
            int filled = 0;
            String before = screenFingerprint();
            for (var field : fields) {
                if (filled >= 6 || !field.isDisplayed()) {
                    continue;
                }
                String blob = (String.valueOf(field.getAttribute("hint")) + " "
                        + String.valueOf(field.getAttribute("text")) + " "
                        + String.valueOf(field.getAttribute("content-desc"))).toLowerCase(Locale.ROOT);
                if (blob.contains("password") || blob.contains("otp") || blob.contains("pin")) {
                    continue;
                }
                String value = blob.contains("search") ? "a"
                        : blob.contains("amount") || blob.contains("rupee") ? "1" : "1";
                field.click();
                try {
                    field.clear();
                } catch (Exception ignored) {
                    // compose fields may reject clear
                }
                field.sendKeys(value);
                filled++;
                settle(60);
            }
            if (filled > 0 && !screenFingerprint().equals(before)) {
                scanOnce(parentId + "-filled", parentLabel + " (filled)");
            } else if (filled > 0) {
                LOG.info("Filled {} edit field(s) on {}", filled, parentLabel);
            }
            try {
                context.driver().hideKeyboard();
            } catch (Exception ignored) {
                // already hidden
            }
        } catch (Exception e) {
            LOG.debug("Edit fields skipped: {}", e.toString());
        }
    }

    private static boolean isFilterish(String label) {
        String n = label.toLowerCase(Locale.ROOT);
        return n.contains("filter") || n.contains("sort") || n.contains("date")
                || n.equals("category") || n.equals("amc") || n.equals("risk");
    }

    private static boolean isModuleTab(String label) {
        String n = normLabel(label);
        for (String tab : TAB_LABELS) {
            if (n.equals(normLabel(tab))) {
                return true;
            }
        }
        return false;
    }

    private boolean shouldSkipControl(String label) {
        if (shouldSkipTap(label) || isModuleTab(label)) {
            return true;
        }
        // Filters/sort already handled by coverFiltersAndEdits for this screen.
        return filtersDoneThisScreen && isFilterish(label);
    }

    private static String normLabel(String label) {
        return label == null ? "" : label.split("\n")[0].trim().toLowerCase(Locale.ROOT);
    }

    private List<String> discoverControls() {
        List<String> out = new ArrayList<>();
        Set<String> seen = new LinkedHashSet<>();
        // Prefer Appium clickable labels when available.
        try {
            for (String label : home.allClickableLabels()) {
                addControl(out, seen, label);
            }
        } catch (Exception ignored) {
            // fall back to source parse
        }
        String src = safeSource();
        // CTAs only — Filter/Sort/tabs are covered by dedicated paths (no soft reloop).
        for (String hint : new String[]{
                "Invest Now", "Start Now", "Start SIP", "Invest More", "Buy Now",
                "All", "View all", "See all", "Details", "Know more",
                "Learn more", "Help", "Bookmark", "Apply Now",
                "Calculate", "Calculate Now", "Buy", "Sell",
                "Get Quote", "Check Eligibility", "Compare", "Plans", "View Plans",
                "Better than FD", "High returns", "Short term", "Equity", "Debt", "Hybrid",
                "Term", "Health", "New", "Renew", "Track", "Status", "Documents",
                "Eligibility", "Offers", "Check Now", "Start", "Continue", "Next"
        }) {
            if (srcContains(src, hint)) {
                addControl(out, seen, hint);
            }
        }
        return out;
    }

    private void addControl(List<String> out, Set<String> seen, String label) {
        if (label == null) {
            return;
        }
        String t = label.split("\n")[0].trim();
        if (t.isBlank() || t.length() > 48) {
            return;
        }
        if (shouldSkipControl(t)) {
            return;
        }
        // Skip bottom tab bar / chrome
        if (t.equals("Products") || t.equals("FinSight") || t.equals("Calculators")
                || t.equals("Hello") || t.equals("Notifications") || t.equals("Open settings")
                || t.equals("Menu option") || t.equals("Back")) {
            return;
        }
        if (seen.add(t)) {
            out.add(t);
        }
    }

    private static boolean shouldSkipTap(String label) {
        String n = label.toLowerCase(Locale.ROOT).trim();
        for (String s : SKIP_TAP) {
            String needle = s.toLowerCase(Locale.ROOT);
            if (n.equals(needle) || n.contains(needle)) {
                return true;
            }
        }
        if (n.contains("http://") || n.contains("https://") || n.contains("www.")) {
            return true;
        }
        return false;
    }

    private void openScanBack(String[] labels, String id, String title) {
        String src = safeSource();
        for (String label : labels) {
            if (!srcContains(src, label)) {
                continue;
            }
            if (quickTap(label)) {
                settle(SCREEN);
                scanOnce(id, title + " / " + label);
                leaveScreen(1);
                return;
            }
        }
    }

    private boolean openFirstGoal() {
        String src = safeSource();
        for (String label : new String[]{"Bike", "Car", "Education", "Home", "Goal"}) {
            if (srcContains(src, label) && quickTap(label)) {
                return true;
            }
        }
        return false;
    }

    private boolean openFirstFundCard() {
        String src = safeSource();
        for (String label : new String[]{"Direct", "Growth", "Invest"}) {
            if (srcContains(src, label) && quickTap(label)) {
                return true;
            }
        }
        return false;
    }

    private static boolean srcContains(String src, String label) {
        return src != null && src.contains(label);
    }

    private void backToMfDash() {
        for (int i = 0; i < 4; i++) {
            if (onMfDash() || onProductsHubLoose()) {
                return;
            }
            leaveOneLevel();
        }
        jumpProductsTab();
    }

    private boolean leaveScreen(int times) {
        for (int i = 0; i < times; i++) {
            String before = screenFingerprint();
            leaveOneLevel();
            ensureMoneyOnly();
            if (onProductsHubLoose() || !screenFingerprint().equals(before)) {
                return true;
            }
        }
        jumpProductsTab();
        return onProductsHubLoose();
    }

    private void leaveUntil(String fingerprint, int max) {
        for (int i = 0; i < max; i++) {
            ensureMoneyOnly();
            if (screenFingerprint().equals(fingerprint) || onProductsHubLoose()) {
                return;
            }
            leaveOneLevel();
        }
        jumpProductsTab();
    }

    /**
     * If another app stole focus: activateApp only — never am start MainActivity
     * (am start relaunches Flutter and looks like a crash).
     */
    private void ensureMoneyOnly() {
        String pkg = currentPackage();
        if (pkg.isEmpty() || PKG.equals(pkg)) {
            return;
        }
        LOG.warn("Focus left Money (pkg={}) — activateApp only (no relaunch)", pkg);
        try {
            context.driver().activateApp(PKG);
        } catch (Exception e) {
            keepForeground.run();
        }
        settle(120);
    }

    /** Products tab via in-app tap / bottom-nav coordinate — does not relaunch the app. */
    private void jumpProductsTab() {
        ensureMoneyOnly();
        if (home.tapLabelFast("Products")) {
            settle(80);
            return;
        }
        String udid = context.config().get("android.udid", "");
        // Bottom-nav Products — only while Money is already foreground
        if (PKG.equals(currentPackage())) {
            AdbCommands.shell(udid, "input", "tap", "180", "2200");
            settle(80);
        }
        ensureMoneyOnly();
    }

    private String screenFingerprint() {
        String src = safeSource();
        if (src.length() <= 240) {
            return src;
        }
        return src.substring(0, 120) + src.substring(src.length() - 120);
    }

    private boolean onMfDash() {
        String src = safeSource();
        return src.contains("content-desc=\"Mutual Funds\"")
                && (src.contains("content-desc=\"Dashboard\"") || src.contains("My Goals")
                || src.contains("Recommendation") || src.contains("Curated"));
    }

    /** Hub recovery in-app only (no system Back / no am start). */
    private boolean returnToInvestmentsHub() {
        if (!sessionAlive()) {
            return false;
        }
        ensureMoneyOnly();
        if (onProductsHubLoose()) {
            return true;
        }
        leaveOneLevel();
        if (onProductsHubLoose()) {
            return true;
        }
        jumpProductsTab();
        boolean ok = onProductsHubLoose();
        if (!ok) {
            LOG.warn("Hub check failed (pkg={}) — continue anyway", currentPackage());
        }
        return ok;
    }

    private boolean sessionAlive() {
        try {
            context.driver().getCurrentPackage();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String currentPackage() {
        try {
            return context.driver().getCurrentPackage();
        } catch (Exception e) {
            return "";
        }
    }

    private static boolean insideProductModule(String src) {
        return src.contains("View all Mutual Funds")
                || src.contains("Create Goal")
                || src.contains("Features of NPS")
                || src.contains("Open NPS Account")
                || src.contains("Explore NPS")
                || src.contains("Calculate Now")
                || src.contains("Buy Gold")
                || src.contains("Sell Gold")
                || src.contains("Own the Best Digital")
                || src.contains("Book FD")
                || src.contains("Select FD")
                || src.contains("Buy in rupees")
                || src.contains("Get Quote")
                || src.contains("Check Eligibility")
                || src.contains("Sign up for free")
                || src.contains("Apply for Credit Card")
                || src.contains("Eligible offers")
                || src.contains("Credit Card Terms")
                || src.contains("Muthoot")
                || src.contains("Check your loan");
    }

    /** True on Products tab grid (Investments / Insurance / Loans) — not inside a module. */
    private boolean onProductsHubLoose() {
        String src = safeSource();
        if (insideProductModule(src)) {
            return false;
        }
        boolean section = src.contains("content-desc=\"Investments\"")
                || src.contains("content-desc=\"Insurance\"")
                || src.contains("content-desc=\"Loans & Credit Cards\"")
                || src.contains("content-desc=\"Personal Finance\"");
        boolean tile = src.contains("content-desc=\"Digital Gold\"")
                || src.contains("content-desc=\"NPS\"")
                || src.contains("content-desc=\"Fixed Deposit\"")
                || src.contains("content-desc=\"Mutual Funds\"")
                || src.contains("content-desc=\"Personal Loan\"")
                || src.contains("content-desc=\"Credit Cards\"")
                || src.contains("content-desc=\"2 Wheeler\"")
                || src.contains("content-desc=\"Tax Filing");
        return section && tile;
    }

    private boolean onInvestmentsHub() {
        return onProductsHubLoose();
    }

    private boolean quickTap(String label) {
        return home.tapLabelFast(label);
    }

    private void scanOnce(String id, String label) {
        if (coveredIds.contains(id)) {
            return;
        }
        scan.scan(id, label, id);
        coveredIds.add(id);
    }

    private String safeSource() {
        try {
            return context.driver().getPageSource();
        } catch (Exception e) {
            return "";
        }
    }

    private static String slug(String label) {
        return label.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }

    private static void settle(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
