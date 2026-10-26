package com.airpay.money.flows;

import com.airpay.money.data.MoneyModuleInventory;
import com.airpay.money.screens.auth.AccountSetupScreen;
import com.airpay.money.screens.auth.BiometricScreen;
import com.airpay.money.screens.auth.GooglePhonePickerScreen;
import com.airpay.money.screens.auth.GuestLandingScreen;
import com.airpay.money.screens.auth.LoginPopupScreen;
import com.airpay.money.screens.auth.MobileLoginScreen;
import com.airpay.money.screens.auth.OtpScreen;
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
        coverageDeadline = System.currentTimeMillis() + Duration.ofMinutes(50).toMillis();
        LOG.info("Coverage: Profile settings, product filters, FinSight, Calculators");
        leaveInnerScreen();
        coverProfileSettings();
        if (timeLeft()) {
            coverProductsDeep();
        }
        if (timeLeft()) {
            coverFinSight();
        }
        if (timeLeft()) {
            coverCalculators();
        }
        publishReport();
        writeScreenList();
        LOG.info("Coverage done — {} screens this run", scannedIds.size());
    }

    /** Current screen is already scanned. Walk every remaining product, FinSight, and calculator link. */
    private void resumeRemainingModules() {
        coverageDeadline = System.currentTimeMillis() + Duration.ofMinutes(45).toMillis();
        LOG.info("Remaining coverage: every product link, FinSight, Calculators");
        leaveInnerScreen();
        coverProductsDeep();
        if (timeLeft()) {
            coverFinSight();
        }
        if (timeLeft()) {
            coverCalculators();
        }
        publishReport();
        writeScreenList();
        LOG.info("Remaining coverage done — {} screens this run", scannedIds.size());
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
        for (int i = 0; i < 6; i++) {
            if (onTabBar()) {
                return;
            }
            home.tapBack();
            sleep(300);
        }
    }

    private boolean onTabBar() {
        String src = safeSource();
        return src.contains("Products") && src.contains("FinSight") && src.contains("Calculators");
    }

    private void returnToTabs() {
        for (int i = 0; i < 5; i++) {
            if (onTabBar()) {
                return;
            }
            home.tapBack();
            sleep(280);
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
        for (int i = 0; i < 2; i++) {
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
        LOG.info("=== Products deep: filters, tabs, fields ===");
        ProductsHubScreen products = new ProductsHubScreen(context);
        if (!products.open()) {
            LOG.info("Products hub was not showing. Opening the Products tab again.");
            home.openProductsTab();
            sleep(700);
        }
        scan("products-hub", "Products", "products");

        String[] modules = {
                "Mutual Funds", "NPS", "Digital Gold", "Fixed Deposit",
                "US Stocks", "Indian Stocks",
                "2 Wheeler", "4 Wheeler", "Health", "Life",
                "Gold Loan", "Credit Cards", "Personal Loan", "Home Loan",
                "Loan Against Property", "Tax Filing & Planning", "Tax Filing"
        };
        for (String module : modules) {
            if (!timeLeft()) {
                return;
            }
            try {
                products.open();
                scrollToTop();
                if (!products.openModule(module)) {
                    LOG.info("Products module not opened: {}", module);
                    continue;
                }
                sleep(700);
                String id = "prod-" + slug(module);
                scanProductFilters(id, "Products / " + module);
                exploreProductInterior(id, "Products / " + module, 0, new LinkedHashSet<>());
            } catch (Exception e) {
                LOG.warn("Products module {} stopped: {}", module, e.toString());
                publishReport();
            }
            returnToTabs();
        }
    }

    private void exploreProductInterior(String parentId, String parentLabel, int depth, Set<String> tried) {
        if (depth > 2 || !timeLeft()) {
            return;
        }
        scan(parentId, parentLabel, parentId);
        List<String> labels = collectLabels();
        labels.sort((a, b) -> Boolean.compare(opensInnerControls(b), opensInnerControls(a)));
        int opened = 0;
        int cap = depth == 0 ? 18 : depth == 1 ? 8 : 4;
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
            sleep(550);
            if (!safeSource().contains(PKG)) {
                LOG.info("Left app after '{}' — Back", label);
                home.pressSystemBack();
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
            sleep(300);
            if (!safeSource().contains(PKG)) {
                home.pressSystemBack();
            }
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
                || n.contains("tab") || n.contains("compare");
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
        LOG.info("=== FinSight and its submodules ===");
        home.openTab("FinSight");
        sleep(500);
        scan("finsight-hub", "FinSight", "finsight");
        for (int i = 1; i <= 2; i++) {
            home.swipeUp();
            sleep(250);
            scan("finsight-hub-s" + i, "FinSight scroll " + i, "finsight");
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
            home.openTab("FinSight");
            scrollToTop();
            if (!home.tapLabel(sub) && !home.tapProductTile(sub)) {
                LOG.info("FinSight item not opened: {}", sub);
                continue;
            }
            sleep(600);
            String id = "finsight-" + slug(sub);
            scan(id, "FinSight / " + sub, id);
            home.swipeUp();
            sleep(200);
            scan(id + "-s", "FinSight / " + sub + " scroll", id);
            returnToTabs();
        }
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

    /** Capture an OTP or PIN screen, then wait until it is closed on the device. */
    private void holdForManualEntry() {
        if (holding || !isOtpOrPin()) {
            return;
        }
        holding = true;
        try {
            String kind = isPin() ? "pin" : "otp";
            String id = "manual-" + kind + "-" + (++manualGates);
            LOG.info("PAUSED on {} screen. Enter it on the device. The scan continues when that screen closes.", kind);
            recordScreen(id, "Manual " + kind + " screen", id);
            publishReport();
            if (isPinPad()) {
                typeConfiguredPin();
            } else if (safeSource().contains("Send OTP") || safeSource().contains("Forgot Your PIN")) {
                LOG.info("Leaving Forgot PIN without sending OTP or logging out");
                AdbCommands.shell(context.config().get("android.udid", ""), "input", "keyevent", "4");
                sleep(800);
            }
            long deadline = System.currentTimeMillis() + Duration.ofSeconds(20).toMillis();
            while (System.currentTimeMillis() < deadline && isOtpOrPin()) {
                sleep(1000);
            }
            if (isOtpOrPin()) {
                LOG.warn("Still on the {} screen after the wait. Continuing the scan.", kind);
            } else {
                LOG.info("Manual {} entry finished. Continuing the scan.", kind);
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
        String pin = context.config().get("money.test.pin", "").trim();
        if (pin.isBlank()) {
            return;
        }
        String udid = context.config().get("android.udid", "");
        String src = safeSource();
        for (int i = 0; i < pin.length(); i++) {
            int[] center = digitCenter(src, pin.charAt(i));
            if (center == null) {
                LOG.warn("PIN key {} was not on screen", pin.charAt(i));
                return;
            }
            AdbCommands.shell(udid, "input", "swipe",
                    Integer.toString(center[0]), Integer.toString(center[1]),
                    Integer.toString(center[0]), Integer.toString(center[1]), "180");
            sleep(280);
            src = safeSource();
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
            Path out = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y).resolve("money-pages-scanned.txt");
            Files.createDirectories(out.getParent());
            StringBuilder sb = new StringBuilder();
            sb.append("Money screens scanned (").append(scannedIds.size()).append(")\n\n");
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
        try {
            if (PKG.equals(context.driver().getCurrentPackage())) {
                return;
            }
            context.driver().activateApp(PKG);
            sleep(1500);
        } catch (Exception e) {
            LOG.warn("activateApp failed: {}", e.toString());
        }
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

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
