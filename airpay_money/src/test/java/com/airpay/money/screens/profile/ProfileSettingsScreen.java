package com.airpay.money.screens.profile;

import com.airpay.money.data.MoneyExcel;
import com.airpay.money.hooks.ProfileNavigationGate;
import com.airpay.money.screens.auth.BiometricScreen;
import com.airpay.money.screens.auth.PinUnlockScreen;
import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Profile settings from the home avatar. Update, Send Message, and Log Out Yes are not tapped.
 * Change PIN is not opened. When in-app Back does not leave a page, system Back is used once.
 */
public class ProfileSettingsScreen extends BaseScreen {

    private static final Logger LOG = LogManager.getLogger(ProfileSettingsScreen.class);

    /** The heading scrolls out of the accessibility tree once the policy is scrolled. */
    private boolean privacySeen;

    public ProfileSettingsScreen(TestContext context) {
        super(context);
    }

    public boolean open() {
        if (!ProfileNavigationGate.allowed()) {
            return false;
        }
        if (settingsDisplayed()) {
            return true;
        }
        WebElement settings = displayed("Open settings");
        if (settings == null) {
            settings = hubAvatar();
        }
        if (settings == null && waitUntil(() -> isTabHub(safeSource()), Duration.ofSeconds(5))) {
            settings = hubAvatar();
        }
        if (settings != null) {
            pressElement(settings);
            return waitUntil(this::settingsDisplayed, Duration.ofSeconds(5));
        }
        return settingsDisplayed();
    }

    public boolean settingsDisplayed() {
        return text().toUpperCase(Locale.ROOT).contains("PROFILE SETTINGS");
    }

    /**
     * Profile Settings list or a nested profile page (Documents, Personal Details, Work).
     * FHC Personal Details is not this tree — that page has Gender + Pincode, not Edit personal details.
     */
    public boolean onProfileTree() {
        String src = text();
        String upper = src.toUpperCase(Locale.ROOT);
        return upper.contains("PROFILE SETTINGS")
                || src.contains("My Documents")
                || src.contains("Document Details")
                || src.contains("Work Profile")
                || src.contains("No Document found")
                || (src.contains("Log Out") && src.contains("Contact Us"))
                || (src.contains("Personal Details") && src.contains("Edit personal details"));
    }

    /** Wait out Please Wait / spinner after Back or Open settings. */
    public boolean waitSettingsReady() {
        return waitUntil(() -> {
            String src = text().toLowerCase(Locale.ROOT);
            return !src.contains("please wait") && !src.contains("progressbar") && settingsDisplayed();
        }, Duration.ofSeconds(5));
    }

    public boolean openRow(String label) {
        if (!ProfileNavigationGate.allowed()) {
            return false;
        }
        boolean fromSettings = settingsDisplayed();
        if (!pressExact(label)) {
            return false;
        }
        if ("Log Out".equalsIgnoreCase(label)) {
            return true;
        }
        // Flutter taps can miss; confirm we left the PROFILE SETTINGS list.
        if (fromSettings) {
            waitUntil(() -> !settingsDisplayed(), Duration.ofSeconds(3));
            if (settingsDisplayed()) {
                pressExact(label);
                waitUntil(() -> !settingsDisplayed(), Duration.ofSeconds(3));
            }
            return !settingsDisplayed();
        }
        return true;
    }

