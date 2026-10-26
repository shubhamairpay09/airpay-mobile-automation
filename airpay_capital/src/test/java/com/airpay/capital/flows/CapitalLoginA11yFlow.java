package com.airpay.capital.flows;

import com.airpay.capital.screens.auth.MobileLoginScreen;
import com.airpay.capital.screens.auth.OtpScreen;
import com.airpay.capital.screens.auth.WelcomeScreen;
import com.airpay.capital.screens.home.HomeScreen;
import com.airpay.capital.screens.home.NotificationsScreen;
import com.airpay.capital.screens.home.ProfileScreen;
import com.airpay.capital.screens.info.InfoScreen;
import com.airpay.capital.screens.product.AbstractProductLandingScreen;
import com.airpay.capital.screens.product.HomeLoanLandingScreen;
import com.airpay.capital.screens.product.ProductLandingScreens;
import com.airpay.mobile.utilities.accessibility.A11yPageRef;
import com.airpay.mobile.utilities.accessibility.A11yPageScanResult;
import com.airpay.mobile.utilities.accessibility.A11yScanSession;
import com.airpay.mobile.utilities.accessibility.AndroidA11yScanner;
import com.airpay.mobile.utilities.android.NotificationOtpReader;
import com.airpay.mobile.utilities.android.PermissionDialogHandler;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.config.ProjectPaths;
import com.airpay.mobile.utilities.crawl.MobileScreenCrawler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Login + Appium-native deep accessibility crawl across Capital modules
 * (Profile, Notifications, Info, and each product Apply → multi-step forms
 * with dummy data fill). Product CTAs use page-scoped locators via
 * Product CTAs use screen objects ({@link AbstractProductLandingScreen}).
 */
public class CapitalLoginA11yFlow {

    private static final Logger LOG = LogManager.getLogger(CapitalLoginA11yFlow.class);

    /** Crawl order: Personal Loan first, then Home Loan, LAP, Gold, Credit Card. */
    private static final List<String> PRODUCT_MODULES = List.of(
            "Personal Loan",
            "Home Loan",
            "Loan Against Property",
            "Gold Loan",
            "Credit Card"
    );

    /** Remaining products when Home Loan is excluded (HTTP 500 risk). */
    private static final List<String> REMAINING_PRODUCT_MODULES = List.of(
            "Personal Loan",
            "Loan Against Property",
            "Gold Loan",
            "Credit Card"
    );

    private final TestContext context;
    private final WelcomeScreen welcome;
    private final MobileLoginScreen mobileLogin;
    private final OtpScreen otpPage;
    private final HomeScreen home;
    private final ProfileScreen profile;
    private final NotificationsScreen notifications;
    private final InfoScreen info;
    private final Set<String> scannedIds = new LinkedHashSet<>();

    public CapitalLoginA11yFlow(TestContext context) {
        this.context = context;
        this.welcome = new WelcomeScreen(context);
        this.mobileLogin = new MobileLoginScreen(context);
        this.otpPage = new OtpScreen(context);
        this.home = new HomeScreen(context);
        this.profile = new ProfileScreen(context);
        this.notifications = new NotificationsScreen(context);
        this.info = new InfoScreen(context);
    }

