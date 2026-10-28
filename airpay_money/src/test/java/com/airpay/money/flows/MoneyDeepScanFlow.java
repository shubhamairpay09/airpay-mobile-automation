package com.airpay.money.flows;

import com.airpay.money.data.MoneyModuleInventory;
import com.airpay.money.data.MoneyTestData;
import com.airpay.money.screens.auth.AccountSetupScreen;
import com.airpay.money.screens.auth.BiometricScreen;
import com.airpay.money.screens.auth.GooglePhonePickerScreen;
import com.airpay.money.screens.auth.GuestLandingScreen;
import com.airpay.money.screens.auth.LoginPopupScreen;
import com.airpay.money.screens.auth.MobileLoginScreen;
import com.airpay.money.screens.auth.OtpScreen;
import com.airpay.money.screens.auth.PinUnlockScreen;
import com.airpay.money.screens.auth.WelcomeScreen;
import com.airpay.money.screens.calculators.CalculatorsHubScreen;
import com.airpay.money.screens.home.HomeScreen;
import com.airpay.money.screens.product.ProductsHubScreen;
import com.airpay.money.screens.legal.LegalWebViewScreen;
import com.airpay.money.screens.menu.DrawerMenuScreen;
import com.airpay.money.screens.menu.MenuHubScreen;
import com.airpay.money.screens.product.InvestHubScreen;
import com.airpay.money.screens.profile.ProfileSettingsScreen;
import com.airpay.money.screens.product.LoanApplyScreen;
import com.airpay.money.screens.terms.TermsAndConditionsScreen;
import com.airpay.mobile.utilities.accessibility.A11yPageRef;
import com.airpay.mobile.utilities.accessibility.A11yPageScanResult;
import com.airpay.mobile.utilities.accessibility.A11yScanSession;
import com.airpay.mobile.utilities.accessibility.AndroidA11yScanner;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.android.PermissionDialogHandler;
import com.airpay.mobile.utilities.config.ProjectPaths;
import com.airpay.mobile.utilities.context.TestContext;
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

/** Login (Privacy + Terms) → hamburger modules → Login popup → OTP → deep crawl. */
public class MoneyDeepScanFlow {

    private static final Logger LOG = LogManager.getLogger(MoneyDeepScanFlow.class);
    private static final String PKG = "com.finmapp.fininfinity";

    private final TestContext context;
    private final GuestLandingScreen guest;
    private final TermsAndConditionsScreen terms;
    private final LoginPopupScreen loginPopup;
    private final LegalWebViewScreen legalWeb;
    private final GooglePhonePickerScreen googlePicker;
    private final WelcomeScreen welcome;
    private final MobileLoginScreen mobileLogin;
    private final OtpScreen otpScreen;
    private final AccountSetupScreen accountSetup;
    private final HomeScreen home;
    private final DrawerMenuScreen drawer;
    private final MenuHubScreen menu;
    private final InvestHubScreen invest;
    private final LoanApplyScreen loanApply;
    private final Set<String> scannedIds = new LinkedHashSet<>();
    private boolean holding;
    private boolean fastResume;
    private int manualGates;
    private long coverageDeadline;

    public MoneyDeepScanFlow(TestContext context) {
        this.context = context;
        this.guest = new GuestLandingScreen(context);
        this.terms = new TermsAndConditionsScreen(context);
        this.loginPopup = new LoginPopupScreen(context);
        this.legalWeb = new LegalWebViewScreen(context);
        this.googlePicker = new GooglePhonePickerScreen(context);
        this.welcome = new WelcomeScreen(context);
        this.mobileLogin = new MobileLoginScreen(context);
        this.otpScreen = new OtpScreen(context);
        this.accountSetup = new AccountSetupScreen(context);
        this.home = new HomeScreen(context);
        this.drawer = new DrawerMenuScreen(context);
        this.menu = new MenuHubScreen(context);
        this.invest = new InvestHubScreen(context);
        this.loanApply = new LoanApplyScreen(context);
    }

    /** Scan whatever is on screen now. Does not tap. */
    public void scanCurrentOnly(String id, String label) {
        A11yScanSession.clear();
        scannedIds.clear();
        scanNoTap(id, label, id);
        writeScreenList();
        LOG.info("Captured current screen {} — {} pages", id, scannedIds.size());
    }

    private void scanNoTap(String id, String label, String route) {
        String unique = id;
        int n = 2;
        while (!scannedIds.add(unique)) {
            unique = id + "-" + n++;
        }
        A11yPageRef ref = A11yPageRef.screen(unique, "money", label, route);
        A11yPageScanResult result = AndroidA11yScanner.scan(context.driver(), context.config(), ref);
        A11yScanSession.add(result);
    }

    /** A11y from UiAutomator XML dumps (no taps). */
    public void scanHierarchyDump(Path xml, String id, String label) {
        try {
            if (A11yScanSession.results().isEmpty()) {
                scannedIds.clear();
            }
            String src = Files.readString(xml);
            A11yPageRef ref = A11yPageRef.screen(id, "money", label, id);
            A11yPageScanResult result = AndroidA11yScanner.scanHierarchy(src, context.config(), ref);
            A11yScanSession.add(result);
            scannedIds.add(id);
            LOG.info("Dump a11y {} score={} violations={}", id, result.score(), result.violationsCount());
        } catch (Exception e) {
            throw new IllegalStateException("Failed to scan dump " + xml, e);
        }
    }

    public void scanFromLoginThroughAllModules() {
        A11yScanSession.clear();
        scannedIds.clear();
        allowDialogs(Duration.ofSeconds(12));
        bringMoneyToForeground();
        googlePicker.dismissIfShown();
        allowDialogs(Duration.ofSeconds(5));
        googlePicker.dismissIfShown();

        String mobile = context.config().require("money.test.mobile");

        if (accountSetup.isDisplayed()) {
            completeAccountSetupThenScan();
            return;
        }

        if (loanApply.isDisplayed()) {
            completeLoanApplyThenScan();
            return;
        }

        if (home.looksLoggedIn() || !onLoginForm()) {
            LOG.info("Already past login — capture the current screen, then remaining modules");
            scan("live-current", "Current screen", "current");
            saveCurrentDump();
            captureSkipThenOk();
            resumeRemainingModules();
            return;
        }

        if (otpScreen.isDisplayed()) {
            LOG.info("OTP screen — waiting after OTP is entered");
            scan("otp", "OTP verification", "otp");
            otpScreen.waitUntilGone(Duration.ofSeconds(90));
            allowDialogs(Duration.ofSeconds(8));
            captureSkipThenOk();
            dismissPostLoginGates();
            if (accountSetup.isDisplayed()) {
                completeAccountSetupThenScan();
                return;
            }
            if (loanApply.isDisplayed()) {
                completeLoanApplyThenScan();
                return;
            }
            if (home.looksLoggedIn()) {
                scan("home", "Home", "home");
                fullModuleDeepScan();
                return;
            }
            throw new IllegalStateException("Still not on home after OTP. Page head: " + head(safeSource()));
        }

        if (mobileLogin.waitUntilDisplayed(Duration.ofSeconds(8))
                && sourceContainsLoginScreen()) {
            LOG.info("Login screen — Privacy Policy, Terms & Conditions, then hamburger / Login with mobile");
            scanLoginPrivacyTermsThenHamburger(mobile);
        } else if (terms.isDisplayed() || guest.isDisplayed()) {
            scan("terms", "Terms & Conditions", "terms");
            scanHamburgerDrawerModules();
            openHeaderLoginPopupThenContinue(mobile);
        } else if (welcome.waitForLoginEntry(Duration.ofSeconds(10))) {
            scan("welcome", "Welcome", "welcome");
            welcome.tapLoginWithMobile();
            sleep(1000);
            performLogin(mobile);
        } else {
            throw new IllegalStateException("Money app not on login, terms, setup, or home. Page head: "
                    + head(safeSource()));
        }

        dismissPostLoginGates();
        if (accountSetup.isDisplayed()) {
            completeAccountSetupThenScan();
            return;
        }
        if (home.looksLoggedIn()) {
            scan("home", "Home", "home");
            fullModuleDeepScan();
        } else {
            scan("post-login", "Post-login screen", "post-login");
            dismissPostLoginGates();
            if (!onLoginForm()) {
                scan("home", "Home", "home");
                fullModuleDeepScan();
            }
        }
    }

    private boolean onLoginForm() {
        String src = safeSource();
        return src.contains("Sign Up or Login to Continue")
                || src.contains("Login with Mobile Number")
                || src.contains("Enter the OTP")
                || src.contains("OTP sent")
                || src.contains("Verify OTP");
    }

