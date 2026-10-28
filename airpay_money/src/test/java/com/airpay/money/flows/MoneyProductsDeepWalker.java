package com.airpay.money.flows;

import com.airpay.money.screens.home.HomeScreen;
import com.airpay.money.screens.product.MutualFundsLandingScreen;
import com.airpay.money.screens.product.ProductsHubScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.Set;
import java.util.function.BooleanSupplier;

/**
 * Ordered Products deep scan: Investments → Insurance → Loans → Personal Finance.
 * Mutual Funds is the full template; other modules use landing + CTAs + tabs then hub.
 * See docs/analysis/money-products-deep-scan-flow.md.
 */
public final class MoneyProductsDeepWalker {

    private static final Logger LOG = LogManager.getLogger(MoneyProductsDeepWalker.class);
    private static final String PKG = "com.finmapp.fininfinity";

    /** Settles after a tap — keep tiny for speed (no multi-second hard waits). */
    private static final long TAP = 80;
    private static final long SCREEN = 150;

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
        LOG.info("=== Products DEEP (ordered) — see money-products-deep-scan-flow.md ===");
        keepForeground.run();
        unlockPin.run();
        if (!returnToInvestmentsHub()) {
            LOG.warn("Investments hub not ready — will still try modules");
        } else {
            scan.scan("products-hub", "Products / Investments hub", "products");
            publish.run();
        }

        coverInvestment("Mutual Funds", this::coverMutualFundsDeep);
        coverInvestment("NPS", () -> coverModuleShallow("NPS", "nps"));
        coverInvestment("Digital Gold", () -> coverModuleShallow("Digital Gold", "gold"));
        coverInvestment("Fixed Deposit", () -> coverModuleShallow("Fixed Deposit", "fd"));
        coverInvestment("US Stocks", () -> coverModuleShallow("US Stocks", "us"));
        coverInvestment("Indian Stocks", () -> coverModuleShallow("Indian Stocks", "in"));

        coverSectionTile("2 Wheeler", "ins-2w", true);
        coverSectionTile("4 Wheeler", "ins-4w", true);
        coverSectionTile("Health", "ins-health", true);
        coverSectionTile("Life", "ins-life", true);

        coverSectionTile("Gold Loan", "loan-gold", true);
        coverSectionTile("Credit Cards", "loan-cc", true);
        coverSectionTile("Personal Loan", "loan-pl", true);
        coverSectionTile("Home Loan", "loan-hl", true);
        coverSectionTile("Loan Against Property", "loan-lap", true);

        coverSectionTile("Tax Filing & Planning", "tax-file", true);
        coverSectionTile("Tax Filing", "tax-file-alt", true);

