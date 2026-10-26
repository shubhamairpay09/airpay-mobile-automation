package com.airpay.money.screens.product;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * Buy Gold investment-type bottom sheet. Dump {@code uidump-gold-27-buy-invest-type} 2.1.3.
 */
public class DigitalGoldInvestmentTypeSheet extends BaseScreen {

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Investment type']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='One Time']")
    private WebElement oneTime;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Monthly ( SIP )']")
    private WebElement monthlySip;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Dismiss']")
    private WebElement dismiss;

    public DigitalGoldInvestmentTypeSheet(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Investment type") && sourceContains("Monthly ( SIP )");
    }

    public void chooseOneTime() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='One Time']"));
        sleep(300);
    }

    public void chooseMonthlySip() {
        tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.view.View[@content-desc='Monthly ( SIP )']"));
        sleep(300);
    }

    public void dismiss() {
        tapFirst(Duration.ofSeconds(3),
                MobileWaits.xpath("//android.view.View[@content-desc='Dismiss']"));
    }
}