    private boolean sourceContainsLoginScreen() {
        String src = safeSource();
        return src.contains("Login with Mobile Number")
                || src.contains("Sign Up or Login to Continue")
                || src.contains("Privacy Policy");
    }

    private void scanLoginPrivacyTermsThenHamburger(String mobile) {
        scan("login", "Login", "login");
        googlePicker.dismissIfShown();

        LOG.info("Open Privacy Policy from login");
        mobileLogin.openPrivacyPolicy();
        sleep(1000);
        scan("privacy-policy", "Privacy Policy", "login/privacy");
        for (int i = 1; i <= 3; i++) {
            home.swipeUp();
            sleep(280);
            scan("privacy-policy-scroll-" + i, "Privacy Policy scroll " + i, "login/privacy");
        }
        legalWeb.tapHeaderBack();
        sleep(700);
        googlePicker.dismissIfShown();

        LOG.info("Open Terms & Conditions from login");
        mobileLogin.openTermsAndConditions();
        sleep(1000);
        scan("terms-and-conditions", "Terms & Conditions", "login/terms");
        for (int i = 1; i <= 3; i++) {
            home.swipeUp();
            sleep(280);
            scan("terms-and-conditions-scroll-" + i, "Terms & Conditions scroll " + i, "login/terms");
        }

        legalWeb.tapHeaderBack();
        sleep(700);
        googlePicker.dismissIfShown();
        scan("login-after-legal", "Login after Privacy and Terms", "login");
        performLogin(mobile);
    }

    private void openHeaderLoginPopupThenContinue(String mobile) {
        LOG.info("Header Login → popup crawl → X → Back → Login with Mobile Number");
        if (terms.hasLogin() || guest.hasHeaderLogin()) {
            terms.tapLogin();
        } else {
            guest.tapHeaderLogin();
        }
        sleep(1000);
        if (loginPopup.isDisplayed() || loginPopup.waitUntilDisplayed(Duration.ofSeconds(8))) {
            scan("login-popup", "Login / Sign up popup", "login/popup");
            crawlCurrentBranch("login-popup");
            loginPopup.tapClose();
            sleep(500);
        }
        terms.tapBackIcon();
        sleep(700);
        googlePicker.dismissIfShown();
        if (mobileLogin.waitUntilDisplayed(Duration.ofSeconds(8))) {
            mobileLogin.tapLoginWithMobileNumber();
            sleep(800);
        } else {
            welcome.tapLoginWithMobile();
            sleep(800);
        }
        performLogin(mobile);
    }

    private void scanGuestLandingFirst() {
        if (guest.isTermsOrGuestContentScreen() || terms.isDisplayed()) {
            scan("terms-and-conditions", "Terms & Conditions", "terms");
            for (int i = 1; i <= 4; i++) {
                home.swipeUp();
                sleep(300);
                scan("terms-and-conditions-scroll-" + i, "Terms scroll " + i, "terms");
            }
        } else {
            scan("guest-landing", "Guest landing", "guest");
        }
    }

    /** Menu = UiSelector().text("Menu") — drawer: Financial Health Check, Resources, etc. */
    private void scanHamburgerDrawerModules() {
        int maxScreens = context.config().getInt("a11y.deep.max.screens", 250);
        LOG.info("=== Menu drawer (UiSelector text=Menu) ===");

        if (!guest.tapHamburger()) {
            LOG.warn("Menu button not found — skipping pre-login drawer scan");
            return;
        }
        sleep(700);
        scan("menu-drawer", "Menu drawer", "menu/drawer");

        MoneyModuleInventory.Module cta = MoneyModuleInventory.startFinancialHealthCheck();
        crawlDrawerItem(cta, maxScreens, false);
        if (!drawer.expandFinancialHealthCheck()) {
            LOG.info("Financial Health Check accordion not found");
        } else {
            sleep(500);
            scan("financial-health-check", "Financial Health Check", "hamburger/fhc");
            for (MoneyModuleInventory.Module item : MoneyModuleInventory.financialHealthCheckItems()) {
                if (scannedIds.size() >= maxScreens) {
                    break;
                }
                crawlDrawerItem(item, maxScreens, true);
            }
        }

        if (drawer.expandResources()) {
            sleep(500);
            scan("resources", "Resources", "hamburger/resources");
            for (MoneyModuleInventory.Module child : MoneyModuleInventory.resourcesChildren()) {
                if (scannedIds.size() >= maxScreens) {
                    break;
                }
                crawlDrawerItem(child, maxScreens, true);
            }
        }

        guest.closeDrawer();
        sleep(500);
        scan("guest-after-drawer", "Guest screen after drawer", "guest:after-drawer");
    }

    private void crawlDrawerItem(MoneyModuleInventory.Module item, int maxScreens, boolean reopenAccordion) {
        try {
            reopenDrawerIfNeeded();
            if (reopenAccordion) {
                String id = item.id();
                if (id.startsWith("what-") || id.startsWith("how-") || "start-fhc".equals(id)) {
                    drawer.expandFinancialHealthCheck();
                } else {
                    drawer.expandResources();
                }
                sleep(350);
            }
            if (!drawer.tapSection(item.entryLabels()[0]) && item.entryLabels().length > 1) {
                boolean opened = false;
                for (String label : item.entryLabels()) {
                    if (drawer.tapSection(label)) {
                        opened = true;
                        break;
                    }
                }
                if (!opened) {
                    LOG.info("Drawer item not found: {}", item.label());
                    return;
                }
            } else if (!drawer.tapSection(item.entryLabels()[0])) {
                LOG.info("Drawer item not found: {}", item.label());
                return;
            }
            sleep(700);
            scan(item.id(), item.label(), item.id());
            for (int i = 1; i <= 3; i++) {
                home.swipeUp();
                sleep(280);
                scan(item.id() + "-scroll-" + i, item.label() + " scroll " + i, item.id());
            }
            crawlCurrentBranch(item.id());
            home.tapBack();
            sleep(400);
            reopenDrawerIfNeeded();
        } catch (Exception e) {
            LOG.warn("Drawer item {} failed: {}", item.label(), e.toString());
            home.tapBack();
            reopenDrawerIfNeeded();
        }
    }

    private void crawlDrawerSection(MoneyModuleInventory.Module section, int maxScreens) {
        try {
            if (!drawer.tapSection(section.entryLabels()[0])) {
                LOG.info("Drawer section not found: {}", section.label());
                return;
            }
            sleep(700);
            scan(section.id(), "Drawer — " + section.label(), section.id());
            for (int i = 1; i <= 3; i++) {
                home.swipeUp();
                sleep(280);
                scan(section.id() + "-scroll-" + i, section.label() + " scroll " + i, section.id());
            }
            if ("drawer-health-cta".equals(section.id())) {
                crawlCurrentBranch(section.id());
                home.tapBack();
                sleep(400);
                reopenDrawerIfNeeded();
            }
        } catch (Exception e) {
            LOG.warn("Drawer section {} failed: {}", section.label(), e.toString());
            home.tapBack();
            reopenDrawerIfNeeded();
        }
    }

    private void crawlDrawerChild(MoneyModuleInventory.Module child, int maxScreens) {
        try {
            if (!drawer.tapSection(child.entryLabels()[0])) {
                return;
            }
            sleep(700);
            scan(child.id(), "Resources / " + child.label(), child.id());
            for (int i = 1; i <= 3; i++) {
                home.swipeUp();
                sleep(280);
                scan(child.id() + "-scroll-" + i, child.label() + " scroll " + i, child.id());
            }
            crawlCurrentBranch(child.id());
            home.tapBack();
            sleep(400);
            reopenDrawerIfNeeded();
            drawer.expandResources();
            sleep(400);
        } catch (Exception e) {
            LOG.warn("Drawer child {} failed: {}", child.label(), e.toString());
            home.tapBack();
            reopenDrawerIfNeeded();
        }
    }

    private void exploreDrawerChildren(String parentId, int maxClicks, int maxScreens) {
        List<String> labels;
        try {
            labels = drawer.visibleDrawerLabels();
        } catch (Exception e) {
            LOG.warn("Drawer child scan skipped: {}", e.toString());
            return;
        }
        int clicked = 0;
        for (String label : labels) {
            if (clicked >= maxClicks || scannedIds.size() >= maxScreens) {
                break;
            }
            String lower = label.toLowerCase(Locale.ROOT);
            if (lower.contains("logout") || lower.equals("menu") || lower.equals("login")) {
                continue;
            }
            if (!drawer.tapSection(label)) {
                continue;
            }
            clicked++;
            sleep(650);
            String childId = parentId + "-" + slug(label);
            scan(childId, parentId + " / " + label, childId);
            crawlCurrentBranch(childId);
            home.tapBack();
            sleep(350);
            reopenDrawerIfNeeded();
        }
    }

