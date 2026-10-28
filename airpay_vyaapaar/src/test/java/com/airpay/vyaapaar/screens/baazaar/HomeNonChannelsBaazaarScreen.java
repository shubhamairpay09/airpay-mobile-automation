package com.airpay.vyaapaar.screens.baazaar;

import com.airpay.vyaapaar.screens.base.BaseScreen;
import com.airpay.mobile.utilities.context.TestContext;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/** Non Channels/Baazaar. Module baazaar. */
public class HomeNonChannelsBaazaarScreen extends BaseScreen {

    public static final String PAGE_ID = "home-non-channels-baazaar";

    public static final String SEARCH_OIL_ALMONDS_EARPHONES_HERE = "//android.widget.TextView[@text='Search oil, almonds, earphones here']";

    @AndroidFindBy(xpath = SEARCH_OIL_ALMONDS_EARPHONES_HERE)
    private WebElement searchOilAlmondsEarphonesHere;

    public static final String CATEGORIES = "//android.widget.TextView[@text='Categories']";

    @AndroidFindBy(xpath = CATEGORIES)
    private WebElement categories;

    public static final String ICON = "//android.view.View[contains(@content-desc,'Icon')]";

    @AndroidFindBy(xpath = ICON)
    private WebElement icon;

    public static final String GROCERY = "//android.widget.TextView[@text='Grocery']";

    @AndroidFindBy(xpath = GROCERY)
    private WebElement grocery;

    public static final String FASHION = "//android.widget.TextView[@text='Fashion']";

    @AndroidFindBy(xpath = FASHION)
    private WebElement fashion;

    public static final String ELECTRONICS = "//android.widget.TextView[@text='Electronics']";

    @AndroidFindBy(xpath = ELECTRONICS)
    private WebElement electronics;

    public static final String HEALTH_WELLNESS = "//android.widget.TextView[@text='Health & Wellness']";

    @AndroidFindBy(xpath = HEALTH_WELLNESS)
    private WebElement healthWellness;

    public static final String BEAUTY_PERSONAL_CARE = "//android.widget.TextView[@text='Beauty & Personal care']";

    @AndroidFindBy(xpath = BEAUTY_PERSONAL_CARE)
    private WebElement beautyPersonalCare;

    public static final String TEST = "//android.widget.TextView[@text='test']";

    @AndroidFindBy(xpath = TEST)
    private WebElement test;

    public static final String GROCERY_HIGHLIGHTS = "//android.widget.TextView[@text='Grocery Highlights']";

    @AndroidFindBy(xpath = GROCERY_HIGHLIGHTS)
    private WebElement groceryHighlights;

    public static final String VIEW_ALL = "//android.widget.TextView[@text='View All']";

    @AndroidFindBy(xpath = VIEW_ALL)
    private WebElement viewAll;

    public static final String FORWARD_ARROW_ICON = "//android.view.View[contains(@content-desc,'Forward Arrow Icon')]";

    @AndroidFindBy(xpath = FORWARD_ARROW_ICON)
    private WebElement forwardArrowIcon;

    public static final String SEREA_IMAGE = "//android.view.View[contains(@content-desc,'Serea image')]";

    @AndroidFindBy(xpath = SEREA_IMAGE)
    private WebElement sereaImage;

    public static final String MANCU_STORE_IMAGE = "//android.view.View[contains(@content-desc,'Mancu Store image')]";

    @AndroidFindBy(xpath = MANCU_STORE_IMAGE)
    private WebElement mancuStoreImage;

    public static final String FROMNEAR_MAIN_STORE_IMAGE = "//android.view.View[contains(@content-desc,'FromNear Main Store image')]";

    @AndroidFindBy(xpath = FROMNEAR_MAIN_STORE_IMAGE)
    private WebElement fromnearMainStoreImage;

    public static final String GLYDIO_DEMO_STORE_IMAGE = "//android.view.View[contains(@content-desc,'Glydio Demo Store image')]";

    @AndroidFindBy(xpath = GLYDIO_DEMO_STORE_IMAGE)
    private WebElement glydioDemoStoreImage;

    public static final String BACK_ICON = "//android.view.View[contains(@content-desc,'Back Icon')]";

    @AndroidFindBy(xpath = BACK_ICON)
    private WebElement backIcon;

    public static final String LOCATION_ON = "//android.view.View[contains(@content-desc,'Location On')]";

    @AndroidFindBy(xpath = LOCATION_ON)
    private WebElement locationOn;

    public static final String LOCATION = "//android.widget.TextView[@text='Location']";

    @AndroidFindBy(xpath = LOCATION)
    private WebElement location;

    public static final String ORDERS = "//android.widget.TextView[@text='Orders']";

    @AndroidFindBy(xpath = ORDERS)
    private WebElement orders;

    public HomeNonChannelsBaazaarScreen(TestContext context) {
        super(context);
    }
}
