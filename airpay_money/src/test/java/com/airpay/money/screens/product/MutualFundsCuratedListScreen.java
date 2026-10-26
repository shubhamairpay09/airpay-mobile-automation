package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.android.AdbCommands;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Curated Funds lists — Better than FDs through Better Than RD.
 * Shared Search + Filter chrome. Live dumps 2026-09-17, build 2.0.124.
 */
public class MutualFundsCuratedListScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Better than FDs']")
    private WebElement titleBetterThanFds;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='High Returns']")
    private WebElement titleHighReturns;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Short Term Investment']")
    private WebElement titleShortTerm;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Long Term Investment']")
    private WebElement titleLongTerm;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Tax Saving Fund']")
    private WebElement titleTaxSaving;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Emergency Fund']")
    private WebElement titleEmergency;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Bluechip Fund']")
    private WebElement titleBluechip;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Better Than RD']")
    private WebElement titleBetterThanRd;

    @AndroidFindBy(xpath = "//android.widget.ImageView[@content-desc='']")
    private WebElement filter;

    @AndroidFindBy(xpath = "//android.widget.EditText[@content-desc='']")
    private WebElement search;

    @AndroidFindBy(xpath = "//android.widget.EditText[@text='HDFC']")
    private WebElement searchHdfc;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Aditya Birla Sun Life Medium Term Plan')]")
    private WebElement firstFund;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'HDFC Medium Term Debt Fund')]")
    private WebElement hdfcMediumTerm;

    public MutualFundsCuratedListScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.widget.EditText[@content-desc='']"),
                MobileWaits.xpath("//android.view.View[@content-desc='Better than FDs']"),
                MobileWaits.xpath("//android.view.View[@content-desc='High Returns']")) != null;
    }

    public void search(String text) {
        WebElement field = firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.EditText"));
        if (field == null) {
            return;
        }
        field.click();
        try {
            field.clear();
        } catch (Exception ignored) {
            // Flutter fields often reject clear()
        }
        field.sendKeys(text);
        sleep(800);
        try {
            driver.hideKeyboard();
        } catch (Exception ignored) {
            // keyboard already closed
        }
    }

    /**
     * List title is the exact label "Better than FDs". The dashboard tile is "Better\nthan FD",
     * so this stays false until the fund list itself is showing.
     */
    public boolean betterThanFdsListVisible() {
        long end = System.currentTimeMillis() + 12000;
        while (System.currentTimeMillis() < end) {
            String src = pageSource();
            if (src.contains("content-desc=\"Better than FDs\"")
                    && (src.contains("Search Funds") || src.contains("android.widget.EditText"))) {
                return true;
            }
            sleep(400);
        }
        return false;
    }

    /**
     * The filter control is the blank image in the top-right header, beside Back.
     * It has no content description. A normal element click does not open it.
     * The device tap that opens it is a real press on that image.
     */
    public boolean openFilter() {
        if (!betterThanFdsListVisible()) {
            return false;
        }
        for (int attempt = 0; attempt < 3; attempt++) {
            int[] point = filterTapPoint();
            int x = point == null ? 962 : point[0];
            int y = point == null ? 174 : point[1];
            shellTap(x, y);
            sleep(1100);
            if (filterSheetOpen()) {
                return true;
            }
        }
        return filterSheetOpen();
    }

    private boolean filterSheetOpen() {
        String src = pageSource();
        return src.contains("Filters") && src.contains("Apply") && src.contains("Category");
    }

    /** Blank clickable image in the list header. Fund cards and the title are not this control. */
    private int[] filterTapPoint() {
        int[] point = null;
        int rightmost = -1;
        for (WebElement node : driver.findElements(
                MobileWaits.xpath("//android.widget.ImageView[@clickable='true']"))) {
            try {
                if (!node.isDisplayed()) {
                    continue;
                }
                String desc = String.valueOf(node.getAttribute("content-desc"));
                if (desc != null && !desc.isBlank() && !"null".equals(desc)) {
                    continue;
                }
                var rect = node.getRect();
                if (rect.getY() < 360 && rect.getX() > 700 && rect.getWidth() < 280
                        && rect.getHeight() < 280 && rect.getX() > rightmost) {
                    point = new int[] {
                            rect.getX() + rect.getWidth() / 2,
                            rect.getY() + rect.getHeight() / 2
                    };
                    rightmost = rect.getX();
                }
            } catch (Exception ignored) {
                // stale
            }
        }
        return point;
    }

    private void shellTap(int x, int y) {
        String udid = context.config().get("android.udid", "");
        int code = AdbCommands.shell(udid, "input", "swipe",
                Integer.toString(x), Integer.toString(y), Integer.toString(x), Integer.toString(y), "160");
        if (code != 0) {
            tapAt(x, y);
        }
    }

    public boolean openFundCard() {
        for (int pass = 0; pass < 3; pass++) {
            for (WebElement card : driver.findElements(MobileWaits.xpath(
                    "//*[@clickable='true' and @content-desc]"))) {
                try {
                    String desc = String.valueOf(card.getAttribute("content-desc"));
                    String lower = desc.toLowerCase();
                    if (!card.isDisplayed() || desc.length() < 28) {
                        continue;
                    }
                    if (lower.contains("back") || lower.contains("filter") || lower.contains("dashboard")) {
                        continue;
                    }
                    if (lower.contains("fund") || lower.contains("investment") || desc.contains("%")) {
                        card.click();
                        sleep(800);
                        return true;
                    }
                } catch (Exception ignored) {
                    // stale
                }
            }
            swipeUp();
            sleep(400);
        }
        return false;
    }
}