        publish.run();
        LOG.info("=== Products DEEP done ===");
    }

    private void coverInvestment(String tile, Runnable deep) {
        if (!timeLeft.getAsBoolean()) {
            return;
        }
        String doneId = "prod-" + slug(tile) + "-done";
        if (coveredIds.contains(doneId) || coveredIds.contains("prod-" + slug(tile))) {
            LOG.info("[{}] already covered — skip", tile);
            return;
        }
        LOG.info(">>> Products DEEP START: {}", tile);
        try {
            if (!returnToInvestmentsHub()) {
                LOG.warn("[{}] hub check soft-fail — still tapping tile", tile);
            }
            scrollForTile(tile);
            if (!products.openModule(tile) && !home.tapProductTile(tile)) {
                LOG.warn("[{}] tile not opened — skip", tile);
                returnToInvestmentsHub();
                return;
            }
            settle(SCREEN);
            deep.run();
            coveredIds.add(doneId);
            scan.scan(doneId, "Products / " + tile + " (deep done)", doneId);
            publish.run();
            LOG.info("<<< Products DEEP DONE: {}", tile);
        } catch (Exception e) {
            LOG.warn("[{}] broke — recovering to Investments hub: {}", tile, e.toString());
            publish.run();
        } finally {
            returnToInvestmentsHub();
        }
    }

    private void coverSectionTile(String tile, String idPrefix, boolean scrollDown) {
        if (!timeLeft.getAsBoolean()) {
            return;
        }
        String doneId = idPrefix + "-done";
        if (coveredIds.contains(doneId) || coveredIds.contains("prod-" + slug(tile))) {
            LOG.info("[{}] already covered — skip", tile);
            return;
        }
        LOG.info(">>> Products DEEP START: {}", tile);
        try {
            if (!returnToInvestmentsHub()) {
                return;
            }
            if (scrollDown) {
                for (int i = 0; i < 5; i++) {
                    home.swipeUp();
                    settle(TAP);
                }
            }
            if (!products.openModule(tile) && !home.tapProductTile(tile)) {
                LOG.warn("[{}] tile not opened — skip", tile);
                return;
            }
            settle(SCREEN);
            coverModuleShallow(tile, idPrefix);
            coveredIds.add(doneId);
            scan.scan(doneId, "Products / " + tile + " (deep done)", doneId);
            publish.run();
            LOG.info("<<< Products DEEP DONE: {}", tile);
        } catch (Exception e) {
            LOG.warn("[{}] broke — hub recovery: {}", tile, e.toString());
            publish.run();
        } finally {
            returnToInvestmentsHub();
        }
    }

    /** Full Mutual Funds ordered walk (flow doc Phase 1). */
    private void coverMutualFundsDeep() {
        scanOnce("mf-dash", "Mutual Funds / Dashboard");
        openScanBack(new String[]{"Help", "Bookmark"}, "mf-hdr", "Mutual Funds / header");
        openScanBack(new String[]{"Create Mutual Fund Account", "Create Account", "Start eKYC"},
                "mf-create-acct", "Mutual Funds / Create account");

        if (mf.openAddGoal() || quickTap("Add Goal")) {
            settle(SCREEN);
            scanOnce("mf-add-goal", "Mutual Funds / Add Goal");
            backToMfDash();
        }

        if (openFirstGoal()) {
            settle(SCREEN);
            scanOnce("mf-goal", "Mutual Funds / Goal detail");
            openScanStay(new String[]{"Monthly SIP", "One Time", "SIP", "Invest"}, "mf-goal-cta");
            backToMfDash();
        }

        if (mf.openRecommendationList() || quickTap("View all") || quickTap("Recommendation")) {
            settle(SCREEN);
            scanOnce("mf-rec-list", "Mutual Funds / Recommendation list");
            if (openFirstFundCard()) {
                settle(SCREEN);
                scanOnce("mf-rec-fund", "Mutual Funds / Recommended fund");
                openScanStay(new String[]{
                        "Bookmark", "Share", "One Time", "Monthly", "1M", "3M", "6M", "1Y", "3Y", "5Y",
                        "Returns", "Peer Comparison", "Peer comparison", "Holdings", "Holding"
                }, "mf-rec-fund-x");
                backTimes(2);
            }
            backToMfDash();
        }

        if (quickTap("View all Mutual Funds") || mf.openSection("View all Mutual Funds")) {
            settle(SCREEN);
            scanOnce("mf-curated", "Mutual Funds / View all / Curated");
            openScanStay(new String[]{"Better than FD", "High returns", "Short term", "Short Term"}, "mf-curated-cat");
            if (openFirstFundCard()) {
                scanOnce("mf-curated-fund", "Mutual Funds / Curated fund");
                backTimes(1);
            }
            backToMfDash();
        }

        if (mf.openTileInSection("Index Funds", "Nifty 50", "Nifty", "Sensex", "Index")
                || quickTap("Nifty 50")) {
            settle(SCREEN);
            scanOnce("mf-index", "Mutual Funds / Index fund");
            backToMfDash();
        }

        if (mf.openRiskEdit() || quickTap("Your Risk Profile") || quickTap("Edit")) {
            settle(SCREEN);
            scanOnce("mf-risk-edit", "Mutual Funds / Risk profile edit");
            backToMfDash();
        }

        openScanBack(new String[]{"Reassess"}, "mf-reassess", "Mutual Funds / Reassess");
        openScanBack(new String[]{"Import Funds", "Import"}, "mf-import", "Mutual Funds / Import Funds");

        if (quickTap("Goal Calculator") || mf.openSection("Goal Calculator")) {
            settle(SCREEN);
            scanOnce("mf-calc-goal", "Mutual Funds / Goal Calculator");
            backToMfDash();
        }
        if (quickTap("SIP Calculator") || mf.openSection("SIP Calculator")) {
            settle(SCREEN);
            scanOnce("mf-calc-sip", "Mutual Funds / SIP Calculator");
            backToMfDash();
        }

        for (String tab : new String[]{"Portfolio", "Transactions", "Profile", "Dashboard"}) {
            if (!timeLeft.getAsBoolean()) {
                break;
            }
            mf.openTab(tab);
            settle(SCREEN);
            scanOnce("mf-tab-" + slug(tab), "Mutual Funds / tab " + tab);
            home.swipeUp();
            settle(TAP);
            scanOnce("mf-tab-" + slug(tab) + "-s", "Mutual Funds / tab " + tab + " scroll");
        }
    }

    private void coverModuleShallow(String name, String prefix) {
        scanOnce(prefix + "-dash", name + " / landing");
        home.swipeUp();
        settle(TAP);
        scanOnce(prefix + "-s", name + " / scroll");
        openScanStay(new String[]{
                "Help", "Calculate Now", "Invest More", "Details", "Explore", "Buy", "Sell",
                "Apply Now", "Start Now", "View all", "Portfolio", "Transactions", "Profile", "Dashboard"
        }, prefix + "-cta");
        for (String tab : new String[]{"Portfolio", "Transactions", "Profile", "Dashboard"}) {
            if (quickTap(tab)) {
                settle(SCREEN);
                scanOnce(prefix + "-tab-" + slug(tab), name + " / " + tab);
            }
        }
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
                backTimes(1);
                return;
            }
        }
    }

    private void openScanStay(String[] labels, String idPrefix) {
        String src = safeSource();
        int n = 0;
        for (String label : labels) {
            if (n >= 6 || !timeLeft.getAsBoolean()) {
                return;
            }
            if (!srcContains(src, label) || !quickTap(label)) {
                continue;
            }
            settle(SCREEN);
            scanOnce(idPrefix + "-" + slug(label), "Open / " + label);
            n++;
            src = safeSource();
            if (!onMfDash()) {
                home.tapBack();
                settle(TAP);
                src = safeSource();
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
        for (int i = 0; i < 6; i++) {
            if (onMfDash()) {
                return;
            }
            if (!home.tapBack()) {
                break;
            }
            settle(TAP);
            unlockPin.run();
        }
    }

    private void backTimes(int n) {
        for (int i = 0; i < n; i++) {
            home.tapBack();
            settle(TAP);
        }
    }

    private boolean onMfDash() {
        String src = safeSource();
        return src.contains("content-desc=\"Mutual Funds\"")
                && (src.contains("content-desc=\"Dashboard\"") || src.contains("My Goals")
                || src.contains("Recommendation") || src.contains("Curated"));
    }

    /**
     * Fast hub recovery: ADB Products tab + short backs. Avoid 8× pageSource loops (~90s).
     */
    private boolean returnToInvestmentsHub() {
        keepForeground.run();
        unlockPin.run();
        String udid = context.config().get("android.udid", "");
        if (onInvestmentsHub()) {
            return true;
        }
        // Products tab (bottom-left on 1080×2340 Money).
        AdbCommands.shell(udid, "input", "tap", "180", "2200");
        settle(250);
        unlockPin.run();
        if (onInvestmentsHub()) {
            return true;
        }
        for (int i = 0; i < 4; i++) {
            home.tapBack();
            settle(TAP);
            AdbCommands.shell(udid, "input", "tap", "180", "2200");
            settle(200);
            unlockPin.run();
            if (onInvestmentsHub()) {
                return true;
            }
        }
        try {
            home.openProductsTab();
        } catch (Exception ignored) {
            AdbCommands.shell(udid, "input", "tap", "180", "2200");
        }
        settle(250);
        for (int i = 0; i < 2; i++) {
            home.swipeDown();
            settle(TAP);
        }
        boolean ok = onInvestmentsHub();
        if (!ok) {
            LOG.warn("Hub check failed — labels: {}", headLabels(safeSource()));
        }
        return ok;
    }

    private boolean onInvestmentsHub() {
        String src = safeSource();
        if (src.contains("View all Mutual Funds") || src.contains("Features of NPS")
                || src.contains("Open NPS Account") || src.contains("Buy in rupees")
                || src.contains("Buy Gold") || src.contains("Calculate Now")) {
            return false;
        }
        // Real Products investments grid: several investment tiles together.
        boolean mf = src.contains("content-desc=\"Mutual Funds\"") || src.contains("content-desc=\"Mutual Fund\"");
        boolean nps = src.contains("content-desc=\"NPS\"");
        boolean gold = src.contains("content-desc=\"Digital Gold\"");
        boolean fd = src.contains("content-desc=\"Fixed Deposit\"");
        int tiles = (mf ? 1 : 0) + (nps ? 1 : 0) + (gold ? 1 : 0) + (fd ? 1 : 0);
        return tiles >= 2 && (src.contains("Investments") || src.contains("content-desc=\"Hello\"")
                || src.contains("content-desc=\"Products\""));
    }

    private static String headLabels(String src) {
        if (src == null || src.isBlank()) {
            return "(empty)";
        }
        String flat = src.replaceAll("\\s+", " ");
        return flat.length() > 280 ? flat.substring(0, 280) : flat;
    }

    private void scrollForTile(String tile) {
        String n = tile.toLowerCase(Locale.ROOT);
        if (n.contains("loan") || n.contains("credit") || n.contains("tax")
                || n.contains("wheeler") || n.contains("health") || n.contains("life")
                || n.contains("gold loan")) {
            for (int i = 0; i < 4; i++) {
                home.swipeUp();
                settle(TAP);
            }
        } else {
            for (int i = 0; i < 2; i++) {
                home.swipeDown();
                settle(TAP);
            }
        }
    }

    private boolean quickTap(String label) {
        return home.tapLabelFast(label);
    }

    private void scanOnce(String id, String label) {
        if (coveredIds.contains(id)) {
            return;
        }
        // Do not unlock/pageSource on every scan — that was burning minutes.
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
