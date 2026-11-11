package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Mutual Funds — goal created summary. Live dump 2026-09-17, build 2.0.124.
 */
public class MutualFundsGoalCreatedScreen extends BaseScreen {

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Back']")
    private WebElement back;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Delete')]")
    private WebElement delete;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='New investment']")
    private WebElement titleNewInvestment;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Buy bike']")
    private WebElement titleBuyBike;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Buy car']")
    private WebElement titleBuyCar;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Monthly SIP')]")
    private WebElement monthlySip;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Monthly SIP') and contains(@content-desc,'851')]")
    private WebElement monthlySip851;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'One time')]")
    private WebElement oneTime;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'One time') and contains(@content-desc,'9810')]")
    private WebElement oneTime9810;

    public MutualFundsGoalCreatedScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return firstPresent(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.Button[contains(@content-desc,'Delete')]"),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Goal created on:')]"),
                MobileWaits.xpath("//android.view.View[contains(@content-desc,'Monthly SIP')]")) != null;
    }

    public boolean deleteGoal() {
        if (!(tapLabel("Delete") || tapLabelFast("Delete") || tapFirstAny(Duration.ofSeconds(3),
                accessibility("Delete"),
                MobileWaits.xpath("//*[contains(@content-desc,'Delete')]")))) {
            tapTopRightDelete();
        }
        sleep(800);
        confirmDeleteIfAsked();
        sleep(1000);
        long deadline = System.currentTimeMillis() + 8_000;
        while (System.currentTimeMillis() < deadline) {
            if (backOnMutualFundsHome()) {
                return true;
            }
            sleep(400);
        }
        return backOnMutualFundsHome();
    }

    /** Unlabelled trash in the goal header, top-right, opposite Back. */
    private void tapTopRightDelete() {
        String udid = context.config().get("android.udid", "");
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(udid);
        int width = size[0] > 0 ? size[0] : 720;
        int height = size[1] > 0 ? size[1] : 1600;
        // Header Back on this 720x1600 realme is ~[54,108]. Trash is the opposite corner.
        int x = (int) (width * 0.92);
        int y = Math.max(96, Math.min(120, (int) (height * 0.07)));
        com.airpay.mobile.utilities.android.AdbCommands.shell(udid, "input", "swipe",
                Integer.toString(x), Integer.toString(y),
                Integer.toString(x), Integer.toString(y), "160");
    }

    private void confirmDeleteIfAsked() {
        String src = pageSource();
        if (!src.contains("Cancel") && !src.contains("Are you sure") && !src.contains("Remove")
                && !src.contains("Delete this") && !src.contains("delete this")) {
            if (!src.contains("Yes") || !src.contains("Delete")) {
                return;
            }
        }
        if (tapLabel("Delete") || tapLabel("Remove") || tapLabel("Yes") || tapLabel("Confirm")) {
            sleep(700);
            return;
        }
        int[] size = com.airpay.mobile.utilities.android.AdbInput.screenSize(
                context.config().get("android.udid", ""));
        tapAt((int) (size[0] * 0.50), (int) (size[1] * 0.72));
        sleep(700);
    }

    private boolean backOnMutualFundsHome() {
        String src = pageSource();
        return src.contains("Mutual Funds")
                && (src.contains("Add Goal") || src.contains("My Goals")
                || src.contains("View all Mutual Funds") || src.contains("Dashboard"))
                && !src.contains("Goal created on");
    }
}