    public void loginWithMobileAndDeepScan() {
        A11yScanSession.clear();
        scannedIds.clear();
        allowDialogs(Duration.ofSeconds(12));
        bringCapitalToForeground();

        if (home.looksLoggedIn() || looksLikeOccupationChooser()
                || home.sourceContains("Tell us about your work", "Personal Loan", "Apply Now")) {
            LOG.info("Session already past login — starting full deep scan from current UI");
            forceBackHome();
            if (home.looksLoggedIn()) {
                scan("home", "Home", "home");
                if (context.config().getBoolean("a11y.deep.scan", true)) {
                    fullModuleDeepScan();
                }
                return;
            }
        }

        // Inspector / relaunch often lands on mobile-login, not Welcome
        if (mobileLogin.waitUntilDisplayed(Duration.ofSeconds(8))
                || home.sourceContains("Send OTP", "Enter 10-Digit", "Accept consent")) {
            LOG.info("Already on mobile-login — skipping Welcome");
            scan("mobile-login", "Mobile login", "mobileLogin");
            String mobileAlready = context.config().require("capital.test.mobile");
            mobileLogin.enterMobile(mobileAlready);
            mobileLogin.acceptConsent();
            sleep(400);
            mobileLogin.tapSendOtp();
            sleep(1500);
            allowDialogs(Duration.ofSeconds(8));
            scan("otp", "OTP verification", "otp");
            enterOtp(mobileAlready);
            sleep(2000);
            dismissPostLoginGates();
            if (home.looksLoggedIn()) {
                scan("home", "Home", "home");
                if (context.config().getBoolean("a11y.deep.scan", true)) {
                    fullModuleDeepScan();
                }
            }
            return;
        }

        if (!welcome.waitForLoginButton(Duration.ofSeconds(25))) {
            allowDialogs(Duration.ofSeconds(8));
            bringCapitalToForeground();
        }
        if (home.looksLoggedIn()) {
            LOG.info("Landed on Home after foregrounding — skipping login");
            scan("home", "Home", "home");
            if (context.config().getBoolean("a11y.deep.scan", true)) {
                fullModuleDeepScan();
            }
            return;
        }
        if (!welcome.waitForLoginButton(Duration.ofSeconds(10))) {
            LOG.warn("Welcome login button not visible after waits; scanning current screen anyway");
        }
        scan("welcome", "Welcome", "welcome");
        if (!welcome.waitForLoginButton(Duration.ofSeconds(3))) {
            throw new IllegalStateException(
                    "Capital is not on Welcome or Home. Unlock the phone, open Airpay Capital, "
                            + "leave it on Home, then re-run. Page source head: " + head(safeSource()));
        }
        welcome.tapLoginWithMobile();
        sleep(1200);
        allowDialogs(Duration.ofSeconds(8));

        if (!mobileLogin.waitUntilDisplayed()) {
            allowDialogs(Duration.ofSeconds(6));
        }
        if (!mobileLogin.waitUntilDisplayed(Duration.ofSeconds(8))) {
            throw new IllegalStateException(
                    "Mobile login screen did not appear after tapping 'Login With Mobile Number'. "
                            + "Page source head: " + head(safeSource()));
        }

        scan("mobile-login", "Mobile login", "mobileLogin");
        String mobile = context.config().require("capital.test.mobile");
        mobileLogin.enterMobile(mobile);
        mobileLogin.acceptConsent();
        sleep(400);
        mobileLogin.tapSendOtp();
        sleep(1500);
        allowDialogs(Duration.ofSeconds(8));

        scan("otp", "OTP verification", "otp");
        enterOtp(mobile);
        sleep(2000);
        if (home.sourceContains("biometric", "Biometric", "Authentication required")) {
            scan("biometric", "Biometric gate", "biometric");
        }
        dismissPostLoginGates();

        if (home.looksLoggedIn()) {
            scan("home", "Home", "home");
            pauseOnHomeIfConfigured();
            if (context.config().getBoolean("a11y.deep.scan", true)) {
                fullModuleDeepScan();
            }
        } else {
            LOG.warn("Still on pre-home UI after OTP — scanning current screen and retrying home.");
            scan("post-otp", "Post-OTP screen", "current");
            dismissPostLoginGates();
            if (home.looksLoggedIn()) {
                scan("home", "Home", "home");
                pauseOnHomeIfConfigured();
                if (context.config().getBoolean("a11y.deep.scan", true)) {
                    fullModuleDeepScan();
                }
            }
        }
    }

    /**
     * Holds the session on Home so a human can inspect Apply Now in Appium Inspector.
     * Enable with {@code -Dcapital.pause.on.home.ms=600000}.
     */
    private void pauseOnHomeIfConfigured() {
        long pauseMs = context.config().getLong("capital.pause.on.home.ms", 0);
        if (pauseMs <= 0) {
            return;
        }
        if (context.config().getBoolean("capital.debug.page.source", false)) {
            try {
                Path dump = ProjectPaths.resolveKind(ProjectPaths.KIND_LOGS)
                        .resolve("debug-home-page-source.xml");
                Files.createDirectories(dump.getParent());
                Files.writeString(dump, safeSource());
                LOG.info("Debug page source → {}", dump.toAbsolutePath());
            } catch (Exception e) {
                LOG.warn("Could not dump home page source: {}", e.toString());
            }
        }
        System.out.println();
        System.out.println("============================================================");
        System.out.println(">>> HOME SCREEN IS READY ON THE PHONE <<<");
        System.out.println(">>> Inspect Apply Now now (Appium Inspector / manually) <<<");
        System.out.println(">>> Pausing " + (pauseMs / 1000) + "s — session stays open <<<");
        System.out.println("============================================================");
        System.out.println();
        LOG.info("HOME SCREEN READY — pausing {} ms for manual Apply Now inspection", pauseMs);
        sleep(pauseMs);
    }

    private void dismissPostLoginGates() {
        for (int i = 0; i < 5; i++) {
            allowDialogs(Duration.ofSeconds(3));
            boolean skipped = home.tapAny(List.of(
                    "Skip, Continue without enabling biometric authentication",
                    "Skip",
                    "Not now",
                    "Maybe later",
                    "Continue without enabling biometric authentication",
                    "Continue"
            ));
            if (!skipped) {
                if (home.sourceContains("biometric", "Biometric", "Fingerprint", "Face")) {
                    if (home.tapLabel("Skip") || home.tapAny(List.of("Skip"))) {
                        sleep(1000);
                        continue;
                    }
                }
                break;
            }
            sleep(1200);
        }
        sleep(1500);
    }