    private void reopenDrawerIfNeeded() {
        if (!drawer.isOpen()) {
            guest.tapHamburger();
            sleep(500);
        }
    }

    private void completeAccountSetupThenScan() {
        LOG.info("Account setup after OTP — fill dummy profile and continue");
        scan("account-setup", "Setup your account", "setup");
        accountSetup.fillAndSubmit();
        for (int i = 0; i < 8; i++) {
            allowDialogs(Duration.ofSeconds(5));
            dismissPostLoginGates();
            if (accountSetup.isDisplayed()) {
                LOG.info("Still on account setup — retry fill/submit ({})", i + 1);
                accountSetup.fillAndSubmit();
                continue;
            }
            if (home.looksLoggedIn()) {
                scan("home", "Home", "home");
                fullModuleDeepScan();
                return;
            }
            scan("post-setup-" + (i + 1), "After account setup", "setup/next");
            sleep(1200);
        }
        throw new IllegalStateException("Home not visible after account setup. Page head: " + head(safeSource()));
    }

    private void completeLoanApplyThenScan() {
        LOG.info("Loan apply — tick declaration checkboxes and Apply for Loan");
        scan("loan-apply", "Apply for Loan", "loan/apply");
        loanApply.acceptDeclarationsAndApply();
        loanApply.enterMobileIfPresent(context.config().get("money.test.mobile", "7906862513"));
        allowDialogs(Duration.ofSeconds(8));
        dismissPostLoginGates();
        sleep(1000);
        scan("loan-apply-next", "After Apply for Loan", "loan/apply/next");
        crawlCurrentBranch("loan-apply");
        if (home.looksLoggedIn()) {
            scan("home", "Home", "home");
            fullModuleDeepScan();
            return;
        }
        fullModuleDeepScan();
    }

    private void performLogin(String mobile) {
        scan("login-submit", "Login submit", "login");
        mobileLogin.enterMobile(mobile);
        mobileLogin.tapLoginWithMobileNumber();
        otpScreen.waitUntilDisplayed(Duration.ofSeconds(25));
        scan("otp", "OTP verification", "otp");
        enterOtp(mobile);
        sleep(2000);
    }

    private void fullModuleDeepScan() {
        fastResume = true;
        coverageDeadline = System.currentTimeMillis() + Duration.ofMinutes(60).toMillis();
        LOG.info("One-by-one coverage: Profile → each Products module → FinSight → Calculators");
        keepMoneyForeground();
        leaveInnerScreen();
        coverProfileSettings();
        if (timeLeft()) {
            coverProductsDeepOrdered();
        }
        // FinSight only after Products deep is stable (paused by design).
        publishReport();
        writeScreenList();
        LOG.info("Coverage done — {} screens this run", scannedIds.size());
    }

    /**
     * Bat / resume: PIN → Products deep (Investments → Insurance → Loans → Tax) in order.
     * Mutual Funds is full depth; other modules landing+CTAs+tabs. FinSight deferred.
     * Flow: docs/analysis/money-products-deep-scan-flow.md
     */
    private void resumeRemainingModules() {
        fastResume = true;
        coverageDeadline = System.currentTimeMillis() + Duration.ofMinutes(90).toMillis();
        Set<String> coveredIds = loadExistingPageIds();
        LOG.info("Products DEEP residual — scorecard has {} pages (FinSight deferred)",
                coveredIds.size());
        keepMoneyForeground();
        unlockPinIfPresent();
        leaveInnerScreen();
        coverProductsDeepOrdered();
        publishReport();
        writeScreenList();
        LOG.info("Products DEEP residual done — {} screens this run", scannedIds.size());
    }

    /** Ordered Products deep walk — see MoneyProductsDeepWalker + flow doc. */
    private void coverProductsDeepOrdered() {
        Set<String> covered = loadExistingPageIds();
        covered.addAll(scannedIds);
        new MoneyProductsDeepWalker(
                context,
                this::scan,
                this::publishReport,
                this::timeLeft,
                this::keepMoneyForeground,
                this::unlockPinIfPresent,
                covered
        ).run();
        scannedIds.addAll(covered);
    }

    private static final String[] PRODUCT_MODULES = {
            "Mutual Funds", "NPS", "Digital Gold", "Fixed Deposit",
            "US Stocks", "Indian Stocks",
            "2 Wheeler", "4 Wheeler", "Health", "Life",
            "Gold Loan", "Credit Cards", "Personal Loan", "Home Loan",
            "Loan Against Property", "Tax Filing & Planning", "Tax Filing"
    };

    /** Prefer activateApp over Home/Back so the PIN gate does not reappear. */
    private void keepMoneyForeground() {
        try {
            String pkg = safePackage();
            if (!PKG.equals(pkg)) {
                LOG.info("Money was not in foreground (pkg={}) — activating without restart", pkg);
                context.driver().activateApp(PKG);
                sleep(fastResume ? 400 : 700);
            }
            unlockPinIfPresent();
        } catch (Exception e) {
            LOG.warn("keepMoneyForeground: {}", e.toString());
        }
    }

    /**
     * Fast PIN unlock: one page-source parse of the pad, then quick taps.
     * Avoids the old per-digit pageSource + 280ms sleeps that took 1–2+ minutes.
     */
    private void unlockPinIfPresent() {
        if (!isPinPad()) {
            return;
        }
        LOG.info("PIN screen — auto-entering configured PIN (app stays in foreground)");
        typeConfiguredPin();
        sleep(fastResume ? 250 : 400);
        if (isPinPad()) {
            // One quick retry only — do not burn 15s of pageSource polls.
            typeConfiguredPin();
            sleep(fastResume ? 250 : 400);
        }
        if (!isPinPad()) {
            try {
                new BiometricScreen(context).dismissIfShown();
            } catch (Exception ignored) {
                // optional
            }
            captureSkipThenOk();
        }
    }

    private void saveCurrentDump() {
        try {
            Path dir = ProjectPaths.reportsRoot().resolve("money").resolve("manual-capture");
            Files.createDirectories(dir);
            Files.writeString(dir.resolve("uidump-live-current.xml"), safeSource());
            LOG.info("Saved current screen dump uidump-live-current.xml");
        } catch (Exception e) {
            LOG.warn("Could not save the current screen dump: {}", e.toString());
        }
    }

    /** Products, then FinSight, then Calculators. Each screen once. Stops within 27 minutes. */
    private void fastOrderedCoverage() {
        coverageDeadline = System.currentTimeMillis() + Duration.ofMinutes(27).toMillis();
        LOG.info("Ordered coverage: Products, then FinSight, then Calculators");
        leaveInnerScreen();
        coverProducts();
        if (timeLeft()) {
            coverFinSight();
        }
        if (timeLeft()) {
            coverCalculators();
        }
        publishReport();
        writeScreenList();
        LOG.info("Ordered coverage done — {} screens this run", scannedIds.size());
    }

    private boolean timeLeft() {
        return System.currentTimeMillis() < coverageDeadline;
    }

    private void leaveInnerScreen() {
        keepMoneyForeground();
        for (int i = 0; i < 8; i++) {
            if (onTabBar()) {
                return;
            }
            if (isPinPad()) {
                unlockPinIfPresent();
                continue;
            }
            dismissOverlayIfAny();
            if (home.tapBack()) {
                sleep(fastResume ? 150 : 280);
                if (!PKG.equals(safePackage())) {
                    keepMoneyForeground();
                }
                continue;
            }
            // Prefer close affordances — system Back often lands on the launcher → PIN gate.
            if (home.tapLabel("Close") || home.tapLabel("Dismiss") || home.tapLabel("Cancel")
                    || home.tapLabel("Not now") || home.tapLabel("Skip for now")) {
                sleep(fastResume ? 150 : 280);
                continue;
            }
            // Jump to Products tab instead of system Back.
            try {
                home.openProductsTab();
            } catch (Exception ignored) {
                AdbCommands.shell(context.config().get("android.udid", ""), "input", "tap", "180", "2200");
            }
            sleep(fastResume ? 200 : 350);
            keepMoneyForeground();
            if (onTabBar() || softProductsHub()) {
                return;
            }
        }
        if (!onTabBar()) {
            LOG.warn("Still not on tab bar after leave — activating Money + Products tab");
            keepMoneyForeground();
            home.openProductsTab();
            sleep(fastResume ? 300 : 500);
        }
    }

