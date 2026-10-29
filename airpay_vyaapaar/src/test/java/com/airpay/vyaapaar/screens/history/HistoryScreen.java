package com.airpay.vyaapaar.screens.history;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** History. Module history. */
public class HistoryScreen extends BaseScreen {

    public static final String PAGE_ID = "history";

    public static final String FILTER_ICON = "//android.view.View[contains(@content-desc,'Filter Icon')]";

    @AndroidFindBy(xpath = FILTER_ICON)
    private WebElement filterIcon;

    public static final String FAILED = "//android.view.View[contains(@content-desc,'FAILED')]";

    @AndroidFindBy(xpath = FAILED)
    private WebElement failed;

    public static final String OTHERS = "//android.widget.TextView[contains(@content-desc,'Others')]";

    @AndroidFindBy(xpath = OTHERS)
    private WebElement others;

    public static final String CMS = "//android.widget.TextView[contains(@content-desc,'Cms')]";

    @AndroidFindBy(xpath = CMS)
    private WebElement cms;

    public static final String COPY_ICON = "//android.view.View[contains(@content-desc,'Copy Icon')]";

    @AndroidFindBy(xpath = COPY_ICON)
    private WebElement copyIcon;

    public static final String EL100 = "//android.widget.TextView[contains(@content-desc,'₹1.00')]";

    @AndroidFindBy(xpath = EL100)
    private WebElement el100;

    public static final String DOWN_ARROW = "//android.view.View[contains(@content-desc,'Down arrow')]";

    @AndroidFindBy(xpath = DOWN_ARROW)
    private WebElement downArrow;

    public static final String DOWNLOAD = "//android.view.View[contains(@content-desc,'Download')]";

    @AndroidFindBy(xpath = DOWNLOAD)
    private WebElement download;

    public static final String HOME = "//android.widget.TextView[contains(@content-desc,'Home')]";

    @AndroidFindBy(xpath = HOME)
    private WebElement home;

    public static final String SERVICES = "//android.widget.TextView[contains(@content-desc,'Services')]";

    @AndroidFindBy(xpath = SERVICES)
    private WebElement services;

    public static final String HISTORY = "//android.widget.TextView[contains(@content-desc,'History')]";

    @AndroidFindBy(xpath = HISTORY)
    private WebElement history;

    public static final String PROFILE = "//android.widget.TextView[contains(@content-desc,'Profile')]";

    @AndroidFindBy(xpath = PROFILE)
    private WebElement profile;

    public HistoryScreen(TestContext context) {
        super(context);
    }
}