    /**
     * Walks every Capital module with an Appium-native screen crawler:
     * page objects open Apply via curated locators → fill dummy form data →
     * Continue/Next until Review (stop before Submit/Pay).
     */
    private void fullModuleDeepScan() {
        int maxScreens = context.config().getInt("a11y.deep.max.screens", 120);
        LOG.info("Starting full Capital module deep scan (maxScreens={})", maxScreens);

        openScanBack("profile", "Profile", "profile", () -> {
            home.openHome();
            sleep(400);
            profile.open();
        });
        openScanBack("notifications", "Notifications", "notifications", () -> {
            home.openHome();
            sleep(400);
            notifications.open();
        });

        if (context.config().getBoolean("a11y.crawl.info", true)) {
            try {
                crawlInfoFully(maxScreens);
            } catch (Exception e) {
                LOG.warn("Info deep scan failed: {}", e.toString());
                home.openHome();
            }
        } else {
            LOG.info("Skipping Info crawl (a11y.crawl.info=false)");
        }

        bringCapitalToForeground();
        try {
            home.openLoanTab();
            sleep(1000);
            scan("loan-tab", "Loan tab", "loan-tab");
            home.openHome();
            sleep(800);
        } catch (Exception e) {
            LOG.warn("Loan tab scan failed: {}", e.toString());
            bringCapitalToForeground();
            home.openHome();
        }

        bringCapitalToForeground();
        for (String module : PRODUCT_MODULES) {
            if (scannedIds.size() >= maxScreens) {
                break;
            }
            try {
                crawlProductModule(module);
            } catch (Exception e) {
                LOG.warn("Module '{}' deep crawl failed: {}", module, e.toString());
                safeBackToHome();
            }
        }

        home.openHome();
        scan("home-final", "Home (after deep scan)", "home");
        LOG.info("Deep scan complete — {} unique screens", scannedIds.size());
    }

    /**
     * Info tab: scan landing, scroll to end, open FAQs / Contact / About / Settings
     * and tap every inner action (accordion, links) except Share/Rate/Logout.
     */
    private void crawlInfoFully(int maxScreens) {
        LOG.info("=== Info tab: FAQ / Contact / About / Settings ===");
        home.openHome();
        sleep(400);
        info.open();
        sleep(800);
        allowDialogs(Duration.ofSeconds(2));
        scan("info", "Info", "info");

        int infoSwipes = context.config().getInt("a11y.info.scroll.swipes", 8);
        for (int i = 1; i <= infoSwipes && scannedIds.size() < maxScreens; i++) {
            home.swipeUp();
            sleep(350);
            scan("info-scroll-" + i, "Info (scroll " + i + ")", "info:scroll:" + i);
        }
        // Return to Info landing
        info.open();
        sleep(500);

        crawlInfoSection("info-faqs", "FAQs", info::navigateToFaqs);
        crawlInfoSection("info-contact", "Contact Us", info::navigateToContactUs);
        crawlInfoSection("info-about", "About", info::navigateToAbout);
        crawlInfoSection("info-settings", "Settings", info::navigateToSettings);

        // Remaining clickable rows on Info (whatever is left)
        info.open();
        sleep(400);
        exploreInfoChildren("info-more", 12, maxScreens);

        home.openHome();
        sleep(500);
    }

    private void crawlInfoSection(String id, String label, java.util.function.BooleanSupplier open) {
        try {
            info.open();
            sleep(400);
            if (!open.getAsBoolean()) {
                LOG.info("Info section '{}' not found — skip", label);
                return;
            }
            sleep(700);
            allowDialogs(Duration.ofSeconds(2));
            scan(id, "Info — " + label, id);
            for (int i = 1; i <= 4; i++) {
                home.swipeUp();
                sleep(280);
                scan(id + "-scroll-" + i, "Info — " + label + " (scroll " + i + ")", id + ":scroll:" + i);
            }
            exploreInfoChildren(id, 10, context.config().getInt("a11y.deep.max.screens", 200));
            home.tapBack();
            sleep(400);
            info.open();
        } catch (Exception e) {
            LOG.warn("Info section {} failed: {}", label, e.toString());
            home.tapBack();
            info.open();
        }
    }

