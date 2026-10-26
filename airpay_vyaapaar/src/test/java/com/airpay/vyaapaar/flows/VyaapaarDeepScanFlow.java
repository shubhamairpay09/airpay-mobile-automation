package com.airpay.vyaapaar.flows;

import com.airpay.mobile.utilities.accessibility.A11yPageRef;
import com.airpay.mobile.utilities.accessibility.A11yScanSession;
import com.airpay.mobile.utilities.accessibility.AndroidA11yScanner;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.android.EmailOtpReader;
import com.airpay.mobile.utilities.android.NotificationOtpReader;
import com.airpay.mobile.utilities.android.PermissionDialogHandler;
import com.airpay.mobile.utilities.config.ProjectPaths;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.locators.PageXpathStore;
import com.airpay.mobile.utilities.locators.UiAutomatorXml;
import com.airpay.vyaapaar.inventory.ScreenInventory;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.w3c.dom.Element;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Logs in once, then walks every link, field, dropdown, image, filter, module,
 * and settings/profile screen a single time. Finished screens are stored so a
 * later run does not loop them.
 */
public class VyaapaarDeepScanFlow {

    private static final Logger LOG = LogManager.getLogger(VyaapaarDeepScanFlow.class);
    private static final Pattern BOUNDS = Pattern.compile("\\[(\\d+),(\\d+)\\]\\[(\\d+),(\\d+)\\]");
    private static final List<String> LOGIN_ACTIONS = List.of(
            "Login with OTP", "Get Started", "Send OTP", "Get OTP", "Continue", "Next", "Verify");

    private final TestContext context;
    private final AndroidDriver driver;
    private final ScreenInventory inventory = new ScreenInventory();
    private final Set<String> explored = new LinkedHashSet<>();
    private final Set<String> finished = new LinkedHashSet<>();
    private final String appPackage;
    private final String appActivity;
    private final int maxScreens;
    private final int maxDepth;
    private final int maxClicks;
    private final int scrollSwipes;
    private final long deadline;
    private final Set<String> storedIds = new LinkedHashSet<>();
    private final Set<String> openedDetails = new LinkedHashSet<>();
    private boolean awakePinned;
    private int captures;
    private int signInRecoveries;
    private int crashReopens;

    public VyaapaarDeepScanFlow(TestContext context) {
        this.context = context;
        this.driver = context.driver();
        this.appPackage = context.config().require("vyaapaar.app.package");
        this.appActivity = context.config().require("vyaapaar.app.activity");
        this.maxScreens = context.config().getInt("a11y.deep.max.screens", 300);
        this.maxDepth = context.config().getInt("a11y.crawl.max.depth", 8);
        this.maxClicks = context.config().getInt("a11y.crawl.max.clicks.per.screen", 28);
        this.scrollSwipes = context.config().getInt("a11y.info.scroll.swipes", 5);
        long budget = context.config().getLong("a11y.crawl.branch.budget.ms", 3_600_000L);
        this.deadline = System.currentTimeMillis() + budget;
    }

    public ScreenInventory inventory() {
        return inventory;
    }

    public void scanEverythingOnce() {
        if (Boolean.getBoolean("a11y.chrome.menu")) {
            scanChromeMenu();
            return;
        }
        if (Boolean.getBoolean("a11y.settings.deep")) {
            scanSettingsDeep();
            return;
        }
        if (Boolean.getBoolean("a11y.profile.rest")) {
            scanProfileRest();
            return;
        }
        if (Boolean.getBoolean("a11y.profile.deep")) {
            scanProfileDeep();
            return;
        }
        if (Boolean.getBoolean("a11y.linkmids.chip")) {
            scanLinkedMidChip();
            return;
        }
        if (Boolean.getBoolean("a11y.linkmids.dropdown")) {
            scanLinkMidsDropdowns();
            return;
        }
        if (Boolean.getBoolean("a11y.nonchannel.here")) {
            scanNonChannelsHere();
            return;
        }
        if (Boolean.getBoolean("a11y.nonchannel.rest")) {
            scanNonChannelRest();
            return;
        }
        if (Boolean.getBoolean("a11y.section.scan")) {
            scanRequestedSections();
            return;
        }
        if (Boolean.getBoolean("a11y.module.detail")) {
            scanModulesInDetail();
            return;
        }
        if (Boolean.getBoolean("a11y.featured.sequence")) {
            scanFeaturedServices();
            return;
        }
        String pick = System.getProperty("a11y.pick.option", "").trim();
        if (!pick.isBlank()) {
            pickOpenOption(pick);
            return;
        }
        if (Boolean.getBoolean("a11y.select.fields")) {
            selectEachOpenField();
            return;
        }
        if (Boolean.getBoolean("a11y.find.city")) {
            findAndCaptureCity();
            return;
        }
        if (Boolean.getBoolean("a11y.capture.only")) {
            captureOpenScreen();
            return;
        }
        A11yScanSession.clear();
        resetFinishedForThisPass();
        loadFinished();
        keepAwake();
        AdbCommands.grantCommonRuntimePermissions(context.config().get("android.udid", ""), appPackage);
        try {
            openAppForScan();
            if (!inApp()) {
                LOG.warn("Vyaapaar did not open. The scan stopped before storing the phone home screen.");
                return;
            }
            loginIfNeeded();
            if (!onSignedInHome(source()) && !isShopForm(source()) && !onboardingRoot(source())) {
                returnToSignedInHome();
            }
            String start = source();
            boolean nestedStart = isShopForm(start) || onboardingRoot(start);
            String title = screenTitle(start);
            String rootModule = onAuthWall(start) ? "auth" : "home";
            crawl(rootModule + "-" + slug(title), title, rootModule, 0);
            if (!nestedStart) {
                LOG.info("Signed-in home scan finished on {}", screenTitle(source()));
                return;
            }
            for (int step = 0; step < 4 && inApp() && !onAuthWall(source()); step++) {
                String before = fingerprint();
                if (!stepBackToParent()) {
                    break;
                }
                if (!inApp() || onAuthWall(source()) || fingerprint().equals(before)) {
                    break;
                }
                title = screenTitle(source());
                crawl(rootModule + "-after-" + slug(title), title, rootModule, 0);
            }
        } finally {
            saveFinished();
            writeScreenList();
            publish(true);
            LOG.info("Vyaapaar scan stored {} screens", inventory.pageCount());
        }
    }

    private static final List<String> FEATURED_SERVICES = List.of(
            "PAN Application",
            "Post Office",
            "AEPS",
            "Aadhaar Pay",
            "CMS",
            "Khata",
            "Dashboard",
            "Withdrawal Limit",
            "Prepaid Cards",
            "Money Transfer");

    /** Opens each Featured Service once, stores that screen, then returns home. No repeated scroll. */
    private void scanFeaturedServices() {
        A11yScanSession.clear();
        try {
            if (!inApp()) {
                LOG.warn("App is not in front — not relaunching, so the session is not sent to biometric");
                return;
            }
            if (!ensureHome()) {
                return;
            }
            capture("home", "home-featured", "Home — " + screenTitle(source()));
            for (String service : FEATURED_SERVICES) {
                if (!ensureHome()) {
                    LOG.warn("Stopped the featured sequence at {} to keep the session", service);
                    break;
                }
                LOG.info("Opening featured service {}", service);
                if (!openFeatured(service)) {
                    LOG.warn("Featured service {} was not on this home view", service);
                    continue;
                }
                sleep(800);
                if (needsBiometric(source()) || !inApp()) {
                    LOG.warn("Biometric or app switch after {} — stopping", service);
                    break;
                }
                capture(slug(service), "featured-" + slug(service), service + " — " + screenTitle(source()));
            }
            ensureHome();
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Featured services sequence stored {} screens", inventory.pageCount());
        }
    }

    /** One module at a time: its links, buttons, edits, filters, and add actions, then home. */
    private void scanModulesInDetail() {
        A11yScanSession.clear();
        List<String> modules = new ArrayList<>(FEATURED_SERVICES);
        modules.add(0, "Add Credits");
        modules.add("Notifications");
        modules.add("Services");
        modules.add("History");
        modules.add("Profile");
        try {
            if (!inApp()) {
                LOG.warn("App is not in front — not relaunching, so the session is not sent to biometric");
                return;
            }
            if (!ensureHome()) {
                return;
            }
            capture("home", "home-detail", "Home — " + screenTitle(source()));
            for (String module : modules) {
                if (!ensureHome()) {
                    LOG.warn("Stopped the detail scan at {} to keep the session", module);
                    break;
                }
                LOG.info("Detail scan of {}", module);
                String homeFp = fingerprint();
                if (!openFeatured(module)) {
                    LOG.warn("{} was not opened", module);
                    continue;
                }
                sleep(800);
                if (needsBiometric(source()) || !inApp()) {
                    LOG.warn("Biometric or app switch after {} — stopping", module);
                    break;
                }
                if (fingerprint().equals(homeFp) || onSignedInHome(source())) {
                    LOG.warn("{} stayed on home", module);
                    continue;
                }
                int[] budget = {16};
                walkModule(slug(module), module, 0, System.currentTimeMillis() + 75_000L, budget, new LinkedHashSet<>());
            }
            ensureHome();
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Module detail scan stored {} screens", inventory.pageCount());
        }
    }

    private void walkModule(String id, String label, int depth, long deadline, int[] budget, Set<String> seen) {
        if (depth > 2 || budget[0] <= 0 || System.currentTimeMillis() > deadline || !inApp()) {
            return;
        }
        if (needsBiometric(source())) {
            LOG.warn("Biometric is showing — stopping inside {}", label);
            return;
        }
        if (depth > 0 && onSignedInHome(source())) {
            return;
        }
        String fp = fingerprint();
        if (fp.isBlank() || !seen.add(fp)) {
            return;
        }
        capture(slug(label), trimId(id), label + " — " + screenTitle(source()));
        budget[0]--;
        int clicks = 0;
        for (Action action : actionsOnce()) {
            if (clicks >= 8 || budget[0] <= 0 || System.currentTimeMillis() > deadline) {
                break;
            }
            if (!inApp() || needsBiometric(source()) || onSignedInHome(source())) {
                break;
            }
            if (shouldSkip(action.label, source()) || isModuleJump(action.label) || isNoise(action.label)) {
                continue;
            }
            String before = fingerprint();
            String beforeTitle = screenTitle(source());
            LOG.info("Inside {} tapping {}", label, action.label);
            if (!tap(action.x, action.y)) {
                continue;
            }
            clicks++;
            sleep(600);
            if (!inApp() || needsBiometric(source())) {
                LOG.warn("Session interrupted after {} — stopping this module", action.label);
                return;
            }
            if (fingerprint().equals(before)) {
                continue;
            }
            if (onSignedInHome(source())) {
                LOG.info("Back on home after {} — next module", action.label);
                return;
            }
            walkModule(trimId(id + "-" + slug(action.label)), label + " / " + action.label,
                    depth + 1, deadline, budget, seen);
            backTo(before, beforeTitle);
            if (!fingerprint().equals(before)) {
                LOG.info("Could not return inside {} after {} — going home for the next module", label, action.label);
                return;
            }
        }
    }

    private List<Action> actionsOnce() {
        Map<String, Action> all = new LinkedHashMap<>();
        for (Action action : visibleActions()) {
            all.putIfAbsent(action.key, action);
        }
        String before = fingerprint();
        swipeUp();
        sleep(250);
        if (!fingerprint().equals(before)) {
            for (Action action : visibleActions()) {
                all.putIfAbsent(action.key, action);
            }
            Dimension size = driver.manage().window().getSize();
            swipe(size.width / 2, (int) (size.height * 0.32), size.width / 2, (int) (size.height * 0.78));
            sleep(250);
        }
        return new ArrayList<>(all.values());
    }

    private boolean backTo(String fp, String title) {
        for (int i = 0; i < 2; i++) {
            if (!inApp() || needsBiometric(source())) {
                return false;
            }
            if (fingerprint().equals(fp) || screenTitle(source()).equalsIgnoreCase(title)) {
                return true;
            }
            if (onSignedInHome(source())) {
                return false;
            }
            if (!tapInAppBack()) {
                return false;
            }
            sleep(400);
        }
        return fingerprint().equals(fp);
    }

    private boolean isModuleJump(String label) {
        String n = normalize(label);
        if (n.equals("home") || n.equals("services") || n.equals("history") || n.equals("profile")
                || n.equals("notifications") || n.equals("add credits")) {
            return true;
        }
        for (String service : FEATURED_SERVICES) {
            String want = normalize(service);
            if (n.equals(want) || n.equals(want + " icon")) {
                return true;
            }
        }
        return false;
    }

    private boolean isNoise(String label) {
        String n = normalize(label);
        return n.contains("visibility") || n.equals("credit balance") || n.equals("featured services");
    }

    private boolean tapVisible(String label) {
        String want = normalize(label);
        for (Action action : visibleActions()) {
            String n = normalize(action.label);
            if (n.equals(want) || n.equals(want + " icon")) {
                return tap(action.x, action.y);
            }
        }
        return false;
    }

    private boolean openFeatured(String service) {
        if (tapNamed(service) || tapVisible(service) || tapLabel(service, service + " icon") || tapTextContaining(service)) {
            return true;
        }
        String before = fingerprint();
        swipeUp();
        sleep(400);
        if (fingerprint().equals(before)) {
            return false;
        }
        return tapVisible(service) || tapLabel(service, service + " icon") || tapTextContaining(service);
    }

    private boolean ensureHome() {
        if (!inApp() && !reopenAfterCrash()) {
            LOG.warn("App is not in front and could not be opened again");
            return false;
        }
        for (int i = 0; i < 5; i++) {
            if (needsBiometric(source())) {
                LOG.warn("Biometric is showing — stopping and leaving the app as it is");
                return false;
            }
            if (onSignedInHome(source())) {
                return true;
            }
            if (onMainShell(source())) {
                tapNamed("Home");
                sleep(350);
                scrollToTop();
                if (onSignedInHome(source())) {
                    return true;
                }
                LOG.warn("Home tabs are visible but Featured Services is not — not pressing system back");
                return false;
            }
            if (tapInAppBack()) {
                sleep(500);
                if (!inApp() || needsBiometric(source())) {
                    LOG.warn("Left the app — stopping so it is not reopened into biometric");
                    return false;
                }
                continue;
            }
            LOG.warn("No in-app back from {} — one system back, then checking the app is still open", screenTitle(source()));
            if (!guardedSystemBack()) {
                return false;
            }
        }
        return onSignedInHome(source());
    }

    private boolean onMainShell(String src) {
        return src != null
                && src.contains("text=\"Home\"")
                && src.contains("text=\"Services\"")
                && src.contains("text=\"History\"")
                && src.contains("text=\"Profile\"");
    }

    private boolean tapNamed(String label) {
        String want = normalize(label);
        int[] icon = null;
        int[] text = null;
        for (Element el : UiAutomatorXml.elements(source())) {
            String n = normalize(shortLabel(first(
                    UiAutomatorXml.attr(el, "content-desc"),
                    UiAutomatorXml.attr(el, "text"))));
            int[] box = bounds(el);
            if (box == null) {
                continue;
            }
            if (n.equals(want + " icon") && icon == null) {
                icon = box;
            } else if (n.equals(want) && text == null) {
                text = box;
            }
        }
        int[] box = icon != null ? icon : text;
        if (box == null) {
            return false;
        }
        return tap((box[0] + box[2]) / 2, (box[1] + box[3]) / 2);
    }