    private void dismissOverlayIfAny() {
        String src = safeSource();
        if (src.contains("content-desc=\"Dismiss\"") || src.contains("content-desc=\"Close\"")
                || src.contains("content-desc=\"Cancel\"") || src.contains("content-desc=\"Not now\"")
                || src.contains("content-desc=\"Skip for now\"")) {
            if (home.tapLabel("Dismiss") || home.tapLabel("Close") || home.tapLabel("Cancel")
                    || home.tapLabel("Not now") || home.tapLabel("Skip for now")) {
                sleep(350);
            }
        }
    }

    private boolean onTabBar() {
        String src = safeSource();
        return src.contains("content-desc=\"Products\"")
                && src.contains("content-desc=\"FinSight\"")
                && src.contains("content-desc=\"Calculators\"");
    }

    private void returnToTabs() {
        for (int i = 0; i < 8; i++) {
            if (onTabBar()) {
                return;
            }
            if (isPinPad()) {
                unlockPinIfPresent();
                continue;
            }
            dismissOverlayIfAny();
            if (home.tapBack()) {
                sleep(fastResume ? 150 : 250);
                if (!PKG.equals(safePackage())) {
                    keepMoneyForeground();
                }
                continue;
            }
            if (home.tapLabel("Close") || home.tapLabel("Dismiss") || home.tapLabel("Cancel")) {
                sleep(fastResume ? 150 : 250);
                continue;
            }
            try {
                home.openProductsTab();
            } catch (Exception ignored) {
                AdbCommands.shell(context.config().get("android.udid", ""), "input", "tap", "180", "2200");
            }
            sleep(fastResume ? 200 : 300);
            keepMoneyForeground();
        }
    }

    private String safePackage() {
        try {
            return context.driver().getCurrentPackage();
        } catch (Exception e) {
            return "";
        }
    }

    private void scrollToTop() {
        for (int i = 0; i < 3; i++) {
            adbSwipe(540, 700, 540, 1750);
            sleep(200);
        }
    }

    private void scrollDown() {
        adbSwipe(540, 1750, 540, 700);
        sleep(250);
    }

    private List<String> collectLabels() {
        List<String> labels = new java.util.ArrayList<>(home.allClickableLabels());
        int scrolls = fastResume ? 1 : 4;
        for (int i = 0; i < scrolls; i++) {
            scrollDown();
            for (String extra : home.allClickableLabels()) {
                if (!labels.contains(extra)) {
                    labels.add(extra);
                }
            }
        }
        scrollToTop();
        return labels;
    }

    private void adbSwipe(int x1, int y1, int x2, int y2) {
        AdbCommands.shell(context.config().get("android.udid", ""), "input", "swipe",
                Integer.toString(x1), Integer.toString(y1),
                Integer.toString(x2), Integer.toString(y2), "320");
    }

    private void coverProducts() {
        LOG.info("=== Products and its modules ===");
        ProductsHubScreen products = new ProductsHubScreen(context);
        products.open();
        sleep(500);
        scan("products-hub", "Products", "products");
        home.swipeUp();
        sleep(250);
        scan("products-hub-scroll", "Products scroll", "products");

        String[] modules = {
                "Mutual Funds", "NPS", "Digital Gold", "Fixed Deposit",
                "US Stocks", "Indian Stocks",
                "2 Wheeler", "4 Wheeler", "Health", "Life",
                "Gold Loan", "Credit Cards", "Personal Loan", "Home Loan",
                "Loan Against Property", "Tax Filing & Planning"
        };
        for (String module : modules) {
            if (!timeLeft()) {
                return;
            }
            products.open();
            scrollToTop();
            if (!home.tapProductTile(module)) {
                LOG.info("Products module not opened: {}", module);
                continue;
            }
            sleep(600);
            String id = "products-" + slug(module);
            scan(id, "Products / " + module, id);
            home.swipeUp();
            sleep(200);
            scan(id + "-s", "Products / " + module + " scroll", id);
            returnToTabs();
        }
    }

    /**
     * Profile settings rows, scanned once. Does not change the photo, change the PIN,
     * send a message, submit feedback, or confirm logout.
     */
    private void coverProfileSettings() {
        LOG.info("=== Profile settings ===");
        ProfileSettingsScreen profile = new ProfileSettingsScreen(context);
        if (!profile.open()) {
            LOG.info("Profile settings not opened");
            return;
        }
        scan("profile-settings", "Profile settings", "profile");
        String[] rows = {
                "Personal Details", "Work Details", "Address Details", "Bank Account",
                "Document Details", "KYC Details", "Transaction history", "Contact Us",
                "How can we help you?"
        };
        for (String row : rows) {
            if (!timeLeft()) {
                return;
            }
            if (!profile.settingsDisplayed()) {
                profile.open();
            }
            if (!profile.openRow(row) && !profile.openContaining(row)) {
                LOG.info("Profile row not opened: {}", row);
                continue;
            }
            sleep(500);
            String id = "profile-" + slug(row);
            scan(id, "Profile / " + row, id);
            home.swipeUp();
            sleep(200);
            scan(id + "-s", "Profile / " + row + " scroll", id);
            if ("Transaction history".equals(row)) {
                for (String tab : List.of("Mutual Funds", "Digital Gold", "NPS", "All", "One Time", "SIP", "Redemption")) {
                    if (home.tapLabel(tab)) {
                        sleep(400);
                        scan(id + "-" + slug(tab), "Profile / Transaction history / " + tab, id);
                    }
                }
            }
            if ("Personal Details".equals(row) && home.tapLabel("Edit personal details")) {
                sleep(500);
                scan(id + "-edit", "Profile / Personal Details / Edit", id);
                profile.goBack();
            }
            if ("Document Details".equals(row) && profile.openContaining("PAN")) {
                sleep(500);
                scan(id + "-pan", "Profile / Document Details / PAN", id);
            }
            if ("FAQs".equals(row)) {
                scanProfileChildren(profile, id, "Profile / FAQs", 8);
            }
            profile.returnToSettings();
        }
        coverGeneralSettings(profile);
        LOG.info("Skipped Change profile picture, Change PIN, and the social apps");
        if (profile.settingsDisplayed() && (profile.openRow("Log Out") || profile.openContaining("Log Out"))) {
            sleep(400);
            scan("profile-logout", "Profile / Log Out", "profile/logout");
            if (!profile.dismissLogout()) {
                home.tapLabel("No");
            }
        }
    }

    private void coverGeneralSettings(ProfileSettingsScreen profile) {
        LOG.info("=== General settings ===");
        if (!profile.settingsDisplayed()) {
            profile.open();
        }
        for (int i = 0; i < 5 && !profile.shows("GENERAL SETTINGS"); i++) {
            profile.swipeUp();
        }
        scan("profile-general", "General settings", "profile/general");
        String[] rows = {
                "Screen Lock", "Utilities", "Terms & Conditions", "Privacy Policy",
                "Account Management", "Rate Us", "FAQs", "About airpay money"
        };
        for (String row : rows) {
            if (!timeLeft()) {
                return;
            }
            if (!profile.settingsDisplayed()) {
                profile.returnToSettings();
                if (!profile.settingsDisplayed()) {
                    profile.open();
                }
            }
            for (int i = 0; i < 4 && !profile.shows(row); i++) {
                profile.swipeUp();
            }
            if (!profile.openRow(row) && !profile.openContaining(row)) {
                LOG.info("General settings row not opened: {}", row);
                continue;
            }
            sleep(500);
            String id = "general-" + slug(row);
            LOG.info("Opened general settings '{}'", row);
            scan(id, "General settings / " + row, id);
            home.swipeUp();
            sleep(200);
            scan(id + "-s", "General settings / " + row + " scroll", id);
            if ("FAQs".equals(row)) {
                scanProfileChildren(profile, id, "General settings / FAQs", 8);
            }
            if ("Privacy Policy".equals(row)) {
                for (int i = 0; i < 3; i++) {
                    profile.swipeUp();
                }
                scan(id + "-scrolled", "General settings / Privacy Policy scrolled", id);
            }
            profile.returnToSettings();
        }
    }

    private void scanProfileChildren(ProfileSettingsScreen profile, String parentId, String parentLabel, int cap) {
        List<String> labels = new java.util.ArrayList<>(home.allClickableLabels());
        int opened = 0;
        for (String label : labels) {
            if (opened >= cap || !timeLeft() || skipProfileControl(label)) {
                continue;
            }
            if (!profile.openRow(label) && !home.tapLabel(label)) {
                continue;
            }
            sleep(450);
            if (!safeSource().contains(PKG)) {
                home.pressSystemBack();
                profile.returnToSettings();
                continue;
            }
            opened++;
            String id = parentId + "-" + slug(label);
            LOG.info("Profile child '{}' from {}", label, parentLabel);
            scan(id, parentLabel + " / " + label, id);
            profile.returnToSettings();
        }
    }