    private void exploreInfoChildren(String parentId, int maxClicks, int maxScreens) {
        List<String> labels = info.sectionLabels();
        int clicked = 0;
        for (String label : labels) {
            if (clicked >= maxClicks || scannedIds.size() >= maxScreens) {
                break;
            }
            String lower = label.toLowerCase(Locale.ROOT);
            if (lower.contains("share") || lower.contains("rate") || lower.contains("logout")
                    || lower.equals("home") || lower.equals("loan") || lower.equals("info")
                    || lower.contains("notification") || lower.contains("outlook")
                    || lower.contains("teams") || lower.contains("truecaller")
                    || lower.contains("indrive") || lower.contains("babygenie")
                    || lower.contains("astrotalk")) {
                continue;
            }
            if (!home.tapLabel(label)) {
                continue;
            }
            clicked++;
            sleep(700);
            allowDialogs(Duration.ofSeconds(2));
            if (looksLikeServerError()) {
                home.tapAny(List.of("OK", "Close", "Got it", "Back"));
                home.tapBack();
                continue;
            }
            String childId = parentId + "-" + slug(label);
            scan(childId, "Info — " + label, childId);
            home.tapBack();
            sleep(400);
        }
    }

    private void crawlProductModule(String moduleLabel) {
        String moduleId = slug(moduleLabel);
        LOG.info("=== Crawler module: {} ===", moduleLabel);

        AbstractProductLandingScreen module;
        boolean enteredApply;

        if ("Home Loan".equals(moduleLabel)) {
            HomeLoanLandingScreen homeLoan = new HomeLoanLandingScreen(context);
            module = homeLoan;
            enteredApply = homeLoan.tapApplyNow();
            LOG.info("Home Loan Apply Now result: {}", enteredApply);
        } else {
            module = ProductLandingScreens.forProduct(context, moduleLabel);
            enteredApply = module.tapHomeCardApplyNow();
            LOG.info("{} home-card Apply Now result: {}", moduleLabel, enteredApply);
        }
        allowDialogs(Duration.ofSeconds(2));

        boolean inApplyFlow = module.looksLikeApplyFlow();
        if (inApplyFlow) {
            scan(moduleId + "-apply", moduleLabel + " — Apply", moduleId + "-apply");
            crawlBothOccupationPaths(module, moduleId, moduleLabel);
            return;
        }

        LOG.warn("Module '{}' — Apply/Apply Now did not open apply flow; skipping crawler on Home grid",
                moduleLabel);
        safeBackToHome();
    }

