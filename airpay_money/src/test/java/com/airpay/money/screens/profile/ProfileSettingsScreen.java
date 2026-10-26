package com.airpay.money.screens.profile;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
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
 * Change PIN is not opened.
 */
public class ProfileSettingsScreen extends BaseScreen {

    public ProfileSettingsScreen(TestContext context) {
        super(context);
    }

    public boolean open() {
        if (settingsDisplayed()) {
            return true;
        }
        for (int i = 0; i < 4; i++) {
            WebElement settings = displayed("Open settings");
            if (settings != null) {
                pressElement(settings);
                sleep(900);
                if (settingsDisplayed()) {
                    return true;
                }
            }
            swipeDown();
        }
        return settingsDisplayed();
    }

    public boolean settingsDisplayed() {
        return text().contains("PROFILE SETTINGS");
    }

    public boolean openRow(String label) {
        return pressExact(label);
    }

    public boolean openContaining(String label) {
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

    public boolean uploadedDocumentDisplayed() {
        String src = text();
        return src.contains("My Documents") && !src.contains("No Document found");
    }

    public boolean panDetailsDisplayed() {
        return text().matches("(?s).*PAN.*([A-Z]{5}[0-9]{4}[A-Z]|X{4,}).*");
    }

    public boolean titleNearTop(String label) {
        WebElement node = displayed(label);
        if (node == null) {
            node = firstPresent(Duration.ofSeconds(3),
                    MobileWaits.xpath("//*[contains(@content-desc," + xpathLiteral(label) + ") or contains(@text," + xpathLiteral(label) + ")]"));
        }
        return node != null && node.getRect().getY() < 500;
    }

    public boolean shows(String label) {
        return text().contains(label);
    }

    public boolean editPersonalDisplayed() {
        String src = text();
        return src.contains("Full name") && src.contains("Father's name") && src.contains("Mother's name");
    }

    public boolean editFieldsPrefilled() {
        List<WebElement> fields = driver.findElements(MobileWaits.xpath("//android.widget.EditText"));
        int checked = 0;
        for (WebElement field : fields) {
            try {
                if (!field.isDisplayed()) {
                    continue;
                }
                String value = String.valueOf(field.getAttribute("text")).trim();
                String hint = String.valueOf(field.getAttribute("hint")).trim();
                if ("null".equals(value) || value.isBlank() || value.equals(hint)) {
                    return false;
                }
                checked++;
            } catch (Exception ignored) {
                // stale
            }
        }
        return checked >= 3;
    }

    public boolean personalDetailsAgain() {
        return titleNearTop("Personal Details") && displayed("Edit personal details") != null && !text().contains("Update");
    }

    public void goBack() {
        tapHeaderBack();
        sleep(800);
    }

    public String contactValue(String hintPart) {
        WebElement field = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.EditText[contains(@hint," + xpathLiteral(hintPart) + ")]"));
        if (field == null) {
            return "";
        }
        String value = String.valueOf(field.getAttribute("text")).trim();
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
        return webViewOpen() && text().contains("Privacy Policy") && !text().contains("PROFILE SETTINGS");
    }

    public boolean privacyDateDisplayed() {
        String plain = text().replaceAll("<[^>]+>", " ").replaceAll("\\s+", " ");
        int at = plain.toLowerCase(Locale.ROOT).indexOf("last updated");
        if (at < 0) {
            return false;
        }
        String window = plain.substring(at, Math.min(plain.length(), at + 80));
        return window.matches("(?i).*(\\d{1,2}[/-]\\d{1,2}[/-]\\d{2,4}|\\d{1,2}\\s+[A-Za-z]+\\s+\\d{4}|[A-Za-z]+\\s+\\d{1,2},?\\s+\\d{4}).*");
    }

    public boolean privacyScrolled() {
        String before = visibleCopy();
        for (int i = 0; i < 4; i++) {
            swipeUpNarrow();
        }
        String after = visibleCopy();
        return privacyTitleDisplayed() && after.length() > 40 && !after.equals(before);
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
        for (int i = 0; i < 5 && !settingsDisplayed(); i++) {
            AdbCommands.shell(context.config().get("android.udid", ""), "input", "keyevent", "4");
            sleep(700);
            if (!settingsDisplayed()) {
                goBack();
            }
        }
        return settingsDisplayed();
    }

    public boolean logoutPromptDisplayed() {
        String src = text();
        return src.contains("Are you sure, you want to log out from the Application?")
                && src.contains("Yes")
                && src.contains("No");
    }

    public boolean dismissLogout() {
        WebElement no = displayed("No");
        if (no == null) {
            return false;
        }
        pressElement(no);
        sleep(700);
        return !text().contains("Are you sure, you want to log out from the Application?") && settingsDisplayed();
    }

    public boolean stillLoggedIn() {
        String src = text();
        return settingsDisplayed()
                && !src.contains("Login with Mobile")
                && !src.contains("Sign Up or Login");
    }

    @Override
    public void swipeUp() {
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        swipe(x, (int) (size.height * 0.78), x, (int) (size.height * 0.32));
    }

    private boolean pressExact(String label) {
        for (int i = 0; i < 8; i++) {
            WebElement target = displayed(label);
            if (target != null) {
                pressElement(target);
                sleep(800);
                return true;
            }
            swipeUp();
        }
        return false;
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

    private void swipeUpNarrow() {
        Dimension size = driver.manage().window().getSize();
        int x = 200;
        int startY = (int) (size.height * 0.75);
        int endY = (int) (size.height * 0.30);
        swipe(x, startY, x, endY);
    }

    private void swipeDown() {
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        swipe(x, (int) (size.height * 0.30), x, (int) (size.height * 0.75));
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