    private boolean skipProfileControl(String label) {
        String n = label == null ? "" : label.toLowerCase(Locale.ROOT).trim();
        return n.isBlank() || n.length() > 70
                || n.contains("change profile") || n.contains("change pin") || n.contains("log out")
                || n.contains("logout") || n.equals("yes") || n.contains("send message")
                || n.contains("submit") || n.contains("update") || n.contains("attach")
                || n.contains("camera") || n.contains("gallery") || n.contains("photo")
                || n.equals("back") || n.contains("open settings");
    }

    /** Open the filter sheet on the current product and scan each filter section. Does not tap Apply. */
    private void scanProductFilters(String parentId, String parentLabel) {
        String[] entries = {"Filters", "Filter", "Filter By", "Sort", "Sort by", "Sort By"};
        for (String entry : entries) {
            if (!timeLeft() || !home.tapLabel(entry)) {
                continue;
            }
            sleep(500);
            String fid = parentId + "-" + slug(entry);
            LOG.info("Opened {} on {}", entry, parentLabel);
            scan(fid, parentLabel + " / " + entry, fid);
            String[] sections = {
                    "Category", "AMC", "Risk", "Returns", "Rating",
                    "Select Categories", "Select AMCs", "Select Risk",
                    "Metal Weight", "Price Range", "Equity", "Balanced",
                    "Less than 2g", "2-4 gms", "4-6 gms", "6-8 gms", "8+ gms"
            };
            for (String section : sections) {
                if (!safeSource().contains(section)) {
                    continue;
                }
                if (!home.tapLabel(section)) {
                    continue;
                }
                sleep(350);
                scan(fid + "-" + slug(section), parentLabel + " / " + entry + " / " + section, fid);
            }
            home.tapBack();
            sleep(300);
            return;
        }
        LOG.info("No filter sheet on {}", parentLabel);
    }

    /** Open each product, then its filters, tabs, and fields. One visit each. Does not pay or book. */
    private void coverProductsDeep() {
        coverProductsDeepOrdered();
    }

    private void coverProductsDeep(String[] modules) {
        coverProductsDeepOrdered();
    }

    /**
     * Bat-safe Products walk: for each module — reset hub → open → scan (and scroll) →
     * return to hub → write scorecard. Never deep-dives into partner webviews.
     */
    private void coverProductsOneByOne(String[] modules) {
        LOG.info("=== Products one-by-one (bat-safe) ===");
        ProductsHubScreen products = new ProductsHubScreen(context);
        if (!resetToProductsHub(products)) {
            LOG.warn("Products hub not ready at start — will still try each module");
        } else {
            scan("products-hub", "Products", "products");
            publishReport();
        }

        Set<String> coveredIds = loadExistingPageIds();
        for (String module : modules) {
            if (!timeLeft()) {
                LOG.warn("Time budget exhausted before Products module: {}", module);
                return;
            }
            String id = "prod-" + slug(module);
            if (productAlreadyCovered(coveredIds, id)) {
                LOG.info("[{}] already in scorecard — skip", module);
                continue;
            }
            LOG.info(">>> Products module START: {}", module);
            try {
                // Same pattern as the first successful Products walk:
                // force real Products hub (Investments) → scroll → tap tile.
                if (!resetToProductsHub(products)) {
                    LOG.warn("[{}] hub reset failed — skip", module);
                    continue;
                }
                if (isLowerProductsTile(module)) {
                    for (int i = 0; i < 4; i++) {
                        scrollDown();
                    }
                } else {
                    scrollToTop();
                }
                boolean opened = products.openModule(module) || home.tapProductTile(module);
                if (!opened) {
                    // Tile may sit mid-list; scroll once more and retry like first-time coverProducts.
                    for (int i = 0; i < 3 && !opened; i++) {
                        scrollDown();
                        opened = products.openModule(module) || home.tapProductTile(module);
                    }
                }
                if (!opened) {
                    LOG.warn("[{}] tile not opened — skip (hub labels: {})", module, head(safeSource()));
                    resetToProductsHub(products);
                    continue;
                }
                sleep(fastResume ? 200 : 400);
                scan(id, "Products / " + module, id);
                home.swipeUp();
                sleep(fastResume ? 120 : 200);
                scan(id + "-s", "Products / " + module + " scroll", id);
                publishReport();
                LOG.info("<<< Products module DONE: {}", module);
            } catch (Exception e) {
                LOG.warn("[{}] stopped: {}", module, e.toString());
                publishReport();
            }
            // Always leave the product interior before the next module.
            leaveProductInterior();
            resetToProductsHub(products);
        }
    }

    /** Back out of MF/NPS/Gold/etc. until the Investments hub is visible. */
    private void leaveProductInterior() {
        for (int i = 0; i < 6; i++) {
            if (onProductsHub()) {
                return;
            }
            if (isPinPad()) {
                unlockPinIfPresent();
                continue;
            }
            dismissOverlayIfAny();
            if (!home.tapBack()) {
                break;
            }
            sleep(fastResume ? 150 : 250);
        }
    }

    /**
     * Return to the real Products hub (Investments grid).
     * Must not treat Mutual Funds / NPS / Digital Gold interiors as the hub —
     * those screens also contain the product name content-desc.
     */
    private boolean resetToProductsHub(ProductsHubScreen products) {
        String udid = context.config().get("android.udid", "");
        keepMoneyForeground();
        leaveProductInterior();
        for (int i = 0; i < 6; i++) {
            if (!PKG.equals(safePackage())) {
                keepMoneyForeground();
            }
            if (onProductsHub()) {
                sleep(fastResume ? 120 : 200);
                return true;
            }
            dismissOverlayIfAny();
            if (home.tapLabel("Close") || home.tapLabel("Dismiss") || home.tapLabel("Cancel")) {
                sleep(fastResume ? 120 : 200);
                continue;
            }
            if (home.tapBack()) {
                sleep(fastResume ? 120 : 200);
                continue;
            }
            try {
                home.openProductsTab();
            } catch (Exception e) {
                AdbCommands.shell(udid, "input", "tap", "180", "2200");
            }
            sleep(fastResume ? 200 : 300);
        }
        keepMoneyForeground();
        products.open();
        sleep(fastResume ? 250 : 400);
        if (onProductsHub()) {
            return true;
        }
        AdbCommands.shell(udid, "input", "tap", "180", "2200");
        sleep(fastResume ? 250 : 400);
        boolean ok = onProductsHub();
        if (!ok) {
            LOG.warn("resetToProductsHub failed — package={} head={}", safePackage(), head(safeSource()));
        }
        return ok;
    }

    private boolean ensureProductsHub(ProductsHubScreen products) {
        int attempts = fastResume ? 4 : 5;
        for (int attempt = 0; attempt < attempts; attempt++) {
            keepMoneyForeground();
            if (attempt == 0) {
                leaveInnerScreen();
            } else {
                dismissOverlayIfAny();
                home.tapBack();
                sleep(fastResume ? 200 : 350);
            }
            if (products.open() || softProductsHub()) {
                return true;
            }
            LOG.info("Products hub retry {} — foreground + Products tab (no Home)", attempt + 1);
            keepMoneyForeground();
            home.openProductsTab();
            sleep(fastResume ? 500 : 900);
            if (softProductsHub()) {
                return true;
            }
        }
        return products.isDisplayed() || softProductsHub();
    }

    /**
     * True only on the Products tab investments grid — not inside Mutual Funds / NPS / Gold.
     * Earlier bug: matching "Mutual Funds" alone treated the MF dashboard as the hub,
     * so NPS / Digital Gold tiles were never found.
     */
    private boolean onProductsHub() {
        String src = safeSource();
        if (!src.contains("content-desc=\"Investments\"")) {
            return false;
        }
        // Product interiors that also mention the product name.
        if (src.contains("View all Mutual Funds")
                || src.contains("Create Goal")
                || src.contains("Features of NPS")
                || src.contains("Open NPS Account")
                || src.contains("Calculate Now")
                || src.contains("Explore NPS")
                || src.contains("Buy Gold")
                || src.contains("Sell Gold")
                || src.contains("Own the Best Digital")
                || src.contains("Book FD")
                || src.contains("Select FD")) {
            return false;
        }
        boolean hasTiles = src.contains("content-desc=\"Digital Gold\"")
                || src.contains("content-desc=\"NPS\"")
                || src.contains("content-desc=\"Fixed Deposit\"");
        return hasTiles && (src.contains("content-desc=\"Products\"") || src.contains("content-desc=\"Hello\""));
    }