    /**
     * Explicit Salaried then Self-Employed paths with dummy fill through steps.
     * Stops before Submit/Pay via {@link MobileScreenCrawler}.
     */
    private void crawlBothOccupationPaths(AbstractProductLandingScreen module, String moduleId, String moduleLabel) {
        LOG.info("Crawling BOTH occupation paths for {} (max ~2.5min/branch, max 6 Continues)", moduleLabel);
        ensureOnOccupationChooser(module);

        // Branch A — Salaried
        long t0 = System.currentTimeMillis();
        if (module.selectSalaried()) {
            scan(moduleId + "-apply-salaried", moduleLabel + " — Apply → Salaried",
                    moduleId + "-apply-salaried");
            if (looksLikeServerError()) {
                markBranchFailed(moduleLabel, "Salaried", "server error after select");
                forceBackHome();
            } else {
                module.tapContinue();
                sleep(400);
                if (looksLikeServerError()) {
                    markBranchFailed(moduleLabel, "Salaried", "HTTP 500 after Continue");
                } else {
                    scan(moduleId + "-apply-salaried-step-1", moduleLabel + " — Salaried after Continue",
                            moduleId + "-apply-salaried-step-1");
                    advancePastProductLanding(module, moduleId + "-apply-salaried", moduleLabel + " — Salaried");
                    if (!looksLikeServerError()) {
                        module.fillOfficialDetails();
                        sleep(400);
                        scan(moduleId + "-apply-salaried-filled", moduleLabel + " — Salaried (filled)",
                                moduleId + "-apply-salaried-filled");
                        MobileScreenCrawler salariedCrawl = newCrawler();
                        salariedCrawl.crawlFromHere(moduleId + "-apply-salaried",
                                moduleLabel + " — Salaried", true);
                        if (salariedCrawl.sawServerError()) {
                            markBranchFailed(moduleLabel, "Salaried", "server error mid-crawl");
                        }
                        LOG.info("PROGRESS | {} | Salaried DONE | screens={} | {}ms",
                                moduleLabel, salariedCrawl.screensVisited(), System.currentTimeMillis() - t0);
                    } else {
                        markBranchFailed(moduleLabel, "Salaried", "HTTP 500 after landing Apply");
                    }
                }
            }
        } else {
            LOG.warn("Could not select Salaried on occupation chooser for {}", moduleLabel);
        }

        forceBackHome();
        // Re-enter apply for Self-Employed from Home (avoids stuck mid-form)
        if (!looksLikeOccupationChooser()) {
            LOG.info("{} — re-open Apply from Home for Self-Employed branch", moduleLabel);
            if ("Home Loan".equals(moduleLabel)) {
                new HomeLoanLandingScreen(context).tapApplyNow();
            } else {
                module.tapHomeCardApplyNow();
            }
            sleep(500);
            allowDialogs(Duration.ofSeconds(2));
            if (looksLikeServerError()) {
                markBranchFailed(moduleLabel, "Self-Employed", "HTTP 500 re-entering apply");
                forceBackHome();
                LOG.info("PROGRESS | {} | LOAN COMPLETE (Self-Employed skipped after 500)", moduleLabel);
                writeLoanProgressSnippet(moduleLabel);
                return;
            }
        }
        ensureOnOccupationChooser(module);

        // Branch B — Self-Employed
        t0 = System.currentTimeMillis();
        if (module.selectSelfEmployed()) {
            scan(moduleId + "-apply-self-employed", moduleLabel + " — Apply → Self-Employed",
                    moduleId + "-apply-self-employed");
            if (looksLikeServerError()) {
                markBranchFailed(moduleLabel, "Self-Employed", "server error after select");
            } else {
                module.tapContinue();
                sleep(400);
                if (looksLikeServerError()) {
                    markBranchFailed(moduleLabel, "Self-Employed", "HTTP 500 after Continue");
                } else {
                    scan(moduleId + "-apply-self-employed-step-1",
                            moduleLabel + " — Self-Employed after Continue",
                            moduleId + "-apply-self-employed-step-1");
                    advancePastProductLanding(module, moduleId + "-apply-self-employed",
                            moduleLabel + " — Self-Employed");
                    if (!looksLikeServerError()) {
                        module.fillOfficialDetails();
                        sleep(400);
                        scan(moduleId + "-apply-self-employed-filled",
                                moduleLabel + " — Self-Employed (filled)",
                                moduleId + "-apply-self-employed-filled");
                        MobileScreenCrawler selfCrawl = newCrawler();
                        selfCrawl.crawlFromHere(moduleId + "-apply-self-employed",
                                moduleLabel + " — Self-Employed", true);
                        if (selfCrawl.sawServerError()) {
                            markBranchFailed(moduleLabel, "Self-Employed", "server error mid-crawl");
                        }
                        LOG.info("PROGRESS | {} | Self-Employed DONE | screens={} | {}ms",
                                moduleLabel, selfCrawl.screensVisited(), System.currentTimeMillis() - t0);
                    } else {
                        markBranchFailed(moduleLabel, "Self-Employed", "HTTP 500 after landing Apply");
                    }
                }
            }
        } else {
            LOG.warn("Could not select Self-Employed on occupation chooser for {}", moduleLabel);
        }

        forceBackHome();
        LOG.info("PROGRESS | {} | LOAN COMPLETE — moving to next product", moduleLabel);
        writeLoanProgressSnippet(moduleLabel);
    }

    private boolean looksLikeServerError() {
        String src = safeSource().toLowerCase(Locale.ROOT);
        if (src.contains("internal server")
                || src.contains("something went wrong")
                || src.contains("server error")
                || src.contains("try again later")) {
            return true;
        }
        return src.contains("http 500") || src.contains("error 500") || src.contains("status code 500")
                || (src.contains(" 500") && src.contains("error"));
    }

    private void markBranchFailed(String moduleLabel, String branch, String reason) {
        LOG.warn("BRANCH FAILED | {} | {} | {}", moduleLabel, branch, reason);
        System.out.println(">>> BRANCH FAILED: " + moduleLabel + " / " + branch + " — " + reason);
        scan(slug(moduleLabel) + "-" + slug(branch) + "-failed",
                moduleLabel + " — " + branch + " FAILED (" + reason + ")",
                slug(moduleLabel) + "-failed");
        // Dismiss error if present
        home.tapAny(List.of("OK", "Ok", "Close", "Got it", "Back", "Retry"));
        sleep(300);
    }

    /** Aggressive exit to Capital Home — Back spam then Home tab. */
    private void forceBackHome() {
        for (int i = 0; i < 8; i++) {
            if (home.looksLoggedIn() && home.sourceContains("Personal Loan", "Home Loan")) {
                home.openHome();
                sleep(300);
                return;
            }
            home.tapBack();
            sleep(280);
        }
        try {
            context.driver().navigate().back();
        } catch (Exception ignored) {
            //
        }
        sleep(300);
        home.openHome();
        sleep(400);
    }