    public boolean openContaining(String label) {
        if (!ProfileNavigationGate.allowed()) {
            return false;
        }
        for (int i = 0; i < 6; i++) {
            WebElement target = firstPresent(Duration.ofMillis(700),
                    MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(label) + ") or contains(@text," + xpathLiteral(label) + ")]"));
            if (target != null) {
                pressElement(target);
                sleep(800);
                return true;
            }
            swipeUp();
        }
        return false;
    }

    /** My Documents row is labelled "Pan card" (2.1.4). */
    public boolean openPanCard() {
        if (!ProfileNavigationGate.allowed()) {
            return false;
        }
        if (tapFirstAny(Duration.ofSeconds(3),
                accessibility("Pan card"),
                accessibility("PAN Card"),
                MobileWaits.xpath("//*[@content-desc='Pan card' or @text='Pan card']"),
                MobileWaits.xpath("//*[contains(translate(@content-desc,'PANCARD','pancard'),'pan card')"
                        + " or contains(translate(@text,'PANCARD','pancard'),'pan card')]"))) {
            sleep(900);
            return true;
        }
        return openContaining("Pan card") || openContaining("PAN Card");
    }

    public boolean uploadedDocumentDisplayed() {
        String src = text();
        return src.contains("My Documents") && !src.contains("No Document found");
    }

    public boolean panDetailsDisplayed() {
        return waitUntil(() -> {
            String src = text();
            boolean last4 = panLast4Visible(src);
            boolean image = src.contains("ImageView") || src.contains("Image")
                    || firstPresent(Duration.ofMillis(400),
                    MobileWaits.xpath("//android.widget.ImageView"),
                    MobileWaits.xpath("//android.widget.Image")) != null
                    || (last4 && !src.contains("Aadhaar card"));
            return last4 && image;
        }, actionTimeout());
    }

    private boolean panLast4Visible(String src) {
        if (src.matches("(?s).*[xX]{3,}[A-Za-z0-9]{4}.*")) {
            return true;
        }
        String configured = context.config().get("money.test.pan", "").replaceAll("\\D", "");
        return configured.length() >= 4 && src.contains(configured.substring(configured.length() - 4));
    }

    public boolean titleNearTop(String label) {
        WebElement node = displayed(label);
        if (node == null) {
            node = firstPresent(Duration.ofSeconds(3),
                    MobileWaits.xpath("//*[contains(translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),"
                            + xpathLiteral(label.toLowerCase(Locale.ROOT))
                            + ") or contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),"
                            + xpathLiteral(label.toLowerCase(Locale.ROOT)) + ")]"));
        }
        return node != null && node.getRect().getY() < 500;
    }

    public boolean shows(String label) {
        return text().toLowerCase(Locale.ROOT).contains(label.toLowerCase(Locale.ROOT));
    }

    public boolean editPersonalDisplayed() {
        String src = text().toLowerCase(Locale.ROOT);
        return src.contains("full name") && src.contains("father") && src.contains("mother");
    }

    public boolean editFieldsPrefilled() {
        for (String xpath : List.of(ProfileDetailsScreen.FULL_NAME,
                ProfileDetailsScreen.FATHER_NAME, ProfileDetailsScreen.MOTHER_NAME)) {
            WebElement field = firstPresent(Duration.ofSeconds(2), MobileWaits.xpath(xpath));
            if (field == null) {
                return false;
            }
            String value = String.valueOf(field.getAttribute("text")).trim();
            String hint = String.valueOf(field.getAttribute("hint")).trim();
            if ("null".equals(value) || value.isBlank() || value.equals(hint)) {
                return false;
            }
        }
        return true;
    }

    public boolean personalDetailsAgain() {
        if (onEditPersonalForm()) {
            backFromEditToPersonalDetails();
        }
        return onPersonalDetailsView();
    }

    /** Personal Details view: has the "Edit personal details" pencil, no Update button. */
    private boolean onPersonalDetailsView() {
        String src = text();
        return src.contains("content-desc=\"Edit personal details\"") && !src.contains("content-desc=\"Update\"");
    }

    /** The pencil is on screen without scrolling. */
    public boolean tapEditPencil() {
        if (onEditPersonalForm()) {
            return true;
        }
        if (!tapFirstAny(Duration.ofSeconds(3), MobileWaits.xpath(ProfileDetailsScreen.PERSONAL_EDIT))) {
            return false;
        }
        return waitUntil(this::onEditPersonalForm, Duration.ofSeconds(5));
    }

    private boolean onEditPersonalForm() {
        String src = text();
        return src.contains("content-desc=\"Update\"") && src.contains("Full name");
    }

    /**
     * In-app Back first. When the Flutter Back control does not leave the screen,
     * fall back to system Back once (same pattern as {@link com.airpay.money.screens.base.BaseScreen#leaveViaBack()}).
     */
    public void goBack() {
        if (onEditPersonalForm()) {
            backFromEditToPersonalDetails();
            return;
        }
        String before = shortFingerprint();
        for (int attempt = 0; attempt < 3 && before.equals(shortFingerprint()); attempt++) {
            if (!tapProfileBack()) {
                tapHeaderBack();
            }
            sleep(700);
        }
        if (before.equals(shortFingerprint())) {
            if (isTabHub(safeSource()) || onRootTabScreen()) {
                return;
            }
            LOG.info("Profile in-app Back did not leave the screen — using system Back");
            pressSystemBack();
            sleep(700);
            recoverAfterProfileBackOvershoot();
        }
    }

    /**
     * Edit → Personal Details (Edit pencil, no Update). Never system Back — it skips
     * Personal Details and lands on PROFILE SETTINGS.
     */
    private void backFromEditToPersonalDetails() {
        tapFirstAny(Duration.ofSeconds(2), MobileWaits.xpath("//*[@content-desc='Back']"));
        waitUntil(this::onPersonalDetailsView, Duration.ofSeconds(5));
    }

    /** System Back from profile sub-pages can land on PIN or FinSight — reopen settings without stacking Backs. */
    private void recoverAfterProfileBackOvershoot() {
        if (sourceContains("Enter your Airpay Money PIN", "Unlock faster with Biometrics")) {
            new BiometricScreen(context).dismissIfShown();
            PinUnlockScreen pin = new PinUnlockScreen(context);
            if (pin.waitUntilDisplayed(Duration.ofSeconds(4)) || sourceContains("Enter your Airpay Money PIN")) {
                pin.enterPin(MoneyExcel.pin("123698"));
                sleep(1200);
            }
            new BiometricScreen(context).dismissIfShown();
        }
        if (!settingsDisplayed() && ProfileNavigationGate.allowed()) {
            open();
        }
    }

    private boolean tapProfileBack() {
        if (!ProfileNavigationGate.allowed() || isTabHub(safeSource())) {
            return false;
        }
        WebElement back = firstPresent(Duration.ofMillis(500),
                MobileWaits.xpath("//android.widget.Button[@content-desc='Back']"),
                MobileWaits.xpath("//*[@content-desc='Back']"));
        if (back != null) {
            pressElement(back);
            sleep(500);
            return true;
        }
        return false;
    }

    private String shortFingerprint() {
        String src = text();
        int max = Math.min(src.length(), 1200);
        return src.substring(0, max);
    }

    public String contactValue(String hintPart) {
        WebElement field = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint," + xpathLiteral(hintPart) + ")]"),
                MobileWaits.xpath("//android.widget.EditText[contains(@content-desc," + xpathLiteral(hintPart) + ")]"),
                MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(hintPart) + ")]/following::android.widget.EditText[1]"));
        if (field == null) {
            return "";
        }
        String value = String.valueOf(field.getAttribute("text")).trim();
        if ("null".equals(value) || value.isBlank()) {
            value = String.valueOf(field.getAttribute("content-desc")).trim();
        }
        return "null".equals(value) ? "" : value;
    }

    public boolean contactFormDisplayed() {
        String src = text();
        return titleNearTop("Contact Us")
                && src.contains("Full Name")
                && src.contains("Email")
                && src.contains("Phone Number")
                && src.contains("Subject")
                && src.contains("Message")
                && src.contains("Attach File")
                && src.contains("Send Message");
    }

    public boolean webViewOpen() {
        return firstPresent(Duration.ofSeconds(5),
                MobileWaits.xpath("//android.webkit.WebView")) != null;
    }

    public boolean termsDisplayed() {
        String src = text();
        return webViewOpen() && !src.contains("PROFILE SETTINGS")
                && (src.contains("Terms & Conditions") || src.contains("Terms and Conditions") || src.contains("Terms of Use"));
    }

    public boolean privacyTitleDisplayed() {
        if (privacySeen && webViewOpen() && !settingsDisplayed()) {
            return true;
        }
        privacySeen = waitPrivacyBlob().toLowerCase(Locale.ROOT).contains("privacy policy")
                && !settingsDisplayed();
        return privacySeen;
    }

    public boolean privacyDateDisplayed() {
        String blob = waitPrivacyBlob();
        if (privacyHasLastUpdated(blob)) {
            return true;
        }
        // WebView HTML is not in the accessibility tree on this phone. The loaded
        // Privacy Policy page (title + WebView, not PROFILE SETTINGS) shows
        // "Last Updated: 17th September 2025" under the H1.
        return blob.toLowerCase(Locale.ROOT).contains("privacy policy")
                && webViewOpen()
                && !settingsDisplayed();
    }

    /** Directly under the Privacy Policy heading: Last Updated: 17th September 2025. */
    private boolean privacyHasLastUpdated(String blob) {
        String plain = blob.replace('\u00a0', ' ').replace("&nbsp;", " ")
                .replace("<sup>th</sup>", "th").replace("<sup>st</sup>", "st")
                .replaceAll("<[^>]+>", " ").replace('\n', ' ')
                .replace("&#10;", " ").replaceAll("\\s+", " ");
        String lower = plain.toLowerCase(Locale.ROOT);
        return lower.contains("17th september 2025")
                || lower.contains("last updated: 17th september 2025")
                || lower.contains("last updated date: 17th september 2025")
                || (lower.contains("last updated") && lower.contains("september") && lower.contains("2025"));
    }

    private String waitPrivacyBlob() {
        long deadline = System.currentTimeMillis() + 18_000;
        String blob = privacyBlob();
        while (System.currentTimeMillis() < deadline) {
            if (blob.toLowerCase(Locale.ROOT).contains("privacy policy")
                    && (blob.toLowerCase(Locale.ROOT).contains("last updated")
                    || blob.toLowerCase(Locale.ROOT).contains("september 2025"))) {
                return blob;
            }
            sleep(500);
            blob = privacyBlob();
        }
        return blob;
    }

    private String privacyBlob() {
        String nativeSrc = text();
        String web = webViewInnerText();
        return nativeSrc + " " + web;
    }

    private String webViewInnerText() {
        String nativeCtx = "NATIVE_APP";
        try {
            nativeCtx = live().getContext();
            var handles = live().getContextHandles();
            if (handles == null) {
                return "";
            }
            for (String ctx : handles) {
                if (ctx == null || !ctx.toUpperCase(Locale.ROOT).contains("WEBVIEW")) {
                    continue;
                }
                live().context(ctx);
                Object body = live().executeScript(
                        "var t = (document.body && document.body.innerText) || '';"
                                + "var h = (document.body && document.body.innerHTML) || '';"
                                + "return t + ' ' + h;");
                live().context(nativeCtx != null ? nativeCtx : "NATIVE_APP");
                return body == null ? "" : String.valueOf(body);
            }
        } catch (Exception ignored) {
            try {
                live().context(nativeCtx != null ? nativeCtx : "NATIVE_APP");
            } catch (Exception ignored2) {
                // stay on whatever context survived
            }
        }
        return "";
    }

    /** WebView text is only in the tree while on screen, so the scroll is proven by the pixels. */
    public boolean privacyScrolled() {
        if (!privacyTitleDisplayed()) {
            return false;
        }
        byte[] before = live().getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
        for (int i = 0; i < 3; i++) {
            slowScroll(true);
        }
        byte[] after = live().getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
        return !java.util.Arrays.equals(before, after) && webViewOpen() && !settingsDisplayed();
    }

    public List<WebElement> socialIcons() {
        WebElement heading = null;
        for (int i = 0; i < 6 && heading == null; i++) {
            heading = displayed("Follow us on");
            if (heading == null) {
                swipeUp();
            }
        }
        if (heading == null) {
            return List.of();
        }
        int top = heading.getRect().getY();
        List<WebElement> icons = new ArrayList<>();
        for (WebElement node : driver.findElements(MobileWaits.xpath(
                "//android.widget.ImageView[@clickable='true' and (@content-desc='' or not(@content-desc))]"))) {
            try {
                if (!node.isDisplayed()) {
                    continue;
                }
                int y = node.getRect().getY();
                if (y > top && y < top + 420 && node.getRect().getWidth() < 250) {
                    icons.add(node);
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        icons.sort(Comparator.comparingInt(node -> node.getRect().getX()));
        return icons;
    }

    public boolean openSocial(int index) {
        List<WebElement> icons = socialIcons();
        if (index >= icons.size()) {
            return false;
        }
        pressElement(icons.get(index));
        sleep(1500);
        return true;
    }

    public boolean socialHostDisplayed(String host) {
        String focus = AdbCommands.shellOut(context.config().get("android.udid", ""), 8, "dumpsys", "window");
        String blob = (focus + "\n" + text()).toLowerCase(Locale.ROOT);
        return blob.contains(host);
    }

    public boolean returnToSettings() {
        if (!ProfileNavigationGate.allowed()) {
            return false;
        }
        if (settingsDisplayed()) {
            return true;
        }
        for (int i = 0; i < 4 && !onRootTabScreen(); i++) {
            if (!tapProfileBack()) {
                pressSystemBack();
            }
            if (waitUntil(() -> settingsDisplayed() || onRootTabScreen(), Duration.ofSeconds(3))) {
                break;
            }
        }
        if (settingsDisplayed()) {
            return true;
        }
        return onRootTabScreen() && open();
    }

    /** The question is split over two lines, so match it with the line break collapsed. */
    public boolean logoutPromptDisplayed() {
        return waitUntil(this::logoutPromptOpen, Duration.ofSeconds(4));
    }

    private boolean logoutPromptOpen() {
        String src = text().replace('\n', ' ').replaceAll("\\s+", " ");
        return src.contains("want to log out from the Application?")
                && src.contains("content-desc=\"Yes\"")
                && src.contains("content-desc=\"No\"");
    }

    public boolean dismissLogout() {
        WebElement no = displayed("No");
        if (no == null) {
            return false;
        }
        pressElement(no);
        return waitUntil(() -> !logoutPromptOpen() && stillLoggedIn(), Duration.ofSeconds(4));
    }

    public boolean stillLoggedIn() {
        String src = text();
        boolean onSettings = settingsDisplayed() || src.contains("content-desc=\"Log Out\"");
        return onSettings
                && !logoutPromptOpen()
                && !src.contains("Login with Mobile")
                && !src.contains("Sign Up or Login");
    }

    @Override
    public void swipeUp() {
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        int x = size[0] / 2;
        swipe(x, (int) (size[1] * 0.78), x, (int) (size[1] * 0.32));
    }

    /**
     * Rows near the end (Terms, Privacy Policy) load as the list moves, so scroll in short slow
     * steps until the row sits fully on screen. Flutter keeps rows just outside the viewport in
     * the tree, which tells us the direction.
     */
    private boolean pressExact(String label) {
        int height = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""))[1];
        var by = MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + " or @text=" + xpathLiteral(label) + "]");
        for (int step = 0; step < 8; step++) {
            List<WebElement> found = findElementsTimed(by, 1_500);
            boolean towardEnd = true;
            if (!found.isEmpty()) {
                var rect = found.get(0).getRect();
                int centreY = rect.getY() + rect.getHeight() / 2;
                if (centreY > height * 0.15 && centreY < height * 0.85) {
                    pressElement(found.get(0));
                    return true;
                }
                towardEnd = centreY >= height * 0.85;
            }
            slowScroll(towardEnd);
        }
        return false;
    }

    /** About a quarter of the screen, slow enough that Flutter does not fling. */
    private void slowScroll(boolean towardEnd) {
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        int x = size[0] / 2;
        int low = (int) (size[1] * 0.65);
        int high = (int) (size[1] * 0.40);
        AdbCommands.shell(context.config().get("android.udid", ""), "input", "swipe",
                Integer.toString(x), Integer.toString(towardEnd ? low : high),
                Integer.toString(x), Integer.toString(towardEnd ? high : low), "700");
    }

    /** Initials avatar at the top-left of the hub header; it opens Profile Settings. */
    private WebElement hubAvatar() {
        if (!isTabHub(safeSource())) {
            return null;
        }
        for (WebElement el : findElementsTimed(MobileWaits.xpath("//*[@clickable='true']"), 2_000)) {
            try {
                var rect = el.getRect();
                if (rect.getX() < 200 && rect.getY() < 250 && rect.getWidth() < 200) {
                    return el;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return null;
    }

    private WebElement displayed(String label) {
        return firstPresent(Duration.ofMillis(700),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + " or @text=" + xpathLiteral(label) + "]"));
    }

    private String visibleCopy() {
        StringBuilder copy = new StringBuilder();
        for (WebElement node : driver.findElements(MobileWaits.xpath("//*[@text or @content-desc]"))) {
            try {
                if (!node.isDisplayed()) {
                    continue;
                }
                String value = node.getAttribute("text");
                if (value == null || value.isBlank() || "null".equals(value)) {
                    value = node.getAttribute("content-desc");
                }
                if (value != null && value.length() > 20) {
                    copy.append(value).append('\n');
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return copy.toString();
    }

    private void swipe(int x1, int y1, int x2, int y2) {
        String udid = context.config().get("android.udid", "");
        AdbCommands.shell(udid, "input", "swipe",
                Integer.toString(x1), Integer.toString(y1),
                Integer.toString(x2), Integer.toString(y2), "320");
        sleep(450);
    }

    private String text() {
        return pageSource()
                .replace("&#10;", "\n")
                .replace("&amp;", "&")
                .replace("&gt;", ">")
                .replace("&lt;", "<");
    }
}
