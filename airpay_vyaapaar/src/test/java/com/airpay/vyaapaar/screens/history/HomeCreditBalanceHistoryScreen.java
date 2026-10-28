package com.airpay.vyaapaar.screens.history;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Credit Balance / History — INITIATED. Module history. */
public class HomeCreditBalanceHistoryScreen extends BaseScreen {

    public static final String PAGE_ID = "home-credit-balance-history";

    public static final String FILTER_ICON = "//android.view.View[contains(@content-desc,'Filter Icon')]";

    @AndroidFindBy(xpath = FILTER_ICON)
    private WebElement filterIcon;

    public static final String INITIATED = "//android.view.View[contains(@content-desc,'INITIATED')]";

    @AndroidFindBy(xpath = INITIATED)
    private WebElement initiated;

    public static final String OTHERS = "//android.widget.TextView[@text='Others']";

    @AndroidFindBy(xpath = OTHERS)
    private WebElement others;

    public static final String CMS = "//android.widget.TextView[@text='Cms']";

    @AndroidFindBy(xpath = CMS)
    private WebElement cms;

    public static final String COPY_ICON = "//android.view.View[contains(@content-desc,'Copy Icon')]";

    @AndroidFindBy(xpath = COPY_ICON)
    private WebElement copyIcon;

    public static final String EL100 = "//android.widget.TextView[@text='₹1.00']";

    @AndroidFindBy(xpath = EL100)
    private WebElement el100;

    public static final String DOWN_ARROW = "//android.view.View[contains(@content-desc,'Down arrow')]";

    @AndroidFindBy(xpath = DOWN_ARROW)
    private WebElement downArrow;

    public static final String FAILED = "//android.view.View[contains(@content-desc,'FAILED')]";

    @AndroidFindBy(xpath = FAILED)
    private WebElement failed;

    public static final String DOWNLOAD = "//android.view.View[contains(@content-desc,'Download')]";

    @AndroidFindBy(xpath = DOWNLOAD)
    private WebElement download;

    public static final String HOME = "//android.widget.TextView[@text='Home']";

    @AndroidFindBy(xpath = HOME)
    private WebElement home;

    public static final String SERVICES = "//android.widget.TextView[@text='Services']";

    @AndroidFindBy(xpath = SERVICES)
    private WebElement services;

    public static final String HISTORY = "//android.widget.TextView[@text='History']";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String PROFILE = "//android.widget.TextView[@text='Profile']";

    @AndroidFindBy(xpath = PROFILE)
    private WebElement profile;

    public HomeCreditBalanceHistoryScreen(TestContext context) {
        super(context);
    }
}