    private void writeLoanProgressSnippet(String moduleLabel) {
        try {
            Path dir = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y);
            Files.createDirectories(dir);
            Path progress = dir.resolve("loan-progress.txt");
            String id = slug(moduleLabel);
            StringBuilder sb = new StringBuilder();
            sb.append("COMPLETED: ").append(moduleLabel).append(" @ ").append(java.time.Instant.now()).append('\n');
            scannedIds.stream().filter(s -> s.startsWith(id)).forEach(s -> sb.append("  ").append(s).append('\n'));
            sb.append('\n');
            Files.writeString(progress, sb.toString(),
                    java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);
            System.out.println(">>> LOAN DONE: " + moduleLabel + " (screens for this loan logged)");
        } catch (Exception e) {
            LOG.debug("progress write: {}", e.toString());
        }
    }

    /**
     * After occupation Continue, Capital often shows a marketing card with another Apply Now
     * (Interest Rate / Tenure) before real form steps.
     */
    private void advancePastProductLanding(AbstractProductLandingScreen module, String idPrefix, String labelPrefix) {
        String src = safeSource();
        boolean marketing = src.contains("Interest Rate") || src.contains("Loan Tenure")
                || src.contains("For Self-Employed") || src.contains("Processing fees")
                || src.contains("Terms and conditions");
        if (!marketing && !src.contains("Apply Now")) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            if (src.contains("Apply Now") || src.contains("Terms and conditions")
                    || home.sourceContains("Apply Now", "Terms and conditions")) {
                break;
            }
            home.swipeUp();
            sleep(280);
            src = safeSource();
        }
        // Inspector: Apply Now is enabled=false until T&C checkboxes are ticked
        if (module.acceptTermsAndTapApply()) {
            sleep(550);
            allowDialogs(Duration.ofSeconds(2));
            scan(idPrefix + "-landing-apply", labelPrefix + " → Apply Now (landing)",
                    idPrefix + "-landing-apply");
        } else {
            LOG.warn("Marketing landing Apply Now not tappable on {}", labelPrefix);
        }
    }

    /**
     * Resume from current device state (no pm clear / no full re-login).
     * Default order: Personal Loan → Home Loan → LAP → Gold → Credit Card
     * (set a11y.crawl.include.home.loan=false to skip Home Loan).
     */
    public void deepScanAllProductsBothOccupationsFromCurrent() {
        A11yScanSession.clear();
        scannedIds.clear();
        allowDialogs(Duration.ofSeconds(3));
        forceBackHome();
        scan("home", "Home (resume)", "home");

        try {
            crawlInfoFully(context.config().getInt("a11y.deep.max.screens", 200));
        } catch (Exception e) {
            LOG.warn("Info crawl on resume failed: {}", e.toString());
            forceBackHome();
        }

        List<String> modules = context.config().getBoolean("a11y.crawl.include.home.loan", true)
                ? PRODUCT_MODULES
                : REMAINING_PRODUCT_MODULES;

        for (String moduleLabel : modules) {
            try {
                LOG.info("======== ALL-PRODUCTS crawl: {} ========", moduleLabel);
                System.out.println(">>> STARTING LOAN: " + moduleLabel);
                forceBackHome();
                sleep(350);
                if (looksLikeServerError()) {
                    markBranchFailed(moduleLabel, "pre-entry", "server error still on screen");
                    forceBackHome();
                    continue;
                }
                crawlProductModule(moduleLabel);
                System.out.println(">>> FINISHED LOAN: " + moduleLabel);
            } catch (org.openqa.selenium.NoSuchSessionException e) {
                LOG.error("Appium session dead during {} — aborting remaining products: {}",
                        moduleLabel, e.getMessage());
                System.out.println(">>> SESSION DEAD at " + moduleLabel + " — stopping crawl");
                writeOccupationScreenReport();
                writeLoanCrawlSummary(modules);
                return;
            } catch (Exception e) {
                if (e.getMessage() != null && e.getMessage().contains("session is either terminated")) {
                    LOG.error("Appium session dead during {} — aborting remaining products", moduleLabel);
                    System.out.println(">>> SESSION DEAD at " + moduleLabel + " — stopping crawl");
                    writeOccupationScreenReport();
                    writeLoanCrawlSummary(modules);
                    return;
                }
                LOG.warn("ALL-PRODUCTS crawl failed for {}: {}", moduleLabel, e.toString());
                System.out.println(">>> FAILED LOAN: " + moduleLabel + " — " + e.getMessage());
                try {
                    forceBackHome();
                } catch (Exception ignored) {
                    LOG.warn("Could not return home after failure — aborting");
                    writeOccupationScreenReport();
                    writeLoanCrawlSummary(modules);
                    return;
                }
            }
        }

        home.openHome();
        sleep(400);
        scan("home-final", "Home (after all-product occupation crawl)", "home");
        writeOccupationScreenReport();
        writeLoanCrawlSummary(modules);
    }

    /** @deprecated prefer {@link #deepScanAllProductsBothOccupationsFromCurrent()} */
    public void deepScanHomeLoanBothOccupationsFromCurrent() {
        deepScanAllProductsBothOccupationsFromCurrent();
    }

    private void writeOccupationScreenReport() {
        try {
            Path dir = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y);
            Files.createDirectories(dir);
            Path list = dir.resolve("all-products-occupation-screens.txt");
            StringBuilder sb = new StringBuilder();
            sb.append("Generated screens (").append(scannedIds.size()).append(")\n\n");
            for (String moduleLabel : PRODUCT_MODULES) {
                String id = slug(moduleLabel);
                sb.append("=== ").append(moduleLabel).append(" ===\n");
                sb.append("Salaried:\n");
                scannedIds.stream()
                        .filter(s -> s.startsWith(id) && s.contains("salaried"))
                        .forEach(s -> sb.append("  - ").append(s).append('\n'));
                sb.append("Self-Employed:\n");
                scannedIds.stream()
                        .filter(s -> s.startsWith(id) && s.contains("self-employed"))
                        .forEach(s -> sb.append("  - ").append(s).append('\n'));
                sb.append("Other:\n");
                scannedIds.stream()
                        .filter(s -> s.startsWith(id) && !s.contains("salaried") && !s.contains("self-employed"))
                        .forEach(s -> sb.append("  - ").append(s).append('\n'));
                sb.append('\n');
            }
            sb.append("ALL:\n");
            scannedIds.forEach(id -> sb.append("  - ").append(id).append('\n'));
            Files.writeString(list, sb.toString());
            Path legacy = dir.resolve("home-loan-occupation-screens.txt");
            Files.writeString(legacy, sb.toString());
            LOG.info("Wrote per-loan screen lists to {}", list.toAbsolutePath());
        } catch (Exception e) {
            LOG.warn("Could not write screens list: {}", e.toString());
        }
    }

    private boolean looksLikeOccupationChooser() {
        String src = safeSource();
        return src.contains("Tell us about your work")
                || src.contains("Select your occupation")
                || (src.contains("Salaried") && src.contains("Self-Employed"));
    }

    private void ensureOnOccupationChooser(AbstractProductLandingScreen module) {
        if (looksLikeOccupationChooser()) {
            return;
        }
        for (int i = 0; i < 6 && !looksLikeOccupationChooser(); i++) {
            module.tapBack();
            sleep(350);
        }
    }

    private void backToOccupationChooser(AbstractProductLandingScreen module) {
        for (int i = 0; i < 10; i++) {
            if (looksLikeOccupationChooser()) {
                return;
            }
            module.tapBack();
            sleep(400);
        }
        LOG.warn("Could not return to occupation chooser after path");
    }

    private MobileScreenCrawler newCrawler() {
        return new MobileScreenCrawler(context.driver(), context.config(),
                (id, label) -> scan(id, label, id));
    }

    private void openScanBack(String id, String label, String route, Runnable open) {
        try {
            home.openHome();
            sleep(500);
            open.run();
            sleep(1000);
            allowDialogs(Duration.ofSeconds(3));
            scan(id, label, route);
            home.tapBack();
            sleep(700);
            home.openHome();
        } catch (Exception e) {
            LOG.warn("{} scan failed: {}", label, e.toString());
            safeBackToHome();
        }
    }

    private void safeBackToHome() {
        for (int i = 0; i < 10; i++) {
            if (home.looksLoggedIn() && home.sourceContains("Personal Loan", "Home Loan", "Credit Card")) {
                return;
            }
            home.tapBack();
            sleep(400);
            home.openHome();
            sleep(500);
            if (home.looksLoggedIn()) {
                return;
            }
        }
    }

    private void enterOtp(String mobile) {
        String configured = context.config().get("capital.test.otp", "");
        if (configured != null && !configured.isBlank()) {
            LOG.info("Using OTP from capital.test.otp");
            otpPage.enterOtp(configured.trim());
            otpPage.tapVerifyIfPresent();
            return;
        }
        long waitMs = context.config().getLong("timeout.otp.wait.ms", 120_000);
        LOG.info("Waiting up to {}s for OTP in device notifications for {}", waitMs / 1000, mobile);
        String otp = NotificationOtpReader.poll(
                context.driver(), context.config(), mobile, Duration.ofMillis(waitMs),
                otpPage.expectedLength(), home::looksLoggedIn);
        if (otp != null && !otp.isBlank()) {
            otpPage.enterOtp(otp);
            sleep(2500);
            otpPage.tapVerifyIfPresent();
            sleep(5000);
            return;
        }
        if (home.looksLoggedIn()) {
            LOG.info("Already past OTP screen (app may have auto-consumed SMS)");
            return;
        }
        LOG.warn("OTP not found in notifications — waiting for manual entry");
        System.out.println(">>> Type OTP on device for " + mobile + " <<<");
        otpPage.waitUntilGone(Duration.ofMillis(waitMs));
    }

    private void scan(String id, String label, String route) {
        String src = safeSource();
        if (!src.contains("com.example.airpay_capital")) {
            LOG.warn("Skip scan {} — Capital not in foreground; reopening app", id);
            bringCapitalToForeground();
            src = safeSource();
            if (!src.contains("com.example.airpay_capital")) {
                return;
            }
        }
        String unique = id;
        int n = 2;
        while (!scannedIds.add(unique)) {
            unique = id + "-" + n++;
        }
        allowDialogs(Duration.ofSeconds(2));
        A11yPageRef ref = A11yPageRef.screen(unique, "capital", label, route);
        A11yPageScanResult result = AndroidA11yScanner.scan(context.driver(), context.config(), ref);
        A11yScanSession.add(result);
    }

    private void writeLoanCrawlSummary(List<String> modules) {
        try {
            Path summary = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y).resolve("loan-crawl-summary.txt");
            Files.createDirectories(summary.getParent());
            StringBuilder sb = new StringBuilder();
            sb.append("Capital loan crawl summary (from Home, no login)\n");
            sb.append("Device: ").append(context.config().get("android.udid", "")).append('\n');
            sb.append("fresh.start=").append(context.config().get("capital.fresh.start", "false")).append('\n');
            sb.append("Screens scanned: ").append(scannedIds.size()).append("\n\n");
            for (String moduleLabel : modules) {
                String id = slug(moduleLabel);
                long salaried = scannedIds.stream()
                        .filter(s -> s.startsWith(id) && s.contains("salaried")).count();
                long selfEmp = scannedIds.stream()
                        .filter(s -> s.startsWith(id) && s.contains("self-employed")).count();
                long failed = scannedIds.stream()
                        .filter(s -> s.startsWith(id) && s.contains("failed")).count();
                sb.append(moduleLabel).append('\n');
                sb.append("  Salaried screens: ").append(salaried).append('\n');
                sb.append("  Self-Employed screens: ").append(selfEmp).append('\n');
                sb.append("  Failed markers: ").append(failed).append('\n');
                scannedIds.stream().filter(s -> s.startsWith(id))
                        .forEach(s -> sb.append("    - ").append(s).append('\n'));
                sb.append('\n');
            }
            sb.append("ALL screen ids:\n");
            scannedIds.forEach(s -> sb.append("  - ").append(s).append('\n'));
            Files.writeString(summary, sb.toString());
            LOG.info("Wrote loan crawl summary to {}", summary.toAbsolutePath());
            System.out.println(">>> SUMMARY: " + summary.toAbsolutePath());
        } catch (Exception e) {
            LOG.warn("Could not write loan-crawl-summary.txt: {}", e.toString());
        }
    }

    private void bringCapitalToForeground() {
        String pkg = context.config().require("capital.app.package");
        String activity = context.config().require("capital.app.activity");
        String udid = context.config().get("android.udid", "");
        for (int i = 0; i < 5; i++) {
            String src = safeSource();
            boolean onLauncher = src.contains("com.sec.android.app.launcher")
                    && !src.contains("com.example.airpay_capital.MainActivity");
            boolean inApp = src.contains("com.example.airpay_capital")
                    && (src.contains("Personal Loan") || src.contains("Login With Mobile")
                    || src.contains("Tell us about your work") || src.contains("Send OTP")
                    || src.contains("Home Loan"));
            if (inApp) {
                return;
            }
            if (onLauncher) {
                home.tapLabel("Airpay Capital");
                sleep(1500);
            }
            try {
                context.driver().activateApp(pkg);
            } catch (Exception e) {
                LOG.debug("activateApp: {}", e.toString());
            }
            com.airpay.mobile.utilities.android.AdbCommands.shell(udid, "am", "start", "-n",
                    pkg + "/" + activity);
            sleep(1800);
            allowDialogs(Duration.ofSeconds(3));
        }
    }

    private void allowDialogs(Duration timeout) {
        try {
            PermissionDialogHandler.allowAll(context.driver(), timeout);
        } catch (Exception e) {
            LOG.debug("Permission dialog pass skipped: {}", e.toString());
        }
    }

    private String safeSource() {
        try {
            return context.driver().getPageSource();
        } catch (Exception e) {
            return "(page source unavailable: " + e.getMessage() + ")";
        }
    }

    private static String head(String xml) {
        if (xml == null) {
            return "";
        }
        String t = xml.replaceAll("\\s+", " ").trim();
        return t.length() > 400 ? t.substring(0, 400) : t;
    }

    private static String slug(String label) {
        String s = label.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-");
        return s.isBlank() ? "screen" : s.replaceAll("^-+|-+$", "");
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
