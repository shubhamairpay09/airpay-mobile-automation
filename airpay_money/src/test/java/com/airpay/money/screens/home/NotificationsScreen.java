package com.airpay.money.screens.home;

import com.airpay.money.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import com.airpay.mobile.utilities.waits.MobileWaits;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

import java.time.Duration;

/**
 * In-app notification list. Dump {@code uidump-notif-01-list} 2.1.3 2026-09-28.
 * Back chevron at [0,95][158,253] has no content-desc (NAF).
 */
public class NotificationsScreen extends BaseScreen {

    public static final String BACK = "//android.widget.Button[@content-desc='']";
    public static final String TITLE = "//android.view.View[@content-desc='Notification']";
    public static final String EMPTY = "//android.view.View[@content-desc='No Notifications Yet']";
    public static final String EMPTY_SUBTITLE =
            "//android.view.View[contains(@content-desc,'We')]";

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Notification']")
    private WebElement title;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='No Notifications Yet']")
    private WebElement empty;

    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,\"We'll notify you when something important happens\")]")
    private WebElement emptySubtitle;

    public NotificationsScreen(TestContext context) {
        super(context);
    }

    public boolean isDisplayed() {
        return sourceContains("Notification") && sourceContains("No Notifications Yet", "We'll notify you");
    }

    public boolean isEmpty() {
        return sourceContains("No Notifications Yet");
    }

    @Override
    public boolean tapBack() {
        if (tapFirst(Duration.ofSeconds(4),
                MobileWaits.xpath("//android.widget.Button[@content-desc='']"))) {
            sleep(400);
            return true;
        }
        boolean tapped = super.tapBack();
        sleep(400);
        return tapped;
    }
}
