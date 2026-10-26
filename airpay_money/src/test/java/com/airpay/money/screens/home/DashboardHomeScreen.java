package com.airpay.money.screens.home;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.money.screens.finsight.FhcFlow;
import com.airpay.money.screens.finsight.NetWorthConsentScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Logged-in FinSight dashboard from the Dashboard home page sheet.
 * Login, OTP, PIN, and biometrics are not started. Let's Start opens Personal Details only.
 * Sync Your Net Worth opens consent and does not submit it.
 */
public class DashboardHomeScreen extends BaseScreen {

    public DashboardHomeScreen(TestContext context) {
        super(context);
    }

    public boolean dashboardDisplayed() {
        scrollToTop();
        String src = text();
        return src.contains("Hello")
                && src.contains("Your Financial Health Score")
                && src.contains("FinSight");
    }

    public boolean showsExact(String label) {
        return displayed(label) != null;
    }

    public String nameBesideHello() {
        WebElement hello = displayed("Hello");
        if (hello == null) {
            return "";
        }
        int helloY = hello.getRect().getY();
        String best = "";
        for (WebElement node : driver.findElements(MobileWaits.xpath("//*[@content-desc]"))) {
            try {
                if (!node.isDisplayed()) {
                    continue;
                }
                String desc = String.valueOf(node.getAttribute("content-desc")).replace("\n", " ").trim();
                int y = node.getRect().getY();
                if ("null".equals(desc) || Math.abs(y - helloY) > 120
                        || !desc.matches("[A-Za-z]+(?:[ .'-][A-Za-z]+){1,6}")) {
                    continue;
                }
                if (desc.length() > best.length()) {
                    best = desc;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return best;
    }

    public boolean healthTitleDisplayed() {
        return text().contains("Your Financial Health Score");
    }

    /** Card copy other than the score title. The title node often holds the extra lines. */
    public List<String> healthInformationLines() {
        List<String> lines = new ArrayList<>();
        WebElement card = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//*[contains(@content-desc,'Your Financial Health Score')]"));
        if (card == null) {
            return lines;
        }
        for (String line : card.getAttribute("content-desc").split("\n")) {
            String trimmed = line.trim();
            if (trimmed.length() > 2 && !trimmed.equals("Your Financial Health Score")) {
                lines.add(trimmed);
            }
        }
        WebElement button = letsStartButton();
        int top = card.getRect().getY() + card.getRect().getHeight();
        int bottom = button == null ? top + 400 : button.getRect().getY();
        for (WebElement node : driver.findElements(MobileWaits.xpath("//*[@content-desc]"))) {
            try {
                if (!node.isDisplayed()) {
                    continue;
                }
                int y = node.getRect().getY();
                String desc = node.getAttribute("content-desc").replace("\n", " ").trim();
                if (y >= top && y < bottom && desc.length() > 8
                        && !desc.equals("Let's start") && !desc.equals("Let's Start")
                        && !lines.contains(desc)) {
                    lines.add(desc);
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return lines;
    }

    public boolean healthInformationAligned() {
        WebElement card = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//*[contains(@content-desc,'Your Financial Health Score')]"));
        WebElement button = letsStartButton();
        if (card == null || button == null) {
            return false;
        }
        int cardX = card.getRect().getX();
        int buttonY = button.getRect().getY();
        if (buttonY <= card.getRect().getY()) {
            return false;
        }
        for (WebElement node : driver.findElements(MobileWaits.xpath("//*[@content-desc]"))) {
            try {
                if (!node.isDisplayed()) {
                    continue;
                }
                int y = node.getRect().getY();
                if (y <= card.getRect().getY() || y >= buttonY) {
                    continue;
                }
                String desc = String.valueOf(node.getAttribute("content-desc")).trim();
                if (desc.length() < 4) {
                    continue;
                }
                if (Math.abs(node.getRect().getX() - cardX) > 80) {
                    return false;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return true;
    }

    public boolean letsStartDisplayed() {
        return letsStartButton() != null;
    }

    public boolean tapLetsStart() {
        WebElement button = letsStartButton();
        if (button == null) {
            return false;
        }
        pressElement(button);
        sleep(900);
        return new FhcFlow(context).onPersonal();
    }

    public boolean personalDetailsDisplayed() {
        return new FhcFlow(context).onPersonal();
    }

    public boolean syncButtonDisplayed() {
        for (int i = 0; i < 6; i++) {
            if (displayed("Sync Your Net Worth") != null) {
                return true;
            }
            swipeUp();
        }
        return false;
    }

    public String syncDescription() {
        WebElement button = displayed("Sync Your Net Worth");
        if (button == null) {
            return "";
        }
        String own = button.getAttribute("content-desc");
        int split = own.indexOf('\n');
        if (split > 0 && split < own.length() - 1) {
            return own.substring(split + 1).trim();
        }
        int buttonY = button.getRect().getY();
        String best = "";
        for (WebElement node : driver.findElements(MobileWaits.xpath("//*[@content-desc]"))) {
            try {
                if (!node.isDisplayed() || node.equals(button)) {
                    continue;
                }
                int y = node.getRect().getY();
                String desc = node.getAttribute("content-desc").replace("\n", " ").trim();
                if (y < buttonY && buttonY - y < 280 && desc.length() > best.length()
                        && !desc.equals("Sync Your Net Worth")
                        && !desc.equals("Products") && !desc.equals("FinSight") && !desc.equals("Calculators")) {
                    best = desc;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return best;
    }

    public boolean tapSync() {
        WebElement button = displayed("Sync Your Net Worth");
        if (button == null) {
            return false;
        }
        pressElement(button);
        sleep(900);
        return new NetWorthConsentScreen(context).isDisplayed();
    }

    public boolean consentDisplayed() {
        return new NetWorthConsentScreen(context).isDisplayed();
    }

    public boolean allowConsentDisplayed() {
        return displayed("Allow Consent") != null
                || text().contains("Allow Consent");
    }

    public boolean tabDisplayed(String label) {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Button[@content-desc=" + xpathLiteral(label) + "]")) != null;
    }

    public boolean tabsAligned() {
        WebElement products = tab("Products");
        WebElement finsight = tab("FinSight");
        WebElement calculators = tab("Calculators");
        if (products == null || finsight == null || calculators == null) {
            return false;
        }
        int productsY = products.getRect().getY();
        int finsightY = finsight.getRect().getY();
        int calculatorsY = calculators.getRect().getY();
        boolean sameRow = Math.abs(productsY - finsightY) <= 40 && Math.abs(finsightY - calculatorsY) <= 40;
        boolean leftToRight = products.getRect().getX() < finsight.getRect().getX()
                && finsight.getRect().getX() < calculators.getRect().getX();
        return sameRow && leftToRight;
    }

    public boolean notificationVisible() {
        WebElement icon = notification();
        return icon != null && icon.getRect().getY() < 320;
    }

    public boolean notificationEnabled() {
        WebElement icon = notification();
        return icon != null && icon.isEnabled();
    }

    private WebElement letsStartButton() {
        WebElement button = displayed("Let's start");
        return button != null ? button : displayed("Let's Start");
    }

    private WebElement notification() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.ImageView[@content-desc='Notifications']"));
    }

    private WebElement tab(String label) {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.Button[@content-desc=" + xpathLiteral(label) + "]"));
    }

    private WebElement displayed(String label) {
        return firstPresent(Duration.ofMillis(800),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]"));
    }

    private void scrollToTop() {
        for (int i = 0; i < 3; i++) {
            swipeDown();
        }
    }

    private void swipeDown() {
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int startY = (int) (size.height * 0.30);
        int endY = (int) (size.height * 0.75);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);
        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(300), PointerInput.Origin.viewport(), x, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
        sleep(300);
    }

    private String text() {
        return pageSource().replace("&#10;", "\n").replace("&amp;", "&");
    }
}
