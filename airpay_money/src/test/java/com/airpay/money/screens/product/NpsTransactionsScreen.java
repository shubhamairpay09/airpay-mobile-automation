package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/** NPS transaction history. Dump {@code uidump-nps-03-Back} 2.1.3. Filters: All, One Time, SIP. */
public class NpsTransactionsScreen extends BaseScreen {

    private static final Set<String> CHROME = Set.of(
            "transaction history", "transactions history", "all", "one time", "sip",
            "back", "nps", "mutual funds", "digital gold", "help",
            "dashboard", "portfolio", "transactions", "profile");

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Transaction History']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='All']")
    private WebElement all;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='One Time']")
    private WebElement oneTime;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='SIP']")
    private WebElement sip;

    public NpsTransactionsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return historyTitleDisplayed() && filterDisplayed("All")
                && filterDisplayed("One Time") && filterDisplayed("SIP");
    }

    public boolean historyTitleDisplayed() {
        return titleNearTop("Transaction History") || titleNearTop("Transactions History");
    }

    public boolean filterDisplayed(String label) {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]")) != null;
    }

    public boolean pressFilter(String label) {
        WebElement chip = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]"));
        if (chip == null) {
            return false;
        }
        pressElement(chip);
        sleep(700);
        return true;
    }

    public List<String> transactionRows() {
        List<String> rows = new ArrayList<>();
        for (WebElement node : driver.findElements(MobileWaits.xpath("//*[@content-desc]"))) {
            try {
                if (!node.isDisplayed()) {
                    continue;
                }
                String desc = String.valueOf(node.getAttribute("content-desc")).replace("\n", " ").trim();
                if (desc.length() < 8 || CHROME.contains(desc.toLowerCase(Locale.ROOT))) {
                    continue;
                }
                if (desc.contains("₹") || desc.matches("(?i).*\\brs\\.?\\s*\\d.*")) {
                    rows.add(desc);
                }
            } catch (Exception ignored) {
                // stale node
            }
        }
        return rows;
    }

    public boolean recordsDisplayed() {
        return !transactionRows().isEmpty() && !text().contains("No Transaction");
    }

    public boolean hasOneTimeRow() {
        return transactionRows().stream().anyMatch(NpsTransactionsScreen::isOneTime);
    }

    public boolean hasSipRow() {
        return transactionRows().stream().anyMatch(NpsTransactionsScreen::isSip);
    }

    /** All shows both kinds. A row counts only when its own text says One Time or SIP. */
    public boolean allShowsBothTypes() {
        boolean oneTimeRow = false;
        boolean sipRow = false;
        for (String row : transactionRows()) {
            if (isOneTime(row)) {
                oneTimeRow = true;
            }
            if (isSip(row)) {
                sipRow = true;
            }
        }
        return oneTimeRow && sipRow;
    }

    public boolean onlyOneTimeRows() {
        List<String> rows = transactionRows();
        if (rows.isEmpty()) {
            return false;
        }
        for (String row : rows) {
            if (!isOneTime(row) || isSip(row)) {
                return false;
            }
        }
        return true;
    }

    public boolean onlySipRows() {
        List<String> rows = transactionRows();
        if (rows.isEmpty()) {
            return false;
        }
        for (String row : rows) {
            if (!isSip(row) || isOneTime(row)) {
                return false;
            }
        }
        return true;
    }

    public boolean sipRowsHidden() {
        for (String row : transactionRows()) {
            if (isSip(row)) {
                return false;
            }
        }
        return !transactionRows().isEmpty();
    }

    public boolean oneTimeRowsHidden() {
        for (String row : transactionRows()) {
            if (isOneTime(row)) {
                return false;
            }
        }
        return !transactionRows().isEmpty();
    }

    private boolean titleNearTop(String label) {
        WebElement node = firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//*[@content-desc=" + xpathLiteral(label) + "]"));
        return node != null && node.getRect().getY() < 500;
    }

    private static boolean isSip(String row) {
        return row.matches("(?i).*\\bSIP\\b.*");
    }

    private static boolean isOneTime(String row) {
        return row.matches("(?i).*(one[\\s-]*time|lumpsum).*");
    }

    private String text() {
        return pageSource().replace("&#10;", "\n").replace("&amp;", "&");
    }
}