    private boolean tapInAppBack() {
        if (tapLabel("Back Icon", "Back")) {
            return true;
        }
        for (Element el : UiAutomatorXml.elements(source())) {
            if (!"true".equals(UiAutomatorXml.attr(el, "clickable"))) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null) {
                continue;
            }
            int w = box[2] - box[0];
            int h = box[3] - box[1];
            if (box[0] < 180 && box[1] < 320 && w > 20 && w < 220 && h > 20 && h < 220) {
                return tap((box[0] + box[2]) / 2, (box[1] + box[3]) / 2);
            }
        }
        return false;
    }

    private boolean guardedSystemBack() {
        if (onSignedInHome(source()) || onMainShell(source())) {
            return false;
        }
        try {
            driver.navigate().back();
        } catch (Exception e) {
            LOG.debug("System back failed: {}", e.toString());
            return false;
        }
        sleep(450);
        if (!inApp() || needsBiometric(source())) {
            LOG.warn("System back left the app — not relaunching");
            return false;
        }
        return true;
    }

    /** Featured leftovers, then Money Transfer, Non-Channel, Recharge, Other services, and the home tabs. */
    private void scanRequestedSections() {
        A11yScanSession.clear();
        try {
            if (!inApp()) {
                LOG.warn("App is not in front — not relaunching, so the session is not sent to biometric");
                return;
            }
            if (!ensureHome()) {
                return;
            }
            capture("home", "home-sections", "Home — " + screenTitle(source()));
            for (String name : List.of("Dashboard", "Withdrawal Limit", "Prepaid Cards")) {
                openAndWalk(name, false);
            }
            scanDiscovered("Money Transfer", 2);
            scanDiscovered("Non-Channel", 20);
            scanDiscovered("Recharge", 4);
            scanDiscovered("Other service", 1);
            for (String tab : List.of("Services", "History", "Profile")) {
                scanTab(tab);
            }
            ensureHome();
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Section scan stored {} screens", inventory.pageCount());
        }
    }

    private boolean onProfile(String src) {
        if (src == null) {
            return false;
        }
        // Hub only: detail screens (Manage PIN, Change Password, QR Code) lack these hub rows.
        return src.contains("My Commercials") && src.contains("BC Certificate");
    }

    private boolean backToProfile() {
        for (int i = 0; i < 6; i++) {
            if (!inApp() || needsBiometric(source())) {
                return false;
            }
            if (onProfile(source())) {
                return true;
            }
            if (onMainShell(source())) {
                if (!tapNamed("Profile")) {
                    tapLabel("Profile");
                }
                sleep(700);
                continue;
            }
            if (onSignedInHome(source())) {
                if (!tapNamed("Profile")) {
                    tapLabel("Profile");
                }
                sleep(700);
                continue;
            }
            if (tapInAppBack()) {
                sleep(600);
                continue;
            }
            if (guardedSystemBack()) {
                sleep(600);
                continue;
            }
            if (!tapNamed("Profile")) {
                tapLabel("Profile");
            }
            sleep(700);
        }
        return onProfile(source());
    }

    /** Profile tab is open. Capture it, then deep-scan each module and its fields. Never taps Logout. */
    private void scanProfileDeep() {
        A11yScanSession.clear();
        try {
            if (!inApp()) {
                LOG.warn("App is not in front — not relaunching");
                return;
            }
            if (needsBiometric(source())) {
                LOG.warn("Biometric is showing — leaving the app as it is");
                return;
            }
            if (!onProfile(source())) {
                if (!tapNamed("Profile")) {
                    tapLabel("Profile");
                }
                sleep(900);
            }
            if (!onProfile(source())) {
                LOG.warn("Profile is not open — leaving the screen as it is");
                return;
            }
            capture("profile", "profile", "Profile — " + screenTitle(source()));
            for (String name : List.of("QR Activation", "Manage PIN", "Change Password",
                    "BC Certificate", "My Commercials")) {
                if (!inApp() || needsBiometric(source())) {
                    break;
                }
                if (!onProfile(source()) && !backToProfile()) {
                    LOG.warn("Could not return to Profile before {}", name);
                    break;
                }
                sleep(400);
                if (!source().contains(name)) {
                    swipeUp();
                    sleep(300);
                }
                LOG.info("Opening Profile module {}", name);
                String before = fingerprint();
                boolean opened = tapNamed(name) || tapLabel(name, name + " icon")
                        || tapTextContaining(name) || tapVisibleIcon(name);
                sleep(900);
                if (fingerprint().equals(before)) {
                    opened = tapLabel(name) || tapTextCenter(name);
                    sleep(900);
                }
                if (!opened || fingerprint().equals(before)) {
                    LOG.warn("{} stayed on the same screen", name);
                    continue;
                }
                int[] budget = {14};
                walkModule("profile-" + slug(name), name, 0,
                        System.currentTimeMillis() + 60_000L, budget, new LinkedHashSet<>());
                if (!backToProfile()) {
                    LOG.warn("Could not return to Profile after {}", name);
                    break;
                }
            }
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Profile deep scan stored {} screens", inventory.pageCount());
        }
    }

    /** Return to Profile and deep-scan the modules the first pass could not open. Never taps Logout. */
    private void scanProfileRest() {
        A11yScanSession.clear();
        try {
            if (!inApp() || needsBiometric(source())) {
                LOG.warn("App is not in front — leaving it as it is");
                return;
            }
            if (!backToProfile()) {
                LOG.warn("Profile could not be reached — leaving the screen as it is");
                return;
            }
            capture("profile", "profile-return", "Profile — " + screenTitle(source()));
            for (String name : List.of("BC Certificate", "My Commercials")) {
                if (!inApp() || needsBiometric(source())) {
                    break;
                }
                if (!onProfile(source()) && !backToProfile()) {
                    LOG.warn("Could not return to Profile before {}", name);
                    break;
                }
                sleep(400);
                LOG.info("Opening Profile module {}", name);
                String before = fingerprint();
                boolean opened = tapVisibleIcon(name) || tapNamed(name)
                        || tapLabel(name, name + " icon")
                        || tapTextContaining(name) || tapTextCenter(name);
                sleep(1000);
                if (fingerprint().equals(before)) {
                    swipeUp();
                    sleep(300);
                    opened = tapVisibleIcon(name) || tapNamed(name)
                            || tapLabel(name, name + " icon") || tapTextCenter(name);
                    sleep(1000);
                }
                if (!opened || fingerprint().equals(before)) {
                    LOG.warn("{} stayed on the same screen", name);
                    continue;
                }
                int[] budget = {14};
                walkModule("profile-" + slug(name), name, 0,
                        System.currentTimeMillis() + 60_000L, budget, new LinkedHashSet<>());
                if (!backToProfile()) {
                    LOG.warn("Could not return to Profile after {}", name);
                    break;
                }
            }
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Profile rest stored {} screens", inventory.pageCount());
        }
    }

    private boolean onSettings(String src) {
        if (src == null) {
            return false;
        }
        return src.contains("Settings") && !src.contains("My Commercials");
    }

    private boolean backToSettings() {
        for (int i = 0; i < 6; i++) {
            if (!inApp() || needsBiometric(source())) {
                return false;
            }
            if (onSettings(source())) {
                return true;
            }
            if (onProfile(source())) {
                LOG.warn("Back on Profile hub while returning to Settings — reopening Settings");
                return false;
            }
            if (tapInAppBack()) {
                sleep(600);
                continue;
            }
            if (guardedSystemBack()) {
                sleep(600);
                continue;
            }
            return false;
        }
        return onSettings(source());
    }

    /** Settings row below My Commercials. Capture Settings, each inner module, then the profile picture. */
    private void scanSettingsDeep() {
        A11yScanSession.clear();
        try {
            if (!inApp() || needsBiometric(source())) {
                LOG.warn("App is not in front — leaving it as it is");
                return;
            }
            if (!onProfile(source()) && !backToProfile()) {
                LOG.warn("Profile hub could not be reached — leaving the screen as it is");
                return;
            }
            if (!source().contains("Settings")) {
                swipeUp();
                sleep(300);
            }
            LOG.info("Opening Settings");
            String before = fingerprint();
            boolean opened = tapVisibleIcon("Settings") || tapNamed("Settings")
                    || tapLabel("Settings", "Settings icon") || tapTextCenter("Settings");
            sleep(1000);
            if (fingerprint().equals(before)) {
                swipeUp();
                sleep(300);
                opened = tapVisibleIcon("Settings") || tapNamed("Settings") || tapTextCenter("Settings");
                sleep(1000);
            }
            if (!opened || fingerprint().equals(before) || !onSettings(source())) {
                LOG.warn("Settings stayed on the same screen");
            } else {
                capture("profile-settings", "profile-settings", "Settings — " + screenTitle(source()));
                List<String> names = labelsOnOpenTab(15);
                LOG.info("Settings modules: {}", names);
                for (String name : names) {
                    String n = normalize(name);
                    if (n.contains("log out") || n.contains("logout") || n.contains("delete")
                            || n.contains("biometric") || n.contains("fingerprint")
                            || n.equals("app") || n.equals("dark theme")
                            || n.equals("notification settings") || n.equals("settings")
                            || n.contains("copyright") || n.contains("version")) {
                        LOG.info("Skipping Settings entry {}", name);
                        continue;
                    }
                    if (!inApp() || needsBiometric(source())) {
                        break;
                    }
                    if (!onSettings(source()) && !backToSettings()) {
                        LOG.warn("Could not return to Settings before {}", name);
                        break;
                    }
                    sleep(300);
                    LOG.info("Opening Settings module {}", name);
                    String innerBefore = fingerprint();
                    boolean innerOpened = tapVisibleIcon(name) || tapNamed(name)
                            || tapLabel(name, name + " icon") || tapTextCenter(name);
                    sleep(900);
                    if (!innerOpened || fingerprint().equals(innerBefore)) {
                        LOG.warn("{} stayed on the same screen", name);
                        continue;
                    }
                    int[] budget = {10};
                    walkModule("profile-settings-" + slug(name), name, 0,
                            System.currentTimeMillis() + 45_000L, budget, new LinkedHashSet<>());
                    if (!inApp() || needsBiometric(source())) {
                        break;
                    }
                    if (onProfile(source())) {
                        if (!tapVisibleIcon("Settings") && !tapNamed("Settings")) {
                            tapTextCenter("Settings");
                        }
                        sleep(1000);
                    } else if (!onSettings(source())) {
                        backToSettings();
                    }
                }
            }
            if (!onProfile(source()) && !backToProfile()) {
                LOG.warn("Could not return to Profile for the profile picture");
                return;
            }
            capture("profile", "profile-before-picture", "Profile — " + screenTitle(source()));
            LOG.info("Opening profile picture");
            String picBefore = fingerprint();
            boolean picOpened = tapLabel("VT") || tapTextCenter("VT") || tapVisibleIcon("VT");
            sleep(900);
            if (!picOpened || fingerprint().equals(picBefore)) {
                LOG.warn("Profile picture stayed on the same screen");
                return;
            }
            int[] budget = {6};
            walkModule("profile-picture", "Profile picture", 0,
                    System.currentTimeMillis() + 30_000L, budget, new LinkedHashSet<>());
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Settings deep scan stored {} screens", inventory.pageCount());
        }
    }

    /** Chrome holds the airpay website (opened from Terms). Tap its hamburger, capture every menu link. */
    private void scanChromeMenu() {
        A11yScanSession.clear();
        try {
            if (appPackage.equals(currentPackage()) && source().contains("airpay")) {
                LOG.info("Reopening the website link to reach Chrome");
                if (!tapTextCenter("www")) {
                    tapLabel("www");
                }
                sleep(3000);
            }
            if (!currentPackage().contains("chrome")) {
                LOG.warn("Chrome is not open — leaving the screen as it is");
                return;
            }
            capture("chrome-menu", "chrome-terms", "Chrome — " + screenTitle(source()));
            String before = fingerprint();
            boolean menuOpen = tapChromeHamburger();
            sleep(1200);
            if (!menuOpen || fingerprint().equals(before)) {
                LOG.warn("Website hamburger stayed on the same screen. Visible: {}", visibleTexts(20));
                return;
            }
            capture("chrome-menu", "chrome-menu-open", "Chrome menu — " + screenTitle(source()));
            String menuFp = fingerprint();
            List<String> links = chromeLinks(10);
            LOG.info("Chrome menu links: {}", links);
            Set<String> captured = new LinkedHashSet<>(Set.of("menu"));
            int done = 0;
            for (String link : links) {
                if (done >= 10 || needsBiometric(source())) {
                    break;
                }
                if (!currentPackage().contains("chrome") || !captured.add(normalize(link))) {
                    continue;
                }
                if (!source().contains(link) && tapChromeHamburger()) {
                    sleep(1200);
                }
                if (!currentPackage().contains("chrome")) {
                    break;
                }
                LOG.info("Opening Chrome link {}", link);
                if (!tapLabel(link)) {
                    tapTextCenter(link);
                }
                sleep(1800);
                String pkg = currentPackage();
                if (!pkg.contains("chrome")) {
                    LOG.info("Chrome link {} left to {} — going back", link, pkg);
                    backToChromeOrApp();
                    continue;
                }
                if (fingerprint().equals(menuFp)) {
                    continue;
                }
                capture("chrome-menu", trimId("chrome-link-" + slug(link)),
                        "Chrome link " + link + " — " + screenTitle(source()));
                done++;
            }
            backToChromeOrApp();
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Chrome menu stored {} screens", inventory.pageCount());
        }
    }

    private boolean tapChromeHamburger() {
        for (Element el : UiAutomatorXml.elements(source())) {
            String label = shortLabel(first(
                    UiAutomatorXml.attr(el, "content-desc"),
                    UiAutomatorXml.attr(el, "text")));
            String n = normalize(label);
            if (!(n.contains("hamburger") || n.contains("open menu") || n.equals("menu")
                    || n.contains("navigation menu") || n.contains("site menu"))) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null || box[3] - box[1] < 20) {
                continue;
            }
            if (tapFirm((box[0] + box[2]) / 2, (box[1] + box[3]) / 2)) {
                return true;
            }
        }
        return false;
    }

    private List<String> chromeLinks(int max) {
        List<String> found = new ArrayList<>();
        Set<String> seen = new LinkedHashSet<>();
        Set<String> browserUi = Set.of("back", "forward", "reload", "refresh", "stop", "home",
                "tabs", "new tab", "open tabs", "address bar", "search", "more options",
                "customize and control", "customize", "close tab", "close", "share", "bookmark",
                "add to home screen", "desktop site", "downloads", "settings", "help");
        for (Element el : UiAutomatorXml.elements(source())) {
            String label = shortLabel(first(
                    UiAutomatorXml.attr(el, "text"),
                    UiAutomatorXml.attr(el, "content-desc")));
            String n = normalize(label);
            if (label.length() < 3 || label.length() > 40 || !seen.add(n)) {
                continue;
            }
            if (browserUi.contains(n) || n.endsWith("icon") || n.endsWith("illustration")
                    || shouldSkip(label, source()) || isNoise(label)) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null || box[1] < 150 || box[3] > 2150) {
                continue;
            }
            found.add(label);
            if (found.size() >= max) {
                break;
            }
        }
        return found;
    }

    private void backToChromeOrApp() {
        for (int i = 0; i < 10; i++) {
            String pkg = currentPackage();
            if (pkg.contains("chrome") || appPackage.equals(pkg)) {
                return;
            }
            try {
                driver.navigate().back();
            } catch (Exception e) {
                return;
            }
            sleep(1000);
        }
    }

    /** Success dialog is open. Store it, dismiss OK, then open one linked MID chip. Do not press Link. */
    private void scanLinkedMidChip() {
        A11yScanSession.clear();
        try {
            if (!inApp()) {
                LOG.warn("App is not in front — not relaunching");
                return;
            }
            if (source().contains("Merchant linked successfully") || source().contains("Success Icon")) {
                capture("link-mids", "link-mids-linked-success", "Merchant linked successfully");
                if (!tapLabel("OK")) {
                    tapTextCenter("OK");
                }
                sleep(1200);
            }
            if (!source().contains("Link MIDs")) {
                LOG.warn("Link MIDs is not open after the success message");
                return;
            }
            capture("link-mids", "link-mids-after-success", "Link MIDs — " + screenTitle(source()));
            String chip = firstLinkedMid();
            if (chip == null) {
                LOG.warn("No linked MID chip is on screen");
                return;
            }
            LOG.info("Opening linked MID {}", chip);
            String before = fingerprint();
            if (!tapTextCenter(chip)) {
                LOG.warn("Could not tap linked MID {}", chip);
                return;
            }
            sleep(900);
            if (fingerprint().equals(before)) {
                LOG.warn("Linked MID {} stayed on the same screen", chip);
                return;
            }
            int[] budget = {8};
            walkModule("link-mids-" + slug(chip), "Linked MID " + chip, 0,
                    System.currentTimeMillis() + 40_000L, budget, new LinkedHashSet<>());
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Linked MID chip stored {} screens", inventory.pageCount());
        }
    }

    private String firstLinkedMid() {
        boolean underHeading = false;
        for (Element el : UiAutomatorXml.elements(source())) {
            String label = shortLabel(UiAutomatorXml.attr(el, "text"));
            String n = normalize(label);
            if (n.equals("linked mids")) {
                underHeading = true;
                continue;
            }
            if (!underHeading || !label.matches("\\d{4,8}")) {
                continue;
            }
            int[] box = bounds(el);
            if (box != null && box[1] > 200 && box[3] < 2100) {
                return label;
            }
        }
        return null;
    }

    private boolean tapTextCenter(String text) {
        for (Element el : UiAutomatorXml.elements(source())) {
            String label = shortLabel(first(
                    UiAutomatorXml.attr(el, "text"),
                    UiAutomatorXml.attr(el, "content-desc")));
            if (!label.equalsIgnoreCase(text)) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null || box[3] - box[1] < 20) {
                continue;
            }
            return tapFirm((box[0] + box[2]) / 2, (box[1] + box[3]) / 2);
        }
        return false;
    }

    /** Link MIDs is open. Capture each dropdown list and each option. Do not press Link. */
    private void scanLinkMidsDropdowns() {
        A11yScanSession.clear();
        try {
            if (!inApp() || !source().contains("Link MIDs")) {
                LOG.warn("Link MIDs is not open — leaving the screen as it is");
                return;
            }
            capture("link-mids", "link-mids", "Link MIDs — " + screenTitle(source()));
            List<int[]> fields = linkMidDropdowns();
            LOG.info("Link MIDs dropdown fields: {}", fields.size());
            for (int i = 0; i < fields.size(); i++) {
                int[] box = fields.get(i);
                int x = (box[0] + box[2]) / 2;
                int y = (box[1] + box[3]) / 2;
                if (!openLinkMidField(x, y)) {
                    LOG.warn("Link MIDs dropdown {} did not open", i + 1);
                    continue;
                }
                String sheet = "link-mids-dropdown-" + (i + 1);
                capture("link-mids", sheet, "Link MIDs dropdown " + (i + 1) + " — " + screenTitle(source()));
                List<String> options = new ArrayList<>();
                Set<String> seen = new LinkedHashSet<>();
                addDropdownOptions(options, seen);
                String beforeScroll = fingerprint();
                swipe(540, 1500, 540, 900);
                sleep(400);
                if (!fingerprint().equals(beforeScroll)) {
                    capture("link-mids", sheet + "-more", "Link MIDs dropdown " + (i + 1) + " more — " + screenTitle(source()));
                    addDropdownOptions(options, seen);
                }
                LOG.info("Link MIDs dropdown {} options: {}", i + 1, options);
                int taken = 0;
                for (String option : options) {
                    if (taken >= 16) {
                        break;
                    }
                    if (!source().toLowerCase(Locale.ROOT).contains(option.toLowerCase(Locale.ROOT))) {
                        if (!openLinkMidField(x, y)) {
                            break;
                        }
                    }
                    String before = fingerprint();
                    if (!tapLabel(option) && !tapTextContaining(option) && !tapVisibleIcon(option)) {
                        LOG.warn("Could not select {}", option);
                        continue;
                    }
                    sleep(700);
                    if (fingerprint().equals(before)) {
                        continue;
                    }
                    taken++;
                    capture("link-mids", trimId(sheet + "-" + slug(option)), "Link MIDs / " + option + " — " + screenTitle(source()));
                    if (!source().contains("Link MIDs") && !source().toLowerCase(Locale.ROOT).contains(option.toLowerCase(Locale.ROOT))) {
                        tapInAppBack();
                        sleep(400);
                    }
                }
                if (!source().contains("Link MIDs")) {
                    tapInAppBack();
                    sleep(400);
                }
            }
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Link MIDs dropdowns stored {} screens", inventory.pageCount());
        }
    }

    private boolean openLinkMidField(int x, int y) {
        if (!source().contains("Link MIDs")) {
            return false;
        }
        String before = fingerprint();
        tapFirm(x, y);
        sleep(800);
        return inApp() && !needsBiometric(source()) && !fingerprint().equals(before);
    }

    private void addDropdownOptions(List<String> options, Set<String> seen) {
        for (Element el : UiAutomatorXml.elements(source())) {
            String label = shortLabel(first(UiAutomatorXml.attr(el, "text"), UiAutomatorXml.attr(el, "content-desc")));
            String n = normalize(label);
            if (n.length() < 2 || label.length() > 48 || n.endsWith("icon")) {
                continue;
            }
            if (n.equals("link mids") || n.equals("link") || n.equals("linked mids") || n.contains("consent")
                    || n.contains("hereby") || isNoise(label) || shouldSkip(label, source())) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null || box[3] < 200 || box[1] > 2100) {
                continue;
            }
            if (seen.add(n)) {
                options.add(label);
            }
        }
    }

    private List<int[]> linkMidDropdowns() {
        List<int[]> fields = new ArrayList<>();
        for (Element el : UiAutomatorXml.elements(source())) {
            if (!"true".equals(UiAutomatorXml.attr(el, "clickable"))) {
                continue;
            }
            if (UiAutomatorXml.className(el).contains("EditText")) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null) {
                continue;
            }
            int width = box[2] - box[0];
            int height = box[3] - box[1];
            if (width > 700 && height > 80 && height < 240 && box[1] > 250 && box[3] < 820) {
                fields.add(box);
            }
        }
        return fields;
    }

    /** The phone is already scrolled to Non Channels. Open each tile, then come back to this list. */
    private void scanNonChannelsHere() {
        A11yScanSession.clear();
        try {
            if (!inApp()) {
                LOG.warn("App is not in front — not relaunching, so the session is not sent to biometric");
                return;
            }
            if (!onNonChannelGrid() && !revealNonChannelGrid()) {
                LOG.warn("Non Channels is not on screen — leaving the scroll where it is");
                return;
            }
            List<String> order = List.of(
                    "ONDC",
                    "Agreement",
                    "Retailer Transaction History",
                    "Money Transfer",
                    "Credit Ledger History",
                    "UPI",
                    "Request From Network",
                    "Loan Lead Generation",
                    "Credit/Debit/Others",
                    "Subscription",
                    "CashIn - Online",
                    "Link MIDs",
                    "Devotional Experiences",
                    "Finance");
            LOG.info("Non Channels order: {}", order);
            for (String name : order) {
                if (!inApp() || needsBiometric(source())) {
                    return;
                }
                if (!onNonChannelGrid() && !backToNonChannels()) {
                    LOG.warn("Left the Non Channels list before {}", name);
                    return;
                }
                if (!bringTileIntoView(name)) {
                    LOG.warn("{} is not visible on Non Channels", name);
                    continue;
                }
                openOneNonChannel(name);
                if (!backToNonChannels()) {
                    LOG.warn("Could not return to Non Channels after {}", name);
                    return;
                }
            }
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Non Channels one-by-one stored {} screens", inventory.pageCount());
        }
    }

    private boolean bringTileIntoView(String name) {
        if (tileOnScreen(name)) {
            return true;
        }
        for (int i = 0; i < 3; i++) {
            swipeBetweenTiles();
            sleep(400);
            if (tileOnScreen(name)) {
                return true;
            }
        }
        for (int i = 0; i < 4; i++) {
            swipeDownBetweenTiles();
            sleep(400);
            if (tileOnScreen(name) || !inApp() || needsBiometric(source())) {
                return tileOnScreen(name);
            }
        }
        return false;
    }

    private void swipeDownBetweenTiles() {
        Dimension size = driver.manage().window().getSize();
        swipe(380, (int) (size.height * 0.46), 380, (int) (size.height * 0.70));
    }

    private boolean tileOnScreen(String name) {
        String want = normalize(name);
        String raw = source().toLowerCase(Locale.ROOT);
        if (!raw.contains(name.toLowerCase(Locale.ROOT)) && !raw.contains((name + " icon").toLowerCase(Locale.ROOT))) {
            return false;
        }
        for (Element el : UiAutomatorXml.elements(source())) {
            String n = normalize(shortLabel(first(
                    UiAutomatorXml.attr(el, "content-desc"),
                    UiAutomatorXml.attr(el, "text"))));
            if (!n.equals(want) && !n.equals(want + " icon")) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null) {
                continue;
            }
            int height = box[3] - box[1];
            if (box[1] >= 140 && box[3] <= 2050 && height >= 48) {
                return true;
            }
        }
        return false;
    }

    private boolean alreadyOpened(String name) {
        String n = normalize(name);
        return n.equals("aeps") || n.equals("collect money") || n.equals("baazaar") || n.equals("upi qr")
                || n.equals("pos activation") || n.equals("certificate") || n.equals("money transfer");
    }

    private boolean onNonChannelGrid() {
        String src = source();
        return onMainShell(src) && (src.contains("Non Channels") || src.contains("ONDC")
                || src.contains("Collect Money") || src.contains("Request From Network")
                || src.contains("Loan Lead") || src.contains("CashIn"));
    }

    private boolean revealNonChannelGrid() {
        if (!onMainShell(source())) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            swipeBetweenTiles();
            sleep(400);
            if (onNonChannelGrid()) {
                return true;
            }
        }
        return false;
    }

    private void openOneNonChannel(String name) {
        LOG.info("Opening {}", name);
        String before = fingerprint();
        if (!tapVisibleIcon(name)) {
            tapNamed(name);
        }
        sleep(900);
        if (fingerprint().equals(before)) {
            tapVisibleIcon(name);
            sleep(800);
        }
        if (!inApp() || needsBiometric(source())) {
            LOG.warn("Session interrupted on {}", name);
            return;
        }
        if (fingerprint().equals(before)) {
            LOG.warn("{} stayed on the same screen", name);
            return;
        }
        int[] budget = {8};
        walkModule(slug(name), name, 0, System.currentTimeMillis() + 40_000L, budget, new LinkedHashSet<>());
    }

    private boolean tapVisibleIcon(String name) {
        String want = normalize(name);
        int[] best = null;
        int bestScore = -1;
        for (Element el : UiAutomatorXml.elements(source())) {
            String n = normalize(shortLabel(first(
                    UiAutomatorXml.attr(el, "content-desc"),
                    UiAutomatorXml.attr(el, "text"))));
            if (!n.equals(want) && !n.equals(want + " icon")) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null) {
                continue;
            }
            int height = box[3] - box[1];
            if (box[1] < 120 || box[3] > 2080 || height < 48) {
                continue;
            }
            int score = (box[2] - box[0]) * height + (n.endsWith("icon") ? 200_000 : 0);
            if (score > bestScore) {
                best = box;
                bestScore = score;
            }
        }
        if (best == null) {
            return false;
        }
        return tapFirm((best[0] + best[2]) / 2, (best[1] + best[3]) / 2);
    }

    private boolean tapFirm(int x, int y) {
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(new Pause(finger, Duration.ofMillis(160)));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(tap));
            return true;
        } catch (Exception e) {
            LOG.debug("Firm tap failed: {}", e.toString());
            return false;
        }
    }

    private boolean backToNonChannels() {
        for (int i = 0; i < 6; i++) {
            if (!inApp() || needsBiometric(source())) {
                return false;
            }
            if (source().contains("Non Channels") || onNonChannelGrid()) {
                return true;
            }
            if (onMainShell(source())) {
                swipeBetweenTiles();
                sleep(400);
                continue;
            }
            if (!tapInAppBack() && !guardedSystemBack()) {
                return false;
            }
            sleep(450);
        }
        return source().contains("Non Channels");
    }

    private List<String> visibleNonChannelTiles() {
        List<String> found = new ArrayList<>();
        Set<String> seen = new LinkedHashSet<>();
        for (Element el : UiAutomatorXml.elements(source())) {
            String label = shortLabel(UiAutomatorXml.attr(el, "text"));
            String n = normalize(label);
            if (n.length() < 2 || label.length() > 40 || n.endsWith("icon")) {
                continue;
            }
            if (n.equals("non channels") || n.equals("home") || n.equals("services") || n.equals("history")
                    || n.equals("profile") || n.equals("notifications") || n.equals("credit balance")
                    || n.equals("featured services") || n.contains("recharge") || n.contains("other service")) {
                if (n.contains("recharge") || n.contains("other service")) {
                    break;
                }
                continue;
            }
            if (isNoise(label) || shouldSkip(label, source())) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null) {
                continue;
            }
            int height = box[3] - box[1];
            if (height < 36 || box[3] > 2080 || box[1] < 80) {
                continue;
            }
            if (seen.add(n)) {
                found.add(label);
            }
        }
        return found;
    }

    private void swipeBetweenTiles() {
        Dimension size = driver.manage().window().getSize();
        swipe(380, (int) (size.height * 0.70), 380, (int) (size.height * 0.46));
    }

    private static final Set<String> NON_CHANNEL_DONE = Set.of(
            "collect money",
            "baazaar",
            "upi qr",
            "pos activation",
            "certificate",
            "retailer transaction history");

    /** The six opened Non-Channel tiles stay as they are. This pass opens the rest, including ONDC and Agreement. */
    private void scanNonChannelRest() {
        A11yScanSession.clear();
        try {
            if (!inApp()) {
                LOG.warn("App is not in front — not relaunching, so the session is not sent to biometric");
                return;
            }
            if (!ensureHome() || !revealHeader("Non-Channel")) {
                LOG.warn("Non-Channel list was not reached");
                return;
            }
            capture("home", "non-channel-row", "Non-Channel — " + screenTitle(source()));
            List<String> names = new ArrayList<>();
            Set<String> seen = new LinkedHashSet<>();
            addModuleNames(names, seen, labelsBesideHeader("Non-Channel"));
            addModuleNames(names, seen, modulesUnder("Non-Channel", 24));
            boolean hub = openNonChannelHub();
            if (hub) {
                capture("home", "non-channel-hub", "Non-Channel hub — " + screenTitle(source()));
                addModuleNames(names, seen, labelsOnOpenTab(30, 8));
                ensureHome();
            }
            List<String> rest = new ArrayList<>();
            for (String name : names) {
                if (NON_CHANNEL_DONE.contains(normalize(name))) {
                    continue;
                }
                rest.add(name);
                if (rest.size() >= 14) {
                    break;
                }
            }
            LOG.info("Non-Channel modules seen: {}", names);
            LOG.info("Non-Channel remaining: {}", rest);
            for (String name : rest) {
                if (!inApp() || needsBiometric(source())) {
                    break;
                }
                if (hub) {
                    if (!openNonChannelHub()) {
                        LOG.warn("Stopped Non-Channel before {}", name);
                        break;
                    }
                    bringLabelIntoView(name);
                } else if (!ensureHome() || !revealHeader("Non-Channel")) {
                    LOG.warn("Stopped Non-Channel before {}", name);
                    break;
                } else {
                    bringNonChannelLabel(name);
                }
                openAndWalk(name, true);
            }
            ensureHome();
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Non-Channel rest stored {} screens", inventory.pageCount());
        }
    }

    private void addModuleNames(List<String> names, Set<String> seen, List<String> extra) {
        for (String label : extra) {
            if (!keepModuleName(label)) {
                continue;
            }
            if (seen.add(normalize(label))) {
                names.add(label);
            }
        }
    }

    private boolean keepModuleName(String label) {
        String n = normalize(label);
        if (n.length() < 3 || label.length() > 40 || n.endsWith("icon")) {
            return false;
        }
        if (n.equals("home") || n.equals("services") || n.equals("history") || n.equals("profile")
                || n.equals("notifications") || n.equals("credit balance") || n.equals("featured services")
                || n.equals("add credits") || n.equals("search")) {
            return false;
        }
        if (n.contains("money transfer") || n.contains("non channel") || n.equals("recharge")
                || n.contains("recharge bill") || n.contains("other service")) {
            return false;
        }
        return !isNoise(label) && !shouldSkip(label, source());
    }

    private List<String> labelsBesideHeader(String header) {
        List<String> found = new ArrayList<>();
        Set<String> seen = new LinkedHashSet<>();
        int[] headerBox = labelBoundsContaining(header);
        if (headerBox == null) {
            return found;
        }
        int rowY = headerBox[3] + 150;
        for (int row = 0; row < 3; row++) {
            int y = rowY + (row * 220);
            for (int swipe = 0; swipe < 4; swipe++) {
                collectBand(found, seen, headerBox[3] + 8, headerBox[3] + 120 + ((row + 1) * 230));
                String before = fingerprint();
                swipeHorizontal(y, true);
                sleep(300);
                if (fingerprint().equals(before) || !pageHasHeader(header)) {
                    break;
                }
            }
            if (!pageHasHeader(header)) {
                break;
            }
        }
        return found;
    }

    private void collectBand(List<String> found, Set<String> seen, int top, int bottom) {
        for (Element el : UiAutomatorXml.elements(source())) {
            String label = shortLabel(first(UiAutomatorXml.attr(el, "text"), UiAutomatorXml.attr(el, "content-desc")));
            int[] box = bounds(el);
            if (box == null || label.isBlank()) {
                continue;
            }
            int cy = (box[1] + box[3]) / 2;
            if (cy < top || cy > bottom) {
                continue;
            }
            if (seen.add(normalize(label))) {
                found.add(label);
            }
        }
    }

    private void bringNonChannelLabel(String label) {
        if (source().toLowerCase(Locale.ROOT).contains(label.toLowerCase(Locale.ROOT))) {
            return;
        }
        int[] headerBox = labelBoundsContaining("Non-Channel");
        int y = headerBox == null ? driver.manage().window().getSize().height / 2 : headerBox[3] + 180;
        for (int i = 0; i < 4; i++) {
            swipeHorizontal(y, true);
            sleep(300);
            if (source().toLowerCase(Locale.ROOT).contains(label.toLowerCase(Locale.ROOT))) {
                return;
            }
        }
        bringLabelIntoView(label);
    }

    private boolean openNonChannelHub() {
        if (!ensureHome() || !revealHeader("Non-Channel")) {
            return false;
        }
        String before = fingerprint();
        if (!(tapLabel("View all", "View All", "See all", "See All") || tapTextContaining("View all"))) {
            tapHeaderEnd("Non-Channel");
        }
        sleep(800);
        if (fingerprint().equals(before)) {
            tapNamed("Non-Channel");
            sleep(800);
        }
        if (!inApp() || needsBiometric(source()) || fingerprint().equals(before) || onSignedInHome(source())) {
            return false;
        }
        String title = screenTitle(source()).toLowerCase(Locale.ROOT);
        if (pageHasHeader("Non-Channel") || title.contains("channel") || visibleTexts(8).size() >= 6) {
            return true;
        }
        ensureHome();
        return false;
    }

    private boolean tapHeaderEnd(String header) {
        int[] box = labelBoundsContaining(header);
        if (box == null) {
            return false;
        }
        int y = (box[1] + box[3]) / 2;
        for (Element el : UiAutomatorXml.elements(source())) {
            if (!"true".equals(UiAutomatorXml.attr(el, "clickable"))) {
                continue;
            }
            int[] node = bounds(el);
            if (node == null) {
                continue;
            }
            int cy = (node[1] + node[3]) / 2;
            int cx = (node[0] + node[2]) / 2;
            if (Math.abs(cy - y) < 50 && cx > box[2] - 10) {
                return tap(cx, cy);
            }
        }
        int x = Math.min(driver.manage().window().getSize().width - 40, Math.max(box[2] + 30, box[2]));
        return tap(x, y);
    }

    private boolean tapAboveLabel(String label) {
        String want = normalize(label);
        for (Element el : UiAutomatorXml.elements(source())) {
            String n = normalize(shortLabel(first(UiAutomatorXml.attr(el, "text"), UiAutomatorXml.attr(el, "content-desc"))));
            if (!n.equals(want)) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null) {
                continue;
            }
            return tap((box[0] + box[2]) / 2, Math.max(90, box[1] - 70));
        }
        return false;
    }

    private int[] labelBoundsContaining(String header) {
        String want = normalize(header);
        for (Element el : UiAutomatorXml.elements(source())) {
            String n = normalize(shortLabel(first(UiAutomatorXml.attr(el, "text"), UiAutomatorXml.attr(el, "content-desc"))));
            if (n.contains(want)) {
                return bounds(el);
            }
        }
        return null;
    }

    private void swipeHorizontal(int y, boolean toTheLeft) {
        Dimension size = driver.manage().window().getSize();
        int yy = Math.max(180, Math.min(y, size.height - 180));
        int start = toTheLeft ? (int) (size.width * 0.84) : (int) (size.width * 0.16);
        int end = toTheLeft ? (int) (size.width * 0.16) : (int) (size.width * 0.84);
        swipe(start, yy, end, yy);
    }

    private void scanDiscovered(String header, int max) {
        LOG.info("Section {}", header);
        if (!ensureHome() || !revealHeader(header)) {
            LOG.warn("Section {} was not found", header);
            return;
        }
        List<String> names = modulesUnder(header, max);
        LOG.info("Section {} modules: {}", header, names);
        for (String name : names) {
            if (!ensureHome() || !revealHeader(header)) {
                LOG.warn("Stopped section {} before {}", header, name);
                break;
            }
            bringLabelIntoView(name);
            openAndWalk(name, true);
        }
    }

    private void openAndWalk(String name, boolean alreadyRevealed) {
        if (!inApp() || needsBiometric(source())) {
            return;
        }
        LOG.info("Opening {}", name);
        String before = fingerprint();
        boolean opened = alreadyRevealed
                ? tapNamed(name) || tapVisible(name) || tapLabel(name, name + " icon") || tapTextContaining(name)
                : openFeatured(name);
        if (!opened) {
            LOG.warn("{} was not opened", name);
            return;
        }
        sleep(800);
        if (needsBiometric(source()) || !inApp()) {
            LOG.warn("Biometric or app switch after {} — stopping", name);
            return;
        }
        if (fingerprint().equals(before)) {
            if (tapLabel(name) || tapTextContaining(name) || tapAboveLabel(name)) {
                sleep(900);
            }
        }
        if (fingerprint().equals(before)) {
            LOG.warn("{} stayed on the same screen", name);
            return;
        }
        int[] budget = {14};
        walkModule(slug(name), name, 0, System.currentTimeMillis() + 60_000L, budget, new LinkedHashSet<>());
        ensureHome();
    }

    private void scanTab(String tab) {
        LOG.info("Tab {}", tab);
        if (!ensureHome() || !openFeatured(tab)) {
            LOG.warn("Tab {} was not opened", tab);
            return;
        }
        sleep(700);
        if (!inApp() || needsBiometric(source())) {
            return;
        }
        capture(slug(tab), slug(tab), tab + " — " + screenTitle(source()));
        List<String> names = labelsOnOpenTab(12);
        LOG.info("Tab {} modules: {}", tab, names);
        ensureHome();
        for (String name : names) {
            if (!ensureHome() || !openFeatured(tab)) {
                LOG.warn("Stopped tab {} before {}", tab, name);
                break;
            }
            sleep(400);
            bringLabelIntoView(name);
            openAndWalk(name, true);
        }
    }

    private void bringLabelIntoView(String label) {
        String want = label.toLowerCase(Locale.ROOT);
        for (int i = 0; i < 4; i++) {
            if (source().toLowerCase(Locale.ROOT).contains(want)) {
                return;
            }
            String before = fingerprint();
            swipeUp();
            sleep(300);
            if (fingerprint().equals(before)) {
                return;
            }
        }
    }

    private List<String> visibleTexts(int max) {
        List<String> found = new ArrayList<>();
        for (Element el : UiAutomatorXml.elements(source())) {
            String label = shortLabel(first(UiAutomatorXml.attr(el, "text"), UiAutomatorXml.attr(el, "content-desc")));
            if (label.length() < 3 || label.length() > 40 || normalize(label).endsWith("icon")) {
                continue;
            }
            found.add(label);
            if (found.size() >= max) {
                break;
            }
        }
        return found;
    }

    private List<String> labelsOnOpenTab(int max) {
        return labelsOnOpenTab(max, 4);
    }

    private List<String> labelsOnOpenTab(int max, int passes) {
        List<String> found = new ArrayList<>();
        Set<String> seen = new LinkedHashSet<>();
        for (int pass = 0; pass < passes && found.size() < max; pass++) {
            for (Element el : UiAutomatorXml.elements(source())) {
                String label = shortLabel(first(UiAutomatorXml.attr(el, "text"), UiAutomatorXml.attr(el, "content-desc")));
                String n = normalize(label);
                if (n.isBlank() || n.endsWith("icon") || n.equals("home") || n.equals("services")
                        || n.equals("history") || n.equals("profile") || n.equals("notifications")
                        || n.equals("featured services") || n.equals("credit balance")
                        || isNoise(label) || shouldSkip(label, source())) {
                    continue;
                }
                if (seen.add(n)) {
                    found.add(label);
                }
                if (found.size() >= max) {
                    break;
                }
            }
            if (found.size() >= max) {
                break;
            }
            String before = fingerprint();
            swipeUp();
            sleep(300);
            if (fingerprint().equals(before)) {
                break;
            }
        }
        return found;
    }

    private boolean pageHasHeader(String header) {
        String raw = source().toLowerCase(Locale.ROOT);
        String want = header.toLowerCase(Locale.ROOT);
        return raw.contains(want) || raw.contains(want.replace('-', ' '));
    }

    private boolean revealHeader(String header) {
        for (int i = 0; i < 8; i++) {
            if (!inApp() || needsBiometric(source())) {
                return false;
            }
            if (!onSignedInHome(source()) && !onMainShell(source())) {
                LOG.warn("Left the home list while looking for {}", header);
                return false;
            }
            if (pageHasHeader(header)) {
                return true;
            }
            String before = fingerprint();
            swipeUp();
            sleep(350);
            if (fingerprint().equals(before)) {
                break;
            }
        }
        if (!pageHasHeader(header)) {
            LOG.info("Labels while looking for {}: {}", header, visibleTexts(20));
        }
        return pageHasHeader(header);
    }

    private List<String> modulesUnder(String header, int max) {
        List<String> found = new ArrayList<>();
        Set<String> seen = new LinkedHashSet<>();
        boolean passedHeader = false;
        String want = normalize(header);
        for (int pass = 0; pass < 8 && found.size() < max; pass++) {
            boolean headerHere = false;
            for (Element el : UiAutomatorXml.elements(source())) {
                String n = normalize(shortLabel(first(UiAutomatorXml.attr(el, "text"), UiAutomatorXml.attr(el, "content-desc"))));
                if (n.contains(want)) {
                    headerHere = true;
                    break;
                }
            }
            boolean started = !headerHere && passedHeader;
            boolean stop = false;
            for (Element el : UiAutomatorXml.elements(source())) {
                if (UiAutomatorXml.className(el).contains("EditText")) {
                    continue;
                }
                String label = shortLabel(first(UiAutomatorXml.attr(el, "text"), UiAutomatorXml.attr(el, "content-desc")));
                String n = normalize(label);
                if (headerHere && !started) {
                    if (n.contains(want)) {
                        started = true;
                        passedHeader = true;
                    }
                    continue;
                }
                if (!started) {
                    continue;
                }
                if (n.isBlank() || n.endsWith("icon") || n.equals("credit balance") || n.equals("notifications")) {
                    continue;
                }
                if (isSectionStop(n, want)) {
                    stop = true;
                    break;
                }
                if (n.equals("home") || n.equals("services") || n.equals("history") || n.equals("profile")
                        || n.equals("featured services") || isModuleJump(label) || isNoise(label)
                        || shouldSkip(label, source())) {
                    continue;
                }
                if (seen.add(n)) {
                    found.add(label);
                }
                if (found.size() >= max) {
                    break;
                }
            }
            if (stop || found.size() >= max || !passedHeader) {
                break;
            }
            String before = fingerprint();
            swipeUp();
            sleep(350);
            if (fingerprint().equals(before)) {
                break;
            }
        }
        return found;
    }

    private boolean isSectionStop(String n, String currentHeader) {
        if (n.contains(currentHeader)) {
            return false;
        }
        return (n.contains("money transfer") && !currentHeader.contains("money"))
                || (n.contains("non channel") && !currentHeader.contains("non"))
                || (n.contains("recharge") && !currentHeader.contains("recharge"))
                || (n.contains("other service") && !currentHeader.contains("other"));
    }

    private boolean needsBiometric(String src) {
        String n = src == null ? "" : src.toLowerCase(Locale.ROOT);
        String pkg = currentPackage().toLowerCase(Locale.ROOT);
        if (pkg.contains("biometric") || pkg.contains("fingerprint")) {
            return true;
        }
        return n.contains("biometric") || n.contains("fingerprint") || n.contains("use fingerprint")
                || n.contains("face unlock") || n.contains("use face") || n.contains("face id")
                || n.contains("confirm your identity") || n.contains("verify your identity")
                || n.contains("verify identity") || n.contains("use pin")
                || n.contains("enter your pin") || n.contains("confirm pin");
    }

    /**
     * Fingerprint, face, or PIN has to be done by the person holding the phone.
     * The scan does not tap it, press back, or reopen the app while it waits.
     */
    private void waitForUserUnlock() {
        if (!needsBiometric(source())) {
            return;
        }
        LOG.warn("Fingerprint, face, or PIN is on screen. Unlock it on the phone — the scan will wait and will not leave the app.");
        long until = System.currentTimeMillis() + 10 * 60_000L;
        long nextLog = System.currentTimeMillis() + 30_000L;
        while (System.currentTimeMillis() < until) {
            sleep(3000);
            if (!needsBiometric(source()) && inApp()) {
                LOG.info("Unlock done — scan continues");
                return;
            }
            if (System.currentTimeMillis() >= nextLog) {
                LOG.warn("Still waiting for fingerprint, face, or PIN on the phone");
                nextLog = System.currentTimeMillis() + 30_000L;
            }
        }
        LOG.warn("Fingerprint, face, or PIN was still on screen after waiting");
    }

    /** Stores the open list, selects one option, then stores the page that follows. */
    private void pickOpenOption(String option) {
        A11yScanSession.clear();
        String sheet = System.getProperty("a11y.sheet.label", "").trim();
        try {
            String visible = screenTitle(source());
            String label = sheet.isBlank() ? visible : sheet;
            String rootId = "open-" + slug(label);
            LOG.info("Capturing {} then selecting {}", label, option);
            capture("home", rootId, label);
            String before = fingerprint();
            if (!tapLabel(option) && !tapTextContaining(option)) {
                LOG.warn("Could not select {}", option);
                return;
            }
            long until = System.currentTimeMillis() + 8_000;
            do {
                sleep(500);
            } while (fingerprint().equals(before) && System.currentTimeMillis() < until);
            String afterTitle = screenTitle(source());
            String afterId = afterTitle.equalsIgnoreCase(label)
                    ? rootId + "-" + slug(option)
                    : "open-" + slug(afterTitle);
            String afterLabel = afterTitle.equalsIgnoreCase(label) ? label + " / " + option : afterTitle;
            capture("home", trimId(afterId), afterLabel);
            LOG.info("Selected {} — now on {}", option, afterTitle);
        } finally {
            writeScreenList();
            publish(true);
        }
    }

    /** Stores the open sheet, then selects each field on it and stores that page too. */
    private void selectEachOpenField() {
        A11yScanSession.clear();
        String sheet = System.getProperty("a11y.sheet.label", "").trim();
        try {
            String visible = screenTitle(source());
            String label = sheet.isBlank() ? visible : sheet;
            String rootId = "open-" + slug(label);
            LOG.info("Capturing {} then selecting each field", label);
            capture("home", rootId, label);
            List<String> fields = fieldLabelsOnSheet();
            LOG.info("Fields on {}: {}", label, fields);
            for (String field : fields) {
                if (captures >= 28) {
                    break;
                }
                if (!source().toLowerCase(Locale.ROOT).contains(field.toLowerCase(Locale.ROOT))) {
                    if (!reopenSheet(sheet, visible)) {
                        LOG.warn("Could not reopen {}", label);
                        break;
                    }
                    sleep(700);
                    capture("home", rootId, label);
                }
                LOG.info("Selecting field {}", field);
                String before = fingerprint();
                if (!tapLabel(field) && !tapTextContaining(field)) {
                    LOG.warn("Could not select {}", field);
                    continue;
                }
                sleep(600);
                capture("home", trimId(rootId + "-" + slug(field)), label + " / " + field);
                if (fingerprint().equals(before)) {
                    continue;
                }
            }
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Each open field stored");
        }
    }

    private boolean reopenSheet(String sheet, String visible) {
        if (!sheet.isBlank() && (tapLabel(sheet) || tapTextContaining(sheet))) {
            return true;
        }
        return tapLabel(visible) || tapTextContaining(visible);
    }

    private List<String> fieldLabelsOnSheet() {
        List<String> labels = new ArrayList<>();
        Set<String> seen = new LinkedHashSet<>();
        String header = normalize(screenTitle(source()));
        String src = source().toLowerCase(Locale.ROOT);
        boolean timeSheet = src.contains("select time") || src.contains("o'clock") || src.contains("o&apos;clock");
        for (int pass = 0; pass < 4; pass++) {
            for (Action action : visibleActions()) {
                rememberField(labels, seen, header, action.label);
            }
            for (Element el : UiAutomatorXml.elements(source())) {
                if (UiAutomatorXml.className(el).contains("EditText")) {
                    continue;
                }
                rememberNode(labels, seen, header, el);
            }
            if (labels.size() >= 24 || timeSheet) {
                break;
            }
            String before = fingerprint();
            swipeUp();
            sleep(350);
            if (fingerprint().equals(before)) {
                break;
            }
        }
        return labels.size() > 24 ? labels.subList(0, 24) : labels;
    }

    private void rememberNode(List<String> labels, Set<String> seen, String header, Element el) {
        String text = UiAutomatorXml.attr(el, "text");
        String desc = UiAutomatorXml.attr(el, "content-desc");
        String combined = (desc == null ? "" : desc) + "\n" + (text == null ? "" : text);
        String lower = combined.toLowerCase(Locale.ROOT);
        if (lower.contains("o'clock") || lower.contains("minute") || lower.contains("a.m")
                || lower.contains("p.m") || lower.contains("select hour")) {
            for (String line : combined.split("\n")) {
                String piece = line.trim();
                if (!piece.isEmpty()) {
                    rememberField(labels, seen, header, piece);
                }
            }
            return;
        }
        rememberField(labels, seen, header, shortLabel(first(text, desc)));
    }

    private void rememberField(List<String> labels, Set<String> seen, String header, String label) {
        String n = normalize(label);
        if (n.isBlank() || n.equals(header) || n.equals("select a") || n.equals("a") || n.equals("p")
                || label.length() > 40 || shouldSkip(label, source()) || isChrome(n)) {
            return;
        }
        if (seen.add(n)) {
            labels.add(label);
        }
    }

    /** Types the city into the open list, scrolls until that row is visible, stores it, then selects it. */
    private void findAndCaptureCity() {
        A11yScanSession.clear();
        String city = context.config().get("vyaapaar.test.city", "Saharanpur");
        try {
            LOG.info("Searching the open list for {}", city);
            capture("home", "open-city-list", screenTitle(source()));
            typeIntoPickerSearch(city);
            boolean found = false;
            for (int i = 0; i < 15; i++) {
                if (screenHasRow(city)) {
                    capture("home", "open-city-search-" + slug(city), "City search " + city);
                    found = tapTextContaining(city);
                    sleep(700);
                    break;
                }
                LOG.info("Scrolling the city list for {}", city);
                swipeUp();
                sleep(450);
            }
            capture("home", "open-city-after-" + slug(screenTitle(source())), screenTitle(source()));
            if (found) {
                LOG.info("Selected {}", city);
            } else {
                LOG.warn("Could not find {} after search and scroll", city);
            }
        } finally {
            writeScreenList();
            publish(true);
        }
    }

    private boolean screenHasRow(String needle) {
        String want = needle.toLowerCase(Locale.ROOT);
        for (Element el : UiAutomatorXml.elements(source())) {
            if (UiAutomatorXml.className(el).contains("EditText")) {
                continue;
            }
            String text = shortLabel(first(
                    UiAutomatorXml.attr(el, "text"),
                    UiAutomatorXml.attr(el, "content-desc"))).toLowerCase(Locale.ROOT);
            if (text.equals(want) || text.startsWith(want + " ") || text.startsWith(want + ",")) {
                return true;
            }
        }
        return false;
    }

    /** Stores whatever is on screen. Does not tap, type, or go back. */
    private void captureOpenScreen() {
        A11yScanSession.clear();
        try {
            String src = source();
            String sheet = System.getProperty("a11y.sheet.label", "").trim();
            boolean linkMids = src.contains("Linked MIDs") || src.contains("Link MIDs");
            String title = !sheet.isBlank() ? sheet : linkMids ? "Link MIDs" : screenTitle(src);
            String module = linkMids ? "link-mids" : "home";
            String id = linkMids ? "link-mids-linked-list" : "open-" + slug(title);
            LOG.info("Capturing the open screen only: {}", title);
            capture(module, id, title);
        } finally {
            writeScreenList();
            publish(true);
            LOG.info("Open screen stored. Leave the phone as it is.");
        }
    }

    /**
     * Opens Vyaapaar when it is closed. Leaves it alone when it is already in front,
     * so a signed-in session is not sent to fingerprint, face, or PIN.
     * A lock screen is left for the person holding the phone.
     */
    private void openAppForScan() {
        if (needsBiometric(source())) {
            waitForUserUnlock();
        }
        if (inApp()) {
            LOG.info("App is already open — scanning from this screen, not starting it again");
            return;
        }
        LOG.info("App is not open — opening it");
        try {
            driver.activateApp(appPackage);
            sleep(1500);
        } catch (Exception e) {
            LOG.warn("Could not resume the app: {}", e.toString());
        }
        if (needsBiometric(source())) {
            waitForUserUnlock();
        }
        if (inApp()) {
            LOG.info("App resumed");
            return;
        }
        LOG.info("App was closed — starting it from scratch");
        AdbCommands.shell(context.config().get("android.udid", ""),
                "am", "start", "-n", appPackage + "/" + appActivity);
        sleep(2500);
        if (needsBiometric(source())) {
            waitForUserUnlock();
        }
    }

    /**
     * Every bat run captures login first, including on another machine where the app
     * is already signed in. Crawl still refuses Logout, so this happens only once at the start.
     */
    private boolean signOutToLogin() {
        if (onAuthWall(source()) || isOtp(source()) || onRegistration(source())) {
            return true;
        }
        LOG.info("Signing out once so this run captures login from the start");
        if (!onProfile(source())) {
            returnToSignedInHome();
            if (!backToProfile()) {
                tapLabel("Profile");
                sleep(800);
            }
        }
        for (int i = 0; i < 6 && inApp() && !onAuthWall(source()); i++) {
            if (needsBiometric(source())) {
                waitForUserUnlock();
            }
            if (tapLabel("Logout", "Log out", "Log Out", "Sign out", "Sign Out")) {
                sleep(800);
                tapLabel("Yes", "Logout", "Log out", "Confirm", "OK");
                sleep(1200);
                if (needsBiometric(source())) {
                    waitForUserUnlock();
                }
                if (onAuthWall(source()) || isOtp(source())) {
                    LOG.info("Login screen is open — capturing from the start");
                    return true;
                }
            }
            swipeUp();
            sleep(400);
        }
        return onAuthWall(source()) || isOtp(source());
    }

    private void loginIfNeeded() {
        if (!inApp()) {
            LOG.warn("App is not in front — the phone home screen is not a signed-in session");
            return;
        }
        if (needsBiometric(source())) {
            waitForUserUnlock();
        }
        String src = source();
        if (!onAuthWall(src) && !onRegistration(src) && !isOtp(src) && !isAlert(src)) {
            if (!signOutToLogin()) {
                LOG.warn("Could not reach login. Scanning from the screen that is already open.");
                return;
            }
        }
        if (Boolean.getBoolean("a11y.login.password")) {
            if (context.config().get("vyaapaar.test.password", "").isBlank()) {
                LOG.warn("a11y.login.password is set but vyaapaar.test.password is empty — using OTP login");
            } else {
                LOG.info("Capturing login from the start, then signing in");
                scanLoginDetail();
                loginWithUsernameAndPassword();
                return;
            }
        }
        String mobile = context.config().require("vyaapaar.test.mobile");
        LOG.info("Using mobile {}", mobile);
        dismissAlerts();
        if (onRegistration(source())) {
            LOG.info("Registration is already open — entering {} then OTP login", mobile);
            walkForm("register", "Register", List.of(
                    "Get OTP", "Send OTP", "Continue", "Next", "Verify", "Submit", "Proceed"), 8, true);
        }
        if (onAuthWall(source()) && !isOtp(source()) && !alreadyRegistered(source())) {
            backToLogin();
            coverRegister();
        }
        dismissAlerts();
        if (onAuthWall(source()) || onRegistration(source()) || isAlert(source())) {
            backToLogin();
            loginWithOtp();
        }
    }

    /** Login wall detail pass: captures the form, its links and boxes, then leaves it for password login. */
    private void scanLoginDetail() {
        dismissAlerts();
        if (!backToLogin()) {
            LOG.warn("Login screen was not reached for the detail scan");
            return;
        }
        String before = fingerprint();
        String title = screenTitle(source());
        capture("auth", "auth-" + slug(title) + "-detail", "Login detail — " + title);
        openDropdowns("auth", "Login");
        openLinkedDetails("auth", "Login");
        acceptUncheckedBoxes();
        fillIdentityIfSafe();
        if (!fingerprint().equals(before)) {
            capture("auth", "auth-" + slug(screenTitle(source())) + "-filled", "Login filled — " + screenTitle(source()));
        }
        captureLinkAndReturn("Register Here", "Register");
        captureLinkAndReturn("Forgot Password?", "Forgot Password");
        backToLogin();
    }

    /** Opens a login link for one capture and comes back. Never submits anything. */
    private void captureLinkAndReturn(String link, String label) {
        backToLogin();
        String before = fingerprint();
        if (!tapLabel(link) && !tapContaining(link)) {
            return;
        }
        sleep(900);
        if (fingerprint().equals(before)) {
            return;
        }
        capture("auth", "auth-" + slug(label), label + " — " + screenTitle(source()));
        backToLogin();
    }

    /** Registration screens, including OTP. Does not change a password on the forgot-password flow. */
    private void coverRegister() {
        if (!backToLogin()) {
            LOG.warn("Login screen was not reached before registration");
            return;
        }
        capture("auth", "auth-" + slug(screenTitle(source())), "Login");
        if (!tapRegisterHere()) {
            LOG.warn("Register Here was not on the login screen. Visible: {}", visibleLabels());
            return;
        }
        sleep(1000);
        LOG.info("Registration — scanning every step, then submitting");
        walkForm("register", "Register", List.of(
                "Register", "Sign up", "Sign Up", "Create account", "Create Account",
                "Get OTP", "Send OTP", "Continue", "Next", "Verify", "Submit", "Proceed"), 8, true);
    }

    /** Forgot-password screens. Stops before saving a new password. */
    private void coverForgotPassword() {
        if (!openLogin()) {
            return;
        }
        if (!tapLabel("Forgot Password?", "Forgot Password", "Forgot password")) {
            LOG.warn("Forgot Password was not on the login screen");
            return;
        }
        sleep(1000);
        LOG.info("Forgot password — scanning the form and OTP, not setting a new password");
        walkForm("forgot-password", "Forgot Password", List.of(
                "Get OTP", "Send OTP", "Submit", "Continue", "Next", "Verify"), 6, false);
        returnToLogin();
    }

    private void loginWithOtp() {
        dismissAlerts();
        String src = source();
        if (!onAuthWall(src) && !isOtp(src) && !onRegistration(src) && !isAlert(src)) {
            LOG.info("Already signed in — OTP login is not repeated");
            return;
        }
        if (!backToLogin()) {
            LOG.warn("Could not reach login before OTP login. Visible: {}", visibleLabels());
            return;
        }
        src = source();
        if (src.contains("Login with OTP") && (src.contains("Password") || src.contains("Username or Mobile"))) {
            if (!tapLabel("Login with OTP") && !tapContaining("Login with OTP")) {
                LOG.warn("Login with OTP was not tappable. Visible: {}", visibleLabels());
            }
            sleep(900);
        }
        String mobile = context.config().require("vyaapaar.test.mobile");
        fillIdentity();
        if (!source().contains(mobile)) {
            typeBelowLabel(mobile, "Mobile Number", "Username or Mobile number", "Username");
        }
        capture("auth", "auth-login-with-otp", "Login with OTP");
        hideKeyboard();
        if (!tapLabel("Get OTP", "Send OTP", "Login", "Continue")
                && !tapContaining("Get OTP") && !tapContaining("Send OTP")) {
            LOG.warn("Could not request the login OTP. Visible: {}", visibleLabels());
            return;
        }
        LOG.info("Login with OTP requested for {}", mobile);
        sleep(1200);
        if (isOtp(source()) && !enterOtp(mobile)) {
            LOG.warn("Login OTP was not read — scan continues from the current screen");
        }
        dismissEntryGates();
    }

    /**
     * Username/password login. Runs only from the dedicated scenario, after the
     * accessibility scan, or from the login wall when {@code a11y.login.password}
     * is set. Needs {@code vyaapaar.test.password} (config/secrets.properties).
     */
    public void loginWithUsernameAndPassword() {
        String mobile = context.config().get("vyaapaar.test.username", "").trim();
        if (mobile.isBlank()) {
            mobile = context.config().require("vyaapaar.test.mobile");
        }
        String password = context.config().get("vyaapaar.test.password", "");
        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "Set vyaapaar.test.password before the username/password login. "
                            + "This step stays after the accessibility scan.");
        }
        openLogin();
        if (source().contains("Login with password")) {
            tapLabel("Login with password");
            sleep(800);
        }
        // The login form uses floating labels INSIDE the fields, so the fields are
        // targeted directly: first EditText = username, second = password.
        boolean userOk = typeLoginField(0, mobile, true);
        boolean passOk = typeLoginField(1, password, false);
        LOG.info("Login form filled — username: {}, password: {}", userOk, passOk);
        capture("auth", "auth-login-with-password", "Login with password");
        if (!userOk || !passOk) {
            LOG.warn("Login fields did not fill — skipping the Login tap. Visible: {}", visibleLabels());
            return;
        }
        tapLabel("Login", "Sign in", "Continue");
        // The OTP screen can appear a few seconds after the wall closes.
        for (int wait = 0; wait < 8; wait++) {
            sleep(1000);
            String after = source();
            if (isOtp(after)) {
                LOG.info("Login asked for OTP — entering the configured OTP");
                enterOtp(mobile);
                break;
            }
            if (!onAuthWall(after) && !onRegistration(after)) {
                LOG.info("Login tap left the login wall — continuing inside the app");
                dismissEntryGates();
                return;
            }
        }
        if (isOtp(source())) {
            LOG.warn("Still on the OTP screen after submitting the OTP. Visible: {}", visibleLabels());
        } else if (onAuthWall(source()) || onRegistration(source())) {
            LOG.warn("LOGIN FAILED — still on the login wall after the Login tap. Visible: {}", visibleLabels());
        } else {
            LOG.info("Signed in after OTP — continuing inside the app");
            dismissEntryGates();
        }
    }

    /**
     * Types into the nth EditText on screen and verifies the text landed.
     * Values are never logged. Falls back to adb key events on retry.
     */
    private boolean typeLoginField(int index, String value, boolean verifyExact) {
        for (int attempt = 1; attempt <= 2; attempt++) {
            try {
                List<WebElement> fields = driver.findElements(By.xpath("//android.widget.EditText"));
                if (index >= fields.size()) {
                    LOG.warn("Login field {} not found ({} fields on screen)", index, fields.size());
                    return false;
                }
                WebElement field = fields.get(index);
                field.click();
                sleep(400);
                try {
                    field.clear();
                } catch (Exception ignored) {
                    // some fields reject clear
                }
                if (attempt == 1) {
                    field.sendKeys(value);
                } else {
                    AdbCommands.shell(context.config().get("android.udid", ""), "input", "text",
                            value.replace(" ", "%s"));
                }
            } catch (Exception e) {
                LOG.warn("Login field {} typing failed (attempt {}): {}", index, attempt, e.toString());
            }
            hideKeyboard();
            sleep(400);
            if (loginFieldFilled(index, verifyExact ? value : null)) {
                return true;
            }
            LOG.warn("Login field {} was still empty after attempt {}", index, attempt);
        }
        return false;
    }

    /** Checks the nth EditText holds text (and the exact value when expected is given). */
    private boolean loginFieldFilled(int index, String expected) {
        try {
            List<Element> nodes = UiAutomatorXml.elements(source());
            int seen = 0;
            for (Element el : nodes) {
                if (!UiAutomatorXml.className(el).contains("EditText")) {
                    continue;
                }
                if (seen++ != index) {
                    continue;
                }
                String text = UiAutomatorXml.attr(el, "text");
                if (text == null || text.isBlank()) {
                    return false;
                }
                return expected == null || text.equals(expected);
            }
        } catch (Exception e) {
            LOG.warn("Login field {} verification failed: {}", index, e.toString());
        }
        return false;
    }

    private void walkForm(String module, String label, List<String> forward, int maxSteps, boolean allowNewPassword) {
        String mobile = context.config().require("vyaapaar.test.mobile");
        String previous = "";
        int stuck = 0;
        for (int step = 0; step < maxSteps && !timedOut(); step++) {
            allowDialogs();
            String src = source();
            String title = screenTitle(src);
            capture(module, module + "-" + slug(title), label + " — " + title);
            openDropdowns(module, label);
            openLinkedDetails(module, label);
            acceptUncheckedBoxes();
            fillIdentity();
            String mobileNow = source();
            if ((mobileNow.contains("Mobile Number") || mobileNow.contains("Username or Mobile")
                    || mobileNow.contains("Phone")) && !mobileNow.contains(mobile)) {
                LOG.info("Entering mobile {}", mobile);
                typeBelowLabel(mobile, "Mobile Number", "Username or Mobile number", "Username", "Phone Number");
            }
            src = source();
            if (!allowNewPassword && isNewPasswordScreen(src)) {
                LOG.info("Stored the new-password screen and left it unchanged");
                return;
            }
            if (alreadyRegistered(src)) {
                LOG.info("This mobile is already registered — stored that screen");
                return;
            }
            if (isOtp(src)) {
                if (!enterOtp(mobile)) {
                    return;
                }
                continue;
            }
            hideKeyboard();
            String action = firstLabeled(source(), forward);
            String fp = fingerprint();
            if (action == null || (fp.equals(previous) && ++stuck >= 2)
                    || !(tapLabel(action) || tapContaining(action))) {
                LOG.info("{} stopped on {}. Visible: {}", module, screenTitle(source()), visibleLabels());
                return;
            }
            if (!fp.equals(previous)) {
                stuck = 0;
            }
            previous = fp;
            LOG.info("{} step: {}", module, action);
            sleep(1100);
        }
    }

    private void openDropdowns(String module, String label) {
        List<Action> dropdowns = new ArrayList<>();
        for (Action action : visibleActions()) {
            String n = normalize(action.label);
            if (n.contains("select") || n.contains("choose") || n.contains("dropdown") || n.equals("dropdown")) {
                dropdowns.add(action);
            }
        }
        int opened = 0;
        for (Action dropdown : dropdowns) {
            if (opened >= 4 || !revealAndTap(dropdown)) {
                continue;
            }
            opened++;
            sleep(500);
            capture(module, module + "-" + slug(label) + "-" + slug(dropdown.label), label + " / " + dropdown.label);
            for (Action option : visibleActions()) {
                if (option.label.equalsIgnoreCase(dropdown.label) || shouldSkip(option.label, source())) {
                    continue;
                }
                tap(option.x, option.y);
                sleep(400);
                break;
            }
        }
    }

    /** Password or OTP login form. The forgot-password page is not this screen. */
    private boolean onLoginForm(String src) {
        if (src.contains("Back to Login")
                && !src.contains("Username or Mobile")
                && !src.contains("Login with password")
                && !src.contains("Don't have an account")) {
            return false;
        }
        return src.contains("Username or Mobile")
                || src.contains("Login with password")
                || src.contains("Don't have an account")
                || (src.contains("Login with OTP") && src.contains("Password"));
    }

    private boolean backToLogin() {
        for (int i = 0; i < 6 && !timedOut(); i++) {
            allowDialogs();
            dismissAlerts();
            String src = source();
            if (onLoginForm(src)) {
                LOG.info("Back on the login screen");
                return true;
            }
            if (isWelcome(src)) {
                tapLabel("Get Started");
                sleep(1000);
                continue;
            }
            boolean auth = onAuthWall(src) || isOtp(src) || onRegistration(src) || isAlert(src)
                    || src.contains("Register") || src.contains("Sign up") || src.contains("Create account");
            if (!auth) {
                LOG.info("Already inside the app — not navigating back to login");
                return false;
            }
            if (!tapLabel("Back to Login", "Back Icon")) {
                try {
                    driver.navigate().back();
                } catch (Exception e) {
                    LOG.debug("Back toward login failed: {}", e.toString());
                }
            }
            sleep(700);
        }
        boolean reached = onLoginForm(source());
        if (!reached) {
            LOG.warn("Login form was not reached. Visible: {}", visibleLabels());
        }
        return reached;
    }

    private boolean openLogin() {
        return backToLogin();
    }

    private void returnToLogin() {
        backToLogin();
    }

    private boolean onRegistration(String src) {
        String n = src.toLowerCase(Locale.ROOT);
        return n.contains("create new account") || n.contains("register now");
    }

    private static boolean isAlert(String src) {
        return src.contains("Dismiss") && (src.contains("Alert") || alreadyRegistered(src));
    }

    private void dismissAlerts() {
        if (!isAlert(source())) {
            return;
        }
        LOG.info("Dismissing the registration alert");
        if (!tapLabel("Dismiss", "OK", "Ok")) {
            tapContaining("Dismiss");
        }
        sleep(700);
    }

    private boolean tapRegisterHere() {
        return tapPhraseInText("Register Here") || tapLabel("Sign up", "Sign Up");
    }

    /** Taps the words themselves. "Register Here" sits at the end of the bottom line. */
    private boolean tapPhraseInText(String phrase) {
        if (phrase == null || phrase.isBlank()) {
            return false;
        }
        String quoted = xpathLiteral(phrase);
        try {
            List<WebElement> nodes = driver.findElements(By.xpath(
                    "//*[contains(@text," + quoted + ") or contains(@content-desc," + quoted + ")]"));
            WebElement best = null;
            int bestBottom = -1;
            for (WebElement node : nodes) {
                if (!node.isDisplayed()) {
                    continue;
                }
                int bottom = node.getRect().y + node.getRect().height;
                if (bottom > bestBottom) {
                    best = node;
                    bestBottom = bottom;
                }
            }
            if (best == null) {
                return false;
            }
            String text = first(best.getAttribute("text"), best.getAttribute("content-desc"));
            int idx = text.toLowerCase(Locale.ROOT).indexOf(phrase.toLowerCase(Locale.ROOT));
            var rect = best.getRect();
            int x = rect.x + rect.width / 2;
            if (idx >= 0 && text.length() > phrase.length()) {
                double mid = (idx + phrase.length() / 2.0) / text.length();
                x = rect.x + (int) Math.round(rect.width * mid);
            }
            int y = rect.y + rect.height / 2;
            LOG.info("Tapping link '{}' at {},{}", phrase, x, y);
            return tap(x, y);
        } catch (Exception e) {
            LOG.debug("Phrase tap failed for {}: {}", phrase, e.toString());
            return false;
        }
    }

    private void openLinkedDetails(String module, String label) {
        Path pages = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y).resolve("pages");
        if (Files.isRegularFile(pages.resolve("register-privacy-policy.json"))
                && Files.isRegularFile(pages.resolve("register-terms-and-conditions.json"))) {
            return;
        }
        String src = source();
        if (src.toLowerCase(Locale.ROOT).contains("privacy policy")) {
            openDetailAndReturn(module, label, "privacy policy");
        }
        if (source().toLowerCase(Locale.ROOT).contains("terms and conditions")) {
            openDetailAndReturn(module, label, "terms and conditions");
        }
    }

    private void openDetailAndReturn(String module, String label, String phrase) {
        if (!openedDetails.add(phrase)) {
            return;
        }
        String before = fingerprint();
        String beforeTitle = screenTitle(source());
        if (!tapPhraseInText(phrase)) {
            return;
        }
        sleep(900);
        if (fingerprint().equals(before)) {
            return;
        }
        String title = screenTitle(source());
        capture(module, module + "-" + slug(phrase), label + " / " + title);
        if (!backUntil(before, beforeTitle)) {
            LOG.warn("Did not return from {}", phrase);
        }
    }

    private void acceptUncheckedBoxes() {
        for (Element el : UiAutomatorXml.elements(source())) {
            if (!UiAutomatorXml.className(el).contains("CheckBox")) {
                continue;
            }
            if ("true".equals(UiAutomatorXml.attr(el, "checked"))) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null) {
                continue;
            }
            tap((box[0] + box[2]) / 2, (box[1] + box[3]) / 2);
            sleep(300);
        }
    }

    private void dismissEntryGates() {
        for (int i = 0; i < 4; i++) {
            allowDialogs();
            if (!tapLabel("Skip for now", "Skip for Now", "Not now", "Maybe later", "Got it")) {
                return;
            }
            sleep(600);
        }
    }

    private static boolean isNewPasswordScreen(String src) {
        String n = src.toLowerCase(Locale.ROOT);
        return n.contains("new password") || n.contains("confirm password")
                || n.contains("set password") || n.contains("create password")
                || n.contains("re-enter password");
    }

    private static boolean alreadyRegistered(String src) {
        String n = src.toLowerCase(Locale.ROOT);
        return n.contains("already registered") || n.contains("already exists")
                || n.contains("account already") || n.contains("mobile number already");
    }

    private static String firstLabeled(String src, List<String> labels) {
        for (String label : labels) {
            if (src.contains(label)) {
                return label;
            }
        }
        return null;
    }

    private boolean enterOtp(String mobile) {
        String otp = context.config().get("vyaapaar.test.otp", "").trim();
        if (otp.isBlank()) {
            otp = NotificationOtpReader.poll(driver, context.config(), mobile, Duration.ofSeconds(35),
                    () -> !isOtp(source()));
        } else {
            LOG.info("Using the OTP from vyaapaar.test.otp");
        }
        if (otp == null || otp.isBlank()) {
            otp = EmailOtpReader.readLatest(driver, context.config(), mobile, appPackage);
        }
        if ((otp == null || otp.isBlank()) && tapLabel("Resend OTP", "Resend", "Resend code")) {
            sleep(3000);
            otp = NotificationOtpReader.poll(driver, context.config(), mobile, Duration.ofSeconds(25),
                    () -> !isOtp(source()));
            if (otp == null || otp.isBlank()) {
                otp = EmailOtpReader.readLatest(driver, context.config(), mobile, appPackage);
            }
        }
        if (otp == null || otp.isBlank()) {
            return false;
        }
        typeOtp(otp);
        sleep(400);
        tapLabel("Verify", "Verify OTP", "Submit", "Continue", "Proceed", "Confirm");
        sleep(2000);
        if (isOtp(source())) {
            LOG.warn("OTP was submitted but the OTP screen is still open");
            return false;
        }
        return true;
    }

    private void typeOtp(String otp) {
        List<WebElement> boxes = driver.findElements(By.xpath("//android.widget.EditText"));
        List<WebElement> visible = new ArrayList<>();
        for (WebElement box : boxes) {
            try {
                if (box.isDisplayed()) {
                    visible.add(box);
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        try {
            if (visible.size() >= otp.length()) {
                for (int i = 0; i < otp.length(); i++) {
                    visible.get(i).click();
                    visible.get(i).sendKeys(otp.substring(i, i + 1));
                }
            } else if (!visible.isEmpty()) {
                visible.get(0).click();
                visible.get(0).sendKeys(otp);
            } else {
                tap(driver.manage().window().getSize().width / 2, driver.manage().window().getSize().height / 2);
                driver.switchTo().activeElement().sendKeys(otp);
            }
            hideKeyboard();
        } catch (Exception e) {
            LOG.warn("Typing OTP failed: {}", e.toString());
        }
    }

    private void crawl(String id, String label, String module, int depth) {
        if (needsBiometric(source())) {
            waitForUserUnlock();
        }
        if (!inApp() && !reopenAfterCrash()) {
            LOG.warn("App is not in front and could not be opened again");
            return;
        }
        if (timedOut() || depth > maxDepth || captures >= maxScreens) {
            return;
        }
        allowDialogs();
        if (source().contains("Exit Registration")) {
            tapLabel("No");
            sleep(400);
        }
        String fp = fingerprint();
        boolean known = finished.contains(fp) || explored.contains(fp);
        if (fp.isBlank() || known) {
            return;
        }
        explored.add(fp);
        if (known) {
            LOG.info("Login screen was already stored — walking it again for registration and OTP");
        }
        try {
            String title = screenTitle(source());
            String screenId = trimId(id);
            capture(module, screenId, label + " — " + title);
            if (needsManualDocument(source())) {
                waitForYourUpload();
            }
            fillIdentityIfSafe();
            if (isOtp(source())) {
                enterOtp(context.config().require("vyaapaar.test.mobile"));
                capture(module, screenId, label);
            }
            if (isPicker(source())) {
                LOG.info("Dropdown {} — storing the open list", title);
                String preferred = preferredOption(source());
                if (preferred != null) {
                    chooseOneOption(module, screenId, label);
                }
                finished.add(fp);
                saveFinished();
                return;
            }
            openEachDropdown(module, screenId, label);
            List<Action> actions = collectActions(module, screenId, label);
            int clicks = 0;
            for (Action action : actions) {
                if (timedOut() || clicks >= maxClicks || captures >= maxScreens) {
                    break;
                }
                if (shouldSkip(action.label, source())) {
                    continue;
                }
                String before = fingerprint();
                String beforeTitle = screenTitle(source());
                if (!revealAndTap(action)) {
                    continue;
                }
                clicks++;
                sleep(350);
                allowDialogs();
                if (needsBiometric(source())) {
                    waitForUserUnlock();
                    continue;
                }
                if (!inApp() && !reopenAfterCrash()) {
                    LOG.warn("Left the app after {} — could not open it again", action.label);
                    break;
                }
                if (isOtp(source())) {
                    LOG.info("OTP screen opened after {} — entering the configured OTP", action.label);
                    String who = context.config().get("vyaapaar.test.username", "").trim();
                    if (who.isBlank()) {
                        who = context.config().get("vyaapaar.test.mobile", "");
                    }
                    enterOtp(who);
                    if (!isOtp(source()) && !onAuthWall(source())) {
                        continue;
                    }
                }
                if (onAuthWall(source()) && !isOtp(source())) {
                    LOG.warn("Login screen opened after {} — signing back in so the rest of the app is scanned",
                            action.label);
                    recoverSignedIn();
                    break;
                }
                if (fingerprint().equals(before)) {
                    continue;
                }
                String childModule = moduleFor(module, action.label, depth);
                if (isToggle(action.label)) {
                    capture(childModule, trimId(screenId + "-" + slug(action.label)), label + " / " + action.label);
                    continue;
                }
                crawl(trimId(screenId + "-" + slug(action.label)),
                        label + " / " + action.label,
                        childModule,
                        depth + 1);
                if (!backUntil(before, beforeTitle)) {
                    LOG.warn("Did not return after {} — scanning the screen that stayed open", action.label);
                    if (needsBiometric(source())) {
                        waitForUserUnlock();
                    }
                    if (inApp() && !onAuthWall(source())) {
                        crawl(trimId(screenId + "-open-" + slug(action.label)),
                                screenTitle(source()),
                                module,
                                depth + 1);
                    }
                    break;
                }
            }
            finished.add(fp);
            saveFinished();
        } catch (Exception e) {
            LOG.warn("Crawl {} failed: {}", label, e.toString());
        }
    }

    /** Opens every dropdown on the page and stores the opened list before the rest of the screen is walked. */
    private void openEachDropdown(String module, String screenId, String label) {
        List<Action> dropdowns = new ArrayList<>();
        Set<String> seen = new LinkedHashSet<>();
        for (Action action : visibleActions()) {
            if (!isDropdownLabel(normalize(action.label)) && !action.label.equalsIgnoreCase("dropdown")) {
                continue;
            }
            if (seen.add(normalize(action.label))) {
                dropdowns.add(action);
            }
        }
        for (Action drop : dropdowns) {
            if (timedOut() || captures >= maxScreens) {
                return;
            }
            String before = fingerprint();
            String beforeTitle = screenTitle(source());
            LOG.info("Opening dropdown {}", drop.label);
            if (!revealAndTap(drop)) {
                continue;
            }
            sleep(500);
            if (needsBiometric(source())) {
                waitForUserUnlock();
                continue;
            }
            if (!inApp() && !reopenAfterCrash()) {
                LOG.warn("Left the app from dropdown {} — could not open it again", drop.label);
                break;
            }
            if (fingerprint().equals(before) && !isPicker(source())) {
                continue;
            }
            String childId = trimId(screenId + "-dropdown-" + slug(drop.label));
            crawl(childId, label + " / " + drop.label, module, 1);
            if (!fingerprint().equals(before) && !backUntil(before, beforeTitle)) {
                LOG.warn("Did not return after dropdown {}", drop.label);
                break;
            }
        }
    }

    private static boolean isDropdownLabel(String n) {
        return n.equals("city") || n.equals("district") || n.equals("state")
                || n.contains("operating hour") || n.contains("gst")
                || n.contains("business type") || n.contains("shop type")
                || n.contains("category") || n.contains("occupation")
                || n.contains("filter") || n.contains("sort")
                || n.equals("status") || n.contains("date range")
                || n.contains("transaction type") || n.contains("service type")
                || n.startsWith("select ") || n.contains("dropdown");
    }

    private boolean isPicker(String src) {
        String title = screenTitle(src).toLowerCase(Locale.ROOT);
        return title.startsWith("select ") || title.startsWith("choose ")
                || src.contains("Select City") || src.contains("Select State");
    }

    private void chooseOneOption(String module, String screenId, String label) {
        String preferred = preferredOption(source());
        if (preferred != null) {
            LOG.info("Choosing {} from the list", preferred);
            typeIntoPickerSearch(preferred);
        }
        for (int pass = 0; pass < 8; pass++) {
            if (preferred != null && tapTextContaining(preferred)) {
                sleep(500);
                capture(module, trimId(screenId + "-" + slug(preferred)), label + " / " + preferred);
                return;
            }
            if (preferred == null) {
                for (Action action : visibleActions()) {
                    if (shouldSkip(action.label, source()) || isChrome(normalize(action.label))) {
                        continue;
                    }
                    String before = fingerprint();
                    if (!revealAndTap(action)) {
                        continue;
                    }
                    sleep(500);
                    if (inApp() && !fingerprint().equals(before)) {
                        capture(module, trimId(screenId + "-" + slug(action.label)), label + " / " + action.label);
                    }
                    return;
                }
            }
            swipeUp();
            sleep(250);
        }
    }

    private String preferredOption(String src) {
        String s = src == null ? "" : src.toLowerCase(Locale.ROOT);
        if (s.contains("select district") || s.contains("choose district")) {
            return context.config().get("vyaapaar.test.city", "Saharanpur");
        }
        if (s.contains("select city") || s.contains("choose city") || s.contains("search city")) {
            return context.config().get("vyaapaar.test.city", "Saharanpur");
        }
        if (s.contains("select state") || s.contains("choose state") || s.contains("search state")) {
            return context.config().get("vyaapaar.test.state", "Uttar Pradesh");
        }
        return null;
    }

    private void typeIntoPickerSearch(String value) {
        try {
            List<WebElement> fields = driver.findElements(By.xpath("//android.widget.EditText"));
            for (WebElement field : fields) {
                if (!field.isDisplayed()) {
                    continue;
                }
                field.click();
                try {
                    field.clear();
                } catch (Exception ignored) {
                    // some fields reject clear
                }
                field.sendKeys(value);
                hideKeyboard();
                sleep(600);
                return;
            }
        } catch (Exception e) {
            LOG.debug("Picker search skipped: {}", e.toString());
        }
    }

    private boolean tapTextContaining(String needle) {
        String want = needle.toLowerCase(Locale.ROOT);
        Element best = null;
        int bestTop = Integer.MAX_VALUE;
        for (Element el : UiAutomatorXml.elements(source())) {
            if (UiAutomatorXml.className(el).contains("EditText")) {
                continue;
            }
            String text = shortLabel(first(
                    UiAutomatorXml.attr(el, "text"),
                    UiAutomatorXml.attr(el, "content-desc"))).toLowerCase(Locale.ROOT);
            if (!text.contains(want)) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null) {
                continue;
            }
            if (box[1] < bestTop) {
                best = el;
                bestTop = box[1];
            }
        }
        if (best == null) {
            return false;
        }
        int[] box = bounds(best);
        tap((box[0] + box[2]) / 2, (box[1] + box[3]) / 2);
        return true;
    }

    private List<Action> collectActions(String module, String screenId, String label) {
        Map<String, Action> all = new LinkedHashMap<>();
        scrollToTop();
        String previous = "";
        for (int i = 0; i <= scrollSwipes; i++) {
            String fp = fingerprint();
            if (!fp.equals(previous)) {
                capture(module, screenId, label);
                previous = fp;
            }
            for (Action action : visibleActions()) {
                all.putIfAbsent(action.key, action);
            }
            if (i == scrollSwipes) {
                break;
            }
            String before = fingerprint();
            swipeUp();
            sleep(150);
            if (fingerprint().equals(before)) {
                break;
            }
        }
        scrollToTop();
        return new ArrayList<>(all.values());
    }

    private void capture(String module, String screenId, String label) {
        String xml = source();
        if (xml.isBlank() || !xml.contains("<hierarchy")) {
            return;
        }
        String id = trimId(screenId);
        boolean fresh = storedIds.add(id);
        inventory.add(module, id, label, PageXpathStore.namedXpaths(xml));
        if (!fresh || captures >= maxScreens) {
            return;
        }
        A11yPageRef ref = A11yPageRef.screen(id, module, label, id);
        var result = AndroidA11yScanner.scan(driver, context.config(), ref);
        A11yScanSession.add(result);
        savePageScore(result);
        captures++;
        LOG.info("Stored {} / {} ({}) score={}", module, id, label, result.score());
        publish(false);
    }

    private String moduleFor(String parent, String label, int depth) {
        String n = normalize(label);
        if (n.contains("setting")) {
            return "settings";
        }
        if (n.contains("profile") || n.equals("account") || n.contains("my account")) {
            return "profile";
        }
        if (depth == 0 && !isChrome(n)) {
            return slug(label);
        }
        return parent;
    }

    private boolean shouldSkip(String label, String src) {
        String n = normalize(label);
        String page = src == null ? "" : src.toLowerCase(Locale.ROOT);
        if (n.isBlank() || n.equals("back") || n.startsWith("back ") || n.equals("navigate up")
                || n.equals("icon")) {
            return true;
        }
        if (n.matches("\\d+") || n.startsWith("digit ")) {
            return true;
        }
        if (n.contains("resend") || n.contains("pay now") || n.equals("pay")
                || n.contains("confirm payment") || n.contains("send money")
                || n.contains("withdraw") || n.contains("transfer now") || n.contains("place order")
                || n.contains("camera") || n.contains("gallery") || n.contains("whatsapp")
                || n.contains("share") || n.contains("play store") || n.equals("call")
                || n.contains("dial") || n.contains("send otp") || n.contains("get otp")
                || n.equals("login") || n.equals("log in") || n.equals("sign in")
                || n.equals("sign up") || n.equals("verify") || n.equals("submit") || n.equals("link")
                || n.contains("unlink") || n.contains("delink") || n.contains("de-link")
                || n.contains("log out") || n.contains("logout") || n.contains("sign out")
                || n.contains("delete account") || n.contains("upload") || n.contains("choose from")
                || n.contains("biometric") || n.contains("fingerprint") || n.contains("face id")
                || n.contains("face unlock") || n.contains("use pin") || n.contains("verify identity")
                || n.equals("notification settings") || n.equals("dark theme")
                || n.contains("http") || n.equals("www") || n.contains("www.")
                || opensDocumentPicker(n)) {
            return true;
        }
        boolean danger = page.contains("are you sure") || page.contains("log out")
                || page.contains("logout") || page.contains("permanently")
                || page.contains("delete account") || page.contains("confirm payment")
                || page.contains("exit registration");
        return danger && (n.equals("yes") || n.equals("confirm") || n.equals("delete")
                || n.contains("log out") || n.contains("logout"));
    }

    private boolean isChrome(String n) {
        return n.equals("back") || n.equals("close") || n.equals("cancel") || n.equals("ok")
                || n.equals("skip") || n.equals("next") || n.equals("continue")
                || n.equals("get started") || n.equals("not now");
    }

    private List<Action> visibleActions() {
        List<Element> nodes = UiAutomatorXml.elements(source());
        Dimension window = driver.manage().window().getSize();
        int sw = Math.max(1, window.width);
        int sh = Math.max(1, window.height);
        List<Action> out = new ArrayList<>();
        Set<String> seen = new LinkedHashSet<>();
        for (Element el : nodes) {
            int[] box = bounds(el);
            if (box == null) {
                continue;
            }
            int w = box[2] - box[0];
            int h = box[3] - box[1];
            if (w < 20 || h < 20) {
                continue;
            }
            int cx = (box[0] + box[2]) / 2;
            int cy = (box[1] + box[3]) / 2;
            if (cy < sh * 0.05 || cy > sh * 0.97) {
                continue;
            }
            if (w > sw * 0.92 && h > sh * 0.40) {
                continue;
            }
            String cls = UiAutomatorXml.className(el);
            if (cls.contains("EditText")) {
                continue;
            }
            boolean clickable = "true".equals(UiAutomatorXml.attr(el, "clickable"));
            String own = shortLabel(first(
                    UiAutomatorXml.attr(el, "content-desc"),
                    UiAutomatorXml.attr(el, "text"),
                    UiAutomatorXml.attr(el, "hint")));
            String label = own.isBlank() && (clickable || cls.contains("Spinner"))
                    ? descendantLabel(nodes, box) : own;
            if (label.isBlank() && cls.contains("Spinner")) {
                label = "dropdown";
            }
            if (label.isBlank() || label.length() > 48) {
                continue;
            }
            if (!clickable && !isCta(label) && !isOnboardingStep(normalize(label))
                    && !isDropdownLabel(normalize(label))
                    && !cls.contains("Spinner") && !cls.contains("Image")) {
                continue;
            }
            if (cls.contains("Image") && own.isBlank() && !clickable) {
                continue;
            }
            String key = normalize(label) + "@" + (cx / 120);
            if (!seen.add(key)) {
                continue;
            }
            out.add(new Action(key, label, cx, cy));
        }
        return out;
    }

    private String descendantLabel(List<Element> nodes, int[] parent) {
        String best = "";
        for (Element el : nodes) {
            int[] box = bounds(el);
            if (box == null || !inside(parent, box)) {
                continue;
            }
            String label = shortLabel(first(
                    UiAutomatorXml.attr(el, "text"),
                    UiAutomatorXml.attr(el, "content-desc"),
                    UiAutomatorXml.attr(el, "hint")));
            if (label.length() < 2 || label.length() > 40) {
                continue;
            }
            if (best.isBlank() || label.length() < best.length()) {
                best = label;
            }
        }
        return best;
    }

    private boolean revealAndTap(Action wanted) {
        scrollToTop();
        for (int i = 0; i < scrollSwipes + 2; i++) {
            for (Action action : visibleActions()) {
                if (action.key.equals(wanted.key) || action.label.equalsIgnoreCase(wanted.label)) {
                    return tap(action.x, action.y) || tapLabel(action.label);
                }
            }
            String before = fingerprint();
            swipeUp();
            sleep(250);
            if (fingerprint().equals(before)) {
                break;
            }
        }
        return tapLabel(wanted.label);
    }

    private boolean tapLabel(String... labels) {
        for (String label : labels) {
            for (Action action : visibleActions()) {
                if (action.label.equalsIgnoreCase(label)) {
                    return tap(action.x, action.y);
                }
            }
            String quoted = xpathLiteral(label);
            try {
                List<WebElement> nodes = driver.findElements(By.xpath(
                        "//*[@text=" + quoted + " or @content-desc=" + quoted + "]"));
                for (WebElement node : nodes) {
                    if (node.isDisplayed()) {
                        node.click();
                        return true;
                    }
                }
            } catch (Exception ignored) {
                // label not on this screen
            }
        }
        return false;
    }

    private boolean tapContaining(String phrase) {
        if (phrase == null || phrase.isBlank()) {
            return false;
        }
        String needle = phrase.toLowerCase(Locale.ROOT);
        for (int attempt = 0; attempt < 3; attempt++) {
            for (Action action : visibleActions()) {
                if (action.label.toLowerCase(Locale.ROOT).contains(needle)) {
                    return tap(action.x, action.y);
                }
            }
            String quoted = xpathLiteral(phrase);
            try {
                List<WebElement> nodes = driver.findElements(By.xpath(
                        "//*[contains(@text," + quoted + ") or contains(@content-desc," + quoted + ")]"));
                for (WebElement node : nodes) {
                    try {
                        if (node.isDisplayed()) {
                            node.click();
                            return true;
                        }
                    } catch (Exception ignored) {
                        // try the next node
                    }
                }
            } catch (Exception ignored) {
                // phrase not in the hierarchy
            }
            if (attempt < 2) {
                swipeUp();
                sleep(350);
            }
        }
        return false;
    }

    private String visibleLabels() {
        List<String> labels = new ArrayList<>();
        for (Action action : visibleActions()) {
            if (labels.size() >= 12) {
                break;
            }
            labels.add(action.label);
        }
        return labels.toString();
    }

    private String loginAction(String src) {
        if (src.contains("Password") && src.contains("Login with OTP")) {
            return "Login with OTP";
        }
        if (src.contains("Get Started") && src.contains("airpay vyaapaar")) {
            return "Get Started";
        }
        String mobile = context.config().get("vyaapaar.test.mobile", "");
        if (isMobileEntry(src) && src.contains(mobile)) {
            if (src.contains("Get OTP")) {
                return "Get OTP";
            }
            if (src.contains("Send OTP")) {
                return "Send OTP";
            }
            if (src.contains("Login")) {
                return "Login";
            }
            if (src.contains("Continue")) {
                return "Continue";
            }
        }
        for (String label : LOGIN_ACTIONS) {
            if (src.contains(label)) {
                return label;
            }
        }
        return null;
    }

    private boolean onSignedInHome(String src) {
        return src != null && src.contains("Featured Services");
    }

    /**
     * Signs in again when a signed-in crawl falls back to the login wall.
     * Capped so a screen that always logs out cannot loop. Never logs the password.
     */
    private boolean recoverSignedIn() {
        if (isOtp(source())) {
            String who = context.config().get("vyaapaar.test.username", "").trim();
            if (who.isBlank()) {
                who = context.config().get("vyaapaar.test.mobile", "");
            }
            LOG.info("OTP screen is open — entering the configured OTP");
            if (enterOtp(who) && !isOtp(source()) && !onAuthWall(source())) {
                LOG.info("OTP accepted — scan continues");
                return true;
            }
        }
        if (signInRecoveries >= 3) {
            LOG.warn("Signed-in session dropped again — not logging in a fourth time");
            return false;
        }
        if (!Boolean.getBoolean("a11y.login.password")
                || context.config().get("vyaapaar.test.password", "").isBlank()) {
            return false;
        }
        signInRecoveries++;
        LOG.warn("Signed-in session dropped to the login screen — signing in again ({})", signInRecoveries);
        try {
            loginWithUsernameAndPassword();
        } catch (Exception e) {
            LOG.warn("Could not sign back in: {}", e.toString());
            return false;
        }
        if (onAuthWall(source())) {
            LOG.warn("Still on the login screen after signing in again");
            return false;
        }
        LOG.info("Signed back in — scan continues");
        return true;
    }

    /** Backs out of a module until the signed-in home tabs are visible. */
    private void returnToSignedInHome() {
        for (int i = 0; i < 6 && inApp(); i++) {
            String src = source();
            if (onSignedInHome(src)) {
                LOG.info("Back on the signed-in home");
                return;
            }
            if (onAuthWall(src)) {
                if (recoverSignedIn()) {
                    continue;
                }
                LOG.warn("Login wall while returning home — leaving the screen as it is");
                return;
            }
            String lower = src.toLowerCase(Locale.ROOT);
            if (src.contains("Exit Registration") || lower.contains("are you sure") || lower.contains("do you want to exit")) {
                tapLabel("No", "Cancel");
                sleep(400);
                continue;
            }
            LOG.info("Leaving {} to reach home", screenTitle(src));
            if (!tapLabel("Back Icon", "Back")) {
                try {
                    driver.navigate().back();
                } catch (Exception e) {
                    LOG.debug("Back failed: {}", e.toString());
                    return;
                }
            }
            sleep(600);
        }
    }

    private void fillIdentityIfSafe() {
        if (Boolean.getBoolean("a11y.skip.fill")) {
            return;
        }
        fillIdentity();
    }

    private boolean onAuthWall(String src) {
        if (src == null || src.isBlank()) {
            return false;
        }
        if (isWelcome(src) || isOtp(src)) {
            return true;
        }
        if (src.contains("Login with OTP") || src.contains("Login with password") || src.contains("Forgot Password")) {
            return true;
        }
        String n = src.toLowerCase(Locale.ROOT);
        return n.contains("login") && (n.contains("mpin") || n.contains("password"));
    }

    private boolean isToggle(String label) {
        String n = normalize(label);
        return n.contains("visibility") || n.contains("illustration");
    }

    private void fillIdentity() {
        String mobile = context.config().get("vyaapaar.test.mobile", "");
        String name = context.config().get("vyaapaar.test.name", "");
        String email = context.config().get("vyaapaar.test.email", "");
        String upi = context.config().get("vyaapaar.test.upi", "");
        String pan = context.config().get("vyaapaar.test.pan", "");
        String pin = context.config().get("vyaapaar.test.pin", "");
        String city = context.config().get("vyaapaar.test.city", "");
        String src = source();
        typeIntoFields(src, mobile, name, email, upi, pan, pin, city);
        if (!mobile.isBlank() && isMobileEntry(src) && !source().contains(mobile)) {
            typeBelowLabel(mobile, "Mobile Number", "Username or Mobile number", "Username");
        }
        if (!email.isBlank() && source().toLowerCase(Locale.ROOT).contains("email") && !source().contains(email)) {
            typeBelowLabel(email, "Email", "Email ID", "Email address");
        }
        if (!name.isBlank() && source().toLowerCase(Locale.ROOT).contains("name") && !source().contains(name)) {
            typeBelowLabel(name, "Full Name", "Name", "Business Name", "Merchant Name", "Store/Company Name");
        }
        if (!pan.isBlank() && !source().contains(pan)) {
            typeBelowLabel(pan, "PAN", "PAN Number", "Pan Number", "Enter PAN");
        }
        if (!pin.isBlank() && !source().contains(pin)) {
            typeBelowLabel(pin, "Pin Code", "PIN Code", "Pincode", "PIN", "Postal Code");
        }
        if (!city.isBlank() && source().toLowerCase(Locale.ROOT).contains("district") && !source().contains(city)) {
            typeBelowLabel(city, "District");
        }
    }

    private void typeIntoFields(String src, String mobile, String name, String email, String upi, String pan, String pin, String city) {
        List<Element> nodes = UiAutomatorXml.elements(src);
        for (WebElement field : driver.findElements(By.xpath("//android.widget.EditText"))) {
            try {
                if (!field.isDisplayed()) {
                    continue;
                }
                String blob = (field.getAttribute("hint") + " " + field.getAttribute("text") + " "
                        + field.getAttribute("content-desc") + " " + labelAbove(field, nodes)).toLowerCase(Locale.ROOT);
                String value = valueFor(blob, mobile, name, email, upi, pan, pin, city);
                if (value == null || blob.contains(value.toLowerCase(Locale.ROOT))) {
                    continue;
                }
                field.click();
                try {
                    field.clear();
                } catch (Exception ignored) {
                    // some fields reject clear
                }
                field.sendKeys(value);
                hideKeyboard();
            } catch (Exception ignored) {
                // stale field
            }
        }
    }

    private void typeBelowLabel(String value, String... labels) {
        List<Element> nodes = UiAutomatorXml.elements(source());
        for (Element el : nodes) {
            String text = shortLabel(first(UiAutomatorXml.attr(el, "text"), UiAutomatorXml.attr(el, "content-desc")));
            for (String label : labels) {
                if (!text.equalsIgnoreCase(label)) {
                    continue;
                }
                int[] box = bounds(el);
                if (box == null) {
                    continue;
                }
                Element field = editTextBelow(nodes, box);
                int[] target = field == null ? null : bounds(field);
                int x = target == null ? (box[0] + box[2]) / 2 : (target[0] + target[2]) / 2;
                int y = target == null ? box[3] + 48 : (target[1] + target[3]) / 2;
                tap(x, y);
                sleep(300);
                try {
                    WebElement focused = driver.switchTo().activeElement();
                    try {
                        focused.clear();
                    } catch (Exception ignored) {
                        // some fields reject clear
                    }
                    focused.sendKeys(value);
                } catch (Exception e) {
                    AdbCommands.shell(context.config().get("android.udid", ""), "input", "text", value.replace(" ", "%s"));
                }
                hideKeyboard();
                return;
            }
        }
    }

    private Element editTextBelow(List<Element> nodes, int[] labelBox) {
        Element best = null;
        int bestGap = Integer.MAX_VALUE;
        int center = (labelBox[0] + labelBox[2]) / 2;
        // Floating-label layouts put the label INSIDE its field — that field wins.
        for (Element el : nodes) {
            if (!UiAutomatorXml.className(el).contains("EditText")) {
                continue;
            }
            int[] box = bounds(el);
            if (box != null && box[0] <= labelBox[0] && box[1] <= labelBox[1]
                    && box[2] >= labelBox[2] && box[3] >= labelBox[3]) {
                return el;
            }
        }
        for (Element el : nodes) {
            if (!UiAutomatorXml.className(el).contains("EditText")) {
                continue;
            }
            int[] box = bounds(el);
            if (box == null || box[1] < labelBox[3] - 8) {
                continue;
            }
            if (center < box[0] - 80 || center > box[2] + 80) {
                continue;
            }
            int gap = box[1] - labelBox[3];
            if (gap >= 0 && gap < bestGap && gap < 220) {
                best = el;
                bestGap = gap;
            }
        }
        return best;
    }

    private String labelAbove(WebElement field, List<Element> nodes) {
        try {
            int top = field.getRect().getY();
            int center = field.getRect().getX() + field.getRect().getWidth() / 2;
            String best = "";
            int bestGap = Integer.MAX_VALUE;
            for (Element el : nodes) {
                int[] box = bounds(el);
                if (box == null || box[3] > top || box[3] < top - 160) {
                    continue;
                }
                if (center < box[0] - 40 || center > box[2] + 40) {
                    continue;
                }
                String text = shortLabel(first(UiAutomatorXml.attr(el, "text"), UiAutomatorXml.attr(el, "content-desc")));
                int gap = top - box[3];
                if (!text.isBlank() && gap < bestGap) {
                    best = text;
                    bestGap = gap;
                }
            }
            return best;
        } catch (Exception e) {
            return "";
        }
    }

    private static String valueFor(String blob, String mobile, String name, String email,
                                   String upi, String pan, String pin, String city) {
        if ((blob.contains("upi id") || blob.contains("vpa") || blob.contains("upi address"))
                && upi != null && !upi.isBlank()) {
            return upi;
        }
        if (blob.contains("otp") || blob.contains("aadhaar") || blob.contains("aadhar")
                || blob.contains("cvv") || blob.contains("card") || blob.contains("ifsc")
                || blob.contains("account number") || blob.contains("upi")) {
            return null;
        }
        if ((blob.contains("password") || blob.contains("mpin")) && !blob.contains("forgot")) {
            return null;
        }
        if (blob.contains("pan")) {
            return pan;
        }
        if (blob.contains("pin") || blob.contains("postal")) {
            return pin;
        }
        if (blob.contains("district")) {
            return city;
        }
        if (blob.contains("email")) {
            return email;
        }
        if (blob.contains("mobile") || blob.contains("phone") || blob.contains("username")) {
            return mobile;
        }
        if (blob.contains("name") || blob.contains("merchant") || blob.contains("business")
                || blob.contains("shop")) {
            return name;
        }
        return null;
    }

    private boolean backUntil(String fp, String title) {
        for (int i = 0; i < 3; i++) {
            if (needsBiometric(source())) {
                waitForUserUnlock();
            }
            if (!inApp() && !reopenAfterCrash()) {
                LOG.warn("Left the app while going back — could not open it again");
                return false;
            }
            String src = source();
            if (onAuthWall(src)) {
                if (recoverSignedIn()) {
                    continue;
                }
                LOG.warn("Reached the login screen while going back — not leaving the app");
                return false;
            }
            if (fp.equals(fingerprint())) {
                return true;
            }
            if (title != null && title.equalsIgnoreCase(screenTitle(src)) && !isPicker(src)) {
                return true;
            }
            if (isShopForm(src) && isShopFormTitle(title)) {
                return true;
            }
            if (src.contains("Exit Registration")) {
                if (isShopFormTitle(title)) {
                    tapLabel("No");
                    sleep(400);
                    return true;
                }
                tapLabel("Yes");
                sleep(500);
                continue;
            }
            if (onboardingRoot(src)) {
                return onboardingRootTitle(title);
            }
            if (!tapLabel("Back Icon")) {
                try {
                    driver.navigate().back();
                } catch (Exception e) {
                    LOG.debug("Back failed: {}", e.toString());
                }
            }
            sleep(300);
        }
        return fp.equals(fingerprint()) && inApp() && !onAuthWall(source());
    }

    /** Leaves a form for the screen behind it. Shop progress is kept. */
    private boolean stepBackToParent() {
        if (!inApp() || onAuthWall(source())) {
            return false;
        }
        if (!tapLabel("Back Icon")) {
            try {
                driver.navigate().back();
            } catch (Exception e) {
                LOG.debug("Back failed: {}", e.toString());
                return false;
            }
        }
        sleep(400);
        if (source().contains("Exit Registration")) {
            tapLabel("Yes");
            sleep(500);
        }
        return inApp() && !onAuthWall(source());
    }

    private boolean onboardingRoot(String src) {
        return src.contains("Onboarding Progress")
                || (src.contains("Welcome to airpay vyaapaar") && src.contains("Type Selection"));
    }

    private boolean isShopForm(String src) {
        if (src == null || isPicker(src) || src.contains("Exit Registration")) {
            return false;
        }
        String s = src.toLowerCase(Locale.ROOT);
        return s.contains("shop details") || s.contains("store details")
                || (s.contains("pin code") && (s.contains("pan") || s.contains("gst")));
    }

    private boolean isShopFormTitle(String title) {
        if (title == null) {
            return false;
        }
        String n = normalize(title);
        return n.contains("shop details") || n.contains("store details") || n.contains("company name");
    }

    private boolean onboardingRootTitle(String title) {
        if (title == null) {
            return false;
        }
        String n = normalize(title);
        return n.contains("welcome to airpay vyaapaar") || n.contains("onboarding") || n.contains("type selection");
    }

    /** Checklist rows such as Upload Dashboard stay tappable. The file picker itself is left for you. */
    private boolean needsManualDocument(String src) {
        if (src == null || src.isBlank() || onboardingRoot(src) || onAuthWall(src)) {
            return false;
        }
        String s = src.toLowerCase(Locale.ROOT);
        return s.contains("upload document") || s.contains("upload photo")
                || s.contains("upload your") || s.contains("upload kyc")
                || s.contains("choose document") || s.contains("select document")
                || s.contains("attach document") || s.contains("upload dashboard")
                || (s.contains("upload") && (s.contains("camera") || s.contains("gallery") || s.contains("browse")));
    }

    private boolean opensDocumentPicker(String n) {
        return n.contains("camera") || n.contains("gallery") || n.contains("choose file")
                || n.contains("browse") || n.contains("attach") || n.contains("take photo")
                || n.contains("select file") || n.contains("upload photo")
                || n.contains("pick document") || n.contains("choose document")
                || n.contains("select document") || n.contains("add document")
                || n.contains("add photo") || n.equals("upload");
    }

    private void waitForYourUpload() {
        LOG.info("Document upload is open. Upload the document on the phone. The app stays open and the screen stays awake.");
        String start = fingerprint();
        long until = System.currentTimeMillis() + 120_000L;
        long nextLog = 0;
        while (System.currentTimeMillis() < until) {
            keepAwake();
            if (System.currentTimeMillis() >= nextLog) {
                LOG.info("Waiting for the document upload — phone stays awake");
                nextLog = System.currentTimeMillis() + 15_000L;
            }
            sleep(2000);
            String now = source();
            if (!fingerprint().equals(start) && !needsManualDocument(now)) {
                LOG.info("Document upload changed — continuing the scan");
                return;
            }
        }
        LOG.info("Continuing past the upload screen without opening the file picker");
    }

    private void keepAwake() {
        String udid = context.config().get("android.udid", "");
        if (!awakePinned) {
            AdbCommands.shell(udid, "svc", "power", "stayon", "true");
            AdbCommands.shell(udid, "settings", "put", "system", "screen_off_timeout", "1800000");
            AdbCommands.shell(udid, "settings", "put", "global", "stay_on_while_plugged_in", "7");
            awakePinned = true;
        }
        AdbCommands.shell(udid, "input", "keyevent", "224");
    }

    private void resetFinishedForThisPass() {
        finished.clear();
        explored.clear();
        try {
            Files.deleteIfExists(sessionFile());
        } catch (Exception e) {
            LOG.warn("Could not clear the saved session: {}", e.toString());
        }
    }

    private void bringAppBack() {
        if (needsBiometric(source())) {
            waitForUserUnlock();
            return;
        }
        reopenAfterCrash();
    }

    private void leaveForeignApp() {
        if (needsBiometric(source())) {
            waitForUserUnlock();
            return;
        }
        reopenAfterCrash();
    }

    /** Crash dialog or a dead process. Opens the app again, then waits if fingerprint, face, or PIN is showing. */
    private boolean reopenAfterCrash() {
        if (inApp()) {
            return true;
        }
        if (needsBiometric(source())) {
            waitForUserUnlock();
            return inApp();
        }
        dismissCrashDialog();
        if (crashReopens >= 3) {
            LOG.warn("App closed again — not opening it a fourth time");
            return false;
        }
        crashReopens++;
        LOG.warn("App crashed or closed — opening it again ({})", crashReopens);
        openAppForScan();
        if (needsBiometric(source())) {
            waitForUserUnlock();
        }
        if (!inApp()) {
            return false;
        }
        if (isOtp(source()) || onAuthWall(source())) {
            LOG.info("App opened on login after the crash — signing in again");
            String who = context.config().get("vyaapaar.test.username", "").trim();
            if (who.isBlank()) {
                who = context.config().get("vyaapaar.test.mobile", "");
            }
            if (isOtp(source())) {
                enterOtp(who);
            }
            if (onAuthWall(source()) && !isOtp(source())) {
                loginWithUsernameAndPassword();
            }
        }
        return inApp();
    }

    private void dismissCrashDialog() {
        String n = source().toLowerCase(Locale.ROOT);
        if (!n.contains("has stopped") && !n.contains("keeps stopping") && !n.contains("isn't responding")) {
            return;
        }
        LOG.warn("App crash dialog is on screen — closing it");
        tapLabel("Close app", "Close", "OK");
        sleep(600);
    }

    private boolean inApp() {
        String pkg = currentPackage();
        if (appPackage.equals(pkg)) {
            return true;
        }
        String src = source();
        if (src.contains(appPackage) || src.contains("Featured Services") || src.contains("Credit Balance")) {
            return true;
        }
        if (pkg.contains("permission") || pkg.contains("packageinstaller")) {
            allowDialogs();
            return appPackage.equals(currentPackage());
        }
        return false;
    }

    private String currentPackage() {
        try {
            String pkg = driver.getCurrentPackage();
            return pkg == null ? "" : pkg;
        } catch (Exception e) {
            return "";
        }
    }

    private boolean isWelcome(String src) {
        return src.contains("airpay vyaapaar") && src.contains("Get Started");
    }

    private boolean isOtp(String src) {
        String n = src.toLowerCase(Locale.ROOT);
        return n.contains("enter the otp") || n.contains("enter otp") || n.contains("verification code")
                || n.contains("otp sent") || n.contains("one time password") || n.contains("resend otp");
    }

    private boolean isMobileEntry(String src) {
        String n = src.toLowerCase(Locale.ROOT);
        return (n.contains("mobile number") || n.contains("phone number") || n.contains("enter mobile"))
                && !isOtp(src);
    }

    private String screenTitle(String src) {
        if (isWelcome(src)) {
            return "Welcome";
        }
        if (isOtp(src)) {
            return src.toLowerCase(Locale.ROOT).contains("email") ? "Email OTP" : "OTP";
        }
        for (Element el : UiAutomatorXml.elements(src)) {
            String label = shortLabel(first(
                    UiAutomatorXml.attr(el, "text"),
                    UiAutomatorXml.attr(el, "content-desc")));
            String n = normalize(label);
            if (label.length() < 3 || label.length() > 40 || n.equals("get started")) {
                continue;
            }
            if (n.endsWith("icon") || n.endsWith("illustration") || n.matches("\\d+.*")) {
                continue;
            }
            return label;
        }
        return "Screen";
    }

    private void allowDialogs() {
        try {
            PermissionDialogHandler.allowAll(driver, Duration.ofMillis(700));
        } catch (Exception ignored) {
            // none showing
        }
    }

    private String fingerprint() {
        List<String> names = new ArrayList<>();
        for (Element el : UiAutomatorXml.elements(source())) {
            String name = shortLabel(first(
                    UiAutomatorXml.attr(el, "content-desc"),
                    UiAutomatorXml.attr(el, "text"),
                    UiAutomatorXml.attr(el, "hint")));
            if (!name.isBlank()) {
                names.add(name.toLowerCase(Locale.ROOT));
            }
        }
        names.sort(String::compareTo);
        String activity = "";
        try {
            activity = String.valueOf(driver.currentActivity());
        } catch (Exception ignored) {
            // keep names only
        }
        return sha(activity + "|" + String.join("|", names));
    }

    private String source() {
        try {
            String src = driver.getPageSource();
            return src == null ? "" : src;
        } catch (Exception e) {
            return "";
        }
    }

    private boolean tap(int x, int y) {
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);
            tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(tap));
            return true;
        } catch (Exception e) {
            LOG.debug("Tap failed: {}", e.toString());
            return false;
        }
    }

    private void swipeUp() {
        Dimension size = driver.manage().window().getSize();
        swipe(size.width / 2, (int) (size.height * 0.62), size.width / 2, (int) (size.height * 0.40));
    }

    private void scrollToTop() {
        String previous = "";
        for (int i = 0; i < 4; i++) {
            String fp = fingerprint();
            if (fp.equals(previous)) {
                return;
            }
            previous = fp;
            Dimension size = driver.manage().window().getSize();
            swipe(size.width / 2, (int) (size.height * 0.32), size.width / 2, (int) (size.height * 0.78));
            sleep(200);
        }
    }

    private void swipe(int x1, int y1, int x2, int y2) {
        try {
            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x1, y1));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(280), PointerInput.Origin.viewport(), x2, y2));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(List.of(swipe));
        } catch (Exception e) {
            LOG.debug("Swipe failed: {}", e.toString());
        }
    }

    private void hideKeyboard() {
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // already hidden
        }
    }

    private void publish(boolean archive) {
        try {
            com.airpay.mobile.utilities.accessibility.A11yScorecardWriter.write(
                    context.config(),
                    appPackage,
                    A11yScanSession.results(),
                    ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y),
                    "vyaapaar",
                    "airpay vyaapaar Android",
                    archive);
        } catch (Exception e) {
            LOG.warn("Scorecard write failed: {}", e.toString());
        }
    }

    private void savePageScore(com.airpay.mobile.utilities.accessibility.A11yPageScanResult result) {
        try {
            Path dir = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y).resolve("pages");
            Files.createDirectories(dir);
            String json = com.airpay.mobile.utilities.accessibility.A11yJson.reportDocument(
                    appPackage,
                    java.time.Instant.now().toString(),
                    List.of(result),
                    result.page().id());
            Files.writeString(dir.resolve(result.page().id() + ".json"), json);
        } catch (Exception e) {
            LOG.warn("Page scorecard failed for {}: {}", result.page().id(), e.toString());
        }
    }

    private void writeScreenList() {
        try {
            Path out = ProjectPaths.resolveKind(ProjectPaths.KIND_A11Y).resolve("vyaapaar-pages-scanned.txt");
            Files.createDirectories(out.getParent());
            StringBuilder sb = new StringBuilder();
            sb.append("Vyaapaar screens stored (").append(inventory.pageCount()).append(")\n\n");
            int i = 1;
            for (String id : inventory.screenIds()) {
                sb.append(i++).append(". ").append(id).append('\n');
            }
            Files.writeString(out, sb.toString());
        } catch (Exception e) {
            LOG.warn("Screen list failed: {}", e.toString());
        }
    }

    private Path sessionFile() {
        return ProjectPaths.reportsRoot().resolve("vyaapaar").resolve("session").resolve("finished-fingerprints.txt");
    }

    private void loadFinished() {
        try {
            Path file = sessionFile();
            if (!Files.isRegularFile(file)) {
                return;
            }
            for (String line : Files.readAllLines(file)) {
                if (!line.isBlank()) {
                    finished.add(line.trim());
                    explored.add(line.trim());
                }
            }
            LOG.info("Restored {} finished screens from the saved session", finished.size());
        } catch (Exception e) {
            LOG.warn("Could not read the saved session: {}", e.toString());
        }
    }

    private void saveFinished() {
        try {
            Path file = sessionFile();
            Files.createDirectories(file.getParent());
            Files.writeString(file, String.join("\n", finished) + "\n");
        } catch (Exception e) {
            LOG.warn("Could not store the session: {}", e.toString());
        }
    }

    private boolean timedOut() {
        return System.currentTimeMillis() >= deadline;
    }

    private static boolean isOnboardingStep(String n) {
        return n.contains("type selection") || n.contains("business details")
                || n.contains("upload dashboard") || n.contains("personal details")
                || n.contains("e-sign") || n.contains("onboarding complete")
                || n.contains("shop details") || n.contains("store details");
    }

    private static boolean isCta(String label) {
        String n = normalize(label);
        return n.equals("get started") || n.equals("continue") || n.equals("next") || n.equals("skip")
                || n.equals("send otp") || n.equals("get otp") || n.equals("login") || n.equals("log in")
                || n.equals("verify") || n.equals("submit") || n.equals("apply") || n.equals("apply now")
                || n.equals("ok") || n.equals("close") || n.equals("save") || n.equals("done")
                || n.equals("filter") || n.equals("view all") || n.equals("see all");
    }

    private static boolean inside(int[] parent, int[] child) {
        return child[0] >= parent[0] && child[1] >= parent[1]
                && child[2] <= parent[2] && child[3] <= parent[3]
                && (child[2] - child[0]) * (child[3] - child[1])
                < (parent[2] - parent[0]) * (parent[3] - parent[1]);
    }

    private static int[] bounds(Element el) {
        Matcher matcher = BOUNDS.matcher(UiAutomatorXml.attr(el, "bounds"));
        if (!matcher.find()) {
            return null;
        }
        return new int[]{
                Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)),
                Integer.parseInt(matcher.group(4))
        };
    }

    private static String shortLabel(String raw) {
        if (raw == null) {
            return "";
        }
        String first = raw.split("\n")[0].trim();
        int dot = first.indexOf('.');
        if (dot > 0 && dot <= 42) {
            first = first.substring(0, dot).trim();
        }
        return first.length() > 80 ? first.substring(0, 80).trim() : first;
    }

    private static String first(String... values) {
        return UiAutomatorXml.firstNonBlank(values);
    }

    private static String normalize(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", " ").trim();
    }

    private static String slug(String value) {
        String slug = value == null ? "" : value.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-");
        slug = slug.replaceAll("^-|-$", "");
        return slug.isBlank() ? "screen" : slug;
    }

    private static String trimId(String id) {
        String clean = slug(id);
        return clean.length() > 90 ? clean.substring(0, 90).replaceAll("-$", "") : clean;
    }

    private static String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        return "concat('" + value.replace("'", "',\"'\",'") + "')";
    }

    private static String sha(String value) {
        try {
            byte[] dig = MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(dig).substring(0, 16);
        } catch (Exception e) {
            return Integer.toHexString(value.hashCode());
        }
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private record Action(String key, String label, int x, int y) {
    }
}