    private boolean softProductsHub() {
        return onProductsHub();
    }

    private static boolean isLowerProductsTile(String module) {
        String n = module.toLowerCase(Locale.ROOT);
        return n.contains("credit") || n.contains("loan") || n.contains("tax")
                || n.contains("gold loan") || n.contains("property");
    }

    /** Page ids already written to the Money scorecard (kept across resume runs). */
    private Set<String> loadExistingPageIds() {
        Set<String> ids = new LinkedHashSet<>();
        try {
            Path pages = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y).resolve("money-pages-scanned.txt");
            if (!Files.isRegularFile(pages)) {
                return ids;
            }
            for (String line : Files.readAllLines(pages)) {
                int open = line.lastIndexOf('[');
                int close = line.lastIndexOf(']');
                if (open >= 0 && close > open) {
                    ids.add(line.substring(open + 1, close).trim());
                }
            }
            LOG.info("Resume: {} page ids already in scorecard", ids.size());
        } catch (Exception e) {
            LOG.warn("Could not read existing scorecard pages: {}", e.toString());
        }
        return ids;
    }

    private static boolean productAlreadyCovered(Set<String> coveredIds, String prodId) {
        if (coveredIds.contains(prodId)) {
            return true;
        }
        String prefix = prodId + "-";
        for (String id : coveredIds) {
            if (id.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    private void exploreProductInterior(String parentId, String parentLabel, int depth, Set<String> tried) {
        int maxDepth = fastResume ? 1 : 3;
        if (depth > maxDepth || !timeLeft()) {
            return;
        }
        keepMoneyForeground();
        scan(parentId, parentLabel, parentId);
        if (!fastResume) {
            openDropdownsAndCalendars(parentId, parentLabel);
        }
        List<String> labels = collectLabels();
        labels.sort((a, b) -> Boolean.compare(opensInnerControls(b), opensInnerControls(a)));
        int opened = 0;
        int cap = fastResume
                ? (depth == 0 ? 8 : 3)
                : (depth == 0 ? 28 : depth == 1 ? 14 : depth == 2 ? 8 : 4);
        for (String label : labels) {
            if (opened >= cap || !timeLeft()) {
                return;
            }
            String key = parentId + "|" + label;
            if (!tried.add(key) || skipProductControl(label)) {
                continue;
            }
            String before = safeSource();
            if (!home.tapLabel(label)) {
                continue;
            }
            sleep(fastResume ? 200 : 450);
            keepMoneyForeground();
            if (!PKG.equals(safePackage())) {
                LOG.info("Left app after '{}' — restore foreground", label);
                keepMoneyForeground();
                continue;
            }
            if (safeSource().equals(before)) {
                continue;
            }
            opened++;
            String childId = parentId + "-" + slug(label);
            LOG.info("Opened link '{}' from {}", label, parentLabel);
            if (depth < 2) {
                exploreProductInterior(childId, parentLabel + " / " + label, depth + 1, tried);
            } else {
                scan(childId, parentLabel + " / " + label, childId);
                scrollDown();
                scan(childId + "-s", parentLabel + " / " + label + " scroll", childId);
            }
            home.tapBack();
            sleep(fastResume ? 120 : 250);
            if (!PKG.equals(safePackage())) {
                keepMoneyForeground();
            }
        }
    }

    /** Open dropdowns, date pickers, and similar controls without submitting payments. */
    private void openDropdownsAndCalendars(String parentId, String parentLabel) {
        String[] controls = {
                "Select", "Choose", "Date", "Calendar", "DOB", "From", "To",
                "Duration", "Tenure", "Frequency", "Gender", "Marital", "Employment",
                "State", "City", "Bank", "Edit", "Change"
        };
        int opened = 0;
        for (String control : controls) {
            if (opened >= 6 || !timeLeft()) {
                return;
            }
            if (!home.tapLabel(control)) {
                continue;
            }
            sleep(400);
            keepMoneyForeground();
            String cid = parentId + "-ctrl-" + slug(control) + "-" + opened;
            scan(cid, parentLabel + " / control " + control, cid);
            opened++;
            dismissOverlayIfAny();
            if (!home.tapBack()) {
                home.tapLabel("Close");
                home.tapLabel("Cancel");
                home.tapLabel("Dismiss");
            }
            sleep(250);
            keepMoneyForeground();
        }
    }

    private boolean opensInnerControls(String label) {
        String n = label.toLowerCase(Locale.ROOT);
        return n.contains("filter") || n.contains("sort") || n.contains("category")
                || n.contains("amc") || n.contains("risk") || n.contains("rating")
                || n.contains("return") || n.contains("weight") || n.contains("price")
                || n.contains("dashboard") || n.contains("transaction") || n.contains("locker")
                || n.contains("holding") || n.contains("order") || n.contains("coin")
                || n.contains("goal") || n.contains("explore") || n.contains("portfolio")
                || n.contains("tab") || n.contains("compare") || n.contains("select")
                || n.contains("date") || n.contains("calendar") || n.contains("dropdown")
                || n.contains("edit") || n.contains("details") || n.contains("history")
                || n.contains("setting") || n.contains("help") || n.contains("faq");
    }

    private boolean skipProductControl(String label) {
        String n = label == null ? "" : label.toLowerCase(Locale.ROOT).trim();
        if (n.isBlank() || n.length() > 60) {
            return true;
        }
        return n.contains("logout") || n.contains("log out") || n.contains("sign out")
                || n.contains("profile picture") || n.contains("open settings") || n.contains("menu option")
                || n.contains("notification") || n.startsWith("arn")
                || n.contains("camera") || n.contains("gallery") || n.contains("photo")
                || n.contains("pay") || n.contains("proceed") || n.contains("book")
                || n.contains("buy now") || n.contains("confirm") || n.contains("submit")
                || n.contains("delete") || n.contains("biometric") || n.contains("share")
                || n.contains("rate") || n.contains("whatsapp") || n.contains("explore nps")
                || n.contains("open nps") || n.contains("request delivery") || n.contains("start sip")
                || n.contains("invest now") || n.contains("apply now") || n.contains("apply for")
                || n.equals("apply") || n.equals("continue") || n.equals("next")
                || n.equals("products") || n.equals("finsight") || n.equals("calculators")
                || n.equals("back") || n.startsWith("digit ");
    }

    private void coverFinSight() {
        coverFinSight(loadExistingPageIds());
    }

    private void coverFinSight(Set<String> coveredIds) {
        LOG.info("=== FinSight one-by-one ===");
        keepMoneyForeground();
        home.openTab("FinSight");
        sleep(400);
        if (!productAlreadyCovered(coveredIds, "finsight-hub")) {
            scan("finsight-hub", "FinSight", "finsight");
            home.swipeUp();
            sleep(200);
            scan("finsight-hub-s1", "FinSight scroll 1", "finsight");
            publishReport();
        }
        String[] subs = {
                "Let's start", "View Details", "Reassess",
                "Sync Your Net Worth", "See the diagnosis",
                "What is FHC", "How it works"
        };
        for (String sub : subs) {
            if (!timeLeft()) {
                return;
            }
            String id = "finsight-" + slug(sub);
            if (productAlreadyCovered(coveredIds, id)) {
                LOG.info("[FinSight] {} already in scorecard — skip", sub);
                continue;
            }
            LOG.info(">>> FinSight START: {}", sub);
            try {
                keepMoneyForeground();
                home.openTab("FinSight");
                sleep(300);
                boolean opened = safeTap(sub);
                if (!opened) {
                    safeSwipeUp();
                    opened = safeTap(sub);
                }
                if (!opened) {
                    LOG.info("[FinSight] {} not on screen — skip", sub);
                    continue;
                }
                sleep(450);
                scan(id, "FinSight / " + sub, id);
                safeSwipeUp();
                scan(id + "-s", "FinSight / " + sub + " scroll", id);
                publishReport();
                home.tapBack();
                sleep(250);
                home.openTab("FinSight");
                LOG.info("<<< FinSight DONE: {}", sub);
            } catch (Exception e) {
                LOG.warn("[FinSight] {} failed: {}", sub, e.toString());
                publishReport();
                keepMoneyForeground();
            }
        }
    }

    private boolean safeTap(String label) {
        try {
            return home.tapLabel(label) || home.tapProductTile(label);
        } catch (Exception e) {
            return false;
        }
    }

    private void safeSwipeUp() {
        try {
            home.swipeUp();
        } catch (Exception e) {
            adbSwipe(540, 1750, 540, 700);
        }
        sleep(200);
    }

    private void coverCalculators() {
        LOG.info("=== Calculators ===");
        CalculatorsHubScreen hub = new CalculatorsHubScreen(context);
        home.openTab("Calculators");
        sleep(500);
        scan("calc-hub", "Calculators", "calculators");
        for (String tile : CalculatorsHubScreen.TILES) {
            if (!timeLeft()) {
                return;
            }
            home.openTab("Calculators");
            scrollToTop();
            if (!hub.openTile(tile)) {
                LOG.info("Calculator not opened: {}", tile);
                continue;
            }
            sleep(500);
            String id = "calc-" + slug(tile);
            scan(id, "Calculator / " + tile, id);
            home.swipeUp();
            sleep(200);
            scan(id + "-s", "Calculator / " + tile + " scroll", id);
            returnToTabs();
        }
    }

    private void openTabAndScan(MoneyModuleInventory.Module tab, int maxScreens) {
        try {
            home.openTab(tab.entryLabels());
            sleep(800);
            allowDialogs(Duration.ofSeconds(2));
            scan(tab.id(), tab.label(), tab.id());
            int swipes = context.config().getInt("a11y.info.scroll.swipes", 10);
            for (int i = 1; i <= swipes && scannedIds.size() < maxScreens; i++) {
                home.swipeUp();
                sleep(300);
                scan(tab.id() + "-scroll-" + i, tab.label() + " scroll " + i, tab.id() + ":scroll:" + i);
            }
            crawlCurrentBranch(tab.id());
        } catch (Exception e) {
            LOG.warn("Tab {} failed: {}", tab.label(), e.toString());
            home.openHomeTab();
        }
    }

    private void exploreProductTile(MoneyModuleInventory.Module tile, int maxScreens) {
        try {
            home.openProductsTab();
            sleep(400);
            if (!home.tapProductTile(tile.entryLabels()[0])) {
                invest.open();
                if (!home.tapProductTile(tile.entryLabels()[0])) {
                    return;
                }
            }
            sleep(800);
            scan(tile.id(), tile.label(), tile.id());
            for (int i = 1; i <= 3; i++) {
                home.swipeUp();
                sleep(280);
                scan(tile.id() + "-s" + i, tile.label() + " scroll " + i, tile.id());
            }
            crawlCurrentBranch(tile.id());
            home.tapBack();
            sleep(400);
        } catch (Exception e) {
            LOG.warn("Product {} failed: {}", tile.label(), e.toString());
            safeBackHome();
        }
    }

    private void crawlMenuFully(int maxScreens) {
        LOG.info("=== Menu: About / Contact / Settings / FAQs / Legal (logged-in More tab) ===");
        home.openMoreTab();
        sleep(700);
        scan("menu", "Menu hub", "menu");

        int swipes = context.config().getInt("a11y.info.scroll.swipes", 10);
        for (int i = 1; i <= swipes && scannedIds.size() < maxScreens; i++) {
            home.swipeUp();
            sleep(300);
            scan("menu-scroll-" + i, "Menu scroll " + i, "menu:scroll:" + i);
        }

        for (MoneyModuleInventory.Module section : MoneyModuleInventory.menuSections()) {
            if (scannedIds.size() >= maxScreens) {
                break;
            }
            crawlMenuSection(section, false);
        }

        menu.open();
        exploreMenuChildren("menu-more", 40, maxScreens, false);
        home.openHomeTab();
    }

    private void crawlMenuSection(MoneyModuleInventory.Module section, boolean fromHamburgerDrawer) {
        try {
            if (!fromHamburgerDrawer) {
                menu.open();
                sleep(400);
            } else if (!guest.isDrawerOpen()) {
                guest.tapHamburger();
                sleep(500);
            }
            if (!openMenuSection(section)) {
                LOG.info("Menu section not found: {}", section.label());
                return;
            }
            sleep(700);
            scan(section.id(), "Menu — " + section.label(), section.id());
            for (int i = 1; i <= 4; i++) {
                home.swipeUp();
                sleep(280);
                scan(section.id() + "-scroll-" + i, section.label() + " scroll " + i, section.id());
            }
            exploreMenuChildren(section.id(), 40, context.config().getInt("a11y.deep.max.screens", 250), fromHamburgerDrawer);
            home.tapBack();
            sleep(400);
            if (fromHamburgerDrawer && !guest.isDrawerOpen()) {
                guest.tapHamburger();
                sleep(400);
            }
        } catch (Exception e) {
            LOG.warn("Menu section {} failed: {}", section.label(), e.toString());
            home.tapBack();
            if (fromHamburgerDrawer) {
                guest.tapHamburger();
            }
        }
    }

    private boolean openMenuSection(MoneyModuleInventory.Module section) {
        return switch (section.id()) {
            case "menu-about" -> menu.navigateToAbout();
            case "menu-contact" -> menu.navigateToContact();
            case "menu-settings" -> menu.navigateToSettings();
            case "menu-faqs" -> menu.navigateToFaqs();
            case "menu-privacy" -> menu.navigateToPrivacy();
            case "menu-terms" -> menu.navigateToTerms();
            case "menu-grievance" -> menu.navigateToGrievance();
            case "menu-notifications" -> home.tapLabel("Notifications");
            default -> home.tapAny(List.of(section.entryLabels()));
        };
    }

    private void exploreMenuChildren(String parentId, int maxClicks, int maxScreens, boolean fromHamburgerDrawer) {
        List<String> labels;
        try {
            labels = menu.clickableLabels();
        } catch (Exception e) {
            LOG.warn("Menu child scan skipped — session may have ended: {}", e.toString());
            return;
        }
        int clicked = 0;
        for (String label : labels) {
            if (clicked >= maxClicks || scannedIds.size() >= maxScreens) {
                break;
            }
            String lower = label.toLowerCase(Locale.ROOT);
            if (lower.contains("logout") || lower.contains("share") || lower.contains("rate")
                    || lower.contains("profile picture") || lower.contains("open settings")
                    || lower.contains("camera") || lower.contains("gallery") || lower.contains("photo")
                    || lower.equals("home") || lower.equals("invest") || lower.equals("portfolio")
                    || lower.equals("products") || lower.equals("finsight") || lower.equals("calculators")) {
                continue;
            }
            if (!home.tapLabel(label)) {
                continue;
            }
            clicked++;
            sleep(650);
            allowDialogs(Duration.ofSeconds(2));
            String childId = parentId + "-" + slug(label);
            scan(childId, parentId + " / " + label, childId);
            crawlCurrentBranch(childId);
            home.tapBack();
            sleep(350);
            if (fromHamburgerDrawer) {
                if (!guest.isDrawerOpen()) {
                    guest.tapHamburger();
                    sleep(400);
                }
            } else {
                menu.open();
            }
        }
    }

    private void crawlRemainingHomeClickables(int maxScreens) {
        try {
            home.openHomeTab();
            sleep(500);
            List<String> labels = home.allClickableLabels();
            int extra = 0;
            for (String label : labels) {
                if (extra >= 40 || scannedIds.size() >= maxScreens) {
                    break;
                }
                String lower = label.toLowerCase(Locale.ROOT);
                if (lower.contains("logout") || lower.contains("profile picture") || lower.contains("open settings")
                        || lower.contains("camera") || lower.contains("gallery") || lower.contains("photo")
                        || lower.equals("home") || lower.equals("invest")
                        || lower.equals("portfolio") || lower.equals("loans") || lower.equals("more")
                        || lower.equals("products") || lower.equals("finsight") || lower.equals("calculators")) {
                    continue;
                }
                home.openHomeTab();
                if (!home.tapProductTile(label) && !home.tapLabel(label)) {
                    continue;
                }
                extra++;
                sleep(700);
                allowDialogs(Duration.ofSeconds(2));
                String id = "home-extra-" + slug(label);
                scan(id, "Home → " + label, id);
                crawlCurrentBranch(id);
                home.tapBack();
                sleep(350);
            }
        } catch (Exception e) {
            LOG.warn("Remaining home clickables failed: {}", e.toString());
            safeBackHome();
        }
    }

    private void crawlCurrentBranch(String prefix) {
        try {
            MobileScreenCrawler crawler = new MobileScreenCrawler(
                    context.driver(), context.config(),
                    (id, label) -> scan(prefix + "-" + id, label, prefix + "/" + id));
            if (context.config().getBoolean("a11y.crawl.exhaustive", true)) {
                crawler.crawlAllInteractive(prefix, prefix);
            } else {
                crawler.crawlFromHere(prefix, prefix);
            }
        } catch (Exception e) {
            LOG.debug("Crawler branch {} skipped: {}", prefix, e.toString());
        }
    }

    private void enterOtp(String mobile) {
        LOG.info("OTP for {} is left for the device. Complete it manually.", mobile);
        holdForManualEntry();
    }

    private void dismissPostLoginGates() {
        captureSkipThenOk();
        for (int i = 0; i < 5; i++) {
            allowDialogs(Duration.ofSeconds(3));
            if (home.tapAny(List.of(
                    "Skip", "Not now", "Maybe later", "Continue", "Got it", "Allow"))) {
                sleep(800);
                captureSkipThenOk();
            }
        }
    }

    /**
     * After OTP, capture the Skip for now screen, tap it, capture the next screen, then tap OK.
     */
    private void captureSkipThenOk() {
        String src = safeSource();
        boolean skip = src.contains("Skip for Now") || src.contains("Skip for now")
                || src.contains("Unlock faster with Biometrics");
        if (skip) {
            scan("post-otp-skip", "Post OTP Skip for now", "post-otp-skip");
            LOG.info("Post-OTP Skip for now is showing — captured, tapping Skip for now");
            home.tapAny(List.of("Skip for Now", "Skip for now"));
            sleep(900);
            src = safeSource();
        }
        if (src.contains("content-desc=\"OK\"") || src.contains("text=\"OK\"")) {
            scan("post-otp-ok", "Post OTP OK", "post-otp-ok");
            LOG.info("OK is showing after capture — tapping OK");
            new BiometricScreen(context).tapOk();
        }
    }

    private void scan(String id, String label, String route) {
        holdForManualEntry();
        if (!safeSource().contains(PKG)) {
            bringMoneyToForeground();
            if (!safeSource().contains(PKG)) {
                return;
            }
        }
        String unique = id;
        int n = 2;
        while (!scannedIds.add(unique)) {
            unique = id + "-" + n++;
        }
        allowDialogs(Duration.ofSeconds(2));
        recordScreen(unique, label, route);
    }

    /** Capture an OTP or PIN screen; auto-enter PIN so the app never needs a manual pause. */
    private void holdForManualEntry() {
        if (holding || !isOtpOrPin()) {
            return;
        }
        holding = true;
        try {
            String kind = isPin() ? "pin" : "otp";
            String id = "manual-" + kind + "-" + (++manualGates);
            recordScreen(id, "Gate " + kind + " screen", id);
            publishReport();
            if (isPin()) {
                LOG.info("Auto-entering PIN — keeping Money in foreground");
                unlockPinIfPresent();
            } else if (safeSource().contains("Send OTP") || safeSource().contains("Forgot Your PIN")) {
                LOG.info("Leaving Forgot PIN without sending OTP or logging out");
                if (!home.tapBack()) {
                    home.pressSystemBack();
                }
                keepMoneyForeground();
                sleep(600);
            } else {
                LOG.info("OTP gate — waiting briefly (auto-continue)");
                sleep(fastResume ? 800 : 1500);
            }
            if (!isOtpOrPin()) {
                captureSkipThenOk();
            }
        } finally {
            holding = false;
        }
    }

    private void recordScreen(String id, String label, String route) {
        A11yPageRef ref = A11yPageRef.screen(id, "money", label, route);
        A11yPageScanResult result = AndroidA11yScanner.scan(context.driver(), context.config(), ref);
        A11yScanSession.add(result);
        if (A11yScanSession.results().size() % 15 == 0) {
            publishReport();
        }
    }

    private void publishReport() {
        try {
            com.airpay.mobile.utilities.accessibility.A11yScorecardWriter.write(
                    context.config(),
                    context.config().get("money.app.package", PKG),
                    A11yScanSession.results(),
                    ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y),
                    "money",
                    "airpay money Android");
        } catch (Exception e) {
            LOG.warn("Could not append the scorecard: {}", e.toString());
        }
    }

    private boolean isPin() {
        String src = safeSource();
        return src.contains("Enter your Airpay Money PIN")
                || src.contains("Enter your New PIN")
                || src.contains("Confirm your PIN")
                || src.contains("Forgot PIN")
                || src.contains("Forgot Your PIN")
                || src.contains("Create a unique 6-digit PIN")
                || (src.contains("Welcome Back") && src.contains("PIN"));
    }

    private boolean isPinPad() {
        String src = safeSource();
        return src.contains("Digit 0") && src.contains("Digit 1");
    }

    private void typeConfiguredPin() {
        String pin = MoneyTestData.pin(context.config());
        if (pin == null || pin.isBlank()) {
            pin = context.config().get("money.test.pin", "").trim();
        }
        if (pin.isBlank()) {
            LOG.warn("No PIN configured in Excel/config — cannot auto-unlock");
            return;
        }
        // Prefer Appium digit taps (no pageSource between keys).
        try {
            PinUnlockScreen pad = new PinUnlockScreen(context);
            if (pad.isDisplayed() || pad.waitUntilDisplayed(Duration.ofSeconds(2))) {
                pad.enterPin(pin);
                return;
            }
        } catch (Exception e) {
            LOG.warn("Appium PIN entry failed — falling back to ADB pad: {}", e.toString());
        }
        // ADB fallback: parse pad once, tap all digits. Never refresh pageSource mid-PIN.
        String udid = context.config().get("android.udid", "");
        String src = safeSource();
        int[][] centers = new int[pin.length()][];
        for (int i = 0; i < pin.length(); i++) {
            centers[i] = digitCenter(src, pin.charAt(i));
            if (centers[i] == null) {
                LOG.warn("PIN key {} was not on screen", pin.charAt(i));
                return;
            }
        }
        for (int[] center : centers) {
            AdbCommands.shell(udid, "input", "tap",
                    Integer.toString(center[0]), Integer.toString(center[1]));
            sleep(50);
        }
    }

    private static int[] digitCenter(String src, char digit) {
        int at = src.indexOf("Digit " + digit);
        if (at < 0) {
            return null;
        }
        int bounds = src.indexOf("bounds=\"[", at);
        if (bounds < 0 || bounds - at > 800) {
            return null;
        }
        int end = src.indexOf('"', bounds + 8);
        if (end < 0) {
            return null;
        }
        String box = src.substring(bounds + "bounds=\"".length(), end);
        String[] parts = box.replace("[", "").replace("]", ",").split(",");
        if (parts.length < 4) {
            return null;
        }
        int x1 = Integer.parseInt(parts[0].trim());
        int y1 = Integer.parseInt(parts[1].trim());
        int x2 = Integer.parseInt(parts[2].trim());
        int y2 = Integer.parseInt(parts[3].trim());
        return new int[] {(x1 + x2) / 2, (y1 + y2) / 2};
    }

    private boolean isOtpOrPin() {
        String src = safeSource();
        return isPin()
                || src.contains("Mobile Number Verification")
                || src.contains("Email Verification")
                || src.contains("Enter 6 digit")
                || src.contains("Verification code sent")
                || src.contains("Enter the OTP")
                || src.contains("Verify OTP");
    }

    private void writeScreenList() {
        try {
            // Do not overwrite money-pages-scanned.txt — scorecard writer owns that merge file.
            Path out = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y).resolve("money-deep-scan-ids.txt");
            Files.createDirectories(out.getParent());
            StringBuilder sb = new StringBuilder();
            sb.append("Money deep-scan ids this run (").append(scannedIds.size()).append(")\n\n");
            int i = 1;
            for (String id : scannedIds) {
                sb.append(i++).append(". ").append(id).append('\n');
            }
            Files.writeString(out, sb.toString());
        } catch (Exception e) {
            LOG.warn("Could not write screen list: {}", e.toString());
        }
    }

    private void bringMoneyToForeground() {
        keepMoneyForeground();
    }

    private void safeBackHome() {
        for (int i = 0; i < 8; i++) {
            if (home.looksLoggedIn()) {
                home.openHomeTab();
                return;
            }
            home.tapBack();
            sleep(350);
        }
    }

    private void allowDialogs(Duration timeout) {
        PermissionDialogHandler.allowAll(context.driver(), timeout);
    }

    private String safeSource() {
        try {
            return context.driver().getPageSource();
        } catch (Exception e) {
            return "";
        }
    }

    private static String head(String src) {
        if (src == null) {
            return "";
        }
        String s = src.replaceAll("\\s+", " ");
        return s.length() > 400 ? s.substring(0, 400) : s;
    }

    private static String slug(String label) {
        return label.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
    }

    /** Hard waits: shortened automatically on bat / one-by-one resume (fastResume). */
    private void sleep(long ms) {
        long wait = ms;
        if (fastResume && ms > 120) {
            wait = Math.max(60, ms / 3);
        }
        try {
            Thread.sleep(wait);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
