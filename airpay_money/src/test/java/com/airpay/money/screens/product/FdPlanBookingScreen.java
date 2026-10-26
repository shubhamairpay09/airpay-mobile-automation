package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * In-app FD plan booking screen (bank plan opened from the FD landing). Live dump 2026-09-21, build 2.0.124.
 * "Book FD at ..." hands off to {@link FixerraFdBookingScreen} in a WebView.
 */
public class FdPlanBookingScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Suryoday Small Finance Bank']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@clickable='true' and @content-desc='']")
    private WebElement amountEdit;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='View all']")
    private WebElement viewAllTenures;

    @AndroidFindBy(xpath = "//android.widget.Button[starts-with(@content-desc,'Book FD at')]")
    private WebElement bookFd;

    public FdPlanBookingScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Select goal tenure']"),
                MobileWaits.xpath("//android.widget.Button[starts-with(@content-desc,'Book FD at')]")) != null;
    }

    public void openAmountSheet() {
        amountEdit.click();
    }

    public void openTenureSheet() {
        viewAllTenures.click();
    }

    public boolean openInterestPayout() {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath(
                "//android.widget.Button[@content-desc='Maturity' or @content-desc='Monthly']"));
    }

    public boolean openRenewalPreference() {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath("//android.widget.Button[@content-desc='no' or @content-desc='total' or @content-desc='principal']"));
    }

    public boolean selectPayout(String option) {
        return tapFirst(Duration.ofSeconds(4), MobileWaits.xpath(
                "//android.widget.Button[@content-desc=" + xpathLiteral(option) + "]"));
    }

    public void bookFd() {
        bookFd.click();
    }

    /** Suryoday details page. Book FD stays visible and is not tapped. */
    public boolean showsPlanDetails() {
        if (!isDisplayed() && !sourceContains("Select goal tenure", "Book FD")) {
            return false;
        }
        String src = pageSource();
        return src.contains("Suryoday")
                && (src.contains("Amount") || src.contains("Invest"))
                && src.contains("Select goal tenure")
                && src.contains("Interest Payout")
                && src.contains("Renewal")
                && src.contains("Maturity")
                && src.contains("Book FD");
    }

    /**
     * Opens the amount editor, enters a new amount, and taps Save Changes. Book FD is not tapped.
     */
    public boolean changeInvestmentAmount(String amount) {
        String maturityBefore = maturityFigure();
        WebElement pencil = amountPencil();
        if (pencil == null) {
            return false;
        }
        pressElement(pencil);
        sleep(700);
        WebElement field = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.EditText"));
        if (field == null) {
            return false;
        }
        replaceField(field, amount);
        WebElement save = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//*[@clickable='true' and @content-desc='Save Changes']"));
        if (save == null) {
            return false;
        }
        pressElement(save);
        sleep(900);
        String src = pageSource().replace(",", "").replace(" ", "");
        String maturityAfter = maturityFigure();
        return src.contains(amount)
                && src.contains("Maturity")
                && !maturityBefore.isBlank()
                && !maturityAfter.isBlank()
                && !maturityBefore.equals(maturityAfter);
    }

    /** The pencil beside Amount Invested has no content description. */
    private WebElement amountPencil() {
        WebElement pencil = null;
        int rightmost = -1;
        for (WebElement node : driver.findElements(MobileWaits.xpath("//android.widget.ImageView[@clickable='true']"))) {
            try {
                if (!node.isDisplayed()) {
                    continue;
                }
                String desc = String.valueOf(node.getAttribute("content-desc"));
                if (desc != null && !desc.isBlank() && !"null".equals(desc)) {
                    continue;
                }
                var rect = node.getRect();
                if (rect.getY() > 400 && rect.getY() < 750 && rect.getX() > 700 && rect.getX() > rightmost) {
                    pencil = node;
                    rightmost = rect.getX();
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return pencil;
    }

    private void replaceField(WebElement field, String amount) {
        pressElement(field);
        sleep(300);
        String existing = String.valueOf(field.getAttribute("text"));
        int deletes = existing == null || "null".equals(existing) ? 16 : existing.length() + 4;
        String udid = context.config().get("android.udid", "");
        for (int i = 0; i < deletes; i++) {
            AdbCommands.shell(udid, "input", "keyevent", "67");
        }
        AdbCommands.shell(udid, "input", "text", amount);
    }

    /** Selects a tenure chip that is not the one already shown on Book FD. Book FD is not tapped. */
    public boolean selectDifferentTenure() {
        String rateBefore = bookFdRate();
        String maturityBefore = maturityFigure();
        for (WebElement label : driver.findElements(MobileWaits.xpath("//*[contains(@content-desc,'%')]"))) {
            try {
                String desc = String.valueOf(label.getAttribute("content-desc"));
                if (!label.isDisplayed() || desc.contains("Book FD") || !tenureLabel(desc)) {
                    continue;
                }
                if (!rateBefore.isBlank() && desc.contains(rateBefore)) {
                    continue;
                }
                WebElement target = chipAt(label);
                if (target == null) {
                    continue;
                }
                pressElement(target);
                sleep(900);
                if (tenureUpdated(rateBefore, maturityBefore)) {
                    return true;
                }
            } catch (Exception ignored) {
                // stale chip
            }
        }
        return false;
    }

    private boolean tenureLabel(String desc) {
        return desc.contains(" Y ") || desc.contains(" M ") || desc.contains(" D ");
    }

    /** The clickable chip has no label. The rate text sits on a sibling with the same bounds. */
    private WebElement chipAt(WebElement label) {
        var wanted = label.getRect();
        for (WebElement node : driver.findElements(MobileWaits.xpath("//*[@clickable='true']"))) {
            try {
                String desc = String.valueOf(node.getAttribute("content-desc"));
                if (desc.contains("Book FD") || desc.contains("View all")) {
                    continue;
                }
                var box = node.getRect();
                if (Math.abs(box.getX() - wanted.getX()) < 12 && Math.abs(box.getY() - wanted.getY()) < 12
                        && Math.abs(box.getWidth() - wanted.getWidth()) < 20) {
                    return node;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return null;
    }

    private boolean tenureUpdated(String rateBefore, String maturityBefore) {
        String rate = bookFdRate();
        boolean knownRate = "8.25".equals(rate) || "7.25".equals(rate) || "4.00".equals(rate);
        String maturityAfter = maturityFigure();
        return knownRate
                && !rate.isBlank()
                && !rate.equals(rateBefore)
                && !maturityBefore.isBlank()
                && !maturityAfter.isBlank()
                && !maturityBefore.equals(maturityAfter);
    }

    private String bookFdRate() {
        Matcher matcher = Pattern.compile("Book FD at\\s*(\\d+\\.\\d{2})\\s*%").matcher(pageSource());
        return matcher.find() ? matcher.group(1) : "";
    }

    private String maturityFigure() {
        String src = pageSource().replace("&#10;", " ");
        int at = src.toLowerCase().indexOf("maturity amount");
        if (at < 0) {
            return "";
        }
        String window = src.substring(at, Math.min(src.length(), at + 240));
        Matcher matcher = Pattern.compile("(\\d[\\d,]{2,}(?:\\.\\d+)?)").matcher(window);
        return matcher.find() ? matcher.group(1) : "";
    }

    /**
     * The Interest Payout button shows Maturity before the sheet opens. The sheet lists Monthly.
     * The choice is not changed, and Book FD is not tapped.
     */
    public boolean payoutOptionsDisplayed() {
        WebElement payout = payoutButton();
        if (payout == null || !"Maturity".equals(String.valueOf(payout.getAttribute("content-desc")).trim())) {
            return false;
        }
        pressElement(payout);
        sleep(700);
        String src = pageSource();
        boolean shown = src.contains("Monthly") && src.contains("Maturity");
        String udid = context.config().get("android.udid", "");
        AdbCommands.shell(udid, "input", "keyevent", "4");
        sleep(400);
        return shown;
    }

    /** The button under the Interest Payout label. Renewal Preference is the button on the right. */
    private WebElement payoutButton() {
        WebElement label = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//*[@content-desc='Interest Payout']"));
        if (label == null) {
            return null;
        }
        var box = label.getRect();
        WebElement payout = null;
        int nearest = Integer.MAX_VALUE;
        for (WebElement node : driver.findElements(MobileWaits.xpath("//android.widget.Button[@clickable='true']"))) {
            try {
                String desc = String.valueOf(node.getAttribute("content-desc"));
                if (desc.contains("Book FD")) {
                    continue;
                }
                var rect = node.getRect();
                int gap = rect.getY() - (box.getY() + box.getHeight());
                int midX = rect.getX() + rect.getWidth() / 2;
                if (gap < -10 || gap > 220 || midX < box.getX() || midX > box.getX() + box.getWidth() + 40) {
                    continue;
                }
                if (gap < nearest) {
                    nearest = gap;
                    payout = node;
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return payout;
    }
}
